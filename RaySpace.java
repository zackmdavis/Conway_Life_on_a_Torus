public class RaySpace
{
    private Universe universe;
    private SpaceTile[][] tiles;

    public RaySpace(Universe u)
    {
        universe = u;
        tiles = new SpaceTile[u.rows][u.cols];
        for (int i=0; i<u.rows; i++)
            {
                for (int j=0; j<u.cols; j++)
                    {
                        // start with a simple plane mapping while
                        // getting the raycasting to work at all; then
                        // swap in torus mapping
                        SpaceVector p0 = planeMapping(i, j);
                        SpaceVector pA = planeMapping(i+1, j);
                        SpaceVector pB = planeMapping(i, j+1);
                        tiles[i][j] = new SpaceTile(p0, pA, pB);
                    }
            }
    }

    public SpaceVector planeMapping(int i, int j)
    {
        return new SpaceVector(-8+i, -8+j, 0);
    }

    private float intersection_t(SpaceVector cameraAt, SpaceVector cameraDir, SpaceTile tile)
    {
        if (tile.N.dot(cameraDir) == 0)
            return Float.NEGATIVE_INFINITY;
        else
            return tile.N.dot(tile.P0.plus(cameraAt.negation()))/tile.N.dot(cameraDir);
    }

    private boolean inTile(SpaceVector intersection, SpaceTile tile)
    {
        if (tile.P0.projectionFrom(intersection.plus(tile.VA.negation())).norm() < tile.VA.norm())
            {
                if (tile.P0.projectionFrom(intersection.plus(tile.VB.negation())).norm() < tile.VB.norm())
                    return true;
            }
        return false;
    }

    public RayPixel castRay(SpaceVector cameraAt, SpaceVector cameraDir)
    {
        float closest_t = Float.POSITIVE_INFINITY;
        RayPixel hit = new RayPixel();
        float t;
        boolean inside;
        for (int r=0; r<universe.rows; r++)
            {
                for (int c=0; c<universe.cols; c++)
                    {
                        t = intersection_t(cameraAt, cameraDir, tiles[r][c]);
                        inside = inTile(cameraAt.plus(cameraDir.scale(t)), tiles[r][c]);
                        if ((t>0) && (t<closest_t) && inside)
                            {
                                closest_t = t;
                                hit = new RayPixel(r, c);
                            }
                    }
            }
        return hit;
    }

}