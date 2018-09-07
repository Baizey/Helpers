package lsm.datastructures.crypto;

import java.util.Base64;

import static lsm.datastructures.crypto.Constants.separator;

class Utils {

    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static final Base64.Encoder encoder = Base64.getEncoder();

    static String join(String... strings) {
        return String.join(separator, strings);
    }

    static String[] split(String string) {
        return string.split(separator);
    }

    static byte[] encode(byte[] arr) {
        return encoder.encode(arr);
    }

    static String encodeToString(byte[] arr) {
        return encoder.encodeToString(arr);
    }

    static byte[] decode(byte[] arr) {
        return decoder.decode(arr);
    }

    static byte[] decode(String str) {
        return decoder.decode(str);
    }
}
