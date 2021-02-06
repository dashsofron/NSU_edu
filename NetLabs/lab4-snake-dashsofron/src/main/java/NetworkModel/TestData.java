package NetworkModel;

import snakesProto.SnakesProto;

public class TestData {


    public static SnakesProto.GameMessage.AnnouncementMsg getAnnounceTest(){
        SnakesProto.GameConfig conf= SnakesProto.GameConfig.newBuilder()
                .setWidth(500)
                .setHeight(500)
                .setFoodStatic(3)
                .setFoodPerPlayer(2)
                .setStateDelayMs(3)
                .setDeadFoodProb(50)
                .setPingDelayMs(5000)
                .setNodeTimeoutMs(1000)
                .build();
        SnakesProto.GamePlayer player= SnakesProto.GamePlayer.newBuilder()
                .setName("dasha")
                .setId(1)
                .setIpAddress("192.168.1.1")
                .setPort(9091)
                .setRole(SnakesProto.NodeRole.DEPUTY)
                .setScore(0)
                .build();
        SnakesProto.GamePlayers players = SnakesProto.GamePlayers.newBuilder()
                .addPlayers(player)
                .build();


        SnakesProto.GameMessage.AnnouncementMsg mes=SnakesProto.GameMessage.AnnouncementMsg.newBuilder()
                .setConfig(conf)
                .setPlayers(players)
                .setCanJoin(true)
                .build();

        return  mes;
    }

}
