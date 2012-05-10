public class RayPixel
{
    public boolean inUniverse;
    public int[] cellIndex = new int[2];

    public RayPixel()
    {
        inUniverse = false;
        cellIndex[0] = -1;
        cellIndex[1] = -1;
    }

    public RayPixel(int i, int j)
    {
        inUniverse = true;
        cellIndex[0] = i;
        cellIndex[1] = j;
    }

}