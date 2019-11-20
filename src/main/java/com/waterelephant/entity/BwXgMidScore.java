package com.waterelephant.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author lwl
 *
 */
@Table(name = "bw_xg_mid_score")
public class BwXgMidScore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主键
    private Long borrowerId; // 借款人ID
    private String dangerScore; // 危险名单分 0-1，分值越高，逾期率越高
    private String aprvScore; // 审批记录分 350-800，分值越高，逾期率越低
    private String installmentLoanScore; // 消费分期信用评分 350-800，分值越高，逾期率越低
    private String qaScore; // 资质记录分 350-800，分值越高，逾期率越低
    private String opScore; // 运营商分 0-1，分值越高，逾期率越高
    private String loanScore; // 借贷记录分 350-800，分值越高，逾期率越低
    private String zhimaScore; // 芝麻信用分 350-950，分值越高，逾期率越低
    private String paydayLoanScore; // 现金借款信用评分 350-800，分值越高，逾期率越低

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
}
