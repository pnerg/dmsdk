package org.dmonix.thread;

import java.util.List;
import java.util.Vector;

/**
 * This class implements a thread pool.
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
public class ThreadPool {
    private List<PooledThread> pool = new Vector<PooledThread>();

    private int initialThreadCount = -1;
    private int currentThreadCount = -1;
    private long threadRequests = 0;

    /**
     * Creates an empty pool. <br>
     * Add further <code>PooledThreads</code> using the method <code>addThread</code>
     * 
     * @see addThread(Runnable)
     */
    public ThreadPool() {
        this(0);
    }

    /**
     * Creates a pool with an initial amount of PooledThreads.
     * 
     * @param initialThreadCount
     *            The inital number of threads
     */
    public ThreadPool(int initialThreadCount) {
        this.initialThreadCount = initialThreadCount;
        for (int i = 0; i < this.initialThreadCount; i++) {
            addThread();
        }
        resetThreadRequests();
    }

    /**
     * Adds a new thread to the pool. <br>
     * Each Runnable object will be wrapped inside a <code>PooledThread</code> object. <br>
     * The PooledThread is started immediately after it has been created.
     * 
     * @param runnable
     *            The runnable to use in the PooledThread
     * @see test.org.dmonix.thread.PooledThread.run
     */
    public void addThread() {
        PooledThread p = new PooledThread(this, "PooledThread" + pool.size() + 1);
        p.start();
        resetThreadRequests();
        this.currentThreadCount++;
    }

    /**
     * Adds a new thread to the pool. <br>
     * Each Runnable object will be wrapped inside a <code>PooledThread</code> object. <br>
     * The PooledThread is started immediately after it has been created.
     * 
     * @param runnable
     *            The runnable to use in the PooledThread
     * @see test.org.dmonix.thread.PooledThread.run
     */
    public void addThread(Runnable runnable) {
        PooledThread p = new PooledThread(this, "PooledThread" + pool.size() + 1);
        p.start();
        p.setRunnable(runnable);
        resetThreadRequests();
        this.currentThreadCount++;
    }

    /**
     * Get the initial size of the pool. <br>
     * This value will never change for the thread pool.
     * 
     * @return The initial amount of threads
     */
    public int getInitialThreadCount() {
        return this.initialThreadCount;
    }

    /**
     * Get the current size of the pool.
     * 
     * @return The current amount of threads
     */
    public int getCurrentThreadCount() {
        return this.currentThreadCount;
    }

    /**
     * Get the amount of running/busy threads.
     * 
     * @return The amount of running threads
     */
    public int getRunningThreadCount() {
        return this.currentThreadCount - this.pool.size();
    }

    /**
     * Get the number of times a thread has been requested from the pool.
     * 
     * @return The number of thread requests
     */
    public long getThreadRequests() {
        return this.threadRequests;
    }

    /**
     * Reset the counter for the number of times a thread has been requested from the pool.
     */
    public void resetThreadRequests() {
        this.threadRequests = 0;
    }

    /**
     * Get a thread from the pool. <br>
     * If there are no more free threads the method is put on hold using <code>wait()</code>
     * 
     * @return The thread
     */
    public synchronized PooledThread getThread() {
        PooledThread t;

        while (pool.size() == 0)
            try {
                wait();
            } catch (java.lang.InterruptedException e) {
            }
        ;

        t = (PooledThread) this.pool.remove(0);
        this.threadRequests++;

        return t;
    }

    /**
     * Return a thread to the pool. <br>
     * If the pool was previously empty, the method invokes <code>notify()</code> in order to release any thread waiting in the method <code>getThread()</code>
     * 
     * @param t
     *            The thread to return
     */
    public synchronized void returnThread(PooledThread t) {
        this.pool.add(t);

        if (this.pool.size() == 1)
            notifyAll();
    }

}