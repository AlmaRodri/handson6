package mlr;

public class DiscreteMaths {

    public double sumX(double x[]){
        double totalX =0;
        for (int i = 0; i < x.length; i++)
            totalX = totalX + x[i];
        return totalX;
    }
    public double sumY(double y[]) {
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

    public double sumXPow2(double x[]) {
        double totalX = 0;
        for (int i = 0; i < x.length; i++)
            totalX =  totalX + Math.pow(x[i], 2);
        return totalX;
    }

    public double sumXPow3(double x[]) {
        double totalX = 0;

        for (int i = 0; i < x.length; i++)
            totalX = totalX + Math.pow(x[i], 3);
        return totalX;
    }

    public double sumXPow4(double x[]) {
        double totalX = 0;
        for (int i = 0; i < x.length; i++)
            totalX = totalX + Math.pow(x[i], 4);
        return totalX;
    }

    public double sumXPow2Y(double x[], double y[]) {
        double totalXY = 0;

        for (int i = 0; i < x.length; i++){
            totalXY = totalXY + (Math.pow(x[i], 2)*y[i]);
        }
        return totalXY;
    }
}
