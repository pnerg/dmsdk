package org.dmonix.jdbc.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dmonix.jdbc.JDBCConnection;
import org.dmonix.jdbc.JDBCConnectionFactory;
import org.dmonix.jdbc.JDBCConnectionPool;

/**
 * The class makes connections available to calling program in its getConnection method. <br>
 * This method searches for an available connection in the connection pool. <br>
 * If no connection is available from the pool, a new connection is created. <br>
 * If a connection is available from the pool, the getConnection method leases the connection and returns it to the calling program.
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
public class JDBCConnectionPoolImpl implements JDBCConnectionPool {

    private static final Logger log = Logger.getLogger(JDBCConnectionPoolImpl.class.getName());
    private static final long TIME_OUT = 60000;

    private List<PooledJDBCConnectionImpl> freeConnections = new Vector<PooledJDBCConnectionImpl>();
    private List<PooledJDBCConnectionImpl> usedConnections = new Vector<PooledJDBCConnectionImpl>();
    private ConnectionReaper reaper;

    /**
     * Constructs a pool of the initial size.
     * 
     * @param url
     *            The URL for the connection
     * @param user
     *            The user for the connection
     * @param password
     *            The password for the connection
     * @param initialSize
     *            The initial size of the pool
     * @param autoCommit
     *            If the connections shall be autocommiting
     * @throws SQLException
     */
    public JDBCConnectionPoolImpl(JDBCConnectionFactory factory, int initialSize, int maxSize) throws SQLException {
        this.freeConnections = new Vector<PooledJDBCConnectionImpl>(initialSize);

        for (int i = 0; i < initialSize; i++) {
            PooledJDBCConnectionImpl c = new PooledJDBCConnectionImpl(this, (JDBCConnectionImpl) factory.getConnection());
            freeConnections.add(c);
        }
    }

    /**
     * This will close and remove all connections from the pool.
     */
    public void close() {
        for (PooledJDBCConnectionImpl conn : freeConnections) {
            try {
                conn.close2();
            } catch (SQLException ex) {
            }
        }
        freeConnections.clear();

        for (PooledJDBCConnectionImpl conn : usedConnections) {
            try {
                conn.close2();
            } catch (SQLException ex) {
            }
        }
        usedConnections.clear();

    }

    /**
     * The method searches for an available connection in the connection pool.<br>
     * If no connection is available from the pool, the method is put on hold using <code>wait()</code>. If a connection is available from the pool, the
     * getConnection method leases the connection and returns it to the calling program
     * 
     * @return The connection
     * @throws SQLException
     */
    public JDBCConnection getConnection() throws SQLException {
        while (freeConnections.size() == 0)
            try {
                if (log.isLoggable(Level.INFO))
                    log.log(Level.INFO, "Connection pool is empty, thread put on hold");

                wait();
            } catch (java.lang.InterruptedException e) {
            }
        ;

        PooledJDBCConnectionImpl c = (PooledJDBCConnectionImpl) this.freeConnections.remove(0);
        usedConnections.add(c);
        c.lease();

        if (log.isLoggable(Level.FINER))
            log.log(Level.FINER, "Got connection : " + c.toString());

        return c;
    }

    public void startConnectionReaper() {
        if (this.reaper != null)
            throw new IllegalStateException("The connection reaper has already been started.");

        this.reaper = new ConnectionReaper();
        this.reaper.start();
    }

    /**
     * This will return a connection to the pool. <br>
     * If the pool was previously empty, the method invokes <code>notify()</code> in order to release any thread waiting in the method
     * <code>getConnection()</code>
     * 
     * @param conn
     *            The connection to return
     */
    void returnConnection(PooledJDBCConnectionImpl conn) {
        if (log.isLoggable(Level.FINER))
            log.log(Level.FINER, "Returned connection : " + conn.toString());

        conn.expireLease();
        usedConnections.remove(conn);
        freeConnections.add(conn);

        if (freeConnections.size() == 1)
            notifyAll();
    }

    /**
     * The method will remove any hanging, dead connections. <br>
     * A connection is dead if the following conditions are met.
     * <ul>
     * <li>The connection is flagged as being in use.</li>
     * <li>The connection is older than a preset connection time out.</li>
     * <li>The connection fails a validation check.</li>
     * </ul>
     */
    private void reapConnections() {
        long staleTime = System.currentTimeMillis() - TIME_OUT;
        for (PooledJDBCConnectionImpl conn : usedConnections) {
            if ((conn.inUse()) && (staleTime > conn.getLastUse()) && (!conn.validate())) {
                log.log(Level.WARNING, "Found a stale connection : " + conn.toString());
                usedConnections.remove(conn);
            }
        }
    }

    /**
     * The connection pool class provides a dead connection reaper to handle locked, hanging connections. <br>
     * At an even interval the class will invoke the method <code>reapConnections</code> on the ConnectionPool class.
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
    private class ConnectionReaper extends Thread {

        private ConnectionReaper() {
        }

        public void run() {
            while (true) {
                try {
                    sleep(TIME_OUT);
                } catch (InterruptedException e) {
                }
                reapConnections();
            }
        }
    }

}
