package org.dmonix.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This is a void outputstream that doesn't write anything. The result is the same as to write to <code>/dev/null/</code> on Solaris/Linux, i.e. all data is
 * written to void.
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
public class NullOutputStream extends OutputStream {

    /**
     * Does nothing.
     * 
     * @param b
     *            The data
     * @throws IOException
     */
    public void write(int b) throws IOException {
    }

    /**
     * Does nothing.
     * 
     * @param b
     *            The data
     * @throws IOException
     */
    public void write(byte[] b) throws IOException {
    }

    /**
     * Does nothing.
     * 
     * @throws IOException
     */
    public void flush() throws IOException {
    }

    /**
     * Does nothing. No checks will be made that the either the offset or the length is within the size of the input data array.
     * 
     * @param b
     *            The data
     * @param offset
     *            The offset in the array
     * @param length
     *            The amount of data to write
     * @throws IOException
     */
    public void write(byte[] b, int offset, int length) throws IOException {
    }

}