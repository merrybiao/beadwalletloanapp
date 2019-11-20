package com.waterelephant.fudata.entity.vo;

import java.util.Map;

/**
 * @author dniglinhao
 *
 */
public class FudataCrawlerVO {
	
	private String open_id;

	private String organization_id;
	
	private String entry_id;
	
	private String custParams;
	
	private String version;
	
	private Map<String,String> accountInfo;
	
	public FudataCrawlerVO(Map<String,String> params) {
		
		if(params == null || params.isEmpty())return;
		
		if(params.containsKey("open_id"))
			open_id = params.remove("open_id");
		if(params.containsKey("org_id"))
			organization_id = params.remove("org_id");
		if(params.containsKey("entry_id"))
			entry_id = params.remove("entry_id");
		if(params.containsKey("version"))
			version = params.remove("version");
		if(params.containsKey("custParams"))
			custParams = params.remove("custParams");
		accountInfo = params;
	}

	public String getOpen_id() {
		return open_id;
	}

	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}

	public String getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(String organization_id) {
		this.organization_id = organization_id;
	}

	public String getEntry_id() {
		return entry_id;
	}

	public void setEntry_id(String entry_id) {
		this.entry_id = entry_id;
	}

	public String getCustParams() {
		return custParams;
	}

	public void setCustParams(String custParams) {
		this.custParams = custParams;
	}

	public Map<String, String> getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(Map<String, String> accountInfo) {
		this.accountInfo = accountInfo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
}