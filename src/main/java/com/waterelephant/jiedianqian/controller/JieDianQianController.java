package com.waterelephant.jiedianqian.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.entity.lianlian.Agreement;
import com.beadwallet.entity.lianlian.CardQueryResult;
import com.beadwallet.entity.lianlian.PlanResult;
import com.beadwallet.entity.lianlian.RepayRequest;
import com.beadwallet.entity.lianlian.RepaymentPlan;
import com.beadwallet.entity.lianlian.RepaymentResult;
import com.beadwallet.entity.lianlian.SignalLess;
import com.beadwallet.servcie.LianLianPayService;
import com.beadwallet.service.sms.dto.MessageDto;
import com.waterelephant.channel.service.ProductService;
import com.waterelephant.dto.PaymentRespDto;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.jiedianqian.entity.JieDianQianResponse;
import com.waterelephant.jiedianqian.entity.RepayResponse;
import com.waterelephant.jiedianqian.service.JieDianQianService;
import com.waterelephant.jiedianqian.util.JieDianQianContext;
import com.waterelephant.jiedianqian.util.JieDianQianLogUtil;
import com.waterelephant.jiedianqian.util.JieDianQianUtils;
import com.waterelephant.jiedianqian.util.RSAUtil;
import com.waterelephant.loanwallet.utils.LoanWalletUtils;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.service.impl.BwOrderService;
import com.waterelephant.service.impl.BwRepaymentPlanService;
import com.waterelephant.third.entity.ThirdResponse;
import com.waterelephant.third.service.ThirdCommonService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.GenerateSerialNumber;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

/**
 * 
 * 
 * Module:
 * 
 * JieDianQianController.java
 * 
 * @author 张博
 * @since JDK 1.8
 * @version 1.0
 * @description: <借点钱>
 */

@Controller
@RequestMapping("/app/jiedianqian")
public class JieDianQianController {

	private JieDianQianLogUtil logger = new JieDianQianLogUtil(JieDianQianController.class);

	@Autowired
	private JieDianQianService jieDianQianService;

	@Autowired
	private BwOrderService bwOrderService;

	@Autowired
	private BwRepaymentPlanService bwRepaymentPlanService;

	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;

	@Autowired
	private IBwBankCardService bwBankCardService;

	@Autowired
	private BwBorrowerService borrowerService;

	@Autowired
	private ThirdCommonService thirdCommonService;

	@Autowired
	private BwOrderRongService bwOrderRongService;


	private static String JIEDIANQIAN_XUDAI = "xudai:order_id";
	private static String partnerPrivateKey = JieDianQianContext.get("priKey"); // 我方私钥
	private static String jdqPublicKey = JieDianQianContext.get("jdqPubKey"); // 借点钱1024位公钥
	private static String channelCode = JieDianQianContext.get("channelCode");// 渠道编码
	private static String bindCardUrl = JieDianQianContext.get("bindCardUrl");// 绑卡url

	/**
	 * 2.1 检测用户接口
	 */
	@RequestMapping("/check-user.do")
	@ResponseBody
	public JieDianQianResponse checkUser(@RequestBody JSONObject json) {
		JieDianQianResponse jieDianQianResponse = new JieDianQianResponse();
		String sessionId = DateUtil.getSessionId();

		// 第一步：验证请求中参数
		try {
			logger.info(sessionId + "  进入用户检验接口,开始接收参数");
			String check = JieDianQianUtils.checkOrderPush(json);

			if (StringUtils.isNotBlank(check)) { // 判断是否为空
				jieDianQianResponse.setCode(-2); // 状态码
				jieDianQianResponse.setDesc(check); // 异常信息
				logger.info(sessionId + " 结束接收订单信息接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
				return jieDianQianResponse; // 响应pojo
			}

			String data = json.getString("data");
			String sign = json.getString("sign");

			String desData = RSAUtil.buildRSADecryptByPrivateKey(data, partnerPrivateKey); // 解密

			boolean flag = RSAUtil.buildRSAverifyByPublicKey(desData, jdqPublicKey, sign); // 验签

			if (!flag) {
				logger.info(sessionId + " 验证签名失败");
				jieDianQianResponse.setCode(-2);
				jieDianQianResponse.setDesc("验证签名失败");
				return jieDianQianResponse;
			}

			JSONObject parseObject = JSONObject.parseObject(desData); // 请求的参数是否为空？

			String checkData = JieDianQianUtils.checkBaseData(parseObject);

			if (StringUtils.isNotBlank(checkData)) { // 判断是否为空
				jieDianQianResponse.setCode(-2); // 状态码
				jieDianQianResponse.setDesc(checkData); // 异常信息
				logger.info(sessionId + " 结束存量用户检验接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
				return jieDianQianResponse; // 响应pojo
			}

			// 第二步:取参数
			// String name = new String(request.getParameter("name").getBytes("ISO-8859-1"), "utf-8"); // 姓名 测试转码
			String name = parseObject.getString("user_name"); // 姓名
			String phone = parseObject.getString("phone"); // 手机号码
			String id_number = parseObject.getString("id_number"); // 身份证

			// 第三步：用户检验
			jieDianQianResponse = jieDianQianService.checkUserInfo(sessionId, name, phone.replace("****", "%"),
					id_number.replace("****", "%"));

		} catch (Exception e) {
			logger.error(sessionId + "  执行用户检验接口异常", e);
			jieDianQianResponse.setCode(-2);
			jieDianQianResponse.setDesc("系统异常，请稍后再试");
		}

		logger.info(sessionId + "  结束存量用户检验接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
		return jieDianQianResponse;
	}

	/**
	 * 2.5 订单状态查询接口
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/order-info.do")
	public JieDianQianResponse queryOrderInfomation(@RequestBody JSONObject json) {
		JieDianQianResponse jieDianQianResponse = new JieDianQianResponse();
		String sessionId = DateUtil.getSessionId();
		//logger.info(sessionId + " 开始进入订单查询接口方法");

		try {
			Map<String, Object> result = new HashMap<String, Object>();
			//logger.info(sessionId + " 开始验证请求参数");
			// 第一步：效验
			String check = JieDianQianUtils.checkOrderPush(json);

			if (StringUtils.isNotBlank(check)) { // 判断是否为空
				jieDianQianResponse.setCode(-1); // 状态码
				jieDianQianResponse.setDesc(check); // 异常信息
				logger.info(sessionId + " 结束接收订单信息接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
				return jieDianQianResponse; // 响应pojo
			}

			String data = json.getString("data"); // 加密数据
			String sign = json.getString("sign"); // 签名串

			String desData = RSAUtil.buildRSADecryptByPrivateKey(data, partnerPrivateKey); // 解密

			boolean flag = RSAUtil.buildRSAverifyByPublicKey(desData, jdqPublicKey, sign); // 验签

			if (!flag) {
				logger.info(sessionId + " 验证签名失败");
				jieDianQianResponse.setCode(-1);
				jieDianQianResponse.setDesc("验证签名失败");
				return jieDianQianResponse;
			}

			JSONObject parseObject = JSONObject.parseObject(desData); // 请求的参数是否为空？
			//logger.info("请求参数：" + parseObject.toString());

			String checkData = JieDianQianUtils.checkOrderId(parseObject);

			if (StringUtils.isNotBlank(checkData)) { // 判断是否为空
				jieDianQianResponse.setCode(-1); // 状态码
				jieDianQianResponse.setDesc(checkData); // 异常信息
				logger.info(sessionId + " 结束订单查询接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
				return jieDianQianResponse; // 响应pojo
			}

			String orderId = parseObject.getString("order_id"); // 获取参数

			BwOrder bwOrder = new BwOrder();
			bwOrder.setOrderNo(orderId);
			bwOrder = this.bwOrderService.findBwOrderByAttr(bwOrder);

			if (CommUtils.isNull(bwOrder)) { // 如果没查到,返回
				jieDianQianResponse.setCode(-1);
				jieDianQianResponse.setDesc("没有查询到订单信息");
				//logger.info(sessionId + " 查询订单信息，返回结果：" + JSON.toJSONString(bwOrder));
				return jieDianQianResponse;
			}
			
			
			
			//获取数据
			 result=jieDianQianService.queryOrderInfo(sessionId,bwOrder);
			
			//验证返回数据，为空返回订单查询信息为空
			
			if (CommUtils.isNull(result)) {
				logger.info("订单"+orderId+"状态+"+result+"信息为空");
				jieDianQianResponse.setCode(-1);
				jieDianQianResponse.setData(result);
				jieDianQianResponse.setDesc("该订单"+orderId+"状态信息"+result+"为空");
				
			}else{
				//成功接收数据
				jieDianQianResponse.setCode(0);
				jieDianQianResponse.setData(result);
				jieDianQianResponse.setDesc("订单"+orderId+"查询成功");
			}
			

		} catch (Exception e) {
			logger.error(sessionId + " 订单状态查询接口异常", e);
			jieDianQianResponse.setCode(-1);
			jieDianQianResponse.setDesc("接口调用异常，请稍后再试");
		}

		logger.info(sessionId + " 结束订单状态查询接口方法，返回结果：" + JSON.toJSONString(jieDianQianResponse));
		return jieDianQianResponse;
	}

	/**
	 * 2.2.接收订单信息接口
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/create-order.do")
	public JieDianQianResponse getOrderInfo(@RequestBody JSONObject json) {
		String sessionId = DateUtil.getSessionId();
		logger.info(sessionId + " 进入接收订单信息接口");
		JieDianQianResponse jieDianQianResponse = new JieDianQianResponse();

		try {
			logger.info(sessionId + " 开始验证请求参数");
			String check = JieDianQianUtils.checkOrderPush(json);

			if (StringUtils.isNotBlank(check)) { // 判断是否为空
				jieDianQianResponse.setCode(-2); // 状态码
				jieDianQianResponse.setDesc(check); // 异常信息
				logger.info(sessionId + " 结束接收订单信息接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
				return jieDianQianResponse; // 响应pojo
			}

			String data = json.getString("data"); // 加密数据
			String sign = json.getString("sign"); // 签名串
			//logger.info("data:" + data + "  sign:" + sign);

			String desData = RSAUtil.buildRSADecryptByPrivateKey(data, partnerPrivateKey); // 解密

			boolean flag = RSAUtil.buildRSAverifyByPublicKey(desData, jdqPublicKey, sign); // 验签

			if (!flag) {
				logger.info(sessionId + " 验证签名失败");
				jieDianQianResponse.setCode(-2);
				jieDianQianResponse.setDesc("验证签名失败");
				return jieDianQianResponse;
			}
			//RedisUtils.rpush(USER_INFO_REDIS, desData);

			jieDianQianResponse = jieDianQianService.saveBwOrder(sessionId,desData);
			

		} catch (Exception e) {
			logger.error(sessionId + " 接收订单信息接口异常", e);
			jieDianQianResponse.setCode(-2);
			jieDianQianResponse.setDesc("接口调用异常，请稍后再试");
		}

		logger.info(sessionId + "结束订单信息接收接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
		return jieDianQianResponse;

	}

	/**
	 * 2.3.提现试算接口
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/calculate.do")
	@ResponseBody
	public JieDianQianResponse calculateMethod(@RequestBody JSONObject json) {
		JieDianQianResponse jieDianQianResponse = new JieDianQianResponse();
		String sessionId = DateUtil.getSessionId();
		logger.info(sessionId + " 开始提现试算接口方法");
		try {
			logger.info(sessionId + " 开始验证请求参数");
			// 第一步：效验
			String check = JieDianQianUtils.checkOrderPush(json);

			if (StringUtils.isNotBlank(check)) { // 判断是否为空
				jieDianQianResponse.setCode(-1); // 状态码
				jieDianQianResponse.setDesc(check); // 异常信息
				logger.info(sessionId + " 结束接收订单信息接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
				return jieDianQianResponse; // 响应pojo
			}

			String data = json.getString("data"); // 加密数据
			String sign = json.getString("sign"); // 签名串

			String desData = RSAUtil.buildRSADecryptByPrivateKey(data, partnerPrivateKey); // 解密

			boolean flag = RSAUtil.buildRSAverifyByPublicKey(desData, jdqPublicKey, sign); // 验签

			if (!flag) {
				logger.info(sessionId + " 验证签名失败");
				jieDianQianResponse.setCode(-1);
				jieDianQianResponse.setDesc("验证签名失败");
				return jieDianQianResponse;
			}

			JSONObject parseObject = JSONObject.parseObject(desData); // 请求的参数是否为空？
			String checkData = JieDianQianUtils.checkCalculateData(parseObject);

			if (StringUtils.isNotBlank(checkData)) { // 判断是否为空
				jieDianQianResponse.setCode(-1); // 状态码
				jieDianQianResponse.setDesc(checkData); // 异常信息
				logger.info(sessionId + " 结束提现试算接口方法，返回结果：" + JSON.toJSONString(jieDianQianResponse));
				return jieDianQianResponse; // 响应pojo
			}

			// 第二步：取参数
			String orderNo = parseObject.getString("order_id");
			String amonut = parseObject.getString("amount");

			// 第三步：计算
			jieDianQianResponse = jieDianQianService.loanCalculate(sessionId, amonut,orderNo);

		} catch (Exception e) {
			logger.error(sessionId + "执行 贷款试算期接口异常", e);
			jieDianQianResponse.setCode(-1);
			jieDianQianResponse.setDesc("接口调用异常，请稍后再试");
		}
		logger.info(sessionId + " 结束提现试算接口方法，返回结果：" + JSON.toJSONString(jieDianQianResponse));
		return jieDianQianResponse;
	}

	/**
	 * 2.4. 确认要款（提现）接口
	 * 
	 * @param jsonObject
	 * @return
	 */
	@RequestMapping("/confirm.do")
	@ResponseBody
	public JieDianQianResponse confirm(@RequestBody JSONObject json) {
		JieDianQianResponse jieDianQianResponse = new JieDianQianResponse();
		String sessionId = DateUtil.getSessionId();
		String methodName = "JieDianQianController.confirm";
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		logger.info(sessionId + "   {" + methodName + " }start");
		try {
			logger.info(sessionId + " 开始验证请求参数");

			// 第一步：效验
			String check = JieDianQianUtils.checkOrderPush(json);

			if (StringUtils.isNotBlank(check)) { // 判断是否为空
				jieDianQianResponse.setCode(-2); // 状态码
				jieDianQianResponse.setDesc(check); // 异常信息
				logger.info(sessionId + " 结束接收订单信息接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
				return jieDianQianResponse; // 响应pojo
			}

			String data = json.getString("data"); // 加密数据
			String sign = json.getString("sign"); // 签名串

			String desData = RSAUtil.buildRSADecryptByPrivateKey(data, partnerPrivateKey); // 解密

			boolean flag = RSAUtil.buildRSAverifyByPublicKey(desData, jdqPublicKey, sign); // 验签

			if (!flag) {
				logger.info(sessionId + " 验证签名失败");
				jieDianQianResponse.setCode(-2);
				jieDianQianResponse.setDesc("验证签名失败");
				return jieDianQianResponse;
			}

			JSONObject parseObject = JSONObject.parseObject(desData); // 请求的参数是否为空？

			String checkData = JieDianQianUtils.checkConfirmData(parseObject);

			if (StringUtils.isNotBlank(checkData)) { // 判断是否为空
				jieDianQianResponse.setCode(-2); // 状态码
				jieDianQianResponse.setDesc(checkData); // 异常信息
				logger.info(sessionId + " 结束确认要款（提现）接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
				return jieDianQianResponse; // 响应pojo
			}
			//

			// 获取参数
			String orderId = parseObject.getString("order_id"); // 订单id
			String loan_amount = parseObject.getString("loan_amount"); // 贷款金额
			String loan_periods = parseObject.getString("loan_periods"); // 贷款期数


			BwOrder bwOrder = new BwOrder(); // 订单
			bwOrder.setOrderNo(orderId);
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder); // 通过第三方传递的订单号,查询我方订单

			if (CommUtils.isNull(bwOrder)) { // 如果没查到,返回
				jieDianQianResponse.setCode(-2);
				jieDianQianResponse.setDesc("没有查询到订单信息");
				logger.info(sessionId + " 查询订单信息，返回结果：" + JSON.toJSONString(bwOrder));
				return jieDianQianResponse;
			}

			Long borrowerId = bwOrder.getBorrowerId();

			// 判断审核是否通过，是否能借款逻辑

			// 第一步：比对金额

			
			if (CommUtils.isNull(bwOrder.getBorrowAmount())) {
				logger.info("借贷金额为：" + String.valueOf(bwOrder.getBorrowAmount()));
				jieDianQianResponse.setCode(-2);
				jieDianQianResponse.setDesc("借贷金额为空");
				methodEnd(stopWatch, methodName, "借贷金额为空", jieDianQianResponse);
				return jieDianQianResponse;
			}

			double loan_amountBD = Double.parseDouble(loan_amount);
			double borrow_amountBD = bwOrder.getBorrowAmount().doubleValue();

			if (loan_amountBD - borrow_amountBD > 0) {
				logger.info("开始验证金额：" + loan_amountBD + "," + borrow_amountBD);
				jieDianQianResponse.setCode(-2);
				jieDianQianResponse.setDesc("贷款金额超出范围");
				methodEnd(stopWatch, methodName, "贷款金额超出范围", jieDianQianResponse);
				return jieDianQianResponse;
			}

			// 第三步：判断订单状态是否是待签约（直接调用接口）
			Integer channelId = bwOrder.getChannel();

			// 获取订单id，查询第三方订单编号
			String order_id = String.valueOf(bwOrder.getId());
			String thirdOrderNo = bwOrderRongService.findThirdOrderNoByOrderId(order_id);
			if(!String.valueOf(bwOrder.getStatusId()).equals("4")){
				logger.info("请检查用户的状态");
				jieDianQianResponse.setCode(-2);
				jieDianQianResponse.setDesc("请检查用户的订单状态");
				return jieDianQianResponse;
			}

			ThirdResponse tResponse = thirdCommonService.updateSignContract(thirdOrderNo, channelId);// 获取调用签约接口的返回值
			if (tResponse.getCode() == 200) {
				jieDianQianResponse.setCode(0);
				jieDianQianResponse.setDesc("操作成功");
				
			} else {
				jieDianQianResponse.setCode(-2);
				jieDianQianResponse.setDesc("操作失败");
				
			}

		} catch (Exception e) {
			logger.error(sessionId + "{  " + methodName + "  } 异常", e);
			jieDianQianResponse.setCode(-2);
			jieDianQianResponse.setDesc("系统异常，请稍后再试");
		}

		return jieDianQianResponse;
	}

	/**
	 * 2.8. 提现跳转合作方页面绑卡url
	 * 
	 * @param json
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/bind-card.do")
	public JieDianQianResponse BindCard(@RequestBody JSONObject json) {
		JieDianQianResponse jieDianQianResponse = new JieDianQianResponse();
		String sessionId = DateUtil.getSessionId();
		Map<String, String> hm = new HashMap<String, String>();
		logger.info(sessionId + " 开始提现跳转合作方页面绑卡接口方法");
		try {
			logger.info(sessionId + " 开始验证请求参数");
			// 第一步：效验
			String check = JieDianQianUtils.checkOrderPush(json);

			if (StringUtils.isNotBlank(check)) { // 判断是否为空
				jieDianQianResponse.setCode(-1); // 状态码
				jieDianQianResponse.setDesc(check); // 异常信息
				logger.info(sessionId + " 结束提现跳转合作方页面绑卡接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
				return jieDianQianResponse; // 响应pojo
			}

			String data = json.getString("data"); // 加密数据
			String sign = json.getString("sign"); // 签名串

			String desData = RSAUtil.buildRSADecryptByPrivateKey(data, partnerPrivateKey); // 解密

			boolean flag = RSAUtil.buildRSAverifyByPublicKey(desData, jdqPublicKey, sign); // 验签

			if (!flag) {
				logger.info(sessionId + " 验证签名失败");
				jieDianQianResponse.setCode(-1);
				jieDianQianResponse.setDesc("验证签名失败");
				return jieDianQianResponse;
			}

			JSONObject parseObject = JSONObject.parseObject(desData); // 请求的参数是否为空？
			String checkData = JieDianQianUtils.checkBindCardData(parseObject);

			if (StringUtils.isNotBlank(checkData)) { // 判断是否为空
				jieDianQianResponse.setCode(-1); // 状态码
				jieDianQianResponse.setDesc(checkData); // 异常信息
				logger.info(sessionId + " 结束提现跳转合作方页面绑卡接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
				return jieDianQianResponse; // 响应pojo
			}

			String orderId = parseObject.getString("order_id");
			String successReturnUrl = parseObject.getString("successReturnUrl");

			// String errorReturnUrl = parseObject.getString("errorReturnUrl");

			// String orderId = json.getString("order_id");
			// String successReturnUrl = json.getString("successReturnUrl");

			// 通过id查询绑卡url需要的信息

			BwOrder bwOrder = new BwOrder();
			bwOrder.setOrderNo(orderId);
			
			
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
			if(CommUtils.isNull(bwOrder)){
				logger.info("找不到对应的订单...");
				jieDianQianResponse.setCode(-1);
				jieDianQianResponse.setDesc("找不到对应的订单...");
				return jieDianQianResponse;
				
			}
			
			String orderNo = bwOrder.getOrderNo();
			Integer channelId = bwOrder.getChannel();
			
			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
			if (!CommUtils.isNull(bwBankCard) && 2 == bwBankCard.getSignStatus()) {
				logger.info(sessionId + "用户绑卡信息已存在，绑卡成功！");
				hm.put("bind_card_url", successReturnUrl);
				jieDianQianResponse.setCode(0);
				jieDianQianResponse.setData(hm);
				jieDianQianResponse.setDesc("请求成功");
				
			}else{
				// 调用绑卡url接口
				RedisUtils.hset("third:bindCard:successReturnUrl:" + channelId, "orderNO_" + orderNo, successReturnUrl);
				hm.put("bind_card_url",bindCardUrl+"orderNO="+ orderNo + "&channelCode=" + channelCode);
				jieDianQianResponse.setCode(0);
				jieDianQianResponse.setData(hm);
				jieDianQianResponse.setDesc("请求成功");
			}

			
		} catch (Exception e) {
			logger.error(sessionId + "执行提现跳转合作方页面绑卡接口", e);
			jieDianQianResponse.setCode(-1);
			jieDianQianResponse.setDesc("接口调用异常，请稍后再试");
		}

		return jieDianQianResponse;
	}

	/**
	 * 2.7还款接口（暂时未开发）
	 * 
	 * @param jsonObject
	 * @return
	 */
	@RequestMapping("/repayment.do")
	@ResponseBody
	public JieDianQianResponse repayment(@RequestBody JSONObject jsonObject) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		JieDianQianResponse jieDianQianResponse = new JieDianQianResponse();
		String sessionId = DateUtil.getSessionId();
		String methodName = "JieDianQianController.repayment";
		logger.info(sessionId + "   {" + methodName + " }start");
		try {
			logger.info(sessionId + " 开始验证请求参数");
			// 第一步：效验
			String check = JieDianQianUtils.checkOrderPush(jsonObject);

			if (StringUtils.isNotBlank(check)) { // 判断是否为空
				jieDianQianResponse.setCode(-1); // 状态码
				jieDianQianResponse.setDesc(check); // 异常信息
				logger.info(sessionId + " 结束接收订单信息接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
				methodEnd(stopWatch, methodName, check, jieDianQianResponse);
				return jieDianQianResponse; // 响应pojo
			}

			String data = jsonObject.getString("data"); // 加密数据
			String sign = jsonObject.getString("sign"); // 签名串

			String desData = RSAUtil.buildRSADecryptByPrivateKey(data, partnerPrivateKey); // 解密

			boolean flag = RSAUtil.buildRSAverifyByPublicKey(desData, jdqPublicKey, sign); // 验签

			if (!flag) {
				logger.info(sessionId + " 验证签名失败");
				jieDianQianResponse.setCode(-1);
				jieDianQianResponse.setDesc("验证签名失败");
				return jieDianQianResponse;
			}

			RepayResponse repayResponse = JSONObject.parseObject(desData, RepayResponse.class);
			Map<String, String> hm = new HashMap<String, String>();

			if (CommUtils.isNull(repayResponse)) {
				jieDianQianResponse.setCode(-1);
				jieDianQianResponse.setData(hm.put("repaymentUrl", repayResponse.getErrorReturnUrl()));
				jieDianQianResponse.setDesc("请求参数异常");
				methodEnd(stopWatch, methodName, "repayResponse is null", jieDianQianResponse);
				return jieDianQianResponse;
			}

			logger.set("REPAYMENT-" + repayResponse.getOrder_id());
			logger.info("请求参数验证通过");

			BwOrder bwOrder = new BwOrder();
			bwOrder.setOrderNo(repayResponse.getOrder_id());
			BwOrder bw = bwOrderService.findBwOrderByAttr(bwOrder);

			if (CommUtils.isNull(bw)) {
				jieDianQianResponse.setCode(-1);
				jieDianQianResponse.setDesc("系统异常");
				jieDianQianResponse.setData(hm.put("repaymentUrl", repayResponse.getErrorReturnUrl()));
				methodEnd(stopWatch, methodName, "工单为空", jieDianQianResponse);
				return jieDianQianResponse;
			}

			Long borrowerId = bw.getBorrowerId();
			BwBankCard bwBankCard = findBwBankCardByBoorwerIdProxy(borrowerId);

			if (CommUtils.isNull(bwBankCard)) {
				jieDianQianResponse.setCode(-1);
				jieDianQianResponse.setDesc("系统异常");
				jieDianQianResponse.setData(hm.put("repaymentUrl", repayResponse.getErrorReturnUrl()));
				methodEnd(stopWatch, methodName, "银行卡信息为空", jieDianQianResponse);
				return jieDianQianResponse;
			}

			jieDianQianResponse = commonRepay(bw, bwBankCard, "repay");

			if ("200".equals(jieDianQianResponse.getCode())) {
				RedisUtils.lpush("notify:orderState", String.valueOf(bw.getId()));
			}

		} catch (Exception e) {
			logger.error(methodName + " 异常", e);
			jieDianQianResponse.setCode(-1);
			jieDianQianResponse.setDesc("系统异常，请稍后再试");
		}

		methodEnd(stopWatch, methodName, "", jieDianQianResponse);
		return jieDianQianResponse;
	}

	/**
	 * 2.9. 查询银行卡信息 在合作方页面绑卡时，需要合作方提供银行卡信息查询接口
	 * 
	 * @param json
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/card-info.do")
	public JieDianQianResponse cardInfo(@RequestBody JSONObject json) {
		JieDianQianResponse jieDianQianResponse = new JieDianQianResponse();
		String sessionId = DateUtil.getSessionId();
		Map<String, String> hm = new HashMap<String, String>();
		logger.info(sessionId + " 开始查询银行卡信息接口方法");
		try {
			logger.info(sessionId + " 开始验证请求参数");
			// 第一步：效验
			String check = JieDianQianUtils.checkOrderPush(json);

			if (StringUtils.isNotBlank(check)) { // 判断是否为空
				jieDianQianResponse.setCode(-1); // 状态码
				jieDianQianResponse.setDesc(check); // 异常信息
				logger.info(sessionId + " 结束查询银行卡信息接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
				return jieDianQianResponse; // 响应pojo
			}

			String data = json.getString("data"); // 加密数据
			String sign = json.getString("sign"); // 签名串

			String desData = RSAUtil.buildRSADecryptByPrivateKey(data, partnerPrivateKey); // 解密

			boolean flag = RSAUtil.buildRSAverifyByPublicKey(desData, jdqPublicKey, sign); // 验签

			if (!flag) {
				logger.info(sessionId + " 验证签名失败");
				jieDianQianResponse.setCode(-1);
				jieDianQianResponse.setDesc("验证签名失败");
				return jieDianQianResponse;
			}
			JSONObject parseObject = JSONObject.parseObject(desData); // 请求的参数是否为空？
			String checkData = JieDianQianUtils.checkOrderId(parseObject);

			if (StringUtils.isNotBlank(checkData)) { // 判断是否为空
				jieDianQianResponse.setCode(-1); // 状态码
				jieDianQianResponse.setDesc(checkData); // 异常信息
				logger.info(sessionId + " 结束存量用户检验接口，返回结果：" + JSON.toJSONString(jieDianQianResponse));
				return jieDianQianResponse; // 响应pojo
			}

			// 取参数
			String orderId = parseObject.getString("order_id"); // B20170905034012489444

			// 通过订单id查询银行卡信息
			BwOrder bwOrder = new BwOrder();
			bwOrder.setOrderNo(orderId);
			bwOrder = this.bwOrderService.findBwOrderByAttr(bwOrder);

			if (CommUtils.isNull(bwOrder)) { // 如果没查到,返回
				jieDianQianResponse.setCode(-1);
				jieDianQianResponse.setDesc("没有查询到订单信息");
				logger.info(sessionId + " 查询订单信息，返回结果：" + JSON.toJSONString(bwOrder));
				return jieDianQianResponse;
			}

			// 获取订单中的借款人id，查询银行信息
			BwBankCard bankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
			if (CommUtils.isNull(bankCard)) { // 如果没查到,返回
				jieDianQianResponse.setCode(-1);
				jieDianQianResponse.setDesc("没有查询到银行卡信息");
				logger.info(sessionId + " 查询银行卡信息，返回结果：" + JSON.toJSONString(bankCard));
				return jieDianQianResponse;
			}
			hm.put("bank_name", bankCard.getBankName()); // 银行名称
			hm.put("card_no", bankCard.getCardNo()); // 银行卡号

			jieDianQianResponse.setCode(0);
			jieDianQianResponse.setData(hm);
			jieDianQianResponse.setDesc("成功");

		} catch (Exception e) {
			logger.error(sessionId + "执行提现跳转合作方页面绑卡接口", e);
			jieDianQianResponse.setCode(-1);
			jieDianQianResponse.setDesc("接口调用异常，请稍后再试");
		}

		return jieDianQianResponse;

	}

	private JieDianQianResponse commonRepay(BwOrder bwOrder, BwBankCard bwBankCard, String type) throws Exception {
		JieDianQianResponse resp = new JieDianQianResponse();

		String orderId = String.valueOf(bwOrder.getId());

		if (RedisUtils.hexists(JIEDIANQIAN_XUDAI, orderId)) {
			resp.setCode(-1);
			resp.setDesc("系统异常");
			logger.info("此工单正在续贷中");
			return resp;
		}

		if (RedisUtils.hexists(SystemConstant.NOTIFY_BAOFU, orderId)) {
			resp.setCode(-1);
			resp.setDesc("系统异常");
			logger.info("此工单正在宝付支付中");
			return resp;
		}

		if (RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId)) {
			resp.setCode(-1);
			resp.setDesc("系统异常");
			logger.info("此工单正在连连支付中");
			return resp;
		}

		Long statusId = bwOrder.getStatusId();// 工单状态
		logger.info("工单状态:" + statusId);
		if (!(statusId.intValue() == 9 || statusId.intValue() == 13)) {
			resp.setCode(-1);
			resp.setDesc("工单只有还款中或逾期中才可还款");
			logger.info("工单只有还款中或逾期中才可还款");
			return resp;
		}

		BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
		bwRepaymentPlan.setOrderId(bwOrder.getId());
		bwRepaymentPlan = findBwRepaymentPlanByAttrProxy(bwRepaymentPlan);
		if (CommUtils.isNull(bwRepaymentPlan)) {
			resp.setCode(-1);
			resp.setDesc("系统异常");
			logger.info("没有符合条件的还款计划");
			return resp;
		}

		// 判断该用户是否签约
		Long borrowerId = bwOrder.getBorrowerId();
		logger.info("开始调用连连签约查询接口,borrowerId=" + borrowerId + ",cardNo=" + bwBankCard.getCardNo());
		CardQueryResult cardQueryResult = LianLianPayService.cardBindQuery(borrowerId.toString(),
				bwBankCard.getCardNo());

		logger.info("结束调用连连签约查询接口,cardQueryResult=" + JSONObject.toJSONString(cardQueryResult));

		if (CommUtils.isNull(cardQueryResult)) {
			resp.setCode(-1);
			resp.setDesc("未签约");
			return resp;
		}

		if (!"0000".equals(cardQueryResult.getRet_code())) {
			resp.setCode(-1);
			resp.setDesc(LoanWalletUtils.convertLian2Msg(cardQueryResult.getRet_code()));
			logger.info("调用连连签约查询接口返回结果失败，ret_code != 0000");
			return resp;
		}

		List<Agreement> agreements = cardQueryResult.getAgreement_list();
		if (CommUtils.isNull(agreements)) {
			resp.setCode(-1);
			resp.setDesc("未签约");
			logger.info("调用连连签约查询接口返回结果失败,Agreement_list is null");
			return resp;
		}

		String agreeNo = agreements.get(0).getNo_agree();

		logger.info("调用连连签约查询接口返回结果成功，连连支付协议号:" + agreeNo);

		List<RepaymentPlan> repays = new ArrayList<RepaymentPlan>();
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		String amount = String.valueOf(bwRepaymentPlan.getRealityRepayMoney());
		BwOverdueRecord overdueRecord = new BwOverdueRecord();
		overdueRecord.setOrderId(bwOrder.getId());
		overdueRecord = findBwOverdueRecordByAttrProxy(overdueRecord);
		if (!CommUtils.isNull(overdueRecord)) {
			amount = new BigDecimal(amount).add(new BigDecimal(overdueRecord.getOverdueAccrualMoney()))
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}
		logger.info("amount=" + amount);

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
			resp.setCode(-1);
			resp.setDesc("支付授权失败");
			logger.info("调用连连授权接口返回结果为空");
			return resp;
		}

		if (!"0000".equals(planResult.getRet_code())) {
			resp.setCode(-1);
			resp.setDesc(LoanWalletUtils.convertLian2Msg(cardQueryResult.getRet_code()));
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
		repayRequest.setMoney_order(amount);
		// 测试
		// repayRequest.setMoney_order("0.01");
		repayRequest.setSchedule_repayment_date(repays.get(0).getDate());
		repayRequest.setRepayment_no(bwOrder.getOrderNo());
		repayRequest.setUser_info_bind_phone(bwBorrower.getPhone());
		repayRequest.setUser_info_dt_register(dateFormat.format(bwBorrower.getCreateTime()));
		repayRequest.setUser_info_full_name(bwBorrower.getName());
		repayRequest.setUser_info_id_no(bwBorrower.getIdCard());
		repayRequest.setNotify_url(SystemConstant.NOTIRY_URL + "/loanWallet/repaymentNotify.do");

		// 存入连连redis中，有效时间15分钟
		if (!RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId())) {
			RedisUtils.setex(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId(), bwOrder.getId().toString(), 900);
			logger.info("存redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId().toString() + "]");
		}

		logger.info("开始调用连连支付接口，repayRequest=" + JSONObject.toJSONString(repayRequest));
		RepaymentResult repaymentResult = LianLianPayService.bankRepay(repayRequest);
		logger.info("结束调用连连支付接口，repaymentResult=" + JSONObject.toJSONString(repaymentResult));

		if (CommUtils.isNull(repaymentResult)) {
			resp.setCode(-1);
			resp.setDesc("支付失败");
			logger.info("调用连连支付接口返回结果为空");
			return resp;
		}

		if (!"0000".equals(repaymentResult.getRet_code()) && !"1003".equals(repaymentResult.getRet_code())) {
			resp.setCode(-1);
			resp.setDesc(LoanWalletUtils.convertLian2Msg(cardQueryResult.getRet_code()) == null
					? repaymentResult.getRet_msg() : LoanWalletUtils.convertLian2Msg(cardQueryResult.getRet_code()));
			// 删除连连REDIS
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId() + "]");
			RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId());
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId() + "]成功");
			logger.info("调用连连支付接口返回结果失败,ret_code != 0000");
			return resp;
		}
		resp.setCode(0);
		resp.setDesc("支付成功");

		return resp;
	}

	private void methodEnd(StopWatch stopWatch, String methodName, String message, JieDianQianResponse resp) {
		stopWatch.stop();
		logger.info(methodName + " end,costTime=" + stopWatch.getTotalTimeMillis() + "," + message + ",resp=" + resp);
		logger.remove();
	}

	/**
	 * 修改OR新增银行卡（暂时未开发）
	 * 
	 * @param borrower
	 * @param bwBankCard
	 * @param bankCode
	 * @param bankName
	 * @return
	 */
	private BwBankCard saveOrUpdateBBC(BwBorrower borrower, BwBankCard bwBankCard, String bankCode, String bankName) {

		BwBankCard bbc = new BwBankCard();
		// 查询银行卡信息
		bbc.setBorrowerId(borrower.getId());
		bbc = findBwBankCardByAttrProxy(bbc);

		// 如果不存在就新增，如果存在就修改
		if (CommUtils.isNull(bbc)) {
			logger.info("银行卡信息不存在，开始新增");
			bbc = new BwBankCard();
			bbc.setBorrowerId(borrower.getId());
			bbc.setCardNo(bwBankCard.getCardNo());
			bbc.setBankCode(bankCode);
			bbc.setBankName(bankName);
			bbc.setPhone(bwBankCard.getPhone());
			bbc.setSignStatus(0);
			bbc.setCreateTime(Calendar.getInstance().getTime());
			bwBankCardService.saveBwBankCard(bbc, borrower.getId());
		} else {
			logger.info("银行卡信息已存在，开始修改");

			bbc.setBorrowerId(borrower.getId());
			bbc.setCardNo(bwBankCard.getCardNo());
			bbc.setBankCode(bankCode);
			bbc.setBankName(bankName);
			bbc.setPhone(bwBankCard.getPhone());
			bbc.setSignStatus(0);
			bbc.setUpdateTime(Calendar.getInstance().getTime());
			bwBankCardService.update(bbc);
		}

		return bbc;
	}

	private BwBorrower findBwBorrowerByAttrProxy(BwBorrower borrower) {
		borrower = borrowerService.findBwBorrowerByAttr(borrower);
		return borrower;
	}

	private BwBankCard findBwBankCardByAttrProxy(BwBankCard bwBankCard) {
		bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
		return bwBankCard;
	}

	private void sendPwdMsg(String password, String phone) {
		// 发送短信
		try {
			String message = LoanWalletUtils.getMsg(password);
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

	private BwBankCard findBwBankCardByBoorwerIdProxy(Long borrowerId) {
		BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(borrowerId);
		return bwBankCard;
	}

	private BwRepaymentPlan findBwRepaymentPlanByAttrProxy(BwRepaymentPlan bwRepaymentPlan) {
		bwRepaymentPlan = bwRepaymentPlanService.findBwRepaymentPlanByAttr(bwRepaymentPlan);
		return bwRepaymentPlan;
	}

	private BwOverdueRecord findBwOverdueRecordByAttrProxy(BwOverdueRecord overdueRecord) {
		overdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(overdueRecord);
		return overdueRecord;
	}

	private BwBorrower findBwBorrowerByIdProxy(Long borrowerId) {
		BwBorrower bwBorrower = borrowerService.findBwBorrowerById(borrowerId);
		return bwBorrower;
	}
}
