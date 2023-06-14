package engine.physics.collider;

import engine.math.vector.Vector3;
import engine.physics.shape.Box;

public class AABBCollider extends Collider
{
    public AABBCollider(Vector3 position, Vector3 dimensions)
    {
        super(new Box(position, dimensions));
    }

    public Vector3 getPosition()
    {
        return ((Box) shape).getPosition();
    }

    public void setPosition(Vector3 position)
    {
        ((Box) shape).setPosition(position);
    }

    public Vector3 getDimensions()
    {
        return ((Box) shape).getDimensions();
    }

    public void setDimensions(Vector3 dimensions)
    {
        ((Box) shape).setDimensions(dimensions);
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

    public void updatePosition(Vector3 position)
    {
        this.setPosition(position);
        ((Box) shape).rotateX(10 / 60.0);
    }
}