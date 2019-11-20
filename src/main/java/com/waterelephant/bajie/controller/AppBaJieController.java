package com.waterelephant.bajie.controller;

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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.entity.lianlian.Agreement;
import com.beadwallet.entity.lianlian.CardQueryResult;
import com.beadwallet.entity.lianlian.NotifyNotice;
import com.beadwallet.entity.lianlian.NotifyResult;
import com.beadwallet.entity.lianlian.PlanResult;
import com.beadwallet.entity.lianlian.RepayRequest;
import com.beadwallet.entity.lianlian.RepaymentPlan;
import com.beadwallet.entity.lianlian.RepaymentResult;
import com.beadwallet.entity.lianlian.SignalLess;
import com.beadwallet.servcie.LianLianPayService;
import com.beadwallet.utils.RSAUtil;
import com.waterelephant.bajie.entity.BaJieResp;
import com.waterelephant.bajie.entity.BajieParam;
import com.waterelephant.bajie.entity.BajieRegisterData;
import com.waterelephant.bajie.entity.BajieRepaymentData;
import com.waterelephant.bajie.util.BaJieContext;
import com.waterelephant.bajie.util.BajieUtils;
import com.waterelephant.bajie.util.LogUtil;
import com.waterelephant.bajie.util.MessageUtils;
import com.waterelephant.bajie.util.SignUtils;
import com.waterelephant.bajie.util.ThreadLocalUtil;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderChannel;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwOrderTem;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwPlatformRecord;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.register.util.DESUtil;
import com.waterelephant.register.util.RegisterUtils;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwOrderTemService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.BwPlatformRecordService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderChannelService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.service.IBwRepaymentService;
import com.waterelephant.sms.service.SendMessageCommonService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.GenerateSerialNumber;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

@Controller
@RequestMapping("/app/bajie")
public class AppBaJieController {
	LogUtil logger = new LogUtil(AppBaJieController.class);

	@Autowired
	private BwOrderRongService bwOrderRongService;

	@Autowired
	private IBwBorrowerService bwBorrowerService;

	@Autowired
	private IBwBankCardService bwBankCardService;

	@Autowired
	private IBwOrderService bwOrderService;

	@Autowired
	private BwPlatformRecordService bwPlatformRecordService;

	@Autowired
	private IBwRepaymentService bwRepaymentService;

	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;

	@Autowired
	private IBwRepaymentPlanService bwRepaymentPlanService;

	@Autowired
	private SendMessageCommonService sendMessageCommonService;

	@Autowired
	private IBwOrderChannelService bwOrderChannelService;

	@Autowired
	private BwOrderTemService bwOrderTemService;

	private static String DES_KEY = BaJieContext.get("desKey");
	private static String XUDAI = "xudai:order_id";
	private static String NOTIFY_ORDER_STATE_REDIS = "notify:orderState";
	private static String NOTIFY_REGISTER_REDIS = "notify:register";

	@ResponseBody
	@RequestMapping("/register.do")
	public BaJieResp register(HttpServletRequest request, HttpServletResponse response) {
		BaJieResp resp = new BaJieResp();
		StopWatch stopWatch = new StopWatch(); // 开始计时
		stopWatch.start();
		logger.set("BAJIE");

		String methodName = "AppBaJieController.register";
		logger.info(methodName + " start");
		try {
			logger.info("开始验证请求参数");
			String param = request.getParameter("param"); // 获取param的参数

			if (StringUtils.isBlank(param)) { // 检验参数是否为空
				resp.setSuccess(false);
				resp.setErrorMsg("必要参数为空");
				logger.info(methodName + " end,param is null,resp=" + resp);
				return resp;
			}

			BajieParam req = JSONObject.parseObject(param, BajieParam.class); // 解析json

			String data = req.getData();
			String sign = req.getSign();

			if (StringUtils.isBlank(data)) { // 判断data（加密后的结果）是否为空
				resp.setSuccess(false);
				resp.setErrorMsg("必要参数为空");
				logger.info(methodName + " end,data is null,resp=" + resp);
				return resp;
			}

			if (StringUtils.isBlank(sign)) { // 判断sign（加密后的签名）是否为空
				resp.setSuccess(false);
				resp.setErrorMsg("必要参数为空");
				logger.info(methodName + " end,sign is null,resp=" + resp);
				return resp;
			}

			String channel = BaJieContext.get("channel"); // 第三方发给机构的调用接口的身份id
			// 根据渠道号查询整个工单渠道对象
			BwOrderChannel bwOrderChannel = bwOrderChannelService.getOrderChannelByCode(channel);

			data = data.replaceAll(" ", "+");

			logger.info("开始验证签名");
			// 验证签名结果是否存在 booean类型的
			// 判断传过来的data数据加密后和传过来的签名是否相同
			boolean check = SignUtils.verify(data, sign, DES_KEY, "UTF-8");

			if (!check) {
				resp.setSuccess(false);
				resp.setErrorMsg("验签失败");
				logger.info(methodName + " end,sign is null,resp=" + resp);
				return resp;
			}

			data = MessageUtils.decrypt(data, DES_KEY); // 解密
			BajieRegisterData bajieRegisterData = JSONObject.parseObject(data, BajieRegisterData.class);
			// 将json格式的data转换成BajieRegisterData的对象
			String mobile = bajieRegisterData.getMobile();
			String orderNo = bajieRegisterData.getOrderNo();
			logger.set("BAJIE-" + mobile);
			logger.info("开始查询借款人信息,phone=" + mobile);
			BwBorrower borrower = new BwBorrower(); // 借款人实体类
			borrower.setPhone(mobile);
			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
			// 根据借款对象查询借款对象（参数借款对象的属性为查询条件）
			// logger.info("结束查询接口人信息,borrowerId=" + JSONObject.toJSONString(borrower));
			logger.info("结束查询接口人信息,borrowerId=" + borrower.getId());
			boolean newUser = false;
			if (CommUtils.isNull(borrower)) {
				// 创建借款人，如果借款人不存在
				logger.info("借款人信息不存在，开始创建借款人");
				String password = RegisterUtils.getRandNum(1, 999999); // 初始密码是1到999999的随机数
				borrower = new BwBorrower();
				borrower.setPhone(mobile); // 设置电话号码
				borrower.setPassword(CommUtils.getMD5(password.getBytes())); // 密码使用MD5加密
				borrower.setAuthStep(1); // 认证步骤 ：默认为1（个人信息认证）
				borrower.setFlag(1); // 删除标志:默认为1(未删除)
				borrower.setState(1); // 借款人状态:默认为1(启用)
				borrower.setChannel(bwOrderChannel.getId()); // 注册渠道(1.ios 2.app 3.微信 4.融360， 17 分期管家)
				borrower.setCreateTime(Calendar.getInstance().getTime()); // 创建时间
				borrower.setUpdateTime(Calendar.getInstance().getTime()); // 更新时间
				bwBorrowerService.addBwBorrower(borrower); // 新增借款人信息到bw_borrower表中
				logger.info("生成的借款人id:" + borrower.getId());

				// 发送短信
				try {
					String message = RegisterUtils.getMsg(password);
					boolean bo = sendMessageCommonService.commonSendMessage(mobile, message);
					if (bo) {
						logger.info("短信发送成功！");
					} else {
						logger.info("短信发送失败！");
					}
				} catch (Exception e) {
					logger.error("发送短信异常:", e);
				}

				newUser = true;

			}
			// 假如借款人存在
			String callBack = BaJieContext.get("callBack");
			String des = BaJieContext.get("callback.des");
			if (StringUtils.isNotBlank(callBack)) {
				Map<String, String> dataMap = new HashMap<String, String>();
				dataMap.put("borrowerId", borrower.getId().toString());
				dataMap.put("channel", channel);
				dataMap.put("phone", mobile);
				dataMap.put("password", borrower.getPassword());
				dataMap.put("flag", String.valueOf(borrower.getFlag()));
				dataMap.put("authStep", String.valueOf(borrower.getAuthStep()));
				dataMap.put("state", String.valueOf(borrower.getState()));
				dataMap.put("channelId", String.valueOf(borrower.getChannel()));
				String dataJson = JSONObject.toJSONString(dataMap);
				String enJson = DESUtil.encryption(dataJson, des); // DES加密
				resp.setUrl(RegisterUtils.getReturnUrl(new Object[] { enJson }, callBack)); // 将借款人信息放入url中，然后回调
			}

			resp.setSuccess(true);
			resp.setErrorMsg("成功");

			BwOrderTem bwOrderTem = new BwOrderTem(); // 生成水象的工单
			bwOrderTem.setChannelKey("bajie");
			bwOrderTem.setCreateTime(Calendar.getInstance().getTime());
			bwOrderTem.setPhone(mobile);
			bwOrderTem.setThirdOrderNo(orderNo);

			bwOrderTemService.save(bwOrderTem); // 保存工单
			// 异步通知注册情况
			RedisUtils.lpush(NOTIFY_REGISTER_REDIS, orderNo + "|" + mobile + "|" + newUser);
		} catch (Exception e) {
			resp.setSuccess(false);
			resp.setErrorMsg("接口异常");
			logger.error("接口异常:", e);
		}

		stopWatch.stop();
		logger.info(methodName + " end,costTime=" + stopWatch.getTotalTimeMillis());
		logger.remove();
		return resp;
	}

	/**
	 * 回调
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/callBack.do")
	public String callBack(HttpServletRequest request, HttpServletResponse response) {
		String methodName = "AppBaJieController.callBack";
		logger.info(methodName + " start");
		String newUserReturnUrl = BaJieContext.get("newUser.returnUrl");
		try {
			String secretData = request.getParameter("data").replaceAll(" ", "+");
			String callBackDes = BaJieContext.get("callback.des");
			String data = DESUtil.decryption(secretData, callBackDes); // DES解密
			Map dataMap = JSONObject.parseObject(data, Map.class); // 将data解析成map
			String borrowerId = (String) dataMap.get("borrowerId");
			String channelId = (String) dataMap.get("channelId");
			String channel = (String) dataMap.get("channel");

			String phone = (String) dataMap.get("phone");
			String password = (String) dataMap.get("password");
			String flag = (String) dataMap.get("flag");
			String authStep = (String) dataMap.get("authStep");
			String state = (String) dataMap.get("state");
			// newUser.returnUrl=https://www.beadwallet.com/loanpage/html/Home/index.html?cid={0}&needLogin=1
			newUserReturnUrl = RegisterUtils.getReturnUrl(new Object[] { channel }, newUserReturnUrl); // 将渠道名称放入url中
			String token = CommUtils.getUUID(); // 生成token
			request.getSession().setAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN, token);
			SystemConstant.SESSION_APP_TOKEN.put(borrowerId, token);
			Cookie uidcookie = new Cookie("cookie_uuid", borrowerId); // 将borroerId放入cookie中
			uidcookie.setPath("/"); // 可在同一应用服务器内共享方法
			Cookie tokencookie = new Cookie("cookie_token", token); // 将token放入cookie中
			tokencookie.setPath("/"); // 可在同一应用服务器内共享方法
			AppResponseResult result = new AppResponseResult(); // 服务器响应app结果对象
			result.setCode("000");
			result.setMsg("登录成功");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("loginToken", token);

			Map<String, String> loginUser = new HashMap<String, String>();
			loginUser.put("id", borrowerId);
			loginUser.put("phone", phone);
			loginUser.put("password", password);
			loginUser.put("flag", flag);
			loginUser.put("authStep", authStep);
			loginUser.put("state", state);
			loginUser.put("channel", channelId);
			map.put("loginUser", JSONObject.toJSONString(loginUser));
			//
			result.setResult(map);
			Cookie userinfocookie = new Cookie("channel_cookie_user_info", JSONObject.toJSONString(result));
			userinfocookie.setPath("/");
			response.addCookie(uidcookie);
			response.addCookie(tokencookie);
			response.addCookie(userinfocookie); // 将token，borrowId，借款人信息放入响应序列
		} catch (Exception e) {
			logger.error(methodName + " occured exception:", e);
		}

		return "redirect:" + newUserReturnUrl;
	}

	/**
	 * 主动还款接口
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/repayment.do")
	public BaJieResp repayment(HttpServletRequest request, HttpServletResponse response) {
		ThreadLocalUtil.set("REPAYMENT");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		BaJieResp resp = new BaJieResp();
		String methodName = "AppBaJieController.repayment";
		logger.info(methodName + " start");

		try {
			logger.info("开始验证请求参数");
			String param = request.getParameter("param");

			if (StringUtils.isBlank(param)) {
				resp.setSuccess(false);
				resp.setErrorMsg("必要参数为空");
				logger.info(methodName + " end,param is null,resp=" + resp);
				return resp;
			}

			BajieParam req = JSONObject.parseObject(param, BajieParam.class); // 解析参数

			String data = req.getData();
			String sign = req.getSign();

			if (StringUtils.isBlank(req.getData())) {
				resp.setSuccess(false);
				resp.setErrorMsg("必要参数为空");
				logger.info(methodName + " end,data is null,resp=" + resp);
				return resp;
			}

			if (StringUtils.isBlank(req.getSign())) {
				resp.setSuccess(false);
				resp.setErrorMsg("必要参数为空");
				logger.info(methodName + " end,sign is null,resp=" + resp);
				return resp;
			}

			logger.info("开始验证签名");

			boolean check = SignUtils.verify(data, sign, DES_KEY, "UTF-8"); // 验证签名（解密）

			if (!check) {
				resp.setSuccess(false);
				resp.setErrorMsg("验签失败");
				logger.info(methodName + " end,sign is null,resp=" + resp);
				return resp;
			}

			data = MessageUtils.decrypt(data, DES_KEY); // 解密

			BajieRepaymentData repaymentBizData = JSONObject.parseObject(data, BajieRepaymentData.class);
			// 将data装换成对象
			ThreadLocalUtil.set("REPAYMENT-" + repaymentBizData.getOrderNo());
			logger.info("参数校验通过");
			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setThirdOrderNo(repaymentBizData.getOrderNo());
			bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);

			if (CommUtils.isNull(bwOrderRong)) { // 判断第三方的工单在数据库中是否为空
				resp.setSuccess(false);
				resp.setErrorMsg("系统异常");
				methodEnd(stopWatch, methodName, "工单为空", resp);
				return resp;
			}

			BwOrder bwOrder = findBwOrderByIdProxy(bwOrderRong.getOrderId());

			if (CommUtils.isNull(bwOrder)) { // 判断水象的工单是否为空
				resp.setSuccess(false);
				resp.setErrorMsg("系统异常");
				methodEnd(stopWatch, methodName, "工单为空", resp);
				return resp;
			}

			Long borrowerId = bwOrder.getBorrowerId();
			BwBankCard bwBankCard = findBwBankCardByBoorwerIdProxy(borrowerId);

			if (CommUtils.isNull(bwBankCard)) { // 判断借款银行卡信息是否存在
				resp.setSuccess(false);
				resp.setErrorMsg("系统异常");
				methodEnd(stopWatch, methodName, "银行卡信息为空", resp);
				return resp;
			}

			resp = commonRepay(bwOrder, bwBankCard, "repay");
		} catch (Exception e) {
			logger.error(methodName + " 异常", e);
			resp.setSuccess(false);
			resp.setErrorMsg("系统异常，请稍后再试");
		}

		methodEnd(stopWatch, methodName, "", resp);
		return resp;
	}

	private BaJieResp commonRepay(BwOrder bwOrder, BwBankCard bwBankCard, String type) throws Exception {
		return commonRepay(bwOrder, bwBankCard, null, type);
	}

	private BaJieResp commonRepay(BwOrder bwOrder, BwBankCard bwBankCard, String amount, String type) throws Exception {
		BaJieResp resp = new BaJieResp(); // 回应

		String orderId = String.valueOf(bwOrder.getId());

		if (RedisUtils.hexists(XUDAI, orderId)) { // 通过key和field判断是否有指定的value存在
			resp.setSuccess(false); // 判断是否在续贷中
			resp.setErrorMsg("系统异常");
			logger.info("此工单正在续贷中");
			return resp;
		}

		if (RedisUtils.hexists(SystemConstant.NOTIFY_BAOFU, orderId)) { // 判断是否在宝付支付中
			resp.setSuccess(false);
			resp.setErrorMsg("系统异常");
			logger.info("此工单正在宝付支付中");
			return resp;
		}

		if (RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId)) { // 判断是否在连连支付中
			resp.setSuccess(false);
			resp.setErrorMsg("系统异常");
			logger.info("此工单正在连连支付中");
			return resp;
		}

		Long statusId = bwOrder.getStatusId();// 工单状态
		logger.info("工单状态:" + statusId);
		if (!(statusId.intValue() == 9 || statusId.intValue() == 13)) { // 9:还款中 13：逾期中
			resp.setSuccess(false);
			resp.setErrorMsg("工单只有还款中或逾期中才可还款");
			logger.info("工单只有还款中或逾期中才可还款");
			return resp;
		}

		BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
		bwRepaymentPlan.setOrderId(bwOrder.getId());
		bwRepaymentPlan = findBwRepaymentPlanByAttrProxy(bwRepaymentPlan); // 根据还款计划Id查询还款计划
		if (CommUtils.isNull(bwRepaymentPlan)) {
			resp.setSuccess(false);
			resp.setErrorMsg("系统异常");
			logger.info("没有符合条件的还款计划");
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
				resp.setSuccess(false);
				resp.setErrorMsg("到期时间十天内方可续贷");
				logger.info("到期时间十天内方可续贷");
				return resp;
			}
		}

		// 判断该用户是否签约
		Long borrowerId = bwOrder.getBorrowerId();
		logger.info("开始调用连连签约查询接口,borrowerId=" + borrowerId + ",cardNo=" + bwBankCard.getCardNo());
		CardQueryResult cardQueryResult = LianLianPayService.cardBindQuery(borrowerId.toString(),
				bwBankCard.getCardNo());

		logger.info("结束调用连连签约查询接口");

		if (CommUtils.isNull(cardQueryResult)) { // 验证签约结果是否为空
			resp.setSuccess(false);
			resp.setErrorMsg("未签约");
			logger.info("调用连连签约查询接口返回结果为空");
			return resp;
		}

		if (!"0000".equals(cardQueryResult.getRet_code())) { // 结果代码是否为"0000"
			resp.setSuccess(false);
			resp.setErrorMsg(BajieUtils.convertLian2Msg(cardQueryResult.getRet_code()));
			logger.info("调用连连签约查询接口返回结果失败，ret_code != 0000");
			return resp;
		}

		List<Agreement> agreements = cardQueryResult.getAgreement_list();
		if (CommUtils.isNull(agreements)) { // 签约结果集（银行卡信息）是否为空
			resp.setSuccess(false);
			resp.setErrorMsg("未签约");
			logger.info("调用连连签约查询接口返回结果失败,Agreement_list is null");
			return resp;
		}

		String agreeNo = agreements.get(0).getNo_agree();

		logger.info("调用连连签约查询接口返回结果成功，连连支付协议号:" + agreeNo);

		List<RepaymentPlan> repays = new ArrayList<RepaymentPlan>();
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		// 设置实际支付金额
		if ("repay".equals(type)) {
			amount = String.valueOf(bwRepaymentPlan.getRealityRepayMoney()); // 实际还款金额
			BwOverdueRecord overdueRecord = new BwOverdueRecord(); // 还款计划
			overdueRecord.setOrderId(bwOrder.getId());
			overdueRecord = findBwOverdueRecordByAttrProxy(overdueRecord); // 根据id查询还款计划
			if (!CommUtils.isNull(overdueRecord)) {
				logger.info("有逾期记录,累加逾期金额");
				amount = new BigDecimal(amount).add(new BigDecimal(overdueRecord.getOverdueAccrualMoney()))
						.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
				// 还款金额=实际还款金额+逾期利息（四舍五入保存2位小数）
			}
			logger.info("amount=" + amount);
		}

		RepaymentPlan repay = new RepaymentPlan();
		repay.setAmount(amount);
		repay.setDate(dateFormat2.format(bwRepaymentPlan.getRepayTime()));
		repays.add(repay);

		BwBorrower bwBorrower = findBwBorrowerByIdProxy(bwOrder.getBorrowerId());
		// 根据id查询借款人
		// 还款人信息
		SignalLess signalLess = new SignalLess();
		signalLess.setAcct_name(bwBorrower.getName());
		// signalLess.setApp_request(app_request);
		signalLess.setCard_no(bwBankCard.getCardNo());
		signalLess.setId_no(bwBorrower.getIdCard());
		signalLess.setNo_agree(agreeNo);
		signalLess.setUser_id(borrowerId.toString());

		logger.info("开始调用连连授权接口" + ",orderNo=" + bwOrder.getOrderNo() + ",repays=" + JSONObject.toJSONString(repays));
		PlanResult planResult = LianLianPayService.sigalAccreditPay(signalLess, bwOrder.getOrderNo(), repays);
		logger.info("结束调用连连授权接口，planResult=" + JSONObject.toJSONString(planResult));

		if (CommUtils.isNull(planResult)) {
			resp.setSuccess(false);
			resp.setErrorMsg("支付授权失败");
			logger.info("调用连连授权接口返回结果为空");
			return resp;
		}

		if (!"0000".equals(planResult.getRet_code())) {
			resp.setSuccess(false);
			resp.setErrorMsg(BajieUtils.convertLian2Msg(planResult.getRet_code()));
			logger.info("调用连连授权接口返回结果失败,ret_code != 0000");
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
		// repayRequest.setMoney_order(amount);
		// 测试
		repayRequest.setMoney_order("0.01");
		repayRequest.setSchedule_repayment_date(repays.get(0).getDate());
		repayRequest.setRepayment_no(bwOrder.getOrderNo());
		repayRequest.setUser_info_bind_phone(bwBorrower.getPhone());
		repayRequest.setUser_info_dt_register(dateFormat.format(bwBorrower.getCreateTime()));
		repayRequest.setUser_info_full_name(bwBorrower.getName());
		repayRequest.setUser_info_id_no(bwBorrower.getIdCard());
		repayRequest.setNotify_url(SystemConstant.NOTIRY_URL + "/app/bajie/repaymentNotify.do");

		// 存入连连redis中，有效时间15分钟
		if (!RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId())) {
			RedisUtils.setex(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId(), bwOrder.getId().toString(), 900);
			logger.info("存redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId().toString() + "]");
			// 设置key value并制定这个键值的有效期
		}

		logger.info("开始调用连连支付接口");
		RepaymentResult repaymentResult = LianLianPayService.bankRepay(repayRequest);
		logger.info("结束调用连连支付接口");

		if (CommUtils.isNull(repaymentResult)) { // 调用连连支付接口失败
			resp.setSuccess(false);
			resp.setErrorMsg("支付失败");
			logger.info("调用连连支付接口返回结果为空");
			return resp;
		}

		if (!"0000".equals(repaymentResult.getRet_code()) && !"1003".equals(repaymentResult.getRet_code())) {
			resp.setSuccess(false);
			resp.setErrorMsg(BajieUtils.convertLian2Msg(repaymentResult.getRet_code()));
			// 删除连连REDIS
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId() + "]");
			RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId());
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId() + "]成功");
			logger.info("调用连连支付接口返回结果失败,ret_code != 0000");
			return resp;
		}

		logger.info("调用连连支付接口成功");
		resp.setSuccess(true);
		resp.setErrorMsg("支付成功");
		return resp;
	}

	/**
	 * 还款回调
	 * 
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "/repaymentNotify.do")
	public NotifyNotice repaymentNotify(HttpServletRequest request) {
		ThreadLocalUtil.set("REPAYNOTIFY");
		String methodName = "AppBaJieController.repaymentNotify";
		logger.info(methodName + " start");
		NotifyNotice notice = new NotifyNotice();

		try {
			NotifyResult notifyResult = getNotifyResult(request); // 还款通知
			logger.info("notifyResult=" + notifyResult.getAcct_name());

			if (CommUtils.isNull(notifyResult)) {
				notice.setRet_code("101");
				notice.setRet_msg("异步还款通知为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			if (StringUtils.isBlank(notifyResult.getSign())) { // 判断签名是否为空
				notice.setRet_code("101");
				notice.setRet_msg("异步还款通知签名为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			if (StringUtils.isBlank(notifyResult.getNo_order())) { // 判断工单是否为空
				notice.setRet_code("101");
				notice.setRet_msg("工单id为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			logger.info("开始验证签名...");

			boolean checkSign = checkLianLianSign(notifyResult);
			if (!checkSign) { // 判断签名是否通过
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
				} catch (Exception e) {
					logger.error("调用八戒网状态反馈接口异常", e);
				}
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setOrderId(Long.parseLong(orderId));
			bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);
			if (CommUtils.isNull(bwOrderRong)) { // 判断工单是否存在
				notice.setRet_code("101");
				notice.setRet_msg("工单不存在");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			ThreadLocalUtil.set("REPAYNOTIFY-" + bwOrderRong.getThirdOrderNo());
			logger.info("参数校验通过");

			BwOrder order = findBwOrderByIdProxy(Long.parseLong(orderId)); // 根据id查询工单信息

			if (CommUtils.isNull(order)) {
				notice.setRet_code("101");
				notice.setRet_msg("工单不存在");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}

			if (order.getStatusId().intValue() == 6) { // 6：结束
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
			BwBankCard card = findBwBankCardByBoorwerIdProxy(borrower.getId());
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

			// 删除连连REDIS
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + order.getId() + "]");
			RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + order.getId()); // 删除redis中的支付信息
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + order.getId() + "]成功");

			// 修改订单状态
			bwRepaymentService.updateOrderStatus(order.getId());

			RedisUtils.lpush(NOTIFY_ORDER_STATE_REDIS, String.valueOf(order.getId()));
			// 在redis中根据key（NOTIFY_ORDER_STATE_REDIS），将工单id放入key值的开头
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
	 * @param notifyResult
	 * @return 检查签名是否通过
	 */
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

	/**
	 * @param notifyResult 付款通知
	 * @param card 银行卡号
	 * @param borrower 借款人信息
	 * @param order 工单信息
	 * @return
	 */
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
	 * @param stopWatch 方法运行的时间
	 * @param methodName 方法的名字
	 * @param message 方法结束的信息
	 * @param resp 响应
	 */
	private void methodEnd(StopWatch stopWatch, String methodName, String message, BaJieResp resp) {
		stopWatch.stop();
		logger.info(methodName + " end,costTime=" + stopWatch.getTotalTimeMillis() + "," + message + ",resp=" + resp);
		ThreadLocalUtil.remove();
	}

	/**
	 * @param bwOrderRong
	 * @return 根据id查询推送的工单信息
	 */
	private BwOrderRong findBwOrderRongByAttrProxy(BwOrderRong bwOrderRong) {
		logger.info("开始查询渠道工单,bwOrderRong=" + JSONObject.toJSONString(bwOrderRong.getOrderId()));
		bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
		logger.info("结束查询渠道工单,bwOrderRongId=" + JSONObject.toJSONString(bwOrderRong.getId()));
		return bwOrderRong;
	}

	/**
	 * @param borrowerId 借款人id
	 * @return 根据借款人ID查询银行卡信息
	 */
	private BwBankCard findBwBankCardByBoorwerIdProxy(Long borrowerId) {
		logger.info("开始查询银行卡信息,borrowerId=" + borrowerId);
		BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(borrowerId);
		logger.info("结束查询银行卡信息,bwBankCardId=" + JSONObject.toJSONString(bwBankCard.getId()));
		return bwBankCard;
	}

	/**
	 * @param borrowerId 借款人id
	 * @return 根据借款人id查询借款人信息
	 */
	private BwBorrower findBwBorrowerByIdProxy(Long borrowerId) {
		logger.info("开始查询借款人信息,borrowerId=" + borrowerId);
		BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
		logger.info("结束查询借款人信息,borrowerId=" + borrowerId);
		return bwBorrower;
	}

	/**
	 * @param orderId 工单id
	 * @return 根据工单id查询工单
	 */
	private BwOrder findBwOrderByIdProxy(Long orderId) {
		logger.info("开始查询工单,orderId=" + orderId);
		BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
		logger.info("结束查询工单,orderId=" + bwOrder.getId());
		return bwOrder;
	}

	/**
	 * @param bwRepaymentPlan 还款计划
	 * @return 根据还款计划查询还款计划
	 */
	private BwRepaymentPlan findBwRepaymentPlanByAttrProxy(BwRepaymentPlan bwRepaymentPlan) {
		logger.info("开始查询还款计划,bwRepaymentPlanId=" + JSONObject.toJSONString(bwRepaymentPlan.getId()));
		bwRepaymentPlan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(bwRepaymentPlan.getOrderId());
		logger.info("结束查询还款计划,bwRepaymentPlanId=" + JSONObject.toJSONString(bwRepaymentPlan.getId()));
		return bwRepaymentPlan;
	}

	/**
	 * @param overdueRecord 逾期记录
	 * @return 根据逾期记录查询逾期记录
	 */
	private BwOverdueRecord findBwOverdueRecordByAttrProxy(BwOverdueRecord overdueRecord) {
		logger.info("开始查询逾期记录,overdueRecordId=" + JSONObject.toJSONString(overdueRecord.getId()));
		overdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(overdueRecord);
		logger.info("结束查询逾期记录,overdueRecordId=" + JSONObject.toJSONString(overdueRecord.getId()));
		return overdueRecord;
	}
}