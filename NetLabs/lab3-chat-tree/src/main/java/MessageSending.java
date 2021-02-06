import java.io.Serializable;
import java.net.InetSocketAddress;

public class MessageSending implements Serializable {
    private Message mes;//message
    private ReceiversType receivers;//for what nodes - One particular/ all or all except one
    private InetSocketAddress socket;//if one - receiver socket, if all except one - non receiver socket. If all - nevermind

    //передаем сообщение и информацию о том, кому сообщение надо отправить
    public MessageSending(Message mes, ReceiversType receivers) {
        this.mes = mes;
        this.receivers = receivers;
        socket = null;
    }


    //передаем параметры для сообщения по отдельности
    public MessageSending(Integer GUID, String name, String data, MessageType type, ReceiversType receivers, InetSocketAddress socket) {
        this.mes = new Message(GUID, name, data, type);
        this.receivers = receivers;
        this.socket = socket;
    }


    //передаем параметры для сообщения по отдельности
    public MessageSending(Integer GUID, String name, String data, MessageType type, ReceiversType receivers) {
        this.mes = new Message(GUID, name, data, type);
        this.receivers = receivers;
        this.socket = null;
    }

    public MessageSending(Message mes, ReceiversType receivers, InetSocketAddress socket) {
        this.mes = mes;
        this.receivers = receivers;
        this.socket = socket;
    }

    public Message getMes() {
        return mes;
    }

    public InetSocketAddress getSocket() {
        return socket;
    }

    public ReceiversType getReceiveType() {
        return receivers;
    }
}
