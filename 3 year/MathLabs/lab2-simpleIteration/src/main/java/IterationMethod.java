public class IterationMethod {
    double getNextX(double x,double l,double h){
        return l*Math.sin(x)/h;
    }
    String findRoot(double start, double eps,double l,double h){
        double x0=start;
        double x=getNextX(x0,l,h);
        int iterNum=1;
        while (Math.abs(x-x0)>eps){
            x0=x;
            x=getNextX(x0,l,h);
            iterNum++;
        }
        return (iterNum+" +-"+x);
    }
}
