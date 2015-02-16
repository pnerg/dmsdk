package org.dmonix.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dmonix.jdbc.impl.JDBCConnectionImpl;
import org.dmonix.jdbc.impl.JDBCConnectionPoolImpl;

/**
 * This class represents a factory for creating connections and/or connection pools towards any type of database. In order for this class to be able to create
 * connections the driver for the specific target database must be loaded in the classloader.<br>
 * This is done by <code>Class.forName("the-driver-class");</code><br>
 * For a more vendor specific factory see the sub types to this class.
 * 
 * @author Peter Nerg
 * @since 2.0
 */
public class GenericJDBCConnectionFactory implements JDBCConnectionFactory {

    private String url;
    private String user;
    private String password;

    /**
     * Creates a factory using the specified URL. Examples of an URL:<br>
     * <code>jdbc:oracle:thin:@[host]:[port]:[sid]</code> <code>jdbc:mysql://[host]:[port]/[dbname]</code>
     * 
     * @param url
     *            The connection URL
     */
    public GenericJDBCConnectionFactory(String url) {
        this(url, null, null);
    }

    /**
     * Creates a factory using the specified URL. This constructor will also set the pre-defined user and password
     * 
     * @param url
     *            The connection URL
     * @param user
     *            The pre-defined user
     * @param password
     *            The pre-defined passwword
     */
    public GenericJDBCConnectionFactory(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    protected GenericJDBCConnectionFactory() {
    }

    /**
     * Get a connection to the database using the provided user/password.
     * 
     * @param user
     * @param psw
     * @return The connection
     * @throws SQLException
     */
    public JDBCConnection getConnection(String user, String psw) throws SQLException {
        Connection con = DriverManager.getConnection(url, user, psw);
        con.setAutoCommit(false);
        return new JDBCConnectionImpl(con);
    }

    /**
     * Get a connection to the database using pre-defined provided user/password.
     * 
     * @return The connection
     * @throws SQLException
     */
    public JDBCConnection getConnection() throws SQLException {
        return this.getConnection(this.user, this.password);
    }

    /**
     * Creates a connection pool. The connections in the pool will be created using the pre-defined user/password
     * 
     * @param initialSize
     *            The intial size of the pool
     * @param maxSize
     *            The maximum size of the pool
     * @see setUser
     * @see setPassword
     */
    public JDBCConnectionPool getConnectionPool(int initialSize, int maxSize) throws SQLException {
        return new JDBCConnectionPoolImpl(this, initialSize, maxSize);
    }

    /**
     * Get the pre-defined password.
     * 
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the pre-defined password.
     * 
     * @param password
     *            The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the URL that is used for creating the connections.
     * 
     * @return Returns the url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Get the pre-defined user.
     * 
     * @return Returns the user.
     */
    public String getUser() {
        return user;
    }

    /**
     * Set the pre-defined user.
     * 
     * @param user
     *            The user to set.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Set the pre-defined URL.
     * 
     * @param url
     *            The url to set.
     */
    protected void setUrl(String url) {
        this.url = url;
    }
}
