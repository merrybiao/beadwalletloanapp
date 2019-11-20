package com.waterelephant.morpho.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 闪蝶征信  code:18002
 * 黑名单查询
 * @author Lion
 */
@Table(name="bw_morpho_blacklist")
public class BwMorphoBlacklist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;// 主键
	private Long orderId;// 工单ID
	private String veidooType;// 维度类型
	private String blackLevel;// 黑名单类型
	private Integer last6MTenantCount;// 最近6个月确认为黑名单的机构数
	private Integer last6MQueryCount;// 最近6个月申请查询数
	private Integer lastConfirmAtDays;// 最新入库距离天数 （如没有记录，则为：-1）
	private String lastConfirmStatus;// 最新欺诈／逾期状态
	private String last12MMaxConfirmStatus;// 最近12个月最严重的欺诈／逾期状态
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
	public String getBlackLevel() {
		return blackLevel;
	}
	public void setBlackLevel(String blackLevel) {
		this.blackLevel = blackLevel;
	}
	public Integer getLast6MTenantCount() {
		return last6MTenantCount;
	}
	public void setLast6MTenantCount(Integer last6mTenantCount) {
		last6MTenantCount = last6mTenantCount;
	}
	public Integer getLast6MQueryCount() {
		return last6MQueryCount;
	}
	public void setLast6MQueryCount(Integer last6mQueryCount) {
		last6MQueryCount = last6mQueryCount;
	}
	public Integer getLastConfirmAtDays() {
		return lastConfirmAtDays;
	}
	public void setLastConfirmAtDays(Integer lastConfirmAtDays) {
		this.lastConfirmAtDays = lastConfirmAtDays;
	}
	public String getLastConfirmStatus() {
		return lastConfirmStatus;
	}
	public void setLastConfirmStatus(String lastConfirmStatus) {
		this.lastConfirmStatus = lastConfirmStatus;
	}
	public String getLast12MMaxConfirmStatus() {
		return last12MMaxConfirmStatus;
	}
	public void setLast12MMaxConfirmStatus(String last12mMaxConfirmStatus) {
		last12MMaxConfirmStatus = last12mMaxConfirmStatus;
	}
}
