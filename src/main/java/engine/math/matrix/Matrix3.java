package engine.math.matrix;

import engine.math.MathUtils;
import engine.math.vector.Vector3;

public class Matrix3 implements IMatrix<Matrix3>
{
    private final double[][] matrix;

    public Matrix3()
    {
        this(new double[3][3]);
    }

    public Matrix3(double[][] matrix)
    {
        this.matrix = matrix;
    }

    public double get(int i, int j)
    {
        return matrix[i][j];
    }

    public void set(int i, int j, double value)
    {
        matrix[i][j] = value;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                sb.append(matrix[i][j]).append(i < 2 ? " | " : "");
            }
            sb.append("\n--------");
        }

        return sb.toString();
    }

    public int hashCode()
    {
        return toString().hashCode();
    }

    public boolean equals(Object object)
    {
        Matrix3 matrix = (Matrix3) object;

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(this.matrix[i][j] != matrix.get(i, j))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public static Matrix3 zero()
    {
        return new Matrix3();
    }

    public static Matrix3 identity()
    {
        return new Matrix3(new double[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1},
        });
    }

    public Matrix3 abs()
    {
        Matrix3 matrix = new Matrix3();

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                matrix.set(i, j, MathUtils.abs(this.matrix[i][j]));
            }
        }
        return matrix;
    }

    public Matrix3 min(Matrix3 matrix)
    {
        Matrix3 b = new Matrix3();

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0 ; j < 3; j++)
            {
                b.set(i, j, MathUtils.min(this.get(i, j), matrix.get(i, j)));
            }
        }
        return b;
    }

    public Matrix3 max(Matrix3 matrix)
    {
        Matrix3 b = new Matrix3();

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0 ; j < 3; j++)
            {
                b.set(i, j, MathUtils.max(this.get(i, j), matrix.get(i, j)));
            }
        }
        return b;
    }

    public static Matrix3 rotateX(double theta)
    {
        return new Matrix3(new double[][]{
                {1, 0, 0},
                {0, Math.cos(theta), -Math.sin(theta)},
                {0, Math.sin(theta), Math.cos(theta)}
        });
    }

    public static Matrix3 rotateY(double theta)
    {
        return new Matrix3(new double[][]{
                {Math.cos(theta), 0, Math.sin(theta)},
                {0, 1, 0},
                {-Math.sin(theta), 0, Math.cos(theta)}
        });
    }

    public static Matrix3 rotateZ(double theta)
    {
        return new Matrix3(new double[][]{
                {Math.cos(theta), -Math.sin(theta), 0},
                {Math.sin(theta), Math.cos(theta), 0},
                {0, 0, 1}
        });
    }

    public Matrix3 add(Matrix3 matrix)
    {
        Matrix3 b = new Matrix3();

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0 ; j < 3; j++)
            {
                b.set(i, j, this.get(i, j) + matrix.get(i, j));
            }
        }
        return b;
    }

    public Matrix3 subtract(Matrix3 matrix)
    {
        Matrix3 b = new Matrix3();

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0 ; j < 3; j++)
            {
                b.set(i, j, this.get(i, j) - matrix.get(i, j));
            }
        }
        return b;
    }

    public Vector3 multiply(Vector3 vector)
    {
        Vector3 b = new Vector3();

        b.setX(this.get(0, 0) * vector.getX() + this.get(0, 1) * vector.getY() + this.get(0, 2) * vector.getZ());
        b.setY(this.get(1, 0) * vector.getX() + this.get(1, 1) * vector.getY() + this.get(1, 2) * vector.getZ());
        b.setZ(this.get(2, 0) * vector.getX() + this.get(2, 1) * vector.getY() + this.get(2, 2) * vector.getZ());

        return b;
    }

    public Matrix3 multiply(Matrix3 matrix)
    {
        Matrix3 b = new Matrix3();

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0 ; j < 3; j++)
            {
                double value = 0;

                for(int k = 0; k < 3; k++)
                {
                    value += this.get(i, k) * matrix.get(k, j);
                }
                b.set(i, j, value);
            }
        }

        return b;
    }

    public Matrix3 translate(Vector3 vector)
    {
        return new Matrix3(new double[][]{
                {1, 0, 0},
                {0, 1, 0},
                {vector.getX(), vector.getY(), vector.getZ()}
        });
    }
}
