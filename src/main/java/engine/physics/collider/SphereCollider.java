package engine.physics.collider;

import engine.math.vector.Vector3;
import engine.physics.shape.Box;
import engine.physics.shape.Sphere;

public class SphereCollider extends Collider
{
    public SphereCollider(Vector3 position, double radius)
    {
        super(new Sphere(position, radius));
    }

    public Vector3 getPosition()
    {
        return ((Sphere) shape).getPosition();
    }

    public void setPosition(Vector3 position)
    {
        ((Sphere) shape).setPosition(position);
    }

    public double getRadius()
    {
        return ((Sphere) shape).getRadius();
    }

    public void setRadius(double radius)
    {
        ((Sphere) shape).setRadius(radius);
    }

    public boolean collides(SphereCollider collider)
    {
        boolean a = this.getPosition().distance(collider.getPosition()) < (this.getRadius() + collider.getRadius());
        //System.out.println(a);
        return a;
    }

    public boolean collides(AABBCollider collider)
    {
        Vector3 closestPoint = ((Box) collider.getShape()).getClosestPointToShape(shape);
        double dist = this.getPosition().distance(closestPoint);
        return dist < this.getRadius();
    }

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

    public void updatePosition(Vector3 position)
    {
        this.setPosition(position);
    }
}