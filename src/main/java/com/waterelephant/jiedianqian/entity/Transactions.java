package com.waterelephant.jiedianqian.entity;

/**
 * 
 * 
 * Module: 
 * 
 * Transactions.java 
 * @author 张博
 * @since JDK 1.8
 * @version 1.0
 * @description: <账单信息>
 */
public class Transactions {			
	private String total_amt;		//总费用（元）

	private String update_time;		//数据获取时间

	private String pay_amt;			//实际缴费金额（元）

	private String plan_amt;		//套餐及固定费（元）

	private String bill_cycle;		//账单月份
	
	private String cell_phone;		//本机号码

	public void setTotal_amt(String total_amt) {
		this.total_amt = total_amt;
	}

	public String getTotal_amt() {
		return this.total_amt;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getUpdate_time() {
		return this.update_time;
	}

	public void setPay_amt(String pay_amt) {
		this.pay_amt = pay_amt;
	}

	public String getPay_amt() {
		return this.pay_amt;
	}

	public void setPlan_amt(String plan_amt) {
		this.plan_amt = plan_amt;
	}

	public String getPlan_amt() {
		return this.plan_amt;
	}

	public void setBill_cycle(String bill_cycle) {
		this.bill_cycle = bill_cycle;
	}

	public String getBill_cycle() {
		return this.bill_cycle;
	}

	public void setCell_phone(String cell_phone) {
		this.cell_phone = cell_phone;
	}

	public String getCell_phone() {
		return this.cell_phone;
	}
}
