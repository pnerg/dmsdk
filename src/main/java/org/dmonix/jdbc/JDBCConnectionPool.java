package org.dmonix.jdbc;

import java.sql.SQLException;

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
public interface JDBCConnectionPool {

    /**
     * This will close and remove all connections from the pool.
     */
    public void close();

    /**
     * The method searches for an available connection in the connection pool.<br>
     * If no connection is available from the pool, the method is put on hold using <code>wait()</code>. If a connection is available from the pool, the
     * getConnection method leases the connection and returns it to the calling program
     * 
     * @return The connection
     * @throws SQLException
     */
    public JDBCConnection getConnection() throws SQLException;
}
