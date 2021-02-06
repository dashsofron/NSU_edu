import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DiagramDrawer extends Application {


    @Override
    public void start(Stage s) throws Exception {
        Counter counter=new ExpCounter();
        Pane p = new Pane();
        ObservableList sl = FXCollections.observableArrayList();
        ObservableList l1 = counter.getFourthCoords();
        ObservableList l2 = counter.getAnalyticCoords();
        System.out.println(counter.getL1());
        //ObservableList l2 = counter.getAnalyticCoords();

        sl.add(new XYChart.Series("count", l1));
        sl.add(new XYChart.Series("analytic", l2));

        Axis x = new NumberAxis("x", 0, 5, 0.1);
        Axis y = new NumberAxis("y", 0, 10, 0.2);
        LineChart c = new LineChart(x, y, sl);
        c.setMinSize(700,700);
        c.setLayoutY(40);

        p.getChildren().add(c);
        Scene sc = new Scene(p);
        s.setScene(sc);
        s.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
