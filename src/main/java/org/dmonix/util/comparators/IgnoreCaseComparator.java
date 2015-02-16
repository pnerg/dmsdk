package org.dmonix.util.comparators;

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
 * @since 2.0
 */
public class IgnoreCaseComparator<T> extends BaseComparator<T> {
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
     * If the comparator is set to character insensitive the comparison will use <code>toLower()</code> on the strings.
     * 
     * @param o1
     *            Object one
     * @param o2
     *            Object two
     * @return The result
     */
    public int compare(T o1, T o2) {
        String s1, s2;
        if (ignoreCase) {
            s1 = o1.toString().toLowerCase();
            s2 = o2.toString().toLowerCase();
        } else {
            s1 = o1.toString();
            s2 = o2.toString();
        }

        if (sortAscending)
            return s1.compareTo(s2);
        else
            return s2.compareTo(s1);
    }

}