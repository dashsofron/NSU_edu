
public class Main {
    public static void main(String[] args) {
        double a1 = 0;
        double b1 = Math.PI;
        double a2 = 0;
        double b2 = 2;
        int n=100;
        CountF funcs=new CountF();
        double sinValue=Math.cos(a1)-Math.cos(b1);
        double expValue=Math.exp(b2)-Math.exp(a2);
        double sinC=Counter.countIntegral(a1,b1,n,funcs.countSin);
        double expC=Counter.countIntegral(a2,b2,n,funcs.countExp);
        System.out.println("sin(x): "+sinC);
        System.out.println("exp(x): "+expC);
        System.out.println("sin estimation :"+(sinValue-sinC));
        System.out.println("exp estimation :"+(expValue-expC));


    }
}
