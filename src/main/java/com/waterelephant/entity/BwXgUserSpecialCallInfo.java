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
@Table(name = "bw_xg_user_special_call_info")
public class BwXgUserSpecialCallInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主键
    private Long borrowerId; // 借款人ID

    private Integer calledCnt; // 被叫次数
    private Integer talkSeconds; // 通话时长
    private Integer talkCnt; // 通话次数
    private Integer msgCnt; // 短信总数
    private Integer calledSeconds; // 被叫时长
    private Integer receiveCnt; // 接受短信数量
    private String detail; // 分月详情
    private Integer callCnt; // 主叫次数
    private Integer unknownCnt; // 未知短信数量
    private Integer sendCnt; // 发送短信数量
    private Integer callSeconds; // 主叫时长
    private String phoneList; // 号码详情

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public Integer getCallSeconds() {
        return callSeconds;
    }

    public void setCallSeconds(Integer callSeconds) {
        this.callSeconds = callSeconds;
    }

    public String getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(String phoneList) {
        this.phoneList = phoneList;
    }

}
