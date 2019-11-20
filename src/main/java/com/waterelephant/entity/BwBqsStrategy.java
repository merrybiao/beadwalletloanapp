package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_bqs_strategy")
public class BwBqsStrategy implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String riskType;// 策略风险类型
    private Date createTime;
    private String strategyMode;// 策略匹配模式
    private String reviewValue;// 权重区间下限系数（只有权重策略模式下有值）
    private String tips;// 策略击中话术提示
    private String strategyScore;// 策略风险系数，只有权重策略模式下有效
    private Long borrowerId;
    private Long decisionId;
    private String strategyId;// 策略ID
    private String strategyDecision;// 策略决策结果
    private String strategyName;// 策略名称
    private String rejectValue;// 权重区间上限系数（只有权重策略模式下有值）

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRiskType() {
        return this.riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStrategyMode() {
        return this.strategyMode;
    }

    public void setStrategyMode(String strategyMode) {
        this.strategyMode = strategyMode;
    }

    public String getReviewValue() {
        return this.reviewValue;
    }

    public void setReviewValue(String reviewValue) {
        this.reviewValue = reviewValue;
    }

    public String getTips() {
        return this.tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getStrategyScore() {
        return this.strategyScore;
    }

    public void setStrategyScore(String strategyScore) {
        this.strategyScore = strategyScore;
    }

    public Long getBorrowerId() {
        return this.borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Long getDecisionId() {
        return this.decisionId;
    }

    public void setDecisionId(Long decisionId) {
        this.decisionId = decisionId;
    }

    public String getStrategyId() {
        return this.strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public String getStrategyDecision() {
        return this.strategyDecision;
    }

    public void setStrategyDecision(String strategyDecision) {
        this.strategyDecision = strategyDecision;
    }

    public String getStrategyName() {
        return this.strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public String getRejectValue() {
        return this.rejectValue;
    }

    public void setRejectValue(String rejectValue) {
        this.rejectValue = rejectValue;
    }

}
