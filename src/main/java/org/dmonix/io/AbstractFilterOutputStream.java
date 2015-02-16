package org.dmonix.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * The base class for all filter output streams.
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 2.0
 */
public abstract class AbstractFilterOutputStream extends FilterOutputStream {

    protected AbstractFilterOutputStream(OutputStream ostream) {
        super(ostream);
    }

    /**
     * Writes an array of bytes. Invoking this method equals <br>
     * <code>write(b, 0, b.length);</code>
     * 
     * @param b
     *            The data
     * @throws IOException
     */
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    /**
     * Writes a single byte value.
     * 
     * @param b
     *            The data
     * @throws IOException
     */
    public void write(int b) throws IOException {
        super.write(b);
    }

    /**
     * Writes an array of bytes. <br>
     * The method will invoke <code>write(int b)</code> for each byte in the array that is to be written.
     * 
     * @param b
     *            The data
     * @param off
     *            The position to start in the array
     * @param len
     *            The amount of data to use from the array
     * @throws IOException
     */
    public void write(byte[] b, int off, int len) throws IOException {
        if (off < 0 || len < 0)
            throw new IllegalArgumentException("Offset " + off + " and length " + len + " may not be a negative value");

        if (off > b.length || len > b.length || off + len > b.length)
            throw new IllegalArgumentException("The offset " + off + " and/or the amount of data to read" + len
                    + " bytes is greater than the length of the data " + b.length);

        for (int i = 0; i < len; i++) {
            write((int) b[off + i]);
        }
    }
}
