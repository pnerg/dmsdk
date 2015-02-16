package org.dmonix.jdbc;

/**
 * This connection factory is specilized for creating connections towards an MySQL database. The class expects the Oracle driver
 * <code>(com.mysql.jdbc.Driver)</code> to be present in the class path.
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
public class MySQLJDBCConnectionFactory extends GenericJDBCConnectionFactory {

    /**
     * Creates a connection factory towards an MySQL database.
     * 
     * @param host
     *            The host
     * @param port
     *            The port
     * @param dbName
     *            The name of the database
     * @param user
     *            The user
     * @param password
     *            The password
     * @throws InstantiationException
     */
    public MySQLJDBCConnectionFactory(String host, int port, String dbName, String user, String password) throws InstantiationException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new InstantiationException("Failed to load the class \"com.mysql.jdbc.Driver\".");
        }

        /*
         * Format of the connection string : jdbc:mysql://[host]:[port]/[dbname]
         */
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:mysql://");
        sb.append(host);
        sb.append(":");
        sb.append(port);
        sb.append("/");
        sb.append(dbName);

        super.setUrl(sb.toString());
        super.setUser(user);
        super.setPassword(password);
    }
}
