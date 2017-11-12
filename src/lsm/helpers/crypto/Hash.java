package lsm.helpers.crypto;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;

import static lsm.helpers.crypto.Utils.*;

@SuppressWarnings("WeakerAccess")
public class Hash {

    // Update these as higher numbers are needed
    private static final String hashingAlgorithm = "PBKDF2WithHmacSHA512";
    private static final int iterations = 100000;   // 100,000
    private static final int hashSize = 64;         // Length of generated hash
    private static final int saltSize = 16;         // Byte length of salt

    private static SecretKeyFactory secret = null;
    static {
        try { secret = SecretKeyFactory.getInstance(hashingAlgorithm); }
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
    }

    public static String hash(String password) throws Exception {
        byte[] salt = randomBytes(saltSize);
        byte[] hash = secret.generateSecret(new PBEKeySpec(password.toCharArray(), salt, iterations, hashSize * 8)).getEncoded();
        return joinStrings(String.valueOf(iterations), toBase64(salt), toBase64(hash));
    }

    public static boolean validate(String password, String fullHash) throws Exception {
        String[] parts = splitStrings(fullHash);
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromBase64(parts[1]);
        byte[] hash = fromBase64(parts[2]);

        byte[] test = secret.generateSecret(new PBEKeySpec(password.toCharArray(), salt, iterations, hash.length * 8)).getEncoded();

        int diff = hash.length ^ test.length;
        int min = Math.min(hash.length, test.length);
        for(int i = 0; i < min; i++)
            diff |= hash[i] ^ test[i];
        return diff == 0;
    }
}

