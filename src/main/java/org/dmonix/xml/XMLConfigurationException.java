package org.dmonix.xml;

/**
 * This exception represent a error that has occurred durign configuration of the XML parser or XML transformer.
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 2.0
 */
public class XMLConfigurationException extends Error {

    /** Variable required by the Serializable interface. */
    private static final long serialVersionUID = 1;

    /**
     * Creates an exception with the provided message.
     * 
     * @param message
     *            The message
     */
    public XMLConfigurationException(String message) {
        super(message);
    }

    /**
     * Creates an exception with the provided message and cause.
     * 
     * @param message
     *            The message
     * @param cause
     *            The cause
     */
    public XMLConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
