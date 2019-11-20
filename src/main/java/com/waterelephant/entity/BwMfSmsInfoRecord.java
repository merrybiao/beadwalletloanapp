package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_sms_info_record
 * @author 
 */
@Table(name = "bw_mf_sms_info_record")
public class BwMfSmsInfoRecord implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 短信详单id
     */
    private Long smsInfoId;

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
    private String msgStartTime;

    /**
     * 发送方式。发送、接收、未知
     */
    private String msgType;

    /**
     * 对方号码
     */
    private String msgOtherNum;

    /**
     * 信息类型。业务短信、行业短信、短信、彩信、
     */
    private String msgChannel;

    /**
     * 业务类型
     */
    private String msgBizName;

    /**
     * 短信地区。如：浙江省.杭州市、海外.美国
     */
    private String msgAddress;

    /**
     * 费用小计。整形数字精确到分
     */
    private String msgCost;

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

    public Long getSmsInfoId() {
        return smsInfoId;
    }

    public void setSmsInfoId(Long smsInfoId) {
        this.smsInfoId = smsInfoId;
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

    public String getMsgStartTime() {
        return msgStartTime;
    }

    public void setMsgStartTime(String msgStartTime) {
        this.msgStartTime = msgStartTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgOtherNum() {
        return msgOtherNum;
    }

    public void setMsgOtherNum(String msgOtherNum) {
        this.msgOtherNum = msgOtherNum;
    }

    public String getMsgChannel() {
        return msgChannel;
    }

    public void setMsgChannel(String msgChannel) {
        this.msgChannel = msgChannel;
    }

    public String getMsgBizName() {
        return msgBizName;
    }

    public void setMsgBizName(String msgBizName) {
        this.msgBizName = msgBizName;
    }

    public String getMsgAddress() {
        return msgAddress;
    }

    public void setMsgAddress(String msgAddress) {
        this.msgAddress = msgAddress;
    }

    public String getMsgCost() {
        return msgCost;
    }

    public void setMsgCost(String msgCost) {
        this.msgCost = msgCost;
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