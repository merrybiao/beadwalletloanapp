package com.waterelephant.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * BwCapitalPush.java
 *
 * @author 崔雄健
 * @since JDK 1.8
 * @version 1.0
 * @description: 资方扣款记录
 */
@Table(name = "bw_capital_withhold")
public class BwCapitalWithhold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 主键ID
    private Long orderId;// 工单ID
    private String orderNo;// 工单ID
    private Integer capitalId;// 理财渠道
    private Integer pushStatus;// 状态。0：待扣款，1：扣款中，2：成功，3：失败
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间
    private String otherOrderNo;
    private String code;
    private String msg;
    private Integer thirdPlatform;
    private BigDecimal money;
    private BigDecimal moneyWithhold;
    private String cardNo;
    /**
     * 支付类型，1.还款 2.展期
     */
    private Integer payType;
    /**
     * 终端类型：0系统后台 1Android 2ios 3WAP 4外部渠道
     */
    private Integer terminalType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getCapitalId() {
        return capitalId;
    }

    public void setCapitalId(Integer capitalId) {
        this.capitalId = capitalId;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
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

    public String getOtherOrderNo() {
        return otherOrderNo;
    }

    public void setOtherOrderNo(String otherOrderNo) {
        this.otherOrderNo = otherOrderNo;
    }

    public Integer getThirdPlatform() {
        return thirdPlatform;
    }

    public void setThirdPlatform(Integer thirdPlatform) {
        this.thirdPlatform = thirdPlatform;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getMoneyWithhold() {
        return moneyWithhold;
    }

    public void setMoneyWithhold(BigDecimal moneyWithhold) {
        this.moneyWithhold = moneyWithhold;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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
}
