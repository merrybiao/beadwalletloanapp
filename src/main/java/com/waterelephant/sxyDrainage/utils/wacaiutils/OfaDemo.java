//package com.waterelephant.sxyDrainage.utils.wacaiutils;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import javax.xml.bind.DatatypeConverter;
//
///**
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/28
// * @since JDK 1.8
// */
//public class OfaDemo {
//
//
//    public static void main(String[] args) {
//        try {
//            String appId = "59fd4016a4e2590001acfc78";
//            String appSecretKey = "340ad68abbfb379d61afee16fcace6207c6aa271";
//
//            String method = "POST";
//            String body = "{ \"openId\": \"59f699d750d5f56b8fce5c9d\", \"limit\": 200 }";
//            String message = method + '\n' + body + '\n' + appId + '\n' + appSecretKey;
//
//            Mac hasher = Mac.getInstance("HmacSHA256");
//            hasher.init(new SecretKeySpec(appSecretKey.getBytes(), "HmacSHA256"));
//
//            byte[] hash = hasher.doFinal(message.getBytes());
//
//            // 获得十六进制形式的签名
//            String signature = DatatypeConverter.printHexBinary(hash).toLowerCase();
//
//            // 输出 6e608a434be077932058adb1434a5ec77dce73c9a56d0ab2074aed6baee073ea
//            System.out.println(signature);
//        } catch (Exception e) {
//            // 异常处理
//        }
//    }
//}
