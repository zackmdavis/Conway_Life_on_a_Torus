import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Dimension;
import javax.swing.*;

public class RayPanel extends JPanel
{
    private Universe universe;
    private RaySpace raySpace;
    private RayPixel[][] pixels;
    private SpaceVector cameraAt, cameraDirection;

    public RayPanel(Universe u)
    {
        universe = u;
        raySpace = new RaySpace(u);
        pixels = new RayPixel[u.rows][u.cols];
        // TESTING
        SpaceVector test1 = new SpaceVector(0f, 0f, 0f);
        SpaceVector test2 = new SpaceVector(0f, 0f, 0f);
        initializePixels(test1, test2);
        this.setPreferredSize(new Dimension(500,200));
    }

    public void initializePixels(SpaceVector cameraAt, SpaceVector cameraDirection)
    {
        for (int i=0; i<universe.rows; i++)
            {
                for (int j=0; j<universe.cols; j++)
                    {
                        pixels[i][j] = raySpace.castRay(cameraAt, cameraDirection);
                    }
            }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        RayPixel p;
        for (int i=0; i<pixels.length; i++)
            {
                for (int j=0; j<pixels[0].length; j++)
                    {
                        p = pixels[i][j];
                        if (!p.inUniverse)
                            g.setColor(Color.blue);
                        else if (universe.board[p.cellIndex[0]][p.cellIndex[0]] == 0)
                            g.setColor(Color.white);
                        else if (universe.board[p.cellIndex[0]][p.cellIndex[0]] == 1)
                            g.setColor(Color.black);
                        
                        g.fillRect(5*j, 5*i, 5, 5);
                    }
            }
    }

}