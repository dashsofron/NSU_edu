import javafx.collections.ObservableList;

public interface PartDer {


    ObservableList getCountCoords(int num);

    ObservableList getAnalyticCoords(int num);

    double getStartX();

    double getEndX();

    double getStartY();

    double getEndY();

    double getAreaStep();

    double getTimeStep();

    int getTimeValue();
}
