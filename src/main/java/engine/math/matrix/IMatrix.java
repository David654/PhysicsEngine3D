package engine.math.matrix;

public interface IMatrix<Matrix>
{
    double get(int i, int j);
    void set(int i, int j, double value);
    String toString();
    int hashCode();
    boolean equals(Object object);
    Matrix abs();
    Matrix min(Matrix matrix);
    Matrix max(Matrix matrix);
    Matrix add(Matrix matrix);
    Matrix subtract(Matrix matrix);
    Matrix multiply(Matrix matrix);

    /*private final double[][] math.matrix;

    public Matrix(int m, int n)
    {
        math.matrix = new double[m][n];
    }

    public Matrix(double[]... rows)
    {
        math.matrix = new double[rows.length][rows[0].length];

        for(int i = 0; i < rows.length; i++)
        {
            System.arraycopy(rows[i], 0, math.matrix[i], 0, rows[0].length);
        }
    }

    public int getRows()
    {
        return math.matrix.length;
    }

    public int getColumns()
    {
        return math.matrix[0].length;
    }

    public Vector get(int i)
    {
        return new Vector(math.matrix[i]);
    }

    public double get(int i, int j)
    {
        return math.matrix[i][j];
    }

    public void set(int i, Vector values)
    {
        for(int j = 0; j < values.length(); j++)
        {
            math.matrix[i][j] = values.get(j);
        }
    }

    private void copy(Matrix a)
    {
        for(int i = 0; i < a.getRows(); i++)
        {
            System.arraycopy(a.math.matrix[i], 0, math.matrix[i], 0, a.getColumns());
        }
    }

    public void set(int i, int j, double val)
    {
        math.matrix[i][j] = val;
    }

    public void zero()
    {
        for(int i = 0; i < math.matrix.length; i++)
        {
            Arrays.fill(math.matrix[i], 0);
        }
    }

    public void abs()
    {
        for(int i = 0; i < this.getRows(); i++)
        {
            for(int j = 0; j < this.getColumns(); j++)
            {
                double value = math.matrix[i][j];
                math.matrix[i][j] += Math.abs(value);
            }
        }
    }

    public Matrix min(Matrix a)
    {
        Matrix b = new Matrix(this.getRows(), this.getColumns());

        if(this.getRows() != a.getRows() && this.getColumns() != a.getColumns())
        {
            System.err.println("Matrices have different sizes.");
        }
        else
        {
            for(int i = 0; i < this.getRows(); i++)
            {
                for(int j = 0 ; j < this.getColumns(); j++)
                {
                    b.set(i, j, Math.min(this.get(i, j), a.get(i, j)));
                }
            }
        }
        return b;
    }

    public Matrix max(Matrix a)
    {
        Matrix b = new Matrix(this.getRows(), this.getColumns());

        if(this.getRows() != a.getRows() && this.getColumns() != a.getColumns())
        {
            System.err.println("Matrices have different sizes.");
        }
        else
        {
            for(int i = 0; i < this.getRows(); i++)
            {
                for(int j = 0 ; j < this.getColumns(); j++)
                {
                    b.set(i, j, Math.max(this.get(i, j), a.get(i, j)));
                }
            }
        }
        return b;
    }

    public void clamp(double min, double max)
    {
        for(int i = 0; i < this.getRows(); i++)
        {
            for(int j = 0; j < this.getColumns(); j++)
            {
                double value = this.get(i, j);
                this.set(i, j, Math.max(min, Math.min(value, max)));
            }
        }
    }

    public void normalize()
    {
        for(int i = 0; i < this.getRows(); i++)
        {
            for(int j = 0; j < this.getColumns(); j++)
            {
                double value = this.get(i, j);
                this.set(i, j, value / this.getRows());
            }
        }
    }

    public void add(Matrix a)
    {
        if(this.getRows() != a.getRows() && this.getColumns() != a.getColumns())
        {
            System.err.println("Matrices have different sizes.");
        }
        else
        {
            for(int i = 0; i < this.getRows(); i++)
            {
                for(int j = 0 ; j < this.getColumns(); j++)
                {
                    math.matrix[i][j] += a.get(i, j);
                }
            }
        }
    }

    public void subtract(Matrix a)
    {
        if(this.getRows() != a.getRows() && this.getColumns() != a.getColumns())
        {
            System.err.println("Matrices have different sizes.");
        }
        else
        {
            for(int i = 0; i < this.getRows(); i++)
            {
                for(int j = 0 ; j < this.getColumns(); j++)
                {
                    math.matrix[i][j] -= a.get(i, j);
                }
            }
        }
    }

    public Matrix multiply(Matrix a)
    {
        if(this.getColumns() == a.getRows())
        {
            Matrix b = new Matrix(this.getRows(), a.getColumns());

            for(int i = 0; i < this.getRows(); i++)
            {
                for(int j = 0 ; j < a.getColumns(); j++)
                {
                    double sum = 0;
                    for(int k = 0; k < a.getRows(); k++)
                    {
                        sum += this.get(i, k) * a.get(k, j);
                    }
                    b.set(i, j, sum);
                }
            }
            return b;
        }
        else
        {
            System.err.println("Matrices have different sizes.");
        }

        return null;
    }

    public void multiply(double scalar)
    {
        for(int i = 0; i < this.getRows(); i++)
        {
            for(int j = 0 ; j < this.getColumns(); j++)
            {
                double value = this.get(i, j);
                this.set(i, j, value * scalar);
            }
        }
    }

    public Matrix transpose()
    {
        Matrix a = new Matrix(this.getColumns(), this.getRows());

        for(int i = 0; i < this.getRows(); i++)
        {
            for(int j = 0 ; j < this.getColumns(); j++)
            {
                double value = this.get(i, j);
                a.set(j, i, value);
            }
        }
        return a;
    }

    public double det()
    {

    }

    public boolean equalTo(Matrix a)
    {
        if(this.getRows() == a.getRows() && this.getColumns() == a.getColumns())
        {
            for(int i = 0; i < this.getRows(); i++)
            {
                for(int j = 0 ; j < this.getColumns(); j++)
                {
                    if(this.get(i, j) != a.get(i, j))
                    {
                        return false;
                    }
                }
            }
        }
        else
        {
            return false;
        }
        return true;
    }

    public static void fill(Matrix a, double min, double max)
    {
        for(int i = 0; i < a.getRows(); i++)
        {
            for(int j = 0; j < a.getColumns(); j++)
            {
                a.set(i, j, Math.random() * (max + 1) + min);
            }
        }
    }

    public String toString()
    {
        StringBuilder s = new StringBuilder();

        for(int i = 0; i < math.matrix.length; i++)
        {
            s.append("[");
            for(int j = 0; j < math.matrix[0].length; j++)
            {
                s.append(math.matrix[i][j]).append(j < math.matrix[0].length - 1 ? ", " : "");
            }
            s.append("]").append(i < math.matrix.length - 1 ? ", " : "");
        }

        return s.toString();
    }**/
}