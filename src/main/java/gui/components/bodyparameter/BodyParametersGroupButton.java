package gui.components.bodyparameter;

import engine.assets.Icons;
import gui.components.button.CustomButton;
import gui.components.button.IconButton;

public class BodyParametersGroupButton extends IconButton
{
    private final BodyParametersGroup bodyParametersGroup;
    private boolean isExpanded = true;

    public BodyParametersGroupButton(String groupName, BodyParametersGroup bodyParametersGroup)
    {
        super(groupName, Icons.createIcon(Icons.ARROW_DOWN_ICON_PATH), Icons.createIcon(Icons.ARROW_RIGHT_ICON_PATH), CustomButton.START);

        this.bodyParametersGroup = bodyParametersGroup;

        this.addActionListener(iconButton.getActionListeners()[0]);
    }

    public boolean isExpanded()
    {
        return isExpanded;
    }

    public void setExpanded(boolean expanded)
    {
        isExpanded = expanded;
    }

    public boolean condition()
    {
        return isExpanded();
    }

    public void setAction()
    {
        setExpanded(!isExpanded());
        bodyParametersGroup.getSplitPane().setVisible(isExpanded);
        bodyParametersGroup.revalidate();
        bodyParametersGroup.repaint();
    }
}