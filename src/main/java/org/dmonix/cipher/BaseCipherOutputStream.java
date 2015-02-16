package org.dmonix.cipher;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

/**
 * The base class for all ciphered output streams.
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
public abstract class BaseCipherOutputStream extends FilterOutputStream {
    private Cipher cipher = null;

    private CipherOutputStream costream = null;

    protected BaseCipherOutputStream(OutputStream ostream, String cipherAlgorithm) throws GeneralSecurityException {
        super(ostream);
        this.cipher = Cipher.getInstance(cipherAlgorithm);
    }

    protected BaseCipherOutputStream(OutputStream ostream, Cipher cipher) {
        super(ostream);
        this.cipher = cipher;
    }

    /**
     * Set the OutputStream.
     * 
     * @param ostream
     */
    void setOutputStream(OutputStream ostream) {
        super.out = ostream;
    }

    /**
     * THIS METHOD IS NOT SUPPORTED!.<br>
     * The method always throws <code>java.lang.CloneNotSupportedException</code>
     * 
     * @return -
     * @throws CloneNotSupportedException
     *             The exception is always thrown
     */
    public final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException(this.getClass().getName() + " : THIS METHOD IS NOT SUPPORTED!");
    }

    /**
     * Closes this output stream and releases any system resources associated with the stream. <br>
     * The close method of this class calls its flush method, and then calls the close method of its underlying output stream.
     * 
     * @throws IOException
     */
    public void close() throws IOException {
        this.costream.flush();
        this.costream.close();
        super.flush();
        super.close();
    }

    /**
     * Flushes this output stream and forces any buffered output bytes to be written out to the stream.
     * 
     * @throws IOException
     */
    public void flush() throws IOException {
        this.costream.flush();
        super.flush();
    }

    /**
     * Writes <code>b.length</code> bytes to this output stream.
     * 
     * @param b
     *            the data to be written.
     * @throws IOException
     */
    public void write(byte[] b) throws IOException {
        this.costream.write(b, 0, b.length);
    }

    /**
     * Writes <code>len</code> bytes from the specified byte array starting at offset <code>off</code> to this output stream.
     * 
     * @param b
     *            the data.
     * @param off
     *            the start offset in the data.
     * @param len
     *            the number of bytes to write.
     * @throws IOException
     */
    public void write(byte[] b, int off, int len) throws IOException {
        this.costream.write(b, off, len);
    }

    /**
     * Writes the specified byte to this output stream.
     * 
     * @param b
     *            the data
     * @throws IOException
     */
    public void write(int b) throws IOException {
        this.costream.write(b);
    }

    /**
     * Init the cipher.
     * 
     * @param key
     *            The key for the cipher
     * @throws InvalidKeyException
     */
    protected void initCipher(Key key) throws GeneralSecurityException {
        this.cipher.init(Cipher.ENCRYPT_MODE, key);
        this.costream = new CipherOutputStream(super.out, this.cipher);
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
    protected void initCipher(Key key, AlgorithmParameterSpec param) throws GeneralSecurityException {
        this.cipher.init(Cipher.ENCRYPT_MODE, key, param);
        this.costream = new CipherOutputStream(super.out, this.cipher);
    }

}