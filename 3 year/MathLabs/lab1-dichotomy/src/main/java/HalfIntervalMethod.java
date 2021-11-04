public class HalfIntervalMethod {
    double a_, b_, c_, d_, e_, del_;
    int deg = 3;

    HalfIntervalMethod(double a, double b, double c, double d, double e, double del) {
        this.a_ = a;
        this.b_ = b;
        this.c_ = c;
        this.d_ = d;
        this.e_ = e;
        this.del_ = del;


    }

    double search(double a, double b) {
        double midPoint = average(a, b);
        double negPoint;
        double posPoint;
        if (f(a) < 0) {
            negPoint = a;
            posPoint = b;
        } else {
            negPoint = b;
            posPoint = a;
        }

        if (closeEnought(negPoint, posPoint)) return midPoint;
        double testValue = f(midPoint);
        if (testValue > 0) return search(negPoint, midPoint);
        else if (testValue < 0) return search(midPoint, posPoint);
        return midPoint;
    }

    double serchProisv(double a, double b) {

        double midPoint = average(a, b);
        double negPoint;
        double posPoint;
        if (f_Proisv(a) < 0) {
            negPoint = a;
            posPoint = b;
        } else {
            negPoint = b;
            posPoint = a;
        }

        double testValue = f_Proisv(midPoint);
        if (testValue > 0) return search(negPoint, midPoint);
        else if (testValue < 0) return search(midPoint, posPoint);
        return midPoint;

    }

    double average(double x, double y) {
        return (x + y) / 2;

    }

    double f(double x) {
        return a_ * x * x * x + b_ * x * x + c_ * x + d_;
    }

    Boolean closeEnought(double x, double y) {
        if (Math.abs(x - y) < e_) return true;
        return false;
    }

    double f_Proisv(double x) {
        return 3 * a_ * x * x + 2 * b_ * x + c_;
    }

    void halfIntervalMethod(double a, double b) {
        double aVal = f(a);
        double bVal = f(b);
        double result;
        if (aVal * bVal < 0) {
            result = search(a, b);
            System.out.println(result);
            deg--;
        }
        else {
            if (f_Proisv(a) * f_Proisv(b) < 0) {
                result = serchProisv(a, b);
                if (closeEnought(f(result), 0)) {
                    System.out.println(result);
                    deg--;
                }
            }
        }




    }

    public void localePoint() {
        long curTime = System.currentTimeMillis();
        double point = 0.1;
        for (int i = 0; deg > 0 && System.currentTimeMillis() - curTime < 1000000; i++) {
            halfIntervalMethod(point + del_ * i, point + del_ * (i + 1));
            halfIntervalMethod(point - del_ * (i + 1), point - del_ * i);
        }
        if (deg > 0)
            System.out.println("some of answer point can be in degree or too far in infinity. Otherwise change delta");

    }


    public void localePoint(double a, double b) {
        int deg1 = deg;
        while (deg > 0) {
            for (int i = 0; a + del_ * i < b; i++) {
                if (a + del_ * (i + 1) > b) halfIntervalMethod(a + del_ * i, b);
                halfIntervalMethod(a + del_ * i, a + del_ * (i + 1));
            }
            break;

        }
        if (deg1 == deg) {
            System.out.println("change function or interval");
            return;
        }
        if (deg > 0) System.out.println("some of answer point can be in some degree.  Otherwise change delta or interval");

    }
}
