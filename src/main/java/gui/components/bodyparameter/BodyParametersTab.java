package gui.components.bodyparameter;

import engine.physics.body.Body;
import gui.GUIComponent;
import gui.components.bodyparameter.BodyParametersGroup;
import gui.components.textfield.NumberTextField;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class BodyParametersTab extends JPanel implements GUIComponent
{
    private final BodyParametersGroup transformGroup;
    private final BodyParametersGroup materialGroup;
    private final BodyParametersGroup physicsGroup;
    private final BodyParametersGroup collisionGroup;

    private final JPanel mainPanel;

    private final JTextField positionXField;
    private final JTextField positionYField;
    private final JTextField positionZField;
    private final JTextField rotationXField;
    private final JTextField rotationYField;
    private final JTextField rotationZField;
    private final JTextField dimensionsXField;
    private final JTextField dimensionsYField;
    private final JTextField dimensionsZField;

    public BodyParametersTab(Body body)
    {
        transformGroup = new BodyParametersGroup(body, "Transform");
        materialGroup = new BodyParametersGroup(body, "Material");
        physicsGroup = new BodyParametersGroup(body, "Physics");
        collisionGroup = new BodyParametersGroup(body, "Collision");

        positionXField = new NumberTextField("X");
        positionYField = new NumberTextField("Y");
        positionZField = new NumberTextField("Z");
        rotationXField = new NumberTextField("X");
        rotationYField = new NumberTextField("Y");
        rotationZField = new NumberTextField("Z");
        dimensionsXField = new NumberTextField("X");
        dimensionsYField = new NumberTextField("Y");
        dimensionsZField = new NumberTextField("Z");

        mainPanel = new JPanel();

        createAndShowGUI();
    }

    public void addBodyParametersGroup(BodyParametersGroup group)
    {
        group.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(group);
    }

    private void initBodyParameters()
    {
        transformGroup.addBodyParameter("Position", positionXField, positionYField, positionZField);
        transformGroup.addBodyParameter("Rotation", rotationXField, rotationYField, rotationZField);
        transformGroup.addBodyParameter("Dimensions", dimensionsXField, dimensionsYField, dimensionsZField);
    }

    public void createAndShowGUI()
    {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        this.addBodyParametersGroup(transformGroup);
        this.addBodyParametersGroup(materialGroup);
        this.addBodyParametersGroup(physicsGroup);
        this.addBodyParametersGroup(collisionGroup);

        initBodyParameters();

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
    }
}