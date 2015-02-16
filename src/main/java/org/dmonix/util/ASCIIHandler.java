package org.dmonix.util;

/**
 * Utility for converting ASCII values to various number formats and vice versa. Copyright: 2001 Copyright (c) Company: dmonix.org
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public abstract class ASCIIHandler {

    /**
     * Convert a ASCII string to a binary string.
     * 
     * @param text
     *            The string to convert
     * @return String
     */
    public static String asciiToBin(String text) {
        byte[] byteArray = text.getBytes();
        String binString = "";

        for (int i = 0; i < byteArray.length; i++) {
            binString += ClassUtil.padString(Integer.toBinaryString(byteArray[i]), 8);
        }

        return binString;
    }

    /**
     * Convert a ASCII string to a decimal string.
     * 
     * @param text
     *            The string to convert
     * @return String
     */
    public static String asciiToDec(String text) {
        byte[] byteArray = text.getBytes();
        String decString = "";

        for (int i = 0; i < byteArray.length; i++) {
            decString += ClassUtil.padString(Integer.toString(byteArray[i]), 3);
        }

        return decString;
    }

    /**
     * Convert a ASCII string to an hex string.
     * 
     * @param text
     *            The string to convert
     * @return String
     */
    public static String asciiToHex(String text) {
        byte[] byteArray = text.getBytes();
        String hexString = "";

        for (int i = 0; i < byteArray.length; i++) {
            hexString += ClassUtil.padString(Integer.toHexString(byteArray[i]), 2);
        }

        return hexString.toUpperCase();
    }

    /**
     * Convert a ASCII string to an octal string.
     * 
     * @param text
     *            The string to convert
     * @return String
     */
    public static String asciiToOct(String text) {
        byte[] byteArray = text.getBytes();
        String octString = "";

        for (int i = 0; i < byteArray.length; i++) {
            octString += ClassUtil.padString(Integer.toOctalString(byteArray[i]), 3);
        }

        return octString;
    }

    /**
     * Convert a bin string to ASCII.
     * 
     * @param value
     *            The string to convert
     * @return String
     */
    public static String binToAscii(String value) {
        return convertBase(value, 8, 2);
    }

    /**
     * Convert a dec string to ASCII
     * 
     * @param value
     *            The string to convert
     * @return String
     */
    public static String decToAscii(String value) {
        return convertBase(value, 3, 10);
    }

    /**
     * Convert a hex string to ASCII.
     * 
     * @param value
     *            The string to convert
     * @return String
     */
    public static String hexToAscii(String value) {
        return convertBase(value, 2, 16);
    }

    /**
     * Convert an oct string to ASCII
     * 
     * @param value
     *            The string to convert
     * @return String
     */
    public static String octToAscii(String value) {
        return convertBase(value, 3, 8);
    }

    private static String convertBase(String s, int stringLength, int base) {
        // pad with zeroes
        if (s.length() % stringLength != 0) {
            int counter = stringLength - (s.length() % stringLength);
            for (int i = 0; i < counter; i++) {
                s = "0" + s;
            }
        }

        int j = 0;
        byte[] bytes = new byte[s.length() / stringLength];

        for (int i = 0; i < s.length() / stringLength; i++) {
            bytes[i] = (Integer.valueOf(Integer.parseInt(s.substring(j, j + stringLength), base))).byteValue();
            j += stringLength;
        }

        return new String(bytes);
    }
}