package gui.components.bodyparameter;

import engine.physics.body.Body;
import gui.GUIComponent;

import javax.swing.*;
import java.awt.*;

public class BodyParametersGroup extends JPanel implements GUIComponent
{
    private final Body body;

    private final String groupName;

    private JSplitPane splitPane;
    private JPanel parametersPanel;
    private JPanel componentsPanel;

    private int numParameters = 0;

    public BodyParametersGroup(Body body, String groupName)
    {
        this.body = body;
        this.groupName = groupName;

        createAndShowGUI();
    }

    public JSplitPane getSplitPane()
    {
        return splitPane;
    }

    public void addBodyParameter(String parameterName, JComponent... components)
    {
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridBagLayout());
        //labelPanel.setBackground(Color.BLUE);

       // labelPanel.setPreferredSize(new Dimension(100, labelPanel.getPreferredSize().height));

        JLabel label = new JLabel(parameterName);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = numParameters;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.gridwidth = 1;

        labelPanel.add(label, gbc);

        JPanel componentPanel = new JPanel();
        componentPanel.setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();

        for(int i = 0; i < components.length; i++)
        {
            JComponent c = components[i];

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = i;
            gbc.gridy = numParameters;
            gbc.weightx = 1;
            gbc.weighty = 0;

            componentPanel.add(c, gbc);
        }

        parametersPanel.add(labelPanel);
        parametersPanel.revalidate();
        parametersPanel.repaint();

        componentsPanel.add(componentPanel);
        componentsPanel.revalidate();
        componentsPanel.repaint();

        numParameters++;
    }

    public void createAndShowGUI()
    {
        this.setLayout(new BorderLayout());

        JButton groupNameButton = new BodyParametersGroupButton(groupName, this);
        this.add(groupNameButton, BorderLayout.NORTH);


        parametersPanel = new JPanel();
        parametersPanel.setLayout(new BoxLayout(parametersPanel, BoxLayout.Y_AXIS));

        componentsPanel = new JPanel();
        componentsPanel.setLayout(new BoxLayout(componentsPanel, BoxLayout.Y_AXIS));

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, parametersPanel, componentsPanel);

        this.add(splitPane, BorderLayout.CENTER);
    }
}