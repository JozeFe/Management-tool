/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.gui;

import javax.swing.JTextArea;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Jozef Krcho
 */
public class TextAreaFilter extends PlainDocument {

    private JTextArea textArea;
    private int limit;
    
    public TextAreaFilter(JTextArea textArea, int limit) {
        this.textArea = textArea;
        this.limit = limit;
    }

    @Override
    public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
        if (str == null || textArea.getText().length() + str.length() > limit) {
            return;
        }
        super.insertString(offs, str, a);
    }
}
