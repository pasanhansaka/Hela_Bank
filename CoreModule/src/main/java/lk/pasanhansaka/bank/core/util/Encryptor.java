package lk.pasanhansaka.bank.core.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Encryptor {

    public static String encrypt(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes(), 0, password.length());

            BigInteger bi = new BigInteger(1, digest.digest());
            return bi.toString(16);

        } catch (Exception e) {
            throw new RuntimeException("Error while Encrypting password.", e);
        }
    }

}
