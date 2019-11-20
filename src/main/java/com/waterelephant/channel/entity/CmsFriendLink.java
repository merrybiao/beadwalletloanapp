/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.channel.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 
 * Module:
 * 
 * Link.java
 * 
 * @author 周诚享
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Table(name = "cms_friend_link")
public class CmsFriendLink implements java.io.Serializable {
	private static final long serialVersionUID = -2234902514334256557L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer linkId;//
	private String linkTitle;// 链接名
	private String linkUrl;// 链接地址
	private Integer linkStatus;// 设置是否显示
	private Integer sort;//
	private Date createTime;// 创建时间
	private Date updateTime;// 更新时间

	/**
	 * 构造函数
	 * 
	 * @param linkId
	 * @param linkTitle
	 * @param linkUrl
	 * @param linkStatus
	 * @param sort
	 * @param createTime
	 * @param updateTime
	 */
	/**
	 * 构造函数
	 */
	public CmsFriendLink() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return 获取 linkId属性值
	 */
	public Integer getLinkId() {
		return linkId;
	}

	/**
	 * @param linkId 设置 linkId 属性值为参数值 linkId
	 */
	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	/**
	 * @return 获取 linkTitle属性值
	 */
	public String getLinkTitle() {
		return linkTitle;
	}

	/**
	 * @param linkTitle 设置 linkTitle 属性值为参数值 linkTitle
	 */
	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	/**
	 * @return 获取 linkUrl属性值
	 */
	public String getLinkUrl() {
		return linkUrl;
	}

	/**
	 * @param linkUrl 设置 linkUrl 属性值为参数值 linkUrl
	 */
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	/**
	 * @return 获取 linkStatus属性值
	 */
	public Integer getLinkStatus() {
		return linkStatus;
	}

	/**
	 * @param linkStatus 设置 linkStatus 属性值为参数值 linkStatus
	 */
	public void setLinkStatus(Integer linkStatus) {
		this.linkStatus = linkStatus;
	}

	/**
	 * @return 获取 sort属性值
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * @param sort 设置 sort 属性值为参数值 sort
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * @return 获取 createTime属性值
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime 设置 createTime 属性值为参数值 createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return 获取 updateTime属性值
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime 设置 updateTime 属性值为参数值 updateTime
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return 获取 serialversionuid属性值
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
