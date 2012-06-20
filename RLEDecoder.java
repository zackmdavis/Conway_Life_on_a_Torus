import java.util.StringTokenizer;
import java.util.Arrays;

public class RLEDecoder
{
    int rows, cols;
    int[][] board;
    int xFillCounter;
    int yFillCounter;

    enum commandType
    {
        NORMAL, REPEAT
    }

    public RLEDecoder(String specification) throws RLEDecodingBoundsException
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
        cols = Integer.parseInt(headerlineTokenizer.nextToken().replaceAll("[^0-9]", ""));
        rows = Integer.parseInt(headerlineTokenizer.nextToken().replaceAll("[^0-9]", ""));
        
        board = new int[rows][cols];
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
                                repeatDigitBuffer = "";
                                
                                for (int r=0; r<repeatCounter; r++)
                                    {
                                        setCell(true);
                                    }
                                CT = commandType.NORMAL;
                            }
                        else if (newChar == 'b')
                            {
                                repeatCounter = Integer.parseInt(repeatDigitBuffer);
                                repeatDigitBuffer = "";
                                for (int r=0; r<repeatCounter; r++)
                                    {
                                        setCell(false);
                                    }
                                CT = commandType.NORMAL;
                            }
                    }
            }
    }

    private void setDeadUntilNewline() throws RLEDecodingBoundsException
    {
        while (xFillCounter < cols)
            {
                try
                    {
                        board[yFillCounter][xFillCounter] = 0;
                    }
                catch (ArrayIndexOutOfBoundsException AIBexp)
                    {
                        throw new RLEDecodingBoundsException(AIBexp, xFillCounter, yFillCounter);
                    }
                xFillCounter++;
            }
        xFillCounter = 0;
        yFillCounter++;
    }

    private void setCell(boolean state) throws RLEDecodingBoundsException
    {
        int mark;
        if (state)
            mark = 1;
        else
            mark = 0;

        try
            {        
                board[yFillCounter][xFillCounter] = mark;
            }
        catch (ArrayIndexOutOfBoundsException AIBexp)
            {
                throw new RLEDecodingBoundsException(AIBexp, xFillCounter, yFillCounter);
            }
        xFillCounter++;
    }

    public Universe toUniverse()
    {
        Universe universe = new Universe(rows, cols, board);
        System.out.println(Arrays.toString(universe.board));
        return universe;
    }

}