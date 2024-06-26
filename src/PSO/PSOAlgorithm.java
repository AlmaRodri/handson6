package PSO;
public class PSOAlgorithm {
    DataSet dataset; // Tu conjunto de datos
    int numParticles = 50; //Number of particles in swarm
    int numIterations = 100; //Max number of iterations
    int dimensions = 2; //Number of dimensions for problem

    double c1 = 1; //Cognitive coefficient
    double c2 = 1; //Social coefficient
    double w = 0.8; //Inertia coefficient

    public PSOAlgorithm(DataSet dataset,int dimensions, int numParticles, int numIterations, double c1, double c2, double w){
    this.dataset = dataset;
    this.dimensions = dimensions;
    this.numParticles = numParticles;
    this.numIterations = numIterations;
    this.c1 = c1;
    this.c2 = c2;
    this.w = w;
}
//Method to initialize the particles for PSO
    public void initParticles(Particle[] particles) {
        //For each particle
        for (int i=0; i<particles.length;i++) {
            double[] positions = new double[dimensions];
            double[] velocities = new double [dimensions];
            //For each dimension of the particle assign a random x value [-5.12,5.12] and velocity=0
            for (int j=0; j<dimensions; j++) {
                positions[j] = ((Math.random()* ((5.12-(-5.12)))) - 5.12);
                velocities[j] = 0;
            }
            //Create the particle
            particles[i] = new Particle(positions, velocities);
            //Set particles personal best to initialized values
            particles[i].pBest = particles[i].position.clone();
        }
    }

    //Method to update the velocities vector of a particle
    public void updateVelocity(Particle particle, double[] best, double[] r1, double[] r2) {
        //First we clone the velocities, positions, personal and neighbourhood best
        double[] velocities = particle.velocity.clone();
        double[] personalBest = particle.pBest.clone();
        double[] positions = particle.position.clone();
        double[] bestNeigh = best.clone();

        double[] inertiaTerm = new double[dimensions];
        double[] difference1 = new double[dimensions];
        double[] difference2 = new double[dimensions];

        double[] c1Timesr1 = new double[dimensions];
        double[] c2Timesr2 = new double[dimensions];

        double[] cognitiveTerm = new double[dimensions];
        double[] socialTerm = new double[dimensions];

        //Calculate inertia component
        for (int i=0; i<dimensions; i++) {
            inertiaTerm[i] = w*velocities[i];
        }

        //Calculate the cognitive component

        //Calculate personal best - current position
        for (int i=0; i<dimensions; i++) {
            difference1[i] = personalBest[i] - positions[i];
        }

        //Calculate c1*r1
        for (int i=0; i<dimensions; i++) {
            c1Timesr1[i] = c1*r1[i];
        }

        //Calculate c1*r1*diff = cognitive term
        for (int i=0; i<dimensions; i++) {
            cognitiveTerm[i] = c1Timesr1[i]*difference1[i];
        }

        //Calculate the social term

        //Calculate neighbourhood best - current position
        for (int i=0; i<dimensions; i++) {
            difference2[i] = bestNeigh[i] - positions[i];
        }

        //Calculate c2*r2
        for (int i=0; i<dimensions; i++) {
            c2Timesr2[i] = c2*r2[i];
        }
        //Calculate c2*r2*diff2 = social component
        for (int i=0; i<dimensions; i++) {
            socialTerm[i] = c2Timesr2[i]*difference2[i];
        }

        //Update particles velocity at all dimensions
        for (int i=0; i<dimensions; i++) {
            particle.velocity[i] = inertiaTerm[i]+cognitiveTerm[i]+socialTerm[i];
        }
    }

  //  Method to update the positions vector of a particle
  public void updatePosition(Particle particle) {
      for (int i=0; i<dimensions; i++) {
          particle.position[i] = particle.position[i]+particle.velocity[i];
      }
  }
//Method to find the best (fittest) particle from a given set of particles
    public double[] findBest(Particle[] particles) {
        double[] best = null;
        double bestFitness = Double.MAX_VALUE;
        for(int i=0; i<numParticles; i++) {
            if (evaluateFitness(particles[i].pBest)<= bestFitness) {
                bestFitness = evaluateFitness(particles[i].pBest);
                best = particles[i].pBest;
            }
        }
        return best;
    }

    //Method to calculate the fitness of a particle using the Rastrigin function
    public double evaluateFitness(double[] positions) {
        double fitness = 0;
        for (int i=0; i<dimensions; i++) {
            fitness = fitness + (Math.pow(positions[i],2)-(10*Math.cos(2*Math.PI*positions[i])));
        }

        fitness = fitness + (10*dimensions);
        return fitness;
    }

}
