package com.waterelephant.drainage.jsonentity.fqgj;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 运营商月账单记录信息
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/22 17:03
 */
@SuppressWarnings("serial")
public class FqgjCarrierBill implements Serializable {

    @JSONField(name="month")
    private String month;//月份

    @JSONField(name="call_pay")
    private String callPay;//当月话费

    @JSONField(name="package_fee")
    private String packageFee;//套餐及固定费用

    @JSONField(name="msg_fee")
    private String msgFee;//额外套餐费-短信

    @JSONField(name="tel_fee")
    private String telFee;//额外套餐费-通话

    @JSONField(name="net_fee")
    private String netFee;//额外套餐费-流量

    @JSONField(name="addtional_fee")
    private String addtionalFee;//增值业务费

    @JSONField(name="preferential_fee")
    private String preferentialFee;//优惠费

    @JSONField(name="generation_fee")
    private String generationFee;//	代收费

    @JSONField(name="other_fee")
    private String otherFee;//其它费用

    @JSONField(name="score")
    private String score;//当月积分

    public String getMonth() {
        return month;
    }

    public FqgjCarrierBill setMonth(String month) {
        this.month = month;
        return this;
    }

    public String getCallPay() {
        return callPay;
    }

    public FqgjCarrierBill setCallPay(String callPay) {
        this.callPay = callPay;
        return this;
    }

    public String getPackageFee() {
        return packageFee;
    }

    public FqgjCarrierBill setPackageFee(String packageFee) {
        this.packageFee = packageFee;
        return this;
    }

    public String getMsgFee() {
        return msgFee;
    }

    public FqgjCarrierBill setMsgFee(String msgFee) {
        this.msgFee = msgFee;
        return this;
    }

    public String getTelFee() {
        return telFee;
    }

    public FqgjCarrierBill setTelFee(String telFee) {
        this.telFee = telFee;
        return this;
    }

    public String getNetFee() {
        return netFee;
    }

    public FqgjCarrierBill setNetFee(String netFee) {
        this.netFee = netFee;
        return this;
    }

    public String getAddtionalFee() {
        return addtionalFee;
    }

    public FqgjCarrierBill setAddtionalFee(String addtionalFee) {
        this.addtionalFee = addtionalFee;
        return this;
    }

    public String getPreferentialFee() {
        return preferentialFee;
    }

    public FqgjCarrierBill setPreferentialFee(String preferentialFee) {
        this.preferentialFee = preferentialFee;
        return this;
    }

    public String getGenerationFee() {
        return generationFee;
    }

    public FqgjCarrierBill setGenerationFee(String generationFee) {
        this.generationFee = generationFee;
        return this;
    }

    public String getOtherFee() {
        return otherFee;
    }

    public FqgjCarrierBill setOtherFee(String otherFee) {
        this.otherFee = otherFee;
        return this;
    }

    public String getScore() {
        return score;
    }

    public FqgjCarrierBill setScore(String score) {
        this.score = score;
        return this;
    }
}
