package engine.graphics;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.FBObject;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import engine.assets.Textures;
import engine.graphics.camera.Camera;
import engine.graphics.shaders.Shader;
import engine.input.MouseInput;
import engine.math.vector.Vector2;
import engine.graphics.shaders.ShaderUtils;
import engine.math.vector.Vector3;
import engine.physics.PhysicsEngine;
import engine.physics.body.Body;
import engine.physics.body.DynamicBody;
import engine.physics.shape.Box;
import engine.physics.shape.Sphere;
import engine.util.ColorUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Canvas implements Drawable
{
    private final int width;
    private final int height;
    private final Scene scene;

    private final Shader shader;
    private int shaderProgram;
    private Texture backgroundTexture;
    private FBObject fbObject;
    private int fbo = 0;
    private int fboTexture = 0;

    public static float TIME;

    private final Camera camera;
    private final MouseInput mouseInput;
    private final PhysicsEngine physicsEngine;

    public Canvas(int width, int height, Scene scene)
    {
        this.width = width;
        this.height = height;
        this.scene = scene;

        shader = new Shader(new String[] {"vertex"}, new String[] {"fragment"});
        fbObject = new FBObject();

        camera = scene.getCamera();
        mouseInput = scene.getMouseInput();
        physicsEngine = scene.getPhysicsEngine();
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



        int positionAttribute = gl.glGetAttribLocation(shaderProgram, "position");
        gl.glEnableVertexAttribArray(positionAttribute);
        gl.glVertexAttribPointer(positionAttribute, 2, GL2.GL_FLOAT, false, 0, 0L);

        backgroundTexture = Textures.createNewTexture(Textures.BACKGROUND_TEXTURE_PATH);
    }

    private void createFrameBuffer(GL2 gl)
    {
        gl.glGenFramebuffers(1, IntBuffer.allocate(1));
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, fbo);

        gl.glGenTextures(1, IntBuffer.allocate(1));
        gl.glBindTexture(GL2.GL_TEXTURE_2D, fboTexture);
        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGB, 1280, 720, 0, GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE, null);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR );
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);

        gl.glFramebufferTexture2D(GL2.GL_FRAMEBUFFER, GL2.GL_COLOR_ATTACHMENT0, GL2.GL_TEXTURE_2D, fboTexture, 0);
    }

    private void updateShader(GL2 gl)
    {
        TIME += 1f / 60;

        ShaderUtils.setUniformVector2(gl, shaderProgram, "uResolution", new Vector2(width, height));
        ShaderUtils.setUniform1f(gl, shaderProgram, "uTime", TIME);
        ShaderUtils.setUniformVector2(gl, shaderProgram, "uMousePosition", mouseInput.getMouseDragPosition());

        ShaderUtils.setUniformVector3(gl, shaderProgram, "uPosition", camera.getCameraPosition().subtract(new Vector3(0, 0, mouseInput.getMouseZoom())));
        ShaderUtils.setUniform1f(gl, shaderProgram, "uFOV", 100 / 60f);
        ShaderUtils.setUniform1f(gl, shaderProgram, "uMaxDist", 100);

        ShaderUtils.setUniformVector2(gl, shaderProgram, "uSeed1", new Vector2(Math.random(), Math.random() * 999.0));
        ShaderUtils.setUniformVector2(gl, shaderProgram, "uSeed2", new Vector2(Math.random(), Math.random() * 999.0));

        for(int i = 0; i < physicsEngine.getBodyHandler().size(); i++)
        {
            Body body = physicsEngine.getBodyHandler().get(i);

            int id = body.getShape() instanceof Sphere ? 1 : 2;

            ShaderUtils.setUniform1i(gl, shaderProgram, "bodies[" + i + "].id", id);

           // ShaderUtils.setUniformVector3(gl, shaderProgram, "bodies[" + i + "].position", body.getShape().getPosition());

            if(body.getShape() instanceof Sphere sphere)
            {
                ShaderUtils.setUniformVector3(gl, shaderProgram, "bodies[" + i + "].position", sphere.getPosition());
                ShaderUtils.setUniformVector3(gl, shaderProgram, "bodies[" + i + "].dimensions", sphere.getDimensions());
            }
            else if(body.getShape() instanceof Box box)
            {
                ShaderUtils.setUniformVector3(gl, shaderProgram, "bodies[" + i + "].position", box.getPosition());
                //System.out.println(box.getPosition());
                ShaderUtils.setUniformVector3(gl, shaderProgram, "bodies[" + i + "].dimensions", box.getDimensions());
            }

            ShaderUtils.setUniformVector3(gl, shaderProgram, "bodies[" + i + "].material.color", ColorUtils.toGLColor(body.getMaterial().getColor()));
            ShaderUtils.setUniform1f(gl, shaderProgram, "bodies[" + i + "].material.diffuse", (float) body.getMaterial().getDiffuse());
            ShaderUtils.setUniform1f(gl, shaderProgram, "bodies[" + i + "].material.refraction", (float) body.getMaterial().getRefraction());
        }

        createFrameBuffer(gl);

        ShaderUtils.setUniformTexture(gl, shaderProgram, "uPreviousFrame", fboTexture);

        ShaderUtils.setUniformTexture(gl, shaderProgram, "uBackgroundTexture", backgroundTexture.getTextureObject());
    }

    public void draw(GL2 gl)
    {
        updateShader(gl);

        gl.glBindFramebuffer(GL2.GL_DRAW_FRAMEBUFFER, fbo);
        gl.glUseProgram(shaderProgram);
        gl.glDrawArrays(GL2.GL_QUADS, 0, 4);
       // gl.glUseProgram(0);
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, 0);

        //gl.glBindTexture(GL2.GL_TEXTURE_2D, fboTexture);
        gl.glUseProgram(shaderProgram);
        gl.glDrawArrays(GL2.GL_QUADS, 0, 4);
    }
}