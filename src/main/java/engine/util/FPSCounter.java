package engine.util;

public final class FPSCounter
{
    private static int FPS;
    private static int LAST_FPS;
    private double previousTime = System.currentTimeMillis();

    public FPSCounter()
    {

    }

    public static int getFPS()
    {
        return LAST_FPS;
    }

    public void update()
    {
        double currentTime = System.currentTimeMillis();
        FPS++;

        if(currentTime - previousTime >= 1000.0)
        {
            LAST_FPS = FPS;
            FPS = 0;
            previousTime = currentTime;
        }
    }
}