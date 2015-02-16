package org.dmonix.jdbc;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * The interface represents a JDBC connection. It is essentially a wrapper around a real JDBC connection.<br>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.1
 */
public interface JDBCConnection {

    /**
     * Creates a <code>PreparedStatement</code> object that will generate <code>ResultSet</code> objects with the given type and concurrency. This method is the
     * same as the <code>prepareStatement</code> method above, but it allows the default result set type and concurrency to be overridden.
     * 
     * @param sql
     *            a <code>String</code> object that is the SQL statement to be sent to the database; may contain one or more ? IN parameters
     * @return a new PreparedStatement object containing the pre-compiled SQL statement that will produce <code>ResultSet</code> objects with the given type and
     *         concurrency
     * @exception SQLException
     *                if a database access error occurs or the given parameters are not <code>ResultSet</code> constants indicating type and concurrency
     */
    public LoggablePreparedStatement prepareStatement(String sql) throws SQLException;

    /**
     * Creates a <code>Statement</code> object that will generate <code>ResultSet</code> objects with the given type and concurrency. This method is the same as
     * the <code>createStatement</code> method above, but it allows the default result set type and concurrency to be overridden.
     * 
     * @param resultSetType
     *            a result set type; one of <code>ResultSet.TYPE_FORWARD_ONLY</code>, <code>ResultSet.TYPE_SCROLL_INSENSITIVE</code>, or
     *            <code>ResultSet.TYPE_SCROLL_SENSITIVE</code>
     * @param resultSetConcurrency
     *            a concurrency type; one of <code>ResultSet.CONCUR_READ_ONLY</code> or <code>ResultSet.CONCUR_UPDATABLE</code>
     * @return a new <code>Statement</code> object that will generate <code>ResultSet</code> objects with the given type and concurrency
     * @exception SQLException
     *                if a database access error occurs or the given parameters are not <code>ResultSet</code> constants indicating type and concurrency
     */
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException;

    /**
     * Makes all changes made since the previous commit/rollback permanent and releases any database locks currently held by this <code>Connection</code>
     * object. This method should be used only when auto-commit mode has been disabled.
     * 
     * @exception SQLException
     *                if a database access error occurs or this <code>Connection</code> object is in auto-commit mode
     */
    public void commit() throws SQLException;

    /**
     * Undoes all changes made in the current transaction and releases any database locks currently held by this <code>Connection</code> object. This method
     * should be used only when auto-commit mode has been disabled.
     * 
     * @exception SQLException
     *                if a database access error occurs or this <code>Connection</code> object is in auto-commit mode
     */
    public void rollback() throws SQLException;

    /**
     * Releases this <code>Connection</code> object's database and JDBC resources immediately instead of waiting for them to be automatically released.
     * <P>
     * Calling the method <code>close</code> on a <code>Connection</code> object that is already closed is a no-op.
     * <P>
     * <B>Note:</B> A <code>Connection</code> object is automatically closed when it is garbage collected. Certain fatal errors also close a
     * <code>Connection</code> object.
     * 
     * @exception SQLException
     *                if a database access error occurs
     */
    public void close() throws SQLException;

    /**
     * Sets this connection's auto-commit mode to the given state. If a connection is in auto-commit mode, then all its SQL statements will be executed and
     * committed as individual transactions. Otherwise, its SQL statements are grouped into transactions that are terminated by a call to either the method
     * <code>commit</code> or the method <code>rollback</code>. By default, new connections are in auto-commit mode.
     * <P>
     * The commit occurs when the statement completes or the next execute occurs, whichever comes first. In the case of statements returning a
     * <code>ResultSet</code> object, the statement completes when the last row of the <code>ResultSet</code> object has been retrieved or the
     * <code>ResultSet</code> object has been closed. In advanced cases, a single statement may return multiple results as well as output parameter values. In
     * these cases, the commit occurs when all results and output parameter values have been retrieved.
     * <P>
     * <B>NOTE:</B> If this method is called during a transaction, the transaction is committed.
     * 
     * @param autoCommit
     *            <code>true</code> to enable auto-commit mode; <code>false</code> to disable it
     * @exception SQLException
     *                if a database access error occurs
     * @see #getAutoCommit
     */
    public void setAutoCommit(boolean autoCommit) throws SQLException;

    /**
     * Retrieves the current auto-commit mode for this <code>Connection</code> object.
     * 
     * @return the current state of this <code>JDBCConnection</code> object's auto-commit mode
     * @exception SQLException
     *                if a database access error occurs
     * @see #setAutoCommit
     */
    public boolean getAutoCommit() throws SQLException;
}