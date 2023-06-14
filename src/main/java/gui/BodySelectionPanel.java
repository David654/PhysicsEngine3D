package gui;

import engine.physics.PhysicsEngine;
import engine.physics.body.Body;
import gui.components.bodyparameter.BodyListElement;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BodySelectionPanel extends JPanel implements GUIComponent
{
    private BodyParametersPanel bodyParametersPanel;

    private final ArrayList<BodyListElement> bodyListElements;

    private BodyListElement selectedBodyListElement;

    private final JPanel mainPanel;

    public BodySelectionPanel()
    {
        bodyListElements = new ArrayList<>();
        mainPanel = new JPanel();

        createAndShowGUI();
    }

    public void setBodyParametersPanel(BodyParametersPanel bodyParametersPanel)
    {
        this.bodyParametersPanel = bodyParametersPanel;
    }

    public BodyListElement getSelectedBodyListElement()
    {
        return selectedBodyListElement;
    }

    public void setSelectedBodyListElement(BodyListElement selectedBodyListElement)
    {
        this.selectedBodyListElement = selectedBodyListElement;
    }

    public void createAndShowGUI()
    {
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.trackArc", 999);
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        UIManager.put("ScrollBar.trackInsets", new Insets(999, 999, 999, 999));
        UIManager.put("ScrollPane.smoothScrolling", true);

        mainPanel.setLayout(new GridBagLayout());

        this.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setBorder(null);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void initBodyListElements()
    {
        for(int i = 0; i < PhysicsEngine.BODY_HANDLER.size(); i++)
        {
            Body body = PhysicsEngine.BODY_HANDLER.get(i);
            BodyListElement bodyListElement = new BodyListElement(this, bodyParametersPanel, body);
            this.addBodyListElement(bodyListElement);
        }
    }

    private void addBodyListElement(BodyListElement bodyListElement)
    {
        bodyListElements.add(bodyListElement);

        GridBagConstraints gbc = new GridBagConstraints();

        mainPanel.removeAll();
        for(int i = 0; i < bodyListElements.size(); i++)
        {
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.NORTH;
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 1;
            gbc.weighty = 0;
            if(i + 1 == bodyListElements.size())
            {
                gbc.weighty = 1;
            }
            mainPanel.add(bodyListElements.get(i), gbc);
        }
        mainPanel.repaint();
    }
}