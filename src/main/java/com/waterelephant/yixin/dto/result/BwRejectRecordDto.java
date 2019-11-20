package com.waterelephant.yixin.dto.result;

/**
 * 被拒的内容
 * @Description:TODO
 * @author:yanfuxing
 * @time:2016年12月15日 下午4:52:21
 */
public class BwRejectRecordDto {

	/**
	 * 命中项码 101010
	 */
	private String riskItemTypeCode;
	
	/**
	 * 命中内容  --身份证号码
	 */
	private String riskItemValue;
	
	/**
	 * 风险明细 描述
	 * 
	 */
	private String riskDetail;
	
	/**
	 * 风险最近的时间 yyyy
	 */
	private String riskTime;

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

	@Override
	public String toString() {
		return "BwRejectRecordDto [riskItemTypeCode=" + riskItemTypeCode + ", riskItemValue=" + riskItemValue
				+ ", riskDetail=" + riskDetail + ", riskTime=" + riskTime + "]";
	}
	
	
}
