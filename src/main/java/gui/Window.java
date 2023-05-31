package gui;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import engine.graphics.Scene;
import engine.input.KeyboardInput;
import engine.input.MouseInput;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame implements GUIComponent
{
    private int width;
    private int height;
    private String title;

    private GLCanvas glCanvas;
    private Scene scene;
    private MouseInput mouseInput;
    private KeyboardInput keyboardInput;

    public Window(int width, int height, String title)
    {
        this.width = width;
        this.height = height;
        this.title = title;

        this.setSize(width, height);
        this.setResizable(true);
        this.setTitle(title);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        createAndShowGUI();

        this.setVisible(true);
    }

    public void createAndShowGUI()
    {
        init();
        initGraphics();
        this.add(glCanvas, BorderLayout.CENTER);
    }

    private void init()
    {
        scene = new Scene(width, height, this);
        mouseInput = scene.getMouseInput();
        keyboardInput = scene.getKeyboardInput();
    }

    private void initGraphics()
    {
        GLProfile glProfile = GLProfile.get(GLProfile.GL2);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        glCanvas = new GLCanvas(glCapabilities);
        FPSAnimator animator = new FPSAnimator(glCanvas, 1000, true);

        glCanvas.addGLEventListener(scene);
        glCanvas.addMouseListener(mouseInput);
        glCanvas.addMouseMotionListener(mouseInput);
        glCanvas.addMouseWheelListener(mouseInput);
        glCanvas.addKeyListener(keyboardInput);
        animator.start();
    }
}