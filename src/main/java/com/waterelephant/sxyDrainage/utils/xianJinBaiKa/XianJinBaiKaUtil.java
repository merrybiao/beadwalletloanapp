//package com.waterelephant.sxyDrainage.utils.xianJinBaiKa;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.TreeMap;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.waterelephant.utils.Base64;
//import com.waterelephant.utils.MD5Util;
//
///**
// * Module:(code:xjbk)
// * <p>
// * XianJinBaiKaUtil.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
//public class XianJinBaiKaUtil {
//    private static Logger logger = LoggerFactory.getLogger(XianJinBaiKaUtil.class);
//
//    public static String[] baoFuBankCard = {"工商银行", "中国银行", "建设银行", "农业银行", "光大银行", "广发银行", "兴业银行", "平安银行", "浦发银行",
//            "邮储银行", "中信银行", "民生银行", "华夏银行"};
//
//    public static Map<String, String> bfMap = new HashMap<>();
//
//    static {
//        bfMap.put("工商银行", "单笔0.5W/日5W");
//        bfMap.put("中国银行", "单笔0.5W/日5W");
//        bfMap.put("建设银行", "单笔0.5W/日5W");
//        bfMap.put("农业银行", "单笔0.5W/日5W");
//        bfMap.put("光大银行", "单笔0.5W/日5W");
//        bfMap.put("广发银行", "单笔0.5W/日5W");
//        bfMap.put("兴业银行", "单笔0.5W/日5W");
//        bfMap.put("平安银行", "单笔0.5W/日5W");
//        bfMap.put("浦发银行", "单笔0.5W/日5W");
//        bfMap.put("邮储银行", "单笔0.5W/日0.5W");
//        bfMap.put("中信银行", "单笔0.5W/日0.5W");
//        bfMap.put("民生银行", "单笔0.5W/日0.5W");
//        bfMap.put("华夏银行", "单笔0.5W/日0.5W");
//    }
//
//    /**
//     * API验证签名
//     *
//     * @param call        请求标识
//     * @param args        调用参数
//     * @param requestSign 请求签名
//     * @return boolean
//     */
//    public static boolean checkSign(String call, String args, String requestSign) {
//        try {
//            String ua = XianJinBaiKaConstant.UA_REQUEST;
//            String signkey = XianJinBaiKaConstant.SIGNKEY_REQUEST;
//            String key = ua + signkey + ua;
//            String sign = MD5Util.md5(key + call + key + args + key);
//
//            if (sign.equals(requestSign)) {
//                logger.info("验签APIsign= {},签名={}", requestSign, true);
//                return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    /**
//     * H5验证签名
//     *
//     * @param treeMap 参数
//     * @return boolean
//     */
//    public static boolean checkH5Sign(Map<String, String> treeMap) {
//        try {
//            String signSrc = treeMap.get("sign");
//            treeMap.remove("sign");
//            String str = "";
//            for (Entry<String, String> string : treeMap.entrySet()) {
//                if (StringUtils.isBlank(str)) {
//                    str = string.getKey() + string.getValue();
//                } else {
//                    str += string.getKey() + string.getValue();
//                }
//            }
//
//            String ua = XianJinBaiKaConstant.UA_REQUEST;
//            String signkey = XianJinBaiKaConstant.SIGNKEY_REQUEST;
//            String key = ua + signkey + ua;
//            String sign = MD5Util.md5(key + str + key);
//            if (sign.equals(signSrc)) {
//                logger.info("验签H5sign= {},签名={}", signSrc, true);
//                return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    /**
//     * H5 生成签名
//     *
//     * @param treeMap 参数
//     * @return String
//     */
//    private static String getH5Sign(Map<String, String> treeMap) {
//        String sign = "";
//        try {
//            String str = "";
//            if (null != treeMap) {
//                for (Entry<String, String> string : treeMap.entrySet()) {
//                    if (StringUtils.isBlank(str)) {
//                        str = string.getKey() + string.getValue();
//                    } else {
//                        str += string.getKey() + string.getValue();
//                    }
//                }
//            }
//            String ua = XianJinBaiKaConstant.UA_REQUEST;
//            String signkey = XianJinBaiKaConstant.SIGNKEY_REQUEST;
//            String key = ua + signkey + ua;
//            sign = MD5Util.md5(key + str + key);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return sign;
//    }
//
//    /**
//     * 根据名称获取银行编码
//     *
//     * @param openBank 银行名称
//     * @return String
//     */
//    public static String convertToBankCode(String openBank) {
//        if (StringUtils.isBlank(openBank)) {
//            return "";
//        } else if (openBank.contains("工商")) {
//            return "ICBC";
//        } else if (openBank.contains("农业")) {
//            return "ABC";
//        } else if (openBank.contains("中国银行")) {
//            return "BOC";
//        } else if (openBank.contains("建设")) {
//            return "CCB";
//        } else if (openBank.contains("交通")) {
//            return "BCOM";
//        } else if (openBank.contains("民生")) {
//            return "CMBC";
//        } else if (openBank.contains("招商")) {
//            return "CMB";
//        } else if (openBank.contains("邮政储蓄") || openBank.contains("邮储")) {
//            return "POST";
//        } else if (openBank.contains("平安")) {
//            return "PAB";
//        } else if (openBank.contains("中信")) {
//            return "CITIC";
//        } else if (openBank.contains("光大")) {
//            return "CEB";
//        } else if (openBank.contains("兴业")) {
//            return "CIB";
//        } else if (openBank.contains("广发")) {
//            return "GDB";
//        } else if (openBank.contains("华夏")) {
//            return "HXB";
//        } else if (openBank.contains("南京")) {
//            return "NJCB";
//        } else if (openBank.contains("浦发")) {
//            return "SPDB";
//        } else if (openBank.contains("北京")) {
//            return "BOB";
//        } else if (openBank.contains("杭州")) {
//            return "HZB";
//        } else if (openBank.contains("宁波")) {
//            return "NBCB";
//        } else if (openBank.contains("浙商")) {
//            return "CZB";
//        } else if (openBank.contains("徽商")) {
//            return "HSB";
//        } else if (openBank.contains("渤海")) {
//            return "CBHB";
//        } else if (openBank.contains("汉口")) {
//            return "HKBANK";
//        } else {
//            return "";
//        }
//    }
//
//    /**
//     * // 2018.05.03 添加(1.0.0)
//     * <p>
//     * 绑卡 转换现金白卡银行编码为宝付
//     *
//     * @param bankcode 现金白卡银行编码
//     * @return 对应宝付银行编码
//     */
//    public static String convertXjbkBankCodeToBaofu(String bankcode) {
//        String res;
//        switch (bankcode) {
//            case "ICBC":
//                res = "ICBC";
//                break;
//            case "BOC":
//                res = "BOC";
//                break;
//            case "CCB":
//                res = "CCB";
//                break;
//            case "BCOM":
//                res = "BCOM";
//                break;
//            case "CITIC":
//                res = "CITIC";
//                break;
//            case "CEB":
//                res = "CEB";
//                break;
//            case "GDB":
//                res = "GDB";
//                break;
//            case "PAB":
//                res = "PAB";
//                break;
//            case "CIB":
//                res = "CIB";
//                break;
//            case "SPDB":
//                res = "SPDB";
//                break;
//            case "POST":
//                res = "PSBC";
//                break;
//            default:
//                res = null;
//        }
//        return res;
//    }
//
//    /**
//     * // 2018.05.03 添加(1.0.1)
//     * <p>
//     * 绑卡 转换现金白卡银行编码为富友
//     *
//     * @param bankcode 银行编码
//     * @return String
//     */
//    public static String convertXjbkBankCodeToFuYou(String bankcode) {
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
//            case "BCOM":
//                res = "0301";
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
//            case "POST":
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
//    /**
//     * 获取现金白卡工作年限
//     *
//     * @param type 工作年限编号
//     * @return String
//     */
//    public static String getWorkAge(String type) {
//        if ("1".equals(type)) {
//            return "0-5个月";
//        } else if ("2".equals(type)) {
//            return "6-11个月";
//        } else if ("3".equals(type)) {
//            return "1-3年";
//        } else if ("4".equals(type)) {
//            return "3-7年";
//        } else if ("5".equals(type)) {
//            return "7年以上";
//        } else {
//            return "";
//        }
//    }
//
//    /**
//     * 获取现金白卡工作类型
//     *
//     * @param type 工作类型编号
//     * @return String
//     */
//    public static String getWorkType(String type) {
//        if ("1".equals(type)) {
//            return "商业、服务人员";
//        } else if ("2".equals(type)) {
//            return "专业技术人员";
//        } else if ("3".equals(type)) {
//            return "办事人员、文员、行政等";
//        } else if ("4".equals(type)) {
//            return "工厂、生产、运输人员 ";
//        } else if ("5".equals(type)) {
//            return "农、林、牧、渔、水利业人员";
//        } else if ("6".equals(type)) {
//            return "前线销售人员";
//        } else if ("7".equals(type)) {
//            return "国家机关、企事业单位管理人员";
//        } else if ("8".equals(type)) {
//            return "军人";
//        } else if ("9".equals(type)) {
//            return "在校学生";
//        } else {
//            return "";
//        }
//    }
//
//    /**
//     * 将汉字转码
//     *
//     * @param url 参数url
//     * @return String
//     */
//    public static String getUrl(String url) {
//        String patternString = "[\u4e00-\u9fa5]";
//        Pattern pattern = Pattern.compile(patternString);
//        Matcher matcher = pattern.matcher(url);
//        while (matcher.find()) {
//            try {
//                url = url.replaceAll(matcher.group(), URLEncoder.encode(matcher.group(), "utf-8"));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//        return url;
//    }
//
//    public static void main(String[] args) {
//        try {
//            String ua = XianJinBaiKaConstant.UA_REQUEST;
//            String signkey = XianJinBaiKaConstant.SIGNKEY_REQUEST;
//            String key = ua + signkey + ua;
//            String call = "aaa";
//            String param = "123456";
//            String string = MD5Util.md5(key + call + key + param + key);
//            boolean result = checkSign(call, param, string);
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Map<String, String> treeMap = new TreeMap<>();
//        treeMap.put("order_sn","20180704000001");
//        treeMap.put("user_name", "张冲");
//        treeMap.put("user_phone", "15972182935");
//        treeMap.put("user_idcard", "421022199307063953");
//        treeMap.put("return_url",
//                "aHR0cDovL3Rlc3Qtd2ViYXBwLnhpYW5qaW5jYXJkLmNvbS9wcm9kdWN0cy9ub3RpZnk/dGl0bGU95pON5L2c5oiQ5YqfJnBhZ2VUaXRsZT3mk43kvZzmiJDlip8mYnV0dG9uPXsibGFiZWwiOiLnoa7lrpoiLCJsaW5rIjoiaHR0cDovL3Rlc3Qtd2ViLnhpYW5qaW5jYXJkLmNvbS9jaGFubmVsL2FwcC9wcm9kdWN0P3Byb2R1Y3RfaWQ9MTY0In0mc3RhdHVzPXN1Y2Nlc3M=");
//        String h5Sign = getH5Sign(treeMap);
//        System.out.println("sign=" + h5Sign);
//        treeMap.put("sign", h5Sign);
//
//        byte[] bs = Base64.decode(
//                "aHR0cDovL3Rlc3Qtd2ViYXBwLnhpYW5qaW5jYXJkLmNvbS9wcm9kdWN0cy9ub3RpZnk/dGl0bGU95pON5L2c5oiQ5YqfJnBhZ2VUaXRsZT3mk43kvZzmiJDlip8mYnV0dG9uPXsibGFiZWwiOiLnoa7lrpoiLCJsaW5rIjoiaHR0cDovL3Rlc3Qtd2ViLnhpYW5qaW5jYXJkLmNvbS9jaGFubmVsL2FwcC9wcm9kdWN0P3Byb2R1Y3RfaWQ9MTY0In0mc3RhdHVzPXN1Y2Nlc3M=");
//        System.out.println("return_url=" + new String(bs));
//
//        boolean s = checkH5Sign(treeMap);
//        System.out.println(s);
//
//        String text = "http://test-webapp.xianjincard.com/products/notify?title=操作成功&pageTitle=操作成功&button={\"label\":\"确定\",\"link\":\"http://test-web.xianjincard.com/channel/app/product?product_id=164\"}&status=success";
//        text = getUrl(text);
//        System.out.println(text);
//
//        //return_url=aHR0cDovL3ByZS13ZWJhcHAueGlhbmppbmNhcmQuY29tL3Byb2R1Y3RzL25vdGlmeT90aXRsZT3mk43kvZzmiJDlip8mcGFnZVRpdGxlPeaTjeS9nOaIkOWKnyZidXR0b249eyJsYWJlbCI6IuehruWumiIsImxpbmsiOiJodHRwJTNBJTJGJTJGcHJlLXdlYmFwcC54aWFuamluY2FyZC5jb20lMkZjaGFubmVsJTJGYXBwJTJGcHJvZHVjdCUzRnByb2R1Y3RfaWQlM0QyNDAlMjZmcm9tJTNET3V0VmVyaWZ5In0mc3RhdHVzPXN1Y2Nlc3M=
//
//        byte[] bss = Base64.decode(
//                "aHR0cDovL3ByZS13ZWJhcHAueGlhbmppbmNhcmQuY29tL3Byb2R1Y3RzL25vdGlmeT90aXRsZT3mk43kvZzmiJDlip8mcGFnZVRpdGxlPeaTjeS9nOaIkOWKnyZidXR0b249eyJsYWJlbCI6IuehruWumiIsImxpbmsiOiJodHRwJTNBJTJGJTJGcHJlLXdlYmFwcC54aWFuamluY2FyZC5jb20lMkZjaGFubmVsJTJGYXBwJTJGcHJvZHVjdCUzRnByb2R1Y3RfaWQlM0QyNDAlMjZmcm9tJTNET3V0VmVyaWZ5In0mc3RhdHVzPXN1Y2Nlc3M=");
//        System.out.println("return_url=" + new String(bss));
//    }
//}
