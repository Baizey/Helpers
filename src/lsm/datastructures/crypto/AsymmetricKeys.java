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
    private Key privateKey, publicKey;

    private static KeyFactory factory = null;
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
        SymmetricKey symmetricKey = new SymmetricKey();
        var key = encrypt(symmetricKey.key(), publicKey);
        var text = symmetricKey.encrypt(data);
        return join(key, text);
    }

    @Override
    public String decrypt(String data) throws Exception {
        var parts = split(data);
        var encryptedKey = parts[0];
        var msg = parts[1];
        return new SymmetricKey(decrypt(encryptedKey, privateKey)).decrypt(msg);
    }

    public String publicKey() {
        return encodeToString(publicKey.getEncoded());
    }

    public String privateKey() {
        return encodeToString(privateKey.getEncoded());
    }
}

@SuppressWarnings("unused")
class AsymmetricKeyBuilder {
    private Key privateKey = null, publicKey = null;

    private static KeyFactory factory = null;
    private static KeyPairGenerator keygen = null;

    static {
        try {
            keygen = KeyPairGenerator.getInstance(asymmetricKeyAlgorithm);
            keygen.initialize(asymmetricKeySize);
            factory = KeyFactory.getInstance(asymmetricKeyAlgorithm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AsymmetricKeyBuilder generateKeys() {
        var pair = keygen.generateKeyPair();
        return setPublic(pair.getPublic())
                .setPrivate(pair.getPrivate());
    }

    public AsymmetricKeyBuilder setPublic(String publicKey) throws InvalidKeySpecException {
        return setPublic(factory.generatePublic(new X509EncodedKeySpec(Utils.decode(publicKey))));
    }

    public AsymmetricKeyBuilder setPublic(PublicKey publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public AsymmetricKeyBuilder setPrivate(String privateKey) throws InvalidKeySpecException {
        return setPrivate(factory.generatePrivate(new PKCS8EncodedKeySpec(Utils.decode(privateKey))));
    }

    public AsymmetricKeyBuilder setPrivate(PrivateKey privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public AsymmetricKeys build() throws Exception {
        return new AsymmetricKeys(publicKey, privateKey);
    }

}