package engine.graphics.shaders;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import engine.math.vector.*;

import java.nio.Buffer;
import java.nio.IntBuffer;

public final class ShaderUtils
{
    public static int TEXTURE_NUM = 0;

    public static void setUniform1f(GL2 gl, int shaderProgram, String name, float value)
    {
        gl.glUniform1f(gl.glGetUniformLocation(shaderProgram, name), value);
    }

    public static void setUniform1i(GL2 gl, int shaderProgram, String name, int value)
    {
        gl.glUniform1i(gl.glGetUniformLocation(shaderProgram, name), value);
    }

    public static void setUniform2f(GL2 gl, int shaderProgram, String name, float value1, float value2)
    {
        gl.glUniform2f(gl.glGetUniformLocation(shaderProgram, name), value1, value1);
    }

    public static void setUniform3f(GL2 gl, int shaderProgram, String name, float value1, float value2, float value3)
    {
        gl.glUniform3f(gl.glGetUniformLocation(shaderProgram, name), value1, value2, value3);
    }

    public static void setUniform4f(GL2 gl, int shaderProgram, String name, float value1, float value2, float value3, float value4)
    {
        gl.glUniform4f(gl.glGetUniformLocation(shaderProgram, name), value1, value2, value3, value4);
    }

    public static void setUniformVector2(GL2 gl, int shaderProgram, String name, Vector2 vector)
    {
        gl.glUniform2f(gl.glGetUniformLocation(shaderProgram, name), (float) vector.getX(), (float) vector.getY());
    }

    public static void setUniformVector3(GL2 gl, int shaderProgram, String name, Vector3 vector)
    {
        gl.glUniform3f(gl.glGetUniformLocation(shaderProgram, name), (float) vector.getX(), (float) vector.getY(), (float) vector.getZ());
    }

    public static void setUniformVector4(GL2 gl, int shaderProgram, String name, Vector4 vector)
    {
        gl.glUniform4f(gl.glGetUniformLocation(shaderProgram, name), (float) vector.getX(), (float) vector.getY(), (float) vector.getZ(), (float) vector.getW());
    }

    public static void setUniformTexture(GL2 gl, int shaderProgram, String name, int texture)
    {
        gl.glActiveTexture(GL2.GL_TEXTURE0 + TEXTURE_NUM);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
        ShaderUtils.setUniform1i(gl, shaderProgram, name, TEXTURE_NUM);
        TEXTURE_NUM++;
    }

    public static void setUniformBuffer(GL2 gl, int shaderProgram, String name, Buffer buffer, int bufferIndex)
    {
        gl.glBindBuffer(GL2.GL_UNIFORM_BUFFER, bufferIndex);
        int blockIndex = gl.glGetUniformBlockIndex(shaderProgram, name);
        gl.glUniformBlockBinding(shaderProgram, blockIndex, 0);
        gl.glBufferData(GL2.GL_UNIFORM_BUFFER, (long) buffer.limit() * Float.SIZE, buffer, GL2.GL_DYNAMIC_DRAW);
        gl.glBindBufferBase(GL2.GL_UNIFORM_BUFFER, blockIndex, bufferIndex);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
    }

    public static int getBufferIndex(GL2 gl)
    {
        IntBuffer buf = Buffers.newDirectIntBuffer(1);
        gl.glGenBuffers(1, buf);
        return buf.get();
    }
}