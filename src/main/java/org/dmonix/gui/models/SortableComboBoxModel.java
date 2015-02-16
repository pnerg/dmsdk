package org.dmonix.gui.models;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;

/**
 * This is a basic non-sorted combobox list model with the added possibility to sort the model at any time.
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public class SortableComboBoxModel extends SortableListModel implements ComboBoxModel {
    private Object selectedItem = null;

    public SortableComboBoxModel() {
    }

    /**
     * Selects the requested object in this model. <br>
     * The method fires a <code>CONTENTS_CHANGED</code> event.
     * 
     * @param o
     *            The item to select
     */
    public void setSelectedItem(Object o) {
        this.selectedItem = o;
        super.fireEvent(ListDataEvent.CONTENTS_CHANGED, 0, 0);
    }

    /**
     * Returns the currently selected item
     * 
     * @return The selectd item
     */
    public Object getSelectedItem() {
        if (this.selectedItem == null && super.data.size() > 0)
            this.selectedItem = super.data.get(0);

        return this.selectedItem;
    }
}
