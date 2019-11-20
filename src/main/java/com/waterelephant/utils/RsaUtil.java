package com.waterelephant.utils;

import java.nio.charset.Charset;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.waterelephant.utils.StringUtil.byteArrayToHexString;

/**
 * RSA加签、验证，推荐使用，不要再各个渠道写一个了啊。
 *
 * @author xanthuim
 */
public class RsaUtil {
    /**
     * 指定加密算法为RSA
     */
    public static final String KEY_ALGORTHM = "RSA";
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    /**
     * 密钥长度，用来初始化
     */
    public static final int KEY_SIZE = 1024;
    /**
     * 指定公钥存放文件
     */
    public static final String PUBLIC_KEY = "RSAPublicKey";
    /**
     * 指定私钥存放文件
     */
    public static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        /** RSA算法要求有一个可信任的随机数源 */
        SecureRandom secureRandom = new SecureRandom();
        /** 为RSA算法创建一个KeyPairGenerator对象 */
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORTHM);
        /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
        keyPairGenerator.initialize(KEY_SIZE, secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 取得公钥，并转化为String类型
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 取得私钥，并转化为String类型
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param privateKey 私钥
     * @param data       加密数据
     * @return
     * @throws Exception
     */
    public static String sign(String privateKey, String data) {
        try {
            /**解密私钥**/
            byte[] keyBytes = decryptBASE64(privateKey);
            // 构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            // 指定加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
            // 取私钥匙对象
            PrivateKey privateSignKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateSignKey);
            signature.update(data.getBytes(Charset.defaultCharset()));
            return encryptBASE64(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 校验数字签名
     *
     * @param publicKey 公钥
     * @param data      加密数据
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(String publicKey, String data, String sign) {
        try {
            // 解密公钥
            byte[] keyBytes = decryptBASE64(publicKey);
            // 构造X509EncodedKeySpec对象
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            // 指定加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
            // 取公钥匙对象
            PublicKey publicSignKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicSignKey);
            signature.update(data.getBytes(Charset.defaultCharset()));
            // 验证签名是否正常
            return signature.verify(decryptBASE64(sign));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return Base64.getDecoder().decode(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return Base64.getEncoder().encodeToString(key);
    }

    /**
     * MD5
     *
     * @param data
     * @return
     */
    public static String md5(String data) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] b = data.getBytes(Charset.defaultCharset());
            md5.update(b, 0, b.length);
            return byteArrayToHexString(md5.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
