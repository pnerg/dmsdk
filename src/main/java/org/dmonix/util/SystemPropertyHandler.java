package org.dmonix.util;

/**
 * <p>
 * Class for getting System parameters.
 * </p>
 * The class includes convenient methods for extracting System properties.
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
public abstract class SystemPropertyHandler {
    /** */
    public static final String FILE_SEPARATOR = "file.separator";

    /** */
    public static final String JAVA_CLASS_PATH = "java.class.path";

    /** */
    public static final String JAVA_HOME = "java.home";

    /** */
    public static final String JAVA_LIB_PATH = "java.library.path";

    /** */
    public static final String JAVA_RUNTIME_NAME = "java.runtime.name";

    /** */
    public static final String JAVA_RUNTIME_VERSION = "java.runtime.version";

    /** */
    public static final String JAVA_VENDOR_URL = "java.vendor.url";

    /** */
    public static final String JAVA_VERSION = "java.version";

    /** */
    public static final String JAVA_VM_VENDOR = "java.vm.vendor";

    /** */
    public static final String JAVA_VM_VERSION = "java.vm.version";

    /** */
    public static final String LINE_SEPARATOR = "line.separator";

    /** */
    public static final String OS_NAME = "os.name";

    /** */
    public static final String PATH_SEPARATOR = "path.separator";

    /** */
    public static final String USER_COUNTRY = "user.country";

    /** */
    public static final String USER_HOME = "user.home";

    /** */
    public static final String USER_NAME = "user.name";

    /**
     * Prints out all System properties to System.out.
     */
    public static void debug() {
        SystemPropertyHandler.debug(System.out);
    }

    /**
     * Prints out all System properties.
     * 
     * @param ostream
     *            The stream to write to
     */
    public static void debug(java.io.PrintStream ostream) {
        System.getProperties().list(ostream);
    }

    /**
     * Get the System property <code>file separator</code>.
     * 
     * @return The property, null if not found
     */
    public static String getFileSeparator() {
        return SystemPropertyHandler.getProperty(SystemPropertyHandler.FILE_SEPARATOR);
    }

    /**
     * Get the System property <code>Java home</code>.
     * 
     * @return The property, null if not found
     */
    public static String getJavaHome() {
        return SystemPropertyHandler.getProperty(SystemPropertyHandler.JAVA_HOME);
    }

    /**
     * Get the System property <code>Java lib path</code>.
     * 
     * @return The property, null if not found
     */
    public static String getJavaLibPath() {
        return SystemPropertyHandler.getProperty(SystemPropertyHandler.JAVA_LIB_PATH);
    }

    /**
     * Get the System property <code>Java runtime name</code>.
     * 
     * @return The property, null if not found
     */
    public static String getJavaRuntimeName() {
        return SystemPropertyHandler.getProperty(SystemPropertyHandler.JAVA_RUNTIME_NAME);
    }

    /**
     * Get the System property <code>Java runtime version</code>.
     * 
     * @return The property, null if not found
     */
    public static String getJavaRuntimeVersion() {
        return SystemPropertyHandler.getProperty(SystemPropertyHandler.JAVA_RUNTIME_VERSION);
    }

    /**
     * Get the System property <code>Java vendor url</code>.
     * 
     * @return The property, null if not found
     */
    public static String getJavaVendorUrl() {
        return SystemPropertyHandler.getProperty(SystemPropertyHandler.JAVA_VENDOR_URL);
    }

    /**
     * Get the System property <code>Java version</code>.
     * 
     * @return The property, null if not found
     */
    public static String getJavaVersion() {
        return SystemPropertyHandler.getProperty(SystemPropertyHandler.JAVA_VERSION);
    }

    /**
     * Get the System property <code>Java VM vendor</code>.
     * 
     * @return The property, null if not found
     */
    public static String getJavaVMVendor() {
        return SystemPropertyHandler.getProperty(SystemPropertyHandler.JAVA_VM_VENDOR);
    }

    /**
     * Get the System property <code>Java VM version</code>.
     * 
     * @return The property, null if not found
     */
    public static String getJavaVMVersion() {
        return SystemPropertyHandler.getProperty(SystemPropertyHandler.JAVA_VM_VERSION);
    }

    /**
     * Get the System property <code>line separator</code>.
     * 
     * @return The property, null if not found
     */
    public static String getLineSeparator() {
        return SystemPropertyHandler.getProperty(SystemPropertyHandler.LINE_SEPARATOR);
    }

    /**
     * Get the System property <code>OS name</code>.
     * 
     * @return The property, null if not found
     */
    public static String getOSName() {
        return SystemPropertyHandler.getProperty(SystemPropertyHandler.OS_NAME);
    }

    /**
     * Get the System property <code>Path separator</code>.
     * 
     * @return The property, null if not found
     */
    public static String getPathSeparator() {
        return SystemPropertyHandler.getProperty(SystemPropertyHandler.PATH_SEPARATOR);
    }

    /**
     * Get the System property <code>user country</code>.
     * 
     * @return The property, null if not found
     */
    public static String getUserCountry() {
        return SystemPropertyHandler.getProperty(SystemPropertyHandler.USER_COUNTRY);
    }

    /**
     * Get the System property <code>user name</code>.
     * 
     * @return The property, null if not found
     */
    public static String getUserName() {
        return SystemPropertyHandler.getProperty(SystemPropertyHandler.USER_NAME);
    }

    /**
     * Get the System property <code>user home</code>.
     * 
     * @return The property, null if not found
     */
    public static String getUserHome() {
        return SystemPropertyHandler.getProperty(SystemPropertyHandler.USER_HOME);
    }

    /**
     * Get the requested system property.
     * 
     * @param property
     *            The property to get
     * @return The property, null if not found
     */
    public static String getProperty(String property) {
        return System.getProperty(property);
    }

    public static void main(String[] param) {
        SystemPropertyHandler.debug(System.out);
    }
}