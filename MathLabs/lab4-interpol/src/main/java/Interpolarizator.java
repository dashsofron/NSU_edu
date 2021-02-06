public class Interpolarizator {
    double[] xSub;
    double[][] diff;

    Interpolarizator(int n) {
        xSub = new double[n - 1];
        diff = new double[n][n];
    }

    public double func(double x) {
        return Math.pow(x, 3) - Math.exp(x) + 1;
    }

    public double accur(int n, double h) {
        double Mn = Math.abs(func(-3));
        return Math.pow(h, n) / n * Mn;
    }

    double func(double x, int nDif) {
        switch (nDif) {
            case 1:
                return 3 * Math.pow(x, 2) - x * Math.exp(x);
            case 2:
                return 6 * x - Math.pow(x, 2) * Math.exp(x);
            case 3:
                return 6 - Math.pow(x, 3) * Math.exp(x);
            default:
                return -Math.pow(x, nDif) * Math.exp(x);
        }
    }

    double Lagr(double x, double[][] nodes) {
        double res = 0;
        for (int i = 0; i < nodes.length; i++) {
            double resi = nodes[i][1];
            //System.out.print(resi);
            for (int j = 0; j < nodes.length; j++) {
                if (i != j) {
                    resi *= (x - nodes[j][0]) / (nodes[i][0] - nodes[j][0]);
                    //System.out.print(" * "+" ( "+x +" - "+ nodes[j][0]+" ) "+ " / " +" ( "+nodes[i][0] +" - " +nodes[j][0]+" ) ");
                }
            }
            //System.out.println(" + ");
            res += resi;
        }
        return res;
    }


    void finiteDiff(int n, double[][] nodes) {
//        double res=0;
//        for(int i=0;i<nodes.length;i++){
//            double resi=nodes[i][1];
//            for(int j=0;j<nodes.length;j++){
//                if(j!=i)resi/=(nodes[i][0]-nodes[j][0]);
//            }
//            res+=resi;
//        }
//        return  res;
        for (int i = 0; i < n; i++)
            diff[i][i] = nodes[i][1];
//        for (int i = 1; i < n; i++)
//            for (int j = 0; j < n - i; j++) {
//                diff[j + i][j] = (diff[i][j + 1] - diff[i - 1][j]) / (nodes[j+1][0] - nodes[i - 1][0]);
//            }
        for(int i=1;i<n;i++){
            for(int j=i;j<n;j++){
                diff[j-i][j]=(diff[j-i+1][j]-diff[j-i][j-1])/(nodes[j][0]-nodes[j-i][0]);
                diff[j][j-i]=(diff[j-i+1][j]-diff[j-i][j-1])/(nodes[j][0]-nodes[j-i][0]);
            }
        }
//        for(int i=0; i<n;i++) {
//            for (int j = 0; j < n; j++)
//                System.out.print("   "+diff[i][j]);
//            System.out.println();
//        }

    }

    double fillSub(double x, double[][] nodes, int n) {
        if (n == 2) {
            xSub[0] = x - nodes[0][0];
            //System.out.println("xSub["+(n-2)+"]="+ (x - nodes[0][0]));

        }
        else {
            double temp=fillSub(x, nodes, n - 1);
            xSub[n - 2] = temp * (x - nodes[n - 1][0]);
            //System.out.println("xSub["+(n-2)+"]="+temp+"*("+x+ "-"+ nodes[n - 1][0]+")="+xSub[n - 2]);
        }
        return xSub[n - 2];
    }

    double Newton(double x, double[][] nodes, int n) {

        if(xSub.length==n-1){
            fillSub(x,nodes,n);
            finiteDiff(n,nodes);
        }
        double res=func(nodes[0][0]);
        for(int i=1;i<n;i++)
        {
            double tempRes=xSub[i-1]*diff[i][0];
            res+=tempRes;
        }
        return  res;


//        if (n == 1) {
//            double res=func(nodes[0][0]);
//            //System.out.println("hi, n="+n+"|result="+res);
//            return res;
//        }

        //double res=Newton(x, nodes, n - 1);
        //System.out.println("hi, n="+n+"|result=res+diff["+(n-1)+"][0]*xSub["+(n-2)+"]=" +res+"+"+diff[n - 1][0] +"*"+ xSub[n - 2]+"="+res+diff[n - 1][0] * xSub[n - 2]);
        //return res + diff[n - 1][0] * xSub[n - 2];
    }
}
