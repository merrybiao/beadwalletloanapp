//
//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Objects;
//import java.util.Set;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.math.NumberUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.beadwallet.service.utils.HttpClientHelper;
//import com.beadwallet.utils.JsonUtils;
//import com.waterelephant.dto.SystemAuditDto;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwKaBaoOperatorBase;
//import com.waterelephant.entity.BwKaBaoOriginBaseInfo;
//import com.waterelephant.entity.BwKaBaoOriginBillInfo;
//import com.waterelephant.entity.BwKaBaoOriginCallInfo;
//import com.waterelephant.entity.BwKabaoOperatorRiskprofile;
//import com.waterelephant.entity.BwKabaoOperatorStatistics;
//import com.waterelephant.entity.BwKabaoOperatorWecashprofile;
//import com.waterelephant.entity.BwKabaoPhoneTagInfo;
//import com.waterelephant.entity.BwKabaoReport;
//import com.waterelephant.entity.BwKabaoReportAddressList;
//import com.waterelephant.entity.BwKabaoReportBillInfo;
//import com.waterelephant.entity.BwKabaoReportContactDict;
//import com.waterelephant.entity.BwKabaoReportContactLabelDict;
//import com.waterelephant.entity.BwKabaoReportInfoList;
//import com.waterelephant.entity.BwKabaoReportTopCallList;
//import com.waterelephant.entity.BwKabaoReportTotalContactInfo;
//import com.waterelephant.entity.BwKabaoReportTotalDict;
//import com.waterelephant.entity.BwKabaoTopFiveCallAddress;
//import com.waterelephant.entity.BwKabaoTopTenCallsInThreeMonth;
//import com.waterelephant.entity.BwKabaoTopTenCallsOutThreeMonth;
//import com.waterelephant.entity.BwKabaoTotalContactInfo;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderProcessRecord;
//import com.waterelephant.entity.BwOrderRong;
//import com.waterelephant.entity.BwThirdOperateBasic;
//import com.waterelephant.entity.BwThirdOperateVoice;
//import com.waterelephant.service.BwKaBaoOperatorBaseService;
//import com.waterelephant.service.BwKaBaoOriginBaseInfoService;
//import com.waterelephant.service.BwKaBaoOriginBillInfoService;
//import com.waterelephant.service.BwKaBaoOriginCallInfoService;
//import com.waterelephant.service.BwKabaoOperatorRiskprofileService;
//import com.waterelephant.service.BwKabaoOperatorStatisticsService;
//import com.waterelephant.service.BwKabaoOperatorWecashprofileService;
//import com.waterelephant.service.BwKabaoPhoneTagInfoService;
//import com.waterelephant.service.BwKabaoReportAddressListService;
//import com.waterelephant.service.BwKabaoReportBillInfoService;
//import com.waterelephant.service.BwKabaoReportContactDictService;
//import com.waterelephant.service.BwKabaoReportContactLabelDictService;
//import com.waterelephant.service.BwKabaoReportInfoListService;
//import com.waterelephant.service.BwKabaoReportService;
//import com.waterelephant.service.BwKabaoReportTopCallListService;
//import com.waterelephant.service.BwKabaoReportTotalContactInfoService;
//import com.waterelephant.service.BwKabaoReportTotalDictService;
//import com.waterelephant.service.BwKabaoTopFiveCallAddressService;
//import com.waterelephant.service.BwKabaoTopTenCallsInThreeMonthService;
//import com.waterelephant.service.BwKabaoTopTenCallsOutThreeMonthService;
//import com.waterelephant.service.BwKabaoTotalContactInfoService;
//import com.waterelephant.service.BwOrderProcessRecordService;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.BwThirdOperateBasicService;
//import com.waterelephant.service.BwThirdOperateVoiceService;
//import com.waterelephant.sxyDrainage.entity.kabao.BillInfoOrigin;
//import com.waterelephant.sxyDrainage.entity.kabao.CallInfo;
//import com.waterelephant.sxyDrainage.entity.kabao.CallInfoDetails;
//import com.waterelephant.sxyDrainage.entity.kabao.DataBaseInfo;
//import com.waterelephant.sxyDrainage.entity.kabao.DataBillInfoOrigin;
//import com.waterelephant.sxyDrainage.entity.kabao.DataCallInfo;
//import com.waterelephant.sxyDrainage.entity.kabao.DetailsBillInfoOrigin;
//import com.waterelephant.sxyDrainage.entity.kabao.KaBaoOperator;
//import com.waterelephant.sxyDrainage.entity.kabao.OperatorBase;
//import com.waterelephant.sxyDrainage.entity.kabao.OperatorCallprofile;
//import com.waterelephant.sxyDrainage.entity.kabao.OperatorRiskprofile;
//import com.waterelephant.sxyDrainage.entity.kabao.OperatorStatistics;
//import com.waterelephant.sxyDrainage.entity.kabao.OperatorWecashprofile;
//import com.waterelephant.sxyDrainage.entity.kabao.Origin;
//import com.waterelephant.sxyDrainage.entity.kabao.PhoneTagInfo;
//import com.waterelephant.sxyDrainage.entity.kabao.TopFiveCallAddress;
//import com.waterelephant.sxyDrainage.entity.kabao.TopTenCallsInThreeMonth;
//import com.waterelephant.sxyDrainage.entity.kabao.TopTenCallsOutThreeMonth;
//import com.waterelephant.sxyDrainage.entity.kabao.TotalContactInfo;
//import com.waterelephant.sxyDrainage.entity.kabao.Transportation;
//import com.waterelephant.sxyDrainage.service.AsyncKaBaoTask;
//import com.waterelephant.sxyDrainage.utils.kabao.ConvertSecondUtil;
//import com.waterelephant.sxyDrainage.utils.kabao.CopyBeanPluginsUtil;
//import com.waterelephant.sxyDrainage.utils.kabao.KaBaoConstant;
//import com.waterelephant.utils.DateUtil;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.SystemConstant;
//
///**
// * ClassName:AsyncKaBaoTaskImpl <br/>
// * Function: 异步处理运营商数据  <br/>
// * @author   liwanliang
// * @version   1.0  
// */
//@Service
//public class AsyncKaBaoTaskImpl implements AsyncKaBaoTask {
//
//	private Logger logger = LoggerFactory.getLogger(AsyncKaBaoTask.class);
//	
//	@Autowired
//	private BwKaBaoOperatorBaseService bwKaBaoOperatorBaseService;
//	
//	@Autowired
//	private BwKaBaoOriginBaseInfoService bwKaBaoOriginBaseInfoService;
//	
//	@Autowired
//	private BwKaBaoOriginBillInfoService bwKaBaoOriginBillInfoService;
//	
//	@Autowired
//	private BwKaBaoOriginCallInfoService bwKaBaoOriginCallInfoService;
//	
//	@Autowired
//	private BwKabaoOperatorRiskprofileService bwKaBaoOperatorRiskprofileService;
//	
//	@Autowired
//	private BwKabaoOperatorWecashprofileService bwKabaoOperatorWecashprofileService;
//	
//	@Autowired
//	private BwKabaoPhoneTagInfoService bwKabaoPhoneTagInfoService;
//	
//	@Autowired
//	private BwKabaoTotalContactInfoService bwKabaoTotalContactInfoService;
//	
//	@Autowired
//	private BwKabaoOperatorStatisticsService bwKabaoOperatorStatisticsService;
//	
//	@Autowired
//	private BwKabaoTopTenCallsInThreeMonthService bwKabaoTopTenCallsInThreeMonthService;
//	
//	@Autowired
//	private BwKabaoTopTenCallsOutThreeMonthService bwKabaoTopTenCallsOutThreeMonthService;
//	
//	@Autowired
//	private BwKabaoTopFiveCallAddressService bwKabaoTopFiveCallAddressService;
//	
//	@Autowired
//	private BwKabaoReportBillInfoService bwKabaoReportBillInfoService;
//	
//	@Autowired
//	private BwKabaoReportTopCallListService bwKabaoReportTopCallListService;
//	
//	@Autowired
//	private BwKabaoReportContactDictService bwKabaoReportContactDictService;
//	
//	@Autowired
//	private BwKabaoReportContactLabelDictService bwKabaoReportContactLabelDictService;
//	
//	@Autowired
//	private BwKabaoReportTotalContactInfoService bwKabaoReportTotalContactInfoService;
//	
//	@Autowired
//	private BwKabaoReportInfoListService bwKabaoReportInfoListService;
//	
//	@Autowired
//	private BwKabaoReportAddressListService bwKabaoReportAddressListService;
//	
//	@Autowired
//	private BwKabaoReportTotalDictService bwKabaoReportTotalDictService;
//	
//	@Autowired
//	private BwKabaoReportService bwKabaoReportService;
//	
//	@Autowired
//	private BwThirdOperateBasicService bwThirdOperateBasicService;
//	
//	@Autowired
//	private BwThirdOperateVoiceService bwThirdOperateVoiceService;
//	
//	@Autowired
//	private BwOrderRongService bwOrderRongService;
//	
//	@Autowired
//	private BwOrderProcessRecordService bwOrderProcessRecordService;
//	
//	@Async("asyncTaskExecutor")
//	@Override
//	public void addOperator(long sessionId, BwOrder bwOrder, BwBorrower borrower, KaBaoOperator operator) {
//		Long orderId = bwOrder.getId();
//		try {
//			logger.info(sessionId +":51卡宝--->>>开始异步处理运营商数据--->>>订单号:" + orderId);
//			
//			// 运营商数据
//			Transportation transportation = operator.getData().getTransportation().get(0);
//			
//			OperatorBase operatorBase = transportation.getOperatorBase();
//			
//			Origin origin = transportation.getOrigin();						//原始数据
//			
//			DataBaseInfo dataBaseInfo = origin.getBaseInfo().getData();		//基本信息
//			
//			BillInfoOrigin billInfoOrigin = origin.getBill_info();			//账单详情
//			
//			CallInfo callInfo = origin.getCallInfo();						//通话详情
//			
//			OperatorRiskprofile operatorRiskprofile = transportation.getOperatorRiskprofile();
//			
//			OperatorWecashprofile operatorWecashprofile = transportation.getOperatorWecashprofile();
//			
//			OperatorCallprofile operatorCallprofile = transportation.getOperatorCallprofile();
//			
//			OperatorStatistics operatorStatistics = transportation.getOperatorStatistics();
//			
//			JSONObject result = transportation.getReport().getJSONObject("result").getJSONObject("operator_protype_data");
//			
//			if(null != operatorBase){
//				addOperatorBase(sessionId, orderId, operatorBase);
//			}else{
//				logger.info(sessionId +":51卡宝--->>>处理运营商数据>>>订单号:" + orderId + "operatorBase为空");
//			}
//			
//			if(null != dataBaseInfo){
//				addBaseInfo(sessionId, orderId, dataBaseInfo,transportation.getAccount());
//				addBaseInfoToPublic(sessionId, orderId, origin, result);
//			}else{
//				logger.info(sessionId +":51卡宝--->>>处理运营商数据>>>订单号:" + orderId + "dataBaseInfo为空");
//			}
//			
//			if(null != billInfoOrigin){
//				addBillInfoOrigin(sessionId, orderId, billInfoOrigin,transportation.getAccount());
//			}else{
//				logger.info(sessionId +":51卡宝--->>>处理运营商数据>>>订单号:" + orderId + "billInfoOrigin为空");
//			}
//			
//			if(null != callInfo){
//				addCallInfoOrigin(sessionId, orderId, callInfo,transportation.getAccount());
//			}else{
//				logger.info(sessionId +":51卡宝--->>>处理运营商数据>>>订单号:" + orderId + "callInfo为空");
//			}
//			
//			if(null != operatorRiskprofile){
//				addOperatorRiskprofile(sessionId, orderId, operatorRiskprofile);
//			}else{
//				logger.info(sessionId +":51卡宝--->>>处理运营商数据>>>订单号:" + orderId + "operatorRiskprofile为空");
//			}
//			
//			if(null != operatorWecashprofile){
//				addOperatorWecashprofile(sessionId, orderId, operatorWecashprofile);
//			}else{
//				logger.info(sessionId +":51卡宝--->>>处理运营商数据>>>订单号:" + orderId + "operatorWecashprofile为空");
//			}
//			
//			if(null != operatorCallprofile){
//				addPhoneTagInfo(sessionId, orderId, operatorCallprofile, transportation.getAccount());
//				addTotalContactInfo(sessionId, orderId, operatorCallprofile, transportation.getAccount());
//			}else{
//				logger.info(sessionId +":51卡宝--->>>处理运营商数据>>>订单号:" + orderId + "operatorCallprofile为空");
//			}
//			
//			if(null != operatorStatistics){
//				addOperatorStatistics(sessionId, orderId, operatorStatistics, transportation.getAccount());
//			}else{
//				logger.info(sessionId +":51卡宝--->>>处理运营商数据>>>订单号:" + orderId + "operatorStatistics为空");
//			}
//			
//			if(null != result){
//				addReportBillInfo(sessionId, orderId, result, transportation.getAccount());
//			}else{
//				logger.info(sessionId +":51卡宝--->>>处理运营商数据>>>订单号:" + orderId + "report为空");
//			}
//			
//			if(null != result){
//				addReportTopCallList(sessionId, orderId, result, transportation.getAccount());
//				addReportContactDict(sessionId, orderId, result, transportation.getAccount());
//				addReportContactLabelDict(sessionId, orderId, result, transportation.getAccount());
//				addReportContactInfo(sessionId, orderId, result, transportation.getAccount());
//				addReportInfoList(sessionId, orderId, result, transportation.getAccount());
//				addReportAddressList(sessionId, orderId, result, transportation.getAccount());
//				addReportTotalDict(sessionId, orderId, result, transportation.getAccount());
//				addReport(sessionId, orderId, result, transportation.getAccount());
//			}else{
//				logger.info(sessionId +":51卡宝--->>>处理运营商数据>>>订单号:" + orderId + "result为空");
//			}
//			
//			//白骑士风控
//			postBaiqishi(sessionId, orderId);
//			
////			bwOrder.setStatusId(2L); // 2初审
////			bwOrder.setUpdateTime(Calendar.getInstance().getTime());
////			bwOrder.setSubmitTime(Calendar.getInstance().getTime());
////			bwOrderService.updateBwOrder(bwOrder);
//			
//			BwOrderRong bwOrderRong = new BwOrderRong();
//			bwOrderRong.setOrderId(orderId);
//			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//			
//			// 系统审核任务 审核 放入redis
//			SystemAuditDto systemAuditDto = new SystemAuditDto();
//			systemAuditDto.setIncludeAddressBook(0);
//			systemAuditDto.setOrderId(orderId);
//			systemAuditDto.setBorrowerId(bwOrder.getBorrowerId());
//			systemAuditDto.setName(borrower.getName());
//			systemAuditDto.setPhone(borrower.getPhone());
//			systemAuditDto.setIdCard(borrower.getIdCard());
//			systemAuditDto.setChannel(bwOrderRong.getChannelId().intValue());
//			systemAuditDto.setThirdOrderId(bwOrderRong.getThirdOrderNo());
//			systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//			RedisUtils.hset(SystemConstant.AUDIT_KEY, orderId + "", JsonUtils.toJson(systemAuditDto));
//			logger.info(sessionId + ":51卡宝>>>结束修改订单状态,并放入redis");
//			
//			
//			HashMap<String, String> hm = new HashMap<>();
//			hm.put("channelId", bwOrder.getChannel() + "");
//			hm.put("orderId", String.valueOf(orderId));
//			hm.put("orderStatus", 2 + "");
//			hm.put("result", "");
//			String hmData = JSON.toJSONString(hm);
//			RedisUtils.rpush("tripartite:orderStatusNotify:" + bwOrder.getChannel(), hmData);
//			
//			// 修改工单进程表
//			logger.info(sessionId + ":51卡宝>>>开始修改工单进程表");
//			BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//			bwOrderProcessRecord.setSubmitTime(new Date());
//			bwOrderProcessRecord.setOrderId(bwOrder.getId());
//			bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//			logger.info(sessionId + ":51卡宝>>>结束更改订单进行时间");
//			
//			logger.info(sessionId +":51卡宝>>>结束处理运营商数据");
//		} catch (Exception e) {
//			logger.info(sessionId +":51卡宝>>>结束处理运营商数据异常" , e);
//		}
//	}
//
//	
//	/**
//	 * @Title: addBaseInfoToPublic  
//	 * @Description:  添加数据到三方公共表 bw_third_operate_voice、bw_third_operate_basic
//	 * @param @param sessionId
//	 * @param @param orderId
//	 * @param @param origin     
//	 * @return void      
//	 * @throws
//	 */
//	private void addBaseInfoToPublic(long sessionId, Long orderId, Origin origin,JSONObject result) {
//		DataBaseInfo dataBaseInfo = origin.getBaseInfo().getData();
//		logger.info(sessionId + ":51卡宝>>>【AsyncKaBaoTaskImpl.addBaseInfoToPublic】开始添加运营商基础信息到bw_third_operate_basic表中");
//		BwThirdOperateBasic conditionBasic = new BwThirdOperateBasic();
//		conditionBasic.setOrderId(orderId);
//		// 基本的运营商数据
//		BwThirdOperateBasic bwThirdOperateBasic = bwThirdOperateBasicService.findByAttr(conditionBasic);
//		if (bwThirdOperateBasic == null) {
//			bwThirdOperateBasic = new BwThirdOperateBasic();
//			bwThirdOperateBasic.setOrderId(orderId);
//			bwThirdOperateBasic.setChannel(Integer.valueOf(KaBaoConstant.CHANNELID));
//			bwThirdOperateBasic.setUserSource(origin.getType());
//			bwThirdOperateBasic.setIdCard(dataBaseInfo.getIdCard());
//			bwThirdOperateBasic.setAddr(dataBaseInfo.getAddress());
//			bwThirdOperateBasic.setRealName(dataBaseInfo.getName());
//			bwThirdOperateBasic.setPhone(origin.getId());
//			bwThirdOperateBasic.setRegTime(result.getDate("register_time"));
//			
//			bwThirdOperateBasic.setPhoneStatus(Objects.equals(dataBaseInfo.getStatus(), "00")?"1":"");// 0,欠费;1,正常
//			if(Objects.equals(dataBaseInfo.getRealnameInfo(), "已审核") || Objects.equals(dataBaseInfo.getRealnameInfo(), "已登记")){
//				bwThirdOperateBasic.setAuthentication("1");
//			}
//			bwThirdOperateBasic.setPackageName(dataBaseInfo.getBrand());
//			bwThirdOperateBasic.setStarLevel(dataBaseInfo.getStarLevel());
//			bwThirdOperateBasic.setCreateTime(new Date());
//			bwThirdOperateBasic.setUpdateTime(new Date());
//			try {
//				bwThirdOperateBasicService.save(bwThirdOperateBasic);
//			} catch (Exception e) {
//				logger.error(sessionId + ":51卡宝>>>保存bwThirdOperateBasic表中异常", e);
//			}
//		} else {
//			//bwThirdOperateBasic = new BwThirdOperateBasic();
//			bwThirdOperateBasic.setOrderId(orderId);
//			bwThirdOperateBasic.setChannel(Integer.valueOf(KaBaoConstant.CHANNELID));
//			bwThirdOperateBasic.setOrderId(orderId);
//			bwThirdOperateBasic.setUserSource(origin.getType());
//			bwThirdOperateBasic.setIdCard(dataBaseInfo.getIdCard());
//			bwThirdOperateBasic.setAddr(dataBaseInfo.getAddress());
//			bwThirdOperateBasic.setRealName(dataBaseInfo.getName());
//			bwThirdOperateBasic.setPhone(origin.getId());
//			bwThirdOperateBasic.setRegTime(result.getDate("register_time"));
//			bwThirdOperateBasic.setPhoneStatus(Objects.equals(dataBaseInfo.getStatus(), "00")?"1":"");// 0,欠费;1,正常
//			if(Objects.equals(dataBaseInfo.getRealnameInfo(), "已审核") || Objects.equals(dataBaseInfo.getRealnameInfo(), "已登记")){
//				bwThirdOperateBasic.setAuthentication("1");
//			}
//			bwThirdOperateBasic.setPackageName(dataBaseInfo.getBrand());
//			bwThirdOperateBasic.setStarLevel(dataBaseInfo.getStarLevel());
//			bwThirdOperateBasic.setCreateTime(new Date());
//			bwThirdOperateBasic.setUpdateTime(new Date());
//			try {
//				bwThirdOperateBasicService.update(conditionBasic);
//			} catch (Exception e) {
//				logger.error(sessionId + ":51卡宝>>>保存bwThirdOperateBasic表中异常", e);
//			}
//		}
//		logger.info(sessionId + ":51卡宝>>>【AsyncKaBaoTaskImpl.addBaseInfoToPublic】结束处理运营商基础信息到bw_third_operate_basic表中操作");
//		
//		//处理通话记录
//		logger.info(sessionId + ":51卡宝>>>开始处理通话记录保存到bw_third_operate_voice表中");
//		if(Objects.equals(origin.getCallInfo().getCode(), "E000000")){   //有通话记录
//			List<DataCallInfo> callInfoList = origin.getCallInfo().getData();	//通话详情
//			if(CollectionUtils.isNotEmpty(callInfoList)){
//				bwThirdOperateVoiceService.deleteAllByOrderId(orderId);
//				BwThirdOperateVoice bwThirdOperateVoice = null;
//				for (DataCallInfo dataCallInfo : callInfoList) {
//					List<CallInfoDetails> details = dataCallInfo.getDetails();	//通话详细
//					if(CollectionUtils.isNotEmpty(details)){
//						for (CallInfoDetails callInfoDetails : details) {
//							if(StringUtils.isNotBlank(callInfoDetails.getAnother_nm()) && callInfoDetails.getAnother_nm().length()>20){
//								logger.info("51卡宝>>>通话记录中，对方号码过长，略过次条记录...............ReceivePhone = " + callInfoDetails.getAnother_nm());
//								continue;
//							}
//							bwThirdOperateVoice = new BwThirdOperateVoice();
//							if(StringUtils.isNotBlank(callInfoDetails.getComm_type())){
//								bwThirdOperateVoice.setTradeType("本地通话".equals(callInfoDetails.getComm_type()) ? 1 : 2);	//通话类型，本地通话，国内漫游通话
//							}
//							bwThirdOperateVoice.setCallType("主叫".equals(callInfoDetails.getComm_mode()) ? 1 : 2); // 通话模式   主叫，被叫  
//							long trade_time = ConvertSecondUtil.getSecond(callInfoDetails.getComm_time());
//							bwThirdOperateVoice.setTradeTime(String.valueOf(trade_time));			//通话时长
//							bwThirdOperateVoice.setReceivePhone(callInfoDetails.getAnother_nm());	//对方号码
//							bwThirdOperateVoice.setTradeAddr(callInfoDetails.getComm_plac());		//通话地
//							bwThirdOperateVoice.setCallTime(callInfoDetails.getStart_time());		//通话开始时间
//							bwThirdOperateVoice.setUpdateTime(new Date());
//							bwThirdOperateVoice.setOrderId(orderId);
//							bwThirdOperateVoice.setChannel(Integer.valueOf(KaBaoConstant.CHANNELID));
//							try {
//								bwThirdOperateVoiceService.save(bwThirdOperateVoice);
//							} catch (Exception e) {
//								logger.error(sessionId + ":51卡宝>>>保存通话记录到bw_third_operate_voice表中异常,忽略此条通话记录...", e);
//							}
//						}
//					}
//				}
//			}
//		}
//	}
//
//	
//	private void addReport(long sessionId, Long orderId, JSONObject result, String account) {
//			logger.info(sessionId +":51卡宝--->>>开始处理Report数据--->>>订单号:" + orderId);
//			try {
//				bwKabaoReportService.deleteByOrderId(orderId);
//				BwKabaoReport bwKabaoReport = new BwKabaoReport();
//				bwKabaoReport.setAccount(account);
//				bwKabaoReport.setLastMonthContactsCount(result.getInteger("last_month_contacts_count"));
//				bwKabaoReport.setContactsCallOutTotalTime(result.getLong("contacts_call_out_total_time"));
//				bwKabaoReport.setLast90DaysCcMobileTime(result.getLong("last_90_days_cc_mobile_time"));
//				bwKabaoReport.setCallTimeInThreeMonth(result.getLong("call_time_in_three_month"));
//				bwKabaoReport.setUsualPhoneEvalValue(result.getDouble("usual_phone_eval_value"));
//				bwKabaoReport.setCallCntInOneMonth(result.getInteger("call_cnt_in_one_month"));
//				bwKabaoReport.setLast90DaysCcMobileCount(result.getInteger("last_90_days_cc_mobile_count"));
//				bwKabaoReport.setContactTotalCount(result.getInteger("contact_total_count"));
//				bwKabaoReport.setTotalContactLabelMarkCount(result.getInteger("total_contact_label_mark_count"));
//				bwKabaoReport.setIdCardAddr(result.getString("ID_card_addr"));
//				bwKabaoReport.setContactsWecashHighriskUserCount(result.getInteger("contacts_wecash_highrisk_user_count"));
//				bwKabaoReport.setContactsWecashUserCount(result.getInteger("contacts_wecash_user_count"));
//				bwKabaoReport.setLastMonthCallInTime(result.getInteger("last_month_call_in_time"));
//				bwKabaoReport.setIsVip(result.getString("is_vip"));
//				bwKabaoReport.setBillInfoMsg(result.getString("bill_info_msg"));
//				bwKabaoReport.setIsIdVerified(result.getString("is_ID_verified"));
//				bwKabaoReport.setBaseInfoMsg(result.getString("base_info_msg"));
//				bwKabaoReport.setCallCntThreeMonth(result.getInteger("call_cnt_three_month"));
//				bwKabaoReport.setPhone((String) result.getJSONArray("phone").get(0));
//				bwKabaoReport.setTotalCallDayCount(result.getInteger("total_call_day_count"));
//				bwKabaoReport.setCallCntOutOneMonth(result.getInteger("call_cnt_out_one_month"));
//				bwKabaoReport.setIdCardName(result.getString("ID_card_name"));
//				bwKabaoReport.setCallCntOutThreeMonth(result.getInteger("call_cnt_out_three_month"));
//				bwKabaoReport.setContactsWecashOverdueCount(result.getInteger("contacts_wecash_overdue_count"));
//				bwKabaoReport.setRegCrawlDays(result.getInteger("reg_crawl_days"));
//				bwKabaoReport.setCallInfoMsg(result.getString("call_info_msg"));
//				bwKabaoReport.setCallinfoContactsMatchValue(result.getDouble("callinfo_contacts_match_value"));
//				bwKabaoReport.setContactBlackCount(result.getInteger("contact_black_count"));
//				bwKabaoReport.setMobileCostPerMonth(result.getDouble("mobile_cost_per_month"));
//				bwKabaoReport.setRegisterTime(result.getDate("register_time"));
//				bwKabaoReport.setUpdateTime(result.getDate("update_time"));
//				bwKabaoReport.setContactsCallInTotalTime(result.getLong("contacts_call_in_total_time"));
//				bwKabaoReport.setContactTotalTime(result.getLong("contact_total_time"));
//				bwKabaoReport.setCallPlaceMsg(result.getString("call_place_msg"));
//				bwKabaoReport.setFriendsTotalCount(result.getInteger("friends_total_count"));
//				bwKabaoReport.setPhoneNumType(result.getString("phone_num_type"));
//				bwKabaoReport.setIdCardNum(result.getString("ID_card_num"));
//				bwKabaoReport.setContactsCallInTotalCount(result.getInteger("contacts_call_in_total_count"));
//				bwKabaoReport.setTopThreeContactsAvgTotalTime(result.getLong("top_three_contacts_avg_total_time"));
//				bwKabaoReport.setContactsCallOutTotalCount(result.getInteger("contacts_call_out_total_count"));
//				bwKabaoReport.setLastMonthContactsTime(result.getLong("last_month_contacts_time"));
//				bwKabaoReport.setRegDays(result.getInteger("reg_days"));
//				bwKabaoReport.setCallTimeThreeMonth(result.getLong("call_time_out_three_month"));
//				bwKabaoReport.setCallCntInThreeMonth(result.getInteger("call_cnt_in_three_month"));
//				bwKabaoReport.setIsOpenDateCrawled(result.getString("is_open_date_crawled"));
//				bwKabaoReport.setTotalContactLabelFinanceMarkCount(result.getInteger("total_contact_label_finance_mark_count"));
//				bwKabaoReport.setLastMonthCallOutTime(result.getLong("last_month_call_out_time"));
//				bwKabaoReport.setOperatorIsAuth(result.getString("operator_is_auth"));
//				bwKabaoReport.setOrderId(orderId);
//				try {
//					bwKabaoReportService.save(bwKabaoReport);
//				} catch (Exception e) {
//					logger.info(sessionId +":51卡宝--->>>处理Report数据失败--->>>订单号:" + orderId,e);
//				}
//			} catch (Exception e) {
//				logger.info(sessionId +":51卡宝--->>>处理Report数据失败--->>>订单号:" + orderId,e);
//			}
//	}
//
//	private void addReportTotalDict(long sessionId, Long orderId, JSONObject result, String account) {
//		try {
//			logger.info(sessionId +":51卡宝--->>>开始处理ReportTotalDict数据--->>>订单号:" + orderId);
//			bwKabaoReportTotalDictService.deleteByOrderId(orderId);
//			JSONObject contactsWecashUserDict = result.getJSONObject("contacts_wecash_user_dict");
//			Map<String, Object> map = contactsWecashUserDict.getInnerMap();
//			Set<Entry<String, Object>> entrySet = map.entrySet();
//			for (Entry<String, Object> entry : entrySet) {
//				BwKabaoReportTotalDict bwKabaoReportTotalDict = new BwKabaoReportTotalDict();
//				String phone = entry.getKey();
//				JSONObject jsonObject = (JSONObject) entry.getValue();
//				bwKabaoReportTotalDict.setAccount(account);
//				bwKabaoReportTotalDict.setOrderId(orderId);
//				bwKabaoReportTotalDict.setPhone(phone);
//				bwKabaoReportTotalDict.setAmount(jsonObject.getInteger("amount"));
//				bwKabaoReportTotalDict.setApplyStatus(jsonObject.getString("apply_status"));
//				bwKabaoReportTotalDict.setCallInCount(jsonObject.getInteger("call_in_count"));
//				bwKabaoReportTotalDict.setCallOutCount(jsonObject.getInteger("call_out_count"));
//				bwKabaoReportTotalDict.setCallInTime(jsonObject.getLong("call_in_time"));
//				bwKabaoReportTotalDict.setCallOutTime(jsonObject.getLong("call_out_time"));
//				bwKabaoReportTotalDict.setLastCallTime(jsonObject.getDate("last_call_time"));
//				bwKabaoReportTotalDict.setChannel(jsonObject.getString("channel"));
//				bwKabaoReportTotalDict.setComments(jsonObject.getString("comments"));
//				bwKabaoReportTotalDict.setCompanyName(jsonObject.getString("company_name"));
//				bwKabaoReportTotalDict.setCustomerType(jsonObject.getString("customer_type"));
//				bwKabaoReportTotalDict.setHaveCallInfo(jsonObject.getString("have_call_info"));
//				bwKabaoReportTotalDict.setIsCurrentOverdue(jsonObject.getString("is_current_overdue"));
//				bwKabaoReportTotalDict.setIsOverdue(jsonObject.getString("is_overdue"));
//				bwKabaoReportTotalDict.setRiskInfo(jsonObject.getString("risk_info"));
//				bwKabaoReportTotalDict.setSchoolName(jsonObject.getString("school_name"));
//				bwKabaoReportTotalDict.setMaxOverdueDay(jsonObject.getInteger("max_overdue_day"));
//				bwKabaoReportTotalDict.setName(jsonObject.getString("name"));
//				bwKabaoReportTotalDict.setSellerName(jsonObject.getString("seller_name"));
//				bwKabaoReportTotalDict.setTotalCount(jsonObject.getInteger("total_count"));
//				bwKabaoReportTotalDict.setTotalTime(jsonObject.getLong("total_time"));
//				bwKabaoReportTotalDict.setSellerType(jsonObject.getString("seller_type"));
//				bwKabaoReportTotalDict.setCreateTime(new Date());
//				bwKabaoReportTotalDict.setUpdateTime(new Date());
//				try {
//				bwKabaoReportTotalDictService.save(bwKabaoReportTotalDict);
//				} catch (Exception e) {
//					logger.error(sessionId +":51卡宝--->>>处理ReportTotalDict数据失败--->>>订单号:" + orderId,e);
//				}
//			}
//		} catch (Exception e) {
//			logger.error(sessionId +":51卡宝--->>>处理ReportTotalDict数据失败--->>>订单号:" + orderId,e);
//		}
//	}
//
//	private void addReportAddressList(long sessionId, Long orderId, JSONObject result, String account) {
//		try {
//			logger.info(sessionId +":51卡宝--->>>开始处理ReportAddressList数据--->>>订单号:" + orderId);
//			bwKabaoReportAddressListService.deleteByOrderId(orderId);
//			JSONArray topCallAddressList = result.getJSONArray("top_call_address_list");
//			if(CollectionUtils.isNotEmpty(topCallAddressList)){
//				for (Object object : topCallAddressList) {
//					JSONObject jsonObject = (JSONObject)object;
//					BwKabaoReportAddressList bwKabaoReportAddressList = new BwKabaoReportAddressList();
//					bwKabaoReportAddressList.setAccount(account);
//					bwKabaoReportAddressList.setOrderId(orderId);
//					bwKabaoReportAddressList.setCallAddr(jsonObject.getString("call_addr"));
//					bwKabaoReportAddressList.setCallInCount(jsonObject.getInteger("call_in_count"));
//					bwKabaoReportAddressList.setCallInTime(jsonObject.getLong("call_in_time"));
//					bwKabaoReportAddressList.setCallOutCount(jsonObject.getInteger("call_out_count"));
//					bwKabaoReportAddressList.setCallOutTime(jsonObject.getLong("call_out_time"));
//					bwKabaoReportAddressList.setTotalCount(jsonObject.getInteger("total_count"));
//					bwKabaoReportAddressList.setTotalTime(jsonObject.getLong("total_time"));
//					bwKabaoReportAddressList.setCreateTime(new Date());
//					bwKabaoReportAddressList.setUpdateTime(new Date());
//					try {
//						bwKabaoReportAddressListService.save(bwKabaoReportAddressList);
//					} catch (Exception e) {
//						logger.error(sessionId +":51卡宝--->>>【AsyncKaBaoTaskImpl.addReportAddressList方法中】bwKabaoReportInfoList为空--->>>订单号:" + orderId,e);
//					}
//				}
//			}else{
//				logger.error(sessionId +":51卡宝--->>>【AsyncKaBaoTaskImpl.addReportAddressList方法中】bwKabaoReportInfoList为空--->>>订单号:" + orderId);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId +":51卡宝--->>>处理ReportAddressList数据失败--->>>订单号:" + orderId,e);
//		}
//	}
//
//	private void addReportInfoList(long sessionId, Long orderId, JSONObject result, String account) {
//		try {
//			logger.info(sessionId +":51卡宝--->>>开始处理ReportInfoList数据--->>>订单号:" + orderId);
//			bwKabaoReportInfoListService.deleteByOrderId(orderId);
//			JSONArray contactsWecashTotalInfos = result.getJSONArray("contacts_wecash_total_info_list");
//			if(CollectionUtils.isNotEmpty(contactsWecashTotalInfos)){
//				for (Object object : contactsWecashTotalInfos) {
//					JSONArray jsonArray = (JSONArray)object;
//					for (Object object2 : jsonArray) {
//						BwKabaoReportInfoList bwKabaoReportInfoList = new BwKabaoReportInfoList();
//						JSONObject jsonObject = (JSONObject)object2;
//						bwKabaoReportInfoList.setAccount(account);
//						bwKabaoReportInfoList.setOrderId(orderId);
//						bwKabaoReportInfoList.setChannel(jsonObject.getString("channel"));
//						bwKabaoReportInfoList.setAmount(jsonObject.getInteger("amount"));
//						bwKabaoReportInfoList.setCompanyName(jsonObject.getString("company_name"));
//						bwKabaoReportInfoList.setCustomerType(jsonObject.getString("customer_type"));
//						bwKabaoReportInfoList.setExtractTimes(jsonObject.getInteger("extract_times"));
//						bwKabaoReportInfoList.setIdcard(jsonObject.getString("idcard"));
//						bwKabaoReportInfoList.setIsCurrentOverdue(jsonObject.getString("is_current_overdue"));
//						bwKabaoReportInfoList.setIsExtractCash(jsonObject.getString("is_extract_cash"));
//						bwKabaoReportInfoList.setMaxOverdueDay(jsonObject.getString("max_overdue_day"));
//						bwKabaoReportInfoList.setName(jsonObject.getString("name"));
//						bwKabaoReportInfoList.setPhone(jsonObject.getString("phone"));
//						bwKabaoReportInfoList.setRegisterDate(jsonObject.getDate("register_date"));
//						bwKabaoReportInfoList.setRemainAmount(jsonObject.getInteger("remain_amount"));
//						bwKabaoReportInfoList.setSchoolName(jsonObject.getString("school_name"));
//						bwKabaoReportInfoList.setCreateTime(new Date());
//						bwKabaoReportInfoList.setUpdateTime(new Date());
//						try {
//							bwKabaoReportInfoListService.save(bwKabaoReportInfoList);
//						} catch (Exception e) {
//							logger.error(sessionId +":51卡宝--->>>【AsyncKaBaoTaskImpl.addReportInfoList方法中】contactsWecashTotalInfos为空--->>>订单号:" + orderId,e);
//						}
//					}
//				}
//			}else{
//				logger.error(sessionId +":51卡宝--->>>【AsyncKaBaoTaskImpl.addReportInfoList方法中】contactsWecashTotalInfos为空--->>>订单号:" + orderId);
//			}
//			
//		} catch (Exception e) {
//			logger.error(sessionId +":51卡宝--->>>处理ReportInfoList数据失败--->>>订单号:" + orderId,e);
//		}
//	}
//
//	private void addReportContactInfo(long sessionId, Long orderId, JSONObject result, String account) {
//		try {
//			logger.info(sessionId +":51卡宝--->>>开始处理ReportContactInfo数据--->>>订单号:" + orderId);
//			bwKabaoReportTotalContactInfoService.deleteByOrderId(orderId);
//			JSONArray totalContactInfo = result.getJSONArray("total_contact_info");
//			if(CollectionUtils.isNotEmpty(totalContactInfo)){
//				for (Object object : totalContactInfo) {
//					BwKabaoReportTotalContactInfo bwKabaoReportTotalContactInfo = new BwKabaoReportTotalContactInfo();
//					JSONObject jsonObject = (JSONObject)object;
//					bwKabaoReportTotalContactInfo.setAccount(account);
//					bwKabaoReportTotalContactInfo.setOrderId(orderId);
//					bwKabaoReportTotalContactInfo.setCallInCount(jsonObject.getInteger("call_in_count"));
//					bwKabaoReportTotalContactInfo.setPhone(jsonObject.getString("phone"));
//					bwKabaoReportTotalContactInfo.setPhoneBookName(jsonObject.getString("phone_book_name"));
//					bwKabaoReportTotalContactInfo.setCallInTime(jsonObject.getLong("call_in_time"));
//					bwKabaoReportTotalContactInfo.setCallOutCount(jsonObject.getInteger("call_out_count"));
//					bwKabaoReportTotalContactInfo.setCallOutTime(jsonObject.getLong("call_out_time"));
//					bwKabaoReportTotalContactInfo.setLastCallTime(jsonObject.getDate("last_call_time"));
//					bwKabaoReportTotalContactInfo.setTotalCount(jsonObject.getInteger("total_count"));
//					bwKabaoReportTotalContactInfo.setTotalTime(jsonObject.getLong("total_time"));
//					bwKabaoReportTotalContactInfo.setCreateTime(new Date());
//					bwKabaoReportTotalContactInfo.setUpdateTime(new Date());
//					try {
//						bwKabaoReportTotalContactInfoService.save(bwKabaoReportTotalContactInfo);
//						
//					} catch (Exception e) {
//						logger.error(sessionId +":51卡宝--->>>处理ReportContactInfo数据失败--->>>订单号:" + orderId,e);
//					}
//				}
//			}else{
//				logger.info(sessionId +":51卡宝--->>>【AsyncKaBaoTaskImpl.addReportContactInfo方法中】totalContactInfo为空--->>>订单号:" + orderId);
//			}
//			
//		} catch (Exception e) {
//			logger.error(sessionId +":51卡宝--->>>处理ReportContactInfo数据失败--->>>订单号:" + orderId,e);
//		}
//	}
//
//	private void addReportContactLabelDict(long sessionId, Long orderId, JSONObject result, String account) {
//		try {
//			logger.info(sessionId +":51卡宝--->>>开始处理ReportContactLabelDict数据--->>>订单号:" + orderId);
//			bwKabaoReportContactLabelDictService.deleteByOrderId(orderId);
//			JSONObject totalContactLabelTotalDict = result.getJSONObject("total_contact_label_total_dict");
//			Map<String, Object> map = totalContactLabelTotalDict.getInnerMap();
//			Set<Entry<String, Object>> entrySet = map.entrySet();
//			for (Entry<String, Object> entry : entrySet) {
//				BwKabaoReportContactLabelDict bwKabaoReportContactLabelDict = new BwKabaoReportContactLabelDict();
//				String phone = entry.getKey();
//				JSONObject jsonObject = (JSONObject) entry.getValue();
//				bwKabaoReportContactLabelDict.setAccount(account);
//				bwKabaoReportContactLabelDict.setOrderId(orderId);
//				bwKabaoReportContactLabelDict.setPhone(phone);
//				bwKabaoReportContactLabelDict.setCallInCount(jsonObject.getInteger("call_in_count"));
//				bwKabaoReportContactLabelDict.setCallInTime(jsonObject.getInteger("call_in_time"));
//				bwKabaoReportContactLabelDict.setCallOutCount(jsonObject.getInteger("call_out_count"));
//				bwKabaoReportContactLabelDict.setCallOutTime(jsonObject.getInteger("call_out_time"));
//				bwKabaoReportContactLabelDict.setLastCallTime(jsonObject.getDate("last_call_time"));
//				String phoneSensitiveInfo = jsonObject.getString("phone_sensitive_info");
//				if(phoneSensitiveInfo.length()>100){
//					phoneSensitiveInfo = phoneSensitiveInfo.substring(0, 100);
//				}
//				bwKabaoReportContactLabelDict.setPhoneSensitiveInfo(phoneSensitiveInfo);
//				bwKabaoReportContactLabelDict.setTotalCount(jsonObject.getInteger("total_count"));
//				bwKabaoReportContactLabelDict.setTotalTime(jsonObject.getLong("total_time"));
//				bwKabaoReportContactLabelDict.setType(jsonObject.getString("type"));
//				bwKabaoReportContactLabelDict.setHaveCallInfo(jsonObject.getString("have_call_info"));
//				bwKabaoReportContactLabelDict.setCreateTime(new Date());
//				bwKabaoReportContactLabelDict.setUpdateTime(new Date());
//				try {
//					bwKabaoReportContactLabelDictService.save(bwKabaoReportContactLabelDict);
//				} catch (Exception e) {
//					logger.error(sessionId +":51卡宝--->>>处理ReportContactLabelDict数据失败--->>>订单号:" + orderId,e);
//				}
//			}
//			
//		} catch (Exception e) {
//			logger.error(sessionId +":51卡宝--->>>处理ReportContactLabelDict数据失败--->>>订单号:" + orderId,e);
//		}
//	}
//
//	private void addReportContactDict(long sessionId, Long orderId, JSONObject result, String account) {
//		try {
//			logger.info(sessionId +":51卡宝--->>>开始处理ReportContactDict数据--->>>订单号:" + orderId);
//			bwKabaoReportContactDictService.deleteByOrderId(orderId);
//			JSONObject totalContactDict = result.getJSONObject("total_contact_dict");
//			Map<String, Object> map = totalContactDict.getInnerMap();
//			Set<Entry<String, Object>> entrySet = map.entrySet();
//			for (Entry<String, Object> entry : entrySet) {
//				BwKabaoReportContactDict bwKabaoReportContactDict = new BwKabaoReportContactDict();
//				String phone = entry.getKey();
//				JSONObject jsonObject = (JSONObject) entry.getValue();
//				bwKabaoReportContactDict.setPhone(phone);
//				bwKabaoReportContactDict.setAccount(account);
//				bwKabaoReportContactDict.setOrderId(orderId);
//				bwKabaoReportContactDict.setCallInCount(jsonObject.getInteger("call_in_count"));
//				bwKabaoReportContactDict.setCallInTime(jsonObject.getLong("call_in_time"));
//				bwKabaoReportContactDict.setCallOutCount(Integer.valueOf(jsonObject.getString("call_out_count")));
//				bwKabaoReportContactDict.setCallOutTime(jsonObject.getLong("call_out_time"));
//				bwKabaoReportContactDict.setLastCallTime(jsonObject.getDate("last_call_time"));
//				bwKabaoReportContactDict.setPhoneBookName(jsonObject.getString("phone_book_name"));
//				bwKabaoReportContactDict.setTotalCount(jsonObject.getInteger("total_count"));	//Integer.valueOf(jsonObject.getString("total_count"))
//				bwKabaoReportContactDict.setTotalTime(jsonObject.getLong("total_time")); //Long.valueOf(jsonObject.getString("total_time"))
//				bwKabaoReportContactDict.setCreateTime(new Date());
//				bwKabaoReportContactDict.setUpdateTime(new Date());
//				try {
//					bwKabaoReportContactDictService.save(bwKabaoReportContactDict);
//				} catch (Exception e) {
//					logger.error(sessionId +":51卡宝--->>>处理ReportContactDict数据失败--->>>订单号:" + orderId,e);
//				}
//			}
//		} catch (Exception e) {
//			logger.error(sessionId +":51卡宝--->>>处理ReportContactDict数据失败--->>>订单号:" + orderId,e);
//		}
//	}
//
//	private void addReportTopCallList(long sessionId, Long orderId, JSONObject result, String account) {
//		try {
//			logger.info(sessionId +":51卡宝--->>>开始处理ReportTopCallList数据--->>>订单号:" + orderId);
//			bwKabaoReportTopCallListService.deleteByOrderId(orderId);
//			JSONArray topCallInLabelList = result.getJSONArray("top_ten_call_in_phone_num_label_list");
//			JSONArray topCallInTupleList = result.getJSONArray("top_ten_call_in_phone_num_tuple_list");
//			JSONArray topCallOutLabelList = result.getJSONArray("top_ten_call_out_phone_num_label_list");
//			JSONArray topCallOutTupleList = result.getJSONArray("top_ten_call_out_phone_num_tuple_list");
//			JSONArray topFiveAreaList = result.getJSONArray("top_five_contacts_area_list");
//			saveReportTopCallList(topCallInLabelList,"top_ten_call_in_phone_num_label_list",account,orderId);
//			saveReportTopCallList(topCallInTupleList,"top_ten_call_in_phone_num_tuple_list",account,orderId);
//			saveReportTopCallList(topCallOutLabelList,"top_ten_call_out_phone_num_label_list",account,orderId);
//			saveReportTopCallList(topCallOutTupleList,"top_ten_call_out_phone_num_tuple_list",account,orderId);
//			saveReportTopCallList(topFiveAreaList,"top_five_contacts_area_list",account,orderId);
//		} catch (Exception e) {
//			logger.error(sessionId +":51卡宝--->>>处理ReportTopCallList数据失败--->>>订单号:" + orderId,e);
//		}
//	}
//
//	/**
//	 * 
//	 * @Title: saveReportTopCallList  
//	 * @Description:  根据json对象中的key值存储value
//	 * @param @param topCallList
//	 * @param @param bwKabaoReportTopCallListService2
//	 * @param @param topCallListKey
//	 * @param @param rTopCallList
//	 * @param @param account
//	 * @param @param orderId     
//	 * @return void      
//	 * @throws
//	 */
//	private void saveReportTopCallList(JSONArray topCallList,String topCallListKey,String account, Long orderId) {
//		if(null != topCallList){
//			for (Object object : topCallList) {
//				JSONArray obj = (JSONArray)object;
//				for (int i = 0; i < obj.size(); i++) {
//					BwKabaoReportTopCallList rTopCallList = new BwKabaoReportTopCallList();
//					rTopCallList.setAccount(account);
//					rTopCallList.setCallInType(topCallListKey);
//					rTopCallList.setOrderId(orderId);
//					rTopCallList.setTopCallLabel(String.valueOf(obj.get(0)));
//					rTopCallList.setTopCallCount(NumberUtils.toInt(obj.get(1)+""));
//					rTopCallList.setCreateTime(new Date());
//					rTopCallList.setUpdateTime(new Date());
//					try {
//						bwKabaoReportTopCallListService.save(rTopCallList);
//					} catch (Exception e) {
//						logger.error(":51卡宝--->>>处理ReportTopCallList数据失败--->>>订单号:",e);
//					}
//				}
//			}
//		}
//	}
//
//	private void addReportBillInfo(long sessionId, Long orderId, JSONObject result, String account) {
//		try {
//			logger.info(sessionId +":51卡宝--->>>开始处理ReportBillInfo数据--->>>订单号:" + orderId);
//			bwKabaoReportBillInfoService.deleteByOrderId(orderId);
//	        JSONArray billInfos = result.getJSONArray("bill_info");
//	        if(CollectionUtils.isNotEmpty(billInfos)){
//		        for (Object object : billInfos) {
//					JSONObject obj = (JSONObject)object;
//					JSONArray billInfoDetails = obj.getJSONArray("bill_info_detail_list");
//					if(CollectionUtils.isNotEmpty(billInfoDetails)){
//						for (Object object2 : billInfoDetails) {
//							BwKabaoReportBillInfo kbReportBillInfo = new BwKabaoReportBillInfo();
//							kbReportBillInfo.setAccount(account);
//							kbReportBillInfo.setBillMonth(obj.getString("bill_month"));
//							kbReportBillInfo.setBillFee(NumberUtils.toDouble(obj.getString("bill_fee")));
//							kbReportBillInfo.setBillStartDate(obj.getString("bill_start_date"));
//							kbReportBillInfo.setBillEndDate(obj.getString("bill_end_date"));
//							JSONObject	obj2 = (JSONObject)object2;
//							kbReportBillInfo.setItemName(obj2.getString("item_name"));
//							kbReportBillInfo.setItemValue(obj2.getDouble("item_value"));
//							kbReportBillInfo.setOrderId(orderId);
//							kbReportBillInfo.setCreateTime(new Date());
//							kbReportBillInfo.setUpdateTime(new Date());
//							try {
//								bwKabaoReportBillInfoService.save(kbReportBillInfo);
//							} catch (Exception e) {
//								logger.error(sessionId +":51卡宝--->>>处理ReportBillInfo数据失败--->>>订单号:" + orderId,e);
//							}
//						}
//					}else{
//						BwKabaoReportBillInfo kbReportBillInfo = new BwKabaoReportBillInfo();
//						kbReportBillInfo.setAccount(account);
//						kbReportBillInfo.setBillMonth(obj.getString("bill_month"));
//						kbReportBillInfo.setBillFee(NumberUtils.toDouble(obj.getString("bill_fee")));
//						kbReportBillInfo.setBillStartDate(obj.getString("bill_start_date"));
//						kbReportBillInfo.setBillEndDate(obj.getString("bill_end_date"));
//						kbReportBillInfo.setOrderId(orderId);
//						kbReportBillInfo.setCreateTime(new Date());
//						kbReportBillInfo.setUpdateTime(new Date());
//						try {
//							bwKabaoReportBillInfoService.save(kbReportBillInfo);
//						} catch (Exception e) {
//							logger.error(sessionId +":51卡宝--->>>处理ReportBillInfo数据失败--->>>订单号:" + orderId,e);
//						}
//					}
//				}
//	        }
//		} catch (Exception e) {
//			logger.info(sessionId +":51卡宝--->>>处理ReportBillInfo数据失败--->>>订单号:" + orderId,e);
//		}
//	}
//
//	private void addOperatorStatistics(long sessionId, Long orderId, OperatorStatistics operatorStatistics,
//			String account) {
//		try {
//			logger.info(sessionId +":51卡宝--->>>开始处理OperatorStatistics数据记录--->>>订单号:" + orderId);
//			bwKabaoOperatorStatisticsService.deleteAllByOrderId(orderId);
//			BwKabaoOperatorStatistics kbOperatorStatistics = new BwKabaoOperatorStatistics();
//			kbOperatorStatistics.setHasNoCallIn30d(operatorStatistics.getHasNoCallIn30d());
//			kbOperatorStatistics.setNightPercent(NumberUtils.toDouble(operatorStatistics.getNightPercent()));
//			List<TopTenCallsOutThreeMonth> topTenCallsOutThreeMonths = operatorStatistics.getTopTenCallsOutThreeMonth();
//			if(CollectionUtils.isNotEmpty(topTenCallsOutThreeMonths)){
//				bwKabaoTopTenCallsOutThreeMonthService.deleteAllByOrderId(orderId);
//				for (TopTenCallsOutThreeMonth topTenCallsOutThreeMonth : topTenCallsOutThreeMonths) {
//					BwKabaoTopTenCallsOutThreeMonth kbTopTenCallsOutThreeMonth = new BwKabaoTopTenCallsOutThreeMonth();
//					kbTopTenCallsOutThreeMonth.setAccount(account);
//					kbTopTenCallsOutThreeMonth.setPhone(topTenCallsOutThreeMonth.getPhone());
//					kbTopTenCallsOutThreeMonth.setCallOutCount(topTenCallsOutThreeMonth.getCallOutCount());
//					kbTopTenCallsOutThreeMonth.setCallOutTime(topTenCallsOutThreeMonth.getCallOutTime());
//					kbTopTenCallsOutThreeMonth.setTotalCount(topTenCallsOutThreeMonth.getTotalCount());
//					kbTopTenCallsOutThreeMonth.setTotalTime(topTenCallsOutThreeMonth.getTotalTime());
//					kbTopTenCallsOutThreeMonth.setOrderId(orderId);
//					kbTopTenCallsOutThreeMonth.setCreateTime(new Date());
//					kbTopTenCallsOutThreeMonth.setUpdateTime(new Date());
//					try {
//						bwKabaoTopTenCallsOutThreeMonthService.save(kbTopTenCallsOutThreeMonth);
//					} catch (Exception e) {
//						logger.error(sessionId +":51卡宝--->>>处理TopTenCallsOutThreeMonth数据失败--->>>订单号:" + orderId,e);
//					}
//				}
//			}
//			kbOperatorStatistics.setHasCallSpan(operatorStatistics.getHasCallSpan());
//			kbOperatorStatistics.setTotalOutCount(operatorStatistics.getTotalOutCount());
//			kbOperatorStatistics.setDoubleEndCallCount(operatorStatistics.getDoubleEndCallCount());
//			kbOperatorStatistics.setDayCount(operatorStatistics.getDayCount());
//			kbOperatorStatistics.setNightCount(operatorStatistics.getNightCount());
//			kbOperatorStatistics.setHasCallDays(operatorStatistics.getHasCallDays());
//			kbOperatorStatistics.setHasNoCallIn180d(operatorStatistics.getHasNoCallIn180d());
//			kbOperatorStatistics.setTotalTime(operatorStatistics.getTotalTime());
//			List<TopFiveCallAddress> topFiveCallAddress = operatorStatistics.getTopFiveCallAddress();
//			if(CollectionUtils.isNotEmpty(topFiveCallAddress)){
//				bwKabaoTopFiveCallAddressService.deleteAllByOrderId(orderId);
//				for (TopFiveCallAddress topFiveCallAddr : topFiveCallAddress) {
//					BwKabaoTopFiveCallAddress bwKabaoTopFiveCallAddress =new BwKabaoTopFiveCallAddress();
//					bwKabaoTopFiveCallAddress.setAccount(account);
//					bwKabaoTopFiveCallAddress.setCallAddr(topFiveCallAddr.getCallAddr());
//					bwKabaoTopFiveCallAddress.setCallCount(topFiveCallAddr.getCallCount());
//					bwKabaoTopFiveCallAddress.setCallTime(new Long(topFiveCallAddr.getCallTime()));
//					bwKabaoTopFiveCallAddress.setOrderId(orderId);
//					bwKabaoTopFiveCallAddress.setCallTimePercent(NumberUtils.toDouble(topFiveCallAddr.getCallTimePercent()));
//					bwKabaoTopFiveCallAddress.setCreateTime(new Date());
//					bwKabaoTopFiveCallAddress.setUpdateTime(new Date());
//					try {
//						bwKabaoTopFiveCallAddressService.save(bwKabaoTopFiveCallAddress);
//					} catch (Exception e) {
//						logger.error(sessionId +":51卡宝--->>>处理TopFiveCallAddress数据失败--->>>订单号:" + orderId,e);
//					}
//				}
//			}
//			kbOperatorStatistics.setDawnCount(operatorStatistics.getDawnCount());
//			kbOperatorStatistics.setWeekendPercent(NumberUtils.toDouble(operatorStatistics.getWeekendPercent()));
//			kbOperatorStatistics.setWeekdayPercent(NumberUtils.toDouble(operatorStatistics.getWeekdayPercent()));
//			kbOperatorStatistics.setTotalCount(operatorStatistics.getTotalCount());
//			kbOperatorStatistics.setWeekendCount(operatorStatistics.getWeekendCount());
//			kbOperatorStatistics.setTotalOutTime(operatorStatistics.getTotalOutTime());
//			kbOperatorStatistics.setThreeMonthCount(operatorStatistics.getThreeMonthCount());
//			kbOperatorStatistics.setTotalInTime(operatorStatistics.getTotalInTime());
//			kbOperatorStatistics.setTotalInCount(operatorStatistics.getTotalInCount());
//			
//			List<TopTenCallsInThreeMonth> topTenCallsInThreeMonths = operatorStatistics.getTopTenCallsInThreeMonth();
//			if(CollectionUtils.isNotEmpty(topTenCallsInThreeMonths)){
//				bwKabaoTopTenCallsInThreeMonthService.deleteAllByOrderId(orderId);
//				for (TopTenCallsInThreeMonth topTenCallsInThreeMonth : topTenCallsInThreeMonths) {
//					BwKabaoTopTenCallsInThreeMonth bwKabaoTopTenCallsInThreeMonth = new BwKabaoTopTenCallsInThreeMonth();
//					bwKabaoTopTenCallsInThreeMonth.setAccount(account);
//					bwKabaoTopTenCallsInThreeMonth.setCallInCount(topTenCallsInThreeMonth.getCallInCount());
//					bwKabaoTopTenCallsInThreeMonth.setCallInTime(topTenCallsInThreeMonth.getCallInTime());
//					bwKabaoTopTenCallsInThreeMonth.setOrderId(orderId);
//					bwKabaoTopTenCallsInThreeMonth.setPhone(topTenCallsInThreeMonth.getPhone());
//					bwKabaoTopTenCallsInThreeMonth.setTotalCount(topTenCallsInThreeMonth.getTotalCount());
//					bwKabaoTopTenCallsInThreeMonth.setTotalTime(topTenCallsInThreeMonth.getTotalTime());
//					bwKabaoTopTenCallsInThreeMonth.setCreateTime(new Date());
//					bwKabaoTopTenCallsInThreeMonth.setUpdateTime(new Date());
//					try {
//						bwKabaoTopTenCallsInThreeMonthService.save(bwKabaoTopTenCallsInThreeMonth);
//					} catch (Exception e) {
//						logger.error(sessionId +":51卡宝--->>>处理TopTenCallsInThreeMonth数据失败--->>>订单号:" + orderId,e);
//					}
//				}
//			}
//			kbOperatorStatistics.setDawnPercent(NumberUtils.toDouble(operatorStatistics.getDawnPercent()));
//			kbOperatorStatistics.setOneMonthCount(operatorStatistics.getOneMonthCount());
//			kbOperatorStatistics.setWeekdayCount(operatorStatistics.getWeekdayCount());
//			kbOperatorStatistics.setDayPercent(NumberUtils.toDouble(operatorStatistics.getDayPercent()));
//			kbOperatorStatistics.setThreeMonthTime(operatorStatistics.getThreeMonthTime());
//			kbOperatorStatistics.setOneMonthTime(operatorStatistics.getOneMonthCount());
//			kbOperatorStatistics.setOrderId(orderId);
//			kbOperatorStatistics.setCreateTime(new Date());
//			kbOperatorStatistics.setUpdateTime(new Date());
//			try {
//				bwKabaoOperatorStatisticsService.save(kbOperatorStatistics);
//			} catch (Exception e) {
//				logger.error(sessionId +":51卡宝--->>>处理OperatorStatistics数据失败--->>>订单号:" + orderId,e);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId +":51卡宝--->>>处理OperatorStatistics数据失败--->>>订单号:" + orderId,e);
//		}
//	}
//
//	private void addTotalContactInfo(long sessionId, Long orderId, OperatorCallprofile operatorCallprofile,
//			String account) {
//		try {
//			logger.info(sessionId +":51卡宝--->>>开始处理TotalContactInfo数据记录--->>>订单号:" + orderId);
//			bwKabaoTotalContactInfoService.deleteAllByOrderId(orderId);
//			List<TotalContactInfo> totalContactInfos = operatorCallprofile.getTotalContactInfo();
//			if(CollectionUtils.isNotEmpty(totalContactInfos)){
//				for (TotalContactInfo totalContactInfo : totalContactInfos) {
//					BwKabaoTotalContactInfo kbTotalContactInfo = new BwKabaoTotalContactInfo();
//					kbTotalContactInfo.setAccount(account);
//					kbTotalContactInfo.setOrderId(orderId);
//					kbTotalContactInfo.setCallInCount(totalContactInfo.getCallInCount());
//					kbTotalContactInfo.setCallInCountRate(totalContactInfo.getCallOutTimeRate());
//					kbTotalContactInfo.setCallInTime(new Long(totalContactInfo.getCallInTime()));
//					kbTotalContactInfo.setCallInTimeRate(new Double(totalContactInfo.getCallInTimeRate()));
//					kbTotalContactInfo.setCallOutCount(totalContactInfo.getCallInCount());
//					kbTotalContactInfo.setCallOutCountRate(totalContactInfo.getCallOutTimeRate());
//					kbTotalContactInfo.setCallOutTime(new Long(totalContactInfo.getCallOutTime()));
//					kbTotalContactInfo.setCallOutTimeRate(totalContactInfo.getCallOutTimeRate());
//					kbTotalContactInfo.setPhoneBookName(totalContactInfo.getPhoneBookName());
//					kbTotalContactInfo.setPhone(totalContactInfo.getPhone());
//					kbTotalContactInfo.setTotalCount(totalContactInfo.getTotalCount());
//					kbTotalContactInfo.setTotalTime(new Long(totalContactInfo.getTotalTime()));
//					kbTotalContactInfo.setCallInCountRate(totalContactInfo.getCallOutCountRate());
//					kbTotalContactInfo.setCreateTime(new Date());
//					kbTotalContactInfo.setUpdateTime(new Date());
//					try {
//						bwKabaoTotalContactInfoService.save(kbTotalContactInfo);
//					} catch (Exception e) {
//						logger.error(sessionId +":51卡宝--->>>处理TotalContactInfo数据失败--->>>订单号:" + orderId,e);
//					}
//				}
//			}
//			
//		} catch (Exception e) {
//			logger.error(sessionId +":51卡宝--->>>处理TotalContactInfo数据失败--->>>订单号:" + orderId,e);
//		}
//	}
//	
//	private void addPhoneTagInfo(long sessionId, Long orderId, OperatorCallprofile operatorCallprofile,
//			String account) {
//		try {
//			logger.info(sessionId +":51卡宝--->>>开始处理PhoneTagInfo数据记录--->>>订单号:" + orderId);
//			bwKabaoPhoneTagInfoService.deleteAllByOrderId(orderId);
//			List<PhoneTagInfo> phoneTagInfos = operatorCallprofile.getPhoneTagInfo();
//			if(CollectionUtils.isNotEmpty(phoneTagInfos)){
//				for (PhoneTagInfo phoneTagInfo : phoneTagInfos) {
//					BwKabaoPhoneTagInfo kbPhoneTagInfo = new BwKabaoPhoneTagInfo();
//					kbPhoneTagInfo.setAccount(account);
//					kbPhoneTagInfo.setCallInCount(phoneTagInfo.getCallInCount());
//					kbPhoneTagInfo.setCallInTime(phoneTagInfo.getCallInTime());
//					kbPhoneTagInfo.setCallOutCount(phoneTagInfo.getCallOutCount());
//					kbPhoneTagInfo.setCallOutTime(phoneTagInfo.getCallOutTime());
//					String lastCallTimeStr = phoneTagInfo.getLastCallTime();
//					Date lastCallTime = null;
//					if(StringUtils.isNotBlank(lastCallTimeStr)){
//						lastCallTime = DateUtil.stringToDate(lastCallTimeStr, DateUtil.yyyy_MM_dd_HHmmss);
//					}
//					kbPhoneTagInfo.setLastCallTime(lastCallTime);
//					kbPhoneTagInfo.setOrderId(orderId);
//					kbPhoneTagInfo.setPhone(phoneTagInfo.getPhone());
//					kbPhoneTagInfo.setTotalTime(phoneTagInfo.getTotalTime());
//					kbPhoneTagInfo.setTag(phoneTagInfo.getTag());
//					kbPhoneTagInfo.setTotalCount(phoneTagInfo.getTotalCount());
//					kbPhoneTagInfo.setCreateTime(new Date());
//					kbPhoneTagInfo.setUpdateTime(new Date());
//					try {
//						bwKabaoPhoneTagInfoService.save(kbPhoneTagInfo);
//					} catch (Exception e) {
//						logger.error(sessionId +":51卡宝--->>>处理PhoneTagInfo数据失败--->>>订单号:" + orderId,e);
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.error(sessionId +":51卡宝--->>>处理PhoneTagInfo数据失败--->>>订单号:" + orderId,e);
//		}
//	}
//
//	private void addOperatorWecashprofile(long sessionId, Long orderId, OperatorWecashprofile operatorWecashprofile) {
//		try {
//			logger.info(sessionId +":51卡宝--->>>开始处理BwKabaoOperatorWecashprofile数据记录--->>>订单号:" + orderId);
//			bwKabaoOperatorWecashprofileService.deleteAllByOrderId(orderId);
//			BwKabaoOperatorWecashprofile kbOperatorWecashprofile = new BwKabaoOperatorWecashprofile();
//			CopyBeanPluginsUtil.transalte3(operatorWecashprofile, kbOperatorWecashprofile);
//			kbOperatorWecashprofile.setOrderId(orderId);
//			kbOperatorWecashprofile.setCreateTime(new Date());
//			kbOperatorWecashprofile.setUpdateTime(new Date());
//			try {
//				//保存BwKabaoOperatorWecashprofile数据
//				bwKabaoOperatorWecashprofileService.save(kbOperatorWecashprofile);
//			} catch (Exception e) {
//				logger.error(sessionId +":51卡宝--->>>处理BwKabaoOperatorWecashprofile数据失败--->>>订单号:" + orderId,e);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId +":51卡宝--->>>处理BwKabaoOperatorWecashprofile数据失败--->>>订单号:" + orderId,e);
//		}
//	}
//
//	private void addOperatorRiskprofile(long sessionId, Long orderId, OperatorRiskprofile operatorRiskprofile) {
//		try {
//			logger.info(sessionId +":51卡宝--->>>开始处理BwKabaoOperatorRiskprofile数据记录--->>>订单号:" + orderId);
//			bwKaBaoOperatorRiskprofileService.deleteAllByOrderId(orderId);
//			BwKabaoOperatorRiskprofile kbOperatorRiskprofile = new BwKabaoOperatorRiskprofile();
//			kbOperatorRiskprofile.setCallinfoContactsMatchValue(operatorRiskprofile.getCallinfoContactsMatchValue());
//			kbOperatorRiskprofile.setOrderId(orderId);
//			kbOperatorRiskprofile.setPhone(operatorRiskprofile.getPhone());
//			kbOperatorRiskprofile.setTotalContactLabelFinanceMarkCount(operatorRiskprofile.getTotalContactLabelFinanceMarkCount());
//			kbOperatorRiskprofile.setTotalContactLabelMarkCount(operatorRiskprofile.getTotalContactLabelMarkCount());
//			kbOperatorRiskprofile.setUsualPhoneEvalValue(operatorRiskprofile.getUsualPhoneEvalValue());
//			
//			kbOperatorRiskprofile.setCreateTime(new Date());
//			kbOperatorRiskprofile.setUpdateTime(new Date());
//			try {
//				//保存BwKabaoOperatorRiskprofile数据
//				bwKaBaoOperatorRiskprofileService.save(kbOperatorRiskprofile);
//			} catch (Exception e) {
//				logger.error(sessionId +":51卡宝--->>>处理BwKabaoOperatorRiskprofile数据失败--->>>订单号:" + orderId,e);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId +":51卡宝--->>>处理BwKabaoOperatorRiskprofile数据失败--->>>订单号:" + orderId,e);
//		}
//	}
//
//	private void addCallInfoOrigin(long sessionId, Long orderId, CallInfo callInfo, String account) {
//		try {
//			logger.info(sessionId +":51卡宝--->>>开始处理originCallInfo数据记录--->>>订单号:" + orderId);
//			bwKaBaoOriginCallInfoService.deleteAllByOrderId(orderId);
//			List<DataCallInfo> data = callInfo.getData();
//			if(CollectionUtils.isNotEmpty(data)){
//				//获取origin-->call_info-->data
//				for (DataCallInfo dataCallInfo : data) {
//					//获取origin-->call_info--->data--->details
//					if(Objects.equals(dataCallInfo.getCode(), "E000000")){
//						List<CallInfoDetails> details = dataCallInfo.getDetails();
//						if(CollectionUtils.isNotEmpty(details)){
//							for (CallInfoDetails callInfoDetails : details) {
//								BwKaBaoOriginCallInfo originCallInfo = new BwKaBaoOriginCallInfo();
//								originCallInfo.setMonth(dataCallInfo.getMonth());
//								originCallInfo.setAccount(account);
//								originCallInfo.setOrderId(orderId);
//								originCallInfo.setCreateTime(new Date());
//								originCallInfo.setUpdateTime(new Date());
//								originCallInfo.setAnotherNm(callInfoDetails.getAnother_nm());
//								originCallInfo.setCommMode(callInfoDetails.getComm_mode());
//								originCallInfo.setCommPlac(callInfoDetails.getComm_plac());
//								originCallInfo.setCommType(callInfoDetails.getComm_type());
//								originCallInfo.setCommFee(NumberUtils.toDouble(callInfoDetails.getComm_fee()));
//								originCallInfo.setCommTime(ConvertSecondUtil.getSecond(callInfoDetails.getComm_time()));
//								String startTime = callInfoDetails.getStart_time();
//								Date startDate = null;
//								if(StringUtils.isNotBlank(startTime)){
//									startDate = DateUtil.stringToDate(startTime, DateUtil.yyyy_MM_dd);
//								}
//								originCallInfo.setStartTime(startDate);
//								try {
//									//保存originCallInfo数据
//									bwKaBaoOriginCallInfoService.save(originCallInfo);
//								} catch (Exception e) {
//									logger.info(sessionId +":51卡宝--->>>处理originCallInfo数据失败--->>>订单号:" + orderId,e);
//								}
//							}
//						}else{
//							logger.info(sessionId +":51卡宝--->>>处理originCallInfo数据,通话详情数据为空--->>>订单号:" + orderId);
//						}
//						
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.error(sessionId +":51卡宝--->>>处理originCallInfo数据失败--->>>订单号:" + orderId,e);
//		}
//	}
//
//	private void addBillInfoOrigin(long sessionId, Long orderId, BillInfoOrigin billInfoOrigin, String account) {
//		try {
//			logger.info(sessionId +":51卡宝--->>>开始处理originBillInfo数据记录--->>>订单号:" + orderId);
//			bwKaBaoOriginBillInfoService.deleteAllByOrderId(orderId);
//			List<DataBillInfoOrigin> data = billInfoOrigin.getData();
//			if(CollectionUtils.isNotEmpty(data)){
//				for (DataBillInfoOrigin dataBillInfoOrigin : data) {
//					//获取origin-->bill_info--->data--->details
//					if(Objects.equals(dataBillInfoOrigin.getCode(), "E000000")){
//						List<DetailsBillInfoOrigin> details = dataBillInfoOrigin.getDetails();
//						if(CollectionUtils.isNotEmpty(details)){
//							for (DetailsBillInfoOrigin detailsBillInfoOrigin : details) {
//								BwKaBaoOriginBillInfo originBillInfo = new BwKaBaoOriginBillInfo();
//								//获取origin-->bill_info-->data
//								originBillInfo.setMonth(dataBillInfoOrigin.getMonth());
//								originBillInfo.setFee(NumberUtils.toDouble(dataBillInfoOrigin.getFee()));
//								originBillInfo.setAccount(account);
//								originBillInfo.setOrderId(orderId);
//								originBillInfo.setCreateTime(new Date());
//								originBillInfo.setUpdateTime(new Date());
//								originBillInfo.setName(detailsBillInfoOrigin.getName());
//								originBillInfo.setValue(NumberUtils.toDouble(detailsBillInfoOrigin.getValue()));
//								try {
//									//保存originBillInfo数据
//									bwKaBaoOriginBillInfoService.save(originBillInfo);
//								} catch (Exception e) {
//									logger.info(sessionId +":51卡宝--->>>处理originBillInfo数据失败--->>>订单号:" + orderId,e);
//								}
//							}
//						}else{
//							logger.info(sessionId +":51卡宝--->>>处理originBillInfo数据,账单详情数据为空--->>>订单号:" + orderId);
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.info(sessionId +":51卡宝--->>>处理originBillInfo数据失败--->>>订单号:" + orderId,e);
//		}
//	}
//
//	private void addBaseInfo(long sessionId, Long orderId, DataBaseInfo dataBaseInfo, String account) {
//		try {
//			logger.info(sessionId +":51卡宝--->>>开始处理originBaseInfo数据记录--->>>订单号:" + orderId);
//			bwKaBaoOriginBaseInfoService.deleteAllByOrderId(orderId);
//			BwKaBaoOriginBaseInfo originBaseInfo = new BwKaBaoOriginBaseInfo();
//			//获取origin-->base_info--->data
//			originBaseInfo.setAddress(dataBaseInfo.getAddress());
//			originBaseInfo.setBrand(dataBaseInfo.getBrand());
//			originBaseInfo.setEmail(dataBaseInfo.getEmail());
//			originBaseInfo.setIdCard(dataBaseInfo.getIdCard());
//			originBaseInfo.setInnetDate(dataBaseInfo.getInnetDate());
//			originBaseInfo.setLevel(dataBaseInfo.getLevel());
//			originBaseInfo.setName(dataBaseInfo.getName());
//			originBaseInfo.setRealnameInfo(dataBaseInfo.getRealnameInfo());
//			originBaseInfo.setSex(dataBaseInfo.getSex());
//			originBaseInfo.setStarLevel(dataBaseInfo.getStarLevel());
//			originBaseInfo.setStatus(dataBaseInfo.getStatus());
//			originBaseInfo.setZipCode(dataBaseInfo.getZipCode());
//			originBaseInfo.setOrderId(orderId);
//			originBaseInfo.setAccount(account);
//			originBaseInfo.setCreateTime(new Date());
//			originBaseInfo.setUpdateTime(new Date());
//			try {
//				//保存originBaseInfo数据
//				bwKaBaoOriginBaseInfoService.save(originBaseInfo);
//			} catch (Exception e) {
//				logger.info(sessionId +":51卡宝--->>>处理originBaseInfo数据失败--->>>订单号:" + orderId,e);
//			}
//		} catch (Exception e) {
//			logger.info(sessionId +":51卡宝--->>>处理originBaseInfo数据失败--->>>订单号:" + orderId,e);
//		}
//	}
//
//	private void addOperatorBase(long sessionId, Long orderId, OperatorBase operatorBase) {
//		try {
//			logger.info(sessionId +":51卡宝--->>>开始处理OperatorBase数据记录--->>>订单号:" + orderId);
//			//先删除当前订单所对应的OperatorBase数据
//			bwKaBaoOperatorBaseService.deleteAllByOrderId(orderId);
//			BwKaBaoOperatorBase bwKabaoOperatorBase = new BwKaBaoOperatorBase();
//			bwKabaoOperatorBase.setAddress(operatorBase.getAddress());
//			bwKabaoOperatorBase.setName(operatorBase.getName());
//			bwKabaoOperatorBase.setLocation(operatorBase.getLocation());
//			bwKabaoOperatorBase.setPhone(operatorBase.getPhone());
//			bwKabaoOperatorBase.setOrderId(orderId);
//			String openDateStr = operatorBase.getOpenDate();
//			Date openDate = null;
//			if(StringUtils.isNotBlank(openDateStr)){
//				openDate = DateUtil.stringToDate(openDateStr, DateUtil.yyyy_MM_dd);
//			}
//			bwKabaoOperatorBase.setOpenDate(openDate);
//			bwKabaoOperatorBase.setCreateTime(new Date());
//			bwKabaoOperatorBase.setUpdateTime(new Date());
//			try {
//				//保存OperatorBase数据
//				bwKaBaoOperatorBaseService.save(bwKabaoOperatorBase);
//			} catch (Exception e) {
//				logger.info(sessionId +":51卡宝--->>>处理OperatorBase数据失败--->>>订单号:" + orderId,e);
//			}
//		} catch (Exception e) {
//			logger.info(sessionId +":51卡宝--->>>处理OperatorBase数据失败--->>>订单号:" + orderId,e);
//		}
//	}
//	
//	/**
//     * 白骑士
//     *
//     * @param orderId 订单ID
//     */
//    private void postBaiqishi(Long sessionId, Long orderId) {
//        Map<String, String> params = new HashMap<>(16);
//        params.put("orderId", String.valueOf(orderId));
//    	// 发送http请求
//        try {
//        	String jsonString = HttpClientHelper.post(KaBaoConstant.BQS_URL, "utf-8", params);
//        	logger.info(sessionId + "白骑士校验：orderId>>>" + orderId + ">>>>+ 返回  >>>" + jsonString);
//			if (StringUtils.isNotBlank(jsonString)) {
//				JSONObject jsonObject = JSON.parseObject(jsonString);
//				String code = jsonObject.getString("code");
//				if ("000".equals(code)) {
//					logger.info(sessionId + "结束白骑士校验：orderId>>>" + orderId + ">> 成功");
//				}else{
//					logger.info(sessionId + "结束白骑士校验：orderId>>>" + orderId + ">> 失败");
//				}
//			}else{
//				logger.info(sessionId + "请求白骑士校验：orderId>>>" + orderId + ">> 返回为空!");
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + "结束白骑士校验>> 异常：orderId>>>" + orderId + ">>>" + e);
//		}
//    }
//}
