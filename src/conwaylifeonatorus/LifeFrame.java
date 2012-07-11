package conwaylifeonatorus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.net.URL;

/**
 * the main window of the application
 *
 * @author Zack M. Davis
 */
public class LifeFrame extends JFrame
{
    /**
     * the clock speed of the simulation
     */
    public int tick = 66;

    /**
     * whether the simulation is running right now
     */
    public boolean running = false;

    /**
     * the underlying universe panel
     */
    public UniversePanel universePanel;

    private JPanel mainPanel = new JPanel();

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
    private JCheckBoxMenuItem toggleBorders;
    private JCheckBoxMenuItem togglePopulationCounter;
    private JCheckBoxMenuItem toggleGenerationCounter;
    private JMenuItem selectSpeed;
    private JMenuItem selectColors;

    private StepAction stepAction = new StepAction("Step");
    private GoAction goAction = new GoAction("Go");
    private StopAction stopAction = new StopAction("Stop");
    private JPanel buttonPanel = new JPanel();
    private JButton step = new JButton(stepAction);
    private JButton go = new JButton(goAction);
    private JButton stop = new JButton(stopAction);
    private JLabel populationLabel = new JLabel("5");
    private JLabel generationLabel = new JLabel("0");

    /**
     * Constructs the main window.
     */
    public LifeFrame()
    {
        int[] x_start = {0, 1, 2, 2, 2};
        int[] y_start = {1, 2, 0, 1, 2};
        universePanel = new UniversePanel(this, new ArrayUniverse(10, 25, x_start, y_start), 20, Color.black, Color.white, false);

        BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(layout);
        mainPanel.add(universePanel);
        mainPanel.add(buttonPanel);

        menuBar = new JMenuBar();

        URL titlebarIconURL = this.getClass().getResource("resources/titlebar_icon.png");
        this.setIconImage(new ImageIcon(titlebarIconURL).getImage());

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

        toggleBorders = new JCheckBoxMenuItem("Toggle Cell Borders");
        toggleBorders.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        toggleBorders.setMnemonic(KeyEvent.VK_B);
        optionsMenu.add(toggleBorders);
        toggleBorders.addActionListener(optionsMenuListener);

        togglePopulationCounter = new JCheckBoxMenuItem("Toggle Population Counter");
        togglePopulationCounter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        togglePopulationCounter.setMnemonic(KeyEvent.VK_P);
        togglePopulationCounter.setState(true);
        optionsMenu.add(togglePopulationCounter);
        togglePopulationCounter.addActionListener(optionsMenuListener);

        toggleGenerationCounter = new JCheckBoxMenuItem("Toggle Generation Counter");
        toggleGenerationCounter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        toggleGenerationCounter.setMnemonic(KeyEvent.VK_G);
        toggleGenerationCounter.setDisplayedMnemonicIndex(7);
        toggleGenerationCounter.setState(true);
        optionsMenu.add(toggleGenerationCounter);
        toggleGenerationCounter.addActionListener(optionsMenuListener);

        selectSpeed = new JMenuItem("Adjust Speed ...");
        selectSpeed.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        optionsMenu.add(selectSpeed);
        selectSpeed.addActionListener(optionsMenuListener);

        selectColors = new JMenuItem("Select Colors ...", KeyEvent.VK_C);
        selectColors.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        optionsMenu.add(selectColors);
        selectColors.addActionListener(optionsMenuListener);

        setJMenuBar(menuBar);

        populationLabel.setForeground(Color.BLUE);
        buttonPanel.add(populationLabel);
        buttonPanel.add(step);
        buttonPanel.add(go);
        buttonPanel.add(stop);
        buttonPanel.add(generationLabel);
        go.setBackground(new Color(125, 240, 160));
        stop.setBackground(new Color(250, 150, 150));
        Font serifBold = new Font("Serif", Font.BOLD, 14);
        step.setFont(serifBold);
        go.setFont(serifBold);
        stop.setFont(serifBold);

        step.getInputMap().put(KeyStroke.getKeyStroke("UP"), "step");
        step.getActionMap().put("step", stepAction);
        go.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "go");
        go.getActionMap().put("go", goAction);
        stop.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "stop");
        stop.getActionMap().put("stop", stopAction);

        setContentPane(mainPanel);
    }

    private static int computeDisplayCellSize(Dimension dims)
    {
        int maxWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width - 130;
        int maxHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height - 130;
        int fullCols = maxWidth/dims.width;
        int fullRows = maxHeight/dims.height;
        int fullSide = (fullCols < fullRows) ? fullCols : fullRows;
        return (fullSide < 20) ? fullSide : 20; 
    }

    /**
     * Sets the universe.
     *
     * @param u the universe 
     * @param displayCellSize the size of the display cells
     * @param liveColor the color of the living cells
     * @param deadColor the color of the dead cells
     * @param borders whether to paint borders on the display cells
     */
    private void setUniverse(Universe u, Color liveColor, Color deadColor, boolean borders)
    {
        Universe universe = u;
        mainPanel.remove(universePanel);
        mainPanel.remove(buttonPanel);
        universePanel = new UniversePanel(this, universe, computeDisplayCellSize(new Dimension(u.getCols(), u.getRows())), liveColor, deadColor, borders);
        mainPanel.add(universePanel);
        mainPanel.add(buttonPanel);
        generationLabel.setText("0");
        updatePopulationLabel();
        this.pack();
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    /**
     * Updates the generation label.
     */
    public void updateGenerationLabel()
    {
        generationLabel.setText(Integer.toString(universePanel.generationCounter));
    }

    /**
     * Updates the population label.
     */
    public void updatePopulationLabel()
    {
        populationLabel.setText(Integer.toString(universePanel.queryPopulation()));
    }

    /**
     * Increments the population label by one.
     */
    public void incrementPopulationLabel()
    {
        int population = Integer.parseInt(populationLabel.getText());
        population++;
        populationLabel.setText(Integer.toString(population));
    }

    /**
     * Decrements the population label by one.
     */
    public void decrementPopulationLabel()
    {
        int population = Integer.parseInt(populationLabel.getText());
        population--;
        populationLabel.setText(Integer.toString(population));        
    }

    private class StepAction extends AbstractAction
    {
        StepAction(String text)
        {
            super(text);
        }

        public void actionPerformed(ActionEvent ae)
        {
            universePanel.advanceGeneration();
            universePanel.updatePanel();
            updateGenerationLabel();
            updatePopulationLabel();
        }

    }

    private class GoAction extends AbstractAction
    {
        GoAction(String text)
        {
            super(text);
        }

        public void actionPerformed(ActionEvent ae)
        {
            running = true;
            stopAction.setEnabled(true);
            this.setEnabled(false);
        }
    }

    private class StopAction extends AbstractAction
    {
        StopAction(String text)
        {
            super(text);
            this.setEnabled(false);
        }

        public void actionPerformed(ActionEvent ae)
        {
            running = false;
            goAction.setEnabled(true);
            this.setEnabled(false);
        }
    }

    private class FileMenuListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            if (ae.getSource() == newBlankUniverse)
                {
                    DimensionsDialog dimensionsDialog = new DimensionsDialog(LifeFrame.this, universePanel.getDimensions());
                    dimensionsDialog.pack();
                    dimensionsDialog.setVisible(true);
                    if (dimensionsDialog.userOkay)
                        {
                            Universe blankUniverse = new ArrayUniverse(dimensionsDialog.userDimensions[0], dimensionsDialog.userDimensions[1]);
                            setUniverse(blankUniverse, universePanel.getLiveColor(), universePanel.getDeadColor(), universePanel.getBorders());
                        }
                }
            else if (ae.getSource() == newRandomUniverse)
                {
                    RandomDialog randomDialog = new RandomDialog(LifeFrame.this, universePanel.getDimensions());
                    randomDialog.pack();
                    randomDialog.setVisible(true);
                    if (randomDialog.userOkay)
                        {
                            Universe randomUniverse = new ArrayUniverse(randomDialog.userDimensions[0], randomDialog.userDimensions[1],randomDialog.getDensity());
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
            if (ae.getSource() == toggleBorders)
                {
                    if (toggleBorders.getState())
                        universePanel.setBorders(true);
                    else
                        universePanel.setBorders(false);
                }
            else if (ae.getSource() == togglePopulationCounter)
                {
                    if (togglePopulationCounter.getState())
                        populationLabel.setVisible(true);
                    else
                        populationLabel.setVisible(false);
                }
            else if (ae.getSource() == toggleGenerationCounter)
                {
                    if (toggleGenerationCounter.getState())
                        generationLabel.setVisible(true);
                    else
                        generationLabel.setVisible(false);
                }
            else if (ae.getSource() == selectSpeed)
                {
                    SpeedDialog speedDialog = new SpeedDialog(LifeFrame.this, tick);
                    speedDialog.pack();
                    speedDialog.setVisible(true);
                    if (speedDialog.userOkay)
                        {
                            tick = speedDialog.tick;
                        }
                }
            else if (ae.getSource() == selectColors)
                {
                    ColorDialog colorDialog = new ColorDialog(LifeFrame.this, universePanel.getLiveColor(), universePanel.getDeadColor());
                    colorDialog.pack();
                    colorDialog.setVisible(true);
                    if (colorDialog.userOkay)
                        {
                            universePanel.setLiveColor(colorDialog.liveColor);
                            universePanel.setDeadColor(colorDialog.deadColor);
                        }
                }
        }
    }
}