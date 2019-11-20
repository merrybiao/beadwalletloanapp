package com.waterelephant.jiedianqian.entity;

import java.util.List;

public class Operator {
	private List<Smses> smses;

	private List<Calls> calls;

	private String datasource;
 
	private String juid;

	private Basic basic;  
  
	private List<Transactions> transactions;

	private String version;

	private String token;

	public List<Smses> getSmses() {
		return smses;
	}

	public void setSmses(List<Smses> smses) {
		this.smses = smses;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public String getJuid() {
		return juid;
	}

	public void setJuid(String juid) {
		this.juid = juid;
	}

	public Basic getBasic() {
		return basic;
	}

	public void setBasic(Basic basic) {
		this.basic = basic;
	}

	public List<Transactions> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transactions> transactions) {
		this.transactions = transactions;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<Calls> getCalls() {
		return calls;
	}

	public void setCalls(List<Calls> calls) {
		this.calls = calls;
	}

	@Override
	public String toString() {
		return "Operator [smses=" + smses + ", calls=" + calls + ", datasource=" + datasource + ", juid=" + juid
				+ ", basic=" + basic + ", transactions=" + transactions + ", version=" + version + ", token=" + token
				+ "]";
	}
}
