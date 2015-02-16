package org.dmonix.thread;

/**
 * The thread used in the <code>ThreadPool</code> class. The thread it self does not perform any action. It will merely allow the runnable object that is set
 * for this object to run.
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
public class PooledThread extends Thread {
    private Object mutex = new Object();

    private ThreadPool pool = null;

    private Runnable runnable = null;
    private boolean running = false;

    PooledThread(ThreadPool pool, String name) {
        super(name);
        this.pool = pool;
    }

    /**
     * Allows for this thread to execute its Runnable object once by releasing the mutex lock.
     */
    public void execute() {
        if (this.runnable == null)
            throw new IllegalStateException("The runnable has not been set");

        synchronized (mutex) {
            this.running = true;
            mutex.notifyAll();
        }
    }

    /**
     * Sets the Runnable object to run.
     * 
     * @param runnable
     *            The runnable used by this thread
     */
    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    /**
     * The thread is immediately put on a mutex wait. <br>
     * The mutex is released when the <code>execute</code> method is invoked. <br>
     * Once the mutex lock is released this thread will execute the <code>run</code> method on the Runnable object. <br>
     * After execution the thread is returned to the pool.
     * 
     * @see setRunnable(Runnable)
     * @see execute
     */
    public void run() {
        while (true) {
            try {
                this.pool.returnThread(this);

                /**
                 * Put the mutex object for wait. The state is released when notify() is invoked, see execute() in this class
                 */
                synchronized (mutex) {
                    while (!running)
                        mutex.wait();
                }

                this.running = false;
                this.runnable.run();
                this.runnable = null;
            } catch (InterruptedException ex) {
            } catch (Exception ex) {
            }
        }
    }

}