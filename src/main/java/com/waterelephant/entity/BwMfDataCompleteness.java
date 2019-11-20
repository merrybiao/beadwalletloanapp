package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_data_completeness
 * @author 
 */
@Table(name = "bw_mf_data_completeness")
public class BwMfDataCompleteness implements Serializable {
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
     * 近1月通话数据是否完整
     */
    @Column(name="is_call_data_complete_1month")
    private String isCallDataComplete1month;

    /**
     * 近3月通话数据是否完整
     */
    @Column(name="is_call_data_complete_3month")
    private String isCallDataComplete3month;

    /**
     * 近1月短信数据是否完整
     */
    @Column(name="is_msg_data_complete_1month")
    private String isMsgDataComplete1month;

    /**
     * 近6月短信数据是否完整
     */
    @Column(name="is_msg_data_complete_6month")
    private String isMsgDataComplete6month;

    /**
     * 近6月消费数据是否完整
     */
    @Column(name="is_consume_data_complete_6month")
    private String isConsumeDataComplete6month;

    /**
     * 近3月消费数据是否完整
     */
    @Column(name="is_consume_data_complete_3month")
    private String isConsumeDataComplete3month;

    /**
     * 近1月消费数据是否完整
     */
    @Column(name="is_consume_data_complete_1month")
    private String isConsumeDataComplete1month;

    /**
     * 近6月通话数据是否完整
     */
    @Column(name="is_call_data_complete_6month")
    private String isCallDataComplete6month;

    /**
     * 近3月短信数据是否完整
     */
    @Column(name="is_msg_data_complete_3month")
    private String isMsgDataComplete3month;

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

    public String getIsCallDataComplete1month() {
        return isCallDataComplete1month;
    }

    public void setIsCallDataComplete1month(String isCallDataComplete1month) {
        this.isCallDataComplete1month = isCallDataComplete1month;
    }

    public String getIsCallDataComplete3month() {
        return isCallDataComplete3month;
    }

    public void setIsCallDataComplete3month(String isCallDataComplete3month) {
        this.isCallDataComplete3month = isCallDataComplete3month;
    }

    public String getIsMsgDataComplete1month() {
        return isMsgDataComplete1month;
    }

    public void setIsMsgDataComplete1month(String isMsgDataComplete1month) {
        this.isMsgDataComplete1month = isMsgDataComplete1month;
    }

    public String getIsMsgDataComplete6month() {
        return isMsgDataComplete6month;
    }

    public void setIsMsgDataComplete6month(String isMsgDataComplete6month) {
        this.isMsgDataComplete6month = isMsgDataComplete6month;
    }

    public String getIsConsumeDataComplete6month() {
        return isConsumeDataComplete6month;
    }

    public void setIsConsumeDataComplete6month(String isConsumeDataComplete6month) {
        this.isConsumeDataComplete6month = isConsumeDataComplete6month;
    }

    public String getIsConsumeDataComplete3month() {
        return isConsumeDataComplete3month;
    }

    public void setIsConsumeDataComplete3month(String isConsumeDataComplete3month) {
        this.isConsumeDataComplete3month = isConsumeDataComplete3month;
    }

    public String getIsConsumeDataComplete1month() {
        return isConsumeDataComplete1month;
    }

    public void setIsConsumeDataComplete1month(String isConsumeDataComplete1month) {
        this.isConsumeDataComplete1month = isConsumeDataComplete1month;
    }

    public String getIsCallDataComplete6month() {
        return isCallDataComplete6month;
    }

    public void setIsCallDataComplete6month(String isCallDataComplete6month) {
        this.isCallDataComplete6month = isCallDataComplete6month;
    }

    public String getIsMsgDataComplete3month() {
        return isMsgDataComplete3month;
    }

    public void setIsMsgDataComplete3month(String isMsgDataComplete3month) {
        this.isMsgDataComplete3month = isMsgDataComplete3month;
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