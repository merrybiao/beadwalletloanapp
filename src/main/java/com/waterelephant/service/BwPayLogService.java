package com.waterelephant.service;


import com.waterelephant.entity.BwPayLog;

/**
 * 调用支付接口、回调、查询日志记录service
 *
 * Created by maoenqi on 2017/9/26.
 */
public interface BwPayLogService extends BaseCommonService<BwPayLog, Long> {

    int insertPayLog(BwPayLog bwPayLog);

    /**
     * 根据tradeNo和logType查询最后一条记录
     *
     * @param tradeNo
     * @param logType
     * @return
     */
    BwPayLog selectLastByTradeNoAndLogType(String tradeNo, Integer logType);
}