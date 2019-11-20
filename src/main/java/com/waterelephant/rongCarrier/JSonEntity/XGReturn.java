package com.waterelephant.rongCarrier.JSonEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 西瓜返回
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/16 13:51
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class XGReturn {
    private String score;
    private MidScore mid_score;
    private OpReport op_report;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public MidScore getMid_score() {
        return mid_score;
    }

    public void setMid_score(MidScore mid_score) {
        this.mid_score = mid_score;
    }

    public OpReport getOp_report() {
        return op_report;
    }

    public void setOp_report(OpReport op_report) {
        this.op_report = op_report;
    }
}
