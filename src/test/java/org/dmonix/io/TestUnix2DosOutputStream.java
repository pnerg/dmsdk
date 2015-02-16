package org.dmonix.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.dmonix.AbstractTestCase;
import org.junit.After;
import org.junit.Before;

/**
 * Test the class <code>Unix2DosOutputStream</code>
 * 
 * @author Peter Nerg
 */
public class TestUnix2DosOutputStream extends AbstractTestCase {

    private Unix2DosOutputStream ostream = null;

    private ByteArrayOutputStream byteArrayOutputStream = null;

    private String testString = "apa\n.xxyz\nadasdasda\n";

    private String expectedString = "apa\r\n.xxyz\r\nadasdasda\r\n";

    @Before
    public void setUp() throws Exception {
        byteArrayOutputStream = new ByteArrayOutputStream();
        ostream = new Unix2DosOutputStream(byteArrayOutputStream);
    }

    @After
    public void tearDown() throws Exception {
        ostream.close();
    }

    /**
     * Test the method <code>write(byte[])</code>
     * 
     * @throws Exception
     */
    public void testWrite() throws Exception {
        ostream.write(testString.getBytes());
        ostream.flush();
        assertEquals("The strings don't match", expectedString, byteArrayOutputStream.toString());
    }

    /**
     * Test the method <code>write(byte[], int, int)</code>
     * 
     * @throws Exception
     */
    public void testWrite2() throws Exception {
        int length = testString.length();
        testString = "XXX" + testString;
        testString += "YYY";

        byte[] data = testString.getBytes();
        ostream.write(data, 3, length);
        ostream.flush();
        assertEquals("The strings don't match", expectedString, byteArrayOutputStream.toString());
    }

    /**
     * Test the method <code>write(int)</code>
     * 
     * @throws Exception
     */
    public void testWrite3() throws Exception {
        byte[] data = testString.getBytes();

        for (int i = 0; i < data.length; i++) {
            ostream.write((int) data[i]);
        }
        ostream.flush();
        assertEquals("The strings don't match", expectedString, byteArrayOutputStream.toString());
    }

    /**
     * Test the write method with various illegal offset and length parameters.
     * 
     * @throws IOException
     */
    public void testIllegalOffsetAndLength() throws IOException {

        byte[] data = testString.getBytes();
        try {
            ostream.write(data, -69, 2);
            fail("Expected an IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            super.setExceptionCaught();
        }

        try {
            ostream.write(data, 10, -69);
            fail("Expected an IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            super.setExceptionCaught();
        }

        try {
            ostream.write(data, 6969, 2);
            fail("Expected an IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            super.setExceptionCaught();
        }

        try {
            ostream.write(data, 2, 6969);
            fail("Expected an IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            super.setExceptionCaught();
        }

        try {
            ostream.write(data, 15, 15);
            fail("Expected an IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            super.setExceptionCaught();
        }

    }
}
