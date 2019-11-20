package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_carrier_consumption_stats
 * @author 
 */
@Table(name = "bw_mf_carrier_consumption_stats")
public class BwMfCarrierConsumptionStats implements Serializable {
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
     * 近1月消费金额
     */
    @Column(name="consume_amount_1month")
    private String consumeAmount1month;

    /**
     * 近3月消费金额
     */
    @Column(name="consume_amount_3month")
    private String consumeAmount3month;

    /**
     * 近6月消费金额
     */
    @Column(name="consume_amount_6month")
    private String consumeAmount6month;

    /**
     * 近1月充值次数
     */
    @Column(name="recharge_count_1month")
    private String rechargeCount1month;

    /**
     * 近3月充值次数
     */
    @Column(name="recharge_count_3month")
    private String rechargeCount3month;

    /**
     * 近6月充值次数
     */
    @Column(name="recharge_count_6month")
    private String rechargeCount6month;

    /**
     * 近1月充值金额
     */
    @Column(name="recharge_amount_1month")
    private String rechargeAmount1month;

    /**
     * 近3月充值金额
     */
    @Column(name="recharge_amount_3month")
    private String rechargeAmount3month;

    /**
     * 近6月充值金额
     */
    @Column(name="recharge_amount_6month")
    private String rechargeAmount6month;

    /**
     * 近6月平均消费金额
     */
    @Column(name="average_consume_amount_6month")
    private String averageConsumeAmount6month;

    /**
     * 近6月账单数量
     */
    @Column(name="bill_count_6month")
    private String billCount6month;

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

    public String getConsumeAmount1month() {
        return consumeAmount1month;
    }

    public void setConsumeAmount1month(String consumeAmount1month) {
        this.consumeAmount1month = consumeAmount1month;
    }

    public String getConsumeAmount3month() {
        return consumeAmount3month;
    }

    public void setConsumeAmount3month(String consumeAmount3month) {
        this.consumeAmount3month = consumeAmount3month;
    }

    public String getConsumeAmount6month() {
        return consumeAmount6month;
    }

    public void setConsumeAmount6month(String consumeAmount6month) {
        this.consumeAmount6month = consumeAmount6month;
    }

    public String getRechargeCount1month() {
        return rechargeCount1month;
    }

    public void setRechargeCount1month(String rechargeCount1month) {
        this.rechargeCount1month = rechargeCount1month;
    }

    public String getRechargeCount3month() {
        return rechargeCount3month;
    }

    public void setRechargeCount3month(String rechargeCount3month) {
        this.rechargeCount3month = rechargeCount3month;
    }

    public String getRechargeCount6month() {
        return rechargeCount6month;
    }

    public void setRechargeCount6month(String rechargeCount6month) {
        this.rechargeCount6month = rechargeCount6month;
    }

    public String getRechargeAmount1month() {
        return rechargeAmount1month;
    }

    public void setRechargeAmount1month(String rechargeAmount1month) {
        this.rechargeAmount1month = rechargeAmount1month;
    }

    public String getRechargeAmount3month() {
        return rechargeAmount3month;
    }

    public void setRechargeAmount3month(String rechargeAmount3month) {
        this.rechargeAmount3month = rechargeAmount3month;
    }

    public String getRechargeAmount6month() {
        return rechargeAmount6month;
    }

    public void setRechargeAmount6month(String rechargeAmount6month) {
        this.rechargeAmount6month = rechargeAmount6month;
    }

    public String getAverageConsumeAmount6month() {
        return averageConsumeAmount6month;
    }

    public void setAverageConsumeAmount6month(String averageConsumeAmount6month) {
        this.averageConsumeAmount6month = averageConsumeAmount6month;
    }

    public String getBillCount6month() {
        return billCount6month;
    }

    public void setBillCount6month(String billCount6month) {
        this.billCount6month = billCount6month;
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