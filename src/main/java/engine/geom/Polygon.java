package engine.geom;

import com.jogamp.opengl.GL2;
import engine.graphics.Drawable;
import engine.math.MathUtils;
import engine.math.matrix.Matrix3;
import engine.math.vector.Vector3;
import engine.util.ColorUtils;

import java.awt.*;

public class Polygon implements GeometricalTransformation, Drawable
{
    protected Vector3[] vertices;
    protected Vector3 normal;
    protected Vector3 color;

    public Polygon(Color color, Vector3... vertices)
    {
        this.vertices = vertices;
        this.normal = normal;
        this.color = ColorUtils.toGLColor(color);
    }

    public Polygon(Vector3... vertices)
    {
        this(new Color(255, 255, 255), vertices);
    }

    public Vector3[] getVertices()
    {
        return vertices;
    }

    public void setVertices(Vector3[] vertices)
    {
        this.vertices = vertices;
    }

    public Vector3 getNormal()
    {
        return normal;
    }

    public void setNormal(Vector3 normal)
    {
        this.normal = normal;
    }

    public Color getColor()
    {
        return ColorUtils.toColor(color);
    }

    public void setColor(Color color)
    {
        this.color = ColorUtils.toGLColor(color);
        this.color = new Vector3(Math.random(), Math.random(), Math.random());
    }

    public void translate(Vector3 point)
    {
        for(int i = 0; i < vertices.length; i++)
        {
            Vector3 vertex = vertices[i];
            vertices[i] = vertex.add(point);
        }
    }

    public void scale(Vector3 scalar)
    {
        for(int i = 0; i < vertices.length; i++)
        {
            Vector3 vertex = vertices[i];
            vertices[i] = vertex.multiply(scalar);
        }
    }

    public void rotate(Matrix3 rotationMatrix)
    {
        for(int i = 0; i < vertices.length; i++)
        {
            Vector3 vertex = vertices[i];
            Vector3 rotatedVertex = rotationMatrix.multiply(vertex);
            vertices[i] = rotatedVertex;
        }
    }

    public void rotateX(double angle)
    {
        for(int i = 0; i < vertices.length; i++)
        {
            Vector3 vertex = vertices[i];
            Vector3 rotatedVertex = MathUtils.rotateX(vertex, angle);
            vertices[i] = rotatedVertex;
        }
    }

    public void rotateY(double angle)
    {
        for(int i = 0; i < vertices.length; i++)
        {
            Vector3 vertex = vertices[i];
            Vector3 rotatedVertex = MathUtils.rotateY(vertex, angle);
            vertices[i] = rotatedVertex;
        }
    }

    public void rotateZ(double angle)
    {
        for(int i = 0; i < vertices.length; i++)
        {
            Vector3 vertex = vertices[i];
            Vector3 rotatedVertex = MathUtils.rotateZ(vertex, angle);
            vertices[i] = rotatedVertex;
        }
    }

    public void init(GL2 gl)
    {

    }

    public void draw(GL2 gl)
    {
        gl.glBegin(GL2.GL_TRIANGLES);

        gl.glColor3d(color.getX(), color.getY(), color.getZ());

        for(int i = 0; i < vertices.length; i++)
        {
            Vector3 vertex = vertices[i];

            gl.glVertex3d(vertex.getX(), vertex.getY(), vertex.getZ());
        }

        gl.glEnd();
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for(Vector3 vertex : vertices)
        {
            sb.append(vertex).append("\n");
        }

        return sb.toString();
    }

    public double getMaxVertexDistance()
    {
        double maxVertexDist = -1;

        for(int i = 0; i < vertices.length; i++)
        {
            Vector3 vertex = vertices[i];
            double vertexDist = vertex.length();

            if(vertexDist > maxVertexDist)
            {
                maxVertexDist = vertexDist;
            }
        }

        return maxVertexDist;
    }
}