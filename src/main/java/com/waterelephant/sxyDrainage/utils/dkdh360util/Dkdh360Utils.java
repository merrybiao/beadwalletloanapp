//package com.waterelephant.sxyDrainage.utils.dkdh360util;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.sxyDrainage.entity.dkdh360.Dkdh360Request;
//import com.waterelephant.sxyDrainage.entity.dkdh360.Dkdh360Response;
//import com.waterelephant.utils.CommUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
//
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.TreeMap;
//
///**
// * (code:dkdh001)
// *
// * @Author: zhangchong
// * @Date: 2018/7/24 19:02
// * @Description: 360贷款导航工具类
// */
//public class Dkdh360Utils {
//    /**
//     * 审核状态映射
//     */
//    private static Map<String, String> APPROVE_STATUS = new HashMap<>();
//    /**
//     * 订单状态映射
//     */
//    private static Map<String, String> ORDER_STATUS_MAP = new HashMap<>();
//
//    static {
//        APPROVE_STATUS.put("7", "40");
//        APPROVE_STATUS.put("8", "40");
//        APPROVE_STATUS.put("4", "10");
//        APPROVE_STATUS.put("6", "10");
//        APPROVE_STATUS.put("9", "10");
//        APPROVE_STATUS.put("11", "10");
//        APPROVE_STATUS.put("12", "10");
//        APPROVE_STATUS.put("13", "10");
//        APPROVE_STATUS.put("14", "10");
//
//        ORDER_STATUS_MAP.put("9", "170");
//        ORDER_STATUS_MAP.put("13", "180");
//        ORDER_STATUS_MAP.put("6", "200");
//        ORDER_STATUS_MAP.put("7", "110");
//        ORDER_STATUS_MAP.put("8", "110");
//        ORDER_STATUS_MAP.put("4", "100");
//        ORDER_STATUS_MAP.put("11", "160");
//        ORDER_STATUS_MAP.put("12", "160");
//        ORDER_STATUS_MAP.put("14", "160");
//    }
//
//    /**
//     * 映射审核状态
//     *
//     * @param key 订单状态
//     * @return String
//     */
//    public static String getApprovalStatus(String key) {
//        if (StringUtils.isBlank(key)) {
//            return null;
//        }
//        return APPROVE_STATUS.get(key);
//    }
//
//    /**
//     * 映射订单状态
//     *
//     * @param key 订单状态
//     * @return String
//     */
//    public static String getOrderStatus(String key) {
//        if (StringUtils.isBlank(key)) {
//            return null;
//        }
//        return ORDER_STATUS_MAP.get(key);
//    }
//
//    /**
//     * 生成签名串
//     *
//     * @param data 待签名数据
//     * @return string
//     */
//    public static String makeSignStr(TreeMap<String, String> data) {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (String key : data.keySet()) {
//            if ("sign".equals(key)) {
//                continue;
//            }
//            stringBuilder.append("&");
//            stringBuilder.append(key);
//            stringBuilder.append("=");
//            stringBuilder.append(data.get(key));
//        }
//        String signStr = stringBuilder.toString();
//        if (!"".equals(signStr)) {
//            signStr = signStr.substring(1);
//        }
//        return signStr;
//    }
//
//    /**
//     * 校验参数
//     *
//     * @param dkdh360Request 请求参数
//     * @return Dkdh360Response
//     */
//    public static Dkdh360Response checkParam(Dkdh360Request dkdh360Request) throws Exception {
//        if (CommUtils.isNull(dkdh360Request)) {
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "请求参数为空");
//        }
//
//        String sign = dkdh360Request.getSign();
//        if (CommUtils.isNull(sign)) {
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "sign为空");
//        }
//
//        String merchantId = dkdh360Request.getMerchant_id();
//        if (CommUtils.isNull(merchantId)) {
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "merchant_id为空");
//        }
//
//        String bizEnc = dkdh360Request.getBiz_enc();
//        if (CommUtils.isNull(bizEnc)) {
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "biz_enc为空");
//        }
//
//        String bizData = dkdh360Request.getBiz_data();
//        if (CommUtils.isNull(bizData)) {
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "biz_data为空");
//        }
//
//        String desKey = dkdh360Request.getDes_key();
//        if (CommUtils.isNull(desKey)) {
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "des_key为空");
//        }
//
//        Long timestamp = dkdh360Request.getTimestamp();
//        if (CommUtils.isNull(timestamp)) {
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "timestamp为空");
//        }
//
//        // 通知参数，需要对key做字典序排序算签名
//        TreeMap<String, String> requestParam = new TreeMap<>();
//        requestParam.put("merchant_id", merchantId);
//        requestParam.put("biz_enc", bizEnc);
//        requestParam.put("des_key", desKey);
//        requestParam.put("biz_data", bizData);
//        requestParam.put("timestamp", timestamp + "");
//
//        String signStr = makeSignStr(requestParam);
//
//        // 验证签名
//        boolean flag = RsaUtils.verify(signStr.getBytes(), Dkdh360Constant.get("dkdh360_public_key"), sign);
//        if (!flag) {
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "验证签名失败");
//        }
//
//        return null;
//    }
//
//    /**
//     * 解密biz_data数据
//     *
//     * @param dkdh360Request 请求参数
//     * @return String
//     */
//    public static String decodeBizData(Dkdh360Request dkdh360Request) throws Exception {
//        String desKeyRsa = dkdh360Request.getDes_key();
//        String bizData = dkdh360Request.getBiz_data();
//
//        // 使用水象私钥解密
//        byte[] desKeys = RsaUtils.decryptByPrivateKey(Base64Utils.decode(desKeyRsa), Dkdh360Constant.get
//            ("sx_private_key"));
//        String desKey = new String(desKeys);
//
//        // 使用des密钥解密
//        return DesUtils.decrypt(bizData, desKey);
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
//        int num = 6;
//        if (number > 0 && number <= num) {
//            String[] workTypes = {"批发/零售业", "制造业", "金融业/保险/证券", " 商业服务业/娱乐/艺术/体育", " 计算机/互联网", " 通讯/电子"};
//            workType = workTypes[number - 1];
//        } else {
//            workType = "其他";
//        }
//        return workType;
//    }
//
//    /**
//     * 通过编号获取银行编码
//     *
//     * @param code 编号
//     * @return String
//     */
//    public static String getOpenBankByCode(String code) {
//        String res;
//        switch (code) {
//            case "0102":
//                res = "ICBC";
//                break;
//            case "0103":
//                res = "ABC";
//                break;
//            case "0104":
//                res = "BOC";
//                break;
//            case "0105":
//                res = "CCB";
//                break;
//            case "0302":
//                res = "CITIC";
//                break;
//            case "0303":
//                res = "CEB";
//                break;
//            case "0304":
//                res = "HXB";
//                break;
//            case "0305":
//                res = "CMBC";
//                break;
//            case "0306":
//                res = "GDB";
//                break;
//            case "0307":
//                res = "PAB";
//                break;
//            case "0309":
//                res = "CIB";
//                break;
//            case "0310":
//                res = "SPDB";
//                break;
//            case "0403":
//                res = "PSBC";
//                break;
//            default:
//                res = null;
//        }
//        return res;
//
//    }
//
//    /**
//     * 通过银行编码获取编号
//     *
//     * @param openBank 银行编码
//     * @return String
//     */
//    public static String getCodeByOpenBank(String openBank) {
//        String res;
//        switch (openBank) {
//            case "ICBC":
//                res = "0102";
//                break;
//            case "ABC":
//                res = "0103";
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
//            case "HXB":
//                res = "0304";
//                break;
//            case "CMBC":
//                res = "0305";
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
//            default:
//                res = null;
//        }
//        return res;
//
//    }
//
//    public static void main(String[] args) {
//        try {
//            //使用RSA加密des密钥
//            byte[] encrypt = RsaUtils.encryptByPublicKey(Dkdh360Constant.get("des_key").getBytes(), Dkdh360Constant.get
//                ("sx_public_key"));
//            String desKey = Base64Utils.encode(encrypt);
//            System.out.println("rsa加密后的des：" + desKey);
//
//            TreeMap<String, String> treeMap = new TreeMap<>();
//            treeMap.put("biz_enc", "1");
//            treeMap.put("des_key", desKey);
//            treeMap.put("merchant_id", "564651");
//            treeMap.put("timestamp", 224545555 + "");
//            treeMap.put("biz_data", DesUtils.encrypt("1234567890", Dkdh360Constant.get("des_key")));
//            String signStr = Dkdh360Utils.makeSignStr(treeMap);
//            String sign = RsaUtils.sign(signStr.getBytes(), Dkdh360Constant.get("dkdh360_private_key"));
//            System.out.println("签名：" + sign);
//            treeMap.put("sign", sign);
//
//            boolean b = RsaUtils.verify(signStr.getBytes(), Dkdh360Constant.get
//                ("dkdh360_public_key"), sign);
//            System.out.println("验签结果：" + b);
//
//            String string = JSON.toJSONString(treeMap);
//            Dkdh360Request dkdh360Request = JSON.parseObject(string, Dkdh360Request.class);
//
//            Dkdh360Response dkdh360Response = checkParam(dkdh360Request);
//            System.out.println(JSON.toJSONString(dkdh360Response));
//            String data = decodeBizData(dkdh360Request);
//            System.out.println(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
