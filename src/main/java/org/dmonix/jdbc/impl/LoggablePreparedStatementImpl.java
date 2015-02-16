package org.dmonix.jdbc.impl;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dmonix.jdbc.LoggablePreparedStatement;

/**
 * This class is wrapped around a <code>java.sql.PreparedStatement</code> object in order to offer additional functionality not included by the Java JDBC API.<br>
 * Among other things this class allows the developer to create a string representation with all the bind variables set for the wrapped statement.
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
public class LoggablePreparedStatementImpl implements LoggablePreparedStatement {

    /** The logger instance for this class */
    private static final Logger logger = Logger.getLogger(LoggablePreparedStatementImpl.class.getName());

    private static final int INITIAL_PARAMLIST_SIZE = 10;

    /** The actual prepared statement */
    private PreparedStatement preparedStatement;

    /** The SQL statement. */
    private String sqlStatement;

    /**
     * The bind parameter values for this statement. The key is the parameter position (1..n) The value is the value of the bind parameter.
     */
    private Map<Integer, String> parameters;

    public LoggablePreparedStatementImpl(Connection con, String stmt) throws SQLException {
        this.sqlStatement = stmt;
        this.preparedStatement = con.prepareStatement(stmt);
        this.parameters = new HashMap<Integer, String>(INITIAL_PARAMLIST_SIZE);
    }

    /**
     * Commit all the transactions for this statement.
     * 
     * @param stmt
     *            The statement
     * @throws IllegalStateException
     *             If the connection has been closed
     * @throws SQLException
     *             if a database access error occurs or this
     */
    public void commit() throws SQLException {
        this.checkSessionState();

        try {
            this.preparedStatement.getConnection().commit();
        } catch (SQLException ex) {
            logger.log(Level.WARNING, "commit() : Failed to commit statement : \n" + toString(), ex);
            parameters = new HashMap<Integer, String>(INITIAL_PARAMLIST_SIZE);
            this.close();
            throw ex;
        }
        parameters = new HashMap<Integer, String>(INITIAL_PARAMLIST_SIZE);
    }

    /**
     * Rollback all the transactions for this statement.
     * 
     * @throws IllegalStateException
     *             If the connection has been closed
     * @throws SQLException
     *             if a database access error occurs or this
     */
    public void rollback() throws SQLException {
        this.checkSessionState();
        try {
            this.preparedStatement.getConnection().rollback();
        } catch (SQLException ex) {
            String error = "Failed to rollback statement : \n" + toString();
            logger.log(Level.SEVERE, "rollback() : " + error, ex);
            parameters = new HashMap<Integer, String>(INITIAL_PARAMLIST_SIZE);
            throw ex;
        }
        parameters = new HashMap<Integer, String>(INITIAL_PARAMLIST_SIZE);
    }

    /**
     * Close the statement.
     */
    public void close() {
        this.checkSessionState();

        try {
            this.preparedStatement.close();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "close() : Failed to close statement", ex);
        }

        this.sqlStatement = null;
        this.parameters = null;
        this.preparedStatement = null;
    }

    /**
     * Executes the SQL statement in this <code>LoggablePreparedStatementImpl</code> object, which may be any kind of SQL statement. Some prepared statements
     * return multiple results; the <code>execute</code> method handles these complex statements as well as the simpler form of statements handled by the
     * methods <code>executeQuery</code> and <code>executeUpdate</code>.
     * <P>
     * The <code>execute</code> method returns a <code>boolean</code> to indicate the form of the first result. You must call either the method
     * <code>getResultSet</code> or <code>getUpdateCount</code> to retrieve the result; you must call <code>getMoreResults</code> to move to any subsequent
     * result(s).
     * 
     * @return <code>true</code> if the first result is a <code>ResultSet</code> object; <code>false</code> if the first result is an update count or there is
     *         no result
     * @throws IllegalStateException
     *             If the connection has been closed
     * @throws SQLException
     *             if a database access error occurs or this
     */
    public boolean execute() throws SQLException {
        this.checkSessionState();

        boolean result = false;
        try {

            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, "execute() : Executing statement\n" + toString());
            }

            result = this.preparedStatement.execute();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "execute() : Failed to execute : \n" + toString(), ex);
            parameters = new HashMap<Integer, String>(INITIAL_PARAMLIST_SIZE);
            throw ex;
        }

        parameters = new HashMap<Integer, String>(INITIAL_PARAMLIST_SIZE);
        return result;
    }

    /**
     * Executes the SQL query in this <code>LoggablePreparedStatementImpl</code> object and returns the <code>ResultSet</code> object generated by the query.
     * 
     * @return a <code>ResultSet</code> object that contains the data produced by the query; never <code>null</code>
     * @throws IllegalStateException
     *             If the connection has been closed
     * @throws SQLException
     *             if a database access error occurs or this
     */
    public ResultSet executeQuery() throws SQLException {
        try {
            this.checkSessionState();

            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, "executeQuery() : Executing query\n" + toString());
            }

            parameters = new HashMap<Integer, String>(INITIAL_PARAMLIST_SIZE);
            return this.preparedStatement.executeQuery();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "executeQuery() : Failed to execute query : \n" + toString(), ex);
            throw ex;
        }
    }

    /**
     * Sets the designated parameter to the given Java <code>int</code> value. The driver converts this to an SQL <code>INTEGER</code> value when it sends it to
     * the database.
     * 
     * @param parameterIndex
     *            the first parameter is 1, the second is 2, ...
     * @param value
     *            the parameter value
     * @throws IllegalStateException
     *             If the connection has been closed
     * @throws SQLException
     *             if a database access error occurs or this
     */
    public void setInt(int parameterIndex, int value) throws SQLException {
        try {
            this.checkSessionState();
            this.storeParameter(parameterIndex, "" + value);
            this.preparedStatement.setInt(parameterIndex, value);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "setInt() : Failed to set the bind parameter : [index=" + parameterIndex + "] : [value=" + value + "] for statement \n"
                    + this.sqlStatement, ex);
            throw ex;
        }

    }

    /**
     * Sets the designated parameter to the given Java <code>long</code> value. The driver converts this to an SQL <code>BIGINT</code> value when it sends it to
     * the database.
     * 
     * @param parameterIndex
     *            the first parameter is 1, the second is 2, ...
     * @param value
     *            the parameter value
     * @throws IllegalStateException
     *             If the connection has been closed
     * @throws SQLException
     *             if a database access error occurs or this
     */
    public void setLong(int parameterIndex, long value) throws SQLException {
        try {
            this.checkSessionState();
            this.storeParameter(parameterIndex, "" + value);
            this.preparedStatement.setLong(parameterIndex, value);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "setLong() : Failed to set the bind parameter : [index=" + parameterIndex + "] : [value=" + value + "] for statement \n"
                    + this.sqlStatement, ex);
            throw ex;
        }
    }

    /**
     * Sets the designated parameter to the given Java <code>String</code> value. The driver converts this to an SQL <code>VARCHAR</code> or
     * <code>LONGVARCHAR</code> value (depending on the argument's size relative to the driver's limits on <code>VARCHAR</code> values) when it sends it to the
     * database.
     * 
     * @param parameterIndex
     *            the first parameter is 1, the second is 2, ...
     * @param value
     *            the parameter value
     * @throws IllegalStateException
     *             If the connection has been closed
     * @throws SQLException
     *             if a database access error occurs or this
     */
    public void setString(int parameterIndex, String value) throws SQLException {
        try {
            this.checkSessionState();
            this.storeParameter(parameterIndex, "'" + value + "'");
            this.preparedStatement.setString(parameterIndex, value);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "setString() : Failed to set the bind parameter : [index=" + parameterIndex + "] : [value=" + value + "] for statement \n"
                    + this.sqlStatement, ex);
            throw ex;
        }
    }

    /**
     * Returns a string representation of the SQL statement for this object. The method will attempt to replace all the bind variables with their correct value
     * before returning the SQL statement.
     */
    public String toString() {
        // copy the original SQL statement
        String sql = this.sqlStatement;

        /*
         * Replace each bind parameter in the sql string (:1, :2, ..., :x) with the actual parameter value
         */
        for (int i = 1; i <= this.parameters.size(); i++) {
            sql = sql.replaceFirst(":.*", this.parameters.get(i));
        }

        return sql;
    }

    /**
     * Verfies that the session/connection still is valid.
     * 
     * @throws IllegalStateException
     *             If the connection has been closed
     */
    private void checkSessionState() {
        // if(connectionClosed) {
        // throw new IllegalStateException("The statement and/or the connection is closed.");
        // }
    }

    /**
     * 
     * @param parameterIndex
     * @param value
     */
    private void storeParameter(int parameterIndex, String value) throws SQLException {
        if (this.parameters.put(parameterIndex, value) != null)
            throw new SQLException("The index[" + parameterIndex + "] is already bound for this statement");
    }
}
