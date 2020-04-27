package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.*;


public class Controller {
    private boolean b1=false;
    private boolean b2=false;
    private boolean b=true;
    @FXML
    private HBox hbox;
    @FXML
    VBox ButtonBox;
    @FXML
    private DrawClass fractalDrawer;
    @FXML
    public Canvas canvas;
    @FXML
    Button Start;
    @FXML
    Button Standard;
    @FXML
    Label SomeInstruct;
    @FXML
    public BorderPane pane;

    private EventListener listener;



    @FXML
    private void buttonClickedM() {
        fractalDrawer.setDrawerForm(new Mandelbrot());
    }

    @FXML
    private void buttonClickedJ() {
        fractalDrawer.setDrawerForm(new Julia());

    }

    @FXML
    private void buttonClickedS() {
        fractalDrawer.setDrawerForm(new Spider());

    }

    @FXML
    private void buttonClickedL() {
        fractalDrawer.setDrawerForm(new Lambda());

    }

    @FXML
    private void buttonClickedStart() {
        setC();
    }

    @FXML
    private void buttonClickedStandard() {
        fractalDrawer.setDrawerColor(new FractalColourName());
        b=true;
    }

    @FXML
    private void buttonClickedGrey1() {
        fractalDrawer.setDrawerColor(new FractalColourGrey());b1=true;
        b=false;
    }

    @FXML
    private void buttonClickedGrey2() {
        fractalDrawer.setDrawerColor1(new FractalColourGrey());b2=true;
        b=false;
    }

    @FXML
    private void buttonClickedRed1() {
        fractalDrawer.setDrawerColor(new FractalColourRed());b1=true;
        b=false;
    }

    @FXML
    private void buttonClickedRed2() {
        fractalDrawer.setDrawerColor1(new FractalColourRed());b2=true;
        b=false;
    }

    @FXML
    private void buttonClickedGreen2() {
        fractalDrawer.setDrawerColor1(new FractalColourGreen());b2=true;
        b=false;
    }

    @FXML
    private void buttonClickedGreen1() {
        fractalDrawer.setDrawerColor(new FractalColourGreen());b1=true;
        b=false;
    }

    @FXML
    private void buttonClickedBlue2() {
        fractalDrawer.setDrawerColor1(new FractalColourBlue());b2=true;
        b=false;
    }
@FXML
TextField ThreadsNum;

    @FXML
    private void buttonClickedBlue1() {
        fractalDrawer.setDrawerColor(new FractalColourBlue());b1=true;
        b=false;
    }

    public Pane getBox() {
        return hbox;
    }

    @FXML
    public void initialize() throws InterruptedException {
        fractalDrawer = new DrawClass(SomeInstruct);
        listener = new EventListener(fractalDrawer, canvas,ThreadsNum,SomeInstruct);
        MyMouse.setSX(0);
        MyMouse.setSY(0);
        MyMouse.setEX(fractalDrawer.getWidth());
        MyMouse.setEY(fractalDrawer.getHeight());
        pane.setPrefHeight(800);
        pane.setPrefWidth(1500);

    }

    public EventListener getListener() {
        return listener;
    }

    public void setC() {
        if(b1&&b2||b)fractalDrawer.drawCanvas(canvas);
        else if(b1)SomeInstruct.setText("You need some colour from right for drawing");
        else SomeInstruct.setText("You need some formula from left for drawing");
    }


}
