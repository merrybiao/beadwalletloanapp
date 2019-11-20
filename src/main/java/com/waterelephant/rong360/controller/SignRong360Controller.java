package com.waterelephant.rong360.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beadwallet.entity.lianlian.SignLess;
import com.beadwallet.servcie.LianLianPayService;
import com.beadwallet.service.rong360.entity.request.ApproveFeedBackReq;
import com.beadwallet.service.rong360.entity.request.BindCardFeedBackReq;
import com.beadwallet.service.rong360.entity.response.ApproveFeedBackResp;
import com.beadwallet.service.rong360.entity.response.BindCardFeedBackResp;
import com.beadwallet.service.rong360.service.BeadWalletRong360Service;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.base.BaseController;
import com.waterelephant.dto.SystemAuditDto;
import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderProcessRecord;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.entity.BwWorkInfo;
import com.waterelephant.rong360.util.LogUtil;
import com.waterelephant.rong360.util.ThreadLocalUtil;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwPersonInfoService;
import com.waterelephant.service.IBwWorkInfoService;
import com.waterelephant.utils.JsonUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

import net.sf.json.JSONObject;

@Deprecated
@Controller
@RequestMapping("/app/rong360")
public class SignRong360Controller extends BaseController {

	private static LogUtil logger = new LogUtil(SignRong360Controller.class);


	@Resource
	private IBwBankCardService iBwBankCardService;
	
	@Resource
	private IBwOrderService bwOrderService;
	
	@Autowired
	private BwOrderRongService bwOrderRongService;
	
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	
	@Autowired
	private IBwAdjunctService bwAdjunctService;
	
	@Autowired
	private IBwPersonInfoService bwPersonInfoService;
	
	@Autowired
	private IBwWorkInfoService bwWorkInfoService;
	
	@Autowired
	private BwOrderProcessRecordService bwOrderProcessRecordService;
	
	private static Integer BIND_CARD_STATUS_SUCCESS = 1;
	private static Integer BIND_CARD_STATUS_FAILED = 2;
	private static String ORDER_NO_SESSION = "rong360OrderNo";
	
	/**
	 * 签约操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/signRongCard.do")
	public void signRongCard(HttpServletRequest request, HttpServletResponse response) {
		String methodName = "SignController.signRongCard";
		
		try {
			String userId = request.getParameter("userId");
			String idNo = request.getParameter("idNo");
			String accName = request.getParameter("accName");
			String cardNo = request.getParameter("cardNo");
			String urlReturn = SystemConstant.NOTIRY_URL + "/app/rong360/signRongCardCallBack.do";
			String orderNo = request.getParameter("orderNo");
			ThreadLocalUtil.set("SIGNCARD-"+orderNo);
			
			HttpSession session = request.getSession();
			session.setAttribute(ORDER_NO_SESSION, orderNo);
			RedisUtils.hset("rong360:bindCard", userId, orderNo);
			RedisUtils.hset("rong360:session", session.getId(), orderNo);
			
			logger.info(methodName+" start,userId="+userId+",idNo="+idNo+",accName="+accName+",cardNo="+cardNo+",urlReturn="+urlReturn+",orderNo="+orderNo);
			
			SignLess signLess = new SignLess();
			signLess.setUser_id(userId);
			signLess.setId_no(idNo);
			signLess.setAcct_name(accName);
			signLess.setCard_no(cardNo);
			signLess.setUrl_return(urlReturn);
			logger.info("开始调用连连签约接口,signLess="+com.alibaba.fastjson.JSONObject.toJSONString(signLess));
			LianLianPayService.signAccreditPay(signLess, response);
			logger.info("结束调用连连签约接口");
		} catch (Exception e) {
			logger.error(methodName+" occured exception",e);
		}
		
		logger.info(methodName+" end");
		ThreadLocalUtil.remove();
	}

	/**
	 * 签约回调
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/signRongCardCallBack.do")
	public String signRongCardCallBack(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		String methodName = "SignRong360Controller.signRongCardCallBack";
		
		try {
			String status = request.getParameter("status");
			String result = request.getParameter("result");
			String thirdUrl = RedisUtils.hget("third:url", "rong360");
			
			HttpSession session = request.getSession();
			session.getAttribute(ORDER_NO_SESSION);
			
			String orderNo = null;
			
			logger.info("get orderNo from session");
			orderNo = String.valueOf(session.getAttribute(ORDER_NO_SESSION));
			session.removeAttribute(ORDER_NO_SESSION);
			logger.info("get orderNo from session is "+orderNo);
			
			if (StringUtils.isBlank(orderNo)) {
				logger.info("start get orderNo from redis[rong360:session]");
				orderNo = RedisUtils.hget("rong360:session", session.getId());
				logger.info("end get orderNo from redis[rong360:session] value is "+orderNo);
			}
			
			RedisUtils.hdel("rong360:session", session.getId());
			
			ThreadLocalUtil.set("SIGNBACK-"+orderNo);
			
			logger.info(methodName+" start,status="+status+",result="+result+",thirdUrl="+thirdUrl);
			
			if (CommUtils.isNull(status)) {
				bindCardFeedBack(orderNo, BIND_CARD_STATUS_FAILED);
				map.put("result", "系统异常");
				logger.info(methodName+" end,status is null,return index page");
				ThreadLocalUtil.remove();
				return "sign_fail_rong360";
			}
			
			// 签约成功
			if (status.equals("0000")) {
				logger.info("signCardCallBack success,status = 0000,start update signStatus=2");
				try {
					JSONObject jsonObject = JSONObject.fromObject(result);
					String userId = jsonObject.getString("user_id");
					if (StringUtils.isBlank(orderNo)) {
						logger.info("get orderNo from redis[rong360:bindCard]");
						orderNo = RedisUtils.hget("rong360:bindCard", userId);
						logger.info("get orderNo from redis[rong360:bindCard] is "+orderNo);
					}
					RedisUtils.hdel("rong360:bindCard", userId);
					
					// 更新为已签约
					iBwBankCardService.updateSignStatusByBorrowerId(Long.valueOf(userId));
					
					bindCardFeedBack(orderNo, BIND_CARD_STATUS_SUCCESS);
					// 修改工单为待审核状态
					updStatus2PreApprove(orderNo);
					logger.info(methodName+" end,signCardCallBack success,status = 0000");
					ThreadLocalUtil.remove();
					return "redirect:" + thirdUrl;
				} catch (Exception e) {
					bindCardFeedBack(orderNo, BIND_CARD_STATUS_FAILED);
					map.put("result", "系统异常");
					logger.error(methodName+" end,signCardCallBack occured exception",e);
					ThreadLocalUtil.remove();
					return "sign_fail_rong360";
				}
			} else {
				// 签约失败
				map.put("result", result);
				bindCardFeedBack(orderNo, BIND_CARD_STATUS_FAILED);
				logger.info(methodName+" end,signCardCallBack failed,status != 0000");
				ThreadLocalUtil.remove();
				return "sign_fail_rong360";
			}
		} catch (Exception e) {
			logger.error(methodName+" occured exception", e);
			map.put("result", "系统异常");
			ThreadLocalUtil.remove();
			return "sign_fail_rong360";
		}
	}
	
	private void bindCardFeedBack(String orderNo, Integer status) {
		try {
			BindCardFeedBackReq bindCardFeedBackReq = new BindCardFeedBackReq();
			bindCardFeedBackReq.setOrder_no(orderNo);
			bindCardFeedBackReq.setBind_status(status);
			logger.info("开始调用融360绑卡反馈接口，"+bindCardFeedBackReq);
			BindCardFeedBackResp bindCardFeedBackResp = BeadWalletRong360Service.bindCardFeedBack(bindCardFeedBackReq);
			logger.info("结束调用融360绑卡反馈接口，"+bindCardFeedBackResp);
		} catch (Exception e) {
			logger.error("调用融360绑卡反馈接口异常",e);
		}
	}
	
	public void updStatus2PreApprove(String orderNo){
		BwOrder bwOrder = getOrderByTOrderNo(orderNo);
		
		if (CommUtils.isNull(bwOrder)) {
			logger.info("订单暂未入库,绑卡标记成功");
			RedisUtils.hset("rong360:bindTag", orderNo, orderNo);
			return;
		}
		
		if (bwOrder.getStatusId().intValue() != 1) {
			logger.info("工单状态不为1,不重复修改");
			return;
		}
		
		boolean check = checkAddInfo(bwOrder);
		if (check) {
			preApprove(bwOrder, orderNo);
		}else {
			// 如果补充信息还没有，在redis中做标记
			logger.info("补充信息暂无,绑卡标记成功");
			RedisUtils.hset("rong360:bindTag", orderNo, orderNo);
		}
	}
	
	public BwOrder getOrderByTOrderNo(String orderNo){
		BwOrder bwOrder = null;
		try {
			logger.info("开始查询融360工单，thirdOrderNo="+orderNo+"...");
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
			logger.info("查询融360工单为:"+com.alibaba.fastjson.JSONObject.toJSONString(bwOrderRong));
			
			if (CommUtils.isNull(bwOrderRong)){
				logger.info("融360工单不存在");
				return null;
			}
			
			logger.info("开始查询工单信息，orderId="+bwOrderRong.getOrderId()+"...");
			bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
			logger.info("查询工单信息为:"+com.alibaba.fastjson.JSONObject.toJSONString(bwOrder));
			
			if (CommUtils.isNull(bwOrder)) {
				logger.info("工单不存在");
				return null;
			}
		} catch (Exception e) {
			logger.error("getOrderByTOrderNo occured exception:", e);
		}
		
		return bwOrder;
		
	}
	
	public boolean checkAddInfo(BwOrder order){
		boolean bool = true;
		logger.info("开始验证工单补充信息是否存在,orderId="+order.getId());
		try {
			logger.info("开始验证持证照信息");
			BwAdjunct czzbaj = new BwAdjunct();
			czzbaj.setAdjunctType(3);
			czzbaj.setOrderId(order.getId());
			czzbaj = bwAdjunctService.findBwAdjunctByAttr(czzbaj);
			
			if (CommUtils.isNull(czzbaj)){
				logger.info("持证照信息为空");
				return false;
			}
			logger.info("持证照信息验证通过");
			
			logger.info("开始验证身份证正面照信息");
			BwAdjunct sfzzmbaj = new BwAdjunct();
			sfzzmbaj.setAdjunctType(1);
			sfzzmbaj.setOrderId(order.getId());
			sfzzmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzzmbaj);
			
			
			if (CommUtils.isNull(sfzzmbaj)){
				logger.info("身份证正面照信息为空");
				return false;
			}
			logger.info("身份证正面照信息验证通过");
			
			logger.info("开始验证身份证反面照信息");
			BwAdjunct sfzfmbaj = new BwAdjunct();
			sfzfmbaj.setAdjunctType(2);
			sfzfmbaj.setOrderId(order.getId());
			sfzfmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzfmbaj);
			
			
			if (CommUtils.isNull(sfzfmbaj)) {
				logger.info("身份证反面照为空");
				return false;
			}
			logger.info("身份证反面照信息验证通过");
			
			logger.info("开始验证联系人信息");
			BwPersonInfo bpi = bwPersonInfoService.findBwPersonInfoByOrderId(order.getId());
			
			if (CommUtils.isNull(bpi)) {
				logger.info("联系人信息为空");
				return false;
			}
			logger.info("联系人信息验证通过");
			
			logger.info("开始验证公司信息");
			BwWorkInfo bwi = new BwWorkInfo();
			bwi.setOrderId(order.getId());
			bwi = bwWorkInfoService.findBwWorkInfoByAttr(bwi);
			if (CommUtils.isNull(bwi)) {
				logger.info("公司信息为空");
				return false;
			}
			logger.info("公司信息验证通过");
			
			logger.info("工单补充信息验证通过!");
		} catch (Exception e) {
			bool = false;
			logger.error("验证工单补充信息异常:", e);
		}
		
		return bool;
	}
	
	public void preApprove(BwOrder bwOrder, String orderNo){
		String methodName = "SignRong360Controller.preApprove";
		
		logger.info("开始查询进行中的订单,borrowerId="+bwOrder.getBorrowerId());
		Long count = bwOrderService.findProOrder(String.valueOf(bwOrder.getBorrowerId()));
		logger.info("结束查询进行中的订单,count="+count);
		
		if (count != null && count.intValue() > 0) {
			try {
				// 修改工单
				logger.info("将工单:" + bwOrder.getId() + "的状态修改为7");
				bwOrder.setStatusId(7L);
				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
				bwOrderService.updateBwOrder(bwOrder);
				
				ApproveFeedBackReq req = new ApproveFeedBackReq();
				req.setOrder_no(orderNo);
				req.setConclusion(40);
				req.setRemark("信用评分过低#拒绝客户");
				req.setRefuse_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
				logger.info("开始反馈拒绝,req="+com.alibaba.fastjson.JSONObject.toJSONString(req));
				ApproveFeedBackResp resp =BeadWalletRong360Service.approveFeedBack(req);
				logger.info("结束反馈拒绝,resp="+com.alibaba.fastjson.JSONObject.toJSONString(resp));
			} catch (Exception e2) {
				logger.error("反馈拒绝异常:", e2);
			}
			ThreadLocalUtil.remove();
			logger.info(methodName+" end,已有进行中的订单,直接拒绝");
			return;
		}
		
		logger.info("开始设置工单状态为待初审状态,orderId="+bwOrder.getId());
		try {
//			// 修改认证状态为4
			logger.info("将借款人" + bwOrder.getBorrowerId() + "的认证状态修改为4");
			BwBorrower borrower = updateBorrower(bwOrder.getBorrowerId());
//			
//			// 修改工单
			logger.info("将工单" + bwOrder.getId() + "的状态修改为2");
			bwOrder.setStatusId(2L);
			bwOrder.setSubmitTime(Calendar.getInstance().getTime());
			bwOrderService.updateBwOrder(bwOrder);
//			
//			// 将待审核的信息放入Redis中
			logger.info("开始存入redis["+SystemConstant.AUDIT_KEY+"]");
			addRedis(bwOrder, borrower, orderNo);
			logger.info("存入redis["+SystemConstant.AUDIT_KEY+"]结束");
			
			try {
				BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
				bwOrderProcessRecord.setOrderId(bwOrder.getId());
				bwOrderProcessRecord.setSubmitTime(new Date());
				bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
			} catch (Exception e) {
				logger.error("occured exception", e);
			}
		} catch (Exception e) {
			logger.error(methodName+" occured exception", e);
		}
	}
	
	private BwBorrower updateBorrower(Long borrowerId){
		BwBorrower borrower = new BwBorrower();
		borrower.setId(borrowerId);
		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
		borrower.setAuthStep(4);
		bwBorrowerService.updateBwBorrower(borrower);
		return borrower;
	}
	
	private void addRedis(BwOrder bwOrder, BwBorrower borrower, String thirdOrderId){
		SystemAuditDto systemAuditDto = new SystemAuditDto();
		systemAuditDto.setIncludeAddressBook(0);
		systemAuditDto.setOrderId(bwOrder.getId());
		systemAuditDto.setBorrowerId(bwOrder.getBorrowerId());
		systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
		systemAuditDto.setName(borrower.getName());
		systemAuditDto.setPhone(borrower.getPhone());
		systemAuditDto.setIdCard(borrower.getIdCard());
		systemAuditDto.setChannel(11);
		systemAuditDto.setThirdOrderId(thirdOrderId);
		logger.info("redis content:"+JsonUtils.toJson(systemAuditDto));
		Long result = RedisUtils.hset(SystemConstant.AUDIT_KEY, systemAuditDto.getOrderId().toString(), JsonUtils.toJson(systemAuditDto));
		if (CommUtils.isNull(result) || result.intValue() < 1) {
			logger.info("放入redis失败");
		}else {
			logger.info("放入redis成功");
		}
	}
}