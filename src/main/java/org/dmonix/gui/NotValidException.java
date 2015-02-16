package org.dmonix.gui;

/**
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public class NotValidException extends Exception {

    private static final long serialVersionUID = 7526472295622776147L;

    /**
     * Constructs a default <code>NotValidException</code> using a specified message.
     * 
     * @param message
     *            the message
     */
    public NotValidException(String message) {
        super(message);
    }
}