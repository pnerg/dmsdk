package org.dmonix.cipher;

import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * A ciphered input stream that uses the DES algorithm.
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
public class CipherInputStreamDES extends BaseCipherInputStream {
    private static final String CIPHER_ALGORITHM = "DES";

    /**
     * 
     * @param istream
     *            The input stream to cipher
     * @param key
     *            The key, must be exactly 8 bytes long (required by the algorithm)
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     */
    public CipherInputStreamDES(InputStream istream, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException {

        super(istream, CIPHER_ALGORITHM);
        super.initCipher(new SecretKeySpec(key, CIPHER_ALGORITHM));
    }

}