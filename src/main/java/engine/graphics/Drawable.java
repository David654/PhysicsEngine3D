package engine.graphics;

import com.jogamp.opengl.GL2;

public interface Drawable
{
    void init(GL2 gl);
    void draw(GL2 gl);
}