package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "wsm_order_push")
public class WsmOrderPush implements Serializable {

    /**
     *
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 编号
    private String orderId;// 我方订单Id
    private String wsmOrderNo;// 微神马商户订单号
    private String status;// 放款状态
    private String code;// 成功为0000，失败为对应错误码
    private String msg;// 错误信息
    private String paymentTime;// 放款时间
    private String contractLink;// 合同地址
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间
    private String bank;// 放款银行名称
    private Double bankRate;// 银行利率
    private Double serviceCost;// 服务费
    private Double totalInterest;// 总利息

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Double getBankRate() {
        return bankRate;
    }

    public void setBankRate(Double bankRate) {
        this.bankRate = bankRate;
    }

    public Double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(Double serviceCost) {
        this.serviceCost = serviceCost;
    }

    public Double getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(Double totalInterest) {
        this.totalInterest = totalInterest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getWsmOrderNo() {
        return wsmOrderNo;
    }

    public void setWsmOrderNo(String wsmOrderNo) {
        this.wsmOrderNo = wsmOrderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getContractLink() {
        return contractLink;
    }

    public void setContractLink(String contractLink) {
        this.contractLink = contractLink;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }



}
