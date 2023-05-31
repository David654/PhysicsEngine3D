package engine.physics.shape;

import engine.math.vector.Vector3;

public sealed interface PrimitiveShape extends Shape permits Sphere, Box
{
    Vector3 getNormal(Shape shape);
    double getArea();

    Vector3 getPosition();
    Vector3 getDimensions();

    default Vector3 getMin()
    {
        Vector3 p1 = this.getPosition().add(this.getDimensions());
        Vector3 p2 = this.getPosition().subtract(this.getDimensions());
        return p1.min(p2);
    }

    default Vector3 getMax()
    {
        Vector3 p1 = this.getPosition().add(this.getDimensions());
        Vector3 p2 = this.getPosition().subtract(this.getDimensions());
        return p1.max(p2);
    }

    default Vector3 getClosestPointToShape(Shape shape)
    {
        if(shape instanceof Sphere sphere)
        {
            Vector3 result = new Vector3(sphere.getPosition());
            Vector3 min = this.getMin();
            Vector3 max = this.getMax();

            result = result.clamp(min, max);
            return result;
        }

        if(shape instanceof Box box)
        {
            Vector3 result = new Vector3(box.getPosition());
            Vector3 min = this.getMin();
            Vector3 max = this.getMax();

            result = result.clamp(min, max);
            return result;
        }

        return null;
    }
}