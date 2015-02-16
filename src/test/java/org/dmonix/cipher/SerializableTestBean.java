package org.dmonix.cipher;

import java.io.Serializable;

/**
 * Simple serializable testbean.
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
public class SerializableTestBean implements Serializable, Comparable {

    /** Variable required by the Serializable interface. */
    private static final long serialVersionUID = 1;

    private String name;
    private long time;

    public SerializableTestBean(String name) {
        this(name, System.currentTimeMillis());
    }

    public SerializableTestBean(String name, long time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return this.name;
    }

    /**
     * The class overrides the default <code>equals</code>.
     */
    public boolean equals(Object o) {
        return (o instanceof SerializableTestBean && toString().equals(o.toString()));
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public String toString() {
        return this.name + " : " + this.time;
    }

    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }
}
