package org.dmonix.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The class implements a standard panel with the added feature that the user can specify an image to use as background. <br>
 * All items in the panel will be transparent (non-opaque), i.e. the image can be seen through them.
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg (epknerg)
 * @since 1.1
 */
public class ImagePanel extends JPanel {
    /**
     * Tiles the images. <br>
     * This is the default style.
     */
    public static final int STYLE_TILED = 0;

    /** Scales the image to suit the size of the panel. */
    public static final int STYLE_SCALED = 1;

    /** Uses the actual size of the image. */
    public static final int STYLE_ACTUAL = 2;

    private static final long serialVersionUID = 7110101108494648L;

    private BufferedImage image;
    private int style = STYLE_TILED;
    private float alignmentX = 0.5f;
    private float alignmentY = 0.5f;

    /**
     * Creates an empty panel. <br>
     * The panel will be created with a BorderLyout manager.
     */
    public ImagePanel() {
        setLayout(new BorderLayout());
    }

    /**
     * Creates a panel with the image as backdrop. <br>
     * The default style used is <code>STYLE_TILED</code>
     * 
     * @param image
     *            The image
     */
    public ImagePanel(BufferedImage image) {
        this(image, STYLE_TILED);
    }

    /**
     * Creates a panel with the image as backdrop.
     * 
     * @param image
     *            The image
     * @param style
     *            The style to use
     * @see STYLE_ACTUAL
     * @see STYLE_SCALED
     * @see STYLE_TILED
     */
    public ImagePanel(BufferedImage image, int style) {
        this.setImage(image, style);
        setLayout(new BorderLayout());
    }

    /**
     * Creates a panel with the image as backdrop.
     * 
     * @param url
     *            The URL where to find the image
     * @param style
     *            The style to use
     * @see STYLE_ACTUAL
     * @see STYLE_SCALED
     * @see STYLE_TILED
     * @throws IOException
     */
    public ImagePanel(URL url, int style) throws IOException {
        this.setImage(url, style);
        setLayout(new BorderLayout());
    }

    /**
     * Sets the image to use as backdrop.
     * 
     * @param image
     *            The image
     * @param style
     *            The style to use
     * @see STYLE_ACTUAL
     * @see STYLE_SCALED
     * @see STYLE_TILED
     */
    public void setImage(BufferedImage image, int style) {
        if (style != STYLE_TILED && style != STYLE_SCALED && style != STYLE_ACTUAL)
            throw new IllegalArgumentException("The value " + style + " is not a valid style");

        this.image = image;
        this.style = style;
    }

    /**
     * Sets the image to use as backdrop.
     * 
     * @param url
     *            The URL where to find the image
     * @param style
     *            The style to use
     * @see STYLE_ACTUAL
     * @see STYLE_SCALED
     * @see STYLE_TILED
     * @throws IOException
     */
    public void setImage(URL url, int style) throws IOException {
        this.setImage(ImageIO.read(url), style);
    }

    /**
     * Set the image alignment.
     * 
     * @param alignmentX
     *            X
     */
    public void setImageAlignmentX(float alignmentX) {
        this.alignmentX = alignmentX > 1.0f ? 1.0f : alignmentX < 0.0f ? 0.0f : alignmentX;
    }

    /**
     * Set the image alignment.
     * 
     * @param alignmentY
     *            Y
     */
    public void setImageAlignmentY(float alignmentY) {
        this.alignmentY = alignmentY > 1.0f ? 1.0f : alignmentY < 0.0f ? 0.0f : alignmentY;
    }

    /**
     * Adds a component to the panel.
     * 
     * @param component
     *            The component to add
     */
    public void add(JComponent component) {
        add(component, null);
    }

    /**
     * Adds a component to the panel. <br>
     * The component will be set to semi-transparent <code>setOpaque(false)</code>
     * 
     * @param component
     *            The component to add
     * @param constraints
     *            The constraint to apply to the component
     */
    public void add(JComponent component, Object constraints) {
        component.setOpaque(false);

        if (component instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) component;
            JViewport viewport = scrollPane.getViewport();
            viewport.setOpaque(false);
            Component c = viewport.getView();

            if (c instanceof JComponent) {
                ((JComponent) c).setOpaque(false);
            }
        }

        super.add(component, constraints);
    }

    /**
     * Paint the components in the panel.
     * 
     * @param g
     *            The graphics object
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image == null)
            return;

        switch (style) {
        case STYLE_TILED:
            drawTiled(g);
            break;

        case STYLE_SCALED:
            Dimension d = getSize();
            g.drawImage(image, 0, 0, d.width, d.height, null);
            break;

        case STYLE_ACTUAL:
            drawActual(g);
            break;
        }
    }

    /**
     * Draws a tiled image.
     * 
     * @param g
     *            graphics
     */
    private void drawTiled(Graphics g) {
        Dimension d = getSize();
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        for (int x = 0; x < d.width; x += width) {
            for (int y = 0; y < d.height; y += height) {
                g.drawImage(image, x, y, null, null);
            }
        }
    }

    /**
     * Draws the image in actual size.
     * 
     * @param g
     *            graphics
     */
    private void drawActual(Graphics g) {
        Dimension d = getSize();
        float x = (d.width - image.getWidth()) * alignmentX;
        float y = (d.height - image.getHeight()) * alignmentY;
        g.drawImage(image, (int) x, (int) y, this);
    }
}