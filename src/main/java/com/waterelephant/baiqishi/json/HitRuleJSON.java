package com.waterelephant.baiqishi.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/23 12:01
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HitRuleJSON {
	private String ruleName; // 规则名称
	private String ruleId; // 规则ID
	private String score; // 规则风险系数，只有权重策略模式下有效
	private String decision;// 规则决策结果，权重策略模式下规则无该字段
	private String memo;// 规则击中信息备注，如名单匹配规则返回相关名单的分类信息。
	private String template;
	private String detail;

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
