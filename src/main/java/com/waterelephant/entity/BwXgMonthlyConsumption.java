package com.waterelephant.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author lwl
 *
 */
@Table(name = "bw_xg_monthly_consumption")
public class BwXgMonthlyConsumption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /** 主键 */
    private Long id;
    private Long borrowerId;
    private String month;
    private Integer calledCnt;
    private Long talkSeconds;
    private Integer talkCnt;
    private Integer msgCnt;
    private Long calledSeconds;
    private Integer receiveCnt;
    private Integer callCnt;
    private Integer unknownCnt;
    private Integer sendCnt;
    private Long callSeconds;
    private Double callConsumption;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getCalledCnt() {
        return calledCnt;
    }

    public void setCalledCnt(Integer calledCnt) {
        this.calledCnt = calledCnt;
    }

    public Long getTalkSeconds() {
        return talkSeconds;
    }

    public void setTalkSeconds(Long talkSeconds) {
        this.talkSeconds = talkSeconds;
    }

    public Integer getTalkCnt() {
        return talkCnt;
    }

    public void setTalkCnt(Integer talkCnt) {
        this.talkCnt = talkCnt;
    }

    public Integer getMsgCnt() {
        return msgCnt;
    }

    public void setMsgCnt(Integer msgCnt) {
        this.msgCnt = msgCnt;
    }

    public Long getCalledSeconds() {
        return calledSeconds;
    }

    public void setCalledSeconds(Long calledSeconds) {
        this.calledSeconds = calledSeconds;
    }

    public Integer getReceiveCnt() {
        return receiveCnt;
    }

    public void setReceiveCnt(Integer receiveCnt) {
        this.receiveCnt = receiveCnt;
    }

    public Integer getCallCnt() {
        return callCnt;
    }

    public void setCallCnt(Integer callCnt) {
        this.callCnt = callCnt;
    }

    public Integer getUnknownCnt() {
        return unknownCnt;
    }

    public void setUnknownCnt(Integer unknownCnt) {
        this.unknownCnt = unknownCnt;
    }

    public Integer getSendCnt() {
        return sendCnt;
    }

    public void setSendCnt(Integer sendCnt) {
        this.sendCnt = sendCnt;
    }

    public Long getCallSeconds() {
        return callSeconds;
    }

    public void setCallSeconds(Long callSeconds) {
        this.callSeconds = callSeconds;
    }

    public Double getCallConsumption() {
        return callConsumption;
    }

    public void setCallConsumption(Double callConsumption) {
        this.callConsumption = callConsumption;
    }


}
