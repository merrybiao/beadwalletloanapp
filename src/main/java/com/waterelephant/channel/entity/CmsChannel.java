/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.channel.entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 
 * Module:
 * 
 * CmsChannel.java
 * 
 * @author 李萌
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Table(name = "cms_channel")
public class CmsChannel implements Serializable {

	/**  */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	@Id
	private Long id;
	/**
	 * 父栏目
	 */
	private CmsChannel parent;
	/**
	 * 栏目名称
	 */
	private String channelName;
	/**
	 * 排序
	 */
	private Integer priority;
	/**
	 * 树节点左边值
	 */
	private Long lft;
	/**
	 * 树节点左边值
	 */
	private Long rgt;

	/**  */
	private Long parentId;

	/**
	 * @return 获取 id属性值
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id 设置 id 属性值为参数值 id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return 获取 parent属性值
	 */
	public CmsChannel getParent() {
		return parent;
	}

	/**
	 * @param parent 设置 parent 属性值为参数值 parent
	 */
	public void setParent(CmsChannel parent) {
		this.parent = parent;
	}

	/**
	 * @return 获取 channelName属性值
	 */
	public String getChannelName() {
		return channelName;
	}

	/**
	 * @param channelName 设置 channelName 属性值为参数值 channelName
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * @return 获取 priority属性值
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * @param priority 设置 priority 属性值为参数值 priority
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	 * @return 获取 lft属性值
	 */
	public Long getLft() {
		return lft;
	}

	/**
	 * @param lft 设置 lft 属性值为参数值 lft
	 */
	public void setLft(Long lft) {
		this.lft = lft;
	}

	/**
	 * @return 获取 rgt属性值
	 */
	public Long getRgt() {
		return rgt;
	}

	/**
	 * @param rgt 设置 rgt 属性值为参数值 rgt
	 */
	public void setRgt(Long rgt) {
		this.rgt = rgt;
	}

	/**
	 * @return 获取 parentId属性值
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId 设置 parentId 属性值为参数值 parentId
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
