public class ProgCount {

    public float getNumD(float[][] matrix,int num){
        return matrix[num][num];
    }
    public float getNumE(float[][] matrix,int num){
        return matrix[num][num+1];
    }
    public float getNumC(float[][] matrix,int num){
        return matrix[num][num-1];

    }
    public float[] initX(float[][] matrix, float[] b,float[] Q,float[] P){
        int n=b.length;
        float[]x=new float[n];
        x[n-1]=(getNumC(matrix,n-1)*(-1)*Q[n-1]+b[n-1])/(getNumD(matrix,n-1)+getNumC(matrix,n-1)*P[n-1]);
        for(int i=n-2;i>=0;i--){
            x[i]=P[i+1]*x[i+1]+Q[i+1];
        }
        return  x;
    }
    public float[] countX(float[][] matrix, float[] b) {
        int n=b.length;
        float[] Q=new float[n];
        float[] P=new float[n];

        P[1]=0;//по условию
        Q[1]=0;//левое граничное условие
        for(int i=2;i<=n-1;i++){
            P[i]=getNumE(matrix,i-1)*(-1)/(getNumD(matrix,i-1)+getNumC(matrix,i-1)*P[i-1]);
            Q[i]=(getNumC(matrix,i-1)*(-1)*Q[i-1]+b[i-1])/(getNumD(matrix,i-1)+getNumC(matrix,i-1)*P[i-1]);
        }
        //System.out.println("\n");
//        for(int i=0;i<n;i++)
//            System.out.print(P[i]+" ");
//        System.out.println();
//        for(int i=0;i<n;i++)
//            System.out.print(Q[i]+" ");
//        System.out.println();

        return initX(matrix,b,Q,P);
    }

}
