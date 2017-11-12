package lsm.helpers.crypto;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Utils {

    // Update these as need be for newer and better algorithms
    private static final char separator = ';';
    private static final String PRNGAlgorithm = "SHA1PRNG";
    private static final String UTF8 = "UTF-8";
    private static SecureRandom random;

    static {
        try {
            random = SecureRandom.getInstance(PRNGAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String joinStrings(String... strings) {
        return joinStrings(separator, strings);
    }
    public static String joinStrings(char separator, String... strings) {
        return String.join(String.valueOf(separator), strings);
    }

    public static String[] splitStrings(String string) {
        return splitStrings(separator, string);
    }
    public static String[] splitStrings(char separator, String string) {
        return string.split(String.valueOf(separator));
    }


    static String asString(byte[] arr) throws UnsupportedEncodingException {
        return new String(arr, UTF8);
    }
    static byte[] asBytes(String text) throws UnsupportedEncodingException {
        return text.getBytes(UTF8);
    }

    static byte[] randomBytes(int length) {
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }

    public static int randomInt(){
        return random.nextInt();
    }
    public static long randomLong(){
        return random.nextLong();
    }

    static String toBase64(byte[] arr) {
        return Base64.getEncoder().encodeToString(arr);
    }
    static byte[] fromBase64(String str) {
        return Base64.getDecoder().decode(str);
    }

    public static String toHex(byte[] arr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : arr) sb.append(String.format("%02x", b));
        return sb.toString();
    }
    public static byte[] fromHex(String str) {
        byte[] arr = new byte[str.length() / 2];
        for (int i = 0, j = 0; i < arr.length; i++, j += 2)
            arr[i] = (byte) (Character.digit(str.charAt(j), 16) * 16 + Character.digit(str.charAt(j + 1), 16));
        return arr;
    }
}
