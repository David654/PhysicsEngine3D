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

    public abstract boolean collides(Collider collider);

    public abstract void updatePosition(Vector3 position);
}