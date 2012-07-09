package conwaylifeonatorus;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

/**
 * a dialog for selecting display cell colors
 */
public class ColorDialog extends JDialog implements ActionListener
{
    protected JPanel mainPanel = new JPanel();

    private JPanel chooserPanel = new JPanel();
    private JTabbedPane chooserPane = new JTabbedPane();
    private JColorChooser liveChooser;
    private JColorChooser deadChooser;

    private JPanel buttonPanel = new JPanel();
    private JButton cancel = new JButton("Cancel");
    private JButton okay = new JButton("Okay");

    public boolean userOkay = false;
    public Color liveColor;
    public Color deadColor;

    /**
     * Constructs the dialog.
     */
    public ColorDialog(Frame owner, Color startingLiveColor, Color startingDeadColor)
    {
        super(owner, "Select Cell Colors", true);

        liveColor = startingLiveColor;
        deadColor = startingDeadColor;

        BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(layout);

        liveChooser = new JColorChooser(startingLiveColor);
        liveChooser.setPreviewPanel(new JPanel());
        deadChooser = new JColorChooser(startingDeadColor);
        deadChooser.setPreviewPanel(new JPanel());
        chooserPane.addTab("Live Cells", liveChooser);
        chooserPane.setMnemonicAt(0, KeyEvent.VK_1);
        chooserPane.addTab("Dead Cells", deadChooser);
        chooserPane.setMnemonicAt(1, KeyEvent.VK_2);
        chooserPanel.add(chooserPane);
        mainPanel.add(chooserPanel);

        buttonPanel.add(cancel);
        buttonPanel.add(okay);
        cancel.addActionListener(this);
        okay.addActionListener(this);
        mainPanel.add(buttonPanel);

        this.setContentPane(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getSource() == okay)
            {
                liveColor = liveChooser.getColor();
                deadColor = deadChooser.getColor();
                userOkay = true;
                setVisible(false);
            }
        else if (ae.getSource() == cancel)
            {
                setVisible(false);
            }

    }
}