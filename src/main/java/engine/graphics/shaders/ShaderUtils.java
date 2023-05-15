package engine.graphics.shaders;

import com.jogamp.opengl.GL2;
import engine.math.vector.*;

public final class ShaderUtils
{
    public static void setUniform1f(GL2 gl, int shaderProgram, String name, float value)
    {
        gl.glUniform1f(gl.glGetUniformLocation(shaderProgram, name), value);
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
}