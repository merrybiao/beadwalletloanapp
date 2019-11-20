package com.waterelephant.dto.pay;

import java.io.Serializable;

/**
 * 工单扣款对外接口返回参数
 */
public class OrderWithholdResponse implements Serializable {
    private static final long serialVersionUID = 3761052598226996132L;
    private Long orderId;
    // 还支付类型（1、还款，2、展期）
    private Integer payType;
    // 支付金额
    private Double payMoney;
    // 支付渠道。1.宝付 , 2.连连，5.支付宝，6.微信，7.口袋，9.易宝，10.合利宝，11.快捷通
    private Integer payChannel;
    /**
     * 代扣调用状态，-2.异常，-1.超时，0.初始状态，1.处理中，2.成功，3.失败，4.失败（）
     */
    private Integer status;
    private String errMsg;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}