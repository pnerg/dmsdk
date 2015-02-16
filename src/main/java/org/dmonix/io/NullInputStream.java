package org.dmonix.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * The class simulates the functionality provided by reading from <code>/dev/null/</code>. <br>
 * I.e. the same value (default null, #0) will continously be returned. <br>
 * There will be no end to the data read from this stream.
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
public class NullInputStream extends InputStream {
    private byte value = 0;

    /**
     * Creates a stream that always returns the byte value 0 (null).
     */
    public NullInputStream() {
        this.value = 0;
    }

    /**
     * Creates a stream that always returns the provided byte value.
     * 
     * @param value
     *            The value to use in the read methods
     */
    public NullInputStream(byte value) {
        this.value = value;
    }

    /**
     * Reads the next byte of data from the input stream. <br>
     * The method will always return the same value.
     * 
     * @return The byte value
     * @throws IOException
     */
    public int read() throws IOException {
        return this.value;
    }

    /**
     * Stores the pre defined byte value in the provided array.
     * 
     * @param b
     *            The array to store data in
     * @param offset
     *            The offset to where to start storing data in the array
     * @param length
     *            The number of bytes to store in the array
     * @return The number of bytes in the array, always the same as the length argument
     * @throws IOException
     */
    public int read(byte[] b, int offset, int length) throws IOException {

        if (offset + length > b.length)
            throw new IllegalArgumentException("The sum of the offset [" + offset + "] and the length [" + length
                    + "] cannot exceed the length of the byte array [" + b.length + "]");

        for (int i = offset; i < offset + length; i++) {
            b[i] = this.value;
        }

        return length;
    }

    /**
     * Stores the pre defined byte value in the provided array. <br>
     * This is the same as: <code>read(b, 0, b.length)</code>
     * 
     * @param b
     *            The array to store data in
     * @return The number of bytes in the array, always the same as the length of the array
     * @throws IOException
     */
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

}