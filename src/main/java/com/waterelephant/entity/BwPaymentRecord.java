package com.waterelephant.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 付款记录明细，还款和分批记录多对一，分批记录拆分的记录
 */
@Table(name = "bw_payment_record")
public class BwPaymentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 工单ID
     */
    private Long orderId;

    /**
     * 还款计划ID
     */
    private Long repayId;

    /**
     * 平台交易记录ID
     */
    private Long platformRecordId;

    /**
     * 分批还款记录ID
     */
    private Long repaymentBatchId;

    /**
     * 催收员ID
     */
    private Long userId;

    /**
     * 结清还款计划催收人ID
     */
    private Long settleUserId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 结清还款计划部门ID
     */
    private Long settleDeptId;

    /**
     * 状态(1.有效，0.失效)
     */
    private Integer state;

    /**
     * 还款类型：1正常还款 2 逾期还款 3 提前还款
     */
    private Integer repayType;

    /**
     * 还款状态：1 未还款 2 已还款 3 已垫付 4 展期
     */
    private Integer repayStatus;

    /**
     * 工单状态id
     */
    private Integer statusId;

    /**
     * 还款后已还款金额
     */
    private Double alreadyRepayMoney;

    /**
     * 逾期天数
     */
    private Integer overdueDay;

    /**
     * 逾期金额
     */
    private Double overdueMoney;

    /**
     * 免罚息金额
     */
    private Double advance;

    /**
     * 交易金额
     */
    private Double tradeAmount;

    /**
     * 还款本金
     */
    private Double repayCorpusMoney;

    /**
     * 还款利息
     */
    private Double repayAccrualMoney;

    /**
     * 手续费（展期补尾款）
     */
    private Double repayRetainageMoney;

    /**
     * 还款湛江委金额
     */
    private Double repayZjwMoney;

    /**
     * 还款罚息金额
     */
    private Double repayOverdueMoney;

    /**
     * 多还金额
     */
    private Double repayExtraMoney;

    /**
     * 支付类型，1.还款 2.展期
     */
    private Integer payType;

    /**
     * 支付渠道。1.宝付 , 2.连连，5.支付宝，6.微信，7.口袋，9.易宝，10.合利宝，11.快捷通
     */
    private Integer payChannel;

    /**
     * 终端类型：0系统后台 1Android 2ios 3WAP 4外部渠道 5定时器
     */
    private Integer terminalType;

    /**
     * 支付方式，1.主动支付，2.贷后代扣，3.自动代扣，4.对公转账
     */
    private Integer payWay;
    private Date createTime;

    /**
     * 交易时间，实际还款时间
     */
    private Date tradeTime;
    private Date updateTime;

    public BwPaymentRecord() {}

    public BwPaymentRecord(Long orderId, Long repayId, Long platformRecordId, Long repaymentBatchId, Date createTime, Date tradeTime) {
        this.orderId = orderId;
        this.repayId = repayId;
        this.platformRecordId = platformRecordId;
        this.repaymentBatchId = repaymentBatchId;
        this.createTime = createTime;
        this.tradeTime = tradeTime;
    }

    public Long getId() {
        return id;
    }

    public BwPaymentRecord setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getOrderId() {
        return orderId;
    }

    public BwPaymentRecord setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public Long getRepayId() {
        return repayId;
    }

    public BwPaymentRecord setRepayId(Long repayId) {
        this.repayId = repayId;
        return this;
    }

    public Long getPlatformRecordId() {
        return platformRecordId;
    }

    public BwPaymentRecord setPlatformRecordId(Long platformRecordId) {
        this.platformRecordId = platformRecordId;
        return this;
    }

    public Long getRepaymentBatchId() {
        return repaymentBatchId;
    }

    public BwPaymentRecord setRepaymentBatchId(Long repaymentBatchId) {
        this.repaymentBatchId = repaymentBatchId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public BwPaymentRecord setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getSettleUserId() {
        return settleUserId;
    }

    public BwPaymentRecord setSettleUserId(Long settleUserId) {
        this.settleUserId = settleUserId;
        return this;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getSettleDeptId() {
        return settleDeptId;
    }

    public BwPaymentRecord setSettleDeptId(Long settleDeptId) {
        this.settleDeptId = settleDeptId;
        return this;
    }

    public Integer getState() {
        return state;
    }

    public BwPaymentRecord setState(Integer state) {
        this.state = state;
        return this;
    }

    public Integer getRepayType() {
        return repayType;
    }

    public BwPaymentRecord setRepayType(Integer repayType) {
        this.repayType = repayType;
        return this;
    }

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public BwPaymentRecord setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
        return this;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public BwPaymentRecord setStatusId(Integer statusId) {
        this.statusId = statusId;
        return this;
    }

    public Double getAlreadyRepayMoney() {
        return alreadyRepayMoney;
    }

    public BwPaymentRecord setAlreadyRepayMoney(Double alreadyRepayMoney) {
        this.alreadyRepayMoney = alreadyRepayMoney;
        return this;
    }

    public Integer getOverdueDay() {
        return overdueDay;
    }

    public BwPaymentRecord setOverdueDay(Integer overdueDay) {
        this.overdueDay = overdueDay;
        return this;
    }

    public Double getOverdueMoney() {
        return overdueMoney;
    }

    public BwPaymentRecord setOverdueMoney(Double overdueMoney) {
        this.overdueMoney = overdueMoney;
        return this;
    }

    public Double getAdvance() {
        return advance;
    }

    public BwPaymentRecord setAdvance(Double advance) {
        this.advance = advance;
        return this;
    }

    public Double getTradeAmount() {
        return tradeAmount;
    }

    public BwPaymentRecord setTradeAmount(Double tradeAmount) {
        this.tradeAmount = tradeAmount;
        return this;
    }

    public Double getRepayCorpusMoney() {
        return repayCorpusMoney;
    }

    public BwPaymentRecord setRepayCorpusMoney(Double repayCorpusMoney) {
        this.repayCorpusMoney = repayCorpusMoney;
        return this;
    }

    public Double getRepayAccrualMoney() {
        return repayAccrualMoney;
    }

    public BwPaymentRecord setRepayAccrualMoney(Double repayAccrualMoney) {
        this.repayAccrualMoney = repayAccrualMoney;
        return this;
    }

    public Double getRepayRetainageMoney() {
        return repayRetainageMoney;
    }

    public BwPaymentRecord setRepayRetainageMoney(Double repayRetainageMoney) {
        this.repayRetainageMoney = repayRetainageMoney;
        return this;
    }

    public Double getRepayZjwMoney() {
        return repayZjwMoney;
    }

    public BwPaymentRecord setRepayZjwMoney(Double repayZjwMoney) {
        this.repayZjwMoney = repayZjwMoney;
        return this;
    }

    public Double getRepayOverdueMoney() {
        return repayOverdueMoney;
    }

    public BwPaymentRecord setRepayOverdueMoney(Double repayOverdueMoney) {
        this.repayOverdueMoney = repayOverdueMoney;
        return this;
    }

    public Double getRepayExtraMoney() {
        return repayExtraMoney;
    }

    public void setRepayExtraMoney(Double repayExtraMoney) {
        this.repayExtraMoney = repayExtraMoney;
    }

    public Integer getPayType() {
        return payType;
    }

    public BwPaymentRecord setPayType(Integer payType) {
        this.payType = payType;
        return this;
    }

    public Integer getPayChannel() {
        return payChannel;
    }

    public BwPaymentRecord setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
        return this;
    }

    public Integer getTerminalType() {
        return terminalType;
    }

    public BwPaymentRecord setTerminalType(Integer terminalType) {
        this.terminalType = terminalType;
        return this;
    }

    public Integer getPayWay() {
        return payWay;
    }

    public BwPaymentRecord setPayWay(Integer payWay) {
        this.payWay = payWay;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public BwPaymentRecord setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public BwPaymentRecord setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public BwPaymentRecord setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
