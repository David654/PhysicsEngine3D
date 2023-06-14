package engine.math.vector;

public class Vector2 implements IVector<Vector2>
{
    private double x;
    private double y;

    public Vector2()
    {
        this(0, 0);
    }

    public Vector2(double x, double y)
    {
        this.x = x;
        this.y = y;
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

    public String toString()
    {
        return "X: " + x + "; Y: " + y;
    }

    public int hashCode()
    {
        return toString().hashCode();
    }

    public boolean equals(Object object)
    {
        Vector2 vector = (Vector2) object;
        return x == vector.x && y == vector.y;
    }

    public static Vector2 unitX()
    {
        return new Vector2(1, 0);
    }

    public static Vector2 unitY()
    {
        return new Vector2(0, 1);
    }

    public static Vector2 zero()
    {
        return new Vector2();
    }

    public Vector2 abs()
    {
        return new Vector2(Math.abs(x), Math.abs(y));
    }

    public double length()
    {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public Vector2 add(Vector2 vector)
    {
        return new Vector2(x + vector.x, y + vector.y);
    }

    public Vector2 subtract(Vector2 vector)
    {
        return new Vector2(x - vector.x, y - vector.y);
    }

    public Vector2 multiply(Vector2 vector)
    {
        return new Vector2(x * vector.x, y * vector.y);
    }

    public Vector2 multiply(double scalar)
    {
        return new Vector2(x * scalar, y * scalar);
    }

    public Vector2 divide(Vector2 vector)
    {
        return new Vector2(x / vector.x, y / vector.y);
    }

    public Vector2 divide(double scalar)
    {
        return new Vector2(x / scalar, y / scalar);
    }

    public Vector2 pow(double power)
    {
        return new Vector2(Math.pow(x, power), Math.pow(y, power));
    }

    public Vector2 max(Vector2 vector)
    {
        return new Vector2(Math.max(x, vector.x), Math.max(y, vector.y));
    }

    public Vector2 min(Vector2 vector)
    {
        return new Vector2(Math.min(x, vector.x), Math.min(y, vector.y));
    }

    public double distance(Vector2 vector)
    {
        return Math.sqrt(Math.pow(x - vector.x, 2) + Math.pow(y - vector.y, 2));
    }

    public Vector2 clamp(Vector2 min, Vector2 max)
    {
        return this.min(max).max(min);
    }

    public Vector2 normalize()
    {
        if(length() == 0)
        {
            return new Vector2();
        }
        return new Vector2(x / length(), y / length());
    }

    public double dot(Vector2 vector)
    {
        return x * vector.x + y * vector.y;
    }

    public Vector2 cross(Vector2 vector)
    {
        return new Vector2(x * vector.y, y * vector.x);
    }

    public Vector2 reflect(Vector2 normal)
    {
        return this.multiply(normal).multiply(normal).multiply(2).subtract(this);
    }

    public Vector2 negate()
    {
        return this.multiply(-1);
    }

    public Vector2 lerp(Vector2 vector, double amount)
    {
        return vector.subtract(this).multiply(amount).add(this);
    }
}