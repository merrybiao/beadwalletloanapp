package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * bw_mf_active_silence_stats
 * @author 
 */
@Table(name="bw_mf_active_silence_stats")
public class BwMfActiveSilenceStats implements Serializable {
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
     * 近3月通话B1043:C1109
     */
    @Column(name="active_day_1call_3month")
    private String activeDay1call3month;

    /**
     * 近6月通话活跃天数
     */
    @Column(name="active_day_1call_6month")
    private String activeDay1call6month;

    /**
     * 近3月最大连续通话活跃天数
     */
    @Column(name="max_continue_active_day_1call_3month")
    private String maxContinueActiveDay1call3month;

    /**
     * 近6月最大连续通话活跃天数
     */
    @Column(name="max_continue_active_day_1call_6month")
    private String maxContinueActiveDay1call6month;

    /**
     * 近3月无通话静默天数
     */
    @Column(name="silence_day_0call_3month")
    private String silenceDay0call3month;

    /**
     * 近3月无主叫通话静默天数
     */
    @Column(name="silence_day_0call_active_3month")
    private String silenceDay0callActive3month;

    /**
     * 近3月无通话和发送短信静默天数
     */
    @Column(name="silence_day_0call_0msg_send_3month")
    private String silenceDay0call0msgSend3month;

    /**
     * 近6月无通话静默天数
     */
    @Column(name="silence_day_0call_6month")
    private String silenceDay0call6month;

    /**
     * 近6月无主叫通话静默天数
     */
    @Column(name="silence_day_0call_active_6month")
    private String silenceDay0callActive6month;

    /**
     * 近6月无通话和发送短信静默天数
     */
    @Column(name="silence_day_0call_0msg_send_6month")
    private String silenceDay0call0msgSend6month;

    /**
     * 近3月连续无通话静默>=3天的次数
     */
    @Column(name="continue_silence_day_over3_0call_3month")
    private String continueSilenceDayOver30call3month;

    /**
     * 近3月连续无通话静默>=15天的次数
     */
    @Column(name="continue_silence_day_over15_0call_3month")
    private String continueSilenceDayOver150call3month;

    /**
     * 近3月连续无主叫通话静默>=3天的次数
     */
    @Column(name="continue_silence_day_over3_0call_active_3month")
    private String continueSilenceDayOver30callActive3month;

    /**
     * 近3月连续无主叫通话静默>=15天的次数
     */
    @Column(name="continue_silence_day_over15_0call_active_3month")
    private String continueSilenceDayOver150callActive3month;

    /**
     * 近3月连续无通话和发送短信静默>=3天的次数
     */
    @Column(name="continue_silence_day_over3_0call_0msg_send_3month")
    private String continueSilenceDayOver30call0msgSend3month;

    /**
     * 近3月连续无通话和发送短信静默>=15天的次数
     */
    @Column(name="continue_silence_day_over15_0call_0msg_send_3month")
    private String continueSilenceDayOver150call0msgSend3month;

    /**
     * 近6月连续无通话静默>=3天的次数
     */
    @Column(name="continue_silence_day_over3_0call_6month")
    private String continueSilenceDayOver30call6month;

    /**
     * 近6月连续无通话静默>=15天的次数
     */
    @Column(name="continue_silence_day_over15_0call_6month")
    private String continueSilenceDayOver150call6month;

    /**
     * 近6月连续无主叫通话静默>=3天的次数
     */
    @Column(name="continue_silence_day_over3_0call_active_6month")
    private String continueSilenceDayOver30callActive6month;

    /**
     * 近6月连续无主叫通话静默>=15天的次数
     */
    @Column(name="continue_silence_day_over15_0call_active_6month")
    private String continueSilenceDayOver150callActive6month;

    /**
     * 近6月连续无通话和发送短信静默>=3天的次数
     */
    @Column(name="continue_silence_day_over3_0call_0msg_send_6month")
    private String continueSilenceDayOver30call0msgSend6month;

    /**
     * 近6月连续无通话和发送短信静默>=15天的次数
     */
    @Column(name="continue_silence_day_over15_0call_0msg_send_6month")
    private String continueSilenceDayOver150call0msgSend6month;

    /**
     * 近3月最大连续无通话静默天数
     */
    @Column(name="max_continue_silence_day_0call_3month")
    private String maxContinueSilenceDay0call3month;

    /**
     * 近3月最大连续无主叫通话静默天数
     */
    @Column(name="max_continue_silence_day_0call_active_3month")
    private String maxContinueSilenceDay0callActive3month;

    /**
     * 近3月最大连续无通话和发送短信静默天数
     */
    @Column(name="max_continue_silence_day_0call_0msg_send_3month")
    private String maxContinueSilenceDay0call0msgSend3month;

    /**
     * 近6月最大连续无通话静默天数
     */
    @Column(name="max_continue_silence_day_0call_6month")
    private String maxContinueSilenceDay0call6month;

    /**
     * 近6月最大连续无主叫通话静默天数
     */
    @Column(name="max_continue_silence_day_0call_active_6month")
    private String maxContinueSilenceDay0callActive6month;

    /**
     * 近6月最大连续无通话和发送短信静默天数
     */
    @Column(name="max_continue_silence_day_0call_0msg_send_6month")
    private String maxContinueSilenceDay0call0msgSend6month;

    /**
     * 近6月最后一次无通话静默间隔天数
     */
    @Column(name="gap_day_last_silence_day_0call_6month")
    private String gapDayLastSilenceDay0call6month;

    /**
     * 近6月最后一次无主叫通话静默间隔天数
     */
    @Column(name="gap_day_last_silence_day_0call_active_6month")
    private String gapDayLastSilenceDay0callActive6month;

    /**
     * 近6月最后一次无通话和发送短信静默间隔天数
     */
    @Column(name="gap_day_last_silence_day_0call_0msg_send_6month")
    private String gapDayLastSilenceDay0call0msgSend6month;
    
    
    /**
     * 近3月连续无通话静默>=3天的记录
     */
    @Column(name="continue_silence_day_over3_0call_3month_detail")
    private String continueSilenceDayOver30call3monthDetail;

    /**
     * 近3月连续无通话静默>=15天的记录
     */
    @Column(name="continue_silence_day_over15_0call_3month_detail")
    private String continueSilenceDayOver150call3monthDetail;

    /**
     * 近3月连续无主叫通话静默>=3天的记录
     */
    @Column(name="continue_silence_day_over3_0call_active_3month_detail")
    private String continueSilenceDayOver30callActive3monthDetail;

    /**
     * 近3月连续无主叫通话静默>=15天的记录
     */
    @Column(name="continue_silence_day_over15_0call_active_3month_detail")
    private String continueSilenceDayOver150callActive3monthDetail;

    /**
     * 近3月连续无通话和发送短信静默>=3天的记录
     */
    @Column(name="continue_silence_day_over3_0call_0msg_send_3month_detail")
    private String continueSilenceDayOver30call0msgSend3monthDetail;

    /**
     * 近3月连续无通话和发送短信静默>=15天的记录
     */
    @Column(name="continue_silence_day_over15_0call_0msg_send_3month_detail")
    private String continueSilenceDayOver150call0msgSend3monthDetail;

    /**
     * 近6月连续无通话静默>=3天的记录
     */
    @Column(name="continue_silence_day_over3_0call_6month_detail")
    private String continueSilenceDayOver30call6monthDetail;

    /**
     * 近6月连续无通话静默>=15天的记录
     */
    @Column(name="continue_silence_day_over15_0call_6month_detail")
    private String continueSilenceDayOver150call6monthDetail;

    /**
     * 近6月连续无主叫通话静默>=3天的记录
     */
    @Column(name="continue_silence_day_over3_0call_active_6month_detail")
    private String continueSilenceDayOver30callActive6monthDetail;

    /**
     * 近6月连续无主叫通话静默>=15天的记录
     */
    @Column(name="continue_silence_day_over15_0call_active_6month_detail")
    private String continueSilenceDayOver150callActive6monthDetail;

    /**
     * 近6月连续无通话和发送短信静默>=3天的记录
     */
    @Column(name="continue_silence_day_over3_0call_0msg_send_6month_detail")
    private String continueSilenceDayOver30call0msgSend6monthDetail;

    /**
     * 近6月连续无通话和发送短信静默>=15天的记录
     */
    @Column(name="continue_silence_day_over15_0call_0msg_send_6month_detail")
    private String continueSilenceDayOver150call0msgSend6monthDetail;

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

    public String getActiveDay1call3month() {
        return activeDay1call3month;
    }

    public void setActiveDay1call3month(String activeDay1call3month) {
        this.activeDay1call3month = activeDay1call3month;
    }

    public String getActiveDay1call6month() {
        return activeDay1call6month;
    }

    public void setActiveDay1call6month(String activeDay1call6month) {
        this.activeDay1call6month = activeDay1call6month;
    }

    public String getMaxContinueActiveDay1call3month() {
        return maxContinueActiveDay1call3month;
    }

    public void setMaxContinueActiveDay1call3month(String maxContinueActiveDay1call3month) {
        this.maxContinueActiveDay1call3month = maxContinueActiveDay1call3month;
    }

    public String getMaxContinueActiveDay1call6month() {
        return maxContinueActiveDay1call6month;
    }

    public void setMaxContinueActiveDay1call6month(String maxContinueActiveDay1call6month) {
        this.maxContinueActiveDay1call6month = maxContinueActiveDay1call6month;
    }

    public String getSilenceDay0call3month() {
        return silenceDay0call3month;
    }

    public void setSilenceDay0call3month(String silenceDay0call3month) {
        this.silenceDay0call3month = silenceDay0call3month;
    }

    public String getSilenceDay0callActive3month() {
        return silenceDay0callActive3month;
    }

    public void setSilenceDay0callActive3month(String silenceDay0callActive3month) {
        this.silenceDay0callActive3month = silenceDay0callActive3month;
    }

    public String getSilenceDay0call0msgSend3month() {
        return silenceDay0call0msgSend3month;
    }

    public void setSilenceDay0call0msgSend3month(String silenceDay0call0msgSend3month) {
        this.silenceDay0call0msgSend3month = silenceDay0call0msgSend3month;
    }

    public String getSilenceDay0call6month() {
        return silenceDay0call6month;
    }

    public void setSilenceDay0call6month(String silenceDay0call6month) {
        this.silenceDay0call6month = silenceDay0call6month;
    }

    public String getSilenceDay0callActive6month() {
        return silenceDay0callActive6month;
    }

    public void setSilenceDay0callActive6month(String silenceDay0callActive6month) {
        this.silenceDay0callActive6month = silenceDay0callActive6month;
    }

    public String getSilenceDay0call0msgSend6month() {
        return silenceDay0call0msgSend6month;
    }

    public void setSilenceDay0call0msgSend6month(String silenceDay0call0msgSend6month) {
        this.silenceDay0call0msgSend6month = silenceDay0call0msgSend6month;
    }

    public String getContinueSilenceDayOver30call3month() {
        return continueSilenceDayOver30call3month;
    }

    public void setContinueSilenceDayOver30call3month(String continueSilenceDayOver30call3month) {
        this.continueSilenceDayOver30call3month = continueSilenceDayOver30call3month;
    }

    public String getContinueSilenceDayOver150call3month() {
        return continueSilenceDayOver150call3month;
    }

    public void setContinueSilenceDayOver150call3month(String continueSilenceDayOver150call3month) {
        this.continueSilenceDayOver150call3month = continueSilenceDayOver150call3month;
    }

    public String getContinueSilenceDayOver30callActive3month() {
        return continueSilenceDayOver30callActive3month;
    }

    public void setContinueSilenceDayOver30callActive3month(String continueSilenceDayOver30callActive3month) {
        this.continueSilenceDayOver30callActive3month = continueSilenceDayOver30callActive3month;
    }

    public String getContinueSilenceDayOver150callActive3month() {
        return continueSilenceDayOver150callActive3month;
    }

    public void setContinueSilenceDayOver150callActive3month(String continueSilenceDayOver150callActive3month) {
        this.continueSilenceDayOver150callActive3month = continueSilenceDayOver150callActive3month;
    }

    public String getContinueSilenceDayOver30call0msgSend3month() {
        return continueSilenceDayOver30call0msgSend3month;
    }

    public void setContinueSilenceDayOver30call0msgSend3month(String continueSilenceDayOver30call0msgSend3month) {
        this.continueSilenceDayOver30call0msgSend3month = continueSilenceDayOver30call0msgSend3month;
    }

    public String getContinueSilenceDayOver150call0msgSend3month() {
        return continueSilenceDayOver150call0msgSend3month;
    }

    public void setContinueSilenceDayOver150call0msgSend3month(String continueSilenceDayOver150call0msgSend3month) {
        this.continueSilenceDayOver150call0msgSend3month = continueSilenceDayOver150call0msgSend3month;
    }

    public String getContinueSilenceDayOver30call6month() {
        return continueSilenceDayOver30call6month;
    }

    public void setContinueSilenceDayOver30call6month(String continueSilenceDayOver30call6month) {
        this.continueSilenceDayOver30call6month = continueSilenceDayOver30call6month;
    }

    public String getContinueSilenceDayOver150call6month() {
        return continueSilenceDayOver150call6month;
    }

    public void setContinueSilenceDayOver150call6month(String continueSilenceDayOver150call6month) {
        this.continueSilenceDayOver150call6month = continueSilenceDayOver150call6month;
    }

    public String getContinueSilenceDayOver30callActive6month() {
        return continueSilenceDayOver30callActive6month;
    }

    public void setContinueSilenceDayOver30callActive6month(String continueSilenceDayOver30callActive6month) {
        this.continueSilenceDayOver30callActive6month = continueSilenceDayOver30callActive6month;
    }

    public String getContinueSilenceDayOver150callActive6month() {
        return continueSilenceDayOver150callActive6month;
    }

    public void setContinueSilenceDayOver150callActive6month(String continueSilenceDayOver150callActive6month) {
        this.continueSilenceDayOver150callActive6month = continueSilenceDayOver150callActive6month;
    }

    public String getContinueSilenceDayOver30call0msgSend6month() {
        return continueSilenceDayOver30call0msgSend6month;
    }

    public void setContinueSilenceDayOver30call0msgSend6month(String continueSilenceDayOver30call0msgSend6month) {
        this.continueSilenceDayOver30call0msgSend6month = continueSilenceDayOver30call0msgSend6month;
    }

    public String getContinueSilenceDayOver150call0msgSend6month() {
        return continueSilenceDayOver150call0msgSend6month;
    }

    public void setContinueSilenceDayOver150call0msgSend6month(String continueSilenceDayOver150call0msgSend6month) {
        this.continueSilenceDayOver150call0msgSend6month = continueSilenceDayOver150call0msgSend6month;
    }

    public String getMaxContinueSilenceDay0call3month() {
        return maxContinueSilenceDay0call3month;
    }

    public void setMaxContinueSilenceDay0call3month(String maxContinueSilenceDay0call3month) {
        this.maxContinueSilenceDay0call3month = maxContinueSilenceDay0call3month;
    }

    public String getMaxContinueSilenceDay0callActive3month() {
        return maxContinueSilenceDay0callActive3month;
    }

    public void setMaxContinueSilenceDay0callActive3month(String maxContinueSilenceDay0callActive3month) {
        this.maxContinueSilenceDay0callActive3month = maxContinueSilenceDay0callActive3month;
    }

    public String getMaxContinueSilenceDay0call0msgSend3month() {
        return maxContinueSilenceDay0call0msgSend3month;
    }

    public void setMaxContinueSilenceDay0call0msgSend3month(String maxContinueSilenceDay0call0msgSend3month) {
        this.maxContinueSilenceDay0call0msgSend3month = maxContinueSilenceDay0call0msgSend3month;
    }

    public String getMaxContinueSilenceDay0call6month() {
        return maxContinueSilenceDay0call6month;
    }

    public void setMaxContinueSilenceDay0call6month(String maxContinueSilenceDay0call6month) {
        this.maxContinueSilenceDay0call6month = maxContinueSilenceDay0call6month;
    }

    public String getMaxContinueSilenceDay0callActive6month() {
        return maxContinueSilenceDay0callActive6month;
    }

    public void setMaxContinueSilenceDay0callActive6month(String maxContinueSilenceDay0callActive6month) {
        this.maxContinueSilenceDay0callActive6month = maxContinueSilenceDay0callActive6month;
    }

    public String getMaxContinueSilenceDay0call0msgSend6month() {
        return maxContinueSilenceDay0call0msgSend6month;
    }

    public void setMaxContinueSilenceDay0call0msgSend6month(String maxContinueSilenceDay0call0msgSend6month) {
        this.maxContinueSilenceDay0call0msgSend6month = maxContinueSilenceDay0call0msgSend6month;
    }

    public String getGapDayLastSilenceDay0call6month() {
        return gapDayLastSilenceDay0call6month;
    }

    public void setGapDayLastSilenceDay0call6month(String gapDayLastSilenceDay0call6month) {
        this.gapDayLastSilenceDay0call6month = gapDayLastSilenceDay0call6month;
    }

    public String getGapDayLastSilenceDay0callActive6month() {
        return gapDayLastSilenceDay0callActive6month;
    }

    public void setGapDayLastSilenceDay0callActive6month(String gapDayLastSilenceDay0callActive6month) {
        this.gapDayLastSilenceDay0callActive6month = gapDayLastSilenceDay0callActive6month;
    }

    public String getGapDayLastSilenceDay0call0msgSend6month() {
        return gapDayLastSilenceDay0call0msgSend6month;
    }

    public void setGapDayLastSilenceDay0call0msgSend6month(String gapDayLastSilenceDay0call0msgSend6month) {
        this.gapDayLastSilenceDay0call0msgSend6month = gapDayLastSilenceDay0call0msgSend6month;
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

	public String getContinueSilenceDayOver30call3monthDetail() {
		return continueSilenceDayOver30call3monthDetail;
	}

	public void setContinueSilenceDayOver30call3monthDetail(String continueSilenceDayOver30call3monthDetail) {
		this.continueSilenceDayOver30call3monthDetail = continueSilenceDayOver30call3monthDetail;
	}

	public String getContinueSilenceDayOver150call3monthDetail() {
		return continueSilenceDayOver150call3monthDetail;
	}

	public void setContinueSilenceDayOver150call3monthDetail(String continueSilenceDayOver150call3monthDetail) {
		this.continueSilenceDayOver150call3monthDetail = continueSilenceDayOver150call3monthDetail;
	}

	public String getContinueSilenceDayOver30callActive3monthDetail() {
		return continueSilenceDayOver30callActive3monthDetail;
	}

	public void setContinueSilenceDayOver30callActive3monthDetail(String continueSilenceDayOver30callActive3monthDetail) {
		this.continueSilenceDayOver30callActive3monthDetail = continueSilenceDayOver30callActive3monthDetail;
	}

	public String getContinueSilenceDayOver150callActive3monthDetail() {
		return continueSilenceDayOver150callActive3monthDetail;
	}

	public void setContinueSilenceDayOver150callActive3monthDetail(String continueSilenceDayOver150callActive3monthDetail) {
		this.continueSilenceDayOver150callActive3monthDetail = continueSilenceDayOver150callActive3monthDetail;
	}

	public String getContinueSilenceDayOver30call0msgSend3monthDetail() {
		return continueSilenceDayOver30call0msgSend3monthDetail;
	}

	public void setContinueSilenceDayOver30call0msgSend3monthDetail(
			String continueSilenceDayOver30call0msgSend3monthDetail) {
		this.continueSilenceDayOver30call0msgSend3monthDetail = continueSilenceDayOver30call0msgSend3monthDetail;
	}

	public String getContinueSilenceDayOver150call0msgSend3monthDetail() {
		return continueSilenceDayOver150call0msgSend3monthDetail;
	}

	public void setContinueSilenceDayOver150call0msgSend3monthDetail(
			String continueSilenceDayOver150call0msgSend3monthDetail) {
		this.continueSilenceDayOver150call0msgSend3monthDetail = continueSilenceDayOver150call0msgSend3monthDetail;
	}

	public String getContinueSilenceDayOver30call6monthDetail() {
		return continueSilenceDayOver30call6monthDetail;
	}

	public void setContinueSilenceDayOver30call6monthDetail(String continueSilenceDayOver30call6monthDetail) {
		this.continueSilenceDayOver30call6monthDetail = continueSilenceDayOver30call6monthDetail;
	}

	public String getContinueSilenceDayOver150call6monthDetail() {
		return continueSilenceDayOver150call6monthDetail;
	}

	public void setContinueSilenceDayOver150call6monthDetail(String continueSilenceDayOver150call6monthDetail) {
		this.continueSilenceDayOver150call6monthDetail = continueSilenceDayOver150call6monthDetail;
	}

	public String getContinueSilenceDayOver30callActive6monthDetail() {
		return continueSilenceDayOver30callActive6monthDetail;
	}

	public void setContinueSilenceDayOver30callActive6monthDetail(String continueSilenceDayOver30callActive6monthDetail) {
		this.continueSilenceDayOver30callActive6monthDetail = continueSilenceDayOver30callActive6monthDetail;
	}

	public String getContinueSilenceDayOver150callActive6monthDetail() {
		return continueSilenceDayOver150callActive6monthDetail;
	}

	public void setContinueSilenceDayOver150callActive6monthDetail(String continueSilenceDayOver150callActive6monthDetail) {
		this.continueSilenceDayOver150callActive6monthDetail = continueSilenceDayOver150callActive6monthDetail;
	}

	public String getContinueSilenceDayOver30call0msgSend6monthDetail() {
		return continueSilenceDayOver30call0msgSend6monthDetail;
	}

	public void setContinueSilenceDayOver30call0msgSend6monthDetail(
			String continueSilenceDayOver30call0msgSend6monthDetail) {
		this.continueSilenceDayOver30call0msgSend6monthDetail = continueSilenceDayOver30call0msgSend6monthDetail;
	}

	public String getContinueSilenceDayOver150call0msgSend6monthDetail() {
		return continueSilenceDayOver150call0msgSend6monthDetail;
	}

	public void setContinueSilenceDayOver150call0msgSend6monthDetail(
			String continueSilenceDayOver150call0msgSend6monthDetail) {
		this.continueSilenceDayOver150call0msgSend6monthDetail = continueSilenceDayOver150call0msgSend6monthDetail;
	}
   
}