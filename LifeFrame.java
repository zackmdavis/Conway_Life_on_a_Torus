import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;

public class LifeFrame extends JFrame implements ActionListener
{
    public int[] x_start = {0, 1, 2, 2, 2};
    public int[] y_start = {1, 2, 0, 1, 2};
    public Universe universe = new Universe(10, 25, x_start, y_start);
    public JPanel mainPanel = new JPanel();
    public UniversePanel universePanel = new UniversePanel(universe, 20);

    public JMenuBar menuBar;

    public JMenu fileMenu;
    public JMenuItem newBlankUniverse;
    public JMenuItem RLEtoConsole;
    JMenuItem saveRLE;
    JFileChooser fileChooser;

    public JPanel buttonPanel = new JPanel();
    public JButton step = new JButton("Step");
    public JButton go = new JButton("Go");
    public JButton stop = new JButton("Stop");
    public boolean running = false;

    public LifeFrame()
    {
        BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(layout);
        mainPanel.add(universePanel);
        mainPanel.add(buttonPanel);

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        newBlankUniverse = new JMenuItem("New Blank Universe ...");
        fileMenu.add(newBlankUniverse);
        newBlankUniverse.addActionListener(this);

        setJMenuBar(menuBar);

        buttonPanel.add(step);
        buttonPanel.add(go);
        buttonPanel.add(stop);
        go.setBackground(new Color(125, 240, 160));
        stop.setBackground(new Color(250, 150, 150));
        stop.setEnabled(false);
        setContentPane(mainPanel);
        step.addActionListener(this);
        go.addActionListener(this);
        stop.addActionListener(this);
    }

    private void setUniverse(String specification)
    {
        RLEDecoder decoder = new RLEDecoder(specification);
        universe = decoder.toUniverse();
        mainPanel.remove(universePanel);
        mainPanel.remove(buttonPanel);
        universePanel = new UniversePanel(universe, 20);
        mainPanel.add(universePanel);
        mainPanel.add(buttonPanel);
        this.pack();
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    private void setUniverse(Universe u)
    {
        universe = u;
        mainPanel.remove(universePanel);
        mainPanel.remove(buttonPanel);
        universePanel = new UniversePanel(universe, 20);
        mainPanel.add(universePanel);
        mainPanel.add(buttonPanel);
        this.pack();
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == step)
            {
                universe.advanceGeneration();
                universePanel.updatePanel();
                System.out.println("step");
            }
        else if (e.getSource() == go)
            {
                running = true;
                System.out.println("running = " + running);
                go.setEnabled(false);
                stop.setEnabled(true);
            }
        else if (e.getSource() == stop)
            {
                running = false;
                System.out.println("running = " + running);
                stop.setEnabled(false);
                go.setEnabled(true);
            }
        else if (e.getSource() == newBlankUniverse)
            {
                DimensionsDialog dimensionsDialog = new DimensionsDialog(this);
                dimensionsDialog.pack();
                dimensionsDialog.setVisible(true);
                if (dimensionsDialog.userOkay)
                    {
                        Universe blankUniverse = new Universe(dimensionsDialog.userDimensions[0], dimensionsDialog.userDimensions[1]);
                        setUniverse(blankUniverse);
                    }
            }
    }
}