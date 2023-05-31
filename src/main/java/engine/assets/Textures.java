package engine.assets;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;
import java.io.IOException;

public final class Textures
{
    public static final String TEXTURE_DIRECTORY = "src\\main\\resources\\textures\\";

    public static final String BACKGROUND_TEXTURE_PATH = TEXTURE_DIRECTORY + "background.jpg";

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
}