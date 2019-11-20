package com.waterelephant.morpho.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * code:18002
 * 闪蝶征信--流量查询
 * @author Lion
 */
@Table(name="bw_morpho_velocity_check")
public class BwMorphoVelocityCheck {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;// 主键
	private Long orderId;// 工单ID
	private String veidooType;// 维度类型
	private Integer subWindowSize;// 子窗口大小 从当前查询开始往前回朔的天数  30/60/90/180
	private Integer crossTenantAppsNumber;// 跨机构申请数
	private Integer mobileCount = 0;// 不同的手机数
	private Integer pidCount = 0;// 不同的身份证号
	private Date createTime;// 添加时间
	private String idNo;// 身份证
	
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVeidooType() {
		return veidooType;
	}
	public void setVeidooType(String veidooType) {
		this.veidooType = veidooType;
	}
	public Integer getSubWindowSize() {
		return subWindowSize;
	}
	public void setSubWindowSize(Integer subWindowSize) {
		this.subWindowSize = subWindowSize;
	}
	public Integer getCrossTenantAppsNumber() {
		return crossTenantAppsNumber;
	}
	public void setCrossTenantAppsNumber(Integer crossTenantAppsNumber) {
		this.crossTenantAppsNumber = crossTenantAppsNumber;
	}
	public Integer getMobileCount() {
		return mobileCount;
	}
	public void setMobileCount(Integer mobileCount) {
		this.mobileCount = mobileCount;
	}
	public Integer getPidCount() {
		return pidCount;
	}
	public void setPidCount(Integer pidCount) {
		this.pidCount = pidCount;
	}
}
