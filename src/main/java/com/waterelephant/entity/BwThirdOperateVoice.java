package com.waterelephant.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author 王亚楠
 * @version 1.0
 * @date 2018/6/8
 * @since JDK 1.8
 */
@Table(name = "bw_third_operate_voice")
public class BwThirdOperateVoice {


    /** 主键id */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;
    /** 我方订单号 */
    private Long orderId;
    /** 渠道来源 */
    private Integer channel;
    /** 通信类型（本地:1，漫游国内：2） */
    private Integer tradeType;
    /** 通话时长 */
    private String tradeTime;
    /** 通话时间 */
    private String callTime;
    /** 通话地点 */
    private String tradeAddr;
    /** 对方号码 */
    private String receivePhone;
    /** 呼叫类型（主叫：1，被叫：2） */
    private Integer callType;
    /** 备注1 */
    private String mark1;
    /** 备注2 */
    private String mark2;
    /** 备注3 */
    private String mark3;

    private Date createTime;
    private Date updateTime;

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

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getTradeAddr() {
        return tradeAddr;
    }

    public void setTradeAddr(String tradeAddr) {
        this.tradeAddr = tradeAddr;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public Integer getCallType() {
        return callType;
    }

    public void setCallType(Integer callType) {
        this.callType = callType;
    }

    public String getMark1() {
        return mark1;
    }

    public void setMark1(String mark1) {
        this.mark1 = mark1;
    }

    public String getMark2() {
        return mark2;
    }

    public void setMark2(String mark2) {
        this.mark2 = mark2;
    }

    public String getMark3() {
        return mark3;
    }

    public void setMark3(String mark3) {
        this.mark3 = mark3;
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
