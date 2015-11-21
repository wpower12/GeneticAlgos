/**
 * Class that will instantiate and perform the LGA on an initial population,
 * given a fitness function.
 *
 * @author wpower
 */
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LittleGeneticAlgorithm {

    private Random rand;
    private List<Individual> population;
    private int indsize;
    private double mrate;
    
    FitnessFunction fitFunc;
    
    public LittleGeneticAlgorithm(int popsize, int ind, double m) {
        rand = new Random();
        indsize = ind;
        mrate = m;
        //Popsize should be divisible by 4.
        population = new ArrayList<Individual>();
        for (int i = 0; i < popsize; i++) {
            population.add(new Individual(indsize));
        }
    }

    public void setFitnessFunction(FitnessFunction f) {
        fitFunc = f;
    }

    public void run(int generations) {
        for (int g = 0; g < generations; g++) {
            decode();
            evaluate();
            crossover();
            applyMutation();
        }
    }

    public void print(PrintStream out) {
        evaluate();
        //Print out the currently stored population
        String line = String.format(" # : %17s | %7s | %11s ", "Genes", "Value", "Fitness");
        out.println(line);
        int g = 1;
        for (Individual p : population) {
            line = String.format("%3d: %12s1 | %6d1 | %11d ", g, p.genes, p.value, p.fitness);
            out.println(line);
            g++;
        }
    }

    /**
     * Private Methods
     */
    private void decode() {
        for (Individual p : population) {
            p.value = Integer.parseInt(p.genes, 2);
        }
    }

    private void evaluate() {
        for (Individual p : population) {
            p.fitness = fitFunc.fitness(p.value);
        }
    }

    private void crossover() {
        //By using the fitness as a weight, and multiplying by a random number,
        //we can then sort to get a 'roulette wheel' chance for each individual
        //weighted by fitness.  The top half stays and makes babies.
        population.stream().forEach((p) -> {
            p.fitness = (int) (p.fitness * rand.nextFloat());
        });
        Collections.sort(population);

        int mid = population.size() / 2;
        for (int i = 0, j = 1; i < mid - 1; i++, j++) {
            //Copy parents to further down the list
            population.get(i + mid).copy(population.get(i));
            population.get(j + mid).copy(population.get(j));

            //Call swap to cross over the parents
            swapGenes(population.get(i), population.get(j));
        }
    }

    private void applyMutation() {
        for (Individual i : population) {
            if (rand.nextDouble() < mrate) {
                int pos = rand.nextInt(indsize);
                char c = (i.genes.charAt(pos) == '1') ? '0' : '1';
                i.genes = i.genes.substring(0, pos) + c + i.genes.substring(pos + 1, indsize);
            }
        }
    }

    private void swapGenes(Individual a, Individual b) {
        int cp = this.rand.nextInt(indsize - 1);
        a.genes = a.genes.substring(0, cp) + b.genes.substring(cp, indsize);
        b.genes = b.genes.substring(0, cp) + a.genes.substring(cp, indsize);
    }

    public static void main(String args[]) {
        //Set basic parameters
        int pop = 16;   //Pop size should be divisible by 4 to ensure even generation split
        int ind = 10;
        double mut_rate = 0.075;
        LittleGeneticAlgorithm lga = new LittleGeneticAlgorithm(pop, ind, mut_rate);

        //Add a fitness function
        FitnessFunction maxsquare = (int a) -> ((a - 30) * (a - 30) + 15);
        FitnessFunction maxcubic  = (int a) -> ((a - 30) * (a) * (a + 30) + 15);

        lga.setFitnessFunction(maxcubic); //Yay lambdas!

        //Run simulation
        int generations = 100;
        lga.run(generations);

        //Output stats
        lga.print(System.out);
    }
}
