package sample;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiClass {
    private InetAddress multiAdres;
    private int port;
    private final long timeout = 6000;
    private long lastMesTime;
    private boolean changed = false;
    private MulticastSocket mSocket;
    //List<CopyNode> copies;
    private Map<InetAddress, Long> copies;

    MultiClass(String multiAdres, int port) throws IOException {
        this.multiAdres = InetAddress.getByName(multiAdres);
        this.port = port;
        mSocket = new MulticastSocket(port);
        mSocket.setSoTimeout((int) timeout);
        mSocket.joinGroup(this.multiAdres);
        copies = new HashMap<InetAddress, Long>();

    }

    private void send(String message) throws IOException {
        byte[] buf = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, multiAdres, port);
        mSocket.send(packet);

    }

    private DatagramPacket receive() {
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        try {
            mSocket.receive(packet);
        } catch (IOException e) {

            return null;
        }
        return packet;
    }

    private String packetGetData(DatagramPacket packet) {
        String received = new String(
                packet.getData(), 0, packet.getLength());
        return received + " : " + packet.getAddress().toString();
    }

    private void checkAliveNodes() {
        List<InetAddress> delete = new ArrayList<InetAddress>();
        for (InetAddress key : copies.keySet()
        ) {
            if (System.currentTimeMillis() - copies.get(key) > timeout) {
                changed = true;
                delete.add(key);
            }
        }
        for (InetAddress key : delete) {
            copies.remove(key);
        }
    }

    private void getAliveNodes() {
        if (changed) {
            System.out.println("alive");
            System.out.println("------------------------");
            for (InetAddress key : copies.keySet()
            ) {
                System.out.println(key);
            }
            System.out.println("------------------------");
        }

    }

    void run() throws IOException {
        lastMesTime = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - lastMesTime > timeout / 2) {
                send("it's me");
                lastMesTime = System.currentTimeMillis();
            }

            DatagramPacket packet = receive();
            if (packet == null) continue;
            lastMesTime = System.currentTimeMillis();
            System.out.println(packetGetData(packet));
            checkAliveNodes();
            if (!copies.containsKey(packet.getAddress())) copies.put(packet.getAddress(), System.currentTimeMillis());
            getAliveNodes();

        }
//mSocket.leaveGroup(multiAdres);

//mSocket.close();
    }


}