package Gradiente;

public class DataSet {
    private double x[];
    private double y[];

   /* public DataSet() {
        x = new double[]{23, 26, 30, 34, 43, 48, 52, 57, 58};
        y = new double[]{651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518};
    }*/

   public DataSet(double x[], double y[]){
        this.x = x;
        this.y = y;
    }

    public double[] getX(){

        return this.x;
    }

    public double[] getY(){

        return this.y;
    }
}
