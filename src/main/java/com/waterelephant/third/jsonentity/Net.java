package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 流量值记录
 * Created by dengyan on 2017/7/20.
 */
public class Net {

    @JSONField(name = "fee")
    private String fee; // 通信费

    @JSONField(name = "net_type")
    private String netType; // 网络类型(4g,3g,2g)

    @JSONField(name = "net_way")
    private String netWay; // 上网方式(CMNET等)

    @JSONField(name = "preferential_fee")
    private String preferentialFee; // 优惠项/套餐优惠

    @JSONField(name = "start_time")
    private String startTime; // 起始时间

    @JSONField(name = "total_time")
    private String totalTime; // 总时长

    @JSONField(name = "total_traffic")
    private String totalTraffic; // 总流量

    @JSONField(name = "trade_addr")
    private String tradeAddr; // 通信地址

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getNetWay() {
        return netWay;
    }

    public void setNetWay(String netWay) {
        this.netWay = netWay;
    }

    public String getPreferentialFee() {
        return preferentialFee;
    }

    public void setPreferentialFee(String preferentialFee) {
        this.preferentialFee = preferentialFee;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getTotalTraffic() {
        return totalTraffic;
    }

    public void setTotalTraffic(String totalTraffic) {
        this.totalTraffic = totalTraffic;
    }

    public String getTradeAddr() {
        return tradeAddr;
    }

    public void setTradeAddr(String tradeAddr) {
        this.tradeAddr = tradeAddr;
    }
}
