package org.dmonix.util.image.imageobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class will paint one or more graphs on a graphics object. <br>
 * Differenct colors are allowed for each graph.
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
public class GraphImageObject extends BaseImageObject {
    /** Uses a cubic style algorithm to connect the dots of the graph. */
    public static final int GRAPH_TYPE_CUBIC = 1;

    /** Uses a quadratic style algorithm to connect the dots of the graph. */
    public static final int GRAPH_TYPE_QUAD = 2;

    /** The logger instance for this class */
    private static final Logger log = Logger.getLogger(GraphImageObject.class.getName());

    private List<List<Integer>> graphs = new Vector<List<Integer>>();
    private List<GraphInfoObject> graphInfoData = new Vector<GraphInfoObject>();
    private String[] headers;
    private int maxHeight = -1;
    private int itemsInWidth = -1;

    private Graphics2D g2 = null;

    /**
     * Constructor [obviously].
     * 
     * @param width
     *            The width of the image
     * @param height
     *            The height of the image
     */
    public GraphImageObject(int width, int height) {
        super(width, height);
    }

    /**
     * Add a new graph to the image. The user may choose the type of algorithm used to connect the dots of the graph.
     * 
     * @param graphType
     *            The type of the graph
     * @param color
     *            The color of the graph
     * @return A reference to the graph
     * @see GRAPH_TYPE_CUBIC
     * @see GRAPH_TYPE_QUAD
     */
    public int addGraph(int graphType, Color color) {
        graphs.add(new Vector<Integer>());
        graphInfoData.add(new GraphInfoObject(graphType, color));
        return graphs.size() - 1;
    }

    /**
     * Add data to a specific graph.
     * 
     * @param graph
     *            The graph to add the data to
     * @param value
     *            The value
     */
    public void addData(int graph, int value) {
        List<Integer> list = graphs.get(graph);
        list.add(value);
        if (value > this.maxHeight)
            this.maxHeight = value;
    }

    /**
     * Sets the text printed on the x-axis.
     * 
     * @param headers
     *            The text
     */
    public void setXAxisHeaders(String[] headers) {
        System.arraycopy(headers, 0, this.headers, 0, headers.length);
    }

    public void paint(Graphics2D g2) {
        this.g2 = g2;
        g2.setColor(Color.black);

        for (List<Integer> list : graphs) {
            if (list.size() > itemsInWidth)
                itemsInWidth = list.size();
        }

        super.calculateXAxisNotchDistanceBasedOnObjects(itemsInWidth);
        super.calculateYAxisNotchDistanceBasedOnValue(maxHeight);

        super.drawXAxis(g2, true, false, false);
        super.drawYAxis(g2, true, false, true);
        for (int i = 0; i < graphs.size(); i++) {
            this.drawData(graphInfoData.get(i), graphs.get(i));
        }
        this.drawXAxisHeaders();
    }

    /**
     * Draw the dots for the graph.
     */
    private void drawData(GraphInfoObject gio, List<Integer> data) {
        int currentValue = -1;
        int nextValue = -1;

        for (int i = 0; i < data.size(); i++) {
            currentValue = data.get(i);

            if (log.isLoggable(Level.FINE))
                log.log(Level.FINE, "" + currentValue);

            if (i < data.size() - 1) {
                nextValue = ((Integer) data.get(i + 1)).intValue();
            } else {
                nextValue = -1;
            }

            this.drawCurve(i, currentValue, nextValue, gio.graphColor);
        }
    }

    private void drawXAxisHeaders() {
        if (headers == null || headers.length == 0)
            return;

        int counter = headers.length;
        if (itemsInWidth < counter)
            counter = itemsInWidth;

        int axisTextCorrection = -1;

        int axiscounter = 0;
        int colsPerAxisDate = Math.round(itemsInWidth / 10) + 1;
        for (int i = 0; i < counter; i++) {
            // calculate the distance for which to move the text to the left
            // use which ever value is the lowest:
            // X_AXIS_NOTCH_DISTANCE or the calculated pixel size of the text
            axisTextCorrection = g2.getFontMetrics().stringWidth(headers[i]) / 2;
            if (X_AXIS_NOTCH_DISTANCE < axisTextCorrection)
                axisTextCorrection = (int) X_AXIS_NOTCH_DISTANCE;

            g2.setColor(Color.black);
            if (i == 1)
                g2.drawString(headers[i], (int) (Y_AXIS_HORIZONTAL_OFFSET + X_AXIS_NOTCH_DISTANCE * i - axisTextCorrection), HEIGHT - X_AXIS_VERTICAL_OFFSET
                        + 20);

            // draw the day/month into the scale
            else if (axiscounter == colsPerAxisDate || (itemsInWidth < 10 && i != 0)) {
                g2.drawString(headers[i], (int) (Y_AXIS_HORIZONTAL_OFFSET + X_AXIS_NOTCH_DISTANCE * i - axisTextCorrection), HEIGHT - X_AXIS_VERTICAL_OFFSET
                        + 20);
                axiscounter = 0;
            }

            axiscounter++;
        }

    }

    /**
     * Draw a curve between two dots.
     * 
     * @param columnCounter
     *            The number of the column to be drawn
     * @param value1
     *            Value of the first dot
     * @param value2
     *            Value of the second dot
     * @param color
     *            Color of the line
     */
    private void drawCurve(int columnCounter, int value1, int value2, Color color) {
        if (value1 == -1)
            return;

        double x1, x2, y1, y2;
        x1 = columnCounter * X_AXIS_NOTCH_DISTANCE + Y_AXIS_HORIZONTAL_OFFSET;
        y1 = (int) ((HEIGHT - X_AXIS_VERTICAL_OFFSET) - ((double) value1) * Y_AXIS_SCALE_FACTOR);

        if (value2 > -1) {
            x2 = (columnCounter + 1) * X_AXIS_NOTCH_DISTANCE + Y_AXIS_HORIZONTAL_OFFSET;
            y2 = (int) ((HEIGHT - X_AXIS_VERTICAL_OFFSET) - ((double) value2) * Y_AXIS_SCALE_FACTOR);

            this.drawQuadCurve((int) x1, (int) y1, (int) x2, (int) y2, color);
        }

        // draw the dot, don't draw it in case the dots would get too close
        // otherwise the graph will become all black
        if (X_AXIS_NOTCH_DISTANCE > STROKE_POINT.getLineWidth() + 1) {
            g2.setColor(Color.black);
            g2.setStroke(STROKE_POINT);
            g2.drawLine((int) x1, (int) y1, (int) x1, (int) y1);
        }
    }

    /**
     * Draw a 2:nd grade curve
     * 
     * @param x1
     *            X-coordinate for the first dot
     * @param y1
     *            Y-coordinate for the first dot
     * @param x2
     *            X-coordinate for the second dot
     * @param y2
     *            Y-coordinate for the second dot
     * @param color
     *            The color of the line
     */
    private void drawQuadCurve(int x1, int y1, int x2, int y2, Color color) {
        g2.setStroke(STROKE_LINE_THICKER);
        g2.setColor(color);
        QuadCurve2D.Double quad = new QuadCurve2D.Double();

        Point2D.Double start, end, control;
        start = new Point2D.Double(x1, y1);
        end = new Point2D.Double(x2, y2);
        control = new Point2D.Double();

        if (y1 > y2)
            control.setLocation(x1, y1 - (y1 - y2) / 2);
        else
            control.setLocation(x1, y2 - (y2 - y1) / 2);

        quad.setCurve(start, control, end);

        g2.draw(quad);
    }

    /**
     * Draw a 4:th grade curve.
     * 
     * @param x1
     *            X-coordinate for the first dot
     * @param y1
     *            Y-coordinate for the first dot
     * @param x2
     *            X-coordinate for the second dot
     * @param y2
     *            Y-coordinate for the second dot private void drawCubicCurve(int x1, int y1, int x2, int y2) { g2.setStroke(STROKE_LINE);
     *            g2.setColor(Color.green); CubicCurve2D.Double cubic = new CubicCurve2D.Double();
     * 
     *            Point2D.Double start, end, control1, control2; start = new Point2D.Double(x1, y1); end = new Point2D.Double(x2, y2); control1 = new
     *            Point2D.Double(); control2 = new Point2D.Double();
     * 
     *            if(y1 > y2) { control1.setLocation(x1 + NOTCH_LENGTH / 2, y1 - (y1 - y2) / 2); control2.setLocation(x2 - NOTCH_LENGTH / 2, y1 - (y1 - y2) /
     *            2); } else { control1.setLocation(x1 + NOTCH_LENGTH / 2, y2 - (y2 - y1) / 2); control2.setLocation(x2 - NOTCH_LENGTH / 2, y2 - (y2 - y1) / 2);
     *            }
     * 
     *            cubic.setCurve(start, control1, control2, end);
     * 
     *            g2.draw(cubic); }
     */

    private class GraphInfoObject {
        int graphType = -1;
        Color graphColor;

        GraphInfoObject(int graphType, Color graphColor) {
            this.graphType = graphType;
            this.graphColor = graphColor;
        }

    }
}
