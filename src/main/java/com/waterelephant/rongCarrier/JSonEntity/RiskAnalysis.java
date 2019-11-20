package com.waterelephant.rongCarrier.JSonEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 危险分析
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/16 10:58
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class RiskAnalysis {


    /**
     * blacklist_cnt : 0
     * loan_record_cnt : 0
     * searched_cnt : 143
     */

    private int blacklist_cnt;
    private int loan_record_cnt;
    private int searched_cnt;

    public int getBlacklist_cnt() {
        return blacklist_cnt;
    }

    public void setBlacklist_cnt(int blacklist_cnt) {
        this.blacklist_cnt = blacklist_cnt;
    }

    public int getLoan_record_cnt() {
        return loan_record_cnt;
    }

    public void setLoan_record_cnt(int loan_record_cnt) {
        this.loan_record_cnt = loan_record_cnt;
    }

    public int getSearched_cnt() {
        return searched_cnt;
    }

    public void setSearched_cnt(int searched_cnt) {
        this.searched_cnt = searched_cnt;
    }

    @Override
    public String toString() {
        return "RiskAnalysis{" +
                "blacklist_cnt=" + blacklist_cnt +
                ", loan_record_cnt=" + loan_record_cnt +
                ", searched_cnt=" + searched_cnt +
                '}';
    }
}
