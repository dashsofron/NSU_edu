package GameModel;

import snakesProto.SnakesProto;

import java.util.List;

public class GSnake {
    SnakesProto.GameState.Snake.SnakeState snakeState;
    List<Coord> coords;
    SnakesProto.Direction direction;
    Boolean dead=false;

    public GSnake(SnakesProto.GameState.Snake.SnakeState snakeState, List<Coord> coords, SnakesProto.Direction direction) {
        this.snakeState = snakeState;
        this.coords = coords;
        this.direction = direction;
    }

    public SnakesProto.GameState.Snake.SnakeState getSnakeState(){
        return  snakeState;
    }
    public List<Coord> getCoords(){
        return  coords;
    }
    public Coord getHead(){
        return coords.get(0);
    }
    public SnakesProto.Direction getDirection(){
        return direction;
    }
public Boolean isDead(){
        return dead;
}
    public void setDirection(SnakesProto.Direction dir){
        this.direction=dir;
    }
    public void setSnakeState(SnakesProto.GameState.Snake.SnakeState state){
        this.snakeState=state;
    }
    public void setDeath(){
        dead=true;
    }
}
