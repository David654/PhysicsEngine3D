package gui.components.textfield;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class AbstractTextField extends JTextField implements DocumentListener
{
    protected final String tooltipTitle;

    public AbstractTextField(String tooltipTitle)
    {
        this.tooltipTitle = tooltipTitle;
        this.getDocument().addDocumentListener(this);

        setToolTipText();
    }

    protected void setToolTipText()
    {
        this.setToolTipText(tooltipTitle + ": " + this.getText());
    }

    public void insertUpdate(DocumentEvent e)
    {
        setToolTipText();
    }

    public void removeUpdate(DocumentEvent e)
    {
        setToolTipText();
    }

    public void changedUpdate(DocumentEvent e)
    {
        setToolTipText();
    }
}