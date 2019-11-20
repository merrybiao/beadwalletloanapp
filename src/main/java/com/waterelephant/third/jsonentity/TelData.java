package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 通话记录
 * Created by dengyan on 2017/7/20.
 */
public class TelData {
    @JSONField(name = "trade_type")
    private String tradeType; // 通信类型

    @JSONField(name = "trade_time")
    private String tradeTime; // 通话时长

    @JSONField(name = "call_time")
    private String callTime; // 通话时间

    @JSONField(name = "trade_addr")
    private String tradeAddr; // 通话地址

    @JSONField(name = "receive_phone")
    private String receivePhone; // 对方号码

    @JSONField(name = "call_type")
    private String callType; // 呼叫类型（主叫：1，被叫：2，未识别状态：3）

    @JSONField(name = "business_name")
    private String businessName; // 业务类型

    @JSONField(name = "fee")
    private String fee; // 费用

    @JSONField(name = "special_offer")
    private String specialOffer; // 特殊费用

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getTradeAddr() {
        return tradeAddr;
    }

    public void setTradeAddr(String tradeAddr) {
        this.tradeAddr = tradeAddr;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getSpecialOffer() {
        return specialOffer;
    }

    public void setSpecialOffer(String specialOffer) {
        this.specialOffer = specialOffer;
    }
}
