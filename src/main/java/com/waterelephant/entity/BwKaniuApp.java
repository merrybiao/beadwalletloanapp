// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kaniu_app")
// public class BwKaniuApp implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Long orderId;//
// private Integer userknAppGamblingCnt;// 安装博彩软件的个数
// private Integer userknAppCarOwnerCnt;// 安装车主软件软件的个数
// private Integer userknAppOnSaleCnt;// 安装打折返利软件的个数
// private Integer userknAppInstalmentCnt;// 安装分期软件软件的个数
// private Integer userknAppStockFundCnt;// 安装股票基金软件的个数
// private Integer userknAppHomeDecorationCnt;// 安装家居装修软件的个数
// private Integer userknAppBuyCarCnt;// 安装买车软件的个数
// private Integer userknAppBuyHouseCnt;// 安装买房软件的个数
// private Integer userknAppJobSearchCnt;// 安装求职软件软件的个数
// private Integer userknAppShoppingCnt;// 安装商城软件的个数
// private Integer userknAppSaveMoneyCnt;// 安装省钱软件软件的个数
// private Integer userknAppCashLoanCnt;// 安装现金贷软件的个数
// private Integer userknAppBankCnt;// 安装银行软件软件的个数
// private Integer userknAppCreditInformationCnt;// 安装征信软件软件的个数
// private Integer userknAppPaymentCnt;// 安装支付软件的个数
// private Integer userknAppRentingCnt;// 安装租房软件的个数
// private Integer userknAppFinancialCnt;// 当前安装理财类APP的个数
// private Integer userknAppInsuranceCnt;// 当前安装保险类APP的个数
// private Integer userknAppBankLoanCnt;// 当前安装贷款类APP的个数
// private Integer userknAppP2pFinancialCnt;// 当前安装P2P理财类APP的个数
// private Date createTime;//
//
//
// public void setId(Long id) {
// this.id = id;
// }
//
// public Long getId() {
// return id;
// }
//
// public void setOrderId(Long orderId) {
// this.orderId = orderId;
// }
//
// public Long getOrderId() {
// return orderId;
// }
//
// public void setUserknAppGamblingCnt(Integer userknAppGamblingCnt) {
// this.userknAppGamblingCnt = userknAppGamblingCnt;
// }
//
// public Integer getUserknAppGamblingCnt() {
// return userknAppGamblingCnt;
// }
//
// public void setUserknAppCarOwnerCnt(Integer userknAppCarOwnerCnt) {
// this.userknAppCarOwnerCnt = userknAppCarOwnerCnt;
// }
//
// public Integer getUserknAppCarOwnerCnt() {
// return userknAppCarOwnerCnt;
// }
//
// public void setUserknAppOnSaleCnt(Integer userknAppOnSaleCnt) {
// this.userknAppOnSaleCnt = userknAppOnSaleCnt;
// }
//
// public Integer getUserknAppOnSaleCnt() {
// return userknAppOnSaleCnt;
// }
//
// public void setUserknAppInstalmentCnt(Integer userknAppInstalmentCnt) {
// this.userknAppInstalmentCnt = userknAppInstalmentCnt;
// }
//
// public Integer getUserknAppInstalmentCnt() {
// return userknAppInstalmentCnt;
// }
//
// public void setUserknAppStockFundCnt(Integer userknAppStockFundCnt) {
// this.userknAppStockFundCnt = userknAppStockFundCnt;
// }
//
// public Integer getUserknAppStockFundCnt() {
// return userknAppStockFundCnt;
// }
//
// public void setUserknAppHomeDecorationCnt(Integer userknAppHomeDecorationCnt) {
// this.userknAppHomeDecorationCnt = userknAppHomeDecorationCnt;
// }
//
// public Integer getUserknAppHomeDecorationCnt() {
// return userknAppHomeDecorationCnt;
// }
//
// public void setUserknAppBuyCarCnt(Integer userknAppBuyCarCnt) {
// this.userknAppBuyCarCnt = userknAppBuyCarCnt;
// }
//
// public Integer getUserknAppBuyCarCnt() {
// return userknAppBuyCarCnt;
// }
//
// public void setUserknAppBuyHouseCnt(Integer userknAppBuyHouseCnt) {
// this.userknAppBuyHouseCnt = userknAppBuyHouseCnt;
// }
//
// public Integer getUserknAppBuyHouseCnt() {
// return userknAppBuyHouseCnt;
// }
//
// public void setUserknAppJobSearchCnt(Integer userknAppJobSearchCnt) {
// this.userknAppJobSearchCnt = userknAppJobSearchCnt;
// }
//
// public Integer getUserknAppJobSearchCnt() {
// return userknAppJobSearchCnt;
// }
//
// public void setUserknAppShoppingCnt(Integer userknAppShoppingCnt) {
// this.userknAppShoppingCnt = userknAppShoppingCnt;
// }
//
// public Integer getUserknAppShoppingCnt() {
// return userknAppShoppingCnt;
// }
//
// public void setUserknAppSaveMoneyCnt(Integer userknAppSaveMoneyCnt) {
// this.userknAppSaveMoneyCnt = userknAppSaveMoneyCnt;
// }
//
// public Integer getUserknAppSaveMoneyCnt() {
// return userknAppSaveMoneyCnt;
// }
//
// public void setUserknAppCashLoanCnt(Integer userknAppCashLoanCnt) {
// this.userknAppCashLoanCnt = userknAppCashLoanCnt;
// }
//
// public Integer getUserknAppCashLoanCnt() {
// return userknAppCashLoanCnt;
// }
//
// public void setUserknAppBankCnt(Integer userknAppBankCnt) {
// this.userknAppBankCnt = userknAppBankCnt;
// }
//
// public Integer getUserknAppBankCnt() {
// return userknAppBankCnt;
// }
//
// public void setUserknAppCreditInformationCnt(Integer userknAppCreditInformationCnt) {
// this.userknAppCreditInformationCnt = userknAppCreditInformationCnt;
// }
//
// public Integer getUserknAppCreditInformationCnt() {
// return userknAppCreditInformationCnt;
// }
//
// public void setUserknAppPaymentCnt(Integer userknAppPaymentCnt) {
// this.userknAppPaymentCnt = userknAppPaymentCnt;
// }
//
// public Integer getUserknAppPaymentCnt() {
// return userknAppPaymentCnt;
// }
//
// public void setUserknAppRentingCnt(Integer userknAppRentingCnt) {
// this.userknAppRentingCnt = userknAppRentingCnt;
// }
//
// public Integer getUserknAppRentingCnt() {
// return userknAppRentingCnt;
// }
//
// public void setUserknAppFinancialCnt(Integer userknAppFinancialCnt) {
// this.userknAppFinancialCnt = userknAppFinancialCnt;
// }
//
// public Integer getUserknAppFinancialCnt() {
// return userknAppFinancialCnt;
// }
//
// public void setUserknAppInsuranceCnt(Integer userknAppInsuranceCnt) {
// this.userknAppInsuranceCnt = userknAppInsuranceCnt;
// }
//
// public Integer getUserknAppInsuranceCnt() {
// return userknAppInsuranceCnt;
// }
//
// public void setUserknAppBankLoanCnt(Integer userknAppBankLoanCnt) {
// this.userknAppBankLoanCnt = userknAppBankLoanCnt;
// }
//
// public Integer getUserknAppBankLoanCnt() {
// return userknAppBankLoanCnt;
// }
//
// public void setUserknAppP2pFinancialCnt(Integer userknAppP2pFinancialCnt) {
// this.userknAppP2pFinancialCnt = userknAppP2pFinancialCnt;
// }
//
// public Integer getUserknAppP2pFinancialCnt() {
// return userknAppP2pFinancialCnt;
// }
//
// public void setCreateTime(Date createTime) {
// this.createTime = createTime;
// }
//
// public Date getCreateTime() {
// return createTime;
// }
// }
