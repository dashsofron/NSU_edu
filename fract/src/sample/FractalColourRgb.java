package sample;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class FractalColourRgb implements FractalColour {
    private int numC;


    public Paint makeGetColour(int num) {
        makeColour(num);
        return getColour();
    }

    public void makeColour(int num) {
        numC = num;
    }

    public Paint getColour() {
        return Color.rgb(numC, numC, numC);
    }
}
