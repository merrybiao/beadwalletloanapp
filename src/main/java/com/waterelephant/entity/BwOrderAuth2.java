package com.waterelephant.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * BwOrderAuth类中有两个字段名字不规范，影响mapper操作
 * @author dinglinhao
 * @date 2018年11月29日15:44:29
 *
 */
@Table(name = "bw_order_auth")
public class BwOrderAuth2 implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;// 工单ID
    private Integer authType;// 认证类型
    private Integer authChannel;// 认证渠道
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间
    private Integer photoState;// 照片认证状态
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Integer getAuthType() {
		return authType;
	}
	public void setAuthType(Integer authType) {
		this.authType = authType;
	}
	public Integer getAuthChannel() {
		return authChannel;
	}
	public void setAuthChannel(Integer authChannel) {
		this.authChannel = authChannel;
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
	public Integer getPhotoState() {
		return photoState;
	}
	public void setPhotoState(Integer photoState) {
		this.photoState = photoState;
	}

   
}
