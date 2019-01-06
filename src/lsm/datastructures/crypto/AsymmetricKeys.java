package lsm.datastructures.crypto;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static lsm.datastructures.crypto.Constants.asymmetricKeyAlgorithm;
import static lsm.datastructures.crypto.Constants.asymmetricKeySize;
import static lsm.datastructures.crypto.Utils.*;

@SuppressWarnings({"WeakerAccess", "unused"})
public class AsymmetricKeys extends AbstractKey {
    private final Key privateKey, publicKey;

    private static KeyPairGenerator keygen = null;
    static {
        try {
            keygen = KeyPairGenerator.getInstance(asymmetricKeyAlgorithm);
            keygen.initialize(asymmetricKeySize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AsymmetricKeys() throws Exception {
        super(asymmetricKeyAlgorithm);
        var pair = keygen.generateKeyPair();
        publicKey = pair.getPublic();
        privateKey = pair.getPrivate();
    }

    public AsymmetricKeys(Key publicKey, Key privateKey) throws Exception {
        super(asymmetricKeyAlgorithm);
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    public String encrypt(String data) throws Exception {
        var symmetricKey = new SymmetricKey();
        var key = encrypt(symmetricKey.key(), publicKey);
        var text = symmetricKey.encrypt(data);
        return join(key, text);
    }

    @Override
    public String decrypt(String data) throws Exception {
        var parts = split(data);
        var keyString = decrypt(parts[0], privateKey);
        var msg = parts[1];
        return new SymmetricKey(keyString).decrypt(msg);
    }

    public String publicKey() {
        return encodeToString(publicKey.getEncoded());
    }

    public String privateKey() {
        return encodeToString(privateKey.getEncoded());
    }
}

