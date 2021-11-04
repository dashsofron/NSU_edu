package Network;

import NetworkModel.MessageForSending;
import snakesProto.SnakesProto;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.BlockingQueue;

//отправка всех видов сообщений
//Не зависи от типа игрока и работает везде одинаково
//берет сообщения из очереди на отправку и отправляет

public class Send implements Runnable {

    private DatagramSocket mySocket;
    private BlockingQueue<MessageForSending> messagesForSending;


    public Send(DatagramSocket mySocket, BlockingQueue<MessageForSending> messagesForSending) {
        this.mySocket = mySocket;
        this.messagesForSending = messagesForSending;
        //System.out.println(System.identityHashCode(messagesForSending));
    }

    public void sendMes(byte[] buf, InetAddress addr, int port) throws IOException {
//        byte[] buf = (message);//а можно ли так? Если объект не сериализуемый, будет хуйня.
        if (buf == null) return;
        DatagramPacket packet = new DatagramPacket(buf, buf.length, addr, port);
//        System.out.println(packet.getAddress() + " adr2");
//        System.out.println(packet.getPort() + " port2");
//        System.out.println(packet.getLength() + " len2");
        mySocket.send(packet);

    }

    public void sendMessage(MessageForSending message) throws IOException {
        //System.out.println("sendMulti");
        InetSocketAddress addr = message.getAddr();
//        System.out.println(addr.getAddress() + " adr");
//        System.out.println(addr.getPort() + " port");
//        System.out.println(message.getMes().getTypeCase()+" type");
        sendMes(message.getMes().toByteArray(), addr.getAddress()  , addr.getPort());
        //если тип сообщения не ответ, нужно добавить в список для отслеживания ответов на сообщения сообщений
    }


    @Override
    public void run() {
        while (true) {
            //System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFF");
            try {
                sendMessage(messagesForSending.take());
                //System.out.println("tried");

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
