package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_credit_dhb_history_search")
public class BwCreditDhbHistorySearch implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer orgCntRecent7Days;// 最近7天查询机构数
    private Integer searchCntRecent7Days;// 最近7天查询次数
    private Integer searchCntRecent30Days;// 最近30天查询次数
    private Integer searchCntRecent90Days;// 最近90天查询次数
    private Integer orgCntRecent180Days;// 最近180天查询机构数
    private Integer searchCntRecent180Days;// 最近180天查询次数
    private Date createTime;
    private Integer orgCntRecent60Days;// 最近60天查询机构数
    private Integer orgCntRecent14Days;// 最近14天查询机构数
    private Integer orgCntRecent30Days;// 最近30天查询机构数
    private Integer searchCntRecent60Days;// 最近60天查询次数
    private Integer orgCntRecent90Days;// 最近90天查询机构数
    private Long infoId;
    private Integer orgCnt;// 历史查询总机构数
    private Integer searchCntRecent14Days;// 最近14天查询次数
    private Integer searchCnt;// 历史查询总次数

    public BwCreditDhbHistorySearch() {
        super();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrgCntRecent7Days() {
        return this.orgCntRecent7Days;
    }

    public void setOrgCntRecent7Days(Integer orgCntRecent7Days) {
        this.orgCntRecent7Days = orgCntRecent7Days;
    }

    public Integer getSearchCntRecent7Days() {
        return this.searchCntRecent7Days;
    }

    public void setSearchCntRecent7Days(Integer searchCntRecent7Days) {
        this.searchCntRecent7Days = searchCntRecent7Days;
    }

    public Integer getSearchCntRecent30Days() {
        return this.searchCntRecent30Days;
    }

    public void setSearchCntRecent30Days(Integer searchCntRecent30Days) {
        this.searchCntRecent30Days = searchCntRecent30Days;
    }

    public Integer getSearchCntRecent90Days() {
        return this.searchCntRecent90Days;
    }

    public void setSearchCntRecent90Days(Integer searchCntRecent90Days) {
        this.searchCntRecent90Days = searchCntRecent90Days;
    }

    public Integer getOrgCntRecent180Days() {
        return this.orgCntRecent180Days;
    }

    public void setOrgCntRecent180Days(Integer orgCntRecent180Days) {
        this.orgCntRecent180Days = orgCntRecent180Days;
    }

    public Integer getSearchCntRecent180Days() {
        return this.searchCntRecent180Days;
    }

    public void setSearchCntRecent180Days(Integer searchCntRecent180Days) {
        this.searchCntRecent180Days = searchCntRecent180Days;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOrgCntRecent60Days() {
        return this.orgCntRecent60Days;
    }

    public void setOrgCntRecent60Days(Integer orgCntRecent60Days) {
        this.orgCntRecent60Days = orgCntRecent60Days;
    }

    public Integer getOrgCntRecent14Days() {
        return this.orgCntRecent14Days;
    }

    public void setOrgCntRecent14Days(Integer orgCntRecent14Days) {
        this.orgCntRecent14Days = orgCntRecent14Days;
    }

    public Integer getOrgCntRecent30Days() {
        return this.orgCntRecent30Days;
    }

    public void setOrgCntRecent30Days(Integer orgCntRecent30Days) {
        this.orgCntRecent30Days = orgCntRecent30Days;
    }

    public Integer getSearchCntRecent60Days() {
        return this.searchCntRecent60Days;
    }

    public void setSearchCntRecent60Days(Integer searchCntRecent60Days) {
        this.searchCntRecent60Days = searchCntRecent60Days;
    }

    public Integer getOrgCntRecent90Days() {
        return this.orgCntRecent90Days;
    }

    public void setOrgCntRecent90Days(Integer orgCntRecent90Days) {
        this.orgCntRecent90Days = orgCntRecent90Days;
    }

    public Long getInfoId() {
        return this.infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public Integer getOrgCnt() {
        return this.orgCnt;
    }

    public void setOrgCnt(Integer orgCnt) {
        this.orgCnt = orgCnt;
    }

    public Integer getSearchCntRecent14Days() {
        return this.searchCntRecent14Days;
    }

    public void setSearchCntRecent14Days(Integer searchCntRecent14Days) {
        this.searchCntRecent14Days = searchCntRecent14Days;
    }

    public Integer getSearchCnt() {
        return this.searchCnt;
    }

    public void setSearchCnt(Integer searchCnt) {
        this.searchCnt = searchCnt;
    }

}
