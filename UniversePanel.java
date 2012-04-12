import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;

public class UniversePanel extends JPanel
{
    public Universe universe;
    private JLabel[][] cells;

    public UniversePanel(Universe u, int cellSize)
    {
        universe = u;
        cells = new JLabel[universe.rows][universe.cols];
        UniversePanelListener listener = new UniversePanelListener(this); 
        Dimension size = new Dimension(cellSize, cellSize);
        setLayout(new GridLayout(cells.length, cells[0].length));
        for (int i=0; i<cells.length; i++)
            {
                for (int j=0; j<cells[0].length; j++)
                    {
                        JLabel cell = new JLabel();
                        cell.setOpaque(true);
                        //System.out.println("indices " + i + " " + j); // DEBUGGING
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
                            cells[i][j].setBackground(Color.black);
                        else
                            cells[i][j].setBackground(Color.white);
                    }
            }
    }

    public void toggleCell(JLabel cell) 
    {
        // TODO?---make Cell class extending JLabel to include index info so as to avoid this awful "WTF" double-loop; or, some other solution
        for (int i=0; i<cells.length; i++)
            {
                for (int j=0; j<cells[0].length; j++)
                    {
                        if (cell == cells[i][j])
                            {
                                if (universe.board[i][j]==1)
                                    {
                                        System.out.println(i + " " + j + " OFF");
                                        universe.board[i][j] = 0;
                                        cells[i][j].setBackground(Color.white);
                                    }
                                else
                                    {
                                        System.out.println(i + " " + j + " ON");
                                        universe.board[i][j] = 1;
                                        cells[i][j].setBackground(Color.black);
                                    }
                            }
                    }
            }
    }

}