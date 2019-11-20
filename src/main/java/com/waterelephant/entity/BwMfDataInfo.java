package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_data_info
 * @author 
 */
@Table(name = "bw_mf_data_info")
public class BwMfDataInfo implements Serializable {
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
     * 流量周期。YYYY-MM
     */
    private String dataCycle;

    /**
     * 总流量大小(KB)。整形数字
     */
    private String totalDataSize;

    /**
     * 总流量次数。整形数字
     */
    private String totalDataCount;

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

    public String getDataCycle() {
        return dataCycle;
    }

    public void setDataCycle(String dataCycle) {
        this.dataCycle = dataCycle;
    }

    public String getTotalDataSize() {
        return totalDataSize;
    }

    public void setTotalDataSize(String totalDataSize) {
        this.totalDataSize = totalDataSize;
    }

    public String getTotalDataCount() {
        return totalDataCount;
    }

    public void setTotalDataCount(String totalDataCount) {
        this.totalDataCount = totalDataCount;
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