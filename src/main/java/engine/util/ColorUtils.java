package engine.util;

import engine.math.vector.Vector3;

import java.awt.*;

public final class ColorUtils
{
    public static Vector3 toGLColor(Color color)
    {
        return new Vector3(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0);
    }
}