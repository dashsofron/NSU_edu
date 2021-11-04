public class Main {
    public static void main(String[] args) {
        ExpCounter count = new ExpCounter();
        float[] y = count.firstOrder();
        count.printY(y);


    }
}
