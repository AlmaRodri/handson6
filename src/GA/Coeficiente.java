package GA;
public class Coeficiente {
    public static double calcularR2(double[] x, double[] y, double b0, double b1) {
        // Calcular la suma total de cuadrados (SST)
        double meanY = calcularMedia(y);
        double sst = 0;
        for (int i = 0; i < y.length; i++) {
            sst += Math.pow(y[i] - meanY, 2);
        }

        // Calcular la suma de cuadrados de la regresión (SSR)
        double[] yPredicho = new double[y.length];
        for (int i = 0; i < y.length; i++) {
            yPredicho[i] = b0 + b1 * x[i];
        }
        double ssr = 0;
        for (int i = 0; i < y.length; i++) {
            ssr += Math.pow(yPredicho[i] - meanY, 2);
        }

        // Calcular R^2
        double r2 = (ssr / sst);
        return r2;
    }

    private static double calcularMedia(double[] valores) {
        double sum = 0;
        for (double valor : valores) {
            sum += valor;
        }
        return sum / valores.length;
    }
}
