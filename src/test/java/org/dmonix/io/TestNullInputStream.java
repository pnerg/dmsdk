package org.dmonix.io;

import org.dmonix.AbstractTestCase;
import org.junit.After;

public class TestNullInputStream extends AbstractTestCase {

    private NullInputStream istream = null;

    @After
    public void tearDown() throws Exception {
        this.istream.close();
        this.istream = null;
    }

    /**
     * Test reading a single byte
     * 
     * @throws Exception
     */
    public void testReadSingleByte() throws Exception {
        this.istream = new NullInputStream();
        assertEquals(0, this.istream.read());
    }

    /**
     * Test reading an array of bytes
     * 
     * @throws Exception
     */
    public void testReadArray() throws Exception {
        this.istream = new NullInputStream();
        byte[] data = new byte[512];
        this.istream.read(data);

        for (int i = 0; i < data.length; i++) {
            assertEquals(0, data[i]);
        }
    }

    /**
     * Test reading an array of bytes with an offset
     * 
     * @throws Exception
     */
    public void testReadWithOffset() throws Exception {
        this.istream = new NullInputStream();
        byte[] data = new byte[512];
        this.istream.read(data, 0, data.length);

        for (int i = 0; i < data.length; i++) {
            assertEquals(0, data[i]);
        }
    }

    /**
     * Test reading an non default single byte
     * 
     * @throws Exception
     */
    public void testReadNonDefaultSingleByte() throws Exception {
        byte value = 69;
        this.istream = new NullInputStream(value);
        assertEquals(value, this.istream.read());
    }

    /**
     * Test reading an array of non default bytes
     * 
     * @throws Exception
     */
    public void testReadNonDefaultArray() throws Exception {
        byte value = 69;
        this.istream = new NullInputStream(value);
        byte[] data = new byte[512];
        this.istream.read(data);

        for (int i = 0; i < data.length; i++) {
            assertEquals(value, data[i]);
        }
    }

    /**
     * Test reading an array of non default bytes with an offset
     * 
     * @throws Exception
     */
    public void testReadNonDefaultWithOffset() throws Exception {
        byte value = 69;
        this.istream = new NullInputStream(value);
        byte[] data = new byte[512];
        this.istream.read(data, 0, data.length);

        for (int i = 0; i < data.length; i++) {
            assertEquals(value, data[i]);
        }
    }

    /**
     * Test reading an array of bytes with an illegal offset
     * 
     * @throws Exception
     */
    public void testReadWithIllegalOffset() throws Exception {
        byte value = 69;
        this.istream = new NullInputStream(value);
        byte[] data = new byte[512];

        boolean exceptionCaught = false;
        try {
            this.istream.read(data, data.length + 100, data.length);
        } catch (Exception ex) {
            exceptionCaught = true;
        }

        assertEquals(true, exceptionCaught);
    }

    /**
     * Test reading an array of bytes with an illegal length
     * 
     * @throws Exception
     */
    public void testReadWithIllegalLength() throws Exception {
        byte value = 69;
        this.istream = new NullInputStream(value);
        byte[] data = new byte[512];

        boolean exceptionCaught = false;
        try {
            this.istream.read(data, 0, data.length + 100);
        } catch (Exception ex) {
            exceptionCaught = true;
        }

        assertEquals(true, exceptionCaught);

    }
}
