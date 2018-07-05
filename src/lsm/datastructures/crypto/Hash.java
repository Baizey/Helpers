package lsm.datastructures.crypto;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;

import static lsm.datastructures.crypto.Constants.*;
import static lsm.datastructures.crypto.Utils.*;

@SuppressWarnings("WeakerAccess")
public class Hash {

    private static SecretKeyFactory secret = null;

    static {
        try {
            secret = SecretKeyFactory.getInstance(hashingAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String hash(String toHash) throws Exception {
        byte[] salt = randomBytes(hashingSaltSize);
        byte[] hash = secret.generateSecret(new PBEKeySpec(toHash.toCharArray(), salt, hashingIterations, hashingHashSize * 8)).getEncoded();
        return joinStrings(String.valueOf(hashingIterations), toBase64(salt), toBase64(hash));
    }

    public static boolean validate(String toTest, String fullHash) throws Exception {
        String[] parts = splitStrings(fullHash);
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromBase64(parts[1]);
        byte[] hash = fromBase64(parts[2]);

        byte[] test = secret.generateSecret(new PBEKeySpec(toTest.toCharArray(), salt, iterations, hash.length * 8)).getEncoded();

        int diff = hash.length ^ test.length;
        int min = Math.min(hash.length, test.length);
        for (int i = 0; i < min; i++)
            diff |= hash[i] ^ test[i];
        return diff == 0;
    }
}

