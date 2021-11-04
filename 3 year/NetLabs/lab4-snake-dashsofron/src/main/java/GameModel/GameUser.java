package GameModel;

import Handler.SendMessageHandler;
import NetworkModel.AvailableGameInfo;
import NetworkModel.MessageForSending;
import NetworkModel.MessageForTracking;
import NetworkModel.SendingInfoNode;
import Network.Receive;
import Network.ReceiveMulti;
import Network.Send;
import snakesProto.SnakesProto;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameUser {
    private Boolean master;
    private String name;
    private Send sender;
    private Receive receiver;
    private ReceiveMulti receiverAnnounce;

    private SendMessageHandler sendMessageHandler;
    private Game game;
    private List<AvailableGameInfo> availableGames = new ArrayList<>();
    private BlockingQueue<MessageForSending> messagesForSending = new LinkedBlockingQueue<>();
private BlockingQueue<MessageForTracking> messagesForTracking=new LinkedBlockingQueue<>();
    private Thread multiReceiveThread;
    private Thread receiveThread;
    private Thread sendThread;


    public GameUser(InetAddress addr, int port, InetAddress multiAddr, int multiPort, String name) throws IOException {
        DatagramSocket mySocket = new DatagramSocket(port, addr);
        MulticastSocket mSocket = new MulticastSocket(multiPort);
        //System.out.println(mySocket.getLocalAddress() + " " + mySocket.getLocalPort());
        mSocket.joinGroup(multiAddr);

        //HashMap<Integer, SendingInfoNode> players = new HashMap<Integer, SendingInfoNode>();
        sendMessageHandler = new SendMessageHandler(messagesForSending,messagesForTracking/*, players*/, new InetSocketAddress(multiAddr, multiPort));
        receiver = new Receive(mySocket, messagesForTracking,sendMessageHandler/*, players*/);
        receiverAnnounce = new ReceiveMulti(mSocket, sendMessageHandler/*, players*/);
        sender = new Send(mySocket, messagesForSending);
        multiReceiveThread = new Thread(receiverAnnounce);
        receiveThread = new Thread(receiver);
        sendThread = new Thread(sender);
        multiReceiveThread.setPriority(1);
        receiveThread.setPriority(9);
        sendThread.setPriority(8);
        receiveThread.start();
        multiReceiveThread.start();
        sendThread.start();
    }

    public List<AvailableGameInfo> getAvailableGames() {
        return receiverAnnounce.getAvailbleGames();
    }

    public void setGame(Game game) {
        this.game = game;
        game.setMaster(this.master);
        receiver.setGame(game);
        receiverAnnounce.setGame(game);
    }

    public void initGame() {
        game.initGame();
        game.setMaster(master);
    }

    public void addPlayerToMyGame(String name, Integer id, Integer port, String ip, SnakesProto.NodeRole role) {
        game.addPlayer(name, id, port, ip, role);
    }

    public void addSnakeToMyGame(List<Coord> coords, Integer id) {
        game.addSnake(coords, id);
    }

    public List<Coord> myGameHavePlaceForSnake() {
        return game.havePlaceForNewSnake();
    }

    public Game getGame() {
        return game;
    }

    public SendMessageHandler getSendHandler() {
        return sendMessageHandler;
    }

    public Receive getReceiver() {
        return receiver;
    }

    public void clearAvailableGames() {
        availableGames.clear();
    }

    public void setMaster(Boolean master) {
        this.master = master;
        if(game!=null)game.setMaster(master);
    }

}
