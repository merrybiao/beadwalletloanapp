package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 还款计划
 *
 * @Description:TODO
 * @author:yanfuxing
 * @time:2016年12月14日 下午1:48:50
 */
@Table(name = "bw_overdue_record")
public class BwOverdueRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 还款计划id
    private Long repayId;
    // 逾期天数
    private Integer overdueDay;
    // 逾期本金
    private Double overdueCorpus;
    // 逾期利息
    private Double overdueAccrualMoney;
    // 还款状态
    private Integer overdueStatus;

    private Long orderId;

    private Date createTime;

    private Date updateTime;
    // 垫付金额
    private Double advance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRepayId() {
        return repayId;
    }

    public void setRepayId(Long repayId) {
        this.repayId = repayId;
    }

    public Integer getOverdueDay() {
        return overdueDay;
    }

    public void setOverdueDay(Integer overdueDay) {
        this.overdueDay = overdueDay;
    }

    public Double getOverdueCorpus() {
        return overdueCorpus;
    }

    public void setOverdueCorpus(Double overdueCorpus) {
        this.overdueCorpus = overdueCorpus;
    }

    public Double getOverdueAccrualMoney() {
        return overdueAccrualMoney;
    }

    public void setOverdueAccrualMoney(Double overdueAccrualMoney) {
        this.overdueAccrualMoney = overdueAccrualMoney;
    }

    public Integer getOverdueStatus() {
        return overdueStatus;
    }

    public void setOverdueStatus(Integer overdueStatus) {
        this.overdueStatus = overdueStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public Double getAdvance() {
        return advance;
    }

    public void setAdvance(Double advance) {
        this.advance = advance;
    }

}
