package org.dmonix.io;

import java.io.ByteArrayOutputStream;

import org.dmonix.AbstractTestCase;
import org.junit.Before;

/**
 * Test the class <code>RemoveLineCommentOutputStream</code>
 * 
 * @author Peter Nerg
 */
public class TestRemoveLineCommentOutputStream extends AbstractTestCase {

    private RemoveLineCommentOutputStream ostream = null;
    private ByteArrayOutputStream byteArrayOutputStream = null;

    private String testString = "apa\n" + "//COMMENT1\n" + "text1\n" + "//COMMENT2\n" + "text2\n" + "//COMMENT3\n" + "text3\n" + "//COMMENT4\n" + "text4";

    private String expectedString = "apa\n" + "text1\n" + "text2\n" + "text3\n" + "text4";

    @Before
    public void setUp() throws Exception {
        byteArrayOutputStream = new ByteArrayOutputStream();
        ostream = new RemoveLineCommentOutputStream(byteArrayOutputStream);
    }

    /**
     * Test the method <code>write(byte[])</code>
     * 
     * @throws Exception
     */
    public void testWrite() throws Exception {
        ostream.write(testString.getBytes());
        ostream.flush();
        ostream.close();
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
        ostream.close();
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
        ostream.close();
        assertEquals("The strings don't match", expectedString, byteArrayOutputStream.toString());
    }
}
