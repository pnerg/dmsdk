package org.dmonix.io;

import java.io.ByteArrayOutputStream;

import org.dmonix.AbstractTestCase;
import org.junit.After;
import org.junit.Before;

/**
 * Test the class <code>RemoveCRLFOutputStream</code>
 * 
 * @author Peter Nerg
 */
public class TestRemoveCRLFOutputStream extends AbstractTestCase {

    private RemoveCRLFOutputStream ostream = null;
    private ByteArrayOutputStream byteArrayOutputStream = null;

    private String testString = "apa\r\n.xxyz\r\nadasdasda\n";
    private String expectedString = "apa.xxyzadasdasda";

    @Before
    public void setUp() throws Exception {
        byteArrayOutputStream = new ByteArrayOutputStream();
        ostream = new RemoveCRLFOutputStream(byteArrayOutputStream);
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
}
