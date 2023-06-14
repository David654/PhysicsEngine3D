package engine.physics.shape;

import engine.math.MathUtils;
import engine.math.matrix.Matrix3;
import engine.math.vector.Vector3;

import java.util.ArrayList;
import java.util.Arrays;

public class ConvexPolyhedron implements Shape
{
    protected final Vector3[] vertices;
    protected final int vertexNum;

    public ConvexPolyhedron(Vector3[] vertices, int vertexNum)
    {
        this.vertices = new Vector3[vertexNum];
        this.vertexNum = vertexNum;
        System.arraycopy(vertices, 0, this.vertices, 0, vertexNum);

        //System.out.println(Arrays.toString(getNormals()));
    }

    public void translate(Vector3 position)
    {
        for(int i = 0; i < vertexNum; i++)
        {
            vertices[i] = position;
        }
    }

    public void rotate(Matrix3 rotationMatrix)
    {
        for(int i = 0; i < vertexNum; i++)
        {
            Vector3 vertex = vertices[i];
            Vector3 rotatedVertex = rotationMatrix.multiply(vertex);
            vertices[i] = rotatedVertex;
        }
    }

    public void rotateX(double angle)
    {
        for(int i = 0; i < vertexNum; i++)
        {
            Vector3 vertex = vertices[i];
            Vector3 rotatedVertex = MathUtils.rotateX(vertex, angle);
            vertices[i] = rotatedVertex;
        }
    }

    public void rotateY(double angle)
    {
        for(int i = 0; i < vertexNum; i++)
        {
            Vector3 vertex = vertices[i];
            Vector3 rotatedVertex = MathUtils.rotateY(vertex, angle);
            vertices[i] = rotatedVertex;
        }
    }

    public void rotateZ(double angle)
    {
        for(int i = 0; i < vertexNum; i++)
        {
            Vector3 vertex = vertices[i];
            Vector3 rotatedVertex = MathUtils.rotateZ(vertex, angle);
            vertices[i] = rotatedVertex;
        }
    }

    public Vector3 getCenter()
    {
        Vector3 c = new Vector3();

        for(int i = 0; i < vertexNum; i++)
        {
            c = c.add(vertices[i]);
        }

        return c.multiply(1.0 / vertexNum);
    }

    public Vector3[] getNormals()
    {
        ArrayList<Vector3> normals = new ArrayList<>();

        for(int i = 0; i < vertexNum - 3; i++)
        {
            Vector3 v1 = vertices[i];
            Vector3 v2 = vertices[i + 1];
            Vector3 v3 = vertices[i + 2];

            Vector3 u = v2.subtract(v1);
            Vector3 w = v3.subtract(v1);
            Vector3 n = u.cross(w);
            normals.add(n.normalize());
        }

        return normals.toArray(new Vector3[0]);
    }

    public Vector3 getNormal(Shape shape)
    {
        return null;
    }

    public double getArea()
    {
        double area = 0;



        return 0;
    }

    public void updatePosition(Vector3 position) {

    }
}