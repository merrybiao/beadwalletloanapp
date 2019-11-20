package com.waterelephant.baiqishi.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/23 11:58
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class DecisionJSON {
    private String finalDecision;//决策结果码，Accept  Reject    Review
    private String finalScore; //最终风险系数，只有权重策略模式下有效
    private String resultCode; //结果码，参见ressultCode参考表
    private String resultDesc; //结果描述，如果结果码非成功则会返回失败明细
    private String flowNo;//本次请求的流水号，用于事后案件调查
    private List<StrategyJSON> strategySet; //策略集内容明细，参考strategySet字段说明

    public String getFinalDecision() {
        return finalDecision;
    }

    public void setFinalDecision(String finalDecision) {
        this.finalDecision = finalDecision;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public String getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(String finalScore) {
        this.finalScore = finalScore;
    }

    public List<StrategyJSON> getStrategySet() {
        return strategySet;
    }

    public void setStrategySet(List<StrategyJSON> strategySet) {
        this.strategySet = strategySet;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }
}
