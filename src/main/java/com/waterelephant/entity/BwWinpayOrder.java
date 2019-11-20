package com.waterelephant.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Module: 微信支付订单
 *
 * BwWinpayOrder.java
 *
 * @author 崔雄健
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Table(name = "bw_winpay_order")
public class BwWinpayOrder implements Serializable {

    private static final long serialVersionUID = 8987699287832151124L;

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 转账金额
     */
    private BigDecimal money;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 转账流水号
     */
    private String winpayNo;

    /**
     * 转账账户名
     */
    private String transferName;

    private Integer type;

    private Long orderId;

    private String message;

    private Integer winpayType;

    private Date updateTime;

    private String otherOrderNo;

    private String thirdNo;

    private BigDecimal chargeMoney;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTransferName() {
        return transferName;
    }

    public void setTransferName(String transferName) {
        this.transferName = transferName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOtherOrderNo() {
        return otherOrderNo;
    }

    public void setOtherOrderNo(String otherOrderNo) {
        this.otherOrderNo = otherOrderNo;
    }

    public String getWinpayNo() {
        return winpayNo;
    }

    public void setWinpayNo(String winpayNo) {
        this.winpayNo = winpayNo;
    }

    public Integer getWinpayType() {
        return winpayType;
    }

    public void setWinpayType(Integer winpayType) {
        this.winpayType = winpayType;
    }

    public String getThirdNo() {
        return thirdNo;
    }

    public void setThirdNo(String thirdNo) {
        this.thirdNo = thirdNo;
    }

    public BigDecimal getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(BigDecimal chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

}
