package traveling_salesman;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Michael on 11/4/2018.
 */


public class Population {
    private final Route[] population;
    private final Lock lock = new ReentrantLock();


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
    public void saveRoute(int index, Route route) {
        lock.lock();
        try {
            population[index] = route;
        } finally {
            lock.unlock();
        }
    }


    /**
     * Retrieves a Route from the Population
     * */
    public Route getRoute(int index) {
        lock.lock();
        try {
            return population[index];
        } finally {
            lock.unlock();
        }

    }


    /**
    * Computes and returns the fittest Route in the Population
    * */
    public Route getFittest() {
        lock.lock();
        try {
            Route fittestRoute = population[0];
            for (int i = 1; i < population.length; i++) {
                if (fittestRoute.getFitness() <= getRoute(i).getFitness()) {
                    fittestRoute = getRoute(i);
                }
            }
            return fittestRoute;
        }finally {
            lock.unlock();
        }
    }


    /**
     * Gets the Population size
     * */
    public int populationSize() {
        lock.lock();
        try {
            return population.length;
        }finally {
            lock.unlock();
        }
    }

}