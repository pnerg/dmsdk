package org.dmonix.net;

import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class is used to parse out information from a HTTP POST body.
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public class HTTPRequestParser {
    /** The logger instance for this class */
    private static final Logger log = Logger.getLogger(HTTPRequestParser.class.getName());

    private DataInputStream istream;
    private long contentLength;
    private Hashtable<String, String> requestParameters = new Hashtable<String, String>();
    private Vector<String> fileNameList = new Vector<String>();
    private int totalBytesRead = 0;
    private File fileDir = null;

    public HTTPRequestParser(InputStream istream, long contentLength) throws IOException {
        this(istream, contentLength, null);
    }

    public HTTPRequestParser(InputStream istream, long contentLength, File fileDir) throws IOException {
        log.fine("ContentLength = " + contentLength);
        this.istream = new DataInputStream(istream);
        this.contentLength = contentLength;
        this.fileDir = fileDir;
        parseStream();
    }

    /**
     * Get the value of a specified parameter
     * 
     * @param name
     *            The parameter
     * @return the value of null if not found
     */
    public String getParameter(String name) {
        Object o = requestParameters.get(name);
        if (o == null)
            return null;
        else
            return o.toString();
    }

    /**
     * Get a list of all parameter names
     * 
     * @return The parameter names
     */
    public Enumeration getParameterNames() {
        return this.requestParameters.keys();
    }

    /**
     * Retruns a list with the names of all files that where stored from the request body.
     * 
     * @return The file names
     */
    public Enumeration getFileNames() {
        return this.fileNameList.elements();
    }

    private void parseStream() throws IOException {
        while (readStreamUntilDelimiter().length() != 0 && totalBytesRead < contentLength) {
            parseParameter();
        }
    }

    private void parseParameter() throws IOException {
        if (log.isLoggable(Level.FINE))
            log.fine("found parameter to parse at byte " + totalBytesRead);

        String dataType = readStreamUntilDelimiter();
        /**
         * It's a file
         */
        if (dataType.indexOf("filename=\"") > 0 && fileDir != null) {
            parseFile(dataType.substring(dataType.indexOf("filename=\"") + 10, dataType.lastIndexOf("\"")));
        }
        /**
         * It's a parameter
         */
        else {
            String name = dataType.substring(dataType.indexOf("name=\"") + 6, dataType.lastIndexOf("\""));

            /**
             * If the parameter has a content-type declaration then read that line and then read the last two CRLF characters Otherwise the first read has
             * already remove the CRLF characters.
             */
            if (readStreamUntilDelimiter().length() > 0) {
                readStreamUntilDelimiter();
            }

            String value = readStreamUntilDelimiter().trim();

            requestParameters.put(name, value);
        }
    }

    /**
     * Parse the file data from the stream and store the file
     * 
     * @param fileName
     *            The name of the file
     * @throws IOException
     */
    private void parseFile(String fileName) throws IOException {
        // in case the name is specified with a path then remove the path
        int index = fileName.lastIndexOf("\\");
        if (index > -1) {
            fileName = fileName.substring(index + 1);
        }

        this.fileNameList.add(fileName);

        if (log.isLoggable(Level.FINE))
            log.fine("found file to parse at byte " + totalBytesRead + " : " + fileName);

        readStreamUntilDelimiter();
        skipBytes(2);

        byte[] dataArray = new byte[] { -1, -1, -1, -1 };
        int imageSize = 0;
        int readCounter = 0;

        File ouputFile = new File(fileDir, fileName);
        BufferedOutputStream ostream = new BufferedOutputStream(new FileOutputStream(ouputFile));
        while (totalBytesRead < contentLength) {
            totalBytesRead++;
            for (int i = 0; i < 3; i++) {
                dataArray[i] = dataArray[i + 1];
            }

            dataArray[3] = istream.readByte();
            readCounter++;

            if (dataArray[0] == 0x0D && dataArray[1] == 0x0A && dataArray[2] == 0x2D && dataArray[3] == 0x2D) {
                ostream.flush();
                ostream.close();
                if (log.isLoggable(Level.FINE))
                    log.fine("Wrote " + imageSize + " bytes for the image");
                return;
            } else if (readCounter >= 4) {
                ostream.write(dataArray[0]);
                imageSize++;
            }
        }
        log.log(Level.WARNING, "Exceeded input buffer size, deleting output file!");
        ostream.flush();
        ostream.close();
        ouputFile.delete();
    }

    /**
     * Read the stream until #0D and #0A are encountered.
     * 
     * @return Return the string representation of the read data
     * @throws IOException
     */
    private String readStreamUntilDelimiter() throws IOException {
        StringBuffer sb = new StringBuffer();
        byte currentByte = 0, prevByte;
        while (totalBytesRead < contentLength) {
            totalBytesRead++;
            prevByte = currentByte;
            if ((currentByte = istream.readByte()) == 0x0A && prevByte == 0x0D)
                break;

            sb.append(new String(new byte[] { currentByte }));
        }

        if (log.isLoggable(Level.FINE))
            log.fine("readStreamUntilDelimiter\n" + sb.toString().trim());

        return sb.toString().trim();
    }

    /**
     * Skips a specified number of bytes in the input stream
     * 
     * @param bytesToSkip
     *            Number of bytes to skip
     * @throws IOException
     */
    private void skipBytes(int bytesToSkip) throws IOException {
        for (int i = 0; i < bytesToSkip; i++) {
            istream.read();
            totalBytesRead++;
        }

    }
}