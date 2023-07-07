package engine.input;

import engine.graphics.Canvas;
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

    public boolean isStill()
    {
        for(int i = 0; i < wasdUD.length; i++)
        {
            if(wasdUD[i])
            {
                return false;
            }
        }

        return true;
    }

    public void update()
    {
        Vector3 dir = new Vector3();
        Vector3 dirTemp;

        if(wasdUD[0]) dir = dir.add(new Vector3(0, 0, 1));
        if(wasdUD[2]) dir = dir.add(new Vector3(0, 0, -1));
        if(wasdUD[1]) dir = dir.add(new Vector3(-1, 0, 0));
        if(wasdUD[3]) dir = dir.add(new Vector3(1, 0, 0));

        if(wasdUD[0] || wasdUD[1] || wasdUD[2] || wasdUD[3] || wasdUD[4] || wasdUD[5])
        {
            Canvas.framesStill = 1;
        }

        dir = MathUtils.rotateY(dir, mouseInput.getMouseDragPosition().getX());
        //dir = MathUtils.rotateZ(dir, mouseInput.getMouseDragPosition().getY());

        camera.setCameraPosition(camera.getCameraPosition().add(dir.multiply(camera.getCameraSpeed())));

        if (wasdUD[4]) camera.setCameraPosition(camera.getCameraPosition().add(new Vector3(0, camera.getCameraSpeed(), 0)));
        else if (wasdUD[5]) camera.setCameraPosition(camera.getCameraPosition().subtract(new Vector3(0, camera.getCameraSpeed(), 0)));
    }

    public void keyTyped(KeyEvent e)
    {

    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        switch(key)
        {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> wasdUD[0] = true;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> wasdUD[1] = true;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> wasdUD[2] = true;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> wasdUD[3] = true;
            case KeyEvent.VK_SPACE -> wasdUD[4] = true;
            case KeyEvent.VK_CONTROL -> wasdUD[5] = true;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();

        switch(key)
        {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> wasdUD[0] = false;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> wasdUD[1] = false;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> wasdUD[2] = false;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> wasdUD[3] = false;
            case KeyEvent.VK_SPACE -> wasdUD[4] = false;
            case KeyEvent.VK_CONTROL -> wasdUD[5] = false;
        }
    }
}