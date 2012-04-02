import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
//import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

public class LifeFrame extends JFrame implements ActionListener
{
    private int[] x_start = {0, 1, 2, 2, 2};
    private int[] y_start = {1, 2, 0, 1, 2};
    private Universe universe = new Universe(10, 10, x_start, y_start);
    private JPanel mainPanel = new JPanel();
    private UniversePanel universePanel = new UniversePanel(universe, 20);
    private JPanel buttonPanel = new JPanel();
    private JButton step = new JButton("Step");
    // TODO---
    // private JButton go = new JButton("Go");
    // private JButton stop = new JButton("Stop");

    public LifeFrame()
    {
        FlowLayout layout = new FlowLayout();
        mainPanel.setLayout(layout);
        mainPanel.add(universePanel);
        mainPanel.add(buttonPanel);
        buttonPanel.add(step);
        // TODO---
        // buttonPanel.add(go);
        // buttonPanel.add(stop);
        this.setContentPane(mainPanel);
        step.addActionListener(this);
        step.addActionListener(this);
        step.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == step)
            {
                universe.advanceGeneration();
                universePanel.updatePanel();
            }
        //else if (e.getSource() == go)
                
    }
    
}