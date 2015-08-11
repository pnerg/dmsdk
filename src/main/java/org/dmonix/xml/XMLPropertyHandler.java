package org.dmonix.xml;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
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
public abstract class XMLPropertyHandler {
    private static final Logger log = Logger.getLogger(XMLPropertyHandler.class.getName());

    private static Document properties = null;

    /**
     * Get the value of a Boolean property.
     * 
     * @param property
     *            The name of the property to get
     * @param defaultValue
     *            The value to return if the method fails
     * @return true if the property is true, default value otherwise
     */
    public static boolean getBooleanProperty(String property, boolean defaultValue) {
        String value = XMLPropertyHandler.getProperty(property, null);

        if (value == null || value.length() < 4)
            return defaultValue;

        return Boolean.valueOf(value).booleanValue();
    }

    /**
     * Get the value of a Boolean property.
     * 
     * @param property
     *            The name of the property to get
     * @return true if the property is true, false otherwise
     */
    public static boolean getBooleanProperty(String property) {
        return XMLPropertyHandler.getBooleanProperty(property, false);
    }

    /**
     * Get the value of an property.
     * 
     * @param property
     *            The name of the property to get
     * @return The value of the property if found, null otherwise
     */
    public static String getProperty(String property) {
        return XMLPropertyHandler.getProperty(property, null);
    }

    /**
     * Get the value of an property.
     * 
     * @param property
     *            The name of the property to get
     * @param defaultValue
     *            The value to return if the method fails
     * @return The value of the property if found, default value otherwise
     */
    public static String getProperty(String property, String defaultValue) {
        if (properties == null)
            return null;

        return XMLUtil.getElementValue(properties.getElementsByTagName(property), defaultValue);
    }

    public static boolean parsePropertyFile(File file) {
        try {
            properties = XMLUtil.getDocument(file);
        } catch (Exception ex) {
            properties = XMLUtil.newDocument();
            Element p = properties.createElement("properties");
            properties.appendChild(p);
            log.log(Level.CONFIG, "Could not read properties : " + ex.getMessage());
            return false;
        }
        return true;
    }

    public static void savePropertyFile(File file) {
        try {
            XMLUtil.saveToFile(properties, file);
        } catch (Exception ex) {
            log.log(Level.SEVERE, "Could not save properties", ex);
        }
    }

    /**
     * Set a property value
     * 
     * @param name
     *            The name of the property
     * @param value
     *            The value of the property
     * @param append
     */
    public static void setProperty(String name, boolean value, boolean append) {
        XMLPropertyHandler.setProperty(name, "" + value, append);
    }

    public static void setProperty(String name, int value, boolean append) {
        XMLPropertyHandler.setProperty(name, "" + value, append);
    }

    public static void setProperty(String name, long value, boolean append) {
        XMLPropertyHandler.setProperty(name, "" + value, append);
    }

    public static void setProperty(String name, String value, boolean append) {
        if (append) {
            XMLUtil.appendChildElement(properties, name, value);
            return;
        }

        NodeList list = properties.getElementsByTagName(name);
        if (list.getLength() < 1) {
            XMLUtil.appendChildElement(properties, name, value);
            return;
        }

        Element e = (Element) list.item(0);
        if (!e.hasChildNodes()) {
            e.appendChild(properties.createTextNode(value));
            return;
        }

        e.getFirstChild().setNodeValue(value);
    }

    public static void setTransformerProperty(String name, String value) {
        XMLUtil.setTransformerProperty(name, value);
    }
}