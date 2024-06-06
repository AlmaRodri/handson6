package GA;
public class Individuo {
    private double b0;
    private double b1;
    private double aptitud;

    public Individuo(double b0, double b1, double aptitud) {
        this.b0 = b0;
        this.b1 = b1;
        this.aptitud = aptitud;
    }

    public double getB0() {
        return b0;
    }

    public double getB1() {
        return b1;
    }

    public void setB0(double b0) {
        this.b0 = b0;
    }

    public void setB1(double b1) {
        this.b1 = b1;
    }

    public void setAptitud(double aptitud) {
        this.aptitud = aptitud;
    }

    public double getAptitud() {
        return aptitud;
    }

    @Override
    public String toString() {
        return "Individuo{" + "b0=" + b0 + ", b1=" + b1 + ", aptitud=" + aptitud + '}';
    }
}

