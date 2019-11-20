package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_behavior_analysis
 * @author 
 */
@Table(name = "bw_mf_behavior_analysis")
public class BwMfBehaviorAnalysis implements Serializable {
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
     * 近6个月紧急联系人3通话情况
     */
    @Column(name="emergency_contact3_analysis_6month")
    private String emergencyContact3Analysis6month;

    /**
     * 近6个月深夜活动情况
     */
    @Column(name="late_night_analysis_6month")
    private String lateNightAnalysis6month;

    /**
     * 近6个月120通话情况
     */
    @Column(name="call_120_analysis_6month")
    private String call120Analysis6month;

    /**
     * 近6个月紧急联系人4通话情况
     */
    @Column(name="emergency_contact4_analysis_6month")
    private String emergencyContact4Analysis6month;

    /**
     * 近6个月律师号码通话情况
     */
    @Column(name="call_lawyer_analysis_6month")
    private String callLawyerAnalysis6month;

    /**
     * 近6个月紧急联系人2通话情况
     */
    @Column(name="emergency_contact2_analysis_6month")
    private String emergencyContact2Analysis6month;

    /**
     * 近6个月互通号码数量
     */
    @Column(name="mutual_number_analysis_6month")
    private String mutualNumberAnalysis6month;

    /**
     * 近6个月小贷号码联系情况
     */
    @Column(name="loan_contact_analysis_6month")
    private String loanContactAnalysis6month;

    /**
     * 近6个月紧急联系人5通话情况
     */
    @Column(name="emergency_contact5_analysis_6month")
    private String emergencyContact5Analysis6month;

    /**
     * 近6个月紧急联系人1通话情况
     */
    @Column(name="emergency_contact1_analysis_6month")
    private String emergencyContact1Analysis6month;

    /**
     * 近6个月催收号码联系情况
     */
    @Column(name="collection_contact_analysis_6month")
    private String collectionContactAnalysis6month;

    /**
     * 近6个月110通话情况
     */
    @Column(name="call_110_analysis_6month")
    private String call110Analysis6month;

    /**
     * 号码入网时间
     */
    @Column(name="mobile_net_age_analysis")
    private String mobileNetAgeAnalysis;

    /**
     * 近6个月手机静默情况
     */
    @Column(name="mobile_silence_analysis_6month")
    private String mobileSilenceAnalysis6month;

    /**
     * 近6个月法院号码通话情况
     */
    @Column(name="call_court_analysis_6month")
    private String callCourtAnalysis6month;

    /**
     * 近6个月澳门电话通话情况
     */
    @Column(name="call_macau_analysis_6month")
    private String callMacauAnalysis6month;

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

    public String getEmergencyContact3Analysis6month() {
        return emergencyContact3Analysis6month;
    }

    public void setEmergencyContact3Analysis6month(String emergencyContact3Analysis6month) {
        this.emergencyContact3Analysis6month = emergencyContact3Analysis6month;
    }

    public String getLateNightAnalysis6month() {
        return lateNightAnalysis6month;
    }

    public void setLateNightAnalysis6month(String lateNightAnalysis6month) {
        this.lateNightAnalysis6month = lateNightAnalysis6month;
    }

    public String getCall120Analysis6month() {
        return call120Analysis6month;
    }

    public void setCall120Analysis6month(String call120Analysis6month) {
        this.call120Analysis6month = call120Analysis6month;
    }

    public String getEmergencyContact4Analysis6month() {
        return emergencyContact4Analysis6month;
    }

    public void setEmergencyContact4Analysis6month(String emergencyContact4Analysis6month) {
        this.emergencyContact4Analysis6month = emergencyContact4Analysis6month;
    }

    public String getCallLawyerAnalysis6month() {
        return callLawyerAnalysis6month;
    }

    public void setCallLawyerAnalysis6month(String callLawyerAnalysis6month) {
        this.callLawyerAnalysis6month = callLawyerAnalysis6month;
    }

    public String getEmergencyContact2Analysis6month() {
        return emergencyContact2Analysis6month;
    }

    public void setEmergencyContact2Analysis6month(String emergencyContact2Analysis6month) {
        this.emergencyContact2Analysis6month = emergencyContact2Analysis6month;
    }

    public String getMutualNumberAnalysis6month() {
        return mutualNumberAnalysis6month;
    }

    public void setMutualNumberAnalysis6month(String mutualNumberAnalysis6month) {
        this.mutualNumberAnalysis6month = mutualNumberAnalysis6month;
    }

    public String getLoanContactAnalysis6month() {
        return loanContactAnalysis6month;
    }

    public void setLoanContactAnalysis6month(String loanContactAnalysis6month) {
        this.loanContactAnalysis6month = loanContactAnalysis6month;
    }

    public String getEmergencyContact5Analysis6month() {
        return emergencyContact5Analysis6month;
    }

    public void setEmergencyContact5Analysis6month(String emergencyContact5Analysis6month) {
        this.emergencyContact5Analysis6month = emergencyContact5Analysis6month;
    }

    public String getEmergencyContact1Analysis6month() {
        return emergencyContact1Analysis6month;
    }

    public void setEmergencyContact1Analysis6month(String emergencyContact1Analysis6month) {
        this.emergencyContact1Analysis6month = emergencyContact1Analysis6month;
    }

    public String getCollectionContactAnalysis6month() {
        return collectionContactAnalysis6month;
    }

    public void setCollectionContactAnalysis6month(String collectionContactAnalysis6month) {
        this.collectionContactAnalysis6month = collectionContactAnalysis6month;
    }

    public String getCall110Analysis6month() {
        return call110Analysis6month;
    }

    public void setCall110Analysis6month(String call110Analysis6month) {
        this.call110Analysis6month = call110Analysis6month;
    }

    public String getMobileNetAgeAnalysis() {
        return mobileNetAgeAnalysis;
    }

    public void setMobileNetAgeAnalysis(String mobileNetAgeAnalysis) {
        this.mobileNetAgeAnalysis = mobileNetAgeAnalysis;
    }

    public String getMobileSilenceAnalysis6month() {
        return mobileSilenceAnalysis6month;
    }

    public void setMobileSilenceAnalysis6month(String mobileSilenceAnalysis6month) {
        this.mobileSilenceAnalysis6month = mobileSilenceAnalysis6month;
    }

    public String getCallCourtAnalysis6month() {
        return callCourtAnalysis6month;
    }

    public void setCallCourtAnalysis6month(String callCourtAnalysis6month) {
        this.callCourtAnalysis6month = callCourtAnalysis6month;
    }

    public String getCallMacauAnalysis6month() {
        return callMacauAnalysis6month;
    }

    public void setCallMacauAnalysis6month(String callMacauAnalysis6month) {
        this.callMacauAnalysis6month = callMacauAnalysis6month;
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