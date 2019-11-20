package com.waterelephant.drainage.util.youyu;

import java.util.Random;

public interface DesService {

    String desEncrypt(String plain_data,String des_key);//加密

    String desDecrypt(String encry_data,String des_key);//解密

    default String getRandomDesKey(int keyLength) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer Keysb = new StringBuffer();
        for(int i = 0; i<keyLength; i++){
            int number = random.nextInt(base.length());
            Keysb.append(base.charAt(number));
        }
        return Keysb.toString();
    }

    int getDesLenth();
}
