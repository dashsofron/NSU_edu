package sample;


import javafx.scene.paint.Color;

public class FractalColourBlue extends FractalColourGen {
    FractalColourBlue() {
        for (int i = 0; i < shortNum / 3; i++) {
            colourAr[i] = Color.rgb(0, 0, 2 * i);
            colourAr[shortNum / 3 + i] = Color.rgb(0, 0, 2 * shortNum / 3 + i);
            colourAr[shortNum - 1 - i] = Color.rgb(0, 0, maxCOLOR - 2 * i);
        }
    }

}

