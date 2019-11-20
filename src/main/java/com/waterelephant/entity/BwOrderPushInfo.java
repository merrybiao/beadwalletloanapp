package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 工单推送信息 20161216
 *
 * @author duxiaoyong
 *
 */
@Table(name = "bw_order_push_info")
public class BwOrderPushInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 主键ID
    private Long orderId;// 工单ID
    private String orderNo;// 工单号
    private Integer financingChannel;// 理财渠道
    private Date pushTime;// 推送时间
    private Integer pushStatus;// 状态。0：推送失败，1：推送成功，2：接收成功，3：接收失败
    private String pushRemark;// 备注
    private Date fullTime;// 满标时间
    private Date loanTime;// 放款时间
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间
    private Integer pushCount;

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getFinancingChannel() {
        return financingChannel;
    }

    public void setFinancingChannel(Integer financingChannel) {
        this.financingChannel = financingChannel;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getPushRemark() {
        return pushRemark;
    }

    public void setPushRemark(String pushRemark) {
        this.pushRemark = pushRemark;
    }

    public Date getFullTime() {
        return fullTime;
    }

    public void setFullTime(Date fullTime) {
        this.fullTime = fullTime;
    }

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
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

    public Integer getPushCount() {
        return pushCount;
    }

    public void setPushCount(Integer pushCount) {
        this.pushCount = pushCount;
    }

}
