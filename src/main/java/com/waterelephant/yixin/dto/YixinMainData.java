/**
 * @author heqiwen
 * @date 2016年12月23日
 */
package com.waterelephant.yixin.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.waterelephant.yixin.dto.param.YixinLoanRecords;
import com.waterelephant.yixin.dto.param.YixinQueryHistory;
import com.waterelephant.yixin.dto.param.YixinQueryStatistics;
import com.waterelephant.yixin.dto.param.YixinRiskResults;

/**
 * @author Administrator
 *
 */
@Table(name="yixin_main_data")
public class YixinMainData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String flowId;
	private String zcCreditScore;
	private String contractBreakRate;
	private String idNo;
	private String name;
	private Date createTime;
	private Date updateTime;
	
	@Transient
	private YixinQueryStatistics queryStatistics;
	@Transient
	private List<YixinQueryHistory> queryHistory;
	@Transient
	private List<YixinLoanRecords> loanRecords;
	@Transient
	private List<YixinRiskResults> riskResults;
	private String timesByOtherOrg;//其他机构查询次数
	private String timesByCurrentOrg;//本机构查询次数
	private String otherOrgCount;//其它查询机构数
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getZcCreditScore() {
		return zcCreditScore;
	}
	public void setZcCreditScore(String zcCreditScore) {
		this.zcCreditScore = zcCreditScore;
	}
	public String getContractBreakRate() {
		return contractBreakRate;
	}
	public void setContractBreakRate(String contractBreakRate) {
		this.contractBreakRate = contractBreakRate;
	}
	
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public YixinQueryStatistics getQueryStatistics() {
		return queryStatistics;
	}
	public void setQueryStatistics(YixinQueryStatistics queryStatistics) {
		this.queryStatistics = queryStatistics;
	}
	public List<YixinQueryHistory> getQueryHistory() {
		return queryHistory;
	}
	public void setQueryHistory(List<YixinQueryHistory> queryHistory) {
		this.queryHistory = queryHistory;
	}
	public List<YixinLoanRecords> getLoanRecords() {
		return loanRecords;
	}
	public void setLoanRecords(List<YixinLoanRecords> loanRecords) {
		this.loanRecords = loanRecords;
	}
	public List<YixinRiskResults> getRiskResults() {
		return riskResults;
	}
	public void setRiskResults(List<YixinRiskResults> riskResults) {
		this.riskResults = riskResults;
	}
	
	
	public String getTimesByOtherOrg() {
		return timesByOtherOrg;
	}
	public void setTimesByOtherOrg(String timesByOtherOrg) {
		this.timesByOtherOrg = timesByOtherOrg;
	}
	public String getTimesByCurrentOrg() {
		return timesByCurrentOrg;
	}
	public void setTimesByCurrentOrg(String timesByCurrentOrg) {
		this.timesByCurrentOrg = timesByCurrentOrg;
	}
	public String getOtherOrgCount() {
		return otherOrgCount;
	}
	public void setOtherOrgCount(String otherOrgCount) {
		this.otherOrgCount = otherOrgCount;
	}
	/**
	 * @param id
	 * @param flowId
	 * @param zcCreditScore
	 * @param contractBreakRate
	 * @param createTime
	 * @param updateTime
	 * @param queryStatistics
	 * @param queryHistory
	 * @param loanRecords
	 * @param riskResults
	 * @author heqiwen
	 * @date 2016年12月23日
	 */
	private YixinMainData(Long id, String flowId, String zcCreditScore, String contractBreakRate, Date createTime,
			Date updateTime, YixinQueryStatistics queryStatistics, List<YixinQueryHistory> queryHistory,
			List<YixinLoanRecords> loanRecords, List<YixinRiskResults> riskResults) {
		super();
		this.id = id;
		this.flowId = flowId;
		this.zcCreditScore = zcCreditScore;
		this.contractBreakRate = contractBreakRate;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.queryStatistics = queryStatistics;
		this.queryHistory = queryHistory;
		this.loanRecords = loanRecords;
		this.riskResults = riskResults;
	}
	/**
	 * 
	 * @author heqiwen
	 * @date 2016年12月23日
	 */
	public YixinMainData() {
	}
	
	
	

}
