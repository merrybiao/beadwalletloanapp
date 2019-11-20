package com.waterelephant.mf.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.shujumohe.ShujumoheSDKService;
import com.waterelephant.entity.BwMoheAuthRecord;
import com.waterelephant.exception.BusinessException;
import com.waterelephant.mf.service.MfBussinessService;
import com.waterelephant.service.BwMfAccountInfoService;
import com.waterelephant.service.BwMfActiveSilenceStatsService;
import com.waterelephant.service.BwMfAllContactDetailService;
import com.waterelephant.service.BwMfAllContactStatsPerMonthService;
import com.waterelephant.service.BwMfAllContactStatsService;
import com.waterelephant.service.BwMfBaseInfoService;
import com.waterelephant.service.BwMfBehaviorAnalysisService;
import com.waterelephant.service.BwMfBehaviorScoreService;
import com.waterelephant.service.BwMfBillInfoService;
import com.waterelephant.service.BwMfCallAreaStatsPerCityService;
import com.waterelephant.service.BwMfCallDurationStats2hourService;
import com.waterelephant.service.BwMfCallInfoService;
import com.waterelephant.service.BwMfCarrierConsumptionStatsPerMonthService;
import com.waterelephant.service.BwMfCarrierConsumptionStatsService;
import com.waterelephant.service.BwMfContactAreaStatsPerCityService;
import com.waterelephant.service.BwMfContactBlacklistAnalysisService;
import com.waterelephant.service.BwMfContactCreditscoreAnalysisService;
import com.waterelephant.service.BwMfContactGangFraudAnalysisService;
import com.waterelephant.service.BwMfContactManyheadsAnalysisService;
import com.waterelephant.service.BwMfContactSuspectCollectionAnalysisService;
import com.waterelephant.service.BwMfDataCompletenessService;
import com.waterelephant.service.BwMfDataInfoService;
import com.waterelephant.service.BwMfEmergencyContactDetailService;
import com.waterelephant.service.BwMfFamilyInfoService;
import com.waterelephant.service.BwMfFinanceContactDetailService;
import com.waterelephant.service.BwMfFinanceContactStatsService;
import com.waterelephant.service.BwMfHomeTelDetailService;
import com.waterelephant.service.BwMfInfoCheckService;
import com.waterelephant.service.BwMfInfoMatchService;
import com.waterelephant.service.BwMfMobileInfoService;
import com.waterelephant.service.BwMfPackageUsageService;
import com.waterelephant.service.BwMfPaymentInfoService;
import com.waterelephant.service.BwMfReportInfoService;
import com.waterelephant.service.BwMfRiskContactDetailService;
import com.waterelephant.service.BwMfRiskContactStatsService;
import com.waterelephant.service.BwMfServiceContactStatsService;
import com.waterelephant.service.BwMfSmsInfoService;
import com.waterelephant.service.BwMfTravelTrackAnalysisPerCityService;
import com.waterelephant.service.BwMfUserInfoService;
import com.waterelephant.service.BwMfWorkTelDetailService;
import com.waterelephant.service.BwMoheAuthRecordService;
import com.waterelephant.utils.GzipUtil;
import com.waterelephant.utils.StringUtil;
/**
 * 魔方运营商数据
 * @author dinglinhao
 * @date 2018年12月29日11:57:02
 * @deprecat  
 */
@Service
public class MfBussinessServiceImpl implements MfBussinessService {
	
	private Logger logger = LoggerFactory.getLogger(MfBussinessServiceImpl.class);
	
	@Autowired
	private BwMoheAuthRecordService bwMoheAuthRecordService;
	
	@Autowired
	private BwMfBillInfoService bwMfBillInfoService;
	
	@Autowired
	private BwMfDataInfoService bwMfDataInfoService;
	
	@Autowired
	private BwMfFamilyInfoService bwMfFamilyInfoService;

	@Autowired
	private BwMfSmsInfoService bwMfSmsInfoService;
	
	@Autowired
	private BwMfAccountInfoService bwMfAccountInfoService;
	
	@Autowired
	private BwMfBaseInfoService bwMfBaseInfoService;
	
	@Autowired
	private BwMfPaymentInfoService bwMfPaymentInfoService;
	
	@Autowired
	private BwMfPackageUsageService bwMfPackageUsageService; 
	
	@Autowired
	private BwMfCallInfoService bwMfCallInfoService;
	
	@Autowired
	private BwMfCallAreaStatsPerCityService bwMfCallAreaStatsPerCityService;
	
	@Autowired
	private BwMfFinanceContactDetailService bwMfFinanceContactDetailService;
	
	@Autowired
	private BwMfBehaviorScoreService bwMfBehaviorScoreService;
	
	@Autowired
	private BwMfHomeTelDetailService bwMfHomeTelDetailService;
	
	@Autowired
	private BwMfRiskContactStatsService bwMfRiskContactStatsService;
	
	@Autowired
	private BwMfAllContactStatsPerMonthService bwMfAllContactStatsPerMonthService;
	
	@Autowired
	private BwMfActiveSilenceStatsService bwMfActiveSilenceStatsService;
	
	@Autowired
	private BwMfCallDurationStats2hourService bwMfCallDurationStats2hourService;
	
	@Autowired
	private BwMfAllContactDetailService bwMfAllContactDetailService;
	
	@Autowired
	private BwMfServiceContactStatsService bwMfServiceContactStatsService;
	
	@Autowired
	private BwMfContactBlacklistAnalysisService bwMfContactBlacklistAnalysisService;
	
	@Autowired
	private BwMfInfoMatchService bwMfInfoMatchService;
	
	@Autowired
	private BwMfInfoCheckService bwMfInfoCheckService;
	@Autowired
	private BwMfWorkTelDetailService bwMfWorkTelDetailService;
	
	@Autowired
	private BwMfAllContactStatsService bwMfAllContactStatsService;
	
	@Autowired
	private BwMfRiskContactDetailService bwMfRiskContactDetailService;
	
	@Autowired
	private BwMfBehaviorAnalysisService bwMfBehaviorAnalysisService;
	
	@Autowired
	private BwMfContactCreditscoreAnalysisService bwMfContactCreditscoreAnalysisService;
	
	@Autowired
	private BwMfUserInfoService bwMfUserInfoService;
	
	@Autowired
	private BwMfContactSuspectCollectionAnalysisService bwMfContactSuspectCollectionAnalysisService;
	
	@Autowired
	private BwMfDataCompletenessService bwMfDataCompletenessService;
	
	@Autowired
	private BwMfFinanceContactStatsService bwMfFinanceContactStatsService;
	
	@Autowired
	private BwMfReportInfoService bwMfReportInfoService;
	
	@Autowired
	private BwMfContactGangFraudAnalysisService bwMfContactGangFraudAnalysisService;
	
	@Autowired
	private BwMfCarrierConsumptionStatsPerMonthService bwMfCarrierConsumptionStatsPerMonthService;
	
	@Autowired
	private BwMfEmergencyContactDetailService bwMfEmergencyContactDetailService;
	
	@Autowired
	private BwMfTravelTrackAnalysisPerCityService bwMfTravelTrackAnalysisPerCityService;
	
	@Autowired
	private BwMfContactManyheadsAnalysisService bwMfContactManyheadsAnalysisService;
	
	@Autowired
	private BwMfContactAreaStatsPerCityService bwMfContactAreaStatsPerCityService;
	
	@Autowired
	private BwMfCarrierConsumptionStatsService bwMfCarrierConsumptionStatsService;
	
	@Autowired
	private BwMfMobileInfoService bwMfMobileInfoService;
	
	@Override
	public Map<String,Object> queryTaskReport(String taskId,boolean gzip){

		BwMoheAuthRecord authRecord = bwMoheAuthRecordService.queryAuthRecordByTaskId(taskId);
		
		if(null == authRecord) throw new BusinessException("该任务没有授权记录或taskId无效~");
		
		String result = ShujumoheSDKService.getTaskReport(taskId, authRecord.getRealName(), authRecord.getIdcardNum());
		
		Assert.hasText(result, "查询报告失败~");
		
		JSONObject jsonObject = JSON.parseObject(result);
		
		String code = jsonObject.getString("code");
		
		if(StringUtils.isEmpty(code) || !"0000".equals(code)) throw new BusinessException(jsonObject.getString("msg"));
		
		JSONObject jsonResult = jsonObject.getJSONObject("result");
		
		Integer moheCode = jsonResult.getInteger("code");
		
		if(null == moheCode || 0 != moheCode.intValue()) throw new BusinessException(jsonResult.getString("msg"));
		
		String data = jsonResult.getString("data");
		
		Map<String,Object>  resultMap = new HashMap<>();
		
		String strData = ShujumoheSDKService.gunzip(data);
		
		if(gzip) {
			strData = GzipUtil.gzip(strData);
			resultMap.put("data", strData);
		}else {
			resultMap.put("data", JSON.parse(strData));
		}
		return resultMap;
	}
	
	
	@Override
	public Map<String,Object> queryTaskData(String taskId,boolean gzip){

		BwMoheAuthRecord authRecord = bwMoheAuthRecordService.queryAuthRecordByTaskId(taskId);
		
		if(null == authRecord) throw new BusinessException("该任务没有授权记录或taskId无效~");
		
		String result = ShujumoheSDKService.queryTaskData(taskId);
		
		Assert.hasText(result, "查询报告失败~");
		
		JSONObject jsonObject = JSON.parseObject(result);
		
		String code = jsonObject.getString("code");
		
		if(StringUtils.isEmpty(code) || !"0000".equals(code)) throw new BusinessException(jsonObject.getString("msg"));
		
		JSONObject jsonResult = jsonObject.getJSONObject("result");
		
		Integer moheCode = jsonResult.getInteger("code");
		
		if(null == moheCode || 0 != moheCode.intValue()) throw new BusinessException(jsonResult.getString("message"));
		
		String data = jsonResult.getString("data");
		
		Map<String,Object>  resultMap = new HashMap<>();
		
		if(gzip) {
			
			resultMap.put("data", GzipUtil.gzip(data));
		}else {
			resultMap.put("data", jsonResult.getJSONObject("data"));
		}
		return resultMap;
	}
	
	
	@Override
	public Map<String, Object> queryTaskReport(String taskId, String partnerCode, boolean gzip) {
		
		String result = null;
		if(StringUtils.isEmpty(partnerCode)) partnerCode = "sxfq1_mohe";
		switch (partnerCode) {
			case "sxfq_mohe"://上海
				result = ShujumoheSDKService.queryShTaskReport(taskId);
				break;
			case "sxfq1_mohe":
				result = ShujumoheSDKService.getTaskReport(taskId);
				break;
			case "lefq_mohe"://上海 乐分期
				result = ShujumoheSDKService.getLefqTaskReport(taskId);
				break;
			default:
				throw new IllegalArgumentException("传入参数[appId]不合法~");
		}
		
		Assert.hasText(result, "查询报告失败~");
		
		
		JSONObject jsonObject = JSON.parseObject(result);
		
		String code = jsonObject.getString("code");
		
		if(StringUtils.isEmpty(code) || !"0000".equals(code)) throw new BusinessException(jsonObject.getString("msg"));
		
		JSONObject jsonResult = jsonObject.getJSONObject("result");
		
		Integer moheCode = jsonResult.getInteger("code");
		
		//上海 2019年3月13日晚打版本 lefq_mohe授权停用，切换成sxfq_mohe，未兼容之前的运营商认证做如下特殊处理：用lefq_mohe查询返回2414后，再用sxfq_mohe查一遍
		if("lefq_mohe".equals(partnerCode) && moheCode == 2414) {
			partnerCode = "sxfq_mohe";//上海
			return queryTaskReport(taskId, partnerCode, gzip);
		}
		
		if(null == moheCode || 0 != moheCode.intValue()) throw new BusinessException(jsonResult.getString("msg"));
		
		String data = jsonResult.getString("data");
		
		Map<String,Object>  resultMap = new HashMap<>();
		resultMap.put("error", 200);
		resultMap.put("msg", jsonResult.getString("msg"));
		
		String strData = ShujumoheSDKService.gunzip(data);
		
		if(gzip) {
			strData = GzipUtil.gzip(strData);
			resultMap.put("data", strData);
		}else {
			resultMap.put("data", JSON.parse(strData));
		}
		return resultMap;
	}
	
	
	@Override
	public Map<String, Object> queryTaskData(String taskId, String partnerCode, boolean gzip) {
		
		String result = null;
		if(StringUtils.isEmpty(partnerCode)) partnerCode = "sxfq1_mohe";
		switch (partnerCode) {
			case "sxfq_mohe"://上海 水象分期  和 七七钱包
				result = ShujumoheSDKService.queryShTaskData(taskId);
				break;
			case "sxfq1_mohe": //自有
				result = ShujumoheSDKService.queryTaskData(taskId);
				break;
			case "lefq_mohe"://上海 乐分期
				result = ShujumoheSDKService.queryLefqTaskData(taskId);
				break;
			default:
				throw new IllegalArgumentException("传入参数[appId]不合法~");
		}
		
		Assert.hasText(result, "查询报告失败~");
		
		JSONObject jsonObject = JSON.parseObject(result);
		
		String code = jsonObject.getString("code");
		
		if(StringUtils.isEmpty(code) || !"0000".equals(code)) throw new BusinessException(jsonObject.getString("msg"));
		
		JSONObject jsonResult = jsonObject.getJSONObject("result");
		
		Integer moheCode = jsonResult.getInteger("code");
		
		//上海 2019年3月13日晚打版本 lefq_mohe授权停用，切换成sxfq_mohe，未兼容之前的运营商认证做如下特殊处理：用lefq_mohe查询返回2414后，再用sxfq_mohe查一遍
		if("lefq_mohe".equals(partnerCode) && moheCode == 2414) {
			partnerCode = "sxfq_mohe";//上海
			return queryTaskData(taskId, partnerCode, gzip);
		}
		
		if(null == moheCode || 0 != moheCode.intValue()) throw new BusinessException(jsonResult.getString("message")+"["+moheCode+"]");
		
		String data = jsonResult.getString("data");
		
		Map<String,Object>  resultMap = new HashMap<>();
		if(gzip) {
			resultMap.put("data", GzipUtil.gzip(data));
		}else {
			resultMap.put("data", jsonResult.getJSONObject("data"));
		}
		
		return resultMap;
	}
	
	@Override
	public boolean saveTaskReport(String taskId,Long orderId) {
		//判断入参是否为空
		Assert.hasText(taskId, "参数[taskId]不能为空~");
		//查询运营商数据
		String result  = ShujumoheSDKService.getTaskReport(taskId);
		//判断结果是否为空或空字符串
		Assert.hasText(result, "[taskId="+taskId+"]查询运营商数据返回结果为空~");
		//判断是否是JSON字符串
		if(!StringUtil.isJson(result)) {
			logger.error("----[魔方]根据taskId:{}查询运营商数据返回结果异常：{}----",taskId,result);
			throw new BusinessException("[taskId="+taskId+"]查询运营商数据返回结果不是json字符串~");
		}
		//解析返回结果成JSON
		JSONObject jsonObject = JSON.parseObject(result);
		//获取返回结果代码
		String code = jsonObject.getString("code");
		//异常返回结果
		if(StringUtils.isEmpty(code) || !"0000".equals(code)) throw new BusinessException(jsonObject.getString("msg"));
		//结果数据
		JSONObject jsonResult = jsonObject.getJSONObject("result");
		//魔方返回代码
		Integer moheCode = jsonResult.getInteger("code");
		//魔方异常返回代码及描述
		if(null == moheCode || 0 != moheCode.intValue()) throw new BusinessException(jsonResult.getString("msg")+"["+moheCode+"]");
		//获取data节点
		String gzData = jsonResult.getString("data");
		//判断空
		Assert.hasText(gzData,"解析魔方运营商报告data节点失败~");
		//解压gzData
		String data = ShujumoheSDKService.gunzip(gzData);
		//转化成JSON对象
		JSONObject reportData = JSON.parseObject(data);
		//申明保存数据开始时间戳
		long star = System.currentTimeMillis();
		try {
			//travel_track_analysis_per_city
			this.bwMfTravelTrackAnalysisPerCityService.saveTravelTrackAnalysisPerCity(taskId,orderId,reportData);
			//contact_manyheads_analysis
			this.bwMfContactManyheadsAnalysisService.saveContactManyheadsAnalysis(taskId,orderId,reportData);
			//contact_area_stats_per_city
			this.bwMfContactAreaStatsPerCityService.saveContactAreaStatsPerCity(taskId,orderId,reportData);
			//mobile_info
			this.bwMfMobileInfoService.saveMobileInfo(taskId,orderId,reportData);
			//carrier_consumption_stats
			this.bwMfCarrierConsumptionStatsService.saveCarrierConsumptionStats(taskId,orderId,reportData);
			//emergency_contact1_detail
			//emergency_contact2_detail
			//emergency_contact3_detail
			//emergency_contact4_detail
			//emergency_contact5_detail
			this.bwMfEmergencyContactDetailService.saveEmergencyContactDetail(taskId,orderId,reportData);
			//carrier_consumption_stats_per_month
			this.bwMfCarrierConsumptionStatsPerMonthService.saveCarrierConsumptionStatsPerMonth(taskId,orderId,reportData);
			//contact_gang_fraud_analysis
			this.bwMfContactGangFraudAnalysisService.saveContactGangFraudAnalysis(taskId,orderId,reportData);
			//report_info
			this.bwMfReportInfoService.saveReportInfo(taskId,orderId,reportData);
			//finance_contact_stats
			this.bwMfFinanceContactStatsService.saveFinanceContactStats(taskId,orderId,reportData);
			//data_completeness
			this.bwMfDataCompletenessService.saveDataCompleteness(taskId,orderId,reportData);
			//contact_suspect_collection_analysis
			this.bwMfContactSuspectCollectionAnalysisService.saveContactSuspectCollectionAnalysis(taskId,orderId,reportData);
			//user_info
			this.bwMfUserInfoService.saveUserInfo(taskId,orderId,reportData);
			//top10_contact_detail_month
			//contact_creditscore_analysis
			this.bwMfContactCreditscoreAnalysisService.saveContactCreditscoreAnalysis(taskId,orderId,reportData);
			//behavior_analysis
			this.bwMfBehaviorAnalysisService.saveBehaviorAnalysis(taskId,orderId,reportData);
			//risk_contact_detail
			this.bwMfRiskContactDetailService.saveRiskContactDetail(taskId,orderId,reportData);
			//all_contact_stats
			this.bwMfAllContactStatsService.saveAllContactStats(taskId,orderId,reportData);
			//work_tel_detail
			this.bwMfWorkTelDetailService.saveWorkTelDetail(taskId,orderId,reportData);
			//info_check
			this.bwMfInfoCheckService.saveInfoCheck(taskId,orderId,reportData);
			//info_match
			this.bwMfInfoMatchService.saveInfoMatch(taskId,orderId,reportData);
			//contact_blacklist_analysis
			this.bwMfContactBlacklistAnalysisService.saveContactBlacklistAnalysis(taskId,orderId,reportData);
			//top5_contact_detail_week
			//service_contact_stats
			this.bwMfServiceContactStatsService.saveServiceContactStats(taskId,orderId,reportData);
			//all_contact_detail
			this.bwMfAllContactDetailService.saveAllContactDetail(taskId,orderId,reportData);
			//call_duration_stats_2hour
			this.bwMfCallDurationStats2hourService.saveCallDurationStats2hour(taskId,orderId,reportData);
			//active_silence_stats
			this.bwMfActiveSilenceStatsService.saveActiveSilenceStats(taskId,orderId,reportData);
			//all_contact_stats_per_month
			this.bwMfAllContactStatsPerMonthService.saveAllContactStatsPerMonth(taskId,orderId,reportData);
			//risk_contact_stats
			this.bwMfRiskContactStatsService.saveRiskContactStats(taskId,orderId,reportData);
			//home_tel_detail
			this.bwMfHomeTelDetailService.saveHomeTelDetail(taskId,orderId,reportData);
			//behavior_score
			this.bwMfBehaviorScoreService.saveBehaviorScore(taskId,orderId,reportData);
			//finance_contact_detail
			this.bwMfFinanceContactDetailService.saveFinanceContactDetail(taskId,orderId,reportData);
			//call_area_stats_per_city
			this.bwMfCallAreaStatsPerCityService.saveCallAreaStatsPerCity(taskId,orderId,reportData);
		} catch (Exception e) {
			logger.error("----【魔方】---运营商报告保存异常，taskId:{},orderId:{},异常：{}----",taskId,orderId,e.getMessage());
			e.printStackTrace();
			throw new BusinessException("保存[魔方]运营商报告失败~");
		} finally {
			long end = System.currentTimeMillis();
			logger.info("----【魔方】saveTaskReport方法耗时：{}ms----",(end-star));
		}
		
		return true;
	}
	
	public boolean saveTaskData(String taskId,Long orderId) {
		//判断入参是否为空
		Assert.hasText(taskId, "参数[taskId]不能为空~");
		//查询运营商数据
		String result  = ShujumoheSDKService.queryTaskData(taskId);
		//判断结果是否为空或空字符串
		Assert.hasText(result, "[taskId="+taskId+"]查询运营商数据返回结果为空~");
		//判断是否是JSON字符串
		if(!StringUtil.isJson(result)) {
			logger.error("----[魔方]根据taskId:{}查询运营商数据返回结果异常：{}----",taskId,result);
			throw new BusinessException("[taskId="+taskId+"]查询运营商数据返回结果不是json字符串~");
		}
		//解析返回结果成JSON
		JSONObject jsonObject = JSON.parseObject(result);
		
		String code = jsonObject.getString("code");
		//异常返回结果
		if(StringUtils.isEmpty(code) || !"0000".equals(code)) throw new BusinessException(jsonObject.getString("message"));
		//结果数据
		JSONObject jsonResult = jsonObject.getJSONObject("result");
		//魔方返回代码
		Integer moheCode = jsonResult.getInteger("code");
		//魔方异常返回代码及描述
		if(null == moheCode || 0 != moheCode.intValue()) throw new BusinessException(jsonResult.getString("message")+"["+moheCode+"]");
		
		JSONObject data = jsonResult.getJSONObject("data");
		
		JSONObject taskData = data.getJSONObject("task_data");
		
		if(null == taskData || taskData.isEmpty()) return false;
		//申明保存开始时间
		long star = System.currentTimeMillis();
		try {
			//bill_info
			this.bwMfBillInfoService.saveBillInfo(taskId,orderId,taskData);
					
			//family_info
			this.bwMfFamilyInfoService.saveFamilyInfo(taskId,orderId,taskData);
			
			//data_info
			this.bwMfDataInfoService.saveDataInfo(taskId,orderId,taskData);
			
			//sms_info
			this.bwMfSmsInfoService.saveSmsInfo(taskId,orderId,taskData);
			
			//account_info
			this.bwMfAccountInfoService.saveAccountInfo(taskId,orderId,taskData);
			
			//base_info
			this.bwMfBaseInfoService.saveBaseInfo(taskId,orderId,taskData);
			
			//payment_info
			this.bwMfPaymentInfoService.savePaymentInfo(taskId,orderId,taskData);
			
			//package_usage
			this.bwMfPackageUsageService.savePackageUsage(taskId,orderId,taskData);
			
			//call_info
			this.bwMfCallInfoService.saveCallInfo(taskId,orderId,taskData);
		} catch (Exception e) {
			logger.error("----【魔方】---运营商原始数据保存异常，taskId:{},orderId:{},异常：{}----",taskId,orderId,e.getMessage());
			e.printStackTrace();
			throw new BusinessException("保存[魔方]运营商原始数据失败~");
		} finally {
			long end = System.currentTimeMillis();
			logger.info("----【魔方】saveTaskData方法耗时：{}ms----",(end-star));
		}
		return true;
	}

}
