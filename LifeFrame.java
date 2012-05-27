import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.*;
import java.io.*;

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
        //mainPanel.add(rayPanel);

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        RLEtoConsole = new JMenuItem("Print RLE to Console");
        fileMenu.add(RLEtoConsole);
        RLEtoConsole.addActionListener(this);

        saveRLE = new JMenuItem("Save as RLE ...");
        fileMenu.add(saveRLE);
        saveRLE.addActionListener(this);
        fileChooser = new JFileChooser();

        setJMenuBar(menuBar);

        buttonPanel.add(step);
        buttonPanel.add(go);
        buttonPanel.add(stop);
        setContentPane(mainPanel);
        step.addActionListener(this);
        go.addActionListener(this);
        stop.addActionListener(this);
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
            }
        else if (e.getSource() == stop)
            {
                running = false;
                System.out.println("running = " + running);
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
                
    }
}