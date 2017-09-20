package lsm.helpers.crypto;

import lsm.helpers.crypto.Hash;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static lsm.helpers.crypto.Hash.textType;

public class AsymmetricKeys {
    private static final String assymetricCrypt = "RSA";
    private final Cipher c = Cipher.getInstance(assymetricCrypt);
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public AsymmetricKeys() throws UnsupportedEncodingException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException {
        this(Hash.generateRandom(128));
    }

    public AsymmetricKeys(String secret) throws UnsupportedEncodingException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException {
        this(secret.getBytes(textType));
    }

    public AsymmetricKeys(byte[] secret) throws UnsupportedEncodingException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException {
        publicKey = createPublicKey(secret);
        privateKey = createPrivateKey(secret);
    }

    private PrivateKey createPrivateKey(byte[] secret) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(secret);
        KeyFactory kf = KeyFactory.getInstance(assymetricCrypt);
        return kf.generatePrivate(spec);
    }

    private PublicKey createPublicKey(byte[] secret) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(secret);
        KeyFactory kf = KeyFactory.getInstance(assymetricCrypt);
        return kf.generatePublic(spec);
    }

    public byte[] encrypt(String text) throws InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        return encrypt(text.getBytes(textType));
    }
    public byte[] encrypt(byte[] text) throws InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        c.init(Cipher.ENCRYPT_MODE, privateKey);
        return c.doFinal(text);
    }

    public byte[] decrypt(String text) throws UnsupportedEncodingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return decrypt(text.getBytes(textType));
    }
    public byte[] decrypt(byte[] text) throws UnsupportedEncodingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        c.init(Cipher.DECRYPT_MODE, publicKey);
        return c.doFinal(text);
    }


    public String decryptToString(String text) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        return new String(decrypt(text), textType);
    }
    public String decryptToString(byte[] text) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        return new String(decrypt(text), textType);
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
