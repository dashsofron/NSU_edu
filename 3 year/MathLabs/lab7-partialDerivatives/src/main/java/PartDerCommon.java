import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.text.DecimalFormat;

public  abstract class PartDerCommon implements PartDer {
    double a ;
    double z ;

    public int N ;
    public int T ;
    public double startX ;
    public double endX ;
    public double h ;
    public double startY ;
    public  double endY ;
    public  double t ;
    public  double r ;

    PartDerCommon(double a,double z,int N,int T,double startX,double endX, double startY , double endY ){
        this.a=a;
        this.z=z;
        this.N=N;
        this.T=T;
        this.startX=startX;
        this.startY=startY;
        this.endX=endX;
        this.endY=endY;
        this.h=(endX - startX) / N;
        this.t= z*h/a;
        this.r=a * t / h;
        if(r>1){
            System.out.println("error, r>1");
        }
    }

    public abstract double[][] countAswer();
    public abstract double[][] countAnalyticAnswer();


    public ObservableList getCountCoords(int num) {
        double[][] u = countAswer();
        XYChart.Data[] coords = new XYChart.Data[N];
        for (int i = 0; i < N; i++)
            coords[i] = new XYChart.Data(startX + i * h, u[num][i]);
        ObservableList a = FXCollections.observableArrayList(coords);
        return a;
    }

    public ObservableList getAnalyticCoords(int num) {
        double[][] u = countAnalyticAnswer();
        XYChart.Data[] coords = new XYChart.Data[N];
        for (int i = 0; i < N; i++)
            coords[i] = new XYChart.Data(startX + i * h, u[num][i]);
        ObservableList a = FXCollections.observableArrayList(coords);
        return a;
    }

    @Override
    public double getStartX() {
        return startX;
    }

    @Override
    public double getEndX() {
        return endX;
    }

    @Override
    public double getStartY() {
        return startY;
    }

    @Override
    public double getEndY() {
        return endY;
    }

    @Override
    public double getAreaStep() {
        return h;
    }
    @Override
    public double getTimeStep() {
        return t;
    }

    public void show(double[][] matrix) {
        final String format = "#0.000";
        System.out.println("\n\n");
        for (int i = 0; i < matrix.length / 2; i++) {
            for (int j = 0; j < matrix.length / 2; j++) {
                String formatted = new DecimalFormat(format).format(matrix[i][j]);
                System.out.print(formatted + " ");
//                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }

    }

    public int getTimeValue() {
        return T;
    }
    public int getAreaValue() {
        return N;
    }
    public double getStep(){
        return z*h/a;
    }
}
