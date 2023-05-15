package engine.math.vector;

public interface IVector<Vector>
{
    String toString();
    int hashCode();
    boolean equals(Object object);

    double length();
    double distance(Vector vector);
    Vector max(Vector vector);
    Vector min(Vector vector);
    Vector abs();
    Vector clamp(Vector min, Vector max);
    Vector lerp(Vector vector, double amount);
    Vector add(Vector vector);
    Vector subtract(Vector vector);
    Vector multiply(Vector vector);
    Vector multiply(double scalar);
    Vector divide(Vector vector);
    Vector divide(double scalar);
    double dot(Vector vector);
    Vector cross(Vector vector);
    Vector reflect(Vector normal);
    Vector normalize();
    Vector negate();
}