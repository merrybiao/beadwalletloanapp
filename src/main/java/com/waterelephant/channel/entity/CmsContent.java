/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.channel.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 
 * Module:
 * 
 * CmsContent.java
 * 
 * @author 李萌
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Table(name = "cms_content")
public class CmsContent implements Serializable {

	/**  */
	private static final long serialVersionUID = 797658367338462876L;
	@Id
	private Long id;
	private Long channelId;
	private String title;
	private String titleImg;
	private String url;
	private String author;
	private String origin;
	private Integer status;
	private String summary;
	private String detail;
	private Date createTime;
	private Date updateTime;
	private Date beginTime;
	private Date endTime;
	private String channelName;
	private CmsChannel channel;

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
	 * @return 获取 channelId属性值
	 */
	public Long getChannelId() {
		return channelId;
	}

	/**
	 * @param channelId 设置 channelId 属性值为参数值 channelId
	 */
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	/**
	 * @return 获取 title属性值
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title 设置 title 属性值为参数值 title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return 获取 titleImg属性值
	 */
	public String getTitleImg() {
		return titleImg;
	}

	/**
	 * @param titleImg 设置 titleImg 属性值为参数值 titleImg
	 */
	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}

	/**
	 * @return 获取 url属性值
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url 设置 url 属性值为参数值 url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return 获取 author属性值
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author 设置 author 属性值为参数值 author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return 获取 origin属性值
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin 设置 origin 属性值为参数值 origin
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return 获取 status属性值
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status 设置 status 属性值为参数值 status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return 获取 summary属性值
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary 设置 summary 属性值为参数值 summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return 获取 detail属性值
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail 设置 detail 属性值为参数值 detail
	 */
	public void setDetail(String detail) {
		this.detail = detail;
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
	 * @return 获取 beginTime属性值
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime 设置 beginTime 属性值为参数值 beginTime
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return 获取 endTime属性值
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime 设置 endTime 属性值为参数值 endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	 * @return 获取 channel属性值
	 */
	public CmsChannel getChannel() {
		return channel;
	}

	/**
	 * @param channel 设置 channel 属性值为参数值 channel
	 */
	public void setChannel(CmsChannel channel) {
		this.channel = channel;
	}

}
