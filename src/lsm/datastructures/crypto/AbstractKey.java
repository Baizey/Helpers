package lsm.datastructures.crypto;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static lsm.datastructures.crypto.Utils.decode;
import static lsm.datastructures.crypto.Utils.encode;

abstract class AbstractKey {
    private final Cipher cipher;

    AbstractKey(String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance(algorithm);
    }

    abstract String decrypt(String data) throws Exception;

    abstract String encrypt(String data) throws Exception;

    String encrypt(String text, Key key) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return new String(encode(cipher.doFinal(text.getBytes(UTF_8))), UTF_8);
    }

    String decrypt(String text, Key key) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(decode(text.getBytes(UTF_8))), UTF_8);
    }

}
