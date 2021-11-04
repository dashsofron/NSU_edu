public class Main {
    public static void main(String[] args){
        int n=3;
        double[][]matrix=new double[n][n];
        double[]b=new double[n];

//        matrix[0][0]=1;
//        matrix[0][1]=0.5;
//        matrix[1][0]=0.5;
//        matrix[1][1]=1F/3;


        //        matrix[0][0]=2;
//        matrix[0][1]=-1;
//        matrix[0][2]=0;
//        matrix[1][0]=5;
//        matrix[1][1]=4;
//        matrix[1][2]=2;
//        matrix[2][0]=0;
//        matrix[2][1]=1;
//        matrix[2][2]=-3;
//
        matrix[0][0] = 1;
        matrix[0][1] = 1/2F;
        matrix[0][2] = 1/3F;
        matrix[1][0] = 1/2F;
        matrix[1][1] = 1/3F;
        matrix[1][2] = 1/4F;
        matrix[2][0] = 1/3F;
        matrix[2][1] = 1/4F;
        matrix[2][2] = 1/5F;

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



        b[0]=2;
        b[1]=2;
        b[2]=2;
        Jac jacC=new Jac(matrix,b);
        GZ gzC=new GZ(matrix,b);
        SI siC=new SI(matrix,b);
        jacC.countIter();
        System.out.println();
        siC.countIter();
        System.out.println();
        gzC.countIter();

    }
}
