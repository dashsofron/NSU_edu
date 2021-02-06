import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class DiagramDrawer extends Application {


    @Override
    public void start(Stage s) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().
                getClassLoader().getResource("fxm.fxml")));
        loader.load();
        Controller controller=loader.getController();
        System.out.println(controller);
        controller.pane.getChildren().add(controller.getLineChart());
        Scene sc = new Scene(controller.pane);
        s.setScene(sc);
        s.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
