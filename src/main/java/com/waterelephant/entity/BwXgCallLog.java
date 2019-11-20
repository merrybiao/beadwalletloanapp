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
@Table(name = "bw_xg_call_log")
public class BwXgCallLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主键
    private Long borrowerId; // 借款人ID
    private Integer contactNonn; // 上午联系次数
    private Integer talkSeconds; // 通话时长
    private Integer talkCnt; // 通话次数
    private Integer contact3m; // 近三个月联系次数
    private Integer msgCnt; // 短信总数
    private Integer contact1m; // 近一个月联系次数
    private Integer unknownCnt; // 未识别状态短信数
    private Integer contactEveing; // 夜晚联系次数
    private Integer contact1w; // 近一周联系次数
    private String phoneInfo; // 互联网标识
    private Integer calledSeconds; // 被叫时长
    private Integer callCnt; // 主叫次数
    private Integer calledCnt; // 被叫次数
    private Integer contactWeekday; // 周末联系次数
    private Integer receiveCnt; // 接收短信数
    private String phone; // 号码
    private Integer callSeconds; // 主叫时长
    private String firstContactDate; // 首次联系时间
    private Integer contactAfternoon; // 下午联系次数
    private Integer contactEarlyMorning; // 凌晨联系次数
    private String lastContactDate; // 最后联系时间
    private Integer contactNight; // 深夜联系次数
    private String phoneLabel; // 类别标签
    private Integer sendCnt; // 发送短信数
    private String phoneLocation; // 号码归属地
    private Integer contactMorning; // 早晨联系次数
    private Integer contactWeekend; // 周末联系次数

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

    public Integer getContactNonn() {
        return contactNonn;
    }

    public void setContactNonn(Integer contactNonn) {
        this.contactNonn = contactNonn;
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

    public Integer getContact3m() {
        return contact3m;
    }

    public void setContact3m(Integer contact3m) {
        this.contact3m = contact3m;
    }

    public Integer getMsgCnt() {
        return msgCnt;
    }

    public void setMsgCnt(Integer msgCnt) {
        this.msgCnt = msgCnt;
    }

    public Integer getContact1m() {
        return contact1m;
    }

    public void setContact1m(Integer contact1m) {
        this.contact1m = contact1m;
    }

    public Integer getUnknownCnt() {
        return unknownCnt;
    }

    public void setUnknownCnt(Integer unknownCnt) {
        this.unknownCnt = unknownCnt;
    }

    public Integer getContactEveing() {
        return contactEveing;
    }

    public void setContactEveing(Integer contactEveing) {
        this.contactEveing = contactEveing;
    }

    public Integer getContact1w() {
        return contact1w;
    }

    public void setContact1w(Integer contact1w) {
        this.contact1w = contact1w;
    }

    public String getPhoneInfo() {
        return phoneInfo;
    }

    public void setPhoneInfo(String phoneInfo) {
        this.phoneInfo = phoneInfo;
    }

    public Integer getCalledSeconds() {
        return calledSeconds;
    }

    public void setCalledSeconds(Integer calledSeconds) {
        this.calledSeconds = calledSeconds;
    }

    public Integer getCallCnt() {
        return callCnt;
    }

    public void setCallCnt(Integer callCnt) {
        this.callCnt = callCnt;
    }

    public Integer getCalledCnt() {
        return calledCnt;
    }

    public void setCalledCnt(Integer calledCnt) {
        this.calledCnt = calledCnt;
    }

    public Integer getContactWeekday() {
        return contactWeekday;
    }

    public void setContactWeekday(Integer contactWeekday) {
        this.contactWeekday = contactWeekday;
    }

    public Integer getReceiveCnt() {
        return receiveCnt;
    }

    public void setReceiveCnt(Integer receiveCnt) {
        this.receiveCnt = receiveCnt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCallSeconds() {
        return callSeconds;
    }

    public void setCallSeconds(Integer callSeconds) {
        this.callSeconds = callSeconds;
    }

    public String getFirstContactDate() {
        return firstContactDate;
    }

    public void setFirstContactDate(String firstContactDate) {
        this.firstContactDate = firstContactDate;
    }

    public Integer getContactAfternoon() {
        return contactAfternoon;
    }

    public void setContactAfternoon(Integer contactAfternoon) {
        this.contactAfternoon = contactAfternoon;
    }

    public Integer getContactEarlyMorning() {
        return contactEarlyMorning;
    }

    public void setContactEarlyMorning(Integer contactEarlyMorning) {
        this.contactEarlyMorning = contactEarlyMorning;
    }

    public String getLastContactDate() {
        return lastContactDate;
    }

    public void setLastContactDate(String lastContactDate) {
        this.lastContactDate = lastContactDate;
    }

    public Integer getContactNight() {
        return contactNight;
    }

    public void setContactNight(Integer contactNight) {
        this.contactNight = contactNight;
    }

    public String getPhoneLabel() {
        return phoneLabel;
    }

    public void setPhoneLabel(String phoneLabel) {
        this.phoneLabel = phoneLabel;
    }

    public Integer getSendCnt() {
        return sendCnt;
    }

    public void setSendCnt(Integer sendCnt) {
        this.sendCnt = sendCnt;
    }

    public String getPhoneLocation() {
        return phoneLocation;
    }

    public void setPhoneLocation(String phoneLocation) {
        this.phoneLocation = phoneLocation;
    }

    public Integer getContactMorning() {
        return contactMorning;
    }

    public void setContactMorning(Integer contactMorning) {
        this.contactMorning = contactMorning;
    }

    public Integer getContactWeekend() {
        return contactWeekend;
    }

    public void setContactWeekend(Integer contactWeekend) {
        this.contactWeekend = contactWeekend;
    }
}
