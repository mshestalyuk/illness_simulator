import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationGUI extends JFrame {
    private Simulation simulation;
    private JPanel simulationPanel;
    private Timer timer;
    private JLabel populationLabel;
    private JLabel infectedLabel;
    private JLabel immunedLabel;
    private JLabel timeLabel;

    public SimulationGUI() {
        simulation = new Simulation(100, 40);
        setTitle("Infection Simulation");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        simulationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawSimulation(g);
            }
        };
        simulationPanel.setPreferredSize(new Dimension(600, 600));

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSimulation();
            }
        });

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> stopSimulation());

        populationLabel = new JLabel("Population: 0");
        infectedLabel = new JLabel("Infected: 0");
        immunedLabel = new JLabel("Symptomatic: 0");
        updateLabels();

        JPanel controlPanel = new JPanel();
        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        controlPanel.add(populationLabel);
        controlPanel.add(infectedLabel);
        controlPanel.add(immunedLabel);
        

        timeLabel = new JLabel("Time: 0s");
        controlPanel.add(timeLabel);
        
        add(simulationPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }

    private int elapsedTime;

    private void startSimulation() {
        elapsedTime = 0;
        timer = new Timer(1000 / 25, e -> {
            simulation.simulateStep();
            updateLabels();
            simulationPanel.repaint();
            elapsedTime++;
            timeLabel.setText("Step: " + elapsedTime);
        });
        timer.start();
    }
    
    

    private void stopSimulation() {
        if (timer != null) {
            timer.stop();
        }
    }

    private void drawSimulation(Graphics g) {
        for (Movable m : simulation.getMovables()) {
            if (m instanceof Person) {
                Person p = (Person) m;
                int x = (int) p.getPosition().getX();
                int y = (int) p.getPosition().getY();
                Color color = getColorForPerson(p);
                g.setColor(color);
                g.fillRect(x * 10, y * 10, 10, 10);
            }
        }
    }

    private Color getColorForPerson(Person p) {
        if (p.isInfected) {
            if (p.hasSymptoms) {
                return Color.RED;
            }
            else
                return Color.ORANGE;
        } else if (p.isImmune) {
            return Color.GREEN;
        } else {
            return Color.BLUE;
        }
    }

    private void updateLabels() {
        populationLabel.setText("Population: " + simulation.getPopulationNum());
        infectedLabel.setText("Infected: " + simulation.getNumInfected());
        immunedLabel.setText("Immuned: " + simulation.getNumImmuned());
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimulationGUI());
    }
}
