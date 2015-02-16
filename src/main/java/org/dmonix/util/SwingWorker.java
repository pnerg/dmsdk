package org.dmonix.util;

import javax.swing.SwingUtilities;

/**
 * The SwingWorker is an abstract class that you subclass to perform GUI-related work in a dedicated thread. An example can be that you want to fetch data from
 * an external source (e.g. database) but you don't want the GUI to halt during the loading of the data.<br>
 * By implementing this class you can in an easy way create a simple safe thread that performs you task.<br>
 * Example:
 * 
 * <pre>
 * <code>
 *  SwingWorker worker = new SwingWorker() {
 * 
 *       public Object construct() {
 *           // your code goes here.
 *           // you may return an object if required
 *           // otherwise just return null
 *           return null;
 *       }
 *             
 *       public void finished() {
 *           // This method is optional. 
 *           // The method will be executed after the thread
 *           // has performed it's job.
 *       }            
 *   };
 *   worker.start();         
 * </code>
 * </pre>
 * 
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public abstract class SwingWorker {

    private Object value; // see getValue(), setValue()

    private ThreadVar threadVar;

    /**
     * Start a thread that will call the <code>construct</code> method and then exit.
     */
    public SwingWorker() {

        final Runnable doFinished = new Runnable() {
            public void run() {
                finished();
            }
        };

        Runnable doConstruct = new Runnable() {
            public void run() {
                try {
                    setValue(construct());
                } finally {
                    threadVar.clear();
                }
                SwingUtilities.invokeLater(doFinished);
            }
        };

        Thread t = new Thread(doConstruct);
        threadVar = new ThreadVar(t);
    }

    /**
     * Get the value produced by the worker thread, or null if it hasn't been constructed yet.
     */
    public synchronized Object getValue() {
        return value;
    }

    /**
     * Compute the value to be returned by the <code>get</code> method.
     */
    public abstract Object construct();

    /**
     * Called on the event dispatching thread (not on the worker thread) after the <code>construct</code> method has returned.
     */
    public void finished() {
    }

    /**
     * A new method that interrupts the worker thread. Call this method to force the worker to stop what it's doing.
     */
    public void interrupt() {
        Thread t = threadVar.get();
        if (t != null) {
            t.interrupt();
        }
        threadVar.clear();
    }

    /**
     * Return the value created by the <code>construct</code> method. Returns null if either the constructing thread or the current thread was interrupted
     * before a value was produced.
     * 
     * @return the value created by the <code>construct</code> method
     */
    public Object get() {
        while (true) {
            Thread t = threadVar.get();
            if (t == null) {
                return getValue();
            }
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // propagate
                return null;
            }
        }
    }

    /**
     * Start the worker thread.
     */
    public void start() {
        Thread t = threadVar.get();
        if (t != null) {
            t.start();
        }
    }

    /**
     * Set the value produced by worker thread
     */
    private synchronized void setValue(Object obj) {
        value = obj;
    }

    /**
     * Class to maintain reference to current worker thread under separate synchronization control.
     */
    private static class ThreadVar {
        private Thread thread;

        private ThreadVar(Thread t) {
            thread = t;
        }

        synchronized Thread get() {
            return thread;
        }

        synchronized void clear() {
            thread = null;
        }
    }

}
