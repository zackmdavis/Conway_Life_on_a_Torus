public class RLEEncoder
{
    Universe universe;
    StringBuilder builder;

    public RLEEncoder(Universe u)
    {
        builder = new StringBuilder(500);
        universe = u;
        builder.append("x = ");
        builder.append(universe.cols);
        builder.append(", y = ");
        builder.append(universe.rows);
        builder.append(", rule = B3/S23\n");
        int charcount = 0;
        Universe.State state = Universe.State.VOID;
        int run = 0;
        for (int i=0; i<universe.rows; i++)
            {
                for (int j=0; j<universe.cols; j++)
                    {
                        if (charcount > 65)
                            {
                                builder.append('\n');
                                charcount = 0;
                            }
                        if (universe.board[i][j] != state)
                            {
                                recordRun(state, run);
                                charcount += 2;
                                state = universe.board[i][j];
                                run = 1;
                            }
                        else
                            run++;
                    }
                if (state == Universe.State.DEAD)
                    {
                        state = Universe.State.VOID;
                        run = 0;
                    }
                else if (state == Universe.State.ALIVE)
                    {
                        recordRun(state, run);
                        charcount += 2;
                        state = Universe.State.VOID;
                        run = 0;
                    }

                if (i == universe.rows - 1)
                    builder.append("!");
                else
                    builder.append("$");
            }
    }

    private void recordRun(Universe.State s, int r)
    {
        String st = "";
        if (s == Universe.State.DEAD)
            st = "b";
        else if (s == Universe.State.ALIVE)
            st = "o";
        if (st == "b" || st == "o")
            {
                if (r == 1)
                    builder.append(st);
                else
                    builder.append(r+st);
            }
    }

    public String getRLE()
    {
        return builder.toString();
    }

    public void printToConsole()
    {
        System.out.println(builder.toString());
    }

}