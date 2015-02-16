package org.dmonix.io.filters;

import java.io.File;
import java.io.FileFilter;

/**
 * A filter for sorting out directories only.
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public class DirectoryFilter implements FileFilter {
    /**
     * The method checks if the input file is a directory or not.
     * 
     * @param f
     *            The file to check
     * @return true if the input file is a directory, false otherwise
     */
    public boolean accept(File f) {
        return f.isDirectory();
    }

}