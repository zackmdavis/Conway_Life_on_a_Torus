import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

public class LifeFrame extends JFrame implements ActionListener
{
    public int[] x_start = {0, 1, 2, 2, 2};
    public int[] y_start = {1, 2, 0, 1, 2};
    public Universe universe = new Universe(10, 25, x_start, y_start);
    public JPanel mainPanel = new JPanel();
    public UniversePanel universePanel = new UniversePanel(universe, 20);
    public RayPanel rayPanel = new RayPanel(universe, 250, 100);
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
        mainPanel.add(rayPanel);
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
                rayPanel.repaint();
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
                
    }


    
}