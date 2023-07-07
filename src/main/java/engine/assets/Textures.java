package engine.assets;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class Textures
{
    public static final String TEXTURE_DIRECTORY = "src\\main\\resources\\textures\\";

    public static final String LOADING_BACKGROUND_TEXTURE_PATH = TEXTURE_DIRECTORY + "loading_background.jpg";
    public static final String BACKGROUND_TEXTURE_PATH = TEXTURE_DIRECTORY + "background.jpg";
    public static final String BUFFER_TEXTURE_PATH = TEXTURE_DIRECTORY + "buffer_texture.png";

    public static Texture createNewTexture(String path)
    {
        try
        {
            return TextureIO.newTexture(new File(path), false);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage createNewImage(String path)
    {
        try
        {
            return ImageIO.read(new File(path));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}