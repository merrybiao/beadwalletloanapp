//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.beadwallet.service.utils.HttpClientHelper;
//import com.beadwallet.utils.JsonUtils;
//import com.waterelephant.dto.SystemAuditDto;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderProcessRecord;
//import com.waterelephant.entity.BwOrderRong;
//import com.waterelephant.service.BwOrderProcessRecordService;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.IBwOrderService;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.BillVo;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.BwXygjBase;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.BwXygjBill;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.BwXygjCall;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.BwXygjGprs;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.BwXygjSms;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.CallVo;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.GprsVo;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.SmsVo;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.XygjOperator;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.XygjOperatorBase;
//import com.waterelephant.sxyDrainage.service.AsyncXjgjTask;
//import com.waterelephant.sxyDrainage.service.BwXygjBaseService;
//import com.waterelephant.sxyDrainage.service.BwXygjBillService;
//import com.waterelephant.sxyDrainage.service.BwXygjCallService;
//import com.waterelephant.sxyDrainage.service.BwXygjGprsService;
//import com.waterelephant.sxyDrainage.service.BwXygjSmsService;
//import com.waterelephant.utils.OkHttpUtil;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.SystemConstant;
//
///**
// * 
// * <p>Title: AsyncXjgjTaskImpl</p>  
// * <p>Description: 信用管家   异步处理运营商数据</p>
// * @since JDK 1.8  
// * @author xiongfeng
// */
//@Component
//public class AsyncXjgjTaskImpl implements AsyncXjgjTask {
//	private Logger logger = LoggerFactory.getLogger(AsyncXjgjTaskImpl.class);
//
//	@Autowired
//    private IBwOrderService bwOrderService;
//    @Autowired
//    private BwOrderProcessRecordService bwOrderProcessRecordService;
//	@Autowired
//	private BwOrderRongService bwOrderRongService;
//	@Autowired
//	private BwXygjGprsService bwXygjGprsService;
//	@Autowired
//	private BwXygjSmsService bwXygjSmsService;
//	@Autowired
//	private BwXygjCallService bwXygjCallService;
//	@Autowired
//	private BwXygjBillService bwXygjBillService;
//	@Autowired
//	private BwXygjBaseService bwXygjBaseService;
//	
//	
//	@Transactional(rollbackFor = RuntimeException.class)
//    @Async("taskExecutor")
//	@Override
//	public void addOperator(Long sessionId, BwOrder bwOrder,BwBorrower borrower, XygjOperator operator) {
//		try{
//		logger.info(sessionId +":信用管家>>>开始处理运营商数据");
//		
//		Long orderId = bwOrder.getId();
//		logger.info(sessionId +":信用管家>>>处理运营商数据>>>订单号:" + orderId);
//		
//		XygjOperatorBase base = operator.getBase();			 // 基础数据
//		List<BillVo> billVOList = operator.getBillVOList();  // 账单信息
//		List<CallVo> callVOList = operator.getCallVOList();  // 通话记录
//		List<SmsVo> smsVOList = operator.getSmsVOList();     // 短信记录
//		List<GprsVo> gprsVOList = operator.getGprsVOList();  // 月流量使用情况
//		
//		if(null != base){
//			addBase(sessionId, orderId, base);
//		}else{
//			logger.info(sessionId +":信用管家>>>处理运营商数据>>>订单号:" + orderId + "基础信息base为空");
//		}
//		
//		if (CollectionUtils.isNotEmpty(billVOList)) {
//			addBill(sessionId, orderId, billVOList);
//		}else{
//			logger.info(sessionId +":信用管家>>>处理运营商数据>>>订单号:" + orderId + "月账单billVOList为空");
//		}
//		
//		if (CollectionUtils.isNotEmpty(callVOList)) {
//			addCall(sessionId, orderId, callVOList);
//		}else{
//			logger.info(sessionId +":信用管家>>>处理运营商数据>>>订单号:" + orderId + "通话详单callVOList为空");
//		}
//		
//		if (CollectionUtils.isNotEmpty(smsVOList)) {
//			addSms(sessionId, orderId, smsVOList);
//		}else{
//			logger.info(sessionId +":信用管家>>>处理运营商数据>>>订单号:" + orderId + "短信详单smsVOList为空");
//		}
//		
//		if (CollectionUtils.isNotEmpty(gprsVOList)) {
//			addGprs(sessionId, orderId, gprsVOList);
//		}else{
//			logger.info(sessionId +":信用管家>>>处理运营商数据>>>订单号:" + orderId + "月流量gprsVOList为空");
//		}
//		
//		//白骑士风控
//        /*postBaiqishi(sessionId, orderId);*/
//		
//		BwOrderRong bwOrderRong = new BwOrderRong();
//		bwOrderRong.setOrderId(orderId);
//		bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//		
//		// 系统审核任务 审核 放入redis
//		SystemAuditDto systemAuditDto = new SystemAuditDto();
//		systemAuditDto.setIncludeAddressBook(0);
//		systemAuditDto.setOrderId(orderId);
//		systemAuditDto.setBorrowerId(bwOrder.getBorrowerId());
//		systemAuditDto.setName(borrower.getName());
//		systemAuditDto.setPhone(borrower.getPhone());
//		systemAuditDto.setIdCard(borrower.getIdCard());
//		systemAuditDto.setChannel(bwOrderRong.getChannelId().intValue());
//		systemAuditDto.setThirdOrderId(bwOrderRong.getThirdOrderNo());
//		systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//		RedisUtils.hset(SystemConstant.AUDIT_KEY, orderId + "", JsonUtils.toJson(systemAuditDto));
//		logger.info(sessionId + ":信用管家>>>结束修改订单状态,并放入redis");
//		
//		//修改订单状态放到异步运营商中处理
//		bwOrder.setStatusId(2L); // 2初审
//		bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//		bwOrder.setSubmitTime(Calendar.getInstance().getTime());
//		bwOrderService.updateBwOrder(bwOrder);
//		
//		HashMap<String, String> hm = new HashMap<>();
//		hm.put("channelId", bwOrder.getChannel() + "");
//		hm.put("orderId", String.valueOf(orderId));
//		hm.put("orderStatus", 2 + "");
//		hm.put("result", "");
//		String hmData = JSON.toJSONString(hm);
//		RedisUtils.rpush("tripartite:orderStatusNotify:" + bwOrder.getChannel(), hmData);
//		
//		// 修改工单进程表
//		logger.info(sessionId + ":信用管家>>>开始修改工单进程表");
//		BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//		bwOrderProcessRecord.setSubmitTime(new Date());
//		bwOrderProcessRecord.setOrderId(bwOrder.getId());
//		bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//		logger.info(sessionId + ":信用管家>>>结束更改订单进行时间");
//					
//		logger.info(sessionId +":信用管家>>>结束处理运营商数据");
//		}catch(Exception e){
//			logger.info(sessionId +":信用管家>>>异步处理运营商数据异常"+e);	
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 添加月流量
//	 */
//	private void addGprs(Long sessionId, Long orderId, List<GprsVo> gprsVOList) {
//		// TODO Auto-generated method stub
//		// 先删除所有记录
//		bwXygjGprsService.deleteAllByOrderId(orderId);
//		
//		for (GprsVo gprsVo : gprsVOList) {
//			BwXygjGprs data = new BwXygjGprs();
//			data.setOrder_id(orderId);
//			data.setTotalflow(gprsVo.getTotalFlow());
//			
//			try {
//				bwXygjGprsService.saveGprs(data);
//			} catch (Exception e) {
//				logger.info(sessionId +":信用管家>>>gprs记录添加表失败,忽略此条记录>>>订单号:" + orderId,e);
//			}
//		}
//		logger.info(sessionId +":信用管家>>>完成处理月流量gprs>>>订单号:" + orderId);
//	}
//
//	/**
//	 * 添加短信
//	 */
//	private void addSms(Long sessionId, Long orderId, List<SmsVo> smsVOList) {
//		// TODO Auto-generated method stub
//		// 先删除所有记录
//		bwXygjSmsService.deleteAllByOrderId(orderId);
//		
//		for (SmsVo smsVo : smsVOList) {
//			BwXygjSms data = new BwXygjSms();
//			data.setOrder_id(orderId);
//			data.setBusinesstype(smsVo.getBusinesstype());
//			data.setHomearea(smsVo.getHomearea());
//			data.setMonth(smsVo.getMonth());
//			data.setSmsfee(smsVo.getSmsfee());
//			data.setSmsphone(smsVo.getSmsphone());
//			data.setSmstime(smsVo.getSmstime());
//			data.setSmstype(smsVo.getSmstype());
//			
//			try {
//				bwXygjSmsService.saveSms(data);
//			} catch (Exception e) {
//				logger.info(sessionId +":信用管家>>>短信记录添加表失败,忽略此条记录>>>订单号:" + orderId,e);
//			}
//		}
//		logger.info(sessionId +":信用管家>>>完成处理短信记录>>>订单号:" + orderId);
//	}
//
//	/**
//	 * 添加通话记录
//	 */
//	private void addCall(Long sessionId, Long orderId, List<CallVo> callVOList) {
//		// TODO Auto-generated method stub
//		// 先删除所有记录
//		bwXygjCallService.deleteAllByOrderId(orderId);
//		
//		for (CallVo callVo : callVOList) {
//			BwXygjCall data = new BwXygjCall();
//			data.setOrder_id(orderId);
//			data.setCalllong(callVo.getCalllong());
//			data.setCallphone(callVo.getCallphone());
//			data.setCalltime(callVo.getCalltime());
//			data.setCalltype(callVo.getCalltype());
//			data.setHomearea(callVo.getHomearea());
//			data.setLandtype(callVo.getLandtype());
//			data.setMonth(callVo.getMonth());
//			data.setThtypename(callVo.getThtypename());
//			
//			try {
//				bwXygjCallService.saveCall(data);
//			} catch (Exception e) {
//				logger.info(sessionId +":信用管家>>>通话记录添加表失败,忽略此条记录>>>订单号:" + orderId,e);
//			}
//		}
//		logger.info(sessionId +":信用管家>>>完成处理通话记录>>>订单号:" + orderId);
//	}
//
//	/**
//	 * 添加月账单
//	 */
//	private void addBill(Long sessionId, Long orderId, List<BillVo> billVOList) {
//		// TODO Auto-generated method stub
//		// 先删除所有记录
//		bwXygjBillService.deleteAllByOrderId(orderId);
//		
//		for (BillVo billVo : billVOList) {
//			BwXygjBill data = new BwXygjBill();
//			data.setOrder_id(orderId);
//			data.setBasefee(billVo.getBaseFee());
//			data.setMonth(billVo.getBaseFee());
//			data.setTotalfee(billVo.getTotalFee());
//			
//			try {
//				bwXygjBillService.saveBill(data);
//			} catch (Exception e) {
//				logger.info(sessionId +":信用管家>>>月账单添加表失败,忽略此条记录>>>订单号:" + orderId,e);
//			}
//		}
//		logger.info(sessionId +":信用管家>>>完成处理月账单记录>>>订单号:" + orderId);
//	}
//
//	/**
//	 * 添加基础信息
//	 */
//	private void addBase(Long sessionId, Long orderId, XygjOperatorBase base) {
//		// TODO Auto-generated method stub
//		// 先删除所有记录
//		bwXygjBaseService.deleteAllByOrderId(orderId);
//		
//		BwXygjBase data = new BwXygjBase();
//		data.setOrder_id(orderId);
//		data.setAddress(base.getAddress());
//		data.setBalance(base.getBalance());
//		data.setCertify(base.getCertify());
//		data.setCity(base.getCity());
//		data.setCreatetime(base.getCreateTime());
//		data.setIdcard(base.getIdCard());
//		data.setMobile(base.getMobile());
//		data.setOpentime(base.getOpenTime());
//		data.setProvince(base.getProvince());
//		data.setToken(base.getToken());
//		data.setTruename(base.getTruename());
//		data.setType(base.getType());
//		
//		try {
//			bwXygjBaseService.saveBase(data);
//		} catch (Exception e) {
//			logger.info(sessionId +":信用管家>>>处理基本信息失败>>>订单号:" + orderId,e);
//		}
//		logger.info(sessionId +":信用管家>>>完成处理基础信息记录>>>订单号:" + orderId);
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
//        String url = "https://tt.sxfq.com/loanapp-api-web/v3/app/order/a10/getSanFangBqs.do";
//        try {
//        	String jsonString = HttpClientHelper.post(url, "utf-8", params);
//    		JSONObject jsonObject = JSON.parseObject(jsonString);
//    		String code = jsonObject.getString("code");
//    		String desc = jsonObject.getString("desc");
//    		String data = jsonObject.getString("data");
//
//    		if ("0".equals(code)) {
//    			logger.info(sessionId + ":信用管家<<<白骑士访问成功：code=" + code + ",desc=" + desc + ",data=" + data);
//    		} else {
//    			logger.info(sessionId + ":信用管家<<<白骑士访问失败：code=" + code + ",desc=" + desc + ",data=" + data);
//    		}
//    		
//		} catch (Exception e) {
//			logger.info(sessionId + ":信用管家<<<白骑士访问接口异常",e);
//		}
//		
//    }
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
