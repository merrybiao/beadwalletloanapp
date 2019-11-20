/**
 * @author heqiwen
 * @date 2016年12月23日
 */
package com.waterelephant.yixin.dto.param;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 *
 */
@Table(name="yixin_risk_results")
public class YixinRiskResults {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long mainid;
	private String orgName;
	private String riskItemTypeCode;
	private String riskItemValue;
	private String riskTypeCode;
	private String riskDetail;
	private String riskTime;
	private Date createTime;
	private Date updateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMainid() {
		return mainid;
	}
	public void setMainid(Long mainid) {
		this.mainid = mainid;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getRiskItemTypeCode() {
		return riskItemTypeCode;
	}
	public void setRiskItemTypeCode(String riskItemTypeCode) {
		this.riskItemTypeCode = riskItemTypeCode;
	}
	public String getRiskItemValue() {
		return riskItemValue;
	}
	public void setRiskItemValue(String riskItemValue) {
		this.riskItemValue = riskItemValue;
	}
	public String getRiskTypeCode() {
		return riskTypeCode;
	}
	public void setRiskTypeCode(String riskTypeCode) {
		this.riskTypeCode = riskTypeCode;
	}
	public String getRiskDetail() {
		return riskDetail;
	}
	public void setRiskDetail(String riskDetail) {
		this.riskDetail = riskDetail;
	}
	public String getRiskTime() {
		return riskTime;
	}
	public void setRiskTime(String riskTime) {
		this.riskTime = riskTime;
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
