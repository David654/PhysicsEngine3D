package gui;

import gui.components.bodyparameter.BodyParametersTabbedPane;

import javax.swing.*;
import java.awt.*;

public class BodyParametersPanel extends JPanel implements GUIComponent
{
    private final BodySelectionPanel selectionPanel;
    private final BodyParametersTabbedPane tabbedPane;

    public BodyParametersPanel(BodySelectionPanel selectionPanel)
    {
        this.selectionPanel = selectionPanel;
        this.tabbedPane = new BodyParametersTabbedPane(selectionPanel);

        createAndShowGUI();
    }

    public BodyParametersTabbedPane getBodyParametersTabbedPane()
    {
        return tabbedPane;
    }

    public void createAndShowGUI()
    {
        this.setLayout(new BorderLayout());

        this.add(tabbedPane, BorderLayout.CENTER);
    }
}