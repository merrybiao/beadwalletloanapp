package com.waterelephant.bajie.util;

import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by woate on 2016/12/23.
 * 16:29
 */
public class MessageUtils {

    static Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd hh:mm:ss").create();

    public static String sign(String message, String secret) {
        return SignUtils.sign(message, secret, "UTF-8");
    }

    public  static boolean verify(String message, String sign, String secret) {
        return SignUtils.verify(message, sign, secret, "UTF-8");
    }

    public static String encrypt(String planText, String secret) {
        try {
            return AesUtils.encrypt(planText, secret, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String decrypt(String cipherText, String secret) {
        try {
            return AesUtils.decrypt(cipherText, secret, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
