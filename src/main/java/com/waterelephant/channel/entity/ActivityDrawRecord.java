package com.waterelephant.channel.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 抽奖活动抽奖记录
 * 
 * 
 * Module:
 * 
 * ActivityDrawRecord.java
 * 
 * @author 毛汇源
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Table(name = "activity_draw_record")
public class ActivityDrawRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer borrowerId;// bigint(20) DEFAULT NULL COMMENT '用户id',
	/**
	 * 活动id
	 */
	private Integer activityId;// bigint(20) DEFAULT NULL COMMENT '活动id',
	/**
	 * 是否中奖 0未中奖；1中奖
	 */
	private Integer isWinning;// int(1) DEFAULT NULL COMMENT '是否中奖 0未中奖；1中奖',
	/**
	 * 奖品id
	 */
	private Integer prizeId;// bigint(20) DEFAULT NULL COMMENT '奖品id',
	/**
	 * 发放状态 0未发放；1已发放
	 */
	private Integer grantStatus;// int(1) DEFAULT NULL COMMENT '发放状态 0未发放；1已发放',
	/**
	 * 联系人姓名
	 */
	private String contactsName;// varchar(50) DEFAULT NULL COMMENT '联系人姓名',
	/**
	 * 联系人手机
	 */
	private String contactsPhone;// varchar(20) DEFAULT NULL COMMENT '联系人手机',
	/**
	 * 中奖时间
	 */
	private Date createTime;// datetime DEFAULT NULL COMMENT '中奖时间',
	/**
	 * 更新时间
	 */
	private Date updateTime;// datetime DEFAULT NULL COMMENT '中奖时间',

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(Integer borrowerId) {
		this.borrowerId = borrowerId;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getIsWinning() {
		return isWinning;
	}

	public void setIsWinning(Integer isWinning) {
		this.isWinning = isWinning;
	}

	public Integer getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Integer prizeId) {
		this.prizeId = prizeId;
	}

	public Integer getGrantStatus() {
		return grantStatus;
	}

	public void setGrantStatus(Integer grantStatus) {
		this.grantStatus = grantStatus;
	}

	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public String getContactsPhone() {
		return contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
