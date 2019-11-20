package com.waterelephant.zhengxin91.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 91征信 - 错误信息
 * @author liuDaodao
 * @date 2017年4月5日
 *
 */
@Table(name = "bw_zx_error")
public class ZxError implements Serializable {

	private static final long serialVersionUID = 7987699547832151875L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 编号
	private String trxNo;
	private String msg;
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTrxNo() {
		return trxNo;
	}

	public void setTrxNo(String trxNo) {
		this.trxNo = trxNo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}