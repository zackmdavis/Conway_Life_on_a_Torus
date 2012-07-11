package conwaylifeonatorus;

import javax.swing.*;
import java.net.URL;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DocFrame extends JFrame implements ActionListener
{
    JPanel mainPanel = new JPanel();
    JTextPane docPane = new JTextPane();
    JButton userOkay = new JButton("Okay");

    public DocFrame()
    {
        super("Documentation");
        BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(layout);
        try
            {
                URL docURL = this.getClass().getResource("resources/documentation.html");
                docPane.setPage(docURL);
            }
        catch (IOException ioe)
            {
                System.out.println("ERROR opening documentation");
            }
        docPane.setEditable(false);
        mainPanel.add(docPane);
        userOkay.addActionListener(this);
        mainPanel.add(userOkay);
        this.setPreferredSize(new Dimension(300, 500));
        this.setContentPane(mainPanel);
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        this.dispose();
    }


}