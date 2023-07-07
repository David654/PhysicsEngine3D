package engine.graphics;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class UniformBufferObject implements BufferObject, Disposable
{
    private final Buffer buffer;
    private IntBuffer uniformBuffer;

    public UniformBufferObject(float[] data)
    {
        buffer = Buffers.newDirectFloatBuffer(data);
    }

    public UniformBufferObject(int[] data)
    {
        buffer = Buffers.newDirectIntBuffer(data);
    }


    public void init(GL2 gl)
    {

    }

    private int create(GL2 gl)
    {
        uniformBuffer = Buffers.newDirectIntBuffer(1);
        gl.glGenBuffers(1, uniformBuffer);
        return uniformBuffer.get(0);
    }

    public void bind(GL2 gl, int shaderProgramID, String name)
    {
        int bufferIndex = create(gl);
        gl.glBindBuffer(GL2.GL_UNIFORM_BUFFER, bufferIndex);
        gl.glBufferData(GL2.GL_UNIFORM_BUFFER, (long) buffer.limit() * Buffers.SIZEOF_FLOAT, buffer, GL2.GL_DYNAMIC_DRAW);
        int blockIndex = gl.glGetUniformBlockIndex(shaderProgramID, name);
        gl.glBindBufferBase(GL2.GL_UNIFORM_BUFFER, blockIndex, bufferIndex);
        gl.glBindBuffer(GL2.GL_UNIFORM_BUFFER, 0);
    }

    public void update(GL2 gl)
    {

    }

    public void update(GL2 gl, float[] data)
    {
        buffer.position(0);
        ((FloatBuffer) buffer).put(data);
        buffer.position(0);
        //buffer = FloatBuffer.wrap(data);

        gl.glBindBuffer(GL2.GL_UNIFORM_BUFFER, uniformBuffer.get(0));
        gl.glBufferSubData(GL2.GL_UNIFORM_BUFFER, 0, (long) buffer.limit() * Buffers.SIZEOF_FLOAT, buffer);
        gl.glBindBuffer(GL2.GL_UNIFORM_BUFFER, 0);
    }

    public void update(GL2 gl, int[] data)
    {
        buffer.position(0);
        ((IntBuffer) buffer).put(data);
        buffer.position(0);
        //buffer = IntBuffer.wrap(data);

        gl.glBindBuffer(GL2.GL_UNIFORM_BUFFER, uniformBuffer.get(0));
        gl.glBufferSubData(GL2.GL_UNIFORM_BUFFER, 0, (long) buffer.limit() * Buffers.SIZEOF_INT, buffer);
        gl.glBindBuffer(GL2.GL_UNIFORM_BUFFER, 0);
    }

    public void dispose(GL2 gl)
    {
        gl.glDeleteBuffers(1, uniformBuffer);
    }
}