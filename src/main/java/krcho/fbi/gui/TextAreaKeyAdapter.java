/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krcho.fbi.gui;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Jozef Krcho
 */
public class TextAreaKeyAdapter extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_TAB) {
            e.consume();
            KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
        }
        if (e.getKeyCode() == KeyEvent.VK_TAB && e.isShiftDown()) {
            e.consume();
            KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent();
        }
    }
}
