import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;

public class LifeFrame extends JFrame implements ActionListener
{
    public int[] x_start = {0, 1, 2, 2, 2};
    public int[] y_start = {1, 2, 0, 1, 2};
    public Universe universe = new Universe(10, 25, x_start, y_start);
    public JPanel mainPanel = new JPanel();
    public UniversePanel universePanel = new UniversePanel(universe, 20);
    // RayPanel is not yet working
    //public RayPanel rayPanel = new RayPanel(universe);
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
                
    }


    
}