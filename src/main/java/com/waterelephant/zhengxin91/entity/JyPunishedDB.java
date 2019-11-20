package com.waterelephant.zhengxin91.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.beadwallet.service.zhengxin91.entity.JyPunished;

/**
 * 91征信 - 人法（数据库实体）
 * @author liuDaodao
 *
 */
@Table(name="bw_zx_rf_jypunished")
public class JyPunishedDB extends JyPunished implements Serializable{
	private static final long serialVersionUID = 8892612246515465L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // 编号
	private Date createTime = new Date(); // 创建时间

	public JyPunishedDB() {
		super();
	}

	public JyPunishedDB(JyPunished jyPunished) {
		super();
		this.setCardNum(jyPunished.getCardNum());
		this.setCaseCode(jyPunished.getCaseCode());
		this.setName(jyPunished.getName());
		this.setSex(jyPunished.getSex());
		this.setAge(jyPunished.getAge());
		this.setIdCardIssued(jyPunished.getIdCardIssued());
		this.setAreaName(jyPunished.getAreaName());
		this.setCourtName(jyPunished.getCourtName());
		this.setRegDate(jyPunished.getRegDate());
		this.setCaseState(jyPunished.getCaseState());
		this.setExecMoney(jyPunished.getExecMoney());
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
