package launcher;

import gui.Window;

public class DesktopLauncher
{
    private Window window;

    public void launch()
    {
        window = new Window(1280, 720, "Physics Engine");
    }

    public static void main(String[] args)
    {
        new DesktopLauncher().launch();
    }
}