package org.dmonix.io;

/**
 * Interface for classes that need to monitor object input streams using <code>org.dmonix.io.ObjectInputStreamMonitor</code>.
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
public interface ObjectInputStreamListener {

    /**
     * Reports an event on the monitored object input stream.
     * 
     * @param o
     *            The object that was received on the monitored stream
     */
    public void inputStreamAction(Object o);
}