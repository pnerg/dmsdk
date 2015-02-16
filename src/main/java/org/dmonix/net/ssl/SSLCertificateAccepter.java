package org.dmonix.net.ssl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.net.ssl.*;

/**
 * Singleton class for handling SSL certificates. <br>
 * The default built-in <code>javax.net.ssl.HostnameVerifier</code> will always accept all certificates.
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
public class SSLCertificateAccepter {
    /** The name of the principal. */
    public static final String PRINCIPAL_NAME = "RFC2253";

    /** The logger instance for this class */
    private static final Logger log = Logger.getLogger(SSLCertificateAccepter.class.getName());

    private static SSLCertificateAccepter sslCertificateAccepter = null;

    /**
     * Creates the default trust manager.
     */
    private X509TrustManager x509TrustManager = new X509TrustManager() {

        /**
         * Does nothing.
         * 
         * @param chain
         *            -
         * @param authType
         *            -
         */
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        /**
         * Does nothing.
         * 
         * @param chain
         *            -
         * @param authType
         *            -
         */
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        /**
         * Always returns null.
         * 
         * @return always null
         */
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };

    /**
     * Creates a class that allows the user to choose whether to trust the host/certificate or not.
     */
    private HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        /**
         * Verifies if a certificate is trusted or not, always returns true.
         * 
         * @param hostname
         *            The host name
         * @param session
         *            The SSL session (includes the certificate)
         * @return always true
         */
        public boolean verify(String hostname, SSLSession session) {

            Certificate receivedCert = null;
            // Try to get the certificate and see if it is stored in the local KeyStore
            try {
                // Get the first (and most likely only) certificate
                receivedCert = session.getPeerCertificates()[0];
            } catch (Exception ex) {
                log.log(Level.SEVERE, "Error when checking certificate", ex);
                return false;
            }

            if (log.isLoggable(Level.FINE)) {
                // Create a StringBuffer with information on the certificate
                String s = ((X509Certificate) receivedCert).getIssuerX500Principal().getName(PRINCIPAL_NAME);
                s = s.replaceAll("CN=", "Issuer     : ");
                s = s.replaceAll("OU=", "Division   : ");
                s = s.replaceAll("O=", "Company    : ");
                s = s.replaceAll("L=", "Location   : ");
                s = s.replaceAll("C=", "Country    : ");
                String[] a = Pattern.compile(",").split(s);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < a.length; i++) {
                    sb.append(a[i]);
                    sb.append("\n");
                }
                sb.append("Serial no. : ");
                sb.append(((X509Certificate) receivedCert).getSerialNumber());
                sb.append("\n");
                sb.append("Valid from : ");
                sb.append(((X509Certificate) receivedCert).getNotBefore());
                sb.append("\n");
                sb.append("Valid to   : ");
                sb.append(((X509Certificate) receivedCert).getNotAfter());

                log.log(Level.FINE, "Received certificate from " + hostname + "\n" + sb.toString());
            }
            return true;
        }
    };

    /**
     * Get an instance of the SSLCertificateAccepter. <br>
     * Since this class is a singleton the method will always return the same SSLCertificateAccepter. <br>
     * If the object is not instantiated a new instance will be created.
     * 
     * @return the instance of the SSLCertificateAccepter
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static SSLCertificateAccepter getInstance() throws GeneralSecurityException, IOException {
        if (sslCertificateAccepter == null)
            sslCertificateAccepter = new SSLCertificateAccepter();

        return sslCertificateAccepter;
    }

    /**
     * Sets a new <code>HostnameVerifier</code>. <br>
     * This allows the user to create own verifiers to manage the received certificates in any desired fashion.
     * 
     * @param verifier
     *            The new HostNameVerifier
     * @see javax.net.ssl.HostnameVerifier
     */
    public void setHostNameVerifier(HostnameVerifier verifier) {
        HttpsURLConnection.setDefaultHostnameVerifier(verifier);
    }

    /**
     * Initiates the SSLCertificateAccepter
     * 
     * @throws GeneralSecurityException
     * @throws IOException
     */
    private SSLCertificateAccepter() throws GeneralSecurityException, IOException {
        SSLContext sslContext = SSLContext.getInstance("TLS");

        /**
         * Set the default SocketFactory and HostnameVerifier for javax.net.ssl.HttpsURLConnection
         */
        if (sslContext != null) {
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        }

        // Set the default trust manager
        X509TrustManager[] xtmArray = new X509TrustManager[] { x509TrustManager };
        sslContext.init(null, xtmArray, new java.security.SecureRandom());

        this.setHostNameVerifier(this.hostnameVerifier);
    }
}
