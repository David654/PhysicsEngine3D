package engine.geom;

import com.jogamp.opengl.GL2;
import engine.graphics.Disposable;
import engine.graphics.Drawable;
import engine.graphics.VertexBufferObject;
import engine.math.matrix.Matrix3;
import engine.math.vector.Vector3;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GeometricObject implements GeometricalTransformation, Drawable, Disposable
{
    protected Polygon[] polygons;
    protected VertexBufferObject vao;

    public GeometricObject(Polygon[] polygons)
    {
        this.polygons = polygons;
    }

    public GeometricObject(Polygon[] polygons, Color[] colors)
    {
        this.polygons = polygons;

        for(int i = 0; i < polygons.length; i++)
        {
            polygons[i].setColor(colors[i]);
        }
    }

    public GeometricObject(Polygon[] polygons, Color color)
    {
        this.polygons = polygons;

        for(int i = 0; i < polygons.length; i++)
        {
            polygons[i].setColor(color);
        }
    }

    public GeometricObject(String filePath, Color color)
    {
        GeometricObject geometricObject = OBJParser.readOBJFile(filePath);
        this.polygons = geometricObject.polygons;
        this.vao = geometricObject.getVAO();

        for(int i = 0; i < polygons.length; i++)
        {
            polygons[i].setColor(color);
        }
    }

    public Polygon[] getPolygons()
    {
        return polygons;
    }

    public void setPolygons(Polygon[] polygons)
    {
        this.polygons = polygons;
    }

    public float[] getVertices()
    {
        ArrayList<Vector3> vertices = new ArrayList<>();

        for(int i = 0; i < polygons.length; i++)
        {
            Polygon polygon = polygons[i];

            vertices.addAll(Arrays.asList(polygon.getVertices()));
        }

        float[] vertexArray = new float[vertices.size() * 3];

        for(int i = 0; i < vertices.size(); i++)
        {
            Vector3 vertex = vertices.get(i);
            vertexArray[i * 3] = (float) vertex.getX();
            vertexArray[i * 3 + 1] = (float) vertex.getY();
            vertexArray[i * 3 + 2] = (float) vertex.getZ();
        }

        return vertexArray;
    }

    public VertexBufferObject getVAO()
    {
        return vao;
    }

    public void setVAO(VertexBufferObject vao)
    {
        this.vao = vao;
    }

    public void translate(Vector3 point)
    {
        for(int i = 0; i < polygons.length; i++)
        {
            Polygon polygon = polygons[i];
            polygon.translate(point);
        }

        vao.translate(point);
    }

    public void scale(Vector3 scalar)
    {
        for(int i = 0; i < polygons.length; i++)
        {
            Polygon polygon = polygons[i];
            polygon.scale(scalar);
        }

        vao.scale(scalar);
    }

    public void autoScale()
    {
        double maxVertexDist = -1;

        for(int i = 0; i < polygons.length; i++)
        {
            Polygon polygon = polygons[i];

            double vertexDist = polygon.getMaxVertexDistance();

            if(vertexDist > maxVertexDist)
            {
                maxVertexDist = vertexDist;
            }
        }

        double scale = 1 / maxVertexDist;
        scale(scale);
    }

    public void rotate(Matrix3 rotationMatrix)
    {
        for(int i = 0; i < polygons.length; i++)
        {
            Polygon polygon = polygons[i];
            polygon.rotate(rotationMatrix);
        }

        vao.rotate(rotationMatrix);
    }

    public void rotateX(double angle)
    {
        for(int i = 0; i < polygons.length; i++)
        {
            Polygon polygon = polygons[i];
            polygon.rotateX(angle);
        }

        vao.rotateX(angle);
    }

    public void rotateY(double angle)
    {
        for(int i = 0; i < polygons.length; i++)
        {
            Polygon polygon = polygons[i];
            polygon.rotateY(angle);
        }

        vao.rotateY(angle);
    }

    public void rotateZ(double angle)
    {
        for(int i = 0; i < polygons.length; i++)
        {
            Polygon polygon = polygons[i];
            polygon.rotateX(angle);
        }

        vao.rotateZ(angle);
    }

    public void init(GL2 gl)
    {
        for(int i = 0; i < polygons.length; i++)
        {
            Polygon polygon = polygons[i];
            polygon.init(gl);
        }

        vao.init(gl);
    }

    public void draw(GL2 gl)
    {
        /*for(int i = 0; i < polygons.length; i++)
        {
            Polygon polygon = polygons[i];
            polygon.draw(gl);
        }**/
        vao.draw(gl);
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for(Polygon p : polygons)
        {
            sb.append(p.toString()).append("\n\n");
        }

        return sb.toString();
    }

    public void dispose(GL2 gl)
    {
        //vao.dispose(gl);
    }
}