package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_call_info
 * @author 
 */
@Table(name = "bw_mf_call_info")
public class BwMfCallInfo implements Serializable {
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
     * 通话周期。YYYY-MM
     */
    private String callCycle;

    /**
     * 总通话时长。时长精确到秒
     */
    private String totalCallTime;

    /**
     * 总通话次数。整形数字
     */
    private String totalCallCount;

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

    public String getCallCycle() {
        return callCycle;
    }

    public void setCallCycle(String callCycle) {
        this.callCycle = callCycle;
    }

    public String getTotalCallTime() {
        return totalCallTime;
    }

    public void setTotalCallTime(String totalCallTime) {
        this.totalCallTime = totalCallTime;
    }

    public String getTotalCallCount() {
        return totalCallCount;
    }

    public void setTotalCallCount(String totalCallCount) {
        this.totalCallCount = totalCallCount;
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