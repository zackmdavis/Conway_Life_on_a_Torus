package conwaylifeonatorus;

import javax.swing.JSlider;
import javax.swing.JLabel;
import java.awt.Frame;
import java.awt.Dimension;

/**
 * Lets the user specify dimensions and density for a new random universe.
 */
public class RandomDialog extends DimensionsDialog
{
    private JSlider densitySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 20);

    public RandomDialog(Frame owner, Dimension dims)
    {
        super(owner, dims);
        mainPanel.add(new JLabel("Density:"));
        mainPanel.add(densitySlider);
    }

    /**
     * Returns the density of the new random universe.
     */
    public double getDensity()
    {
        return (double)densitySlider.getValue()/100;
    }

}