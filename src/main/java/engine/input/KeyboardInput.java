package engine.input;

import engine.graphics.camera.Camera;
import engine.math.MathUtils;
import engine.math.vector.Vector3;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener
{
    private final MouseInput mouseInput;
    private final Camera camera;

    private final boolean[] wasdUD = {false, false, false, false, false, false};

    public KeyboardInput(MouseInput mouseInput, Camera camera)
    {
        this.mouseInput = mouseInput;
        this.camera = camera;
    }

    public void update()
    {
        Vector3 dir = new Vector3();
        Vector3 dirTemp;

        if(wasdUD[0]) dir = new Vector3(0, 0, 1);
        else if (wasdUD[2]) dir = new Vector3(0, 0, -1);
        if(wasdUD[1]) dir = dir.add(new Vector3(-1, 0, 0));
        else if(wasdUD[3]) dir = dir.add(new Vector3(1, 0, 0));

        dirTemp = MathUtils.rotateY(dir, mouseInput.getMouseDragPosition().getX());
        dir = MathUtils.rotateZ(dirTemp, mouseInput.getMouseDragPosition().getY());

        camera.setCameraPosition(camera.getCameraPosition().add(dir.multiply(camera.getCameraSpeed())));

        if (wasdUD[4]) camera.setCameraPosition(camera.getCameraPosition().subtract(new Vector3(0, 0, camera.getCameraSpeed())));
        else if (wasdUD[5]) camera.setCameraPosition(camera.getCameraPosition().add(new Vector3(0, 0, camera.getCameraSpeed())));
    }

    public void keyTyped(KeyEvent e)
    {

    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        switch(key)
        {
            case KeyEvent.VK_W -> wasdUD[0] = true;
            case KeyEvent.VK_A -> wasdUD[1] = true;
            case KeyEvent.VK_S -> wasdUD[2] = true;
            case KeyEvent.VK_D -> wasdUD[3] = true;
            case KeyEvent.VK_SPACE -> wasdUD[4] = true;
            case KeyEvent.VK_SHIFT -> wasdUD[5] = true;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();

        switch(key)
        {
            case KeyEvent.VK_W -> wasdUD[0] = false;
            case KeyEvent.VK_A -> wasdUD[1] = false;
            case KeyEvent.VK_S -> wasdUD[2] = false;
            case KeyEvent.VK_D -> wasdUD[3] = false;
            case KeyEvent.VK_SPACE -> wasdUD[4] = false;
            case KeyEvent.VK_SHIFT -> wasdUD[5] = false;
        }
    }
}