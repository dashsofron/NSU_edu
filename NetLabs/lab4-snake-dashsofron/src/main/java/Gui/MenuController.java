package Gui;

import GameModel.Game;
import NetworkModel.AvailableGameInfo;
import GameModel.GameUser;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import snakesProto.SnakesProto;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class MenuController {
    private InetAddress multiAddr = InetAddress.getByName("224.0.147.1");
    private int multiPort = 9090;
    private List<AvailableGameInfo> availableGamesList = new ArrayList<>();
    private final Object lock = new Object();
    /**
     * menu elements
     */
    private GameUser user;
    @FXML
    TextField name;
    @FXML
    TextField port;
    @FXML
    Button newGame;
    @FXML
    Button startGame;
    @FXML
    Button availableGames;
    @FXML
    ChoiceBox<Integer> chooseGameNum;
    @FXML
    TextArea gameInfo;

    @FXML
    Label infoLabel;

    @FXML
    Label gameInfoHeader;
    @FXML
    Button getUserInfo;
    private String nameValue;
    private String portValue;

    public MenuController() throws UnknownHostException {
    }

    public void tryGetData() throws IOException {
        nameValue = name.getText();
        portValue = port.getText();
        if (portValue != null && nameValue != null) {
            name.setEditable(false);
            port.setEditable(false);
            getUserInfo.setDisable(true);
            infoLabel.setText("get it");
            newGame.setDisable(false);
            startGame.setDisable(false);
            chooseGameNum.setDisable(false);
            gameInfo.setDisable(false);
            gameInfoHeader.setDisable(false);
            availableGames.setDisable(false);
            user = new GameUser(InetAddress.getLocalHost(), Integer.parseInt(port.getText()), multiAddr, multiPort, name.getText());
            user.getReceiver().setController(this);
            availableGamesList = user.getAvailableGames();

            //user.availableGames.add(new AvailableGameInfo(TestData.getAnnounceTest(), new InetSocketAddress("192.168.1.1", 9091)));
            //тут надо поставить прослушку announce
        }
    }


    public void getAvailableGames() {
        chooseGameNum.getItems().clear();
        gameInfo.clear();
        String games = "";
        String config = "config:\n";
        String players = " players number: ";
        String address = " address: ";

        int n = 1;
        List<Integer> nums = new ArrayList<>();
        if (availableGamesList.isEmpty()) return;
        for (AvailableGameInfo gameMes : availableGamesList
        ) {
            nums.add(n);
            games = games.concat("game number:" + n++ + "\n");
            games = games.concat(address + gameMes.getAddr() + " ");
            if (gameMes.getCanJoin()) games = games.concat("can join\n");
            games = games.concat(config + gameMes.getConfig());
            games = games.concat(players + gameMes.getPlayers().getPlayersCount() + "\n");
            games = games.concat(players + gameMes.getPlayers() + "\n");

            games = games.concat("****************");

        }
        chooseGameNum.setItems(FXCollections.observableArrayList(nums));

        gameInfo.setText(games);
    }


    public void goToMyGame() {

        try {
            user.setGame(new Game(user.getSendHandler()));
            try {
                user.initGame();
                user.addPlayerToMyGame(name.getText(), 0, Integer.parseInt(port.getText()), InetAddress.getLocalHost().toString(), SnakesProto.NodeRole.MASTER);
                user.addSnakeToMyGame(user.myGameHavePlaceForSnake(), 0);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            new PlayWindow(user.getGame(),user.getSendHandler(),0,true);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    Long time = System.currentTimeMillis();
                    user.getSendHandler().sendAnnounce(user.getGame().getState().getProtoConfig(), user.getGame().getState().getProtoPlayers(), true, 0, 0);
                    //System.out.println("sendMulti");
                    while (!Thread.interrupted()&&user.getGame().getPlayers().size()>0) {
                        try {
                            Thread.sleep(1000);
                            user.getSendHandler().sendAnnounce(user.getGame().getState().getProtoConfig(), user.getGame().getState().getProtoPlayers(), true, 0, 0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //System.out.println("sendMulti");

                    }
                    //System.out.println("YA POHODU SDOH");
                }


            });
            thread.start();
            //тут ссылку на игру надо отдать юзеру, чтобы он мог ей управлять
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    public void goToGame(Integer myID, Integer ownerID) throws Exception {
        //user.getGame().addPlayer(name.getText(), myID, Integer.parseInt(port.getText()), InetAddress.getLocalHost().toString(), SnakesProto.NodeRole.NORMAL);
        //user.game.showState();
        new PlayWindow(user.getGame(),user.getSendHandler(),myID,false);


    }

    public void startNewGame() {
        user.setMaster(true);
        goToMyGame();
    }

    public void sendJoin(Integer num) {
        try {
            user.setGame(new Game(user.getSendHandler()));
            user.initGame();
            //user.receiveMessageHandler.setGame(user.game);
            //System.out.println(availableGamesList.get(num - 1).getAddr());
            user.getSendHandler().sendJoin(name.getText(), availableGamesList.get(num - 1).getAddr());
            user.getGame().setConfig(availableGamesList.get(num - 1).getConfig());
            user.getGame().setPlayers(availableGamesList.get(num - 1).getPlayers());
            //user.game.setPlayers(availableGamesList.get(num-1).getPlayers());

            //user.game.addPlayer(name.getText(),0,Integer.parseInt(port.getText()),InetAddress.getLocalHost().toString(), SnakesProto.NodeRole.NORMAL);

            //тут ссылку на игру надо отдать юзеру, чтобы он мог ей управлять
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void joinGame() {
        user.setMaster(false);
        Integer num = chooseGameNum.getValue();
        if (num != null)
            sendJoin(num);

    }
}
