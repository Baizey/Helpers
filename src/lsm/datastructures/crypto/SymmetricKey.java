package lsm.datastructures.crypto;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

import static lsm.datastructures.crypto.Utils.*;

@SuppressWarnings("WeakerAccess")
public class SymmetricKey extends AbstractKey {
    private static final String symmetricKeyAlgorithm = "AES";
    private static final int keySize = 256; // 2^9
    private Key key;

    public SymmetricKey() throws Exception {
        super(symmetricKeyAlgorithm);
        KeyGenerator generator = KeyGenerator.getInstance(symmetricKeyAlgorithm);
        generator.init(keySize);
        this.key = generator.generateKey();
    }

    public SymmetricKey(String key) throws Exception {
        this(new SecretKeySpec(fromBase64(key), symmetricKeyAlgorithm));
    }

    public SymmetricKey(Key key) throws Exception {
        super(key.getAlgorithm());
        this.key = key;
    }

    @Override
    public String encrypt(String data) throws Exception {
        return encrypt(data, key);
    }

    @Override
    public String decrypt(String data) throws Exception {
        return decrypt(data, key);
    }

    public Key key() {
        return key;
    }
    public String keyString() throws Exception {
        return toBase64(key.getEncoded());
    }
}
