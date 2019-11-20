package com.waterelephant.drainage.entity.xianJinCard;

import java.util.List;

/**
 * Module: OperatorVerify.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述> 运营商原始数据
 */
public class OperatorVerify {
	private BasicVo basic;// 基本信息
	private List<CallsVo> calls;// 通话信息
	private String datasource;// 数据源
	// private List<NetsVo> nets;// 流量使用信息
	// private List<SmsesVo> smses;// 短信信息
	// private List<TransactionsVo> transactions;// 账单信息

	public void setBasic(BasicVo basic) {
		this.basic = basic;
	}

	public BasicVo getBasic() {
		return basic;
	}

	public List<CallsVo> getCalls() {
		return calls;
	}

	public void setCalls(List<CallsVo> calls) {
		this.calls = calls;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
	// public List<NetsVo> getNets() {
	// return nets;
	// }
	//
	// public void setNets(List<NetsVo> nets) {
	// this.nets = nets;
	// }
	//
	// public List<SmsesVo> getSmses() {
	// return smses;
	// }
	//
	// public void setSmses(List<SmsesVo> smses) {
	// this.smses = smses;
	// }
	//
	// public List<TransactionsVo> getTransactions() {
	// return transactions;
	// }
	//
	// public void setTransactions(List<TransactionsVo> transactions) {
	// this.transactions = transactions;
	// }

}