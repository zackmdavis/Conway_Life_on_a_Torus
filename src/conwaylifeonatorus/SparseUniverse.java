package conwaylifeonatorus;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class SparseUniverse implements Universe
{
    private int rows;
    private int cols;
    private HashMap<String, State> liveCells;

    public SparseUniverse(int r, int c, int[] xInitials, int[] yInitials)
    {
        rows = r;
        cols = c;
        liveCells = new HashMap<String, State>(r*c);
        for (int k=0; k<xInitials.length; k++)
            {
                liveCells.put(encodeIndices(xInitials[k], yInitials[k]), State.ALIVE);
            }
    }

    public SparseUniverse(int r, int c)
    {
        rows = r;
        cols = c;
        liveCells = new HashMap<String, State>(r*c);        
    }

    public SparseUniverse(int r, int c, State[][] b)
    {
        rows = r;
        cols = c;
        liveCells = new HashMap<String, State>(r*c);
        for (int i=0; i<rows; i++)
            {
                for (int j=0; j<cols; j++)
                    {
                        if (b[i][j] == State.ALIVE)
                            liveCells.put(encodeIndices(i, j), State.ALIVE);
                    }
            }
    }

    public SparseUniverse(int r, int c, double p)
    {
        rows = r;
        cols = c;
        liveCells = new HashMap<String, State>(r*c);
        for (int i=0; i<rows; i++)
            {
                for (int j=0; j<cols; j++)
                    {
                        double chance = Math.random();
                        if (chance < p)
                            liveCells.put(encodeIndices(i, j), State.ALIVE);
                    }
            }

    }

    public int getRows()
    {
        return rows;
    }

    public int getCols()
    {
        return cols;
    }

    public State getCellState(int i, int j)
    {
        State lookup = liveCells.get(encodeIndices(i, j));
        if (lookup == null)
            return State.DEAD;
        else
            return State.ALIVE;
    }


    public void setCellState(int i, int j, State s)
    {
        State lookup = liveCells.get(encodeIndices(i, j));
        if (s == State.ALIVE)
            {
                if (lookup == null)
                    {
                        liveCells.put(encodeIndices(i, j), State.ALIVE);
                    }
            }
        else if (s == State.DEAD)
            {
                if (lookup != null)
                    {
                        liveCells.remove(encodeIndices(i, j));
                    }
            }
    }

    private static String[] acquireNeighborhood(int i, int j)
    {
        String[] neighborhood = new String[8];
        int neighborsPlaced = 0;
        for (int xOffset=-1; xOffset<=1; xOffset++)
            {
                for (int yOffset=-1; yOffset<=1; yOffset++)
                    {
                        if ((xOffset != 0) || (yOffset !=0))
                            {
                                neighborhood[neighborsPlaced] = encodeIndices(xOffset, yOffset);
                                neighborsPlaced++;
                            }
                    }
            }
        return neighborhood;
    }

    private int countLiveNeighbors(int i, int j)
    {
        String[] neighborhood = acquireNeighborhood(i, j);
        int liveNeighbors = 0;
        for (int k=0; k<8; k++)
            {
                if (liveCells.get(neighborhood[k]) != null)
                    liveNeighbors++;
            }
        return liveNeighbors;
    }

    public void advanceGeneration()
    {
        //live cells and all neighbors thereof
        Set<String> toUpdate = new HashSet<String>(rows*cols);
        HashMap<String, State> newLiveCells = new HashMap<String, State>(rows*cols);
        for (String stringCell : liveCells.keySet())
            {
                int[] cell = decodeIndices(stringCell);
                toUpdate.add(stringCell);
                for (String stringNeighbor : acquireNeighborhood(cell[0], cell[1]))
                    {
                        toUpdate.add(stringNeighbor);
                    }
            }
        for (String stringCell : toUpdate)
            {
                int[] cell = decodeIndices(stringCell);
                int liveNeighbors = countLiveNeighbors(cell[0], cell[1]);
                if (getCellState(cell[0], cell[1]) == State.ALIVE)
                    {
                        if ((liveNeighbors == 2) || (liveNeighbors == 3))
                            newLiveCells.put(stringCell, State.ALIVE);
                    }
                else if (getCellState(cell[0], cell[1]) == State.DEAD)
                    {
                        if (liveNeighbors == 3)
                            newLiveCells.put(stringCell, State.ALIVE);
                    }
            }
        liveCells = newLiveCells;
    }

    public int queryPopulation()
    {
        return liveCells.size();
    }

    private static String encodeIndices(int i, int j)
    {
        return i + "," + j;
    }

    private static int[] decodeIndices(String key)
    {
        String[] stringIndices =  key.split(",");
        int[] indices = new int[2];
        for (int k=0; k<2; k++)
            {
                indices[k] = Integer.parseInt(stringIndices[k]);
            }
        return indices;
    }

    /**
     * modulo operation that has the sign of the divisor (rather than the dividend, as is the case for Java's "%")
     *
     * @param a the dividend 
     * @param b the divisor
     */
    private static int modulo(int a, int b)
    {
        return (a%b + b)%b;
    }
}