package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_all_contact_stats_per_month
 * @author 
 */
@Table(name = "bw_mf_all_contact_stats_per_month")
public class BwMfAllContactStatsPerMonth implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 工单id
     */
    private Long orderId;

    /**
     * 认证任务id
     */
    private String taskId;

    /**
     * 月主叫通话次数
     */
    private String callCountActive;

    /**
     * 月被叫通话次数
     */
    private String callCountPassive;

    /**
     * 月非工作时间通话时长
     */
    private String callTimeOffworkTime;

    /**
     * 月通话号码数量
     */
    private String contactCount;

    /**
     * 月通话时长
     */
    private String callTime;

    /**
     * 月通话时长1-5分钟的通话次数
     */
    @Column(name="call_count_call_time_1min5min")
    private String callCountCallTime1min5min;

    @Column(name="contact_count_call_count_over10")
    private String contactCountCallCountOver10;

    /**
     * 月工作时间通话时长
     */
    private String callTimeWorkTime;

    /**
     * 月主叫号码数量
     */
    private String contactCountActive;

    /**
     * 月通话时长<1分钟的通话次数
     */
    @Column(name="call_count_call_time_less1min")
    private String callCountCallTimeLess1min;

    /**
     * 月通话时长>=10分钟的通话次数
     */
    @Column(name="call_count_call_time_over10min")
    private String callCountCallTimeOver10min;

    /**
     * 月非工作时间通话次数
     */
    private String callCountOffworkTime;

    /**
     * 月深夜通话时长
     */
    private String callTimeLateNight;

    /**
     * 月工作时间通话次数
     */
    private String callCountWorkTime;

    /**
     * 月互通号码数量
     */
    private String contactCountMutual;

    /**
     * 月份
     */
    private String month;

    /**
     * 月被叫通话时长
     */
    private String callTimePassive;

    /**
     * 月深夜通话次数
     */
    private String callCountLateNight;

    /**
     * 月通话次数
     */
    private String callCount;

    /**
     * 月通话时长5-10分钟的通话次数
     */
    @Column(name="call_count_call_time_5min10min")
    private String callCountCallTime5min10min;

    /**
     * 月主叫通话时长
     */
    private String callTimeActive;

    /**
     * 月被叫号码数量
     */
    private String contactCountPassive;

    /**
     * 月短信次数
     */
    private String msgCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCallCountActive() {
        return callCountActive;
    }

    public void setCallCountActive(String callCountActive) {
        this.callCountActive = callCountActive;
    }

    public String getCallCountPassive() {
        return callCountPassive;
    }

    public void setCallCountPassive(String callCountPassive) {
        this.callCountPassive = callCountPassive;
    }

    public String getCallTimeOffworkTime() {
        return callTimeOffworkTime;
    }

    public void setCallTimeOffworkTime(String callTimeOffworkTime) {
        this.callTimeOffworkTime = callTimeOffworkTime;
    }

    public String getContactCount() {
        return contactCount;
    }

    public void setContactCount(String contactCount) {
        this.contactCount = contactCount;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getCallCountCallTime1min5min() {
        return callCountCallTime1min5min;
    }

    public void setCallCountCallTime1min5min(String callCountCallTime1min5min) {
        this.callCountCallTime1min5min = callCountCallTime1min5min;
    }

    public String getContactCountCallCountOver10() {
        return contactCountCallCountOver10;
    }

    public void setContactCountCallCountOver10(String contactCountCallCountOver10) {
        this.contactCountCallCountOver10 = contactCountCallCountOver10;
    }

    public String getCallTimeWorkTime() {
        return callTimeWorkTime;
    }

    public void setCallTimeWorkTime(String callTimeWorkTime) {
        this.callTimeWorkTime = callTimeWorkTime;
    }

    public String getContactCountActive() {
        return contactCountActive;
    }

    public void setContactCountActive(String contactCountActive) {
        this.contactCountActive = contactCountActive;
    }

    public String getCallCountCallTimeLess1min() {
        return callCountCallTimeLess1min;
    }

    public void setCallCountCallTimeLess1min(String callCountCallTimeLess1min) {
        this.callCountCallTimeLess1min = callCountCallTimeLess1min;
    }

    public String getCallCountCallTimeOver10min() {
        return callCountCallTimeOver10min;
    }

    public void setCallCountCallTimeOver10min(String callCountCallTimeOver10min) {
        this.callCountCallTimeOver10min = callCountCallTimeOver10min;
    }

    public String getCallCountOffworkTime() {
        return callCountOffworkTime;
    }

    public void setCallCountOffworkTime(String callCountOffworkTime) {
        this.callCountOffworkTime = callCountOffworkTime;
    }

    public String getCallTimeLateNight() {
        return callTimeLateNight;
    }

    public void setCallTimeLateNight(String callTimeLateNight) {
        this.callTimeLateNight = callTimeLateNight;
    }

    public String getCallCountWorkTime() {
        return callCountWorkTime;
    }

    public void setCallCountWorkTime(String callCountWorkTime) {
        this.callCountWorkTime = callCountWorkTime;
    }

    public String getContactCountMutual() {
        return contactCountMutual;
    }

    public void setContactCountMutual(String contactCountMutual) {
        this.contactCountMutual = contactCountMutual;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCallTimePassive() {
        return callTimePassive;
    }

    public void setCallTimePassive(String callTimePassive) {
        this.callTimePassive = callTimePassive;
    }

    public String getCallCountLateNight() {
        return callCountLateNight;
    }

    public void setCallCountLateNight(String callCountLateNight) {
        this.callCountLateNight = callCountLateNight;
    }

    public String getCallCount() {
        return callCount;
    }

    public void setCallCount(String callCount) {
        this.callCount = callCount;
    }

    public String getCallCountCallTime5min10min() {
        return callCountCallTime5min10min;
    }

    public void setCallCountCallTime5min10min(String callCountCallTime5min10min) {
        this.callCountCallTime5min10min = callCountCallTime5min10min;
    }

    public String getCallTimeActive() {
        return callTimeActive;
    }

    public void setCallTimeActive(String callTimeActive) {
        this.callTimeActive = callTimeActive;
    }

    public String getContactCountPassive() {
        return contactCountPassive;
    }

    public void setContactCountPassive(String contactCountPassive) {
        this.contactCountPassive = contactCountPassive;
    }

    public String getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(String msgCount) {
        this.msgCount = msgCount;
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