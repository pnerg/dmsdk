package org.dmonix.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JFrame;

/**
 * Base class for action handlers.
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
public class StandardAction extends AbstractAction {
    private static final long serialVersionUID = 7526472295622776147L;

    protected JFrame owner;

    public StandardAction() {
    }

    public StandardAction(String name) {
        super(name);
    }

    public StandardAction(String name, Icon icon) {
        super(name, icon);
    }

    public StandardAction(String name, Icon icon, JFrame owner) {
        super(name, icon);
        this.owner = owner;
    }

    public void actionPerformed(ActionEvent e) {
        /** @todo Implement this java.awt.event.ActionListener abstract method */
    }
}