import javafx.collections.ObservableList;

public interface Counter {


    void printY();

    void printY(float[] inY);

    void init();

    float[] analyticExp();


    float[] firstOrder();

    float[] secondOrder();

    float[] fourthOrder();


    ObservableList getFirstCoords();

    ObservableList getSecondCoords();

    ObservableList getFourthCoords();


    ObservableList getAnalyticCoords();


    Float getL1();


}
