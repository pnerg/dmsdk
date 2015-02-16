package org.dmonix.cipher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.dmonix.AbstractTestCase;
import org.dmonix.io.NullInputStream;
import org.dmonix.io.NullOutputStream;
import org.junit.Test;

public class TestCipherDES extends AbstractTestCase {

    private SerializableTestBean OBJECT_TO_CIPHER = new SerializableTestBean("Peter", System.currentTimeMillis());

    private byte[] KEY_DES = "12345678".getBytes();

    /**
     * Test writing an object to file and decoding the object.
     * 
     * @throws Exception
     */
    @Test
    public void testCipherDESOstream() throws Exception {
        File testFile = new File("testCipherDESOstream.cph");
        ObjectOutputStream ostream = new ObjectOutputStream(new CipherOutputStreamDES(new FileOutputStream(testFile), KEY_DES));
        ostream.writeObject(OBJECT_TO_CIPHER);
        ostream.flush();
        ostream.close();

        assertTrue(testFile.exists());

        ObjectInputStream istream = new ObjectInputStream(new CipherInputStreamDES(new FileInputStream(testFile), KEY_DES));
        Object object = istream.readObject();
        istream.close();

        assertEquals(OBJECT_TO_CIPHER, object);

        testFile.delete();
    }

    /**
     * Test that cloning can not be performed on DMCipherOutputStreamDES
     * 
     * @throws Exception
     */
    @Test
    public void testCloningCipherDESOstream() throws Exception {
        CipherOutputStreamDES stream = new CipherOutputStreamDES(new NullOutputStream(), KEY_DES);
        try {
            stream.clone();
        } catch (CloneNotSupportedException ex) {
            super.setExceptionCaught();
        }
        super.assertExceptionCaught();
    }

    /**
     * Test that cloning can not be performed on DMCipherInputStreamDES
     * 
     * @throws Exception
     */
    @Test
    public void testCloningCipherDESIstream() throws Exception {
        CipherInputStreamDES stream = new CipherInputStreamDES(new NullInputStream(), KEY_DES);
        try {
            stream.clone();
        } catch (CloneNotSupportedException ex) {
            super.setExceptionCaught();
        }
        super.assertExceptionCaught();
    }
}
