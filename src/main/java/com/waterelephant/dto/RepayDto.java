package com.waterelephant.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 还款dto
 * 
 * @author song
 *
 */
public class RepayDto implements Serializable {

	private static final long serialVersionUID = 2853313188107798513L;

	private Double realityRepayMoney; // 实际还款金额
	private Date createTime; // 申请时间
	private int repayTerm; // 借款期限
	private int repayType; // 还款方式
	private Date repayTime; // 还款时间
	
	
	
	@Override
	public String toString() {
		return "RepayDto [realityRepayMoney=" + realityRepayMoney + ", createTime=" + createTime + ", repayTerm="
				+ repayTerm + ", repayType=" + repayType + ", repayTime=" + repayTime + "]";
	}

	public Double getRealityRepayMoney() {
		return realityRepayMoney;
	}

	public void setRealityRepayMoney(Double realityRepayMoney) {
		this.realityRepayMoney = realityRepayMoney;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getRepayTerm() {
		return repayTerm;
	}

	public void setRepayTerm(int repayTerm) {
		this.repayTerm = repayTerm;
	}

	public int getRepayType() {
		return repayType;
	}

	public void setRepayType(int repayType) {
		this.repayType = repayType;
	}

	public Date getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(Date repayTime) {
		this.repayTime = repayTime;
	}

}
