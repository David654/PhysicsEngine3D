package gui.components.textfield;

import javax.swing.text.*;

public class NumberTextField extends AbstractTextField
{
    public NumberTextField(String tooltipTitle)
    {
        super(tooltipTitle);

        PlainDocument doc = (PlainDocument) this.getDocument();
        doc.setDocumentFilter(new NumberFilter());
    }

    private static class NumberFilter extends DocumentFilter
    {
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException
        {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.insert(offset, string);

            if(test(sb.toString()))
            {
                super.insertString(fb, offset, string, attr);
            }
            else
            {
                // warn the user and don't allow the insert
            }
        }

        private boolean test(String text)
        {
            try
            {
                Integer.parseInt(text);
                return true;
            }
            catch(NumberFormatException e)
            {
                return false;
            }
        }

        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException
        {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if(test(sb.toString()))
            {
                super.replace(fb, offset, length, text, attrs);
            }
            else
            {
                // warn the user and don't allow the insert
            }
        }

        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException
        {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if(test(sb.toString()))
            {
                super.remove(fb, offset, length);
            }
            else
            {
                // warn the user and don't allow the insert
            }
        }
    }
}