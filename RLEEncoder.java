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
        for (int i=0; i<universe.rows; i++)
            {
                for (int j=0; j<universe.rows; j++)
                    {
                        // TODO: encode universe in RLE format
                    }
            }
    }

    public void printToConsole()
    {
        System.out.println(builder.toString());
    }

}