package NetworkModel;

import java.net.InetSocketAddress;

public class SendingInfoNode {
    private InetSocketAddress addr;
    private String name;

    public InetSocketAddress getAddr() {
        return addr;
    }

    public String getName() {
        return name;
    }

    public SendingInfoNode(/*Integer myID,*/InetSocketAddress addr, String name) {
        this.addr = addr;
        this.name = name;
    }
}

