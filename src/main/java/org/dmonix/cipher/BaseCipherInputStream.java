package org.dmonix.cipher;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;

/**
 * The base class for all ciphered input streams.
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
public abstract class BaseCipherInputStream extends FilterInputStream {
    private Cipher cipher = null;

    private CipherInputStream cistream = null;

    protected BaseCipherInputStream(InputStream istream, String cipherAlgorithm) throws NoSuchAlgorithmException, NoSuchPaddingException {
        super(istream);
        this.cipher = Cipher.getInstance(cipherAlgorithm);
    }

    /**
     * THIS METHOD IS NOT SUPPORTED! <br>
     * The method always throws <code>java.lang.CloneNotSupportedException</code>
     * 
     * @return -
     * @throws CloneNotSupportedException
     *             The exception is always thrown
     */
    public final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException(BaseCipherInputStream.class.getName() + " : THIS METHOD IS NOT SUPPORTED!");
    }

    /**
     * Closes this input stream and releases any system resources associated with the stream.
     * 
     * @throws IOException
     */
    public void close() throws IOException {
        this.cistream.close();
        super.close();
    }

    /**
     * Reads the next byte of data from this input stream. <br>
     * The value byte is returned as an int in the range 0 to 255.<br>
     * If no byte is available because the end of the stream has been reached, the value -1 is returned.<br>
     * This method blocks until input data is available, the end of the stream is detected, or an exception is thrown.
     * 
     * @return the next byte of data, or -1 if the end of the stream is reached.
     * @throws IOException
     */
    public int read() throws IOException {
        return this.cistream.read();
    }

    /**
     * Reads up to b.length bytes of data from this input stream into an array of bytes. <br>
     * This method blocks until some input is available.<br>
     * This method simply performs the call read(b, 0, b.length) and returns the result.<br>
     * 
     * @param b
     *            the buffer into which the data is read.
     * @return the total number of bytes read into the buffer, or -1 if there is no more data because the end of the stream has been reached.
     * @throws IOException
     */
    public int read(byte[] b) throws IOException {
        return this.cistream.read(b, 0, b.length);
    }

    /**
     * Reads up to len bytes of data from this input stream into an array of bytes. <br>
     * This method blocks until some input is available.<br>
     * 
     * @param b
     *            the buffer into which the data is read.
     * @param off
     *            the start offset of the data.
     * @param len
     *            the maximum number of bytes read.
     * @return the total number of bytes read into the buffer, or -1 if there is no more data because the end of the stream has been reached.
     * @throws IOException
     */
    public int read(byte[] b, int off, int len) throws IOException {
        return this.cistream.read(b, off, len);
    }

    /**
     * Init the cipher.
     * 
     * @param key
     *            The key for the cipher
     * @throws InvalidKeyException
     */
    protected void initCipher(Key key) throws InvalidKeyException {
        this.cipher.init(Cipher.DECRYPT_MODE, key);
        this.cistream = new CipherInputStream(super.in, this.cipher);
    }

    /**
     * Init the cipher.
     * 
     * @param key
     *            The key for the cipher
     * @param param
     *            Cipher parameters
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     */
    protected void initCipher(Key key, AlgorithmParameterSpec param) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.cipher.init(Cipher.DECRYPT_MODE, key, param);
        this.cistream = new CipherInputStream(super.in, this.cipher);
    }
}