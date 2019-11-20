package com.waterelephant.zhengxin91.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.zhengxin91.entity.CrimeEscaping;

/**
 * 91征信 - 犯罪在逃（数据库实体）
 * @author liuDaodao
 *
 */
@Table(name="bw_zx_crimeEscaping")
public class CrimeEscapingDB implements Serializable{
	private static final long serialVersionUID = 455621884489544L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // 编号
	private String idCard; // 身份证号
	private String name; // 姓名
	private boolean status; // 状态
	private String caseTime; // 案件时间
	private Date createTime = new Date();// 创建时间

	public CrimeEscapingDB() {
		super();
	}

	public CrimeEscapingDB(CrimeEscaping crimeEscaping) {
		super();
		this.status = crimeEscaping.getStatus();
		this.caseTime = JSONObject.toJSONString(crimeEscaping.getCaseTime());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCaseTime() {
		return caseTime;
	}

	public void setCaseTime(String caseTime) {
		this.caseTime = caseTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
