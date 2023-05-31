package engine.physics.shape;

import engine.math.matrix.Matrix3;
import engine.math.vector.Vector3;

public class ConvexPolyhedron implements Shape
{
    protected final Vector3[] vertices;
    protected final int vertexNum;

    public ConvexPolyhedron(Vector3[] vertices, int vertexNum)
    {
        this.vertices = new Vector3[vertexNum];
        this.vertexNum = vertexNum;
        System.arraycopy(vertices, 0, this.vertices, 0, vertexNum);
    }

    protected void translate(Vector3 position)
    {
        for(int i = 0; i < vertexNum; i++)
        {
            vertices[i] = position;
        }
    }

    protected void rotate(Matrix3 rotationMatrix)
    {
        for(int i = 0; i < vertexNum; i++)
        {
            Vector3 vertex = vertices[i];
            Vector3 rotatedVertex = rotationMatrix.multiply(vertex);
            vertices[i] = rotatedVertex;
        }
    }

    public Vector3 getNormal(Shape shape) {
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