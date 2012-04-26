public class RaySpace
{
    public Universe universe;
    public SpaceTile[] tiles;

    public RaySpace(Universe u)
    {
        tiles = new SpaceTile[u.rows*u.cols];
        for (int i=0; i<u.rows; i++)
            {
                for (int j=0; j<u.cols; j++)
                    {
                        // start with a simple plane mapping while
                        // getting the raycasting to work at all; then
                        // swap in torus mapping
                        SpaceVector p0 = planeMapping(i, j);
                        SpaceVector pA = planeMapping(i, j);
                        SpaceVector pB = planeMapping(i, j);
                        tiles[i+j] = new SpaceTile(p0, pA, pB);
                    }
            }
    }

    public SpaceVector planeMapping(int i, int j)
    {
        // TODO
        return new SpaceVector(0, 0, 0);
    }

}