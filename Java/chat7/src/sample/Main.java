package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import server.ChatServer;

import javax.swing.*;

public class Main extends Application {
    Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        loader.load(getClass().getResource("sample.fxml").openStream());
        controller = loader.getController();
        primaryStage.setTitle("Chatio");
        primaryStage.getIcons().add(new Image("message_icon.jpg"));

        controller.setStage(primaryStage);
        primaryStage.setScene(new Scene(controller.pane));
        primaryStage.getScene().setOnKeyPressed(event -> {
            try {
                controller.keyHandler(event);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
