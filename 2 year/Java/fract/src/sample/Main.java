package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
    Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        loader.load(getClass().getResource("sample.fxml").openStream());
        controller = loader.getController();
        primaryStage.setTitle("Fract");
        primaryStage.getIcons().add(new Image("20f0480250d8f68090c7387bb17bfd75.png"));

        primaryStage.setScene(new Scene(controller.getBox()));
        primaryStage.getScene().setOnMousePressed(controller.getListener()::mousePressedHandler);
        primaryStage.getScene().setOnMouseReleased(controller.getListener()::mouseReleasedHandler);
        primaryStage.getScene().setOnKeyPressed(controller.getListener()::keyHandler);
        primaryStage.getScene().setOnScroll(controller.getListener()::mouseScrollHandler);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
