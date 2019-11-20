//package com.waterelephant.sxyDrainage.utils.jieba;
//
//
//import org.apache.commons.codec.binary.Base64;
//
///**
// * (code:jb001)
// *
// * @Author: ZhangChong
// * @Description:
// * @Date: Created in 19:49 2018/6/12
// * @Modified By:
// */
//public class Base64Utils {
//
//    /**
//     * <p>
//     * BASE64字符串解码为二进制数据
//     * </p>
//     *
//     * @param base64 BASE64字符串
//     * @return byte
//     */
//    public static byte[] decode(String base64) throws Exception {
//        return new Base64().decode(base64);
//    }
//
//    /**
//     * <p>
//     * 二进制数据编码为BASE64字符串
//     * </p>
//     *
//     * @param bytes 二进制数据
//     * @return String
//     */
//    public static String encode(byte[] bytes) throws Exception {
//        return new Base64().encodeToString(bytes);
//    }
//
//    public static void main(String[] args) {
//        String s = "zhangchon";
//        try {
//            System.out.println(encode(s.getBytes()));
//            System.out.println(new String(decode(encode(s.getBytes()))));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
