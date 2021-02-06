
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class TreeNode {
    private InetSocketAddress socket;
    private InetSocketAddress replacement;
    private long lastTimeAction;

    public TreeNode(InetAddress ip, int myPort) throws SocketException {
        socket = new InetSocketAddress(ip, myPort);
        lastTimeAction = System.currentTimeMillis();
    }

    public void setAlter(InetSocketAddress node) {
        this.replacement = node;
    }

    public InetSocketAddress getReplacement() {
        return replacement;
    }

    public InetSocketAddress getSocket() {
        return socket;
    }

    public long getTime() {
        //System.out.println(lastTimeAction);
        return lastTimeAction;
    }

    public void updateLastTimeAction() {
        lastTimeAction = System.currentTimeMillis();
    }
}
