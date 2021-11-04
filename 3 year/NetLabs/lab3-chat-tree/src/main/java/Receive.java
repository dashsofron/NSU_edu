import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Receive implements Runnable {
    private List<TreeNode> bond;
    DatagramSocket mySocket;
    int lostPercent;
    InetSocketAddress replacement;
    Serialize serializator = new Serialize();
    BlockingQueue<MessageTracking> messagesForTracking;
    BlockingQueue<MessageSending> messagesForSending;
    Random random = new Random();

    Receive(int lostPercent, DatagramSocket mySocket, List<TreeNode> bond, InetSocketAddress replacement, BlockingQueue<MessageSending> messagesForSending, BlockingQueue<MessageTracking> messagesForTracking) {
        this.lostPercent = lostPercent;
        this.mySocket = mySocket;
        this.bond = bond;
        this.messagesForSending = messagesForSending;
        this.messagesForTracking = messagesForTracking;
        this.replacement = replacement;
    }

//    public void sendLostPer(int n){
//        lostPercent=n;
//    }
//

    //для симуляции потери пакетов проверяем, патерян этот пакет или нет
    boolean checkReceiving() {
        return random.nextInt(100) > lostPercent;
    }

    //удаление полученного сообщения в списке
    public void removeMes(Message receivedMes, InetAddress addr, int port) {
        //если нашли сообщение с таким же адресом и номером, то мы получили ответ на это сообщение и его можно больше не отслеживать
        messagesForTracking.removeIf(message -> (message.getGUID() == receivedMes.getGUID()) && (message.getPort() == port) && (message.getAddress().equals(addr)));
    }

    TreeNode getNodeBySocket(InetSocketAddress socket) {
        for (TreeNode node : bond
        ) {

            if (node.getSocket().getPort() == socket.getPort() && node.getSocket().getAddress().equals(socket.getAddress()))
                return node;
        }
        return null;
    }

    //получение сообщения
    public Message receiveMes() throws ClassNotFoundException, IOException {
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        try {
            //получаем пакет
            mySocket.receive(packet);
        } catch (IOException e) {
            return null;
        }
        //проверяем потерю для симуляции потерь
        if (checkReceiving()) {
            TreeNode node = getNodeBySocket(new InetSocketAddress(packet.getAddress(), packet.getPort()));
            int index = bond.indexOf(node);
            if (node != null)
                bond.get(index).updateLastTimeAction();
            //если пакет получили - сериализуем информацию из байтов и далее работаем с сообщением
            Message mes = serializator.deserialize(packet.getData());
            //в зависимости от типа сообщения будем по-разному обрабатывать его получение

            switch (mes.getType()) {
                //обычное новое сообщение. Добавляем ответ на него для отправления(в очередь для отправки) и выводим.
                //Т.К. на это сообщение ответ не ждем (сами отвечаем), добавляем только в одну очередь!
                case MESSAGE: {
                    //в ответе для его правильной обработки указываем тот же GUID, что и у сообщения
                    //data = thanks
                    //name - не знаем, sender поставит во время отправки, проверив на 0
                    //type - ANSWER
                    //и получатели - один конкретный узел с его сокетом
                    messagesForSending.add(new MessageSending(mes.getGUID(), null, "thanks", MessageType.ANSWER, ReceiversType.ONE,
                            new InetSocketAddress(packet.getAddress(), packet.getPort())));
                    messagesForSending.add(new MessageSending(-1, mes.getName(), mes.getData(), MessageType.MESSAGE, ReceiversType.AllExceptOne, new InetSocketAddress(packet.getAddress(), packet.getPort())));
                    System.out.println(mes.getName() + ": " + mes.getData());
                    return mes;
                }
                //сообщение запроса соединения. Добавяем в список своих узлов и отправляем ответ что все ок. Не выводим
                case CONNECTION: {
//
                    if (node == null)
                        bond.add(new TreeNode(packet.getAddress(), packet.getPort()));
                    if (bond.size() == 1 || replacement == null)
                        replacement = new InetSocketAddress(packet.getAddress(), packet.getPort());
                    messagesForSending.add(new MessageSending(mes.getGUID(), null, "connected", MessageType.ANSWER, ReceiversType.ONE,
                            new InetSocketAddress(packet.getAddress(), packet.getPort())));
                    messagesForSending.add(new MessageSending(0, null, replacement.getAddress().getHostAddress() + "/" + replacement.getPort(), MessageType.REPLACE_INFO, ReceiversType.ALL, replacement));

                    System.out.println(mes.getName() + ": " + mes.getData());
                    System.out.println("my replacement -" + replacement.getHostName() + replacement.getPort());

                    return mes;

                }
                //сообщение - ответ на наше. Удаляем наше из списка ожидания. Никуда не выводим, просто перестаем трекать сообщение
                case ANSWER: {
                    if (mes.getData().equals("connected"))
                        messagesForSending.add(new MessageSending(0, null, replacement.getAddress().getHostAddress() + "/" + replacement.getPort(), MessageType.REPLACE_INFO, ReceiversType.ALL, replacement));
                    removeMes(mes, packet.getAddress(), packet.getPort());
                    return null;
                }
                case REPLACE_INFO: {
                    if (node == null) return null;
                    bond.get(index).setAlter(serializator.getAddressFromMessage(mes.getData()));
                    messagesForSending.add(new MessageSending(mes.getGUID(), null, "replaced", MessageType.ANSWER, ReceiversType.ONE,
                            new InetSocketAddress(packet.getAddress(), packet.getPort())));
                    System.out.println("my node replacement: for " + node.getSocket().getPort() + " - " + node.getReplacement().getPort());
                    return null;
                }


            }
            bond.get(index).updateLastTimeAction();

        }
        return null;
    }

    public void run() {
        while (true) {
            try {
                Message mes = receiveMes();
            } catch (ClassNotFoundException | IOException e) {
                System.out.println("receiving: " + e);
            }
        }
    }
}
