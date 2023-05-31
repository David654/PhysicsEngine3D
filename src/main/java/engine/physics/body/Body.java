package engine.physics.body;

import com.jogamp.opengl.GL2;
import engine.math.vector.Vector3;
import engine.physics.material.Material;
import engine.physics.shape.Shape;

public abstract class Body
{
    protected Vector3 position;
    protected Shape shape;
    protected Material material;

    public Body(Vector3 position, Shape shape, Material material)
    {
        this.position = position;
        this.shape = shape;
        this.material = material;
    }

    public Vector3 getPosition()
    {
        return position;
    }

    public void setPosition(Vector3 position)
    {
        this.position = position;
    }

    public Shape getShape()
    {
        return shape;
    }

    public void setShape(Shape shape)
    {
        this.shape = shape;
    }

    public Material getMaterial()
    {
        return material;
    }

    public void setMaterial(Material material)
    {
        this.material = material;
    }

    public abstract void updateForces();

    public abstract void checkCollisions();

    public abstract void updatePosition();

    public abstract void draw(GL2 gl);
}