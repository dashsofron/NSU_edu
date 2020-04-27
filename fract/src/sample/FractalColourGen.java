package sample;

import javafx.scene.paint.Paint;

public class FractalColourGen implements FractalColour {
    protected int shortNum=150;
    protected int curNum;
    protected int maxCOLOR=255;
    protected Paint[] colourAr;
    FractalColourGen(){colourAr=new Paint[shortNum];}
    public void makeColour(int num){ curNum=num; }
    public Paint getColour(){
        if(shortNum-1-curNum<0) System.out.println("colornum:"+shortNum+" curnum:"+curNum+"\n");
        return colourAr[shortNum-1-curNum];}

    public Paint makeGetColour(int num){
        makeColour(num);
        return getColour();
    }
}
