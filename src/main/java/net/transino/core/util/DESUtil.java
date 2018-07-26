//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.transino.core.util;

import java.nio.charset.Charset;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import org.apache.commons.codec.binary.Base64;

public class DESUtil {
    private static Key key;
    private static String KEY_STR = "veggieg";
    private static String ALGORITHM = "DES";

    public DESUtil() {
    }

    public static String getEncryptString(String str) {
        try {
            byte[] e = str.getBytes(Charset.forName("UTF-8"));
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(1, key);
            byte[] encryptBytes = cipher.doFinal(e);
            return new String(Base64.encodeBase64(encryptBytes), Charset.forName("UTF-8"));
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }

    public static String getDecryptString(String str) {
        try {
            byte[] e = Base64.decodeBase64(str);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(2, key);
            byte[] decryptBytes = cipher.doFinal(e);
            return new String(decryptBytes, Charset.forName("UTF-8"));
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }

    public static void main(String[] args) {
        System.out.println(getDecryptString("8KLg/DPra64="));
        System.out.println(getDecryptString("Aw5YKMDpHtM="));
    }

    static {
        try {
            KeyGenerator e = KeyGenerator.getInstance(ALGORITHM);
            e.init(new SecureRandom(KEY_STR.getBytes(Charset.forName("UTF-8"))));
            key = e.generateKey();
            e = null;
        } catch (Exception var1) {
            throw new RuntimeException(var1);
        }
    }
}
