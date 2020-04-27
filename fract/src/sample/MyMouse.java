package sample;
public class MyMouse {
    private static int startX;
    private static int startY;
    private static int endX;
    private static int endY;
    public static double zoomFactor=1.5;
    private static int startXl;
    private static int startYl;
    private static int endXl;
    private static int endYl;
    public static int getLeftX(){
        return Math.min(startX, endX);
    }
    public static int getRightX(){
        return Math.max(startX,endX);
    }
    public static int getTop(){
        return Math.min(startY,endY);
    }
    public static int getBot(){
        return Math.max(startY,endY);
    }
    public static int getLeftXl(){
        return Math.min(startXl, endXl);
    }
    public static int getRightXl(){
        return Math.max(startXl,endXl);
    }
    public static int getTopl(){
        return Math.min(startYl,endYl);
    }
    public static int getBotl(){
        return Math.max(startYl,endYl);
    }
    public static void setSX(int x){
        startX=x;
    }
    public static void setSY(int y){
        startY=y;
    }
    public static void setEX(int x){ endX=x; }
    public static void setEY(int y){
        endY=y;
    }
    public static void saveOld(){
        startXl=startX;
        startYl=startY;
        endXl=endX;
        endYl=endY;
    }
}
