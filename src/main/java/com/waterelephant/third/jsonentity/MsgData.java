package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 短信信息
 * Created by dengyan on 2017/7/20.
 */
public class MsgData {

    @JSONField(name = "send_time")
    private String sendTime; // 发送时间（精确到1秒）

    @JSONField(name = "trade_way")
    private String tradeWay; // 短信状态（发送：1；接收：2；未识别状态：3 ）

    @JSONField(name = "receiver_phone")
    private String receiverPhone; // 对方号码

    @JSONField(name = "business_name")
    private String businessName; // 业务类型

    @JSONField(name = "fee")
    private String fee; // 费用

    @JSONField(name = "trade_addr")
    private String tradeAddr; // 短信地址

    @JSONField(name = "trade_type")
    private String tradeType; // 类型1：短信 2：彩信 3：其他

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getTradeWay() {
        return tradeWay;
    }

    public void setTradeWay(String tradeWay) {
        this.tradeWay = tradeWay;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
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

    public String getTradeAddr() {
        return tradeAddr;
    }

    public void setTradeAddr(String tradeAddr) {
        this.tradeAddr = tradeAddr;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}
