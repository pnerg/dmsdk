package org.dmonix.util;

/**
 * Title: ArrayHandler Description: The class provides functions for different operations on arrays Copyright: Copyright (c) Company:
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public class ArrayHandler {
    public static final int ASCENDING = 10;

    public static final int DESCENDING = 100;

    /**
     * Add an item to the end of the supplied array
     * 
     * @param array
     *            The array from which to add the element
     * @param element
     *            The element to add
     * @return byte[]
     */
    public static byte[] addElement(byte[] array, byte element) {
        byte[] array2 = new byte[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            array2[i] = array[i];
        }
        array2[array.length] = element;
        return array2;
    }

    /**
     * Add an item to the end of the supplied array
     * 
     * @param array
     *            The array from which to add the element
     * @param element
     *            The element to add
     * @return int[]
     */
    public static int[] addElement(int[] array, int element) {
        int[] array2 = new int[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            array2[i] = array[i];
        }
        array2[array.length] = element;
        return array2;
    }

    /**
     * Add an item to the end of the supplied array
     * 
     * @param array
     *            The array from which to add the element
     * @param element
     *            The element to add
     * @return String[]
     */
    public static String[] addElement(String[] array, String element) {
        String[] array2 = new String[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            array2[i] = array[i];
        }
        array2[array.length] = element;
        return array2;
    }

    /**
     * Add an item at the specific index of the supplied array
     * 
     * @param array
     *            The array from which to add the element
     * @param element
     *            The element to add
     * @param index
     *            The index from which to add the element
     * @return byte[]
     */
    public static byte[] addElementAt(byte[] array, byte element, int index) {
        if (index < array.length) {
            int size = 0;
            boolean added = false;
            byte[] array2 = new byte[array.length + 1];
            for (int i = 0; i < array.length; i++) {
                if (i == index && added == false) {
                    array2[size] = element;
                    added = true;
                    i--;
                } else {
                    array2[size] = array[i];
                }
                size++;
            }
            return array2;
        }
        return array;
    }

    /**
     * Add an item at the specific index of the supplied array
     * 
     * @param array
     *            The array from which to add the element
     * @param element
     *            The element to add
     * @param index
     *            The index from which to add the element
     * @return int[]
     */
    public static int[] addElementAt(int[] array, int element, int index) {
        if (index < array.length) {
            int size = 0;
            boolean added = false;
            int[] array2 = new int[array.length + 1];
            for (int i = 0; i < array.length; i++) {
                if (i == index && added == false) {
                    array2[size] = element;
                    added = true;
                    i--;
                } else {
                    array2[size] = array[i];
                }
                size++;
            }
            return array2;
        }
        return array;
    }

    /**
     * Add an item at the specific index of the supplied array
     * 
     * @param array
     *            The array from which to add the element
     * @param element
     *            The element to add
     * @param index
     *            The index from which to add the element
     * @return String[]
     */
    public static String[] addElementAt(String[] array, String element, int index) {
        if (index < array.length) {
            int size = 0;
            boolean added = false;
            String[] array2 = new String[array.length + 1];
            for (int i = 0; i < array.length; i++) {
                if (i == index && added == false) {
                    array2[size] = element;
                    added = true;
                    i--;
                } else {
                    array2[size] = array[i];
                }
                size++;
            }
            return array2;
        }
        return array;
    }

    /**
     * Search the array for matching elements
     * 
     * @param array
     *            The array from which to search for the element
     * @param element
     *            The element to search for
     * @return int[]
     */
    public static int[] indexOf(byte[] array, byte element) {
        int[] result = new int[array.length];

        int size = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == element) {
                result[size] = i;
                size++;
            }
        }

        return trimToSize(result, size);
    }

    /**
     * Search the array for matching elements
     * 
     * @param array
     *            The array from which to search for the element
     * @param element
     *            The element to search for
     * @return int[]
     */
    public static int[] indexOf(int[] array, int element) {
        int[] result = new int[array.length];

        int size = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == element) {
                result[size] = i;
                size++;
            }
        }

        return trimToSize(result, size);
    }

    /**
     * Search the array for matching elements
     * 
     * @param array
     *            The array from which to search for the element
     * @param element
     *            The element to search for
     * @return int[]
     */
    public static int[] indexOf(String[] array, String element) {
        int[] result = new int[array.length];

        int size = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(element)) {
                result[size] = i;
                size++;
            }
        }

        return trimToSize(result, size);
    }

    /**
     * Remove all elements matching the supplied element
     * 
     * @param array
     *            The array from which to remove the element(s)
     * @param element
     *            The element to remove from the array
     * @return byte[]
     */
    public static byte[] removeElement(byte[] array, byte element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == element) {
                array = removeElementAt(array, i);
                i--;
            }
        }
        return array;
    }

    /**
     * Remove all elements matching the supplied element
     * 
     * @param array
     *            The array from which to remove the element(s)
     * @param element
     *            The element to remove from the array
     * @return int[]
     */
    public static int[] removeElement(int[] array, int element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == element) {
                array = removeElementAt(array, i);
                i--;
            }
        }
        return array;
    }

    /**
     * Remove all elements matching the supplied element
     * 
     * @param array
     *            The array from which to remove the element(s)
     * @param element
     *            The element to remove from the array
     * @return String[]
     */
    public static String[] removeElement(String[] array, String element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(element)) {
                array = removeElementAt(array, i);
                i--;
            }
        }
        return array;
    }

    /**
     * Remove an element from the supplied array at the specific index
     * 
     * @param array
     *            The array from which to remove the element
     * @param index
     *            The index from which to remove the element
     * @return byte[]
     */
    public static byte[] removeElementAt(byte[] array, int index) {
        if (index < array.length) {
            byte[] array2 = new byte[array.length - 1];

            int size = 0;
            for (int i = 0; i < array.length; i++) {
                if (i != index) {
                    array2[size] = array[i];
                    size++;
                }
            }
            return array2;
        }
        return array;
    }

    /**
     * Remove an element from the supplied array at the specific index
     * 
     * @param array
     *            The array from which to remove the element
     * @param index
     *            The index from which to remove the element
     * @return int[]
     */
    public static int[] removeElementAt(int[] array, int index) {
        if (index < array.length) {
            int[] array2 = new int[array.length - 1];

            int size = 0;
            for (int i = 0; i < array.length; i++) {
                if (i != index) {
                    array2[size] = array[i];
                    size++;
                }
            }
            return array2;
        }
        return array;
    }

    /**
     * Remove an element from the supplied array at the specific index
     * 
     * @param array
     *            The array from which to remove the element
     * @param index
     *            The index from which to remove the element
     * @return String[]
     */
    public static String[] removeElementAt(String[] array, int index) {
        if (index < array.length) {
            String[] array2 = new String[array.length - 1];

            int size = 0;
            for (int i = 0; i < array.length; i++) {
                if (i != index) {
                    array2[size] = array[i];
                    size++;
                }
            }
            return array2;
        }
        return array;
    }

    /**
     * Sorts the array
     * 
     * @param array
     *            The array to sort
     * @param sort
     *            Ascending/descending
     * @return byte[]
     */
    public static byte[] sort(byte[] array, int sort) {
        boolean sorted = false;
        byte tmp;
        while (sorted == false) {
            sorted = true;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i + 1] && sort == ASCENDING || array[i] < array[i + 1] && sort == DESCENDING) {
                    tmp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = tmp;
                    sorted = false;
                }
            }
        }
        return array;
    }

    /**
     * Sorts the array
     * 
     * @param array
     *            The array to sort
     * @param sort
     *            Ascending/descending
     * @return int[]
     */
    public static int[] sort(int[] array, int sort) {
        boolean sorted = false;
        int tmp;
        while (sorted == false) {
            sorted = true;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i + 1] && sort == ASCENDING || array[i] < array[i + 1] && sort == DESCENDING) {
                    tmp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = tmp;
                    sorted = false;
                }
            }
        }
        return array;
    }

    /**
     * Sorts the array
     * 
     * @param array
     *            The array to sort
     * @param sort
     *            Ascending/descending
     * @return String[]
     */
    public static String[] sort(String[] array, int sort) {
        boolean sorted = false;
        String tmp;
        while (sorted == false) {
            sorted = true;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i].compareTo(array[i + 1]) > 0 && sort == ASCENDING || array[i].compareTo(array[i + 1]) < 0 && sort == DESCENDING) {
                    tmp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = tmp;
                    sorted = false;
                }
            }
        }
        return array;
    }

    /**
     * Trims the capacity of this array to be the given size
     * 
     * @param array
     *            The array from which to remove the element
     * @param size
     *            The size to trim to
     * @return byte[]
     */
    public static byte[] trimToSize(byte[] array, int size) {
        if (size < array.length || size < 0) {
            byte[] array2 = new byte[size];
            for (int i = 0; i < size; i++) {
                array2[i] = array[i];
            }
            return array2;
        }
        return array;
    }

    /**
     * Trims the capacity of this array to be the given size
     * 
     * @param array
     *            The array from which to remove the element
     * @param size
     *            The size to trim to
     * @return int[]
     */
    public static int[] trimToSize(int[] array, int size) {
        if (size < array.length || size < 0) {
            int[] array2 = new int[size];
            for (int i = 0; i < size; i++) {
                array2[i] = array[i];
            }
            return array2;
        }
        return array;
    }

    /**
     * Trims the capacity of this array to be the given size
     * 
     * @param array
     *            The array from which to remove the element
     * @param size
     *            The size to trim to
     * @return String[]
     */
    public static String[] trimToSize(String[] array, int size) {
        if (size < array.length || size < 0) {
            String[] array2 = new String[size];
            for (int i = 0; i < size; i++) {
                array2[i] = array[i];
            }
            return array2;
        }
        return array;
    }
}