package NetworkModel;

import snakesProto.SnakesProto;

import java.net.InetSocketAddress;

public class MessageForTracking {
    SnakesProto.GameMessage message;
    Long time;
    InetSocketAddress addr;
    int resendNum=0;
    public MessageForTracking(SnakesProto.GameMessage message, Long time, InetSocketAddress addr){
        this.message=message;
        this.time=time;
        this.addr=addr;
    }

    public SnakesProto.GameMessage getMes(){
        return message;
    }
    public Long getTime(){
        return  time;
    }
    public InetSocketAddress getAddr(){
        return addr;
    }
    public void incrementResend(){
        resendNum++;
    }
    public int getResend(){
        return  resendNum;
    }
}
