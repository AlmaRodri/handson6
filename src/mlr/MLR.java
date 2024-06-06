package mlr;
public class MLR {
    private double beta0;
    private double beta1;
    private double beta2;
    private DataSet dS;
    private DiscreteMaths disMath;


    public MLR(DataSet dS, DiscreteMaths disMath) {
        beta0 = 0;
        beta1 = 0;
        beta2 = 0;
        this.dS = dS;
        this.disMath = disMath;
    }
    public double calculeteMatrixA(){
        int n=dS.getX().length;
        double matrizAux=(n*disMath.sumXPow2(dS.getX())*disMath.sumXPow4(dS.getX()))+(disMath.sumX(dS.getX())*
                disMath.sumXPow2(dS.getX())*disMath.sumXPow3(dS.getX()))+(disMath.sumX(dS.getX())*
                disMath.sumXPow2(dS.getX())*disMath.sumXPow3(dS.getX()));
        double matrizHelp= (float) ((-n* Math.pow(disMath.sumXPow3(dS.getX()),2))-Math.pow(disMath.sumXPow2(dS.getX()),3)
                - Math.pow(disMath.sumX(dS.getX()),2)*disMath.sumXPow4(dS.getX()));
        double matrizA=matrizAux + matrizHelp;
        return matrizA;
    }
    public double calculateIntersection(){
        // beta0 = ...
        int n=dS.getX().length;
        double beta0Dividend= ((disMath.sumY(dS.getY())*disMath.sumXPow2(dS.getX())*disMath.sumXPow4(dS.getX()))+
                        (disMath.sumXY(dS.getX(), dS.getY())*disMath.sumXPow3(dS.getX())*disMath.sumXPow2(dS.getX()))+
                        (disMath.sumXPow2Y(dS.getX(), dS.getY())*disMath.sumX(dS.getX())*disMath.sumXPow3(dS.getX()))-
                        (Math.pow(disMath.sumXPow2(dS.getX()),2)*disMath.sumXPow2Y(dS.getX(), dS.getY()))-
                        (Math.pow(disMath.sumXPow3(dS.getX()),2)*disMath.sumY(dS.getY()))-
                        (disMath.sumX(dS.getX())*disMath.sumXY(dS.getX(), dS.getY())*disMath.sumXPow4(dS.getX())));

        double beta0= beta0Dividend/calculeteMatrixA();
        return beta0;
    }
    public double calculateSlope1(){
        // beta1 = ...
        int n=dS.getX().length;
        double betaPosi=(n*disMath.sumXY(dS.getX(), dS.getY())*disMath.sumXPow4(dS.getX()))+
                (disMath.sumY(dS.getY())*disMath.sumXPow2(dS.getX())*disMath.sumXPow3(dS.getX()))+
                (disMath.sumX(dS.getX())*disMath.sumXPow2Y(dS.getX(), dS.getY())*disMath.sumXPow2(dS.getX()));
        double betanega= (n*disMath.sumXPow2Y(dS.getX(), dS.getY()))*disMath.sumXPow3(dS.getX())+
                        (disMath.sumY(dS.getY())* disMath.sumX(dS.getX())*disMath.sumXPow4(dS.getX()))+
                        (disMath.sumXY(dS.getX(), dS.getY())* Math.pow(disMath.sumXPow2(dS.getX()),2));

        double beta1= (betaPosi-betanega)/calculeteMatrixA();

        return beta1;
    }

    public double calculateSlope2(){
    //beta 2= ...
        int n=dS.getX().length;
    double betaPosi= (n*disMath.sumXPow2(dS.getX())* disMath.sumXPow2Y(dS.getX(), dS.getY()))+
            (disMath.sumX(dS.getX())*disMath.sumXPow3(dS.getX())*disMath.sumY(dS.getY()))+
            (disMath.sumX(dS.getX())*disMath.sumXY(dS.getX(), dS.getY())*disMath.sumXPow2(dS.getX()));
    double betanega= (disMath.sumY(dS.getY())* Math.pow(disMath.sumXPow2(dS.getX()),2))+
                (disMath.sumXPow2Y(dS.getX(), dS.getY())* Math.pow(disMath.sumX(dS.getX()),2))+
                (n*disMath.sumXY(dS.getX(), dS.getY())*disMath.sumXPow3(dS.getX()));
    double beta2= (betaPosi-betanega)/calculeteMatrixA();

        return beta2;
    }

    public void printRegEquation(){
        System.out.print("La ecuacion de regresion es: y = ");
        System.out.println(calculateSlope2()+ " X^2 + " + calculateSlope1() + " X  + " + calculateIntersection());
    }

   public void predict(double x){
        double predictY;
        predictY= (calculateIntersection()+(calculateSlope1()*x)+(calculateSlope2()* Math.pow(x,2)));
        System.out.println("La prediccion de "+ x+ " es: "+ predictY);
    }

}
