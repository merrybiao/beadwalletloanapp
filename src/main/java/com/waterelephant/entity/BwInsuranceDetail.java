package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_insurance_detail")
public class BwInsuranceDetail implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 主键
    private Date endDate;// 终保日期
    private String amount;// 保单金额
    private String policyNo;// 保单号
    private Date createTime;// 添加日期
    private String typeId;// 状态ID 0为首次放款 1,2,3分别为分期还款期数
    private Long isSuccess;// 是否成功标记 0为成功 1为失败
    private String orderCode;// 订单编号（创建订单接口返回值）
    private Date orderDate;// 保单日期
    private Date updateTime;// 更新时间
    private String premium;// 保险费用
    private String descMsg;// 返回结果描述信息
    private String epolicyUrl;// 电子保单下载地址
    private Long orderId;// 工单ID
    private String orderExt;// 订单附加信息
    private Date startDate;// 起保日期

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPolicyNo() {
        return this.policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTypeId() {
        return this.typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Long getIsSuccess() {
        return this.isSuccess;
    }

    public void setIsSuccess(Long isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getOrderCode() {
        return this.orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPremium() {
        return this.premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getDescMsg() {
        return this.descMsg;
    }

    public void setDescMsg(String descMsg) {
        this.descMsg = descMsg;
    }

    public String getEpolicyUrl() {
        return this.epolicyUrl;
    }

    public void setEpolicyUrl(String epolicyUrl) {
        this.epolicyUrl = epolicyUrl;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderExt() {
        return this.orderExt;
    }

    public void setOrderExt(String orderExt) {
        this.orderExt = orderExt;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

}
