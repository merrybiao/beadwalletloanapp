package com.waterelephant.dto.pay;

import java.io.Serializable;

/**
 * 工单扣款对外接口请求参数
 */
public class OrderWithholdRequest implements Serializable {
    private static final long serialVersionUID = 7252866367662880796L;
    private Long orderId;
    // 还支付类型（1、还款，2、展期）
    private Integer payType;
    // 终端类型（0.贷后代扣 1、Android，2、ios 3、h5 4.外部渠道 5.自动代扣）
    private Integer terminalType;
    // 支付方式，1.主动支付，2.贷后代扣，3.自动代扣，4.对公转账
    private Integer payWay;
    // 支付金额
    private Double payMoney;
    // 支付渠道。1.宝付 , 2.连连，5.支付宝，6.微信，7.口袋，9.易宝，10.合利宝，11.快捷通
    private Integer payChannel;
    private String sign;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(Integer terminalType) {
        this.terminalType = terminalType;
    }

    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}