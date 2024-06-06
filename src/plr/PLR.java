package plr;
public class PLR {
    private DataSet dS;
    private DiscretMath diMath;

    public PLR(DataSet dS, DiscretMath diMath) {
        this.dS = dS;
        this.diMath = diMath;
    }

    public double [][] invertir (){
        double [][] tranpuesta, mulXtX, inversa;
        tranpuesta = diMath.transposeX(dS.getX()); // transpuesta
        mulXtX = diMath.prodXtX(tranpuesta,dS.getX()); // transpuesta de x por x
        inversa = diMath.inversaGaussJordan(mulXtX); // inversa de la multiplicacion de la transpuesta por x
        return inversa;
    }
    public double[] getBetas() {
        double[] xTY, betas;
        double[][] inversa;
        xTY = diMath.matXVect(diMath.transposeX(dS.getX()), dS.getY());
        inversa = invertir();
        betas = diMath.matXVect(inversa,xTY);
        return betas;
    }

public void predict(double x){
    double predictY=0;
    double[] xTY, polinomio;
    double[][] inversa;
    xTY = diMath.matXVect(diMath.transposeX(dS.getX()),dS.getY());
    inversa = invertir();
    polinomio = diMath.matXVect(inversa, xTY);
    for (int i = 0; i < polinomio.length; i++) {
        predictY += polinomio[i] * Math.pow(x, i);
    }
    System.out.println("La prediccion de " + x + " es: " + predictY);
}

}
