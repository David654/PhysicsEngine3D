package engine.physics.shape;

import engine.math.vector.Vector3;

public interface Shape
{
    Vector3 getNormal(Shape shape);
    double getArea();
    void updatePosition(Vector3 position);
}