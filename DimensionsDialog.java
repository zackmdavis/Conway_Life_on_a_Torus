import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class DimensionsDialog extends JDialog implements ActionListener
{
    private JPanel mainPanel = new JPanel();
    
    private JPanel textPanel = new JPanel();
    private JTextField xDimensionInput = new JTextField(4);
    private JTextField yDimensionInput = new JTextField(4);
    
    private JPanel buttonPanel = new JPanel();
    private JButton okay = new JButton("Okay");
    private JButton cancel = new JButton("Cancel");
    
    int[] userDimensions = new int[2];
    boolean userOkay = false;
    
    public DimensionsDialog(Frame owner)
    {
        super(owner, true);
        BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(layout);
        mainPanel.add(textPanel);
        mainPanel.add(xDimensionInput);
        mainPanel.add(yDimensionInput);
        mainPanel.add(buttonPanel);
        
        textPanel.add(new JLabel("Enter Height: "));
        textPanel.add(xDimensionInput);
        textPanel.add(new JLabel("Enter Width: "));
        textPanel.add(yDimensionInput);
        
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
                try
                    {
                        userDimensions[0] = Integer.parseInt(xDimensionInput.getText());
                        userDimensions[1] = Integer.parseInt(yDimensionInput.getText());
                        userOkay = true;
                    }
                catch (NumberFormatException nfe)
                    {
                        JOptionPane.showMessageDialog(this, "Regretfully, the program is unable to interpret your input.", "Error Parsing Dimensions", JOptionPane.ERROR_MESSAGE);
                    }
                setVisible(false);
            }
        else if (ae.getSource() == cancel)
            {
                setVisible(false);
            }
    } 
}
