package sample;

import javafx.scene.paint.Color;

public class FractalColourGreen extends FractalColourGen {
    FractalColourGreen() {
        for (int i = 0; i < shortNum/3; i++) {
            colourAr[i] = Color.rgb(0, 2 * i, 0);
            colourAr[shortNum/3  + i] = Color.rgb(0, 2*shortNum/3  + i, 0);
            colourAr[shortNum-1 - i] = Color.rgb(0, maxCOLOR - 2 * i, 0);
        }
    }

}

