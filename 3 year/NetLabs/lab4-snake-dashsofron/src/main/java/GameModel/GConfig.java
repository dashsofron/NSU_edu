package GameModel;

import snakesProto.SnakesProto;

public class GConfig {
    private Integer width=50;
    private Integer height=50;
    private Integer foodStatic=1;
    private Integer foodPerPlayer=1;
    private Integer stateDelay=1000;
    private Float deadFoodProb=0.1F;
    private Integer ping=100;
    private Integer nodeTimeout=800;


    public Integer getWidth(){
        return  width;
    }

    public Integer getHeight(){
        return  height;
    }
    public Integer getFoodStatic(){
        return  foodStatic;
    }
    public Integer getFoodPerPlayer(){
        return  foodPerPlayer;
    }
    public Integer getStateDelay(){
        return  stateDelay;
    }

    public  Float getDeadFoodProb(){
        return  deadFoodProb;
    }
    public  Integer getPing(){
        return  ping;
    }
    public Integer getNodeTimeout(){
        return  nodeTimeout;
    }
    GConfig(){

    }
    GConfig(Integer width,Integer height,Integer foodStatic,Float foodPerPlayer,Float deadFoodProb,Integer nodeTimeout,Integer ping,Integer stateDelay){
        this.width=width;
        this.height=height;
        this.foodStatic=foodStatic;
        this.foodPerPlayer=Math.round(foodPerPlayer);
        this.deadFoodProb=deadFoodProb;
        this.nodeTimeout=nodeTimeout;
        this.ping=ping;
        this.stateDelay=stateDelay;
    }

}
