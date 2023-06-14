package engine.physics.body;

import com.jogamp.opengl.GL2;
import engine.math.vector.Vector3;
import engine.physics.PhysicConstants;
import engine.physics.material.Material;
import engine.physics.shape.Shape;

public abstract class Body
{
    public static int BODY_COUNT = 0;

    protected Vector3 position;
    protected Shape shape;
    protected Material material;
    protected String name;
    protected boolean isVisible;
    protected boolean collides;

    public Body(Vector3 position, Shape shape, Material material)
    {
        this.position = position;
        this.shape = shape;
        this.material = material;
        this.name = "Body " + (BODY_COUNT + 1);
        this.isVisible = true;
        BODY_COUNT++;
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isVisible()
    {
        return isVisible;
    }

    public void setVisible(boolean visible)
    {
        isVisible = visible;
    }

    public boolean isActive()
    {
        return isVisible && position.length() <= PhysicConstants.MAX_DISTANCE;
    }

    public boolean collides()
    {
        return collides;
    }

    public abstract void updateForces();

    public abstract void checkCollisions();

    public abstract void updatePosition();

    public abstract void draw(GL2 gl);
}