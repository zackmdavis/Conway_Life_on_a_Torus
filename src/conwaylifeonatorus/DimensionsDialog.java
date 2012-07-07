package conwaylifeonatorus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Lets the user specify dimensions for a new universe.
 */
public class DimensionsDialog extends JDialog implements ActionListener
{
    protected JPanel mainPanel = new JPanel();
    
    private JPanel inputPanel = new JPanel();
    private JSpinner rowsInput;
    private JSpinner colsInput;

    private JPanel buttonPanel = new JPanel();
    private JButton okay = new JButton("Okay");
    private JButton cancel = new JButton("Cancel");
    
    public int[] userDimensions = new int[2];
    public boolean userOkay = false;

    /**
     * Constructs the dialog.
     */
    public DimensionsDialog(Frame owner, Dimension dims)
    {
        super(owner, "New Universe", true);
        BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(layout);
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);
        
        rowsInput = new JSpinner(new SpinnerNumberModel(dims.height, 0, 500, 1));
        colsInput = new JSpinner(new SpinnerNumberModel(dims.width, 0, 500, 1));

        inputPanel.add(new JLabel("Enter Height: "));
        inputPanel.add(rowsInput);
        inputPanel.add(new JLabel("Enter Width: "));
        inputPanel.add(colsInput);
        
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
                userDimensions[0] = (Integer)rowsInput.getValue();
                userDimensions[1] = (Integer)colsInput.getValue();
                userOkay = true;
                setVisible(false);
            }
        else if (ae.getSource() == cancel)
            {
                setVisible(false);
            }
    } 
}
