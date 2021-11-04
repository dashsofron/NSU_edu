package GameModel;

import snakesProto.SnakesProto;

import java.util.ArrayList;
import java.util.List;

public class GState {
    private Integer stateOrder = 0;
    private List<GPlayer> players = new ArrayList<>();
    private GConfig config;
    private MyField field;//цифра - номер змейки. -1 пустое поле, -2 еда
    private List<Coord> food = new ArrayList<>();

    public synchronized void addFood(Coord coord) {
        food.add(coord);
    }

    public synchronized void removeFood(Coord coord) {
        food.remove(coord);
    }

    public void updateState(SnakesProto.GameState state) {
        setConfig(state.getConfig());
        setPlayers(state.getPlayers());
        setStateOrder(state.getStateOrder());
        food.clear();
        for (SnakesProto.GameState.Coord c : state.getFoodsList()) {
            addFood(new Coord(c.getX(), c.getY(), state.getConfig().getWidth(), state.getConfig().getHeight()));

        }
        for (int i = 0; i < state.getSnakesCount(); i++) {
            SnakesProto.GameState.Snake snake = state.getSnakes(i);
            List<Coord> coords = new ArrayList<>();
            for (int k = 0; k < snake.getPointsCount(); k++)
                coords.add(new Coord(snake.getPoints(k).getX(), snake.getPoints(k).getY(), getConfig().getWidth(), getConfig().getHeight()));
            GSnake newSnake = new GSnake(snake.getState(), coords, snake.getHeadDirection());
            getPlayerByNum(snake.getPlayerId()).setSnake(newSnake);
        }
        fillField();
    }

    public void fillField() {
        for (int i = 0; i < config.getWidth(); i++)
            for (int j = 0; j < config.getHeight(); j++)
                field.setCell(i, j, -1);
        for (Coord foodCoord : food) {
            field.setCell(foodCoord.getI(), foodCoord.getJ(), -2);
        }
        //System.out.println(players);
        for (GPlayer player : players) {
            //System.out.println();
            //System.out.println(player);
            if(player.getSnake()==null)continue;
            if (player.getSnake().getSnakeState() == SnakesProto.GameState.Snake.SnakeState.ALIVE)
                for (Coord snakeC : player.getSnake().getCoords()
                ) {
                    field.setCell(snakeC.getI(), snakeC.getJ(), player.getId());
                }

        }
    }

    public void addPlayer(String name, Integer id, Integer port, String addr, SnakesProto.NodeRole role) {
        players.add(new GPlayer(name, id, port, addr, role, 0));
//        SnakesProto.GamePlayer player = SnakesProto.GamePlayer.newBuilder()
//                .setName(name)
//                .setId(id)
//                .setIpAddress(addr)
//                .setPort(port)
//                .setRole(role)
//                .setScore(0)
//                .build();
//        SnakesProto.GamePlayers.Builder builder = SnakesProto.GamePlayers.newBuilder();
//        if (players != null) {
//            List<SnakesProto.GamePlayer> pl = players.getPlayersList();
//            for (SnakesProto.GamePlayer player1 : pl
//            ) {
//                builder.addPlayers(player1);
//
//            }
//        }
//        builder.addPlayers(player);
//        this.players = builder.build();


    }

    public MyField getField() {
        return field;
    }

    public void setField(MyField field) {
        this.field = field;
    }

    public SnakesProto.GamePlayers getProtoPlayers() {
        SnakesProto.GamePlayers.Builder builder = SnakesProto.GamePlayers.newBuilder();
        for (GPlayer player : players
        ) {
            builder.addPlayers(SnakesProto.GamePlayer.newBuilder()
                    .setName(player.getName())
                    .setId(player.getId())
                    .setPort(player.getPort())
                    .setIpAddress(player.getIpAddress())
                    .setScore(player.getScore())
                    .setRole(player.getRole())
                    .build()
            );

        }
        return builder.build();
    }

    public List<GPlayer> getPlayers() {
        return players;
    }

    public List<Coord> getFood() {
        return food;
    }

    public List<SnakesProto.GameState.Snake> getProtoSnakes() {
        List<SnakesProto.GameState.Snake> snakes = new ArrayList<>();
        for (GPlayer player : players
        ) {
            GSnake snake = player.getSnake();
            SnakesProto.GameState.Snake.Builder builder = SnakesProto.GameState.Snake.newBuilder();
            builder.setState(snake.getSnakeState());
            builder.setHeadDirection(snake.getDirection());
            builder.setPlayerId(player.getId());
            for (Coord coord : snake.getCoords()
            ) {
                builder.addPoints(coord.getProtoCoord());
            }
            snakes.add(builder.build());

        }
        return snakes;

    }

    public List<SnakesProto.GameState.Coord> getProtoFood() {
        List<SnakesProto.GameState.Coord> food = new ArrayList<>();
        for (int i = 0; i < config.getWidth(); i++)
            for (int j = 0; j < config.getHeight(); j++)
                if (field.getCell(i, j) == -2)
                    food.add(SnakesProto.GameState.Coord.newBuilder().setX(i).setY(j).build());
        return food;
    }

    public SnakesProto.GameState getProtoState() {
        //еда
        SnakesProto.GameState.Builder builder = SnakesProto.GameState.newBuilder();
        builder.setConfig(getProtoConfig());
        builder.setStateOrder(stateOrder++);
        builder.setPlayers(getProtoPlayers());
        List<SnakesProto.GameState.Snake> snakes = getProtoSnakes();
        for (SnakesProto.GameState.Snake snake : snakes
        ) {
            builder.addSnakes(snake);

        }
        List<SnakesProto.GameState.Coord> food = getProtoFood();
        for (SnakesProto.GameState.Coord coord : food
        ) {
            builder.addFoods(coord);
        }

        return builder.build();

    }


    public SnakesProto.GameConfig getProtoConfig() {
        return SnakesProto.GameConfig.newBuilder()
                .setWidth(config.getWidth())
                .setHeight(config.getHeight())
                .setFoodStatic(config.getFoodStatic())
                .setFoodPerPlayer(config.getFoodPerPlayer())
                .setStateDelayMs(config.getStateDelay())
                .setDeadFoodProb(config.getDeadFoodProb())
                .setPingDelayMs(config.getPing())
                .setNodeTimeoutMs(config.getNodeTimeout())
                .build();
    }

    public GConfig getConfig() {
        return config;
    }

    public GPlayer getPlayerByNum(int id) {
        for (GPlayer player : players
        ) {
            if (player.getId() == id) return player;

        }
        return null;
    }

    public void setPlayers(List<GPlayer> players) {
        this.players = players;
    }

    public void setPlayers(SnakesProto.GamePlayers players) {
        this.players.clear();
        List<SnakesProto.GamePlayer> players1 = players.getPlayersList();
        for (SnakesProto.GamePlayer player : players1
        ) {
            this.players.add(new GPlayer(player));
        }
    }

    public void setConfig() {
        this.config = new GConfig();
//                SnakesProto.GameConfig.newBuilder()
//                .setWidth(50)
//                .setHeight(50)
//                .setFoodStatic(1)
//                .setFoodPerPlayer(1)
//                .setStateDelayMs(1000)
//                .setDeadFoodProb(0.1F)
//                .setPingDelayMs(100)
//                .setNodeTimeoutMs(800)
//                .build();
    }

    public void setConfig(GConfig config) {
        this.config = config;
    }

    public void setConfig(SnakesProto.GameConfig config) {

        this.config = new GConfig(config.getWidth(), config.getHeight(), config.getFoodStatic(), config.getFoodPerPlayer(), config.getDeadFoodProb(), config.getNodeTimeoutMs(), config.getPingDelayMs(), config.getStateDelayMs());
    }

    public void setStateOrder(int stateOrder) {
        this.stateOrder = stateOrder;
    }

    public void setSnakes() {

    }

    public void setFood() {

    }

}
