package com.waterelephant.drainage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.entity.lianlian.Agreement;
import com.beadwallet.entity.lianlian.CardQueryResult;
import com.beadwallet.entity.lianlian.NotifyNotice;
import com.beadwallet.entity.lianlian.NotifyResult;
import com.beadwallet.entity.lianlian.PlanResult;
import com.beadwallet.entity.lianlian.RepayRequest;
import com.beadwallet.entity.lianlian.RepaymentPlan;
import com.beadwallet.entity.lianlian.RepaymentResult;
import com.beadwallet.entity.lianlian.SignLess;
import com.beadwallet.entity.lianlian.SignRequest;
import com.beadwallet.entity.lianlian.SignResponse;
import com.beadwallet.entity.lianlian.SignalLess;
import com.beadwallet.servcie.LianLianPayService;
import com.beadwallet.service.baiqishi.entity.ServiceResult91;
import com.beadwallet.service.drainage.FqgjServiceSDK;
import com.beadwallet.service.entity.request.MsgReqData;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.serve.BeadWalletSendMsgService;
import com.beadwallet.service.sms.dto.MessageDto;
import com.beadwallet.utils.RSAUtil;
import com.google.gson.Gson;
import com.waterelephant.controller.AppActivityController;
import com.waterelephant.drainage.entity.fqgj.DrainageResp;
import com.waterelephant.drainage.entity.fqgj.FqgjIsOldUserData;
import com.waterelephant.drainage.entity.fqgj.FqgjReq;
import com.waterelephant.drainage.entity.fqgj.OrderFeedBackReq;
import com.waterelephant.drainage.entity.fqgj.OrderFeedBackResp;
import com.waterelephant.drainage.entity.fqgj.RepayFeedBackReq;
import com.waterelephant.drainage.entity.fqgj.RepayFeedBackResp;
import com.waterelephant.drainage.jsonentity.fqgj.FqgjApproveConfirmData;
import com.waterelephant.drainage.jsonentity.fqgj.FqgjApproveContractData;
import com.waterelephant.drainage.jsonentity.fqgj.FqgjBindCardData;
import com.waterelephant.drainage.jsonentity.fqgj.FqgjOrder;
import com.waterelephant.drainage.jsonentity.fqgj.FqgjOrderInfo;
import com.waterelephant.drainage.jsonentity.fqgj.FqgjSupplementContact;
import com.waterelephant.drainage.jsonentity.fqgj.FqgjSupplementOrderInfo;
import com.waterelephant.drainage.jsonentity.fqgj.FqgjSupplementPhone;
import com.waterelephant.drainage.service.DrainageService;
import com.waterelephant.drainage.service.FqgjService;
import com.waterelephant.drainage.util.fqgj.FqgjUtil;
import com.waterelephant.dto.SystemAuditDto;
import com.waterelephant.entity.*;
import com.waterelephant.drainage.util.fqgj.ThreadLocalUtil;
import com.waterelephant.service.*;
import com.waterelephant.service.impl.BwRepaymentPlanService;
import com.waterelephant.sms.service.SendMessageCommonService;
import com.waterelephant.utils.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分期管家调用接口
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/20 10:24
 */

@Controller
public class FqgjCallBackController {

	private Logger logger = Logger.getLogger(AppActivityController.class);
	private static String FQGJ_XUDAI = "xudai:order_id";

	private static Map<String, String> NOTIFY_MAP = new HashMap<String, String>();
	static {
		NOTIFY_MAP.put("repay", "/drainage/fqgj/callback/repaymentNotify.do");
		NOTIFY_MAP.put("defer", "/drainage/fqgj/callback/deferNotify.do");
	}

	@Autowired
	private DrainageService drainageService;
	@Autowired
	private FqgjService fqgjService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private SendMessageCommonService sendMessageCommonService;
	@Autowired
	private BwRepaymentPlanService bwRepaymentPlanService;
	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;
	@Autowired
	private IBwBankCardService iBwBankCardService;
	@Autowired
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
	private BwPlatformRecordService bwPlatformRecordService;
	@Autowired
	private IBwRepaymentService bwRepaymentService;
	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;
	@Autowired
	private BwOrderProcessRecordService bwOrderProcessRecordService;
	@Autowired
	private IBwContactListService bwContactListService;

	private static String APPROVE_STATUS_PASS = "10";
	private static String APPROVE_STATUS_REFUSED = "40";
	private static Integer BIND_CARD_STATUS_SUCCESS = 1;
	private static Integer BIND_CARD_STATUS_FAILED = 2;

	/**
	 * 老用户 黑名单 过滤
	 *
	 * @param req
	 * @return
	 * @author GuoKun
	 */
	@ResponseBody
	@RequestMapping("/drainage/fqgj/callback/isolduser.do")
//	public DrainageResp isolduser(HttpServletRequest request,HttpServletResponse response) {
	public DrainageResp isolduser(@RequestBody FqgjReq req) {
		logger.info("~~~~~~~~~~~~~~~~~~ 分期管家老用户判断接口");
		ThreadLocalUtil.set("ISOLDUSER");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String methodName = "DrainageCallBackController.isolduser：";

//		FqgjReq req = new FqgjReq();
//		req.setApp_id(request.getParameter("app_id"));
//		req.setBiz_data(request.getParameter("biz_data"));
//		req.setFormat(request.getParameter("format"));
//		req.setSign(request.getParameter("sign"));
//		req.setSign_type(request.getParameter("sign_type"));
//		req.setTimestamp(request.getParameter("timestamp"));
//		req.setVersion(request.getParameter("version"));
//		String bizData = req.getBiz_data();
//		logger.info(methodName + " start,bizData=" + bizData);
		
		logger.info(methodName + " start,req=" + req);
		DrainageResp resp = new DrainageResp();

		try {
			// 请求参数非空验证
			String check = FqgjUtil.isolduser(req);
			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}
			// 验签
			String s = JSONObject.toJSONString(req);
			logger.info(s);
			boolean checkSign = FqgjUtil.veriry(JSONObject.parseObject(s));
			logger.info("分期管家验签结果:" + checkSign);
			if (!checkSign) {
				resp.setCode("101");
				resp.setMsg("验签失败");
				methodEnd(stopWatch, methodName, "验签失败", resp);
				return resp;
			}

			// 请求数据转换为实体对象
			String bizData = req.getBiz_data();
			FqgjIsOldUserData fqgjIsOldUserData = JSONObject.parseObject(bizData, FqgjIsOldUserData.class);
			if (CommUtils.isNull(fqgjIsOldUserData)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "fqgjIsOldUserData is null", resp);
				return resp;
			}

			// 获取身份证，姓名，电话
			String idCard = fqgjIsOldUserData.getId_card();
			String phone = fqgjIsOldUserData.getUser_mobile();
			String name = fqgjIsOldUserData.getUser_name();
			ThreadLocalUtil.set("ISOLDUSER-" + name + "|" + idCard + "|" + phone);
			logger.info("参数校验通过");
			if (StringUtils.isBlank(idCard)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "idCard is null", resp);
				return resp;
			}
			if (StringUtils.isBlank(phone)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "phone is null", resp);
				return resp;
			}
			if (StringUtils.isBlank(name)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "name is null", resp);
				return resp;
			}

			// 判断是否为老用户
			resp = drainageService.isOldUser(idCard, name);
		} catch (Exception e) {
			logger.error(methodName + " 异常", e);
			resp.setCode("103");
			resp.setMsg("系统异常，请稍后再试");
		}
		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 接受工单基本信息
	 *
	 * @param req
	 * @return
	 * @author GuoKun
	 * @author GuoKun
	 */
	@ResponseBody
	@RequestMapping("/drainage/fqgj/callback/orderPush.do")
	public DrainageResp orderPush(@RequestBody FqgjReq req ) {
		logger.info("~~~~~~~~~~~~~~~~~~ 分期管家订单推送接口");
		ThreadLocalUtil.set("orderPush");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String methodName = "DrainageCallBackController.orderPush";
		logger.info(methodName + " start");
//		FqgjReq req = new FqgjReq();
//		req.setApp_id(request.getParameter("app_id"));
//		req.setBiz_data(request.getParameter("biz_data"));
//		req.setFormat(request.getParameter("format"));
//		req.setSign(request.getParameter("sign"));
//		req.setSign_type(request.getParameter("sign_type"));
//		req.setTimestamp(request.getParameter("timestamp"));
//		req.setVersion(request.getParameter("version"));
		// logger.info(req.getBiz_data()); // 生产请注释
		DrainageResp resp = new DrainageResp();

		try {
			// 请求参数非空验证
			String check = FqgjUtil.isolduser(req);
			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			// 验签
			String s = JSONObject.toJSONString(req);
			boolean checkSign = FqgjUtil.veriry(JSONObject.parseObject(s));
			logger.info("分期管家验签结果:" + checkSign);
			if (!checkSign) {
				resp.setCode("101");
				resp.setMsg("验签失败");
				methodEnd(stopWatch, methodName, "验签失败", resp);
				return resp;
			}

			// 参数转换
			String biz_data = req.getBiz_data();
			FqgjOrder fqgjOrder = JSONObject.parseObject(biz_data, FqgjOrder.class);
			if (fqgjOrder == null) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "OrderDetail is null", resp);
				return resp;
			}

			// 获取订单信息
			FqgjOrderInfo orderInfo = fqgjOrder.getOrder_info();
			if (orderInfo == null) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "orderInfo is null", resp);
				return resp;
			}

			String orderNo = orderInfo.getOrderNo();
			ThreadLocalUtil.set("BASEINFO-" + orderNo);
			logger.info("参数校验通过");
			logger.info("分期管家推单的订单号为：" + orderNo);
			boolean save = fqgjService.saveOrderPush(fqgjOrder);
			if (save) {
				resp.setCode("200");
				resp.setMsg("接受订单成功");
			} else {
				resp.setCode("205");
				resp.setMsg("订单保存失败");
			}
		} catch (Exception e) {
			logger.error(methodName + " 异常", e);
			resp.setCode("102");
			resp.setMsg("系统异常，请稍后再试");
		}

		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 接收工单补充信息
	 *
	 * @param req
	 * @return
	 * @author GuoKun
	 * @author GuoKun
	 */
	@ResponseBody
	@RequestMapping("/drainage/fqgj/callback/orderAddInfoPush.do")
	public DrainageResp orderAddInfoPush(@RequestBody FqgjReq req) {
		logger.info("~~~~~~~~~~~~~~~~~~ 分期管家订单补充信息推送接口");
		ThreadLocalUtil.set("orderPush");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String methodName = "DrainageCallBackController.orderAddInfoPush";
		logger.info(methodName + " start");
//		FqgjReq req = new FqgjReq();
//		req.setApp_id(request.getParameter("app_id"));
//		req.setBiz_data(request.getParameter("biz_data"));
//		req.setFormat(request.getParameter("format"));
//		req.setSign(request.getParameter("sign"));
//		req.setSign_type(request.getParameter("sign_type"));
//		req.setTimestamp(request.getParameter("timestamp"));
//		req.setVersion(request.getParameter("version"));
		DrainageResp resp = new DrainageResp();

		try {
			// 请求参数非空验证
			String check = FqgjUtil.isolduser(req);
			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			// 验签
			String s = JSONObject.toJSONString(req);
			boolean checkSign = FqgjUtil.veriry(JSONObject.parseObject(s));
			logger.info("分期管家验签结果:" + checkSign);
			if (!checkSign) {
				resp.setCode("101");
				resp.setMsg("验签失败");
				methodEnd(stopWatch, methodName, "验签失败", resp);
				return resp;
			}

			// 参数转换
			String biz_data = req.getBiz_data();
			FqgjSupplementOrderInfo fqgjSupplementOrderInfo = JSONObject.parseObject(biz_data,
					FqgjSupplementOrderInfo.class);
			if (fqgjSupplementOrderInfo == null) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "OrderDetail is null", resp);
				return resp;
			}

			// 获取订单信息
			String order_no = fqgjSupplementOrderInfo.getOrder_no();
			if (order_no == null) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "orderInfo is null", resp);
				return resp;
			}

			ThreadLocalUtil.set("BASEINFO-" + order_no);
			logger.info("参数校验通过");
			logger.info("分期管家推单的订单号为：" + order_no);
			resp = fqgjService.saveAddInfo(fqgjSupplementOrderInfo);
		} catch (Exception e) {
			logger.error(methodName + " 异常", e);
			resp.setCode("102");
			resp.setMsg("系统异常，请稍后再试");
		}

		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 绑卡接口
	 *
	 * @param req
	 * @return
	 * @author GuoKun
	 */
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping("/drainage/fqgj/callback/bindCard.do")
	public DrainageResp bindCard(@RequestBody FqgjReq req) {
		logger.info("~~~~~~~~~~~~~~~~~~ 分期管家绑卡接口");
		ThreadLocalUtil.set("BINDCARD");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String methodName = "DrainageCallBackController.bindCard";
//		FqgjReq req = new FqgjReq();
//		req.setApp_id(request.getParameter("app_id"));
//		req.setBiz_data(request.getParameter("biz_data"));
//		req.setFormat(request.getParameter("format"));
//		req.setSign(request.getParameter("sign"));
//		req.setSign_type(request.getParameter("sign_type"));
//		req.setTimestamp(request.getParameter("timestamp"));
//		req.setVersion(request.getParameter("version"));
		DrainageResp resp = new DrainageResp();
		try {
			// 请求参数非空验证
			String check = FqgjUtil.isolduser(req);
			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			// 验签
			String s = JSONObject.toJSONString(req);
			boolean checkSign = FqgjUtil.veriry(JSONObject.parseObject(s));
			logger.info("分期管家验签结果:" + checkSign);
			if (!checkSign) {
				resp.setCode("101");
				resp.setMsg("验签失败");
				methodEnd(stopWatch, methodName, "验签失败", resp);
				return resp;
			}

			// 参数转换
			String bizData = req.getBiz_data();
			FqgjBindCardData bindCardData = JSONObject.parseObject(bizData, FqgjBindCardData.class);
			if (bindCardData == null) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "RongBindCardBizData is null", resp);
				return resp;
			}
			ThreadLocalUtil.set("BINDCARD-" + bindCardData.getOrder_no());
			logger.info("参数校验通过");

			// 转换银行卡
			String bankCode = FqgjUtil.convertToBankCode(bindCardData.getOpen_bank());
			if (StringUtils.isBlank(bankCode) || "0000".equals(bankCode)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "该银行卡所在地区暂未开通服务", resp);
				return resp;
			}

			// 根据bankCode从REDIS中获取对应的bankName
			String bankName = RedisUtils.hget("fuiou:bank", bankCode);
			String phone = bindCardData.getUser_mobile();
			String idNumber = bindCardData.getId_number();
			String name = bindCardData.getUser_name();

			// 获取借款人信息
			BwBorrower borrower = new BwBorrower();
			borrower.setIdCard(idNumber);
			borrower.setFlag(1);// 未删除的
			borrower = findBwBorrowerByAttrProxy(borrower);
			logger.info("借款人查询结果：" + JSONObject.toJSONString(borrower));

			// 保存银行卡信息
			BwBankCard bbc = saveOrUpdateBBC(borrower, bindCardData, bankCode, bankName);
			Map<String, String> map = new HashMap<String, String>();
			String userId = String.valueOf(borrower.getId());// 借款人id
			String idNo = borrower.getIdCard();// 证件号码 18位
			String accName = borrower.getName();// 姓名
			String cardNo = bbc.getCardNo();// 银行卡号
			String orderNo = bindCardData.getOrder_no();

			// 存放回调地址
			RedisUtils.hset("fqgj:order", userId, bindCardData.getReturn_url());
			try {
				String urlReturn = SystemConstant.NOTIRY_URL + "/drainage/fqgj/callback/signCardCallBack.do?thirdOrderNo="
						+ orderNo;
				ThreadLocalUtil.set("SIGNCARD-" + orderNo);
				RedisUtils.hset("fqgj:bindCard", userId, orderNo);
				logger.info(methodName + " start,userId=" + userId + ",idNo=" + idNo + ",accName=" + accName
						+ ",cardNo=" + cardNo + ",urlReturn=" + urlReturn + ",orderNo=" + orderNo);
				SignLess signLess = new SignLess();
				signLess.setUser_id(userId);
				signLess.setId_no(idNo);
				signLess.setAcct_name(accName);
				signLess.setCard_no(cardNo);
				signLess.setUrl_return(urlReturn);
				logger.info("开始调用连连签约接口,signLess=" + com.alibaba.fastjson.JSONObject.toJSONString(signLess));
//				 LianLianPayService.signAccreditPay(signLess, response);
				String re_url = signAccreditPay(signLess);
				logger.info("开始调用连连签约接口,re_url=" + re_url);
				resp.setCode("200");
				resp.setMsg("OK");
				Map a = new HashMap();
				a.put("bind_card_url", re_url);
				resp.setData(a);
				logger.info("结束调用连连签约接口");
			} catch (Exception e) {
				logger.error(methodName + " occured exception", e);
			}
		} catch (Exception e) {
			logger.error(methodName + " 异常", e);
			resp.setCode("102");
			resp.setMsg("系统异常，请稍后再试");
		}
		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 获取连连签约
	 * 
	 * @param signLess
	 * @return
	 */
	@SuppressWarnings("unused")
	public String signAccreditPay(SignLess signLess) {
		SignResponse signResponse = new SignResponse();
		SignRequest signRequest = new SignRequest();
		try {
			signRequest.setAcct_name(signLess.getAcct_name());
			signRequest.setApp_request(signLess.getApp_request());
			signRequest.setCard_no(signLess.getCard_no());
			signRequest.setId_no(signLess.getId_no());
			signRequest.setUser_id(signLess.getUser_id());
			signRequest.setUrl_return(signLess.getUrl_return());

			// 一起的三个参数，全部不要只是签约 不绑定还款计划
			/*
			 * signRequest.setRepayment_no(request.getParameter("repayment_no"))
			 * ; signRequest.setRepayment_plan(request.getParameter(
			 * "repayment_plan"));
			 * signRequest.setSms_param(request.getParameter("sms_param"));
			 */

			// 初始化为空的参数
			if (CommUtils.isNull(signRequest.getVersion()) | "null".equals(signRequest.getVersion())) {
				signRequest.setVersion("1.1");
			}
			if (CommUtils.isNull(signRequest.getId_type()) | "null".equals(signRequest.getId_type())) {
				signRequest.setId_type("0");
			}
			if (CommUtils.isNull(signRequest.getApp_request()) | "null".equals(signRequest.getApp_request())) {
				signRequest.setApp_request("3");
			}
			if (CommUtils.isNull(signRequest.getOid_partner()) | "null".equals(signRequest.getOid_partner())) {
				signRequest.setOid_partner("201608101001022519");
			}
			if (CommUtils.isNull(signRequest.getPay_type()) | "null".equals(signRequest.getPay_type())) {
				signRequest.setPay_type("I");
			}
			if (CommUtils.isNull(signRequest.getSign_type()) | "null".equals(signRequest.getSign_type())) {
				signRequest.setSign_type("RSA");
			}

			// 签名
			Map<String, String> map = new HashMap<>();
			map.put("acct_name", signRequest.getAcct_name());
			map.put("app_request", signRequest.getApp_request());
			map.put("card_no", signRequest.getCard_no());
			map.put("id_no", signRequest.getId_no());
			map.put("id_type", signRequest.getId_type());
			map.put("oid_partner", signRequest.getOid_partner());
			map.put("pay_type", signRequest.getPay_type());
			map.put("risk_item", signRequest.getRisk_item());
			map.put("sign_type", signRequest.getSign_type());
			map.put("url_return", signRequest.getUrl_return());
			map.put("user_id", signRequest.getUser_id());
			map.put("version", signRequest.getVersion());

			// map.put("repayment_no", "110");
			// map.put("repayment_plan","{\"repaymentPlan\":[{\"date\":\"2017-09-06\",\"amount\":\"0.01\"}]}");
			// map.put("sms_param", "");

			final String TRADER_PRI_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKZGXpmfgya2gGh6UdFPqPqi6e2z/HX4aIlMH394FOXTVwErnSGY5S0YFw5WskJrQLU2RHwFiA5P9Yt8VPxwgLDpdIm1/a7NlyjvWNvBd8V7wyITH8teJA1Ae5yWmRRrWFcVRSjpBq3xfwv76lVl+Nq/jR08p/ugVYJgtYEIM53JAgMBAAECgYA17SarPj+j45a7y8gTUXmlaAbkb/ZWMG1+8fBZQBHPA/74wzNf/R1+xYxcuyNvRSekXehSLN0WfzpMtdM+WCJ0ODqHRFsbAxmi784hzBZHOAxoJV49P8PVy6HIPthXxiSNUcacSt/HKJrUI6zACpymJLiVxMb9GqQAyx3BJl7rjQJBANG+RDszZYl3J1z1AtD0WggycrH2YOB7v5o3qKOz2AQ6CHWApSN6cuvqFwaUtHK9gMpDhvWR6zbYVRP+f4AxoQ8CQQDK8fTkpHNrHc011E8jjk3Uq5PWTJ0jAvcqk4rqZa4eV9953YSJYtJ2Fk2JnL3Ba7AU+qStnyD6MvSIpwIPSaOnAkEAptbFaZ4Jn55jdmMC2Xn1f925NGx6RTbKg37Qq18sbrhG8Ejjk2QctCIiLL7vBvJM1xd97CslQhw1GNFxVGSl6wJAQzwFtfoFgudMpRjBXzY18s8lG0omhQLmf+SBkUY+eS8Diowo7Jsgvp6E8aJL+1iB7XFcPWkKs9lNyjgKJqZu4QJAM22ULfWKrNIqaBJaYDmQSupUkHR/WL5rQJtAuVo8Zg3+rBrtMTXfIHJpR0MNpMgRSsPK6pZ3n4i+VvC5WxKUzA==";

			String osign = RSAUtil.sortParams(map);
			logger.info("排序参数：" + osign);
			String sign = RSAUtil.sign(TRADER_PRI_KEY, osign);
			logger.info("RSA加密后的密文：" + sign);

			// signRequest.setSign(sign);
			map.put("sign", sign);

			// String value = new Gson().toJson(signRequest);
			String value = new Gson().toJson(map);
			// 构建跳转表单
			/*
			 * String html =
			 * "<html><head><meta charset='utf-8'></head><div style='display:none;'><form id='signForm' name='signForm' type='post' "
			 * +
			 * "action='https://yintong.com.cn/llpayh5/signApply.htm'><input name='req_data' value='"
			 * +value+"'></form>" +
			 * "</div><script type='text/javascript'>document.forms['signForm'].submit();</script></html>"
			 * ;
			 */

			// String html =
			// "https://wap.lianlianpay.com/signApply.htm?req_data=" + value;
			String html = "<html><head><meta charset='utf-8'></head><div style='display:none;'><form id='signForm' name='signForm' type='post' "
					+ "action='https://wap.lianlianpay.com/signApply.htm'><input name='req_data' value='" + value
					+ "'></form>"
					+ "</div><script type='text/javascript'>document.forms['signForm'].submit();</script></html>";

			return html;
		} catch (Exception e) {
			logger.error(e, e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 连连签约回调
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/drainage/fqgj/callback/signCardCallBack.do")
	public String signRongCardCallBack(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		String methodName = "FqgjSignController.signRongCardCallBack";
		String reason = null;
		String orderNo = null;
		try {
			String status = request.getParameter("status");
			String result = request.getParameter("result");
			orderNo = request.getParameter("thirdOrderNo");
			logger.info("result为：" + result);
			JSONObject resultJson = JSONObject.parseObject(result);
			String userId = resultJson.getString("user_id");
			logger.info("用户的userId为：" + userId);
			String thirdUrl = RedisUtils.hget("fqgj:order", userId);

			ThreadLocalUtil.set("SIGNBACK-" + orderNo);

			logger.info(methodName + " start,status=" + status + ",result=" + result + ",thirdUrl=" + thirdUrl);

			if (com.beadwallet.utils.CommUtils.isNull(status)) {
				bindCardFeedBack(orderNo, BIND_CARD_STATUS_FAILED, result);
				map.put("result", "系统异常");
				logger.info(methodName + " end,status is null,return index page");
				ThreadLocalUtil.remove();
				return "sign_fail_fqgj";
			}

			// 签约成功
			if (status.equals("0000")) {
				logger.info("signCardCallBack success,status = 0000,start update signStatus=2");
				try {
					net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(result);
					if (StringUtils.isBlank(orderNo)) {
						logger.info("get orderNo from redis[fqgj:bindCard]");
						orderNo = RedisUtils.hget("fqgj:bindCard", userId);
						logger.info("get orderNo from redis[fqgj:bindCard] is " + orderNo);
					}
					RedisUtils.hdel("fqgj:bindCard", userId);

					// 更新为已签约
					iBwBankCardService.updateSignStatusByBorrowerId(Long.valueOf(userId));

					bindCardFeedBack(orderNo, BIND_CARD_STATUS_SUCCESS, null);
					// 修改工单为待审核状态
					updStatus2PreApprove(orderNo);
					logger.info(methodName + " end,signCardCallBack success,status = 0000");
					ThreadLocalUtil.remove();
					return "redirect:" + thirdUrl;
				} catch (Exception e) {
					reason = "系统异常";
					bindCardFeedBack(orderNo, BIND_CARD_STATUS_FAILED, reason);
					map.put("result", "系统异常");
					logger.error(methodName + " end,signCardCallBack occured exception", e);
					ThreadLocalUtil.remove();
					return "sign_fail_fqgj";
				}
			} else {
				// 签约失败
				map.put("result", result);
				bindCardFeedBack(orderNo, BIND_CARD_STATUS_FAILED, result);
				logger.info(methodName + " end,signCardCallBack failed,status != 0000");
				ThreadLocalUtil.remove();
				return "sign_fail_fqgj";
			}
		} catch (Exception e) {
			logger.error(methodName + " occured exception", e);
			map.put("result", "系统异常");
			ThreadLocalUtil.remove();
			return "sign_fail_fqgj";
		}
	}

	/**
	 * 拉取审批结论
	 *
	 * @param req
	 * @return
	 * @author GuoKun
	 */
	@ResponseBody
	@RequestMapping("/drainage/fqgj/callback/gainApprove.do")
	public DrainageResp gainApprove(@RequestBody FqgjReq req) {
		logger.info("~~~~~~~~~~~~~~~~~~ 分期管家拉取审批结论接口");
		ThreadLocalUtil.set("GAINAPPROVE");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String methodName = "DrainageCallBackController.gainApprove";
//		FqgjReq req = new FqgjReq();
//		req.setApp_id(request.getParameter("app_id"));
//		req.setBiz_data(request.getParameter("biz_data"));
//		req.setFormat(request.getParameter("format"));
//		req.setSign(request.getParameter("sign"));
//		req.setSign_type(request.getParameter("sign_type"));
//		req.setTimestamp(request.getParameter("timestamp"));
//		req.setVersion(request.getParameter("version"));
		DrainageResp resp = new DrainageResp();
		try {
			// 请求参数非空验证
			String check = FqgjUtil.isolduser(req);
			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			// 验签
			String s = JSONObject.toJSONString(req);
			boolean checkSign = FqgjUtil.veriry(JSONObject.parseObject(s));
			logger.info("分期管家验签结果:" + checkSign);
			if (!checkSign) {
				resp.setCode("101");
				resp.setMsg("验签失败");
				methodEnd(stopWatch, methodName, "验签失败", resp);
				return resp;
			}

			// 判断thirdOrderNo不为空
			String bizData = req.getBiz_data();
			JSONObject jsonObject = JSONObject.parseObject(bizData);
			String thirdOrderNo = jsonObject.getString("order_no");
			if (CommUtils.isNull(thirdOrderNo)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "GainApprove data is null", resp);
				return resp;
			}

			ThreadLocalUtil.set("APPROVECONFIRM-" + thirdOrderNo);
			logger.info("参数校验通过");
			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setThirdOrderNo(thirdOrderNo);
			bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);

			if (CommUtils.isNull(bwOrderRong)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "分期管家工单不存在", resp);
				return resp;
			}

			Long orderId = bwOrderRong.getOrderId();
			BwOrder bwOrder = findBwOrderByIdProxy(orderId);

			if (CommUtils.isNull(bwOrder)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "工单为空", resp);
				return resp;
			}

			String approveStatus = FqgjUtil.convertApproveStatus(bwOrder.getStatusId());
			logger.info("映射为分期管家审批状态为：" + approveStatus);

			if (StringUtils.isBlank(approveStatus)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "当前状态[" + bwOrder.getStatusId() + "]无法映射为分期管家审批状态", resp);
				return resp;
			}

			Map<String, String> map = new HashMap<String, String>();

			if (APPROVE_STATUS_PASS.equals(approveStatus)) {
				map.put("order_no", thirdOrderNo);
				map.put("conclusion", approveStatus); // 审批结论
				map.put("term_unit", "1"); // 期限类型
				map.put("approval_term", "30"); // 审批天（月）数-固定
				map.put("service_fee", String.valueOf(bwOrder.getCreditLimit() * Float.parseFloat("0.18"))); // 手续费
				map.put("remark", "审核通过");
				map.put("amount_type", "0"); // 金额固定
				map.put("approval_amount", String.valueOf(bwOrder.getCreditLimit())); // 审批金额
				double receive_amount = bwOrder.getCreditLimit() * 0.82; // 到帐金额
				map.put("receive_amount", receive_amount + "");
				map.put("pay_amount", bwOrder.getCreditLimit() + ""); // 总还款
				map.put("period_amount", String.valueOf(bwOrder.getCreditLimit())); // 每期应还金额
				map.put("approval_time", Calendar.getInstance().getTime().getTime() + ""); // 审批通过时间
				map.put("term_type", "0");
			} else if (APPROVE_STATUS_REFUSED.equals(approveStatus)) {
				map.put("order_no", thirdOrderNo);
				map.put("conclusion", approveStatus);
				map.put("remark", "信用评分过低#拒绝客户");
				map.put("refuse_time",
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
			}
			resp.setCode("200");
			resp.setMsg("成功");
			resp.setData(map);
		} catch (Exception e) {
			logger.error("拉取审批结论异常", e);
			resp.setCode("103");
			resp.setMsg("系统异常，请稍后再试");
		}
		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 审批确认
	 *
	 * @param req
	 * @return
	 * @author GuoKun
	 */
	@ResponseBody
	@RequestMapping("/drainage/fqgj/callback/approveConfirm.do")
	public DrainageResp approveConfirm(@RequestBody FqgjReq req) {
		logger.info("~~~~~~~~~~~~~~~~~~ 分期管家审批确认接口");
		ThreadLocalUtil.set("APPROVECONFIRM");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String methodName = "DrainageCallBackController.approveConfirm";
//		FqgjReq req = new FqgjReq();
//		req.setApp_id(request.getParameter("app_id"));
//		req.setBiz_data(request.getParameter("biz_data"));
//		req.setFormat(request.getParameter("format"));
//		req.setSign(request.getParameter("sign"));
//		req.setSign_type(request.getParameter("sign_type"));
//		req.setTimestamp(request.getParameter("timestamp"));
//		req.setVersion(request.getParameter("version"));
		logger.info(methodName + "开始获取补充订单信息");
		DrainageResp resp = new DrainageResp();

		try {
			// 请求参数非空验证
			String check = FqgjUtil.isolduser(req);
			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			// 验签
			String s = JSONObject.toJSONString(req);
			boolean checkSign = FqgjUtil.veriry(JSONObject.parseObject(s));
			logger.info("分期管家验签结果:" + checkSign);
			if (!checkSign) {
				resp.setCode("101");
				resp.setMsg("验签失败");
				methodEnd(stopWatch, methodName, "验签失败", resp);
				return resp;
			}

			// 参数转换
			String bizData = req.getBiz_data();
			FqgjApproveConfirmData approveConfirm = JSONObject.parseObject(bizData, FqgjApproveConfirmData.class);
			if (CommUtils.isNull(approveConfirm)) {
				resp.setCode("102");
				resp.setMsg("请求参数为空");
				methodEnd(stopWatch, methodName, "ApproveConfirmData is null", resp);
				return resp;
			}

			// 获取第三方订单号
			String thirdOrderNo = approveConfirm.getOrder_no();
			ThreadLocalUtil.set("APPROVECONFIRM-" + thirdOrderNo);
			logger.info("参数校验通过");
			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setThirdOrderNo(thirdOrderNo);
			bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);
			if (CommUtils.isNull(bwOrderRong)) {
				resp.setCode("102");
				resp.setMsg("分期管家对应该工单不存在");
				methodEnd(stopWatch, methodName, "分期管家对应该工单不存在", resp);
				return resp;
			}

			Long orderId = bwOrderRong.getOrderId();
			String orderTerm = approveConfirm.getLoan_term();

			// 获取利率字典表信息
			BwProductDictionary bwProductDictionary = bwProductDictionaryService.findById(2l);// 固定查询产品id为2的合同利率
			Double contractMonthRate = 0.0;
			Double repayAmount = 0.0;
			Double contractAmount = 0.0;

			// 等额本息
			// 计算合同月利率
			contractMonthRate = bwProductDictionary.getpBorrowRateMonth();

			// 计算合同金额
			BwOrder order = findBwOrderByIdProxy(orderId);
			if (CommUtils.isNull(order)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "工单不存在", resp);
				return resp;
			}
			if (CommUtils.isNull(order.getBorrowAmount())) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "借款金额为空", resp);
				return resp;
			}

			// 计算还款金额
			repayAmount = DoubleUtil.round(((order.getBorrowAmount() / (Integer.parseInt(orderTerm) / 30))
					+ (order.getBorrowAmount() * bwProductDictionary.getpInvestRateMonth())), 2);
			// 计算合同金额
			contractAmount = DoubleUtil.round((repayAmount
					* (Math.pow(1 + contractMonthRate, (Integer.parseInt(orderTerm) / 30)) - 1))
					/ (contractMonthRate * (Math.pow(1 + contractMonthRate, (Integer.parseInt(orderTerm) / 30)))), 2);
			BwOrder bo = new BwOrder();
			bo.setId(orderId);
			bo = bwOrderService.findBwOrderByAttr(bo);

			bo.setRepayTerm(Integer.parseInt(orderTerm));
			bo.setRepayType(Integer.parseInt("1"));// 待确认
			bo.setBorrowRate(bwProductDictionary.getpInvestRateMonth());
			bo.setContractRate(bwProductDictionary.getpInvesstRateYear());
			bo.setContractMonthRate(contractMonthRate);
			// 将产品类型更新成2
			bo.setStatusId(11L);
			bo.setContractAmount(contractAmount);
			// 工单修改时间
			bo.setUpdateTime(new Date());

			if (order.getStatusId() == 4) {// 签约
				int num = bwOrderService.updateBwOrder(bo);
				logger.info("修改工单条数:" + num);
				if (num == 0) {
					resp.setCode("1004");
					resp.setMsg("修改工单失败");
					methodEnd(stopWatch, methodName, "修改工单失败", resp);
					return resp;
				}

				// 生成合同
				try {
					RedisUtils.rpush("system:contract", String.valueOf(orderId));

					BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
					bwOrderProcessRecord.setOrderId(order.getId());
					bwOrderProcessRecord.setSignTime(new Date());
					bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
				} catch (Exception e) {
					logger.error("occured exception:", e);
				}
			}

			resp.setCode("200");
			resp.setMsg("成功");
		} catch (Exception e) {
			logger.error(methodName + " 异常", e);
			resp.setCode("103");
			resp.setMsg("系统异常，请稍后再试");
		}

		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 获取合同地址
	 *
	 * @param req
	 * @return
	 * @author GuoKun
	 */
	@ResponseBody
	@RequestMapping("/drainage/fqgj/callback/approveContract.do")
	public DrainageResp approveContract(@RequestBody FqgjReq req) {
		logger.info("~~~~~~~~~~~~~~~~~~ 分期管家合同获取接口");
		ThreadLocalUtil.set("CONTRACT");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		DrainageResp resp = new DrainageResp();
		String methodName = "DrainageCallBackController.approveContract";
//		FqgjReq req = new FqgjReq();
//		req.setApp_id(request.getParameter("app_id"));
//		req.setBiz_data(request.getParameter("biz_data"));
//		req.setFormat(request.getParameter("format"));
//		req.setSign(request.getParameter("sign"));
//		req.setSign_type(request.getParameter("sign_type"));
//		req.setTimestamp(request.getParameter("timestamp"));
//		req.setVersion(request.getParameter("version"));
		try {
			// 请求参数非空验证
			String check = FqgjUtil.isolduser(req);
			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			// 验签
			String s = JSONObject.toJSONString(req);
			boolean checkSign = FqgjUtil.veriry(JSONObject.parseObject(s));
			logger.info("分期管家验签结果:" + checkSign);
			if (!checkSign) {
				resp.setCode("101");
				resp.setMsg("验签失败");
				methodEnd(stopWatch, methodName, "验签失败", resp);
				return resp;
			}

			// 参数转换
			String bizData = req.getBiz_data();
			FqgjApproveContractData approveContractData = JSONObject.parseObject(bizData,
					FqgjApproveContractData.class);
			if (CommUtils.isNull(approveContractData)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "ApproveConfirmData is null", resp);
				return resp;
			}

			// 获取第三方order_no
			String orderNo = approveContractData.getOrder_no();
			if (StringUtils.isBlank(orderNo)) {
				resp.setCode("102");
				resp.setMsg("order_no参数为空");
				methodEnd(stopWatch, methodName, "order_no参数为空", resp);
				return resp;
			}
			ThreadLocalUtil.set("CONTRACT-" + orderNo);
			logger.info("参数校验通过");

			// 根据订单号查询到工单号
			BwOrderRong bwOrderRong = getByOrderNoProxy(orderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				resp.setCode("102");
				resp.setMsg("分期管家工单查询订单为空");
				methodEnd(stopWatch, methodName, "分期管家工单查询订单为空", resp);
				return resp;
			}

			// 查找合同地址
			Long orderId = bwOrderRong.getOrderId();
			logger.info("工单号" + orderId);
			BwAdjunct bwAdjunct = new BwAdjunct();
			bwAdjunct.setOrderId(orderId);
			bwAdjunct.setAdjunctType(13);
			bwAdjunct = findBwAdjunctByAttrProxy(bwAdjunct);
			if (CommUtils.isNull(bwAdjunct)) {
				resp.setCode("205");
				resp.setMsg("未产生合同");
				methodEnd(stopWatch, methodName, "bwAdjunct为空", resp);
				return resp;
			}

			// 拼接地址
			String conUrl = SystemConstant.PDF_URL + bwAdjunct.getAdjunctPath();
			logger.info("查询到的合同地址是：" + conUrl);
			Map<String, String> map = new HashMap<String, String>();
			map.put("contract_url", conUrl);
			resp.setCode("200");
			resp.setMsg("成功");
			resp.setData(map);
		} catch (Exception e) {
			logger.error("查询合同异常", e);
			resp.setCode("103");
			resp.setMsg("系统异常，请稍后再试");
		}
		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 拉取订单状态
	 *
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/drainage/fqgj/callback/pullOrderStatus.do")
	public DrainageResp pullOrderStatus(@RequestBody FqgjReq req) {
		logger.info("~~~~~~~~~~~~~~~~~~ 分期管家拉取订单状态接口");
		ThreadLocalUtil.set("PULLORDERSTATUS");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		DrainageResp resp = new DrainageResp();
		String methodName = "DrainageCallBackController.pullOrderStatus";
//		FqgjReq req = new FqgjReq();
//		req.setApp_id(request.getParameter("app_id"));
//		req.setBiz_data(request.getParameter("biz_data"));
//		req.setFormat(request.getParameter("format"));
//		req.setSign(request.getParameter("sign"));
//		req.setSign_type(request.getParameter("sign_type"));
//		req.setTimestamp(request.getParameter("timestamp"));
//		req.setVersion(request.getParameter("version"));
		try {
			// 请求参数非空验证
			String check = FqgjUtil.isolduser(req);
			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			// 验签
			String s = JSONObject.toJSONString(req);
			boolean checkSign = FqgjUtil.veriry(JSONObject.parseObject(s));
			logger.info("分期管家验签结果:" + checkSign);
			if (!checkSign) {
				resp.setCode("101");
				resp.setMsg("验签失败");
				methodEnd(stopWatch, methodName, "验签失败", resp);
				return resp;
			}

			// 参数转换
			String bizData = req.getBiz_data();
			JSONObject jsonObject = JSONObject.parseObject(bizData);
			String thirdOrderNo = jsonObject.getString("order_no");
			if (CommUtils.isNull(thirdOrderNo)) {
				resp.setCode("102");
				resp.setMsg("订单号为空");
				methodEnd(stopWatch, methodName, "orderStatusData为空", resp);
				return resp;
			}

			ThreadLocalUtil.set("PULLORDERSTATUS-" + thirdOrderNo);
			logger.info("参数校验通过");

			// 根据分期管家订单号查询到水象工单号
			BwOrderRong bwOrderRong = getByOrderNoProxy(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				resp.setCode("102");
				resp.setMsg("订单不存在");
				methodEnd(stopWatch, methodName, "orderRong表中工单为空", resp);
				return resp;
			}

			// 根据水象工单号查询订单
			Long orderId = bwOrderRong.getOrderId();
			BwOrder bwOrder = findBwOrderByIdProxy(orderId);
			if (CommUtils.isNull(bwOrder)) {
				resp.setCode("102");
				resp.setMsg("订单不存在");
				methodEnd(stopWatch, methodName, "工单为空", resp);
				return resp;
			}

			// 映射状态
			String orderStatus = FqgjUtil.convertOrderStatus(bwOrder.getStatusId());
			logger.info("映射为分期管家订单状态为：" + orderStatus);
			if (StringUtils.isBlank(orderStatus)) {
				resp.setCode("102");
				resp.setMsg("工单正在审核中..");
				methodEnd(stopWatch, methodName, "当前状态[" + bwOrder.getStatusId() + "]无法映射为分期管家订单状态", resp);
				return resp;
			}

			Map<String, String> map = new HashMap<String, String>();
			map.put("order_no", thirdOrderNo);
			map.put("order_status", orderStatus);
			map.put("update_time", String.valueOf(System.currentTimeMillis() / 1000));
			resp.setCode("200");
			resp.setMsg("成功");
			resp.setData(map);
		} catch (Exception e) {
			logger.error("拉取订单状态异常", e);
			resp.setCode("103");
			resp.setMsg("系统异常，请稍后再试");
		}

		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 拉取还款计划
	 *
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/drainage/fqgj/callback/pullRepaymentPlan.do")
	public DrainageResp pullRepaymentPlan(@RequestBody FqgjReq req) {
		logger.info("~~~~~~~~~~~~~~~~~~ 分期管家拉取还款计划接口");
		ThreadLocalUtil.set("PULLREPAYMENTPLAN");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		DrainageResp resp = new DrainageResp();
		String methodName = "FqgjCallBackController.pullRepaymentPlan";
//		FqgjReq req = new FqgjReq();
//		req.setApp_id(request.getParameter("app_id"));
//		req.setBiz_data(request.getParameter("biz_data"));
//		req.setFormat(request.getParameter("format"));
//		req.setSign(request.getParameter("sign"));
//		req.setSign_type(request.getParameter("sign_type"));
//		req.setTimestamp(request.getParameter("timestamp"));
//		req.setVersion(request.getParameter("version"));
		try {
			// 请求参数非空验证
			String check = FqgjUtil.isolduser(req);
			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			// 验签
			String s = JSONObject.toJSONString(req);
			boolean checkSign = FqgjUtil.veriry(JSONObject.parseObject(s));
			logger.info("分期管家验签结果:" + checkSign);
			if (!checkSign) {
				resp.setCode("101");
				resp.setMsg("验签失败");
				methodEnd(stopWatch, methodName, "验签失败", resp);
				return resp;
			}

			// 参数转换
			String bizData = req.getBiz_data();
			JSONObject jsonObject = JSONObject.parseObject(bizData);
			String thirdOrderNo = jsonObject.getString("order_no");
			if (CommUtils.isNull(thirdOrderNo)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "orderStatusData为空", resp);
				return resp;
			}
			ThreadLocalUtil.set("PULLREPAYMENTPLAN-" + thirdOrderNo);
			logger.info("参数校验通过");

			// 根据分期管家订单号查询到水象工单号
			BwOrderRong bwOrderRong = getByOrderNoProxy(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "OrderRong工单为空", resp);
				return resp;
			}
			Long orderId = bwOrderRong.getOrderId();
			logger.info("工单号" + orderId);
			BwOrder bwOrder = findBwOrderByIdProxy(orderId);
			if (CommUtils.isNull(bwOrder)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "工单为空", resp);
				return resp;
			}

			// 查询银行卡信息
			BwBankCard bankCard = findBwBankCardByBorrowerIdProxy(bwOrder.getBorrowerId());
			if (CommUtils.isNull(bankCard)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "银行卡信息为空", resp);
				return resp;
			}

			// 查询还款计划
			BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
			bwRepaymentPlan.setOrderId(bwOrder.getId());
			bwRepaymentPlan = findBwRepaymentPlanByAttrProxy(bwRepaymentPlan);
			if (CommUtils.isNull(bwRepaymentPlan)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "还款计划为空", resp);
				return resp;
			}

			// 银行映射
			String billStatus = FqgjUtil.convertBillStatus(bwOrder.getStatusId());
			logger.info("映射分期管家的账单状态为:" + billStatus);
			if (StringUtils.isBlank(billStatus)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "当前状态[" + bwOrder.getStatusId() + "]无法映射为分期管家的账单状态", resp);
				return resp;
			}

			// 查询详情
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, String> planMap = new HashMap<String, String>();
			BigDecimal allAmount = new BigDecimal(bwRepaymentPlan.getRealityRepayMoney());

			if ("3".equals(billStatus)) {
				BwOverdueRecord bov = new BwOverdueRecord();
				bov.setRepayId(bwRepaymentPlan.getId());
				bov = findBwOverdueRecordByAttrProxy(bov);
				if (!CommUtils.isNull(bov) && bov.getOverdueAccrualMoney() != null) {
					allAmount = allAmount.add(new BigDecimal(bov.getOverdueAccrualMoney()));
					planMap.put("overdue_fee", String.valueOf(bov.getOverdueAccrualMoney()));
				}
			}
			planMap.put("amount", allAmount.toString());
			String timestamp = String.valueOf(bwRepaymentPlan.getRepayTime().getTime() / 1000);
			planMap.put("due_time", String.valueOf(Integer.valueOf(timestamp)));
			planMap.put("pay_type", "4");
			planMap.put("period_no", "1");
			planMap.put("remark", "本金:" + bwOrder.getBorrowAmount());
			planMap.put("bill_status", billStatus);
			planMap.put("can_repay_time", bwRepaymentPlan.getRepayTime().getTime() + "");
			List<Map<String, String>> planMaps = new ArrayList<Map<String, String>>();
			planMaps.add(planMap);
			map.put("order_no", thirdOrderNo);
			map.put("open_bank", bankCard.getBankName());
			map.put("bank_card", bankCard.getCardNo());
			map.put("repayment_plan", planMaps);
			resp.setCode("200");
			resp.setMsg("成功");
			resp.setData(map);
		} catch (Exception e) {
			logger.error("拉取账单异常", e);
			resp.setCode("103");
			resp.setMsg("系统异常，请稍后再试");
		}
		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 主动还款接口
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/drainage/fqgj/callback/repayment.do")
	public DrainageResp repayment(@RequestBody FqgjReq req) {
		ThreadLocalUtil.set("REPAYMENT");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		DrainageResp resp = new DrainageResp();
		String methodName = "FqgjCallBackController.repayment";
//		FqgjReq req = new FqgjReq();
//		req.setApp_id(request.getParameter("app_id"));
//		req.setBiz_data(request.getParameter("biz_data"));
//		req.setFormat(request.getParameter("format"));
//		req.setSign(request.getParameter("sign"));
//		req.setSign_type(request.getParameter("sign_type"));
//		req.setTimestamp(request.getParameter("timestamp"));
//		req.setVersion(request.getParameter("version"));

		try {
			// 请求参数非空验证
			String check = FqgjUtil.isolduser(req);
			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			// 验签
			String s = JSONObject.toJSONString(req);
			boolean checkSign = FqgjUtil.veriry(JSONObject.parseObject(s));
			logger.info("分期管家验签结果:" + checkSign);
			if (!checkSign) {
				resp.setCode("101");
				resp.setMsg("验签失败");
				methodEnd(stopWatch, methodName, "验签失败", resp);
				return resp;
			}

			// 参数转换
			String bizData = req.getBiz_data();
			JSONObject jsonObject = JSONObject.parseObject(bizData);
			String thirdOrderNo = jsonObject.getString("order_no");
			if (CommUtils.isNull(thirdOrderNo)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "orderStatusData为空", resp);
				return resp;
			}
			ThreadLocalUtil.set("PULLREPAYMENTPLAN-" + thirdOrderNo);
			logger.info("参数校验通过");
			// 根据水象分期管家订单号查询到水象工单号
			BwOrderRong bwOrderRong = getByOrderNoProxy(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "OrderRong工单为空", resp);
				return resp;
			}
			Long orderId = bwOrderRong.getOrderId();
			logger.info("工单号" + orderId);
			BwOrder bwOrder = findBwOrderByIdProxy(orderId);
			if (CommUtils.isNull(bwOrder)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "工单为空", resp);
				return resp;
			}

			// 查询银行卡信息
			BwBankCard bankCard = findBwBankCardByBorrowerIdProxy(bwOrder.getBorrowerId());
			if (CommUtils.isNull(bankCard)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "银行卡信息为空", resp);
				return resp;
			}

			resp = commonRepay(bwOrder, bankCard, "repay");
		} catch (Exception e) {
			logger.error(methodName + " 异常", e);
			resp.setCode("103");
			resp.setMsg("系统异常，请稍后再试");
		}

		methodEnd(stopWatch, methodName, "", resp);
		return resp;
	}

	private DrainageResp commonRepay(BwOrder bwOrder, BwBankCard bwBankCard, String type) throws Exception {
		return commonRepay(bwOrder, bwBankCard, null, type);
	}

	private DrainageResp commonRepay(BwOrder bwOrder, BwBankCard bwBankCard, String amount, String type)
			throws Exception {
		logger.info("~~~~~~~~~~~~~~~~~~ 分期管家拉取还款计划接口");
		ThreadLocalUtil.set("PULLREPAYMENTPLAN");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String methodName = "FqgjCallBackController.commonRepay";
		DrainageResp resp = new DrainageResp();

		String orderId = String.valueOf(bwOrder.getId());

		if (RedisUtils.hexists(FQGJ_XUDAI, orderId)) {
			resp.setCode("102");
			resp.setMsg("系统异常");
			methodEnd(stopWatch, methodName, "此工单正在续贷中", resp);
			return resp;
		}

		if (RedisUtils.hexists(SystemConstant.NOTIFY_BAOFU, orderId)) {
			resp.setCode("102");
			resp.setMsg("系统异常");
			methodEnd(stopWatch, methodName, "此工单正在宝付支付中", resp);
			return resp;
		}

		if (RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId)) {
			resp.setCode("102");
			resp.setMsg("系统异常");
			methodEnd(stopWatch, methodName, "此工单正在连连支付中", resp);
			return resp;
		}

		Long statusId = bwOrder.getStatusId();// 工单状态
		logger.info("工单状态:" + statusId);
		if (!(statusId.intValue() == 9 || statusId.intValue() == 13)) {
			resp.setCode("102");
			resp.setMsg("工单只有还款中或逾期中才可还款");
			methodEnd(stopWatch, methodName, "工单只有还款中或逾期中才可还款", resp);
			return resp;
		}

		BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
		bwRepaymentPlan.setOrderId(bwOrder.getId());
		bwRepaymentPlan = findBwRepaymentPlanByAttrProxy(bwRepaymentPlan);
		if (CommUtils.isNull(bwRepaymentPlan)) {
			resp.setCode("102");
			resp.setMsg("系统异常");
			methodEnd(stopWatch, methodName, "没有符合条件的还款计划", resp);
			return resp;
		}

		if (statusId.intValue() == 9 && "defer".equals(type)) {
			// 获取下个还款日
			// 下个还款日
			String repayTime = "";
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
			repayTime = formatter.format(bwRepaymentPlan.getRepayTime());
			logger.info("下个还款日:" + repayTime);
			Date repayDate = formatter.parse(repayTime);
			int day = MyDateUtils.getDaySpace(new Date(), repayDate);// 间隔时间
			logger.info("还款中状态下到期间隔天数:" + day);
			if (day > 10) {
				resp.setCode("102");
				resp.setMsg("到期时间十天内方可续贷");
				methodEnd(stopWatch, methodName, "到期时间十天内方可续贷", resp);
				return resp;
			}
		}

		// 判断该用户是否签约
		Long borrowerId = bwOrder.getBorrowerId();
		logger.info("开始调用连连签约查询接口,borrowerId=" + borrowerId + ",cardNo=" + bwBankCard.getCardNo());
		CardQueryResult cardQueryResult = LianLianPayService.cardBindQuery(borrowerId.toString(),
				bwBankCard.getCardNo());

		logger.info("结束调用连连签约查询接口,cardQueryResult=" + JSONObject.toJSONString(cardQueryResult));

		if (CommUtils.isNull(cardQueryResult)) {
			resp.setCode("102");
			resp.setMsg("未签约");
			methodEnd(stopWatch, methodName, "调用连连签约查询接口返回结果为空", resp);
			return resp;
		}

		if (!"0000".equals(cardQueryResult.getRet_code())) {
			resp.setCode("102");
			resp.setMsg("系统异常");
			methodEnd(stopWatch, methodName, "调用连连签约查询接口返回结果失败，ret_code != 0000", resp);
			return resp;
		}

		List<Agreement> agreements = cardQueryResult.getAgreement_list();
		if (CommUtils.isNull(agreements)) {
			resp.setCode("102");
			resp.setMsg("未签约");
			methodEnd(stopWatch, methodName, "调用连连签约查询接口返回结果失败,Agreement_list is null", resp);
			return resp;
		}

		String agreeNo = agreements.get(0).getNo_agree();

		logger.info("调用连连签约查询接口返回结果成功，连连支付协议号:" + agreeNo);

		List<RepaymentPlan> repays = new ArrayList<RepaymentPlan>();
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		// 设置实际支付金额
		if ("repay".equals(type)) {
			amount = String.valueOf(bwRepaymentPlan.getRealityRepayMoney());
			BwOverdueRecord overdueRecord = new BwOverdueRecord();
			overdueRecord.setOrderId(bwOrder.getId());
			overdueRecord = findBwOverdueRecordByAttrProxy(overdueRecord);
			if (!CommUtils.isNull(overdueRecord)) {
				logger.info("有逾期记录,累加逾期金额");
				amount = new BigDecimal(amount).add(new BigDecimal(overdueRecord.getOverdueAccrualMoney()))
						.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			}
			logger.info("amount=" + amount);
		}

		RepaymentPlan repay = new RepaymentPlan();
		repay.setAmount(amount);
		repay.setDate(dateFormat2.format(bwRepaymentPlan.getRepayTime()));
		repays.add(repay);

		BwBorrower bwBorrower = findBwBorrowerByIdProxy(bwOrder.getBorrowerId());
		SignalLess signalLess = new SignalLess();
		signalLess.setAcct_name(bwBorrower.getName());
		// signalLess.setApp_request(app_request);
		signalLess.setCard_no(bwBankCard.getCardNo());
		signalLess.setId_no(bwBorrower.getIdCard());
		signalLess.setNo_agree(agreeNo);
		signalLess.setUser_id(borrowerId.toString());

		logger.info("开始调用连连授权接口,signalLess=" + JSONObject.toJSONString(signalLess) + ",orderNo=" + bwOrder.getOrderNo()
				+ ",repays=" + JSONObject.toJSONString(repays));
		PlanResult planResult = LianLianPayService.sigalAccreditPay(signalLess, bwOrder.getOrderNo(), repays);
		logger.info("结束调用连连授权接口，planResult=" + JSONObject.toJSONString(planResult));

		if (CommUtils.isNull(planResult)) {
			resp.setCode("102");
			resp.setMsg("支付授权失败");
			methodEnd(stopWatch, methodName, "调用连连授权接口返回结果为空", resp);
			return resp;
		}

		if (!"0000".equals(planResult.getRet_code())) {
			resp.setCode("102");
			resp.setMsg("系统异常");
			methodEnd(stopWatch, methodName, "调用连连授权接口返回结果失败,ret_code != 0000", resp);
			return resp;
		}

		logger.info("调用连连授权接口成功");

		// 支付
		RepayRequest repayRequest = new RepayRequest();
		repayRequest.setNo_order(GenerateSerialNumber.getSerialNumber().substring(8) + bwOrder.getId());
		repayRequest.setUser_id(borrowerId.toString());
		repayRequest.setNo_agree(agreeNo);
		repayRequest.setDt_order(dateFormat.format(new Date()));
		repayRequest.setName_goods("易秒贷");
		repayRequest.setMoney_order(amount);
		// 测试
		// repayRequest.setMoney_order("0.01");
		repayRequest.setSchedule_repayment_date(repays.get(0).getDate());
		repayRequest.setRepayment_no(bwOrder.getOrderNo());
		repayRequest.setUser_info_bind_phone(bwBorrower.getPhone());
		repayRequest.setUser_info_dt_register(dateFormat.format(bwBorrower.getCreateTime()));
		repayRequest.setUser_info_full_name(bwBorrower.getName());
		repayRequest.setUser_info_id_no(bwBorrower.getIdCard());
		repayRequest.setNotify_url(SystemConstant.NOTIRY_URL + NOTIFY_MAP.get(type));

		// 存入连连redis中，有效时间15分钟
		if (!RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId())) {
			RedisUtils.setex(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId(), bwOrder.getId().toString(), 900);
			logger.info("存redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId().toString() + "]");
		}

		logger.info("开始调用连连支付接口，repayRequest=" + JSONObject.toJSONString(repayRequest));
		RepaymentResult repaymentResult = LianLianPayService.bankRepay(repayRequest);
		logger.info("结束调用连连支付接口，repaymentResult=" + JSONObject.toJSONString(repaymentResult));

		if (CommUtils.isNull(repaymentResult)) {
			resp.setCode("102");
			resp.setMsg("支付失败");
			methodEnd(stopWatch, methodName, "调用连连支付接口返回结果为空", resp);
			return resp;
		}

		if (!"0000".equals(repaymentResult.getRet_code()) && !"1003".equals(repaymentResult.getRet_code())) {
			resp.setCode("102");
			resp.setMsg(repaymentResult.getRet_msg());
			// 删除连连REDIS
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId() + "]");
			RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId());
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId() + "]成功");
			methodEnd(stopWatch, methodName, "调用连连支付接口返回结果失败,ret_code != 0000", resp);
			return resp;
		}

		logger.info("调用连连支付接口成功");
		resp.setCode("200");
		resp.setMsg("支付成功");
		return resp;
	}

	/**
	 * 还款回调
	 * 
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "/drainage/fqgj/callback/repaymentNotify.do")
	public NotifyNotice repaymentNotify(HttpServletRequest request) {
		ThreadLocalUtil.set("REPAYNOTIFY");
		String methodName = "FqgjCallBackController.repaymentNotify";
		logger.info(methodName + " start");
		NotifyNotice notice = new NotifyNotice();

		try {
			NotifyResult notifyResult = getNotifyResult(request);
			logger.info("notifyResult=" + JSONObject.toJSONString(notifyResult));

			if (CommUtils.isNull(notifyResult)) {
				notice.setRet_code("101");
				notice.setRet_msg("异步还款通知为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			if (StringUtils.isBlank(notifyResult.getSign())) {
				notice.setRet_code("101");
				notice.setRet_msg("异步还款通知签名为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			if (StringUtils.isBlank(notifyResult.getNo_order())) {
				notice.setRet_code("101");
				notice.setRet_msg("工单id为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			logger.info("开始验证签名...");

			boolean checkSign = checkLianLianSign(notifyResult);
			if (!checkSign) {
				notice.setRet_code("101");
				notice.setRet_msg("验签未通过");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}
			logger.info("验证签名通过");

			String orderId = notifyResult.getNo_order().substring(20);

			// 验证是否成功
			if (!"SUCCESS".equals(notifyResult.getResult_pay())) {
				notice.setRet_code("102");
				notice.setRet_msg("交易失败");

				try {
					String thirdOrderNo = bwOrderRongService.findThirdOrderNoByOrderId(orderId);
					// 还款状态
					RepayFeedBackReq repayFeedBackReq = new RepayFeedBackReq();
					repayFeedBackReq.setOrder_no(thirdOrderNo);
					repayFeedBackReq.setPeriod_nos("1");
					repayFeedBackReq.setRepay_status("2");
					repayFeedBackReq.setRepay_place("1");
					repayFeedBackReq.setRemark(notifyResult.getResult_pay());
					logger.info("开始调用融360还款状态反馈接口," + repayFeedBackReq);
					RepayFeedBackResp repayFeedBackResp = repayFeedBack(repayFeedBackReq);
					logger.info("结束调用融360还款状态反馈接口," + repayFeedBackResp);
				} catch (Exception e) {
					logger.error("调用融360还款状态反馈接口异常", e);
				}
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setOrderId(Long.parseLong(orderId));
			bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);
			if (CommUtils.isNull(bwOrderRong)) {
				notice.setRet_code("101");
				notice.setRet_msg("融360工单不存在");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			ThreadLocalUtil.set("REPAYNOTIFY-" + bwOrderRong.getThirdOrderNo());
			logger.info("参数校验通过");

			BwOrder order = findBwOrderByIdProxy(Long.parseLong(orderId));

			if (CommUtils.isNull(order)) {
				notice.setRet_code("101");
				notice.setRet_msg("工单不存在");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}

			if (order.getStatusId().intValue() == 6) {
				notice.setRet_code("0000");
				notice.setRet_msg("交易成功");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}

			// 查询还款人信息
			BwBorrower borrower = findBwBorrowerByIdProxy(order.getBorrowerId());
			if (CommUtils.isNull(borrower)) {
				notice.setRet_code("101");
				notice.setRet_msg("借款人为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}

			// 查询银行卡信息
			BwBankCard card = findBwBankCardByBorrowerIdProxy(borrower.getId());
			if (CommUtils.isNull(card)) {
				notice.setRet_code("101");
				notice.setRet_msg("银行卡信息为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}

			// 查询流水记录
			BwPlatformRecord bwPlatformRecord = new BwPlatformRecord();
			bwPlatformRecord.setTradeNo(notifyResult.getOid_paybill());
			int count = bwPlatformRecordService.getBwPlatformRecordCount(bwPlatformRecord);
			if (count > 1) {
				notice.setRet_code("101");
				notice.setRet_msg("该笔还款记录已入库");
				logger.info("该笔还款已记录流水，不需要重复记录,支付订单号：" + notifyResult.getOid_paybill());
				return notice;
			}

			// 记录流水
			logger.info("记录交易流水");
			int platFormInt = savePlatformRecord(notifyResult, card, borrower, order);
			logger.info("记录交易流水结束,新增条数：" + platFormInt);

			// 删除连连REDIS
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + order.getId() + "]");
			RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + order.getId());
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + order.getId() + "]成功");

			// 修改订单状态
			bwRepaymentService.updateOrderStatus(order.getId());

			/**
			 * 融360反馈
			 */
			try {
				String thirdOrderNo = bwOrderRongService.findThirdOrderNoByOrderId(orderId);
				// 订单状态
				OrderFeedBackReq orderFeedBackReq = new OrderFeedBackReq();
				orderFeedBackReq.setOrder_no(thirdOrderNo);
				orderFeedBackReq.setOrder_status(200);
				logger.info("开始调用分期管家订单状态反馈接口," + orderFeedBackReq);
				OrderFeedBackResp orderFeedBackResp = orderFeedBack(orderFeedBackReq);
				logger.info("结束调用分期管家订单状态反馈接口," + orderFeedBackResp);

				// 账单状态
				// RepaymentFeedBackReq repaymentFeedBackReq = new
				// RepaymentFeedBackReq();
				// repaymentFeedBackReq.setOrder_no(thirdOrderNo);
				// repaymentFeedBackReq.setPeriod_no("1");
				// repaymentFeedBackReq.setAmount(notifyResult.getMoney_order());
				// repaymentFeedBackReq.setBill_status("2");
				// repaymentFeedBackReq.setRemark("还款金额：" +
				// notifyResult.getMoney_order());
				// logger.info("开始调用融360账单状态反馈接口," + repaymentFeedBackReq);
				// RepaymentFeedBackResp repaymentFeedBackResp =
				// BeadWalletRong360Service
				// .repaymentFeedBack(repaymentFeedBackReq);
				// logger.info("结束调用融360账单状态反馈接口," + repaymentFeedBackResp);

				// 还款状态
				RepayFeedBackReq repayFeedBackReq = new RepayFeedBackReq();
				repayFeedBackReq.setOrder_no(thirdOrderNo);
				repayFeedBackReq.setPeriod_nos("1");
				repayFeedBackReq.setRepay_status("1");
				repayFeedBackReq.setRepay_place("1");
				logger.info("开始调用融360还款状态反馈接口," + repayFeedBackReq);
				RepayFeedBackResp repayFeedBackResp = repayFeedBack(repayFeedBackReq);
				logger.info("结束调用融360还款状态反馈接口," + repayFeedBackResp);

			} catch (Exception e) {
				logger.error("调用融360反馈接口异常", e);
			}

			// try {
			// logger.info("开始异步通知");
			// Map<String, Object> resp = channelService.sendOrderStatusMQ("11",
			// orderId, "6");
			// logger.info("结束异步通知,resp="+JSONObject.toJSONString(resp));
			// } catch (Exception e) {
			// logger.error("异步通知异常", e);
			// }

			notice.setRet_code("0000");
			notice.setRet_msg("交易成功");
		} catch (Exception e) {
			logger.error("还款回调异常", e);
			notice.setRet_code("103");
			notice.setRet_msg("交易失败");
		}

		logger.info(methodName + " end,resp=" + JSONObject.toJSONString(notice));
		ThreadLocalUtil.remove();
		return notice;
	}

	/**
	 * 还款回调
	 * 
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "/drainage/fqgj/callback/.do")
	public NotifyNotice deferNotify(HttpServletRequest request) {
		ThreadLocalUtil.set("DEFERNOTIFY");
		String methodName = "FqgjCallBackController.deferNotify";
		logger.info(methodName + " start");
		NotifyNotice notice = new NotifyNotice();

		try {
			NotifyResult notifyResult = getNotifyResult(request);
			logger.info("notifyResult=" + JSONObject.toJSONString(notifyResult));

			// 查询流水记录
			BwPlatformRecord bwPlatformRecord = new BwPlatformRecord();
			bwPlatformRecord.setTradeNo(notifyResult.getOid_paybill());
			int count = bwPlatformRecordService.getBwPlatformRecordCount(bwPlatformRecord);
			if (count > 1) {
				notice.setRet_code("101");
				notice.setRet_msg("该笔还款记录已入库");
				logger.info("该笔还款已记录流水，不需要重复记录,支付订单号：" + notifyResult.getOid_paybill());
				return notice;
			}

			if (CommUtils.isNull(notifyResult)) {
				notice.setRet_code("101");
				notice.setRet_msg("异步还款通知为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			if (StringUtils.isBlank(notifyResult.getSign())) {
				notice.setRet_code("101");
				notice.setRet_msg("异步还款通知签名为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			if (StringUtils.isBlank(notifyResult.getNo_order())) {
				notice.setRet_code("101");
				notice.setRet_msg("工单id为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			logger.info("开始验证签名...");

			boolean checkSign = checkLianLianSign(notifyResult);
			if (!checkSign) {
				notice.setRet_code("101");
				notice.setRet_msg("验签未通过");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}
			logger.info("验证签名通过");

			// 验证是否成功
			if (!"SUCCESS".equals(notifyResult.getResult_pay())) {
				notice.setRet_code("102");
				notice.setRet_msg("交易失败");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			String orderId = notifyResult.getNo_order().substring(20);

			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setOrderId(Long.parseLong(orderId));
			bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);

			if (CommUtils.isNull(bwOrderRong)) {
				notice.setRet_code("101");
				notice.setRet_msg("融360工单不存在");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			ThreadLocalUtil.set("DEFERNOTIFY-" + bwOrderRong.getThirdOrderNo());

			boolean b = RedisUtils.hexists(FQGJ_XUDAI, orderId);
			if (b) {
				logger.info("该工单:" + orderId + " 已经存在redis[" + FQGJ_XUDAI + "]中");
				notice.setRet_code("0000");
				notice.setRet_msg("交易成功");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}
			boolean c = RedisUtils.hexists(SystemConstant.NOTIFY_BAOFU, orderId);
			if (c) {
				logger.info("该工单:" + orderId + " 已经存在redis[" + SystemConstant.NOTIFY_BAOFU + "]中");
				notice.setRet_code("0000");
				notice.setRet_msg("交易成功");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}

			BwOrder order = findBwOrderByIdProxy(Long.parseLong(orderId));

			if (CommUtils.isNull(order)) {
				notice.setRet_code("101");
				notice.setRet_msg("工单不存在");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}

			if (order.getStatusId().intValue() == 6) {
				notice.setRet_code("0000");
				notice.setRet_msg("交易成功");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}

			// 查询还款人信息
			BwBorrower borrower = bwBorrowerService.findBwBorrowerByOrderId(Long.valueOf(orderId));
			if (CommUtils.isNull(borrower)) {
				notice.setRet_code("101");
				notice.setRet_msg("借款人为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}

			// 查询银行卡信息
			BwBankCard card = findBwBankCardByBorrowerIdProxy(borrower.getId());
			if (CommUtils.isNull(card)) {
				notice.setRet_code("101");
				notice.setRet_msg("银行卡信息为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}

			// 记录流水
			logger.info("记录交易流水");
			int platFormInt = savePlatformRecord(notifyResult, card, borrower, order);
			logger.info("记录交易流水结束,新增条数：" + platFormInt);

			logger.info("存续贷redis[" + FQGJ_XUDAI + "]开始");
			RedisUtils.hset("xudai:order_id", orderId, orderId);
			logger.info("存续贷redis[" + FQGJ_XUDAI + "]结束");

			// 删除连连REDIS
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + order.getId() + "]");
			RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + order.getId());
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + order.getId() + "]成功");

			notice.setRet_code("0000");
			notice.setRet_msg("交易成功");
		} catch (Exception e) {
			logger.error("还款回调异常", e);
			notice.setRet_code("103");
			notice.setRet_msg("交易失败");
		}

		logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
		ThreadLocalUtil.remove();
		return notice;
	}

	
	private NotifyResult getNotifyResult(HttpServletRequest request) throws Exception {
		NotifyResult notifyResult = null;

		if (CommUtils.isNull(request)) {
			return null;
		}

		request.setCharacterEncoding("utf-8");

		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
		StringBuilder sbuff = new StringBuilder();
		String tmp = "";
		while ((tmp = br.readLine()) != null) {
			sbuff.append(tmp);
		}

		br.close();

		String data = sbuff.toString();

		if (StringUtils.isBlank(data)) {
			return null;
		}

		notifyResult = JSONObject.parseObject(data, NotifyResult.class);

		return notifyResult;
	}

	private boolean checkLianLianSign(NotifyResult notifyResult) {
		Map<String, String> map = new HashMap<>();
		map.put("oid_partner", notifyResult.getOid_partner());
		map.put("sign_type", notifyResult.getSign_type());
		map.put("dt_order", notifyResult.getDt_order());
		map.put("no_order", notifyResult.getNo_order());
		map.put("oid_paybill", notifyResult.getOid_paybill());
		map.put("money_order", notifyResult.getMoney_order());
		map.put("result_pay", notifyResult.getResult_pay());
		map.put("settle_date", notifyResult.getSettle_date());
		map.put("info_order", notifyResult.getInfo_order());
		map.put("pay_type", notifyResult.getPay_type());
		map.put("bank_code", notifyResult.getBank_code());
		map.put("no_agree", notifyResult.getNo_agree());
		map.put("id_type", notifyResult.getId_type());
		map.put("id_no", notifyResult.getId_no());
		map.put("acct_name", notifyResult.getAcct_name());
		map.put("card_no", notifyResult.getCard_no());
		String osign = RSAUtil.sortParams(map);

		return RSAUtil.checksign(SystemConstant.YT_PUB_KEY, osign, notifyResult.getSign());
	}

	/**
	 * 查询附件表
	 * 
	 * @param bwAdjunct
	 * @return
	 */
	private BwAdjunct findBwAdjunctByAttrProxy(BwAdjunct bwAdjunct) {
		logger.info("开始查询附件表,bwAdjunct=" + JSONObject.toJSONString(bwAdjunct));
		bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
		logger.info("结束查询附件表,bwAdjunct=" + JSONObject.toJSONString(bwAdjunct));
		return bwAdjunct;
	}

	private BwOrderRong getByOrderNoProxy(String orderNo) {
		BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
		return bwOrderRong;
	}

	private void methodEnd(StopWatch stopWatch, String methodName, String message, DrainageResp resp) {
		stopWatch.stop();
		logger.info(methodName + " end,costTime=" + stopWatch.getTotalTimeMillis() + "," + message + ",resp=" + resp);
		ThreadLocalUtil.remove();
	}

	private BwBorrower findBwBorrowerByAttrProxy(BwBorrower borrower) {
		logger.info("开始查询借款人信息,borrower=" + JSONObject.toJSONString(borrower));
		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
		logger.info("结束查询借款人信息,borrower=" + JSONObject.toJSONString(borrower));
		return borrower;
	}

	@SuppressWarnings("unused")
	private void sendPwdMsg(String password, String phone) {
		// 发送短信
		try {
			String message = "尊敬的用户您好，恭喜您成功注册，您的登录密码为：" + password + "，您还可以登录 t.cn/R6rhkzU 查看最新的借款进度哦！";
			 MsgReqData msg = new MsgReqData();
			 msg.setPhone(phone);
			 msg.setMsg(message);
			 msg.setType("0");
			 logger.info("开始发送密码短信,phone=" + phone);
			 Response<Object> response =
			 BeadWalletSendMsgService.sendMsg(msg);
			 logger.info("发送完成,发送结果：" + JSONObject.toJSONString(response));
			 boolean bo = sendMessageCommonService.commonSendMessage(phone,
			 message);
			 if (bo) {
			 logger.info("短信发送成功！");
			 } else {
			 logger.info("短信发送失败！");
			 }
			MessageDto messageDto = new MessageDto();
			messageDto.setBusinessScenario("1");
			messageDto.setPhone(phone);
			messageDto.setMsg(message);
			messageDto.setType("1");
			RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
		} catch (Exception e) {
			logger.error("发送短信异常:", e);
		}
	}

	private BwOrderRong findBwOrderRongByAttrProxy(BwOrderRong bwOrderRong) {
		logger.info("开始查询OrderRong工单,bwOrderRong=" + JSONObject.toJSONString(bwOrderRong));
		bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
		logger.info("开始查询OrderRong工单,bwOrderRong=" + JSONObject.toJSONString(bwOrderRong));
		return bwOrderRong;
	}

	/**
	 * 查询工单Id
	 *
	 * @param orderId
	 * @return
	 * @author GuoKun
	 */
	private BwOrder findBwOrderByIdProxy(Long orderId) {
		logger.info("开始查询工单,orderId=" + orderId);
		BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
		logger.info("结束查询工单,bwOrder=" + JSONObject.toJSONString(bwOrder));
		return bwOrder;
	}

	/**
	 * 修改OR新增银行卡
	 *
	 * @param borrower
	 * @param rongBindCard
	 * @param bankCode
	 * @param bankName
	 * @return
	 * @throws InterruptedException
	 */
	private BwBankCard saveOrUpdateBBC(BwBorrower borrower, FqgjBindCardData rongBindCard, String bankCode,
			String bankName) {

		BwBankCard bbc = new BwBankCard();
		// 查询银行卡信息
		bbc.setBorrowerId(borrower.getId());
		bbc = findBwBankCardByAttrProxy(bbc);

		// 如果不存在就新增，如果存在就修改
		if (CommUtils.isNull(bbc)) {
			logger.info("银行卡信息不存在，开始新增");
			bbc = new BwBankCard();
			bbc.setBorrowerId(borrower.getId());
			bbc.setCardNo(rongBindCard.getBank_card());
			bbc.setBankCode(bankCode);
			bbc.setBankName(bankName);
			bbc.setPhone(rongBindCard.getUser_mobile());
			bbc.setSignStatus(0);
			bbc.setCreateTime(Calendar.getInstance().getTime());
			bwBankCardService.saveBwBankCard(bbc, borrower.getId());
		} else {
			logger.info("银行卡信息已存在，开始修改");

			bbc.setBorrowerId(borrower.getId());
			bbc.setCardNo(rongBindCard.getBank_card());
			bbc.setBankCode(bankCode);
			bbc.setBankName(bankName);
			bbc.setPhone(rongBindCard.getUser_mobile());
			bbc.setSignStatus(0);
			bbc.setUpdateTime(Calendar.getInstance().getTime());
			bwBankCardService.update(bbc);
		}

		return bbc;
	}

	private BwBankCard findBwBankCardByAttrProxy(BwBankCard bwBankCard) {
		logger.info("开始查询银行卡信息:bwBankCard=" + JSONObject.toJSONString(bwBankCard));
		bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
		logger.info("银行卡信息查询结果：bwBankCard=" + JSONObject.toJSONString(bwBankCard));
		return bwBankCard;
	}

	private BwBankCard findBwBankCardByBorrowerIdProxy(Long borrowerId) {
		logger.info("开始查询银行卡信息,borrowerId=" + borrowerId);
		BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBorrowerId(borrowerId);
		logger.info("结束查询银行卡信息,bwBankCard=" + JSONObject.toJSONString(bwBankCard));
		return bwBankCard;
	}

	private BwBorrower findBwBorrowerByIdProxy(Long borrowerId) {
		logger.info("开始查询借款人信息,borrowerId=" + borrowerId);
		BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
		logger.info("结束查询借款人信息,borrower=" + JSONObject.toJSONString(bwBorrower));
		return bwBorrower;
	}

	private BwRepaymentPlan findBwRepaymentPlanByAttrProxy(BwRepaymentPlan bwRepaymentPlan) {
		logger.info("开始查询还款计划,bwRepaymentPlan=" + JSONObject.toJSONString(bwRepaymentPlan));
		bwRepaymentPlan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(bwRepaymentPlan.getOrderId());
		logger.info("结束查询还款计划,bwRepaymentPlan=" + JSONObject.toJSONString(bwRepaymentPlan));
		return bwRepaymentPlan;
	}

	private BwOverdueRecord findBwOverdueRecordByAttrProxy(BwOverdueRecord overdueRecord) {
		logger.info("开始查询逾期记录,overdueRecord=" + JSONObject.toJSONString(overdueRecord));
		overdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(overdueRecord);
		logger.info("结束查询逾期记录,overdueRecord=" + JSONObject.toJSONString(overdueRecord));
		return overdueRecord;
	}

	private void bindCardFeedBack(String orderNo, Integer status, String reason) {
		try {
			logger.info("开始调用绑卡反馈接口");
			ServiceResult91 serviceResult = FqgjServiceSDK.bindCardFeedback(orderNo, String.valueOf(status), reason);
			logger.info("结束调用绑卡反馈接口," + JSONObject.toJSONString(serviceResult));
		} catch (Exception e) {
			logger.error("调用绑卡反馈接口异常", e);
		}
	}

	private int savePlatformRecord(NotifyResult notifyResult, BwBankCard card, BwBorrower borrower, BwOrder order) {
		BwPlatformRecord bwPlatformRecord = new BwPlatformRecord();
		bwPlatformRecord.setTradeNo(notifyResult.getOid_paybill());
		bwPlatformRecord.setTradeAmount(Double.valueOf(notifyResult.getMoney_order()));// 交易金额
		bwPlatformRecord.setTradeType(1);// 1划拨2转账
		bwPlatformRecord.setOutAccount(card.getCardNo());
		bwPlatformRecord.setOutName(borrower.getName());
		bwPlatformRecord.setInAccount("上海水象金融信息服务有限公司-连连支付");
		bwPlatformRecord.setInName("上海水象金融信息服务有限公司-连连支付");
		bwPlatformRecord.setOrderId(order.getId());
		bwPlatformRecord.setTradeTime(new Date());
		bwPlatformRecord.setTradeRemark("连连还款扣款");
		bwPlatformRecord.setTradeChannel(3);// 连连支付
		int platFormInt = bwPlatformRecordService.saveBwPlatFormRecord(bwPlatformRecord);
		return platFormInt;
	}

	/**
	 * 订单状态反馈接口
	 * 
	 * @param req
	 * @return
	 */
	public static OrderFeedBackResp orderFeedBack(OrderFeedBackReq req) {
		OrderFeedBackResp resp = new OrderFeedBackResp();
		try {
			String json = JSONObject.toJSONString(FqgjServiceSDK.orderfeedback(req.getOrder_no(),
					String.valueOf(req.getOrder_status()), String.valueOf(System.currentTimeMillis())));
			resp = JSONObject.parseObject(json, OrderFeedBackResp.class);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError("102");
			resp.setMsg("系统错误,请稍后再试[" + e.getMessage() + "]");
			return resp;
		}

		return resp;
	}

	/**
	 * 还款状态异步通知接口
	 * 
	 * @param req
	 * @return
	 */
	public static RepayFeedBackResp repayFeedBack(RepayFeedBackReq req) {
		RepayFeedBackResp resp = new RepayFeedBackResp();
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("order_no", req.getOrder_no());
			paramMap.put("period_nos", req.getPeriod_nos());
			paramMap.put("repay_status", req.getRepay_status());
			paramMap.put("repay_place", req.getRepay_place());
			paramMap.put("remark", req.getRemark());
			String json = JSONObject.toJSONString(FqgjServiceSDK.repayfeedback(paramMap));
			resp = JSONObject.parseObject(json, RepayFeedBackResp.class);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError("102");
			resp.setMsg("系统错误,请稍后再试[" + e.getMessage() + "]");
			return resp;
		}

		return resp;
	}

	public void updStatus2PreApprove(String orderNo) {
		BwOrder bwOrder = getOrderByTOrderNo(orderNo);

		if (com.beadwallet.utils.CommUtils.isNull(bwOrder)) {
			logger.info("订单暂未入库,绑卡标记成功");
			RedisUtils.hset("fqgj:bindTag", orderNo, orderNo);
			return;
		}

		if (bwOrder.getStatusId().intValue() != 1) {
			logger.info("工单状态不为1,不重复修改");
			return;
		}

		boolean check = checkAddInfo(bwOrder);
		if (check) {
			preApprove(bwOrder, orderNo);
		} else {
			// 如果补充信息还没有，在redis中做标记
			logger.info("补充信息暂无,绑卡标记成功");
			RedisUtils.hset("fqgj:bindTag", orderNo, orderNo);
		}
	}

	public BwOrder getOrderByTOrderNo(String orderNo) {
		BwOrder bwOrder = null;
		try {
			logger.info("开始查询orderRong工单，thirdOrderNo=" + orderNo + "...");
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
			logger.info("查询orderRong工单为:" + com.alibaba.fastjson.JSONObject.toJSONString(bwOrderRong));

			if (com.beadwallet.utils.CommUtils.isNull(bwOrderRong)) {
				logger.info("orderRong工单不存在");
				return null;
			}

			logger.info("开始查询工单信息，orderId=" + bwOrderRong.getOrderId() + "...");
			bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
			logger.info("查询工单信息为:" + com.alibaba.fastjson.JSONObject.toJSONString(bwOrder));

			if (com.beadwallet.utils.CommUtils.isNull(bwOrder)) {
				logger.info("工单不存在");
				return null;
			}
		} catch (Exception e) {
			logger.error("getOrderByTOrderNo occured exception:", e);
		}

		return bwOrder;

	}

	public boolean checkAddInfo(BwOrder order) {
		boolean bool = true;
		logger.info("开始验证工单补充信息是否存在,orderId=" + order.getId());
		try {
			logger.info("开始验证持证照信息");
			BwAdjunct czzbaj = new BwAdjunct();
			czzbaj.setAdjunctType(3);
			czzbaj.setOrderId(order.getId());
			czzbaj = bwAdjunctService.findBwAdjunctByAttr(czzbaj);

			if (com.beadwallet.utils.CommUtils.isNull(czzbaj)) {
				logger.info("持证照信息为空");
				return false;
			}
			logger.info("持证照信息验证通过");

			logger.info("开始验证身份证正面照信息");
			BwAdjunct sfzzmbaj = new BwAdjunct();
			sfzzmbaj.setAdjunctType(1);
			sfzzmbaj.setOrderId(order.getId());
			sfzzmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzzmbaj);

			if (com.beadwallet.utils.CommUtils.isNull(sfzzmbaj)) {
				logger.info("身份证正面照信息为空");
				return false;
			}
			logger.info("身份证正面照信息验证通过");

			logger.info("开始验证身份证反面照信息");
			BwAdjunct sfzfmbaj = new BwAdjunct();
			sfzfmbaj.setAdjunctType(2);
			sfzfmbaj.setOrderId(order.getId());
			sfzfmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzfmbaj);

			if (com.beadwallet.utils.CommUtils.isNull(sfzfmbaj)) {
				logger.info("身份证反面照为空");
				return false;
			}
			logger.info("身份证反面照信息验证通过");

			logger.info("开始验证联系人信息");
			BwPersonInfo bpi = bwPersonInfoService.findBwPersonInfoByOrderId(order.getId());

			if (com.beadwallet.utils.CommUtils.isNull(bpi)) {
				logger.info("联系人信息为空");
				return false;
			}
			logger.info("联系人信息验证通过");

			logger.info("开始验证公司信息");
			BwWorkInfo bwi = new BwWorkInfo();
			bwi.setOrderId(order.getId());
			bwi = bwWorkInfoService.findBwWorkInfoByAttr(bwi);
			if (com.beadwallet.utils.CommUtils.isNull(bwi)) {
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

	public void preApprove(BwOrder bwOrder, String orderNo) {
		String methodName = "SignfqgjController.preApprove";

		logger.info("开始查询进行中的订单,borrowerId=" + bwOrder.getBorrowerId());
		Long count = bwOrderService.findProOrder(String.valueOf(bwOrder.getBorrowerId()));
		logger.info("结束查询进行中的订单,count=" + count);

		if (count != null && count.intValue() > 0) {
			try {
				// 修改工单
				logger.info("将工单:" + bwOrder.getId() + "的状态修改为7");
				bwOrder.setStatusId(7L);
				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
				bwOrderService.updateBwOrder(bwOrder);
				logger.info("开始反馈拒绝");
				// 将拒绝订单添加到redis中
				RedisUtils.lpush("order:reject", String.valueOf(bwOrder.getId()));
				logger.info("结束反馈拒绝");
			} catch (Exception e2) {
				logger.error("反馈拒绝异常:", e2);
			}
			ThreadLocalUtil.remove();
			logger.info(methodName + " end,已有进行中的订单,直接拒绝");
			return;
		}

		logger.info("开始设置工单状态为待初审状态,orderId=" + bwOrder.getId());
		try {
			// // 修改认证状态为4
			logger.info("将借款人" + bwOrder.getBorrowerId() + "的认证状态修改为4");
			BwBorrower borrower = updateBorrower(bwOrder.getBorrowerId());
			//
			// // 修改工单
			logger.info("将工单" + bwOrder.getId() + "的状态修改为2");
			bwOrder.setStatusId(2L);
			bwOrderService.updateBwOrder(bwOrder);
			//
			// // 将待审核的信息放入Redis中
			logger.info("开始存入redis[" + SystemConstant.AUDIT_KEY + "]");
			addRedis(bwOrder, borrower, orderNo);
			logger.info("存入redis[" + SystemConstant.AUDIT_KEY + "]结束");
		} catch (Exception e) {
			logger.error(methodName + " occured exception", e);
		}
	}

	private BwBorrower updateBorrower(Long borrowerId) {
		BwBorrower borrower = new BwBorrower();
		borrower.setId(borrowerId);
		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
		borrower.setAuthStep(4);
		bwBorrowerService.updateBwBorrower(borrower);
		return borrower;
	}

	private void addRedis(BwOrder bwOrder, BwBorrower borrower, String thirdOrderId) {
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
		logger.info("redis content:" + JsonUtils.toJson(systemAuditDto));
		Long result = RedisUtils.hset(SystemConstant.AUDIT_KEY, systemAuditDto.getOrderId().toString(),
				JsonUtils.toJson(systemAuditDto));
		if (com.beadwallet.utils.CommUtils.isNull(result) || result.intValue() < 1) {
			logger.info("放入redis失败");
		} else {
			logger.info("放入redis成功");
		}
	}
}
