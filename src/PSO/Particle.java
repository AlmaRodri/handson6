package PSO;
public class Particle {
   public double[] position; //The position vector of this particle
    public double fitness; //The fitness of this particle
    public double[] velocity; //The velocity vector of this particle
    public double[] pBest; //Personal best of the particle

    public Particle(double[] position, double[] velocity) {
        this.position = position;
        this.velocity = velocity;
    }
}
