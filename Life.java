import javax.swing.JFrame;

public class Life
{
    public static void main(String[] args) throws InterruptedException
    {
        LifeFrame lifeFrame = new LifeFrame();
        lifeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lifeFrame.pack();
        lifeFrame.setVisible(true);
        // OLD MAIN METHOD
        // int[] x_start = {0, 1, 2, 2, 2};
        // int[] y_start = {1, 2, 0, 1, 2};
        // Universe universe = new Universe(10, 10, x_start, y_start);
        // UniversePanel universePanel = new UniversePanel(universe, 20);
        // JFrame frame = new JFrame("Conway Life on a Torus---TEST");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.getContentPane().add(universePanel);
        // frame.pack();
        // frame.setVisible(true);
        // for (int g=0; g<1000; g++)
        //     {
        //         universe.advanceGeneration();
        //         universePanel.updatePanel();
        //         Thread.sleep(60);
        //     }

    }
}