import java.net.InetAddress;
import java.net.InetSocketAddress;

public class MessageTracking {
    private Message mes;
    //private int GUID;// mes num
    private InetSocketAddress socket;// sender socket
    long time;// time that we can wait for answer
    int confirmed = 0;//num of re-send attempts

    public MessageTracking(Message mes, InetSocketAddress socket) {
        this.mes = mes;
        this.socket = socket;
        time = System.currentTimeMillis();
    }

    public MessageTracking(Message mes, InetAddress address, int port) {
        this.mes = mes;
        this.socket = new InetSocketAddress(address, port);
        time = System.currentTimeMillis();

    }

    public Message getMes() {
        return mes;
    }

    public int getGUID() {
        return mes.getGUID();
    }

    public InetAddress getAddress() {
        return socket.getAddress();
    }

    public int getPort() {
        return socket.getPort();
    }

    public void updateSendingTime() {
        time = System.currentTimeMillis();
    }
}
