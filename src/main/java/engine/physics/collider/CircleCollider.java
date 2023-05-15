package engine.physics.collider;

import engine.math.vector.Vector3;

public class CircleCollider extends Collider
{
    private Vector3 position;
    private double radius;

    public CircleCollider(Vector3 position, double radius)
    {
        this.position = position;
        this.radius = radius;
    }

    public Vector3 getPosition()
    {
        return position;
    }

    public void setPosition(Vector3 position)
    {
        this.position = position;
    }

    public double getRadius()
    {
        return radius;
    }

    public void setRadius(double radius)
    {
        this.radius = radius;
    }

    public boolean collides(CircleCollider collider)
    {
        return position.distance(collider.getPosition()) < radius + collider.getRadius();
    }
}