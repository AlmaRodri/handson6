package plr;
public class DataSet {
    private double [][] x;
    private double [] y;

 /*   public DataSet() { // para cubica
        x = new double [][]{
                {1, 108, Math.pow(108,2), Math.pow(108,3)},
                {1, 115, Math.pow(115,2), Math.pow(115,3)},
                {1, 106, Math.pow(106,2), Math.pow(106,3)},
                {1, 97, Math.pow(97,2), Math.pow(97,3)},
                {1, 95, Math.pow(95,2), Math.pow(95,3)},
                {1, 91, Math.pow(91,2), Math.pow(91,3)},
                {1, 97, Math.pow(97,2), Math.pow(97,3)},
                {1, 83, Math.pow(83,2), Math.pow(83,3)},
                {1, 83, Math.pow(83,2), Math.pow(83,3)},
                {1, 78, Math.pow(78,2), Math.pow(78,3)},
                {1, 54, Math.pow(54,2), Math.pow(54,3)},
                {1, 67, Math.pow(67,2), Math.pow(67,3)},
                {1, 56, Math.pow(56,2), Math.pow(56,3)},
                {1, 53, Math.pow(53,2), Math.pow(53,3)},
                {1, 61, Math.pow(61,2), Math.pow(61,3)},
                {1, 115, Math.pow(115,2), Math.pow(115,3)},
                {1, 81, Math.pow(81,2), Math.pow(81,3)},
                {1, 78, Math.pow(78,2), Math.pow(78,3)},
                {1, 30, Math.pow(30,2), Math.pow(30,3)},
                {1, 45, Math.pow(45,2), Math.pow(45,3)},
                {1, 99, Math.pow(99,2), Math.pow(99,3)},
                {1, 32, Math.pow(32,2), Math.pow(32,3)},
                {1, 25, Math.pow(25,2), Math.pow(25,3)},
                {1, 28, Math.pow(28,2), Math.pow(28,3)},
                {1, 90, Math.pow(90,2), Math.pow(90,3)},
                {1, 89, Math.pow(89,2), Math.pow(89,3)},
        };
        y = new double[]{95,96,95,97,93,94,95,93,92,86,73,80,65,69,77,96,87,89,60,63,95,61,55,56,94,93};
    }*/

    public DataSet(double[][] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    public double[][] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }
}
