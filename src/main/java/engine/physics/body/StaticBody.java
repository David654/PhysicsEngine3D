package engine.physics.body;

import com.jogamp.opengl.GL2;
import engine.math.vector.Vector3;
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

    public void checkCollisions() {

    }

    public void updatePosition() {

    }

    public void draw(GL2 gl)
    {

    }
}