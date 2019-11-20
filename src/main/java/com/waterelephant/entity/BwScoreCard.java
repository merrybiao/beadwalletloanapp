package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 打分卡表. @author MyEclipse Persistence Tools
 */

@Table(name = "bw_score_card")
public class BwScoreCard implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 序列id
    private String indexName;// 指标名称
    private Integer indexType;// 指标类型
    private Long borrowerId;// 借款人id
    private Long orderId;// 工单id
    private Integer score;// 分数
    private Integer weiduType;// 维度类型
    private String weiduName;// 维度名称
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIndexType() {
        return indexType;
    }

    public void setIndexType(Integer indexType) {
        this.indexType = indexType;
    }

    public Integer getWeiduType() {
        return weiduType;
    }

    public void setWeiduType(Integer weiduType) {
        this.weiduType = weiduType;
    }

    public String getWeiduName() {
        return weiduName;
    }

    public void setWeiduName(String weiduName) {
        this.weiduName = weiduName;
    }



}
