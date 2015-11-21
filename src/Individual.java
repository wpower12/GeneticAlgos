
/**
 * Individual - Core internal class for tracking the population members.
 * @author wpower
 */
class Individual implements Comparable {

    String genes;
    int value;
    int fitness;

    //Random constructor - size is the length of the individuals bitstring
    public Individual(int size) {
        genes = Individual.randomGenes(size);
    }

    //String based constructor, gene is just set to the give String.  
    //Caller must make sure the String is valid in the LGA's alphabet (0,1)'s
    public Individual(String g) {
        genes = g;
    }

    //Returns a random bitstring of length size
    private static String randomGenes(int size) {
        String ret = "";
        for (int i = 0; i < size; i++) {
            ret += (Math.random() > 0.5) ? 0 : 1;
        }
        return ret;
    }

    /**
     * The direction in which we sort directly impacts the interpretation, and
     * result of the LGA. By sorting such that the lowest valued items are
     * first in the list, we prioritze low fitness values. IOW, if we use a
     * proper function as a fitness function, we are in effect finding the
     * minimum of it within the range of integers expressable by the lenght
     * of the individuals bitstrings.
     *
     * Sorting from high to low would instead find maxima.
     */
    @Override
    public int compareTo(Object o) {
        Individual p = (Individual) o;
        return (new Integer(fitness)).compareTo(p.fitness);
    }

    public void copy(Individual i) {
        this.fitness = i.fitness;
        this.genes = i.genes;
        this.value = i.value;
    }
}
