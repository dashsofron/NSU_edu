package GameModel;

import GameModel.GSnake;
import snakesProto.SnakesProto;

public class GPlayer {
    private String name;
    private int id;
    private int port;
    private String ip;
    private int score;
    private SnakesProto.NodeRole role;
    private GSnake snake;
    SnakesProto.Direction curDir;
    GPlayer(String name,int id,int port,String ip,SnakesProto.NodeRole role,int score){
        this.name=name;
        this.id=id;
        this.ip=ip;
        this.port=port;
        this.score=score;
        this.role=role;
    }

    public int getId(){

        return id;
    }

    public SnakesProto.Direction getDir(){
        return curDir;
    }
    public void setDIr(SnakesProto.Direction dir){
        this.curDir=dir;
    }
    public String getName(){
        return  name;
    }
    public  int getPort(){
        return port;
    }
    public  String getIpAddress(){
        return  ip;
    }
    public  int getScore(){
        return  score;
    }
    public SnakesProto.NodeRole getRole(){
        return  role;
    }
    public GSnake getSnake(){
        return snake;
    }

    GPlayer(SnakesProto.GamePlayer player){
        name=player.getName();
        id=player.getId();
        port=player.getPort();
        ip=player.getIpAddress();
        score=player.getScore();
        role=player.getRole();

    }
    public void setRole(SnakesProto.NodeRole role){
        this.role=role;
    }
    public void setSnake(GSnake snake){
        this.snake=snake;
    }
    public void setScore(int newScore){
        score=newScore;
    }
}
