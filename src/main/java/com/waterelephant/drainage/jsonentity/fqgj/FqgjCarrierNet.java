package com.waterelephant.drainage.jsonentity.fqgj;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 运营商流量值记录信息
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/22 17:03
 */
@SuppressWarnings("serial")
public class FqgjCarrierNet implements Serializable {

    @JSONField(name="fee")
    private String fee;

    @JSONField(name="net_type")
    private String netType;

    @JSONField(name="net_way")
    private String netWay;

    @JSONField(name="preferential_fee")
    private String preferentialFee;

    @JSONField(name="start_time")
    private String startTime;

    @JSONField(name="total_time")
    private String totalTime;

    @JSONField(name="total_traffic")
    private String totalTraffic;

    @JSONField(name="trade_addr")
    private String tradeAddr;

    public String getFee() {
        return fee;
    }

    public FqgjCarrierNet setFee(String fee) {
        this.fee = fee;
        return this;
    }

    public String getNetType() {
        return netType;
    }

    public FqgjCarrierNet setNetType(String netType) {
        this.netType = netType;
        return this;
    }

    public String getNetWay() {
        return netWay;
    }

    public FqgjCarrierNet setNetWay(String netWay) {
        this.netWay = netWay;
        return this;
    }

    public String getPreferentialFee() {
        return preferentialFee;
    }

    public FqgjCarrierNet setPreferentialFee(String preferentialFee) {
        this.preferentialFee = preferentialFee;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public FqgjCarrierNet setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public FqgjCarrierNet setTotalTime(String totalTime) {
        this.totalTime = totalTime;
        return this;
    }

    public String getTotalTraffic() {
        return totalTraffic;
    }

    public FqgjCarrierNet setTotalTraffic(String totalTraffic) {
        this.totalTraffic = totalTraffic;
        return this;
    }

    public String getTradeAddr() {
        return tradeAddr;
    }

    public FqgjCarrierNet setTradeAddr(String tradeAddr) {
        this.tradeAddr = tradeAddr;
        return this;
    }
}
