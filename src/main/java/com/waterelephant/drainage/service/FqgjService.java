package com.waterelephant.drainage.service;

import com.waterelephant.drainage.entity.fqgj.DrainageResp;
import com.waterelephant.drainage.jsonentity.fqgj.FqgjOrder;
import com.waterelephant.drainage.jsonentity.fqgj.FqgjSupplementOrderInfo;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/23 14:27
 */
public interface FqgjService {

    /**
     * 存储分期管家推送订单
     * @param fqgjOrder
     * @return
     * @throws Exception
     */
    boolean saveOrderPush(FqgjOrder fqgjOrder) throws  Exception;

    /**
     * 存储分期管家订单补充信息
     * @return
     * @throws Exception
     */
    DrainageResp saveAddInfo(FqgjSupplementOrderInfo fqgjSupplementOrderInfo) throws Exception;

}
