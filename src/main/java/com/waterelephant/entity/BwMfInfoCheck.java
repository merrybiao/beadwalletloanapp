package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_info_check
 * @author 
 */
@Table(name = "bw_mf_info_check")
public class BwMfInfoCheck implements Serializable {
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
     * 近6个月内是否和紧急联系人4通话
     */
    @Column(name="is_contact4_called_6month")
    private String isContact4Called6month;

    /**
     * 近3个月常用通话地是否和号码归属地一致
     */
    @Column(name="is_net_addr_call_addr_3month")
    private String isNetAddrCallAddr3month;

    /**
     * 近6个月内是否和紧急联系人2通话
     */
    @Column(name="is_contact2_called_6month")
    private String isContact2Called6month;

    /**
     * 近6个月内是否和工作电话通话
     */
    @Column(name="is_work_tel_called_6month")
    private String isWorkTelCalled6month;

    /**
     * 近6个月内是否和紧急联系人3通话
     */
    @Column(name="is_contact3_called_6month")
    private String isContact3Called6month;

    /**
     * 入网时长是否大于3个月
     */
    @Column(name="is_net_age_over_3month")
    private String isNetAgeOver3month;

    /**
     * 手机号账户状态是否正常
     */
    @Column(name="is_mobile_status_active")
    private String isMobileStatusActive;

    /**
     * 近6个月内是否和紧急联系人1通话
     */
    @Column(name="is_contact1_called_6month")
    private String isContact1Called6month;

    /**
     * 近6个月内是否和家庭电话通话
     */
    @Column(name="is_home_tel_called_6month")
    private String isHomeTelCalled6month;

    /**
     * 近6个月内是否和紧急联系人5通话
     */
    @Column(name="is_contact5_called_6month")
    private String isContact5Called6month;

    /**
     * 近1个月常用通话地是否和号码归属地一致
     */
    @Column(name="is_net_addr_call_addr_1month")
    private String isNetAddrCallAddr1month;

    /**
     * 身份证号码是否有效
     */
    private String isIdentityCodeValid;

    /**
     * 运营商是否实名认证
     */
    private String isIdentityCodeReliable;

    /**
     * 近6个月常用通话地是否和号码归属地一致
     */
    @Column(name="is_net_addr_call_addr_6month")
    private String isNetAddrCallAddr6month;

    /**
     * 近1个月是否被催收号码呼叫
     */
    @Column(name="is_called_by_collection_1month")
    private String isCalledByCollection1month;

    /**
     * 近3个月是否被催收号码呼叫
     */
    @Column(name="is_called_by_collection_3month")
    private String isCalledByCollection3month;

    /**
     * 近6个月是否被催收号码呼叫
     */
    @Column(name="is_called_by_collection_6month")
    private String isCalledByCollection6month;

    /**
     * 前10联系人是否有命中黑名单
     */
    @Column(name="is_top10_contact_in_blacklist")
    private String isTop10ContactInBlacklist;

    /**
     * 前10联系人近1月是否申请过贷款平台
     */
    @Column(name="is_top10_contact_in_manyheads_1month")
    private String isTop10ContactInManyheads1month;

    /**
     * 前10联系人近3月是否申请过贷款平台
     */
    @Column(name="is_top10_contact_in_manyheads_3month")
    private String isTop10ContactInManyheads3month;

    /**
     * 前10联系人近6月是否申请过贷款平台
     */
    @Column(name="is_top10_contact_in_manyheads_6month")
    private String isTop10ContactInManyheads6month;

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

    public String getIsContact4Called6month() {
        return isContact4Called6month;
    }

    public void setIsContact4Called6month(String isContact4Called6month) {
        this.isContact4Called6month = isContact4Called6month;
    }

    public String getIsNetAddrCallAddr3month() {
        return isNetAddrCallAddr3month;
    }

    public void setIsNetAddrCallAddr3month(String isNetAddrCallAddr3month) {
        this.isNetAddrCallAddr3month = isNetAddrCallAddr3month;
    }

    public String getIsContact2Called6month() {
        return isContact2Called6month;
    }

    public void setIsContact2Called6month(String isContact2Called6month) {
        this.isContact2Called6month = isContact2Called6month;
    }

    public String getIsWorkTelCalled6month() {
        return isWorkTelCalled6month;
    }

    public void setIsWorkTelCalled6month(String isWorkTelCalled6month) {
        this.isWorkTelCalled6month = isWorkTelCalled6month;
    }

    public String getIsContact3Called6month() {
        return isContact3Called6month;
    }

    public void setIsContact3Called6month(String isContact3Called6month) {
        this.isContact3Called6month = isContact3Called6month;
    }

    public String getIsNetAgeOver3month() {
        return isNetAgeOver3month;
    }

    public void setIsNetAgeOver3month(String isNetAgeOver3month) {
        this.isNetAgeOver3month = isNetAgeOver3month;
    }

    public String getIsMobileStatusActive() {
        return isMobileStatusActive;
    }

    public void setIsMobileStatusActive(String isMobileStatusActive) {
        this.isMobileStatusActive = isMobileStatusActive;
    }

    public String getIsContact1Called6month() {
        return isContact1Called6month;
    }

    public void setIsContact1Called6month(String isContact1Called6month) {
        this.isContact1Called6month = isContact1Called6month;
    }

    public String getIsHomeTelCalled6month() {
        return isHomeTelCalled6month;
    }

    public void setIsHomeTelCalled6month(String isHomeTelCalled6month) {
        this.isHomeTelCalled6month = isHomeTelCalled6month;
    }

    public String getIsContact5Called6month() {
        return isContact5Called6month;
    }

    public void setIsContact5Called6month(String isContact5Called6month) {
        this.isContact5Called6month = isContact5Called6month;
    }

    public String getIsNetAddrCallAddr1month() {
        return isNetAddrCallAddr1month;
    }

    public void setIsNetAddrCallAddr1month(String isNetAddrCallAddr1month) {
        this.isNetAddrCallAddr1month = isNetAddrCallAddr1month;
    }

    public String getIsIdentityCodeValid() {
        return isIdentityCodeValid;
    }

    public void setIsIdentityCodeValid(String isIdentityCodeValid) {
        this.isIdentityCodeValid = isIdentityCodeValid;
    }

    public String getIsIdentityCodeReliable() {
        return isIdentityCodeReliable;
    }

    public void setIsIdentityCodeReliable(String isIdentityCodeReliable) {
        this.isIdentityCodeReliable = isIdentityCodeReliable;
    }

    public String getIsNetAddrCallAddr6month() {
        return isNetAddrCallAddr6month;
    }

    public void setIsNetAddrCallAddr6month(String isNetAddrCallAddr6month) {
        this.isNetAddrCallAddr6month = isNetAddrCallAddr6month;
    }

    public String getIsCalledByCollection1month() {
        return isCalledByCollection1month;
    }

    public void setIsCalledByCollection1month(String isCalledByCollection1month) {
        this.isCalledByCollection1month = isCalledByCollection1month;
    }

    public String getIsCalledByCollection3month() {
        return isCalledByCollection3month;
    }

    public void setIsCalledByCollection3month(String isCalledByCollection3month) {
        this.isCalledByCollection3month = isCalledByCollection3month;
    }

    public String getIsCalledByCollection6month() {
        return isCalledByCollection6month;
    }

    public void setIsCalledByCollection6month(String isCalledByCollection6month) {
        this.isCalledByCollection6month = isCalledByCollection6month;
    }

    public String getIsTop10ContactInBlacklist() {
        return isTop10ContactInBlacklist;
    }

    public void setIsTop10ContactInBlacklist(String isTop10ContactInBlacklist) {
        this.isTop10ContactInBlacklist = isTop10ContactInBlacklist;
    }

    public String getIsTop10ContactInManyheads1month() {
        return isTop10ContactInManyheads1month;
    }

    public void setIsTop10ContactInManyheads1month(String isTop10ContactInManyheads1month) {
        this.isTop10ContactInManyheads1month = isTop10ContactInManyheads1month;
    }

    public String getIsTop10ContactInManyheads3month() {
        return isTop10ContactInManyheads3month;
    }

    public void setIsTop10ContactInManyheads3month(String isTop10ContactInManyheads3month) {
        this.isTop10ContactInManyheads3month = isTop10ContactInManyheads3month;
    }

    public String getIsTop10ContactInManyheads6month() {
        return isTop10ContactInManyheads6month;
    }

    public void setIsTop10ContactInManyheads6month(String isTop10ContactInManyheads6month) {
        this.isTop10ContactInManyheads6month = isTop10ContactInManyheads6month;
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