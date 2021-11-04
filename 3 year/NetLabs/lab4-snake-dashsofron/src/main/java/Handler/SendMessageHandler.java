package Handler;

import NetworkModel.MessageForSending;
import NetworkModel.MessageForTracking;
import NetworkModel.SendingInfoNode;
import snakesProto.SnakesProto;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

//обрабатывает запросы на отправку сообщений, добавляет готовые сообщения в очередь на отправку
public class SendMessageHandler {
    private BlockingQueue<MessageForTracking> messagesForTracking;
    private BlockingQueue<MessageForSending> messagesForSending;
    //private Map<Integer, SendingInfoNode> players;
    private InetSocketAddress multicast;
    private Long myMessageNum=0L;



    public SendMessageHandler(BlockingQueue<MessageForSending> messagesForSending, BlockingQueue<MessageForTracking> messagesForTracking/*, HashMap<Integer, SendingInfoNode> players*/, InetSocketAddress multicast) {
        this.messagesForSending = messagesForSending;
        this.messagesForTracking=messagesForTracking;
       // System.out.println(System.identityHashCode(messagesForSending));

        //this.players = players;
        this.multicast=multicast;
        //System.out.println(multicast);

    }
//    public InetSocketAddress getAddr(Integer receiver) {
//        return players.getOrDefault(receiver, null).getAddr();
//    }

    public void sendAck(Long mesNum,Integer receiver, Integer sender,InetSocketAddress addr) {
        SnakesProto.GameMessage mes = SnakesProto.GameMessage.newBuilder()
                .setMsgSeq(mesNum)
                .setSenderId(sender)
                .setReceiverId(receiver)
                .setAck(SnakesProto.GameMessage.AckMsg.newBuilder().build())
                .build();

        messagesForSending.add(new MessageForSending(mes,addr));
    }

    public void sendAnnounce(SnakesProto.GameConfig config, SnakesProto.GamePlayers players, Boolean canJoin, Integer receiver, Integer sender) {
        SnakesProto.GameMessage mes = SnakesProto.GameMessage.newBuilder()
                .setMsgSeq(myMessageNum++)
                .setSenderId(sender)
                .setReceiverId(receiver)
                .setAnnouncement(SnakesProto.GameMessage.AnnouncementMsg.newBuilder()
                        .setConfig(config)
                        .setPlayers(players)
                        .setCanJoin(canJoin)
                        .build())
                .build();
        messagesForSending.add(new MessageForSending(mes,multicast));
    }


    public void sendError(String errorStr,InetSocketAddress nodeAddr) {
        SnakesProto.GameMessage mes = SnakesProto.GameMessage.newBuilder()
                .setMsgSeq(myMessageNum++)
                .setSenderId(0)
                .setReceiverId(0)
                .setError(SnakesProto.GameMessage.ErrorMsg.newBuilder()
                        .setErrorMessage(errorStr)
                        .build())
                .build();
        messagesForSending.add(new MessageForSending(mes,nodeAddr));
        messagesForTracking.add(new MessageForTracking(mes,System.currentTimeMillis(),nodeAddr));

    }

    public void sendJoin(String name,InetSocketAddress masterAddr) {
        SnakesProto.GameMessage mes = SnakesProto.GameMessage.newBuilder()
                .setMsgSeq(myMessageNum++)
                .setSenderId(0)
                .setReceiverId(0)
                .setJoin(SnakesProto.GameMessage.JoinMsg.newBuilder()
                        .setName(name))
                .build();
        messagesForSending.add(new MessageForSending(mes,masterAddr));
        messagesForTracking.add(new MessageForTracking(mes,System.currentTimeMillis(),masterAddr));

    }

    public void sendPing(Integer receiver, Integer sender,InetSocketAddress addr) {
        SnakesProto.GameMessage mes = SnakesProto.GameMessage.newBuilder()
                .setMsgSeq(myMessageNum++)
                .setSenderId(sender)
                .setReceiverId(receiver)
                .setPing(SnakesProto.GameMessage.PingMsg.newBuilder().build())
                .build();
        messagesForSending.add(new MessageForSending(mes,addr));
        messagesForTracking.add(new MessageForTracking(mes,System.currentTimeMillis(),addr));

    }

    public void sendRoleChange(SnakesProto.NodeRole receiverRole, SnakesProto.NodeRole senderRole, Integer receiver, Integer sender,InetSocketAddress addr) {
        SnakesProto.GameMessage mes = SnakesProto.GameMessage.newBuilder()
                .setMsgSeq(myMessageNum++)
                .setSenderId(sender)
                .setReceiverId(receiver)
                .setRoleChange(SnakesProto.GameMessage.RoleChangeMsg.newBuilder()
                        .setReceiverRole(receiverRole)
                        .setSenderRole(senderRole)
                        .build())
                .build();
        messagesForSending.add(new MessageForSending(mes,addr));
        messagesForTracking.add(new MessageForTracking(mes,System.currentTimeMillis(),addr));

    }

    public void sendState(SnakesProto.GameState state, Integer receiver, Integer sender,InetSocketAddress addr) {
        SnakesProto.GameMessage mes = SnakesProto.GameMessage.newBuilder()
                .setMsgSeq(myMessageNum++)
                .setSenderId(sender)
                .setReceiverId(receiver)
                .setState(SnakesProto.GameMessage.StateMsg.newBuilder()
                        .setState(state)
                        .build())
                .build();
        //System.out.println("addr in stateMes " + addr);
        messagesForSending.add(new MessageForSending(mes,addr));
        messagesForTracking.add(new MessageForTracking(mes,System.currentTimeMillis(),addr));

    }

    public void sendSteer(SnakesProto.Direction direction, Integer receiver, Integer sender,InetSocketAddress addr) {
        SnakesProto.GameMessage mes = SnakesProto.GameMessage.newBuilder()
                .setMsgSeq(myMessageNum++)
                .setSenderId(sender)
                .setReceiverId(receiver)
                .setSteer(SnakesProto.GameMessage.SteerMsg.newBuilder()
                        .setDirection(direction)
                        .build())
                .build();
        //System.out.println(receiver + " MASTER ID");
        messagesForSending.add(new MessageForSending(mes,addr));
        messagesForTracking.add(new MessageForTracking(mes,System.currentTimeMillis(),addr));

    }

    public  void sendMessage(SnakesProto.GameMessage mes,InetSocketAddress addr){
        messagesForSending.add(new MessageForSending(mes,addr));

    }

}
