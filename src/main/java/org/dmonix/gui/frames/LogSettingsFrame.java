package org.dmonix.gui.frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

/**
 * Allows the user to change the log settings. <br>
 * The frame allows the user to change the current level of logging for all existing handlers (console, file etc.).<br>
 * The user may also save the configuration to file, the file will then override the system settings. <br>
 * The point with this is to deliver an application with a log configuration that states logging at level WARRNING or above. The user can then override this
 * setting locally. <br>
 * This can be very convenient for testing purposes.
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
public class LogSettingsFrame extends JFrame {
    private static final long serialVersionUID = 7526472295622776147L;

    /** The available levels of logging. */
    private static Level[] LOG_LEVELS = new Level[] { Level.ALL, Level.OFF, Level.SEVERE, Level.WARNING, Level.INFO, Level.CONFIG, Level.FINE, Level.FINER,
            Level.FINEST };

    private File localLogPropertiesFile = null;
    private Hashtable<String, HandlerObject> handlerTable = new Hashtable<String, HandlerObject>();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JPanel buttonPanel = new JPanel();
    private JButton okButton = new JButton();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JButton cancelButton = new JButton();
    private JScrollPane handlerScrollPane = new JScrollPane();
    private JPanel handlerPanel = new JPanel();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private JLabel fileHandlerLabel = new JLabel();
    private JButton saveButton = new JButton();

    /**
     * Creates the frame. <br>
     * The provided file is used to read any local settings that are used to override the system settings.
     * 
     * @param localLogPropertiesFile
     *            The file from where to find the local settings
     */
    public LogSettingsFrame(File localLogPropertiesFile) {
        this.setTitle("Log Management");
        this.localLogPropertiesFile = localLogPropertiesFile;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!localLogPropertiesFile.exists())
            localLogPropertiesFile.getParentFile().mkdirs();

        Properties logProperties = new Properties();
        try {
            if (localLogPropertiesFile.exists())
                logProperties.load(new FileInputStream(localLogPropertiesFile));
            else
                localLogPropertiesFile.getParentFile().mkdirs();
        } catch (IOException ex) {
        }

        Handler[] handlers = Logger.getLogger("").getHandlers();
        Object property;
        Level level = null;
        for (int i = 0; i < handlers.length; i++) {

            Handler tmpH = handlers[i];
            String name = tmpH.getClass().getName();
            property = logProperties.getProperty(name + ".level");

            JLabel tmpLabel = new JLabel(name.substring(name.lastIndexOf(".") + 1));
            JComboBox tmpComboBox = new JComboBox(LogSettingsFrame.LOG_LEVELS);

            // If there propery is found in the file it overrides the system settings
            if (property == null)
                level = tmpH.getLevel();
            else {
                for (int j = 0; j < LOG_LEVELS.length - 1; j++) {
                    if (property.toString().equalsIgnoreCase(LOG_LEVELS[j].toString())) {
                        level = LOG_LEVELS[j];
                        tmpH.setLevel(level); // set the new level from the file
                        break;
                    }
                }
            }

            tmpComboBox.setSelectedItem(level);

            handlerPanel.add(tmpLabel, new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(6, 6, 6,
                    6), 0, 0));
            handlerPanel.add(tmpComboBox, new GridBagConstraints(1, i, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5,
                    5, 5), 0, 0));
            handlerTable.put(name, new HandlerObject(tmpH, tmpComboBox));
        }
    }

    /**
     * Align this frame to a specific component, most commonly the frame/panel currently in view.
     * 
     * @param component
     *            The component to align to
     */
    public void setFrameRelativeTo(Component component) {
        this.setLocation(component.getX() + component.getWidth() / 2 - this.getWidth() / 2, component.getY() + component.getHeight() / 2 - this.getHeight() / 2);
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(gridBagLayout1);
        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okButton_actionPerformed(e);
            }
        });
        buttonPanel.setLayout(gridBagLayout2);
        cancelButton.setToolTipText("");
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelButton_actionPerformed(e);
            }
        });
        handlerPanel.setLayout(gridBagLayout3);
        fileHandlerLabel.setText("FileHandler");
        saveButton.setText("Save to file");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveButton_actionPerformed(e);
            }
        });
        this.getContentPane().add(buttonPanel,
                new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        buttonPanel.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(11, 5, 11, 5),
                0, 0));
        buttonPanel.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(11, 5, 11,
                11), 0, 0));
        buttonPanel.add(saveButton, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(11, 11, 11,
                5), 0, 0));
        this.getContentPane().add(handlerScrollPane,
                new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        handlerScrollPane.getViewport().add(handlerPanel, null);
    }

    /**
     * Save the logging properties to file. The file will be stored at
     * 
     * @param e
     *            the action event
     */
    private void saveButton_actionPerformed(ActionEvent e) {
        Properties logProperties = new Properties();

        try {
            if (localLogPropertiesFile.exists()) {
                logProperties.load(new FileInputStream(localLogPropertiesFile));
            }
        } catch (IOException ex) {
        }

        Enumeration keys = handlerTable.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            HandlerObject handlerObject = (HandlerObject) handlerTable.get(key);
            handlerObject.getHandler().setLevel((Level) handlerObject.getComboBox().getSelectedItem());
            logProperties.setProperty(key + ".level", handlerObject.getHandler().getLevel().toString());
        }

        try {
            logProperties.store(new FileOutputStream(localLogPropertiesFile, false), "");
        } catch (IOException ex1) {
        }
    }

    /**
     * Apply the current logging settings and dispose the frame.
     * 
     * @param e
     *            the action event
     */
    private void okButton_actionPerformed(ActionEvent e) {
        Properties logProperties = new Properties();

        try {
            if (localLogPropertiesFile.exists()) {
                logProperties.load(new FileInputStream(localLogPropertiesFile));
            }
        } catch (IOException ex) {
        }

        Enumeration keys = handlerTable.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            HandlerObject obj = (HandlerObject) handlerTable.get(key);
            obj.getHandler().setLevel((Level) obj.getComboBox().getSelectedItem());
            logProperties.setProperty(key + ".level", obj.getHandler().getLevel().toString());
        }
        this.dispose();
    }

    /**
     * Dispose the logging frame without applying the logging settings.
     * 
     * @param e
     *            the action event
     */
    private void cancelButton_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    private class HandlerObject {
        private Handler handler;
        private JComboBox comboBox;

        public HandlerObject(Handler handler, JComboBox comboBox) {
            this.handler = handler;
            this.comboBox = comboBox;
        }

        public Handler getHandler() {
            return handler;
        }

        public JComboBox getComboBox() {
            return comboBox;
        }
    }

}
