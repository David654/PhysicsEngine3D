package gui.components.bodyparameter;

import engine.assets.Icons;
import engine.physics.body.Body;
import gui.BodyParametersPanel;
import gui.BodySelectionPanel;
import gui.components.button.CustomButton;
import gui.components.button.IconButton;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BodyListElement extends IconButton implements MouseListener
{
    private final BodySelectionPanel bodySelectionPanel;
    private final BodyParametersPanel bodyParametersPanel;
    private final Body body;

    public BodyListElement(BodySelectionPanel bodySelectionPanel, BodyParametersPanel bodyParametersPanel, Body body)
    {
        super(body.getName(), body.isVisible() ? Icons.createIcon(Icons.EYE_ICON_PATH) : Icons.createEmptyIcon(), CustomButton.END);
        this.bodySelectionPanel = bodySelectionPanel;
        this.bodyParametersPanel = bodyParametersPanel;
        this.body = body;

        this.addMouseListener(this);
    }

    public Body getBody()
    {
        return body;
    }

    public boolean condition()
    {
        return body.isVisible();
    }

    public void setAction()
    {
        body.setVisible(!body.isVisible());
    }

    public void mouseClicked(MouseEvent e)
    {
        bodySelectionPanel.setSelectedBodyListElement(this);
        if(e.getClickCount() == 2)
        {
            bodyParametersPanel.getBodyParametersTabbedPane().addBodyParametersTab();
        }
    }

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}
}