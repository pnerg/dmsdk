package org.dmonix.cipher;

import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * A ciphered input stream that uses the PBEWithMD5AndDES algorithm.
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
public class CipherInputStreamPBE extends BaseCipherInputStream {
    private static final String CIPHER_ALGORITHM = "PBEWithMD5AndDES";

    private static final byte[] SALT = { (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c, (byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99 };

    // Iteration count
    private static final int ITERATION_COUNT = 20;

    /**
     * A ciphered input stream that uses the PBEWithMD5AndDES algorithm.
     * 
     * @param istream
     *            The outputstream to cipher
     * @param key
     *            The key
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     */
    public CipherInputStreamPBE(InputStream istream, char[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            NoSuchPaddingException, InvalidAlgorithmParameterException {
        super(istream, CIPHER_ALGORITHM);
        super.initCipher(SecretKeyFactory.getInstance(CIPHER_ALGORITHM).generateSecret(new PBEKeySpec(key)), new PBEParameterSpec(SALT, ITERATION_COUNT));
    }

    /**
     * A ciphered input stream that uses the PBEWithMD5AndDES algorithm.
     * 
     * @param istream
     *            The outputstream to cipher
     * @param key
     *            The key
     * @param salt
     *            The salt for the algorithm
     * @param iterationCount
     *            The iteration count for the algorithm
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     */
    public CipherInputStreamPBE(InputStream istream, char[] key, byte[] salt, int iterationCount) throws NoSuchAlgorithmException, InvalidKeyException,
            InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException {
        super(istream, CIPHER_ALGORITHM);
        super.initCipher(SecretKeyFactory.getInstance(CIPHER_ALGORITHM).generateSecret(new PBEKeySpec(key)), new PBEParameterSpec(salt, iterationCount));
    }

}