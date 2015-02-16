package org.dmonix.gui.models;

import org.dmonix.AbstractTestCase;
import org.dmonix.cipher.SerializableTestBean;

public class TestSortableListModel extends AbstractTestCase {

    public void testIteration() {
    }

    public void testSortCharSensitive() {

        SortableListModel model = new SortableListModel(false);
        createModel(model);

        assertEquals("A", ((SerializableTestBean) model.getElementAt(0)).getName());
        assertEquals("A", ((SerializableTestBean) model.getElementAt(1)).getName());
        assertEquals("C", ((SerializableTestBean) model.getElementAt(2)).getName());
        assertEquals("D", ((SerializableTestBean) model.getElementAt(3)).getName());
        assertEquals("R", ((SerializableTestBean) model.getElementAt(4)).getName());
        assertEquals("a", ((SerializableTestBean) model.getElementAt(5)).getName());
        assertEquals("b", ((SerializableTestBean) model.getElementAt(6)).getName());
    }

    public void testSortCharInsensitive() {

        SortableListModel model = new SortableListModel(true);
        createModel(model);

        assertEquals("A", ((SerializableTestBean) model.getElementAt(0)).getName());
        assertEquals("a", ((SerializableTestBean) model.getElementAt(1)).getName());
        assertEquals("A", ((SerializableTestBean) model.getElementAt(2)).getName());
        assertEquals("b", ((SerializableTestBean) model.getElementAt(3)).getName());
        assertEquals("C", ((SerializableTestBean) model.getElementAt(4)).getName());
        assertEquals("D", ((SerializableTestBean) model.getElementAt(5)).getName());
        assertEquals("R", ((SerializableTestBean) model.getElementAt(6)).getName());
    }

    private void createModel(SortableListModel model) {
        model.addElement(new SerializableTestBean("A"));
        model.addElement(new SerializableTestBean("R"));
        model.addElement(new SerializableTestBean("C"));
        model.addElement(new SerializableTestBean("a"));
        model.addElement(new SerializableTestBean("b"));
        model.addElement(new SerializableTestBean("A"));
        model.addElement(new SerializableTestBean("D"));
        model.sort();
    }
}
