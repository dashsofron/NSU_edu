package sample;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GeneralClass implements Cloneable {
    private Fractal element; //выбираем способ подсчета фрактала
    private FractalColour draw; //выбираем способ подсчета цвета
    private FractalColour draw1; //выбираем способ подсчета цвета
    int colorsNum;


    public GeneralClass(Fractal formula, FractalColour color) {
        element = formula;
        draw = color;
        colorsNum = 1;
    }

    public GeneralClass(Fractal formula, FractalColour color, FractalColour color2) {
        element = formula;
        draw = color;
        draw1 = color2;
        colorsNum = 2;
    }

    public GeneralClass clone() throws CloneNotSupportedException {
        return (GeneralClass) super.clone();
    }

    public void setFormula(Fractal formula) {
        element = formula;
    }

    public void setColor(FractalColour color) {
        draw = color;
    }

    public void setColor2(FractalColour color) {
        draw1 = color;
        colorsNum = 2;
    }

    public int countFractal(Complex c) {
        return element.countFractal(c);
    }

    Paint getColorForRect(int num) {
        if (colorsNum == 1) {
            draw.makeColour(num);
            return draw.getColour();
        }
        if (num == 299) return Color.rgb(0, 0, 0);
        if (num < 150) {
            draw.makeColour(num);
            return draw.getColour();
        } else {
            draw1.makeColour(299 - num);
            return draw1.getColour();
        }
    }
}
