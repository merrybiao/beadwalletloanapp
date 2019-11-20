package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 月账单记录
 * Created by dengyan on 2017/7/20.
 */
public class Bill {
    @JSONField(name = "month")
    private String month; // 月份

    @JSONField(name = "call_pay")
    private String callPay; // 当月话费

    @JSONField(name = "package_fee")
    private String packageFee; // 套餐及固定费用

    @JSONField(name = "msg_fee")
    private String msgFee; // 额外套餐费-短信

    @JSONField(name = "tel_fee")
    private String telFee; // 额外套餐费-通话

    @JSONField(name = "net_fee")
    private String netFee; // 额外套餐费-流量

    @JSONField(name = "addtional_fee")
    private String addtionalFee; // 增值业务费

    @JSONField(name = "preferential_fee")
    private String preferentialFee; // 优惠费

    @JSONField(name = "generation_fee")
    private String generationFee; // 代收费

    @JSONField(name = "other_fee")
    private String otherFee; // 其它费用

    @JSONField(name = "score")
    private String score; // 当月积分

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCallPay() {
        return callPay;
    }

    public void setCallPay(String callPay) {
        this.callPay = callPay;
    }

    public String getPackageFee() {
        return packageFee;
    }

    public void setPackageFee(String packageFee) {
        this.packageFee = packageFee;
    }

    public String getMsgFee() {
        return msgFee;
    }

    public void setMsgFee(String msgFee) {
        this.msgFee = msgFee;
    }

    public String getTelFee() {
        return telFee;
    }

    public void setTelFee(String telFee) {
        this.telFee = telFee;
    }

    public String getNetFee() {
        return netFee;
    }

    public void setNetFee(String netFee) {
        this.netFee = netFee;
    }

    public String getAddtionalFee() {
        return addtionalFee;
    }

    public void setAddtionalFee(String addtionalFee) {
        this.addtionalFee = addtionalFee;
    }

    public String getPreferentialFee() {
        return preferentialFee;
    }

    public void setPreferentialFee(String preferentialFee) {
        this.preferentialFee = preferentialFee;
    }

    public String getGenerationFee() {
        return generationFee;
    }

    public void setGenerationFee(String generationFee) {
        this.generationFee = generationFee;
    }

    public String getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(String otherFee) {
        this.otherFee = otherFee;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
