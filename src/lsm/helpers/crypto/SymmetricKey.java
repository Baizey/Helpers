package lsm.helpers.crypto;

import lsm.helpers.crypto.Hash;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static lsm.helpers.crypto.Hash.textType;

public class SymmetricKey {
    private static final String symmetricCrypt = "AES";
    private Cipher c = Cipher.getInstance(symmetricCrypt);
    private SecretKeySpec k;

    public SymmetricKey() throws UnsupportedEncodingException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException {
        this(Hash.generateRandom(128));
    }

    public SymmetricKey(String secret) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        this(secret.getBytes(textType));
    }

    public SymmetricKey(byte[] secret) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        k = new SecretKeySpec(secret, symmetricCrypt);
    }

    public byte[] encrypt(String text) throws InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        return encrypt(text.getBytes(textType));
    }
    public byte[] encrypt(byte[] text) throws InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        c.init(Cipher.ENCRYPT_MODE, k);
        return c.doFinal(text);
    }

    public byte[] decrypt(String text) throws InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        return decrypt(text.getBytes(textType));
    }
    public byte[] decrypt(byte[] text) throws InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        c.init(Cipher.DECRYPT_MODE, k);
        return c.doFinal(text);
    }

    public String decryptToString(String text) throws InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        return new String(decrypt(text), textType);
    }
    public String decryptToString(byte[] text) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        return new String(decrypt(text), textType);
    }
}
