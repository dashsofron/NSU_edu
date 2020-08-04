package sample;

public class Julia implements Fractal {
    private int max;
    private int maxIter;

    public Julia(){max=2;maxIter=299;}

    public  int countFractal(Complex  zInstance){
        double yShift = -2;
        double xShift = -2;
        double ratio = 4;
        Complex z=new Complex(xShift + ratio *zInstance.getRe(), yShift + ratio *zInstance.getIm());
        Complex c= new Complex((float)0.36,(float)-0.36);
        int curIter = 0;
        while ((curIter <maxIter)&&(Math.pow(z.getMod(),2)<max)) {
            Complex z1 = Complex.Sum(Complex.cPow(z,2),c);
            z=z1;
            curIter++;
        }
        return curIter;
    }

}
