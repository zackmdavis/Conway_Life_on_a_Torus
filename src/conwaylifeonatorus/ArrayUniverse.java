package conwaylifeonatorus;

import java.util.Arrays;

/**
 *  the arena in which Life happens
 */ 
public class ArrayUniverse implements Universe
{
    private int rows;
    private int cols;
    private State[][] board;

    /**
     * Constructs an array universe with specified cells alive.
     * 
     * @param r the number of rows
     * @param c the number of columns
     * @param x_initials the horizontal coordinates of the cells that are initially alive.
     * @param y_initials the vertical coordinates of the cells that are initially alive
     */
    public ArrayUniverse(int r, int c, int[] x_initials, int[] y_initials)
    {
        rows = r;
        cols = c;
        int to_place = x_initials.length;
        int placed = 0;
        board = new State[rows][cols];
        for (int i=0; i<rows; i++)
            {
                for (int j=0; j<cols; j++)
                    {
                        if (placed < to_place)
                            {
                                if (i == x_initials[placed] && j == y_initials[placed])
                                    {
                                        board[i][j] = State.ALIVE;
                                        placed++;
                                    }
                                else
                                    board[i][j] = State.DEAD;
                            }
                        else
                            board[i][j] = State.DEAD;
                    }
            }
    }

    /**
     * Constructs a array universe with no living cells.
     *
     * @param r the number of rows
     * @param c the number of columns
     */
    public ArrayUniverse(int r, int c)
    {
        rows = r;
        cols = c;
        board = new State[rows][cols];
        for (int i=0; i<rows; i++)
            {
                for (int j=0; j<cols; j++)
                    {
                        board[i][j] = State.DEAD;
                    }
            }
    }

    /**
     * Constructs an array universe from a given two-dimensional array of cell states.
     *
     * @param r the number of rows
     * @param c the number of columns
     * @param b the state array
     */
    public ArrayUniverse(int r, int c, State[][] b)
    {
        rows = r;
        cols = c;
        board = b;
    }

    /**
     * Constructs an array universe with each cell alive or dead with some fixed (independent) probability.
     *
     * @param r the number of rows
     * @param c the number of columns
     * @param p the probability of a cell starting out alive
     */
    public ArrayUniverse(int r, int c, double p)
    {
        rows = r;
        cols = c;
        board = new State[rows][cols];
        for (int i=0; i<rows; i++)
            {
                for (int j=0; j<cols; j++)
                    {
                        double chance = Math.random();
                        if (chance < p)
                            board[i][j] = State.ALIVE;
                        else
                            board[i][j] = State.DEAD;
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
        return board[i][j];
    }

    public void setCellState(int i, int j, State s)
    {
        board[i][j] = s;
    }

    /**
     * Counts the number of live neighbors of the indicated cell.
     *
     * @param i the horizontal index of the cell under consideration
     * @param j the vertical index of the cell under consideration
     */
    private int neighborhood(int i, int j)
    {
        int live_neighbors = 0;
        for(int x_offset=-1; x_offset<=1; x_offset++)
            {
                for(int y_offset=-1; y_offset<=1; y_offset++)
                    {
                        if((board[modulo(i+x_offset, rows)][modulo(j+y_offset, cols)] == State.ALIVE) && (x_offset != 0 || y_offset != 0))
                            live_neighbors++;
                    }
            }
        return live_neighbors;
    }

    /**
     * Advances the simulation forward one tick.
     */
    public void advanceGeneration()
    {
        State[][] new_board = new State[rows][cols];
        for(int i=0; i<rows; i++)
            {
                for(int j=0; j<cols; j++)
                    {
                        if (board[i][j] == State.ALIVE)
                            {
                                if (neighborhood(i, j) < 2)
                                    new_board[i][j] = State.DEAD;
                                else if (neighborhood(i, j) <= 3)
                                    new_board[i][j] = State.ALIVE;
                                else if (neighborhood(i, j) > 3)
                                    new_board[i][j] = State.DEAD;
                            }
                        else if (board[i][j] == State.DEAD)
                            {
                                if (neighborhood(i, j) == 3)
                                   new_board[i][j] = State.ALIVE;
                                else
                                   new_board[i][j] = State.DEAD;
                            }
                    }
            }
        board = new_board;
    }

    /**
     *  Counts the number of cells that are still alive.
     */
    public int queryPopulation()
    {
        int population = 0;
        for (int i=0; i<rows; i++)
            {
                for (int j=0; j<cols; j++)
                    {
                        if (board[i][j] == State.ALIVE)
                            population++;
                    }
            }
        return population;
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