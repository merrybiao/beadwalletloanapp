package com.waterelephant.baiqishi.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/23 12:00
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class StrategyJSON {
    private String strategyName; //策略名称
    private String strategyId; //策略ID
    private String strategyDecision; //	策略决策结果
    private String strategyMode; //策略匹配模式
    private String strategyScore; //策略风险系数，只有权重策略模式下有效
    private String rejectValue; //权重区间上限系数（只有权重策略模式下有值）
    private String reviewValue; //权重区间下限系数（只有权重策略模式下有值）
    private String riskType; //策略风险类型
    private String tips; //策略击中话术提示
    private List<HitRuleJSON> hitRules; //规则内容明细，参考rule字段说明

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    public String getStrategyDecision() {
        return strategyDecision;
    }

    public void setStrategyDecision(String strategyDecision) {
        this.strategyDecision = strategyDecision;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public String getStrategyMode() {
        return strategyMode;
    }

    public void setStrategyMode(String strategyMode) {
        this.strategyMode = strategyMode;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public String getStrategyScore() {
        return strategyScore;
    }

    public void setStrategyScore(String strategyScore) {
        this.strategyScore = strategyScore;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public List<HitRuleJSON> getHitRules() {
        return hitRules;
    }

    public void setHitRules(List<HitRuleJSON> hitRules) {
        this.hitRules = hitRules;
    }

    public String getRejectValue() {
        return rejectValue;
    }

    public void setRejectValue(String rejectValue) {
        this.rejectValue = rejectValue;
    }

    public String getReviewValue() {
        return reviewValue;
    }

    public void setReviewValue(String reviewValue) {
        this.reviewValue = reviewValue;
    }
}
