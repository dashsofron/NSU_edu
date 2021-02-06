public class Main {

    public static void main(String[] args){
        int n=200;
        double a=-3;
        double b=10;
        double h=(b+1-a)/n;
        double x=0;
        Interpolarizator inter=new Interpolarizator(n);
        double value=inter.func(x);
        double [][] nodes=new double[n][n];
        for(int i=0;i<n;i++){
            nodes[i][0]=a+i*h;
            nodes[i][1]=inter.func(nodes[i][0]);
            //System.out.println(nodes[i][0]+"|"+nodes[i][1]);
        }
        System.out.println("Lagrange value:"+inter.Lagr(x,nodes));
        System.out.println("Newton value:"+inter.Newton(x,nodes,n));
        System.out.println("Accurancy assessment:"+inter.accur(n,h));


    }
}
