package com.waterelephant.channel.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 
 * 
 * Module:
 * 
 * Partner.java
 * 
 * @author 毛汇源
 * @since JDK 1.8
 * @version 1.0
 * @description: 合作伙伴实体类 2017-03-16
 */

@Table(name = "cms_partner")
public class Partner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/**
	 * 主键
	 */
	private Integer partnerId;

	/**
	 * 伙伴名称
	 */
	private String partnerName;

	/**
	 * 图片url
	 */
	private String imgUrl;
	/**
	 * 启用状态( 0 : 未启用；1：启用)
	 */
	private Integer status;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更改时间
	 */
	private Date updateTime;

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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
