package org.dmonix.gui;

import javax.swing.JCheckBoxMenuItem;
import java.awt.event.ActionEvent;

/**
 * A simple check box menu item that can hold a value.
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
public class SimpleCheckBoxMenuItem extends JCheckBoxMenuItem {
    private static final long serialVersionUID = 7526472295622776147L;

    private String value = null;

    /**
     * Creates a button with a text and value.
     * 
     * @param text
     *            The text to display for the button
     * @param selected
     *            If the button is checked or not
     * @param value
     *            The hidden value for the button
     */
    public SimpleCheckBoxMenuItem(String text, boolean selected, String value) {
        super(text, selected);
        this.value = value;
    }

    /**
     * Creates a button with a text and value.
     * 
     * @param text
     *            The text to display for the button
     * @param value
     *            The hidden value for the button
     */
    public SimpleCheckBoxMenuItem(String text, String value) {
        this(text, false, value);
    }

    /**
     * Gets the parameter value of the button.
     * 
     * @return The value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Sets the parameter value of the button.
     * 
     * @param value
     *            The hidden value for the button
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Changes the button to be selected or not. <br>
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