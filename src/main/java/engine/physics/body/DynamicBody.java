package engine.physics.body;

import com.jogamp.opengl.GL2;
import engine.math.vector.Vector3;
import engine.physics.PhysicConstants;
import engine.physics.PhysicsEngine;
import engine.physics.collider.Collider;
import engine.physics.material.Material;
import engine.physics.shape.Box;
import engine.physics.shape.Shape;
import engine.physics.shape.Sphere;

public class DynamicBody extends CollisionBody
{
    private Vector3 velocity;
    private double mass;
    private Vector3 force;
    private Vector3 tmpVelocity;
    private Vector3 previousAcceleration;

    public DynamicBody(Vector3 position, Vector3 velocity, double mass, Collider collider, Shape shape, Material material)
    {
        super(position, collider, shape, material);

        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        force = new Vector3();
        tmpVelocity = velocity;
        previousAcceleration = new Vector3();
    }

    public DynamicBody(Vector3 position, double mass, Collider collider, Shape shape, Material material)
    {
        this(position, new Vector3(), mass, collider, shape, material);
    }

    public DynamicBody(Vector3 position, Collider collider, Shape shape, Material material)
    {
        this(position, new Vector3(), 1, collider, shape, material);
    }

    public DynamicBody(Collider collider, Shape shape, Material material)
    {
        this(new Vector3(), new Vector3(), 1, collider, shape, material);
    }

    public Vector3 getVelocity()
    {
        return velocity;
    }

    public void setVelocity(Vector3 velocity)
    {
        this.velocity = velocity;
    }

    public double getMass()
    {
        return mass;
    }

    public void setMass(double mass)
    {
        this.mass = mass;
    }

    public Vector3 getForce()
    {
        return force;
    }

    public void setForce(Vector3 force)
    {
        this.force = force;
    }

    private Vector3 getGravity()
    {
        return new Vector3(0, -mass * PhysicConstants.g, 0);
    }

    private Vector3 getAirResistance()
    {
        double area = 0;
        double dragCoefficient = 0;

        if(shape instanceof Sphere sphere)
        {
            area = 4 * Math.PI * Math.pow(sphere.getRadius(), 2);
            dragCoefficient = 0.47;
        }
        else if(shape instanceof Box box)
        {
            Vector3 dimensions = box.getDimensions();
            area = dimensions.getX() * dimensions.getY() * dimensions.getZ();
            dragCoefficient = 1.05;
        }

        return velocity.multiply(velocity).multiply(0.5 * PhysicConstants.AIR_DENSITY * area * dragCoefficient);
    }

    public void updateForces()
    {
        force = new Vector3();
        force = force.add(getGravity());
        //force = force.add(getAirResistance());
    }

    public void checkCollisions()
    {
        for(int i = 0; i < PhysicsEngine.BODY_HANDLER.size(); i++)
        {
            Body body = PhysicsEngine.BODY_HANDLER.get(i);

            if(body != this)
            {
                if(body instanceof DynamicBody dynamicBody)
                {
                    if(this.getCollider().collides(dynamicBody.getCollider()))
                    {
                        Vector3 v = dynamicBody.getVelocity();
                        double m = dynamicBody.getMass();

                        tmpVelocity = velocity.multiply((mass - m) / (mass + m)).add(v.multiply(2 * m / (mass + m)));
                    }
                }
                else if(body instanceof StaticBody staticBody)
                {
                    if(this.collider.collides(staticBody.getCollider()))
                    {
                        // System.out.println(position.add(shape.getDimensions()).subtract(staticBody.getPosition()));

                        //tmpVelocity = velocity.multiply((mass - Double.MAX_VALUE) / (mass + Double.MAX_VALUE));
                        //System.out.println(normal.multiply(-2 * normal.dot(velocity)));
                        Vector3 normal = staticBody.getCollider().getShape().getNormal(collider.getShape());
                        //System.out.println(normal);
                        tmpVelocity = velocity.add(normal.multiply(-2 * normal.dot(velocity)));
                    }
                }
            }
        }
    }

    public void updatePosition()
    {
        double dt = 1 / 60.0;

        velocity = tmpVelocity;
        Vector3 acceleration = force.multiply(1 / mass);

        position = position.add(velocity.multiply(dt)).add(acceleration.multiply(0.5 * dt * dt));
        velocity = velocity.add(previousAcceleration.add(acceleration).multiply(0.5 * dt));

        //System.out.println(velocity);

        tmpVelocity = velocity;

        previousAcceleration = acceleration;

        //position = position.add(velocity);
        this.getCollider().updatePosition(position);
        this.getShape().updatePosition(position);
    }

    public void draw(GL2 gl)
    {

    }
}