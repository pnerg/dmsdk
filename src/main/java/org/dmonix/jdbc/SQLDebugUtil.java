package org.dmonix.jdbc;

import java.io.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Utility class for performing debugging an SQL result sets.
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public abstract class SQLDebugUtil {
    public static final String TAB = "\t";
    public static final byte TAB_BYTE = "\t".getBytes()[0];
    public static final String NEW_LINE = "\n";
    public static final byte NEW_LINE_BYTE = "\n".getBytes()[0];

    /**
     * Print the supplied resultset to the provided stream. <br>
     * The output stream will not be closed.
     * 
     * @param rs
     *            The result set
     * @param ostream
     *            The stream
     * @throws SQLException
     * @throws IOException
     */
    public static void printResultSet(ResultSet rs, OutputStream ostream) throws SQLException, IOException {
        printResultSet(rs, new OutputStreamWriter(ostream));
    }

    /**
     * Print the supplied resultset to the provided writer. <br>
     * The writer will not be closed.
     * 
     * @param rs
     *            The result set
     * @param writer
     *            The writer
     * @throws SQLException
     * @throws IOException
     */
    public static void printResultSet(ResultSet rs, Writer writer) throws SQLException, IOException {
        ResultSetMetaData rsm = rs.getMetaData();
        int columns = rsm.getColumnCount();
        Object data;
        for (int i = 1; i <= columns; i++) {
            writer.write(rsm.getColumnLabel(i));
            writer.write(TAB);
        }
        writer.write(NEW_LINE);
        while (rs.next()) {
            for (int i = 1; i <= columns; i++) {
                data = rs.getObject(i);
                if (data != null)
                    writer.write(data.toString());
                else
                    writer.write("");
                writer.write(TAB);
            }
            writer.write(NEW_LINE);
            writer.flush();
        }
        writer.flush();
    }

    /**
     * Print the supplied resultset to a string
     * 
     * @param rs
     *            The result set
     * @return The result set in string format
     * @throws SQLException
     * @throws IOException
     */
    public static String resultSetToString(ResultSet rs) throws SQLException, IOException {
        StringWriter sw = new StringWriter();
        printResultSet(rs, sw);
        return sw.toString();
    }
}
