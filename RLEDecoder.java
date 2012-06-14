import java.util.StringTokenizer;

public class RLEDecoder
{
    Universe universe;
    int xFillCounter;
    int yFillCounter;

    enum commandType
    {
        NORMAL, REPEAT
    }

    public RLEDecoder(String specification)
    {
        xFillCounter = 0;
        yFillCounter = 0;
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
        String repeatDigitBuffer = "";
        int repeatCounter = 0;
        commandType CT = commandType.NORMAL;
        for (int c=0; c<characters; c++)
            {
                char newChar = pattern.charAt(c);
                if (CT == commandType.NORMAL)
                    {
                        if (Character.isDigit(newChar))
                            {
                                repeatDigitBuffer += newChar;
                                CT = commandType.REPEAT;
                            }
                        else if (newChar == 'o')
                            {
                                setCell(true);
                            }
                        else if (newChar == 'b')
                            {
                                setCell(false);
                            }
                        else if (newChar == '$' || newChar == '!')
                            {
                                setDeadUntilNewline();
                            }
                    }
                else if (CT == commandType.REPEAT)
                    {
                        if (Character.isDigit(newChar))
                            {
                                repeatDigitBuffer += newChar;
                            }
                        else if (newChar == 'o')
                            {
                                repeatCounter = Integer.parseInt(repeatDigitBuffer);
                                for (int r=0; r<repeatCounter; r++)
                                    {
                                        setCell(true);
                                    }
                                CT = commandType.NORMAL;
                            }
                        else if (newChar == 'b')
                            {
                                repeatCounter = Integer.parseInt(repeatDigitBuffer);
                                for (int r=0; r<repeatCounter; r++)
                                    {
                                        setCell(false);
                                    }
                                CT = commandType.NORMAL;
                            }
                    }
            }
    }

    private void setDeadUntilNewline()
    {
        while (xFillCounter < universe.cols)
            {
                universe.board[xFillCounter][yFillCounter] = 0;
                xFillCounter++;
            }
        yFillCounter++;
    }

    private void setCell(boolean state)
    {
        int mark;
        if (state)
            mark = 1;
        else
            mark = 0;

        universe.board[xFillCounter][yFillCounter] = mark;
        xFillCounter++;

        if (xFillCounter >= universe.cols)
            {
                xFillCounter = 0;
                yFillCounter++;
            }
    }

    public Universe toUniverse()
    {
        return universe;
    }

}