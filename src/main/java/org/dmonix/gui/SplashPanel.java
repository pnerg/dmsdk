package org.dmonix.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import org.dmonix.util.ImageLoaderUtil;

/**
 * The panel used to draw the Splash Window
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
public class SplashPanel extends JPanel {
    private static final long serialVersionUID = 7526472295622776147L;

    private JWindow splashWindow;

    // the size of the panel
    private static final int width = 550;

    // splash gif new
    private static Image splash = ImageLoaderUtil.getImage("/img/splash.jpg");

    // dmonix logo
    private static Image dmonixLogo = ImageLoaderUtil.getDmonixLogoColorImage();

    // product name
    private String productName = null;
    private Font productNameFont = new Font("Arial", Font.BOLD, 30);
    private Color productNameColor = new Color(102, 102, 102);

    // version
    private String version = null;
    private Font versionFont = new Font("Arial", Font.PLAIN, 11);
    private Color versionColor = new Color(102, 102, 102);

    // copyright
    private String copyright = "Author Peter Nerg, Sweden";
    private String credits = "Brought to you by : http://www.dmonix.org";
    private Font copyrightFont = new Font("Arial", Font.PLAIN, 9);
    private Color copyrightColor = new Color(102, 102, 102);

    // checked ribbon
    private static final Color checkedRibbonBgColor = new Color(51, 102, 153);
    private static final Color checkedRibbonSquareColor = new Color(51, 153, 204);

    // dotted line
    private static final Color dottedLineColor[] = { new Color(245, 245, 245), new Color(255, 255, 255), new Color(172, 175, 172) };

    // closeButton
    private boolean showCloseBtn = true;
    private static final Color closeButtonbgColor = new Color(172, 175, 172);
    private static final Font closeButtonFont = new Font("Arial", Font.PLAIN, 11);
    private static final int closeButtonX = 490;
    private static final int closeButtonY = 247;
    private static final int closeButtonWidth = 50;
    private static final int closeButtonHeight = 265 - 246 - 1;

    // copyrighttext
    private static final String[] copyrighttext = new String[] { "This software was developed by dmonix.org", "",
            "The software on is licensed according to the terms of", "The GNU General Public License (GPL) version 3\n" };

    private static final Font copyrightTextFont = new Font("Arial", Font.PLAIN, 9);
    private static final Color copyrightTextColor = Color.BLACK;

    /**
     * Shows the DMoniX splashpanel. <br>
     * If the system parameter <code>dm.nosplash</code> is set to <code>true</code> no splash is displayed.
     * 
     * @param productName
     *            The name of the product, software
     * @param majorVersion
     *            Major version
     * @param minorVersion
     *            Minor version
     */
    public static void showSplash(String productName, int majorVersion, int minorVersion) {
        if (System.getProperty("dm.nosplash") != null && System.getProperty("dm.nosplash").equalsIgnoreCase("true"))
            return;

        SplashPanel splash = new SplashPanel(productName, majorVersion, minorVersion, false);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
        }

        splash.close();
    }

    /**
     * Shows the DMoniX splashpanel. <br>
     * If the system parameter <code>dm.nosplash</code> is set to <code>true</code> no splash is displayed.
     * 
     * @param productName
     *            The name of the product, software
     * @param majorVersion
     *            Major version
     * @param minorVersion
     *            Minor version
     */
    public static void showSplash(String productName, String version) {
        if (System.getProperty("dm.nosplash") != null && System.getProperty("dm.nosplash").equalsIgnoreCase("true"))
            return;

        SplashPanel splash = new SplashPanel(productName, version, false);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
        }

        splash.close();
    }

    public SplashPanel(String productName, int majorVersion, int minorVersion) {
        this(productName, majorVersion, minorVersion, true);
    }

    public SplashPanel(String productName, int majorVersion, int minorVersion, boolean showCloseBtn) {
        super();
        this.showCloseBtn = showCloseBtn;

        this.version = "Version " + majorVersion + "." + minorVersion + "." + getBuildNumber();
        this.productName = productName;

        // Adjust the font of the product name, the size is limited
        JLabel label = new JLabel(productName);
        label.setFont(productNameFont);
        int productnameMaxWidth = 533 - 217; // the width is not allowed to be bigger
        while (label.getPreferredSize().width > productnameMaxWidth) {
            productNameFont = new Font("Arial", Font.BOLD, productNameFont.getSize() - 1);
            label.setFont(productNameFont);
        }

        splashWindow = new JWindow();

        this.setBackground(Color.WHITE);
        splashWindow.setContentPane(this);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        this.setLayout(new BorderLayout());

        // There is a Close button to close the splash before the GUI is loaded
        this.addMouseListener(new SplashPanel_closeBtn_mouseAdapter());

        // The size and position
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 550;
        int height = 300;
        int x = screenSize.width / 2 - width / 2;
        int y = screenSize.height / 2 - height / 2;
        splashWindow.setBounds(x, y, width, height);

        splashWindow.setVisible(true);
        splashWindow.update(splashWindow.getGraphics());

    }

    public SplashPanel(String productName, String version, boolean showCloseBtn) {
        super();
        this.showCloseBtn = showCloseBtn;

        this.version = "Version " + version;
        this.productName = productName;

        // Adjust the font of the product name, the size is limited
        JLabel label = new JLabel(productName);
        label.setFont(productNameFont);
        int productnameMaxWidth = 533 - 217; // the width is not allowed to be bigger
        while (label.getPreferredSize().width > productnameMaxWidth) {
            productNameFont = new Font("Arial", Font.BOLD, productNameFont.getSize() - 1);
            label.setFont(productNameFont);
        }

        splashWindow = new JWindow();

        this.setBackground(Color.WHITE);
        splashWindow.setContentPane(this);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        this.setLayout(new BorderLayout());

        // There is a Close button to close the splash before the GUI is loaded
        this.addMouseListener(new SplashPanel_closeBtn_mouseAdapter());

        // The size and position
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 550;
        int height = 300;
        int x = screenSize.width / 2 - width / 2;
        int y = screenSize.height / 2 - height / 2;
        splashWindow.setBounds(x, y, width, height);

        splashWindow.setVisible(true);
        splashWindow.update(splashWindow.getGraphics());

    }

    public boolean isClosePressed(int x, int y) {
        if (x >= closeButtonX && x <= closeButtonX + closeButtonWidth && y >= closeButtonY && y <= closeButtonY + closeButtonHeight)
            return true;
        else
            return false;
    }

    public void close() {
        splashWindow.toBack();
        splashWindow.setVisible(false);
        splashWindow.dispose();
    }

    public void paint(Graphics g) {
        super.paint(g);

        // The splash picture
        g.drawImage(splash, 2, 2, 199, 296, this);

        // upper checked ribbon
        int checkedWidth = width - 2 - 330;
        int checkedHeight = 17;
        g.setColor(checkedRibbonBgColor);
        g.fillRect(330, 18, checkedWidth, checkedHeight);
        g.setColor(checkedRibbonSquareColor);
        for (int i = 0; i < checkedHeight; i = i + 2) {
            g.drawLine(330, 18 + i, width - 2 - 1, 18 + i);
        }
        for (int i = 330; i <= width - 2 - 1; i = i + 2) {
            g.drawLine(i, 18, i, 18 + checkedHeight - 1);
        }

        // logo
        // g.drawImage(dmonixLogo,218,16,102,24,this);
        g.drawImage(dmonixLogo, 200, 16, 200, 30, this);

        // Product Name
        g.setFont(productNameFont);
        g.setColor(productNameColor);
        g.drawString(productName, 217, 95 - productNameFont.getSize());

        // version
        g.setFont(versionFont);
        g.setColor(versionColor);
        g.drawString(version, 218, 107 - versionFont.getSize());

        // copyright text
        g.setColor(copyrightTextColor);
        int ypos = 154 - copyrightTextFont.getSize();
        for (int i = 0; copyrighttext != null && i < copyrighttext.length; i++) {
            String text = (String) copyrighttext[i];
            g.drawString(text, 217, ypos);
            ypos += copyrightTextFont.getSize() + 4; // 4 is some extra space
        }

        // dotted line
        for (int colorIndex = 0; colorIndex < 3; colorIndex++) {
            g.setColor(dottedLineColor[colorIndex]);
            for (int i = 202 + colorIndex; i <= width - 2 - 1; i = i + 3) {
                g.drawLine(i, 246, i, 246);
                g.drawLine(i, 265, i, 265);
            }
        }

        // close button
        if (this.showCloseBtn) {
            g.setColor(closeButtonbgColor);
            g.fillRect(closeButtonX, closeButtonY, closeButtonWidth, closeButtonHeight);
            g.setFont(closeButtonFont);
            g.setColor(Color.BLACK);
            g.drawString("Close", closeButtonX + 10, closeButtonY + 11 + 2);
        }

        // copyright
        g.setFont(copyrightFont);
        g.setColor(copyrightColor);
        g.drawString(copyright, 217, 284);
        g.drawString(credits, 217, 295);
    }

    private String getBuildNumber() {
        try {
            Properties p = new Properties();
            p.load(SplashPanel.class.getClassLoader().getResourceAsStream("build.number"));
            return p.getProperty("build.number", "x");
        } catch (Exception ex) {
            return "x";
        }
    }

    /**
     * Internal action listener.
     */
    class SplashPanel_closeBtn_mouseAdapter extends java.awt.event.MouseAdapter {
        SplashPanel_closeBtn_mouseAdapter() {
        }

        public void mouseClicked(MouseEvent e) {
            if (isClosePressed(e.getX(), e.getY())) {
                splashWindow.toBack();
                splashWindow.setVisible(false);
                splashWindow.dispose();
            }
        }
    }
}
