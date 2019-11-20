package com.waterelephant.rongCarrier.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 西瓜分中间分
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/15
 */
@Table(name = "bw_xg_mid_score")
public class XgMidScore implements Serializable {
	private static final long serialVersionUID = 8687699327832151875L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long borrowerId;
	private String dangerScore;
	private String aprvScore;
	private String installmentLoanScore;
	private String qaScore;
	private String opScore;
	private String loanScore;
	private String zhimaScore;
	private String paydayLoanScore;

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

	public String getDangerScore() {
		return dangerScore;
	}

	public void setDangerScore(String dangerScore) {
		this.dangerScore = dangerScore;
	}

	public String getAprvScore() {
		return aprvScore;
	}

	public void setAprvScore(String aprvScore) {
		this.aprvScore = aprvScore;
	}

	public String getInstallmentLoanScore() {
		return installmentLoanScore;
	}

	public void setInstallmentLoanScore(String installmentLoanScore) {
		this.installmentLoanScore = installmentLoanScore;
	}

	public String getQaScore() {
		return qaScore;
	}

	public void setQaScore(String qaScore) {
		this.qaScore = qaScore;
	}

	public String getOpScore() {
		return opScore;
	}

	public void setOpScore(String opScore) {
		this.opScore = opScore;
	}

	public String getLoanScore() {
		return loanScore;
	}

	public void setLoanScore(String loanScore) {
		this.loanScore = loanScore;
	}

	public String getZhimaScore() {
		return zhimaScore;
	}

	public void setZhimaScore(String zhimaScore) {
		this.zhimaScore = zhimaScore;
	}

	public String getPaydayLoanScore() {
		return paydayLoanScore;
	}

	public void setPaydayLoanScore(String paydayLoanScore) {
		this.paydayLoanScore = paydayLoanScore;
	}

	@Override
	public String toString() {
		return "XgMidScore{" +
				"borrower_id=" + borrowerId +
				", dangerScore='" + dangerScore + '\'' +
				", aprvScore='" + aprvScore + '\'' +
				", installmentLoanScore='" + installmentLoanScore + '\'' +
				", qaScore='" + qaScore + '\'' +
				", opScore='" + opScore + '\'' +
				", loanScore='" + loanScore + '\'' +
				", zhimaScore='" + zhimaScore + '\'' +
				", paydayLoanScore='" + paydayLoanScore + '\'' +
				'}';
	}
}
