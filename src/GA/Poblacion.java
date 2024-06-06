package GA;

import java.util.Random;

public class Poblacion {
    public Individuo[] individuos;

    public Poblacion(int tamanioPoblacion, double rangoB0Min, double rangoB0Max, double rangoB1Min, double rangoB1Max) {
        individuos = new Individuo[tamanioPoblacion];
        Random random = new Random();
        for (int i = 0; i < tamanioPoblacion; i++) {
            double b0 = rangoB0Min + (rangoB0Max - rangoB0Min) * random.nextDouble();
            double b1 = rangoB1Min + (rangoB1Max - rangoB1Min) * random.nextDouble();
            individuos[i] = new Individuo(b0, b1, 0);
        }
    }

    public void evaluarAptitud(double[] x, double[] y) {
        // Evalúa la aptitud de cada individuo comparando con los datos de entrada y salida
        for (Individuo individuo : individuos) {
            double b0 = individuo.getB0();
            double b1 = individuo.getB1();
            double errorCuadraticoMedio = calcularECM(x, y, b0, b1);
            individuo.setAptitud(errorCuadraticoMedio);
        }
    }

    private double calcularECM(double[] x, double[] y, double b0, double b1) {
        double sumatoriaError = 0;
        int n = x.length;
        for (int i = 0; i < n; i++) {
            double yPredicho = b0 + b1 * x[i];
            double error = y[i] - yPredicho;
            sumatoriaError += Math.pow(error, 2);
        }
        return sumatoriaError / n;
    }

    public Individuo[] seleccionarPadres() {
        // Selecciona los padres para el cruce
        Individuo[] padresSeleccionados = new Individuo[individuos.length];

        // Calcular la suma total de aptitudes de todos los individuos en la población
        double sumaAptitudes = 0;
        for (Individuo individuo : individuos) {
            sumaAptitudes += individuo.getAptitud();
        }

        // Calcular la probabilidad de selección de cada individuo
        double[] probabilidades = new double[individuos.length];
        for (int i = 0; i < individuos.length; i++) {
            probabilidades[i] = individuos[i].getAptitud() / sumaAptitudes;
        }

        // Realizar la selección por ruleta
        Random random = new Random();
        double sumaProbabilidades = 0;
        for (int i = 0; i < individuos.length; i++) {
            double valorSeleccionado = random.nextDouble();
            for (int j = 0; j < individuos.length; j++) {
                sumaProbabilidades += probabilidades[j];
                if (sumaProbabilidades >= valorSeleccionado) {
                    padresSeleccionados[i] = individuos[j];
                    break;
                }
            }
        }
        return padresSeleccionados;
    }

    public Individuo[] cruzar(Individuo[] padres) {
        Individuo[] descendencia = new Individuo[padres.length];

        Random random = new Random();
        for (int i = 0; i < padres.length; i += 2) {
            double b0Padre1 = padres[i].getB0();
            double b1Padre1 = padres[i].getB1();
            double b0Padre2 = padres[i + 1].getB0();
            double b1Padre2 = padres[i + 1].getB1();

            // Realizar el cruce de un punto
            double puntoCruce = random.nextDouble(); // Punto de cruce aleatorio

            double b0Descendiente1 = puntoCruce * b0Padre1 + (1 - puntoCruce) * b0Padre2;
            double b1Descendiente1 = puntoCruce * b1Padre1 + (1 - puntoCruce) * b1Padre2;

            double b0Descendiente2 = puntoCruce * b0Padre2 + (1 - puntoCruce) * b0Padre1;
            double b1Descendiente2 = puntoCruce * b1Padre2 + (1 - puntoCruce) * b1Padre1;

            descendencia[i] = new Individuo(b0Descendiente1, b1Descendiente1, 0);
            descendencia[i + 1] = new Individuo(b0Descendiente2, b1Descendiente2, 0);
        }

        return descendencia;
    }

    public void mutar(double probabilidadMutacion, double rangoB0Min, double rangoB0Max, double rangoB1Min, double rangoB1Max) {
        // Realiza la mutación de los individuos con cierta probabilidad
        Random random = new Random();
        for (Individuo individuo : individuos) {
            // Aplicar mutación al coeficiente B0
            if (random.nextDouble() < probabilidadMutacion) {
                double nuevoB0 = rangoB0Min + (rangoB0Max - rangoB0Min) * random.nextDouble();
                individuo.setB0(nuevoB0);
            }

            // Aplicar mutación al coeficiente B1
            if (random.nextDouble() < probabilidadMutacion) {
                double nuevoB1 = rangoB1Min + (rangoB1Max - rangoB1Min) * random.nextDouble();
                individuo.setB1(nuevoB1);
            }
        }
    }

    public Individuo obtenerMejorIndividuo() {
        // Devuelve el mejor individuo de la población
        Individuo mejorIndividuo = individuos[0];
        double mejorAptitud = mejorIndividuo.getAptitud();

        for (int i = 1; i < individuos.length; i++) {
            if (individuos[i].getAptitud() < mejorAptitud) {
                mejorIndividuo = individuos[i];
                mejorAptitud = mejorIndividuo.getAptitud();
            }
        }
        return mejorIndividuo;
    }
}



