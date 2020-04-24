package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;


public class Controller {
    private boolean b1 = false;
    private boolean b2 = false;
    private boolean b = true;
    private boolean redr = true;
    private Stage primaryStage;
    private Image print;
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
    Button Save;
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
        redr = true;
    }

    @FXML
    private void buttonClickedJ() {
        fractalDrawer.setDrawerForm(new Julia());
        redr = true;

    }

    @FXML
    private void buttonClickedS() {
        fractalDrawer.setDrawerForm(new Spider());
        redr = true;

    }

    @FXML
    private void buttonClickedL() {
        fractalDrawer.setDrawerForm(new Lambda());
        redr = true;

    }

    @FXML
    private void buttonClickedStart() {
        setC();
    }

    @FXML
    private void buttonClickedSave() throws IOException {
        save1();
    }

    @FXML
    private void buttonClickedStandard() {
        fractalDrawer.setDrawerColor(new FractalColourName());
        b = true;
    }

    @FXML
    private void buttonClickedGrey1() {
        fractalDrawer.setDrawerColor(new FractalColourGrey());
        b1 = true;
        b = false;
    }

    @FXML
    private void buttonClickedGrey2() {
        fractalDrawer.setDrawerColor1(new FractalColourGrey());
        b2 = true;
        b = false;
    }

    @FXML
    private void buttonClickedRed1() {
        fractalDrawer.setDrawerColor(new FractalColourRed());
        b1 = true;
        b = false;
    }

    @FXML
    private void buttonClickedRed2() {
        fractalDrawer.setDrawerColor1(new FractalColourRed());
        b2 = true;
        b = false;
    }

    @FXML
    private void buttonClickedGreen2() {
        fractalDrawer.setDrawerColor1(new FractalColourGreen());
        b2 = true;
        b = false;
    }

    @FXML
    private void buttonClickedGreen1() {
        fractalDrawer.setDrawerColor(new FractalColourGreen());
        b1 = true;
        b = false;
    }

    @FXML
    private void buttonClickedBlue2() {
        fractalDrawer.setDrawerColor1(new FractalColourBlue());
        b2 = true;
        b = false;
    }

    @FXML
    TextField ThreadsNum;

    @FXML
    private void buttonClickedBlue1() {
        fractalDrawer.setDrawerColor(new FractalColourBlue());
        b1 = true;
        b = false;
    }

    public Pane getBox() {
        return hbox;
    }

    @FXML
    public void initialize() throws InterruptedException {
        fractalDrawer = new DrawClass((int) canvas.getWidth(), (int) canvas.getHeight());
        listener = new EventListener(fractalDrawer, canvas, ThreadsNum, SomeInstruct);
        MyMouse.setSX(0);
        MyMouse.setSY(0);
        MyMouse.setEX(fractalDrawer.getWidth());
        MyMouse.setEY(fractalDrawer.getHeight());
        pane.setPrefHeight(800);
        pane.setPrefWidth(1500);

    }

    public void save1() throws IOException {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {

            WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            canvas.snapshot(null, writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, "png", file);
        }
    }

    public void setStage(Stage stage) {
        primaryStage = stage;

    }

    public EventListener getListener() {
        return listener;
    }

    public void setC() {
        if (b1 && b2 || b) {
            SomeInstruct.setText("Hi there!You can change threads number in field above to make drawing faster (check your device settings for your max threads number).Default set is Mandelbrot, colour is Standard. For changing it press buttons.You can use different colours,choose one left and right colour.You can scroll and select a rectangle for zooming,press Delete to go back.Have fun :)");
            if (redr) fractalDrawer.makeColors();
            fractalDrawer.drawCanvas(canvas);
            redr = false;
        } else if (b1) SomeInstruct.setText("You need some colour from right for drawing");
        else SomeInstruct.setText("You need some formula from left for drawing");
    }


}
