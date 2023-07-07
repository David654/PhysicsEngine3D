package engine.physics.body;

import com.jogamp.opengl.GL2;
import engine.math.vector.Vector3;
import engine.physics.PhysicConstants;
import engine.physics.PhysicsEngine;
import engine.physics.collider.Collider;
import engine.physics.material.Material;
import engine.physics.shape.Shape;

public class StaticBody extends CollisionBody
{
    public StaticBody(Vector3 position, Collider collider, Shape shape, Material material)
    {
        super(position, collider, shape, material);
    }

    public void updateForces() {

    }

    public void checkCollisions()
    {
        for(int i = 0; i < PhysicsEngine.BODY_HANDLER.size(); i++)
        {
            Body body = PhysicsEngine.BODY_HANDLER.get(i);

            if(body != this && body.isEnabled())
            {
                if(body instanceof DynamicBody dynamicBody)
                {
                    if(this.getCollider().collides(dynamicBody.getCollider()))
                    {
                        Vector3 normal = collider.getShape().getNormal(dynamicBody.getCollider().getShape());
                       // dynamicBody.setVelocity(dynamicBody.getVelocity().add(normal.multiply(-(1 + dynamicBody.getMaterial().getRestitution()) * normal.dot(dynamicBody.getVelocity()))));

                        Vector3 friction = new Vector3(-dynamicBody.getMass() * PhysicConstants.g, dynamicBody.getMass() * PhysicConstants.g, -dynamicBody.getMass() * PhysicConstants.g).multiply(dynamicBody.getVelocity().normalize()).multiply(material.getFriction());
                        //dynamicBody.setForce(dynamicBody.getForce().add(friction));
                    }
                }
            }
        }
    }

    public void updatePosition()
    {

    }

    public void draw(GL2 gl)
    {

    }
}