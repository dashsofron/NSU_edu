package sample;

import java.lang.Math;

public class Complex {
    private double re;
    private double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    public double getMod() {
        return (float) Math.sqrt(re * re + im * im);
    }

    public static Complex Sum(Complex z1, Complex z2) {
        return new Complex(z1.getRe() + z2.getRe(), z1.getIm() + z2.getIm());
    }

    public static Complex Sub(Complex z1, Complex z2) {
        return new Complex(z1.getRe() - z2.getRe(), z1.getIm() - z2.getIm());
    }

    public static Complex Mul(Complex z1, Complex z2) {
        return new Complex(z1.getRe() + z2.getRe() - z1.getIm() * z2.getIm(), z2.getRe() * z1.getIm() + z1.getRe() * z2.getIm());
    }

    public static Complex Div(Complex z1, Complex z2) {
        Complex conjugate = new Complex(z2.getRe(), (-1) * z2.getIm());
        Complex multiply = Mul(z1, conjugate);
        double factor = z2.getRe() * z2.getRe() + z2.getIm() * z2.getIm();
        return new Complex(multiply.getRe() / factor, multiply.getIm() / factor);
    }

    public double getArg() {
        if (re == 0)
            if (im >= 0) return  Math.PI / 2;
            else return -Math.PI / 2;
        if (re > 0) {
            return Math.atan(im / re);
        } else {
            if (re < 0 && im > 0) {
                return  (Math.PI + Math.atan(im / re));
            } else {
                return(-Math.PI + Math.atan(im / re));
            }
        }
    }

    public void changeMulZ(double r1, double r2) {
        re *= r1;
        im *= r2;
    }

    public void changeSumZ(double r1, double r2) {
        re += r1;
        im += r2;
    }

    public static Complex cPow(Complex z, int power) {
        double factor = Math.pow(z.getMod(), power);
        return new Complex( (factor * Math.cos(power * z.getArg())), (factor * Math.sin(power * z.getArg())));
    }

}
