package org.dmonix.jdbc.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.dmonix.jdbc.JDBCConnection;
import org.dmonix.jdbc.LoggablePreparedStatement;

/**
 * 
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
public class JDBCConnectionImpl implements JDBCConnection {

    /** The logger instance for this class */
    protected final Logger logger = Logger.getLogger(getClass().getName());

    private Connection connection;

    public JDBCConnectionImpl(Connection con) {
        this.connection = con;
    }

    public LoggablePreparedStatement prepareStatement(String sql) throws SQLException {
        return new LoggablePreparedStatementImpl(this.connection, sql);
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return this.connection.createStatement(resultSetType, resultSetConcurrency);
    }

    public void commit() throws SQLException {
        this.connection.commit();
    }

    public void rollback() throws SQLException {
        this.connection.rollback();
    }

    public void close() throws SQLException {
        this.connection.close();
    }

    Connection getConnection() {
        return this.connection;
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        this.connection.setAutoCommit(autoCommit);
    }

    public boolean getAutoCommit() throws SQLException {
        return this.connection.getAutoCommit();
    }

}
