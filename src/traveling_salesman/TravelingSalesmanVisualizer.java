package traveling_salesman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class TravelingSalesmanVisualizer extends JFrame {
    public static final int WIDTH = 1200, HEIGHT = 750;

    public TravelingSalesmanVisualizer() throws IOException{
        Dimension dimension = new Dimension(WIDTH, HEIGHT);
        this.setSize(dimension);
        this.setMinimumSize(dimension);
        this.setPreferredSize(dimension);
        this.setResizable(true);

        Display display = new Display();
        this.setContentPane(display);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                switch (key) {
                    case KeyEvent.VK_ESCAPE:
                        System.exit(0);
                        break;
                }
            }
        });

        this.setFocusable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Traveling Salesman Visualizer");
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        // Create and add our locations
        Location location = new Location(60, 200);
        LocationManager.addLocation(location);
        Location location2 = new Location(180, 200);
        LocationManager.addLocation(location2);
        Location location3 = new Location(80, 180);
        LocationManager.addLocation(location3);
        Location location4 = new Location(140, 180);
        LocationManager.addLocation(location4);
        Location location5 = new Location(20, 160);
        LocationManager.addLocation(location5);
        Location location6 = new Location(100, 160);
        LocationManager.addLocation(location6);
        Location location7 = new Location(200, 160);
        LocationManager.addLocation(location7);
        Location location8 = new Location(140, 140);
        LocationManager.addLocation(location8);
        Location location9 = new Location(40, 120);
        LocationManager.addLocation(location9);
        Location location10 = new Location(100, 120);
        LocationManager.addLocation(location10);
        Location location11 = new Location(180, 100);
        LocationManager.addLocation(location11);
        Location location12 = new Location(60, 80);
        LocationManager.addLocation(location12);
        Location location13 = new Location(120, 80);
        LocationManager.addLocation(location13);
        Location location14 = new Location(180, 60);
        LocationManager.addLocation(location14);
        Location location15 = new Location(20, 40);
        LocationManager.addLocation(location15);
        Location location16 = new Location(100, 40);
        LocationManager.addLocation(location16);
        Location location17 = new Location(200, 40);
        LocationManager.addLocation(location17);
        Location location18 = new Location(20, 20);
        LocationManager.addLocation(location18);
        Location location19 = new Location(60, 20);
        LocationManager.addLocation(location19);
        Location location20 = new Location(160, 20);
        LocationManager.addLocation(location20);

        // Initialize population
        Population pop = new Population(50, true);
        System.out.println("Initial distance: " + pop.getFittest().getDistance());

        // Evolve population for 100 generations
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(null);
        pop = geneticAlgorithm.evolvePopulation(pop);
        for (int i = 0; i < 100; i++) {
            pop = geneticAlgorithm.evolvePopulation(pop);
        }

        // Print final results
        System.out.println("Finished");
        System.out.println("Final distance: " + pop.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(pop.getFittest());
        LocationManager.clearLocations();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new TravelingSalesmanVisualizer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
