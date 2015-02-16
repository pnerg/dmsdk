package org.dmonix.io;

import java.io.IOException;

import org.dmonix.AbstractTestCase;
import org.junit.After;
import org.junit.Before;

public class TestNullWriter extends AbstractTestCase {

    private NullWriter writer = null;

    @Before
    public void setUp() throws Exception {
        writer = new NullWriter();
    }

    @After
    public void tearDown() throws Exception {
        writer.close();
    }

    /**
     * Test the method <code>write(char[])</code>
     * 
     * @throws IOException
     */
    public void testWrite() throws IOException {
        writer.write("data-to-write".toCharArray());
        writer.flush();
    }

    /**
     * Test the method <code>write(char[], int, int)</code>
     * 
     * @throws IOException
     */
    public void testWrite2() throws IOException {
        writer.write("data-to-write".toCharArray(), 1, 3);
        writer.flush();
    }

    /**
     * Test the method <code>write(String)</code>
     * 
     * @throws IOException
     */
    public void testWrite3() throws IOException {
        writer.write("data-to-write");
        writer.flush();
    }

    /**
     * Test the method <code>write(String, int, int)</code>
     * 
     * @throws IOException
     */
    public void testWrite4() throws IOException {
        writer.write("data-to-write", 1, 4);
        writer.flush();
    }

    /**
     * Test the method <code>write(int)</code>
     * 
     * @throws IOException
     */
    public void testWrite5() throws IOException {
        writer.write(1);
        writer.write(2);
        writer.write(69);
        writer.flush();
    }
}
