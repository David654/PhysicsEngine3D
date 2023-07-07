package engine.geom.primitives;

import engine.geom.Polygon;
import engine.math.vector.Vector3;

import java.awt.*;

public class Box extends PrimitiveGeometricObject
{
    private Vector3 position;
    private Vector3 dimensions;

    public Box(Vector3 position, Vector3 dimensions, Color[] colors)
    {
        super(new Polygon[] {new Polygon(), new Polygon(), new Polygon(), new Polygon(), new Polygon(), new Polygon()}, colors);
        this.position = position;
        this.dimensions = dimensions;

        setPolygons(initPolygons());

        for(int i = 0; i < colors.length; i++)
        {
            polygons[i].setColor(colors[i]);
        }
    }

    public Box(Vector3 position, Vector3 dimensions, Color color)
    {
        this(position, dimensions, new Color[] {color, color, color, color, color, color});
    }

    public Vector3 getPosition()
    {
        return position;
    }

    public void setPosition(Vector3 position)
    {
        this.position = position;
        translate(this.position);
    }

    public Vector3 getDimensions()
    {
        return dimensions;
    }

    public void setDimensions(Vector3 dimensions)
    {
        this.dimensions = dimensions;
        setPolygons(initPolygons());
    }

    protected Polygon[] initPolygons()
    {
        Polygon polygon1 = new Polygon(
                new Vector3(0, 0, 0),
                new Vector3(0, 0, dimensions.getZ()),
                new Vector3(dimensions.getX(), 0, dimensions.getZ()),
                new Vector3(dimensions.getX(), 0, 0));

        Polygon polygon2 = new Polygon(
                new Vector3(0, 0, dimensions.getZ()),
                new Vector3(0, dimensions.getY(), dimensions.getZ()),
                new Vector3(dimensions.getX(), dimensions.getY(), dimensions.getZ()),
                new Vector3(dimensions.getX(), 0, dimensions.getZ()));

        Polygon polygon3 = new Polygon(
                new Vector3(dimensions.getX(), 0, dimensions.getZ()),
                new Vector3(dimensions.getX(), dimensions.getY(), dimensions.getZ()),
                new Vector3(dimensions.getX(), dimensions.getY(), 0),
                new Vector3(dimensions.getX(), 0, 0));

        Polygon polygon4 = new Polygon(
                new Vector3(0, dimensions.getY(), 0),
                new Vector3(0, dimensions.getY(), dimensions.getZ()),
                new Vector3(dimensions.getX(), dimensions.getY(), dimensions.getZ()),
                new Vector3(dimensions.getX(), dimensions.getY(), 0));

        Polygon polygon5 = new Polygon(new Vector3(0, 0, dimensions.getZ()),
                new Vector3(0, dimensions.getY(), dimensions.getZ()),
                new Vector3(0, dimensions.getY(), 0),
                new Vector3(0, 0, 0));

        Polygon polygon6 = new Polygon(new Vector3(0, 0, 0),
                new Vector3(0, dimensions.getY(), 0),
                new Vector3(dimensions.getX(), dimensions.getY(), 0),
                new Vector3(dimensions.getX(), 0, 0));

        Polygon[] polygons = new Polygon[] {polygon1, polygon2, polygon3, polygon4, polygon5, polygon6};

        for(Polygon p : polygons)
        {
            p.translate(position);
        }

        return polygons;
    }
}