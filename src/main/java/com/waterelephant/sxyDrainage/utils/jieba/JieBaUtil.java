//package com.waterelephant.sxyDrainage.utils.jieba;
//
//import com.waterelephant.utils.CommUtils;
//
//import java.security.KeyFactory;
//import java.security.Signature;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * (code:jb001)
// *
// * @Author: ZhangChong
// * @Description:
// * @Date: Created in 19:42 2018/6/12
// * @Modified By:
// */
//public class JieBaUtil {
//
//    /**
//     * 生成签名
//     *
//     * @param src 待签名字符串
//     * @return String
//     */
//    public static String getSign(String src, String priKey) throws Exception {
//
//        Signature sigEng = Signature.getInstance("SHA1withRSA");
//
//        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64Utils.decode(priKey));
//
//        KeyFactory fac = KeyFactory.getInstance("RSA");
//
//        RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);
//
//        sigEng.initSign(privateKey);
//
//        sigEng.update(src.getBytes("utf-8"));
//
//        byte[] signature = sigEng.sign();
//
//        return Base64Utils.encode(signature);
//    }
//
//    /**
//     * 验签
//     *
//     * @param sign 签名串
//     * @param src  待签名参数
//     * @return boolean
//     */
//    public static boolean checkSign(String sign, String src, String pubKey) throws Exception {
//
//        byte[] rsaBuffer = Base64Utils.decode(pubKey);
//
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(rsaBuffer);
//
//        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
//
//        Signature signature = Signature.getInstance("SHA1withRSA");
//
//        signature.initVerify(publicKey);
//
//        signature.update(src.getBytes("utf-8"));
//
//        return signature.verify(Base64Utils.decode(sign));
//    }
//
//    /**
//     * 处理待签名数据
//     *
//     * @param data 业务参数
//     * @return src
//     */
//    public static String handleData(Map<String, String> data) {
//
//        Collection<String> keyset = data.keySet();
//
//        List<String> list = new ArrayList<>(keyset);
//
//        Collections.sort(list);
//
//        StringBuilder sb = new StringBuilder();
//
//        for (String s : list) {
//            sb.append(s).append("=").append(data.get(s)).append("&");
//        }
//        int length = sb.length();
//        sb.delete(length - 1, length);
//        String src = sb.toString();
//        //去除转义符
//        //src = StringEscapeUtils.unescapeJava(src);
//        return src;
//    }
//
//    /**
//     * 获取待签名字符串
//     *
//     * @param bizData   参数
//     * @param timestamp 时间戳
//     * @return String
//     */
//    public static String getSignStr(String bizData, String timestamp) {
//        Map<String, String> params = new HashMap<>(16);
//        params.put("sign_type", "RSA");
//        params.put("app_id", JieBaConstant.appId);
//        params.put("version", "1.0");
//        params.put("format", "json");
//        params.put("timestamp", timestamp);
//        params.put("biz_data", bizData);
//        return handleData(params);
//    }
//
//    /**
//     * 获取用户的行业
//     *
//     * @param number 数值
//     * @return String
//     */
//    public static String getWorkType(Integer number) {
//        String workType;
//        if (CommUtils.isNull(number)) {
//            return "其他类型";
//        }
//        int num = 10;
//        if (number >= 0 && number <= num) {
//            String[] workTypes = {"工人", "教师", "白领", "学生", "创业者", "个体户", "公司职员", "企业法人", "网店店主", "暂无职业", "其他"};
//            workType = workTypes[number];
//        } else {
//            workType = "其他";
//        }
//        return workType;
//    }
//
//    /**
//     * 检查是否为中文
//     */
//    public static boolean checkChinese(String text) {
//        if (CommUtils.isNull(text)) {
//            return false;
//        }
//        String patternString = "[\u4e00-\u9fa5]";
//        Pattern pattern = Pattern.compile(patternString);
//        Matcher matcher = pattern.matcher(text);
//        return matcher.find();
//    }
//
//    /**
//     * 绑卡 转换借吧银行编码为富友
//     *
//     * @param bankcode 银行编码
//     * @return String
//     */
//    public static String convertBankCodeToFuYou(String bankcode) {
//        String res;
//        switch (bankcode) {
//            case "ICBC":
//                res = "0102";
//                break;
//            case "BOC":
//                res = "0104";
//                break;
//            case "CCB":
//                res = "0105";
//                break;
//            case "CITIC":
//                res = "0302";
//                break;
//            case "CEB":
//                res = "0303";
//                break;
//            case "GDB":
//                res = "0306";
//                break;
//            case "PAB":
//                res = "0307";
//                break;
//            case "CIB":
//                res = "0309";
//                break;
//            case "SPDB":
//                res = "0310";
//                break;
//            case "PSBC":
//                res = "0403";
//                break;
//            case "ABC":
//                res = "0103";
//                break;
//            case "CMBC":
//                res = "0305";
//                break;
//            case "HXB":
//                res = "0304";
//                break;
//            default:
//                res = null;
//        }
//        return res;
//    }
//
//    public static void main(String[] args) throws Exception {
//        //待加密数据
//        Map<String, String> params = new HashMap<>(16);
//        params.put("sign_type", "RSA");
//        params.put("app_id", "0");
//        params.put("version", "1.0");
//        params.put("format", "json");
//        params.put("timestamp", "1529911704");
//        params.put("biz_data", "{\"id_card\":\"2102811987102*****\",\"user_mobile\":\"1801899****\",\"user_name\":\"\\u9648\\u660e\\u6d9b\",\"product_id\":99,\"user_md5\":\"7c7c978be61f12ece60908fb81ed218b\"}");
//
//        //生成签名
//        String singString = handleData(params);
//
//        System.out.println(singString);
//        String p = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAN4JejBktGnwJ82tVGW/RNNe4ZE6kVwiM2YIeKYoHGOtIlw1e/6yBaPxuEaN8bWkgCQsOaGQCW5XQDCrx0uJQoruT1WYX48nlgq820+pg5G+US2nyLBXRicVq0Vax71tSiIOAMag2/a9THm4jdVLxUjF23GNS1TLEBB8krBf1wSzAgMBAAECgYEA0tb9CGcYSmCZ61zj/5oYqP44A8gxr+NqG0GAD4eXLa+CKT1AeSJi6MLn0VeIZovGgpLcRuaXjgecFJuJq9izLaU79Ooo15CNUIxf5ZUlwfn+9T07KCe/v7W07S6bHKX2j6CJLTEE9j9RxhTQZbWbAo3I1sqXU1MOd/FhzNWqGEECQQDxNn1pf+Vr6oMguNUtGjbgpSkxQGZOpSzEA/iTmIWvkfY4xM8lsmSlxHbAsM1JeL6LRDSf3CwGvCEhte098+ZXAkEA66YMc1V4dmBT74ZIpA2kDonDHwaBo14srizEdDgn13Du0rrXOJx+CFwv9fX/uV6S0JwGHqQeW5qLrICwm1ODBQJAfkTbA9YqwAJEYGXBWlnlrlVnKdwfcj3vIDE/9+uY196duPv1wDMRkuE0lQw4eqSVgovSzHhSJ7hl06LLrWm3AwJBAJuz6NAgLmtRNMS0XrT/SmJMxn9uhiQS6sTfUaNVI4Yn2bWlvOVsCZ6ugwYq2CB8i9eI8EY4vNJcKGP/2DGZmZ0CQQDhbpWAPPxUZAPu4bw7LbFKxtvffZxWv4mFF9EplLS2ppL1NX8WFXoE0W3BaWP2K75kkQXLc3dVFr/U2sbb4/sQ";
//        String sign = getSign(singString, p);
//
//        System.out.println("签名为:" + sign);
//        String s = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDeCXowZLRp8CfNrVRlv0TTXuGROpFcIjNmCHimKBxjrSJcNXv+sgWj8bhGjfG1pIAkLDmhkAluV0Awq8dLiUKK7k9VmF+PJ5YKvNtPqYORvlEtp8iwV0YnFatFWse9bUoiDgDGoNv2vUx5uI3VS8VIxdtxjUtUyxAQfJKwX9cEswIDAQAB";
//        boolean t = checkSign(sign, singString, s);
//
//        System.out.println("验签结果为:" + t);
//
//
//        System.out.println(checkChinese("张fkd"));
//    }
//}
