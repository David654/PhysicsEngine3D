package engine.physics.body;

import com.jogamp.opengl.GL2;
import engine.math.vector.Vector3;
import engine.physics.collider.Collider;
import engine.physics.material.Material;
import engine.physics.shape.Shape;

public abstract class CollisionBody extends Body
{
    protected Collider collider;

    public CollisionBody(Vector3 position, Collider collider, Shape shape, Material material)
    {
        super(position, shape, material);

        this.collider = collider;
    }

    public Collider getCollider()
    {
        return collider;
    }

    public void setCollider(Collider collider)
    {
        this.collider = collider;
    }

    public abstract void draw(GL2 gl);
}