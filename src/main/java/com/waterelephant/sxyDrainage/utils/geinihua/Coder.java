//package com.waterelephant.sxyDrainage.utils.geinihua;
//
//import java.io.IOException;
//
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
//
///**
// * @author xanthuim
// */
//public class Coder {
//    /**
//     * BASE64解密
//     *
//     * @param key
//     * @return
//     */
//    public static byte[] decryptBASE64(String key) {
//        try {
//            return new BASE64Decoder().decodeBuffer(key);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * BASE64加密
//     *
//     * @param key
//     * @return
//     * @throws Exception
//     */
//    public static String encryptBASE64(byte[] key) {
//        return new BASE64Encoder().encodeBuffer(key);
//    }
//
//}
