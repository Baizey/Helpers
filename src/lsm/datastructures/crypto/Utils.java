package lsm.datastructures.crypto;

import lsm.helpers.interfaces.Alter;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Utils {

    // Update these as need be for newer and better datastructures
    private static final String separator = ";";
    private static final String PRNGAlgorithm = "SHA1PRNG";
    private static SecureRandom random;

    static {
        try {
            random = SecureRandom.getInstance(PRNGAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    static String joinStrings(String... strings) {
        return String.join(separator, strings);
    }

    static String[] splitStrings(String string) {
        return string.split(separator);
    }

    static String alterBytes(String text, Alter<byte[]> action) throws Exception {
        return new String(action.alter(text.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    static byte[] randomBytes() {
        byte[] bytes = new byte[Constants.hashingSaltSize];
        random.nextBytes(bytes);
        return bytes;
    }

    static String toBase64(byte[] arr) {
        return Base64.getEncoder().encodeToString(arr);
    }

    static byte[] fromBase64(String str) {
        return Base64.getDecoder().decode(str);
    }
}
