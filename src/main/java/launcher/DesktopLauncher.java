package launcher;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import gui.Window;

public class DesktopLauncher
{
    private Window window;

    public void launch()
    {
        FlatDarculaLaf.setup();
        window = new Window(2560, 1440, "Physics Engine");
    }

    public static void main(String[] args)
    {
        new DesktopLauncher().launch();
    }
}