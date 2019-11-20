package com.waterelephant.drainage.jsonentity.fqgj;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 运营商短信记录信息
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/22 17:03
 */
@SuppressWarnings("serial")
public class FqgjCarrierMsgData implements Serializable {

    @JSONField(name="send_time")
    private String sendTime;//发送时间(精确到秒)

    //发送：1；接收：2；未识别状态：3
    @JSONField(name="trade_way")
    private int tradeWay;

    @JSONField(name="receiver_phone")
    private String receiverPhone;//对方号码

    @JSONField(name="business_name")
    private String businessName;//业务类型

    @JSONField(name="fee")
    private String fee;//费用

    @JSONField(name="trade_addr")
    private String tradeAddr;//短信地址

    //类型1：短信 2：彩信 3：其他
    @JSONField(name="trade_type")
    private String tradeType;

    public String getSendTime() {
        return sendTime;
    }

    public FqgjCarrierMsgData setSendTime(String sendTime) {
        this.sendTime = sendTime;
        return this;
    }

    public int getTradeWay() {
        return tradeWay;
    }

    public FqgjCarrierMsgData setTradeWay(int tradeWay) {
        this.tradeWay = tradeWay;
        return this;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public FqgjCarrierMsgData setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
        return this;
    }

    public String getBusinessName() {
        return businessName;
    }

    public FqgjCarrierMsgData setBusinessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public String getFee() {
        return fee;
    }

    public FqgjCarrierMsgData setFee(String fee) {
        this.fee = fee;
        return this;
    }

    public String getTradeAddr() {
        return tradeAddr;
    }

    public FqgjCarrierMsgData setTradeAddr(String tradeAddr) {
        this.tradeAddr = tradeAddr;
        return this;
    }

    public String getTradeType() {
        return tradeType;
    }

    public FqgjCarrierMsgData setTradeType(String tradeType) {
        this.tradeType = tradeType;
        return this;
    }
}
