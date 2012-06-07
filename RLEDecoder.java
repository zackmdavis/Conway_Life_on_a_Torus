import java.util.StringTokenizer;

public class RLEDecoder
{
    Universe universe;

    enum commandType
    {
        EMPTY, REPEAT, STATE, NEWLINE
    }

    public RLEDecoder(String specification)
    {
        StringTokenizer lineTokenizer = new StringTokenizer(specification, "\n");
        String headerline = "";
        while (!headerline.contains("x") || !headerline.contains("y"))
            {
                headerline = lineTokenizer.nextToken();
            }
        StringTokenizer headerlineTokenizer = new StringTokenizer(headerline, ",");
        int cols = Integer.parseInt(headerlineTokenizer.nextToken().replaceAll("[^0-9]", ""));
        int rows = Integer.parseInt(headerlineTokenizer.nextToken().replaceAll("[^0-9]", ""));
        int[][] board = new int[rows][cols];
        
        String pattern = "";
        while (lineTokenizer.hasMoreTokens())
            {
                pattern += lineTokenizer.nextToken();
            }
        pattern.replaceAll("\n", "");


        int characters = pattern.length();
        String command = "";
        commandType CT = commandType.EMPTY;
        for (int c=0; c<characters; c++)
            {
                char newChar = pattern.charAt(c);
                // TODO
            }
        

    }



}