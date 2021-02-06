public class Main {

    public static void main(String[] args) {
        double a=1;
        double b=-0.01;
        double c=1;
        double d=-0.01;
        double e=0.01;
        double del=0.5;
        HalfIntervalMethod reshatel = new HalfIntervalMethod(a, b, c, d, e, del);
        reshatel.localePoint();

    }

}
//не находит точки четной кратности(не все)