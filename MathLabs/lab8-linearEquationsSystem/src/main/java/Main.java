import java.text.DecimalFormat;

public class Main {

    public static void main(String[] args) {
        int n = 3;
        float[][] matrix = new float[n][n];
//        matrix[0][0]=2;
//        matrix[0][1]=-1;
//        matrix[0][2]=0;
//        matrix[1][0]=5;
//        matrix[1][1]=4;
//        matrix[1][2]=2;
//        matrix[2][0]=0;
//        matrix[2][1]=1;
//        matrix[2][2]=-3;

        matrix[0][0] = 2;
        matrix[0][1] = -1;
        matrix[0][2] = 0;
        matrix[1][0] = -1;
        matrix[1][1] = 2;
        matrix[1][2] = -1;
        matrix[2][0] = 0;
        matrix[2][1] = -1;
        matrix[2][2] = 2;

//
//        matrix[0][0] = 10;
//        matrix[0][1] = -7;
//        matrix[0][2] = 0;
//        matrix[1][0] = -3;
//        matrix[1][1] = 6;
//        matrix[1][2] = 2;
//        matrix[2][0] = 5;
//        matrix[2][1] = -1;
//        matrix[2][2] = 5;

//        matrix[0][0] = 1;
//        matrix[0][1] = 2;
//        matrix[1][0] = 3;
//        matrix[1][1] = 4;


        float[] b = new float[n];
        b[0] = 1;
        b[1] = 1;
        b[2] = 1;
//        b[0] = 1;
//        b[1] = 2;
        CountTridiagonal counterTrig = new CountTridiagonal();
        CountLu counterLu = new CountLu();
        long start=System.nanoTime();
        float[] xTrig = counterTrig.countX(matrix, b);
        long end=System.nanoTime();

        System.out.println(end-start);
        start=System.nanoTime();
        Float[] xLU = counterLu.countX(matrix, b);
        end=System.nanoTime();
        System.out.println(end-start);

        //System.out.println((time2 - time1) );


        final String format = "#0.00";

        for (int i = 0; i < n; i++) {

            String formatted = new DecimalFormat(format).format(xTrig[i]);

            System.out.print(formatted + " ");
        }
        System.out.println();
        for (int i = 0; i < n; i++) {

            String formatted = new DecimalFormat(format).format(xLU[i]);

            System.out.print(formatted + " ");
        }
    }
}