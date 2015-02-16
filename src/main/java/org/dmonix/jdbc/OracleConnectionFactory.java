package org.dmonix.jdbc;

/**
 * This connection factory is specilized for creating connections towards an Oracle database. The class expects the Oracle driver
 * <code>(oracle.jdbc.driver.OracleDriver)</code> to be present in the class path.
 * 
 * @author Peter Nerg
 * @since 2.0
 */
public class OracleConnectionFactory extends GenericJDBCConnectionFactory {

    /**
     * Creates a connection factory towards an Oracle database.
     * 
     * @param host
     *            The host for the database (either ip-address or host name)
     * @param port
     *            The port for the database listener (normally 1521)
     * @param sid
     *            The Service ID for the database
     * @param user
     *            The user/schema
     * @param password
     *            The password for the user
     * @throws InstantiationException
     */
    public OracleConnectionFactory(String host, int port, String sid, String user, String password) throws InstantiationException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            throw new InstantiationException("Failed to load the class \"oracle.jdbc.driver.OracleDriver\".");
        }

        /*
         * Format of the connection string : jdbc:oracle:thin:@[host]:[port]:[sid]
         */
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:oracle:thin:@");
        sb.append(host);
        sb.append(":");
        sb.append(port);
        sb.append(":");
        sb.append(sid);
        super.setUrl(sb.toString());
        super.setUser(user);
        super.setPassword(password);
    }

    /**
     * Creates a connection factory towards an Oracle database. This constructor will use the default listener port 1521.
     * 
     * @param host
     *            The host for the database (either ip-address or host name)
     * @param sid
     *            The Service ID for the database
     * @param user
     *            The user/schema
     * @param psw
     *            The password for the user
     * @throws InstantiationException
     */
    public OracleConnectionFactory(String host, String sid, String user, String password) throws InstantiationException {
        this(host, 1521, sid, user, password);
    }
}
