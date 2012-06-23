import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.awt.Color;

/**
 * the main window of the application
 */
public class LifeFrame extends JFrame implements ActionListener
{
    public int tick = 66;
    public boolean running = false;

    public JPanel mainPanel = new JPanel();
    public UniversePanel universePanel;

    public JMenuBar menuBar;

    public JMenu fileMenu;

    public JMenuItem newBlankUniverse;
    public JMenuItem newRandomUniverse;

    public JMenuItem openRLE;
    public JMenuItem saveRLE;
    public JFileChooser fileChooser = new JFileChooser();

    public JMenuItem quit;

    public JMenu optionsMenu;
    public JMenuItem chooseSpeed;
    public JMenuItem chooseLiveColor;
    public JMenuItem chooseDeadColor;
    public JColorChooser colorChooser;

    public JPanel buttonPanel = new JPanel();
    public JButton step = new JButton("Step");
    public JButton go = new JButton("Go");
    public JButton stop = new JButton("Stop");
    public JLabel generationLabel = new JLabel("0");

    /**
     * constructs the main window
     */
    public LifeFrame()
    {
        int[] x_start = {0, 1, 2, 2, 2};
        int[] y_start = {1, 2, 0, 1, 2};
        universePanel= new UniversePanel(new Universe(10, 25, x_start, y_start), 20, Color.black, Color.white);

        BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(layout);
        mainPanel.add(universePanel);
        mainPanel.add(buttonPanel);

        menuBar = new JMenuBar();

        this.setIconImage(new ImageIcon("titlebar_icon.png").getImage());

        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        newBlankUniverse = new JMenuItem("New Blank Universe ...", KeyEvent.VK_N);
        fileMenu.add(newBlankUniverse);
        newBlankUniverse.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newBlankUniverse.addActionListener(this);

        newRandomUniverse = new JMenuItem("New Random Universe ...", KeyEvent.VK_R);
        fileMenu.add(newRandomUniverse);
        newRandomUniverse.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        newRandomUniverse.addActionListener(this);

        openRLE = new JMenuItem("Open ...", KeyEvent.VK_O);
        openRLE.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        fileMenu.add(openRLE);
        openRLE.addActionListener(this);

        saveRLE = new JMenuItem("Save ...", KeyEvent.VK_S);
        saveRLE.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        fileMenu.add(saveRLE);
        saveRLE.addActionListener(this);

        quit = new JMenuItem("Quit", KeyEvent.VK_Q);
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        fileMenu.add(quit);
        quit.addActionListener(this);

        optionsMenu = new JMenu("Options");
        optionsMenu.setMnemonic(KeyEvent.VK_T);
        menuBar.add(optionsMenu);

        colorChooser = new JColorChooser();

        chooseSpeed = new JMenuItem("Choose Speed ...", KeyEvent.VK_P);
        chooseSpeed.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        optionsMenu.add(chooseSpeed);
        chooseSpeed.addActionListener(this);

        chooseLiveColor = new JMenuItem("Choose Live Cell Color ...", KeyEvent.VK_L);
        chooseLiveColor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        optionsMenu.add(chooseLiveColor);
        chooseLiveColor.addActionListener(this);

        chooseDeadColor = new JMenuItem("Choose Dead Cell Color ...", KeyEvent.VK_D);
        chooseDeadColor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        optionsMenu.add(chooseDeadColor);
        chooseDeadColor.addActionListener(this);

        setJMenuBar(menuBar);

        buttonPanel.add(step);
        buttonPanel.add(go);
        buttonPanel.add(stop);
        buttonPanel.add(generationLabel);
        go.setBackground(new Color(125, 240, 160));
        stop.setBackground(new Color(250, 150, 150));
        stop.setEnabled(false);
        setContentPane(mainPanel);
        step.addActionListener(this);
        go.addActionListener(this);
        stop.addActionListener(this);
    }

    /**
     * Sets the universe.
     *
     * @param u the universe 
     * @param liveColor the color of the living cells
     * @param deadColor the color of the dead cells
     */
    private void setUniverse(Universe u, Color liveColor, Color deadColor)
    {
        Universe universe = u;
        mainPanel.remove(universePanel);
        mainPanel.remove(buttonPanel);
        universePanel = new UniversePanel(universe, 20, liveColor, deadColor);
        mainPanel.add(universePanel);
        mainPanel.add(buttonPanel);
        generationLabel.setText("0");
        this.pack();
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    public void updateGenerationLabel()
    {
        generationLabel.setText(Integer.toString(universePanel.generationCounter));
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == step)
            {
                universePanel.advanceGeneration();
                universePanel.updatePanel();
                updateGenerationLabel();
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
                        setUniverse(blankUniverse, universePanel.getLiveColor(), universePanel.getDeadColor());
                    }
            }
        else if (e.getSource() == newRandomUniverse)
            {
                RandomDialog randomDialog = new RandomDialog(this);
                randomDialog.pack();
                randomDialog.setVisible(true);
                if (randomDialog.userOkay)
                    {
                        Universe randomUniverse = new Universe(randomDialog.userDimensions[0], randomDialog.userDimensions[1],randomDialog.getDensity());
                        setUniverse(randomUniverse, universePanel.getLiveColor(), universePanel.getDeadColor());
                    }
            }
        else if (e.getSource() == openRLE)
            {
                int returnVal = fileChooser.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                    {
                        File openFile = fileChooser.getSelectedFile();
                        System.out.println(openFile);
                        try
                            {
                                String RLE = new Scanner(openFile).useDelimiter("\\Z").next();
                                RLEDecoder decoder = new RLEDecoder(RLE);
                                setUniverse(decoder.toUniverse(), universePanel.getLiveColor(), universePanel.getDeadColor());
                            }
                        catch (FileNotFoundException FNFexp)
                            {
                                JOptionPane.showMessageDialog(this, "File Not Found Error", "File Not Found Error", JOptionPane.ERROR_MESSAGE);
                            }
                        catch (RLEDecodingBoundsException RLEDBexp)
                            {
                                JOptionPane.showMessageDialog(this, RLEDBexp.getMessage()+"\nThe problematic index was: "+RLEDBexp.getCause().getMessage(), "RLE Decoding Error", JOptionPane.ERROR_MESSAGE);
                            }
                        // TODO: Just "Exception"?---be more specific!
                        catch (Exception exp)
                            {
                                JOptionPane.showMessageDialog(this, "There was an error:\n" + exp.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);   
                            }
                    }
            }
        else if (e.getSource() == saveRLE)
            {
                int returnVal = fileChooser.showSaveDialog(this);
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
        else if (e.getSource() == quit)
            {
                System.exit(0);
            }
        else if (e.getSource() == chooseSpeed)
            {
                SpeedDialog speedDialog = new SpeedDialog(this, tick);
                speedDialog.pack();
                speedDialog.setVisible(true);
                if (speedDialog.userOkay)
                    {
                        tick = speedDialog.tick;
                    }
            }
        else if (e.getSource() == chooseLiveColor)
            {
                Color newColor = colorChooser.showDialog(this, "Live Cell Color", universePanel.getLiveColor());
                if (newColor != null)
                    universePanel.setLiveColor(newColor);
            }
        else if (e.getSource() == chooseDeadColor)
            {
                Color newColor = colorChooser.showDialog(this, "Dead Cell Color", universePanel.getDeadColor());
                if (newColor != null)
                    universePanel.setDeadColor(newColor);
            }
    }
}