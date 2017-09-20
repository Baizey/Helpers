package lsm.helpers.crypto;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;


public class Hash {

    static final String textType = "UTF-8";

    private static final int iterations = 100000; // 100,000

    private static final String algoType = "PBKDF2WithHmacSHA512";
    private static final String prngType = "SHA1PRNG";
    private static final int saltSize = 16;

    private static SecretKeyFactory secret = null;
    private static SecureRandom random = null;

    static {
        try {
            secret = SecretKeyFactory.getInstance(algoType);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            random = SecureRandom.getInstance(prngType);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static byte[] generateRandom(int length) {
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }

    public static String hash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return hash(password, getSalt());
    }

    public static String hash(String password, byte[] salt) throws InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, password.length());
        byte[] hash = secret.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    public static boolean validate(String password, String fullHash) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        String[] parts = fullHash.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = parts[1].getBytes(textType);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, hash.length * 8);
        byte[] testHash = secret.generateSecret(spec).getEncoded();
        return toHex(testHash).equals(parts[2]);
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        return generateRandom(saltSize);
    }

    private static String toHex(byte[] arr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : arr)
            sb.append(String.format("%02X", b));
        return sb.toString().trim();
    }

    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int b = 0, c = 0; b < bytes.length; b++, c += 2)
            bytes[b] = (byte) (asByte(hex.charAt(c)) * 16 + asByte(hex.charAt(c + 1)));
        return bytes;
    }

    private static int asByte(char c) {
        return asByte((int) Character.toUpperCase(c));
    }

    private static int asByte(int c) {
        return c < 'A' ? c - '0' : 10 + c - 'A';
    }
}

