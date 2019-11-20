package com.waterelephant.drainage.jsonentity.fqgj;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 运营商通话记录信息
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/22 17:03
 */
@SuppressWarnings("serial")
public class FqgjCarrierTelData implements Serializable {

    //本地:1，漫游国内：2，其他：3

    @JSONField(name="trade_type")
    private String tradeType;//通信类型

    @JSONField(name="trade_time")
    private String tradeTime;//通话时长

    @JSONField(name="call_time")
    private String callTime;//通话时间

    @JSONField(name="trade_addr")
    private String tradeAddr;//通话时间

    @JSONField(name="receive_phone")
    private String receivePhone;// 对方号码

    //主叫：1，被叫：2，未识别状态：3
    @JSONField(name="call_type")
    private String callType;//呼叫类型

    @JSONField(name="business_name")
    private String businessName;//业务类型

    @JSONField(name="fee")
    private String fee;//费用

    @JSONField(name="special_offer")
    private String specialOffer;//特殊费用

    public String getTradeType() {
        return tradeType;
    }

    public FqgjCarrierTelData setTradeType(String tradeType) {
        this.tradeType = tradeType;
        return this;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public FqgjCarrierTelData setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
        return this;
    }

    public String getCallTime() {
        return callTime;
    }

    public FqgjCarrierTelData setCallTime(String callTime) {
        this.callTime = callTime;
        return this;
    }

    public String getTradeAddr() {
        return tradeAddr;
    }

    public FqgjCarrierTelData setTradeAddr(String tradeAddr) {
        this.tradeAddr = tradeAddr;
        return this;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public FqgjCarrierTelData setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
        return this;
    }

    public String getCallType() {
        return callType;
    }

    public FqgjCarrierTelData setCallType(String callType) {
        this.callType = callType;
        return this;
    }

    public String getBusinessName() {
        return businessName;
    }

    public FqgjCarrierTelData setBusinessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public String getFee() {
        return fee;
    }

    public FqgjCarrierTelData setFee(String fee) {
        this.fee = fee;
        return this;
    }

    public String getSpecialOffer() {
        return specialOffer;
    }

    public FqgjCarrierTelData setSpecialOffer(String specialOffer) {
        this.specialOffer = specialOffer;
        return this;
    }
}
