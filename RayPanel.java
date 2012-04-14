import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Dimension;
import javax.swing.*;

public class RayPanel extends JPanel
{
    private Universe universe;

    public RayPanel(Universe u)
    {
        universe = u;
        this.setPreferredSize(new Dimension(500,200));
    }

    @Override
    public void paintComponent(Graphics g)
    {
        for (int i=0; i<10; i++)
            {
                for (int j=0; j<10; j++)
                    {
                        if (universe.board[i][j] == 1)
                            g.setColor(Color.black);
                        else if (universe.board[i][j] == 0)
                            g.setColor(Color.white);
                        g.fillRect(10*i, 10*j, 10, 10);
                    }
            }       

    }

}