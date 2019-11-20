package com.waterelephant.drainage.jsonentity.fqgj;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 运营商 充值记录
 */
@SuppressWarnings("serial")
public class FqgjCarrierRecharge implements Serializable {

    @JSONField(name="fee")
    private String fee;
    @JSONField(name="recharge_time")
    private String rechargeTime;
    @JSONField(name="recharge_way")
    private String rechargeWay;

    public String getFee() {
        return fee;
    }

    public FqgjCarrierRecharge setFee(String fee) {
        this.fee = fee;
        return this;
    }

    public String getRechargeTime() {
        return rechargeTime;
    }

    public FqgjCarrierRecharge setRechargeTime(String rechargeTime) {
        this.rechargeTime = rechargeTime;
        return this;
    }

    public String getRechargeWay() {
        return rechargeWay;
    }

    public FqgjCarrierRecharge setRechargeWay(String rechargeWay) {
        this.rechargeWay = rechargeWay;
        return this;
    }
}
