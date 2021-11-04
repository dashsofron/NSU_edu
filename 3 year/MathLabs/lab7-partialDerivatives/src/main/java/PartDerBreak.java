public class PartDerBreak  extends PartDerCommon {


    PartDerBreak()
    {
        super(0.1,0.2,30,40,-1,1,0.5,2.5);


    }
    public Double funk(double x) {
        if (x >= -1 && x < 0) return 2.0;
        if (x >= 0 && x <=1) return 1.0;
        return null;
    }

    @Override
    public double[][] countAnalyticAnswer() {
        double[][] u = new double[T][N];
        for (int j = 1; j < N+1; j++)
            for (int n = 0; n < T; n++) {
                double x = j * h + startX;
                double ta = startY + n * t;
                Double temp = funk(x - a * ta);
                if (temp == null) {
                    System.out.println("bad point");
                    temp=2.0;
                    //break;
                }
                u[n][j-1] = temp;
            }
//        System.out.println("analytic");
//        show(u);
        return u;
    }

    @Override
    public double[][] countAswer() {
        double[][] u = new double[T][N];
        for (int n = 0; n < T; n++)
            u[n][0] = 2;
        for (int j = 0; j < N; j++) {
            Double temp = funk(startX + j * h);
            if (temp == null) {
                System.out.println("bad point");
                temp=2.0;
            }
            u[0][j] = temp;
        }


        for (int j = 1; j < N; j++)
            for (int n = 0; n < T - 1; n++) {
                u[n + 1][j] = (1 - z) * u[n][j] + z * u[n][j - 1];
            }
//        System.out.println("count");
//        show(u);
        return u;
    }

}
