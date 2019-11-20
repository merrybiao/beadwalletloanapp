package com.waterelephant.rongCarrier.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 地域分析
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/17 10:58
 */
@Table(name="bw_xg_emergency_analysis")
public class XgEmergencyAnalysis {
    private static final long serialVersionUID = 78941231242455L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long borrowerId;

    private String phone;
    private String name;
    private String firstContactDate;
    private String lastContactDate;
    private Integer talkSeconds;
    private Integer talkCnt;
    private Integer callSeconds;
    private Integer callCnt;

    private Integer calledSeconds;
    private Integer calledCnt;
    private Integer msgCnt;
    private Integer sendCnt;
    private Integer receiveCnt;
    private Integer unknownCnt;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstContactDate() {
        return firstContactDate;
    }

    public void setFirstContactDate(String firstContactDate) {
        this.firstContactDate = firstContactDate;
    }

    public String getLastContactDate() {
        return lastContactDate;
    }

    public void setLastContactDate(String lastContactDate) {
        this.lastContactDate = lastContactDate;
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

    public Integer getCallSeconds() {
        return callSeconds;
    }

    public void setCallSeconds(Integer callSeconds) {
        this.callSeconds = callSeconds;
    }

    public Integer getCallCnt() {
        return callCnt;
    }

    public void setCallCnt(Integer callCnt) {
        this.callCnt = callCnt;
    }

    public Integer getCalledSeconds() {
        return calledSeconds;
    }

    public void setCalledSeconds(Integer calledSeconds) {
        this.calledSeconds = calledSeconds;
    }

    public Integer getCalledCnt() {
        return calledCnt;
    }

    public void setCalledCnt(Integer calledCnt) {
        this.calledCnt = calledCnt;
    }

    public Integer getMsgCnt() {
        return msgCnt;
    }

    public void setMsgCnt(Integer msgCnt) {
        this.msgCnt = msgCnt;
    }

    public Integer getSendCnt() {
        return sendCnt;
    }

    public void setSendCnt(Integer sendCnt) {
        this.sendCnt = sendCnt;
    }

    public Integer getReceiveCnt() {
        return receiveCnt;
    }

    public void setReceiveCnt(Integer receiveCnt) {
        this.receiveCnt = receiveCnt;
    }

    public Integer getUnknownCnt() {
        return unknownCnt;
    }

    public void setUnknownCnt(Integer unknownCnt) {
        this.unknownCnt = unknownCnt;
    }
}
