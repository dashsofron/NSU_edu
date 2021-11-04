public class PartDerSIn extends PartDerCommon {

    PartDerSIn(){
        super(0.1,0.5,20,40,0,5,0,5);
    }
    public Double funk(double x) {
        if (x >= 0 && x < 1 || x > 4 && x <= 5) return 0.0;
        if (x >= 1 && x <= 4) return Math.sin(Math.PI/3*(x-1));
        return null;
    }


    public double[][] countAnalyticAnswer() {
        double[][] u = new double[T][N];
        for (int j = 0; j < N; j++)
            for (int n = 0; n < T; n++) {
                double x = j * h + startX;
                double ta = startY + n * t;
                Double temp = funk(x - a * ta);
                if (temp == null) {
                    System.out.println("bad point");
                    temp = 0.0;
                    //break;
                }
                u[n][j] = temp;
            }
        return u;
    }

    @Override
    public double[][] countAswer() {
        double[][] u = new double[T][N];
        for (int n = 0; n < T; n++)
            u[n][0] = 0;
        for (int j = 0; j < N; j++) {
            Double temp = funk(startX + j * h);
            if (temp == null) {
                System.out.println("bad point");
            } else u[0][j] = temp;
        }
        for (int j = 1; j < N; j++)
            for (int n = 0; n < T - 1; n++) {
                u[n + 1][j] = (1 - r) * u[n][j] + r * u[n][j - 1];
            }
        return u;
    }


    public static void main(String[] args) {
        PartDerSIn p = new PartDerSIn();
        double[][] analytic = p.countAnalyticAnswer();
        double[][] count = p.countAswer();
        p.show(analytic);
        System.out.println();
        p.show(count);
    }


}
