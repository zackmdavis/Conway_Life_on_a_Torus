import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Dimension;
import javax.swing.*;

public class RayPanel extends JPanel
{
    public int panelWidth;
    public int panelHeight;
    private int pixelScale = 2;
    private Universe universe;
    private RaySpace raySpace;
    private SpaceVector camera;
    private RayPixel[][] pixels;
    private SpaceVector cameraAt, cameraDirection;

    public RayPanel(Universe u, int w, int h)
    {
        universe = u;
        panelWidth = w;
        panelHeight = h;
        raySpace = new RaySpace(u);
        pixels = new RayPixel[panelHeight/pixelScale][panelWidth/pixelScale];
        camera = new SpaceVector(0f, 0f, 10f);
        initializePixels(camera);
        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
    }

    public void initializePixels(SpaceVector cameraAt)
    {
        SpaceVector direction;
        for (int i=0; i<panelHeight/pixelScale; i++)
            {
                for (int j=0; j<panelWidth/pixelScale; j++)
                    {
                        direction = new SpaceVector(-10+0.1f*i, -10+0.1f*j, -1);
                        pixels[i][j] = raySpace.castRay(camera, direction);
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
                        
                        g.fillRect(2*j, 2*i, 2, 2);
                    }
            }
    }

}