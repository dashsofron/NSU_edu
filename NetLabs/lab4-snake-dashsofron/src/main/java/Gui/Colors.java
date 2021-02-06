package Gui;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Colors {
    private Paint[] paints = {
            //Color.rgb(0, 0, 0),
            Color.rgb(252, 5, 5),
            Color.rgb(140, 3, 99),
            Color.rgb(244, 128, 255),
            Color.rgb(55, 2, 120),
            Color.rgb(50, 73, 250),
            Color.rgb(0, 170, 255),
            Color.rgb(0, 255, 225),
            Color.rgb(11, 101, 50),
            Color.rgb(5, 255, 5),
            Color.rgb(255, 252, 168),
            Color.rgb(247, 247, 10),
            Color.rgb(247, 173, 121),
            Color.rgb(163, 71, 5),
            Color.rgb(54, 43, 36),
            Color.rgb(92, 92, 92),
    };

    public Paint getColor(Integer num) {
        return paints[num];
    }
}
