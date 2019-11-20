package com.waterelephant.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @ClassName: BwInsureRecord
 * @Description: TODO(社保记录实体类)
 * @author SongYajun
 * @date 2016年8月30日 下午5:17:40
 *
 */
@Table(name = "bw_insure_record")
public class BwInsureRecord implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 编号
    private String idCard; // 身份证号
    private String payDate; // 交易日期
    private String startDate; // 汇缴日期
    private String endDate; // 汇缴结束日期
    private String baseRmb; // 缴费基数
    private String comRmb; // 单位缴费
    private String perRmb; // 个人缴费
    private String balanceRmb; // 余额
    private String monthRmb; // 月交易额
    private String comName; // 缴费公司名称
    private String payType; // 缴费类型或者交易摘要
    private Integer flowType; // 缴费险种
    private Long insureInfoId; // 社保信息id
    private Long orderId; // 借款人信息id
    // 字段注释原因：数据库表中不存在该字段（code0084）
    // private String conMonth; // 本单位社保连续缴纳月数
    // private String totalConMonth; // 社保连续缴纳月数

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBaseRmb() {
        return baseRmb;
    }

    public void setBaseRmb(String baseRmb) {
        this.baseRmb = baseRmb;
    }

    public String getComRmb() {
        return comRmb;
    }

    public void setComRmb(String comRmb) {
        this.comRmb = comRmb;
    }

    public String getPerRmb() {
        return perRmb;
    }

    public void setPerRmb(String perRmb) {
        this.perRmb = perRmb;
    }

    public String getBalanceRmb() {
        return balanceRmb;
    }

    public void setBalanceRmb(String balanceRmb) {
        this.balanceRmb = balanceRmb;
    }

    public String getMonthRmb() {
        return monthRmb;
    }

    public void setMonthRmb(String monthRmb) {
        this.monthRmb = monthRmb;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getFlowType() {
        return flowType;
    }

    public void setFlowType(Integer flowType) {
        this.flowType = flowType;
    }

    public Long getInsureInfoId() {
        return insureInfoId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setInsureInfoId(Long insureInfoId) {
        this.insureInfoId = insureInfoId;
    }

}
