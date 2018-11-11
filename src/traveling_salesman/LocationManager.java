package traveling_salesman;

import java.util.HashMap;

/**
 * Created by Michael on 11/4/2018.
 */

public class LocationManager {
    private static HashMap<Integer, Location> destinationLocations = new HashMap<Integer, Location>();


    /**
     * Adds a destination Location
     * */
    public static void addLocation(Location location) {
        destinationLocations.put(destinationLocations.size(), location);
    }


    /**
     * Adds all provided destination Locations
     * */
    public static void addAll(HashMap<Integer, Location> locations) {
        destinationLocations.putAll(locations);
    }


    /**
     * Removes all current destination Locations
     * */
    public static void clearLocations() {
        destinationLocations.clear();
    }


    /**
     * Retrieves a Location from the set of destination Locations
     * */
    public static Location getLocation(Integer index){
        return destinationLocations.get(index);
    }


    /**
     * Determines if the set of destination Locations currently contains the provided Location
     * */
    public static boolean containsLocation(Location location) {
        return destinationLocations.containsValue(location);
    }


    /**
     * Get the current number of destination Locations
     * */
    public static int numberOfLocations(){
        return destinationLocations.size();
    }

}