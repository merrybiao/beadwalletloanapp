//package com.waterelephant.sxyDrainage.utils.jieba;
//
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.ResourceBundle;
//
///**
// * (code:jb001)
// *
// * @Author: ZhangChong
// * @Description:
// * @Date: Created in 10:29 2018/6/13
// * @Modified By:
// */
//public class JieBaConstant {
//    public static String appId;
//    public static String channelId;
//    public static String jbPublicKey;
//
//    private static Map<String, String> keyMap = new HashMap<>();
//    public static Map<String, String> statusMap = new HashMap<>();
//
//    static {
//        ResourceBundle jbBundle = ResourceBundle.getBundle("jieba");
//        if (jbBundle == null) {
//            throw new IllegalArgumentException("[jieba.properties] is not found!");
//        }
//
//        // 获取配置文件中所有的key
//        Enumeration<String> keys = jbBundle.getKeys();
//        String key;
//        while (keys.hasMoreElements()) {
//            key = keys.nextElement();
//            // 把键和值封装到map中
//            keyMap.put(key, jbBundle.getString(key));
//        }
//
//        //10 审批通过 审批通过的状态
//        //40 审批不通过 审批不通过的状态
//        //161 贷款取消 1. 因各种原因此用户贷款终止（机构终止or用户主动取消等）；2. 此状态为终结状态，贷款取消后不再允许流转至其它状态；
//        //169 放款失败 1. 用户已进入放款环节，但因资方等各种问题导致最终无法给机构打款；2. 此状态为终结状态，放款失败后不再允许流转至其它状态；
//        //170 放款成功 1. 款项必须已成功打至用户卡中才可推送放款成功；2. 进入放款成功的订单必须推送还款结果，且仅能流转至：200（到期结清）、180（已逾期），若用户已展期订单状态仍为170即可，但需更新还款计划；
//        //180 逾期 1. pay_day产品，超过了最后还款日仍有未还清的借款且未办理延期还款；2. 多期产品，超过了最后1期账单的最后还款日仍有未还清的借款；
//        //200 贷款结清 用户还清全部欠款
//        statusMap.put("4", "10");
//        statusMap.put("7", "40");
//        statusMap.put("8", "40");
//        statusMap.put("9", "170");
//        statusMap.put("13", "180");
//        statusMap.put("6", "200");
//    }
//
//    public static String get(String key) {
//        return keyMap.get(key);
//    }
//
//    static {
//        appId = JieBaConstant.get("app_id");
//        channelId = JieBaConstant.get("channel_id");
//        jbPublicKey = JieBaConstant.get("publicKey");
//    }
//
//    public static void main(String[] args) {
//        System.out.println(appId);
//    }
//}
