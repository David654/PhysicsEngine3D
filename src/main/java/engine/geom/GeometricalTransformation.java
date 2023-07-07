package engine.geom;

import engine.math.matrix.Matrix3;
import engine.math.vector.Vector3;

public interface GeometricalTransformation
{
    void translate(Vector3 point);
    void scale(Vector3 scalar);
    default void scale(double scalar)
    {
        scale(new Vector3(scalar));
    }
    void rotate(Matrix3 rotationMatrix);
    void rotateX(double angle);
    void rotateY(double angle);
    void rotateZ(double angle);
}