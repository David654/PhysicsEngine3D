package engine.physics.body;

import com.jogamp.opengl.GL2;
import engine.math.vector.Vector3;
import engine.physics.PhysicConstants;
import engine.physics.PhysicsEngine;
import engine.physics.collider.Collider;
import engine.physics.material.Material;
import engine.physics.shape.Box;
import engine.physics.shape.PrimitiveShape;
import engine.physics.shape.Shape;
import engine.physics.shape.Sphere;

public class DynamicBody extends CollisionBody
{
    private Vector3 lastPosition;
    private Vector3 velocity;
    private double mass;
    private Vector3 force;
    private Vector3 tmpVelocity;
    private Vector3 previousAcceleration;
    private Vector3 rotation;
    private Vector3 rotationVelocity;

    private boolean applyGravity = true;

    public DynamicBody(Vector3 position, Vector3 velocity, double mass, Collider collider, Shape shape, Material material)
    {
        super(position, collider, shape, material);

        this.position = position;
        lastPosition = position;
        this.velocity = velocity;
        this.mass = mass;
        force = new Vector3();
        tmpVelocity = velocity;
        previousAcceleration = new Vector3();
        rotation = new Vector3();
        rotationVelocity = new Vector3();
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

    public Vector3 getLastPosition()
    {
        return lastPosition;
    }

    public boolean isGravityApplied()
    {
        return applyGravity;
    }

    public void applyGravity(boolean applyGravity)
    {
        this.applyGravity = applyGravity;
    }

    private Vector3 getGravity()
    {
        return new Vector3(0, -mass * PhysicConstants.g, 0);
    }

    private Vector3 getAirResistance()
    {
        double dragCoefficient = 0;

        if(shape instanceof Sphere)
        {
            dragCoefficient = 0.47;
        }
        else if(shape instanceof Box)
        {
            dragCoefficient = 1.05;
        }

     //   Vector3 force = new Vector3(0, -Math.signum(velocity.getY()) * Math.pow(velocity.getY(), 2) * (0.5 * PhysicConstants.AIR_DENSITY * shape.getArea() * dragCoefficient), 0);

        Vector3 velocityDirection = velocity.normalize();
        return velocityDirection.multiply(-Math.pow(velocity.length(), 2) * 0.5 * PhysicConstants.AIR_DENSITY * shape.getArea() * dragCoefficient);
    }

    public void updateForces()
    {
        force = new Vector3();
        if(applyGravity)
        {
            force = force.add(getGravity());
        }
       // force = force.add(getAirResistance());
    }

    public void checkCollisions()
    {
        collides = false;

        for(int i = 0; i < PhysicsEngine.BODY_HANDLER.size(); i++)
        {
            Body body = PhysicsEngine.BODY_HANDLER.get(i);

            if(body != this && body.isEnabled())
            {
                Vector3 p = body.getPosition();

                if(body instanceof DynamicBody dynamicBody)
                {
                    if(this.getCollider().collides(dynamicBody.getCollider()))
                    {
                        collides = true;
                        Vector3 v = dynamicBody.getVelocity();
                        double m = dynamicBody.getMass();

                        //tmpVelocity = velocity.multiply((mass - m) / (mass + m)).add(v.multiply(2 * m / (mass + m)));
                        //tmpVelocity = tmpVelocity.subtract(position.subtract(p).multiply(2 * m / (mass + m) * velocity.subtract(v).multiply(material.getRestitution()).dot(position.subtract(p)) / Math.pow(position.subtract(p).length(), 2)));
                       // tmpVelocity = tmpVelocity.multiply(normal);

                        Vector3 newVelocity = position.subtract(p).multiply((1 + material.getRestitution()) * m / (mass + m) * velocity.subtract(v).dot(position.subtract(p)) / Math.pow(position.subtract(p).length(), 2));
                        tmpVelocity = tmpVelocity.subtract(newVelocity);

                       // Vector3 normal = dynamicBody.getCollider().getShape().getNormal(this.getShape());
                       // double n = velocity.dot(normal);
                        Vector3 N = getGravity().negate();
                        //force = force.add(N.multiply(0.19));

                        Vector3 friction = velocity.normalize().multiply(-mass * PhysicConstants.g * body.getMaterial().getFriction());
                        force = force.add(friction);

                        //System.out.println(position.subtract(body.getPosition()).length() - ((Sphere) shape).getRadius() - ((Sphere) body.getShape()).getRadius());

                        //double t = ((Sphere) shape).getRadius() + ((Sphere) body.getShape()).getRadius() - ();
                    }
                }
                else if(body instanceof StaticBody staticBody)
                {
                    if(this.collider.collides(staticBody.getCollider()))
                    {
                        collides = true;
                        // System.out.println(position.add(shape.getDimensions()).subtract(staticBody.getPosition()));

                        //tmpVelocity = velocity.multiply((mass - Double.MAX_VALUE) / (mass + Double.MAX_VALUE));
                        //System.out.println(normal.multiply(-2 * normal.dot(velocity)));
                        Vector3 normal = staticBody.getCollider().getShape().getNormal(collider.getShape());
                        //System.out.println(normal);
                        //Vector3 v = position.subtract(p).multiply((1 + material.getRestitution()) * velocity.dot(position.subtract(p)) / Math.pow(position.subtract(p).length(), 2));
                        tmpVelocity = velocity.add(normal.multiply(-(1 + material.getRestitution()) * normal.dot(velocity)));

                       // System.out.println(tmpVelocity);

                       // Vector3 newVelocity = position.subtract(p).multiply((1 + material.getRestitution()) * velocity.negate().dot(position.subtract(p)) / Math.pow(position.subtract(p).length(), 2));
                       // System.out.println(velocity + "; " + newVelocity);
                       // tmpVelocity = tmpVelocity.add(normal.multiply(-2 * normal.dot(newVelocity)));



                       // tmpVelocity = tmpVelocity.subtract(position.subtract(p).multiply(2 * velocity.dot(position.subtract(p)) / Math.pow(position.subtract(p).length(), 2)));
                        //System.out.println(position.subtract(p).multiply(2 * velocity.dot(position.subtract(p)) / Math.pow(position.subtract(p).length(), 2)));

                        Vector3 relativeVelocity = tmpVelocity.subtract(velocity);
                        Vector3 tangent = relativeVelocity.subtract(normal.multiply(relativeVelocity.dot(normal))).normalize();
                        //double jt = -

                        Vector3 friction = velocity.normalize().multiply(-mass * PhysicConstants.g * body.getMaterial().getFriction());
                        force = force.add(friction);
                    }
                }
            }
        }
    }

    public void updatePosition()
    {
        double dt = 1 / 1000.0;

        velocity = new Vector3(tmpVelocity);
        Vector3 acceleration = force.multiply(1 / mass);


        velocity = velocity.add(acceleration.multiply(dt));
        position = position.add(velocity.multiply(dt));
        //position = position.add(velocity.multiply(dt)).add(acceleration.multiply(0.5 * dt * dt));
        //velocity = velocity.add(previousAcceleration.add(acceleration).multiply(0.5 * dt));

        rotation = rotation.add(rotationVelocity.multiply(dt));
        rotationVelocity = rotationVelocity.add(velocity);

        //System.out.println(velocity);

        lastPosition = new Vector3(position);

        tmpVelocity = new Vector3(velocity);

        previousAcceleration = acceleration;
        force = new Vector3();

        //position = position.add(velocity);
        this.getCollider().updatePosition(position);
        this.getShape().updatePosition(position);

        if(velocity.length() < 0.000001)
        {
            velocity = new Vector3();
        }
    }

    public void draw(GL2 gl)
    {

    }
}