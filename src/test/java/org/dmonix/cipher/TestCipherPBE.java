package org.dmonix.cipher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.dmonix.AbstractTestCase;
import org.dmonix.io.NullInputStream;
import org.dmonix.io.NullOutputStream;
import org.junit.After;
import org.junit.Before;

public class TestCipherPBE extends AbstractTestCase {

    private SerializableTestBean OBJECT_TO_CIPHER = new SerializableTestBean("Peter", System.currentTimeMillis());

    private char[] KEY_PBE = "fairfnqi9480498204235h".toCharArray();

    private static final byte[] SALT = { (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c, (byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99 };

    private static final int ITERATION_COUNT = 20;

    private File testFile = new File("testCipherPBEOstream.cph");

    @Before
    public void setUp() throws Exception {
        testFile = new File("testCipherPBEOstream.cph");
    }

    @After
    public void tearDown() throws Exception {
        testFile.delete();
    }

    /**
     * Test writing an object to file and decoding the object.
     * 
     * @throws Exception
     */
    public void testCipherPBEOstream() throws Exception {
        CipherOutputStreamPBE dmCipherOutputStreamPBE = new CipherOutputStreamPBE(new FileOutputStream(testFile), KEY_PBE);
        CipherInputStreamPBE dmCipherInputStreamPBE = new CipherInputStreamPBE(new FileInputStream(testFile), KEY_PBE);
        assertEncodeDecode(dmCipherOutputStreamPBE, dmCipherInputStreamPBE);
    }

    /**
     * Test writing an object to file and decoding the object.
     * 
     * @throws Exception
     */
    public void testCipherPBEOstream2() throws Exception {
        CipherOutputStreamPBE dmCipherOutputStreamPBE = new CipherOutputStreamPBE(new FileOutputStream(testFile), KEY_PBE, SALT, ITERATION_COUNT);
        CipherInputStreamPBE dmCipherInputStreamPBE = new CipherInputStreamPBE(new FileInputStream(testFile), KEY_PBE, SALT, ITERATION_COUNT);
        assertEncodeDecode(dmCipherOutputStreamPBE, dmCipherInputStreamPBE);
    }

    /**
     * Test that cloning can not be performed on DMCipherOutputStreamPBE
     * 
     * @throws Exception
     */
    public void testCloningCipherPBEOstream() throws Exception {
        CipherOutputStreamPBE stream = new CipherOutputStreamPBE(new NullOutputStream(), KEY_PBE);
        try {
            stream.clone();
        } catch (CloneNotSupportedException ex) {
            super.setExceptionCaught();
        }
        super.assertExceptionCaught();
    }

    /**
     * Test that cloning can not be performed on DMCipherOutputStreamPBE
     * 
     * @throws Exception
     */
    public void testCloningCipherPBEIstream() throws Exception {
        CipherInputStreamPBE stream = new CipherInputStreamPBE(new NullInputStream(), KEY_PBE);
        try {
            stream.clone();
        } catch (CloneNotSupportedException ex) {
            super.setExceptionCaught();
        }
        super.assertExceptionCaught();
    }

    private void assertEncodeDecode(CipherOutputStreamPBE dmCipherOutputStreamPBE, CipherInputStreamPBE dmCipherInputStreamPBE) throws Exception {
        ObjectOutputStream ostream = new ObjectOutputStream(dmCipherOutputStreamPBE);
        ostream.writeObject(OBJECT_TO_CIPHER);
        ostream.flush();
        ostream.close();

        assertTrue(testFile.exists());

        ObjectInputStream istream = new ObjectInputStream(dmCipherInputStreamPBE);
        Object object = istream.readObject();
        istream.close();

        assertEquals(OBJECT_TO_CIPHER, object);

        testFile.delete();

    }

}
