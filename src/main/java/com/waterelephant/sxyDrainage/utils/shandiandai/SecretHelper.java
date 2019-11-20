//package com.waterelephant.sxyDrainage.utils.shandiandai;
//
//import org.apache.commons.codec.binary.Base64;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import sun.misc.BASE64Decoder;
//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.nio.charset.Charset;
//import java.security.InvalidKeyException;
//import java.security.KeyFactory;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//
///**
// * 掌众金融平台API 加密、解密、加签、验签
// *
// * @author xinzhixuan
// * @version V1.0
// * @date 2017/11/7 14:14
// */
//public class SecretHelper {
//    // 本地解密私钥,解密所有接收到的数据
//    private static final String PRIVATE_KEY =
//            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC/06HCUhzZFiYvY/UsBMkshFK2pFVB8YqRBoXfv/rknmBd6d6jWdX2Mu2KCaQrN4b8IIEBdo3k2X/WP6YauGR2qyYP6ydYJYSKJy9cw1hA0CdMwvEZHra+Qjvv6HfAk539XiIVlIJY+HaDZZ22x+VGUJnymi5Wh7rIJrhpQOA2gblplek1mYESCrOB8OTV5tOjMzqeshLnwks/QwXU3egnWaxMskCEweWKt6NZjDrecZ733hUtgIYrPHcs79cYazSGNbJrhGt/wcoWRKBc3FOId7wyQWFWUWXki5MN5fSWklO12HAMtZAaA6jZ3W9YaZboyeLCRYAh5q3eWSTEqxT3AgMBAAECggEBAICZwLgllHF1B+R9fsAj9TqC+3C2evHanKrdVKLMcccS5kgPodKhR2/mdJk2HXlboIMmx1XTxQzHQ4y+3vzK4Y8s7jd9zsKpZFG6M2ZdD61j3vT3O+s7TAgBH22Wy7GvRRu3H+KXzUsYR1uPpEwFdw/MuWiLNMWN+Qp16uulMqtW4pZlSGZOSLuBc068leV1lWqzanohoA1S209fmd91sLbaOIEepb87ZJF1rYXgWcyfjTA8rFMilgUltPChsxZTeQxIWJqN8gGtjmlcplUA14RbrJsNf7BZCSAnKlXuF51Z5zOhQ9kCS1matJf+7m/2+xUDJ9b3YFCHijSq0AJBAgECgYEA7zzC917yc63sVmiXcTvM9SQoEIfWYWGCfZpMkobbqNQQovcNRkMml8YhHB6Mjzg2RZq/8fP0djQgZmOEsCPtHUzAIj+JTFvtJeS0OSzj9mho1Wscmwpsc/aJiAcaE2DQTYQs2p1B8/jp20yx3XQdCADRqLGIkps99fBWW0rJRxkCgYEAzUR1KnPjAKe94ZKjQo2Ks/u6rgTjXzYmlPMdvPWKE88GwZPI7EjfJomzzqr55HEhp5T/BR0ej6P5+qe5oJGiXAbsHmsmffQZ6H0DWmfdQVA820WX2MSSYmaMVIGG0C0UOTQLeGHOA6J2wqDSCPLPkgGGIssCVtNuk2gznEdBDo8CgYEAuCf9rI3Z9FIdayZ37SIrU0jRzmfCDpQQ7r6n0uMck4kqhUfHYB6wxoQtWIfrixOqM0o8FvPDanoVtrtEmJIPJE7AV5yxA6Z/lnvGf3yevj8A88fP7UjI0zskAyH0YJZVwlXMcTw8WKFy4uzWvbFz54U53rFKgBAgUgD/eKbI6YECgYAhImLTZn+HNIyXJAeMj7KLGSBqRMm7Lc+OjawIO459YhbSGpv9YEn9O4811i2ebZCkL4328ltjjnGkv3xcagXOWyO0SVGeVU/1UnC/IDL7/wrDznkTea+ziKAwZ/QuGIjCLx+G4CrUNV58Tl95KFDPXcWcSi7nexvHWbOzD2F2CwKBgDlNx1i81vtDx29D5X8/jmmBzkzFqv+crDx0VybIu8YD8bMXbgwQgappbEglbhPIIP4aFDq9lcdd7H3cUuF0jj4recBDlWs50OdpLWXL5ajWr3j4A2Q7RCX53cCRyzwgL+0nN7mo9jmqtF6R9jPEa+BnCX/Y53qdkAznHu4QMIHr";
//    private static Logger logger = LoggerFactory.getLogger(SecretHelper.class);
//    private static PrivateKey priKey;
//    private static int MAX_EN_LENGTH = 2048 / 8 - 11; // 最大加密明文长度
//    private static int MAX_DE_LENGTH = 2048 / 8; // 最大解密密文长度
//
//    static {
//        try {
//            BASE64Decoder base64Decoder = new BASE64Decoder();
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            // byte[] buffer = base64Decoder.decodeBuffer(PUBLIC_KEY);
//            // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
//            // pubKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
//
//            byte[] buffer2 = base64Decoder.decodeBuffer(PRIVATE_KEY);
//            PKCS8EncodedKeySpec keySpec2 = new PKCS8EncodedKeySpec(buffer2);
//            priKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec2);
//        } catch (NoSuchAlgorithmException e) {
//            logger.error("RSA公钥/秘钥初始化-无此算法", e);
//        } catch (InvalidKeySpecException e) {
//            logger.error("RSA公钥/秘钥初始化-公钥/秘钥非法", e);
//        } catch (IOException e) {
//            logger.error("RSA公钥/秘钥初始化-公钥/秘钥数据内容读取错误", e);
//        } catch (NullPointerException e) {
//            logger.error("RSA公钥/秘钥初始化-公钥/秘钥数据为空", e);
//        }
//    }
//
//    public SecretHelper(String publicKey) {
//        initPublicKey(publicKey);
//    }
//
//    private PublicKey pubKey;
//
//    public static void main(String[] args) throws Exception {
//        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
//        /*
//         * KeyPairGenerator keyPairGen = null; try { keyPairGen = KeyPairGenerator.getInstance("RSA"); }
//         * catch (NoSuchAlgorithmException e) { e.printStackTrace(); } // 初始化密钥对生成器，密钥大小为96-1024位
//         * keyPairGen.initialize(2048, new SecureRandom()); // 生成一个密钥对，保存在keyPair中 KeyPair keyPair =
//         * keyPairGen.generateKeyPair(); // 得到私钥 RSAPrivateKey privateKey = (RSAPrivateKey)
//         * keyPair.getPrivate(); // 得到公钥 RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic(); try {
//         * // 得到公钥字符串 String publicKeyString = Base64.encodeBase64String(publicKey.getEncoded()); // 得到私钥字符串
//         * String privateKeyString = Base64.encodeBase64String(privateKey.getEncoded());
//         * System.out.println("公钥:" + publicKeyString); System.out.println("私钥:" + privateKeyString); }
//         * catch (Exception e) { e.printStackTrace(); }
//         */
//        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv9OhwlIc2RYmL2P1LATJLIRStqRVQfGKkQaF37/65J5gXeneo1nV9jLtigmkKzeG/CCBAXaN5Nl/1j+mGrhkdqsmD+snWCWEiicvXMNYQNAnTMLxGR62vkI77+h3wJOd/V4iFZSCWPh2g2WdtsflRlCZ8pouVoe6yCa4aUDgNoG5aZXpNZmBEgqzgfDk1ebTozM6nrIS58JLP0MF1N3oJ1msTLJAhMHlirejWYw63nGe994VLYCGKzx3LO/XGGs0hjWya4Rrf8HKFkSgXNxTiHe8MkFhVlFl5IuTDeX0lpJTtdhwDLWQGgOo2d1vWGmW6MniwkWAIeat3lkkxKsU9wIDAQAB";
//        SecretHelper helper = new SecretHelper(publicKey);
//        String SECRET_KEY = "1234456";
//        System.out.println("加密：" + helper.encrypt("{\"uid\":\"000001\"}"));
//        String enStr = "AnhBdc6iX6nUXErN0V0et78skExeuhkG6hZHPcqrZScmDloQZJnSWIVhYEnC0T6hhWaJi0cMfbYE07gtq/T2L3I/vkncbMqcasTkNRBa/5IwOJLdFvcM+TWh+YaNpgB3ujplCp1bRR82XJPJdwglfyWmNE0A3JLM/k3DVZ2UU8XyYrrSVFilLoesth/YCpoJTSJy6niH66FsGAFEKhufUxMEHv1SBxCXMGtbhpdb83bZ2IbWY0VORgexmnC+sCXN44XLgq0SeGiGEwZ4kZTJruGBspHtAgZWbN3a5z+BpkVPmfm37xPhzQcF9jP8mEfNbiK4pFQuPwDTjnfInTWfVA==";
//        System.out.println("解密：" + decrypt(enStr));
//        System.out.println("签名：" + sign(enStr, SECRET_KEY));
//        System.out.println("验签：" + signCheck(enStr, SECRET_KEY, "586f755d479e923aab7b37604d0cb3a2"));
//
//        // System.out.println("加密：" + helper.encrypt("{}"));
//        // enStr =
//        // "IGPJ7SxzoHW2thx6NTMpHI7n+aICMB13m+FxAv5RmifvglIYa398QMhzAhirHBq8VKOeTQCcR7p0GEPwsi2C315UFhOHaSJx6pWMV6UL+g2umeuA/EnNPAiX4BNlxthvSInJ/MWwVRaa6S5ohD8IoHn4nUPdzApZgOB00DqNSB14PiGI9Uo7RSzbHdZtL7vumVzqwTHvNrS5VNgRCW/9KDvFwvlug5o7EubQ69vd035C+YOkMZx70UFxbwJmqeto9WReUzq2oU8FnW2FgDvGIASy7qA+pRuBmT8Ef+QqxdSPeqZf/XJeEIEtHvx3/V/6PtO2SrskVceDIpNz/UiNGQ==";
//        // System.out.println("解密：" + decrypt(enStr));
//        // System.out.println("签名：" + sign(enStr, SECRET_KEY));
//        // System.out.println("验签：" + signCheck(enStr, SECRET_KEY, "1a254f7babbc2d29faad649871becc3d"));
//        String sd6q45rmrq4rbzfpi = sign("{\"phone\":\"13611111111\",\"name\":\"张三\",\"idCard\":\"620510199514124586\"}", "sd6q45rmrq4rbzfpi");
//        System.out.println("sign=" + sd6q45rmrq4rbzfpi);
//
//    }
//
//    /**
//     * 解密，无编码格式
//     *
//     * @param srcData 待解密字符串.
//     * @return 解密后值.
//     */
//    public static String decrypt(String srcData) {
//        return decrypt(srcData, null);
//    }
//
//    /**
//     * 字符串形式解密
//     *
//     * @param srcData 待解密字符串
//     * @param charSet 字符集编码
//     * @return 解密后值
//     */
//    public static String decrypt(String srcData, String charSet) {
//        try {
//            if (charSet == null) {
//                return new String(decrypt(Base64.decodeBase64(srcData.getBytes())));
//            } else {
//                return new String(decrypt(Base64.decodeBase64(srcData.getBytes(charSet))), charSet);
//            }
//        } catch (UnsupportedEncodingException e) {
//            logger.error("编码格式错误", e);
//        }
//        return null;
//    }
//
//    /**
//     * 私钥解密过程
//     *
//     * @param data 密文数据
//     * @return 明文
//     */
//    public static byte[] decrypt(byte[] data) {
//        if (priKey == null) {
//            logger.error("解密私钥为空, 请设置");
//            return null;
//        }
//        Cipher cipher;
//        try {
//
//            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//            // 计算分段解密的block数 (理论上应该能整除)
//            int nBlock = (data.length / MAX_DE_LENGTH);
//            // 输出buffer, , 大小为nBlock个encryptBlock
//            ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * MAX_EN_LENGTH);
//            cipher.init(Cipher.DECRYPT_MODE, priKey);
//            // 分段解密
//            for (int offset = 0; offset < data.length; offset += MAX_DE_LENGTH) {
//                // block大小: decryptBlock 或 剩余字节数
//                int inputLen = (data.length - offset);
//                if (inputLen > MAX_DE_LENGTH) {
//                    inputLen = MAX_DE_LENGTH;
//                }
//                // 得到分段解密结果
//                byte[] decryptedBlock = cipher.doFinal(data, offset, inputLen);
//                // 追加结果到输出buffer中
//                outbuf.write(decryptedBlock);
//            }
//            outbuf.flush();
//            outbuf.close();
//            return outbuf.toByteArray();
//        } catch (NoSuchAlgorithmException e) {
//            logger.error("无此解密算法", e);
//        } catch (NoSuchPaddingException e) {
//            logger.error("无此填充机制", e);
//        } catch (InvalidKeyException e) {
//            logger.error("解密私钥非法,请检查", e);
//        } catch (IllegalBlockSizeException e) {
//            logger.error("密文长度非法", e);
//        } catch (BadPaddingException e) {
//            logger.error("密文数据已损坏", e);
//        } catch (IOException e) {
//            logger.error("解密异常", e);
//        }
//        return null;
//    }
//
//    /**
//     * MD5签名
//     *
//     * @param content 待签名数据
//     * @return 签名值
//     */
//    public static String sign(String content, String secretKey) {
//        try {
//            return md5(content + "&secretKey=" + secretKey);
//        } catch (Exception e) {
//            logger.error("MD5签名异常", e);
//        }
//        return null;
//    }
//
//    public static String md5(String str) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] input = str.getBytes(Charset.forName("utf-8"));
//            byte[] buff = md.digest(input);
//            return bytesToHex(buff).toLowerCase();
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    /**
//     * 二进制到16进制
//     *
//     * @param bytes
//     * @return
//     */
//    public static String bytesToHex(byte[] bytes) {
//        StringBuilder md5str = new StringBuilder();
//        int digital;
//        for (byte aByte : bytes) {
//            digital = aByte;
//
//            if (digital < 0) {
//                digital += 256;
//            }
//            if (digital < 16) {
//                md5str.append("0");
//            }
//            md5str.append(Integer.toHexString(digital));
//        }
//        return md5str.toString();
//    }
//
//    /**
//     * MD5加密
//     *
//     * @param str .
//     * @return .
//     */
//    /*
//     * public static String MD5(String str) { MessageDigest md5 = null; try { md5 =
//     * MessageDigest.getInstance("MD5"); } catch (Exception e) { System.out.println(e.toString());
//     * e.printStackTrace(); return ""; } char[] charArray = str.toCharArray(); byte[] byteArray = new
//     * byte[charArray.length];
//     *
//     * for (int i = 0; i < charArray.length; i++) byteArray[i] = (byte) charArray[i]; byte[] md5Bytes =
//     * md5.digest(byteArray); StringBuffer hexValue = new StringBuffer(); for (int i = 0; i <
//     * md5Bytes.length; i++) { int val = ((int) md5Bytes[i]) & 0xff; if (val < 16) hexValue.append("0");
//     * hexValue.append(Integer.toHexString(val)); } return hexValue.toString();
//     *
//     * }
//     */
//
//    /**
//     * 验签操作
//     *
//     * @param content 待签名数据.
//     * @param secretKey .
//     * @param signStr 签名.
//     * @return .
//     */
//    public static boolean signCheck(String content, String secretKey, String signStr) {
//        if (signStr == null) {
//            return false;
//        }
//        try {
//            return signStr.equals(sign(content, secretKey));
//        } catch (Exception e) {
//            logger.error("MD5验证签名异常", e);
//        }
//        return false;
//    }
//
//    /**
//     * 加密，无编码格式
//     *
//     * @param srcData 待加密字符串
//     * @return .
//     */
//    public String encrypt(String srcData) {
//        return encrypt(srcData, null);
//    }
//
//    /**
//     * 字符串形式加密
//     *
//     * @param srcData 待加密字符串.
//     * @return .
//     */
//    public String encrypt(String srcData, String charSet) {
//        try {
//            if (charSet == null) {
//                return Base64.encodeBase64String(encrypt(srcData.getBytes()));
//            } else {
//                return Base64.encodeBase64String(encrypt(srcData.getBytes(charSet)));
//            }
//        } catch (UnsupportedEncodingException e) {
//            logger.error("编码格式错误", e);
//        }
//        return null;
//    }
//
//    /**
//     * 公钥加密过程
//     *
//     * @param plainBytes 明文数据
//     * @return .
//     * @throws Exception 加密过程中的异常信息
//     */
//    public byte[] encrypt(byte[] plainBytes) {
//        if (pubKey == null) {
//            logger.error("加密公钥为空, 请设置");
//            return null;
//        }
//        Cipher cipher;
//        try {
//            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//            // 计算分段加密的block数 (向上取整)
//            int nBlock = (plainBytes.length / MAX_EN_LENGTH);
//            if ((plainBytes.length % MAX_EN_LENGTH) != 0) { // 余数非0，block数再加1
//                nBlock += 1;
//            }
//            // 输出buffer, 大小为nBlock个decryptBlock
//            ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * MAX_DE_LENGTH);
//            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
//            // 分段加密
//            for (int offset = 0; offset < plainBytes.length; offset += MAX_EN_LENGTH) {
//                // block大小: encryptBlock 或 剩余字节数
//                int inputLen = (plainBytes.length - offset);
//                if (inputLen > MAX_EN_LENGTH) {
//                    inputLen = MAX_EN_LENGTH;
//                }
//                // 得到分段加密结果
//                byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
//                // 追加结果到输出buffer中
//                outbuf.write(encryptedBlock);
//            }
//            return outbuf.toByteArray();
//        } catch (NoSuchAlgorithmException e) {
//            logger.error("无此加密算法", e);
//        } catch (NoSuchPaddingException e) {
//            logger.error("加密异常", e);
//        } catch (InvalidKeyException e) {
//            logger.error("加密公钥非法,请检查", e);
//        } catch (IllegalBlockSizeException e) {
//            logger.error("明文长度非法", e);
//        } catch (BadPaddingException e) {
//            logger.error("明文数据已损坏", e);
//        } catch (IOException e) {
//            logger.error("加密异常，写数据异常！", e);
//        }
//        return null;
//    }
//
//    private void initPublicKey(String publickey) {
//        try {
//            BASE64Decoder base64Decoder = new BASE64Decoder();
//            byte[] buffer = base64Decoder.decodeBuffer(publickey);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
//            pubKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
//        } catch (NoSuchAlgorithmException e) {
//            logger.error("RSA公钥/秘钥初始化-无此算法", e);
//        } catch (InvalidKeySpecException e) {
//            logger.error("RSA公钥/秘钥初始化-公钥/秘钥非法", e);
//        } catch (IOException e) {
//            logger.error("RSA公钥/秘钥初始化-公钥/秘钥数据内容读取错误", e);
//        } catch (NullPointerException e) {
//            logger.error("RSA公钥/秘钥初始化-公钥/秘钥数据为空", e);
//        }
//    }
//
//
//}
