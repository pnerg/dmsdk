package org.dmonix.gui.comparators;

import java.util.Comparator;

/**
 * Basic comparator that can be set to be character case sensitive or not.
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 * @deprecated use org.dmonix.util.IgnoreCaseComparator
 */
public class IgnoreCaseComparator implements Comparator {
    protected boolean ignoreCase = true;

    /**
     * Creates a case insensitive comparator.
     */
    public IgnoreCaseComparator() {
        this.ignoreCase = true;
    }

    /**
     * Creates a case (in)sensitive comparator.
     * 
     * @param ignoreCase
     *            true - case insensitive, false case sensitive
     */
    public IgnoreCaseComparator(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    /**
     * Sets if this comparator is to be case sensitive or not.
     * 
     * @param b
     *            true - case sensitive, false - case insensitive
     */
    public void setCaseSensitive(boolean b) {
        this.ignoreCase = !b;
    }

    /**
     * Returns if the comparator is case sensitive or not.
     * 
     * @return true - case sensitive, false - case insensitive
     */
    public boolean isCaseSensitive() {
        return !ignoreCase;
    }

    /**
     * Compares the <code>toString</code> representation of two objects. <br>
     * If the comparator is set to character insensitive the comparison will use <code>compareToIgnoreCase()</code> on the strings.
     * 
     * @param o1
     *            Object one
     * @param o2
     *            Object two
     * @return The result
     */
    public int compare(Object o1, Object o2) {
        if (ignoreCase)
            return o1.toString().compareToIgnoreCase(o2.toString());
        else
            return o1.toString().compareTo(o2.toString());
    }

}