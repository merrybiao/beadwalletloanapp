package com.waterelephant.drainage.entity.rongShu;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaoXingWu
 * @time 2017年8月15日
 * @since JDK1.8
 * @description
 */
public class BwOrderVo {
	private Long credit_limit;//额度
	private Double borrow_amount;//借款金额
	private Double expect_money;//用户期望借款额度
	private  long status; //状态
	private String  remark; //备注
	private Date  updateTime;//
	public long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public Long getCredit_limit() {
		return credit_limit;
	}
	public void setCredit_limit(Long credit_limit) {
		this.credit_limit = credit_limit;
	}
	public Double getBorrow_amount() {
		return borrow_amount;
	}
	public void setBorrow_amount(Double borrow_amount) {
		this.borrow_amount = borrow_amount;
	}
	public Double getExpect_money() {
		return expect_money;
	}
	public void setExpect_money(Double expect_money) {
		this.expect_money = expect_money;
	}


	

}

