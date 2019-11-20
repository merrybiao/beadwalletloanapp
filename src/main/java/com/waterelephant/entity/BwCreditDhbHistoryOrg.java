package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_credit_dhb_history_org")
public class BwCreditDhbHistoryOrg implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer offlineCashLoanCnt;// 线下现金贷出现次数
    private Integer onlineCashLoanCnt;// 线上现金贷出现次数
    private Integer onlineInstallmentCnt;// 线上消费分期出现次数
    private Integer paydayLoanCnt;// 小额快速贷出现次数
    private Date createTime;
    private Long infoId;
    private Integer creditCardRepaymentCnt;// 信用卡代还出现次数
    private Integer offlineInstallmentCnt;// 线下消费分期出现次数
    private Integer othersCnt;// 其他

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOfflineCashLoanCnt() {
        return this.offlineCashLoanCnt;
    }

    public void setOfflineCashLoanCnt(Integer offlineCashLoanCnt) {
        this.offlineCashLoanCnt = offlineCashLoanCnt;
    }

    public Integer getOnlineCashLoanCnt() {
        return this.onlineCashLoanCnt;
    }

    public void setOnlineCashLoanCnt(Integer onlineCashLoanCnt) {
        this.onlineCashLoanCnt = onlineCashLoanCnt;
    }

    public Integer getOnlineInstallmentCnt() {
        return this.onlineInstallmentCnt;
    }

    public void setOnlineInstallmentCnt(Integer onlineInstallmentCnt) {
        this.onlineInstallmentCnt = onlineInstallmentCnt;
    }

    public Integer getPaydayLoanCnt() {
        return this.paydayLoanCnt;
    }

    public void setPaydayLoanCnt(Integer paydayLoanCnt) {
        this.paydayLoanCnt = paydayLoanCnt;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getInfoId() {
        return this.infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public Integer getCreditCardRepaymentCnt() {
        return this.creditCardRepaymentCnt;
    }

    public void setCreditCardRepaymentCnt(Integer creditCardRepaymentCnt) {
        this.creditCardRepaymentCnt = creditCardRepaymentCnt;
    }

    public Integer getOfflineInstallmentCnt() {
        return this.offlineInstallmentCnt;
    }

    public void setOfflineInstallmentCnt(Integer offlineInstallmentCnt) {
        this.offlineInstallmentCnt = offlineInstallmentCnt;
    }

    public Integer getOthersCnt() {
        return this.othersCnt;
    }

    public void setOthersCnt(Integer othersCnt) {
        this.othersCnt = othersCnt;
    }

}
