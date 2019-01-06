package lsm.datastructures.crypto;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static lsm.datastructures.crypto.Constants.asymmetricKeyAlgorithm;
import static lsm.datastructures.crypto.Constants.asymmetricKeySize;

@SuppressWarnings("unused")
public class AsymmetricKeyBuilder {
    private Key
            privateKey = null,
            publicKey = null;

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
        return withPublic(pair.getPublic())
                .withPrivate(pair.getPrivate());
    }

    public AsymmetricKeyBuilder withPublic(String publicKey) throws InvalidKeySpecException {
        return withPublic(factory.generatePublic(new X509EncodedKeySpec(Utils.decode(publicKey))));
    }

    public AsymmetricKeyBuilder withPublic(PublicKey publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public AsymmetricKeyBuilder withPrivate(String privateKey) throws InvalidKeySpecException {
        return withPrivate(factory.generatePrivate(new PKCS8EncodedKeySpec(Utils.decode(privateKey))));
    }

    public AsymmetricKeyBuilder withPrivate(PrivateKey privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public AsymmetricKeys build() throws Exception {
        return new AsymmetricKeys(publicKey, privateKey);
    }

}
