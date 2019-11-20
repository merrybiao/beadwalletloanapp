package com.waterelephant.yixin.dto.result;

import java.util.List;

/**
 * 返回的宜信的data内容
 * @Description:TODO
 * @author:yanfuxing
 * @time:2016年12月15日 下午4:48:23
 */
public class YixinDataDto {
	
	//订单和逾期的内容
	private List<BwOrderDto> loanRecords;
	
	//被拒的内容
	private List<BwRejectRecordDto> riskResults;

	public List<BwOrderDto> getLoanRecords() {
		return loanRecords;
	}

	public void setLoanRecords(List<BwOrderDto> loanRecords) {
		this.loanRecords = loanRecords;
	}

	public List<BwRejectRecordDto> getRiskResults() {
		return riskResults;
	}

	public void setRiskResults(List<BwRejectRecordDto> riskResults) {
		this.riskResults = riskResults;
	}

	@Override
	public String toString() {
		return "YixinDataDto [loanRecords=" + loanRecords + ", riskResults=" + riskResults + "]";
	}
	
	
}
