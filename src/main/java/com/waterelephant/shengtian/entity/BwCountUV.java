package com.waterelephant.shengtian.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ClassName:BwCountUV <br/>
 * Function: TODO  <br/>
 * Date:     2018年5月10日 上午9:54:55 <br/>
 * @author   liwanliang
 * @version   1.0
 * @since    JDK 1.7
 * @see 	 
 */

@Table(name = "bw_count_uv")
public class BwCountUV implements Serializable{
	
	private static final long serialVersionUID = -6384781687583098550L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//主键ID
	private Long id;
	
    //用户的IP地址
	private String userIp;
	
	//用户对应Cookie的Value值
	private String cookieVal;
	
	//用户访问时间
	private Date createTime;
	
	//预留字段1
	private String ext1;
	
	//预留字段2
	private String ext2;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getCookieVal() {
		return cookieVal;
	}
	public void setCookieVal(String cookieVal) {
		this.cookieVal = cookieVal;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
}

