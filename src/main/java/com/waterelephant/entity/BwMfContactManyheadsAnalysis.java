package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_contact_manyheads_analysis
 * @author 
 */
@Table(name = "bw_mf_contact_manyheads_analysis")
public class BwMfContactManyheadsAnalysis implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 工单id
     */
    private Long orderId;

    /**
     * 认证任务id
     */
    private String taskId;

    /**
     * 前10联系人近6月平均申请平台数
     */
    private String manyheadsTop10ContactRecent6monthPartnercodeCountAvg;

    /**
     * 前10联系人近6月申请2个及以上平台的人数
     */
    private String manyheadsTop10ContactRecent6monthPartnercodeCountOver2;

    /**
     * 前10联系人近1月平均申请平台数
     */
    private String manyheadsTop10ContactRecent1monthPartnercodeCountAvg;

    /**
     * 前10联系人近3月申请2个及以上平台的人数
     */
    private String manyheadsTop10ContactRecent3monthPartnercodeCountOver2;

    /**
     * 前10联系人近6月最大申请平台数
     */
    private String manyheadsTop10ContactRecent6monthPartnercodeCountMax;

    /**
     * 前10联系人近3月最大申请平台数
     */
    private String manyheadsTop10ContactRecent3monthPartnercodeCountMax;

    /**
     * 前10联系人近1月最大申请平台数
     */
    private String manyheadsTop10ContactRecent1monthPartnercodeCountMax;

    /**
     * 前10联系人近1月有申请平台的人数
     */
    private String manyheadsTop10ContactRecent1monthHavePartnercodeCount;

    /**
     * 前10联系人近3月平均申请平台数
     */
    private String manyheadsTop10ContactRecent3monthPartnercodeCountAvg;

    /**
     * 前10联系人近3月有申请平台的人数
     */
    private String manyheadsTop10ContactRecent3monthHavePartnercodeCount;

    /**
     * 前10联系人近6月有申请平台的人数
     */
    private String manyheadsTop10ContactRecent6monthHavePartnercodeCount;

    /**
     * 前10联系人近1月申请2个及以上平台的人数
     */
    private String manyheadsTop10ContactRecent1monthPartnercodeCountOver2;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getManyheadsTop10ContactRecent6monthPartnercodeCountAvg() {
        return manyheadsTop10ContactRecent6monthPartnercodeCountAvg;
    }

    public void setManyheadsTop10ContactRecent6monthPartnercodeCountAvg(String manyheadsTop10ContactRecent6monthPartnercodeCountAvg) {
        this.manyheadsTop10ContactRecent6monthPartnercodeCountAvg = manyheadsTop10ContactRecent6monthPartnercodeCountAvg;
    }

    public String getManyheadsTop10ContactRecent6monthPartnercodeCountOver2() {
        return manyheadsTop10ContactRecent6monthPartnercodeCountOver2;
    }

    public void setManyheadsTop10ContactRecent6monthPartnercodeCountOver2(String manyheadsTop10ContactRecent6monthPartnercodeCountOver2) {
        this.manyheadsTop10ContactRecent6monthPartnercodeCountOver2 = manyheadsTop10ContactRecent6monthPartnercodeCountOver2;
    }

    public String getManyheadsTop10ContactRecent1monthPartnercodeCountAvg() {
        return manyheadsTop10ContactRecent1monthPartnercodeCountAvg;
    }

    public void setManyheadsTop10ContactRecent1monthPartnercodeCountAvg(String manyheadsTop10ContactRecent1monthPartnercodeCountAvg) {
        this.manyheadsTop10ContactRecent1monthPartnercodeCountAvg = manyheadsTop10ContactRecent1monthPartnercodeCountAvg;
    }

    public String getManyheadsTop10ContactRecent3monthPartnercodeCountOver2() {
        return manyheadsTop10ContactRecent3monthPartnercodeCountOver2;
    }

    public void setManyheadsTop10ContactRecent3monthPartnercodeCountOver2(String manyheadsTop10ContactRecent3monthPartnercodeCountOver2) {
        this.manyheadsTop10ContactRecent3monthPartnercodeCountOver2 = manyheadsTop10ContactRecent3monthPartnercodeCountOver2;
    }

    public String getManyheadsTop10ContactRecent6monthPartnercodeCountMax() {
        return manyheadsTop10ContactRecent6monthPartnercodeCountMax;
    }

    public void setManyheadsTop10ContactRecent6monthPartnercodeCountMax(String manyheadsTop10ContactRecent6monthPartnercodeCountMax) {
        this.manyheadsTop10ContactRecent6monthPartnercodeCountMax = manyheadsTop10ContactRecent6monthPartnercodeCountMax;
    }

    public String getManyheadsTop10ContactRecent3monthPartnercodeCountMax() {
        return manyheadsTop10ContactRecent3monthPartnercodeCountMax;
    }

    public void setManyheadsTop10ContactRecent3monthPartnercodeCountMax(String manyheadsTop10ContactRecent3monthPartnercodeCountMax) {
        this.manyheadsTop10ContactRecent3monthPartnercodeCountMax = manyheadsTop10ContactRecent3monthPartnercodeCountMax;
    }

    public String getManyheadsTop10ContactRecent1monthPartnercodeCountMax() {
        return manyheadsTop10ContactRecent1monthPartnercodeCountMax;
    }

    public void setManyheadsTop10ContactRecent1monthPartnercodeCountMax(String manyheadsTop10ContactRecent1monthPartnercodeCountMax) {
        this.manyheadsTop10ContactRecent1monthPartnercodeCountMax = manyheadsTop10ContactRecent1monthPartnercodeCountMax;
    }

    public String getManyheadsTop10ContactRecent1monthHavePartnercodeCount() {
        return manyheadsTop10ContactRecent1monthHavePartnercodeCount;
    }

    public void setManyheadsTop10ContactRecent1monthHavePartnercodeCount(String manyheadsTop10ContactRecent1monthHavePartnercodeCount) {
        this.manyheadsTop10ContactRecent1monthHavePartnercodeCount = manyheadsTop10ContactRecent1monthHavePartnercodeCount;
    }

    public String getManyheadsTop10ContactRecent3monthPartnercodeCountAvg() {
        return manyheadsTop10ContactRecent3monthPartnercodeCountAvg;
    }

    public void setManyheadsTop10ContactRecent3monthPartnercodeCountAvg(String manyheadsTop10ContactRecent3monthPartnercodeCountAvg) {
        this.manyheadsTop10ContactRecent3monthPartnercodeCountAvg = manyheadsTop10ContactRecent3monthPartnercodeCountAvg;
    }

    public String getManyheadsTop10ContactRecent3monthHavePartnercodeCount() {
        return manyheadsTop10ContactRecent3monthHavePartnercodeCount;
    }

    public void setManyheadsTop10ContactRecent3monthHavePartnercodeCount(String manyheadsTop10ContactRecent3monthHavePartnercodeCount) {
        this.manyheadsTop10ContactRecent3monthHavePartnercodeCount = manyheadsTop10ContactRecent3monthHavePartnercodeCount;
    }

    public String getManyheadsTop10ContactRecent6monthHavePartnercodeCount() {
        return manyheadsTop10ContactRecent6monthHavePartnercodeCount;
    }

    public void setManyheadsTop10ContactRecent6monthHavePartnercodeCount(String manyheadsTop10ContactRecent6monthHavePartnercodeCount) {
        this.manyheadsTop10ContactRecent6monthHavePartnercodeCount = manyheadsTop10ContactRecent6monthHavePartnercodeCount;
    }

    public String getManyheadsTop10ContactRecent1monthPartnercodeCountOver2() {
        return manyheadsTop10ContactRecent1monthPartnercodeCountOver2;
    }

    public void setManyheadsTop10ContactRecent1monthPartnercodeCountOver2(String manyheadsTop10ContactRecent1monthPartnercodeCountOver2) {
        this.manyheadsTop10ContactRecent1monthPartnercodeCountOver2 = manyheadsTop10ContactRecent1monthPartnercodeCountOver2;
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
}