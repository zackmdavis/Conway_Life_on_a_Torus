package conwaylifeonatorus;

/**
 * Encodes a universe into <a href="http://conwaylife.com/wiki/RLE">run length encoded</a> format for safekeeping.
 *
 * @author Zack M. Davis
 */
public class RLEEncoder
{
    Universe universe;
    StringBuilder builder;

    /**
     * Encodes a universe into RLE format for safekeeping.
     *
     * @param u the universe to be encoded
     */
    public RLEEncoder(Universe u)
    {
        builder = new StringBuilder(500);
        universe = u;
        builder.append("x = ");
        builder.append(universe.getCols());
        builder.append(", y = ");
        builder.append(universe.getRows());
        builder.append(", rule = B3/S23\n");
        int charcount = 0;
        State state = State.VOID;
        int run = 0;
        for (int i=0; i<universe.getRows(); i++)
            {
                for (int j=0; j<universe.getCols(); j++)
                    {
                        if (charcount > 65)
                            {
                                builder.append('\n');
                                charcount = 0;
                            }
                        if (universe.getCellState(i, j) != state)
                            {
                                recordRun(state, run);
                                charcount += 2;
                                state = universe.getCellState(i, j);
                                run = 1;
                            }
                        else
                            run++;
                    }
                if (state == State.DEAD)
                    {
                        state = State.VOID;
                        run = 0;
                    }
                else if (state == State.ALIVE)
                    {
                        recordRun(state, run);
                        charcount += 2;
                        state = State.VOID;
                        run = 0;
                    }

                if (i == universe.getRows() - 1)
                    builder.append("!");
                else
                    builder.append("$");
            }
    }

    private void recordRun(State s, int r)
    {
        String st = "";
        if (s == State.DEAD)
            st = "b";
        else if (s == State.ALIVE)
            st = "o";
        if (st == "b" || st == "o")
            {
                if (r == 1)
                    builder.append(st);
                else
                    builder.append(r+st);
            }
    }

    /**
     * Returns the RLE-encoded universe as a string.
     */
    public String getRLE()
    {
        return builder.toString();
    }

    /**
     * Prints the RLE-encoding of the universe to the console.
     */
    public void printToConsole()
    {
        System.out.println(builder.toString());
    }

}