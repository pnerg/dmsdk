package org.dmonix.io;

import java.io.File;

import org.dmonix.AbstractTestCase;
import org.junit.After;

/**
 * Test the class <code>StackTraceLogger</code>
 * 
 * @author Peter Nerg
 */
public class TestStackTraceLogger extends AbstractTestCase {

    private File file = new File("TestStackTraceLogger.log");

    @After
    protected void tearDown() throws Exception {
        IOUtil.deleteFile(file);
    }

    /**
     * Test the method <code>getInstance</code>.
     * 
     * @throws Exception
     */
    public void testGetInstance() throws Exception {
        StackTraceLogger stackTraceLogger = StackTraceLogger.newInstance(file);
        assertNotNull(stackTraceLogger);
        assertEquals(stackTraceLogger, StackTraceLogger.getInstance());
        stackTraceLogger.destroy();
    }

    /**
     * Test the method <code>getOutputStream</code>.
     * 
     * @throws Exception
     */
    public void testGetOutputStream() throws Exception {
        StackTraceLogger stackTraceLogger = StackTraceLogger.newInstance(file);
        assertNotNull(stackTraceLogger);
        assertNotNull(stackTraceLogger.getOutputStream());
        stackTraceLogger.destroy();
    }

    /**
     * Test the method <code>destroy</code>.
     * 
     * @throws Exception
     */
    public void testDestroy() throws Exception {
        StackTraceLogger stackTraceLogger = StackTraceLogger.newInstance(file);
        assertNotNull(stackTraceLogger);
        stackTraceLogger.destroy();

        try {
            StackTraceLogger.getInstance();
        } catch (Exception e) {
            super.setExceptionCaught();
        }
        assertExceptionCaught();
    }
}
