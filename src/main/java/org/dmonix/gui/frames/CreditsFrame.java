package org.dmonix.gui.frames;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JWindow;

/**
 * <p>
 * Title:
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
public class CreditsFrame extends JWindow implements Runnable {
    private static final long serialVersionUID = 7526472295622776147L;

    private JPanel panel;
    private JScrollPane scrollPane = new JScrollPane();

    public CreditsFrame(JPanel panel) {
        this.panel = panel;

        // The size and position
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) panel.getPreferredSize().getWidth() + 25;
        int height = 200;
        int x = screenSize.width / 2 - width / 2;
        int y = screenSize.height / 2 - height / 2;
        this.setBounds(x, y, width, height);

        this.scrollPane.getViewport().add(this.panel);
        this.getContentPane().add(this.scrollPane);
    }

    public void showFrame() {
        this.scrollPane.getVerticalScrollBar().setMaximumSize(new Dimension(0, 0));
        this.scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        this.scrollPane.getVerticalScrollBar().setVisible(false);
        this.scrollPane.getVerticalScrollBar().setEnabled(false);
        this.setVisible(true);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
        }

        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        int pos = 0;
        while (pos < this.scrollPane.getVerticalScrollBar().getMaximum()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }

            this.scrollPane.getVerticalScrollBar().setValue(pos);
            pos += 2;
        }
        this.dispose();
    }
}
