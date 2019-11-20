package com.waterelephant.drainage.util.fqgj.jkzj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class KeyReader {

    private static final Logger log = LoggerFactory.getLogger(KeyReader.class);

    private static String salt = "DzkC6Um9kj+3zU1V";

    public static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception {
        RSAPublicKey publicKey = null;
        try {
            byte[] buffer = Base64Utils.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            log.error("无此算法");
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            log.error("公钥非法");
            throw new Exception("公钥非法");
        } catch (IOException e) {
            log.error("公钥数据内容读取错误");
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            log.error("公钥数据为空");
            throw new Exception("公钥数据为空");
        }
        return publicKey;
    }

    public static boolean doCheck(String content, byte[] sign, RSAPublicKey pubKey)
            throws SignatureException {
        try {
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes("utf-8"));
            return signature.verify((sign));
        } catch (Exception e) {
            log.error("RSA验证签名[content = " + content
                    + "; charset = " + "; signature = " + sign.toString() + "]发生异常!", e);
            throw new SignatureException("RSA验证签名[content = " + content
                    + "; charset = " + "; signature = " + sign.toString() + "]发生异常!", e);
        }
    }

    public static String generateSHA1withRSASigature(String src, String privateKeyStr) {
        try {
            Signature sigEng = Signature.getInstance("SHA1withRSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64Utils.decode(privateKeyStr));
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);
            sigEng.initSign(privateKey);
            sigEng.update(src.getBytes("utf-8"));
            byte[] signature = sigEng.sign();
            return Base64Utils.encode(signature);
        } catch (Exception e) {
            log.error("私钥签名出错",e);
            return null;
        }
    }

    public static String md5Sign(Map params){
        byte[] bytes = null;
        Map<String, String> sortedParams = new TreeMap<String, String>();
        sortedParams.putAll(params);
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append("=").append(param.getValue());
        }
        basestring.append(salt);
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(basestring.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException ex) {
            ex.printStackTrace();
        }
        // 将MD5输出的二进制结果转换为小写的十六进制
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }

    public static boolean md5verify(Map params,String targetSign){
        String sign = md5Sign(params);
        return sign.equals(targetSign)?true:false;
    }

}