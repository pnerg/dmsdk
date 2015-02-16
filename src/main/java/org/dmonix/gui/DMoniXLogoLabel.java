package org.dmonix.gui;

import java.io.File;

import java.awt.Component;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

import org.dmonix.gui.frames.LogSettingsFrame;
import org.dmonix.util.ClassUtil;
import org.dmonix.util.ImageLoaderUtil;

/**
 * A special label that holds the DMoniX.org logo. <br>
 * The label also has a mouseadapter listener. If a user holds CTRL+ALT+SHIFT and double clicks on the logo a <code>LogSettingsFrame</code> is displayed.
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public class DMoniXLogoLabel extends JLabel {
    private static final long serialVersionUID = 9798101108494648L;

    private LogSettingsFrame logFrame;
    private Component owner;

    /**
     * Creates the logo. <br>
     * The provided file is used to read any local settings that are used to override the system settings.
     * 
     * @param properties
     */
    public DMoniXLogoLabel(File properties) {
        super(ImageLoaderUtil.getDmonixLogoBWIcon());

        this.logFrame = new LogSettingsFrame(properties);
        this.addMouseListener(new DMoniXLogoLabel_mouseAdapter());
    }

    /**
     * Sets the component to which the <code>LogSettingsFrame</code> shall be positioned to.
     * 
     * @param owner
     */
    public void setOwner(Component owner) {
        this.owner = owner;
    }

    private class DMoniXLogoLabel_mouseAdapter extends java.awt.event.MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            /**
             * Show log settings frame
             */
            if (e.isAltDown() && e.isShiftDown() && e.isControlDown() && e.getClickCount() == 2) {
                logFrame.pack();
                if (owner != null)
                    logFrame.setFrameRelativeTo(owner);

                logFrame.setVisible(true);
            }

            /**
             * Show dmonix.org
             */
            else if (e.getClickCount() == 2) {
                try {
                    ClassUtil.openBrowser("http://www.dmonix.org");
                } catch (Exception ex) {
                }
            }

        }
    }

}