package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_operate_voice")
public class BwOperateVoice implements Serializable {
    private static final long serialVersionUID = -3826890542690815478L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long id;
    // (code:s2s)
    private Integer tradeType;
    private String tradeTime;
    private String callTime;
    private String tradeAddr;
    private String receivePhone;
    private Integer callType;
    private Long borrowerId;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTrade_type() {
        return tradeType;
    }

    public void setTrade_type(Integer trade_type) {
        this.tradeType = trade_type;
    }

    public String getTrade_time() {
        return tradeTime;
    }

    public void setTrade_time(String trade_time) {
        this.tradeTime = trade_time;
    }

    public String getCall_time() {
        return callTime;
    }

    public void setCall_time(String call_time) {
        this.callTime = call_time;
    }

    public String getTrade_addr() {
        return tradeAddr;
    }

    public void setTrade_addr(String trade_addr) {
        this.tradeAddr = trade_addr;
    }

    public String getReceive_phone() {
        return receivePhone;
    }

    public void setReceive_phone(String receive_phone) {
        this.receivePhone = receive_phone;
    }

    public Integer getCall_type() {
        return callType;
    }

    public void setCall_type(Integer call_type) {
        this.callType = call_type;
    }

    public Long getBorrower_id() {
        return borrowerId;
    }

    public void setBorrower_id(Long borrower_id) {
        this.borrowerId = borrower_id;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
