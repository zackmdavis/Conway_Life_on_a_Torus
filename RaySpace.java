public class RaySpace
{
    private Universe universe;
    private SpaceTile[] tiles;

    public RaySpace(Universe u)
    {
        universe = u;
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
        return new SpaceVector(-8+i, -8+j, 0);
    }

    public RayPixel castRay(SpaceVector origin, SpaceVector direction)
    {
        // TESTING
        RayPixel test = new RayPixel(0, 0);
        return test;
        // (old code to rewrite/reconsider follows)
        // float t, d;
        // float closest_t = Float.POSITIVE_INFINITY;
        // int hitTile = -1;
        // for(int tile = 0; tile<tiles.length; tile++)
        //     {
        //         System.out.println("TILE #" + tile + " " + tiles[tile].N.dot(direction)); // DEBUGGING
        //         if (tiles[tile].N.dot(direction) == 0)
        //             continue;
        //         d = tiles[tile].P0.negation().dot(tiles[tile].N);
        //         t = (-d + tiles[tile].N.dot(origin))/(tiles[tile].N.dot(direction));
        //         if (t < 0)
        //             continue;
        //         else if (t > closest_t)
        //             continue;
        //         else
        //             {
        //                 closest_t = t;
        //                 hitTile = tile;
        //             }
        //     }
        // if (hitTile == -1)
        //     {
        //         return -1;
        //     }
        // else
        //     {
        //         if (universe.board[hitTile/universe.rows][hitTile%universe.rows] == 0)
        //             return 0;
        //         else
        //             return 1;
        //     }
        
    }

}