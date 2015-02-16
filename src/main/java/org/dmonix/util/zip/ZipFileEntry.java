package org.dmonix.util.zip;

import java.util.zip.ZipEntry;
import java.io.*;

/**
 * Extends the <code>java.util.zip.ZipEntry</code> class with additional methods. Copyright: Copyright (c) 2001 Company: dmonix.org
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public class ZipFileEntry extends ZipEntry {
    private final String SEPARATOR = File.separator;

    /**
     * Creates a ZIP entry.
     * 
     * @param name
     *            The name of the entry
     */
    public ZipFileEntry(String name) {
        super(name);
    }

    /**
     * Creates a ZIP entry from an other ZIPEntry.
     * 
     * @param zipEntry
     *            The entry
     */
    public ZipFileEntry(ZipEntry zipEntry) {
        super(zipEntry);
    }

    /**
     * Return the entire path of the file represented by this entry
     * 
     * @return String
     */
    public String getAbsolutePath() {
        return super.getName();
    }

    /**
     * Return the name of the file represented by this entry
     * 
     * @return String
     */
    public String getName() {
        String path = super.getName();
        int index = path.lastIndexOf(SEPARATOR);
        if (index == -1)
            return path;

        return path.substring(index + 1);
    }

    /**
     * Return the parent path to the file represented by this entry
     * 
     * @return The name of the parent, null if there is no parent
     */
    public String getParent() {
        String path = super.getName();

        int index = path.lastIndexOf(SEPARATOR);
        if (index == -1)
            return null;

        return path.substring(0, index + 1);
    }

    /**
     * Return the size of the packaged entry vs. the original file. <br>
     * In case the compressed file is larger than the original file, the method will return 0.
     * 
     * @return 0-100
     */
    public float getRatio() {
        float size = this.getSize();
        float packed = this.getCompressedSize();

        if (packed <= size)
            return (1 - (packed / size)) * 100;
        else
            return 0;
    }

    /**
     * Returns a string representation of the object.
     * 
     * @return The string representation
     */
    public String toString() {
        return super.getName();
    }
}