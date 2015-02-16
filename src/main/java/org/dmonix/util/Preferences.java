package org.dmonix.util;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * The class reads/writes the <code>Preferences.xml</code> file
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
public abstract class Preferences {
    public static final File HOME_PATH = new File(System.getProperty("user.home") + File.separator + "MOLRServer2" + File.separator);
    public static final File LOG_PATH = new File(HOME_PATH, "logs" + File.separator);
    public static final File CACHE_PATH = new File(HOME_PATH, "cache" + File.separator);

    public static final String PREFERENCE_PORT = "port";
    public static final String PREFERENCE_THREADS = "threads";
    public static final String PREFERENCE_LOGGING = "logging";

    private static final File PREFERENCES_FILE = new File(HOME_PATH + File.separator + "preferences.xml");

    private static Document preferences = null;

    /** The build number for this program. */
    private static String buildNumber;

    static {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            if (PREFERENCES_FILE.exists()) {
                preferences = db.parse(PREFERENCES_FILE);
            } else {
                preferences = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

                Element p = preferences.createElement("preferences");
                preferences.appendChild(p);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            Properties p = new Properties();
            p.load(Preferences.class.getClassLoader().getResourceAsStream("build.number"));
            buildNumber = p.getProperty("build.number", "x");
        } catch (Exception ex) {
            buildNumber = "x";
        }

    }

    public static void setProperty(String property, String value) {
        NodeList list = preferences.getElementsByTagName(property);

        Element e = preferences.createElement(property);
        e.appendChild(preferences.createTextNode(value));

        if (list.getLength() > 0) {
            preferences.getDocumentElement().replaceChild(e, list.item(0));
        } else
            preferences.getDocumentElement().appendChild(e);

    }

    public static void savePreferences() {
        if (PREFERENCES_FILE.exists())
            PREFERENCES_FILE.delete();

        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty("encoding", "ISO-8859-1");
            t.setOutputProperty("standalone", "no");
            t.setOutputProperty("indent", "yes");
            t.transform(new DOMSource(preferences), new StreamResult(PREFERENCES_FILE));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Get the build number for the program.
     * 
     * @return
     */
    public static String getBuildNumber() {
        return buildNumber;
    }

    public static String getProperty(String property, String defaultValue) {
        NodeList list = preferences.getElementsByTagName(property);
        if (list.getLength() > 0)
            return ((Element) list.item(0)).getFirstChild().getNodeValue();

        return defaultValue;
    }

}