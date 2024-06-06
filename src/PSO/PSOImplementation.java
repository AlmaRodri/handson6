package PSO;
import jade.core.behaviours.Behaviour;
public class PSOImplementation {

    public final int dimensions = 2; //Number of dimensions for problem
    public final int numParticles = 30; //Number of particles in swarm
    public final int numIterations = 10000; //Max number of iterations
    public final double c1 = 2; //Cognitive coefficient
    public final double c2 = 2; //Social coefficient
    public final double w = 0.729844; //Inertia coefficient
    public  double[] r1; //Random vector 1
    public  double[] r2;  //Random vector 2
    public double[] best;
    Particle[] particles; //Array to hold all particles
    DataSet dataset;

    public PSOImplementation(DataSet dataset) {
        this.dataset = dataset;

        //PSO algorithm
        particles = new Particle[numParticles];
        PSOAlgorithm PSO = new PSOAlgorithm(dataset,dimensions, numParticles, numIterations, c1, c2, w);

        //Initialize particles
        PSO.initParticles(particles);

        //PSO loop
        int numIter = 0;
        while (numIter<numIterations) {
            // Evaluate fitness of each particle
            for (int i=0; i<numParticles; i++) {
                particles[i].fitness = PSO.evaluateFitness(particles[i].position);

                //update personal best position
                if (particles[i].fitness <= PSO.evaluateFitness(particles[i].pBest)) {
                    particles[i].pBest = particles[i].position.clone();
                }
            }
            //Find best particle in set
            best = PSO.findBest(particles);

            //Initialize the random vectors for updates
            r1 = new double[dimensions];
            r2 = new double[dimensions];
            for (int i=0; i<dimensions; i++) {
                r1[i] = Math.random();
                r2[i] = Math.random();
            }

            //Update the velocity and position vectors
            for (int i=0; i<numParticles;i++) {
                PSO.updateVelocity(particles[i], best, r1, r2);
                PSO.updatePosition(particles[i]);
            }
            numIter++;
        }
        //Print the best solution
        print((best));
       // System.out.println(PSO.evaluateFitness(best));
    }

     // Helped method to print an array
    public void print (double[] a) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            if (i == 0) {
                result.append(a[i]); // El término b0 no tiene variable asociada
            } else {
                if (a[i] >= 0) {
                    result.append(",+"); // Agregar el signo más para términos positivos
                } else {
                    result.append(",-"); // Agregar el signo menos para términos negativos
                }
                result.append(Math.abs(a[i])).append("x").append(i); // Agregar el término bi
            }
        }
        System.out.println(result.toString());
    }

}
