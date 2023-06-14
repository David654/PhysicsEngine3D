package engine.physics.shape;

import engine.math.vector.Vector3;
import engine.physics.PhysicConstants;

public non-sealed class Sphere implements PrimitiveShape
{
    private Vector3 position;
    private double radius;

    public Sphere(Vector3 position, double radius)
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

    public Vector3 getDimensions()
    {
        return new Vector3(getRadius());
    }

    public Vector3 getNormal(Shape shape)
    {
        Vector3 diff = this.getClosestPointToShape(shape);

        if(shape instanceof Sphere sphere)
        {
            diff = diff.subtract(sphere.getPosition());
        }
        else if(shape instanceof Box box)
        {
            diff = diff.subtract(box.getPosition());
        }

        return diff.normalize();
    }

    public double getArea()
    {
        return 4 * Math.PI * Math.pow(radius, 2);
    }

    public void updatePosition(Vector3 position)
    {
        this.setPosition(position);
    }
}