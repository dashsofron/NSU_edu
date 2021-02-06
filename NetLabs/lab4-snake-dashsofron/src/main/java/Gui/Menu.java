package Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Menu extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().
                getClassLoader().getResource("menu.fxml")));
        Parent root = loader.load();
        MenuController controller = loader.getController();
        Scene scene = new Scene(root);
        //scene.setOnKeyPressed(controller::keyHandler);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake");
        primaryStage.getIcons().add(new Image("Images\\-KqV-PDJ3uQ.jpg"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
