public class Jac implements  SLAUCount{
    double[][] matrix;
    double[]b;
    double[]x;
    double[]prev;
    double eps = 0.001;
    int n;
    Jac(double[][] matrix,double[]b){
        this.matrix=matrix;
        this.b=b;
        this.n=b.length;
        this.x=new double[n];
        this.prev=new double[n];
        for (int i = 0; i < n; i++)
            x[i] = 5;
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


    public double[] countIter(){
        int iter=0;
        double sub = 2 * eps;

        while (sub>eps){
            iter++;

            for(int i=0;i<n;i++){
                prev[i]=x[i];
                x[i]=b[i];
                for(int j=0;j<n;j++)
                    if(j!=i){
                      x[i]-=matrix[i][j]*prev[j];
                    }
                x[i]/=matrix[i][i];
            }
            sub=sub();
        }
        System.out.println(iter);

        for(int i=0;i<n;i++){
            System.out.print(x[i]+" ");
        }
        return x;
    }
}
