package org.dmonix.io.filters;

/**
 * Filters out all files with the requested extension(s). <br>
 * The class uses regular expressions to sort out the required files. <br>
 * The regular expression used for a single extension filter is: <br>
 * <code>.*\\x2Eextension</code> <br>
 * e.g.<code>.*\\x2Ejava</code> <br>
 * when multiple extensions are used the filter looks like this: <br>
 * <code>.*\\x2Eextension1||.*\\x2Eextension2||...||.*\\x2Eextensionx</code> <br>
 * e.g.<code>.*\\x2Ejpg||.*\\x2Egif||.*\\x2Ebmp</code> <br>
 * By default the matcher is case sensitive, i.e. the extension <code>jpg</code> and <code>JPG</code> are not the same.
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
public class FileExtensionFilter extends RegExpFilter {
    // hex 2E is '.'
    private static final String REGEX_DOT = ".*\\x2E";

    /**
     * Creates a filter that accepts a single extension. <br>
     * The extension is provided without punctuation, i.e. <code>java</code> will filter on all files with the extension <code>.java</code>.
     * 
     * @param extension
     *            The extension to filter on
     */
    public FileExtensionFilter(String extension) {
        super(REGEX_DOT + extension, true);
    }

    /**
     * Creates a filter that accepts a single extension. <br>
     * The extension is provided without punctuation, i.e. <code>java</code> will filter on all files with the extension <code>.java</code>.
     * 
     * @param extension
     *            The extension to filter on
     * @param caseSensitive
     *            If the matcher shall be case sensitive or not
     */
    public FileExtensionFilter(String extension, boolean caseSensitive) {
        super(REGEX_DOT + extension, caseSensitive);
    }

    /**
     * Creates a filter that accepts a single extension. <br>
     * The extension is provided without punctuation, i.e. <code>java</code> will filter on all files with the extension <code>.java</code>.
     * 
     * @param extension
     *            The extension to filter on
     * @param description
     *            The description of the filter
     */
    public FileExtensionFilter(String extension, String description) {
        super(REGEX_DOT + extension);
        super.setDescription(description);
    }

    /**
     * Creates a filter that accepts a single extension. <br>
     * The extension is provided without punctuation, i.e. <code>java</code> will filter on all files with the extension <code>.java</code>.
     * 
     * @param extension
     *            The extension to filter on
     * @param description
     *            The description of the filter
     * @param caseSensitive
     *            If the matcher shall be case sensitive or not
     */
    public FileExtensionFilter(String extension, String description, boolean caseSensitive) {
        super(REGEX_DOT + extension, caseSensitive);
        super.setDescription(description);
    }

    /**
     * Creates a filter that accepts a list of extensions. <br>
     * The extensions are provided without punctuation, i.e. <code>java,txt</code> will filter on all files with the extensions <code>.java</code> or
     * <code>.txt</code>.
     * 
     * @param extensions
     *            The array with extensions to filter on
     */
    public FileExtensionFilter(String[] extensions) {
        this(extensions, true);
    }

    /**
     * Creates a filter that accepts a list of extensions. <br>
     * The extensions are provided without punctuation, i.e. <code>java,txt</code> will filter on all files with the extensions <code>.java</code> or
     * <code>.txt</code>.
     * 
     * @param extensions
     *            The array with extensions to filter on
     * @param caseSensitive
     *            If the matcher shall be case sensitive or not
     */
    public FileExtensionFilter(String[] extensions, boolean caseSensitive) {
        super();
        StringBuffer pattern = new StringBuffer();
        for (int i = 0; i < extensions.length - 1; i++) {
            pattern.append(REGEX_DOT);
            pattern.append(extensions[i]);
            pattern.append("||");
        }
        pattern.append(REGEX_DOT);
        pattern.append(extensions[extensions.length - 1]);

        super.setPattern(pattern.toString(), caseSensitive);
    }

}