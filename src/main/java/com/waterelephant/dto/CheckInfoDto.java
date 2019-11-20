package com.waterelephant.dto;

public class CheckInfoDto {

	/**
	 * 工单Id
	 */
	private Long orderId;
	/**
	 * 借款人id
	 */
	private Long bwId;
	/**
	 * 申请人姓名
	 */
	private String name;
	/**
	 * 申请人身份证号
	 */
	private String idNo;
	/**
	 * 申请人电话号码
	 */
	private String mobile;
	/**
	 * 亲属联系人姓名
	 */
	private String KinshipName;
	/**
	 * 亲属联系人电话号码
	 */
	private String KinshipMobile;
	/**
	 * 非亲属联系人姓名
	 */
	private String unKinshipName;
	/**
	 * 非亲属联系人电话号码
	 */
	private String unKinshipMobile;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getKinshipName() {
		return KinshipName;
	}
	public void setKinshipName(String kinshipName) {
		KinshipName = kinshipName;
	}
	public String getKinshipMobile() {
		return KinshipMobile;
	}
	public void setKinshipMobile(String kinshipMobile) {
		KinshipMobile = kinshipMobile;
	}
	public String getUnKinshipName() {
		return unKinshipName;
	}
	public void setUnKinshipName(String unKinshipName) {
		this.unKinshipName = unKinshipName;
	}
	public String getUnKinshipMobile() {
		return unKinshipMobile;
	}
	public void setUnKinshipMobile(String unKinshipMobile) {
		this.unKinshipMobile = unKinshipMobile;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getBwId() {
		return bwId;
	}
	public void setBwId(Long bwId) {
		this.bwId = bwId;
	}
	
	
	
}
