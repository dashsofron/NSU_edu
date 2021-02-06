import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;



public class Controller {
    @FXML
    private Label level;
    int picNum=0;
    @FXML
    Pane pane;
    LineChart c;
    public PartDer counter;
    public void initialize() {
        counter=new PartDerSIn() {
        };
        ObservableList sl = FXCollections.observableArrayList();
        ObservableList l1 = counter.getCountCoords(0);
        ObservableList l2 = counter.getAnalyticCoords(0);
        sl.add(new XYChart.Series("count", l1));
        sl.add(new XYChart.Series("analytic", l2));
        level.setText("0");
        Axis x = new NumberAxis("area", counter.getStartX(), counter.getEndX(), counter.getAreaStep());
        Axis y = new NumberAxis("time", 0, 5, 0.1);

        c = new LineChart(x, y, sl);
        c.setLayoutY(40);
        pane.setMinSize(700,700);
        c.setMinSize(700,600);
        c.setMaxHeight(600);

    }
    public void getNextImage(){
        if(hasNext()){
            picNum++;
            level.setText(String.valueOf(picNum));
            ObservableList sl = FXCollections.observableArrayList();
            ObservableList l1 = counter.getCountCoords(picNum);
            ObservableList l2 = counter.getAnalyticCoords(picNum);
            sl.add(new XYChart.Series("count", l1));
            sl.add(new XYChart.Series("analytic", l2));
            c.setData(sl);

        }
    }
    public void getPrevImage(){
        if(hasPrev()){
            picNum--;
            level.setText(String.valueOf(picNum));
            ObservableList sl = FXCollections.observableArrayList();
            ObservableList l1 = counter.getCountCoords(picNum);
            ObservableList l2 = counter.getAnalyticCoords(picNum);
            sl.add(new XYChart.Series("count", l1));
            sl.add(new XYChart.Series("analytic", l2));
            c.setData(sl);
        }

    }
    public LineChart getLineChart(){
        return c;
    }
    boolean hasPrev(){
        return picNum-1>=0;
    }
    boolean hasNext(){
        return picNum+counter.getTimeStep()<counter.getTimeValue();
    }
}
