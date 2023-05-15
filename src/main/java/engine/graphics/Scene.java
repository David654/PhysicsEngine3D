package engine.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

public class Scene implements GLEventListener
{
    private final int width;
    private final int height;

    private Canvas canvas;

    public Scene(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    private void initSceneComponents(GL2 gl)
    {
        canvas = new Canvas(width, height);
        canvas.init(gl);
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

    }
}