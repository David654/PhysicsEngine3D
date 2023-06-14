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
import engine.math.vector.Vector4;
import engine.physics.PhysicConstants;
import engine.physics.PhysicsEngine;
import engine.physics.body.Body;
import engine.physics.shape.Box;
import engine.physics.shape.Sphere;
import engine.util.ColorUtils;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

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

    private IntBuffer idBuffer;
    private FloatBuffer positionBuffer;
    private FloatBuffer dimensionBuffer;
    private FloatBuffer colorBuffer;
    private FloatBuffer diffuseBuffer;
    private FloatBuffer refractionBuffer;

    private int idBufferIndex;
    private int positionBufferIndex;
    private int dimensionBufferIndex;
    private int colorBufferIndex;
    private int diffuseBufferIndex;
    private int refractionBufferIndex;

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

        int blockIndex = gl.glGetUniformBlockIndex(shaderProgram, "uBodyColorBlock");

        idBuffer = IntBuffer.allocate(PhysicConstants.MAX_BODY_NUM);
        positionBuffer = FloatBuffer.allocate(PhysicConstants.MAX_BODY_NUM * 3);
        dimensionBuffer = FloatBuffer.allocate(PhysicConstants.MAX_BODY_NUM * 3);
        colorBuffer = FloatBuffer.allocate(PhysicConstants.MAX_BODY_NUM * 3);
        diffuseBuffer = FloatBuffer.allocate(PhysicConstants.MAX_BODY_NUM);
        refractionBuffer = FloatBuffer.allocate(PhysicConstants.MAX_BODY_NUM);

        /*for(int i = 0; i < positionBuffer.limit(); i++)
        {
            positionBuffer.put(1);
        }
        positionBuffer.rewind();**/
//
       // idBufferIndex = ShaderUtils.getBufferIndex(gl);
       // positionBufferIndex = ShaderUtils.getBufferIndex(gl);
       // dimensionBufferIndex = ShaderUtils.getBufferIndex(gl);
        colorBufferIndex = ShaderUtils.getBufferIndex(gl);
        diffuseBufferIndex = ShaderUtils.getBufferIndex(gl);
       // refractionBufferIndex = ShaderUtils.getBufferIndex(gl);

        System.out.println(positionBufferIndex);
        System.out.println(dimensionBufferIndex);
        System.out.println(colorBufferIndex);
        System.out.println(diffuseBufferIndex);
        System.out.println(refractionBufferIndex);
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

    private synchronized void updateShader(GL2 gl)
    {
        TIME += 1f / 60;

        ShaderUtils.setUniformVector2(gl, shaderProgram, "uResolution", new Vector2(width, height));
        ShaderUtils.setUniform1f(gl, shaderProgram, "uTime", TIME);
        ShaderUtils.setUniformVector2(gl, shaderProgram, "uMousePosition", mouseInput.getMouseDragPosition());
        ShaderUtils.setUniform1i(gl, shaderProgram, "uNumBodies", physicsEngine.getBodyHandler().getNumActiveBodies());

        double normalisedX = 2.0f * mouseInput.getMouseClick().getX() / width - 1.0f;
        double normalisedY = 1.0f - 2.0f * mouseInput.getMouseClick().getY() / height;

        Vector4 mouseClick = new Vector4(normalisedX, normalisedY,-1,1);
       // Matrix4 projectionMatrix = new Matrix4(transformation.getProjectionMatrix()).invert();

      //  ShaderUtils.setUniformVector2(gl, shaderProgram, "uMouseClick", mouseClick);

        Body[] activeBodies = physicsEngine.getBodyHandler().getActiveBodies();
        int selectedBodyIndex = -1;

        if(scene.getBodyPanel().getSelectedBodyListElement() != null)
        {
            Body selectedBody = scene.getBodyPanel().getSelectedBodyListElement().getBody();
            if(selectedBody.isActive())
            {
                selectedBodyIndex = Arrays.asList(activeBodies).indexOf(selectedBody);
            }
        }

        ShaderUtils.setUniform1i(gl, shaderProgram, "uSelectedBodyIndex", selectedBodyIndex);

        ShaderUtils.setUniformVector3(gl, shaderProgram, "uPosition", camera.getCameraPosition().subtract(new Vector3(0, 0, mouseInput.getMouseZoom())));
        ShaderUtils.setUniform1f(gl, shaderProgram, "uFOV", 100 / 60f);
        ShaderUtils.setUniform1f(gl, shaderProgram, "uMaxDist", 100);

        ShaderUtils.setUniformVector2(gl, shaderProgram, "uSeed1", new Vector2(Math.random(), Math.random() * 999.0));
        ShaderUtils.setUniformVector2(gl, shaderProgram, "uSeed2", new Vector2(Math.random(), Math.random() * 999.0));

       /* int bodyArrayUBO = 0;
        gl.glGenBuffers(1, IntBuffer.allocate(1));
        gl.glBindBuffer (GL2.GL_UNIFORM_BUFFER, bodyArrayUBO);
        gl.glBufferData(GL2.GL_UNIFORM_BUFFER, 100L,
                null, GL2.GL_DYNAMIC_DRAW);

        //gl.glBufferSubData;

        int bodyArrayBlockIndex = gl.glGetUniformBlockIndex (shaderProgram, "bodyArrayBlock");

        gl.glUniformBlockBinding (shaderProgram, bodyArrayBlockIndex, 0);
        gl.glBindBufferBase(GL2.GL_UNIFORM_BUFFER, 0, bodyArrayUBO);**/

       // FloatBuffer positionsBuffer = FloatBuffer.wrap(PhysicsEngine.BODY_HANDLER.getBodyPositions());

       // positionTextureData.setBuffer(positionsBuffer);

      //  bufferTexture = TextureIO.newTexture(positionTextureData);

        /*idBuffer.clear();
        positionBuffer.clear();
        dimensionBuffer.clear();
        colorBuffer.clear();
        diffuseBuffer.clear();
        refractionBuffer.clear();**/

        System.out.println(diffuseBuffer.get(0));

        for(int i = 0; i < activeBodies.length; i++)
        {
            Body body = activeBodies[i];

            // ID
            int id = body.getShape() instanceof Sphere ? 1 : 2;
            idBuffer.put(i, id);

            // Position
            Vector3 position = body.getPosition().multiply(PhysicConstants.WORLD_SCALE);
            positionBuffer.put(i * 3, (float) position.getX());
            positionBuffer.put(i * 3 + 1, (float) position.getY());
            positionBuffer.put(i * 3 + 2, (float) position.getZ());

            // Dimensions
            Vector3 dimensions = new Vector3();

            if(body.getShape() instanceof Sphere sphere)
            {
                dimensions = sphere.getDimensions();
            }
            else if(body.getShape() instanceof Box box)
            {
                dimensions = box.getDimensions();
            }

            dimensions = dimensions.multiply(PhysicConstants.WORLD_SCALE);
            dimensionBuffer.put(i * 3, (float) dimensions.getX());
            dimensionBuffer.put(i * 3 + 1, (float) dimensions.getY());
            dimensionBuffer.put(i * 3 + 2, (float) dimensions.getZ());

            // Color
            Vector3 color = ColorUtils.toGLColor(body.getMaterial().getColor());
            colorBuffer.put(i * 3, (float) Math.sin(TIME));
            colorBuffer.put(i * 3 + 1, (float) Math.cos(TIME));
            colorBuffer.put(i * 3 + 2, (float) 0);

            // Diffuse
            diffuseBuffer.put(i, (float) body.getMaterial().getDiffuse());

            // Refraction
            refractionBuffer.put(i, (float) body.getMaterial().getRefraction());

        }

        //gl.glBufferSubData();

        for(int i = 0; i < activeBodies.length; i++)
        {

        }

      //  System.out.println(positionBuffer.get(0) + "; " + positionBuffer.get(1) + "; " + positionBuffer.get(2));
      //  System.out.println(activeBodies[0].getPosition());
       // System.out.println(diffuseBuffer.get(0) + "; " + activeBodies[0].getMaterial().getDiffuse());

        idBuffer.rewind();
        positionBuffer.rewind();
        dimensionBuffer.rewind();
        colorBuffer.rewind();
        diffuseBuffer.rewind();
        refractionBuffer.rewind();

      //  ShaderUtils.setUniformBuffer(gl, shaderProgram, "uBodyIDBlock", idBuffer, idBufferIndex);
      //  ShaderUtils.setUniformBuffer(gl, shaderProgram, "uBodyPositionBlock", positionBuffer, positionBufferIndex);
       // ShaderUtils.setUniformBuffer(gl, shaderProgram, "uBodyDimensionBlock", dimensionBuffer, dimensionBufferIndex);
        ShaderUtils.setUniformBuffer(gl, shaderProgram, "uBodyColorBlock", colorBuffer, colorBufferIndex);
        ShaderUtils.setUniformBuffer(gl, shaderProgram, "uBodyDiffuseBlock", diffuseBuffer, diffuseBufferIndex);
       // ShaderUtils.setUniformBuffer(gl, shaderProgram, "uBodyRefractionBlock", refractionBuffer, refractionBufferIndex);
        //gl.glUniformBlockBinding(shaderProgram, blockIndex, 0);

        for(int i = 0; i < activeBodies.length; i++)
        {
            Body body = activeBodies[i];

            int id = body.getShape() instanceof Sphere ? 1 : 2;

            ShaderUtils.setUniform1i(gl, shaderProgram, "bodies[" + i + "].id", id);

            // ShaderUtils.setUniformVector3(gl, shaderProgram, "bodies[" + i + "].position", body.getShape().getPosition());

            ShaderUtils.setUniformVector3(gl, shaderProgram, "bodies[" + i + "].position", body.getPosition().multiply(PhysicConstants.WORLD_SCALE));

            if(body.getShape() instanceof Sphere sphere)
            {
                ShaderUtils.setUniformVector3(gl, shaderProgram, "bodies[" + i + "].dimensions", sphere.getDimensions().multiply(PhysicConstants.WORLD_SCALE));
            }
            else if(body.getShape() instanceof Box box)
            {
                //System.out.println(box.getPosition());
                ShaderUtils.setUniformVector3(gl, shaderProgram, "bodies[" + i + "].dimensions", box.getDimensions().multiply(PhysicConstants.WORLD_SCALE));
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