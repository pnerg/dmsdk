package org.dmonix.gui.frames;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;
import java.util.Hashtable;
import java.util.Locale;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.dmonix.gui.SimpleButtonGroup;
import org.dmonix.gui.SimpleRadioButtonMenuItem;
import org.dmonix.gui.SplashPanel;
import org.dmonix.gui.panes.OptionPaneHandler;
import org.dmonix.xml.XMLDocument;
import org.dmonix.xml.XMLElement;
import org.dmonix.xml.XMLElementList;

/**
 * A base frame class to be used by all swing applications.
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
public class BaseFrame extends JFrame implements WindowListener {
    /** Logging resource for this class. */
    private static final Logger log = Logger.getLogger(BaseFrame.class.getName());

    private static final long serialVersionUID = 4526472235624756147L;

    protected static final String LOOKNFEEL_SUN = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
    protected static final String LOOKNFEEL_GTK = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
    protected static final String LOOKNFEEL_METAL = "javax.swing.plaf.metal.MetalLookAndFeel";
    protected static final String LOOKNFEEL_CROSS = UIManager.getCrossPlatformLookAndFeelClassName();
    protected static final String LOOKNFEEL_WINDOWS = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    protected static final String LOOKNFEEL_MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";

    protected JPanel panelFrame = new JPanel();

    private static Hashtable<String, String> hashTableProperties = null;
    /** Menu containing all the available look and feels. */
    private JMenu menuLooknFeel = null;
    private SimpleButtonGroup btnGroupLooknFeel = null;

    private String lookNFeel = null;

    static {
        Locale.setDefault(Locale.US);
        System.setProperty("user.country", Locale.US.getCountry());
        System.setProperty("user.language", Locale.US.getLanguage());
        System.setProperty("user.variant", Locale.US.getVariant());
    }

    public BaseFrame() {
    }

    /**
     * Shows an error message.
     * 
     * @param title
     *            The title
     * @param message
     *            The message since 1.1
     */
    public void showErrorMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shows an information message.
     * 
     * @param title
     *            The title
     * @param message
     *            The message since 1.1
     */
    public void showInformationMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Handle window closing event
     * 
     * @param e
     *            The event
     */
    public void windowClosed(WindowEvent e) {
    }

    /**
     * Handle window deiconified event
     * 
     * @param e
     *            The event
     */
    public void windowDeiconified(WindowEvent e) {
        this.repaint();
    }

    /**
     * Handle window iconified event
     * 
     * @param e
     *            The event
     */
    public void windowIconified(WindowEvent e) {
    }

    /**
     * Handle window activated event
     * 
     * @param e
     *            The event
     */
    public void windowActivated(WindowEvent e) {
    }

    /**
     * Handle window deactivated event
     * 
     * @param e
     *            The event
     */
    public void windowDeactivated(WindowEvent e) {
    }

    /**
     * Handle window opened event
     * 
     * @param e
     *            The event
     */
    public void windowOpened(WindowEvent e) {
        this.repaint();
    }

    /**
     * Handle window closing event
     * 
     * @param e
     *            The event
     */
    public void windowClosing(WindowEvent e) {
        super.dispose();
        System.exit(0);
    }

    /**
     * This will read the config.xml file and store the properties in a static hashtable.
     * 
     * @param path
     *            The path to the config file
     */
    protected void configure(String path) {
        if (hashTableProperties != null)
            return;

        hashTableProperties = new Hashtable<String, String>();

        try {
            XMLDocument doc = new XMLDocument(BaseFrame.class.getResourceAsStream(path));

            XMLElementList list = doc.getElementsByTagName("property");
            for (XMLElement xmlElement : list) {
                hashTableProperties.put(xmlElement.getAttribute("name"), xmlElement.getElementValue());
            }

            // set the title
            super.setTitle(this.getProperty("title"));

            // set the frame size
            String width = this.getProperty("width");
            String height = this.getProperty("height");
            if (width != null && height != null) {
                super.setSize(Integer.parseInt(width), Integer.parseInt(height));
            }

            // set the frame resizable
            String property = this.getProperty("resizable");
            if (property != null)
                super.setResizable(Boolean.getBoolean(property));

            // set the frame icon
            property = this.getProperty("frameicon");
            if (property != null)
                this.setIconImage(new ImageIcon(BaseFrame.class.getResource(property)).getImage());

        } catch (Exception ex) {
            this.exitError(ex);
        }
    }

    /**
     * Something has gone wrong, log the error and exit the program
     * 
     * @param ex
     *            The exception to log
     */
    protected void exitError(Exception ex) {
        OptionPaneHandler.errorLogPane(this);
        log.log(Level.SEVERE, "Exiting program", ex);
        super.dispose();
        System.exit(-1);
    }

    /**
     * Something has gone wrong, log the error and exit the program
     * 
     * @param configFile
     *            The name of the log file
     * @param ex
     *            The exception to log
     */
    protected void exitError(String configFile, Exception ex) {
        OptionPaneHandler.errorLogPane(this, configFile);
        log.log(Level.SEVERE, "Exiting program", ex);
        super.dispose();
        System.exit(-1);
    }

    /**
     * Get a property from the config.xml file
     * 
     * @param name
     *            The name of the property
     * @return The property value
     */
    protected String getProperty(String name) {
        Object o = hashTableProperties.get(name);
        if (o == null) {
            log.log(Level.CONFIG, "No such property exist : " + name);
            return null;
        }

        return o.toString();
    }

    /**
     * Set the font for the all menu and menu items in the given menu bar. <br>
     * The method will recursively iterate through all menus and sub-menus.
     * 
     * @param menuBar
     *            The menu bar
     * @param fontMenu
     *            The font for the menus
     * @param fontMenuItem
     *            The font for the menu items
     */
    protected void setFont(JMenuBar menuBar, Font fontMenu, Font fontMenuItem) {
        Component[] components = menuBar.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof JMenu) {
                setFont((JMenu) components[i], fontMenu, fontMenuItem);
            } else if (components[i] instanceof JMenuItem)
                components[i].setFont(fontMenuItem);
        }
    }

    /**
     * Set the font for the all menu and menu items in the given menu bar. <br>
     * The method will recursively iterate through all menus and sub-menus.
     * 
     * @param menu
     *            The menu
     * @param fontMenu
     *            The font for the menus
     * @param fontMenuItem
     *            The font for the menu items
     */
    protected void setFont(JMenu menu, Font fontMenu, Font fontMenuItem) {
        menu.setFont(fontMenu);
        Component[] components = menu.getMenuComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof JMenu) {
                setFont((JMenu) components[i], fontMenu, fontMenuItem);
            } else if (components[i] instanceof JMenuItem)
                components[i].setFont(fontMenuItem);
        }
    }

    /**
     * Creates and returns a look and feel menu
     * 
     * @return
     * @since 1.1
     */
    protected JMenu getLooknFeelMenu() {
        /** Menu containing all the available look and feels. */
        menuLooknFeel = new JMenu("Look 'n feel");
        btnGroupLooknFeel = new SimpleButtonGroup();

        // Get all defined look 'n feels and add them as radio buttons to the
        // menu
        UIManager.LookAndFeelInfo[] look = UIManager.getInstalledLookAndFeels();
        for (int i = 0; i < look.length; i++) {
            // Ignore the look 'n feel if is not supported by the OS
            try {
                if (((LookAndFeel) Class.forName(look[i].getClassName()).newInstance()).isSupportedLookAndFeel()) {
                    SimpleRadioButtonMenuItem btn = new SimpleRadioButtonMenuItem(look[i].getName(), look[i].getClassName());
                    btn.setAction(new ActionLooknFeel(look[i].getName(), this));
                    menuLooknFeel.add(btn);
                    btnGroupLooknFeel.add(btn);
                }
            } catch (Exception ex) {
                log.log(Level.FINER, "Look and feel" + look[i].getClassName() + " is not supported");
            }
        }
        return menuLooknFeel;
    }

    /**
     * 
     * @return
     * @since 1.1
     */
    protected JMenuItem getAboutMenuItem() {
        JMenuItem menuItemAbout = new JMenuItem("About", new ImageIcon(BaseFrame.class.getClassLoader().getResource("org/dmonix/gui/img/Information16.gif")));
        menuItemAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSplashScreen(true);
            }
        });

        return menuItemAbout;
    }

    /**
     * Set the look and feel for the GUI.
     * 
     * @param looknfeel
     *            The look and feel
     * @throws Exception
     */
    protected void setLooknFeel(String looknfeel) {
        try {
            if (this.btnGroupLooknFeel != null)
                btnGroupLooknFeel.setSelectionByValue(looknfeel);
            else {
                UIManager.setLookAndFeel(looknfeel);
                SwingUtilities.updateComponentTreeUI(this);
            }
            this.lookNFeel = looknfeel;
        } catch (Exception ex) {
            log.log(Level.WARNING, "Failed to set look and feel " + looknfeel);
        }
    }

    /**
     * Returns the currently set look and feel.
     * 
     * @return The look and feel, null if not set
     */
    protected String getLooknFeel() {
        return this.lookNFeel;
    }

    protected void showSplashScreen(boolean allowClose) {
        String title = "";
        String version = "x.x.x";
        try {
            String className = getClass().getSimpleName();
            String classFileName = className + ".class";
            String pathToThisClass = getClass().getResource(classFileName).toString();

            int mark = pathToThisClass.indexOf("!");
            String pathToManifest = pathToThisClass.toString().substring(0, mark + 1) + "/META-INF/MANIFEST.MF";
            Manifest manifest = new Manifest(new URL(pathToManifest).openStream());

            Attributes attr = manifest.getMainAttributes();

            title = attr.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
            version = attr.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
        } catch (Exception ex) {
            log.log(Level.WARNING, "Failed to load version from manifest", ex);
        }

        if (!allowClose)
            SplashPanel.showSplash(title, version);
        else
            new SplashPanel(title, version, true);
    }

    /**
     * The "look n feel" action class.
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
    protected class ActionLooknFeel extends AbstractAction {
        private static final long serialVersionUID = 2526472442622776147L;
        private JFrame owner;

        private ActionLooknFeel(String name, JFrame owner) {
            super(name);
            this.owner = owner;
        }

        public void actionPerformed(ActionEvent e) {
            try {
                String looknfeel = ((SimpleRadioButtonMenuItem) e.getSource()).getValue();
                UIManager.setLookAndFeel(looknfeel);
                SwingUtilities.updateComponentTreeUI(owner);
            } catch (Exception ex) {
                log.log(Level.WARNING, "Error when changing look 'n feel", ex);
            }
        }
    }
}