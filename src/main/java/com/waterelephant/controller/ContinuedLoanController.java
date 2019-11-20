package com.waterelephant.controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beadwallet.entity.request.AppTransReqData;
import com.beadwallet.servcie.BeadwalletService;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.dto.LoanInfo;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwPersonRecord;
import com.waterelephant.entity.BwPlatformRecord;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.service.BwPersonRecordService;
import com.waterelephant.service.BwPlatformRecordService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.FuYouService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.service.impl.BwOrderService;
import com.waterelephant.service.impl.BwOrderXuDaiService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.GenerateSerialNumber;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;
import com.waterelephant.utils.SystemConstant;

/**
 * 续贷处理控制器
 * 
 * @author wrh
 *
 */
@Controller
@RequestMapping("/loan/")
public class ContinuedLoanController {
	private Logger logger = Logger.getLogger(ContinuedLoanController.class);
	@Autowired
	private BwOrderService bwOrderService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private BwPersonRecordService bwPersonRecordService;
	@Autowired
	private FuYouService fuYouService;
	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;
	@Autowired
	private IBwRepaymentPlanService bwRepaymentPlanService;
	@Autowired
	private BwPlatformRecordService bwPlatformRecordService;
	@Autowired
	private BwOrderXuDaiService bwOrderXudaiService;

	/**
	 * 我要续贷
	 */
	@ResponseBody
	@RequestMapping("appCheckLogin/continued.do")
	public AppResponseResult contiued(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");// 订单id
		String bwId = request.getParameter("bwId");// 借款人id
		String amt = request.getParameter("amt");// 利息
		String term = request.getParameter("term");// 续贷期限
		logger.info("我要续贷借款入参orderId===" + orderId + "=借款人bwId=====" + bwId + "要还利息amt====" + amt + "续贷期限term======="
				+ term + "个月");

		if (RedisUtils.hexists(SystemConstant.WEIXIN_ORDER_ID, orderId)) {
			result.setCode("108");
			result.setMsg("此工单续贷正在处理中..");
			return result;
		}
		BwOrder bwOrder = new BwOrder();
		if (!CommUtils.isNull(orderId)) {
			bwOrder.setId(Long.valueOf(orderId));
		}
		BwOrder order = bwOrderService.selectByPrimaryKey(bwOrder.getId());
		if (CommUtils.isNull(order)) {
			result.setCode("101");
			result.setMsg("工单不存在");
			return result;
		}

		BwBorrower bwBorrower = new BwBorrower();
		if (!CommUtils.isNull(bwId)) {
			bwBorrower.setId(Long.valueOf(bwId));
		}
		BwBorrower bw = bwBorrowerService.findBwBorrowerByAttr(bwBorrower);
		if (CommUtils.isNull(bw)) {
			result.setCode("102");
			result.setMsg("借款人不存在");
			return result;
		}
		// int xudaiTerm= bwOrderXuDaiService.queryXudaiTerm(order.getId());
		// if(xudaiTerm>=3){
		// result.setCode("109");
		// result.setMsg("每续贷3次必须结清一次本金，否则不允许再次续贷!");
		// return result;
		//
		// }
		Long statusId = order.getStatusId();// 工单状态
		logger.info("工单状态==========statusId==================" + statusId);
		if (!(statusId == 9 || statusId == 13)) {// 还款中 逾期
			result.setCode("106");
			result.setMsg("此工单续贷正在处理中..");// 提示信息方便前端显示 让用户去手动刷新界面
			// 实际提示信息(工单只有还款中和逾期中才可还款 )
			return result;
		}
		try {
			if (statusId == 9) {// 还款中...
				// 获取下个还款日
				List<Map<String, Object>> list = bwRepaymentPlanService
						.findBwRepaymentPlanByOrderId(Long.parseLong(orderId));
				if (list != null && list.size() > 0) {
					// 下个还款日
					String repay_time = "";
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
					repay_time = formatter.format(list.get(0).get("repay_time"));
					logger.info("下个还款日============================" + repay_time);
					Date repay_date = formatter.parse(repay_time);
					int day = MyDateUtils.getDaySpace(new Date(), repay_date);// 间隔时间
					logger.info("还款中状态下到期间隔天数============================" + day);
					if (day > 10) {
						result.setCode("103");
						result.setMsg("到期时间十天内方可续贷");
						return result;
					}
				}
			}
			HashMap<String, Double> map = fuYouService.getFuiouAmount(bwId);
			if (map.size() == 0) {
				result.setCode("107");
				result.setMsg("系统异常 请稍后重试..");
				logger.info("我要续贷=======调用富友接口 查询余额出错=====================");
				return result;
			}
			// Double ct_balance = map.get("ct_balance");// 账面总余额
			Double ca_balance = map.get("ca_balance");// 可用余额
			// Double cf_balance = map.get("cf_balance");// 冻结余额
			// 解冻余额
			// if (cf_balance > 0) {
			// String cf_mchnt_txn_ssn = GenerateSerialNumber.getSerialNumber();
			// AppResponseResult cfResult = fuYouService.removeFreeze(bwId, cf_balance / 100, cf_mchnt_txn_ssn);
			// String cfrespCode = cfResult.getCode();// 0000成功
			// String cfbeadwalletMsg = cfResult.getMsg();// 提示信息
			// logger.info("我要续贷 解冻接口返回 编码cfrespCode===" + cfrespCode + "消息cfbeadwalletMsg===" + cfbeadwalletMsg
			// + "解冻金额==" + cf_balance / 100 + "元");
			// if (!StringUtils.isBlank(cfrespCode) && cfrespCode.equals("0000")) {// 解冻成功
			// // 解冻金额记录
			// BwPersonRecord rechargeRecord = new BwPersonRecord();
			// rechargeRecord.setTradeNo(cf_mchnt_txn_ssn);// 交易流水号
			// rechargeRecord.setTradeAmount(cf_balance/100);// 交易金额
			// rechargeRecord.setTradeType(2);// 交易类型 1-冻结 2-解冻 3-委托提现// 4-委托充值',
			// rechargeRecord.setPersonAccount(bw.getFuiouAcct());// 交易人账号
			// rechargeRecord.setPersonName(bw.getName());// 交易人姓名
			// rechargeRecord.setOrderId(Long.valueOf(orderId));// 工单id
			// rechargeRecord.setTradeTime(new Date());// 交易时间
			// rechargeRecord.setTradeRemark("解冻余额");
			// int cfInt = bwPersonRecordService.saveBwPersonRecord(rechargeRecord);
			// logger.info("解冻还款人余额=记录解冻==cfInt==" + cfInt);
			// ///////////
			// } else {
			// result.setCode("105");
			// result.setMsg(cfbeadwalletMsg);
			// return result;
			// }
			// }
			logger.info("借款人富友账号的总余额=====" + ca_balance);
			if (ca_balance < Double.valueOf(amt) * 100) {

				result.setCode("104");
				result.setMsg("富友账号余额不足,请先充值!");
				result.setResult(Double.valueOf(amt) - (ca_balance / 100));
				logger.info("我要续贷 要充值的差额====" + (Double.valueOf(amt) - (ca_balance / 100)));
				return result;
			}
			////////////////////// 转账
			String mchnt_txn_ssn = GenerateSerialNumber.getSerialNumber();
			logger.info("借款人打入公司平台账号的流水号=====" + mchnt_txn_ssn);
			// 转账
			AppResponseResult transferResult = fuYouService.bwTransferBu(bwId, Double.valueOf(amt), mchnt_txn_ssn);
			String respCode = transferResult.getCode();// 0000成功
			String beadwalletMsg = transferResult.getMsg();// 提示信息
			logger.info("借款人打入公司平台金额==划拨===respCode=====" + respCode + "beadwalletMsg======" + beadwalletMsg);

			// 划拨到平台记录
			BwPlatformRecord bwPlatformRecord = new BwPlatformRecord();
			bwPlatformRecord.setTradeNo(mchnt_txn_ssn);
			bwPlatformRecord.setTradeAmount(Double.valueOf(amt));
			bwPlatformRecord.setTradeType(1);// 1划拨2转账
			bwPlatformRecord.setOutAccount(bw.getFuiouAcct());
			bwPlatformRecord.setOutName(bw.getName());
			bwPlatformRecord.setInAccount(SystemConstant.FUIOU_MCHNT_BACKUP);
			bwPlatformRecord.setInName("续贷划拨风险备用金");
			bwPlatformRecord.setOrderId(order.getId());
			bwPlatformRecord.setTradeTime(new Date());
			bwPlatformRecord.setTradeChannel(1);
			logger.info("借款人打入公司平台金额==划拨===OutName=====" + bw.getName() + "InName======" + "续贷风险备用金");
			//////////////////////////
			if (StringUtils.isBlank(respCode) || !respCode.equals("0000")) {// 划拨不成功
				result.setCode(respCode);
				result.setMsg(beadwalletMsg);
				return result;
			} else {
				bwPlatformRecord.setTradeCode(respCode);// 编码
				bwPlatformRecord.setTradeRemark(beadwalletMsg);// 提示
				logger.info("存redis开始===========");
				// *********修改工单及系列操作 改为*********存redis
				RedisUtils.hset(SystemConstant.WEIXIN_ORDER_ID, order.getId().toString(), order.getId().toString());
				// *******************
				logger.info("存redis结束===========");
				int plantFormInt = bwPlatformRecordService.saveBwPlatFormRecord(bwPlatformRecord);
				logger.info("划拨记录新增条数=====" + plantFormInt);
				result.setCode(respCode);
				result.setMsg(beadwalletMsg);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("我要续贷接口出错=====" + e.getMessage());
			result.setCode("111");
			result.setMsg("系统出现异常 请稍后重试...!");

			return result;
		}
	}

	/**
	 * 我要续贷 查询订单信息（旧版接口）
	 * 
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping("appCheckLogin/queryOrder.do")
	public AppResponseResult queryOrder(HttpServletRequest request) throws ParseException {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");// 订单id
		logger.info("我要续贷 查询订单信息接口orderId==================：" + orderId);
		BwOrder bwOrder = new BwOrder();
		if (!CommUtils.isNull(orderId)) {
			bwOrder.setId(Long.valueOf(orderId));
		}

		BwOrder order = bwOrderService.selectByPrimaryKey(bwOrder.getId());
		if (CommUtils.isNull(order)) {
			result.setCode("101");
			result.setMsg("工单不存在");
			return result;
		}
		try {
			// 获取产品信息
			BwProductDictionary dict = bwProductDictionaryService.findById(Long.valueOf(order.getProductId()));
			Double pInvestRateMonth = dict.getpInvestRateMonth();// 月利率
			Double pPoundageScale = dict.getpPoundageScale();// 产品手续费
			Double borrowAmount = order.getBorrowAmount();
			DecimalFormat df = new DecimalFormat("######0.00");
			Integer hasAfterXudaiTerm = bwRepaymentPlanService.getXuDaiCountAfterDate(Long.parseLong(orderId));
			logger.info("我要续贷 查询订单信息接口orderId==================：" + orderId + ",已续贷次数hasAfterXudaiTerm:"
					+ hasAfterXudaiTerm);
			if (StringUtil.toInteger(hasAfterXudaiTerm) == 0) {
				pPoundageScale += 0.09;
			}
			if (StringUtil.toInteger(hasAfterXudaiTerm) == 1) {
				pPoundageScale += 0.12;
			}
			if (StringUtil.toInteger(hasAfterXudaiTerm) >= 2) {
				pPoundageScale += 0.15;
			}
			logger.info(
					"我要续贷 查询订单信息接口orderId==================：" + orderId + ",续贷总服务利率pPoundageScale:" + pPoundageScale);
			String amt = df.format(borrowAmount * (pInvestRateMonth + pPoundageScale));
			logger.info("月利率==================：" + pInvestRateMonth + "产品手续费=============" + pPoundageScale
					+ "======续贷要还利息=======" + amt);

			LoanInfo loanInfo = new LoanInfo();
			loanInfo.setAmt(amt);// 要还利息
			String applyTime = new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
			loanInfo.setApplyTime(applyTime);// 申请时间
			loanInfo.setTerm("1");// 续贷时长
			loanInfo.setBorrowAmount(df.format(borrowAmount));// 续贷金额

			// 获取还款日期
			String delayTime = "";
			BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService.getPlan(Long.parseLong(orderId), 1);
			if (bwRepaymentPlan != null) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
				delayTime = formatter.format(MyDateUtils.addMonths(bwRepaymentPlan.getRepayTime(), 1));
				loanInfo.setDelayTime(delayTime);

			}
			logger.info("要还利息==================：" + amt + "申请时间=============" + applyTime + "======续贷金额======="
					+ borrowAmount + "=======延期时间至======" + delayTime);
			result.setCode("000");
			result.setMsg("成功");
			result.setResult(loanInfo);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("我要续贷查询订单信息出错=====" + e.getMessage());
			result.setCode("111");
			result.setMsg("我要续贷 查询订单信息出错...!");
		}

		return result;
	}

	/**
	 * 充值回调 微信
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rechargeCallback.do")
	public AppResponseResult rechargeCallback(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String param = request.getParameter("param") == null ? "" : request.getParameter("param");
		String orderId = param.split("_")[1];// 854
		//// String term = request.getParameter("term");// 续贷期限
		// ////////充值返回参数//////////
		String login_id = request.getParameter("login_id");// 富友账号
		String resp_code = request.getParameter("resp_code");// 返回码
		String mchnt_txn_ssn = request.getParameter("mchnt_txn_ssn");// 流水号
		String temp = request.getParameter("amt");// 交易金额
		Double rechargeAmt = Double.valueOf(temp) / 100;
		logger.info("充值返回前台代码:" + resp_code + ",充值返回前台流水号:" + mchnt_txn_ssn);
		try {
			// 还款人信息
			BwBorrower borrower = new BwBorrower();
			borrower.setFuiouAcct(login_id);
			BwBorrower bw = bwBorrowerService.findBwBorrowerByAttr(borrower);
			// 订单信息
			BwOrder bwOrder = new BwOrder();
			if (!CommUtils.isNull(orderId)) {
				bwOrder.setId(Long.valueOf(orderId));
			}
			BwOrder order = bwOrderService.selectByPrimaryKey(bwOrder.getId());

			// 充值成功后处理
			if (!StringUtils.isBlank(resp_code) && resp_code.equals("0000") && !CommUtils.isNull(bw)) {
				//////////////////////////// 还款人充值成功记录添加
				BwPersonRecord rechargeRecord = new BwPersonRecord();
				rechargeRecord.setTradeNo(mchnt_txn_ssn);// 交易流水号
				rechargeRecord.setTradeAmount(rechargeAmt);// 交易金额
				rechargeRecord.setTradeType(5);// 交易类型 1-冻结 2-解冻 3-委托提现 4-委托充值 5 充值',
				rechargeRecord.setPersonAccount(bw.getFuiouAcct());// 交易人账号
				rechargeRecord.setPersonName(bw.getName());// 交易人姓名
				rechargeRecord.setOrderId(Long.valueOf(orderId));// 工单id
				rechargeRecord.setTradeTime(new Date());// 交易时间
				rechargeRecord.setTradeRemark("续贷充值");
				rechargeRecord.setTradeChannel(1);
				bwPersonRecordService.saveBwPersonRecord(rechargeRecord);
				///////////// 查询要还利息//////////////
				BwProductDictionary dict = bwProductDictionaryService.findById(Long.valueOf(order.getProductId()));
				Double pInvestRateMonth = dict.getpInvestRateMonth();// 月利率
				Double pPoundageScale = dict.getpPoundageScale();// 产品手续费
				Double borrowAmount = order.getBorrowAmount();
				DecimalFormat df = new DecimalFormat("######0.00");
				Double amt = Double.valueOf(df.format(borrowAmount * (pInvestRateMonth + pPoundageScale)));
				logger.info("充值回调要划拨的利息=======amt=======" + amt);
				//////////////////////////// 还款人划拨到公司风险金账号
				String mchntTxnSsn = GenerateSerialNumber.getSerialNumber();// 流水号
				AppResponseResult transferResult = fuYouService.bwTransferBu(String.valueOf(bw.getId()), amt,
						mchntTxnSsn);
				String respCode = transferResult.getCode();// 0000成功
				String beadwalletMsg = transferResult.getMsg();// 提示信息

				// 转账到平台记录
				BwPlatformRecord bwPlatformRecord = new BwPlatformRecord();
				bwPlatformRecord.setTradeNo(mchnt_txn_ssn);
				bwPlatformRecord.setTradeAmount(amt);
				bwPlatformRecord.setTradeType(1);// 1划拨2转账
				bwPlatformRecord.setOutAccount(bw.getFuiouAcct());
				bwPlatformRecord.setOutName(bw.getName());
				bwPlatformRecord.setInAccount(SystemConstant.FUIOU_MCHNT_BACKUP);
				bwPlatformRecord.setInName("续贷划拨风险备用金");
				bwPlatformRecord.setOrderId(order.getId());
				bwPlatformRecord.setTradeTime(new Date());
				bwPlatformRecord.setTradeChannel(1);

				if (!CommUtils.isNull(respCode) && respCode.equals("0000")) {// 划拨成功
					bwPlatformRecord.setTradeCode(respCode);// 转账编码
					bwPlatformRecord.setTradeRemark(beadwalletMsg);// 转账提示
				} else {
					//////////////////// 划拨失败 1冻结充值金额 2 添加划拨平台交易记录
					bwPlatformRecord.setTradeCode(respCode);// 错误编码
					bwPlatformRecord.setTradeRemark(beadwalletMsg);// 错误提示
					fuYouService.freezeAccount(login_id, amt);

				}
				logger.info(
						"划拨返回 ===respCode==============" + respCode + "======beadwalletMsg==========" + beadwalletMsg);
				//////////////////////////// 修改工单及系列操作
				int planFormInt = bwPlatformRecordService.saveBwPlatFormRecord(bwPlatformRecord);// 划拨到平台保存
				logger.info("划拨记录新增条数===========" + planFormInt);
				logger.info("存redis开始===========");
				// *********修改工单及系列操作 改为*********存redis
				RedisUtils.hset(SystemConstant.WEIXIN_ORDER_ID, order.getId().toString(), order.getId().toString());
				// *****************************************
				logger.info("存redis结束===========");
			}
			result.setCode("000");
			result.setMsg("成功");

		} catch (Exception e) {
			logger.error("我要续贷==充值回调同时处理划拨接口出错 =================" + e.getMessage());
			result.setCode("111");
			result.setMsg("我要续贷==充值回调同时处理划拨接口出错");
		}
		return result;
	}

	/**
	 * app跳转到充值页面 amt -- 充值金额 page_notify_url --回调地址 userId ---用户ID orderId---订单编号 back_notify_url -- 通知地址
	 * 
	 * @param wyTransReqData
	 * @return
	 */
	@RequestMapping("rechargePage.do")
	public void rechargePage(AppTransReqData appTransReqData, HttpServletResponse response,
			HttpServletRequest request) {
		String orderId = request.getParameter("orderId");
		String userId = request.getParameter("userId");
		String type = request.getParameter("type");
		String param = request.getParameter("param");
		String amt = appTransReqData.getAmt();
		logger.info("=====页面传入充值金额=amt===========" + amt);
		appTransReqData.setAmt(doubleToString(Double.valueOf(amt)));
		logger.info(" ====计算后传入富友充值金额============" + appTransReqData.getAmt());
		// 商户ID
		appTransReqData.setMchnt_cd(ResourceBundle.getBundle("fuiou").getString("mchnt_cd"));
		// 流水号
		String mchnt_txn_ssn = GenerateSerialNumber.getSerialNumber();
		appTransReqData.setMchnt_txn_ssn(mchnt_txn_ssn);
		// 根据用户账号查询对应的富有账号
		if (StringUtils.isNotBlank(userId)) {
			BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(Long.parseLong(userId));
			if (!CommUtils.isNull(bwBorrower)) {
				appTransReqData.setLogin_id(bwBorrower.getFuiouAcct());
			}
		}
		// 还款
		if (type.equals("1")) {
			appTransReqData.setBack_notify_url(SystemConstant.CALLBACK_URL + "/app/repay/repayment.do?param=" + param);
			appTransReqData.setPage_notify_url(
					SystemConstant.CALLBACK_URL + "/loan/reimbursement_weixin.do?orderId=" + orderId);
		}
		// 续贷
		if (type.equals("2")) {
			appTransReqData
					.setBack_notify_url(SystemConstant.CALLBACK_URL + "/loan/rechargeCallback.do?param=" + param);
			appTransReqData.setPage_notify_url(
					SystemConstant.CALLBACK_URL + "/loan/reimbursement_xudai.do?orderId=" + orderId);
		}
		// app回调
		// 还款
		if (type.equals("3")) {
			appTransReqData.setBack_notify_url(SystemConstant.CALLBACK_URL + "/app/repay/repayment.do?param=" + param);
			appTransReqData.setPage_notify_url(
					SystemConstant.CALLBACK_URL + "/loan/reimbursement_weixin_app.do?orderId=" + orderId);
		}
		// 续贷
		if (type.equals("4")) {
			appTransReqData
					.setBack_notify_url(SystemConstant.CALLBACK_URL + "/loan/rechargeCallback.do?param=" + param);
			appTransReqData.setPage_notify_url(
					SystemConstant.CALLBACK_URL + "/loan/reimbursement_xudai_app.do?orderId=" + orderId);
		}
		BeadwalletService.bwRedirectAppRecharge(appTransReqData, response);
	}

	/**
	 * 微信充值 微信
	 * 
	 * @param request
	 * @param response
	 * @param modelManp
	 * @return
	 */
	@RequestMapping("/reimbursement_weixin.do")
	public String signCallBack(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String orderId = request.getParameter("orderId");
		String resp_code = request.getParameter("resp_code");
		String resp_desc = request.getParameter("resp_desc");
		logger.info("微信充值回调请求获取orderId:" + orderId);
		logger.info("微信充值回调请求获取resp_code:" + resp_code);
		logger.info("微信充值回调请求获取resp_desc:" + resp_desc);
		modelMap.put("orderId", orderId);
		if (!CommUtils.isNull(resp_code) && (resp_code.equals("0000") || resp_code.equals("5360"))) {
			return "reimbursement_success_weixin";
		} else {
			modelMap.put("respDesc", resp_desc);
			return "reimbursement_fail_weixin";
		}
	}

	/**
	 * 续贷成功页面 微信
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/reimbursement_xudai.do")
	public String signXuDaiCallBack(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String orderId = request.getParameter("orderId");
		String resp_code = request.getParameter("resp_code");
		String resp_desc = request.getParameter("resp_desc");
		logger.info("微信充值回调页面获取orderId:" + orderId);
		logger.info("微信充值回调页面获取resp_code:" + resp_code);
		logger.info("微信充值回调页面获取resp_desc:" + resp_desc);
		modelMap.put("orderId", orderId);
		if (!CommUtils.isNull(resp_code) && (resp_code.equals("0000") || resp_code.equals("5360"))) {
			return "reimbursement_success_xudai";
		} else {
			modelMap.put("respDesc", resp_desc);
			return "reimbursement_fail_xudai";
		}
	}

	/**
	 * 微信充值 App
	 * 
	 * @param request
	 * @param response
	 * @param modelManp
	 * @return
	 */
	@RequestMapping("/reimbursement_weixin_app.do")
	public String signCallBackApp(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String orderId = request.getParameter("orderId");
		String resp_code = request.getParameter("resp_code");
		String resp_desc = request.getParameter("resp_desc");
		logger.info("微信充值回调请求获取orderId:" + orderId);
		logger.info("微信充值回调请求获取resp_code:" + resp_code);
		logger.info("微信充值回调请求获取resp_desc:" + resp_desc);
		modelMap.put("orderId", orderId);
		if (!CommUtils.isNull(resp_code) && (resp_code.equals("0000") || resp_code.equals("5360"))) {
			return "reimbursement_success_weixin_app";
		} else {
			modelMap.put("respDesc", resp_desc);
			return "reimbursement_fail_weixin_app";
		}
	}

	/**
	 * 续贷成功页面 APP
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/reimbursement_xudai_app.do")
	public String signXuDaiCallBackApp(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String orderId = request.getParameter("orderId");
		String resp_code = request.getParameter("resp_code");
		String resp_desc = request.getParameter("resp_desc");
		logger.info("微信充值回调页面获取orderId:" + orderId);
		logger.info("微信充值回调页面获取resp_code:" + resp_code);
		logger.info("微信充值回调页面获取resp_desc:" + resp_desc);
		modelMap.put("orderId", orderId);
		if (!CommUtils.isNull(resp_code) && (resp_code.equals("0000") || resp_code.equals("5360"))) {
			return "reimbursement_success_xudai_app";
		} else {
			modelMap.put("respDesc", resp_desc);
			return "reimbursement_fail_xudai_app";
		}
	}

	private String doubleToString(double num) {
		num = num * 100;
		DecimalFormat decimalFormat = new DecimalFormat("0");
		return decimalFormat.format(num);
	}
}
