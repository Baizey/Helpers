package lsm.datastructures.crypto;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import static lsm.datastructures.crypto.Utils.*;

@SuppressWarnings({"unused"})
public class Hash {

    private static int hashingIterations = Constants.defaultHashingIterations;
    private static SecureRandom random = null;
    private static SecretKeyFactory secret = null;
    private static int hashSizeInBits = Constants.hashingHashSize * 8;

    static {
        try {
            secret = SecretKeyFactory.getInstance(Constants.hashingAlgorithm);
            random = SecureRandom.getInstance(Constants.PRNGAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void setDefaultHashingIterations(int iterations) {
        hashingIterations = iterations;
    }

    public static int getDefaultHashingIterations() {
        return hashingIterations;
    }

    public static String hash(String toHash) throws Exception {
        return hash(toHash, hashingIterations);
    }

    public static String hash(String toHash, int hashingIterations) throws Exception {
        var salt = new byte[Constants.hashingSaltSize];
        random.nextBytes(salt);
        var hash = secret.generateSecret(new PBEKeySpec(toHash.toCharArray(), salt, hashingIterations, hashSizeInBits)).getEncoded();
        return join(Integer.toString(hashingIterations), encodeToString(salt), encodeToString(hash));
    }

    public static boolean validate(String toTest, String fullHash) throws Exception {
        var parts = split(fullHash);
        var iterations = Integer.parseInt(parts[0]);
        var salt = decode(parts[1]);
        var hash = decode(parts[2]);
        var testHash = secret.generateSecret(new PBEKeySpec(toTest.toCharArray(), salt, iterations, hashSizeInBits)).getEncoded();
        return Arrays.equals(testHash, hash);
    }
}

