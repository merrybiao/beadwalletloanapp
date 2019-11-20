/**
 * @author heqiwen
 * @date 2016年12月23日
 */
package com.waterelephant.yixin.dto.param;

import java.util.Date;

import javax.persistence.Table;

/**
 * @author Administrator
 *
 */
@Table(name="yixin_main_data")
public class YixinQueryStatistics {
	private Long id;
	private Long mainid;
	private String timesByOtherOrg;//其他机构查询次数
	private String timesByCurrentOrg;//本机构查询次数
	private String otherOrgCount;//其它查询机构数
	private Date createTime;
	private Date updateTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMainid() {
		return mainid;
	}
	public void setMainid(Long mainid) {
		this.mainid = mainid;
	}
	public String getTimesByOtherOrg() {
		return timesByOtherOrg;
	}
	public void setTimesByOtherOrg(String timesByOtherOrg) {
		this.timesByOtherOrg = timesByOtherOrg;
	}
	public String getTimesByCurrentOrg() {
		return timesByCurrentOrg;
	}
	public void setTimesByCurrentOrg(String timesByCurrentOrg) {
		this.timesByCurrentOrg = timesByCurrentOrg;
	}
	public String getOtherOrgCount() {
		return otherOrgCount;
	}
	public void setOtherOrgCount(String otherOrgCount) {
		this.otherOrgCount = otherOrgCount;
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
