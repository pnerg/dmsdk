package org.dmonix.util;

/**
 * Title: Description: Copyright: Copyright (c) Company:
 * 
 * @author Peter Nerg
 * @since 1.0
 */

public abstract class StringHandler {

    /**
     * Removes all white space from a string
     * 
     * @param text
     *            The string to remove the white space from
     * @return String
     */
    public static String trim(String text) {
        text = text.trim();
        byte[] byteArray = text.getBytes();

        for (int i = 0; i < byteArray.length; i++) {
            if (byteArray[i] == 32) {
                byteArray = ArrayHandler.removeElementAt(byteArray, i);
                i--;
            }
        }
        return new String(byteArray);
    }
}