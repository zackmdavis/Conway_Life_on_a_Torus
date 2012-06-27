class RLEDecodingBoundsException extends Exception
{
    public RLEDecodingBoundsException(ArrayIndexOutOfBoundsException AIBexp, int x, int y)
    {
        super("RLE decoding failed while trying to populate cell " + x + ", " + y + ".", AIBexp);
    }
}
