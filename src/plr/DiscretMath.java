package plr;
public class DiscretMath {
    // transponer una matriz
    public double[][] transposeX(double[][] x) {
        int m = x.length;
        int n = x[0].length;
        double matrizT[][] = new double[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < x[0].length; j++) {
                matrizT[j][i] = x[i][j];
            }
        }
        return matrizT;
    }

    public double[][] prodXtX(double[][] tX, double[][] x) {
        int m = tX.length;
        int n = tX[0].length;
        int v = x[0].length;
        double[][] xtX = new double[m][v];
        double sum = 0;
        for (int i = 0; i < m; i++) { //recorrer filas de x
            for (int j = 0; j < v; j++) {  //recorrer columnas de x transpuesta
                for (int k = 0; k < n; k++) { //recorrer columnas de x y filas de tX
                    xtX [i][j] += tX[i][k] * x[k][j];
                }
            }
        }
        return xtX;
    }

    public double[]  matXVect(double[][] tranX, double[] y) {
        int m= tranX.length;
        int n = tranX[0].length;
        int v = y.length;
        if (n != v)
            throw new RuntimeException("No se pueden multiplicar las matrices");
        double[] xTY = new double[m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                xTY[i]+= tranX[i][j] * y[j];
            }
        }
        return xTY;
    }
    public double[][] inversaGaussJordan(double[][] matriz) {
        int n = matriz.length;
        double[][] identidad = new double[n][n];
        double[][] aumentada = new double[n][n * 2];
        double[][] inversa = new double[n][n];
        for (int i = 0; i < n; i++) {
            identidad[i][i] = 1;
            for (int j = 0; j < n; j++) {
                aumentada[i][j] = matriz[i][j];
                aumentada[i][j + n] = identidad[i][j];
            }
        }
        for (int i = 0; i < n; i++) {
            double pivot = aumentada[i][i];
            for (int j = i; j < n * 2; j++) {
                aumentada[i][j] /= pivot;
            }
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    double factor = aumentada[j][i];
                    for (int k = i; k < n * 2; k++) {
                        aumentada[j][k] -= factor * aumentada[i][k];
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inversa[i][j] = aumentada[i][j + n];
            }
        }
        return inversa;
    }
}