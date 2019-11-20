//package com.waterelephant.sxyDrainage.utils;
//
//import com.waterelephant.sxyDrainage.exception.BorrowerException;
//
//import java.util.Base64;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.SecretKeySpec;
//
///**
// * AES之ECB模式，注意key的长度在不同Java版本是不一样的，比如1.8_144使用16位，1.8_161版本则可以使用32位
// *
// * @author xanthuim
// */
//public class AesEcbUtil {
//
//    /**
//     * 加密
//     *
//     * @param sSrc
//     * @param sKey
//     * @return
//     * @throws Exception
//     */
//    public static String encrypt(String sSrc, String sKey) throws Exception {
//        byte[] raw = sKey.getBytes("utf-8");
//        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//        //"算法/模式/补码方式"
//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
//        //此处使用BASE64做转码功能，同时能起到2次加密的作用。
//        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
//        return Base64.getEncoder().encodeToString(encrypted);
//    }
//
//    /**
//     * 解密
//     *
//     * @param sSrc
//     * @param sKey
//     * @return
//     * @throws Exception
//     */
//    public static String decrypt(String sSrc, String sKey) throws Exception {
//        try {
//            byte[] raw = sKey.getBytes("utf-8");
//            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
//            //先用base64解密
//            byte[] encrypted1 = Base64.getDecoder().decode(sSrc);
//            byte[] original = cipher.doFinal(encrypted1);
//            String originalString = new String(original, "utf-8");
//            return originalString;
//        } catch (Exception ex) {
//            throw new BorrowerException("解密失败，请检查加密数据", -2);
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        /*
//         * 此处使用AES-128-ECB加密模式，key需要为16位
//         *
//         */
//        String cKey = "MIIBIjANBgkqhkiG";
//
//        // 解密
//        String src = "18672935910";
//        String encrypt = AesEcbUtil.encrypt(src, cKey);
//        System.out.println("待解密的字串是：" + encrypt);
//
//
//        String DesString = AesEcbUtil.decrypt(encrypt, cKey);
//        System.out.println("解密后的字串是：" + DesString);
//
//    }
//}
