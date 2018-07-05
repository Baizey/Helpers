package lsm.datastructures.crypto;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static lsm.datastructures.crypto.Constants.asymmetricKeyAlgorithm;
import static lsm.datastructures.crypto.Constants.asymmetricKeySize;
import static lsm.datastructures.crypto.Utils.*;

@SuppressWarnings("WeakerAccess")
public class AsymmetricKeys extends AbstractKey {
    private Key privateKey, publicKey;

    public AsymmetricKeys() throws Exception {
        super(asymmetricKeyAlgorithm);
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(asymmetricKeyAlgorithm);
        keygen.initialize(asymmetricKeySize);
        KeyPair pair = keygen.generateKeyPair();
        publicKey = pair.getPublic();
        privateKey = pair.getPrivate();
    }

    public AsymmetricKeys(Key publicKey, Key privateKey) throws Exception {
        super(asymmetricKeyAlgorithm);
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public AsymmetricKeys(String publicKey, String privateKey) throws Exception {
        super(asymmetricKeyAlgorithm);
        setPublicKey(publicKey);
        setPrivateKey(privateKey);
    }

    public void setPublicKey(String key) throws Exception {
        setPublicKey(key == null ? null : KeyFactory.getInstance(asymmetricKeyAlgorithm).generatePublic(new X509EncodedKeySpec(fromBase64(key))));
    }

    public void setPrivateKey(String key) throws Exception {
        setPrivateKey(key == null ? null : KeyFactory.getInstance(asymmetricKeyAlgorithm).generatePrivate(new PKCS8EncodedKeySpec(fromBase64(key))));
    }

    public void setPublicKey(Key key) {
        this.publicKey = key;
    }

    public void setPrivateKey(Key key) {
        this.privateKey = key;
    }

    public void newKeySet() throws Exception {
        AsymmetricKeys temp = new AsymmetricKeys();
        this.publicKey = temp.publicKey;
        this.privateKey = temp.privateKey;
    }

    @Override
    public String encrypt(String data) throws Exception {
        SymmetricKey symmetricKey = new SymmetricKey();
        return joinStrings(
                encrypt(symmetricKey.keyString(), publicKey),
                symmetricKey.encrypt(data)
        );
    }

    @Override
    public String decrypt(String data) throws Exception {
        String[] parts = splitStrings(data);
        String encryptedKey = parts[0];
        String msg = parts[1];
        return new SymmetricKey(decrypt(encryptedKey, privateKey)).decrypt(msg);
    }

    public Key publicKey() {
        return publicKey;
    }

    public Key privateKey() {
        return privateKey;
    }

    public String publicKeyString() {
        return toBase64(publicKey.getEncoded());
    }

    public String privateKeyString() {
        return toBase64(privateKey.getEncoded());
    }
}
