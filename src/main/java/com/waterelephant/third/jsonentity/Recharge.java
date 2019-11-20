package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 充值记录
 * Created by dengyan on 2017/7/20.
 */
public class Recharge {

    @JSONField(name = "fee")
    private String fee; // 金额

    @JSONField(name = "recharge_time")
    private String rechargeTime; // 充值时间

    @JSONField(name = "recharge_way")
    private String rechargeWay; // 充值方式

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(String rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public String getRechargeWay() {
        return rechargeWay;
    }

    public void setRechargeWay(String rechargeWay) {
        this.rechargeWay = rechargeWay;
    }
}
