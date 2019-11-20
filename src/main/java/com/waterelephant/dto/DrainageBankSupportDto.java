
package com.waterelephant.dto;

/**
 * @ClassName: DrainageBankSupportDto  
 * @Description:   
 * @author liwanliang  
 * @date 2018年5月16日  
 *
 */
public class DrainageBankSupportDto {

	//"是否支持该银行卡"
	private Boolean supportBank;
	//"银行编码"
	private String bankCode;
    //"银行名称"
	private String bankName;
	
	public Boolean getSupportBank() {
		return supportBank;
	}
	public void setSupportBank(Boolean supportBank) {
		this.supportBank = supportBank;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	} 
}

