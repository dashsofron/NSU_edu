package sample;

import java.lang.Math;

public class Mandelbrot implements Fractal {
    private int max;
    private int maxIter;

    public Mandelbrot() {
        max = 16;
        maxIter = 299;
    }

    public int countFractal(Complex cInstance) {
        double yShift1 = -1;
        double xShift1 = (float) -1.44;
        double ratio1 = 2;
        Complex c = new Complex(xShift1 + ratio1 * cInstance.getRe(), yShift1 + ratio1 * cInstance.getIm());
        Complex z = new Complex(0, 0);
        int curIter = 0;
        while ((curIter < maxIter) && (Math.pow(z.getMod(), 2) < max)) {

            z = Complex.Sum(Complex.cPow(z, 2), c);
            curIter++;
        }
        return curIter;
    }
}
