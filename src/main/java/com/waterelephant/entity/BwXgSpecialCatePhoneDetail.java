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
@Table(name = "bw_xg_special_cate_phone_detail")
public class BwXgSpecialCatePhoneDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主键
    private Long borrowerId; // 借款人ID
    private Long specialCateId; // 特殊种类号码主表ID
    private String calledCnt; // 被叫次数
    private String talkSeconds; // 通话时长
    private String talkCnt; // 通话次数
    private String msgCnt; // 短信总数
    private String calledSeconds; // 被叫时长
    private String receiveCnt; // 接受短信数量
    private String callCnt; // 主叫次数
    private String unknownCnt; // 未知短信数量
    private String sendCnt; // 发送短信数量
    private String callSeconds; // 主叫时长
    private String phoneInfo; // 电话信息
    private String phone; // 电话号码

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

    public String getMsgCnt() {
        return msgCnt;
    }

    public void setMsgCnt(String msgCnt) {
        this.msgCnt = msgCnt;
    }

    public String getCalledSeconds() {
        return calledSeconds;
    }

    public void setCalledSeconds(String calledSeconds) {
        this.calledSeconds = calledSeconds;
    }

    public String getReceiveCnt() {
        return receiveCnt;
    }

    public void setReceiveCnt(String receiveCnt) {
        this.receiveCnt = receiveCnt;
    }

    public String getCallCnt() {
        return callCnt;
    }

    public void setCallCnt(String callCnt) {
        this.callCnt = callCnt;
    }

    public String getUnknownCnt() {
        return unknownCnt;
    }

    public void setUnknownCnt(String unknownCnt) {
        this.unknownCnt = unknownCnt;
    }

    public String getSendCnt() {
        return sendCnt;
    }

    public void setSendCnt(String sendCnt) {
        this.sendCnt = sendCnt;
    }

    public String getCallSeconds() {
        return callSeconds;
    }

    public void setCallSeconds(String callSeconds) {
        this.callSeconds = callSeconds;
    }

    public String getPhoneInfo() {
        return phoneInfo;
    }

    public void setPhoneInfo(String phoneInfo) {
        this.phoneInfo = phoneInfo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
