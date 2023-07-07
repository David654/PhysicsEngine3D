package engine.graphics;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.Texture;
import engine.assets.Textures;
import engine.geom.GeometricObject;
import engine.graphics.camera.Camera;
import engine.graphics.shaders.ShaderProgram;
import engine.input.KeyboardInput;
import engine.input.MouseInput;
import engine.math.MathUtils;
import engine.math.matrix.Matrix4;
import engine.math.vector.Vector2;
import engine.graphics.shaders.ShaderUtils;
import engine.math.vector.Vector3;
import engine.physics.PhysicConstants;
import engine.physics.PhysicsEngine;
import engine.physics.body.Body;
import engine.physics.body.DynamicBody;
import engine.physics.shape.Box;
import engine.physics.shape.Sphere;
import engine.util.ColorUtils;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

public class Canvas implements Drawable, Disposable
{
    private final int width;
    private final int height;
    private final Scene scene;

    private final ShaderProgram shaderProgram;
    private int shaderProgramID;
    private Texture backgroundTexture;

    private final ShaderProgram shader;
    private int shaderID;

    private int fbo = -1;
    private int framebufferTexture1 = -1;
    public static int framesStill = 1;

    public static float TIME;

    private final Camera camera;
    private final MouseInput mouseInput;
    private final KeyboardInput keyboardInput;
    private final PhysicsEngine physicsEngine;

    private IntBuffer idBuffer;
    private FloatBuffer positionBuffer;
    private FloatBuffer dimensionBuffer;
    private FloatBuffer colorBuffer;
    private FloatBuffer diffuseBuffer;
    private FloatBuffer refractionBuffer;

    private UniformBufferObject idUBO;
    private UniformBufferObject positionUBO;
    private UniformBufferObject dimensionUBO;
    private UniformBufferObject colorUBO;
    private UniformBufferObject diffuseUBO;
    private UniformBufferObject refractionUBO;

    private int idBufferIndex;
    private int positionBufferIndex;
    private int dimensionBufferIndex;
    private int colorBufferIndex;
    private int diffuseBufferIndex;
    private int refractionBufferIndex;

    private int lastSelectedIndex = -1;

    private Matrix4 transformationMatrix;

    private engine.geom.primitives.Box box = new engine.geom.primitives.Box(new Vector3(0, 0, 0), new Vector3(0.5, 0.25, 1), Color.RED);
    private GeometricObject object = new GeometricObject("src\\main\\resources\\objects\\dragon\\dragon.obj",new Color(0, 128, 0));

    GLU glu = new GLU();

    public Canvas(int width, int height, Scene scene)
    {
        this.width = width;
        this.height = height;
        this.scene = scene;

        shaderProgram = new ShaderProgram(new String[] {"vertex"}, new String[] {"fragment"});
        shader = new ShaderProgram(new String[] {"vertex1"}, new String[]{"fragment1"});

        camera = scene.getCamera();
        mouseInput = scene.getMouseInput();
        keyboardInput = scene.getKeyboardInput();
        physicsEngine = scene.getPhysicsEngine();
    }

    public void init(GL2 gl)
    {
        shaderProgram.compileShader(gl);
        shaderProgramID = shaderProgram.getShaderProgram();

        shader.compileShader(gl);
        shaderID = shader.getShaderProgram();

        object.init(gl);

        object.autoScale();

        transformationMatrix = new Matrix4();
        transformationMatrix = transformationMatrix.identity();
        //object.rotateZ(Math.PI / 8);

        //object.rotateZ(-Math.PI / 2);



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

        int positionAttribute = gl.glGetAttribLocation(shaderProgramID, "position");
        gl.glEnableVertexAttribArray(positionAttribute);
        gl.glVertexAttribPointer(positionAttribute, 2, GL2.GL_FLOAT, false, 0, 0L);
        gl.glVertexAttribPointer(0, 2, GL2.GL_FLOAT, false, 0, 0L);

        gl.glBindAttribLocation(shaderID, 0, "position");

        backgroundTexture = Textures.createNewTexture(Textures.BACKGROUND_TEXTURE_PATH);

        /*idBuffer = IntBuffer.allocate(PhysicConstants.MAX_BODY_NUM);
        positionBuffer = FloatBuffer.allocate(PhysicConstants.MAX_BODY_NUM * 3);
        dimensionBuffer = FloatBuffer.allocate(PhysicConstants.MAX_BODY_NUM * 3);
        colorBuffer = FloatBuffer.allocate(PhysicConstants.MAX_BODY_NUM * 3);
        diffuseBuffer = FloatBuffer.allocate(PhysicConstants.MAX_BODY_NUM);
        refractionBuffer = FloatBuffer.allocate(PhysicConstants.MAX_BODY_NUM);**/

        /*for(int i = 0; i < positionBuffer.limit(); i++)
        {
            positionBuffer.put(1);
        }
        positionBuffer.rewind();**/
//
        /*idBufferIndex = ShaderUtils.getBufferIndex(gl);
        positionBufferIndex = ShaderUtils.getBufferIndex(gl);
        dimensionBufferIndex = ShaderUtils.getBufferIndex(gl);
        colorBufferIndex = ShaderUtils.getBufferIndex(gl);
        diffuseBufferIndex = ShaderUtils.getBufferIndex(gl);
        refractionBufferIndex = ShaderUtils.getBufferIndex(gl);

        ShaderUtils.bindUniformBuffer(gl, shaderProgramID, "uBodyIDBlock", idBuffer, idBufferIndex);
        ShaderUtils.bindUniformBuffer(gl, shaderProgramID, "uBodyPositionBlock", positionBuffer, positionBufferIndex);
        ShaderUtils.bindUniformBuffer(gl, shaderProgramID, "uBodyDimensionBlock", dimensionBuffer, dimensionBufferIndex);
        ShaderUtils.bindUniformBuffer(gl, shaderProgramID, "uBodyColorBlock", colorBuffer, colorBufferIndex);
        ShaderUtils.bindUniformBuffer(gl, shaderProgramID, "uBodyDiffuseBlock", diffuseBuffer, diffuseBufferIndex);
        ShaderUtils.bindUniformBuffer(gl, shaderProgramID, "uBodyRefractionBlock", refractionBuffer, refractionBufferIndex);**/

        idUBO = new UniformBufferObject(physicsEngine.getBodyHandler().getBodyIDs());
        positionUBO = new UniformBufferObject(physicsEngine.getBodyHandler().getBodyPositions());
        dimensionUBO = new UniformBufferObject(physicsEngine.getBodyHandler().getBodyDimensions());
        colorUBO = new UniformBufferObject(physicsEngine.getBodyHandler().getBodyColors());
        diffuseUBO = new UniformBufferObject(physicsEngine.getBodyHandler().getBodyDiffuses());
        refractionUBO = new UniformBufferObject(physicsEngine.getBodyHandler().getBodyRefractions());

        idUBO.bind(gl, shaderProgramID, "uBodyIDBlock");
        positionUBO.bind(gl, shaderProgramID, "uBodyPositionBlock");
        dimensionUBO.bind(gl, shaderProgramID, "uBodyDimensionBlock");
        colorUBO.bind(gl, shaderProgramID, "uBodyColorBlock");
        diffuseUBO.bind(gl, shaderProgramID, "uBodyDiffuseBlock");
        refractionUBO.bind(gl, shaderProgramID, "uBodyRefractionBlock");

        //box.init(gl);
        //box.rotateX(0.5);
        //box.scale(new Vector3(2, 0.1, 0.1));

        //createFrameBuffer(gl);
    }

    private void createFrameBuffer(GL2 gl)
    {
        int[] ids = new int[1];
        gl.glGenFramebuffers(1, ids, 0);
        fbo = ids[0];
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, fbo);

        gl.glGenTextures(1, ids, 0);
        framebufferTexture1 = ids[0];
        gl.glBindTexture(GL2.GL_TEXTURE_2D, framebufferTexture1);
        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGB, width, height, 0, GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE, null);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
        gl.glFramebufferTexture2D(GL2.GL_FRAMEBUFFER, GL2.GL_COLOR_ATTACHMENT0, GL2.GL_TEXTURE_2D, framebufferTexture1, 0);

        gl.glGenRenderbuffers(1, ids, 0);
        int rbId = ids[0];
        gl.glBindRenderbuffer(GL2.GL_RENDERBUFFER, rbId);
        gl.glRenderbufferStorage(GL2.GL_RENDERBUFFER, GL2.GL_DEPTH24_STENCIL8, width, height);
        gl.glFramebufferRenderbuffer(GL2.GL_FRAMEBUFFER, GL2.GL_STENCIL_ATTACHMENT, GL2.GL_RENDERBUFFER, rbId);

        int fboStatus = gl.glCheckFramebufferStatus(GL2.GL_FRAMEBUFFER);
        if(fboStatus != GL2.GL_FRAMEBUFFER_COMPLETE)
        {
            System.out.println("Framebuffer error: " + fboStatus);
        }
    }

    private synchronized void updateShader(GL2 gl)
    {
        TIME += 1f / 60;

        ShaderUtils.setUniformVector2(gl, shaderProgramID, "uResolution", new Vector2(width, height));
        ShaderUtils.setUniform1f(gl, shaderProgramID, "uTime", TIME);
        ShaderUtils.setUniformVector2(gl, shaderProgramID, "uMousePosition", mouseInput.getMouseDragPosition());
        ShaderUtils.setUniform1i(gl, shaderProgramID, "uNumBodies", physicsEngine.getBodyHandler().getNumActiveBodies());

        double normalisedX = 2.0f * mouseInput.getMouseClick().getX() / width - 1.0f;
        double normalisedY = 1.0f - 2.0f * mouseInput.getMouseClick().getY() / height;

        Vector2 mouseClick = new Vector2(normalisedX, normalisedY);
       // Matrix4 projectionMatrix = new Matrix4(transformation.getProjectionMatrix()).invert();

        ShaderUtils.setUniformVector2(gl, shaderProgramID, "uMouseClick", mouseClick);

        Body[] activeBodies = physicsEngine.getBodyHandler().getActiveBodies();

        ShaderUtils.setUniformVector3(gl, shaderProgramID, "uPosition", camera.getCameraPosition().subtract(new Vector3(0, 0, mouseInput.getMouseZoom())));
        ShaderUtils.setUniform1f(gl, shaderProgramID, "uFOV", 100 / 60f);
        ShaderUtils.setUniform1f(gl, shaderProgramID, "uMaxDist", 100);

        ShaderUtils.setUniformVector2(gl, shaderProgramID, "uSeed1", new Vector2(Math.random(), Math.random() * 999.0));
        ShaderUtils.setUniformVector2(gl, shaderProgramID, "uSeed2", new Vector2(Math.random(), Math.random() * 999.0));

        ShaderUtils.setUniformTexture(gl, shaderProgramID, "uBackgroundTexture", backgroundTexture.getTextureObject());
        ShaderUtils.setUniform1f(gl, shaderProgramID, "uSamplePart", 1.0f / framesStill);
        ShaderUtils.setUniformTexture(gl, shaderProgramID, "uSample", framebufferTexture1);

        int selectedBodyIndex = -1;

        if(scene.getBodyPanel().getSelectedBodyListElement() != null)
        {
            Body selectedBody = scene.getBodyPanel().getSelectedBodyListElement().getBody();
            if(selectedBody.isActive())
            {
                selectedBodyIndex = Arrays.asList(activeBodies).indexOf(selectedBody);

                if(lastSelectedIndex != selectedBodyIndex)
                {
                    framesStill = 1;
                }

                lastSelectedIndex = selectedBodyIndex;
            }
        }

        ShaderUtils.setUniform1i(gl, shaderProgramID, "uSelectedBodyIndex", selectedBodyIndex);

        for(int i = 0; i < activeBodies.length; i++)
        {
            Body body = activeBodies[i];

            if(body instanceof DynamicBody dynamicBody)
            {
                if (dynamicBody.getVelocity().length() > 0.001)
                {
                    framesStill = 1;
                }
            }
        }

       /* for(int i = 0; i < activeBodies.length; i++)
        {
            Body body = activeBodies[i];

            if(body instanceof DynamicBody dynamicBody)
            {
                if(dynamicBody.getVelocity().length() > 0.001)
                {
                    framesStill = 1;
                }
            }

            // ID
            idBuffer.put(i, body.getShape() instanceof Sphere ? 1 : 2);

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
            colorBuffer.put(i * 3, (float) color.getX());
            colorBuffer.put(i * 3 + 1, (float) color.getY());
            colorBuffer.put(i * 3 + 2, (float) color.getZ());

            // Diffuse
            diffuseBuffer.put(i, (float) body.getMaterial().getDiffuse());

            // Refraction
            refractionBuffer.put(i, (float) body.getMaterial().getRefraction());
        }

        idBuffer.rewind();
        positionBuffer.rewind();
        dimensionBuffer.rewind();
        colorBuffer.rewind();
        diffuseBuffer.rewind();
        refractionBuffer.rewind();**/



        idUBO.update(gl, physicsEngine.getBodyHandler().getBodyIDs());
        positionUBO.update(gl, physicsEngine.getBodyHandler().getBodyPositions());
        dimensionUBO.update(gl, physicsEngine.getBodyHandler().getBodyDimensions());
        colorUBO.update(gl, physicsEngine.getBodyHandler().getBodyColors());
        dimensionUBO.update(gl, physicsEngine.getBodyHandler().getBodyDiffuses());
        refractionUBO.update(gl, physicsEngine.getBodyHandler().getBodyRefractions());

      //  ShaderUtils.setUniformBuffer(gl, shaderProgram, "uBodyIDBlock", idBuffer, idBufferIndex);
      //  ShaderUtils.setUniformBuffer(gl, shaderProgram, "uBodyPositionBlock", positionBuffer, positionBufferIndex);
       // ShaderUtils.setUniformBuffer(gl, shaderProgram, "uBodyDimensionBlock", dimensionBuffer, dimensionBufferIndex);
        //ShaderUtils.setUniformBuffer(gl, shaderProgramID, "uBodyColorBlock", colorBuffer, colorBufferIndex);
       // ShaderUtils.setUniformBuffer(gl, shaderProgramID, "uBodyDiffuseBlock", diffuseBuffer, diffuseBufferIndex);
       // ShaderUtils.setUniformBuffer(gl, shaderProgramID, "uBodyRefractionBlock", refractionBuffer, refractionBufferIndex);
        //gl.glUniformBlockBinding(shaderProgram, blockIndex, 0);

       /* for(int i = 0; i < activeBodies.length; i++)
        {
            Body body = activeBodies[i];

            int id = body.getShape() instanceof Sphere ? 1 : 2;

            ShaderUtils.setUniform1i(gl, shaderProgramID, "bodies[" + i + "].id", id);

            ShaderUtils.setUniform1i(gl, shaderProgramID, "bodies[" + i + "].lightID", body.getMaterial().isLightSource() ? 1 : 0);

            // ShaderUtils.setUniformVector3(gl, shaderProgram, "bodies[" + i + "].position", body.getShape().getPosition());

            ShaderUtils.setUniformVector3(gl, shaderProgramID, "bodies[" + i + "].position", body.getPosition().multiply(PhysicConstants.WORLD_SCALE));

            if(body.getShape() instanceof Sphere sphere)
            {
                ShaderUtils.setUniformVector3(gl, shaderProgramID, "bodies[" + i + "].dimensions", sphere.getDimensions().multiply(PhysicConstants.WORLD_SCALE));
            }
            else if(body.getShape() instanceof Box box)
            {
                //System.out.println(box.getPosition());
                ShaderUtils.setUniformVector3(gl, shaderProgramID, "bodies[" + i + "].dimensions", box.getDimensions().multiply(PhysicConstants.WORLD_SCALE));
            }

            ShaderUtils.setUniformVector3(gl, shaderProgramID, "bodies[" + i + "].material.color", ColorUtils.toGLColor(body.getMaterial().getColor()));

            ShaderUtils.setUniform1f(gl, shaderProgramID, "bodies[" + i + "].material.diffuse", (float) body.getMaterial().getDiffuse());

            ShaderUtils.setUniform1f(gl, shaderProgramID, "bodies[" + i + "].material.refraction", (float) body.getMaterial().getRefraction());
        }**/

        if(mouseInput.isMouseDragged())
        {
            framesStill = 1;
            mouseInput.setMouseDragged(false);
        }
    }

    public void draw(GL2 gl)
    {
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glClearColor(0, 0, 0, 1);

        gl.glLoadIdentity();

        /*shaderProgram.start(gl);

        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, fbo);


        updateShader(gl);
        gl.glDrawArrays(GL2.GL_QUADS, 0, 4);


        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, 0);

        gl.glBindTexture(GL2.GL_TEXTURE_2D, framebufferTexture1);
        gl.glDrawArrays(GL2.GL_QUADS, 0, 4);

        shaderProgram.stop(gl);**/

        framesStill++;

       // box.rotateX(Math.sin(TIME) / 60);
       // box.rotateY(0.01);
        //box.draw(gl);
      //  object.rotateY(0.01);
        //shaderProgram.start(gl);



        shader.start(gl);

        transformationMatrix = transformationMatrix.multiply(MathUtils.getRotationMatrixX(0));
        transformationMatrix = transformationMatrix.multiply(MathUtils.getRotationMatrixY(0.01));
        transformationMatrix = transformationMatrix.multiply(MathUtils.getRotationMatrixZ(0));
        transformationMatrix = transformationMatrix.multiply(MathUtils.getTranslationMatrix(new Vector3(0, 0, 0)));
        transformationMatrix = transformationMatrix.multiply(MathUtils.getScalingMatrix(1));

        ShaderUtils.setUniformMatrix4(gl, shaderID, "transformationMatrix", transformationMatrix);

        object.draw(gl);
        shader.stop(gl);
       // shaderProgram.stop(gl);

       // vertBuffer = FloatBuffer.wrap(object.getVertices());
      //  vertBuffer.position(0);
       // vertBuffer.put(object.getVertices());
       // vertBuffer.position(0);
        //gl.glBufferSubData(GL2.GL_ARRAY_BUFFER, 0, (long) vertBuffer.limit() * Buffers.SIZEOF_FLOAT, vertBuffer);

        //VAO.draw(gl);

        //gl.glDrawArrays(GL2.GL_TRIANGLES, 0, );
    }

    public void dispose(GL2 gl)
    {
     //   idUBO.dispose(gl);
       // positionUBO.dispose(gl);
      //  dimensionUBO.dispose(gl);
       // colorUBO.dispose(gl);
       // diffuseUBO.dispose(gl);
       // refractionUBO.dispose(gl);

        box.dispose(gl);
        object.dispose(gl);
        shaderProgram.dispose(gl);
        backgroundTexture.destroy(gl);
    }
}