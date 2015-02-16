package org.dmonix.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JProgressBar;
import javax.swing.Timer;

/**
 * Progressbar that keeps loops rolling from left to the right. <br>
 * The class is useful for applications where one wants to display a constantly rolling progressbar to indicate that something is happening.
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
public class RollingProgressBar extends JProgressBar {
    private static final long serialVersionUID = 7526472295622776147L;

    private int intervall = 500; // 500 milliseconds
    private int resolution = 10; // show 10 parts before zeroing
    private Timer timer;

    /**
     * Creates a progressbar with the default interval and resolution.
     */
    public RollingProgressBar() {
        this(500, 10);
    }

    /**
     * Creates a progressbar with the supplied interval and resolution.
     * 
     * @param intervall
     *            The interval (millisecs) for the progressbar update
     * @param resolution
     *            In how meny parts is the step before starting all over
     */
    public RollingProgressBar(int intervall, int resolution) {
        this.intervall = intervall;
        this.resolution = resolution;

        init();
    }

    /**
     * Starts the rolling and shows the progressbar.
     */
    public void startRolling() {
        setVisible(true);
        if (!timer.isRunning())
            timer.start();
    }

    /**
     * Stops the rolling and hides the progressbar.
     */
    public void stopRolling() {
        if (timer.isRunning())
            timer.stop();
        setValue(getMinimum());
        setVisible(false);
    }

    /**
     * Checks if the progressbar is running
     * 
     * @return true if it is busy otherwise false
     */
    public boolean isBusy() {
        return timer.isRunning();
    }

    private void init() {
        setValue(0);
        setVisible(false);
        setMaximum(resolution);
        timer = new Timer(intervall, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                int newValue = getValue() + 1;
                if (newValue > getMaximum() || newValue < getMinimum()) {
                    newValue = 0;
                }
                setValue(newValue);
            }
        });
    }

}