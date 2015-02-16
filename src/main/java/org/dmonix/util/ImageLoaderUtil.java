package org.dmonix.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Utility class for simple loading of the images defined in the dmonix package.
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
public abstract class ImageLoaderUtil {
    /** The logger instance for this class */
    private static final Logger log = Logger.getLogger(ImageLoaderUtil.class.getName());

    /** The base path to the image repository. */
    public static final String PATH_IMG_REPOSITORY = "/org/dmonix/img/";

    public static final String PATH_TOOLBARBUTTONGRAPHICS_DEVELOPMENT = PATH_IMG_REPOSITORY + "toolbarButtonGraphics/development/";

    public static final String PATH_TOOLBARBUTTONGRAPHICS_GENERAL = PATH_IMG_REPOSITORY + "toolbarButtonGraphics/general/";

    public static final String PATH_TOOLBARBUTTONGRAPHICS_MEDIA = PATH_IMG_REPOSITORY + "toolbarButtonGraphics/media/";

    public static final String PATH_TOOLBARBUTTONGRAPHICS_NAVIGATION = PATH_IMG_REPOSITORY + "toolbarButtonGraphics/navigation/";

    public static final String PATH_TOOLBARBUTTONGRAPHICS_TABLE = PATH_IMG_REPOSITORY + "toolbarButtonGraphics/table/";

    public static final String PATH_TOOLBARBUTTONGRAPHICS_TEXT = PATH_IMG_REPOSITORY + "toolbarButtonGraphics/text/";

    /**
     * Get the color DMoniX logo. <br>
     * /img/dmonix_logo.gif
     * 
     * @return The logo
     */
    public static ImageIcon getDmonixLogoColorIcon() {
        return getImageIcon("/img/", "dmonix_logo.gif");
    }

    /**
     * Get the color DMoniX logo. <br>
     * /img/dmonix_logo.gif
     * 
     * @return The logo
     */
    public static Image getDmonixLogoColorImage() {
        return getImage("/img/", "dmonix_logo.gif");
    }

    /**
     * Get the black and white DMoniX logo. <br>
     * /img/dmonix_logo.gif
     * 
     * @return The logo
     */
    public static ImageIcon getDmonixLogoBWIcon() {
        return getImageIcon("/img/", "dmonix_logo_bw.jpg");
    }

    /**
     * Get the black and white DMoniX logo. <br>
     * /img/dmonix_logo.gif
     * 
     * @return The logo
     */
    public static Image getDmonixLogoBWImage() {
        return getImage("/img/", "dmonix_logo_bw.jpg");
    }

    /**
     * Get the image at the specified path.
     * 
     * @param path
     *            The path to the image
     * @param imgName
     *            The name of the image
     * @return The image
     * @since 1.1
     */
    public static Image getImage(String name) {
        try {
            return ImageIO.read(ImageLoaderUtil.class.getResource(name));
        } catch (Exception ex) {
            log.log(Level.WARNING, "Could not load image : " + name);
            throw new MissingResourceException("Could not load image : " + name, name, null);
        }
    }

    /**
     * Get the image at the specified path.
     * 
     * @param path
     *            The path to the image
     * @param imgName
     *            The name of the image
     * @return The image
     */
    public static Image getImage(String path, String imgName) {
        if (!path.endsWith("/"))
            path = path + "/";

        return getImage(path + imgName);
    }

    /**
     * Get the image icon at the specified path.
     * 
     * @param path
     *            The path to the image
     * @param imgName
     *            The name of the image
     * @return The image icon
     */
    public static ImageIcon getImageIcon(String path, String imgName) {
        try {
            return new ImageIcon(ImageLoaderUtil.class.getResource(path + imgName));
        } catch (Exception ex) {
            log.log(Level.WARNING, "Could not load image : " + path + imgName);
            throw new MissingResourceException("Could not load image : " + path + imgName, path + imgName, null);
        }
    }

    /**
     * Get the scaled image icon at the specified path.
     * 
     * @param path
     *            The path to the image
     * @param imgName
     *            The name of the image
     * @param width
     *            The width of the image
     * @param height
     *            The height of the image
     * @return The image icon
     * @since 1.1
     */
    public static ImageIcon getImageIcon(String name, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(ImageLoaderUtil.class.getResource(name));
            Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        } catch (NullPointerException ex) {
            log.log(Level.WARNING, "Could not load image : " + name);
            throw new MissingResourceException("Could not load image : " + name, name, null);
        }
    }

    /**
     * Gets a BufferedImage from a file.
     * 
     * @param file
     *            The image file
     * @return The BufferedImage
     * @throws IOException
     */
    public static BufferedImage getBufferedImage(File file) throws IOException {
        return ImageIO.read(file);
    }

    /**
     * Gets a resized BufferedImage from a file.
     * 
     * @param file
     *            The image file
     * @param sx
     *            Scale width
     * @param sy
     *            Scale height
     * @return The resized BufferedImage
     * @throws IOException
     */
    public static BufferedImage getBufferedImage(File file, double sx, double sy) throws IOException {
        return resize(ImageIO.read(file), sx, sy);
    }

    /**
     * Gets a resized BufferedImage from a file.
     * 
     * @param file
     *            The image file
     * @param width
     *            The resulting width
     * @param height
     *            The resulting height
     * @return The resized BufferedImage
     * @throws IOException
     */
    public static BufferedImage getBufferedImage(File file, int width, int height) throws IOException {
        return resize(ImageIO.read(file), width, height);
    }

    /**
     * Resizes a BufferedImage to the requested size.
     * 
     * @param source
     *            The source image
     * @param sx
     *            Scale width
     * @param sy
     *            Scale height
     * @return The resulting image
     */
    public static BufferedImage resize(BufferedImage source, double sx, double sy) {
        int type = source.getType();
        BufferedImage target = null;
        if (type == BufferedImage.TYPE_CUSTOM) { // handmade
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster((int) (source.getWidth() * sx), (int) (source.getHeight() * sy));
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage((int) (source.getWidth() * sx), (int) (source.getHeight() * sy), type);
        }

        Graphics2D g2 = target.createGraphics();
        // smoother than exlax:
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        g2.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g2.dispose();
        return target;
    }

    /**
     * Resizes a BufferedImage to the requested size.
     * 
     * @param source
     *            The source image
     * @param width
     *            The resulting width
     * @param height
     *            The resulting height
     * @return The resulting image
     */
    public static BufferedImage resize(BufferedImage source, int width, int height) {
        return resize(source, (double) width / source.getWidth(), (double) height / source.getHeight());
    }
}
