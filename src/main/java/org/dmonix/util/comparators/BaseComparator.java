package org.dmonix.util.comparators;

import java.util.Comparator;

/**
 * Basic comparator that can be set to sort either in ASCENDING or DESCENDING order.
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 2.0
 */
public class BaseComparator<T> implements Comparator<T> {
    protected boolean sortAscending = true;

    /**
     * Default constructor, will sort in ASCENDING order.
     */
    public BaseComparator() {
        this.sortAscending = true;
    }

    /**
     * Creates a comparator that sorts in the requested order.
     * 
     * @param b
     *            true - ASCENDING, false - DESCENDING
     */
    public BaseComparator(boolean b) {
        this.sortAscending = b;
    }

    /**
     * Compares the <code>toString</code> representation of two objects. <br>
     * The comparison uses either <code>o1.toString().compareTo(o2.toString())</code> or <code>o2.toString().compareTo(o1.toString())</code> depending of if
     * this comparator sorts in ASCENDING or DESCENDING order.
     * 
     * @param o1
     *            Object one
     * @param o2
     *            Object two
     * @return The result
     */
    public int compare(T o1, T o2) {
        if (sortAscending)
            return o1.toString().compareTo(o2.toString());
        else
            return o2.toString().compareTo(o1.toString());
    }

    /**
     * Sets if this comparator is to sort ASCENDING or DESCENDING. <br>
     * Default value is true - ASCENDING
     * 
     * @param b
     *            true - ASCENDING, false - DESCENDING
     */
    public void setSortAscending(boolean b) {
        this.sortAscending = b;
    }
}