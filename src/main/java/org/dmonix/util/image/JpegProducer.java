package org.dmonix.util.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.dmonix.util.image.imageobjects.ImageObject;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * Utility class for creating JPEG encoded images.
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
public class JpegProducer {
    private Color background = Color.white;

    private BufferedImage bi = null;

    /**
     * Set the background color. <br>
     * The default color is white.
     * 
     * @param bgColor
     *            The background color
     */
    public void setBackground(Color bgColor) {
        background = bgColor;
    }

    /**
     * Encodes the buffered image to the provided output stream. <br>
     * Note that the method does neither flush nor close the provided stream.
     * 
     * @param bi
     *            The buffered image
     * @param stream
     *            The stream to write the image to
     * @throws IOException
     */
    @SuppressWarnings("restriction")
    public static void encodeImage(BufferedImage bi, OutputStream stream) throws IOException {
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(stream);
        encoder.encode(bi);
    }

    /**
     * Encodes the buffered image to the provided file. <br>
     * Any existing file will be overwritten.
     * 
     * @param bi
     *            The buffered image
     * @param file
     *            The file to write the image to
     * @throws IOException
     */
    public static void encodeImage(BufferedImage bi, File file) throws IOException {
        if (file.exists())
            file.delete();

        file.createNewFile();
        FileOutputStream ostream = new FileOutputStream(file);
        encodeImage(bi, ostream);
        ostream.flush();
        ostream.close();
    }

    /**
     * Encodes the image object to the provided output stream.<br>
     * Note that the method does neither flush nor close the given stream.
     * 
     * @param imageObject
     *            The object used to render an image
     * @param stream
     *            The output stream
     * @throws IOException
     */
    public void encodeImage(ImageObject imageObject, OutputStream stream) throws IOException {
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(stream);
        encoder.encode(createImage(imageObject));
    }

    /**
     * Returns the current buffered image.
     * 
     * @return The buffered image
     */
    public BufferedImage getBufferedImage() {
        return this.bi;
    }

    /**
     * Creates a buffered image out of an image object.
     * 
     * @param imageObject
     *            The image object
     * @return The image
     * @throws IOException
     */
    public BufferedImage createImage(ImageObject imageObject) throws IOException {
        this.bi = new BufferedImage(imageObject.getBounds().width, imageObject.getBounds().height, BufferedImage.TYPE_BYTE_INDEXED);
        Graphics2D graphics = bi.createGraphics();

        // Set backround
        graphics.setBackground(background);
        graphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());
        imageObject.paint(graphics);
        return bi;
    }
}
