package org.dmonix.gui;

import javax.swing.JRadioButtonMenuItem;
import java.awt.event.ActionEvent;

/**
 * A simple RadioButtonMenuItem that can hold a value. <br>
 * The value isn't used for anything, it's just a wasy way of creating a button that has a display text and a hidden value that can be retrieved.
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public class SimpleRadioButtonMenuItem extends JRadioButtonMenuItem {
    private static final long serialVersionUID = 7526472295622776147L;

    private String value;

    /**
     * Creates a button with a text and value.
     * 
     * @param text
     *            The text to display for the button
     * @param value
     *            The hidden value for the button
     */
    public SimpleRadioButtonMenuItem(String text, String value) {
        super(text);
        this.value = value;
    }

    /**
     * Gets the value parameter of the button.
     * 
     * @return The value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Sets the value parameter of the button.
     * 
     * @param value
     *            The hidden value for the button
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Changes the button to be selected. <br>
     * The method also fires an action event.
     * 
     * @param selected
     *            selected or not
     */
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        this.fireActionPerformed(new ActionEvent(this, 0, ""));
    }
}