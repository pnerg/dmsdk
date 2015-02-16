package org.dmonix.gui.models;

import java.util.*;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.dmonix.util.comparators.IgnoreCaseComparator;

/**
 * This is a basic non-sorted list model with the added possibility to sort the model at any time. <br>
 * This class is well suited for large amounts of data since it uses a very fast sorting algorithm.
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
public class SortableListModel implements ListModel, Iterable {

    /** The data in the model. */
    protected Vector<Comparable> data = new Vector<Comparable>();

    /** */
    protected List<ListDataListener> listeners = new Vector<ListDataListener>();

    /**
     * Create a default comparator that always performs the compareTo method on the two objects
     */
    private Comparator<Comparable> comparator = new Comparator<Comparable>() {
        @SuppressWarnings("unchecked")
        public int compare(Comparable o1, Comparable o2) {
            return o1.compareTo(o2);
        }
    };

    /**
     * Creates a basic character case sensitive list model. This is equivalent to <code>SortableListModel(false)</code>
     */
    public SortableListModel() {
        this(false);
    }

    /**
     * Creates a basic character case (in)sensitive list model. <br>
     * The value of the argument decides whether the list model should be character case sensitive or not. <br>
     * The model will use the <code>IgnoreCaseComparator</code>.
     * 
     * @param ignoreCase
     *            character sensitive or not
     * @see org.dmonix.util.comparators.IgnoreCaseComparator
     * 
     */
    @SuppressWarnings("unchecked")
    public SortableListModel(boolean ignoreCase) {
        this(new IgnoreCaseComparator<Comparable>(ignoreCase));
    }

    /**
     * Creates a list model with custom comparator.
     * 
     * @param comparator
     *            The comparator
     */
    public SortableListModel(Comparator<Comparable> comparator) {
        this.comparator = comparator;
    }

    /**
     * Adds a listener to the list that's notified each time a change to the data model occurs.
     * 
     * @param l
     *            The listener to add
     */
    public void addListDataListener(ListDataListener l) {
        this.listeners.add(l);
    }

    /**
     * Removes a listener from the list that's notified each time a change to the data model occurs.
     * 
     * @param l
     *            The listener to remove
     */
    public void removeListDataListener(ListDataListener l) {
        this.listeners.remove(l);
    }

    /**
     * Adds the specified component to the end of this list. <br>
     * The object must implement <code>java.lang.Comparable</code> <br>
     * The list model can be sorted by invoking the <code>sort</code> method.
     * 
     * @param o
     *            The object to add
     * @throws IllegalArgumentException
     *             If the provided object doesn't implement <code>java.lang.Comparable</code>
     */
    public void addElement(Comparable o) {
        data.add(o);
        this.fireEvent(ListDataEvent.INTERVAL_ADDED, data.size(), data.size());
    }

    /**
     * Removes all of the elements from this list.
     */
    public void clear() {
        int size = data.size();
        data.clear();
        this.fireEvent(ListDataEvent.INTERVAL_REMOVED, 0, size);
    }

    /**
     * List the data as an enum.
     * 
     * @return The data as an enum
     */
    public Enumeration elements() {
        return this.data.elements();
    }

    /**
     * Increases the capacity of this list. If necessary, to ensure that it can hold at least the number of components specified by the minimum capacity
     * argument.
     * 
     * @param minCapacity
     *            The minimum capacity
     */
    public void ensureCapacity(int minCapacity) {
        data.ensureCapacity(minCapacity);
    }

    /**
     * Returns the component at the specified index.
     * 
     * @param index
     *            The index
     * @return The object
     */
    public Object getElementAt(int index) {
        return data.get(index);
    }

    /**
     * Returns the number of components in this list.
     * 
     * @return The number of items in the model
     */
    public int getSize() {
        return data.size();
    }

    /**
     * Removes the first (lowest-indexed) occurrence of the argument from this list.
     * 
     * @param obj
     *            The object to remove
     * @return true - removed, false - not removed/or not found
     */
    public boolean removeElement(Object obj) {
        int index = data.indexOf(obj);
        boolean b = data.removeElement(obj);

        if (b) {
            this.fireEvent(ListDataEvent.INTERVAL_REMOVED, index, index);
        }

        return b;
    }

    /**
     * Deletes the object at the specified index.
     * 
     * @param index
     *            The index
     * @return Returns the deleted object
     */
    public Object removeElementAt(int index) {
        Object o = getElementAt(index);
        data.removeElementAt(index);
        this.fireEvent(ListDataEvent.INTERVAL_REMOVED, index, index);
        return o;
    }

    /**
     * Set the <code>Comparator</code> used in the <code>sort</code> method.
     * 
     * @param comparator
     *            The comparator
     */
    public void setComparator(Comparator<Comparable> comparator) {
        this.comparator = comparator;
    }

    /**
     * Sorts the specified list using the specified comparator. <br>
     * Please refer to <code>java.util.Collections.sort</code> for further information.
     */
    public void sort() {
        Collections.sort(data, this.comparator);
        this.fireEvent(ListDataEvent.CONTENTS_CHANGED, 0, data.size());
    }

    /**
     * Returns an array representation of this list model.
     * 
     * @return The array
     */
    public Object[] toArray() {
        return this.data.toArray();
    }

    /**
     * Copies the components of this model into the specified array. <br>
     * The item at index k in this model is copied into component k of the array. <br>
     * The array must be big enough to hold all the objects in this vector, else an IndexOutOfBoundsException is thrown.
     * 
     * @param array
     *            The array to store the data in
     */
    public void toArray(Object[] array) {
        data.trimToSize();
        data.copyInto(array);
    }

    /**
     * Trims the data container.
     */
    public void trimToSize() {
        data.trimToSize();
    }

    /**
     * Fires a specific event. <br>
     * Each registered listener will be notified of the event.
     * 
     * @param event
     *            The id of the event to fire
     * @param startIndex
     *            The starting index of the concerned objects
     * @param endIndex
     *            The end index of the concerned objects
     */
    protected void fireEvent(int event, int startIndex, int endIndex) {
        ListDataEvent lde = new ListDataEvent(this, event, startIndex, endIndex);
        for (ListDataListener listener : this.listeners) {
            listener.contentsChanged(lde);
        }
    }

    /**
     * @since 2.0
     */
    public Iterator<Comparable> iterator() {
        return this.data.iterator();
    }
}
