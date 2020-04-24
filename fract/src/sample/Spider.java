package sample;


public class Spider implements Fractal {
    private int max;
    private int maxIter;

    public Spider() {
        max = 8;
        maxIter = 299;
    }

    public int countFractal(Complex z1) {
        double ratio = 4;
        double yShift = -2;
        double xShift = -2;
        Complex z = new Complex(xShift + ratio * z1.getRe(), yShift + ratio * z1.getIm());
        Complex c = new Complex(xShift + ratio * z1.getRe(), yShift + ratio * z1.getIm());
        int curIter = 0;
        while ((curIter < maxIter) && (Math.pow(z.getMod(), 2) < max)) {
            Complex zn = Complex.Sum(Complex.cPow(z, 2), c);
            z = zn;
            c.changeMulZ( 0.5,  0.5);
            c.changeSumZ(z.getRe(), z.getIm());
            curIter++;
        }
        return curIter;
    }
}

