package com.waterelephant.third.utils;


/**
 * Created by dy199 on 2017/8/1.
 */
public class RedisKeyUtil {

    public static String orderStatues(String chennal) {
        return "third_" + chennal + ":orderStatus";
    }
}
