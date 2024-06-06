package Gradiente;

public class GradientDescent {
    private  double b0;
    private double b1;
    private double learningRate;


    public GradientDescent(double learningRate) {
        this.b0 = 0;
        this.b1 = 0;
        this.learningRate = learningRate;
    }


   public String gradiente (double[] x, double[] y, int epochs){
        int n = x.length;
        //training
        for (int epoch = 0; epoch < epochs; epoch++){
            double sumB0 = 0;
            double sumB1 = 0;
            double totalError = 0; // Total error for this epoch

            for (int i = 0; i < n; i++){
                double prediction = predict(x[i]);
                double error = prediction - y[i] ;
                sumB0 += error;
                sumB1 += error * x[i];
                totalError += Math.pow(error, 2); // Sum of squared errors
            }
            b0 -= (learningRate/n) * sumB0;
            b1 -= (learningRate/n) * sumB1;
            // Calculate mean squared error for this epoch
            double meanSquaredError = totalError / n;
            System.out.println("Epoch " + (epoch + 1) + ", Mean Squared Error: " + meanSquaredError);
        }
       String  sb0 = String.valueOf(b0);
        String sb1 = String.valueOf(b1);
       return sb0+","+sb1+"x";
    }

    public double predict(double x){
        return b0 + b1 * x;
    }
    public void printRegEquation(){
        System.out.println("La ecuacion de regresion es: y = " + b0 +" + "+ b1 + "x");
    }

}
