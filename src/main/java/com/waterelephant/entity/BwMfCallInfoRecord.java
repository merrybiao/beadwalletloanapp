package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_call_info_record
 * @author 
 */
@Table(name = "bw_mf_call_info_record")
public class BwMfCallInfoRecord implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 通话详单id
     */
    private Long callInfoId;

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
    private String callStartTime;

    /**
     * 通话地点。如：浙江省.杭州市、海外.美国
     */
    private String callAddress;

    /**
     * 呼叫类型。主叫、被叫、呼转、未知
     */
    private String callTypeName;

    /**
     * 对方号码
     */
    private String callOtherNumber;

    /**
     * 通话时长。时长精确到秒
     */
    private String callTime;

    /**
     * 通话类型。本地通话、国内长途、国际长途、
     */
    private String callLandType;

    /**
     * 费用小计。整形数字精确到分
     */
    private String callCost;

    /**
     * 号码分类。如银行、律师、快递等
     */
    private String contactType;

    /**
     * 号码标签。若有多个标签，用”;”分割
     */
    private String contactName;

    /**
     * 号码归属地。如：浙江省.杭州市
     */
    private String contactArea;

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

    public Long getCallInfoId() {
        return callInfoId;
    }

    public void setCallInfoId(Long callInfoId) {
        this.callInfoId = callInfoId;
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

    public String getCallStartTime() {
        return callStartTime;
    }

    public void setCallStartTime(String callStartTime) {
        this.callStartTime = callStartTime;
    }

    public String getCallAddress() {
        return callAddress;
    }

    public void setCallAddress(String callAddress) {
        this.callAddress = callAddress;
    }

    public String getCallTypeName() {
        return callTypeName;
    }

    public void setCallTypeName(String callTypeName) {
        this.callTypeName = callTypeName;
    }

    public String getCallOtherNumber() {
        return callOtherNumber;
    }

    public void setCallOtherNumber(String callOtherNumber) {
        this.callOtherNumber = callOtherNumber;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getCallLandType() {
        return callLandType;
    }

    public void setCallLandType(String callLandType) {
        this.callLandType = callLandType;
    }

    public String getCallCost() {
        return callCost;
    }

    public void setCallCost(String callCost) {
        this.callCost = callCost;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactArea() {
        return contactArea;
    }

    public void setContactArea(String contactArea) {
        this.contactArea = contactArea;
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