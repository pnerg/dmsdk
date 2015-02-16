package org.dmonix.util;

import java.util.regex.Pattern;

/**
 * Utility class for performing regular expression validations.
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.1
 */
public abstract class RegExpUtil {

    /** Pattern that only allows the characters [a-z] and [A-Z]. */
    public static final String ONLY_CHARACTERS_PATTERN = "[a-zA-z]*";

    /** Pattern that only allows numbers [0-9]. */
    public static final String ONLY_NUMBERS_PATTERN = "[\\d]*";

    /** Pattern for validating ip-adress formats. */
    public static final String VALID_IP_PATTERN = "\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";

    /**
     * A predefined pattern to check that a string doesn't contain any XML invalid characters &amp; , &lt; , &gt; , &apos; or &quot;
     */
    public static final String XML_INVALID_CHARS_PATTERN = "[^�&><'\"]*";

    /**
     * Validates the string towards the provided pattern
     * 
     * @param pattern
     * @param text
     */
    public static boolean validateRegEx(String pattern, String text) {
        return validateRegEx(Pattern.compile(pattern), text);
    }

    /**
     * Validates the string towards the provided pattern
     * 
     * @param pattern
     * @param text
     */
    public static boolean validateRegEx(Pattern pattern, String text) {
        return pattern.matcher(text).matches();
    }
}
