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
@Table(name = "bw_xg_area_analysis")
public class BwXgAreaAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主键
    private Long borrowerId; // 借款人ID
    private Integer calledCnt; // 被叫次数
    private Integer talkSeconds; // 通话时长
    private Integer talkCnt; // 通话次数
    private String area; // 地区
    private Integer receiveCnt; // 接受短信数量
    private Integer calledSeconds; // 被叫时长
    private Integer msgCnt; // 短信数量
    private Integer callCnt; // 主叫次数
    private Integer unknownCnt; // 未识别短信数量
    private Integer contactPhoneCnt; // 联系号码数量
    private Integer sendCnt; // 发送短信数量
    private Integer callSeconds; // 主叫时长

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getReceiveCnt() {
        return receiveCnt;
    }

    public void setReceiveCnt(Integer receiveCnt) {
        this.receiveCnt = receiveCnt;
    }

    public Integer getCalledSeconds() {
        return calledSeconds;
    }

    public void setCalledSeconds(Integer calledSeconds) {
        this.calledSeconds = calledSeconds;
    }

    public Integer getMsgCnt() {
        return msgCnt;
    }

    public void setMsgCnt(Integer msgCnt) {
        this.msgCnt = msgCnt;
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

    public Integer getContactPhoneCnt() {
        return contactPhoneCnt;
    }

    public void setContactPhoneCnt(Integer contactPhoneCnt) {
        this.contactPhoneCnt = contactPhoneCnt;
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

}
