package engine.math.vector;

import engine.math.matrix.Matrix3;

public class Vector4 implements IVector<Vector4>
{
    private double x;
    private double y;
    private double z;
    private double w;

    public Vector4()
    {
        this(0, 0, 0, 0);
    }

    public Vector4(double x, double y, double z, double w)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public double getZ()
    {
        return z;
    }

    public void setZ(double z)
    {
        this.z = z;
    }

    public double getW()
    {
        return w;
    }

    public void setW(double w)
    {
        this.w = w;
    }

    public String toString()
    {
        return "X: " + x + "; Y: " + y + "; Z: " + z + "; W: " + w;
    }

    public int hashCode()
    {
        return toString().hashCode();
    }

    public boolean equals(Object object)
    {
        Vector4 vector = (Vector4) object;
        return x == vector.x && y == vector.y && z == vector.z;
    }

    public static Vector4 unitX()
    {
        return new Vector4(1, 0, 0, 0);
    }

    public static Vector4 unitY()
    {
        return new Vector4(0, 1, 0, 0);
    }

    public static Vector4 unitZ()
    {
        return new Vector4(0, 0, 1, 0);
    }

    public static Vector4 unitW()
    {
        return new Vector4(0, 0, 0, 1);
    }

    public static Vector4 zero()
    {
        return new Vector4();
    }

    public Vector4 abs()
    {
        return new Vector4(Math.abs(x), Math.abs(y), Math.abs(z), Math.abs(w));
    }

    public double length()
    {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public Vector4 add(Vector4 vector)
    {
        return new Vector4(x + vector.x, y + vector.y, z + vector.z, w + vector.w);
    }

    public Vector4 subtract(Vector4 vector)
    {
        return new Vector4(x - vector.x, y - vector.y, z - vector.z, w - vector.w);
    }

    public Vector4 multiply(Vector4 vector)
    {
        return new Vector4(x * vector.x, y * vector.y, z * vector.z, w * vector.w);
    }

    public Vector4 multiply(double scalar)
    {
        return new Vector4(x * scalar, y * scalar, z * scalar, w * scalar);
    }

    public Vector4 divide(Vector4 vector)
    {
        return new Vector4(x / vector.x, y / vector.y, z / vector.z, w / vector.w);
    }

    public Vector4 divide(double scalar)
    {
        return new Vector4(x / scalar, y / scalar, z / scalar, w / scalar);
    }

    public Vector4 max(Vector4 vector)
    {
        return new Vector4(Math.max(x, vector.x), Math.max(y, vector.y), Math.max(z, vector.z), Math.max(w, vector.w));
    }

    public Vector4 min(Vector4 vector)
    {
        return new Vector4(Math.min(x, vector.x), Math.min(y, vector.y), Math.min(z, vector.z), Math.min(w, vector.w));
    }

    public double distance(Vector4 vector)
    {
        return (double) Math.sqrt(Math.pow(x - vector.x, 2) + Math.pow(y - vector.y, 2) + Math.pow(z - vector.z, 2));
    }

    public Vector4 clamp(Vector4 min, Vector4 max)
    {
        return this.min(max).max(min);
    }

    public Vector4 normalize()
    {
        return new Vector4(x / length(), y / length(), z / length(), w / length());
    }

    public double dot(Vector4 vector)
    {
        return x * vector.x + y * vector.y + z * vector.z;
    }

    public Vector4 cross(Vector4 vector)
    {
        return null;
    }

    public Vector4 reflect(Vector4 normal)
    {
        return this.multiply(normal).multiply(normal).multiply(2).subtract(this);
    }

    public Vector4 negate()
    {
        return this.multiply(-1);
    }

    public Vector4 lerp(Vector4 vector, double amount)
    {
        return vector.subtract(this).multiply(amount).add(this);
    }

    public Vector4 multiply(Matrix3 matrix)
    {
        Vector4 vector = new Vector4();

        vector.setX(vector.x + x * matrix.get(0, 0));
        vector.setX(vector.x + y * matrix.get(0, 1));
        vector.setX(vector.x + z * matrix.get(0, 2));

        vector.setY(vector.y + x * matrix.get(1, 0));
        vector.setY(vector.y + y * matrix.get(1, 1));
        vector.setY(vector.y + z * matrix.get(1, 2));

        vector.setZ(vector.z + x * matrix.get(2, 0));
        vector.setZ(vector.z + y * matrix.get(2, 1));
        vector.setZ(vector.z + z * matrix.get(2, 2));
        return vector;
    }
}