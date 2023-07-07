package launcher;

import engine.Application;

public class DesktopLauncher
{

    public void launch()
    {
        Application application = new Application();
        application.launch();
    }

    public static void main(String[] args)
    {
        new DesktopLauncher().launch();
    }
}