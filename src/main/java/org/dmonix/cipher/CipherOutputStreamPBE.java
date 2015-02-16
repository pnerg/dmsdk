package org.dmonix.cipher;

import java.io.OutputStream;
import java.security.GeneralSecurityException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * A ciphered output stream that uses the PBEWithMD5AndDES algorithm.
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public class CipherOutputStreamPBE extends BaseCipherOutputStream {
    private static final String CIPHER_ALGORITHM = "PBEWithMD5AndDES";

    /** The default iteration salt. */
    private static final byte[] SALT = { (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c, (byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99 };

    /** The default iteration count. */
    private static final int ITERATION_COUNT = 20;

    /**
     * A ciphered output stream that uses the PBEWithMD5AndDES algorithm. <br>
     * The object will use a default value for the salt and iteration count for the algorithm
     * 
     * @param ostream
     *            The outputstream to cipher
     * @param key
     *            The key
     * @throws GeneralSecurityException
     */
    public CipherOutputStreamPBE(OutputStream ostream, char[] key) throws GeneralSecurityException {
        this(ostream, key, SALT, ITERATION_COUNT);
    }

    /**
     * A ciphered output stream that uses the PBEWithMD5AndDES algorithm.
     * 
     * @param ostream
     *            The outputstream to cipher
     * @param key
     *            The key
     * @param salt
     *            The salt for the algorithm
     * @param iterationCount
     *            The iteration count for the algorithm
     * @throws GeneralSecurityException
     */
    public CipherOutputStreamPBE(OutputStream ostream, char[] key, byte[] salt, int iterationCount) throws GeneralSecurityException {
        super(ostream, CIPHER_ALGORITHM);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(CIPHER_ALGORITHM);
        super.initCipher(keyFactory.generateSecret(new PBEKeySpec(key)), new PBEParameterSpec(salt, iterationCount));
    }
}