import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;

public class UniversePanel extends JPanel
{
    public Universe universe;
    private Cell[][] cells;
    private Color liveColor = Color.black;
    private Color deadColor = Color.white;

    public UniversePanel(Universe u, int cellSize)
    {
        universe = u;
        cells = new Cell[universe.rows][universe.cols];
        UniversePanelListener listener = new UniversePanelListener(this); 
        Dimension size = new Dimension(cellSize, cellSize);
        setLayout(new GridLayout(cells.length, cells[0].length));
        for (int i=0; i<cells.length; i++)
            {
                for (int j=0; j<cells[0].length; j++)
                    {
                        Cell cell = new Cell(i, j);
                        cell.setOpaque(true);
                        if (universe.board[i][j]==1)
                            cell.setBackground(Color.black);
                        else
                            cell.setBackground(Color.white);
                        cell.addMouseListener(listener);
                        cell.setPreferredSize(size);
                        add(cell);
                        cells[i][j] = cell;
                    }
            }
    }

    public void updatePanel()
    {
        for (int i=0; i<cells.length; i++)
            {
                for (int j=0; j<cells[0].length; j++)
                    {
                        if (universe.board[i][j]==1)
                            cells[i][j].setBackground(liveColor);
                        else
                            cells[i][j].setBackground(deadColor);
                    }
            }
    }

    public void toggleCell(Cell c) 
    {
        if (universe.board[c.I][c.J] == 1)
            {
                System.out.println(c.I + " " + c.J + " OFF");
                universe.board[c.I][c.J] = 0;
                cells[c.I][c.J].setBackground(deadColor);
            }
        else
            {
                System.out.println(c.I + " " + c.J + " ON");
                universe.board[c.I][c.J] = 1;
                cells[c.I][c.J].setBackground(liveColor);
            }
    }

    public void changeLiveColor(Color newColor)
    {
        liveColor = newColor;
        updatePanel();
    }

    public void changeDeadColor(Color newColor)
    {
        deadColor = newColor;
        updatePanel();
    }
}