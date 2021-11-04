import UI.ConnectController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class Main extends Application {
    ConnectController controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Locale.setDefault(new Locale("ru", "RU"));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/connect.fxml"));
        loader.load(getClass().getResource("/connect.fxml").openStream());
        controller = loader.getController();

        primaryStage.setTitle("Подключение");
        primaryStage.setScene(new Scene(controller.connectPane));
        primaryStage.show();
    }
}
