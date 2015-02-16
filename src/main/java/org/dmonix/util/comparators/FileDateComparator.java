package org.dmonix.util.comparators;

import java.io.File;

/**
 * Comparator that compares the creation/modification date between two files.
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 2.0
 */
public class FileDateComparator extends BaseComparator<File> {

    /**
     * Compares the <code>lastModified</code> values of two files. <br>
     * The comparison uses either <code>f1.lastModified() - f2.lastModified()</code> or<br>
     * <code>(f1.lastModified() - f2.lastModified())*-1</code> depending of if this comparator sorts in ASCENDING or DESCENDING order.
     * 
     * @param f1
     *            File one
     * @param f2
     *            File two
     * @return The result
     */
    public int compare(File f1, File f2) {
        if (sortAscending)
            return (int) (f1.lastModified() - f2.lastModified());
        else
            return (int) (f1.lastModified() - f2.lastModified()) * -1;
    }
}
