package org.dmonix.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.dmonix.AbstractTestCase;
import org.junit.After;
import org.junit.Before;

/**
 * Test the class <code>IOUtil</code>
 * 
 * @author Peter Nerg
 */
public class TestIOUtil extends AbstractTestCase {

    private File file1 = new File("TestIOUtil.f1");

    private File file2 = new File("TestIOUtil.f2");

    private File file3 = new File("TestIOUtil.f3");

    @Before
    public void setUp() throws Exception {

        this.createFile(file1, "apapapaapapapapapapapaa\n%\"!p&/(/(=)/&(&,-,�dsa�asdspdjaoejfwiecwsqieybqwie");
        this.createFile(file2, "apapapaapapapapapapapaa\n%\"!p&/(/(=)/&(&,-,�dsa�asdspdjaoejfwiecwsqieybqwie");
        this.createFile(file3, "apapapaapapapapapapapaa\n%\"!p&/(/(=)/&(&,-,�dsa�asdspdjaoejfwiecwsqieyXXXXX");
    }

    @After
    public void tearDown() throws Exception {

        IOUtil.deleteFile(file1);
        IOUtil.deleteFile(file2);
        IOUtil.deleteFile(file3);
    }

    /**
     * Test the method <code>compare</code>.
     * 
     * @throws IOException
     */
    public void testCompare() throws IOException {
        assertTrue("Expected the files to match", IOUtil.compare(file1, file1));
        assertTrue("Expected the files to match", IOUtil.compare(file1, file2));

        assertFalse("Expected the files not to match", IOUtil.compare(file1, file3));
        assertFalse("Expected the files not to match", IOUtil.compare(file1, null));
        assertFalse("Expected the files not to match", IOUtil.compare(null, file3));
        assertFalse("Expected the files not to match", IOUtil.compare(file1, new File("DOES_NOT_EXIST.f")));
        assertFalse("Expected the files not to match", IOUtil.compare(new File("DOES_NOT_EXIST.f"), file3));

    }

    /**
     * Test the method <code>copyFile</code>.
     * 
     * @throws IOException
     */
    public void testCopyFile() throws Exception {
        assertFalse("Expected the files not to match", IOUtil.compare(file1, file3));
        IOUtil.copyFile(file1, file3);
        assertTrue("Expected the files to match", IOUtil.compare(file1, file3));
    }

    /**
     * Test the method <code>copyFile</code>.
     * 
     * @throws IOException
     */
    public void testCopyFileNoExpcetion() throws Exception {
        assertFalse("Expected the files not to match", IOUtil.compare(file1, file3));
        IOUtil.copyFileNoException(file1, file3);
        assertTrue("Expected the files to match", IOUtil.compare(file1, file3));
    }

    private void createFile(File f, String content) throws IOException {
        BufferedOutputStream ostream = new BufferedOutputStream(new FileOutputStream(f));
        ostream.write(content.getBytes());
        ostream.flush();
        ostream.close();
    }
}
