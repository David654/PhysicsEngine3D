package engine.graphics.camera;

import engine.math.vector.Vector3;

public class Camera
{
    private Vector3 cameraPosition;
    private double cameraSpeed;

    public Camera()
    {
        cameraPosition = new Vector3(0, 0, -5);
        cameraSpeed = 0.1;
    }

    public Vector3 getCameraPosition()
    {
        return cameraPosition;
    }

    public void setCameraPosition(Vector3 cameraPosition)
    {
        this.cameraPosition = cameraPosition;
    }

    public double getCameraSpeed()
    {
        return cameraSpeed;
    }

    public void setCameraSpeed(double cameraSpeed)
    {
        this.cameraSpeed = cameraSpeed;
    }
}