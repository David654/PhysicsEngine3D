package engine.physics.material;

import java.awt.*;

public class Material
{
    protected Color color;
    protected double diffuse;
    protected double refraction;
    protected double friction;
    protected double restitution;

    public Material(Color color, double diffuse, double refraction, double friction, double restitution)
    {
        this.color = color;
        this.diffuse = diffuse;
        this.refraction = refraction;
        this.friction = friction;
        this.restitution = restitution;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public double getDiffuse()
    {
        return diffuse;
    }

    public void setDiffuse(double diffuse)
    {
        this.diffuse = diffuse;
    }

    public double getRefraction()
    {
        return refraction;
    }

    public void setRefraction(double refraction)
    {
        this.refraction = refraction;
    }

    public double getFriction()
    {
        return friction;
    }

    public void setFriction(double friction)
    {
        this.friction = friction;
    }

    public double getRestitution()
    {
        return restitution;
    }

    public void setRestitution(double restitution)
    {
        this.restitution = restitution;
    }
}