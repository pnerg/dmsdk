package org.dmonix.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dmonix.util.DateHandler;

/**
 * Class with net related utility methods.
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
public abstract class NetUtil {
    /** The logger instance for this class */
    private static final Logger log = Logger.getLogger(NetUtil.class.getName());

    /** Content type plain set in the HTTP header. */
    public static final String CONTENT_TYPE_PLAIN = "text/plain";

    /** Content type XML set in the HTTP header. */
    public static final String CONTENT_TYPE_XML = "text/xml";

    // ============================
    // Character encoding set in the HTTP header.
    // ============================
    /**
     * Seven-bit ASCII, a.k.a. ISO646-US, a.k.a. the Basic Latin block of the Unicode character set.
     */
    public static final String ENCODING_US_ASCII = "US-ASCII";

    /** ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1. */
    public static final String ENCODING_ISO_8859_1 = "ISO-8859-1";

    /** Eight-bit UCS Transformation Format. */
    public static final String ENCODING_UTF_8 = "UTF-8";

    /** Sixteen-bit UCS Transformation Format, big-endian byte order. */
    public static final String ENCODING_UTF_16BE = "UTF-16BE";

    /** Sixteen-bit UCS Transformation Format, little-endian byte order. */
    public static final String ENCODING_UTF_16LE = "UTF-16LE";

    /**
     * Sixteen-bit UCS Transformation Format, byte order identified by an optional byte-order mark.
     */
    public static final String ENCODING_UTF_16 = "UTF-16";

    /**
     * The method returns the IP-address as seen by servers/clients outside the local LAN. <br>
     * The method does not return the locally configured IP-address for the client, i.e. the IP-address configured for the client in the LAN.<br>
     * Instead the method returns IP-address as perceived by servers outside the LAN.<br>
     * This means that the method returns the IP-address of the proxy/firewall/gateway server.<br>
     * The method attemps to contact <code>http://www.dmonix.org/getIP.jsp</code> to find out the IP-address.
     * 
     * @return The IP-address
     * @throws IOException
     * @throws MalformedURLException
     */
    public static String getExternalIPAddress() throws IOException, MalformedURLException {
        String response = sendHTTPPostRequest("www.dmonix.org", "/getIP.jsp").toLowerCase().trim();
        response = response.substring(response.indexOf("<h1>") + 4, response.indexOf("</h1>"));
        return response;
    }

    /**
     * Returns the date of the last modification for the remote file. <br>
     * This is done by sending a <code>HTTP HEAD</code> request and read the <code>Last-Modified</code> parameter in the answer. <br>
     * The date will be parsed using any of the pre-defined date formats in <code>org.dmonix.util.DateHandler</code>
     * 
     * @param url
     *            The URL of the file
     * @return The date, null if not possible to parse
     * @throws IOException
     */
    public static Date getModifiedDate(URL url) throws IOException {
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        urlCon.setRequestMethod("HEAD");
        urlCon.setUseCaches(false);
        urlCon.connect();
        return DateHandler.getDate(urlCon.getHeaderField("Last-Modified"));
    }

    /**
     * Returns the date of the last modification for the remote file. <br>
     * This is done by sending a <code>HTTP HEAD</code> request and read the <code>Last-Modified</code> parameter in the answer.
     * 
     * @param url
     *            The URL of the file
     * @param dateFormat
     *            The date format to use for the parse
     * @return The date, null if not possible to parse
     * @throws IOException
     */
    public static Date getModifiedDate(URL url, String dateFormat) throws IOException {
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        urlCon.setRequestMethod("HEAD");
        urlCon.setUseCaches(false);
        urlCon.connect();
        return DateHandler.getDate(urlCon.getHeaderField("Last-Modified"), dateFormat);
    }

    /**
     * Sets the system parameters used to determine the proxy. <br>
     * The method sets system properties, i.e.the method only needs to be run once.
     * 
     * @param proxyHost
     *            The host
     * @param proxyPort
     *            The port
     */
    public static void setProxy(String proxyHost, String proxyPort) {
        System.setProperty("http.proxyHost", proxyHost);
        System.setProperty("http.proxyPort", proxyPort);
    }

    /**
     * Resets/removes the system parameters for the proxy host.
     */
    public static void resetProxy() {
        System.getProperties().remove(("http.proxyHost"));
        System.getProperties().remove(("http.proxyPort"));
    }

    /**
     * Sets the socket timeout.
     * 
     * @param timeout
     *            socket timeout in millis
     */
    public void setSocketTimeout(String timeout) {
        System.setProperty("sun.net.client.defaultConnectTimeout", timeout);
        System.setProperty("sun.net.client.defaultReadTimeout", timeout);
    }

    /**
     * Resets/removes the socket timeout.
     */
    public static void resetSocketTimeout() {
        System.getProperties().remove(("sun.net.client.defaultConnectTimeout"));
        System.getProperties().remove(("sun.net.client.defaultReadTimeout"));
    }

    /**
     * Send a HTTP get request. <br>
     * Default HTTP parameters are:
     * <ul>
     * <li>port = 80</li>
     * <li>content type = text/plain</li>
     * </ul>
     * 
     * @param address
     *            The remote address
     * @param file
     *            The remote file
     * @return The response string from the remote server
     * @throws IOException
     * @throws MalformedURLException
     */
    public static String sendHTTPGetRequest(String address, String file) throws IOException, MalformedURLException {
        return sendHTTPGetRequest(address, 80, file, CONTENT_TYPE_PLAIN, "");
    }

    /**
     * Send a HTTP get request.
     * 
     * @param address
     *            The remote address
     * @param port
     *            The remote port
     * @param file
     *            The remote file
     * @param contentType
     *            content type
     * @param requestString
     *            The string to send
     * @return The response string from the remote server
     * @throws IOException
     * @throws MalformedURLException
     * @see #CONTENT_TYPE_PLAIN
     * @see #CONTENT_TYPE_XML
     */
    public static String sendHTTPGetRequest(String address, int port, String file, String contentType, String requestString) throws IOException,
            MalformedURLException {
        return sendRequest("HTTP", address, port, file, "GET", contentType, requestString);
    }

    /**
     * Send a HTTP post request. <br>
     * Default HTTP parameters are:
     * <ul>
     * <li>port = 80</li>
     * <li>content type = text/plain</li>
     * </ul>
     * 
     * @param address
     *            The remote address
     * @param file
     *            The remote file
     * @return The response string from the remote server
     * @throws IOException
     * @throws MalformedURLException
     */
    public static String sendHTTPPostRequest(String address, String file) throws IOException, MalformedURLException {
        return sendHTTPPostRequest(address, 80, file, CONTENT_TYPE_PLAIN, "");
    }

    /**
     * Send a HTTP post request.
     * 
     * @param address
     *            The remote address
     * @param port
     *            The remote port
     * @param file
     *            The remote file
     * @param contentType
     *            content type
     * @param requestString
     *            The string to send
     * @return The response string from the remote server
     * @throws IOException
     * @throws MalformedURLException
     * @see #CONTENT_TYPE_PLAIN
     * @see #CONTENT_TYPE_XML
     */
    public static String sendHTTPPostRequest(String address, int port, String file, String contentType, String requestString) throws IOException,
            MalformedURLException {
        return sendRequest("HTTP", address, port, file, "POST", contentType, requestString);
    }

    /**
     * Send a HTTPS get request. <br>
     * Default HTTPS parameters are:
     * <ul>
     * <li>port = 80</li>
     * <li>content type = text/plain</li>
     * </ul>
     * 
     * @param address
     *            The remote address
     * @param file
     *            The remote file
     * @return The response string from the remote server
     * @throws IOException
     * @throws MalformedURLException
     */
    public static String sendHTTPSGetRequest(String address, String file) throws IOException, MalformedURLException {
        return sendHTTPSGetRequest(address, 80, file, CONTENT_TYPE_PLAIN, "");
    }

    /**
     * Send a HTTPS get request.
     * 
     * @param address
     *            The remote address
     * @param port
     *            The remote port
     * @param file
     *            The remote file
     * @param contentType
     *            content type
     * @param requestString
     *            The string to send
     * @return The response string from the remote server
     * @throws IOException
     * @throws MalformedURLException
     * @see #CONTENT_TYPE_PLAIN
     * @see #CONTENT_TYPE_XML
     */
    public static String sendHTTPSGetRequest(String address, int port, String file, String contentType, String requestString) throws IOException,
            MalformedURLException {
        return sendRequest("HTTPS", address, port, file, "GET", contentType, requestString);
    }

    /**
     * Send a HTTPS post request. <br>
     * Default HTTPS parameters are:
     * <ul>
     * <li>port = 80</li>
     * <li>content type = text/plain</li>
     * </ul>
     * 
     * @param address
     *            The remote address
     * @param file
     *            The remote file
     * @return The response string from the remote server
     * @throws IOException
     * @throws MalformedURLException
     */
    public static String sendHTTPSPostRequest(String address, String file) throws IOException, MalformedURLException {
        return sendHTTPSPostRequest(address, 80, file, CONTENT_TYPE_PLAIN, "");
    }

    /**
     * Send a HTTPS post request.
     * 
     * @param address
     *            The remote address
     * @param port
     *            The remote port
     * @param file
     *            The remote file
     * @param contentType
     *            content type
     * @param requestString
     *            The string to send
     * @return The response string from the remote server
     * @throws IOException
     * @throws MalformedURLException
     * @see #CONTENT_TYPE_PLAIN
     * @see #CONTENT_TYPE_XML
     */
    public static String sendHTTPSPostRequest(String address, int port, String file, String contentType, String requestString) throws IOException,
            MalformedURLException {
        return sendRequest("HTTPS", address, port, file, "POST", contentType, requestString);
    }

    /**
     * Receive data from the server.<br>
     * The SocketTimeoutException exception is propagated in order to let the sending class handle the exception and perhaps display an message to the user.
     * 
     * @param httpURLCon
     *            The HttpURLConnection object
     * @return The response
     * @throws SocketTimeoutException
     */
    private static String receive(HttpURLConnection httpURLCon) throws SocketTimeoutException {
        String tmpStr = "";
        StringBuffer stringBuf = new StringBuffer();
        boolean errorInResponse = false;

        try {
            if (log.isLoggable(Level.FINER)) {
                log.finer("==Header fields==");
                Map<String, List<String>> map = httpURLCon.getHeaderFields();
                for (String key : map.keySet()) {
                    log.finer(key + " : " + map.get(key));
                }
                log.finer("==End Header fields==");
            }

            BufferedReader inBuffer;
            try {
                inBuffer = new BufferedReader(new InputStreamReader(httpURLCon.getInputStream()));
            } catch (SocketTimeoutException ex) {
                throw ex;
            } catch (Exception ex) {
                log.log(Level.WARNING, "Cannot open response stream from server", ex);
                inBuffer = new BufferedReader(new InputStreamReader(httpURLCon.getErrorStream()));
                errorInResponse = true;
            }

            while ((tmpStr = inBuffer.readLine()) != null) {
                stringBuf.append(tmpStr + "\n");
            }

            inBuffer.close();
            inBuffer = null;

            if (errorInResponse) {
                log.warning("Error from server : \n" + stringBuf.toString());
                return null;
            }

            if (log.isLoggable(Level.FINE))
                log.fine("Response from server: \n" + stringBuf.toString());

        } catch (SocketTimeoutException ex) {
            log.log(Level.WARNING, "Socket timed out", ex);
            throw ex;
        } catch (Exception ex) {
            log.log(Level.WARNING, "Error while receiving response", ex);
            return null;
        }

        return stringBuf.toString();
    }

    private static String sendRequest(String protocol, String address, int port, String file, String requestMethod, String contentType, String requestString)
            throws IOException, MalformedURLException {
        URL url = new URL(protocol, address, port, file);

        if (log.isLoggable(Level.FINE))
            log.log(Level.FINE, "Connect to : " + url.toString());

        HttpURLConnection httpURLCon = (HttpURLConnection) url.openConnection();
        httpURLCon.setDoOutput(true);
        httpURLCon.setDoInput(true);
        httpURLCon.setRequestMethod(requestMethod);
        if (requestString != null && requestString.length() > 0) {
            httpURLCon.setRequestProperty("Content-Type", contentType);
            httpURLCon.setRequestProperty("Content-Length", "" + requestString.length());

            DataOutputStream dostream = new DataOutputStream(httpURLCon.getOutputStream());

            if (log.isLoggable(Level.FINE))
                log.fine("requestString : \n" + requestString + "\n" + "length=" + requestString.length());

            dostream.writeBytes(requestString);
            dostream.flush();
            dostream.close();
        }

        return receive(httpURLCon);
    }
}
