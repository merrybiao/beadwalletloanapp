package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_data_info_record
 * @author 
 */
@Table(name = "bw_mf_data_info_record")
public class BwMfDataInfoRecord implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 流量详单id
     */
    private Long dataInfoId;

    /**
     * 工单id
     */
    private Long orderId;

    /**
     * 认证任务id
     */
    private String taskId;

    /**
     * 起始时间。YYYY-MM-DD HH:mm:SS或未知
     */
    private String dataStartTime;

    /**
     * 使用时长。精确到秒
     */
    private String dataTime;

    /**
     * 流量大小(KB)。整形数字
     */
    private String dataSize;

    /**
     * 使用地点。如：浙江省.杭州市、海外.美国、未
     */
    private String dataAddress;

    /**
     * 网络类型。如：2G、3G、4G、未知
     */
    private String dataType;

    /**
     * 业务类型
     */
    private String dataBizName;

    /**
     * 费用小计。整形数字精确到分
     */
    private String dataCost;

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

    public Long getDataInfoId() {
        return dataInfoId;
    }

    public void setDataInfoId(Long dataInfoId) {
        this.dataInfoId = dataInfoId;
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

    public String getDataStartTime() {
        return dataStartTime;
    }

    public void setDataStartTime(String dataStartTime) {
        this.dataStartTime = dataStartTime;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getDataSize() {
        return dataSize;
    }

    public void setDataSize(String dataSize) {
        this.dataSize = dataSize;
    }

    public String getDataAddress() {
        return dataAddress;
    }

    public void setDataAddress(String dataAddress) {
        this.dataAddress = dataAddress;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataBizName() {
        return dataBizName;
    }

    public void setDataBizName(String dataBizName) {
        this.dataBizName = dataBizName;
    }

    public String getDataCost() {
        return dataCost;
    }

    public void setDataCost(String dataCost) {
        this.dataCost = dataCost;
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