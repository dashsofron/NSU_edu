import java.beans.Expression;


public class Counter {


    static double trapeze(double f1, double f2, double h){
        return (f1+f2)/2*h;
    }
    static double countIntegral(double a, double b, int n, Count func) {
        double h = (b - a) / n;
        double res=0;

        for(int i=0;i<n;i++){
            res+=trapeze(func.count(a+i*h),func.count(a+(i+1)*h),h);
        }
        return res;

    }

}
