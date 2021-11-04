package NetworkModel;

import snakesProto.SnakesProto;

import java.net.InetSocketAddress;

public class AvailableGameInfo {
    private SnakesProto.GameMessage.AnnouncementMsg info;
    private InetSocketAddress addr;
    public AvailableGameInfo(SnakesProto.GameMessage.AnnouncementMsg info, InetSocketAddress addr){
        this.info=info;
        this.addr=addr;
    }

    public InetSocketAddress getAddr(){
        return  addr;
    }

    public SnakesProto.GameConfig getConfig(){
        return info.getConfig();
    }
    public Boolean getCanJoin(){
        return info.getCanJoin();
    }
    public SnakesProto.GamePlayers getPlayers(){
        return info.getPlayers();
    }
}

