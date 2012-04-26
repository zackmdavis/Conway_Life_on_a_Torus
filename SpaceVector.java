public class SpaceVector
{
    public float X, Y, Z;

    public SpaceVector(float x, float y, float z)
    {
        X = x;
        Y = y;
        Z = z;
    }

    public float dot(SpaceVector v)
    {
        return X*v.X + Y*v.Y + Z*v.Z;
    }

    public SpaceVector cross(SpaceVector v)
    {
        return new SpaceVector(Y*v.Z - Z*v.Z, Z*v.X - X*v.Z, X*v.Y - Y*v.X);
    }

    public SpaceVector plus(SpaceVector v)
    {
        return new SpaceVector(X+v.X, Y+v.Y, Z+v.Z);
    }

    public SpaceVector negation()
    {
        return new SpaceVector(-X, -Y, -Z);
    }
}