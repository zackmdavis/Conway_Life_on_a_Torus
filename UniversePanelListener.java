import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class UniversePanelListener extends MouseAdapter
{
    private UniversePanel universePanel;

    public UniversePanelListener(UniversePanel u)
    {
        universePanel = u;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        universePanel.toggleCell((JLabel)e.getSource());
    }
}
