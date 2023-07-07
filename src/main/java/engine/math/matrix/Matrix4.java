package engine.math.matrix;

import engine.math.MathUtils;
import engine.math.vector.Vector4;

import java.util.Arrays;

public class Matrix4 implements IMatrix<Matrix4>
{
    private final double[][] matrix;

    public Matrix4()
    {
        this(new double[4][4]);
    }

    public Matrix4(double[][] matrix)
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

    public float[] toFloatArray()
    {
        float[] array = new float[16];

        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                array[i + 4 * j] = (float) matrix[i][j];
            }
        }

        return array;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                sb.append(matrix[i][j]).append(i < 3 ? " | " : "");
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
        Matrix4 matrix = (Matrix4) object;

        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                if(this.matrix[i][j] != matrix.get(i, j))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public Matrix4 zero()
    {
        return new Matrix4();
    }

    public Matrix4 identity()
    {
        return new Matrix4(new double[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
    }

    public Matrix4 abs()
    {
        Matrix4 matrix = new Matrix4();

        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                matrix.set(i, j, MathUtils.abs(this.matrix[i][j]));
            }
        }
        return matrix;
    }

    public Matrix4 min(Matrix4 matrix)
    {
        Matrix4 b = new Matrix4();

        for(int i = 0; i < 4; i++)
        {
            for(int j = 0 ; j < 4; j++)
            {
                b.set(i, j, MathUtils.min(this.get(i, j), matrix.get(i, j)));
            }
        }
        return b;
    }

    public Matrix4 max(Matrix4 matrix)
    {
        Matrix4 b = new Matrix4();

        for(int i = 0; i < 4; i++)
        {
            for(int j = 0 ; j < 4; j++)
            {
                b.set(i, j, MathUtils.max(this.get(i, j), matrix.get(i, j)));
            }
        }
        return b;
    }

    public Matrix4 add(Matrix4 matrix)
    {
        Matrix4 b = new Matrix4();

        for(int i = 0; i < 4; i++)
        {
            for(int j = 0 ; j < 4; j++)
            {
                b.set(i, j, this.get(i, j) + matrix.get(i, j));
            }
        }
        return b;
    }

    public Matrix4 subtract(Matrix4 matrix)
    {
        Matrix4 b = new Matrix4();

        for(int i = 0; i < 4; i++)
        {
            for(int j = 0 ; j < 4; j++)
            {
                b.set(i, j, this.get(i, j) - matrix.get(i, j));
            }
        }
        return b;
    }

    public Matrix4 multiply(Matrix4 matrix)
    {
        Matrix4 b = new Matrix4();

        for(int i = 0; i < 4; i++)
        {
            for(int j = 0 ; j < 4; j++)
            {
                double value = 0;

                for(int k = 0; k < 4; k++)
                {
                    value += this.get(i, k) * matrix.get(k, j);
                }
                b.set(i, j, value);
            }
        }

        return b;
    }

    public Vector4 multiply(Vector4 vector)
    {
        Vector4 b = new Vector4();

        b.setX(this.get(0, 0) * vector.getX() + this.get(0, 1) * vector.getY() + this.get(0, 2) * vector.getZ() + this.get(0, 3) * vector.getW());
        b.setY(this.get(1, 0) * vector.getX() + this.get(1, 1) * vector.getY() + this.get(1, 2) * vector.getZ() + this.get(1, 3) * vector.getW());
        b.setZ(this.get(2, 0) * vector.getX() + this.get(2, 1) * vector.getY() + this.get(2, 2) * vector.getZ() + this.get(2, 3) * vector.getW());
        b.setW(this.get(2, 0) * vector.getX() + this.get(3, 1) * vector.getY() + this.get(3, 2) * vector.getZ() + this.get(3, 3) * vector.getW());

        return b;
    }
}