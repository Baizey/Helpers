package lsm.helpers.crypto;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static lsm.helpers.crypto.Utils.*;

abstract class AbstractKey {
    private final Cipher cipher;

    AbstractKey(String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance(algorithm);
    }

    abstract String decrypt(String data) throws Exception;
    abstract String encrypt(String data) throws Exception;

    String encrypt(String data, Key key) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return asString(Base64.getEncoder().encode(cipher.doFinal(asBytes(data))));
    }
    String decrypt(String data, Key key) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, key);
        return asString(cipher.doFinal(Base64.getDecoder().decode(asBytes(data))));
    }
}
