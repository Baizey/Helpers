package lsm.datastructures.crypto;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import static lsm.datastructures.crypto.Constants.symmetricKeyAlgorithm;
import static lsm.datastructures.crypto.Constants.symmetricKeySize;
import static lsm.datastructures.crypto.Utils.decode;
import static lsm.datastructures.crypto.Utils.encodeToString;

@SuppressWarnings("WeakerAccess")
public class SymmetricKey extends AbstractKey {

    private Key key;

    private static KeyGenerator generator = null;

    static {
        try {
            generator = KeyGenerator.getInstance(symmetricKeyAlgorithm);
            generator.init(symmetricKeySize);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public SymmetricKey() throws Exception {
        super(symmetricKeyAlgorithm);
        key = generator.generateKey();
    }

    public SymmetricKey(String key) throws Exception {
        this(new SecretKeySpec(decode(key), symmetricKeyAlgorithm));
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

    public String key() {
        return encodeToString(key.getEncoded());
    }
}
