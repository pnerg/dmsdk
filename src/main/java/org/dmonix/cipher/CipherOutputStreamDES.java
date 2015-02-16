package org.dmonix.cipher;

import java.io.OutputStream;
import java.security.GeneralSecurityException;

import javax.crypto.spec.SecretKeySpec;

/**
 * A ciphered output stream that uses the DES algorithm.
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
public class CipherOutputStreamDES extends BaseCipherOutputStream {
    private static final String CIPHER_ALGORITHM = "DES";

    /**
     * A ciphered output stream that uses the DES algorithm.
     * 
     * @param ostream
     *            The outputstream to cipher
     * @param key
     *            The key, must be exactly 8 bytes long (required by the algorithm)
     * @throws GeneralSecurityException
     */
    public CipherOutputStreamDES(OutputStream ostream, byte[] key) throws GeneralSecurityException {
        super(ostream, CIPHER_ALGORITHM);
        super.initCipher(new SecretKeySpec(key, CIPHER_ALGORITHM));
    }

}