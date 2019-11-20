package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_sms_info
 * @author 
 */
@Table(name = "bw_mf_sms_info")
public class BwMfSmsInfo implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 工单id
     */
    private Long orderId;

    private String taskId;

    /**
     * 短信周期。YYYY-MM
     */
    private String msgCycle;

    /**
     * 总短信次数。整形数字
     */
    private String totalMsgCount;

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

    public String getMsgCycle() {
        return msgCycle;
    }

    public void setMsgCycle(String msgCycle) {
        this.msgCycle = msgCycle;
    }

    public String getTotalMsgCount() {
        return totalMsgCount;
    }

    public void setTotalMsgCount(String totalMsgCount) {
        this.totalMsgCount = totalMsgCount;
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