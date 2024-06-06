package slr;
public class DiscreteMaths {
    public static double sumX(double x[]) {
        double totalX = 0;

        for (int i = 0; i < x.length; i++)
            totalX = totalX + x[i];
        return totalX;
    }

    public static double sumY(double y[]) {
        double totalY = 0;
        for (int i = 0; i < y.length; i++)
            totalY = totalY + y[i];
        return totalY;
    }


    public double sumXY(double x[], double y[]) {

        double totalXY = 0;
        for (int i = 0; i < x.length; i++)
            totalXY = totalXY + (x[i] * y[i]);

        return totalXY;

    }

    public double sumXPow(double x[]) {
        double totalX = 0;

        for (int i = 0; i < x.length; i++)
            totalX = (double) (totalX + Math.pow(x[i], 2));
        return totalX;
    }
}
