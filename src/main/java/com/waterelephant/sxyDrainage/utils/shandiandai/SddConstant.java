//package com.waterelephant.sxyDrainage.utils.shandiandai;
//
//import java.util.ResourceBundle;
//
///**
// * 闪电贷基础数据类
// *
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/4
// * @since JDK 1.8
// */
//public class SddConstant {
//
//    public static String CHANNEL_SDD = "";
//    public static String SECRET_KEY = "";
//    public static String PRODUCT_ID = "";
//    public static String BQS_URL = "";
//    public static String CHANNEL_SX = "";
//
//
//    static {
//        ResourceBundle ryt_Bundle = ResourceBundle.getBundle("shandiandai");
//        if (ryt_Bundle == null) {
//            throw new IllegalArgumentException("[shandiandai.properties] is not found!");
//        }
//        SddConstant.CHANNEL_SDD = ryt_Bundle.getString("channel_sdd");
//        SddConstant.SECRET_KEY = ryt_Bundle.getString("secret_key");
//        SddConstant.PRODUCT_ID = ryt_Bundle.getString("product_id");
//        SddConstant.BQS_URL = ryt_Bundle.getString("bqs_url");
//        SddConstant.CHANNEL_SX = ryt_Bundle.getString("channel_sx");
//
//    }
//
//    public static void main(String[] args) {
//        System.out.println(SddConstant.CHANNEL_SX);
//    }
//
//}
