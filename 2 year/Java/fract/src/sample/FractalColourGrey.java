package sample;

import javafx.scene.paint.Color;

public class FractalColourGrey extends FractalColourGen {
    FractalColourGrey() {
        for (int i = 0; i < shortNum/3; i++) {
            colourAr[i] = Color.rgb(2 * i, 2 * i, 2 * i);
            colourAr[shortNum/3 + i] = Color.rgb(2*shortNum/3 + i, 2*shortNum/3  + i, 2*shortNum/3 + i);
            colourAr[shortNum-1 - i] = Color.rgb(maxCOLOR - 2 * i, maxCOLOR - 2 * i, maxCOLOR - 2 * i);
        }
    }

}
