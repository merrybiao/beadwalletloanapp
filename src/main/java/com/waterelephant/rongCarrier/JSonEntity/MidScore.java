package com.waterelephant.rongCarrier.JSonEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 西瓜分中间分
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/16 13:52
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class MidScore {

    /**
     * danger_score : -1
     * aprv_score : -1
     * installment_loan_score : -1
     * qa_score : -1
     * op_score : 0.14291643
     * loan_score : 530
     * zhima_score : -1
     * payday_loan_score : -1
     */

    private String danger_score;
    private String aprv_score;
    private String installment_loan_score;
    private String qa_score;
    private String op_score;
    private String loan_score;
    private String zhima_score;
    private String payday_loan_score;

    public String getDanger_score() {
        return danger_score;
    }

    public void setDanger_score(String danger_score) {
        this.danger_score = danger_score;
    }

    public String getAprv_score() {
        return aprv_score;
    }

    public void setAprv_score(String aprv_score) {
        this.aprv_score = aprv_score;
    }

    public String getInstallment_loan_score() {
        return installment_loan_score;
    }

    public void setInstallment_loan_score(String installment_loan_score) {
        this.installment_loan_score = installment_loan_score;
    }

    public String getQa_score() {
        return qa_score;
    }

    public void setQa_score(String qa_score) {
        this.qa_score = qa_score;
    }

    public String getOp_score() {
        return op_score;
    }

    public void setOp_score(String op_score) {
        this.op_score = op_score;
    }

    public String getLoan_score() {
        return loan_score;
    }

    public void setLoan_score(String loan_score) {
        this.loan_score = loan_score;
    }

    public String getZhima_score() {
        return zhima_score;
    }

    public void setZhima_score(String zhima_score) {
        this.zhima_score = zhima_score;
    }

    public String getPayday_loan_score() {
        return payday_loan_score;
    }

    public void setPayday_loan_score(String payday_loan_score) {
        this.payday_loan_score = payday_loan_score;
    }
}
