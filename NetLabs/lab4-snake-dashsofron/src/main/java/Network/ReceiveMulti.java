package Network;

import GameModel.Game;
import Handler.SendMessageHandler;
import NetworkModel.AvailableGameInfo;
import NetworkModel.SendingInfoNode;
import com.google.protobuf.InvalidProtocolBufferException;
import snakesProto.SnakesProto;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ReceiveMulti implements Runnable {
    private List<AvailableGameInfo> availableGames = new ArrayList<>();
    private SendMessageHandler sendMessageHandler;
    private Game game;

    //private HashMap<Integer, SendingInfoNode> players;
    private MulticastSocket mSocket;
    public void setGame(Game game) {
        this.game = game;
    }

    public ReceiveMulti(MulticastSocket mSocket, SendMessageHandler sendMessageHandler/*, HashMap<Integer, SendingInfoNode> players*/) throws SocketException {
        this.mSocket = mSocket;
        //System.out.println(mSocket);

        this.sendMessageHandler = sendMessageHandler;
        //this.players = players;
        mSocket.setSoTimeout(0);

    }

    public SnakesProto.GameMessage receiveAnnounceMes() throws InvalidProtocolBufferException, UnknownHostException {
        byte[] buf = new byte[500];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        try {
            //получаем пакет
            mSocket.receive(packet);
        } catch (IOException e) {
            noPacket();
        }
        SnakesProto.GameMessage message = SnakesProto.GameMessage.parseFrom(Arrays.copyOfRange(packet.getData(), 0, packet.getLength()));
        long mesNum = message.getMsgSeq();
        int sender = message.getSenderId();
        int receiver = message.getReceiverId();
        //Для всех сообщений - сначала надо проверить список недавно полученных сообщений, чтобы не получать повторно то же сообщений
        //Если такое сообщение уже было получено - отправить ответ и не обрабатывать
        //иначе обработать, добавить в список полученных и почистить список, ожидающих ответ, если это был ответ


        //тут нужно проверить, получали уже такое сообщение или нет

        handleAnnounce(message.getAnnouncement(), new InetSocketAddress(packet.getAddress(), packet.getPort()));
        //все сообщения нужно добавлять в недавно полученные


        //проверяем потерю для симуляции потерь
        //if (checkReceiving()) {

        //System.out.println("receive announce");
        return message;

    }

    //ничего не пришло за промежуток получения сообщения
    public void noPacket() {

    }

    public void handleAnnounce(SnakesProto.GameMessage.AnnouncementMsg announcementMsg, InetSocketAddress addr) {
//        System.out.println("have game");
//        System.out.println("Config: " + announcementMsg.getConfig());
//        System.out.println("Players: " + announcementMsg.getPlayers());
//        if (announcementMsg.getCanJoin()) System.out.println("can Join!");
//        else System.out.println("can not Join");
        AvailableGameInfo infon = haveThisGame(announcementMsg, addr);
        if (infon != null) availableGames.remove(infon);
        availableGames.add(new AvailableGameInfo(announcementMsg, addr));

    }


    public AvailableGameInfo haveThisGame(SnakesProto.GameMessage.AnnouncementMsg announcementMsg, InetSocketAddress addr) {
        for (AvailableGameInfo gameInfo : availableGames
        ) {
            if (gameInfo.getAddr().equals(addr))
                return gameInfo;

        }
        return null;
    }

    public List<AvailableGameInfo> getAvailbleGames() {
        return availableGames;
    }

    @Override
    public void run() {
        while (true) {
            try {
                receiveAnnounceMes();
            } catch (InvalidProtocolBufferException | UnknownHostException e) {
                System.out.println("announcement receiving troubles ");
            }
        }
    }
}
