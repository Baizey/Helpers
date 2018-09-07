package lsm.datastructures.crypto;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import static lsm.datastructures.crypto.Utils.alterBytes;

abstract class AbstractKey {
    private final Cipher cipher;

    AbstractKey(String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance(algorithm);
    }

    abstract String decrypt(String data) throws Exception;

    abstract String encrypt(String data) throws Exception;

    String encrypt(String text, Key key) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return alterBytes(text, data -> Base64.getEncoder().encode(cipher.doFinal(data)));
    }

    String decrypt(String text, Key key) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, key);
        return alterBytes(text, data -> cipher.doFinal(Base64.getDecoder().decode(data)));
    }

}
