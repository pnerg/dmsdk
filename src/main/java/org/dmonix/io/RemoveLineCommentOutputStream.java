package org.dmonix.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Removes all Java style line comments "//" from the outputstream. <br>
 * All data after a found line comment is ignored until a line feed or or carriage return is found. <br>
 * In practice it removes the rest of the line from where the comment was found.
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
public class RemoveLineCommentOutputStream extends FilterOutputStream {
    private boolean foundComment = false;
    private int[] databuffer = new int[3];

    /** Not a valid piece of data [-666] */
    private static final int NOT_VALID_DATA = -666;

    /** The character '/' [47]. */
    private int CHAR_SLASH = (int) '/';

    public RemoveLineCommentOutputStream(OutputStream out) {
        super(out);
        for (int i = 0; i < databuffer.length; i++) {
            databuffer[i] = NOT_VALID_DATA;
        }
    }

    public void close() throws IOException {
        for (int i = 1; i < databuffer.length; i++) {
            super.write(databuffer[i]);
        }

        super.flush();
        super.close();
    }

    /**
     * Writes an array of bytes. <br>
     * Invoking this method equals <br>
     * <code>write(b, 0, b.length);</code>
     * 
     * @param b
     *            The data
     * @throws IOException
     */
    public void write(byte[] b) throws IOException {
        this.write(b, 0, b.length);
    }

    /**
     * Writes a single byte value. <br>
     * If the method detects two adjacent '/' signs all data up until the next line feed or carriage return is ignored, including the two '/' signs.
     * 
     * @param b
     *            The data
     * @throws IOException
     */
    public void write(int b) throws IOException {
        for (int i = 1; i < databuffer.length; i++) {
            databuffer[i - 1] = databuffer[i];
        }

        databuffer[databuffer.length - 1] = b;

        if (databuffer[0] == NOT_VALID_DATA)
            return;

        if (databuffer[0] == CHAR_SLASH && databuffer[1] == CHAR_SLASH) {
            foundComment = true;
            return;
        }

        if (foundComment && (databuffer[0] == 10 || databuffer[0] == 13)) {
            foundComment = false;
            return;
        } else if (foundComment)
            return;

        super.write(databuffer[0]);
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
        for (int i = 0; i < len; i++) {
            this.write((int) b[off + i]);
        }
    }

}