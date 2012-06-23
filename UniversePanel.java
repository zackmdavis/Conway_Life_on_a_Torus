import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;

/**
 * the panel that displays the Universe under consideration
 */
public class UniversePanel extends JPanel
{
    public Universe universe;
    public int generationCounter;
    private Cell[][] cells;
    private Color liveColor;
    private Color deadColor;

    /**
     * Constructs a UniversePanel
     *
     * @param u the Universe to be contained
     * @param cellSize the size of the display cells
     * @param lc the color of the living cells
     * @param dc the color of the dead cells
     */
    public UniversePanel(Universe u, int cellSize, Color lc, Color dc)
    {
        universe = u;
        cells = new Cell[universe.rows][universe.cols];
        liveColor = lc;
        deadColor = dc;
        UniversePanelListener listener = new UniversePanelListener(this); 
        Dimension size = new Dimension(cellSize, cellSize);
        setLayout(new GridLayout(cells.length, cells[0].length));
        for (int i=0; i<cells.length; i++)
            {
                for (int j=0; j<cells[0].length; j++)
                    {
                        Cell cell = new Cell(i, j);
                        cell.setOpaque(true);
                        if (universe.board[i][j] == Universe.State.ALIVE)
                            cell.setBackground(lc);
                        else
                            cell.setBackground(dc);
                        cell.addMouseListener(listener);
                        cell.setPreferredSize(size);
                        add(cell);
                        cells[i][j] = cell;
                    }
            }
    }
    
    /**
     * Updates the appearance of the panel.
     */
    public void updatePanel()
    {
        for (int i=0; i<cells.length; i++)
            {
                for (int j=0; j<cells[0].length; j++)
                    {
                        if (universe.board[i][j] == Universe.State.ALIVE)
                            cells[i][j].setBackground(liveColor);
                        else
                            cells[i][j].setBackground(deadColor);
                    }
            }
    }

    public void advanceGeneration()
    {
        universe.advanceGeneration();
        generationCounter++;
    }

    /**
     * Toggles the state (and appearance) of the designated cell (and display cell) 
     * 
     * @param c the designated cell
     */
    public void toggleCell(Cell c) 
    {
        if (universe.board[c.I][c.J] == Universe.State.ALIVE)
            {
                System.out.println(c.I + " " + c.J + " OFF");
                universe.board[c.I][c.J] = Universe.State.DEAD;
                cells[c.I][c.J].setBackground(deadColor);
            }
        else
            {
                System.out.println(c.I + " " + c.J + " ON");
                universe.board[c.I][c.J] = Universe.State.ALIVE;
                cells[c.I][c.J].setBackground(liveColor);
            }
    }

    public Universe getUniverse()
    {
        return universe;
    }

    public Color getLiveColor()
    {
        return liveColor;
    }

    public void setLiveColor(Color newColor)
    {
        liveColor = newColor;
        updatePanel();
    }

    public Color getDeadColor()
    {
        return deadColor;
    }

    public void setDeadColor(Color newColor)
    {
        deadColor = newColor;
        updatePanel();
    }
}