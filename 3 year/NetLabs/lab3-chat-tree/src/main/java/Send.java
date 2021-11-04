import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Send implements Runnable {
    private List<TreeNode> bond;
    DatagramSocket mySocket;
    String name;
    Serialize serializator = new Serialize();
    BlockingQueue<MessageTracking> messagesForTracking;
    BlockingQueue<MessageSending> messagesForSending;
    InetSocketAddress replacement;

    private int sended = 0;
    long ttl = 4000;
    int maxSending = 10;

    int getNext(String mes) {
        //System.out.println(mes+" "+sended);
        return sended++;
    }

    Send(String name, DatagramSocket mySocket, List<TreeNode> bond, InetSocketAddress replacement, BlockingQueue<MessageSending> messagesForSending, BlockingQueue<MessageTracking> messagesForTracking) {
        this.name = name;
        this.mySocket = mySocket;
        this.bond = bond;
        this.messagesForSending = messagesForSending;
        this.messagesForTracking = messagesForTracking;
        this.replacement = replacement;

    }


    //функция запроса связи
    //в данном варианте сразу отправляется и идет на отслеживание. Можно сделать так, что отправка всех сообщений только из очереди на отправку
    public void sendConnectMessage(InetSocketAddress socket) throws IOException {
        byte[] buf;
        Message mes = new Message(getNext("CONNECT"), name, "connection", MessageType.CONNECTION);
        buf = serializator.serialize(mes);
        //отправили сообщение  узлу, с которым соединяемся
        mySocket.send(new DatagramPacket(buf, buf.length, socket.getAddress(), socket.getPort()));
        //добавили сообщение для ожидания ответа
        messagesForTracking.add(new MessageTracking(mes, socket.getAddress(), socket.getPort()));
        System.out.println("my replacement -" + replacement.getHostName() + " " + replacement.getPort());

        //messages.add(new MessageTrack(mes, socket, MessageType.MESSAGE));
    }

    //переслать сообщение связанным узлам при получении
    public void forwardMes(MessageSending mes) throws IOException {
        byte[] buf;
        Message sending = new Message(mes);
        if (bond.size() == 0) return;


        if (bond.size() == 1 && bond.get(0).getSocket().getAddress().equals(mes.getSocket().getAddress()) && bond.get(0).getSocket().getPort() == mes.getSocket().getPort())
            return;
        sending.setGUID(getNext("FORWARD"));
        buf = serializator.serialize(sending);

        for (TreeNode node : bond
        ) {
            //если это узел, который отправил это сообщение - не надо ему отправлять
            if (node.getSocket().getAddress().equals(mes.getSocket().getAddress()) && node.getSocket().getPort() == mes.getSocket().getPort())
                continue;
            //иначе отправляем сообщение узлу
            mySocket.send(new DatagramPacket(buf, buf.length, node.getSocket().getAddress(), node.getSocket().getPort()));
            //добавляем запись для отслеживания ответа
            messagesForTracking.add(new MessageTracking(sending, node.getSocket()));

        }
    }


    //отправить всем - в случае считывания с консоли
    public void sendMessage(String mes) throws IOException {
        if (bond.size() == 0) return;
        byte[] buf;
        int mesNum = getNext("JUSTSEND");
        Message message = new Message(mesNum, name, mes/*+": "+mesNum*/);
        buf = serializator.serialize(message);

        for (TreeNode node : bond
        ) {
            mySocket.send(new DatagramPacket(buf, buf.length, node.getSocket().getAddress(), node.getSocket().getPort()));
            messagesForTracking.add(new MessageTracking(message, node.getSocket().getAddress(), node.getSocket().getPort()));
        }
    }

    public void sendMessage(Message mes) throws IOException {
        if (bond.size() == 0) return;
        byte[] buf;
        int mesNum = getNext("JUSTSEND");
        mes.setGUID(mesNum);
        mes.setName(name);
        buf = serializator.serialize(mes);

        for (TreeNode node : bond
        ) {
            mySocket.send(new DatagramPacket(buf, buf.length, node.getSocket().getAddress(), node.getSocket().getPort()));
            messagesForTracking.add(new MessageTracking(mes, node.getSocket().getAddress(), node.getSocket().getPort()));

        }
    }

    //тут может быть сообщение любого типа.Надо отправить и добавить в отслеживание
    public void checkMessagesForSending() throws IOException {//переделать без фора , просто отправку через некст и удаление сразу
        for (MessageSending messageForSending : messagesForSending) {
            if (messageForSending.getReceiveType() == ReceiversType.ALL) {
                if (messageForSending.getMes().getType() == MessageType.REPLACE_INFO) {
                    sendMessage(messageForSending.getMes());
                } else {
                    sendMessage(messageForSending.getMes().getData());
                }
            } else if (messageForSending.getReceiveType() == ReceiversType.AllExceptOne) {
                forwardMes(messageForSending);
            } else {
                Message sendingMes = new Message(messageForSending);
                if (sendingMes.getName() == null) sendingMes.setName(name);
                byte[] buf = serializator.serialize(sendingMes);
                //отправили сообщение  узлу, с которым соединяемся
                mySocket.send(new DatagramPacket(buf, buf.length, messageForSending.getSocket().getAddress(), messageForSending.getSocket().getPort()));
                //добавили сообщение для ожидания ответа. Если сообщение типа ответ - не отслеживаем получение
                if (sendingMes.getType() != MessageType.ANSWER)
                    messagesForTracking.add(new MessageTracking(sendingMes, messageForSending.getSocket().getAddress(), messageForSending.getSocket().getPort()));
                //отправили - удалили, это сообщение больше не нуждается в отправке
            }
            messagesForSending.remove(messageForSending);

        }

    }

    TreeNode getNodeBySocket(InetSocketAddress socket) {
        for (TreeNode node : bond
        ) {
            if (node.getSocket().getAddress().equals(socket.getAddress()) && node.getSocket().getPort() == socket.getPort())
                return node;
        }
        return null;
    }

    public void checkMessageTracking() throws IOException {
        for (MessageTracking message : messagesForTracking) {
            if (System.currentTimeMillis() - message.time > ttl) {
                if (maxSending > message.confirmed) {
                    byte[] buf;
                    buf = serializator.serialize(new Message(message));
                    mySocket.send(new DatagramPacket(buf, buf.length, message.getAddress(), message.getPort()));
                    message.updateSendingTime();
                    message.confirmed++;
                } else {
                    rebuildTree(getNodeBySocket(new InetSocketAddress(message.getAddress(), message.getPort())));
                    //System.out.println(bond);
                }

            }
        }
    }


    public void cleanMes(InetSocketAddress socket) {
        messagesForTracking.removeIf(mes -> mes.getAddress().equals(socket.getAddress()) && mes.getPort() == socket.getPort());
    }


    public void rebuildTree(TreeNode deadnode) throws IOException {
        if (deadnode == null) return;
        //если мы не заместитель - надо подключиться к заместителю

        if (deadnode.getReplacement() == null) return;
        if (mySocket.getLocalAddress().equals(deadnode.getReplacement().getAddress()) && mySocket.getLocalPort() == deadnode.getReplacement().getPort()) {
            System.out.println("I'm replacement");


        } else {
            //отправляем запрос

            sendConnectMessage(deadnode.getReplacement());
            bond.add(new TreeNode(deadnode.getReplacement().getAddress(), deadnode.getReplacement().getPort()));
        }
        System.out.println(deadnode.getSocket().getHostName() + deadnode.getSocket().getPort() + "- сдох");
        //удаляем сдохшего собрата
        bond.remove(deadnode);
        //и сдохшие сообщения для него
        cleanMes(deadnode.getSocket());
        //если сдохший был заместителем узла, нужно выбрать нового
        if (replacement == null || replacement.getAddress().equals(deadnode.getSocket().getAddress()) && replacement.getPort() == deadnode.getSocket().getPort()) {
            if (bond.size() == 0) {
                replacement = null;
                System.out.println("I'm all alone");

            } else {
                replacement = bond.get(0).getSocket();
                //отправили узел заместитель всем кроме него самого
                messagesForSending.add(new MessageSending(getNext("REPLACE"), name, replacement.getAddress().getHostAddress() + "/" + replacement.getPort(), MessageType.REPLACE_INFO, ReceiversType.ALL, replacement));
                System.out.println("my new replacement -" + replacement.getHostName() + replacement.getPort());
            }

        }

    }

//    public void checkAliveNodes(){
//        bond.removeIf(node -> System.currentTimeMillis() - node.getTime() > ttl);
//    }


    public void run() {
        long time = System.currentTimeMillis();
        int num = 0;
        while (true) {
            try {
                if (System.currentTimeMillis() - time > 1000) {
                    sendMessage("hello" + num);
                    num++;
                    time = System.currentTimeMillis();
                }
                checkMessagesForSending();
                checkMessageTracking();
                //checkAliveNodes();

            } catch (IOException e) {
                System.out.println("sending error: " + e);
            }
        }
    }


    //проверка статусов сообщений - отправка ответов, если надо и переотправка сообщений, если не были получены ответы о их получении
//    public void checkMessages() throws IOException, ClassNotFoundException {
//        Iterator<MessageTrack> it = messages.iterator();
//        //System.out.println("checking-------------------------------");
//        while (it.hasNext()) {
//            MessageTrack mes = it.next();
//            if (mes.type == MessageType.ANSWER) {
//                byte[] buf;
//                mes.name = name;
//                //mes.GUID = sended++;
//                buf = serializator.serialize(new Message(mes));
//                //Message meS=serializator.deserialize(buf);
//                //System.out.println("send answer for message: "+"socket: "+mes.socket+" id: "+mes.GUID+" name: "+mes.name);
//
//                //messages.add(new MessageTracking(mes, mes.socket, 0));
//                mySocket.send(new DatagramPacket(buf, buf.length, mes.socket.getAddress(), mes.socket.getPort()));
//                it.remove();
//
//            }
//
//            else //System.out.println("it is out message waiting for answer");
//                if (System.currentTimeMillis() - mes.ttl > timeout) {
//                    if (maxSending > mes.confirmed) {
//                        byte[] buf;
//                        buf = serializator.serialize(new Message(mes));
//                        mySocket.send(new DatagramPacket(buf, buf.length, mes.getSocket().getAddress(), mes.getSocket().getPort()));
//                        mes.updateTTL();
//                    }
//                    //else  удалить ноду потому что она считается сдохшей
//                }
//        }
//
//
//    }
}

