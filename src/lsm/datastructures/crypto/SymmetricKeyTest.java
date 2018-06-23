package lsm.datastructures.crypto;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class SymmetricKeyTest {

    private static final String alpha = "1234567890qwertyuiopasdfghjklzxcvbnm";
    private static String makeString(int length) {
        Random random = new Random();
        char[] arr = new char[length];
        for(int i = 0; i < length; i++)
            arr[i] = alpha.charAt(random.nextInt(alpha.length()));
        return new String(arr);
    }

    @Test
    public void testMsg() throws Exception {
        SymmetricKey key = new SymmetricKey();
        String test = makeString(10);
        Assert.assertEquals(test, test, key.decrypt(key.encrypt(test)));
        test = makeString(2000);
        Assert.assertEquals(test, test, key.decrypt(key.encrypt(test)));
        test = makeString(1000000);
        Assert.assertEquals(test, test, key.decrypt(key.encrypt(test)));
    }
}
