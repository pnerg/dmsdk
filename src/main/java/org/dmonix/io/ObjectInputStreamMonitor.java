package org.dmonix.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Vector;

/**
 * The class that monitors an inputstream for incoming objects. <br>
 * The class will create a thread that waits for incoming objects on the monitored input stream. <br>
 * For each incoming object an event is propagated to each registered listener.
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
public class ObjectInputStreamMonitor {
    /** The logger instance for this class. */
    // private static final Logger log = Logger.getLogger(ObjectInputStreamMonitor.class.getName());

    private ObjectInputStream istream = null;

    private int sleepTime = -1;

    private List<ObjectInputStreamListener> listeners = new Vector<ObjectInputStreamListener>();

    private ListenerThread listenerThread = new ListenerThread();

    private InputStream is = null;

    /**
     * Creates the stream monitor.
     * 
     * @param is
     *            The input stream to monitor
     * @param sleepTime
     *            The time to sleep after each received object
     * @throws IOException
     */
    public ObjectInputStreamMonitor(InputStream is, int sleepTime) throws IOException {
        this.is = is;
        this.sleepTime = sleepTime;
    }

    public void start() {
        this.listenerThread.start();
    }

    /**
     * Adds a listener.
     * 
     * @param listener
     *            the listener
     */
    public void addListener(ObjectInputStreamListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Removes a listener.
     * 
     * @param listener
     *            the listener to remove
     */
    public void removeListener(ObjectInputStreamListener listener) {
        this.listeners.remove(listener);
    }

    /**
     * Will stop the listener thread and dispose this object.
     */
    public void dispose() {
        this.listenerThread.interrupt();
    }

    /**
     * Internal thread for monitoring the input stream
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
    private class ListenerThread extends Thread {
        public void run() {
            try {
                istream = new ObjectInputStream(is);
                while (!isInterrupted()) {
                    try {
                        Object o = istream.readObject();
                        for (ObjectInputStreamListener listener : listeners) {
                            listener.inputStreamAction(o);
                        }
                    } catch (EOFException eofEx) {
                        try {
                            super.sleep(sleepTime);
                        } catch (InterruptedException ex1) {
                        }
                    }
                }
            } catch (Exception ex) {
                // log.log(Level.WARNING, ex.getMessage(), ex);
            }
        }

    }

}