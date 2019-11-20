package com.waterelephant.drainage.entity.qihu360;

/**
 * 
 * 
 * 
 * Module:抓取信审信息（addInfo）
 * 
 * AddInfo.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class AddInfo {
	private AddInfoCredit credit; // 信用分
	private AddInfoMobile mobile; // 运营商数据

	public AddInfoMobile getMobile() {
		return mobile;
	}

	public void setMobile(AddInfoMobile mobile) {
		this.mobile = mobile;
	}

	public AddInfoCredit getCredit() {
		return credit;
	}

	public void setCredit(AddInfoCredit credit) {
		this.credit = credit;
	}

}
