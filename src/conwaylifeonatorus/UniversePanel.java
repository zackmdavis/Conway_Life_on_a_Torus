package conwaylifeonatorus;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class DisplayCell extends JLabel
{
    public int I;
    public int J;
    public DisplayCell(int i, int j)
        {
            I = i;
            J = j;
        }
}

/**
 * the panel that displays the Universe under consideration
 */
public class UniversePanel extends JPanel
{
    private Universe universe;
    public LifeFrame parentFrame;
    public int generationCounter;
    private DisplayCell[][] displayCells;
    private Color liveColor;
    private Color deadColor;
    private boolean borders;

    /**
     * Constructs a UniversePanel
     *
     * @param u the Universe to be contained
     * @param displayCellSize the size of the display cells
     * @param lc the color of the living cells
     * @param dc the color of the dead cells
     * @param b whether to draw display cell borders
     */
    public UniversePanel(LifeFrame pf, Universe u, int displayCellSize, Color lc, Color dc, boolean b)
    {
        parentFrame = pf;
        universe = u;
        displayCells = new DisplayCell[universe.getRows()][universe.getCols()];
        liveColor = lc;
        deadColor = dc;
        borders = b;
        UniversePanelListener listener = new UniversePanelListener(); 
        Dimension size = new Dimension(displayCellSize, displayCellSize);
        setLayout(new GridLayout(displayCells.length, displayCells[0].length));
        for (int i=0; i<displayCells.length; i++)
            {
                for (int j=0; j<displayCells[0].length; j++)
                    {
                        DisplayCell displayCell = new DisplayCell(i, j);
                        displayCell.setOpaque(true);
                        if (universe.getCellState(i, j) == State.ALIVE)
                            displayCell.setBackground(lc);
                        else
                            displayCell.setBackground(dc);
                        displayCell.addMouseListener(listener);
                        displayCell.setPreferredSize(size);
                        add(displayCell);
                        displayCells[i][j] = displayCell;
                    }
            }
        if (borders)
            setBorders(true);
    }
    
    /**
     * Updates the appearance of the panel.
     */
    public void updatePanel()
    {
        for (int i=0; i<displayCells.length; i++)
            {
                for (int j=0; j<displayCells[0].length; j++)
                    {
                        if (universe.getCellState(i, j) == State.ALIVE)
                            displayCells[i][j].setBackground(liveColor);
                        else
                            displayCells[i][j].setBackground(deadColor);
                    }
            }
    }

    /**
     * Advances the simulation forward one generation.
     */
    public void advanceGeneration()
    {
        universe.advanceGeneration();
        generationCounter++;
    }

    /**
     * Queries the underlying universe for the population.
     */
    public int queryPopulation()
    {
        return universe.queryPopulation();
    }

    /**
     * Toggles the state (and appearance) of the designated cell (and display cell).
     * 
     * @param c the designated cell
     */
    public void toggleDisplayCell(DisplayCell c) 
    {
        if (universe.getCellState(c.I, c.J) == State.ALIVE)
            {
                System.out.println(c.I + " " + c.J + " OFF");
                universe.setCellState(c.I, c.J, State.DEAD);
                displayCells[c.I][c.J].setBackground(deadColor);
                parentFrame.decrementPopulationLabel();
            }
        else
            {
                System.out.println(c.I + " " + c.J + " ON");
                universe.setCellState(c.I, c.J, State.ALIVE);
                displayCells[c.I][c.J].setBackground(liveColor);
                parentFrame.incrementPopulationLabel();
            }
    }

    /**
     * Returns the underlying universe.
     */
    public Universe getUniverse()
    {
        return universe;
    }

    /**
     * Returns the dimensions of the universe.
     */
    public Dimension getDimensions()
    {
        return new Dimension(displayCells[0].length, displayCells.length);
    }

    /**
     * Returns the designated display color for living cells.
     */
    public Color getLiveColor()
    {
        return liveColor;
    }

    /**
     * Sets the designated display color for living cells.
     * 
     * @param newColor the new display color for living cells
     */
    public void setLiveColor(Color newColor)
    {
        liveColor = newColor;
        updatePanel();
    }

    /**
     * Returns the designated display color for dead cells.
     */
    public Color getDeadColor()
    {
        return deadColor;
    }

    /**
     * Sets the designated display color for dead cells.
     * 
     * @param newColor the new display color for dead cells
     */
    public void setDeadColor(Color newColor)
    {
        deadColor = newColor;
        updatePanel();
    }

    /**
     * Reports whether borders are being displayed.
     */
    public boolean getBorders()
    {
        return borders;
    }

    /**
     * Sets whether borders are to be displayed.
     *
     * @param b whether borders are to be displayed
     */
    public void setBorders(boolean b)
    {
        if (b)
            {
                for (int i=0; i<displayCells.length-1; i++)
                    {
                        for (int j=0; j<displayCells[0].length-1; j++)
                            {
                                displayCells[i][j].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.black));
                            }
                    }
                for  (int i=0; i<displayCells.length-1; i++)
                    {
                        displayCells[i][displayCells[0].length-1].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
                    }
                for  (int j=0; j<displayCells[0].length-1; j++)
                    {
                        displayCells[displayCells.length-1][j].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));
                    }
                borders = true;
            }                   
        else if (!b)
            {
                for (int i=0; i<displayCells.length; i++)
                    {
                        for (int j=0; j<displayCells[0].length; j++)
                            {
                                displayCells[i][j].setBorder(BorderFactory.createEmptyBorder());
                            }
                    }
                borders = false;
            }
    }

    private class UniversePanelListener extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent e)
        {
            toggleDisplayCell((DisplayCell)e.getSource());
        }
    }
}