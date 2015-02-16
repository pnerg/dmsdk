package org.dmonix.jdbc;

import java.sql.SQLException;

/**
 * This interface represents a connection towards a JDBC database.
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
public interface JDBCConnectionFactory {

    /**
     * Get a connection using the pre-defined username/password.
     * 
     * @return
     * @throws SQLException
     *             �
     * @see setPassword
     * @see setUser
     */
    public JDBCConnection getConnection() throws SQLException;

    /**
     * Get a connection using the provided username/password.
     * 
     * @param user
     *            The username
     * @param password
     *            The password
     * @return
     * @throws SQLException
     */
    public JDBCConnection getConnection(String user, String password) throws SQLException;

    /**
     * Get a connection pool with requested amount of connections.
     * 
     * @param initialSize
     *            Te intial size of the pool
     * @param maxSize
     *            The maximum size of the pool, zero (0) means unlimited amount
     * @return The connection pool
     * @throws SQLException
     */
    public JDBCConnectionPool getConnectionPool(int initialSize, int maxSize) throws SQLException;

    /**
     * Get the pre-defined password.
     * 
     * @return Returns the password, null if not set
     */
    public String getPassword();

    /**
     * Set the pre-defined password.
     * 
     * @param password
     *            The password to set.
     */
    public void setPassword(String password);

    /**
     * Get the pre-defined user.
     * 
     * @return Returns the user, null if not set
     */
    public String getUser();

    /**
     * Set the pre-defined user.
     * 
     * @param user
     *            The user to set.
     */
    public void setUser(String user);
}
