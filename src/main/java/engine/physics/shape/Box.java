package engine.physics.shape;

import engine.math.vector.Vector3;

public non-sealed class Box extends ConvexPolyhedron implements PrimitiveShape
{
    private Vector3 position;
    private Vector3 dimensions;

    public Box(Vector3 position, Vector3 dimensions)
    {
        super(new Vector3[]{
                position,
                new Vector3(position.getX(), position.getY() + dimensions.getY(), position.getZ()),
                new Vector3(position.getX() + dimensions.getX(), position.getY() + dimensions.getY(), position.getZ()),
                new Vector3(position.getX() + dimensions.getX(), position.getY(), position.getZ()),

                new Vector3(position.getX(), position.getY() + dimensions.getY(), position.getZ() + dimensions.getZ()),
                new Vector3(position.getX() + dimensions.getX(), position.getY() + dimensions.getY(), position.getZ() + dimensions.getZ()),
                new Vector3(position.getX() + dimensions.getX(), position.getY(), position.getZ() + dimensions.getZ()),
                new Vector3(position.getX(), position.getY(), position.getZ() + dimensions.getZ())
        }, 8);

        this.position = position;
        this.dimensions = dimensions;
    }

    public Vector3 getPosition()
    {
        return position;
    }

    public void setPosition(Vector3 position)
    {
        this.translate(position);
        this.position = position;
    }

    public Vector3 getDimensions()
    {
        return dimensions;
    }

    public void setDimensions(Vector3 dimensions)
    {
        this.dimensions = dimensions;
    }

    public Vector3 getNormal(Shape shape)
    {
        /*Vector3 diff = this.getClosestPointToShape(shape).subtract(this.getPosition().add(this.getDimensions()));

        Vector3 normal = diff.abs();

        if(normal.getX() < normal.getY() && normal.getX() < normal.getZ())
        {
            normal = new Vector3(1 * Math.signum(diff.getX()), 0, 0);
        }
        else if(normal.getY() < normal.getX() && normal.getY() < normal.getZ())
        {
            normal = new Vector3(0, 1 * Math.signum(diff.getY()), 0);
        }
        else if(normal.getZ() < normal.getX() && normal.getZ() < normal.getY())
        {
            normal = new Vector3(0, 0, 1 * Math.signum(diff.getZ()));
        }**/

        Vector3 diff = this.getClosestPointToShape(shape);

        if(shape instanceof Sphere sphere)
        {
            diff = diff.subtract(sphere.getPosition());
        }
        else if(shape instanceof Box box)
        {
            diff = diff.subtract(box.getPosition());
        }

        Vector3 normal = diff;
        if(normal.getX() < normal.getY() && normal.getX() < normal.getZ())
        {
            normal = new Vector3(1 * Math.signum(diff.getX()), 0, 0);
        }
        else if(normal.getY() < normal.getX() && normal.getY() < normal.getZ())
        {
            normal = new Vector3(0, 1 * Math.signum(diff.getY()), 0);
        }
        else if(normal.getZ() < normal.getX() && normal.getZ() < normal.getY())
        {
            normal = new Vector3(0, 0, 1 * Math.signum(diff.getZ()));
        }

        normal = normal.normalize();

        return normal;
    }

    public double getArea()
    {
        return dimensions.getX() * dimensions.getY() * dimensions.getZ();
    }

    public void updatePosition(Vector3 position)
    {
        this.setPosition(position);
    }
}