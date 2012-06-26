import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;

/**
 * the panel that displays the Universe under consideration
 */
public class UniversePanel extends JPanel
{
    private Universe universe;
    public int generationCounter;
    private Cell[][] cells;
    private Color liveColor;
    private Color deadColor;
    private boolean borders;

    /**
     * Constructs a UniversePanel
     *
     * @param u the Universe to be contained
     * @param cellSize the size of the display cells
     * @param lc the color of the living cells
     * @param dc the color of the dead cells
     * @param b whether to draw display cell borders
     */
    public UniversePanel(Universe u, int cellSize, Color lc, Color dc, boolean b)
    {
        universe = u;
        cells = new Cell[universe.rows][universe.cols];
        liveColor = lc;
        deadColor = dc;
        borders = b;
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
        if (borders)
            setBorders(true);
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

    public boolean getBorders()
    {
        return borders;
    }

    public void setBorders(boolean b)
    {
        if (b)
            {
                for (int i=0; i<cells.length-1; i++)
                    {
                        for (int j=0; j<cells[0].length-1; j++)
                            {
                                cells[i][j].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.black));
                            }
                    }
                for  (int i=0; i<cells.length-1; i++)
                    {
                        cells[i][cells[0].length-1].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
                    }
                for  (int j=0; j<cells[0].length-1; j++)
                    {
                        cells[cells.length-1][j].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));
                    }
                borders = true;
            }                   
        else if (!b)
            {
                for (int i=0; i<cells.length; i++)
                    {
                        for (int j=0; j<cells[0].length; j++)
                            {
                                cells[i][j].setBorder(BorderFactory.createEmptyBorder());
                            }
                    }
                borders = false;
            }
    }
}