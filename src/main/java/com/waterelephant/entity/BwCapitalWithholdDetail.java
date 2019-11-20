package com.waterelephant.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "bw_capital_withhold_detail")
public class BwCapitalWithholdDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long capitalWithholdId;
    /**
     * 商户订单号
     */
    private String mchOrderNo;

    /**
     * 渠道订单号
     */
    private String channelOrderNo;

    /**
     * 渠道批次号
     */
    private String channelBatchNo;

    /**
     * 优惠券ID
     */
    private Long distributeId;

    /**
     * 交易渠道。1：宝付 , 3：连连，5：支付宝，6：微信，7：口袋，9：易宝，10：合利宝，11：快捷通，15：宝付协议支付，16畅捷代扣
     */
    private Integer payChannel;

    /**
     * 支付方式，1.主动支付，2.贷后代扣，3.自动代扣，4.对公转账
     */
    private Integer payWay;

    /**
     * 支付渠道ID，1.宝付，2.连连，3.益倍嘉，4.合利宝，5.汇潮，6.快捷通，7.口袋，8.畅捷，9.易宝，10.通联
     */
    private Long payChannelId;

    /**
     * 支付产品ID，1.代扣，2.协议支付，3.快捷支付，5.支付宝，6.微信
     */
    private Long payProductId;

    /**
     * 扩展JSON串
     */
    private String extra;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCapitalWithholdId() {
        return capitalWithholdId;
    }

    public void setCapitalWithholdId(Long capitalWithholdId) {
        this.capitalWithholdId = capitalWithholdId;
    }

    public String getMchOrderNo() {
        return mchOrderNo;
    }

    public void setMchOrderNo(String mchOrderNo) {
        this.mchOrderNo = mchOrderNo;
    }

    public String getChannelOrderNo() {
        return channelOrderNo;
    }

    public void setChannelOrderNo(String channelOrderNo) {
        this.channelOrderNo = channelOrderNo;
    }

    public String getChannelBatchNo() {
        return channelBatchNo;
    }

    public void setChannelBatchNo(String channelBatchNo) {
        this.channelBatchNo = channelBatchNo;
    }

    public Long getDistributeId() {
        return distributeId;
    }

    public void setDistributeId(Long distributeId) {
        this.distributeId = distributeId;
    }

    public Integer getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    public Long getPayChannelId() {
        return payChannelId;
    }

    public void setPayChannelId(Long payChannelId) {
        this.payChannelId = payChannelId;
    }

    public Long getPayProductId() {
        return payProductId;
    }

    public void setPayProductId(Long payProductId) {
        this.payProductId = payProductId;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
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
