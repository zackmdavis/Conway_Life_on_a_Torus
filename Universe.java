import java.util.Arrays;

public class Universe
{
    enum State
    {
        DEAD, ALIVE, VOID
    }

    public int rows;
    public int cols;
    public State[][] board;

    public Universe(int r, int c, int[] x_initials, int[] y_initials)
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

    public Universe(int r, int c)
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

    public Universe(int r, int c, State[][] b)
    {
        rows = r;
        cols = c;
        board = b;
    }

    // Randomly seeded universe
    public Universe(int r, int c, double p)
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

    public int neighborhood(int i, int j)
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

    public void advanceGeneration()
    {
        State[][] new_board = new State[rows][cols];
        for(int i=0; i<rows; i++)
            {
                for(int j=0; j<cols; j++)
                    {
                        new_board[i][j] = State.DEAD;
                    }
            }
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

    private static int modulo(int a, int b)
    {
        return (a%b + b)%b;
    }
}