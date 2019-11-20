package com.waterelephant.channel.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;

@Table(name = "bw_notice_record")
public class BwNoticeRecord implements Serializable {

	/**  */
	private static final long serialVersionUID = 1L;

	private Long id;

	/** 用户id */
	private Long borrowerId;

	/** 公告id */
	private Long noticeId;

	/** 创建时间 */
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(Long borrowerId) {
		this.borrowerId = borrowerId;
	}

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
