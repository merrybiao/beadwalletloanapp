//package com.waterelephant.sxyDrainage.utils.wacaiutils;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.ResourceBundle;
//
///**
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/28
// * @since JDK 1.8
// */
//public class WaCaiConstant {
//
//    public static String APP_ID = "";
//    public static String APP_SECRET_KEY = "";
//    public static String CHANNEL_SX = "";
//    public static String BQS_URL = "";
//    public static String PRODUCT_ID = "";
//    public static String WACAI_URL = "";
//
//    /** 银行卡编码转换 */
//    private static Map<String, String> BANK_CODE_STATUS = new HashMap<>();
//
//    static {
//        // 银行卡状态转换
//        BANK_CODE_STATUS.put("ICBC", "0102");
//        BANK_CODE_STATUS.put("ABC", "0103");
//        BANK_CODE_STATUS.put("BOC", "0104");
//        BANK_CODE_STATUS.put("CITIC", "0302");
//        BANK_CODE_STATUS.put("CEB", "0303");
//        BANK_CODE_STATUS.put("HXBANK", "0304");
//        BANK_CODE_STATUS.put("CMBC", "0305");
//        BANK_CODE_STATUS.put("CGB", "0306");
//        BANK_CODE_STATUS.put("SPABANK", "0307");
//        BANK_CODE_STATUS.put("CIB", "0309");
//        BANK_CODE_STATUS.put("SPDB", "0310");
//        BANK_CODE_STATUS.put("PSBC", "0403");
//        BANK_CODE_STATUS.put("CCB", "0105");
//    }
//
//    // 读取配置文件，赋值
//    static {
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("wacai");
//        if (resourceBundle == null) {
//            throw new IllegalArgumentException("[wacai.properties] 没有找到!");
//        }
//        WaCaiConstant.APP_ID = resourceBundle.getString("app_id");
//        WaCaiConstant.APP_SECRET_KEY = resourceBundle.getString("appSecretKey");
//        WaCaiConstant.PRODUCT_ID = resourceBundle.getString("product_id");
//        WaCaiConstant.BQS_URL = resourceBundle.getString("bqs_url");
//        WaCaiConstant.CHANNEL_SX = resourceBundle.getString("channel_sx");
//        WaCaiConstant.WACAI_URL = resourceBundle.getString("wacai_url");
//    }
//
//
//    /**
//     * 银行卡转换
//     *
//     * @param code 三方银行卡标识
//     * @return sx银行卡编码
//     */
//    public static String getBankCode(String code) {
//        if (code == null) {
//            return null;
//        }
//        return BANK_CODE_STATUS.get(code);
//    }
//}
