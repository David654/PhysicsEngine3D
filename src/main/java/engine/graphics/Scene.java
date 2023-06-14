package engine.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import engine.graphics.camera.Camera;
import engine.input.KeyboardInput;
import engine.input.MouseInput;
import engine.physics.PhysicsEngine;
import engine.util.FPSCounter;
import gui.BodySelectionPanel;
import gui.Window;

public class Scene implements GLEventListener
{
    private int width;
    private int height;
    private final Window window;

    private final FPSCounter fpsCounter;

    private Canvas canvas;
    private final BodySelectionPanel bodySelectionPanel;

    private final Camera camera;
    private final MouseInput mouseInput;
    private final KeyboardInput keyboardInput;
    private final PhysicsEngine physicsEngine;

    public Scene(int width, int height, Window window)
    {
        this.width = width;
        this.height = height;
        this.window = window;

        fpsCounter = new FPSCounter();

        camera = new Camera();
        mouseInput = new MouseInput();
        keyboardInput = new KeyboardInput(mouseInput, camera);
        physicsEngine = new PhysicsEngine();

        bodySelectionPanel = new BodySelectionPanel();
    }

    public BodySelectionPanel getBodyPanel()
    {
        return bodySelectionPanel;
    }

    public Camera getCamera()
    {
        return camera;
    }

    public MouseInput getMouseInput()
    {
        return mouseInput;
    }

    public KeyboardInput getKeyboardInput()
    {
        return keyboardInput;
    }

    public PhysicsEngine getPhysicsEngine()
    {
        return physicsEngine;
    }

    private synchronized void initSceneComponents(GL2 gl)
    {
        canvas = new Canvas(width, height, this);
        canvas.init(gl);
        physicsEngine.start();
    }

    public void init(GLAutoDrawable glAutoDrawable)
    {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        float aspect = (float) width / (float) height;
        gl.glOrtho(-aspect, aspect, -1, 1, -1, 1);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0, 0, 0, 1);

        initSceneComponents(gl);
    }

    private void update()
    {
        fpsCounter.update();
        window.setTitle("Physics Engine 3D | FPS: " + FPSCounter.getFPS());
        keyboardInput.update();
    }

    public void display(GLAutoDrawable glAutoDrawable)
    {

        update();

        GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glClearColor(0, 0, 0, 1);
        gl.glLoadIdentity();

        canvas.draw(gl);
    }

    public void dispose(GLAutoDrawable glAutoDrawable)
    {

    }

    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height)
    {
        this.width = width;
        this.height = height;
    }
}