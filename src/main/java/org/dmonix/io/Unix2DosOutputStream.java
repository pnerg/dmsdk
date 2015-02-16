package org.dmonix.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A filter outputstream that converts Unix formatted line breaks (#10 , \n) to Windows formatted line breaks, i.e. adding carriage return (#13#10 , \r\n)
 * signs.
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
public class Unix2DosOutputStream extends AbstractFilterOutputStream {
    private int previous_data = -1;

    public Unix2DosOutputStream(OutputStream out) {
        super(out);
    }

    /**
     * Writes a single byte value. <br>
     * If the data equals #10 (line feed), the method adds (if not already present) a #13 (carriage return) before the #10.
     * 
     * @param b
     *            The data
     * @throws IOException
     */
    public void write(int b) throws IOException {
        if (b == 10 && previous_data != 13)
            super.write(13);

        previous_data = b;

        super.write(b);
    }
}