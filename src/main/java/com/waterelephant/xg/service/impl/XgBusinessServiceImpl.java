package com.waterelephant.xg.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.rongCarrier.JSonEntity.AreaAnalysis;
import com.waterelephant.rongCarrier.JSonEntity.AreaAnalysisDetail;
import com.waterelephant.rongCarrier.JSonEntity.BasicInfo;
import com.waterelephant.rongCarrier.JSonEntity.CallLog;
import com.waterelephant.rongCarrier.JSonEntity.Detail;
import com.waterelephant.rongCarrier.JSonEntity.EmergencyAnalysis;
import com.waterelephant.rongCarrier.JSonEntity.HeadInfo;
import com.waterelephant.rongCarrier.JSonEntity.InputInfo;
import com.waterelephant.rongCarrier.JSonEntity.MidScore;
import com.waterelephant.rongCarrier.JSonEntity.MonthlyConsumption;
import com.waterelephant.rongCarrier.JSonEntity.OpReport;
import com.waterelephant.rongCarrier.JSonEntity.RiskAnalysis;
import com.waterelephant.rongCarrier.JSonEntity.SpecialCate;
import com.waterelephant.rongCarrier.JSonEntity.SpecialCateMonthDetail;
import com.waterelephant.rongCarrier.JSonEntity.SpecialCatePhoneDetail;
import com.waterelephant.rongCarrier.JSonEntity.TripAnalysis;
import com.waterelephant.rongCarrier.JSonEntity.UserPortrait;
import com.waterelephant.rongCarrier.JSonEntity.UserPortraitActiveDays;
import com.waterelephant.rongCarrier.JSonEntity.UserPortraitSpecialCallInfo;
import com.waterelephant.rongCarrier.JSonEntity.XGReturn;
import com.waterelephant.rongCarrier.entity.XgAreaAnalysis;
import com.waterelephant.rongCarrier.entity.XgAreaAnalysisDetail;
import com.waterelephant.rongCarrier.entity.XgCallLog;
import com.waterelephant.rongCarrier.entity.XgCallLogDetail;
import com.waterelephant.rongCarrier.entity.XgEmergencyAnalysis;
import com.waterelephant.rongCarrier.entity.XgMidScore;
import com.waterelephant.rongCarrier.entity.XgMonthlyConsumption;
import com.waterelephant.rongCarrier.entity.XgOverall;
import com.waterelephant.rongCarrier.entity.XgSpecialCate;
import com.waterelephant.rongCarrier.entity.XgSpecialCateMonthDetail;
import com.waterelephant.rongCarrier.entity.XgSpecialCatePhoneDetail;
import com.waterelephant.rongCarrier.entity.XgTripAnalysis;
import com.waterelephant.rongCarrier.entity.XgTripAnalysisDetail;
import com.waterelephant.rongCarrier.entity.XgUserSpecialCallInfo;
import com.waterelephant.rongCarrier.service.XgAreaAnalysisDetailService;
import com.waterelephant.rongCarrier.service.XgAreaAnalysisService;
import com.waterelephant.rongCarrier.service.XgCallLogDetailService;
import com.waterelephant.rongCarrier.service.XgCallLogService;
import com.waterelephant.rongCarrier.service.XgEmergencyAnalysisService;
import com.waterelephant.rongCarrier.service.XgMidScoreService;
import com.waterelephant.rongCarrier.service.XgMonthlyConsumptionService;
import com.waterelephant.rongCarrier.service.XgOverallService;
import com.waterelephant.rongCarrier.service.XgSpecialCateMonthDetailService;
import com.waterelephant.rongCarrier.service.XgSpecialCatePhoneDetailService;
import com.waterelephant.rongCarrier.service.XgSpecialCateService;
import com.waterelephant.rongCarrier.service.XgTripAnalysisDetailService;
import com.waterelephant.rongCarrier.service.XgTripAnalysisService;
import com.waterelephant.rongCarrier.service.XgUserSpecialCallInfoService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.xg.service.XgBusinessService;

/**
 * 西瓜数据业务处理
 * @author dinglinhao
 * @date 2018年12月29日11:32:48
 * @deprecat 连接新的主库（测试环境新主库201 sxfq）
 *
 */
@Service
public class XgBusinessServiceImpl implements XgBusinessService {
	
	private Logger logger = LoggerFactory.getLogger(XgBusinessService.class);
	
	@Autowired
	private XgMidScoreService xgMidScoreService;
	@Autowired
	private XgOverallService xgOverallService;
	@Autowired
	private XgUserSpecialCallInfoService xgUserSpecialCallInfoService;
	@Autowired
	private XgEmergencyAnalysisService xgEmergencyAnalysisService;
	@Autowired
	private XgSpecialCateService xgSpecialCateService;
	@Autowired
	private XgSpecialCatePhoneDetailService xgSpecialCatePhoneDetailService;
	@Autowired
	private XgSpecialCateMonthDetailService xgSpecialCateMonthDetailService;
	@Autowired
	private XgCallLogService xgCallLogService;
	@Autowired
	private XgCallLogDetailService xgCallLogDetailService;
	@Autowired
	private XgMonthlyConsumptionService xgMonthlyConsumptionService;
	@Autowired
	private XgTripAnalysisService xgTripAnalysisService;
	@Autowired
	private XgTripAnalysisDetailService xgTripAnalysisDetailService;
	@Autowired
	private XgAreaAnalysisService xgAreaAnalysisService;
	@Autowired
	private XgAreaAnalysisDetailService xgAreaAnalysisDetailService;

	@Override
	public void saveXgData(OpReport opReport, Long borrowerId) throws Exception {
			HeadInfo headInfo = opReport.getHead_info();
			InputInfo inputInfo = opReport.getInput_info();
			BasicInfo basicInfo = opReport.getBasic_info();
			RiskAnalysis riskAnalysis = opReport.getRisk_analysis();
			UserPortrait userPortrait = opReport.getUser_portrait();

			try {
				// 保存主表
				XgOverall xgOverall = new XgOverall();
				xgOverall.setBorrowerId(borrowerId);
				xgOverallService.deleteZ(xgOverall);
				xgOverall.setCreateDate(new Date());

				// 设置HeadInfo
				if (headInfo != null) {
					xgOverall.setSearchId(headInfo.getSearch_id());
					xgOverall.setReportTime(headInfo.getReport_time());
					xgOverall.setUserType(headInfo.getUser_type());
				}
				if (inputInfo != null) {
					xgOverall.setUserName(inputInfo.getUser_name());
					xgOverall.setIdCard(inputInfo.getId_card());
					xgOverall.setPhone(inputInfo.getPhone());
					xgOverall.setEmergencyName1(inputInfo.getEmergency_name1());
					xgOverall.setEmergencyName2(inputInfo.getEmergency_name2());
					xgOverall.setEmergencyPhone1(inputInfo.getEmergency_phone1());
					xgOverall.setEmergencyPhone2(inputInfo.getEmergency_phone2());
					xgOverall.setEmergencyRelation1(inputInfo.getEmergency_relation1());
					xgOverall.setEmergencyRelation2(inputInfo.getEmergency_relation2());
				}
				if (basicInfo != null) {
					xgOverall.setOperator(basicInfo.getOperator());
					xgOverall.setOperatorZh(basicInfo.getOperator_zh());
					xgOverall.setPhoneLocation(basicInfo.getPhone_location());
					xgOverall.setIdCardCheck(basicInfo.getId_card_check());
					xgOverall.setNameCheck(basicInfo.getName_check());
					xgOverall.setBasicInfoIdCard(basicInfo.getId_card());
					xgOverall.setBasicInfoRealName(basicInfo.getReal_name());
					xgOverall.setAveMonthlyConsumption(basicInfo.getAve_monthly_consumption());
					xgOverall.setCurrentBalance(basicInfo.getCurrent_balance());
					xgOverall.setRegTime(basicInfo.getReg_time());
					xgOverall.setIfCallEmergency1(basicInfo.getIf_call_emergency1());
					xgOverall.setIfCallEmergency2(basicInfo.getIf_call_emergency2());
				}
				if (riskAnalysis != null) {
					xgOverall.setBlacklistCnt(riskAnalysis.getBlacklist_cnt());
					xgOverall.setSearchedCnt(riskAnalysis.getSearched_cnt());
					xgOverall.setLoanRecordCnt(riskAnalysis.getLoan_record_cnt());
				}
				if (userPortrait != null) {
					if (userPortrait.getContact_distribution() != null) {
						xgOverall.setContactDistributionLocation(userPortrait.getContact_distribution().getLocation());
						xgOverall.setContactDistributionRatio(userPortrait.getContact_distribution().getRatio());
					}
					xgOverall.setNightActivityRatio(userPortrait.getNight_activity_ratio());
					xgOverall.setBothCallCnt(userPortrait.getBoth_call_cnt());
					xgOverall.setNightMsgRatio(userPortrait.getNight_msg_ratio());
					if (userPortrait.getActive_days() != null) {
						UserPortraitActiveDays activeDays = userPortrait.getActive_days();
						xgOverall.setActiveDaysStartDay(activeDays.getStart_day());
						xgOverall.setActiveDaysEndDay(activeDays.getEnd_day());
						xgOverall.setActiveDaysTotalDays(activeDays.getTotal_days());
						xgOverall.setActiveDaysStopDays(activeDays.getStop_days());
						xgOverall.setActiveDaysStop3Days(activeDays.getStop_3_days());
						xgOverall.setActiveDaysStop3DaysDetail(activeDays.getStop_3_days_detail());
						xgOverall.setActiveDaysStopDaysDetail(activeDays.getStop_days_detail());

					}
				}
				xgOverallService.save(xgOverall);
				XgUserSpecialCallInfo ddd = new XgUserSpecialCallInfo();
				ddd.setBorrowerId(borrowerId);
				xgUserSpecialCallInfoService.deleteZ(ddd);
				if (userPortrait != null) {
					List<UserPortraitSpecialCallInfo> s = userPortrait.getSpecial_call_info();
					if (s != null && s.size() > 0) {
						for (UserPortraitSpecialCallInfo userPortraitSpecialCallInfo : s) {
							XgUserSpecialCallInfo xgUserSpecialCallInfo = new XgUserSpecialCallInfo();
							xgUserSpecialCallInfo.setBorrowerId(borrowerId);
							xgUserSpecialCallInfo.setCallCnt(userPortraitSpecialCallInfo.getCalled_seconds());
							xgUserSpecialCallInfo.setTalkSeconds(userPortraitSpecialCallInfo.getTalk_seconds());
							xgUserSpecialCallInfo.setTalkCnt(userPortraitSpecialCallInfo.getTalk_cnt());
							xgUserSpecialCallInfo.setMsgCnt(userPortraitSpecialCallInfo.getMsg_cnt());
							xgUserSpecialCallInfo.setCalledSeconds(userPortraitSpecialCallInfo.getCalled_seconds());
							xgUserSpecialCallInfo.setReceiveCnt(userPortraitSpecialCallInfo.getReceive_cnt());
							xgUserSpecialCallInfo.setDetail(userPortraitSpecialCallInfo.getDetail());
							xgUserSpecialCallInfo.setCallCnt(userPortraitSpecialCallInfo.getCall_cnt());
							xgUserSpecialCallInfo.setUnknown_cnt(userPortraitSpecialCallInfo.getUnknown_cnt());
							xgUserSpecialCallInfo.setSendCnt(userPortraitSpecialCallInfo.getSend_cnt());
							xgUserSpecialCallInfo.setCallSeconds(userPortraitSpecialCallInfo.getCall_seconds());
							// xgUserSpecialCallInfo.setPhoneList(userPortraitSpecialCallInfo.getPhone_list());
							xgUserSpecialCallInfo.setCalledCnt(userPortraitSpecialCallInfo.getCalled_cnt());

							xgUserSpecialCallInfoService.save(xgUserSpecialCallInfo);
						}
					}
				}

				// 保存EmergencyAnalysis
				List<EmergencyAnalysis> emergencyAnalysisList = opReport.getEmergency_analysis();
				XgEmergencyAnalysis dd3 = new XgEmergencyAnalysis();
				dd3.setBorrowerId(borrowerId);
				xgEmergencyAnalysisService.deleteZ(dd3);
				if (emergencyAnalysisList != null && emergencyAnalysisList.size() > 0) {
					for (EmergencyAnalysis emergencyAnalysis : emergencyAnalysisList) {
						XgEmergencyAnalysis xgEmergencyAnalysis = new XgEmergencyAnalysis();
						xgEmergencyAnalysis.setBorrowerId(borrowerId);
						xgEmergencyAnalysis.setPhone(emergencyAnalysis.getPhone());
						xgEmergencyAnalysis.setName(emergencyAnalysis.getName());
						xgEmergencyAnalysis.setFirstContactDate(emergencyAnalysis.getFirst_contact_date());
						xgEmergencyAnalysis.setLastContactDate(emergencyAnalysis.getLast_contact_date());
						xgEmergencyAnalysis.setTalkSeconds(emergencyAnalysis.getTalk_seconds());
						xgEmergencyAnalysis.setTalkCnt(emergencyAnalysis.getTalk_cnt());
						xgEmergencyAnalysis.setCallCnt(emergencyAnalysis.getCall_cnt());
						xgEmergencyAnalysis.setCallSeconds(emergencyAnalysis.getCall_seconds());
						xgEmergencyAnalysis.setCalledSeconds(emergencyAnalysis.getCalled_seconds());
						xgEmergencyAnalysis.setCalledCnt(emergencyAnalysis.getCalled_cnt());
						xgEmergencyAnalysis.setMsgCnt(emergencyAnalysis.getMsg_cnt());
						xgEmergencyAnalysis.setSendCnt(emergencyAnalysis.getSend_cnt());
						xgEmergencyAnalysis.setReceiveCnt(emergencyAnalysis.getReceive_cnt());
						xgEmergencyAnalysis.setUnknownCnt(emergencyAnalysis.getUnknown_cnt());
						xgEmergencyAnalysisService.save(xgEmergencyAnalysis);
					}
				}

				// 保存SpecialCate
				List<SpecialCate> specialCateList = opReport.getSpecial_cate();
				XgSpecialCate d = new XgSpecialCate();
				d.setBorrowerId(borrowerId);
				xgSpecialCateService.deleteZ(d);
				XgSpecialCatePhoneDetail d1 = new XgSpecialCatePhoneDetail();
				d1.setBorrowerId(borrowerId);
				xgSpecialCatePhoneDetailService.deleteZ(d1);
				XgSpecialCateMonthDetail d2 = new XgSpecialCateMonthDetail();
				d2.setBorrowerId(borrowerId);
				xgSpecialCateMonthDetailService.deleteZ(d2);
				if (specialCateList != null && specialCateList.size() > 0) {
					for (SpecialCate specialCate : specialCateList) {
						XgSpecialCate xgSpecialCate = new XgSpecialCate();
						xgSpecialCate.setBorrowerId(borrowerId);
						xgSpecialCate.setCallCnt(specialCate.getCall_cnt());
						xgSpecialCate.setCalledCnt(specialCate.getCalled_cnt());
						xgSpecialCate.setCalledSeconds(specialCate.getCalled_seconds());
						xgSpecialCate.setCallSeconds(specialCate.getCall_seconds());
						xgSpecialCate.setTalkCnt(specialCate.getTalk_cnt());
						xgSpecialCate.setTalkSeconds(specialCate.getTalk_seconds());
						xgSpecialCate.setMsgCnt(specialCate.getMsg_cnt());
						xgSpecialCate.setReceiveCnt(specialCate.getReceive_cnt());
						xgSpecialCate.setUnknownCnt(specialCate.getUnknown_cnt());
						xgSpecialCate.setSendCnt(specialCate.getSend_cnt());
						xgSpecialCate.setCate(specialCate.getCate());

						xgSpecialCateService.save(xgSpecialCate);

						// 保存SpecialCate中的 PhoneDetail
						List<SpecialCatePhoneDetail> specialCatePhoneDetailList = specialCate.getPhone_detail();
						if (specialCatePhoneDetailList != null && specialCatePhoneDetailList.size() > 0) {
							for (SpecialCatePhoneDetail specialCatePhoneDetail : specialCatePhoneDetailList) {
								XgSpecialCatePhoneDetail xgSpecialCatePhoneDetail = new XgSpecialCatePhoneDetail();
								xgSpecialCatePhoneDetail.setBorrowerId(borrowerId);
								xgSpecialCatePhoneDetail.setSpecialCateId(xgSpecialCate.getId());
								xgSpecialCatePhoneDetail.setCalledCnt(specialCatePhoneDetail.getCalled_cnt());
								xgSpecialCatePhoneDetail.setTalkSeconds(specialCatePhoneDetail.getTalk_seconds());
								xgSpecialCatePhoneDetail.setTalkCnt(specialCatePhoneDetail.getTalk_cnt());
								xgSpecialCatePhoneDetail.setMsgCnt(specialCatePhoneDetail.getMsg_cnt());
								xgSpecialCatePhoneDetail.setCalledSeconds(specialCatePhoneDetail.getCalled_seconds());
								xgSpecialCatePhoneDetail.setReceiveCnt(specialCatePhoneDetail.getReceive_cnt());
								xgSpecialCatePhoneDetail.setCallCnt(specialCatePhoneDetail.getCall_cnt());
								xgSpecialCatePhoneDetail.setUnknownCnt(specialCatePhoneDetail.getUnknown_cnt());
								xgSpecialCatePhoneDetail.setCalledSeconds(specialCatePhoneDetail.getCalled_seconds());
								xgSpecialCatePhoneDetail.setPhone(specialCatePhoneDetail.getPhone());
								xgSpecialCatePhoneDetail.setUnknownCnt(specialCatePhoneDetail.getUnknown_cnt());
								xgSpecialCatePhoneDetail.setPhoneInfo(specialCatePhoneDetail.getPhone_info());
								xgSpecialCatePhoneDetail.setCallSeconds(specialCatePhoneDetail.getCall_seconds());
								xgSpecialCatePhoneDetail.setSendCnt(specialCatePhoneDetail.getSend_cnt());
								xgSpecialCatePhoneDetailService.save(xgSpecialCatePhoneDetail);
							}
						}
						List<SpecialCateMonthDetail> specialCateMonthDetails = specialCate.getMonth_detail();
						if (specialCateMonthDetails != null && specialCateMonthDetails.size() > 0) {
							for (SpecialCateMonthDetail specialCateMonthDetail : specialCateMonthDetails) {
								XgSpecialCateMonthDetail xgSpecialCateMonthDetail = new XgSpecialCateMonthDetail();
								xgSpecialCateMonthDetail.setBorrowerId(borrowerId);
								xgSpecialCateMonthDetail.setSpecialCateId(xgSpecialCate.getId());

								xgSpecialCateMonthDetail.setCalledCnt(specialCateMonthDetail.getCalled_cnt());
								xgSpecialCateMonthDetail.setTalkSeconds(specialCateMonthDetail.getTalk_seconds());
								xgSpecialCateMonthDetail.setTalkCnt(specialCateMonthDetail.getTalk_cnt());
								xgSpecialCateMonthDetail.setMsgCnt(specialCateMonthDetail.getMsg_cnt());
								xgSpecialCateMonthDetail.setCalledSeconds(specialCateMonthDetail.getCalled_seconds());
								xgSpecialCateMonthDetail.setReceiveCnt(specialCateMonthDetail.getReceive_cnt());
								xgSpecialCateMonthDetail.setCallCnt(specialCateMonthDetail.getCall_cnt());
								xgSpecialCateMonthDetail.setUnknownCnt(specialCateMonthDetail.getUnknown_cnt());
								xgSpecialCateMonthDetail.setCalledSeconds(specialCateMonthDetail.getCalled_seconds());
								xgSpecialCateMonthDetail.setMonth(specialCateMonthDetail.getMonth());
								xgSpecialCateMonthDetail.setCallSeconds(specialCateMonthDetail.getCall_seconds());
								xgSpecialCateMonthDetail.setSendCnt(specialCateMonthDetail.getSend_cnt());
								xgSpecialCateMonthDetailService.save(xgSpecialCateMonthDetail);
							}
						}
					}
				}

				// 保存 monthly_consumption
				List<MonthlyConsumption> monthlyConsumption = opReport.getMonthly_consumption();
				XgMonthlyConsumption d3 = new XgMonthlyConsumption();
				d3.setBorrowerId(borrowerId);
				xgMonthlyConsumptionService.deleteZ(d3);
				if (monthlyConsumption != null && monthlyConsumption.size() > 0) {
					for (MonthlyConsumption consumption : monthlyConsumption) {
						XgMonthlyConsumption xgMonthlyConsumption = new XgMonthlyConsumption();
						xgMonthlyConsumption.setBorrowerId(borrowerId);
						xgMonthlyConsumption.setCalledCnt(consumption.getCalled_cnt());
						xgMonthlyConsumption.setTalkSeconds(consumption.getTalk_seconds());
						xgMonthlyConsumption.setTalkCnt(consumption.getTalk_cnt());
						xgMonthlyConsumption.setMsgCnt(consumption.getMsg_cnt());
						xgMonthlyConsumption.setCalledSeconds(consumption.getCalled_seconds());
						xgMonthlyConsumption.setReceiveCnt(consumption.getReceive_cnt());
						xgMonthlyConsumption.setMonth(consumption.getMonth());
						xgMonthlyConsumption.setCallCnt(consumption.getCall_cnt());
						xgMonthlyConsumption.setUnknownCnt(consumption.getUnknown_cnt());
						xgMonthlyConsumption.setCallConsumption(consumption.getCall_consumption());
						xgMonthlyConsumption.setSendCnt(consumption.getSend_cnt());
						xgMonthlyConsumption.setCallSeconds(consumption.getCall_seconds());
						xgMonthlyConsumptionService.save(xgMonthlyConsumption);
					}
				}

				// 保存 call log
				List<CallLog> callLogList = opReport.getCall_log();
				XgCallLog d4 = new XgCallLog();
				d4.setBorrowerId(borrowerId);
				xgCallLogService.deleteZ(d4);
				XgCallLogDetail d12 = new XgCallLogDetail();
				d12.setBorrowerId(borrowerId);
				xgCallLogDetailService.deleteZ(d12);
				if (callLogList != null && callLogList.size() > 0) {
					for (CallLog callLog : callLogList) {
						XgCallLog xgCallLog = new XgCallLog();
						xgCallLog.setBorrowerId(borrowerId);
						xgCallLog.setContactNonn(callLog.getContact_noon());
						xgCallLog.setTalkSeconds(callLog.getTalk_seconds());
						xgCallLog.setTalkCnt(callLog.getTalk_cnt());
						xgCallLog.setContact3m(callLog.getContact_3m());
						xgCallLog.setMsgCnt(callLog.getMsg_cnt());
						xgCallLog.setContact1m(callLog.getContact_1m());
						xgCallLog.setUnknownCnt(callLog.getUnknown_cnt());
						xgCallLog.setContactEveing(callLog.getContact_eveing());
						xgCallLog.setContact1w(callLog.getContact_1w());
						xgCallLog.setPhoneInfo(callLog.getPhone_info());
						xgCallLog.setCallSeconds(callLog.getCall_seconds());
						xgCallLog.setCallCnt(callLog.getCall_cnt());
						xgCallLog.setCalledCnt(callLog.getCalled_cnt());
						xgCallLog.setContactWeekday(callLog.getContact_weekday());
						xgCallLog.setReceiveCnt(callLog.getReceive_cnt());
						xgCallLog.setPhone(callLog.getPhone());
						xgCallLog.setCallSeconds(callLog.getCall_seconds());
						xgCallLog.setFirstContactDate(callLog.getFirst_contact_date());
						xgCallLog.setContactAfternoon(callLog.getContact_afternoon());
						xgCallLog.setContactEarlyMorning(callLog.getContact_early_morning());
						xgCallLog.setLastContactDate(callLog.getLast_contact_date());
						xgCallLog.setContactNight(callLog.getContact_night());
						xgCallLog.setPhoneLabel(callLog.getPhone_label());
						xgCallLog.setSendCnt(callLog.getSend_cnt());
						xgCallLog.setPhoneLocation(callLog.getPhone_location());
						xgCallLog.setContactMorning(callLog.getContact_morning());
						xgCallLog.setContactWeekend(callLog.getContact_weekend());
						xgCallLog.setCalledSeconds(callLog.getCalled_seconds());
						xgCallLogService.save(xgCallLog);

						List<Detail> s = callLog.getDetail();
						for (Detail detail : s) {
							XgCallLogDetail xgCallLogDetail = new XgCallLogDetail();
							xgCallLogDetail.setBorrowerId(borrowerId);
							xgCallLogDetail.setCallLogId(xgCallLog.getId());
							xgCallLogDetail.setCalledCnt(detail.getCalled_cnt());
							xgCallLogDetail.setTalkSeconds(detail.getTalk_seconds());
							xgCallLogDetail.setTalkCnt(detail.getTalk_cnt());
							xgCallLogDetail.setMsgCnt(detail.getMsg_cnt());
							xgCallLogDetail.setCalledSeconds(detail.getCalled_seconds());
							xgCallLogDetail.setReceiveCnt(detail.getReceive_cnt());
							xgCallLogDetail.setMonth(detail.getMonth());
							xgCallLogDetail.setCallCnt(detail.getCall_cnt());
							xgCallLogDetail.setUnknownCnt(detail.getUnknown_cnt());
							xgCallLogDetail.setSendCnt(detail.getSend_cnt());
							xgCallLogDetail.setCallSeconds(detail.getCall_seconds());
							xgCallLogDetailService.save(xgCallLogDetail);
						}
					}
				}

				// 保存 trip_analysis
				List<TripAnalysis> tripAnalysisList = opReport.getTrip_analysis();
				XgTripAnalysis d6 = new XgTripAnalysis();
				d6.setBorrowerId(borrowerId);
				xgTripAnalysisService.deleteZ(d6);
				XgTripAnalysisDetail d15 = new XgTripAnalysisDetail();
				d15.setBorrowerId(borrowerId);
				xgTripAnalysisDetailService.deleteZ(d15);
				if (tripAnalysisList != null && tripAnalysisList.size() > 0) {
					for (TripAnalysis tripAnalysis : tripAnalysisList) {
						XgTripAnalysis xgtripAnalysis = new XgTripAnalysis();
						xgtripAnalysis.setBorrowerId(borrowerId);

						xgtripAnalysis.setCalledCnt(tripAnalysis.getCalled_cnt());
						xgtripAnalysis.setTalkCnt(tripAnalysis.getTalk_cnt());
						xgtripAnalysis.setTalkSeconds(tripAnalysis.getTalk_seconds());
						xgtripAnalysis.setMsgCnt(tripAnalysis.getMsg_cnt());
						xgtripAnalysis.setCallSeconds(tripAnalysis.getCall_seconds());
						xgtripAnalysis.setReceiveCnt(tripAnalysis.getReceive_cnt());
						xgtripAnalysis.setDateDistribution(tripAnalysis.getDate_distribution());
						xgtripAnalysis.setCallCnt(tripAnalysis.getCall_cnt());
						xgtripAnalysis.setUnknownCnt(tripAnalysis.getUnknown_cnt());
						xgtripAnalysis.setLocation(tripAnalysis.getLocation());
						xgtripAnalysis.setSendCnt(tripAnalysis.getSend_cnt());
						xgtripAnalysis.setCalledSeconds(tripAnalysis.getCalled_seconds());
						xgTripAnalysisService.save(xgtripAnalysis);

						List<Detail> s = tripAnalysis.getDetail();
						for (Detail detail : s) {
							XgTripAnalysisDetail xgTripAnalysisDetail = new XgTripAnalysisDetail();
							xgTripAnalysisDetail.setBorrowerId(borrowerId);
							xgTripAnalysisDetail.setTripAnalysisId(xgtripAnalysis.getId());
							xgTripAnalysisDetail.setCalledCnt(detail.getCalled_cnt());
							xgTripAnalysisDetail.setTalkSeconds(detail.getTalk_seconds());
							xgTripAnalysisDetail.setTalkCnt(detail.getTalk_cnt());
							xgTripAnalysisDetail.setMsgCnt(detail.getMsg_cnt());
							xgTripAnalysisDetail.setCalledSeconds(detail.getCalled_seconds());
							xgTripAnalysisDetail.setReceiveCnt(detail.getReceive_cnt());
							xgTripAnalysisDetail.setMonth(detail.getMonth());
							xgTripAnalysisDetail.setCallCnt(detail.getCall_cnt());
							xgTripAnalysisDetail.setUnknownCnt(detail.getUnknown_cnt());
							xgTripAnalysisDetail.setSendCnt(detail.getSend_cnt());
							xgTripAnalysisDetail.setCallSeconds(detail.getCall_seconds());
							xgTripAnalysisDetailService.save(xgTripAnalysisDetail);
						}
					}
				}

				// 保存 area_analysis
				List<AreaAnalysis> areaAnalysisList = opReport.getArea_analysis();
				XgAreaAnalysisDetail d112 = new XgAreaAnalysisDetail();
				d112.setBorrowerId(borrowerId);
				xgAreaAnalysisDetailService.deleteZ(d112);
				XgAreaAnalysis d8 = new XgAreaAnalysis();
				d8.setBorrowerId(borrowerId);
				xgAreaAnalysisService.deleteZ(d8);

				if (areaAnalysisList != null && areaAnalysisList.size() > 0) {
					for (AreaAnalysis areaAnalysis : areaAnalysisList) {
						XgAreaAnalysis xgAreaAnalysis = new XgAreaAnalysis();
						xgAreaAnalysis.setBorrowerId(borrowerId);

						xgAreaAnalysis.setCalledCnt(areaAnalysis.getCalled_cnt());
						xgAreaAnalysis.setTalkSeconds(areaAnalysis.getTalk_seconds());
						xgAreaAnalysis.setTalkCnt(areaAnalysis.getTalk_cnt());
						xgAreaAnalysis.setArea(areaAnalysis.getArea());
						xgAreaAnalysis.setReceiveCnt(areaAnalysis.getReceive_cnt());
						xgAreaAnalysis.setCallSeconds(areaAnalysis.getCall_seconds());
						xgAreaAnalysis.setMsgCnt(areaAnalysis.getMsg_cnt());
						xgAreaAnalysis.setCallCnt(areaAnalysis.getCall_cnt());
						xgAreaAnalysis.setUnknownCnt(areaAnalysis.getUnknown_cnt());
						xgAreaAnalysis.setContact_phoneCnt(areaAnalysis.getContact_phone_cnt());
						xgAreaAnalysis.setSendCnt(areaAnalysis.getSend_cnt());
						xgAreaAnalysis.setCalledSeconds(areaAnalysis.getCalled_seconds());
						xgAreaAnalysisService.save(xgAreaAnalysis);

						List<AreaAnalysisDetail> s = areaAnalysis.getDetail();
						for (AreaAnalysisDetail detail : s) {
							XgAreaAnalysisDetail xgTripAnalysisDetail = new XgAreaAnalysisDetail();
							xgTripAnalysisDetail.setBorrowerId(borrowerId);
							xgTripAnalysisDetail.setAreaAnalysisId(xgAreaAnalysis.getId());

							xgTripAnalysisDetail.setCalledCnt(detail.getCalled_cnt());
							xgTripAnalysisDetail.setTalkSeconds(detail.getTalk_seconds());
							xgTripAnalysisDetail.setTalkCnt(detail.getTalk_cnt());
							xgTripAnalysisDetail.setMsgCnt(detail.getMsg_cnt());
							xgTripAnalysisDetail.setCalledSeconds(detail.getCalled_seconds());
							xgTripAnalysisDetail.setReceiveCnt(detail.getReceive_cnt());
							xgTripAnalysisDetail.setMonth(detail.getMonth());
							xgTripAnalysisDetail.setCallCnt(detail.getCall_cnt());
							xgTripAnalysisDetail.setUnknownCnt(detail.getUnknown_cnt());
							xgTripAnalysisDetail.setContactPhoneCnt(detail.getContact_phone_cnt());
							xgTripAnalysisDetail.setSendCnt(detail.getSend_cnt());
							xgTripAnalysisDetail.setCallSeconds(detail.getCall_seconds());
							xgAreaAnalysisDetailService.save(xgTripAnalysisDetail);
						}
					}
				}
				//保存成功后往redis扔一条记录
			} catch (Exception e) {
				logger.error("~~~~~~~~~~~~~~~ borrowerId:{}运营商报表保存异常：", borrowerId,e.getMessage());
				throw new Exception("保存运营商数据失败，系统异常~");
			}
		}
	
	@Override
	public void saveXgDataToNewTab(OpReport opReport, Long borrowerId) throws Exception {
			HeadInfo headInfo = opReport.getHead_info();
			InputInfo inputInfo = opReport.getInput_info();
			BasicInfo basicInfo = opReport.getBasic_info();
			RiskAnalysis riskAnalysis = opReport.getRisk_analysis();
			UserPortrait userPortrait = opReport.getUser_portrait();

			try {
				// 保存主表
				XgOverall xgOverall = new XgOverall();
				xgOverall.setBorrowerId(borrowerId);
				xgOverallService.deleteZ(xgOverall);//这个表名不变，其他xg表名后缀加"_new"
				xgOverall.setCreateDate(new Date());

				// 设置HeadInfo
				if (headInfo != null) {
					xgOverall.setSearchId(headInfo.getSearch_id());
					xgOverall.setReportTime(headInfo.getReport_time());
					xgOverall.setUserType(headInfo.getUser_type());
				}
				if (inputInfo != null) {
					xgOverall.setUserName(inputInfo.getUser_name());
					xgOverall.setIdCard(inputInfo.getId_card());
					xgOverall.setPhone(inputInfo.getPhone());
					xgOverall.setEmergencyName1(inputInfo.getEmergency_name1());
					xgOverall.setEmergencyName2(inputInfo.getEmergency_name2());
					xgOverall.setEmergencyPhone1(inputInfo.getEmergency_phone1());
					xgOverall.setEmergencyPhone2(inputInfo.getEmergency_phone2());
					xgOverall.setEmergencyRelation1(inputInfo.getEmergency_relation1());
					xgOverall.setEmergencyRelation2(inputInfo.getEmergency_relation2());
				}
				if (basicInfo != null) {
					xgOverall.setOperator(basicInfo.getOperator());
					xgOverall.setOperatorZh(basicInfo.getOperator_zh());
					xgOverall.setPhoneLocation(basicInfo.getPhone_location());
					xgOverall.setIdCardCheck(basicInfo.getId_card_check());
					xgOverall.setNameCheck(basicInfo.getName_check());
					xgOverall.setBasicInfoIdCard(basicInfo.getId_card());
					xgOverall.setBasicInfoRealName(basicInfo.getReal_name());
					xgOverall.setAveMonthlyConsumption(basicInfo.getAve_monthly_consumption());
					xgOverall.setCurrentBalance(basicInfo.getCurrent_balance());
					xgOverall.setRegTime(basicInfo.getReg_time());
					xgOverall.setIfCallEmergency1(basicInfo.getIf_call_emergency1());
					xgOverall.setIfCallEmergency2(basicInfo.getIf_call_emergency2());
				}
				if (riskAnalysis != null) {
					xgOverall.setBlacklistCnt(riskAnalysis.getBlacklist_cnt());
					xgOverall.setSearchedCnt(riskAnalysis.getSearched_cnt());
					xgOverall.setLoanRecordCnt(riskAnalysis.getLoan_record_cnt());
				}
				if (userPortrait != null) {
					if (userPortrait.getContact_distribution() != null) {
						xgOverall.setContactDistributionLocation(userPortrait.getContact_distribution().getLocation());
						xgOverall.setContactDistributionRatio(userPortrait.getContact_distribution().getRatio());
					}
					xgOverall.setNightActivityRatio(userPortrait.getNight_activity_ratio());
					xgOverall.setBothCallCnt(userPortrait.getBoth_call_cnt());
					xgOverall.setNightMsgRatio(userPortrait.getNight_msg_ratio());
					if (userPortrait.getActive_days() != null) {
						UserPortraitActiveDays activeDays = userPortrait.getActive_days();
						xgOverall.setActiveDaysStartDay(activeDays.getStart_day());
						xgOverall.setActiveDaysEndDay(activeDays.getEnd_day());
						xgOverall.setActiveDaysTotalDays(activeDays.getTotal_days());
						xgOverall.setActiveDaysStopDays(activeDays.getStop_days());
						xgOverall.setActiveDaysStop3Days(activeDays.getStop_3_days());
						xgOverall.setActiveDaysStop3DaysDetail(activeDays.getStop_3_days_detail());
						xgOverall.setActiveDaysStopDaysDetail(activeDays.getStop_days_detail());

					}
				}
				xgOverallService.save(xgOverall);//这个表不变
				XgUserSpecialCallInfo ddd = new XgUserSpecialCallInfo();
				ddd.setBorrowerId(borrowerId);
				xgUserSpecialCallInfoService.deleteToNewTab(ddd);
				if (userPortrait != null) {
					List<UserPortraitSpecialCallInfo> s = userPortrait.getSpecial_call_info();
					if (s != null && s.size() > 0) {
						for (UserPortraitSpecialCallInfo userPortraitSpecialCallInfo : s) {
							XgUserSpecialCallInfo xgUserSpecialCallInfo = new XgUserSpecialCallInfo();
							xgUserSpecialCallInfo.setBorrowerId(borrowerId);
							xgUserSpecialCallInfo.setCallCnt(userPortraitSpecialCallInfo.getCalled_seconds());
							xgUserSpecialCallInfo.setTalkSeconds(userPortraitSpecialCallInfo.getTalk_seconds());
							xgUserSpecialCallInfo.setTalkCnt(userPortraitSpecialCallInfo.getTalk_cnt());
							xgUserSpecialCallInfo.setMsgCnt(userPortraitSpecialCallInfo.getMsg_cnt());
							xgUserSpecialCallInfo.setCalledSeconds(userPortraitSpecialCallInfo.getCalled_seconds());
							xgUserSpecialCallInfo.setReceiveCnt(userPortraitSpecialCallInfo.getReceive_cnt());
							xgUserSpecialCallInfo.setDetail(userPortraitSpecialCallInfo.getDetail());
							xgUserSpecialCallInfo.setCallCnt(userPortraitSpecialCallInfo.getCall_cnt());
							xgUserSpecialCallInfo.setUnknown_cnt(userPortraitSpecialCallInfo.getUnknown_cnt());
							xgUserSpecialCallInfo.setSendCnt(userPortraitSpecialCallInfo.getSend_cnt());
							xgUserSpecialCallInfo.setCallSeconds(userPortraitSpecialCallInfo.getCall_seconds());
							// xgUserSpecialCallInfo.setPhoneList(userPortraitSpecialCallInfo.getPhone_list());
							xgUserSpecialCallInfo.setCalledCnt(userPortraitSpecialCallInfo.getCalled_cnt());

							xgUserSpecialCallInfoService.saveToNewTab(xgUserSpecialCallInfo);
						}
					}
				}

				// 保存EmergencyAnalysis
				List<EmergencyAnalysis> emergencyAnalysisList = opReport.getEmergency_analysis();
				XgEmergencyAnalysis dd3 = new XgEmergencyAnalysis();
				dd3.setBorrowerId(borrowerId);
				xgEmergencyAnalysisService.deleteToNewTab(dd3);
				if (emergencyAnalysisList != null && emergencyAnalysisList.size() > 0) {
					for (EmergencyAnalysis emergencyAnalysis : emergencyAnalysisList) {
						XgEmergencyAnalysis xgEmergencyAnalysis = new XgEmergencyAnalysis();
						xgEmergencyAnalysis.setBorrowerId(borrowerId);
						xgEmergencyAnalysis.setPhone(emergencyAnalysis.getPhone());
						xgEmergencyAnalysis.setName(emergencyAnalysis.getName());
						xgEmergencyAnalysis.setFirstContactDate(emergencyAnalysis.getFirst_contact_date());
						xgEmergencyAnalysis.setLastContactDate(emergencyAnalysis.getLast_contact_date());
						xgEmergencyAnalysis.setTalkSeconds(emergencyAnalysis.getTalk_seconds());
						xgEmergencyAnalysis.setTalkCnt(emergencyAnalysis.getTalk_cnt());
						xgEmergencyAnalysis.setCallCnt(emergencyAnalysis.getCall_cnt());
						xgEmergencyAnalysis.setCallSeconds(emergencyAnalysis.getCall_seconds());
						xgEmergencyAnalysis.setCalledSeconds(emergencyAnalysis.getCalled_seconds());
						xgEmergencyAnalysis.setCalledCnt(emergencyAnalysis.getCalled_cnt());
						xgEmergencyAnalysis.setMsgCnt(emergencyAnalysis.getMsg_cnt());
						xgEmergencyAnalysis.setSendCnt(emergencyAnalysis.getSend_cnt());
						xgEmergencyAnalysis.setReceiveCnt(emergencyAnalysis.getReceive_cnt());
						xgEmergencyAnalysis.setUnknownCnt(emergencyAnalysis.getUnknown_cnt());
						xgEmergencyAnalysisService.saveToNewTab(xgEmergencyAnalysis);
					}
				}

				// 保存SpecialCate
				List<SpecialCate> specialCateList = opReport.getSpecial_cate();
				XgSpecialCate d = new XgSpecialCate();
				d.setBorrowerId(borrowerId);
				xgSpecialCateService.deleteToNewTab(d);
				XgSpecialCatePhoneDetail d1 = new XgSpecialCatePhoneDetail();
				d1.setBorrowerId(borrowerId);
				xgSpecialCatePhoneDetailService.deleteToNewTab(d1);
				XgSpecialCateMonthDetail d2 = new XgSpecialCateMonthDetail();
				d2.setBorrowerId(borrowerId);
				xgSpecialCateMonthDetailService.deleteToNewTab(d2);
				if (specialCateList != null && specialCateList.size() > 0) {
					for (SpecialCate specialCate : specialCateList) {
						XgSpecialCate xgSpecialCate = new XgSpecialCate();
						xgSpecialCate.setBorrowerId(borrowerId);
						xgSpecialCate.setCallCnt(specialCate.getCall_cnt());
						xgSpecialCate.setCalledCnt(specialCate.getCalled_cnt());
						xgSpecialCate.setCalledSeconds(specialCate.getCalled_seconds());
						xgSpecialCate.setCallSeconds(specialCate.getCall_seconds());
						xgSpecialCate.setTalkCnt(specialCate.getTalk_cnt());
						xgSpecialCate.setTalkSeconds(specialCate.getTalk_seconds());
						xgSpecialCate.setMsgCnt(specialCate.getMsg_cnt());
						xgSpecialCate.setReceiveCnt(specialCate.getReceive_cnt());
						xgSpecialCate.setUnknownCnt(specialCate.getUnknown_cnt());
						xgSpecialCate.setSendCnt(specialCate.getSend_cnt());
						xgSpecialCate.setCate(specialCate.getCate());

						xgSpecialCateService.saveToNewTab(xgSpecialCate);

						// 保存SpecialCate中的 PhoneDetail
						List<SpecialCatePhoneDetail> specialCatePhoneDetailList = specialCate.getPhone_detail();
						if (specialCatePhoneDetailList != null && specialCatePhoneDetailList.size() > 0) {
							for (SpecialCatePhoneDetail specialCatePhoneDetail : specialCatePhoneDetailList) {
								XgSpecialCatePhoneDetail xgSpecialCatePhoneDetail = new XgSpecialCatePhoneDetail();
								xgSpecialCatePhoneDetail.setBorrowerId(borrowerId);
								xgSpecialCatePhoneDetail.setSpecialCateId(xgSpecialCate.getId());
								xgSpecialCatePhoneDetail.setCalledCnt(specialCatePhoneDetail.getCalled_cnt());
								xgSpecialCatePhoneDetail.setTalkSeconds(specialCatePhoneDetail.getTalk_seconds());
								xgSpecialCatePhoneDetail.setTalkCnt(specialCatePhoneDetail.getTalk_cnt());
								xgSpecialCatePhoneDetail.setMsgCnt(specialCatePhoneDetail.getMsg_cnt());
								xgSpecialCatePhoneDetail.setCalledSeconds(specialCatePhoneDetail.getCalled_seconds());
								xgSpecialCatePhoneDetail.setReceiveCnt(specialCatePhoneDetail.getReceive_cnt());
								xgSpecialCatePhoneDetail.setCallCnt(specialCatePhoneDetail.getCall_cnt());
								xgSpecialCatePhoneDetail.setUnknownCnt(specialCatePhoneDetail.getUnknown_cnt());
								xgSpecialCatePhoneDetail.setCalledSeconds(specialCatePhoneDetail.getCalled_seconds());
								xgSpecialCatePhoneDetail.setPhone(specialCatePhoneDetail.getPhone());
								xgSpecialCatePhoneDetail.setUnknownCnt(specialCatePhoneDetail.getUnknown_cnt());
								xgSpecialCatePhoneDetail.setPhoneInfo(specialCatePhoneDetail.getPhone_info());
								xgSpecialCatePhoneDetail.setCallSeconds(specialCatePhoneDetail.getCall_seconds());
								xgSpecialCatePhoneDetail.setSendCnt(specialCatePhoneDetail.getSend_cnt());
								xgSpecialCatePhoneDetailService.saveToNewTab(xgSpecialCatePhoneDetail);
							}
						}
						List<SpecialCateMonthDetail> specialCateMonthDetails = specialCate.getMonth_detail();
						if (specialCateMonthDetails != null && specialCateMonthDetails.size() > 0) {
							for (SpecialCateMonthDetail specialCateMonthDetail : specialCateMonthDetails) {
								XgSpecialCateMonthDetail xgSpecialCateMonthDetail = new XgSpecialCateMonthDetail();
								xgSpecialCateMonthDetail.setBorrowerId(borrowerId);
								xgSpecialCateMonthDetail.setSpecialCateId(xgSpecialCate.getId());

								xgSpecialCateMonthDetail.setCalledCnt(specialCateMonthDetail.getCalled_cnt());
								xgSpecialCateMonthDetail.setTalkSeconds(specialCateMonthDetail.getTalk_seconds());
								xgSpecialCateMonthDetail.setTalkCnt(specialCateMonthDetail.getTalk_cnt());
								xgSpecialCateMonthDetail.setMsgCnt(specialCateMonthDetail.getMsg_cnt());
								xgSpecialCateMonthDetail.setCalledSeconds(specialCateMonthDetail.getCalled_seconds());
								xgSpecialCateMonthDetail.setReceiveCnt(specialCateMonthDetail.getReceive_cnt());
								xgSpecialCateMonthDetail.setCallCnt(specialCateMonthDetail.getCall_cnt());
								xgSpecialCateMonthDetail.setUnknownCnt(specialCateMonthDetail.getUnknown_cnt());
								xgSpecialCateMonthDetail.setCalledSeconds(specialCateMonthDetail.getCalled_seconds());
								xgSpecialCateMonthDetail.setMonth(specialCateMonthDetail.getMonth());
								xgSpecialCateMonthDetail.setCallSeconds(specialCateMonthDetail.getCall_seconds());
								xgSpecialCateMonthDetail.setSendCnt(specialCateMonthDetail.getSend_cnt());
								xgSpecialCateMonthDetailService.saveToNewTab(xgSpecialCateMonthDetail);
							}
						}
					}
				}

				// 保存 monthly_consumption
				List<MonthlyConsumption> monthlyConsumption = opReport.getMonthly_consumption();
				XgMonthlyConsumption d3 = new XgMonthlyConsumption();
				d3.setBorrowerId(borrowerId);
				xgMonthlyConsumptionService.deleteToNewTab(d3);
				if (monthlyConsumption != null && monthlyConsumption.size() > 0) {
					for (MonthlyConsumption consumption : monthlyConsumption) {
						XgMonthlyConsumption xgMonthlyConsumption = new XgMonthlyConsumption();
						xgMonthlyConsumption.setBorrowerId(borrowerId);
						xgMonthlyConsumption.setCalledCnt(consumption.getCalled_cnt());
						xgMonthlyConsumption.setTalkSeconds(consumption.getTalk_seconds());
						xgMonthlyConsumption.setTalkCnt(consumption.getTalk_cnt());
						xgMonthlyConsumption.setMsgCnt(consumption.getMsg_cnt());
						xgMonthlyConsumption.setCalledSeconds(consumption.getCalled_seconds());
						xgMonthlyConsumption.setReceiveCnt(consumption.getReceive_cnt());
						xgMonthlyConsumption.setMonth(consumption.getMonth());
						xgMonthlyConsumption.setCallCnt(consumption.getCall_cnt());
						xgMonthlyConsumption.setUnknownCnt(consumption.getUnknown_cnt());
						xgMonthlyConsumption.setCallConsumption(consumption.getCall_consumption());
						xgMonthlyConsumption.setSendCnt(consumption.getSend_cnt());
						xgMonthlyConsumption.setCallSeconds(consumption.getCall_seconds());
						xgMonthlyConsumptionService.saveToNewTab(xgMonthlyConsumption);
					}
				}

				// 保存 call log
				List<CallLog> callLogList = opReport.getCall_log();
				XgCallLog d4 = new XgCallLog();
				d4.setBorrowerId(borrowerId);
				xgCallLogService.deleteToNewTab(d4);
				XgCallLogDetail d12 = new XgCallLogDetail();
				d12.setBorrowerId(borrowerId);
				xgCallLogDetailService.deleteToNewTab(d12);
				if (callLogList != null && callLogList.size() > 0) {
					for (CallLog callLog : callLogList) {
						XgCallLog xgCallLog = new XgCallLog();
						xgCallLog.setBorrowerId(borrowerId);
						xgCallLog.setContactNonn(callLog.getContact_noon());
						xgCallLog.setTalkSeconds(callLog.getTalk_seconds());
						xgCallLog.setTalkCnt(callLog.getTalk_cnt());
						xgCallLog.setContact3m(callLog.getContact_3m());
						xgCallLog.setMsgCnt(callLog.getMsg_cnt());
						xgCallLog.setContact1m(callLog.getContact_1m());
						xgCallLog.setUnknownCnt(callLog.getUnknown_cnt());
						xgCallLog.setContactEveing(callLog.getContact_eveing());
						xgCallLog.setContact1w(callLog.getContact_1w());
						xgCallLog.setPhoneInfo(callLog.getPhone_info());
						xgCallLog.setCallSeconds(callLog.getCall_seconds());
						xgCallLog.setCallCnt(callLog.getCall_cnt());
						xgCallLog.setCalledCnt(callLog.getCalled_cnt());
						xgCallLog.setContactWeekday(callLog.getContact_weekday());
						xgCallLog.setReceiveCnt(callLog.getReceive_cnt());
						xgCallLog.setPhone(callLog.getPhone());
						xgCallLog.setCallSeconds(callLog.getCall_seconds());
						xgCallLog.setFirstContactDate(callLog.getFirst_contact_date());
						xgCallLog.setContactAfternoon(callLog.getContact_afternoon());
						xgCallLog.setContactEarlyMorning(callLog.getContact_early_morning());
						xgCallLog.setLastContactDate(callLog.getLast_contact_date());
						xgCallLog.setContactNight(callLog.getContact_night());
						xgCallLog.setPhoneLabel(callLog.getPhone_label());
						xgCallLog.setSendCnt(callLog.getSend_cnt());
						xgCallLog.setPhoneLocation(callLog.getPhone_location());
						xgCallLog.setContactMorning(callLog.getContact_morning());
						xgCallLog.setContactWeekend(callLog.getContact_weekend());
						xgCallLog.setCalledSeconds(callLog.getCalled_seconds());
						xgCallLogService.saveToNewTab(xgCallLog);

						List<Detail> s = callLog.getDetail();
						for (Detail detail : s) {
							XgCallLogDetail xgCallLogDetail = new XgCallLogDetail();
							xgCallLogDetail.setBorrowerId(borrowerId);
							xgCallLogDetail.setCallLogId(xgCallLog.getId());
							xgCallLogDetail.setCalledCnt(detail.getCalled_cnt());
							xgCallLogDetail.setTalkSeconds(detail.getTalk_seconds());
							xgCallLogDetail.setTalkCnt(detail.getTalk_cnt());
							xgCallLogDetail.setMsgCnt(detail.getMsg_cnt());
							xgCallLogDetail.setCalledSeconds(detail.getCalled_seconds());
							xgCallLogDetail.setReceiveCnt(detail.getReceive_cnt());
							xgCallLogDetail.setMonth(detail.getMonth());
							xgCallLogDetail.setCallCnt(detail.getCall_cnt());
							xgCallLogDetail.setUnknownCnt(detail.getUnknown_cnt());
							xgCallLogDetail.setSendCnt(detail.getSend_cnt());
							xgCallLogDetail.setCallSeconds(detail.getCall_seconds());
							xgCallLogDetailService.saveToNewTab(xgCallLogDetail);
						}
					}
				}

				// 保存 trip_analysis
				List<TripAnalysis> tripAnalysisList = opReport.getTrip_analysis();
				XgTripAnalysis d6 = new XgTripAnalysis();
				d6.setBorrowerId(borrowerId);
				xgTripAnalysisService.deleteToNewTab(d6);
				XgTripAnalysisDetail d15 = new XgTripAnalysisDetail();
				d15.setBorrowerId(borrowerId);
				xgTripAnalysisDetailService.deleteToNewTab(d15);
				if (tripAnalysisList != null && tripAnalysisList.size() > 0) {
					for (TripAnalysis tripAnalysis : tripAnalysisList) {
						XgTripAnalysis xgtripAnalysis = new XgTripAnalysis();
						xgtripAnalysis.setBorrowerId(borrowerId);

						xgtripAnalysis.setCalledCnt(tripAnalysis.getCalled_cnt());
						xgtripAnalysis.setTalkCnt(tripAnalysis.getTalk_cnt());
						xgtripAnalysis.setTalkSeconds(tripAnalysis.getTalk_seconds());
						xgtripAnalysis.setMsgCnt(tripAnalysis.getMsg_cnt());
						xgtripAnalysis.setCallSeconds(tripAnalysis.getCall_seconds());
						xgtripAnalysis.setReceiveCnt(tripAnalysis.getReceive_cnt());
						xgtripAnalysis.setDateDistribution(tripAnalysis.getDate_distribution());
						xgtripAnalysis.setCallCnt(tripAnalysis.getCall_cnt());
						xgtripAnalysis.setUnknownCnt(tripAnalysis.getUnknown_cnt());
						xgtripAnalysis.setLocation(tripAnalysis.getLocation());
						xgtripAnalysis.setSendCnt(tripAnalysis.getSend_cnt());
						xgtripAnalysis.setCalledSeconds(tripAnalysis.getCalled_seconds());
						xgTripAnalysisService.saveToNewTab(xgtripAnalysis);

						List<Detail> s = tripAnalysis.getDetail();
						for (Detail detail : s) {
							XgTripAnalysisDetail xgTripAnalysisDetail = new XgTripAnalysisDetail();
							xgTripAnalysisDetail.setBorrowerId(borrowerId);
							xgTripAnalysisDetail.setTripAnalysisId(xgtripAnalysis.getId());
							xgTripAnalysisDetail.setCalledCnt(detail.getCalled_cnt());
							xgTripAnalysisDetail.setTalkSeconds(detail.getTalk_seconds());
							xgTripAnalysisDetail.setTalkCnt(detail.getTalk_cnt());
							xgTripAnalysisDetail.setMsgCnt(detail.getMsg_cnt());
							xgTripAnalysisDetail.setCalledSeconds(detail.getCalled_seconds());
							xgTripAnalysisDetail.setReceiveCnt(detail.getReceive_cnt());
							xgTripAnalysisDetail.setMonth(detail.getMonth());
							xgTripAnalysisDetail.setCallCnt(detail.getCall_cnt());
							xgTripAnalysisDetail.setUnknownCnt(detail.getUnknown_cnt());
							xgTripAnalysisDetail.setSendCnt(detail.getSend_cnt());
							xgTripAnalysisDetail.setCallSeconds(detail.getCall_seconds());
							xgTripAnalysisDetailService.saveToNewTab(xgTripAnalysisDetail);
						}
					}
				}

				// 保存 area_analysis
				List<AreaAnalysis> areaAnalysisList = opReport.getArea_analysis();
				XgAreaAnalysisDetail d112 = new XgAreaAnalysisDetail();
				d112.setBorrowerId(borrowerId);
				xgAreaAnalysisDetailService.deleteToNewTab(d112);
				XgAreaAnalysis d8 = new XgAreaAnalysis();
				d8.setBorrowerId(borrowerId);
				xgAreaAnalysisService.deleteToNewTab(d8);

				if (areaAnalysisList != null && areaAnalysisList.size() > 0) {
					for (AreaAnalysis areaAnalysis : areaAnalysisList) {
						XgAreaAnalysis xgAreaAnalysis = new XgAreaAnalysis();
						xgAreaAnalysis.setBorrowerId(borrowerId);

						xgAreaAnalysis.setCalledCnt(areaAnalysis.getCalled_cnt());
						xgAreaAnalysis.setTalkSeconds(areaAnalysis.getTalk_seconds());
						xgAreaAnalysis.setTalkCnt(areaAnalysis.getTalk_cnt());
						xgAreaAnalysis.setArea(areaAnalysis.getArea());
						xgAreaAnalysis.setReceiveCnt(areaAnalysis.getReceive_cnt());
						xgAreaAnalysis.setCallSeconds(areaAnalysis.getCall_seconds());
						xgAreaAnalysis.setMsgCnt(areaAnalysis.getMsg_cnt());
						xgAreaAnalysis.setCallCnt(areaAnalysis.getCall_cnt());
						xgAreaAnalysis.setUnknownCnt(areaAnalysis.getUnknown_cnt());
						xgAreaAnalysis.setContact_phoneCnt(areaAnalysis.getContact_phone_cnt());
						xgAreaAnalysis.setSendCnt(areaAnalysis.getSend_cnt());
						xgAreaAnalysis.setCalledSeconds(areaAnalysis.getCalled_seconds());
						xgAreaAnalysisService.saveToNewTab(xgAreaAnalysis);

						List<AreaAnalysisDetail> s = areaAnalysis.getDetail();
						for (AreaAnalysisDetail detail : s) {
							XgAreaAnalysisDetail xgTripAnalysisDetail = new XgAreaAnalysisDetail();
							xgTripAnalysisDetail.setBorrowerId(borrowerId);
							xgTripAnalysisDetail.setAreaAnalysisId(xgAreaAnalysis.getId());

							xgTripAnalysisDetail.setCalledCnt(detail.getCalled_cnt());
							xgTripAnalysisDetail.setTalkSeconds(detail.getTalk_seconds());
							xgTripAnalysisDetail.setTalkCnt(detail.getTalk_cnt());
							xgTripAnalysisDetail.setMsgCnt(detail.getMsg_cnt());
							xgTripAnalysisDetail.setCalledSeconds(detail.getCalled_seconds());
							xgTripAnalysisDetail.setReceiveCnt(detail.getReceive_cnt());
							xgTripAnalysisDetail.setMonth(detail.getMonth());
							xgTripAnalysisDetail.setCallCnt(detail.getCall_cnt());
							xgTripAnalysisDetail.setUnknownCnt(detail.getUnknown_cnt());
							xgTripAnalysisDetail.setContactPhoneCnt(detail.getContact_phone_cnt());
							xgTripAnalysisDetail.setSendCnt(detail.getSend_cnt());
							xgTripAnalysisDetail.setCallSeconds(detail.getCall_seconds());
							xgAreaAnalysisDetailService.saveToNewTab(xgTripAnalysisDetail);
						}
					}
				}
				//保存成功后往redis扔一条记录
			} catch (Exception e) {
				logger.error("~~~~~~~~~~~~~~~ borrowerId:{}运营商报表保存异常：", borrowerId,e.getMessage());
				e.printStackTrace();
				throw new Exception("保存运营商数据失败，系统异常~");
			}
		}

	@Override
	public void saveXgData2(JSONObject jsonObject, Long borrowerId) throws Exception {
		try {
			String objStr = jsonObject.getString("obj");

			JSONObject xgJson = JSON.parseObject(objStr);
			XGReturn xgReturn = JSONObject.toJavaObject(xgJson, XGReturn.class);

			if (xgReturn != null && !CommUtils.isNull(xgReturn.getScore())) {
				// 更新主表
				XgOverall xgOverall = xgOverallService.findXgOverall(borrowerId);
				if (xgOverall != null) {
					xgOverall.setScore(xgReturn.getScore());
					xgOverallService.updateXgOverall(xgOverall);
				}

				MidScore midScore = xgReturn.getMid_score();
				if (midScore != null) {
					XgMidScore xgMidScore = new XgMidScore();
					xgMidScore.setBorrowerId(borrowerId);
					xgMidScoreService.deleteZ(xgMidScore);

					xgMidScore.setAprvScore(midScore.getAprv_score());
					xgMidScore.setDangerScore(midScore.getDanger_score());
					xgMidScore.setInstallmentLoanScore(midScore.getInstallment_loan_score());
					xgMidScore.setLoanScore(midScore.getLoan_score());
					xgMidScore.setOpScore(midScore.getOp_score());
					xgMidScore.setPaydayLoanScore(midScore.getPayday_loan_score());
					xgMidScore.setQaScore(midScore.getQa_score());
					xgMidScore.setZhimaScore(midScore.getZhima_score());
					xgMidScoreService.save(xgMidScore);
				}
			}

		} catch (Exception e) { 
			logger.error("~~~~~~~~~~~~~~~ 西瓜分数据异常：", e);
		}
	}

	@Override
	public void saveXgData2ToNewTab(JSONObject jsonObject, Long borrowerId) throws Exception {
		try {
			String objStr = jsonObject.getString("obj");

			JSONObject xgJson = JSON.parseObject(objStr);
			XGReturn xgReturn = JSONObject.toJavaObject(xgJson, XGReturn.class);

			if (xgReturn != null && !CommUtils.isNull(xgReturn.getScore())) {
				// 更新主表
				XgOverall xgOverall = xgOverallService.findXgOverall(borrowerId);
				if (xgOverall != null) {
					xgOverall.setScore(xgReturn.getScore());
					xgOverallService.updateXgOverall(xgOverall);
				}

				MidScore midScore = xgReturn.getMid_score();
				if (midScore != null) {
					XgMidScore xgMidScore = new XgMidScore();
					xgMidScore.setBorrowerId(borrowerId);
					xgMidScoreService.deleteZ(xgMidScore);

					xgMidScore.setAprvScore(midScore.getAprv_score());
					xgMidScore.setDangerScore(midScore.getDanger_score());
					xgMidScore.setInstallmentLoanScore(midScore.getInstallment_loan_score());
					xgMidScore.setLoanScore(midScore.getLoan_score());
					xgMidScore.setOpScore(midScore.getOp_score());
					xgMidScore.setPaydayLoanScore(midScore.getPayday_loan_score());
					xgMidScore.setQaScore(midScore.getQa_score());
					xgMidScore.setZhimaScore(midScore.getZhima_score());
					xgMidScoreService.save(xgMidScore);
				}
			}

		} catch (Exception e) {
			logger.error("~~~~~~~~~~~~~~~ 西瓜分数据异常：", e);
		}
	}
}
