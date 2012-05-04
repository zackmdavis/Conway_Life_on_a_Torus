import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Dimension;
import javax.swing.*;

public class RayPanel extends JPanel
{
    private RaySpace raySpace;

    public RayPanel(Universe u)
    {
        raySpace = new RaySpace(u);
        this.setPreferredSize(new Dimension(500,200));
    }

    @Override
    public void paintComponent(Graphics g)
    {
        SpaceVector camera = new SpaceVector(0, 0, 20);
        for (int i=0; i<100; i++)
            {
                for (int j=0; j<100; j++)
                    {
                        SpaceVector dir = new SpaceVector(0.1f*i, 0.1f*j, -1);
                        int pixel = raySpace.castRay(camera, dir);
                        switch (pixel)
                            {
                            case -1:
                                g.setColor(Color.blue);
                                break;
                            case 0:
                                g.setColor(Color.white);
                                break;
                            case 1:
                                g.setColor(Color.black);
                                break;
                            }
                        g.fillRect(i, j, 1, 1);
                        // (old placeholder code)
                        // if (universe.board[i][j] == 1)
                        //     g.setColor(Color.black);
                        // else if (universe.board[i][j] == 0)
                        //     g.setColor(Color.white);
                        // g.fillRect(10*i, 10*j, 10, 10);
                    }
            }       

    }

}