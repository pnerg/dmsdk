package org.dmonix.util.print;

import java.awt.*;
import java.awt.geom.*;
import java.awt.print.*;

import java.util.*;

import javax.swing.*;
import java.awt.event.*;

/**
 * <p>
 * A printable object.
 * </p>
 * The class contains an outer frame that is wrapped around a printable panel.<br>
 * The class provides the possibility to make a pre-view of the pritable inner-panel and also adds some buttons for the user to chose whether to proceed with
 * the printout or not.
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
public class SimplePrintableObject extends JFrame {
    private static final long serialVersionUID = 7526472295622776147L;

    private PrinterJob printerJob;

    private String title = "";

    private int maxWidth = 0;

    private int maxWidth2 = 0;

    private int y_position = 0;

    GridBagLayout gridBagLayoutFrame = new GridBagLayout();

    JPanel btnPanel = new JPanel();

    PrintablePanel printablePanel = new PrintablePanel(this);

    FlowLayout flowLayout1 = new FlowLayout();

    JButton btnCancel = new JButton();

    JButton btnPrint = new JButton();

    JScrollPane scrollPane = new JScrollPane();

    public SimplePrintableObject() {
        this("");
    }

    /**
     * @param title
     *            The title of the printout
     */
    public SimplePrintableObject(String title) {
        this.printerJob = PrinterJob.getPrinterJob();
        this.title = title;
        try {
            jbInit();
            this.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a row heading and rows from the data in the hashtable.<br>
     * Each key in the hashtable will be a row in the printout.
     * 
     * @param heading
     *            The row heading
     * @param data
     *            The rows
     */
    public void add(String heading, Hashtable data) {
        if (data.isEmpty())
            return;

        this.addHeading(heading);

        Enumeration params = data.keys();
        String param;
        while (params.hasMoreElements()) {
            param = params.nextElement().toString();
            this.addRow(param, data.get(param).toString());
        }
    }

    /**
     * Add a row heading.
     * 
     * @param heading
     */
    public void addHeading(String heading) {
        JLabel lblHeading = new JLabel(heading);
        lblHeading.setHorizontalAlignment(JLabel.CENTER);
        lblHeading.setBackground(Color.lightGray);
        lblHeading.setOpaque(true);
        lblHeading.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
        this.printablePanel.panelText.add(lblHeading, new GridBagConstraints(0, this.y_position, 2, 1, 0.0, 0.0, GridBagConstraints.NORTH,
                GridBagConstraints.HORIZONTAL, new Insets(6, 12, 6, 12), 0, 0));

        this.y_position++;
        this.validate();
    }

    /**
     * Add a row.
     * 
     * @param param
     *            The name of the parameter
     * @param value
     *            The value of the parameter
     */
    public void addRow(String param, boolean value) {
        this.addRow(param, "" + value);
    }

    /**
     * Add a row.
     * 
     * @param param
     *            The name of the parameter
     * @param value
     *            The value of the parameter
     */
    public void addRow(String param, String value) {
        JLabel lblParam = new JLabel(param);
        lblParam.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new java.awt.Font("Dialog", Font.PLAIN, 11));

        lblValue.setHorizontalAlignment(SwingConstants.CENTER);

        /** @todo debug, remove */
        lblParam.setBackground(Color.white);
        lblParam.setOpaque(true);
        lblValue.setBackground(Color.white);
        lblValue.setOpaque(true);

        // Add the labels to the row panel
        this.printablePanel.panelText.add(lblParam, new GridBagConstraints(0, this.y_position, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, new Insets(6, 12, 6, 12), 0, 0));

        this.printablePanel.panelText.add(lblValue, new GridBagConstraints(1, this.y_position, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, new Insets(6, 12, 6, 12), 0, 0));

        this.y_position++;
        this.validate();

        this.maxWidth = lblParam.getWidth();
        this.maxWidth2 = lblValue.getWidth();
    }

    /**
     * Formats the pre-view panel for printout<b> This is done by adding an empty row to the bottom.<br>
     * The row will expand itself thus forcing the rest of the content of the panel up to the top of the panel.
     */
    public void finish() {
        // Expand the empty label to be somewhat wider then the original size.
        // Otherwise the printout will behave strangely and sometimes some of
        // the text will not fit into the page
        JLabel empty1 = new JLabel(" ");
        empty1.setMinimumSize(new Dimension(this.maxWidth + 24 + 10, 1));
        empty1.setPreferredSize(new Dimension(this.maxWidth + 24 + 10, 1));

        JLabel empty2 = new JLabel(" ");
        empty2.setMinimumSize(new Dimension(this.maxWidth2 + 24 + 10, 1));
        empty2.setPreferredSize(new Dimension(this.maxWidth2 + 24 + 10, 1));

        /** @todo debug, remove */
        empty1.setBackground(Color.white);
        empty1.setOpaque(true);
        empty2.setBackground(Color.white);
        empty2.setOpaque(true);

        this.printablePanel.panelText.add(empty1, new GridBagConstraints(0, this.y_position, 1, 1, 0.0, 1.0, GridBagConstraints.NORTHWEST,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        this.printablePanel.panelText.add(empty2, new GridBagConstraints(1, this.y_position, 1, 1, 0.0, 1.0, GridBagConstraints.NORTHWEST,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        btnPrint.setEnabled(true);
        this.validate();
    }

    /**
     * Set the title of the printout.
     * 
     * @param title
     */
    public void setPageTitle(String title) {
        this.printablePanel.lblTitle.setText(title);
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(gridBagLayoutFrame);
        this.setBackground(Color.white);
        this.addWindowListener(new SimplePrintableObject_this_windowAdapter(this));

        JPanel dummyPanel = new JPanel();
        dummyPanel.setLayout(new FlowLayout());
        dummyPanel.add(printablePanel);
        dummyPanel.setBackground(Color.white);

        btnPanel.setLayout(flowLayout1);
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new SimplePrintableObject_btnCancel_actionAdapter(this));
        btnPrint.setText("Print");
        btnPrint.setEnabled(false);
        btnPrint.addActionListener(new SimplePrintableObject_btnPrint_actionAdapter(this));
        btnPanel.setBackground(Color.white);

        scrollPane.getViewport().add(dummyPanel);
        scrollPane.setBackground(Color.white);

        this.getContentPane().add(scrollPane,
                new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        this.getContentPane().add(btnPanel,
                new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

        btnPanel.add(btnPrint, null);
        btnPanel.add(btnCancel, null);
    }

    private void init() {
        this.printablePanel.lblTitle.setText(this.title);
        this.setSize(600, 800);
        this.setTitle("Printout preview");
        this.setVisible(true);
    }

    void this_windowClosing(WindowEvent e) {
        this.dispose();
    }

    void btnPrint_actionPerformed(ActionEvent e) {
        if (!this.printerJob.printDialog()) {
            this.dispose();
            return;
        }

        this.setVisible(false);
        this.printerJob.setPageable(this.printablePanel);

        try {
            this.printerJob.print();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        this.dispose();
    }

    void btnCancel_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    /**
     * <p>
     * This is the panel that actually will be printed.
     * </p>
     * The panel contains nothing but the labels that will be printed.
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
    private class PrintablePanel extends JPanel implements Printable, Pageable {
        private static final long serialVersionUID = 7526472295622776147L;

        private PageFormat pageFormat;

        private int pageCount = -1;

        private SimplePrintableObject owner;

        private JPanel panelTitle = new JPanel();

        private JPanel panelText = new JPanel();

        private GridBagLayout gridBagLayoutTitle = new GridBagLayout();

        private GridBagLayout gridBagLayoutText = new GridBagLayout();

        private GridBagLayout gridBagLayoutPanel = new GridBagLayout();

        private JLabel EMPTY_LABEL = new JLabel(" ");

        private JLabel lblTitle = new JLabel();

        private PrintablePanel(SimplePrintableObject owner) {
            this.owner = owner;
            try {
                jbInit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public int getNumberOfPages() {
            if (this.pageCount > 0)
                return this.pageCount;

            this.pageFormat = printerJob.defaultPage();

            int panelHeight = this.getHeight();
            int pageHeight = (int) pageFormat.getImageableHeight();
            int lblHeight = (int) this.EMPTY_LABEL.getHeight();

            // We need to subtract the size of the empty label in order
            // achieve a correct page count otherwise we will always have an
            // empty space in the end of the printout
            panelHeight -= lblHeight;

            this.pageCount = ((panelHeight - panelHeight % pageHeight) / pageHeight) + 1;
            return this.pageCount;
        }

        public PageFormat getPageFormat(int pageIndex) {
            return this.pageFormat;
        }

        public Printable getPrintable(int pageIndex) {
            Dimension d = new Dimension((int) pageFormat.getImageableWidth(), this.getHeight());
            this.owner.setSize(d);
            this.owner.validate();
            this.owner.repaint();

            return owner.printablePanel;
        }

        public int print(Graphics graphic, PageFormat pageFormat, int pageIndex) {
            if (pageIndex >= this.pageCount)
                return Printable.NO_SUCH_PAGE;

            graphic.setColor(Color.black);

            Graphics2D g2 = (Graphics2D) graphic;

            AffineTransform saveXform = g2.getTransform();

            // int inset =
            // ((int)(pageFormat.getImageableWidth()-this.getWidth())/2)-10;
            // System.out.println("inset="+inset);
            // inset = 0;

            g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            g2.translate((this.getWidth() / 2), -(pageIndex * pageFormat.getImageableHeight()));

            // g2.translate(pageFormat.getImageableX(),
            // -pageIndex * pageFormat.getImageableHeight());

            g2.setPaint(Color.white);

            super.printAll(graphic);

            g2.setTransform(saveXform);

            return Printable.PAGE_EXISTS;
        }

        private void jbInit() {
            this.setLayout(gridBagLayoutPanel);
            this.setBackground(Color.white);
            panelTitle.setLayout(gridBagLayoutTitle);
            panelText.setLayout(gridBagLayoutText);

            panelTitle.setBackground(Color.white);
            panelText.setBackground(Color.white);

            lblTitle.setFont(new java.awt.Font("Dialog", 1, 16));
            lblTitle.setText("Title");

            panelTitle.add(lblTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(12, 0, 0, 0),
                    0, 0));

            this.add(panelTitle, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            this.add(panelText, new GridBagConstraints(0, 1, 2, 1, 0.0, 1.0, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        }
    }

    private class SimplePrintableObject_this_windowAdapter extends java.awt.event.WindowAdapter {
        SimplePrintableObject adaptee;

        private SimplePrintableObject_this_windowAdapter(SimplePrintableObject adaptee) {
            this.adaptee = adaptee;
        }

        public void windowClosing(WindowEvent e) {
            adaptee.this_windowClosing(e);
        }
    }

    private class SimplePrintableObject_btnPrint_actionAdapter implements java.awt.event.ActionListener {
        SimplePrintableObject adaptee;

        private SimplePrintableObject_btnPrint_actionAdapter(SimplePrintableObject adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.btnPrint_actionPerformed(e);
        }
    }

    private class SimplePrintableObject_btnCancel_actionAdapter implements java.awt.event.ActionListener {
        SimplePrintableObject adaptee;

        private SimplePrintableObject_btnCancel_actionAdapter(SimplePrintableObject adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.btnCancel_actionPerformed(e);
        }
    }
}
