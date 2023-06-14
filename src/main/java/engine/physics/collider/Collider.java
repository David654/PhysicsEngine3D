package engine.physics.collider;

import engine.math.vector.Vector3;
import engine.physics.shape.Shape;

public abstract class Collider
{
    protected Shape shape;

    public Collider(Shape shape)
    {
        this.shape = shape;
    }

    public Shape getShape()
    {
        return shape;
    }

    public abstract boolean collides(SphereCollider collider);

    public abstract boolean collides(AABBCollider collider);

    public boolean collides(Collider collider)
    {
        if(collider instanceof SphereCollider sphereCollider)
        {
            return collides(sphereCollider);
        }
        else if(collider instanceof AABBCollider AABBCollider)
        {
            return collides(AABBCollider);
        }
        return false;
    }

    public abstract void updatePosition(Vector3 position);
}