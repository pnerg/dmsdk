package org.dmonix.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implements a list of XMLElements. The list is built upon an unsynchronized list type, i.e. it is not thread safe.
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public class XMLElementList implements Iterable<XMLElement> {

    private List<XMLElement> list = new ArrayList<XMLElement>();

    /**
     * Appends the specified element to the end of this list. <code>null</code> values are not allowed.
     * 
     * @param e
     *            The element to be appended to this list.
     * @throws IllegalArgumentException
     *             if a <code>null</null> value is passed
     */
    public void add(XMLElement e) {
        if (e == null)
            throw new IllegalArgumentException("Null values are not allowed");

        this.list.add(e);
    }

    /**
     * Returns the XMLElement at the specified position in this list.
     * 
     * @param index
     *            index of element to return.
     * @return the XMLElement at the specified position in this list.
     * 
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (index &lt; 0 || index &gt;= size()).
     */
    public XMLElement get(int index) {
        return this.list.get(index);
    }

    /**
     * Returns the number of elements in this list. If this list contains more than <tt>Integer.MAX_VALUE</tt> elements, returns <tt>Integer.MAX_VALUE</tt>.
     * 
     * @return the number of elements in this list.
     */
    public int size() {
        return this.list.size();
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * 
     * @return an iterator over the elements in this list in proper sequence.
     */
    public XMLElementListIterator iterator() {
        return new XMLElementListIterator();
    }

    /**
     * The iterator for the XMLElementList.
     * <p>
     * Copyright: Copyright (c) 2006
     * </p>
     * <p>
     * Company: dmonix.org
     * </p>
     * 
     * @author Peter Nerg
     * @since 1.0
     */
    public class XMLElementListIterator implements Iterator<XMLElement> {

        private Iterator<XMLElement> iterator = list.iterator();

        private XMLElementListIterator() {
        };

        /**
         * Returns <tt>true</tt> if the iteration has more elements. (In other words, returns <tt>true</tt> if <tt>next</tt> would return an element rather than
         * throwing an exception.)
         * 
         * @return <tt>true</tt> if the iterator has more elements.
         */
        public boolean hasNext() {
            return iterator.hasNext();
        }

        /**
         * Returns the next element in the iteration. Calling this method repeatedly until the {@link #hasNext()} method returns false will return each element
         * in the underlying collection exactly once.
         * 
         * @return the next element in the iteration.
         * @exception NoSuchElementException
         *                iteration has no more elements.
         */
        public XMLElement next() {
            return iterator.next();
        }

        /**
         * THIS METHOD IS NOT SUPPORTED.
         * 
         * @throws Always
         *             throws UnsupportedOperationException
         */
        public void remove() {
            throw new UnsupportedOperationException("This method [remove()] is not supported.");
        }

    }
}
