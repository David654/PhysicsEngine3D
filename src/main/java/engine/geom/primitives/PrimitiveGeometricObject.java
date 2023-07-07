package engine.geom.primitives;

import engine.geom.GeometricObject;
import engine.geom.Polygon;

import java.awt.*;

public abstract class PrimitiveGeometricObject extends GeometricObject
{
    public PrimitiveGeometricObject(Polygon[] polygons)
    {
        super(polygons);
    }

    public PrimitiveGeometricObject(Polygon[] polygons, Color[] colors)
    {
        super(polygons, colors);
    }

    public PrimitiveGeometricObject(Polygon[] polygons, Color color)
    {
        super(polygons, color);
    }

    protected abstract Polygon[] initPolygons();
}