package gui.components.button;

import engine.assets.Icons;
import gui.GUIComponent;

import javax.swing.*;
import java.awt.*;

public abstract class CustomButton extends JButton implements GUIComponent
{
    public static final int START = 0;
    public static final int END = 1;

    protected JLabel nameLabel;
    protected JButton iconButton;

    protected ImageIcon icon1;
    protected ImageIcon icon2;

    public CustomButton(String name, ImageIcon icon1, ImageIcon icon2, int iconPosition)
    {
        this.icon1 = icon1;
        this.icon2 = icon2;
        this.setLayout(new BorderLayout());

        nameLabel = new JLabel(name);
        iconButton = new JButton(icon1);
        iconButton.setBackground(null);
        iconButton.setBorderPainted(false);

        this.add(nameLabel, BorderLayout.CENTER);
        this.add(iconButton, iconPosition == START ? BorderLayout.WEST : BorderLayout.EAST);
    }

    public CustomButton(String name, ImageIcon icon, int iconPosition)
    {
        this(name, icon, Icons.createEmptyIcon(), iconPosition);
    }

    public abstract void createAndShowGUI();
}