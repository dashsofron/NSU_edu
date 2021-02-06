//Vasya 2001 10
//Oksana 2004 50 2001 "192.168.0.104" (глубина)
//Vova 2005 90 2004 "192.168.0.104" (глубинаx2)
//Artur 2006 50 2004 "192.168.0.104" (глубинаx2)

//Petya 2002 50 2001 "192.168.0.104"
//Olesya 2003 0 2001 "192.168.0.104"  (ширина)


//Olesya 2003 0 2001 "192.168.1.33"  (ширина)
//Oksana 2004 0 2002 "192.168.1.33" (глубина)
//Petya 2002 0 2001 "192.168.1.33"

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Node {
    private List<TreeNode> bond;//nearby nodes
    private InetSocketAddress replacement = null;//node that can replace this in case of dying for tree structure saving
    private DatagramSocket mySocket;
    private int maxTimeout = 3000;
    Send sender;
    Receive receiver;
    GetMessage getter;
    Thread senderThread;
    Thread receiverThread;
    Thread getterThread;

    Node(String name, int myPort, int lostPercent) throws IOException {
        bond = new ArrayList<>();
        mySocket = new DatagramSocket(myPort, InetAddress.getLocalHost());
        mySocket.setSoTimeout(maxTimeout);//время ожидания сообщения сокетом
        BlockingQueue<MessageTracking> messagesForTracking = new LinkedBlockingQueue<>();
        BlockingQueue<MessageSending> messagesForSending = new LinkedBlockingQueue<>();
        sender = new Send(name, mySocket, bond, replacement, messagesForSending, messagesForTracking);
        receiver = new Receive(lostPercent, mySocket, bond, replacement, messagesForSending, messagesForTracking);
        getter = new GetMessage(messagesForSending);
        senderThread = new Thread(sender);
        receiverThread = new Thread(receiver);
        getterThread = new Thread(getter);
        System.out.println(name + " " + InetAddress.getLocalHost() + " " + myPort);
    }

    public Node(String name, int myPort, int lostPercent, int parentPort, String parentIp) throws IOException, ClassNotFoundException {
        bond = new ArrayList<>();
        mySocket = new DatagramSocket(myPort, InetAddress.getLocalHost());
        BlockingQueue<MessageTracking> messagesForTracking = new LinkedBlockingQueue<>();
        mySocket.setSoTimeout(maxTimeout);//время ожидания сообщения сокетом
        BlockingQueue<MessageSending> messagesForSending = new LinkedBlockingQueue<>();
        getter = new GetMessage(messagesForSending);
        senderThread = new Thread(sender);
        receiverThread = new Thread(receiver);
        getterThread = new Thread(getter);
        TreeNode parentNode = new TreeNode(InetAddress.getByName(parentIp), parentPort);
        bond.add(parentNode);
        System.out.println(bond);

        replacement = parentNode.getSocket();
        receiver = new Receive(lostPercent, mySocket, bond, replacement, messagesForSending, messagesForTracking);
        sender = new Send(name, mySocket, bond, replacement, messagesForSending, messagesForTracking);

        sender.sendConnectMessage(parentNode.getSocket());
        System.out.println(name + " " + InetAddress.getLocalHost() + " " + myPort);
    }

    void run() throws IOException, ClassNotFoundException, InterruptedException {
        long time = System.currentTimeMillis();
        int num = 0;
//        receiver.run();
//        sender.run();

//        receiverThread.start();
//        senderThread.start();
        getterThread.start();
        while (true) {
            Message mes = receiver.receiveMes();
            if (System.currentTimeMillis() - time > 1000) {

                sender.sendMessage("hello" + num);
                num++;
                //System.out.println(num);
                time = System.currentTimeMillis();
            }
            //if(num>10)receiver.lostPercent=100;
            sender.checkMessagesForSending();
            sender.checkMessageTracking();
            //sender.checkAliveNodes();
        }

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Node node;
        switch (args.length) {
            case 3:
                node = new Node(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                node.run();
                break;

            case 5:
                node = new Node(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), args[4]);
                node.run();
                break;
            default:
                System.out.println("need 3 or 5 arguments");
        }

    }

}
