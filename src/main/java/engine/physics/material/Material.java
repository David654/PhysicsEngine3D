package engine.physics.material;

import java.awt.*;

public class Material
{
    protected Color color;
    protected double diffuse;
    protected double refraction;

    public Material(Color color, double diffuse, double refraction)
    {
        this.color = color;
        this.diffuse = diffuse;
        this.refraction = refraction;
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
}