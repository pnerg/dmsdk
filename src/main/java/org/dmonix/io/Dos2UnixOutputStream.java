package org.dmonix.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A filter outputstream that converts Windows formatted line breaks (#13#10 , \r\n) to Unix/Linux formatted line breaks, i.e. removing all carriage return (#13
 * , \r) signs.
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
public class Dos2UnixOutputStream extends AbstractFilterOutputStream {
    public Dos2UnixOutputStream(OutputStream out) {
        super(out);
    }

    /**
     * Writes a single byte value. <br>
     * If the data equals 13 (carriage return), the method returns without writing anything.
     * 
     * @param b
     *            The data
     * @throws IOException
     */
    public void write(int b) throws IOException {
        if (b == 13)
            return;

        super.write(b);
    }
}