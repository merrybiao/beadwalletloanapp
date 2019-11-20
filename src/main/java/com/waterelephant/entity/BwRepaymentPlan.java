package com.waterelephant.entity;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * BwRepaymentPlan entity. @author MyEclipse Persistence Tools
 */
@Table(name = "bw_repayment_plan")
public class BwRepaymentPlan implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Double repayMoney;
    private Date repayTime;
    private Integer repayType;
    private Integer repayStatus;
    private String repayDesc;
    private Integer repayIsCorpus;
    private Double repayCorpusMoney;
    private Double repayAccrualMoney;
    private Date createTime;
    private Date updateTime;
    private Integer number;
    private Double realityRepayMoney;
    private Double alreadyRepayMoney; // 已还款金额
    private Integer rolloverNumber;// 展期次数(code0090)
    /**
     * 湛江委3%金额
     */
    private Double zjw;

    @Transient
    private String repayTimeText;// 还款日期的字符表现形式

    /** default constructor */
    public BwRepaymentPlan() {}

    public BwRepaymentPlan(Long id, Long orderId, Double repayMoney, Date repayTime, Integer repayType, Integer repayStatus, String repayDesc, Integer repayIsCorpus, Double repayCorpusMoney,
            Double repayAccrualMoney, Date createTime, Integer number) {
        super();
        this.id = id;
        this.orderId = orderId;
        this.repayMoney = repayMoney;
        this.repayTime = repayTime;
        this.repayType = repayType;
        this.repayStatus = repayStatus;
        this.repayDesc = repayDesc;
        this.repayIsCorpus = repayIsCorpus;
        this.repayCorpusMoney = repayCorpusMoney;
        this.repayAccrualMoney = repayAccrualMoney;
        this.createTime = createTime;
        this.number = number;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRepayMoney() {
        return this.repayMoney;
    }

    public void setRepayMoney(Double repayMoney) {
        this.repayMoney = repayMoney;
    }

    public Date getRepayTime() {
        return this.repayTime;
    }

    public void setRepayTime(Timestamp repayTime) {
        this.repayTime = repayTime;
    }

    public Integer getRepayStatus() {
        return this.repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getRepayDesc() {
        return this.repayDesc;
    }

    public void setRepayDesc(String repayDesc) {
        this.repayDesc = repayDesc;
    }

    public Double getRepayCorpusMoney() {
        return this.repayCorpusMoney;
    }

    public void setRepayCorpusMoney(Double repayCorpusMoney) {
        this.repayCorpusMoney = repayCorpusMoney;
    }

    public Double getRepayAccrualMoney() {
        return this.repayAccrualMoney;
    }

    public void setRepayAccrualMoney(Double repayAccrualMoney) {
        this.repayAccrualMoney = repayAccrualMoney;
    }

    public Integer getRepayType() {
        return repayType;
    }

    public void setRepayType(Integer repayType) {
        this.repayType = repayType;
    }

    public Integer getRepayIsCorpus() {
        return repayIsCorpus;
    }

    public void setRepayIsCorpus(Integer repayIsCorpus) {
        this.repayIsCorpus = repayIsCorpus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setRepayTime(Date repayTime) {
        this.repayTime = repayTime;
    }

    public Double getRealityRepayMoney() {
        return realityRepayMoney;
    }

    public void setRealityRepayMoney(Double realityRepayMoney) {
        this.realityRepayMoney = realityRepayMoney;
    }

    public String getRepayTimeText() {
        return repayTimeText;
    }

    public void setRepayTimeText(String repayTimeText) {
        this.repayTimeText = repayTimeText;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return 获取 alreadyRepayMoney属性值
     */
    public Double getAlreadyRepayMoney() {
        return alreadyRepayMoney;
    }

    /**
     * @param alreadyRepayMoney 设置 alreadyRepayMoney 属性值为参数值 alreadyRepayMoney
     */
    public void setAlreadyRepayMoney(Double alreadyRepayMoney) {
        this.alreadyRepayMoney = alreadyRepayMoney;
    }

    /**
     * @return 获取 rolloverNumber属性值
     */
    public Integer getRolloverNumber() {
        return rolloverNumber;
    }

    /**
     * @param rolloverNumber 设置 rolloverNumber 属性值为参数值 rolloverNumber
     */
    public void setRolloverNumber(Integer rolloverNumber) {
        this.rolloverNumber = rolloverNumber;
    }

    public Double getZjw() {
        return zjw;
    }

    public void setZjw(Double zjw) {
        this.zjw = zjw;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BwRepaymentPlan[");
        builder.append(" 主键ID[id] = ");
        builder.append(id);
        builder.append(" 工单ID[orderId] = ");
        builder.append(orderId);
        builder.append(" 还款金额[repayMoney] = ");
        builder.append(repayMoney);
        builder.append(" 实际还款金额[realityRepayMoney] = ");
        builder.append(realityRepayMoney);
        builder.append(" 还款日期[repayTime] = ");
        builder.append(repayTime);
        builder.append(" 还款类型：1正常还款 2 逾期还款 3 提前还款[repayType] = ");
        builder.append(repayType);
        builder.append(" 还款状态：1 未还款 2 已还款 3垫付[repayStatus] = ");
        builder.append(repayStatus);
        builder.append(" 还款描述 备用字段[repayDesc] = ");
        builder.append(repayDesc);
        builder.append(" 还款金额类型，1：利息，2：本金加利息[repayIsCorpus] = ");
        builder.append(repayIsCorpus);
        builder.append(" 还款本金[repayCorpusMoney] = ");
        builder.append(repayCorpusMoney);
        builder.append(" 还款利息[repayAccrualMoney] = ");
        builder.append(repayAccrualMoney);
        builder.append(" 更新时间[updateTime] = ");
        builder.append(updateTime);
        builder.append(" [createTime] = ");
        builder.append(createTime);
        builder.append(" 第几期[number] = ");
        builder.append(number);
        builder.append(" 已还款金额[alreadyRepayMoney] = ");
        builder.append(alreadyRepayMoney);
        builder.append(" 展期次数[rolloverNumber] = ");
        builder.append(rolloverNumber);
        builder.append("]");
        return builder.toString();
    }

}
