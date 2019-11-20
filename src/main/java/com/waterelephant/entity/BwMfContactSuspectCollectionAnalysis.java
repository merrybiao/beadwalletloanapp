package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_contact_suspect_collection_analysis
 * @author 
 */
@Table(name = "bw_mf_contact_suspect_collection_analysis")
public class BwMfContactSuspectCollectionAnalysis implements Serializable {
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
     * 风险分类
     */
    private String riskType;

    /**
     * 近1月通话号码数量
     */
    @Column(name="contact_count_1month")
    private String contactCount1month;

    /**
     * 近1月主叫号码数量
     */
    @Column(name="contact_count_active_1month")
    private String contactCountActive1month;

    /**
     * 近1月被叫号码数量
     */
    @Column(name="contact_count_passive_1month")
    private String contactCountPassive1month;

    /**
     * 近3月通话号码数量
     */
    @Column(name="contact_count_3month")
    private String contactCount3month;

    /**
     * 近3月主叫号码数量
     */
    @Column(name="contact_count_active_3month")
    private String contactCountActive3month;

    /**
     * 近3月被叫号码数量
     */
    @Column(name="contact_count_passive_3month")
    private String contactCountPassive3month;

    /**
     * 近6月通话号码数量
     */
    @Column(name="contact_count_6month")
    private String contactCount6month;

    /**
     * 近6月主叫号码数量
     */
    @Column(name="contact_count_active_6month")
    private String contactCountActive6month;

    /**
     * 近6月被叫号码数量
     */
    @Column(name="contact_count_passive_6month")
    private String contactCountPassive6month;

    /**
     * 近1月通话次数
     */
    @Column(name="call_count_1month")
    private String callCount1month;

    /**
     * 近1月主叫通话次数
     */
    @Column(name="call_count_active_1month")
    private String callCountActive1month;

    /**
     * 近1月被叫通话次数
     */
    @Column(name="call_count_passive_1month")
    private String callCountPassive1month;

    /**
     * 近3月通话次数
     */
    @Column(name="call_count_3month")
    private String callCount3month;

    /**
     * 近3月主叫通话次数
     */
    @Column(name="call_count_active_3month")
    private String callCountActive3month;

    /**
     * 近3月被叫通话次数
     */
    @Column(name="call_count_passive_3month")
    private String callCountPassive3month;

    /**
     * 近6月通话次数
     */
    @Column(name="call_count_6month")
    private String callCount6month;

    /**
     * 近6月主叫通话次数
     */
    @Column(name="call_count_active_6month")
    private String callCountActive6month;

    /**
     * 近6月被叫通话次数
     */
    @Column(name="call_count_passive_6month")
    private String callCountPassive6month;

    /**
     * 近1月通话时长
     */
    @Column(name="call_time_1month")
    private String callTime1month;

    /**
     * 近1月主叫通话时长
     */
    @Column(name="call_time_active_1month")
    private String callTimeActive1month;

    /**
     * 近1月被叫通话时长
     */
    @Column(name="call_time_passive_1month")
    private String callTimePassive1month;

    /**
     * 近3月通话时长
     */
    @Column(name="call_time_3month")
    private String callTime3month;

    /**
     * 近3月主叫通话时长
     */
    @Column(name="call_time_active_3month")
    private String callTimeActive3month;

    /**
     * 近3月被叫通话时长
     */
    @Column(name="call_time_passive_3month")
    private String callTimePassive3month;

    /**
     * 近6月通话时长
     */
    @Column(name="call_time_6month")
    private String callTime6month;

    /**
     * 近6月主叫通话时长
     */
    @Column(name="call_time_active_6month")
    private String callTimeActive6month;

    /**
     * 近6月被叫通话时长
     */
    @Column(name="call_time_passive_6month")
    private String callTimePassive6month;

    /**
     * 近1月短信次数
     */
    @Column(name="msg_count_1month")
    private String msgCount1month;

    /**
     * 近3月短信次数
     */
    @Column(name="msg_count_3month")
    private String msgCount3month;

    /**
     * 近6月短信次数
     */
    @Column(name="msg_count_6month")
    private String msgCount6month;

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

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    public String getContactCount1month() {
        return contactCount1month;
    }

    public void setContactCount1month(String contactCount1month) {
        this.contactCount1month = contactCount1month;
    }

    public String getContactCountActive1month() {
        return contactCountActive1month;
    }

    public void setContactCountActive1month(String contactCountActive1month) {
        this.contactCountActive1month = contactCountActive1month;
    }

    public String getContactCountPassive1month() {
        return contactCountPassive1month;
    }

    public void setContactCountPassive1month(String contactCountPassive1month) {
        this.contactCountPassive1month = contactCountPassive1month;
    }

    public String getContactCount3month() {
        return contactCount3month;
    }

    public void setContactCount3month(String contactCount3month) {
        this.contactCount3month = contactCount3month;
    }

    public String getContactCountActive3month() {
        return contactCountActive3month;
    }

    public void setContactCountActive3month(String contactCountActive3month) {
        this.contactCountActive3month = contactCountActive3month;
    }

    public String getContactCountPassive3month() {
        return contactCountPassive3month;
    }

    public void setContactCountPassive3month(String contactCountPassive3month) {
        this.contactCountPassive3month = contactCountPassive3month;
    }

    public String getContactCount6month() {
        return contactCount6month;
    }

    public void setContactCount6month(String contactCount6month) {
        this.contactCount6month = contactCount6month;
    }

    public String getContactCountActive6month() {
        return contactCountActive6month;
    }

    public void setContactCountActive6month(String contactCountActive6month) {
        this.contactCountActive6month = contactCountActive6month;
    }

    public String getContactCountPassive6month() {
        return contactCountPassive6month;
    }

    public void setContactCountPassive6month(String contactCountPassive6month) {
        this.contactCountPassive6month = contactCountPassive6month;
    }

    public String getCallCount1month() {
        return callCount1month;
    }

    public void setCallCount1month(String callCount1month) {
        this.callCount1month = callCount1month;
    }

    public String getCallCountActive1month() {
        return callCountActive1month;
    }

    public void setCallCountActive1month(String callCountActive1month) {
        this.callCountActive1month = callCountActive1month;
    }

    public String getCallCountPassive1month() {
        return callCountPassive1month;
    }

    public void setCallCountPassive1month(String callCountPassive1month) {
        this.callCountPassive1month = callCountPassive1month;
    }

    public String getCallCount3month() {
        return callCount3month;
    }

    public void setCallCount3month(String callCount3month) {
        this.callCount3month = callCount3month;
    }

    public String getCallCountActive3month() {
        return callCountActive3month;
    }

    public void setCallCountActive3month(String callCountActive3month) {
        this.callCountActive3month = callCountActive3month;
    }

    public String getCallCountPassive3month() {
        return callCountPassive3month;
    }

    public void setCallCountPassive3month(String callCountPassive3month) {
        this.callCountPassive3month = callCountPassive3month;
    }

    public String getCallCount6month() {
        return callCount6month;
    }

    public void setCallCount6month(String callCount6month) {
        this.callCount6month = callCount6month;
    }

    public String getCallCountActive6month() {
        return callCountActive6month;
    }

    public void setCallCountActive6month(String callCountActive6month) {
        this.callCountActive6month = callCountActive6month;
    }

    public String getCallCountPassive6month() {
        return callCountPassive6month;
    }

    public void setCallCountPassive6month(String callCountPassive6month) {
        this.callCountPassive6month = callCountPassive6month;
    }

    public String getCallTime1month() {
        return callTime1month;
    }

    public void setCallTime1month(String callTime1month) {
        this.callTime1month = callTime1month;
    }

    public String getCallTimeActive1month() {
        return callTimeActive1month;
    }

    public void setCallTimeActive1month(String callTimeActive1month) {
        this.callTimeActive1month = callTimeActive1month;
    }

    public String getCallTimePassive1month() {
        return callTimePassive1month;
    }

    public void setCallTimePassive1month(String callTimePassive1month) {
        this.callTimePassive1month = callTimePassive1month;
    }

    public String getCallTime3month() {
        return callTime3month;
    }

    public void setCallTime3month(String callTime3month) {
        this.callTime3month = callTime3month;
    }

    public String getCallTimeActive3month() {
        return callTimeActive3month;
    }

    public void setCallTimeActive3month(String callTimeActive3month) {
        this.callTimeActive3month = callTimeActive3month;
    }

    public String getCallTimePassive3month() {
        return callTimePassive3month;
    }

    public void setCallTimePassive3month(String callTimePassive3month) {
        this.callTimePassive3month = callTimePassive3month;
    }

    public String getCallTime6month() {
        return callTime6month;
    }

    public void setCallTime6month(String callTime6month) {
        this.callTime6month = callTime6month;
    }

    public String getCallTimeActive6month() {
        return callTimeActive6month;
    }

    public void setCallTimeActive6month(String callTimeActive6month) {
        this.callTimeActive6month = callTimeActive6month;
    }

    public String getCallTimePassive6month() {
        return callTimePassive6month;
    }

    public void setCallTimePassive6month(String callTimePassive6month) {
        this.callTimePassive6month = callTimePassive6month;
    }

    public String getMsgCount1month() {
        return msgCount1month;
    }

    public void setMsgCount1month(String msgCount1month) {
        this.msgCount1month = msgCount1month;
    }

    public String getMsgCount3month() {
        return msgCount3month;
    }

    public void setMsgCount3month(String msgCount3month) {
        this.msgCount3month = msgCount3month;
    }

    public String getMsgCount6month() {
        return msgCount6month;
    }

    public void setMsgCount6month(String msgCount6month) {
        this.msgCount6month = msgCount6month;
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