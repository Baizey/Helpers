package lsm.datastructures.crypto;

import java.security.Key;
import java.security.KeyFactory;
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
        var keygen = KeyPairGenerator.getInstance(asymmetricKeyAlgorithm);
        keygen.initialize(asymmetricKeySize);
        var pair = keygen.generateKeyPair();
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

    public void newKeys() throws Exception {
        var temp = new AsymmetricKeys();
        this.publicKey = temp.publicKey;
        this.privateKey = temp.privateKey;
    }

    @Override
    public String encrypt(String data) throws Exception {
        SymmetricKey symmetricKey = new SymmetricKey();
        var key = encrypt(symmetricKey.key(), publicKey);
        var text = symmetricKey.encrypt(data);
        return joinStrings(key, text);
    }

    @Override
    public String decrypt(String data) throws Exception {
        var parts = splitStrings(data);
        var encryptedKey = parts[0];
        var msg = parts[1];
        return new SymmetricKey(decrypt(encryptedKey, privateKey)).decrypt(msg);
    }

    public String publicKey() {
        return toBase64(publicKey.getEncoded());
    }

    public String privateKey() {
        return toBase64(privateKey.getEncoded());
    }
}
