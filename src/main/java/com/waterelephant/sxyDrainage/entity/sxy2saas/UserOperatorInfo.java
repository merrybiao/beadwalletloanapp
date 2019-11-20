///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.sxy2saas;
//
//import java.util.List;
//
//import com.waterelephant.entity.BwBorrowerPosition;
//import com.waterelephant.entity.BwBqsDecision;
//import com.waterelephant.entity.BwBqsHitRule;
//import com.waterelephant.entity.BwBqsStrategy;
//import com.waterelephant.entity.BwContactList;
//import com.waterelephant.entity.BwFundAuth;
//import com.waterelephant.entity.BwFundInfo;
//import com.waterelephant.entity.BwFundRecord;
//import com.waterelephant.entity.BwInsureAccout;
//import com.waterelephant.entity.BwInsureInfo;
//import com.waterelephant.entity.BwInsureRecord;
//import com.waterelephant.entity.BwJdBankcards;
//import com.waterelephant.entity.BwJdOrderList;
//import com.waterelephant.entity.BwJdShippingAddrs;
//import com.waterelephant.entity.BwJdUserInfo;
//import com.waterelephant.entity.BwOperateBasic;
//import com.waterelephant.entity.BwOperateVoice;
//import com.waterelephant.entity.BwTbDeliverAddre;
//import com.waterelephant.entity.BwTbOrder;
//import com.waterelephant.entity.BwTbUser;
//import com.waterelephant.entity.BwTbZhifubaoBinding;
//import com.waterelephant.entity.BwXgAreaAnalysis;
//import com.waterelephant.entity.BwXgAreaAnalysisDetail;
//import com.waterelephant.entity.BwXgCallLog;
//import com.waterelephant.entity.BwXgCallLogDetail;
//import com.waterelephant.entity.BwXgEmergencyAnalysis;
//import com.waterelephant.entity.BwXgMidScore;
//import com.waterelephant.entity.BwXgMonthlyConsumption;
//import com.waterelephant.entity.BwXgOverall;
//import com.waterelephant.entity.BwXgSpecialCate;
//import com.waterelephant.entity.BwXgSpecialCatePhoneDetail;
//import com.waterelephant.entity.BwXgSpecialMonthDetail;
//import com.waterelephant.entity.BwXgTripAnalysis;
//import com.waterelephant.entity.BwXgTripAnalysisDetail;
//import com.waterelephant.entity.BwXgUserSpecialCallInfo;
//
///**
// * 
// * 
// * Module: (code:s2s)
// * 
// * UserOperatorInfo.java
// * 
// * @author zhangchong
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public class UserOperatorInfo {
//	private List<BwContactList> bwContactLists;
//	private BwOperateBasic bwOperateBasic;
//	private List<BwOperateVoice> bwOperateVoices;
//
//	private List<BwXgAreaAnalysis> bwXgAreaAnalysiss;
//	private List<BwXgAreaAnalysisDetail> bwXgAreaAnalysisDetails;
//	private List<BwXgCallLog> bwXgCallLogs;
//	private List<BwXgCallLogDetail> bwXgCallLogDetails;
//	private List<BwXgEmergencyAnalysis> bwXgEmergencyAnalysiss;
//	private List<BwXgMidScore> bwXgMidScores;
//	private List<BwXgMonthlyConsumption> bwXgMonthlyConsumptions;
//	private List<BwXgOverall> bwXgOveralls;
//	private List<BwXgSpecialCate> bwXgSpecialCates;
//	private List<BwXgSpecialMonthDetail> bwXgSpecialCateMonthDetails;
//	private List<BwXgSpecialCatePhoneDetail> bwXgSpecialCatePhoneDetails;
//	private List<BwXgTripAnalysis> bwXgTripAnalysiss;
//	private List<BwXgTripAnalysisDetail> bwXgTripAnalysisDetails;
//	private List<BwXgUserSpecialCallInfo> bwXgUserSpecialCallInfos;
//
//	private List<BwTbDeliverAddre> bwTbDeliverAddres;
//	private List<BwTbOrder> bwTbOrders;
//	private List<BwTbUser> bwTbUsers;
//	private List<BwTbZhifubaoBinding> bwTbZhifubaoBindings;
//
//	private List<BwJdBankcards> bwJdBankcardss;
//	private List<BwJdOrderList> bwJdOrderLists;
//	private List<BwJdShippingAddrs> bwJdShippingAddrss;
//	private List<BwJdUserInfo> bwJdUserInfos;
//
//	private List<BwBqsDecision> bwBqsDecisions;
//	private List<BwBqsHitRule> bwBqsHitRules;
//	private List<BwBqsStrategy> bwBqsStrategies;
//
//	private List<BwFundAuth> bwFundAuths;
//	private BwFundInfo bwFundInfo;
//	private List<BwFundRecord> bwFundRecords;
//
//	private BwInsureAccout bwInsureAccout;
//	private List<BwInsureInfo> bwInsureInfos;
//	private List<BwInsureRecord> bwInsureRecords;
//
//	private List<BwBorrowerPosition> bwBorrowerPositions;
//
//	public List<BwContactList> getBwContactLists() {
//		return bwContactLists;
//	}
//
//	public void setBwContactLists(List<BwContactList> bwContactLists) {
//		this.bwContactLists = bwContactLists;
//	}
//
//	public BwOperateBasic getBwOperateBasic() {
//		return bwOperateBasic;
//	}
//
//	public void setBwOperateBasic(BwOperateBasic bwOperateBasic) {
//		this.bwOperateBasic = bwOperateBasic;
//	}
//
//	public List<BwOperateVoice> getBwOperateVoices() {
//		return bwOperateVoices;
//	}
//
//	public void setBwOperateVoices(List<BwOperateVoice> bwOperateVoices) {
//		this.bwOperateVoices = bwOperateVoices;
//	}
//
//	public List<BwXgAreaAnalysis> getBwXgAreaAnalysiss() {
//		return bwXgAreaAnalysiss;
//	}
//
//	public void setBwXgAreaAnalysiss(List<BwXgAreaAnalysis> bwXgAreaAnalysiss) {
//		this.bwXgAreaAnalysiss = bwXgAreaAnalysiss;
//	}
//
//	public List<BwXgAreaAnalysisDetail> getBwXgAreaAnalysisDetails() {
//		return bwXgAreaAnalysisDetails;
//	}
//
//	public void setBwXgAreaAnalysisDetails(List<BwXgAreaAnalysisDetail> bwXgAreaAnalysisDetails) {
//		this.bwXgAreaAnalysisDetails = bwXgAreaAnalysisDetails;
//	}
//
//	public List<BwXgCallLog> getBwXgCallLogs() {
//		return bwXgCallLogs;
//	}
//
//	public void setBwXgCallLogs(List<BwXgCallLog> bwXgCallLogs) {
//		this.bwXgCallLogs = bwXgCallLogs;
//	}
//
//	public List<BwXgCallLogDetail> getBwXgCallLogDetails() {
//		return bwXgCallLogDetails;
//	}
//
//	public void setBwXgCallLogDetails(List<BwXgCallLogDetail> bwXgCallLogDetails) {
//		this.bwXgCallLogDetails = bwXgCallLogDetails;
//	}
//
//	public List<BwXgEmergencyAnalysis> getBwXgEmergencyAnalysiss() {
//		return bwXgEmergencyAnalysiss;
//	}
//
//	public void setBwXgEmergencyAnalysiss(List<BwXgEmergencyAnalysis> bwXgEmergencyAnalysiss) {
//		this.bwXgEmergencyAnalysiss = bwXgEmergencyAnalysiss;
//	}
//
//	public List<BwXgMidScore> getBwXgMidScores() {
//		return bwXgMidScores;
//	}
//
//	public void setBwXgMidScores(List<BwXgMidScore> bwXgMidScores) {
//		this.bwXgMidScores = bwXgMidScores;
//	}
//
//	public List<BwXgMonthlyConsumption> getBwXgMonthlyConsumptions() {
//		return bwXgMonthlyConsumptions;
//	}
//
//	public void setBwXgMonthlyConsumptions(List<BwXgMonthlyConsumption> bwXgMonthlyConsumptions) {
//		this.bwXgMonthlyConsumptions = bwXgMonthlyConsumptions;
//	}
//
//	public List<BwXgOverall> getBwXgOveralls() {
//		return bwXgOveralls;
//	}
//
//	public void setBwXgOveralls(List<BwXgOverall> bwXgOveralls) {
//		this.bwXgOveralls = bwXgOveralls;
//	}
//
//	public List<BwXgSpecialCate> getBwXgSpecialCates() {
//		return bwXgSpecialCates;
//	}
//
//	public void setBwXgSpecialCates(List<BwXgSpecialCate> bwXgSpecialCates) {
//		this.bwXgSpecialCates = bwXgSpecialCates;
//	}
//
//	public List<BwXgSpecialMonthDetail> getBwXgSpecialCateMonthDetails() {
//		return bwXgSpecialCateMonthDetails;
//	}
//
//	public void setBwXgSpecialCateMonthDetails(List<BwXgSpecialMonthDetail> bwXgSpecialCateMonthDetails) {
//		this.bwXgSpecialCateMonthDetails = bwXgSpecialCateMonthDetails;
//	}
//
//	public List<BwXgSpecialCatePhoneDetail> getBwXgSpecialCatePhoneDetails() {
//		return bwXgSpecialCatePhoneDetails;
//	}
//
//	public void setBwXgSpecialCatePhoneDetails(List<BwXgSpecialCatePhoneDetail> bwXgSpecialCatePhoneDetails) {
//		this.bwXgSpecialCatePhoneDetails = bwXgSpecialCatePhoneDetails;
//	}
//
//	public List<BwXgTripAnalysis> getBwXgTripAnalysiss() {
//		return bwXgTripAnalysiss;
//	}
//
//	public void setBwXgTripAnalysiss(List<BwXgTripAnalysis> bwXgTripAnalysiss) {
//		this.bwXgTripAnalysiss = bwXgTripAnalysiss;
//	}
//
//	public List<BwXgTripAnalysisDetail> getBwXgTripAnalysisDetails() {
//		return bwXgTripAnalysisDetails;
//	}
//
//	public void setBwXgTripAnalysisDetails(List<BwXgTripAnalysisDetail> bwXgTripAnalysisDetails) {
//		this.bwXgTripAnalysisDetails = bwXgTripAnalysisDetails;
//	}
//
//	public List<BwXgUserSpecialCallInfo> getBwXgUserSpecialCallInfos() {
//		return bwXgUserSpecialCallInfos;
//	}
//
//	public void setBwXgUserSpecialCallInfos(List<BwXgUserSpecialCallInfo> bwXgUserSpecialCallInfos) {
//		this.bwXgUserSpecialCallInfos = bwXgUserSpecialCallInfos;
//	}
//
//	public List<BwTbDeliverAddre> getBwTbDeliverAddres() {
//		return bwTbDeliverAddres;
//	}
//
//	public void setBwTbDeliverAddres(List<BwTbDeliverAddre> bwTbDeliverAddres) {
//		this.bwTbDeliverAddres = bwTbDeliverAddres;
//	}
//
//	public List<BwTbOrder> getBwTbOrders() {
//		return bwTbOrders;
//	}
//
//	public void setBwTbOrders(List<BwTbOrder> bwTbOrders) {
//		this.bwTbOrders = bwTbOrders;
//	}
//
//	public List<BwTbUser> getBwTbUsers() {
//		return bwTbUsers;
//	}
//
//	public void setBwTbUsers(List<BwTbUser> bwTbUsers) {
//		this.bwTbUsers = bwTbUsers;
//	}
//
//	public List<BwTbZhifubaoBinding> getBwTbZhifubaoBindings() {
//		return bwTbZhifubaoBindings;
//	}
//
//	public void setBwTbZhifubaoBindings(List<BwTbZhifubaoBinding> bwTbZhifubaoBindings) {
//		this.bwTbZhifubaoBindings = bwTbZhifubaoBindings;
//	}
//
//	public List<BwJdBankcards> getBwJdBankcardss() {
//		return bwJdBankcardss;
//	}
//
//	public void setBwJdBankcardss(List<BwJdBankcards> bwJdBankcardss) {
//		this.bwJdBankcardss = bwJdBankcardss;
//	}
//
//	public List<BwJdOrderList> getBwJdOrderLists() {
//		return bwJdOrderLists;
//	}
//
//	public void setBwJdOrderLists(List<BwJdOrderList> bwJdOrderLists) {
//		this.bwJdOrderLists = bwJdOrderLists;
//	}
//
//	public List<BwJdShippingAddrs> getBwJdShippingAddrss() {
//		return bwJdShippingAddrss;
//	}
//
//	public void setBwJdShippingAddrss(List<BwJdShippingAddrs> bwJdShippingAddrss) {
//		this.bwJdShippingAddrss = bwJdShippingAddrss;
//	}
//
//	public List<BwJdUserInfo> getBwJdUserInfos() {
//		return bwJdUserInfos;
//	}
//
//	public void setBwJdUserInfos(List<BwJdUserInfo> bwJdUserInfos) {
//		this.bwJdUserInfos = bwJdUserInfos;
//	}
//
//	public List<BwBqsDecision> getBwBqsDecisions() {
//		return bwBqsDecisions;
//	}
//
//	public void setBwBqsDecisions(List<BwBqsDecision> bwBqsDecisions) {
//		this.bwBqsDecisions = bwBqsDecisions;
//	}
//
//	public List<BwBqsHitRule> getBwBqsHitRules() {
//		return bwBqsHitRules;
//	}
//
//	public void setBwBqsHitRules(List<BwBqsHitRule> bwBqsHitRules) {
//		this.bwBqsHitRules = bwBqsHitRules;
//	}
//
//	public List<BwBqsStrategy> getBwBqsStrategies() {
//		return bwBqsStrategies;
//	}
//
//	public void setBwBqsStrategies(List<BwBqsStrategy> bwBqsStrategies) {
//		this.bwBqsStrategies = bwBqsStrategies;
//	}
//
//	public List<BwFundAuth> getBwFundAuths() {
//		return bwFundAuths;
//	}
//
//	public void setBwFundAuths(List<BwFundAuth> bwFundAuths) {
//		this.bwFundAuths = bwFundAuths;
//	}
//
//	public BwFundInfo getBwFundInfo() {
//		return bwFundInfo;
//	}
//
//	public void setBwFundInfo(BwFundInfo bwFundInfo) {
//		this.bwFundInfo = bwFundInfo;
//	}
//
//	public List<BwFundRecord> getBwFundRecords() {
//		return bwFundRecords;
//	}
//
//	public void setBwFundRecords(List<BwFundRecord> bwFundRecords) {
//		this.bwFundRecords = bwFundRecords;
//	}
//
//	public BwInsureAccout getBwInsureAccout() {
//		return bwInsureAccout;
//	}
//
//	public void setBwInsureAccout(BwInsureAccout bwInsureAccout) {
//		this.bwInsureAccout = bwInsureAccout;
//	}
//
//	public List<BwInsureInfo> getBwInsureInfos() {
//		return bwInsureInfos;
//	}
//
//	public void setBwInsureInfos(List<BwInsureInfo> bwInsureInfos) {
//		this.bwInsureInfos = bwInsureInfos;
//	}
//
//	public List<BwInsureRecord> getBwInsureRecords() {
//		return bwInsureRecords;
//	}
//
//	public void setBwInsureRecords(List<BwInsureRecord> bwInsureRecords) {
//		this.bwInsureRecords = bwInsureRecords;
//	}
//
//	public List<BwBorrowerPosition> getBwBorrowerPositions() {
//		return bwBorrowerPositions;
//	}
//
//	public void setBwBorrowerPositions(List<BwBorrowerPosition> bwBorrowerPositions) {
//		this.bwBorrowerPositions = bwBorrowerPositions;
//	}
//
//}
