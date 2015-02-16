package org.dmonix.cipher;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.dmonix.AbstractTestCase;
import org.dmonix.io.IOUtil;
import org.junit.After;
import org.junit.Before;

public class TestDataCipherer extends AbstractTestCase {

    private final String STRING_TO_CIPHER = "This string is to be encoded : !\"#�%&/()!!!";
    private final byte[] KEY_DES = "12345678".getBytes();
    private final char[] KEY_PBE = "fairfnqi9480498204235h".toCharArray();

    private final File original = new File("TestDataCipherer_original.f");
    private final File encoded = new File("TestDataCipherer_encoded.f");
    private final File decoded = new File("TestDataCipherer_decoded.f");

    @Before
    public void setUp() throws Exception {

        BufferedOutputStream bostream = new BufferedOutputStream(new FileOutputStream(original));
        bostream.write("aodaodasidhadiashdlaishdksadaskdxasjdas".getBytes());
        bostream.write("kdjasidasiajcdajdo".getBytes());
        bostream.write("Q#&!\"�#&%\"�\"#%\"�#\"�\"!#%#\"\n".getBytes());
        bostream.write("40839173913712923712o9837128!!!!\n".getBytes());
        bostream.write("aodaodasidhadiashdlaishdksadaskdxasjdas\n".getBytes());
        bostream.write("aodaodasidhadiashdlaishdksadaskdxasjdas\n".getBytes());
        bostream.write("aodaodasidhadiashdlaishdksadaskdxasjdas\n".getBytes());
        bostream.flush();
        bostream.close();
    }

    @After
    public void tearDown() throws Exception {
        IOUtil.deleteFile(original);
        IOUtil.deleteFile(encoded);
        IOUtil.deleteFile(decoded);
    }

    /**
     * Test to encode/decode a String using DES
     * 
     * @throws Exception
     */
    public void testEncodeDataDES() throws Exception {
        // test coding data
        byte[] encodedData = DataCipherer.encodeDataDES(STRING_TO_CIPHER.getBytes(), KEY_DES);
        assertNotNull(encodedData);
        assertFalse(encodedData.length == 0);

        // test decoding data
        byte[] decodedData = DataCipherer.decodeDataDES(encodedData, KEY_DES);
        assertNotNull(decodedData);
        assertFalse(decodedData.length == 0);
        assertTrue("The decoded string doesn't match the original", STRING_TO_CIPHER.equals(new String(decodedData)));
    }

    /**
     * Test to encode/decode a String using PBE
     * 
     * @throws Exception
     */
    public void testEncodeDataPBE() throws Exception {
        // test coding data
        byte[] encodedData = DataCipherer.encodeDataPBE(STRING_TO_CIPHER.getBytes(), KEY_PBE);
        assertNotNull(encodedData);
        assertFalse(encodedData.length == 0);

        // test decoding data
        byte[] decodedData = DataCipherer.decodeDataPBE(encodedData, KEY_PBE);
        assertNotNull(decodedData);
        assertFalse(decodedData.length == 0);
        assertTrue("The decoded string doesn't match the original", STRING_TO_CIPHER.equals(new String(decodedData)));
    }

    /**
     * Test the method <code>encodeFileDES</code>
     * 
     * @throws Exception
     */
    public void testEncodeFileDES() throws Exception {
        DataCipherer.encodeFileDES(original, encoded, KEY_DES);
        assertFalse("The encoded file should not match the original", IOUtil.compare(original, encoded));
        DataCipherer.decodeFileDES(encoded, decoded, KEY_DES);
        assertTrue("The decoded file did not match the original", IOUtil.compare(original, decoded));
    }

    /**
     * Test the method <code>encodeFilePBE</code>
     * 
     * @throws Exception
     */
    public void testEncodeFilePBE() throws Exception {
        DataCipherer.encodeFilePBE(original, encoded, KEY_PBE);
        assertFalse("The encoded file should not match the original", IOUtil.compare(original, encoded));
        DataCipherer.decodeFilePBE(encoded, decoded, KEY_PBE);
        assertTrue("The decoded file did not match the original", IOUtil.compare(original, decoded));
    }
}
