package org.dmonix.util;

import java.util.*;
import java.io.*;

/**
 * A class to handle property files. Copyright: Copyright (c) 2001 Company:
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public class PropertyHandler extends Properties {
    private static final long serialVersionUID = 7526472295622776147L;

    private String fileName;
    private Properties properties = new Properties();

    /**
     * Creates an empty property list with no default values.
     */
    public PropertyHandler() {
    }

    /**
     * Creates an empty property list with the specified defaults.
     * 
     * @param properties
     *            Default properties to use
     */
    public PropertyHandler(Properties properties) {
        this.properties = properties;
    }

    /**
     * Creates an empty property list with the specified defaults from the file
     * 
     * @param fileName
     *            The file to read the properties from
     */
    public PropertyHandler(String fileName) throws IOException {
        this.fileName = fileName;
        FileInputStream fin = new FileInputStream(fileName);
        this.properties.load(fin);
        fin.close();
    }

    /**
     * Searches for the property with the specified key in this property list.
     * 
     * @param key
     *            The key to search for
     * @return String
     */
    // public String getProperty(String key)
    // {
    // return properties.getProperty(key);
    // }

    /**
     * Searches for the property with the specified key in this property list.
     * 
     * @param key
     *            The key to search for
     * @param defaultValue
     *            The value to return in case the primary key is not found
     * @return String
     */
    // public String getProperty(String key, String defaultValue)
    // {
    // return properties.getProperty(key, defaultValue);
    // }

    /**
     * Adds/overwrites a property
     * 
     * @param key
     *            The key
     * @param value
     *            The value
     * @return Object, if the key exists the old object is returned
     */
    // public Object setProperty(String key, String value)
    // {
    // return properties.setProperty(key,value);
    // }

    /**
     * Reads a property list (key and element pairs) from the given file.
     * 
     * @param fileName
     *            The property file
     */
    public void load(String fileName) throws IOException {
        this.fileName = fileName;
        FileInputStream fin = new FileInputStream(fileName);
        properties.load(fin);
        fin.close();
    }

    /**
     * Store the properties to the define file
     * 
     * @param header
     *            A header line can be added to the file
     */
    public void store(String header) throws IOException {
        FileOutputStream fout = new FileOutputStream(this.fileName);
        properties.store(fout, header);
        fout.flush();
        fout.close();
    }

    /**
     * Store the properties to the specified file
     * 
     * @param header
     *            A header line can be added to the file
     */
    public void store(String fileName, String header) throws IOException {
        FileOutputStream fout = new FileOutputStream(fileName);
        properties.store(fout, header);
        fout.flush();
        fout.close();
    }
}