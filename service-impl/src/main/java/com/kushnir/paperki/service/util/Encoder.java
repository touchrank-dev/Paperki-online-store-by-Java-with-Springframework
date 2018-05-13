package com.kushnir.paperki.service.util;

import java.math.BigInteger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoder {

    public static String encoding(String input) {
        String str;
        if(input.isEmpty()) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            str = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            str = null;
        }
        return str;
    }

    public static boolean matching(String orig, String compare){
        String md5;
        try{
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(compare.getBytes());
            md5 = new BigInteger(1, digest.digest()).toString(16);
            return md5.equals(orig);
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

}
