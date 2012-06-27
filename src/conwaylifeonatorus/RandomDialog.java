package conwaylifeonatorus;

import javax.swing.JSlider;
import javax.swing.JLabel;
import java.awt.Frame;

public class RandomDialog extends DimensionsDialog
{
    private JSlider densitySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 20);

    public RandomDialog(Frame owner)
    {
        super(owner);
        mainPanel.add(new JLabel("Density:"));
        mainPanel.add(densitySlider);
    }

    public double getDensity()
    {
        return (double)densitySlider.getValue()/100;
    }

}