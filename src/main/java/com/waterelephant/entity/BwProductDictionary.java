package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @ClassName: BwProductDictionary
 * @Description: TODO(产品实体)
 * @author SongYaJun
 * @date 2016年10月27日 上午11:08:47
 *
 */
@Table(name = "bw_product_dictionary")
public class BwProductDictionary implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 编号
    private String pName; // 产品名称
    private Double pInvestRateMonth; // 借款月利率
    private Double pInvesstRateYear; // 合同年利率
    private Double pBorrowRateMonth; // 合同月利率
    private String pRepayType; // 产品还款方式1先息后本2等额本息
    private Double pRiskScale; // 风险备用金占比
    private Double pPoundageScale; // 产品手续费占比
    private String pTerm; // 产品期限
    // ====新增字段
    private String pTermType; // 产品类型（1：月；2：天）pTermType
    private String pNo; // 产品编号
    private Double pFastReviewCost; // 快速信审费
    private Double pPlatformUseCost; // 平台使用费
    private Double pNumberManageCost; // 账户管理费
    private Double pCollectionPassagewayCost; // 代收通道费
    private Double pCapitalUseCost; // 资金使用费
    private Integer productType; // 产品类型，1.单期 2.分期
    private Double interestRate; // 利息率
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
    private Double rateOverdue;// 逾期费率
    /**
     * 湛江委律师函手续费(单期总金额3%)
     */
    private Double zjwCost;
    // ====新增字段2
    private String userTypeCanUse;// 用户类型(逗号隔开)
    private Integer minAmount;// 最小金额
    private Integer maxAmount;// 最大金额
    private Integer defaultAmount;// 默认金额
    private Double ratePreService;// 贷前咨询服务费率(%)
    private Double rateAfterLoan;// 贷后信用管理费率(%)
    private Double rateFundUtilization;// 资金使用费率(%)
    private Integer supportExtension;// 是否支持展期(0否 1是)
    private Integer daysExtensionLoanLimit;// 借款多少天之后才能展期
    private Integer daysExtensionOverdueLimit;// 逾期多少天之后不能展期
    private Integer sort;// 排序(从小到大)
    private Integer defaultNumber;// 默认期数
    private String optName;// 操作人
    private Integer orderToDraftTerm;// 工单转草稿天数(默认7天)
    private Integer rejectTime;// 拒绝限制时间

    public BwProductDictionary(Long id, String pName, Double pInvestRateMonth, Double pInvesstRateYear, Double pBorrowRateMonth, String pRepayType, Double pRiskScale, Double pPoundageScale,
            String pTerm, String pNo, String pTermType, Date createTime, Date updateTime, Double pFastReviewCost, Double pPlatformUseCost, Double pNumberManageCost, Double pCollectionPassagewayCost,
            Double pCapitalUseCost) {
        this.id = id;
        this.pName = pName;
        this.pInvestRateMonth = pInvestRateMonth;
        this.pInvesstRateYear = pInvesstRateYear;
        this.pBorrowRateMonth = pBorrowRateMonth;
        this.pRepayType = pRepayType;
        this.pRiskScale = pRiskScale;
        this.pPoundageScale = pPoundageScale;
        this.pTerm = pTerm;
        this.pTermType = pTermType; // 产品类型（1：月；2：天）
        this.pNo = pNo; // 产品编号
        this.pFastReviewCost = pFastReviewCost; // 快速信审费
        this.pPlatformUseCost = pPlatformUseCost; // 平台使用费
        this.pNumberManageCost = pNumberManageCost; // 账户管理费
        this.pCollectionPassagewayCost = pCollectionPassagewayCost; // 代收通道费
        this.pCapitalUseCost = pCapitalUseCost; // 资金使用费
        this.createTime = createTime; // 创建时间
        this.updateTime = updateTime; // 更新时间
    }

    public BwProductDictionary() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    /**
     * 借款月利率
     */
    public Double getpInvestRateMonth() {
        return pInvestRateMonth;
    }

    public void setpInvestRateMonth(Double pInvestRateMonth) {
        this.pInvestRateMonth = pInvestRateMonth;
    }

    /**
     * 合同年利率
     */
    public Double getpInvesstRateYear() {
        return pInvesstRateYear;
    }

    public void setpInvesstRateYear(Double pInvesstRateYear) {
        this.pInvesstRateYear = pInvesstRateYear;
    }

    /**
     * 合同月利率
     */
    public Double getpBorrowRateMonth() {
        return pBorrowRateMonth;
    }

    public void setpBorrowRateMonth(Double pBorrowRateMonth) {
        this.pBorrowRateMonth = pBorrowRateMonth;
    }

    public String getpRepayType() {
        return pRepayType;
    }

    public void setpRepayType(String pRepayType) {
        this.pRepayType = pRepayType;
    }

    public Double getpRiskScale() {
        return pRiskScale;
    }

    public void setpRiskScale(Double pRiskScale) {
        this.pRiskScale = pRiskScale;
    }

    public Double getpPoundageScale() {
        return pPoundageScale;
    }

    public void setpPoundageScale(Double pPoundageScale) {
        this.pPoundageScale = pPoundageScale;
    }

    public String getpTerm() {
        return pTerm;
    }

    public void setpTerm(String pTerm) {
        this.pTerm = pTerm;
    }

    public String getpTermType() {
        return pTermType;
    }

    public void setpTermType(String pTermType) {
        this.pTermType = pTermType;
    }

    public String getpNo() {
        return pNo;
    }

    public void setpNo(String pNo) {
        this.pNo = pNo;
    }

    public Double getpFastReviewCost() {
        return pFastReviewCost;
    }

    public void setpFastReviewCost(Double pFastReviewCost) {
        this.pFastReviewCost = pFastReviewCost;
    }

    public Double getpPlatformUseCost() {
        return pPlatformUseCost;
    }

    public void setpPlatformUseCost(Double pPlatformUseCost) {
        this.pPlatformUseCost = pPlatformUseCost;
    }

    public Double getpNumberManageCost() {
        return pNumberManageCost;
    }

    public void setpNumberManageCost(Double pNumberManageCost) {
        this.pNumberManageCost = pNumberManageCost;
    }

    public Double getpCollectionPassagewayCost() {
        return pCollectionPassagewayCost;
    }

    public void setpCollectionPassagewayCost(Double pCollectionPassagewayCost) {
        this.pCollectionPassagewayCost = pCollectionPassagewayCost;
    }

    public Double getpCapitalUseCost() {
        return pCapitalUseCost;
    }

    public void setpCapitalUseCost(Double pCapitalUseCost) {
        this.pCapitalUseCost = pCapitalUseCost;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
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

    public Double getZjwCost() {
        return zjwCost;
    }

    public void setZjwCost(Double zjwCost) {
        this.zjwCost = zjwCost;
    }

    public String getUserTypeCanUse() {
        return userTypeCanUse;
    }

    public void setUserTypeCanUse(String userTypeCanUse) {
        this.userTypeCanUse = userTypeCanUse;
    }

    public Integer getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Integer minAmount) {
        this.minAmount = minAmount;
    }

    public Integer getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Integer maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Integer getDefaultAmount() {
        return defaultAmount;
    }

    public void setDefaultAmount(Integer defaultAmount) {
        this.defaultAmount = defaultAmount;
    }

    public Double getRatePreService() {
        return ratePreService;
    }

    public void setRatePreService(Double ratePreService) {
        this.ratePreService = ratePreService;
    }

    public Double getRateAfterLoan() {
        return rateAfterLoan;
    }

    public void setRateAfterLoan(Double rateAfterLoan) {
        this.rateAfterLoan = rateAfterLoan;
    }

    public Double getRateFundUtilization() {
        return rateFundUtilization;
    }

    public void setRateFundUtilization(Double rateFundUtilization) {
        this.rateFundUtilization = rateFundUtilization;
    }

    public Integer getSupportExtension() {
        return supportExtension;
    }

    public void setSupportExtension(Integer supportExtension) {
        this.supportExtension = supportExtension;
    }

    public Integer getDaysExtensionLoanLimit() {
        return daysExtensionLoanLimit;
    }

    public void setDaysExtensionLoanLimit(Integer daysExtensionLoanLimit) {
        this.daysExtensionLoanLimit = daysExtensionLoanLimit;
    }

    public Integer getDaysExtensionOverdueLimit() {
        return daysExtensionOverdueLimit;
    }

    public void setDaysExtensionOverdueLimit(Integer daysExtensionOverdueLimit) {
        this.daysExtensionOverdueLimit = daysExtensionOverdueLimit;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getDefaultNumber() {
        return defaultNumber;
    }

    public void setDefaultNumber(Integer defaultNumber) {
        this.defaultNumber = defaultNumber;
    }

    public String getOptName() {
        return optName;
    }

    public void setOptName(String optName) {
        this.optName = optName;
    }

    public Integer getOrderToDraftTerm() {
        return orderToDraftTerm;
    }

    public void setOrderToDraftTerm(Integer orderToDraftTerm) {
        this.orderToDraftTerm = orderToDraftTerm;
    }

    public Integer getRejectTime() {
        return rejectTime;
    }

    public void setRejectTime(Integer rejectTime) {
        this.rejectTime = rejectTime;
    }

    /**
     * @return 获取 rateOverdue属性值
     */
    public Double getRateOverdue() {
        return rateOverdue;
    }

    /**
     * @param rateOverdue 设置 rateOverdue 属性值为参数值 rateOverdue
     */
    public void setRateOverdue(Double rateOverdue) {
        this.rateOverdue = rateOverdue;
    }

}
