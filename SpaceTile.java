public class SpaceTile
{
    public SpaceVector P0, PA, PB, N, VA, VB;
    public float d;

    public SpaceTile(SpaceVector p0, SpaceVector pA, SpaceVector pB)
    {
        P0 = p0;
        PA = pA;
        PB = pB;
        VA = pA.plus(p0.negation());
        VB = pB.plus(p0.negation());
        N = VA.cross(VB);
        d = P0.negation().dot(N);
    }
}