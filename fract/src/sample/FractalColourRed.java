package sample;

import javafx.scene.paint.Color;

public class FractalColourRed extends FractalColourGen {
    FractalColourRed() {
        for (int i = 0; i < shortNum/3 ; i++) {
            colourAr[i] = Color.rgb(2 * i, 0, 0);
            colourAr[shortNum/3  + i] = Color.rgb(2*shortNum/3  + i, 0, 0);
            colourAr[shortNum-1 - i] = Color.rgb(maxCOLOR - 2 * i, 0, 0);
        }
    }
}
