package org.dmonix.util.image.imageobjects;

import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

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
public class ColumnChartImageObject extends BaseImageObject {
    /** The logger instance for this class */
    private static final Logger log = Logger.getLogger(ColumnChartImageObject.class.getName());

    private static final int COLUMN_WIDTH = 10;
    private static final double ANGLE_RADIANS = Math.toRadians(90);

    private List<ChartDataObject> data = new Vector<ChartDataObject>();
    private int maxHeight = -1;

    private Graphics2D g2 = null;

    public ColumnChartImageObject(int width, int height) {
        super(width, height);
    }

    public void addData(String name, int value, Color color) {
        if (value > maxHeight)
            maxHeight = value;

        data.add(new ChartDataObject(name, value, color));
    }

    public void paint(Graphics2D g2) {
        this.g2 = g2;
        g2.setColor(Color.black);

        super.X_AXIS_VERTICAL_OFFSET = 200;

        super.calculateXAxisNotchDistanceBasedOnObjects(data.size() + 1);
        super.calculateYAxisNotchDistanceBasedOnValue(maxHeight);

        super.drawXAxis(g2, false, false, false);
        super.drawYAxis(g2, true, false, true);
        try {
            this.drawData();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void drawData() throws IOException {
        ChartDataObject chartDataObject = null;
        String name = null;
        int x, y, x1, y1;
        for (int i = 0; i < data.size(); i++) {
            chartDataObject = (ChartDataObject) data.get(i);

            if (log.isLoggable(Level.FINE))
                log.log(Level.FINE, chartDataObject.toString());

            name = ((ChartDataObject) data.get(data.size() - 1 - i)).name;

            this.paintColumn(g2, i + 1, chartDataObject.value, chartDataObject.color);

            // Calculate the coords for the text
            g2.setColor(Color.black);
            x = (int) (Y_AXIS_HORIZONTAL_OFFSET + (i + 1) * X_AXIS_NOTCH_DISTANCE) - COLUMN_WIDTH / 2;
            // x = (int)(Y_AXIS_HORIZONTAL_OFFSET+(i+1)*X_AXIS_NOTCH_DISTANCE);
            y = HEIGHT - X_AXIS_VERTICAL_OFFSET + 5;

            /**
             * Since the entire coordiante system is turned with 90 degrees we need to translate the coordinates (x,y) to the new coordinate system. The
             * following forumula can be simplified since we always use 90 degrees and the following mathematical rules apply. cos(90) = 0; sin(90) = 1;
             * 
             * x1 = (int)(Math.cos(ANGLE_RADIANS)*(WIDTH/2+(y-HEIGHT/2)) + Math.sin(ANGLE_RADIANS)*(WIDTH/2+(y-HEIGHT/2))); y1 =
             * (int)(Math.cos(ANGLE_RADIANS)*(HEIGHT/2+(x-WIDTH/2)) + Math.sin(ANGLE_RADIANS)*(HEIGHT/2+(x-WIDTH/2)));
             */
            x1 = WIDTH / 2 + (y - HEIGHT / 2);
            y1 = HEIGHT / 2 + (x - WIDTH / 2) - (int) X_AXIS_NOTCH_DISTANCE;

            log.log(Level.FINE, "x=" + x1 + " ; y=" + y1);

            g2.rotate(ANGLE_RADIANS, WIDTH / 2, HEIGHT / 2);
            g2.drawString(name, x1, y1);
            g2.rotate(-ANGLE_RADIANS, WIDTH / 2, HEIGHT / 2);

        }
    }

    /**
     * Paint a column.
     * 
     * @param g2
     * @param columnCounter
     * @param height
     * @param color
     */
    private void paintColumn(Graphics2D g2, int columnCounter, int height, Color color) {
        int x, y;
        int scaledHeight = (int) (height * Y_AXIS_SCALE_FACTOR);

        x = (int) (Y_AXIS_HORIZONTAL_OFFSET + columnCounter * X_AXIS_NOTCH_DISTANCE) - COLUMN_WIDTH / 2;
        y = HEIGHT - X_AXIS_VERTICAL_OFFSET - scaledHeight;

        // Draw the rectangle
        g2.draw3DRect(x, y, COLUMN_WIDTH, (int) scaledHeight, true);

        // paint the rectangle
        GradientPaint paint = new GradientPaint(x, y, color, x + COLUMN_WIDTH, y + scaledHeight, Color.white);
        g2.setPaint(paint);

        g2.fill(new RoundRectangle2D.Double(x, y, COLUMN_WIDTH, scaledHeight, 10, 10));
    }

    /**
     * Internal class for holding information on each column object
     */
    private class ChartDataObject {
        private String name;
        private int value;
        private Color color;

        ChartDataObject(String name, int value, Color color) {
            this.name = name;
            this.value = value;
            this.color = color;
        }

        public String toString() {
            return name + " : " + value + " : " + color;
        }
    }

}