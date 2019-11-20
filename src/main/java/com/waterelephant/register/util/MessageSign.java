package com.waterelephant.register.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class MessageSign {

	private static Logger logger = Logger.getLogger(MessageSign.class);

    public static final Charset CHARSET = Charset.forName("utf-8");

    public static final String ALGORITHMS_SHA1 = "SHA-1";

    public static final String ALGORITHMS_MD5 = "MD5";

    /**
     * 消息摘要，使用参数 algorithms 指定的算法
     *
     * @param algorithms
     * @param inputStr
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] sign(String algorithms, String inputStr) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithms);
        messageDigest.update(inputStr.getBytes(CHARSET));
        return messageDigest.digest();
    }

    /**
     * byte 数组转 十六进制字符串
     *
     * @param byteArray
     * @return
     */
    public static String byte2HexStr(byte[] byteArray) {

        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }

        return new String(resultCharArray);
    }

    /**
     * 签名,并将签名结果转换成 十六进制字符串
     *
     * @param inputStr
     * @param secretKey
     * @return
     */
    public static String signToHexStr(String algorithms, String inputStr) {
        try {
            return byte2HexStr(sign(algorithms, inputStr));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 拼接参数字符串
     *
     * @param paramMap
     * @return
     */
    public static String paramTreeMapToString(TreeMap<String, String> paramMap) {
        StringBuilder paramStrBuilder = new StringBuilder();

        Iterator<Map.Entry<String, String>> it = paramMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entrySet = it.next();
            paramStrBuilder.append(entrySet.getKey()).append('=').append(entrySet.getValue()).append('&');
        }
        return paramStrBuilder.substring(0, paramStrBuilder.length() - 1);
    }

    public static String signParams(String key, String sourceStr) {
        String md5Str1 = signToHexStr(ALGORITHMS_MD5, sourceStr);
        String sourceStr2 = md5Str1 + key;
        return signToHexStr(ALGORITHMS_MD5, sourceStr2);
    }

    public static String signParams(String key, TreeMap<String, String> paramMap) {
        String sourceStr = paramTreeMapToString(paramMap);
        System.out.println(sourceStr);
        return signParams(key, sourceStr);
    }

    /**
     * 利用 TreeMap 对 param 参数名称进行字典排序，然后拼接字符串，然后进行签名
     *
     * 仅作为测试用途，具体加密流程以接口文档为准
     *
     * @param applyNo
     * @param channelNo
     * @param applyInfo
     * @param userAttribute
     * @param timestamp
     * @return
     */
    public static String signTest() {

        String secretKey = "3FADAE9950B216AF";
//        String applyNo = "201512140075000002";
//        String channelNo = "211";
//        String applyInfo = "gLi5lSf1FW+r1nuhjheOlA2vYlbt1U9kOKnGPPG/LZUXzq0J7qlqUSckCtGfRiQkkqgfZHwEGaBZkpGWuIyZTtCegU8xj85Xp7bG3Fyfd6k=";
//        String userAttribute = "/9Tsys8IEam1eOl8HogR1f7cV5xtULIxpNG727DprU6L+uNr1a8PBkl5F5OfQtWibcBeLu0aRW9ER7AZAN5rjGjXeDAbRH8uVqDyTd4+OE0=";
//        String timestamp = "1477915880877";
//
//        String pNameApplyNo = "apply_no";
//        String pNameChannelNo = "channel_no";
//        String pNameApplyInfo = "apply_info";
//        String pNameUserAttr = "user_attribute";
//        String pNameTimestamp = "timestamp";
        TreeMap<String, String> paramMap = new TreeMap<>();
        paramMap.put("mobile", "13918146222");
        paramMap.put("name", "测试用户");
        paramMap.put("idcard", "42062119881000271X");
		

        return signParams(secretKey, paramMap);
    }

    public static void main(String[] args) {
        logger.info(signTest());
    }

}

