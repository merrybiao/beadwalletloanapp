package com.waterelephant.drainage.entity.xianJinCard;

/**
 * Module: WorkSoleInfo.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class WorkSoleInfo {
	private String true_operation_address;// true_operation_address string 是 实际经营所在地
	private String industry;// industry string 是 所属行业
	private String manage_type;// manage_type string 是 经营类型
	private String is_license;// is_license int 是 是否办理营业执照 : 1：否; 2：是
	private String manage_life_time;// manage_life_time int 是 经营年限
	private String total_revenue;// total_revenue int 是 每月总营收（万元）

	public String getTrue_operation_address() {
		return true_operation_address;
	}

	public void setTrue_operation_address(String true_operation_address) {
		this.true_operation_address = true_operation_address;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getManage_type() {
		return manage_type;
	}

	public void setManage_type(String manage_type) {
		this.manage_type = manage_type;
	}

	public String getIs_license() {
		return is_license;
	}

	public void setIs_license(String is_license) {
		this.is_license = is_license;
	}

	public String getManage_life_time() {
		return manage_life_time;
	}

	public void setManage_life_time(String manage_life_time) {
		this.manage_life_time = manage_life_time;
	}

	public String getTotal_revenue() {
		return total_revenue;
	}

	public void setTotal_revenue(String total_revenue) {
		this.total_revenue = total_revenue;
	}

}