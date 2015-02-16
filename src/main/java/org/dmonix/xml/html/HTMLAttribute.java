package org.dmonix.xml.html;

/**
 * Base class for all attribute type classes. The class represents a XML/HMTL attribute consisting of a name and a value.
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
public class HTMLAttribute implements Cloneable, Comparable {

    /** The attribute name is changed to UPPER case. */
    public static final int UPPER_CASE = 1;

    /** The attribute name is changed to LOWER case. */
    public static final int LOWER_CASE = -1;

    /** The attribute name is kept as it was declared. */
    public static final int KEEP_CASE = 0;

    /** The name of the attribute. */
    private String attributeName;

    /** The value of the attribute. */
    private String attributeValue;

    private int useCase = KEEP_CASE;

    /**
     * Creates a HTML attribute object.
     * 
     * @param attributeName
     *            The name of the attribute
     * @param attributeValue
     *            The value of the attribute
     */
    public HTMLAttribute(String attributeName, String attributeValue) {
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    /**
     * Creates a HTML attribute object.
     * 
     * @param attributeName
     *            The name of the attribute
     * @param attributeValue
     *            The value of the attribute
     * @param upperCase
     *            true = upper case, false = lower case
     * @since 1.1
     */
    public HTMLAttribute(String attributeName, String attributeValue, int useCase) {
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
        this.useCase = useCase;
    }

    /**
     * Creates a clone of the current object.
     * 
     * @return The clone
     * @since 1.1
     */
    public final Object clone() {
        return new HTMLAttribute(this.attributeName, this.attributeValue);
    }

    /**
     * Peforms a toString comparison on the two objects
     * 
     * @param o
     *            The object to compare to
     * @return The result
     * @since 1.1
     */
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

    /**
     * Returns the name of the attribute. <br>
     * The way the name is returned depends on what was set with <code>setCase(int)</code>
     * 
     * @return The name
     */
    public String getAttributeName() {
        if (this.useCase <= LOWER_CASE)
            return this.attributeName.toLowerCase();
        else if (this.useCase >= UPPER_CASE)
            return this.attributeName.toUpperCase();
        else
            return this.attributeName;
    }

    /**
     * Returns the value of the attribute.
     * 
     * @return The value
     */
    public String getAttributeValue() {
        return this.attributeValue;
    }

    /**
     * Set the value of the attribute.
     * 
     * @param value
     *            The attribute value
     * @since 1.1
     */
    public void setAttributeValue(String value) {
        this.attributeValue = value;
    }

    /**
     * Returns if all the attribute names are to be in upper case or not.
     * 
     * @return true if attribute names are written in uppercase, false otherwise
     * @since 1.1
     */
    public int getCase() {
        return this.useCase;
    }

    /**
     * Sets if all the attribute names are to be in upper case, lower case or in the format they where declared. <br>
     * This determines if the attribute names written to the document will be in upper or lower case. <br>
     * No browser will care if the name is called <code>href</code> , <code>HREF</code> or <code>HrEf</code>. <br>
     * But an XML parser is case sensitive so if you plan write the output to a file that is to be parsed by something else than a browser it is adbisable to
     * stick to either upper or lower case. <br>
     * Default is KEEP_CASE
     * 
     * @param useCase
     *            How the atttribute is to be displayed
     * @see LOWER_CASE
     * @see UPPER_CASE
     * @see KEEP_CASE
     * @since 1.1
     */
    public void setCase(int useCase) {
        this.useCase = useCase;
    }

    /**
     * Returns the string representation of the attribute object.
     * 
     * @return Returns a string in the format <code>[attributeName]="[attributeValue]"</code>
     */
    public String toString() {
        return getAttributeName() + "=\"" + attributeValue + "\"";
    }

    // =====================================================
    // Attribute classes
    // =====================================================

    /**
     * Represents the <code>CLASS</code> attribute.
     * 
     * @author Peter Nerg
     * @since 1.1
     */
    public static class ClassAttribute extends HTMLAttribute {
        /**
         * Create a <code>CLASS</code> attribute
         * 
         * @param attributeValue
         *            The value of the attribute
         */
        public ClassAttribute(String attributeValue) {
            super("CLASS", attributeValue);
        }
    }

    /**
     * Represents the <code>HREF</code> attribute.
     * 
     * @author Peter Nerg
     * @since 1.1
     */
    public static class HrefAttribute extends HTMLAttribute {
        /**
         * Create a <code>HREF</code> attribute
         * 
         * @param attributeValue
         *            The value of the attribute
         */
        public HrefAttribute(String attributeValue) {
            super("HREF", attributeValue);
        }
    }

    /**
     * Represents the <code>SRC</code> attribute.
     * 
     * @author Peter Nerg
     * @since 1.1
     */
    public static class SrcAttribute extends HTMLAttribute {
        /**
         * Create a <code>SRC</code> attribute
         * 
         * @param attributeValue
         *            The value of the attribute
         */
        public SrcAttribute(String attributeValue) {
            super("SRC", attributeValue);
        }
    }

}
