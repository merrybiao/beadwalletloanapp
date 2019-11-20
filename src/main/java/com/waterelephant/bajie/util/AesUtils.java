package com.waterelephant.bajie.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Base64;

public class AesUtils {
    /**
     * 加密
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return 加密的字节数组
     */
    public static byte[] encrypt(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes("UTF-8"));
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("使用ASC加密发生错误");
            return null;
        }
    }

    /**
     * 加密
     * @param plaintext 需要加密的内容
     * @param encoding 编码集
     * @param password 密码
     * @return 加密的字符串
     * @throws UnsupportedEncodingException 无法转换的字符集
     */
    public static String encrypt(String plaintext, String password, String encoding) throws UnsupportedEncodingException {

        byte[] input = Base64.getEncoder().encode(plaintext.getBytes(encoding));
        byte[] results = encrypt(input, password);
        byte[] base64bytes = Base64.getEncoder().encode(results);
        String ciphertext = new String(base64bytes);
//        System.out.println("plaintext:"+plaintext+" ciphertext:"+ ciphertext);
        return ciphertext;
    }
    /**
     * 解密
     *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return 解密的字节数组
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes("UTF-8"));
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 解密
        } catch (Exception e) {
            System.out.println("使用ASC解密发生错误");
            return null;
        }
    }

    /**
     *
     * @param ciphertext 待解密内容
     * @param encoding 编码集
     * @param password 解密密钥
     * @return 加密的字符串
     * @throws UnsupportedEncodingException 无法转换的字符集
     */
    public static String decrypt(String ciphertext, String password, String encoding) throws UnsupportedEncodingException {
        byte[] input = Base64.getDecoder().decode(ciphertext.getBytes(encoding));
        byte[] results = decrypt(input, password);
        byte[] base64bytes = Base64.getDecoder().decode(results);
        String plaintext = new String(base64bytes);
//        System.out.println("plaintext:" + plaintext + " ciphertext:" + ciphertext);
        return plaintext;
    }

}
