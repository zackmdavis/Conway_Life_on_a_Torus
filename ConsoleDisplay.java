public class ConsoleDisplay
{
    public static void main(String[] args) throws InterruptedException
    {
        int[] x_start = {0, 1, 2, 2, 2};
        int[] y_start = {1, 2, 0, 1, 2};
        Universe U = new Universe(10, 10, x_start, y_start);
        for (int g=0; g<1000; g++)
            {
                display(U);
                System.out.println("GENERATION " + g);
                U.advanceGeneration();
                Thread.sleep(120);
            }
    }

    public static void display(Universe u)
    {
        for(int i=0; i<10; i++)
            {
                String line = "";
                for(int j=0; j<10; j++)
                    {
                        if(u.board[i][j]==1)
                            line += 'X';
                        else
                            line += ' ';
                    }
                System.out.println(line);
            }
    }

}