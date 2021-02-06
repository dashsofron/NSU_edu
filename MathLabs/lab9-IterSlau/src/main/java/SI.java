public class SI implements SLAUCount {
    double[][] matrix;
    double[] b;
    double[] x;
    double[] prev;
    double eps = 0.001;
    int n;

    SI(double[][] matrix, double[] b) {
        this.matrix = matrix;
        this.b = b;
        this.n = b.length;
        this.x = new double[n];
        this.prev = new double[n];
        for (int i = 0; i < n; i++)
            x[i] = 1;
    }

    double sub() {
        double[] temp = new double[n];

        for (int i = 0; i < n; i++) {
            temp[i] = 0;
            for (int j = 0; j < n; j++)
                temp[i] += matrix[i][j] * x[j];
        }
        double sub = 0;
        for (int i = 0; i < n; i++) {
            if (Math.abs(b[i] - temp[i]) > sub) sub = Math.abs(b[i] - temp[i]);
        }
        return sub;
    }


    public double[] countIter() {
        int iter = 0;
        double sub = 2 * eps;

        while (sub > eps) {
            iter++;
            for (int i = 0; i < n; i++) {
                prev[i] = x[i];
                x[i] = getBetta(i);
                for (int j = 0; j < n; j++) {
                    x[i] += getAlpha(i, j) * prev[j];
                }
            }
            sub = sub();
        }
        System.out.println(iter);
        for (int i = 0; i < n; i++) {
            System.out.print(x[i] + " ");
        }
        return x;
    }


    double getBetta(int i) {
        return b[i] / matrix[i][i];
    }

    double getAlpha(int i, int j) {
        if (i == j) return 0.0;
        return (-1) * matrix[i][j] / matrix[i][i];
    }


}
