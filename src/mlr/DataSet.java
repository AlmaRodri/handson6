package mlr;
public class DataSet {
private double x[];
private double y[];

/*public DataSet(){
    x=new double[]{-3,-2,-1,0,1,2,3 };
    y=new double[]{ 7.5,3,0.5,1,3,6,14};
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
