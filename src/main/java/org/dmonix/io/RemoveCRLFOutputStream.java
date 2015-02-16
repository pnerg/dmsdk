package org.dmonix.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A filter outputstream that removes all line feed (#10) and carriage return (#13) signs. The output will become a single line datastream.
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
public class RemoveCRLFOutputStream extends AbstractFilterOutputStream {
    public RemoveCRLFOutputStream(OutputStream out) {
        super(out);
    }

    /**
     * Writes a single byte value. If the data equals either 10 (line feed) or 13 (carriage return), the method returns without writing anything.
     * 
     * @param b
     *            The data
     * @throws IOException
     */
    public void write(int b) throws IOException {
        if (b == 10 || b == 13)
            return;

        super.write(b);
    }
}