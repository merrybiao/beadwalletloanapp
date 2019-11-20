///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.utils;
//
//import java.text.ParsePosition;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
//import org.apache.commons.lang.StringUtils;
//
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.DoubleUtil;
//
///**
// * Module: bindCardUtils.java
// *
// * @author huangjin
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
//public class DrainageUtils {
//    public static Double calculateRepayMoney(Double amount, int term, Double interestRate) {
//        switch (term) {
//            case 1:
//                return DoubleUtil.round(amount / 4 * 4 * interestRate, 0);
//            case 2:
//                return DoubleUtil.round(amount / 4 * 3 * interestRate, 0);
//            case 3:
//                return DoubleUtil.round(amount / 4 * 2 * interestRate, 0);
//            case 4:
//                return DoubleUtil.round(amount / 4 * 1 * interestRate, 0);
//            default:
//                return 0.0D;
//        }
//    }
//
//    /**
//     * 绑卡 转换富友银行编码为宝付
//     *
//     * @param bankcode 富友银行编码
//     * @return 对应宝付银行编码
//     */
//    public static String convertFuiouBankCodeToBaofu(String bankcode) {
//        if (StringUtils.isBlank(bankcode)) {
//            return null;
//        }
//        String res = null;
//        switch (bankcode) {
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
//            case "0301":
//                res = "BCOM";
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
//            case "0308":
//                res = "CMB";
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
//        }
//        return res;
//    }
//
//    public static String convertToBankCode(String openBank) {
//        if (StringUtils.isBlank(openBank)) {
//            return "";
//        } else if (openBank.contains("建设")) {
//            return "0105";
//        } else if (openBank.contains("光大")) {
//            return "0303";
//        } else if (openBank.contains("工商")) {
//            return "0102";
//        } else if (openBank.contains("农业")) {
//            return "0103";
//        } else if (openBank.contains("中国银行")) {
//            return "0104";
//        } else if (openBank.contains("交通")) {
//            return "0301";
//        } else if (openBank.contains("中信")) {
//            return "0302";
//        } else if (openBank.contains("华夏")) {
//            return "0304";
//        } else if (openBank.contains("民生")) {
//            return "0305";
//        } else if (openBank.contains("广东发展") || openBank.contains("广发")) {
//            return "0306";
//        } else if (openBank.contains("平安")) {
//            return "0307";
//        } else if (openBank.contains("招商")) {
//            return "0308";
//        } else if (openBank.contains("兴业")) {
//            return "0309";
//        } else if (openBank.contains("浦发") || openBank.contains("浦东发展")) {
//            return "0310";
//        } else if (openBank.contains("邮政")) {
//            return "0403";
//        }
//        return "0000";
//    }
//
//
//    /**
//     * 绑卡 转换富友银行编码为银行名称
//     *
//     * @param bankcode 富友银行编码
//     * @return 对应宝付银行编码
//     */
//    public static String convertFuiouBankCodeToBankName(String bankcode) {
//        if (StringUtils.isBlank(bankcode)) {
//            return null;
//        }
//        String res = null;
//        switch (bankcode) {
//            case "0307":
//                res = "平安银行股份有限公司";
//                break;
//            case "0104":
//                res = "中国银行";
//                break;
//            case "0105":
//                res = "中国建设银行";
//                break;
//            case "0303":
//                res = "中国光大银行";
//                break;
//            case "0304":
//                res = "华夏银行";
//                break;
//            case "0305":
//                res = "中国民生银行";
//                break;
//            case "0301":
//                res = "交通银行";
//                break;
//            case "0310":
//                res = "上海浦东发展银行";
//                break;
//            case "0102":
//                res = "中国工商银行";
//                break;
//            case "0302":
//                res = "中信实业银行";
//                break;
//            case "0306":
//                res = "广东发展银行";
//                break;
//            case "0309":
//                res = "兴业银行";
//                break;
//            case "0103":
//                res = "中国农业银行";
//                break;
//            case "0403":
//                res = "中国邮政储蓄银行有限公司";
//                break;
//        }
//        return res;
//    }
//
//    /**
//     * 绑卡 转换现金白卡银行编码为富友
//     *
//     * @param bankcode
//     * @return
//     */
//    public static String convertBankCodeToFuiou(String bankcode) {
//        if (StringUtils.isBlank(bankcode)) {
//            return null;
//        }
//        String res = null;
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
//            case "PSBC":
//                res = "0403";
//                break;
//        }
//        return res;
//    }
//
//
//    /**
//     * 生成水象云工单号
//     *
//     * @return 工单号
//     */
//    public static String generateOrderNo() {
//        StringBuffer orderNo = new StringBuffer("Y");
//        orderNo.append(CommUtils.convertDateToString(new Date(), "yyyyMMddhhmmssSSS"));
//        orderNo.append(CommUtils.getRandomNumber(3));
//        return orderNo.toString();
//    }
//
//    /**
//     * 日期前推或后推天数,其中day表示天数
//     *
//     * @param date yyyy-MM-dd
//     * @param day  向前推为负数，向后推为正数
//     * @return
//     */
//    public static Date getPreDay(Date dateDate, int day) {
//        Calendar gc = Calendar.getInstance();
//        gc.setTime(dateDate);
//        gc.add(Calendar.DATE, day);
//        return gc.getTime();
//    }
//
//    /**
//     * 将指定格式的字符串转为日期时间对象
//     *
//     * @param strDate 时间
//     * @param format  格式
//     * @return 时间 date
//     */
//    public static Date formatToDate(String strDate, String format) {
//        if (strDate == null) {
//            return null;
//        }
//        SimpleDateFormat formatter = new SimpleDateFormat(format);
//        ParsePosition pos = new ParsePosition(0);
//        Date strtodate = formatter.parse(strDate, pos);
//        return strtodate;
//    }
//
//    /**
//     * 将日期时间对象转为 指定格式的字符串
//     *
//     * @param strDate
//     * @param format
//     * @return string
//     */
//    public static String formatToStr(Date dateDate, String format) {
//        if (dateDate == null) {
//            return null;
//        }
//        SimpleDateFormat formatter = new SimpleDateFormat(format);
//        String dateString = formatter.format(dateDate);
//        return dateString;
//    }
//
//    /**
//     * 借点钱银行简称对应的编码
//     *
//     * @param bankcode  银行简称
//     * @return
//     */
//    public static String convertJdqToBaoFuCode(String bankcode) {
//        if (StringUtils.isBlank(bankcode)) {
//            return null;
//        }
//        String res = null;
//        switch (bankcode) {
//
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
//            case "BCOM":
//                res = "0301";
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
//            case "CMB":
//                res = "0308";
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
//        }
//        return res;
//    }
//}
