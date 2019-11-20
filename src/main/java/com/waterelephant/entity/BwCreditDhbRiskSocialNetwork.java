package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_credit_dhb_risk_social_network")
public class BwCreditDhbRiskSocialNetwork implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer snOrder2BlacklistRoutersCnt;// 认识间接黑人的直接联系人个数
    private Date createTime;
    private Integer snOrder1BlacklistContactsCnt;// 直接联系人在黑名单中数量(直接黑人)
    private Integer snOrder2BlacklistContactsCnt;// 间接联系人在黑名单中数量(间接黑人)
    private Integer snOrder1ContactsCnt;// 直接联系人
    private Long infoId;
    private Double snOrder2BlacklistRoutersPct;// 认识间接黑人的直接联系人比例
    private Integer snScore;// 葫芦分

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSnOrder2BlacklistRoutersCnt() {
        return this.snOrder2BlacklistRoutersCnt;
    }

    public void setSnOrder2BlacklistRoutersCnt(Integer snOrder2BlacklistRoutersCnt) {
        this.snOrder2BlacklistRoutersCnt = snOrder2BlacklistRoutersCnt;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSnOrder1BlacklistContactsCnt() {
        return this.snOrder1BlacklistContactsCnt;
    }

    public void setSnOrder1BlacklistContactsCnt(Integer snOrder1BlacklistContactsCnt) {
        this.snOrder1BlacklistContactsCnt = snOrder1BlacklistContactsCnt;
    }

    public Integer getSnOrder2BlacklistContactsCnt() {
        return this.snOrder2BlacklistContactsCnt;
    }

    public void setSnOrder2BlacklistContactsCnt(Integer snOrder2BlacklistContactsCnt) {
        this.snOrder2BlacklistContactsCnt = snOrder2BlacklistContactsCnt;
    }

    public Integer getSnOrder1ContactsCnt() {
        return this.snOrder1ContactsCnt;
    }

    public void setSnOrder1ContactsCnt(Integer snOrder1ContactsCnt) {
        this.snOrder1ContactsCnt = snOrder1ContactsCnt;
    }

    public Long getInfoId() {
        return this.infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public Double getSnOrder2BlacklistRoutersPct() {
        return this.snOrder2BlacklistRoutersPct;
    }

    public void setSnOrder2BlacklistRoutersPct(Double snOrder2BlacklistRoutersPct) {
        this.snOrder2BlacklistRoutersPct = snOrder2BlacklistRoutersPct;
    }

    public Integer getSnScore() {
        return this.snScore;
    }

    public void setSnScore(Integer snScore) {
        this.snScore = snScore;
    }

}
