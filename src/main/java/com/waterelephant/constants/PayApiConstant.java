package com.waterelephant.constants;

import java.util.ResourceBundle;

/**
 * 对外提供接口常量
 */
public class PayApiConstant {
    private static ResourceBundle configBundle = ResourceBundle.getBundle("payApi");
    public static String ORDER_WITHHOLD_MD5_KEY;
    public static String ORDER_WITHHOLD_URL;

    static {
        ORDER_WITHHOLD_MD5_KEY = configBundle.getString("order_withhold_md5_key");
        ORDER_WITHHOLD_URL = configBundle.getString("order_withhold_url");
    }
}