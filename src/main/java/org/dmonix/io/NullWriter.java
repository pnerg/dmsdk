package org.dmonix.io;

import java.io.IOException;
import java.io.Writer;

/**
 * This Writer does nothing, every method returns without performing anything. The purpose of this class is to use it at places where there is a need to test a
 * method on a class and one of the the arguments is a Writer.
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 2.0
 */
public class NullWriter extends Writer {

    /**
     * Does nothing.
     * 
     * @param b
     *            The data
     * @throws IOException
     */
    public void flush() throws IOException {
    }

    /**
     * Does nothing.
     * 
     * @param b
     *            The data
     * @throws IOException
     */
    public void close() throws IOException {
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
    public void write(char[] cbuf, int off, int len) throws IOException {
    }

    /**
     * Does nothing.
     * 
     * @param b
     *            The data
     * @throws IOException
     */
    public void write(char[] arg0) throws IOException {
    }

    /**
     * Does nothing.
     * 
     * @param b
     *            The data
     * @throws IOException
     */
    public void write(String arg0) throws IOException {
    }

    /**
     * Does nothing.
     * 
     * @param b
     *            The data
     * @throws IOException
     */
    public void write(int arg0) throws IOException {
    }
}
