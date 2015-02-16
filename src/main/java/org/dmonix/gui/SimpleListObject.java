package org.dmonix.gui;

/**
 * Simple list object that holds both a text and a value. <br>
 * The text is the text that will be displayed in the list when is used, the value is never shown to the user.
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
public class SimpleListObject implements Comparable {
    private String text;
    private Object value;

    /**
     * Creats a list object.
     * 
     * @param text
     *            The text of the list object
     * @param value
     *            The value of the list object
     */
    public SimpleListObject(String text, Object value) {
        this.text = text;
        this.value = value;
    }

    /**
     * Get the text of the object.
     * 
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Set the text of the object.
     * 
     * @param text
     *            The text of the object
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Get the value of the object.
     * 
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Set the value of the object.
     * 
     * @param value
     *            The value of the object
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Returns the <code>getText()</code> value of the object.
     * 
     * @return the text
     * @see setText(String)
     */
    public String toString() {
        return text;
    }

    /**
     * Performs a <code>toString</code> comparison between this object and the supplied object.
     * 
     * @param o
     *            The object to compare to
     * @return The result
     */
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }
}