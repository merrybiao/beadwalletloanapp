package com.waterelephant.zhengxin91.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 借贷黑名单
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/4/6 10:59
 */
@Table(name = "bw_zx_black")
public class ZxBlack implements Serializable {
	private static final long serialVersionUID = 8987699327832151875L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long borrowId;
	private Long orderId;
	private String name;
	private String idCard;
	private Integer source;
	private Integer sourceItem;
	private Integer type;
	private String remark;
	private Integer rejectType;
	private String rejectInfo;
	private Date createTime;
	private Date updateTime;
	private Double score;

	public ZxBlack() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSourceItem() {
		return sourceItem;
	}

	public void setSourceItem(Integer sourceItem) {
		this.sourceItem = sourceItem;
	}

	public Long getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Long borrowId) {
		this.borrowId = borrowId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getRejectType() {
		return rejectType;
	}

	public void setRejectType(Integer rejectType) {
		this.rejectType = rejectType;
	}

	public String getRejectInfo() {
		return rejectInfo;
	}

	public void setRejectInfo(String rejectInfo) {
		this.rejectInfo = rejectInfo;
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

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "ZxBlack [id=" + id + ", borrowId=" + borrowId + ", orderId=" + orderId + ", name=" + name + ", idCard="
				+ idCard + ", source=" + source + ", sourceItem=" + sourceItem + ", type=" + type + ", remark=" + remark
				+ ", rejectType=" + rejectType + ", rejectInfo=" + rejectInfo + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", score=" + score + "]";
	}
}
