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
    // RayPanel is not yet working
    //public RayPanel rayPanel = new RayPanel(universe);

    public JMenuBar menuBar;

    public JMenu fileMenu;
    JMenuItem newBlankUniverse;
    public JMenuItem RLEtoConsole;
    JMenuItem saveRLE;
    JFileChooser fileChooser;

    JMenuItem testReadRLE;

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
        //mainPanel.add(rayPanel);

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        newBlankUniverse = new JMenuItem("New Blank Universe ...");
        fileMenu.add(newBlankUniverse);
        newBlankUniverse.addActionListener(this);

        RLEtoConsole = new JMenuItem("Print RLE to Console");
        fileMenu.add(RLEtoConsole);
        RLEtoConsole.addActionListener(this);

        saveRLE = new JMenuItem("Save as RLE ...");
        fileMenu.add(saveRLE);
        saveRLE.addActionListener(this);
        fileChooser = new JFileChooser();

        testReadRLE = new JMenuItem("Test Read RLE");
        fileMenu.add(testReadRLE);
        testReadRLE.addActionListener(this);

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
        else if (e.getSource() == RLEtoConsole)
            {
                RLEEncoder encoder = new RLEEncoder(universe);
                encoder.printToConsole();
            }
        else if (e.getSource() == saveRLE)
            {
                int returnVal = fileChooser.showSaveDialog(this);
                File saveFile = saveFile = fileChooser.getSelectedFile();
                System.out.println(saveFile);
                try
                    {
                        PrintWriter RLESaver = new PrintWriter(new FileWriter(saveFile));
                        RLEEncoder encoder = new RLEEncoder(universe);
                        String RLE = encoder.getRLE();
                        System.out.println(RLE);
                        RLESaver.print(RLE);
                        RLESaver.close();
                    }
                catch(Exception exp)
                    {
                        System.out.println("An exception occurred while trying to save a file.");
                    }
            }

        else if (e.getSource() == testReadRLE)
            {
                String testRLE = "x = 22, y = 22, rule = b3/s23\n10b2o10b$10b2o10b$5bo10bo5b$4bobo8bobo4b$3bobo3bo2bo3bobo3b$2bobo4bo2b\no4bobo2b$3bo5bo2bo5bo3b3$4b3o8b3o4b$2o18b2o$2o18b2o$4b3o8b3o4b3$3bo5bo\n2bo5bo3b$2bobo4bo2bo4bobo2b$3bobo3bo2bo3bobo3b$4bobo8bobo4b$5bo10bo5b$\n10b2o10b$10b2o!";
                setUniverse(testRLE);
            }


        else if (e.getSource() == newBlankUniverse)
            {
                String userDimensionInput = JOptionPane.showInputDialog("Enter the horizontal and vertical\ndimensions (separated by a comma)\nof the new universe to be created"
);
                StringTokenizer dimensionTokenizer = new StringTokenizer(userDimensionInput, ",");
                int[] userDimensions = new int[2];
                try
                    {
                        for (int i=0; i<2; i++)
                            {
                                userDimensions[i] = Integer.parseInt(dimensionTokenizer.nextToken());
                            }
                    }
                catch (NumberFormatException nfe)
                    {
                        JOptionPane.showMessageDialog(this, "Error: can't parse dimensions");                  
                    }
                Universe blankUniverse = new Universe(userDimensions[0], userDimensions[1]);
                setUniverse(blankUniverse);
            }

    }

}