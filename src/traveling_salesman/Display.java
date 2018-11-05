package traveling_salesman;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by Michael on 11/4/2018.
 */
public class Display extends JPanel {
    private static int GENERATIONS = 200;
    private static int PANEL_WIDTH = 1200;
    private static int PANEL_HEIGHT = 1000;
    private static int CANVAS_WIDTH = 1200;
    private static int CANVAS_HEIGHT = 850;
    private static int TEXT_FIELD_WIDTH = 200;
    private static int TEXT_FIELD_HEIGHT = 50;
    private static int BUTTON_WIDTH = 200;
    private static int BUTTON_HEIGHT = 50;
    private JFormattedTextField mutationRateDescription;
    private JFormattedTextField tournamentSizeDescription;
    /*private JFormattedTextField preferentialBehaviorDescription;*/
    private JFormattedTextField mutationRate;
    private JFormattedTextField tournamentSize;
    private JCheckBox preferentialBehavior;
    private JButton runButton;
    private JButton resetButton;
    private DrawCanvas canvas;
    private GeneticAlgorithm geneticAlgorithm;


    public Display() throws IOException {
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
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(canvas, constraints);

        mutationRateDescription = new JFormattedTextField();
        mutationRateDescription.setText("Desired Mutation Rate:");
        mutationRateDescription.setMinimumSize(textFieldMinimumAndPreferredSize);
        mutationRateDescription.setPreferredSize(textFieldMinimumAndPreferredSize);
        mutationRateDescription.setEditable(false);
        mutationRateDescription.setBackground(textFieldDescriptorColor);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = .5;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        this.add(mutationRateDescription, constraints);

        tournamentSizeDescription = new JFormattedTextField();
        tournamentSizeDescription.setText("Desired Tournament Size:");
        tournamentSizeDescription.setMinimumSize(textFieldMinimumAndPreferredSize);
        tournamentSizeDescription.setPreferredSize(textFieldMinimumAndPreferredSize);
        tournamentSizeDescription.setEditable(false);
        tournamentSizeDescription.setBackground(textFieldDescriptorColor);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = .5;
        constraints.gridx = 1;
        constraints.gridy = 1;
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
        constraints.gridx = 2;
        constraints.gridy = 1;
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
        constraints.gridx = 0;
        constraints.gridy = 2;
        this.add(mutationRate, constraints);

        tournamentSize = new JFormattedTextField();
        tournamentSize.setText("");
        tournamentSize.setMinimumSize(textFieldMinimumAndPreferredSize);
        tournamentSize.setPreferredSize(textFieldMinimumAndPreferredSize);
        tournamentSize.setEditable(true);
        tournamentSize.setBackground(textFieldColor);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = .5;
        constraints.gridx = 1;
        constraints.gridy = 2;
        this.add(tournamentSize, constraints);

        runButton = new JButton("Run");
        runButton.setMinimumSize(buttonMinimumAndPreferredSize);
        runButton.setPreferredSize(buttonMinimumAndPreferredSize);
        runButton.setBackground(buttonColor);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = .5;
        constraints.gridx = 2;
        constraints.gridy = 2;
        this.add(runButton, constraints);

        resetButton = new JButton("Reset");
        resetButton.setMinimumSize(buttonMinimumAndPreferredSize);
        resetButton.setPreferredSize(buttonMinimumAndPreferredSize);
        resetButton.setBackground(buttonColor);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = .5;
        constraints.gridx = 3;
        constraints.gridy = 2;
        this.add(resetButton, constraints);

        geneticAlgorithm = new GeneticAlgorithm();

        setActionListeners();

    }


    public void setActionListeners() {


    }


    class DrawCanvas extends JPanel {

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

        }
    }


    public Dimension getPreferredSize() {
        return new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT);
    }

}
