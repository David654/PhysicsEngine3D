package engine.physics.collider;

import engine.math.vector.Vector3;
import engine.physics.shape.Box;

public class OBBCollider extends Collider
{
    public OBBCollider(Vector3 position, Vector3 dimensions)
    {
        super(new Box(position, dimensions));
    }

    public boolean collides(SphereCollider collider)
    {
        return collider.collides(this);
    }

    public boolean collides(AABBCollider collider)
    {
        Vector3 aMin = ((Box) shape).getMin();
        Vector3 aMax = ((Box) shape).getMax();
        Vector3 bMin = ((Box) collider.getShape()).getMin();
        Vector3 bMax = ((Box) collider.getShape()).getMax();

        return	(aMin.getX() <= bMax.getX() && aMax.getX() >= bMin.getX()) &&
                (aMin.getY() <= bMax.getY() && aMax.getY() >= bMin.getY()) &&
                (aMin.getZ() <= bMax.getZ() && aMax.getZ() >= bMin.getZ());
    }

    public boolean collides(Collider collider)
    {
        return false;
    }

    public void updatePosition(Vector3 position)
    {

    }
}