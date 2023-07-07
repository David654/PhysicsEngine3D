package gui;

import engine.assets.Textures;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LoadingWindow extends JFrame implements GUIComponent
{
    private int width;
    private int height;
    private String title;

    public LoadingWindow(int width, int height, String title)
    {
        this.width = width;
        this.height = height;
        this.title = title;

        this.setSize(width, height);
        this.setResizable(false);
        this.setTitle(title);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
    }

    public void open()
    {
        this.setVisible(true);
    }

    public void createAndShowGUI()
    {

    }

    private class BackgroundPanel extends JPanel
    {
        private final BufferedImage backgroundImage;

        public BackgroundPanel()
        {
            backgroundImage = Textures.createNewImage(Textures.BACKGROUND_TEXTURE_PATH);
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = this.getPreferredSize().width;
            int height = this.getPreferredSize().height;

            g2d.drawImage(backgroundImage, 0, 0, width, height, null);
        }
    }
}