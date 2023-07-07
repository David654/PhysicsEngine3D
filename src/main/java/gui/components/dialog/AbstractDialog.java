package gui.components.dialog;

import gui.GUIComponent;

import javax.swing.*;

public abstract class AbstractDialog extends JDialog implements GUIComponent
{
    protected final int width;
    protected final int height;
    protected final String title;

    public AbstractDialog(int width, int height, String title, JComponent component)
    {
        this.width = width;
        this.height = height;
        this.title = title;

        this.setSize(width, height);
        this.setResizable(true);
        this.setTitle(title);
        this.setLocationRelativeTo(component);
        this.setModal(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public void open()
    {
        this.setVisible(true);
    }

    public void close()
    {
        this.dispose();
    }

    public abstract void createAndShowGUI();
}