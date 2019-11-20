package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * (code:dkdh001)
 * 月账单记录实体类
 *
 * @author lwl
 */
@Table(name = "bw_operate_bill")
public class BwOperateBill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long id; // 主键
    private Long borrowerId; // 借款人id
    private String month; // 月份
    private String callPay; // 当月话费
    private String payFee; // 实际缴纳费用
    private String packageFee; // 套餐及固定费用
    private String msgFee; // 额外套餐费-短信
    private String telFee; // 额外套餐费-通话
    private String netFee; // 额外套餐费-流量
    private String addtionalFee; // 增值业务费
    private String preferentialFee; // 优惠费
    private String generationFee; // 代收费
    private String otherFee; // 其他费用
    private String otherspaidFee; // 代付费
    private String score; // 当月积分
    private Date createTime; // 当月积分
    private Date updateTime; // 当月积分

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCallPay() {
        return callPay;
    }

    public void setCallPay(String callPay) {
        this.callPay = callPay;
    }

    public String getPayFee() {
        return payFee;
    }

    public void setPayFee(String payFee) {
        this.payFee = payFee;
    }

    public String getPackageFee() {
        return packageFee;
    }

    public void setPackageFee(String packageFee) {
        this.packageFee = packageFee;
    }

    public String getMsgFee() {
        return msgFee;
    }

    public void setMsgFee(String msgFee) {
        this.msgFee = msgFee;
    }

    public String getTelFee() {
        return telFee;
    }

    public void setTelFee(String telFee) {
        this.telFee = telFee;
    }

    public String getNetFee() {
        return netFee;
    }

    public void setNetFee(String netFee) {
        this.netFee = netFee;
    }

    public String getAddtionalFee() {
        return addtionalFee;
    }

    public void setAddtionalFee(String addtionalFee) {
        this.addtionalFee = addtionalFee;
    }

    public String getPreferentialFee() {
        return preferentialFee;
    }

    public void setPreferentialFee(String preferentialFee) {
        this.preferentialFee = preferentialFee;
    }

    public String getGenerationFee() {
        return generationFee;
    }

    public void setGenerationFee(String generationFee) {
        this.generationFee = generationFee;
    }

    public String getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(String otherFee) {
        this.otherFee = otherFee;
    }

    public String getOtherspaidFee() {
        return otherspaidFee;
    }

    public void setOtherspaidFee(String otherspaidFee) {
        this.otherspaidFee = otherspaidFee;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
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
