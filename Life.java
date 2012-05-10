import javax.swing.JFrame;

public class Life
{
    public static void main(String[] args) throws InterruptedException
    {
        LifeFrame lifeFrame = new LifeFrame();
        lifeFrame.setTitle("Conway Life on a Torus (in development)");
        lifeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lifeFrame.pack();
        lifeFrame.setVisible(true);
        while (true)
            {
                // The seemingly-useless print statement below seems
                // to essential for the program to function properly
                // (?!?); I notice that I am confused
                System.out.print("");
                if (lifeFrame.running)
                    {
                        lifeFrame.universe.advanceGeneration();
                        lifeFrame.universePanel.updatePanel();
                        lifeFrame.rayPanel.repaint();
                        Thread.sleep(60);
                    }
            }
    }
}