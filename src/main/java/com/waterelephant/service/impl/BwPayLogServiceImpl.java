package com.waterelephant.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwPayLog;
import com.waterelephant.service.BwPayLogService;
import com.waterelephant.utils.RedisUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 调用支付接口、回调、查询日志记录service
 * <p>
 * Created by maoenqi on 2017/9/26.
 */
@Service
public class BwPayLogServiceImpl extends BaseCommonServiceImpl<BwPayLog, Long>
        implements BwPayLogService {
    private Logger logger = Logger.getLogger(BwPayLogServiceImpl.class);

    @Override
    public int insertPayLog(BwPayLog bwPayLog) {
        int count = 0;
        if (bwPayLog == null || bwPayLog.getOrderId() == null) {
            return count;
        }
        Long orderId = bwPayLog.getOrderId();
        Integer logType = bwPayLog.getLogType();
        String tradeNo = bwPayLog.getTradeNo();
        try {
            if (logType != null && (logType == 2 || logType == 3)) {// 回调或查询，取调用接口logType=1的交易时间
                BwPayLog lastReqPayLog = selectLastByTradeNoAndLogType(tradeNo, 1);
                if (lastReqPayLog != null) {
                    bwPayLog.setTradeTime(lastReqPayLog.getTradeTime());
                }
            }
            count = insertSelective(bwPayLog);
        } catch (Exception e) {
            logger.error("【BwPayLogService.insertPayLog】orderId:" + orderId + ",保存异常", e);
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(bwPayLog));
            jsonObject.put("exceptionMsg", e.getMessage());
            // 保存三方返回数据可能数据过长，抛异常保存到redis
            RedisUtils.hset("PAY_LOG_FAIL", orderId.toString(), jsonObject.toJSONString());
        }
        return count;
    }

    /**
     * 根据tradeNo和logType查询最后一条记录
     *
     * @param tradeNo
     * @param logType
     * @return
     */
    @Override
    public BwPayLog selectLastByTradeNoAndLogType(String tradeNo, Integer logType) {
        StringBuilder sqlSB = new StringBuilder();
        sqlSB.append("select * from bw_pay_log where trade_no='");
        sqlSB.append(tradeNo);
        sqlSB.append("' and log_type=");
        sqlSB.append(logType);
        sqlSB.append(" order by id limit 1");
        BwPayLog bwPayLog = sqlMapper.selectOne(sqlSB.toString(), BwPayLog.class);
        return bwPayLog;
    }
}
