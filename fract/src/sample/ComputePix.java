package sample;


public class ComputePix implements Runnable {
    private int iStart;
    private int iEnd;
    private int jStart;
    private int jEnd;
    private int reckSize;
    private int width;
    private int height;
    private double xShift;
    private double yShift;
    private double stepX;
    private double stepY;
    private GeneralClass M;
    private int[][] paintNum;
    private Thread thread;

    public ComputePix(int iStart, int iEnd, int jStart, int jEnd, int reckSize, int width, int height, double xShift, double yShift, double stepX, double stepY, GeneralClass M, int[][] paintNum) {
        thread = new Thread(this);
        this.iStart = iStart;
        this.iEnd = iEnd;
        this.jStart = jStart;
        this.jEnd = jEnd;
        this.reckSize = reckSize;
        this.width = width;
        this.height = height;
        this.xShift = xShift;
        this.yShift = yShift;
        this.stepX = stepX;
        this.stepY = stepY;
        this.M = M;
        this.paintNum = paintNum;
    }

    public Thread getThread() {
        return thread;
    }

    public void run() {

        for (int i = iStart; i < iEnd; i += reckSize) {
            for (int j = jStart; j < jEnd; j += reckSize) {
                double x = i * stepX / reckSize + xShift;
                double y = j * stepY / reckSize + yShift;
                paintNum[i][j] = M.countFractal(new Complex(x / (double) width, y / (double) height));
            }
        }
    }

    public void start() {
        thread.start();
    }
}
