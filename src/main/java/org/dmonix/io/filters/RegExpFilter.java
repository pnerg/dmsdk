package org.dmonix.io.filters;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 * A file filter that uses regular expression matching. The filter compares the absolute path name of the file towards the provided regular expression. <br>
 * As well as implementing the <code>FileFilter</code> and <code>FilenameFilter</code> interfaces the class also extends the
 * <code>javax.swing.filechooser.FileFilter</code> class. <br>
 * This way the filter can be used in all standard file filtering as well as in conjunction with the <code>JFileChooser</code> class.
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
public class RegExpFilter extends javax.swing.filechooser.FileFilter implements FileFilter, FilenameFilter {

    /** The default description text. */
    private static final String DESCRIPTION = "A filter that uses the following regular expression pattern:\n";
    private Pattern filter;
    private String description = DESCRIPTION;

    /**
     * Creates a filter using the provided regular expression. <br>
     * By default the mathcer is case sensitive. <br>
     * For more information on how to use regular expressions, refer to <code>java.util.regex.Pattern</code>
     * 
     * @param regex
     *            The regular expression
     * @see java.util.regex.Pattern
     */
    public RegExpFilter(String regex) {
        this.setPattern(Pattern.compile(regex));
    }

    /**
     * Creates a filter using the provided regular expression. <br>
     * For more information on how to use regular expressions, refer to <code>java.util.regex.Pattern</code>
     * 
     * @param regex
     *            The regular expression
     * @param caseSensitive
     *            If the matcher shall be case sensitive or not
     * @see java.util.regex.Pattern
     */
    public RegExpFilter(String regex, boolean caseSensitive) {
        if (caseSensitive)
            this.setPattern(Pattern.compile(regex));
        else
            this.setPattern(Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
    }

    /**
     * Creates a filter using the provided pattern.
     * 
     * @param filter
     *            The pattern
     */
    public RegExpFilter(Pattern filter) {
        this.setPattern(filter);
    }

    protected RegExpFilter() {
    }

    /**
     * The method will match the absolute file path towards the regular expression pattern.
     * 
     * @param dir
     *            The file directory
     * @param name
     *            The file name
     * @return true if match, false otherwise
     */
    public boolean accept(File dir, String name) {
        return filter.matcher(dir.getAbsolutePath() + File.separator + name).matches();
    }

    /**
     * The method will match the absolute file path towards the regular expression pattern.
     * 
     * @param f
     *            The file to match towards
     * @return true if match, false otherwise
     */
    public boolean accept(File f) {
        return filter.matcher(f.getAbsolutePath()).matches();
    }

    /**
     * Returns the description of the currently used pattern. <br>
     * If not set by the user a default text will be displayed.
     * 
     * @return The description
     * @see setDescription(String)
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the filter. <br>
     * The description text is used by the <code>JFileChooser</code>
     * 
     * @param s
     *            The description
     */
    public void setDescription(String s) {
        this.description = s;
    }

    /**
     * Get the pattern that is used by this filter.
     * 
     * @return The pattern
     */
    public Pattern getPattern() {
        return this.filter;
    }

    /**
     * Set the pattern that is to be used by this filter. <br>
     * This will overwrite any previously set pattern. <br>
     * It will also reset the description of the filter.
     * 
     * @param filter
     *            The pattern
     */
    public void setPattern(Pattern filter) {
        this.filter = filter;
        this.description = DESCRIPTION + toString();
    }

    /**
     * Set the pattern that is to be used by this filter. <br>
     * This will overwrite any previously set pattern.
     * 
     * @param regex
     *            The pattern
     */
    public void setPattern(String regex) {
        this.setPattern(regex, true);
    }

    /**
     * Set the pattern that is to be used by this filter. <br>
     * This will overwrite any previously set pattern.
     * 
     * @param regex
     *            The pattern
     * @param caseSensitive
     *            If the matcher shall be case sensitive or not
     */
    public void setPattern(String regex, boolean caseSensitive) {
        if (caseSensitive) {
            this.setPattern(Pattern.compile(regex));
        } else {
            this.setPattern(Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
        }
    }

    /**
     * Returns the string representation of the currently used regular expression pattern.
     * 
     * @return The pattern
     */
    public String toString() {
        return filter.pattern();
    }

}