package org.dmonix.gui;

import java.awt.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

/**
 * <p>
 * Title: DMGUI
 * </p>
 * <p>
 * Description:
 * </p>
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

public class BrowserFrame extends JFrame {
    private static final long serialVersionUID = 7526472295622776147L;

    /** Logging resource for this class. */
    private static final Logger log = Logger.getLogger(BrowserFrame.class.getName());

    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JPanel panelScroll = new JPanel();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JScrollPane scrollPane = new JScrollPane();
    private JPanel panelBtn = new JPanel();
    private JButton btnClose = new JButton();
    private JButton btnPrint = new JButton();
    private JEditorPane editorPane = new JEditorPane();

    private String title;
    private ImageIcon icon;
    private String url;

    public BrowserFrame(String title, ImageIcon icon, String url) {
        this.title = title;
        this.icon = icon;
        this.url = url;

        try {
            jbInit();
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws HeadlessException {
        new BrowserFrame("Test", null, "http://www.dmonix.org/programs/timex/manual.jsp");
    }

    public void setURL(String url) {
        try {
            this.editorPane.setPage(new URL(url));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Could not open help dialog", "Error opening help", JOptionPane.ERROR_MESSAGE);
            log.log(Level.WARNING, "Could not open help dialog", ex);
            super.dispose();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(gridBagLayout1);
        panelScroll.setLayout(gridBagLayout2);
        btnClose.setText("jButton1");
        btnPrint.setText("jButton2");
        editorPane.setFont(new java.awt.Font("Dialog", 0, 9));
        editorPane.setContentType("text/html");
        this.getContentPane().add(panelScroll,
                new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        panelScroll.add(scrollPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
                0));
        this.getContentPane().add(panelBtn,
                new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        panelBtn.add(btnPrint, null);
        panelBtn.add(btnClose, null);
        scrollPane.getViewport().add(editorPane, null);
    }

    private void init() {
        this.setURL(this.url);
        this.editorPane.setCaretPosition(0);

        super.setSize(400, 400);
        super.setTitle(this.title);

        if (this.icon != null)
            super.setIconImage(this.icon.getImage());

        super.setVisible(true);
    }

}