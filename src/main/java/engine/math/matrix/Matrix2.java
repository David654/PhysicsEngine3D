package engine.math.matrix;

import engine.math.vector.Vector2;

public class Matrix2 implements IMatrix<Matrix2>
{
    private final double[][] matrix;

    public Matrix2()
    {
        this(new double[2][2]);
    }

    public Matrix2(double[][] matrix)
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

        for(int i = 0; i < 2; i++)
        {
            for(int j = 0; j < 2; j++)
            {
                sb.append(matrix[i][j]).append(i < 1 ? " | " : "");
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
        Matrix2 matrix = (Matrix2) object;

        for(int i = 0; i < 2; i++)
        {
            for(int j = 0; j < 2; j++)
            {
                if(this.matrix[i][j] != matrix.get(i, j))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public Matrix2 zero()
    {
        return new Matrix2();
    }

    public Matrix2 identity()
    {
        return new Matrix2(new double[][]{
                {1, 0},
                {0, 1},
        });
    }

    public Matrix2 abs()
    {
        Matrix2 matrix = new Matrix2();

        for(int i = 0; i < 2; i++)
        {
            for(int j = 0; j < 2; j++)
            {
                matrix.set(i, j, Math.abs(this.matrix[i][j]));
            }
        }
        return matrix;
    }

    public Matrix2 min(Matrix2 matrix)
    {
        Matrix2 b = new Matrix2();

        for(int i = 0; i < 2; i++)
        {
            for(int j = 0 ; j < 2; j++)
            {
                b.set(i, j, Math.min(this.get(i, j), matrix.get(i, j)));
            }
        }
        return b;
    }

    public Matrix2 max(Matrix2 matrix)
    {
        Matrix2 b = new Matrix2();

        for(int i = 0; i < 2; i++)
        {
            for(int j = 0 ; j < 2; j++)
            {
                b.set(i, j, Math.max(this.get(i, j), matrix.get(i, j)));
            }
        }
        return b;
    }

    public Matrix2 rotate(double theta)
    {
        return new Matrix2(new double[][]{
                {Math.cos(theta), -Math.sin(theta)},
                {Math.sin(theta), Math.cos(theta)}
        });
    }

    public Matrix2 add(Matrix2 matrix)
    {

        Matrix2 b = new Matrix2();

        for(int i = 0; i < 2; i++)
        {
            for(int j = 0 ; j < 2; j++)
            {
                b.set(i, j, this.get(i, j) + matrix.get(i, j));
            }
        }
        return b;
    }

    public Matrix2 subtract(Matrix2 matrix)
    {
        Matrix2 b = new Matrix2();

        for(int i = 0; i < 2; i++)
        {
            for(int j = 0 ; j < 2; j++)
            {
                b.set(i, j, this.get(i, j) - matrix.get(i, j));
            }
        }
        return b;
    }

    public Vector2 multiply(Vector2 vector)
    {
        Vector2 b = new Vector2();

        b.setX(this.get(0, 0) * vector.getX() + this.get(0, 1) * vector.getY());
        b.setY(this.get(1, 0) * vector.getX() + this.get(1, 1) * vector.getY());

        return b;
    }

    public Matrix2 multiply(Matrix2 matrix)
    {
        Matrix2 b = new Matrix2();

        for(int i = 0; i < 2; i++)
        {
            for(int j = 0 ; j < 2; j++)
            {
                double value = 0;

                for(int k = 0; k < 2; k++)
                {
                    value += this.get(i, k) * matrix.get(k, j);
                }
                b.set(i, j, value);
            }
        }

        return b;
    }
}