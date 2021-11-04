package NetworkModel;

import snakesProto.SnakesProto;

import java.net.InetSocketAddress;

public class MessageForSending {
    private SnakesProto.GameMessage message;
    private InetSocketAddress address;
    public MessageForSending(SnakesProto.GameMessage message, InetSocketAddress address){
        this.message=message;
        this.address=address;
    }
    public InetSocketAddress getAddr(){
        return address;
    }
    public SnakesProto.GameMessage getMes(){
        return  message;
    }

}
