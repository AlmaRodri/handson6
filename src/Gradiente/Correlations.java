package Gradiente;

public class Correlations {
    public static DiscreteMaths discreteMaths;

    public Correlations(DiscreteMaths discreteMaths) {
        this.discreteMaths = discreteMaths;
    }

    public static double correlaCoef(DataSet dataSet){
    double [] x = dataSet.getX();
    double [] y = dataSet.getY();
     //calcular media
    double meanX = discreteMaths.sumX(x)/ x.length;
    double meanY = discreteMaths.sumY(y)/ y.length;

    //calcular numerador y denominador
        double numerador = 0;
        double denominadorX = 0;
        double denominadorY = 0;

        for(int i=0; i < x.length; i++ ){
            numerador += (x[i] - meanX) * (y[i] - meanY);
            denominadorX += Math.pow(x[i] - meanX, 2);
            denominadorY += Math.pow(y[i] - meanY, 2);
        }
        double denominador = (Math.sqrt(denominadorX) * Math.sqrt(denominadorY));

        try {
            double Coefficient = numerador/denominador;
            return Coefficient;
        } catch (Exception e){
            System.out.println("No se puede dividir por 0"+ e);
        }
        return 0;
}
public static double correlaDetermina(DataSet dataSet){
    double r = correlaCoef(dataSet);
    double r2 = r*r ;
    return r2;
}

}
