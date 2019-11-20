package com.waterelephant.drainage.entity.xianJinCard;

/**
 * Module: WorkEnterpriseInfo.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class WorkEnterpriseInfo {
	private String company_name;// 企业名称
	private String user_position;// 经营者身份 1.股东，2.法人，3.其它
	private String share;// 所在股份
	private String is_market;// 是否已上市: 1:已上市; 2：未上市
	private String true_operation_address;// 实际经营所在地
	private String industry;// 所属行业
	private String manage_type;// 经营类型
	private String is_license;// is_license
	private String manage_life_time;// 经营年限
	private String total_revenue;// 每月总营收（万元）
	private String public_revenue;// 每月对公流水（万元）
	private String private_revenue;// 每月对私流水（万元）
	private String settle_revenue;// 每月现金结算收益（万元）

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getUser_position() {
		return user_position;
	}

	public void setUser_position(String user_position) {
		this.user_position = user_position;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}

	public String getIs_market() {
		return is_market;
	}

	public void setIs_market(String is_market) {
		this.is_market = is_market;
	}

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

	public String getPublic_revenue() {
		return public_revenue;
	}

	public void setPublic_revenue(String public_revenue) {
		this.public_revenue = public_revenue;
	}

	public String getPrivate_revenue() {
		return private_revenue;
	}

	public void setPrivate_revenue(String private_revenue) {
		this.private_revenue = private_revenue;
	}

	public String getSettle_revenue() {
		return settle_revenue;
	}

	public void setSettle_revenue(String settle_revenue) {
		this.settle_revenue = settle_revenue;
	}

}