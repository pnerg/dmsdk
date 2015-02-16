package org.dmonix.gui.panes;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 * Utility for displaying various messages. Copyrigh: Copyright (c) 2001 Company: dmonix.org
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public abstract class OptionPaneHandler {

    /**
     * Display copyright information
     * 
     * @param parent
     *            Owner frame, null if not a frame
     * @param year
     *            Year for the copyright
     * @param owner
     *            Name of the owner
     * @param version
     *            Current version
     * @param heading
     *            The text to be displayed in the heading
     */
    public static void copyrightPane(Component parent, String year, String owner, String version, String heading) {
        String copyright = "Copyright " + year + ", " + owner + '\n' + "v" + version;
        JOptionPane.showMessageDialog(parent, copyright, heading, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Notify the user that an error has occurred and it has been logged to file
     * 
     * @param parent
     *            Owner frame, null if not a frame
     */
    public static void errorLogPane(Component parent) {
        String heading = "Runtime Error";
        String text = "For further infomration refer to the logfile for this program\n' The program will terminate";
        JOptionPane.showMessageDialog(parent, text, heading, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Notify the user that an error has occurred and it has been logged to file
     * 
     * @param parent
     *            Owner frame, null if not a frame
     * @param logFile
     *            Name of the logfile beeing used
     */
    public static void errorLogPane(Component parent, String logFile) {
        String heading = "Runtime Error";
        String text = "The error has been logged to: " + logFile + '\n' + "The program will terminate";
        JOptionPane.showMessageDialog(parent, text, heading, JOptionPane.ERROR_MESSAGE);
    }
}