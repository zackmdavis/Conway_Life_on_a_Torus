package conwaylifeonatorus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.awt.Color;

/**
 * the main window of the application
 */
public class LifeFrame extends JFrame
{
    public int tick = 66;
    public boolean running = false;

    private JPanel mainPanel = new JPanel();
    public UniversePanel universePanel;

    private JMenuBar menuBar;

    private JMenu fileMenu;

    private ActionListener fileMenuListener = new FileMenuListener();
    private JMenuItem newBlankUniverse;
    private JMenuItem newRandomUniverse;
    private JMenuItem openRLE;
    private JMenuItem saveRLE;
    private JFileChooser fileChooser = new JFileChooser();
    private JMenuItem quit;

    private ActionListener optionsMenuListener = new OptionsMenuListener();
    private JMenu optionsMenu;
    private JMenuItem chooseSpeed;
    private JCheckBoxMenuItem toggleBorders;
    private JMenuItem chooseLiveColor;
    private JMenuItem chooseDeadColor;
    private JColorChooser colorChooser;

    private ActionListener controlButtonListener = new ControlButtonListener();
    private JPanel buttonPanel = new JPanel();
    private JButton step = new JButton("Step");
    private JButton go = new JButton("Go");
    private JButton stop = new JButton("Stop");
    private JLabel generationLabel = new JLabel("0");
    private JLabel populationLabel = new JLabel("5");

    /**
     * constructs the main window
     */
    public LifeFrame()
    {
        int[] x_start = {0, 1, 2, 2, 2};
        int[] y_start = {1, 2, 0, 1, 2};
        universePanel = new UniversePanel(this, new Universe(10, 25, x_start, y_start), 20, Color.black, Color.white, false);

        BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(layout);
        mainPanel.add(universePanel);
        mainPanel.add(buttonPanel);

        menuBar = new JMenuBar();

        // TODO: make icon work within jarfile
        //this.setIconImage(new ImageIcon("titlebar_icon.png").getImage());

        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        newBlankUniverse = new JMenuItem("New Blank Universe ...", KeyEvent.VK_N);
        fileMenu.add(newBlankUniverse);
        newBlankUniverse.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newBlankUniverse.addActionListener(fileMenuListener);

        newRandomUniverse = new JMenuItem("New Random Universe ...", KeyEvent.VK_R);
        fileMenu.add(newRandomUniverse);
        newRandomUniverse.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        newRandomUniverse.addActionListener(fileMenuListener);

        openRLE = new JMenuItem("Open ...", KeyEvent.VK_O);
        openRLE.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        fileMenu.add(openRLE);
        openRLE.addActionListener(fileMenuListener);

        saveRLE = new JMenuItem("Save ...", KeyEvent.VK_S);
        saveRLE.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        fileMenu.add(saveRLE);
        saveRLE.addActionListener(fileMenuListener);

        quit = new JMenuItem("Quit", KeyEvent.VK_Q);
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        fileMenu.add(quit);
        quit.addActionListener(fileMenuListener);

        optionsMenu = new JMenu("Options");
        optionsMenu.setMnemonic(KeyEvent.VK_T);
        menuBar.add(optionsMenu);

        colorChooser = new JColorChooser();

        toggleBorders = new JCheckBoxMenuItem("Toggle Cell Borders");
        toggleBorders.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        optionsMenu.add(toggleBorders);
        toggleBorders.addActionListener(optionsMenuListener);

        chooseSpeed = new JMenuItem("Choose Speed ...", KeyEvent.VK_P);
        chooseSpeed.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        optionsMenu.add(chooseSpeed);
        chooseSpeed.addActionListener(optionsMenuListener);

        chooseLiveColor = new JMenuItem("Choose Live Cell Color ...", KeyEvent.VK_L);
        chooseLiveColor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        optionsMenu.add(chooseLiveColor);
        chooseLiveColor.addActionListener(optionsMenuListener);

        chooseDeadColor = new JMenuItem("Choose Dead Cell Color ...", KeyEvent.VK_D);
        chooseDeadColor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        optionsMenu.add(chooseDeadColor);
        chooseDeadColor.addActionListener(optionsMenuListener);

        setJMenuBar(menuBar);

        populationLabel.setForeground(Color.BLUE);
        buttonPanel.add(populationLabel);
        buttonPanel.add(step);
        buttonPanel.add(go);
        buttonPanel.add(stop);
        buttonPanel.add(generationLabel);
        go.setBackground(new Color(125, 240, 160));
        stop.setBackground(new Color(250, 150, 150));
        stop.setEnabled(false);
        setContentPane(mainPanel);
        step.addActionListener(controlButtonListener);
        go.addActionListener(controlButtonListener);
        stop.addActionListener(controlButtonListener);
    }

    /**
     * Sets the universe.
     *
     * @param u the universe 
     * @param liveColor the color of the living cells
     * @param deadColor the color of the dead cells
     * @param borders whether to paint borders on the display cells
     */
    private void setUniverse(Universe u, Color liveColor, Color deadColor, boolean borders)
    {
        Universe universe = u;
        mainPanel.remove(universePanel);
        mainPanel.remove(buttonPanel);
        universePanel = new UniversePanel(this, universe, 20, liveColor, deadColor, borders);
        mainPanel.add(universePanel);
        mainPanel.add(buttonPanel);
        generationLabel.setText("0");
        updatePopulationLabel();
        this.pack();
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    public void updateGenerationLabel()
    {
        generationLabel.setText(Integer.toString(universePanel.generationCounter));
    }

    public void updatePopulationLabel()
    {
        populationLabel.setText(Integer.toString(universePanel.queryPopulation()));
    }

    public void incrementPopulationLabel()
    {
        int population = Integer.parseInt(populationLabel.getText());
        population++;
        populationLabel.setText(Integer.toString(population));
    }

    public void decrementPopulationLabel()
    {
        int population = Integer.parseInt(populationLabel.getText());
        population--;
        populationLabel.setText(Integer.toString(population));        
    }

    private class ControlButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            if (ae.getSource() == step)
                {
                    universePanel.advanceGeneration();
                    universePanel.updatePanel();
                    updateGenerationLabel();
                    updatePopulationLabel();
                    System.out.println("step");
                }
            else if (ae.getSource() == go)
                {
                    running = true;
                    System.out.println("running = " + running);
                    go.setEnabled(false);
                    stop.setEnabled(true);
                }
            else if (ae.getSource() == stop)
                {
                    running = false;
                    System.out.println("running = " + running);
                    stop.setEnabled(false);
                    go.setEnabled(true);
                }
        }
    }

    private class FileMenuListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            if (ae.getSource() == newBlankUniverse)
                {
                    DimensionsDialog dimensionsDialog = new DimensionsDialog(LifeFrame.this);
                    dimensionsDialog.pack();
                    dimensionsDialog.setVisible(true);
                    if (dimensionsDialog.userOkay)
                        {
                            Universe blankUniverse = new Universe(dimensionsDialog.userDimensions[0], dimensionsDialog.userDimensions[1]);
                            setUniverse(blankUniverse, universePanel.getLiveColor(), universePanel.getDeadColor(), universePanel.getBorders());
                        }
                }
            else if (ae.getSource() == newRandomUniverse)
                {
                    RandomDialog randomDialog = new RandomDialog(LifeFrame.this);
                    randomDialog.pack();
                    randomDialog.setVisible(true);
                    if (randomDialog.userOkay)
                        {
                            Universe randomUniverse = new Universe(randomDialog.userDimensions[0], randomDialog.userDimensions[1],randomDialog.getDensity());
                            setUniverse(randomUniverse, universePanel.getLiveColor(), universePanel.getDeadColor(), universePanel.getBorders());
                        }
                }
            else if (ae.getSource() == openRLE)
                {
                    int returnVal = fileChooser.showOpenDialog(LifeFrame.this);
                    if (returnVal == JFileChooser.APPROVE_OPTION)
                        {
                            File openFile = fileChooser.getSelectedFile();
                            System.out.println(openFile);
                            try
                                {
                                    String RLE = new Scanner(openFile).useDelimiter("\\Z").next();
                                    RLEDecoder decoder = new RLEDecoder(RLE);
                                    setUniverse(decoder.toUniverse(), universePanel.getLiveColor(), universePanel.getDeadColor(), universePanel.getBorders());
                                }
                            catch (FileNotFoundException FNFexp)
                                {
                                    JOptionPane.showMessageDialog(LifeFrame.this, "File Not Found Error", "File Not Found Error", JOptionPane.ERROR_MESSAGE);
                                }
                            catch (RLEDecodingBoundsException RLEDBexp)
                                {
                                    JOptionPane.showMessageDialog(LifeFrame.this, RLEDBexp.getMessage()+"\nThe problematic index was: "+RLEDBexp.getCause().getMessage(), "RLE Decoding Error", JOptionPane.ERROR_MESSAGE);
                                }
                            // TODO: Just "Exception"?---be more specific!
                            catch (Exception exp)
                                {
                                    JOptionPane.showMessageDialog(LifeFrame.this, "There was an error:\n" + exp.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);   
                                }
                        }
                }
            else if (ae.getSource() == saveRLE)
                {
                    int returnVal = fileChooser.showSaveDialog(LifeFrame.this);
                    File saveFile = fileChooser.getSelectedFile();
                    System.out.println(saveFile);
                    try
                        {
                            PrintWriter RLESaver = new PrintWriter(new FileWriter(saveFile));
                            RLEEncoder encoder = new RLEEncoder(universePanel.getUniverse());
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
            else if (ae.getSource() == quit)
                {
                    System.exit(0);
                }   
        }
    }

    private class OptionsMenuListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {        
            if (ae.getSource() == chooseSpeed)
                {
                    SpeedDialog speedDialog = new SpeedDialog(LifeFrame.this, tick);
                    speedDialog.pack();
                    speedDialog.setVisible(true);
                    if (speedDialog.userOkay)
                        {
                            tick = speedDialog.tick;
                        }
                }
            else if (ae.getSource() == toggleBorders)
                {
                    if (toggleBorders.getState())
                        universePanel.setBorders(true);
                    else
                        universePanel.setBorders(false);
                }
            else if (ae.getSource() == chooseLiveColor)
                {
                    Color newColor = colorChooser.showDialog(LifeFrame.this, "Live Cell Color", universePanel.getLiveColor());
                    if (newColor != null)
                        universePanel.setLiveColor(newColor);
                }
            else if (ae.getSource() == chooseDeadColor)
                {
                    Color newColor = colorChooser.showDialog(LifeFrame.this, "Dead Cell Color", universePanel.getDeadColor());
                    if (newColor != null)
                        universePanel.setDeadColor(newColor);
                }
        }
    }
}