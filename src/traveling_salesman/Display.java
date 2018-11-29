package traveling_salesman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Michael on 11/4/2018.
 */
public class Display extends JPanel {
    private static int PANEL_WIDTH = 1200;
    private static int PANEL_HEIGHT = 700;
    private static int CANVAS_WIDTH = 1000;
    private static int CANVAS_HEIGHT = 600;
    private static int TEXT_FIELD_WIDTH = 200;
    private static int TEXT_FIELD_HEIGHT = 25;
    private static int BUTTON_WIDTH = 200;
    private static int BUTTON_HEIGHT = 25;
    private static int GENERATIONS = 200;
    private static int ROUTE_DISPLAY_TIME_MILLIS = 50;
    private static int POPULATION_SIZE = 50;
    private static int DEFAULT_TOURNAMENT_SIZE = 5;
    private static double DEFAULT_RATE = 0.015;
    private static int NUMBEROFTHREADS = 8;
    private static Color NEW_LOCATION_COLOR = Color.blue;
    private static Color ROUTE_LOCATION_COLOR = Color.green;
    private static Color ROUTE_EDGE_COLOR = Color.RED;
    private JFormattedTextField mutationRateDescription;
    private JFormattedTextField tournamentSizeDescription;
    private JFormattedTextField outputDescription;
    /*private JFormattedTextField preferentialBehaviorDescription;*/
    private JFormattedTextField mutationRate;
    private JFormattedTextField tournamentSize;
    private JTextArea output;
    private JCheckBox preferentialBehavior;
    private JButton runButton;
    private JButton resetButton;
    private JComboBox crossoverBox;
    private DrawCanvas canvas;
    private GeneticAlgorithm geneticAlgorithm;
    private HashMap<Integer, Location> selectedLocations;
    private Queue<Route> drawQueue;
    private Route currentBestRoute;
    //executor to start and stop threads
    private static ExecutorService executor;


    public Display() throws IOException {

        executor = Executors.newFixedThreadPool(NUMBEROFTHREADS);

        Dimension panelMinimumAndPreferredSize = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
        Dimension canvasMinimumAndPreferredSize = new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT);
        Dimension textFieldMinimumAndPreferredSize = new Dimension(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        Dimension buttonMinimumAndPreferredSize = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
        Color panelColor = new Color(0, 0, 77);
        Color canvasColor = new Color(234, 234, 234);
        Color textFieldDescriptorColor = new Color(0, 100, 180);
        Color textFieldColor = new Color(234, 234, 234);
        Color buttonColor = new Color(0, 125, 0);

        this.setSize(panelMinimumAndPreferredSize);
        this.setPreferredSize(panelMinimumAndPreferredSize);
        this.setMinimumSize(panelMinimumAndPreferredSize);
        this.setBackground(panelColor);

        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        canvas = new DrawCanvas();
        canvas.setMinimumSize(canvasMinimumAndPreferredSize);
        canvas.setPreferredSize(canvasMinimumAndPreferredSize);
        canvas.setBackground(canvasColor);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = .5;
        constraints.gridwidth = 3;
        constraints.gridheight = 24;
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(canvas, constraints);

        outputDescription = new JFormattedTextField();
        outputDescription.setText("Output:");
        outputDescription.setMinimumSize(textFieldMinimumAndPreferredSize);
        outputDescription.setPreferredSize(textFieldMinimumAndPreferredSize);
        outputDescription.setEditable(false);
        outputDescription.setBackground(textFieldDescriptorColor);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = .5;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 3;
        constraints.gridy = 0;
        this.add(outputDescription, constraints);

        output = new JTextArea();
        output.setMinimumSize(textFieldMinimumAndPreferredSize);
        output.setEditable(false);
        output.setBackground(textFieldColor);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = .5;
        constraints.gridwidth = 1;
        constraints.gridheight = 23;
        constraints.gridx = 3;
        constraints.gridy = 1;
        this.add(output, constraints);

        mutationRateDescription = new JFormattedTextField();
        mutationRateDescription.setText("Desired Mutation Rate:");
        mutationRateDescription.setMinimumSize(textFieldMinimumAndPreferredSize);
        mutationRateDescription.setPreferredSize(textFieldMinimumAndPreferredSize);
        mutationRateDescription.setEditable(false);
        mutationRateDescription.setBackground(textFieldDescriptorColor);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = .5;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 0;
        constraints.gridy = 25;
        this.add(mutationRateDescription, constraints);

        tournamentSizeDescription = new JFormattedTextField();
        tournamentSizeDescription.setText("Desired Tournament Size:");
        tournamentSizeDescription.setMinimumSize(textFieldMinimumAndPreferredSize);
        tournamentSizeDescription.setPreferredSize(textFieldMinimumAndPreferredSize);
        tournamentSizeDescription.setEditable(false);
        tournamentSizeDescription.setBackground(textFieldDescriptorColor);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = .5;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 1;
        constraints.gridy = 25;
        this.add(tournamentSizeDescription, constraints);

        /*preferentialBehaviorDescription = new JFormattedTextField();
        preferentialBehaviorDescription.setText("Toggle Preferential Behavior:");
        preferentialBehaviorDescription.setMinimumSize(textFieldMinimumAndPreferredSize);
        preferentialBehaviorDescription.setPreferredSize(textFieldMinimumAndPreferredSize);
        preferentialBehaviorDescription.setEditable(false);
        preferentialBehaviorDescription.setBackground(textFieldDescriptorColor);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = .5;
        constraints.gridx = 2;
        constraints.gridy = 1;
        this.add(preferentialBehaviorDescription, constraints);*/

        preferentialBehavior = new JCheckBox("Toggle Preferential Behavior", true);
        preferentialBehavior.setMinimumSize(textFieldMinimumAndPreferredSize);
        preferentialBehavior.setPreferredSize(textFieldMinimumAndPreferredSize);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = .5;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 2;
        constraints.gridy = 25;
        this.add(preferentialBehavior, constraints);

        mutationRate = new JFormattedTextField();
        mutationRate.setText("");
        mutationRate.setMinimumSize(textFieldMinimumAndPreferredSize);
        mutationRate.setPreferredSize(textFieldMinimumAndPreferredSize);
        mutationRate.setEditable(true);
        mutationRate.setBackground(textFieldColor);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = .5;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 0;
        constraints.gridy = 26;
        this.add(mutationRate, constraints);

        tournamentSize = new JFormattedTextField();
        tournamentSize.setText("");
        tournamentSize.setMinimumSize(textFieldMinimumAndPreferredSize);
        tournamentSize.setPreferredSize(textFieldMinimumAndPreferredSize);
        tournamentSize.setEditable(true);
        tournamentSize.setBackground(textFieldColor);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = .5;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 1;
        constraints.gridy = 26;
        this.add(tournamentSize, constraints);

        String crossovers[] = {"modifierCrossover", "orderCrossover", "positionBasedCrossover", "cycleCrossover"};
        crossoverBox = new JComboBox(crossovers);
        crossoverBox.setMinimumSize(buttonMinimumAndPreferredSize);
        crossoverBox.setPreferredSize(buttonMinimumAndPreferredSize);
        crossoverBox.setBackground(buttonColor);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = .5;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 2;
        constraints.gridy = 26;
        this.add(crossoverBox, constraints);


        runButton = new JButton("Run");
        runButton.setMinimumSize(buttonMinimumAndPreferredSize);
        runButton.setPreferredSize(buttonMinimumAndPreferredSize);
        runButton.setBackground(buttonColor);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = .5;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 3;
        constraints.gridy = 26;
        this.add(runButton, constraints);


        resetButton = new JButton("Reset");
        resetButton.setMinimumSize(buttonMinimumAndPreferredSize);
        resetButton.setPreferredSize(buttonMinimumAndPreferredSize);
        resetButton.setBackground(buttonColor);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = .5;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 4;
        constraints.gridy = 26;
        this.add(resetButton, constraints);

        geneticAlgorithm = new GeneticAlgorithm(this);
        selectedLocations = new HashMap<Integer, Location>();
        drawQueue = new LinkedList<Route>();
        currentBestRoute = null;

        setActionListeners();

    }


    private void setActionListeners() {
        final Display thisDisplay = this;
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mutationRateText = mutationRate.getText();
                String tournamentSizeText = tournamentSize.getText();
                boolean prefBehav = preferentialBehavior.isSelected();
                Double rate = null;
                Integer size = null;
                String displayMessage = "";

                try {
                    output.setText("");
                    rate = Double.parseDouble(mutationRateText);
                    size = Integer.parseInt(tournamentSizeText);

                    if (rate >= 0.0 && rate <= 1.0) {
                        displayMessage += "Range for mutation rate accepted.\n";
                    }else {
                        rate = DEFAULT_RATE;
                        displayMessage += "Invalid range for mutation rate. Using default value.\n";
                    }
                    if (size > 0) {
                        displayMessage += "Range for tournament size accepted.\n";
                    }else {
                        size = DEFAULT_TOURNAMENT_SIZE;
                        displayMessage += "Invalid range for tournament size. Using default value.\n";
                    }
                }catch (NumberFormatException nfe) {
                    displayMessage += "Error: value(s) could not be interpreted. Using default values for mutation " +
                            "rate and tournament size.\n";
                    rate = DEFAULT_RATE;
                    size = DEFAULT_TOURNAMENT_SIZE;
                }

                displayMessage += "Running Algorithm...\n";
                output.setText(displayMessage);

                /*
                * Clear old trial data
                * */
                drawQueue.clear();
                currentBestRoute = null;
                LocationManager.clearLocations();

                /*
                * Create new algorithm and add to the display queue accordingly, in order of generation number
                * */
                geneticAlgorithm = new GeneticAlgorithm(thisDisplay, rate, size, prefBehav);
                LocationManager.addAll(selectedLocations);
                Population pop = new Population(POPULATION_SIZE, true);
                drawQueue.add(pop.getFittest());
                //set static initial population
                GeneticAlgorithm.population = pop;
                //execute desired number of threads
                for(int i = 0; i < NUMBEROFTHREADS; i++) {
                    executor.execute(new GeneticAlgorithm(thisDisplay, rate, size, prefBehav));
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocationManager.clearLocations();
                geneticAlgorithm = null;
                selectedLocations.clear();
                drawQueue.clear();
                currentBestRoute = null;
                output.setText("Locations Reset");
                repaint();
            }
        });

        canvas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedLocations.put(selectedLocations.size(), new Location(e.getX(), e.getY()));
                System.out.println(selectedLocations.toString());
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }

    /**
     * This method is used to add new route to drawing queue
     * from GeneticAlgorithm asynchronously
     * @param route route to add to the queue
     */
    public synchronized void addToDrawQueue(Route route){
        drawQueue.add(route);
    }

    public synchronized int getGENERATIONS(){
        return GENERATIONS;
    }


    class DrawCanvas extends JPanel {

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (Location location : selectedLocations.values()) {
                location.draw(g, NEW_LOCATION_COLOR);
            }

            Route queuedRoute = drawQueue.poll();

            if (queuedRoute != null) {
                queuedRoute.draw(g, ROUTE_LOCATION_COLOR, ROUTE_EDGE_COLOR);
                output.setText(queuedRoute.toString() + "\nRoutes Remaining:\t" + drawQueue.size());
                try {
                    Thread.sleep(ROUTE_DISPLAY_TIME_MILLIS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }else if (currentBestRoute != null) {
                currentBestRoute.draw(g, ROUTE_LOCATION_COLOR, ROUTE_EDGE_COLOR);
                output.setText(currentBestRoute.toString() + "\nRoutes Remaining:\t0");
            }else {
                return;
            }

        }
    }


    public Dimension getPreferredSize() {
        return new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    public String getCrossoverType() {
        return crossoverBox.getSelectedItem().toString();
    }
}
