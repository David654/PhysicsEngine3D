package engine.graphics;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import engine.geom.GeometricalTransformation;
import engine.math.MathUtils;
import engine.math.matrix.Matrix3;
import engine.math.vector.Vector3;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class VertexBufferObject implements BufferObject, GeometricalTransformation, Disposable
{
    private final float[] vertexArray;
    private final float[] textureArray;
    private final int[] indexArray;

    private final ArrayList<IntBuffer> vaoList;
    private final ArrayList<IntBuffer> vboList;

    private FloatBuffer vertexBuffer;
    private IntBuffer vertexVBO;

    private int vaoID;

    public VertexBufferObject(float[] vertexArray, float[] textureArray, int[] indexArray)
    {
        this.vertexArray = vertexArray;
        this.textureArray = textureArray;
        this.indexArray = indexArray;

        vaoList = new ArrayList<>();
        vboList = new ArrayList<>();
    }

    public float[] getVertexArray()
    {
        return vertexArray;
    }

    public float[] getTextureArray()
    {
        return textureArray;
    }

    public int[] getIndexArray()
    {
        return indexArray;
    }

    public void init(GL2 gl)
    {
        vaoID = createVAO(gl);
        bindIndicesBuffer(gl, indexArray);
        storeDataInAttributeList(gl, 0, vertexArray);
        unbindVAO(gl);
    }

    public int createVAO(GL2 gl)
    {
        IntBuffer vao = Buffers.newDirectIntBuffer(1);
        vaoList.add(vao);
        gl.glGenVertexArrays(1, vao);
        gl.glBindVertexArray(vao.get(0));
        return vao.get(0);
    }

    public void storeDataInAttributeList(GL2 gl, int attributeNumber, float[] data)
    {
        vertexVBO = Buffers.newDirectIntBuffer(1);
        vboList.add(vertexVBO);
        gl.glGenBuffers(1, vertexVBO);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, vertexVBO.get(0));
        vertexBuffer = storeDataInFloatBuffer(data);
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, (long) vertexBuffer.limit() * Buffers.SIZEOF_FLOAT, vertexBuffer, GL2.GL_DYNAMIC_DRAW);
        gl.glVertexAttribPointer(attributeNumber, 3, GL2.GL_FLOAT, false, 0, 0);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
    }

    public void unbindVAO(GL2 gl)
    {
        gl.glBindVertexArray(0);
    }

    public void bindIndicesBuffer(GL2 gl, int[] indices)
    {
        IntBuffer vbo = Buffers.newDirectIntBuffer(1);
        vboList.add(vbo);
        gl.glGenBuffers(1, vbo);
        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, vbo.get(0));
        IntBuffer buffer = storeDataInIntBuffer(indices);
        gl.glBufferData(GL2.GL_ELEMENT_ARRAY_BUFFER, (long) buffer.limit() * Buffers.SIZEOF_INT, buffer, GL2.GL_STATIC_DRAW);
    }

    private IntBuffer storeDataInIntBuffer(int[] data)
    {
        return Buffers.newDirectIntBuffer(data);
    }

    public FloatBuffer storeDataInFloatBuffer(float[] data)
    {
        return Buffers.newDirectFloatBuffer(data);
    }

    public void update(GL2 gl)
    {
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, vertexVBO.get(0));
        gl.glBufferSubData(GL2.GL_ARRAY_BUFFER, 0, (long) vertexBuffer.limit() * Buffers.SIZEOF_FLOAT, vertexBuffer);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
    }

    public void draw(GL2 gl)
    {
        update(gl);

        gl.glBindVertexArray(vaoID);
        gl.glEnableVertexAttribArray(0);
        //gl.glDrawArrays(GL2.GL_TRIANGLES, 0, vertexArray.length / 3);
        gl.glDrawElements(GL2.GL_TRIANGLES, indexArray.length, GL2.GL_UNSIGNED_INT, 0);
        gl.glDisableVertexAttribArray(0);
        gl.glBindVertexArray(0);
    }

    public void translate(Vector3 point)
    {
        for(int i = 0; i < vertexArray.length; i++)
        {
            if(i % 3 == 0)
            {
                vertexArray[i] += point.getX();
            }
            else if(i % 3 == 1)
            {
                vertexArray[i] += point.getY();
            }
            else
            {
                vertexArray[i] += point.getZ();
            }
        }

        vertexBuffer = FloatBuffer.wrap(vertexArray);
    }

    public void scale(Vector3 scalar)
    {
        System.out.println(vertexArray.length);
        for(int i = 0; i < vertexArray.length / 3; i++)
        {
            Vector3 vertex = new Vector3(vertexArray[i * 3], vertexArray[i * 3 + 1], vertexArray[i * 3 + 2]);
            vertex = vertex.multiply(scalar);

            vertexArray[i * 3] = (float) vertex.getX();
            vertexArray[i * 3 + 1] = (float) vertex.getY();
            vertexArray[i * 3 + 2] = (float) vertex.getZ();
        }

        vertexBuffer = FloatBuffer.wrap(vertexArray);
    }

    public void rotate(Matrix3 rotationMatrix)
    {
        for(int i = 0; i < vertexArray.length / 3; i++)
        {
            Vector3 vertex = new Vector3(vertexArray[i * 3], vertexArray[i * 3 + 1], vertexArray[i * 3 + 2]);
            Vector3 rotatedVertex = rotationMatrix.multiply(vertex);

            vertexArray[i * 3] = (float) rotatedVertex.getX();
            vertexArray[i * 3 + 1] = (float) rotatedVertex.getY();
            vertexArray[i * 3 + 2] = (float) rotatedVertex.getZ();
        }

        vertexBuffer = FloatBuffer.wrap(vertexArray);
    }

    public void rotateX(double angle)
    {
        for(int i = 0; i < vertexArray.length / 3; i++)
        {
            Vector3 vertex = new Vector3(vertexArray[i * 3], vertexArray[i * 3 + 1], vertexArray[i * 3 + 2]);
            Vector3 rotatedVertex = MathUtils.rotateX(vertex, angle);

            vertexArray[i * 3] = (float) rotatedVertex.getX();
            vertexArray[i * 3 + 1] = (float) rotatedVertex.getY();
            vertexArray[i * 3 + 2] = (float) rotatedVertex.getZ();
        }

        vertexBuffer = FloatBuffer.wrap(vertexArray);
    }

    public void rotateY(double angle)
    {
        for(int i = 0; i < vertexArray.length / 3; i++)
        {
            Vector3 vertex = new Vector3(vertexArray[i * 3], vertexArray[i * 3 + 1], vertexArray[i * 3 + 2]);
            Vector3 rotatedVertex = MathUtils.rotateY(vertex, angle);

            vertexArray[i * 3] = (float) rotatedVertex.getX();
            vertexArray[i * 3 + 1] = (float) rotatedVertex.getY();
            vertexArray[i * 3 + 2] = (float) rotatedVertex.getZ();
        }

        vertexBuffer = FloatBuffer.wrap(vertexArray);
    }

    public void rotateZ(double angle)
    {
        for(int i = 0; i < vertexArray.length / 3; i++)
        {
            Vector3 vertex = new Vector3(vertexArray[i * 3], vertexArray[i * 3 + 1], vertexArray[i * 3 + 2]);
            Vector3 rotatedVertex = MathUtils.rotateZ(vertex, angle);

            vertexArray[i * 3] = (float) rotatedVertex.getX();
            vertexArray[i * 3 + 1] = (float) rotatedVertex.getY();
            vertexArray[i * 3 + 2] = (float) rotatedVertex.getZ();
        }

        vertexBuffer = FloatBuffer.wrap(vertexArray);
    }

    public void dispose(GL2 gl)
    {
        for(IntBuffer vao : vaoList)
        {
            gl.glDeleteVertexArrays(1, vao);
        }

        for(IntBuffer vbo : vboList)
        {
            gl.glDeleteBuffers(1, vbo);
        }
    }
}