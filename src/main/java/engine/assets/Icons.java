package engine.assets;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public final class Icons
{
    public static final int ICON_SIZE = 16;

    /**
     * Application icon.
     */
    public static final String APPLICATION_ICON_PATH = "src\\main\\resources\\logo\\logo 5.png";

    /**
     * Button icons.
     */
    public static final String ICON_DIRECTORY = "src\\main\\resources\\icons\\";

    public static final String SETTINGS_ICON_PATH = ICON_DIRECTORY + "settings.png";
    public static final String HELP_ICON_PATH = ICON_DIRECTORY + "help.png";
    public static final String UP_ICON_PATH = ICON_DIRECTORY + "up.png";
    public static final String DOWN_ICON_PATH = ICON_DIRECTORY + "down.png";
    public static final String EYE_ICON_PATH = ICON_DIRECTORY + "eye.png";
    public static final String PLAY_ICON_PATH = ICON_DIRECTORY + "play.png";
    public static final String PAUSE_ICON_PATH = ICON_DIRECTORY + "pause.png";
    public static final String RESET_ICON_PATH = ICON_DIRECTORY + "reset.png";
    public static final String SWITCH_ICON_PATH = ICON_DIRECTORY + "switch.png";
    public static final String ARROW_DOWN_ICON_PATH = ICON_DIRECTORY + "arrow_down.png";
    public static final String ARROW_RIGHT_ICON_PATH = ICON_DIRECTORY + "arrow_right.png";
    public static final String PC_ICON_PATH = ICON_DIRECTORY + "pc.png";

    public static ImageIcon createIcon(String fileName, int size)
    {
        return Icons.resize(new ImageIcon(fileName), size);
    }

    public static ImageIcon createIcon(String fileName)
    {
        return createIcon(fileName, ICON_SIZE);
    }

    public static ImageIcon createEmptyIcon()
    {
        Image image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        return new ImageIcon(image);
    }

    public static ImageIcon resize(ImageIcon icon, int size)
    {
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }
}