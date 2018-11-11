package traveling_salesman;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Michael on 11/4/2018.
 */


public class Route{
    private HashMap<Integer, Location> route = new HashMap<Integer, Location>();
    private double fitness = 0.0;
    private double distance = 0.0;


    /**
     * Constructs an empty Route
     * */
    public Route(){
        for (int i = 0; i < LocationManager.numberOfLocations(); i++) {
            route.put(i, null);
        }
    }


    /**
     * Generates a random Route for a Population
     * */
    public void generateRandomRoute() {
        ArrayList<Integer> indicies = new ArrayList<Integer>();
        for (Integer index = 0; index < LocationManager.numberOfLocations(); index++) {
            indicies.add(index);
        }
        Collections.shuffle(indicies);
        for (Integer index : indicies) {
            route.put(index, LocationManager.getLocation(index));
        }
    }


    /**
     * Retrieves a Location from the Route
     * */
    public Location getLocation(Integer routePosition) {
        return route.get(routePosition);
    }


    /**
     * Puts a Location in a certain position along a Route
     * */
    public void setLocation(Integer position, Location location) {
        route.put(position, location);
        /*
        * Reset the fitness and distance since the Route has been altered
        * */
        fitness = 0.0;
        distance = 0.0;
    }


    /**
    * Computes the Route's fitness
    * */
    public double getFitness() {
        /*
        * TODO --- Determine new fitness metric?
        * */
        if (fitness == 0.0) {
            fitness = 1.0 / getDistance();
        }
        return fitness;
    }


    /**
     * Computes the Route's total distance
     * */
    public double getDistance(){
        if (distance == 0.0) {
            double routeDistance = 0.0;

            for (int locationIndex=0; locationIndex < route.size(); locationIndex++) {
                Location origin = getLocation(locationIndex);
                Location destination;

                if (locationIndex + 1 < routeSize()){
                    destination = getLocation(locationIndex + 1);
                }else {
                    destination = getLocation(0);
                }

                routeDistance += origin.distanceTo(destination);

            }

            distance = routeDistance;

        }

        return distance;

    }


    /**
     * Gets the number of Locations currently on the Route
     * */
    public int routeSize() {
        return route.size();
    }


    /**
     * Checks if the Route contains a particular Location
     * */
    public boolean containsLocation(Location location){
        return route.containsValue(location);
    }


    public void draw(Graphics g, Color locationColor, Color edgeColor) {
        for (Integer index : route.keySet()) {
            if (index < route.size() - 1) {
                route.get(index).draw(g, locationColor);
                drawEdge(g, edgeColor, route.get(index), route.get(index + 1));
            }else {
                route.get(index).draw(g, locationColor);
                drawEdge(g, edgeColor, route.get(index), route.get(0));
            }
        }
    }


    private void drawEdge(Graphics g, Color edgeColor, Location fromLocation, Location toLocation) {
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(edgeColor);
        g2d.draw(new Line2D.Double(fromLocation.getX() + Location.getRadius(), fromLocation.getY() + Location.getRadius(),
                toLocation.getX() + Location.getRadius(), toLocation.getY() + Location.getRadius()));

        g2d.dispose();
    }


    /**
     * String representation of a Route
     * */
    @Override
    public String toString() {
        String geneString = "";
        for (Integer i = 0; i < routeSize(); i++) {
            geneString += "|" + getLocation(i) + "|\n";
        }
        return geneString;
    }
}