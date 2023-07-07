package engine.util;

public final class FPSCounter
{
    private static int FPS;
    private static int LAST_FPS;
    private double previousTime = System.currentTimeMillis();

    private static double FRAME_TIME;
    private long start = System.nanoTime();

    public FPSCounter()
    {

    }

    public static int getFPS()
    {
        return LAST_FPS;
    }

    public static double getFrameTime()
    {
        return FRAME_TIME;
    }

    public void update()
    {
        double currentTime = System.currentTimeMillis();
        FPS++;



       // end = System.nanoTime();

        if(currentTime - previousTime >= 1000.0)
        {
            LAST_FPS = FPS;
            FPS = 0;
            previousTime = currentTime;
        }
    }

    public void updateFrameTime()
    {
        FRAME_TIME = (System.nanoTime() - start) * 10e-6;
        start = System.nanoTime();
    }
}