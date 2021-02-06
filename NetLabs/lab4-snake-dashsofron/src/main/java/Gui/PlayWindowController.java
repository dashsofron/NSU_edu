package Gui;

import GameModel.*;
import Handler.SendMessageHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import snakesProto.SnakesProto;

import java.net.UnknownHostException;
import java.util.List;


public class PlayWindowController {
    private Game game;
    private Colors colors = new Colors();
    private Boolean master; //FIXME DELETE
    private SendMessageHandler sendMessageHandler;
    //int myID;
    @FXML
    Canvas canvas;
    @FXML
    TextField lenght;
    @FXML
    TextField width;
    @FXML
    TextField staticFood;
    @FXML
    TextField foodPerPlayer;
    @FXML
    TextField deadFoodProb;
    @FXML
    TextField stateDelay;
    @FXML
    TextField ping;
    @FXML
    TextField nodeTimeout;
    Stage stage;
    @FXML
    javafx.scene.control.Button start;
    @FXML
    TextArea playersInfo;

    private Integer gameTick;
    private Integer widthCellNum;
    private Integer heightCellNum;
    private MyField field;

    void changeState() throws UnknownHostException {
        //сдвигаем змей
        //сначала сдвинем тех, которые идут на еду или свободное поле
        //потом сдвигаем остальных. Если обе пришли в одну точку - обе погибли
        //Если как
        int foodNum = game.getState().getConfig().getFoodStatic() + game.getState().getConfig().getFoodPerPlayer() * game.getAliveSnakesNum();

        List<GPlayer> players = List.copyOf(game.getPlayers());
        for (GPlayer player : players
        ) {
            game.changeSnakeDir(player.getId());
            GSnake snake = player.getSnake();
            if(snake==null)continue;
            if (snake.isDead()) {
                sendMessageHandler.sendError("your snake is dead", game.getAddrById(player.getId()));
                player.setRole(SnakesProto.NodeRole.VIEWER);
                player.setSnake(null);
                sendMessageHandler.sendRoleChange(SnakesProto.NodeRole.VIEWER, SnakesProto.NodeRole.MASTER, player.getId(), game.getMyID(), game.getAddrById(player.getId()));
                //game.getPlayers().remove(player);
                continue;
            }


            //System.out.println(snake.getDirection());

            Coord snakeHead = snake.getHead();
            List<Coord> foodList = List.copyOf(game.getState().getFood());
            Coord newCoord = null;
            switch (snake.getDirection()) {

                case LEFT: {
                    newCoord = new Coord(snakeHead.getI(game.getState().getConfig().getWidth(), -1), snakeHead.getJ(),
                            game.getState().getConfig().getWidth(), game.getState().getConfig().getHeight());

                    break;
                }
                case RIGHT: {
                    newCoord = new Coord(snakeHead.getI(game.getState().getConfig().getWidth(), 1), snakeHead.getJ(),
                            game.getState().getConfig().getWidth(), game.getState().getConfig().getHeight());


                    break;

                }
                case DOWN: {
                    newCoord = new Coord(snakeHead.getI(), snakeHead.getJ(game.getState().getConfig().getHeight(), 1),
                            game.getState().getConfig().getWidth(), game.getState().getConfig().getHeight());

                    break;

                }
                case UP: {
                    newCoord = new Coord(snakeHead.getI(), snakeHead.getJ(game.getState().getConfig().getHeight(), -1),
                            game.getState().getConfig().getWidth(), game.getState().getConfig().getHeight());

                    break;

                }

            }
            snake.getCoords().add(0, newCoord);
            Coord sTail = snake.getCoords().remove(snake.getCoords().size() - 1);

            for (Coord foodItem : foodList
            ) {

                if (foodItem.getJ().equals(newCoord.getJ()) && foodItem.getI().equals(newCoord.getI())) {
                    game.getState().removeFood(foodItem);

                    player.setScore(player.getScore() + 1);
                    snake.getCoords().add(sTail);

                }

            }

//            for (Coord sCoord : snake.getCoords()
//            ) {
//                System.out.println(sCoord.getI() + " " + sCoord.getJ() + " ");
//
//            }
        }
        if (players.size() == 0) {
            Platform.runLater(() -> {
                stage.close();
            });
            return;
        }

        game.getState().fillField();
        if (game.getState().getFood().size() < foodNum)
            game.generateFood(foodNum - game.getState().getFood().size());
//        for (Coord fCoord : game.getState().getFood()
//        ) {
//            System.out.println("food: "+fCoord.getI() + " " + fCoord.getJ() + " ");
//
//        }
        checkSnakesCollide();
        //если мастер помер, выбираем мастера. Если есть заместитель - то он, иначе берем какого-то игрока и выбираем его в мастеры
        //if (players.get(game.getMyID()).getSnake().isDead()) {
        if (!game.haveDeputy() || game.getDeputy().getSnake().isDead()) {
            GPlayer newMaster = game.chooseAlivePlayer();
            if (newMaster != null) {
                newMaster.setRole(SnakesProto.NodeRole.DEPUTY);

                sendMessageHandler.sendRoleChange(SnakesProto.NodeRole.DEPUTY, SnakesProto.NodeRole.MASTER, newMaster.getId(), game.getMyID(), game.getAddrById(newMaster.getId()));
            }
//            } else
//                sendMessageHandler.sendRoleChange(SnakesProto.NodeRole.DEPUTY, SnakesProto.NodeRole.MASTER, game.getDeputy().getId(), game.getMyID(), game.getAddrById(game.getDeputy().getId()));
        }

        game.getState().fillField();

    }

    void checkSnakesCollide() {
        List<GPlayer> players = game.getPlayers();
        //идем по всем игрокам (чтобы пройти по всем змейкам)
        for (int i = 0; i < players.size(); i++) {
            GSnake snake = players.get(i).getSnake();
            Coord snakeHead = snake.getHead();
            //снова идем по всем игрокам, чтобы сравнить координаты
            for (GPlayer player1 : players) {
                List<Coord> snakeCoords = player1.getSnake().getCoords();

                if (player1.equals(players.get(i))) {
                    for (int j = 1; j < snakeCoords.size(); j++)
                        if (snakeHead.getJ().equals(snakeCoords.get(j).getJ()) && snakeHead.getI().equals(snakeCoords.get(j).getI())) {
                            snake.setDeath();
                            break;
                        }
                } else {
                    for (int j = 0; j < snakeCoords.size(); j++)
                        if (snakeHead.getJ().equals(snakeCoords.get(j).getJ()) && snakeHead.getI().equals(snakeCoords.get(j).getI())) {
                            snake.setDeath();

                            if (j == 0) {
                                player1.getSnake().setDeath();

                            } else player1.setScore(player1.getScore() + snakeCoords.size());
                            break;
                        }
                }
            }
        }


    }

    void sendChanges() throws UnknownHostException {
        for (GPlayer player : game.getState().getPlayers()
        ) {

            if (player.getId() != game.getMyID()) {
                sendMessageHandler.sendState(game.getProtoState(), player.getId(), game.getMyID(), game.getAddrById(player.getId()));
            }
            //System.out.println(player.getId() + " " + player.getIpAddress() + " " + player.getPort());

        }
    }

    void redrawField() {
//        for(int i=0;i<game.getState().getConfig().getWidth();i++){
//            for(int j=0;j<game.getState().getConfig().getHeight();j++){
//                System.out.print(game.getField().getCell(i,j)+" ");
//            }
//            System.out.println();
//        }
        double wStep = canvas.getWidth() / game.getState().getConfig().getWidth();
        double hStep = canvas.getHeight() / game.getState().getConfig().getHeight();
        GraphicsContext c = canvas.getGraphicsContext2D();
        Platform.runLater(() -> {
            //System.out.println("YA RISUYU, YA PIDORAS");
            for (int i = 0; i < game.getState().getConfig().getWidth(); i++) {
                for (int j = 0; j < game.getState().getConfig().getHeight(); j++) {
                    c.clearRect(i * wStep, j * hStep, wStep, hStep);
                }
            }

            for (int i = 0; i < game.getState().getConfig().getWidth(); i++) {
                for (int j = 0; j < game.getState().getConfig().getHeight(); j++) {
                    if (field.getCell(i, j) == -1) c.setFill(Paint.valueOf("white"));
                    if (field.getCell(i, j) >= 0) c.setFill(colors.getColor(field.getCell(i, j)));
                    c.fillRect(i * wStep, j * hStep, wStep, hStep);
                    if (field.getCell(i, j) == -2) {
                        c.setFill(Paint.valueOf("black"));
                        c.fillOval(i * wStep, j * hStep, wStep, hStep);
                    }


                }
            }
        });
    }

    void showPlayersIfo() {
        String playersInfoString = "";
        List<GPlayer> players = List.copyOf(game.getPlayers());
        for (GPlayer player : players) {
            playersInfoString = playersInfoString.concat("name: " + player.getName() +
                    " id: " + player.getId() +
                    " port: " + player.getPort() +
                    " ip: " + player.getIpAddress() +
                    " role: " + player.getRole() +
                    " score: " + player.getScore() +
                    "\n");
        }
        playersInfo.setText(playersInfoString);
    }

    public void start() {
        this.gameTick = game.getState().getConfig().getStateDelay();
        this.field = game.getField();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Long time = System.currentTimeMillis();
                while (!Thread.interrupted()) {
                    try {
                        if (master) {
                            changeState();
                            sendChanges();
//                                if(game.getPlayers().size()==0)
//                                    Thread.currentThread().interrupt();
                        }

                        redrawField();

                        showPlayersIfo();
                        Thread.sleep(100);
                        if (game.getPlayers() == null || game.getPlayerNumById(game.getMyID()) == null || game.getPlayers().get(game.getPlayerNumById(game.getMyID())).getSnake() == null) {
                            continue;
                        }
                        if (game.getPlayers().get(game.getPlayerNumById(game.getMyID())) == null
                                || game.getPlayers().get(game.getPlayerNumById(game.getMyID())).getSnake().getSnakeState()
                                == SnakesProto.GameState.Snake.SnakeState.ZOMBIE) {
//                            Platform.runLater(() -> {
//                                stage.close();
//                                if (game.getMaster().getId() == game.getMyID() && game.getDeputy() != null) {
//                                    try {
//                                        sendMessageHandler.sendRoleChange(SnakesProto.NodeRole.MASTER, SnakesProto.NodeRole.NORMAL, game.getDeputy().getId(), game.getMyID(), game.getAddrById(game.getDeputy().getId()));
//                                    } catch (UnknownHostException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
                            return;

                        }
                    } catch (InterruptedException | UnknownHostException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        thread.start();
    }

    public void setMyID(Integer id) {
        game.setMyID(id);
    }

    public void setGame(Game game) {
        this.game = game;

    }

    public void setSendHandler(SendMessageHandler sendMessageHandler) {
        this.sendMessageHandler = sendMessageHandler;
    }


    public void keyHandler(KeyEvent event) {
        //System.out.println(event.getCode() + "COOOOOOOOOOOOOOOOOOD");
        System.out.println(sendMessageHandler);
        System.out.println(game);
        System.out.println(game.getMaster().getId());
        try {
            System.out.println(game.getAddrById(game.getMaster().getId()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if(game.getPlayers().get(game.getPlayerNumById(game.getMyID())).getSnake()==null)
            return;
        switch (event.getCode()) {

            case W:
            case UP: {
                SnakesProto.Direction prevDir = game.getState().getPlayerByNum(game.getMyID()).getSnake().getDirection();
                //if (prevDir == SnakesProto.Direction.DOWN) break;
                if (master) game.setSnakeDirByID(game.getMyID(), SnakesProto.Direction.UP);
                else {
                    try {

                        sendMessageHandler.sendSteer(SnakesProto.Direction.UP, game.getMaster().getId(), game.getMyID(), game.getAddrById(game.getMaster().getId()));
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }

            case S:
            case DOWN: {
                SnakesProto.Direction prevDir = game.getState().getPlayerByNum(game.getMyID()).getSnake().getDirection();
                //if (prevDir == SnakesProto.Direction.UP) break;
                if (master) game.setSnakeDirByID(game.getMyID(), SnakesProto.Direction.DOWN);
                else {
                    try {
                        sendMessageHandler.sendSteer(SnakesProto.Direction.DOWN, game.getMaster().getId(), game.getMyID(), game.getAddrById(game.getMaster().getId()));
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }

                break;
            }
            case A:
            case LEFT: {
                SnakesProto.Direction prevDir = game.getState().getPlayerByNum(game.getMyID()).getSnake().getDirection();
                //if (prevDir == SnakesProto.Direction.RIGHT) break;
                if (master) game.setSnakeDirByID(game.getMyID(), SnakesProto.Direction.LEFT);
                else {
                    try {
                        sendMessageHandler.sendSteer(SnakesProto.Direction.LEFT, game.getMaster().getId(), game.getMyID(), game.getAddrById(game.getMaster().getId()));
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }

                break;
            }
            case D:
            case RIGHT: {
                SnakesProto.Direction prevDir = game.getState().getPlayerByNum(game.getMyID()).getSnake().getDirection();
                //if (prevDir == SnakesProto.Direction.LEFT) break;
                if (master) game.setSnakeDirByID(game.getMyID(), SnakesProto.Direction.RIGHT);
                else {
                    try {
                        sendMessageHandler.sendSteer(SnakesProto.Direction.RIGHT, game.getMaster().getId(), game.getMyID(), game.getAddrById(game.getMaster().getId()));
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }

                break;
            }
            default:
                break;
        }
        event.consume();
    }

    public void setMaster(Boolean master) {
        this.master = master;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
