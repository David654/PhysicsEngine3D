package gui.components.textfield;

public class StringTextField extends AbstractTextField
{
    public StringTextField(String tooltipTitle)
    {
        super(tooltipTitle);
    }

    protected boolean check()
    {
        return true;
    }
}