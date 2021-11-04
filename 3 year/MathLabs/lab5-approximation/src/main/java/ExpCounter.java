import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class ExpCounter implements Counter {
    float y0 = 1;
    float b = 5;
    float a = 0;
    int n = 40;
    float h = (b - a) / (n - 1);
    float[] g = new float[n];
    float[] x = new float[n];

    float[] y = new float[n ];
    float[] yAn = new float[n];

    public void printY() {
        System.out.println(y[0]);
        for (int i = 1; i < y.length; i++)
            System.out.println(y[i] + "|" + g[i - 1]);
    }

    public void printY(float[] inY) {
        System.out.println(inY[0]);
        for (int i = 1; i < inY.length; i++)
            System.out.println(inY[i] + "|" + g[i - 1]);
    }

    public void init() {
        for(int i=0; i < n; i++)
            x[i] = a + h * i;
        for (int i = 0;i < n; i++)
            g[i] = (float)Math.exp(x[i]);
        //printY(g);
    }

    public float[] analyticExp() {
        init();
        yAn[0] = y0;
        for (int i = 1; i < n; i++) {
            yAn[i] = (float) Math.exp(x[i]);
        }
        return yAn;
    }


    public float[] firstOrder() {
        init();
        y[0] = y0;

        for (int x = 0; x < n-1; x++) {
            y[x + 1] = g[x] * h + y[x];
        }
        //printY();
        return y;
    }

    public float[] secondOrder() {
        init();
        y[0] = y0;
        y[1] = g[0]*h+y[0];

        for (int x = 1; x < n - 1; x++) {
            y[x + 1] = (g[x + 1] + g[x - 1]) * h + y[x - 1];
        }
        return y;

    }

    public float[] fourthOrder() {
        init();
        y[0] = y0;
        y[1] = (g[0]+g[1]+(float)Math.exp(h))*h/3+y[0];
        //y[2] = y2;
        //y[3] = y3;


        for (int x = 1; x < n - 1; x++) {
            y[x + 1] = (g[x+1]+g[x-1]+4*g[x])*h/3+y[x-1];
        }
        return y;

    }



    public ObservableList getFirstCoords() {
        float[] y = firstOrder();
        //printY(y);

        XYChart.Data[] coords = new XYChart.Data[n];
        for (int i = 0; i < n; i++) {
            coords[i] = new XYChart.Data(x[i], y[i ]);
        }
        ObservableList a = FXCollections.observableArrayList(coords);
        return a;

    }

    public ObservableList getSecondCoords() {
        float[] y = secondOrder();
        //printY(y);

        XYChart.Data[] coords = new XYChart.Data[n];
        for (int i = 0; i < n; i++) {
            coords[i] = new XYChart.Data(x[i], y[i ]);
        }
        ObservableList a = FXCollections.observableArrayList(coords);
        return a;

    }

    public ObservableList getFourthCoords() {
        float[] y = fourthOrder();
        //printY(y);

        XYChart.Data[] coords = new XYChart.Data[n];
        for (int i = 0; i < n; i++) {
            coords[i] = new XYChart.Data(x[i], y[i ]);
        }
        ObservableList a = FXCollections.observableArrayList(coords);
        return a;

    }


    public ObservableList getAnalyticCoords() {
        float[] y = analyticExp();
        XYChart.Data[] coords = new XYChart.Data[n];
        for (int i = 0; i < n; i++) {
            coords[i] = new XYChart.Data(x[i], y[i ]);
        }
        ObservableList a = FXCollections.observableArrayList(coords);
        return a;

    }


    public Float getL1(){
        Float sum=0F;
        for(int i=0;i<n;i++)
            sum+=Math.abs(yAn[i]-y[i]);
        return sum*h;
    }

}
