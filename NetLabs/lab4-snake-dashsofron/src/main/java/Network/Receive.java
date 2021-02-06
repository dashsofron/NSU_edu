package Network;

import GameModel.Coord;
import GameModel.GPlayer;
import GameModel.Game;
import Gui.MenuController;
import Handler.SendMessageHandler;
import NetworkModel.MessageForTracking;
import snakesProto.SnakesProto;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Receive implements Runnable {
    private Integer id = 1;
    private Game game;
    private MenuController controller;
    private SendMessageHandler sendMessageHandler;
    private BlockingQueue<MessageForTracking> messagesForTracking;
    //private Map<Integer, SendingInfoNode> players;
    private Integer myID = 0;
    private DatagramSocket mySocket;
    int resendNum = 5;

    //мультисокет надо будет запускать в другом потоке, чтобы оба могли повиснуть
    public Receive(DatagramSocket mySocket, BlockingQueue<MessageForTracking> messagesForTracking, SendMessageHandler sendMessageHandler/*, HashMap<Integer, SendingInfoNode> players*/) throws SocketException {
        this.mySocket = mySocket;
        mySocket.setSoTimeout(0);
        this.sendMessageHandler = sendMessageHandler;
        this.messagesForTracking = messagesForTracking;

        //this.players = players;

    }

    void removeByNum(Long num, int sender, int receiver) {
        messagesForTracking.removeIf(message -> (message.getMes().getMsgSeq() == num) && (message.getMes().getSenderId() == sender) && (message.getMes().getReceiverId() == receiver));

    }

    //получение сообщения и отправка на обработку
    public SnakesProto.GameMessage receiveMes(DatagramPacket packet) throws Exception {

        SnakesProto.GameMessage message = SnakesProto.GameMessage.parseFrom(Arrays.copyOfRange(packet.getData(), 0, packet.getLength()));

        long mesNum = message.getMsgSeq();
        int sender = message.getSenderId();
        int receiver = message.getReceiverId();
        //Для всех сообщений - сначала надо проверить список недавно полученных сообщений, чтобы не получать повторно то же сообщений
        //Если такое сообщение уже было получено - отправить ответ и не обрабатывать
        //иначе обработать, добавить в список полученных и почистить список, ожидающих ответ, если это был ответ

        //тут нужно проверить, получали уже такое сообщение или нет
        switch (message.getTypeCase()) {
            //подтверждение получения сообщения - игрок получил сообщение. Удаляем сообщение из списка ожидания и ничего не отправляем игроку
            case ACK: {
                //Если это ответ на Join,нужно вытащить свой идентификатор и нужен receive
                //Чтобы понять, что сообщение было join - нужно найти тип в списке сообщений ожидающих ответа (по номеру mesNum)
                //В обработке просто ищем сообщение с этим номером в хранилище и удаляем его, т.к. не ждем больше ответ
                //Если у сообщения был тип join, нужно постаить себе id для игры
                handleAck(mesNum, sender, receiver);
                break;
            }
            //новый игрок хочет присоединиться. Проверяем, есть ли свободное место. Если есть - добавляем, иначе ошибка операции
            //уведомление об идущей игре. Просто вывести инфу об игре
            //ошибка операции. Нужно вывести строку с ошибкой
            case ERROR: {
                handleError(message.getError(), mesNum, sender, new InetSocketAddress(packet.getAddress(), packet.getPort()));
                break;

            }
            case JOIN: {
                //System.out.println("SOSU TUT");
                handleJoin(message.getJoin(), mesNum, packet.getPort(), packet.getAddress(), message.getSenderId());
                break;

            }
            //говорим, что мы живы ответом на это сообщение
            case PING: {
                handlePing(mesNum, sender, new InetSocketAddress(packet.getAddress(), packet.getPort()));
                break;

            }
            //смена роли одного из игроков
            case ROLE_CHANGE: {
                handleRoleChange(message.getRoleChange(), mesNum, sender, new InetSocketAddress(packet.getAddress(), packet.getPort()));
                break;

            }

            //состояние игры от центр. Обновить свою инфу для правильного вывода
            case STATE: {
                handleState(message.getState(), mesNum, sender, new InetSocketAddress(packet.getAddress(), packet.getPort()));
                break;

            }
            //поворот головы змеи от нецентр. Нужно обновить инфу для правильной отрисовки
            case STEER: {
                handleSteer(message.getSteer(), mesNum, sender, new InetSocketAddress(packet.getAddress(), packet.getPort()));
                break;

            }


            //без типа - вывести какую-нибудь ошибку
            case TYPE_NOT_SET: {
                handleMes();
                break;

            }
        }
        return message;

    }


    public void setController(MenuController controller) {
        this.controller = controller;
    }

    public void setGame(Game game) {
        this.game = game;
    }


    public void setMyID(Integer myID) {
        this.myID = myID;
    }

    public int getNewId() {
        return id++;
    }

    public void handleAck(Long mesNum, Integer sender, Integer receiver) throws Exception {
        System.out.println("get ack for " + mesNum + " message");
        if (myID != receiver) {
            myID = receiver;
            controller.goToGame(myID, sender);
            //System.out.println(myID);
            //System.out.println(receiver);

        }
        removeByNum(mesNum, sender, receiver);

        //добавить себе игру
        //Нужно удалить сообщени, на которое получили ответ из списка ожидания ответов
    }


    public void handleError(SnakesProto.GameMessage.ErrorMsg errorMsg, Long mesNum, Integer sender, InetSocketAddress addr) {
        System.out.println("some error: " + errorMsg.getErrorMessage());
        sendMessageHandler.sendAck(mesNum, sender, myID, addr);

    }


    public void handleJoin(SnakesProto.GameMessage.JoinMsg joinMsg, Long mesNum, Integer port, InetAddress addr, int sender) throws UnknownHostException {
        System.out.println("get join request from " + joinMsg.getName());
        List<Coord> snakeC = game.havePlaceForNewSnake();
        if (snakeC != null) {
            //SendingInfoNode newNode = new SendingInfoNode(new InetSocketAddress(addr, port), joinMsg.getName());
            Integer newID = getNewId();
            //players.put(newID, newNode);

            sendMessageHandler.sendAck(mesNum, newID, myID, new InetSocketAddress(addr, port));


            if (game.haveDeputy())
                game.addPlayer(joinMsg.getName(), newID, port, addr.toString(), SnakesProto.NodeRole.NORMAL);
            else {
                game.addPlayer(joinMsg.getName(), newID, port, addr.toString(), SnakesProto.NodeRole.DEPUTY);
                sendMessageHandler.sendRoleChange(SnakesProto.NodeRole.DEPUTY, SnakesProto.NodeRole.MASTER, newID, myID, new InetSocketAddress(addr, port));
            }

            game.addSnake(snakeC, newID);

            //game.showState();


            //sendMessageHandler.sendAck(mesNum, id, myID);
            //добавить себе игрока
            //надо бы еще координаты змейки кинуть
        } else {
            sendMessageHandler.sendError("no place for new snake", new InetSocketAddress(addr, port));
            System.out.println("no place for new snake ");
        }
        //sendMessageHandler.sendAck(mesNum, sender, myID, new InetSocketAddress(addr, port));


    }

    public void handlePing(long mesNum, Integer sender, InetSocketAddress addr) {
        System.out.println("get ping from " + sender);
        //sendMessageHandler.sendAck(mesNum,sender,myID);
        sendMessageHandler.sendAck(mesNum, sender, myID, addr);

    }

    public void handleRoleChange(SnakesProto.GameMessage.RoleChangeMsg roleChangeMsg, Long mesNum, Integer sender, InetSocketAddress addr) {
        //sendMessageHandler.sendAck(mesNum,sender,myID);
        System.out.println("role changing: sender role: " + roleChangeMsg.getSenderRole() + " my role: " + roleChangeMsg.getReceiverRole());
        game.changeNodeRole(myID, roleChangeMsg.getReceiverRole());
        if (roleChangeMsg.getReceiverRole() == SnakesProto.NodeRole.MASTER) {
            game.setMaster(true);
            for (GPlayer player:game.getPlayers()
                 ) {
                try {
                    sendMessageHandler.sendRoleChange(SnakesProto.NodeRole.NORMAL, SnakesProto.NodeRole.MASTER,player.getId(),myID,game.getAddrById(player.getId()));
                    GPlayer alive=game.chooseAlivePlayer();
                    if(alive!=null) sendMessageHandler.sendRoleChange(SnakesProto.NodeRole.DEPUTY, SnakesProto.NodeRole.MASTER,alive.getId(),myID,game.getAddrById(alive.getId()));

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

            }
            //game.getPlayers().get(game.getPlayerNumById(myID)).setRole(SnakesProto.NodeRole.MASTER);

        }

        if (roleChangeMsg.getReceiverRole() == SnakesProto.NodeRole.DEPUTY) {
            game.getPlayers().get(game.getPlayerNumById(myID)).setRole(SnakesProto.NodeRole.DEPUTY);


        }

        if (roleChangeMsg.getSenderRole() == SnakesProto.NodeRole.MASTER) {
            game.getPlayers().get(game.getPlayerNumById(sender)).setRole(SnakesProto.NodeRole.MASTER);

        }

            game.showState();
        sendMessageHandler.sendAck(mesNum, sender, myID, addr);

    }

    public void handleState(SnakesProto.GameMessage.StateMsg stateMsg, Long mesNum, Integer sender, InetSocketAddress addr) {
        //System.out.println("get new state, stateNum: " + stateMsg.getState().getStateOrder());
        game.updateState(stateMsg.getState());
        //if(game.getPlayers().get(game.getPlayerNumById(myID)).getSnake().isDead())
        //sendMessageHandler.sendAck(mesNum,sender,myID);
        sendMessageHandler.sendAck(mesNum, sender, myID, addr);

    }

    public void handleSteer(SnakesProto.GameMessage.SteerMsg steerMsg, Long mesNum, Integer sender, InetSocketAddress addr) {
        System.out.println("get steer message with " + steerMsg.getDirection() + " direction");
        game.changeSnakeDirection(steerMsg.getDirection(), sender);
        //sendMessageHandler.sendAck(mesNum,sender,myID);
        sendMessageHandler.sendAck(mesNum, sender, myID, addr);

    }


    public void handleMes() {

    }

    void checkAnswers() {
        for (MessageForTracking mes : messagesForTracking
        ) {

            if (System.currentTimeMillis() - mes.getTime() > game.getState().getConfig().getPing() && mes.getResend() < resendNum)
                sendMessageHandler.sendMessage(mes.getMes(), mes.getAddr());
            else {
                sendMessageHandler.sendError("you're dead", mes.getAddr());
                //тут систему надо перестроить
                try {
                    changeSuperStar(mes.getMes().getSenderId());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void changeSuperStar(int id) throws UnknownHostException {
        //если мастер не отвечает и мы обычный игрок - заменяем мастера, если мы заместитель - ставим себе мастера и добавляем мультикаст
        //если мы мастер и не отвечает заместитель, меняем его
        game.markDead(id);
        if (myID == game.getMaster().getId() && id == game.getDeputy().getId()) {
            GPlayer alive = game.chooseAlivePlayer();
            if (alive != null) {
                alive.setRole(SnakesProto.NodeRole.DEPUTY);
                sendMessageHandler.sendRoleChange(SnakesProto.NodeRole.DEPUTY, SnakesProto.NodeRole.MASTER, alive.getId(), myID, game.getAddrById(alive.getId()));
            }
        }
        if (id == game.getMaster().getId()) {
            game.getDeputy().setRole(SnakesProto.NodeRole.MASTER);

            if (myID == game.getMaster().getId()) {
                game.setMaster(true);
                for (GPlayer player : game.getPlayers()
                ) {
                    //sendMessageHandler.sendRoleChange(SnakesProto.NodeRole.MASTER, SnakesProto.NodeRole.NORMAL, player.getId(), myID, game.getAddrById(player.getId()));
                    GPlayer alive = game.chooseAlivePlayer();
                    if (alive != null) {
                        alive.setRole(SnakesProto.NodeRole.DEPUTY);
                        sendMessageHandler.sendRoleChange(SnakesProto.NodeRole.DEPUTY, SnakesProto.NodeRole.MASTER, alive.getId(), myID, game.getAddrById(alive.getId()));
                    }

                }
//                GPlayer alive = game.chooseAlivePlayer();
//                if (alive != null) {
//                    alive.setRole(SnakesProto.NodeRole.DEPUTY);
//                    sendMessageHandler.sendRoleChange(SnakesProto.NodeRole.DEPUTY, SnakesProto.NodeRole.MASTER, alive.getId(), myID, game.getAddrById(alive.getId()));
//                }
//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        sendMessageHandler.sendAnnounce(game.getState().getProtoConfig(), game.getState().getProtoPlayers(), true, 0, 0);
//                        while (!Thread.interrupted() && game.getPlayers().size() > 0) {
//                            try {
//                                Thread.sleep(1000);
//                                sendMessageHandler.sendAnnounce(game.getState().getProtoConfig(), game.getState().getProtoPlayers(), true, 0, 0);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            game.removePlayerByID(id);
//                            //System.out.println("sendMulti");
//
//                        }
//                        //System.out.println("YA POHODU SDOH");
//                    }
//
//
//                });
                //thread.start();
            }

        }
    }


    @Override
    public void run() {
        byte[] buf = new byte[500];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (game != null) {
                    checkAnswers();
                    try {
                        Thread.sleep(game.getState().getConfig().getPing());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        });
        thread.start();
        while (true) {
            try {
                mySocket.receive(packet);
                //System.out.println("poimal norm packet");
                receiveMes(packet);

            } catch (IOException e) {
                System.out.println("receiving troubles ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
