//package com.waterelephant.sxyDrainage.utils.dkdh360util;
//
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.ResourceBundle;
//
///**
// * (code:dkdh001)
// *
// * @Author: zhangchong
// * @Date: 2018/7/24 19:25
// * @Description: 常量类
// */
//public class Dkdh360Constant {
//    private static ResourceBundle dkdh360_config;
//
//    public static Map<String, String> key_map = new HashMap<>(16);
//
//    static {
//        dkdh360_config = ResourceBundle.getBundle("dkdh360");
//        if (dkdh360_config == null) {
//            throw new IllegalArgumentException("[dkdh360.properties] is not found!");
//        }
//    }
//
//    public static String get(String key) {
//        // 获取配置文件中所有的key
//        Enumeration<String> keys = dkdh360_config.getKeys();
//
//        while (keys.hasMoreElements()) {
//            String key1 = keys.nextElement();
//            key_map.put(key1, dkdh360_config.getString(key1));
//        }
//
//        return key_map.get(key);
//    }
//
//    public static void main(String[] args) {
//        System.out.println(get("channel_id"));
//    }
//}
