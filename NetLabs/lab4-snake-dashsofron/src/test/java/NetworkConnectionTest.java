//import GameModel.GameUser;
//import Model.SendingInfoNode;
//import org.junit.Test;
//import snakesProto.SnakesProto;
//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.InetSocketAddress;
//
//public class NetworkConnectionTest {
//    @Test
//    public void AckTest() throws IOException {
//        InetAddress multiAddr = InetAddress.getByName("224.0.147.1");
//        int multiPort = 9090;
//        InetAddress addr = InetAddress.getLocalHost();
//        int port1 = 9091;
//        int port2 = 9092;
//        GameUser user1 = new GameUser(addr, port1, multiAddr, multiPort,"dasha");
//        GameUser user2 = new GameUser(addr, port2, multiAddr, multiPort,"artur");
//
//
//        InetSocketAddress firstAddr=new InetSocketAddress(addr,port1);
//        InetSocketAddress secondAddr=new InetSocketAddress(addr,port2);
//
//        SendingInfoNode info1=new SendingInfoNode(firstAddr,"dasha");
//        SendingInfoNode info2=new SendingInfoNode(secondAddr,"artur");
//
//        user1.gameaddPlayer(info2,2);
//        user2.receiveMessageHandler.addPlayer(info1,1);
//
//        user2.sendMessageHandler.sendAck(0L,1,2);
//        user2.sender.sendMessage();
//
//        user1.receiver.receiveMes();
//        user1.receiveMessageHandler.receiveMes();
//
//    }
//    @Test
//    public void AnnounceTest() throws IOException {
//        InetAddress multiAddr = InetAddress.getByName("224.0.147.1");
//        int multiPort = 9090;
//        InetAddress addr = InetAddress.getLocalHost();
//        int port1 = 9091;
//        int port2 = 9092;
//        GameUser user1 = new GameUser(addr, port1, multiAddr, multiPort,"dasha");
//        GameUser user2 = new GameUser(addr, port2, multiAddr, multiPort,"artur");
//
//        InetSocketAddress firstAddr=new InetSocketAddress(addr,port1);
//        InetSocketAddress secondAddr=new InetSocketAddress(addr,port2);
//
//        SendingInfoNode info1=new SendingInfoNode(firstAddr,"dasha");
//        SendingInfoNode info2=new SendingInfoNode(secondAddr,"artur");
//
//        user1.receiveMessageHandler.addPlayer(info2,2);
//        user1.receiveMessageHandler.addPlayer(info1,1);
//
//        user2.receiveMessageHandler.addPlayer(info1,1);
//        user2.receiveMessageHandler.addPlayer(info1,2);
//        SnakesProto.GameConfig conf= SnakesProto.GameConfig.newBuilder()
//                .setWidth(500)
//                .setHeight(500)
//                .setFoodStatic(3)
//                .setFoodPerPlayer(2)
//                .setStateDelayMs(3)
//                .setDeadFoodProb(50)
//                .setPingDelayMs(5000)
//                .setNodeTimeoutMs(1000)
//                .build();
//        SnakesProto.GamePlayer player= SnakesProto.GamePlayer.newBuilder()
//                .setName("dasha")
//                .setId(1)
//                .setIpAddress(addr.getHostAddress())
//                .setPort(port1)
//                .setRole(SnakesProto.NodeRole.DEPUTY)
//                .setScore(0)
//                .build();
//        SnakesProto.GamePlayers players = SnakesProto.GamePlayers.newBuilder()
//                .addPlayers(player)
//                .build();
//
//
//        user2.sendMessageHandler.sendAnnounce(conf,players,true,1,2);
//        user2.sender.sendMessage();
//
//        user1.receiverAnnounce.receiveAnnounceMes();
//        user1.receiveMessageHandler.receiveMes();
//    }
//    @Test
//    public void ErrorTest() throws IOException {
//        InetAddress multiAddr = InetAddress.getByName("224.0.147.1");
//        int multiPort = 9090;
//        InetAddress addr = InetAddress.getLocalHost();
//        int port1 = 9091;
//        int port2 = 9092;
//        GameUser user1 = new GameUser(addr, port1, multiAddr, multiPort,"dasha");
//        GameUser user2 = new GameUser(addr, port2, multiAddr, multiPort,"artur");
//
//        InetSocketAddress firstAddr=new InetSocketAddress(addr,port1);
//        InetSocketAddress secondAddr=new InetSocketAddress(addr,port2);
//
//        SendingInfoNode info1=new SendingInfoNode(firstAddr,"dasha");
//        SendingInfoNode info2=new SendingInfoNode(secondAddr,"artur");
//
//        user1.receiveMessageHandler.addPlayer(info2,2);
//        user2.receiveMessageHandler.addPlayer(info1,1);
//
//        user2.sendMessageHandler.sendError("shit happened",1,2);
//        user2.sender.sendMessage();
//
//        user1.receiver.receiveMes();
//        user1.receiveMessageHandler.receiveMes();
//    }
//    @Test
//    public void JoinTest() throws IOException {
//        InetAddress multiAddr = InetAddress.getByName("224.0.147.1");
//        int multiPort = 9090;
//        InetAddress addr = InetAddress.getLocalHost();
//        int port1 = 9091;
//        int port2 = 9092;
//        GameUser user1 = new GameUser(addr, port1, multiAddr, multiPort,"dasha");
//        GameUser user2 = new GameUser(addr, port2, multiAddr, multiPort,"artur");
//
//        InetSocketAddress firstAddr=new InetSocketAddress(addr,port1);
//        user2.sendMessageHandler.sendJoin("dasha",firstAddr);
//        user2.sender.sendMessage();
//
//        user1.receiver.receiveMes();
//        user1.receiveMessageHandler.receiveMes();
//    }
//
//    @Test
//    public void PingTest() throws IOException {
//        InetAddress multiAddr = InetAddress.getByName("224.0.147.1");
//        int multiPort = 9090;
//        InetAddress addr = InetAddress.getLocalHost();
//        int port1 = 9091;
//        int port2 = 9092;
//        GameUser user1 = new GameUser(addr, port1, multiAddr, multiPort,"dasha");
//        GameUser user2 = new GameUser(addr, port2, multiAddr, multiPort,"artur");
//
//        InetSocketAddress firstAddr=new InetSocketAddress(addr,port1);
//        InetSocketAddress secondAddr=new InetSocketAddress(addr,port2);
//
//        SendingInfoNode info1=new SendingInfoNode(firstAddr,"dasha");
//        SendingInfoNode info2=new SendingInfoNode(secondAddr,"artur");
//
//        user1.receiveMessageHandler.addPlayer(info2,2);
//        user2.receiveMessageHandler.addPlayer(info1,1);
//
//        user2.sendMessageHandler.sendPing(1,2);
//        user2.sender.sendMessage();
//
//        user1.receiver.receiveMes();
//        user1.receiveMessageHandler.receiveMes();
//    }
//
//    @Test
//    public void RoleChangeTest() throws IOException {
//        InetAddress multiAddr = InetAddress.getByName("224.0.147.1");
//        int multiPort = 9090;
//        InetAddress addr = InetAddress.getLocalHost();
//        int port1 = 9091;
//        int port2 = 9092;
//        GameUser user1 = new GameUser(addr, port1, multiAddr, multiPort,"dasha");
//        GameUser user2 = new GameUser(addr, port2, multiAddr, multiPort,"artur");
//
//        InetSocketAddress firstAddr=new InetSocketAddress(addr,port1);
//        InetSocketAddress secondAddr=new InetSocketAddress(addr,port2);
//
//        SendingInfoNode info1=new SendingInfoNode(firstAddr,"dasha");
//        SendingInfoNode info2=new SendingInfoNode(secondAddr,"artur");
//
//        user1.receiveMessageHandler.addPlayer(info2,2);
//        user2.receiveMessageHandler.addPlayer(info1,1);
//
//        user2.sendMessageHandler.sendRoleChange(SnakesProto.NodeRole.DEPUTY, SnakesProto.NodeRole.MASTER,1,2);
//
//        user2.sender.sendMessage();
//
//        user1.receiver.receiveMes();
//        user1.receiveMessageHandler.receiveMes();
//    }
//
//    @Test
//    public void StateTest() throws IOException {
//        InetAddress multiAddr = InetAddress.getByName("224.0.147.1");
//        int multiPort = 9090;
//        InetAddress addr = InetAddress.getLocalHost();
//        int port1 = 9091;
//        int port2 = 9092;
//        GameUser user1 = new GameUser(addr, port1, multiAddr, multiPort,"dasha");
//        GameUser user2 = new GameUser(addr, port2, multiAddr, multiPort,"artur");
//
//        InetSocketAddress firstAddr=new InetSocketAddress(addr,port1);
//        InetSocketAddress secondAddr=new InetSocketAddress(addr,port2);
//
//        SendingInfoNode info1=new SendingInfoNode(firstAddr,"dasha");
//        SendingInfoNode info2=new SendingInfoNode(secondAddr,"artur");
//
//        user1.receiveMessageHandler.addPlayer(info2,2);
//        user1.receiveMessageHandler.addPlayer(info1,1);
//
//        user2.receiveMessageHandler.addPlayer(info1,1);
//        user2.receiveMessageHandler.addPlayer(info1,2);
//
//        //тут нужно полность задать состояние игры
//        SnakesProto.GameConfig conf= SnakesProto.GameConfig.newBuilder()
//                .setWidth(500)
//                .setHeight(500)
//                .setFoodStatic(3)
//                .setFoodPerPlayer(2)
//                .setStateDelayMs(3)
//                .setDeadFoodProb(50)
//                .setPingDelayMs(5000)
//                .setNodeTimeoutMs(1000)
//                .build();
//        SnakesProto.GamePlayer player= SnakesProto.GamePlayer.newBuilder()
//                .setName("dasha")
//                .setId(1)
//                .setIpAddress(addr.getHostAddress())
//                .setPort(port1)
//                .setRole(SnakesProto.NodeRole.DEPUTY)
//                .setScore(0)
//                .build();
//        SnakesProto.GamePlayers players = SnakesProto.GamePlayers.newBuilder()
//                .addPlayers(player)
//                .build();
//
//        SnakesProto.GameState.Coord snakeC= SnakesProto.GameState.Coord.newBuilder()
//                .setX(0)
//                .setY(0)
//                .build();
//        SnakesProto.GameState.Snake snake= SnakesProto.GameState.Snake.newBuilder()
//                .setState(SnakesProto.GameState.Snake.SnakeState.ALIVE)
//                .setPlayerId(1)
//                .setHeadDirection(SnakesProto.Direction.RIGHT)
//                .build();
//        snake.toBuilder().addPoints(snakeC).build();
//        SnakesProto.GameState.Coord foods= SnakesProto.GameState.Coord.newBuilder()
//                .setX(1)
//                .setY(1)
//                .build();
//
//        SnakesProto.GameState state   = SnakesProto.GameState.newBuilder()
//                .setStateOrder(0)
//                .setConfig(conf)
//                .addSnakes(snake)
//                .setPlayers(players)
//                .addFoods(foods)
//                .build();
//        //state.getPlayers().toBuilder().addPlayers(player).build();
//
//        user2.sendMessageHandler.sendState(state,1,2);
//        user2.sender.sendMessage();
//
//        user1.receiver.receiveMes();
//        user1.receiveMessageHandler.receiveMes();
//    }
//    @Test
//    public void SteerTest() throws IOException {
//        InetAddress multiAddr = InetAddress.getByName("224.0.147.1");
//        int multiPort = 9090;
//        InetAddress addr = InetAddress.getLocalHost();
//        int port1 = 9091;
//        int port2 = 9092;
//        GameUser user1 = new GameUser(addr, port1, multiAddr, multiPort,"dasha");
//        GameUser user2 = new GameUser(addr, port2, multiAddr, multiPort,"artur");
//
//        InetSocketAddress firstAddr=new InetSocketAddress(addr,port1);
//        InetSocketAddress secondAddr=new InetSocketAddress(addr,port2);
//
//        SendingInfoNode info1=new SendingInfoNode(firstAddr,"dasha");
//        SendingInfoNode info2=new SendingInfoNode(secondAddr,"artur");
//
//        user1.receiveMessageHandler.addPlayer(info2,2);
//        user2.receiveMessageHandler.addPlayer(info1,1);
//
//        user2.sendMessageHandler.sendSteer(SnakesProto.Direction.RIGHT,1,2);
//        user2.sender.sendMessage();
//
//        user1.receiver.receiveMes();
//        user1.receiveMessageHandler.receiveMes();
//    }
//
//}
