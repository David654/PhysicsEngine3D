package engine.geom.primitives;

import engine.math.vector.Vector3;

import java.awt.*;

public class Cube extends Box
{
    public Cube(Vector3 position, double size, Color[] colors)
    {
        super(position, new Vector3(size), colors);
    }

    public Cube(Vector3 position, double size, Color color)
    {
        super(position, new Vector3(size), color);
    }
}