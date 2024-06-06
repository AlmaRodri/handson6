package Gradiente;

public class SLR {
        private double beta0;
        private double beta1;
        private DataSet dataSet;
        private DiscreteMaths discreteMaths;
        public SLR(DataSet dataSet, DiscreteMaths discreteMaths){
            beta0 = 0;
            beta1 = 0;
            this.dataSet = dataSet;
            this.discreteMaths = discreteMaths;
        }

        public double calculateIntersection(){
            // beta0 = ...
            int n=dataSet.getX().length;
            double beta0Dividend=(discreteMaths.sumY(dataSet.getY())-(discreteMaths.sumX(dataSet.getX())*calculateSlope()));
            double beta0= beta0Dividend/n;
            return beta0;
        }
        public double calculateSlope(){
            // beta1 = ...
            int n=dataSet.getX().length;
            double betaDividend=(n*discreteMaths.sumXY(dataSet.getX(),dataSet.getY())) -(discreteMaths.sumX(dataSet.getX())
                    * discreteMaths.sumY(dataSet.getY()));
            double betaDivisor= (float) ((n*discreteMaths.sumXPow(dataSet.getX()))-
                    (Math.pow(discreteMaths.sumX(dataSet.getX()),2)));

            double beta1= betaDividend/betaDivisor;

            return beta1;
        }

        public void printRegEquation(){
            System.out.print("La ecuacion de regresion es: y = ");
            System.out.println(calculateIntersection() + " + " +calculateSlope() + "x");
        }

        public void predict(double x){
            double predictY;
            predictY= (calculateIntersection()+(calculateSlope()*x));
            System.out.println("La prediccion de "+ x+ " es: "+ predictY);
        }
    }

