package engine.graphics;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import engine.graphics.shaders.Shader;
import engine.math.vector.Vector2;
import engine.graphics.shaders.ShaderUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Canvas implements Drawable
{
    private final int width;
    private final int height;

    private final Shader shader;
    private int shaderProgram;

    private float time;

    public Canvas(int width, int height)
    {
        this.width = width;
        this.height = height;

        shader = new Shader(new String[] {"vertex"}, new String[] {"fragment"});
    }

    public void init(GL2 gl)
    {
        shader.compileShader(gl);
        shaderProgram = shader.getShaderProgram();

        float[] verts = {
                -1f, -1f,
                -1f, 1f,
                1f, 1f,
                1f, -1f
        };

        IntBuffer vao = Buffers.newDirectIntBuffer(1);
        gl.glGenVertexArrays(1, vao);
        gl.glBindVertexArray(vao.get(0));

        IntBuffer vbo = Buffers.newDirectIntBuffer(1);
        FloatBuffer vertBuffer = Buffers.newDirectFloatBuffer(verts);

        gl.glGenBuffers(1, vbo);

        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, vbo.get(0));
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, (long) vertBuffer.limit() * Buffers.SIZEOF_FLOAT, vertBuffer, GL2.GL_STATIC_DRAW);

        gl.glUseProgram(shaderProgram);

        int positionAttribute = gl.glGetAttribLocation(shaderProgram, "position");
        gl.glEnableVertexAttribArray(positionAttribute);
        gl.glVertexAttribPointer(positionAttribute, 2, GL2.GL_FLOAT, false, 0, 0L);
    }

    private void updateShader(GL2 gl)
    {
        time += 1f / 60;

        ShaderUtils.setUniformVector2(gl, shaderProgram, "uResolution", new Vector2(width, height));
        ShaderUtils.setUniform1f(gl, shaderProgram, "uTime", time);
    }

    public void draw(GL2 gl)
    {
        updateShader(gl);

        gl.glDrawArrays(GL2.GL_QUADS, 0, 4);
    }
}