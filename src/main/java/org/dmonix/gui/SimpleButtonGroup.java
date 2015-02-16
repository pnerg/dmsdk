package org.dmonix.gui;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

/**
 * Extends the standard button group with additional methods.
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
public class SimpleButtonGroup extends ButtonGroup {
    private static final long serialVersionUID = 7526472295622776147L;

    /**
     * Returns the currently selected button in the group.
     * 
     * @return The selected button, null if none
     */
    public AbstractButton getSelectedButton() {
        Enumeration enumeration = super.getElements();

        AbstractButton btn;
        while (enumeration.hasMoreElements()) {
            btn = (AbstractButton) enumeration.nextElement();
            if (btn.isSelected())
                return btn;
        }

        return null;
    }

    public void setSelectionByValue(String value) {
        if (value == null)
            return;

        Enumeration enumeration = super.getElements();

        SimpleRadioButtonMenuItem btn;
        while (enumeration.hasMoreElements()) {
            btn = (SimpleRadioButtonMenuItem) enumeration.nextElement();
            if (btn.getValue().equals(value)) {
                btn.setSelected(true);
                return;
            }
        }
    }
}