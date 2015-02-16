package org.dmonix.cipher;

import java.io.*;
import java.security.GeneralSecurityException;

import org.dmonix.io.IOUtil;

/**
 * Utility class for encoding/decoding small amounts of data using ciphers. <br>
 * The class is designed to encode/decode smaller amounts of data, e.g. strings that are to be stored on file or in a database.<br>
 * The class can also encode/decode files.<br>
 * If larger amounts of streamed data is to be encoded/decoded it is advised to use any of the sub classes to <code>DMCipherInputStream</code> and
 * <code>DMCipherOutputStream</code>.
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public abstract class DataCipherer {

    /**
     * Encodes the data using the <code>DES</code> algorithm.
     * 
     * @param b
     *            The data to encode
     * @param key
     *            The key for the cipher algoritm, must be exactly 8 bytes long (required by the algorithm)
     * @return The encoded data
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static byte[] encodeDataDES(byte b[], byte[] key) throws IOException, GeneralSecurityException {

        ByteArrayOutputStream baostream = new ByteArrayOutputStream();
        return encodeData(b, baostream, new CipherOutputStreamDES(baostream, key));
        // ByteArrayOutputStream baostream = new ByteArrayOutputStream();
        // BufferedOutputStream bostream = new BufferedOutputStream(new DMCipherOutputStreamDES(baostream, key));

        // bostream.write(b);
        // bostream.flush();
        // bostream.close();
        //
        // byte[] data = baostream.toByteArray();
        // baostream.close();
        //
        // return data;
    }

    /**
     * Encodes the data using the <code>PBEWithMD5AndDES</code> algorithm.
     * 
     * @param b
     *            The data to encode
     * @param key
     *            The key for the cipher algoritm
     * @return The encoded data
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static byte[] encodeDataPBE(byte[] b, char[] key) throws IOException, GeneralSecurityException {

        ByteArrayOutputStream baostream = new ByteArrayOutputStream();
        return encodeData(b, baostream, new CipherOutputStreamPBE(baostream, key));
        // BufferedOutputStream bostream = new BufferedOutputStream(new DMCipherOutputStreamPBE(baostream, key));
        //
        // bostream.write(b);
        // bostream.flush();
        // bostream.close();
        //
        // byte[] data = baostream.toByteArray();
        // baostream.close();
        //
        // return data;
    }

    private static byte[] encodeData(byte[] outputData, ByteArrayOutputStream byteArrayOutputStream, OutputStream outputStream) throws IOException {
        BufferedOutputStream bostream = new BufferedOutputStream(outputStream);

        bostream.write(outputData);
        bostream.flush();
        bostream.close();

        byte[] data = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();

        return data;
    }

    /**
     * Decodes the data using the <code>DES</code> algorithm.
     * 
     * @param b
     *            The data to decode
     * @param key
     *            The key for the cipher algoritm, must be exactly 8 bytes long (required by the algorithm)
     * @return The decoded data
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static byte[] decodeDataDES(byte[] b, byte[] key) throws IOException, GeneralSecurityException {

        BufferedInputStream bistream = new BufferedInputStream(new CipherInputStreamDES(new ByteArrayInputStream(b), key));
        ByteArrayOutputStream baostream = new ByteArrayOutputStream();

        IOUtil.copyStreams(bistream, baostream, true, false);
        byte[] data = baostream.toByteArray();
        baostream.close();

        return data;
    }

    /**
     * Decodes the data using the <code>PBEWithMD5AndDES</code> algorithm.
     * 
     * @param b
     *            The data to decode
     * @param key
     *            The key for the cipher algoritm
     * @return The decoded data
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static byte[] decodeDataPBE(byte[] b, char[] key) throws IOException, GeneralSecurityException {

        BufferedInputStream bistream = new BufferedInputStream(new CipherInputStreamPBE(new ByteArrayInputStream(b), key));
        ByteArrayOutputStream baostream = new ByteArrayOutputStream();

        IOUtil.copyStreams(bistream, baostream, true, false);
        byte[] data = baostream.toByteArray();
        baostream.close();

        return data;
    }

    /**
     * Encodes a file using the <code>DES</code< algorithm
     * 
     * @param input
     *            The input file
     * @param output
     *            The output file
     * @param key
     *            The key, must be exactly 8 bytes long (required by the algorithm)
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static void encodeFileDES(File input, File output, byte[] key) throws IOException, GeneralSecurityException {

        IOUtil.copyStreams(new BufferedInputStream(new FileInputStream(input)), new BufferedOutputStream(new CipherOutputStreamDES(
                new FileOutputStream(output), key)));
    }

    /**
     * Encodes a single file using the <code>PBEWithMD5AndDES</code> algorithm.
     * 
     * @param input
     *            The input (original) file
     * @param output
     *            The output (coded) file
     * @param key
     *            The key to use for the encoding
     * @throws IOException
     * @throws GeneralSecurityException
     * @see org.dmonix.cipher.CipherOutputStreamPBE
     */
    public static void encodeFilePBE(File input, File output, char[] key) throws IOException, GeneralSecurityException {

        IOUtil.copyStreams(new BufferedInputStream(new FileInputStream(input)), new CipherOutputStreamPBE(new FileOutputStream(output), key));
    }

    /**
     * Decodes a single file using the <code>DES</code< algorithm.
     * 
     * @param input
     *            The input (coded) file
     * @param output
     *            The output (decoded) file
     * @param key
     *            The key to use for the encoding, must be exactly 8 bytes long (required by the algorithm)
     * @throws IOException
     * @throws GeneralSecurityException
     * @see org.dmonix.cipher.CipherInputStreamDES
     */
    public static void decodeFileDES(File input, File output, byte[] key) throws IOException, GeneralSecurityException {

        IOUtil.copyStreams(new BufferedInputStream(new CipherInputStreamDES(new FileInputStream(input), key)), new FileOutputStream(output));
    }

    /**
     * Decodes a single file using the <code>PBEWithMD5AndDES</code> algorithm.
     * 
     * @param input
     *            The input (coded) file
     * @param output
     *            The output (decoded) file
     * @param key
     *            The key to use for the encoding
     * @see org.dmonix.cipher.CipherInputStreamPBE
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static void decodeFilePBE(File input, File output, char[] key) throws IOException, GeneralSecurityException {

        IOUtil.copyStreams(new BufferedInputStream(new CipherInputStreamPBE(new FileInputStream(input), key)), new FileOutputStream(output));
    }
}