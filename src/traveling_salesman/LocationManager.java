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
     * Get the current number of destination Locations
     * */
    public static int numberOfLocations(){
        return destinationLocations.size();
    }

}