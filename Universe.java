import java.util.Arrays;

public class Universe
{
    public int rows;
    public int cols;
    public int[][] board;

    public Universe(int r, int c, int[] x_initials, int[] y_initials)
    {
        rows = r;
        cols = c;
        int to_place = x_initials.length;
        int placed = 0;
        board = new int[rows][cols];
        for (int i=0; i<rows; i++)
            {
                for (int j=0; j<cols; j++)
                    {
                        if (placed<to_place)
                            {
                                if (i==x_initials[placed] && j==y_initials[placed])
                                    {
                                        board[i][j] = 1;
                                        placed++;
                                    }
                                else
                                    board[i][j] = 0;
                            }
                        else
                            board[i][j] = 0;
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
                        if((board[modulo(i+x_offset,rows)][modulo(j+y_offset,cols)]==1) && (x_offset!=0 || y_offset!=0))
                            live_neighbors++;
                    }
            }
        return live_neighbors;
    }

    public void advanceGeneration()
    {
        int[][] new_board = new int[rows][cols];
        for(int i=0; i<rows; i++)
            {
                for(int j=0; j<rows; j++)
                    {
                        new_board[i][j] = 0;
                    }
            }
        for(int i=0; i<rows; i++)
            {
                for(int j=0; j<rows; j++)
                    {
                        if(board[i][j]==1)
                            {
                                if(neighborhood(i, j)<2)
                                    new_board[i][j]=0;
                                else if(neighborhood(i, j)<=3)
                                    new_board[i][j] = 1;
                                else if (neighborhood(i, j)>3)
                                    new_board[i][j] = 0;
                            }
                        if(board[i][j]==0)
                            {
                                if(neighborhood(i, j)==3)
                                   new_board[i][j] = 1;
                                else
                                   new_board[i][j] = 0;
                            }
                    }
            }
        board =  new_board;        
    }

    public static int modulo(int a, int b)
    {
        return (a%b + b)%b;
    }
}