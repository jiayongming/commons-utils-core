package com.commons.util.encrypt;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Secure Hash Algorithm，安全散列算法
 */
public class SecureHA {

    public static final String KEY_SHA = "SHA";

    public static String getResult(String inputStr) {
        byte[] inputData = inputStr.getBytes();
        BigInteger sha = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA);
            messageDigest.update(inputData);
            sha = new BigInteger(messageDigest.digest());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sha == null ? null : sha.toString(32);
    }


}
