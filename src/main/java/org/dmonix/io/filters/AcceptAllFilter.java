package org.dmonix.io.filters;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

/**
 * Simple filter that accepts all files regardless of path and name.
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
public class AcceptAllFilter implements FileFilter, FilenameFilter {
    /**
     * Always returns true.
     * 
     * @param pathname
     *            The file path
     * @return Always true
     */
    public boolean accept(File pathname) {
        return true;
    }

    /**
     * Always returns true.
     * 
     * @param dir
     *            The file directory
     * @param name
     *            The file name
     * @return Always true
     */
    public boolean accept(File dir, String name) {
        return true;
    }

}