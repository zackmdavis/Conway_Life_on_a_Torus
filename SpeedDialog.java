import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SpeedDialog extends JDialog implements ActionListener
{
    protected JPanel mainPanel = new JPanel();

    private JLabel label = new JLabel("Speed (generations per second)");

    public int tick;

    private JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 1, 50, 15);
    
    private JPanel buttonPanel = new JPanel();
    private JButton okay = new JButton("Okay");
    private JButton cancel = new JButton("Cancel");
    
    boolean userOkay = false;
    
    public SpeedDialog(Frame owner, int currentTick)
    {
        super(owner, "Change Speed", true);
        BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(layout);
        mainPanel.add(label);

        speedSlider.setValue((int)((double)1000/currentTick));
        speedSlider.setPaintLabels(true);
        speedSlider.setLabelTable(speedSlider.createStandardLabels(10, 5));
        mainPanel.add(speedSlider);

        mainPanel.add(buttonPanel);
                
        buttonPanel.add(cancel);
        buttonPanel.add(okay);
        cancel.addActionListener(this);
        okay.addActionListener(this);
        
        this.setContentPane(mainPanel);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getSource() == okay)
            {
                tick = (int)((double)1000/speedSlider.getValue());
                System.out.println("ms per frame " + tick);
                userOkay = true;
                setVisible(false);
            }
        else if (ae.getSource() == cancel)
            {
                setVisible(false);
            }
    } 
}
