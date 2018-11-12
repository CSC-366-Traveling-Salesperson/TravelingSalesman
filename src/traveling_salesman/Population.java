package traveling_salesman;

/**
 * Created by Michael on 11/4/2018.
 */


public class Population {
    private final Route[] population;


    /**
     * Constructs a population
     * */
    public Population(int populationSize, boolean initialize) {
        population = new Route[populationSize];

        if (initialize) {
            for (int i = 0; i < populationSize(); i++) {
                Route route = new Route();
                route.generateRandomRoute();
                saveRoute(i, route);
            }
        }

    }


    /**
     * Saves a Route at a particular index in tbe Population
     * */
    public synchronized void saveRoute(int index, Route route) {
        population[index] = route;
    }


    /**
     * Retrieves a Route from the Population
     * */
    public synchronized Route getRoute(int index) {
        return population[index];
    }


    /**
    * Computes and returns the fittest Route in the Population
    * */
    public synchronized Route getFittest() {
        Route fittestRoute = population[0];
        for (int i = 1; i < population.length; i++) {
            if (fittestRoute.getFitness() <= getRoute(i).getFitness()) {
                fittestRoute = getRoute(i);
            }
        }
        return fittestRoute;
    }


    /**
     * Gets the Population size
     * */
    public synchronized int populationSize() {
        return population.length;
    }

}