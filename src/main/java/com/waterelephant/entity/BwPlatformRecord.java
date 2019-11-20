package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @Description:TODO
 * @author:yanfuxing
 * @time:2016年12月12日 上午9:24:34
 */
@Table(name = "bw_platform_record")
public class BwPlatformRecord implements Serializable {
    private static final long serialVersionUID = -5141444323698089914L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 交易流水号
     */
    @Column
    private String tradeNo;
    /**
     * 交易金额
     */
    @Column
    private Double tradeAmount;
    /**
     * 交易类型 1:划拨 2:转账
     */
    @Column
    private Integer tradeType;
    // 出账人账号
    @Column
    private String outAccount;
    // 出账人姓名
    @Column
    private String outName;
    // 进账人账号
    @Column
    private String inAccount;
    // 进账人姓名
    @Column
    private String inName;
    /**
     * 返回的code
     */
    @Column
    private String tradeCode;
    // 工单id
    @Column
    private Long orderId;
    @Column
    private String tradeRemark;
    /**
     * 交易时间
     */
    @Column
    private Date tradeTime;
    /**
     * 交易渠道。1：富友，2：宝付 , 3：连连
     */
    @Column
    private Integer tradeChannel;

    /**
     * 终端类型：0系统后台 1Android 2ios 3WAP 4外部渠道
     */
    @Column
    private Integer terminalType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Double getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(Double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public String getOutAccount() {
        return outAccount;
    }

    public void setOutAccount(String outAccount) {
        this.outAccount = outAccount;
    }

    public String getOutName() {
        return outName;
    }

    public void setOutName(String outName) {
        this.outName = outName;
    }

    public String getInAccount() {
        return inAccount;
    }

    public void setInAccount(String inAccount) {
        this.inAccount = inAccount;
    }

    public String getInName() {
        return inName;
    }

    public void setInName(String inName) {
        this.inName = inName;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTradeRemark() {
        return tradeRemark;
    }

    public void setTradeRemark(String tradeRemark) {
        this.tradeRemark = tradeRemark;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public Integer getTradeChannel() {
        return tradeChannel;
    }

    public void setTradeChannel(Integer tradeChannel) {
        this.tradeChannel = tradeChannel;
    }

    /**
     * @return 获取 terminalType属性值
     */
    public Integer getTerminalType() {
        return terminalType;
    }

    /**
     * @param terminalType 设置 terminalType 属性值为参数值 terminalType
     */
    public void setTerminalType(Integer terminalType) {
        this.terminalType = terminalType;
    }
}
