package gui.components.bodyparameter;

import engine.physics.body.Body;
import gui.BodySelectionPanel;
import gui.GUIComponent;
import gui.components.bodyparameter.BodyParametersTab;

import javax.swing.*;
import java.util.function.IntConsumer;

public class BodyParametersTabbedPane extends JTabbedPane implements GUIComponent
{
    private final BodySelectionPanel bodySelectionPanel;

    public BodyParametersTabbedPane(BodySelectionPanel bodySelectionPanel)
    {
        this.bodySelectionPanel = bodySelectionPanel;

        createAndShowGUI();
    }

    public void addBodyParametersTab()
    {
        Body body = bodySelectionPanel.getSelectedBodyListElement().getBody();

        boolean contains = false;
        for(int i = 0; i < this.getTabCount(); i++)
        {
            if(this.getTitleAt(i).equals(body.getName()))
            {
                contains = true;
                break;
            }
        }

        if(!contains)
        {
            BodyParametersTab tab = new BodyParametersTab(body);
            this.addTab(body.getName(), tab);
            this.revalidate();
            this.repaint();
        }

        for(int i = 0; i < this.getTabCount(); i++)
        {
            if(this.getTitleAt(i).equals(body.getName()))
            {
                this.setSelectedIndex(i);
                break;
            }
        }
    }

    public void createAndShowGUI()
    {
        this.putClientProperty("JTabbedPane.tabClosable", true);
        this.putClientProperty("JTabbedPane.tabCloseCallback", (IntConsumer) this::removeTab);
        this.putClientProperty("JTabbedPane.scrollButtonsPlacement", "both");
        this.putClientProperty("JTabbedPane.tabCloseToolTipText", "Close");
        UIManager.put("TabbedPane.showTabSeparators", true);
        this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    public void removeTab(int index)
    {
        if(this.getTabCount() > 0)
        {
            this.remove(index);
            this.revalidate();
            this.repaint();
        }
    }

    public void removeAllTabs()
    {
        this.removeAll();
    }
}