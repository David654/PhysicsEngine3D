package gui.components.button;

import engine.assets.Icons;
import gui.GUIComponent;

import javax.swing.*;

public abstract class IconButton extends CustomButton implements GUIComponent
{
    public IconButton(String name, ImageIcon icon1, ImageIcon icon2, int iconPosition)
    {
        super(name, icon1, icon2, iconPosition);

        createAndShowGUI();
    }

    public IconButton(String name, ImageIcon icon, int iconPosition)
    {
        super(name, icon, iconPosition);

        createAndShowGUI();
    }

    public abstract boolean condition();

    public abstract void setAction();

    public void createAndShowGUI()
    {
        iconButton.addActionListener(e ->
        {
            setAction();
            iconButton.setIcon(condition() ? icon1 : icon2);
        });
    }
}