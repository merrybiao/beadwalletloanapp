package com.waterelephant.rongCarrier.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 漫游详情
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/17 13:38
 */
@Table(name="bw_xg_special_cate_phone_detail")
public class XgSpecialCatePhoneDetail {
    private static final long serialVersionUID = 75685621252455L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long borrowerId;
    private Long specialCateId;

    private String calledCnt;
    private String talkSeconds;
    private String talkCnt;
    private String receiveCnt;
    private String calledSeconds;
    private String msgCnt;
    private String callCnt;
    private String phone;

    private String sendCnt;

    private String unknownCnt;
    private String callSeconds;
    private String phoneInfo;

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

    public Long getSpecialCateId() {
        return specialCateId;
    }

    public void setSpecialCateId(Long specialCateId) {
        this.specialCateId = specialCateId;
    }

    public String getCalledCnt() {
        return calledCnt;
    }

    public void setCalledCnt(String calledCnt) {
        this.calledCnt = calledCnt;
    }

    public String getTalkSeconds() {
        return talkSeconds;
    }

    public void setTalkSeconds(String talkSeconds) {
        this.talkSeconds = talkSeconds;
    }

    public String getTalkCnt() {
        return talkCnt;
    }

    public void setTalkCnt(String talkCnt) {
        this.talkCnt = talkCnt;
    }

    public String getPhoneInfo() {
        return phoneInfo;
    }

    public void setPhoneInfo(String phoneInfo) {
        this.phoneInfo = phoneInfo;
    }

    public String getReceiveCnt() {
        return receiveCnt;
    }

    public void setReceiveCnt(String receiveCnt) {
        this.receiveCnt = receiveCnt;
    }

    public String getCalledSeconds() {
        return calledSeconds;
    }

    public void setCalledSeconds(String calledSeconds) {
        this.calledSeconds = calledSeconds;
    }

    public String getMsgCnt() {
        return msgCnt;
    }

    public void setMsgCnt(String msgCnt) {
        this.msgCnt = msgCnt;
    }

    public String getCallCnt() {
        return callCnt;
    }

    public void setCallCnt(String callCnt) {
        this.callCnt = callCnt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSendCnt() {
        return sendCnt;
    }

    public void setSendCnt(String sendCnt) {
        this.sendCnt = sendCnt;
    }

    public String getUnknownCnt() {
        return unknownCnt;
    }

    public void setUnknownCnt(String unknownCnt) {
        this.unknownCnt = unknownCnt;
    }

    public String getCallSeconds() {
        return callSeconds;
    }

    public void setCallSeconds(String callSeconds) {
        this.callSeconds = callSeconds;
    }
}
