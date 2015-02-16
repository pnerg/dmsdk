package org.dmonix.util;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Miscellaneus utility methods.
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
public abstract class ClassUtil {

    /**
     * Checks if a class implements the requested interface. <br>
     * If the class does not implements the requested interface and the <code>checkParent</code> is <code>true</code> then all the the parents will be checked
     * until either a class that implements the interface or there are no more parent classes.
     * 
     * @param interfaceClass
     *            The interface class
     * @param c
     *            The class to check
     * @param checkParent
     *            true - checks all parents, false checks only the provided class
     * @return true if the class or parent class implements the interface, false otherwise
     * @see isInterfaceImplemention(String, Class, boolean)
     */
    public static boolean isInterfaceImplemention(Class<?> interfaceClass, Class<?> c, boolean checkParent) {
        return isInterfaceImplemention(interfaceClass.getName(), c, checkParent);
    }

    /**
     * Checks if a class implements the requested interface. <br>
     * If the class does not implements the requested interface and the <code>checkParent</code> is <code>true</code> then all the the parents will be checked
     * until either a class that implements the interface or there are no more parent classes.
     * 
     * @param interfaceClass
     *            The interface class
     * @param c
     *            The class to check
     * @param checkParent
     *            true - checks all parents, false checks only the provided class
     * @return true if the class or parent class implements the interface, false otherwise
     */
    public static boolean isInterfaceImplemention(String interfaceClass, Class<?> c, boolean checkParent) {
        if (c == null)
            return false;

        Class<?>[] interfaces = c.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            if (interfaces[i].getName().equals(interfaceClass)) {
                return true;
            }
        }
        if (!checkParent)
            return false;

        return isInterfaceImplemention(interfaceClass, c.getSuperclass(), true);
    }

    /**
     * Opens the default brower with the supplied URL. <br>
     * The method requires the <code>com.sun.javaws.BrowserSupport</code> class to be included in the classpath. <br>
     * The above class will automatically be included in the classpath if the application invoking this method is running under Java Web Start.
     * 
     * @param url
     *            The URL
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws MalformedURLException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void openBrowser(String url) throws ClassNotFoundException, NoSuchMethodException, MalformedURLException, InvocationTargetException,
            IllegalAccessException {
        Class.forName("com.sun.javaws.BrowserSupport").getDeclaredMethod("showDocument", new Class[] { URL.class }).invoke(null, new Object[] { new URL(url) });
    }

    /**
     * Returns a string with a leading zero(es) if the input number is shorter than the requested length.
     * 
     * @param number
     *            The number to pad
     * @param length
     *            The requested length of the string
     * @return The padded string representation
     */
    public static String padNumber(int number, int length) {
        return padString("" + number, length);
    }

    /**
     * Returns a string with a leading zero(es) if the input string is shorter than the requested length.
     * 
     * @param s
     *            The string to pad
     * @param size
     *            The requested length of the string
     * @return The padded string representation
     */
    public static String padString(String s, int size) {
        for (int i = s.length(); i < size; i++) {
            s = "0" + s;
        }
        return s;
    }

}