package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class DrawClass {
    private int width = 1400;
    private int height = 800;
    private int reckSize;
    private double xShift = 0;
    private double yShift = 0;
    private double stepX;
    private double stepY;
    private GeneralClass M;
    private Paint[][] paintNum;
    private ComputePix[] threads;
    private int threadNum = 4;
    Label SomeInstruct;
    DrawClass(Label L) {
        reckSize = 1;
        SomeInstruct=L;
        stepX = reckSize;
        stepY = reckSize;
        M = setDrawer(new Mandelbrot(), new FractalColourName());
        paintNum = new Paint[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                paintNum[i][j] = Color.rgb(255, 255, 255);
        threads = new ComputePix[threadNum];
    }

    public void setThreadsNum(int num) {
        threadNum = num;
    }

    public void setDrawerForm(Fractal formula) {
        M.setFormula(formula);
        stepX = reckSize;
        stepY = reckSize;
        xShift = 0;
        yShift = 0;
        MyMouse.setSX(0);
        MyMouse.setSY(0);
        MyMouse.setEX(width);
        MyMouse.setEY(height);
        MyMouse.saveOld();
    }

    public void setDrawerColor(FractalColour color) {
        M.setColor(color);
    }

    public void setDrawerColor1(FractalColour color) {
        M.setColor2(color);
    }

    public GeneralClass setDrawer(Fractal formula, FractalColour color) {
        return new GeneralClass(formula, color);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @FXML
    public Canvas drawCanvas(Canvas canvas) {
        SomeInstruct.setText("Drawing...");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        makeColors();
        for (int i = 0; i < width; i += reckSize)
            for (int j = 0; j < height; j += reckSize) {
                gc.setFill(paintNum[i][j]);
                gc.fillRect(i, j, reckSize, reckSize);
            }
        SomeInstruct.setText("Finished!");
        return canvas;
    }

    public void parallelMakeColors() {

        for (int i = 0; i < threadNum; i++)
            try {
                threads[i] = new ComputePix(i * width / threadNum, (i + 1) * width / threadNum, 0,
                        height, reckSize, width, height, xShift, yShift, stepX, stepY, M.clone(), paintNum);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        for (int i = 0; i < threadNum; i++)
            try {
                threads[i].start();
                threads[i].getThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    public void makeColors() {
        parallelMakeColors();
    }

    public void reactOnResize(double xLeft, double xRight, double yUp, double yDown, Canvas canvas) {
        resizeBySpots(xLeft, xRight, yUp, yDown);
        drawCanvas(canvas);
    }

    public void resizeBySpots(double xLeft, double xRight, double yUp, double yDown) {
        double centreX = (xLeft + xRight) / 2;
        double centreY = (yUp + yDown) / 2;
        double rW = (xRight - xLeft);
        double rH = (yUp - yDown);
        if (width / rW > height / rH) rH = rW * height / width;
        else rW = rH * width / height;
        xShift = centreX - rW / 2;
        yShift = centreY - rH / 2;
        stepX = reckSize * rW / width;
        stepY = reckSize * rH / height;
    }
}
