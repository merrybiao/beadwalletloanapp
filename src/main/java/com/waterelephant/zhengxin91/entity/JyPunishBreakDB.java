package com.waterelephant.zhengxin91.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.beadwallet.service.zhengxin91.entity.JyPunishBreak;

/**
 * 91征信 - 人法（数据库实体）
 * @author liuDaodao
 *
 */
@Table(name="bw_zx_rf_jypunishbreak")
public class JyPunishBreakDB extends JyPunishBreak implements Serializable{
	private static final long serialVersionUID = 78928521252455L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // 编号
	private Date createTime = new Date(); // 创建时间

	public JyPunishBreakDB() {
		super();
	}

	public JyPunishBreakDB(JyPunishBreak jyPunishBreak) {
		super();
		this.setCardNum(jyPunishBreak.getCardNum());
		this.setName(jyPunishBreak.getName());
		this.setCaseCode(jyPunishBreak.getCaseCode());
		this.setType(jyPunishBreak.getType());
		this.setSex(jyPunishBreak.getSex());
		this.setAge(jyPunishBreak.getAge());
		this.setIdCardIssued(jyPunishBreak.getIdCardIssued());
		this.setLegalPerson(jyPunishBreak.getLegalPerson());
		this.setRegDate(jyPunishBreak.getRegDate());
		this.setPublishDate(jyPunishBreak.getPublishDate());
		this.setCourName(jyPunishBreak.getCourName());
		this.setAreaName(jyPunishBreak.getAreaName());
		this.setGistId(jyPunishBreak.getGistId());
		this.setGistUnit(jyPunishBreak.getGistUnit());
		this.setDuty(jyPunishBreak.getDuty());
		this.setDisruptTypeName(jyPunishBreak.getDisruptTypeName());
		this.setPerformance(jyPunishBreak.getPerformance());
		this.setPerformedPart(jyPunishBreak.getPerformedPart());
		this.setUnperformPart(jyPunishBreak.getUnperformPart());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
