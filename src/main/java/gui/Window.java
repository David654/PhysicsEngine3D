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

    private JSplitPane splitPane1;
    private JSplitPane splitPane2;
    private JPanel canvasPanel;
    private MenuBar menuBar;
    private BodyParametersPanel bodyParametersPanel;
    private BodySelectionPanel bodySelectionPanel;

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
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLayout(new BorderLayout());

        createAndShowGUI();

        this.setVisible(true);
    }

    public void createAndShowGUI()
    {
        init();
        initGraphics();
        initGUI();
    }

    private void init()
    {
        scene = new Scene(width * 4 / 5, height * 3 / 5, this);
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
        //this.add(glCanvas, BorderLayout.CENTER);
    }

    private void initGUI()
    {
        menuBar = new MenuBar();
        this.setJMenuBar(menuBar);

        canvasPanel = new JPanel();
        canvasPanel.setPreferredSize(new Dimension(width * 4 / 5, height));
        canvasPanel.setLayout(new BorderLayout());
        canvasPanel.add(glCanvas, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(width * 4 / 5, height * 2 / 5));

        canvasPanel.add(panel, BorderLayout.SOUTH);

        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BorderLayout());
        bodyPanel.setPreferredSize(new Dimension(width / 5, height));

        bodySelectionPanel = scene.getBodyPanel();
       // bodySelectionPanel.setPreferredSize(new Dimension(width / 5, height / 2));

        bodyParametersPanel = new BodyParametersPanel(bodySelectionPanel);
       // bodyParametersPanel.setPreferredSize(new Dimension(width / 5, height / 2));

        bodySelectionPanel.setBodyParametersPanel(bodyParametersPanel);
        bodySelectionPanel.initBodyListElements();

        JScrollPane scrollPane1 = new JScrollPane(bodySelectionPanel);
        scrollPane1.setBorder(null);
        JScrollPane scrollPane2 = new JScrollPane(bodyParametersPanel);
        scrollPane2.setBorder(null);

        UIManager.put("SplitPaneDivider.style", "plain");
        JSplitPane bodySplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane1, scrollPane2);

        bodyPanel.add(bodySplitPane, BorderLayout.CENTER);

        //JPanel panel = new JPanel();
        //panel.setBackground(Color.BLUE);
        //panel.setPreferredSize(new Dimension(width / 3 * 5, height));
       // splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, controlPanel, panel);
        //splitPane1.setMaximumSize(new Dimension(width / 5, height));
       // splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane1, bodyPanel);
        //splitPane2.setMaximumSize(new Dimension(width / 5, height));

        this.add(canvasPanel, BorderLayout.CENTER);
        this.add(bodyPanel, BorderLayout.EAST);
    }
}