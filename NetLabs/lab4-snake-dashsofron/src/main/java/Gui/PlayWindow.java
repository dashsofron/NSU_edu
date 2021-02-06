package Gui;

import GameModel.Game;
import Handler.SendMessageHandler;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class PlayWindow {
    private String name;

    public PlayWindow(Game game,SendMessageHandler sendMessageHandler,Integer myID,Boolean master) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().
                getClassLoader().getResource("gameField.fxml")));

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                PlayWindowController controller = loader.getController();
                controller.setGame(game);
                controller.setSendHandler(sendMessageHandler);
                controller.setMyID(myID);
                controller.setMaster(master);
                controller.canvas.setStyle("-fx-border-color: black;");
                Stage stage = new Stage();
                controller.setStage(stage);

                stage.setTitle("Snake");
                Scene scene = new Scene(root);
                scene.setOnKeyPressed(controller::keyHandler);
                stage.setScene(scene);
                root.requestFocus();
                stage.getIcons().add(new Image("Images\\-KqV-PDJ3uQ.jpg"));
                controller.start();
                stage.show();


            }
        });

    }


}
