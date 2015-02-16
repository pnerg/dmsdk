package org.dmonix.util.image.imageobjects;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.awt.*;

//import com.ericsson.util.ImageObject;

/**
 * The base class for all types of image objects.
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
public abstract class BaseImageObject implements ImageObject {
    /** The logger instance for this class */
    private static final Logger log = Logger.getLogger(BaseImageObject.class.getName());

    /** The distance from the X-axis line to the left of the image. */
    protected int X_AXIS_HORIZONTAL_START_OFFSET = 10;

    /** The distance from the X-axis line to the right of the image. */
    protected int X_AXIS_HORIZONTAL_END_OFFSET = 10;

    /** The distance from the X-axis line to the bottom of the image. */
    protected int X_AXIS_VERTICAL_OFFSET = 30;

    /** The absolute length of the X-axis. */
    protected int X_AXIS_ABSOLUTE_LENGTH = -1;

    /**
     * The scale factor for the X-axis. It depends on the size of the image and the largest value for a dot/column.
     */
    protected double X_AXIS_SCALE_FACTOR = -1;

    /** The step size for the X-axis . */
    protected final int X_AXIS_SCALE_STEP = 10;

    /** The distance from the Y-axis line to the left of the image. */
    protected int Y_AXIS_HORIZONTAL_OFFSET = 30;

    /** The distance from the Y-axis line to the top of the image. */
    protected final int Y_AXIS_VERTICAL_START_OFFSET = 10;

    /** The distance from the Y-axis line to the bottom of the image. */
    protected final int Y_AXIS_VERTICAL_END_OFFSET = 10;

    /** The step size for the Y-axis . */
    protected final int Y_AXIS_SCALE_STEP = 10;

    /** The absolute length of the Y-axis . */
    protected int Y_AXIS_ABSOLUTE_LENGTH = -1;

    /** The size/length of the notch used on either axis. */
    protected final int NOTCH_LENGTH = 4;

    /** The background color. */
    protected final static Color bg = Color.white;

    /** The foreground color. */
    protected final static Color fg = Color.black;

    protected final static BasicStroke stroke = new BasicStroke(2.0f);
    protected final static BasicStroke STROKE_POINT = new BasicStroke(4.0f);
    protected final static BasicStroke STROKE_DOTTED_LINE = new BasicStroke(4.0f);
    protected final static BasicStroke STROKE_LINE = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    protected final static BasicStroke STROKE_LINE_THICKER = new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    protected final static BasicStroke wideStroke = new BasicStroke(8.0f);

    private final static float dash1[] = { 10.0f };
    protected final static BasicStroke STROKE_DASHED_LINE = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);

    /** The image area. */
    private Rectangle rectangle;

    /** The width of the image. */
    protected int WIDTH = -1;

    /** The height of the image. */
    protected int HEIGHT = -1;

    /** The distance between the notches on the x-axis. */
    protected double X_AXIS_NOTCH_DISTANCE = -1;

    /** The distance between the notches on the y-axis. */
    protected double Y_AXIS_NOTCH_DISTANCE = -1;

    /** The number of notches on the x-axis. */
    protected int X_AXIS_NOTCH_COUNT = -1;

    /** The number of notches on the y-axis. */
    protected int Y_AXIS_NOTCH_COUNT = -1;

    /**
     * The scale factor for the Y-axis. It depends on the size of the image and the largest value for a dot/column.
     */
    protected double Y_AXIS_SCALE_FACTOR = -1;

    /**
     * Constructor [obviously].
     * 
     * @param width
     *            The width of the image
     * @param height
     *            The height of the image
     */
    public BaseImageObject(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.rectangle = new Rectangle((int) WIDTH, (int) HEIGHT);
    }

    /**
     * Get the rectangle for this image.
     * 
     * @return The rectangle.
     */
    public Rectangle getBounds() {
        return this.rectangle;
    }

    /**
     * Paint the image.
     * 
     * @param g2
     *            The graphics object.
     */
    public abstract void paint(Graphics2D g2);

    /**
     * Calculate and set the distance between the X-axis notches.
     * 
     * @param objects
     *            The number of objects to fit on the axis.
     */
    protected void calculateXAxisNotchDistanceBasedOnObjects(int objects) {
        this.X_AXIS_ABSOLUTE_LENGTH = WIDTH - Y_AXIS_HORIZONTAL_OFFSET - X_AXIS_HORIZONTAL_END_OFFSET - 5;

        this.X_AXIS_NOTCH_COUNT = objects - 1;
        this.X_AXIS_NOTCH_DISTANCE = (double) ((double) X_AXIS_ABSOLUTE_LENGTH) / (double) X_AXIS_NOTCH_COUNT;

        log.log(Level.FINE, "objects = " + objects);
        log.log(Level.FINE, "X_AXIS_NOTCH_DISTANCE = " + X_AXIS_NOTCH_DISTANCE);
        log.log(Level.FINE, "X_AXIS_SCALE_FACTOR = " + X_AXIS_SCALE_FACTOR);
    }

    protected void calculateXAxisNotchDistanceBasedOnValue(int maxHeight) {
        this.X_AXIS_ABSOLUTE_LENGTH = WIDTH - Y_AXIS_HORIZONTAL_OFFSET - X_AXIS_HORIZONTAL_END_OFFSET - 5;

        // round off to nearest X_AXIS_SCALE_STEP:th value
        int rounded = maxHeight + (X_AXIS_SCALE_STEP - (maxHeight % X_AXIS_SCALE_STEP));
        this.X_AXIS_NOTCH_COUNT = (rounded / X_AXIS_SCALE_STEP);

        this.X_AXIS_NOTCH_DISTANCE = (double) ((double) X_AXIS_ABSOLUTE_LENGTH) / (double) X_AXIS_NOTCH_COUNT;
        this.X_AXIS_SCALE_FACTOR = (double) ((double) (X_AXIS_ABSOLUTE_LENGTH) / (double) rounded);

        log.log(Level.FINE, "maxHeight = " + maxHeight + "+" + (X_AXIS_SCALE_STEP - (maxHeight % X_AXIS_SCALE_STEP)));
        log.log(Level.FINE, "X_AXIS_NOTCH_DISTANCE = " + X_AXIS_NOTCH_DISTANCE);
        log.log(Level.FINE, "X_AXIS_SCALE_FACTOR = " + X_AXIS_SCALE_FACTOR);
    }

    protected void calculateYAxisNotchDistanceBasedOnObjects(int objects) {
        this.Y_AXIS_ABSOLUTE_LENGTH = HEIGHT - X_AXIS_VERTICAL_OFFSET - Y_AXIS_VERTICAL_START_OFFSET - 5;

        this.Y_AXIS_NOTCH_COUNT = objects;
        this.Y_AXIS_NOTCH_DISTANCE = (double) ((double) Y_AXIS_ABSOLUTE_LENGTH) / (double) Y_AXIS_NOTCH_COUNT;

        log.log(Level.FINE, "objects = " + objects);
        log.log(Level.FINE, "Y_AXIS_NOTCH_DISTANCE = " + Y_AXIS_NOTCH_DISTANCE);
        log.log(Level.FINE, "Y_AXIS_SCALE_FACTOR = " + Y_AXIS_SCALE_FACTOR);
    }

    /**
     * Calculate and set the distance between the Y-axis notches.
     * 
     * @param maxHeight
     *            The maximum height of the axis.
     */
    protected void calculateYAxisNotchDistanceBasedOnValue(int maxHeight) {
        this.Y_AXIS_ABSOLUTE_LENGTH = HEIGHT - X_AXIS_VERTICAL_OFFSET - Y_AXIS_VERTICAL_START_OFFSET - 5;

        // round off to nearest Y_AXIS_SCALE_STEP:th value
        int rounded = maxHeight + (Y_AXIS_SCALE_STEP - (maxHeight % Y_AXIS_SCALE_STEP));
        this.Y_AXIS_NOTCH_COUNT = (rounded / Y_AXIS_SCALE_STEP);

        this.Y_AXIS_NOTCH_DISTANCE = (double) ((double) Y_AXIS_ABSOLUTE_LENGTH) / (double) Y_AXIS_NOTCH_COUNT;
        this.Y_AXIS_SCALE_FACTOR = (double) ((double) (Y_AXIS_ABSOLUTE_LENGTH) / (double) rounded);

        log.log(Level.FINE, "maxHeight = " + maxHeight);
        log.log(Level.FINE, "Y_AXIS_NOTCH_DISTANCE = " + Y_AXIS_NOTCH_DISTANCE);
        log.log(Level.FINE, "Y_AXIS_SCALE_FACTOR = " + Y_AXIS_SCALE_FACTOR);
    }

    /**
     * Draw the X-axis line.
     * 
     * @param g2
     *            The graphics object
     * @param notches
     *            Should nothces been drawn
     * @param numbering
     *            Should numbering be added
     * @param scale
     *            Should a scale be added
     */
    protected void drawXAxis(Graphics2D g2, boolean notches, boolean numbering, boolean scale) {
        g2.setStroke(STROKE_LINE);
        g2.setColor(Color.black);

        // Create the axis line
        g2.drawLine(X_AXIS_HORIZONTAL_START_OFFSET, HEIGHT - X_AXIS_VERTICAL_OFFSET, WIDTH - X_AXIS_HORIZONTAL_END_OFFSET, HEIGHT - X_AXIS_VERTICAL_OFFSET);

        if (!notches && !numbering)
            return;

        // Create notches
        int scaleCounter = 1;
        double x = (double) Y_AXIS_HORIZONTAL_OFFSET;
        for (int i = 0; i < X_AXIS_NOTCH_COUNT; i++) {
            x += X_AXIS_NOTCH_DISTANCE;
            if (notches)
                g2.drawLine((int) x, HEIGHT - (X_AXIS_VERTICAL_OFFSET - NOTCH_LENGTH / 2), (int) x, HEIGHT - (X_AXIS_VERTICAL_OFFSET + NOTCH_LENGTH / 2));

            if (numbering)
                g2.drawString("" + (scaleCounter + 1), (int) x - 5, HEIGHT - X_AXIS_VERTICAL_OFFSET + 20);

            if (scale)
                g2.drawString("" + scaleCounter * X_AXIS_SCALE_STEP, (int) x - 5, HEIGHT - X_AXIS_VERTICAL_OFFSET + 20);

            scaleCounter++;
        }
    }

    /**
     * Draw the Y-axis line.
     * 
     * @param g2
     *            The graphics object
     * @param notches
     *            Should notches been drawn
     * @param numbering
     *            Should numbering be added
     * @param scale
     *            Should a scale be added
     */
    protected void drawYAxis(Graphics2D g2, boolean notches, boolean numbering, boolean scale) {
        g2.setStroke(STROKE_LINE);
        g2.setColor(Color.black);

        // Create the axis line
        g2.drawLine(Y_AXIS_HORIZONTAL_OFFSET, Y_AXIS_VERTICAL_START_OFFSET, Y_AXIS_HORIZONTAL_OFFSET, HEIGHT - Y_AXIS_VERTICAL_END_OFFSET);

        int scaleCounter = 1;

        int fontHeight = g2.getFontMetrics().getHeight();

        // if the font is larger than the available distance
        // between the notches the the text cannot be written to each notch
        int distanceBetweenAxisText = (int) Math.ceil(fontHeight / Y_AXIS_NOTCH_DISTANCE);

        // Create the notches and the scale
        double y = HEIGHT - X_AXIS_VERTICAL_OFFSET;
        for (int i = 0; i < Y_AXIS_NOTCH_COUNT; i++) {
            y -= Y_AXIS_NOTCH_DISTANCE;

            // the notch
            if (notches && (i - 1) % distanceBetweenAxisText == 0)
                g2.drawLine(Y_AXIS_HORIZONTAL_OFFSET - NOTCH_LENGTH / 2, (int) y, Y_AXIS_HORIZONTAL_OFFSET + NOTCH_LENGTH / 2, (int) y);

            // the numbering
            if (numbering && scaleCounter > 1 && (i - 1) % distanceBetweenAxisText == 0)
                g2.drawString("" + scaleCounter, Y_AXIS_HORIZONTAL_OFFSET - 10, (int) y + 5);

            // the scale
            if (scale && (i - 1) % distanceBetweenAxisText == 0)
                g2.drawString("" + scaleCounter * Y_AXIS_SCALE_STEP, Y_AXIS_HORIZONTAL_OFFSET - 25, (int) y + 5);

            scaleCounter++;
        }
    }
}
