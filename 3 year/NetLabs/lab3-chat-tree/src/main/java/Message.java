import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Message implements Serializable {
    private int GUID;//mes num
    private String name;//mes sender name
    private String data;//mes data
    private MessageType type;//mes type - message, connection or answer

    public Message(int GUID, String name, String data) {
        this.GUID = GUID;
        this.name = name;
        this.data = data;
        this.type = MessageType.MESSAGE;
    }

    public Message(String data) {
        this.GUID = -1;
        this.name = null;
        this.data = data;
        this.type = MessageType.MESSAGE;
    }

    public Message(Message mes) {
        this.GUID = mes.getGUID();
        this.name = mes.getName();
        this.data = mes.getData();
        this.type = mes.getType();

    }

    public Message(MessageSending mes) {
        this.GUID = mes.getMes().getGUID();
        this.data = mes.getMes().getData();
        this.name = mes.getMes().name;
        this.type = mes.getMes().getType();

    }

    public Message(MessageTracking mes) {
        this.GUID = mes.getMes().getGUID();
        this.data = mes.getMes().getData();
        this.name = mes.getMes().name;
        this.type = mes.getMes().getType();

    }
    public Message(Integer GUID, String name, String data, MessageType type) {
        this.GUID = GUID;
        this.name = name;
        this.data = data;
        this.type = type;
    }


    public Integer getGUID() {
        return GUID;
    }

    public String getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public MessageType getType() {
        return type;
    }

    public void setGUID(Integer GUID) {
        this.GUID = GUID;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(MessageType type) {
        this.type = type;
    }


//    public Message(MessageTrack mes, MessageType type) {
//        this.GUID = mes.GUID;
//        this.name = mes.name;
//        this.data = mes.data;
//        this.type = type;
//
//    }

//    public Message(MessageTrack mes) {
//        this.GUID = mes.getGUID();
//        this.name = mes.getName();
//        this.data = mes.getData();
//        this.type = mes.getType();
//
//    }
}
