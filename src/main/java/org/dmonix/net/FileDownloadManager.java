package org.dmonix.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.dmonix.io.IOUtil;

/**
 * Utility for downloading files from remote servers.
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public abstract class FileDownloadManager {
    private static File cachedFile = null;

    public static void downloadFile(String file, File dir) throws Exception {
        URL codeBase = getCodeBase();
        URL url = new URL(codeBase + file);

        cachedFile = new File(dir, file);
        loadFile(url);
    }

    public static void downloadFile(String path, String file, File dir) throws Exception {
        URL codeBase = getCodeBase();
        URL url = new URL(codeBase + path + file);

        cachedFile = new File(dir, file);
        loadFile(url);
    }

    private static URL getCodeBase() throws Exception {
        /*
         * BasicService bs = (BasicService) ServiceManager.lookup("javax.jnlp.BasicService"); URL url = bs.getCodeBase();
         */
        Class<?> serviceManager = Class.forName("javax.jnlp.ServiceManager");
        Method lookup = serviceManager.getMethod("lookup", new Class[] { String.class });

        Object basicService = lookup.invoke(null, new Object[] { "javax.jnlp.BasicService" });

        Method getCodeBase = basicService.getClass().getMethod("getCodeBase", new Class[0]);
        Object url = getCodeBase.invoke(basicService, new Object[0]);

        return (URL) url;
    }

    /**
     * Load the JAR file. <br>
     * The first thing that is done is that a HTTP HEAD request will be sent to the HTTP server to ask the installation date of the mpcgui.jar file on the
     * server. This date will be compared with the date of the cached file if it exist. If the file on the server is newer, it will be downloded.
     * 
     * @param url
     *            The ULR of the file
     * @throws IOException
     */
    private static void loadFile(URL url) throws IOException {
        Date fileDate = NetUtil.getModifiedDate(url);

        if (cachedFile.exists()) {
            try {
                Date cachedFileDate = new Date(cachedFile.lastModified());

                if (cachedFileDate.before(fileDate)) {
                    downloadFile(url);
                    cachedFile.setLastModified(fileDate.getTime());
                } else {
                }
            } catch (IOException ex) {
            }
        } else {
            downloadFile(url);
            cachedFile.setLastModified(fileDate.getTime());
        }
    }

    /**
     * Download the jar file and place it in the cache.
     * 
     * @param url
     *            the url to download from.
     * @throws IOException
     */
    private static void downloadFile(URL url) throws IOException {
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        urlCon.connect();

        IOUtil.copyStreams(urlCon.getInputStream(), new FileOutputStream(cachedFile), true, true);

        urlCon.disconnect();
    }

}