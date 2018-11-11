package traveling_salesman;

/**
 * Created by Michael on 11/4/2018.
 */
public class GeneticAlgorithm {
    private final Display display;
    private final double MUTATION_RATE;
    private final int TOURNAMENT_SIZE;
    private final boolean PREFERENTIAL_BEHAVIOR;

    /**
     * Constructs a GeneticAlgorithm with default values for MUTATION_RATE, TOURNAMENT_SIZE, and PREFERENTIAL_BEHAVIOR
     * */
    public GeneticAlgorithm(Display display) {
        this.display = display;
        this.MUTATION_RATE = 0.015;
        this.TOURNAMENT_SIZE = 5;
        this.PREFERENTIAL_BEHAVIOR = true;
    }


    /**
     * Constructs a GeneticAlgorithm with the provided values for MUTATION_RATE, TOURNAMENT_SIZE, and PREFERENTIAL_BEHAVIOR
     * */
    public GeneticAlgorithm(Display display, double mutationRate, int tournamentSize, boolean preferentialBehavior) {
        this.display = display;
        this.MUTATION_RATE = mutationRate;
        this.TOURNAMENT_SIZE = tournamentSize;
        this.PREFERENTIAL_BEHAVIOR = preferentialBehavior;
    }


    /**
     * Evolves a given Population over one generation through the process of:
     * o Selection
     * o Crossover
     * o Mutation
     * */
    public Population evolvePopulation(Population population) {
        Population newPopulation = new Population(population.populationSize(), false);

        /*
        * Keep the provided Population's best individual in an un-mutated form if PREFERENTIAL_BEHAVIOR is enabled
        * */
        int offset = 0;
        if (PREFERENTIAL_BEHAVIOR) {
            newPopulation.saveRoute(0, population.getFittest());
            offset = 1;
        }

        /*
        * For each of the new Population's indices, create individual Routes from the current Population via a
        * tournament selection process and crossover of genetic information
        * */
        for (int i = offset; i < newPopulation.populationSize(); i++) {
            /* Select parents */
            Route parent1 = tournamentSelection(population);
            Route parent2 = tournamentSelection(population);

            /* Crossover genetic information to child */
            Route child = crossover(parent1, parent2);

            /* Save child to the new Population */
            newPopulation.saveRoute(i, child);
        }

        /*
        * Mutate the new population slightly to introduce some new genetic material
        * */
        for (int i = offset; i < newPopulation.populationSize(); i++) {
            mutate(newPopulation.getRoute(i));
        }

        return newPopulation;
    }


    /**
     * Selects the best Route out of a random subset of the provided Population as a candidate Route for a followup
     * crossover of genes
     * */
    private Route tournamentSelection(Population population) {
        Population tournament = new Population(TOURNAMENT_SIZE, false);
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            int randomId = (int) (Math.random() * population.populationSize());
            tournament.saveRoute(i, population.getRoute(randomId));
        }
        Route fittest = tournament.getFittest();
        return fittest;
    }


    /**
     * Generates a child Route via an application of crossover to a set of parent Routes
     *     * f
     *     * TODO -------- Change the method of deciding which genes to persist? Possible application of pattern matching-esque
     *     * TODO -------- method?
    * */
    public Route crossover(Route parent1, Route parent2) {
        assert parent1.routeSize() == parent2.routeSize();
        Route child = new Route();
        int routeSize = parent1.routeSize();

        /*
        * Allocate a section of parent1's genes to persist to the new generation
        * */
        int startPos = (int) (Math.random() * parent1.routeSize());
        int endPos = startPos + (int) (Math.random() * (parent1.routeSize() - startPos));

        /*
        * Persist the allocated section of parent1's genes to the new generation
        * */
        for (int i = 0; i < endPos; i++) {
            if (startPos <= i) {
                child.setLocation(i, parent1.getLocation(i));
            }
        }

        /*
        * Persist the remaining genes from parent2 to the new generation
        * */
        for (int i = 0; i < routeSize; i++) {
            if (!child.containsLocation(parent2.getLocation(i))) {
                for (int j = 0; j < routeSize; j++) {
                    if (child.getLocation(j) == null) {
                        child.setLocation(j, parent2.getLocation(i));
                        break;
                    }
                }
            }
        }

        return child;
    }


    /**
     * Mutate a Route using swap mutation
     * */
    private void mutate(Route route) {
        for(int position1 = 0; position1 < route.routeSize(); position1++){
            if (Math.random() < MUTATION_RATE){
                int position2 = (int) (Math.random() * route.routeSize());

                Location location1 = route.getLocation(position1);
                Location location2 = route.getLocation(position2);

                route.setLocation(position2, location1);
                route.setLocation(position1, location2);
            }
        }
    }

}