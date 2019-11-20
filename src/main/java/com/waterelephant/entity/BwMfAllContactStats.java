package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_all_contact_stats
 * @author 
 */
@Table(name = "bw_mf_all_contact_stats")
public class BwMfAllContactStats implements Serializable {
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
     * 近6月通话时长>=10分钟的通话次数
     */
    @Column(name="call_count_call_time_over10min_6month")
    private String callCountCallTimeOver10min6month;

    /**
     * 近3月主叫号码数量
     */
    @Column(name="contact_count_active_3month")
    private String contactCountActive3month;

    /**
     * 近6月主叫通话次数
     */
    @Column(name="call_count_active_6month")
    private String callCountActive6month;

    /**
     * 近1月通话时长
     */
    @Column(name="call_time_1month")
    private String callTime1month;

    /**
     * 近6月手机主叫通话时长
     */
    @Column(name="call_time_active_mobile_6month")
    private String callTimeActiveMobile6month;

    /**
     * 近6月通话时长
     */
    @Column(name="call_time_6month")
    private String callTime6month;

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
     * 近6月被叫号码数量
     */
    @Column(name="contact_count_passive_6month")
    private String contactCountPassive6month;

    /**
     * 近6月通话时长5-10分钟的通话次数
     */
    @Column(name="call_count_call_time_5min10min_6month")
    private String callCountCallTime5min10min6month;

    /**
     * 近6月手机被叫通话时长
     */
    @Column(name="call_time_passive_mobile_6month")
    private String callTimePassiveMobile6month;

    /**
     * 近6月深夜通话时长
     */
    @Column(name="call_time_late_night_6month")
    private String callTimeLateNight6month;

    /**
     * 近3月通话次数
     */
    @Column(name="call_count_3month")
    private String callCount3month;

    /**
     * 近1月通话号码数量
     */
    @Column(name="contact_count_1month")
    private String contactCount1month;

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
     * 近6月非工作时间通话时长
     */
    @Column(name="call_time_offwork_time_6month")
    private String callTimeOffworkTime6month;

    /**
     * 近6月非工作时间通话次数
     */
    @Column(name="call_count_offwork_time_6month")
    private String callCountOffworkTime6month;

    /**
     * 近3月非工作时间通话时长
     */
    @Column(name="call_time_offwork_time_3month")
    private String callTimeOffworkTime3month;

    /**
     * 近3月互通号码数量
     */
    @Column(name="contact_count_mutual_3month")
    private String contactCountMutual3month;

    /**
     * 近1月通话次数
     */
    @Column(name="call_count_1month")
    private String callCount1month;

    /**
     * 近6月固话通话号码数量
     */
    @Column(name="contact_count_telephone_6month")
    private String contactCountTelephone6month;

    /**
     * 近3月被叫通话时长
     */
    @Column(name="call_time_passive_3month")
    private String callTimePassive3month;

    /**
     * 近6月通话时长<1分钟的通话次数
     */
    @Column(name="call_count_call_time_less1min_6month")
    private String callCountCallTimeLess1min6month;

    /**
     * 近3月通话次数>=10的号码数量
     */
    @Column(name="contact_count_call_count_over10_3month")
    private String contactCountCallCountOver103month;

    /**
     * 近6月工作日通话次数
     */
    @Column(name="call_count_workday_6month")
    private String callCountWorkday6month;

    /**
     * 近6月通话次数>=10的号码数量
     */
    @Column(name="contact_count_call_count_over10_6month")
    private String contactCountCallCountOver106month;

    /**
     * 近1月短信次数
     */
    @Column(name="msg_count_1month")
    private String msgCount1month;

    /**
     * 近6月互通号码数量
     */
    @Column(name="contact_count_mutual_6month")
    private String contactCountMutual6month;

    /**
     * 近6月通话时长1-5分钟的通话次数
     */
    @Column(name="call_count_call_time_1min5min_6month")
    private String callCountCallTime1min5min6month;

    /**
     * 近6月节假日通话次数
     */
    @Column(name="call_count_holiday_6month")
    private String callCountHoliday6month;

    /**
     * 近6月主叫号码数量
     */
    @Column(name="contact_count_active_6month")
    private String contactCountActive6month;

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
     * 近3月深夜通话次数
     */
    @Column(name="call_count_late_night_3month")
    private String callCountLateNight3month;

    /**
     * 近6月工作时间通话时长
     */
    @Column(name="call_time_work_time_6month")
    private String callTimeWorkTime6month;

    /**
     * 近6月工作时间通话次数
     */
    @Column(name="call_count_work_time_6month")
    private String callCountWorkTime6month;

    /**
     * 近3月深夜通话时长
     */
    @Column(name="call_time_late_night_3month")
    private String callTimeLateNight3month;

    /**
     * 近3月主叫通话时长
     */
    @Column(name="call_time_active_3month")
    private String callTimeActive3month;

    /**
     * 近6月非手机固话通话号码数量
     */
    @Column(name="contact_count_not_mobile_telephone_6month")
    private String contactCountNotMobileTelephone6month;

    /**
     * 近3月被叫号码数量
     */
    @Column(name="contact_count_passive_3month")
    private String contactCountPassive3month;

    /**
     * 近3月被叫通话次数
     */
    @Column(name="call_count_passive_3month")
    private String callCountPassive3month;

    /**
     * 近6月深夜主叫通话次数
     */
    @Column(name="call_count_active_late_night_6month")
    private String callCountActiveLateNight6month;

    /**
     * 近3月工作时间通话时长
     */
    @Column(name="call_time_work_time_3month")
    private String callTimeWorkTime3month;

    /**
     * 近6月手机通话号码数量
     */
    @Column(name="contact_count_mobile_6month")
    private String contactCountMobile6month;

    /**
     * 近6月深夜被叫通话次数
     */
    @Column(name="call_count_passive_late_night_6month")
    private String callCountPassiveLateNight6month;

    /**
     * 近3月通话号码数量
     */
    @Column(name ="contact_count_3month")
    private String contactCount3month;

    /**
     * 近6月通话号码数量
     */
    @Column(name ="contact_count_6month")
    private String contactCount6month;

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

    public String getCallCountCallTimeOver10min6month() {
        return callCountCallTimeOver10min6month;
    }

    public void setCallCountCallTimeOver10min6month(String callCountCallTimeOver10min6month) {
        this.callCountCallTimeOver10min6month = callCountCallTimeOver10min6month;
    }

    public String getContactCountActive3month() {
        return contactCountActive3month;
    }

    public void setContactCountActive3month(String contactCountActive3month) {
        this.contactCountActive3month = contactCountActive3month;
    }

    public String getCallCountActive6month() {
        return callCountActive6month;
    }

    public void setCallCountActive6month(String callCountActive6month) {
        this.callCountActive6month = callCountActive6month;
    }

    public String getCallTime1month() {
        return callTime1month;
    }

    public void setCallTime1month(String callTime1month) {
        this.callTime1month = callTime1month;
    }

    public String getCallTimeActiveMobile6month() {
        return callTimeActiveMobile6month;
    }

    public void setCallTimeActiveMobile6month(String callTimeActiveMobile6month) {
        this.callTimeActiveMobile6month = callTimeActiveMobile6month;
    }

    public String getCallTime6month() {
        return callTime6month;
    }

    public void setCallTime6month(String callTime6month) {
        this.callTime6month = callTime6month;
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

    public String getContactCountPassive6month() {
        return contactCountPassive6month;
    }

    public void setContactCountPassive6month(String contactCountPassive6month) {
        this.contactCountPassive6month = contactCountPassive6month;
    }

    public String getCallCountCallTime5min10min6month() {
        return callCountCallTime5min10min6month;
    }

    public void setCallCountCallTime5min10min6month(String callCountCallTime5min10min6month) {
        this.callCountCallTime5min10min6month = callCountCallTime5min10min6month;
    }

    public String getCallTimePassiveMobile6month() {
        return callTimePassiveMobile6month;
    }

    public void setCallTimePassiveMobile6month(String callTimePassiveMobile6month) {
        this.callTimePassiveMobile6month = callTimePassiveMobile6month;
    }

    public String getCallTimeLateNight6month() {
        return callTimeLateNight6month;
    }

    public void setCallTimeLateNight6month(String callTimeLateNight6month) {
        this.callTimeLateNight6month = callTimeLateNight6month;
    }

    public String getCallCount3month() {
        return callCount3month;
    }

    public void setCallCount3month(String callCount3month) {
        this.callCount3month = callCount3month;
    }

    public String getContactCount1month() {
        return contactCount1month;
    }

    public void setContactCount1month(String contactCount1month) {
        this.contactCount1month = contactCount1month;
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

    public String getCallTimeOffworkTime6month() {
        return callTimeOffworkTime6month;
    }

    public void setCallTimeOffworkTime6month(String callTimeOffworkTime6month) {
        this.callTimeOffworkTime6month = callTimeOffworkTime6month;
    }

    public String getCallCountOffworkTime6month() {
        return callCountOffworkTime6month;
    }

    public void setCallCountOffworkTime6month(String callCountOffworkTime6month) {
        this.callCountOffworkTime6month = callCountOffworkTime6month;
    }

    public String getCallTimeOffworkTime3month() {
        return callTimeOffworkTime3month;
    }

    public void setCallTimeOffworkTime3month(String callTimeOffworkTime3month) {
        this.callTimeOffworkTime3month = callTimeOffworkTime3month;
    }

    public String getContactCountMutual3month() {
        return contactCountMutual3month;
    }

    public void setContactCountMutual3month(String contactCountMutual3month) {
        this.contactCountMutual3month = contactCountMutual3month;
    }

    public String getCallCount1month() {
        return callCount1month;
    }

    public void setCallCount1month(String callCount1month) {
        this.callCount1month = callCount1month;
    }

    public String getContactCountTelephone6month() {
        return contactCountTelephone6month;
    }

    public void setContactCountTelephone6month(String contactCountTelephone6month) {
        this.contactCountTelephone6month = contactCountTelephone6month;
    }

    public String getCallTimePassive3month() {
        return callTimePassive3month;
    }

    public void setCallTimePassive3month(String callTimePassive3month) {
        this.callTimePassive3month = callTimePassive3month;
    }

    public String getCallCountCallTimeLess1min6month() {
        return callCountCallTimeLess1min6month;
    }

    public void setCallCountCallTimeLess1min6month(String callCountCallTimeLess1min6month) {
        this.callCountCallTimeLess1min6month = callCountCallTimeLess1min6month;
    }

    public String getContactCountCallCountOver103month() {
        return contactCountCallCountOver103month;
    }

    public void setContactCountCallCountOver103month(String contactCountCallCountOver103month) {
        this.contactCountCallCountOver103month = contactCountCallCountOver103month;
    }

    public String getCallCountWorkday6month() {
        return callCountWorkday6month;
    }

    public void setCallCountWorkday6month(String callCountWorkday6month) {
        this.callCountWorkday6month = callCountWorkday6month;
    }

    public String getContactCountCallCountOver106month() {
        return contactCountCallCountOver106month;
    }

    public void setContactCountCallCountOver106month(String contactCountCallCountOver106month) {
        this.contactCountCallCountOver106month = contactCountCallCountOver106month;
    }

    public String getMsgCount1month() {
        return msgCount1month;
    }

    public void setMsgCount1month(String msgCount1month) {
        this.msgCount1month = msgCount1month;
    }

    public String getContactCountMutual6month() {
        return contactCountMutual6month;
    }

    public void setContactCountMutual6month(String contactCountMutual6month) {
        this.contactCountMutual6month = contactCountMutual6month;
    }

    public String getCallCountCallTime1min5min6month() {
        return callCountCallTime1min5min6month;
    }

    public void setCallCountCallTime1min5min6month(String callCountCallTime1min5min6month) {
        this.callCountCallTime1min5min6month = callCountCallTime1min5min6month;
    }

    public String getCallCountHoliday6month() {
        return callCountHoliday6month;
    }

    public void setCallCountHoliday6month(String callCountHoliday6month) {
        this.callCountHoliday6month = callCountHoliday6month;
    }

    public String getContactCountActive6month() {
        return contactCountActive6month;
    }

    public void setContactCountActive6month(String contactCountActive6month) {
        this.contactCountActive6month = contactCountActive6month;
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

    public String getCallCountLateNight3month() {
        return callCountLateNight3month;
    }

    public void setCallCountLateNight3month(String callCountLateNight3month) {
        this.callCountLateNight3month = callCountLateNight3month;
    }

    public String getCallTimeWorkTime6month() {
        return callTimeWorkTime6month;
    }

    public void setCallTimeWorkTime6month(String callTimeWorkTime6month) {
        this.callTimeWorkTime6month = callTimeWorkTime6month;
    }

    public String getCallCountWorkTime6month() {
        return callCountWorkTime6month;
    }

    public void setCallCountWorkTime6month(String callCountWorkTime6month) {
        this.callCountWorkTime6month = callCountWorkTime6month;
    }

    public String getCallTimeLateNight3month() {
        return callTimeLateNight3month;
    }

    public void setCallTimeLateNight3month(String callTimeLateNight3month) {
        this.callTimeLateNight3month = callTimeLateNight3month;
    }

    public String getCallTimeActive3month() {
        return callTimeActive3month;
    }

    public void setCallTimeActive3month(String callTimeActive3month) {
        this.callTimeActive3month = callTimeActive3month;
    }

    public String getContactCountNotMobileTelephone6month() {
        return contactCountNotMobileTelephone6month;
    }

    public void setContactCountNotMobileTelephone6month(String contactCountNotMobileTelephone6month) {
        this.contactCountNotMobileTelephone6month = contactCountNotMobileTelephone6month;
    }

    public String getContactCountPassive3month() {
        return contactCountPassive3month;
    }

    public void setContactCountPassive3month(String contactCountPassive3month) {
        this.contactCountPassive3month = contactCountPassive3month;
    }

    public String getCallCountPassive3month() {
        return callCountPassive3month;
    }

    public void setCallCountPassive3month(String callCountPassive3month) {
        this.callCountPassive3month = callCountPassive3month;
    }

    public String getCallCountActiveLateNight6month() {
        return callCountActiveLateNight6month;
    }

    public void setCallCountActiveLateNight6month(String callCountActiveLateNight6month) {
        this.callCountActiveLateNight6month = callCountActiveLateNight6month;
    }

    public String getCallTimeWorkTime3month() {
        return callTimeWorkTime3month;
    }

    public void setCallTimeWorkTime3month(String callTimeWorkTime3month) {
        this.callTimeWorkTime3month = callTimeWorkTime3month;
    }

    public String getContactCountMobile6month() {
        return contactCountMobile6month;
    }

    public void setContactCountMobile6month(String contactCountMobile6month) {
        this.contactCountMobile6month = contactCountMobile6month;
    }

    public String getCallCountPassiveLateNight6month() {
        return callCountPassiveLateNight6month;
    }

    public void setCallCountPassiveLateNight6month(String callCountPassiveLateNight6month) {
        this.callCountPassiveLateNight6month = callCountPassiveLateNight6month;
    }

    public String getContactCount3month() {
        return contactCount3month;
    }

    public void setContactCount3month(String contactCount3month) {
        this.contactCount3month = contactCount3month;
    }

    public String getContactCount6month() {
        return contactCount6month;
    }

    public void setContactCount6month(String contactCount6month) {
        this.contactCount6month = contactCount6month;
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