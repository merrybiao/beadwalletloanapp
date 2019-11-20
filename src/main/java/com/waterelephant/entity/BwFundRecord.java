package com.waterelephant.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_fund_record")
public class BwFundRecord implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 编号
    private String endDate;// 汇缴结束日期
    private String inMonthRmb;
    private String payDate;// 交易日期
    private String perRmb;// 个人缴费
    private String monthRmb;// 变动金额/提取额/增加额
    private String idCard;// 身份证号
    private Long fundInfoId;// 公积金信息id
    private String balanceRmb;// 余额
    private String baseRmb;// 缴费基数
    private Integer flowType;// 险种：6=公积金
    private String comRmb;// 单位缴费
    private String outMonthRmb;
    private String payType;// 缴费类型
    private Long orderId;// 工单id
    private String startDate;// 汇缴起始日期
    private String comName;// 缴费单位

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getInMonthRmb() {
        return this.inMonthRmb;
    }

    public void setInMonthRmb(String inMonthRmb) {
        this.inMonthRmb = inMonthRmb;
    }

    public String getPayDate() {
        return this.payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPerRmb() {
        return this.perRmb;
    }

    public void setPerRmb(String perRmb) {
        this.perRmb = perRmb;
    }

    public String getMonthRmb() {
        return this.monthRmb;
    }

    public void setMonthRmb(String monthRmb) {
        this.monthRmb = monthRmb;
    }

    public String getIdCard() {
        return this.idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Long getFundInfoId() {
        return this.fundInfoId;
    }

    public void setFundInfoId(Long fundInfoId) {
        this.fundInfoId = fundInfoId;
    }

    public String getBalanceRmb() {
        return this.balanceRmb;
    }

    public void setBalanceRmb(String balanceRmb) {
        this.balanceRmb = balanceRmb;
    }

    public String getBaseRmb() {
        return this.baseRmb;
    }

    public void setBaseRmb(String baseRmb) {
        this.baseRmb = baseRmb;
    }

    public Integer getFlowType() {
        return this.flowType;
    }

    public void setFlowType(Integer flowType) {
        this.flowType = flowType;
    }

    public String getComRmb() {
        return this.comRmb;
    }

    public void setComRmb(String comRmb) {
        this.comRmb = comRmb;
    }

    public String getOutMonthRmb() {
        return this.outMonthRmb;
    }

    public void setOutMonthRmb(String outMonthRmb) {
        this.outMonthRmb = outMonthRmb;
    }

    public String getPayType() {
        return this.payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getComName() {
        return this.comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

}
