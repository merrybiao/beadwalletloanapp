package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_work_tel_detail
 * @author 
 */
@Table(name = "bw_mf_work_tel_detail")
public class BwMfWorkTelDetail implements Serializable {
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
     * 近3月节假日通话次数
     */
    @Column(name="call_count_holiday_3month")
    private String callCountHoliday3month;

    /**
     * 近6月节假日通话次数
     */
    @Column(name="call_count_holiday_6month")
    private String callCountHoliday6month;

    /**
     * 号码属性
     */
    private String contactAttribute;

    /**
     * 近6月主叫通话次数
     */
    @Column(name="call_count_active_6month")
    private String callCountActive6month;

    /**
     * 近3月通话时长
     */
    @Column(name="call_time_3month")
    private String callTime3month;

    /**
     * 近3月短信次数
     */
    @Column(name="msg_count_3month")
    private String msgCount3month;

    /**
     * 近1月通话时长
     */
    @Column(name="call_time_1month")
    private String callTime1month;

    /**
     * 近6月被叫通话时长
     */
    @Column(name="call_time_passive_6month")
    private String callTimePassive6month;

    /**
     * 近3月主叫通话次数
     */
    @Column(name="call_count_active_3month")
    private String callCountActive3month;

    /**
     * 近6月通话时长
     */
    @Column(name="call_time_6month")
    private String callTime6month;

    /**
     * 号码分类
     */
    private String contactType;

    /**
     * 号码是否小号
     */
    private String isVirtualNumber;

    /**
     * 近3月深夜通话次数
     */
    @Column(name="call_count_late_night_3month")
    private String callCountLateNight3month;

    /**
     * 近6月深夜通话次数
     */
    @Column(name="call_count_late_night_6month")
    private String callCountLateNight6month;

    /**
     * 近6月被叫通话次数
     */
    @Column(name="call_count_passive_6month")
    private String callCountPassive6month;

    /**
     * 近6月主叫通话时长
     */
    @Column(name="call_time_active_6month")
    private String callTimeActive6month;

    /**
     * 近6月短信次数
     */
    @Column(name="msg_count_6month")
    private String msgCount6month;

    /**
     * 近6月工作时间通话次数
     */
    @Column(name="call_count_work_time_6month")
    private String callCountWorkTime6month;

    /**
     * 近3月主叫通话时长
     */
    @Column(name="call_time_active_3month")
    private String callTimeActive3month;

    /**
     * 近3月被叫通话次数
     */
    @Column(name="call_count_passive_3month")
    private String callCountPassive3month;

    /**
     * 近3月通话次数
     */
    @Column(name="call_count_3month")
    private String callCount3month;

    /**
     * 号码标签
     */
    private String contactName;

    /**
     * 联系人关系
     */
    private String contactRelation;

    /**
     * 近3月工作时间通话次数
     */
    @Column(name="call_count_work_time_3month")
    private String callCountWorkTime3month;

    /**
     * 近3月工作日通话次数
     */
    @Column(name="call_count_workday_3month")
    private String callCountWorkday3month;

    /**
     * 近6月通话次数
     */
    @Column(name="call_count_6month")
    private String callCount6month;

    /**
     * 近3月非工作时间通话次数
     */
    @Column(name="call_count_offwork_time_3month")
    private String callCountOffworkTime3month;

    /**
     * 近6月非工作时间通话次数
     */
    @Column(name="call_count_offwork_time_6month")
    private String callCountOffworkTime6month;

    /**
     * 联系人号码
     */
    private String contactNumber;

    /**
     * 号码归属地
     */
    private String contactArea;

    /**
     * 近1周通话次数
     */
    @Column(name="call_count_1week")
    private String callCount1week;

    /**
     * 联系人排名
     */
    private String contactSeqNo;

    /**
     * 近1月通话次数
     */
    @Column(name="call_count_1month")
    private String callCount1month;

    /**
     * 近3月被叫通话时长
     */
    @Column(name="call_time_passive_3month")
    private String callTimePassive3month;

    /**
     * 近6月工作日通话次数
     */
    @Column(name="call_count_workday_6month")
    private String callCountWorkday6month;

    /**
     * 近6月非工作时间通话时长
     */
    @Column(name="call_time_offwork_time_6month")
    private String callTimeOffworkTime6month;

    @Column(name="call_time_offwork_time_3month")
    private String callTimeOffworkTime3month;

    /**
     * 近6月工作时间通话时长
     */
    @Column(name="call_time_work_time_6month")
    private String callTimeWorkTime6month;

    /**
     * 近3月工作时间通话时长
     */
    @Column(name="call_time_work_time_3month")
    private String callTimeWorkTime3month;

    /**
     * 近6月深夜通话时长
     */
    @Column(name="call_time_late_night_6month")
    private String callTimeLateNight6month;

    /**
     * 近3月深夜通话时长
     */
    @Column(name="call_time_late_night_3month")
    private String callTimeLateNight3month;

    /**
     * 近1月短信次数
     */
    @Column(name="msg_count_1month")
    private String msgCount1month;

    /**
     * 近6个月是否有全天通话
     */
    @Column(name="is_whole_day_call_6month")
    private String isWholeDayCall6month;

    /**
     * 近3个月是否有全天通话
     */
    @Column(name="is_whole_day_call_3month")
    private String isWholeDayCall3month;

    /**
     * 近6月第一次通话时间
     */
    @Column(name="first_time_call_6month")
    private String firstTimeCall6month;

    /**
     * 近3月第一次通话时间
     */
    @Column(name="last_time_call_6month")
    private String lastTimeCall6month;

    /**
     * 近6月最长通话间隔天数
     */
    @Column(name="max_gap_day_call_6month")
    private String maxGapDayCall6month;

    /**
     * 近6月平均通话间隔天数
     */
    @Column(name="average_gap_day_call_6month")
    private String averageGapDayCall6month;

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

    public String getCallCountHoliday3month() {
        return callCountHoliday3month;
    }

    public void setCallCountHoliday3month(String callCountHoliday3month) {
        this.callCountHoliday3month = callCountHoliday3month;
    }

    public String getCallCountHoliday6month() {
        return callCountHoliday6month;
    }

    public void setCallCountHoliday6month(String callCountHoliday6month) {
        this.callCountHoliday6month = callCountHoliday6month;
    }

    public String getContactAttribute() {
        return contactAttribute;
    }

    public void setContactAttribute(String contactAttribute) {
        this.contactAttribute = contactAttribute;
    }

    public String getCallCountActive6month() {
        return callCountActive6month;
    }

    public void setCallCountActive6month(String callCountActive6month) {
        this.callCountActive6month = callCountActive6month;
    }

    public String getCallTime3month() {
        return callTime3month;
    }

    public void setCallTime3month(String callTime3month) {
        this.callTime3month = callTime3month;
    }

    public String getMsgCount3month() {
        return msgCount3month;
    }

    public void setMsgCount3month(String msgCount3month) {
        this.msgCount3month = msgCount3month;
    }

    public String getCallTime1month() {
        return callTime1month;
    }

    public void setCallTime1month(String callTime1month) {
        this.callTime1month = callTime1month;
    }

    public String getCallTimePassive6month() {
        return callTimePassive6month;
    }

    public void setCallTimePassive6month(String callTimePassive6month) {
        this.callTimePassive6month = callTimePassive6month;
    }

    public String getCallCountActive3month() {
        return callCountActive3month;
    }

    public void setCallCountActive3month(String callCountActive3month) {
        this.callCountActive3month = callCountActive3month;
    }

    public String getCallTime6month() {
        return callTime6month;
    }

    public void setCallTime6month(String callTime6month) {
        this.callTime6month = callTime6month;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getIsVirtualNumber() {
        return isVirtualNumber;
    }

    public void setIsVirtualNumber(String isVirtualNumber) {
        this.isVirtualNumber = isVirtualNumber;
    }

    public String getCallCountLateNight3month() {
        return callCountLateNight3month;
    }

    public void setCallCountLateNight3month(String callCountLateNight3month) {
        this.callCountLateNight3month = callCountLateNight3month;
    }

    public String getCallCountLateNight6month() {
        return callCountLateNight6month;
    }

    public void setCallCountLateNight6month(String callCountLateNight6month) {
        this.callCountLateNight6month = callCountLateNight6month;
    }

    public String getCallCountPassive6month() {
        return callCountPassive6month;
    }

    public void setCallCountPassive6month(String callCountPassive6month) {
        this.callCountPassive6month = callCountPassive6month;
    }

    public String getCallTimeActive6month() {
        return callTimeActive6month;
    }

    public void setCallTimeActive6month(String callTimeActive6month) {
        this.callTimeActive6month = callTimeActive6month;
    }

    public String getMsgCount6month() {
        return msgCount6month;
    }

    public void setMsgCount6month(String msgCount6month) {
        this.msgCount6month = msgCount6month;
    }

    public String getCallCountWorkTime6month() {
        return callCountWorkTime6month;
    }

    public void setCallCountWorkTime6month(String callCountWorkTime6month) {
        this.callCountWorkTime6month = callCountWorkTime6month;
    }

    public String getCallTimeActive3month() {
        return callTimeActive3month;
    }

    public void setCallTimeActive3month(String callTimeActive3month) {
        this.callTimeActive3month = callTimeActive3month;
    }

    public String getCallCountPassive3month() {
        return callCountPassive3month;
    }

    public void setCallCountPassive3month(String callCountPassive3month) {
        this.callCountPassive3month = callCountPassive3month;
    }

    public String getCallCount3month() {
        return callCount3month;
    }

    public void setCallCount3month(String callCount3month) {
        this.callCount3month = callCount3month;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactRelation() {
        return contactRelation;
    }

    public void setContactRelation(String contactRelation) {
        this.contactRelation = contactRelation;
    }

    public String getCallCountWorkTime3month() {
        return callCountWorkTime3month;
    }

    public void setCallCountWorkTime3month(String callCountWorkTime3month) {
        this.callCountWorkTime3month = callCountWorkTime3month;
    }

    public String getCallCountWorkday3month() {
        return callCountWorkday3month;
    }

    public void setCallCountWorkday3month(String callCountWorkday3month) {
        this.callCountWorkday3month = callCountWorkday3month;
    }

    public String getCallCount6month() {
        return callCount6month;
    }

    public void setCallCount6month(String callCount6month) {
        this.callCount6month = callCount6month;
    }

    public String getCallCountOffworkTime3month() {
        return callCountOffworkTime3month;
    }

    public void setCallCountOffworkTime3month(String callCountOffworkTime3month) {
        this.callCountOffworkTime3month = callCountOffworkTime3month;
    }

    public String getCallCountOffworkTime6month() {
        return callCountOffworkTime6month;
    }

    public void setCallCountOffworkTime6month(String callCountOffworkTime6month) {
        this.callCountOffworkTime6month = callCountOffworkTime6month;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactArea() {
        return contactArea;
    }

    public void setContactArea(String contactArea) {
        this.contactArea = contactArea;
    }

    public String getCallCount1week() {
        return callCount1week;
    }

    public void setCallCount1week(String callCount1week) {
        this.callCount1week = callCount1week;
    }

    public String getContactSeqNo() {
        return contactSeqNo;
    }

    public void setContactSeqNo(String contactSeqNo) {
        this.contactSeqNo = contactSeqNo;
    }

    public String getCallCount1month() {
        return callCount1month;
    }

    public void setCallCount1month(String callCount1month) {
        this.callCount1month = callCount1month;
    }

    public String getCallTimePassive3month() {
        return callTimePassive3month;
    }

    public void setCallTimePassive3month(String callTimePassive3month) {
        this.callTimePassive3month = callTimePassive3month;
    }

    public String getCallCountWorkday6month() {
        return callCountWorkday6month;
    }

    public void setCallCountWorkday6month(String callCountWorkday6month) {
        this.callCountWorkday6month = callCountWorkday6month;
    }

    public String getCallTimeOffworkTime6month() {
        return callTimeOffworkTime6month;
    }

    public void setCallTimeOffworkTime6month(String callTimeOffworkTime6month) {
        this.callTimeOffworkTime6month = callTimeOffworkTime6month;
    }

    public String getCallTimeOffworkTime3month() {
        return callTimeOffworkTime3month;
    }

    public void setCallTimeOffworkTime3month(String callTimeOffworkTime3month) {
        this.callTimeOffworkTime3month = callTimeOffworkTime3month;
    }

    public String getCallTimeWorkTime6month() {
        return callTimeWorkTime6month;
    }

    public void setCallTimeWorkTime6month(String callTimeWorkTime6month) {
        this.callTimeWorkTime6month = callTimeWorkTime6month;
    }

    public String getCallTimeWorkTime3month() {
        return callTimeWorkTime3month;
    }

    public void setCallTimeWorkTime3month(String callTimeWorkTime3month) {
        this.callTimeWorkTime3month = callTimeWorkTime3month;
    }

    public String getCallTimeLateNight6month() {
        return callTimeLateNight6month;
    }

    public void setCallTimeLateNight6month(String callTimeLateNight6month) {
        this.callTimeLateNight6month = callTimeLateNight6month;
    }

    public String getCallTimeLateNight3month() {
        return callTimeLateNight3month;
    }

    public void setCallTimeLateNight3month(String callTimeLateNight3month) {
        this.callTimeLateNight3month = callTimeLateNight3month;
    }

    public String getMsgCount1month() {
        return msgCount1month;
    }

    public void setMsgCount1month(String msgCount1month) {
        this.msgCount1month = msgCount1month;
    }

    public String getIsWholeDayCall6month() {
        return isWholeDayCall6month;
    }

    public void setIsWholeDayCall6month(String isWholeDayCall6month) {
        this.isWholeDayCall6month = isWholeDayCall6month;
    }

    public String getIsWholeDayCall3month() {
        return isWholeDayCall3month;
    }

    public void setIsWholeDayCall3month(String isWholeDayCall3month) {
        this.isWholeDayCall3month = isWholeDayCall3month;
    }

    public String getFirstTimeCall6month() {
        return firstTimeCall6month;
    }

    public void setFirstTimeCall6month(String firstTimeCall6month) {
        this.firstTimeCall6month = firstTimeCall6month;
    }

    public String getLastTimeCall6month() {
        return lastTimeCall6month;
    }

    public void setLastTimeCall6month(String lastTimeCall6month) {
        this.lastTimeCall6month = lastTimeCall6month;
    }

    public String getMaxGapDayCall6month() {
        return maxGapDayCall6month;
    }

    public void setMaxGapDayCall6month(String maxGapDayCall6month) {
        this.maxGapDayCall6month = maxGapDayCall6month;
    }

    public String getAverageGapDayCall6month() {
        return averageGapDayCall6month;
    }

    public void setAverageGapDayCall6month(String averageGapDayCall6month) {
        this.averageGapDayCall6month = averageGapDayCall6month;
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