package GA;
import java.util.Random;

public class GenericAlgorithm {
    private static final int sizePopulation = 100;
    private static final int generations = 1000;
    private static final double MutationRate = 0.1;
    private double[] x;
    private double[] y;
    private Random random;

    public GenericAlgorithm(double[] x, double[] y) {
        this.x = x;
        this.y = y;
        random = new Random();
    }

    public String ejecutar() {
        Poblacion poblacion = new Poblacion(sizePopulation, 1, 200, 0, 50); // Dos variables: B0 y B1

        for (int i = 0; i < generations; i++) {
            poblacion.evaluarAptitud(x, y);
            Individuo[] padres = poblacion.seleccionarPadres();
            poblacion.cruzar(padres);
            poblacion.mutar(MutationRate, 1, 200, 0, 50);

            Individuo mejorIndividuo = poblacion.obtenerMejorIndividuo();
            double b0 = mejorIndividuo.getB0();
            double b1 = mejorIndividuo.getB1();
            double cod = Coeficiente.calcularR2(x, y, b0, b1);

            if (cod < 0.90 || cod > 1) {
                continue;
            } else {
                // Convertir los valores de b0 y b1 a String y concatenarlos
               String sb0 = String.valueOf(b0);
               String sb1 = String.valueOf(b1);
                return  sb0+","+sb1+"x";

            }
        }
        return null;
    }
}

