package sample;

import java.lang.Math;

public class Lambda implements Fractal {
    private int max;
    private int maxIter;

    public Lambda() {
        max = 4;
        maxIter = 299;
    }

    public int countFractal(Complex cInstance) {
        double ratiox = 10;
        double xShift = -4;
        double ratioy = 5;
        double yShift = -2;
        Complex c = new Complex(xShift + ratiox * cInstance.getRe(), yShift + ratioy * cInstance.getIm());
        Complex z = new Complex(0.5, 0);
        int curIter = 0;

        while ((curIter < maxIter) && (Math.pow(z.getMod(), 2) < max)) {
            Complex temp = new Complex((double) (z.getRe() - Math.pow(z.getRe(), 2) + Math.pow(z.getIm(), 2)), z.getIm() - 2 * z.getIm() * z.getRe());
            Complex zn = new Complex(c.getRe() * temp.getRe() - c.getIm() * temp.getIm(), c.getRe() * temp.getIm() + c.getIm() * temp.getRe());
            z = zn;
            curIter++;
        }
        return curIter;
    }
}

