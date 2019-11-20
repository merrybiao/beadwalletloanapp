package com.waterelephant.dto;

import java.io.Serializable;
import java.util.Date;

import com.waterelephant.entity.CmsContent;


/**
 * CMS内容
 *
 * @author duxiaoyong
 */
public class CmsContentVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7111887034467525822L;
	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 标题图片
	 */
	private String titleImg;
	/**
	 * 外部链接
	 */
	private String url;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 来源
	 */
	private String origin;
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	public CmsContentVo(CmsContent cmsContent) {
		super();
		this.id = cmsContent.getId();
		this.title = cmsContent.getTitle();
		this.titleImg = cmsContent.getTitleImg();
		this.url = cmsContent.getUrl();
		this.author = cmsContent.getAuthor();
		this.origin = cmsContent.getOrigin();
		this.summary = cmsContent.getSummary();
		this.createTime = cmsContent.getCreateTime();
		this.updateTime = cmsContent.getUpdateTime();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitleImg() {
		return titleImg;
	}
	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
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
