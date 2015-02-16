package org.dmonix.gui.frames;

import java.awt.*;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

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
public class HelpFrame extends JFrame {
    private static final long serialVersionUID = 7526472295622776147L;

    private JPanel panelText = new JPanel();
    private JPanel panelTree = new JPanel();
    private GridBagLayout gridBagLayoutTree = new GridBagLayout();

    private JScrollPane scrollPaneTree = new JScrollPane();
    private JScrollPane scrollPaneTextPanel = new JScrollPane();

    private JTree tree = new JTree();
    private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Help");
    private DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
    private JSplitPane splitPane = new JSplitPane();

    public HelpFrame() throws HeadlessException {
        try {
            super.setIconImage(new ImageIcon(HelpFrame.class.getResource("/org/dmonix/gui/img/Help.gif")).getImage());
            jbInit();
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNode(String name, JPanel panel) {
        HelpTreeNode node = new HelpTreeNode(name, panel);
        this.rootNode.add(node);
    }

    public void finish() {
        this.tree.expandPath(new TreePath(this.rootNode.getPath()));
        this.setVisible(true);
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(new BorderLayout());
        tree.setModel(treeModel);

        this.setTitle("");
        this.addWindowListener(new HelpFrame_this_windowAdapter(this));
        panelText.setBackground(Color.white);
        panelTree.setBackground(Color.white);
        scrollPaneTextPanel.getViewport().setBackground(Color.white);
        scrollPaneTree.getViewport().add(tree);
        panelTree.setLayout(gridBagLayoutTree);
        scrollPaneTextPanel.getViewport().add(panelText);

        panelTree.add(scrollPaneTree, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 5), 0, 0));

        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(5);
        splitPane.setLeftComponent(scrollPaneTree);
        splitPane.setRightComponent(scrollPaneTextPanel);
        splitPane.setLastDividerLocation(150);
        splitPane.setDividerLocation(150);

        this.getContentPane().add(splitPane, BorderLayout.CENTER);

        tree.addTreeSelectionListener(new HelpFrame_tree_treeSelectionAdapter(this));
    }

    private void init() {
        this.setSize(600, 600);
        this.setResizable(true);
        this.setTitle("Help");
    }

    void tree_valueChanged(TreeSelectionEvent e) {
        Object obj = e.getPath().getLastPathComponent();

        if (obj == null || !(obj instanceof HelpTreeNode))
            return;

        this.scrollPaneTextPanel.getViewport().add(((HelpTreeNode) obj).panel);
    }

    public static void main(String[] args) throws HeadlessException {
        HelpFrame helpFrame = new HelpFrame();
        helpFrame.addNode("Timex", new JPanel());
        helpFrame.addNode("Timex Reader", new JPanel());
        helpFrame.addNode("Timex Editor", new JPanel());
        helpFrame.addNode("FAQ", new JPanel());
        helpFrame.finish();
    }

    void this_windowClosing(WindowEvent e) {
        super.dispose();
    }

    private class HelpTreeNode extends DefaultMutableTreeNode {
        private static final long serialVersionUID = 7526472295622776147L;

        String text;
        JPanel panel;

        HelpTreeNode(String text, JPanel panel) {
            this.text = text;
            this.panel = panel;
        }

        public String toString() {
            return this.text;
        }
    }

    private class HelpFrame_tree_treeSelectionAdapter implements javax.swing.event.TreeSelectionListener {
        HelpFrame adaptee;

        HelpFrame_tree_treeSelectionAdapter(HelpFrame adaptee) {
            this.adaptee = adaptee;
        }

        public void valueChanged(TreeSelectionEvent e) {
            adaptee.tree_valueChanged(e);
        }
    }

    private class HelpFrame_this_windowAdapter extends java.awt.event.WindowAdapter {
        HelpFrame adaptee;

        HelpFrame_this_windowAdapter(HelpFrame adaptee) {
            this.adaptee = adaptee;
        }

        public void windowClosing(WindowEvent e) {
            adaptee.this_windowClosing(e);
        }
    }
}