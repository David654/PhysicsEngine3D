package engine;

import com.formdev.flatlaf.FlatDarculaLaf;
import gui.Window;
import gui.components.dialog.SystemInfo;

public class Application
{
    public static SystemInfo SYSTEM_INFO;

    public void launch()
    {
        FlatDarculaLaf.setup();

        SYSTEM_INFO = new SystemInfo(600, 400, null);

        new Window(2560, 1440, "Physics Engine");
    }
}