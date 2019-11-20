package com.waterelephant.baiqishi.util;

import com.waterelephant.utils.RedisUtils;

/**
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/31 17:14
 */
public class BaiQiShiUtil {

    /**
     * 成功返回OK失败和异常返回null
     * @param orderId
     * @param tokenKey
     * @return
     */
    public  static String putTokenKey(String orderId, String tokenKey) {
        return RedisUtils.setex("BaiQiShi:tokenKey:"+orderId, tokenKey, 86000);
    }
    
}
