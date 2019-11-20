package com.waterelephant.rongCarrier.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * S
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/17 14:52
 */
@Table(name="bw_xg_monthly_consumption")
public class XgMonthlyConsumption {

    private static final long serialVersionUID = 752314121252455L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long borrowerId;

    private Integer calledCnt;
    private Integer talkSeconds;
    private Integer talkCnt;
    private Integer msgCnt;
    private Integer calledSeconds;
    private Integer receiveCnt;
    private Integer callCnt;
    private Integer callSeconds;
    private Integer unknownCnt;
    private Integer sendCnt;
    private String month;
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

    public Integer getCalledCnt() {
        return calledCnt;
    }

    public void setCalledCnt(Integer calledCnt) {
        this.calledCnt = calledCnt;
    }

    public Integer getTalkSeconds() {
        return talkSeconds;
    }

    public void setTalkSeconds(Integer talkSeconds) {
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

    public Integer getCalledSeconds() {
        return calledSeconds;
    }

    public void setCalledSeconds(Integer calledSeconds) {
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

    public Integer getCallSeconds() {
        return callSeconds;
    }

    public void setCallSeconds(Integer callSeconds) {
        this.callSeconds = callSeconds;
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getCallConsumption() {
        return callConsumption;
    }

    public void setCallConsumption(Double callConsumption) {
        this.callConsumption = callConsumption;
    }
}
