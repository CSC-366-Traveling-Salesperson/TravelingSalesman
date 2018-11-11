package traveling_salesman;

import java.awt.*;

/**
 * Created by Michael on 11/4/2018.
 */

public class Location {
    private static final int RADIUS = 10;
    private static final double MAX_X = 1200.0;
    private static final double MAX_Y = 850.0;
    private final double x;
    private final double y;


    /**
    * Constructs a randomly placed Location
    * */
    public Location(){
        this.x = Math.random() * MAX_X;
        this.y = Math.random() * MAX_Y;
    }


    /**
     * Constructs a Location at the chosen x and y coordinates
     * */
    public Location(double x, double y){
        this.x = x;
        this.y = y;
    }


    /**
     * Gets the Location's x coordinate
     * */
    public double getX(){
        return this.x;
    }


    /**
     * Gets the Location's y coordinate
     * */
    public double getY(){
        return this.y;
    }


    /**
     * Gets the Location's preset Radius
     * */
    public static int getRadius() {
        return RADIUS;
    }

    /**
     * Gets the distance to the second provided Location
     * */
    public double distanceTo(Location location){
        double xDistance = x - location.getX();
        double yDistance = y - location.getY();
        double distance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));

        return distance;
    }


    public void draw(Graphics g, Color color) {
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(color);

        g2d.fillOval((int) x, (int) y, 2*RADIUS, 2*RADIUS);
        g2d.dispose();
    }


    /**
     * String representation of a Location
     * */
    @Override
    public String toString(){
        /*return String.format("X: %.3f, Y: %.3f", x, y);*/
        return "X: " + x + ", Y: " + y;
    }

}