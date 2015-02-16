package org.dmonix.util.image.imageobjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

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
public interface ImageObject {

    /**
     * Returns the boundaries of the image.
     * 
     * @return The boundaries
     */
    public Rectangle getBounds();

    /**
     * Paints the graphics.
     * 
     * @param g2
     *            The graphics object
     */
    public void paint(Graphics2D g2);
}
