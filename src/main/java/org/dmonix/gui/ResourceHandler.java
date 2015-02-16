package org.dmonix.gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Properties;

import javax.swing.ImageIcon;

/**
 * <p>
 * Title: DMUTIL
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 * @deprecated
 */
public abstract class ResourceHandler {
    protected static final ClassLoader classLoader = ResourceHandler.class.getClassLoader();

    /**
     * Get the buildnumber.
     * 
     * @deprecated
     * @return
     */
    public static String getBuildNumber() {
        try {
            Properties p = new Properties();
            p.load(ResourceHandler.class.getClassLoader().getResourceAsStream("build.number"));
            return p.getProperty("build.number", "x");
        } catch (Exception ex) {
            return "x";
        }
    }

    /**
     * Get the dmonix logo.
     * 
     * @return the logo
     * @deprecated Use ImageLoaderUtil.getDmonixLogoColorIcon();
     */
    public static ImageIcon getDmonixLogo() {
        return getImageIcon("/org/dmonix/gui/img/dmonix_logo.gif");
    }

    /**
     * 
     * @param name
     * @param size
     * @return
     * @throws FileNotFoundException
     * @deprecated Use ImageLoaderUtil.getImageIcon();
     */
    public static ImageIcon getIcon(String name, int size) throws FileNotFoundException {
        Image image = Toolkit.getDefaultToolkit().getImage(ResourceHandler.getResource(name));
        return new ImageIcon(image.getScaledInstance(-1, size, Image.SCALE_SMOOTH));
    }

    /**
     * 
     * @param name
     * @return
     * @throws FileNotFoundException
     * @deprecated Use ImageLoaderUtil.getImageIcon();
     */
    public static ImageIcon getIcon(String name) throws FileNotFoundException {
        return new ImageIcon(ResourceHandler.getResource(name));
    }

    /**
     * Loads a URL to an image.
     * 
     * @param url
     * @return The image
     * @deprecated Use ImageLoaderUtil.getImage();
     */
    public static Image getImage(String url) {
        return new ImageIcon(ResourceHandler.class.getResource(url)).getImage();
    }

    /**
     * Loads a URL to an ImageIcon
     * 
     * @param url
     *            The url
     * @return the ImageIcon
     * @deprecated Use ImageLoaderUtil.getImageIcon();
     */
    public static ImageIcon getImageIcon(String url) {
        return new ImageIcon(ResourceHandler.class.getResource(url));
    }

    private static URL getResource(String name) throws FileNotFoundException {
        URL url = classLoader.getResource(name);
        if (url == null)
            throw new FileNotFoundException("Cannot find the file " + name);

        return url;
    }
}