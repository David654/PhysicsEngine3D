package engine.input;

import engine.math.vector.Vector2;

import java.awt.event.*;

public class MouseInput implements MouseListener, MouseMotionListener, MouseWheelListener
{
    private Vector2 mousePosition;
    private Vector2 mouseDragPosition;
    private double mouseZoom;

    public MouseInput()
    {
        mousePosition = new Vector2();
        mouseDragPosition = new Vector2();
    }

    public Vector2 getMouseDragPosition()
    {
        return mouseDragPosition;
    }

    public double getMouseZoom()
    {
        return mouseZoom;
    }

    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent e)
    {
        mousePosition = new Vector2(e.getX(), e.getY());
    }

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseDragged(MouseEvent e)
    {
        Vector2 d = new Vector2(e.getX(), e.getY()).subtract(mousePosition);
        d = new Vector2(d.getX() / 1280, d.getY() / 720);
        //d = d.multiply(0.001);

        mouseDragPosition = mouseDragPosition.add(d);
        mouseDragPosition = mouseDragPosition.lerp(mouseDragPosition.subtract(d), 0.4);

        //mouseDragPosition.setX(mouseDragPosition.getX() - (int) (mouseDragPosition.getX() / 2 / Math.PI));

        mousePosition = new Vector2(e.getX(), e.getY());

       // System.out.println(mouseDragPosition.toString());
    }

    public void mouseMoved(MouseEvent e)
    {

    }

    public void mouseWheelMoved(MouseWheelEvent e)
    {
        mouseZoom += e.getWheelRotation();
    }
}