/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.capital.baofoo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.capital.service.CapitalBaofooService;
import com.beadwallet.service.sms.dto.MessageDto;
import com.beadwallet.utils.RsaCodingUtil;
import com.beadwallet.utils.SecurityUtil;
import com.waterelephant.capital.koudai.KdaiSignUtils;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwCapitalOrder;
import com.waterelephant.entity.BwCapitalPush;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderPushInfo;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.service.BwCapitalOrderService;
import com.waterelephant.service.BwCapitalPushService;
import com.waterelephant.service.BwCapitalWithholdService;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwOrderPushInfoService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwRepaymentService;
import com.waterelephant.service.SystemCapitalCentersService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.service.impl.BwRepaymentPlanService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;
import com.waterelephant.utils.SystemConstant;

/**
 * @author 崔雄健
 * @date 2017年4月12日
 * @description
 */
@Controller
@RequestMapping("/baofoo")
public class BaofooController {
	private Logger logger = Logger.getLogger(BaofooController.class);

	@Resource
	private IBwOrderService bwOrderService;
	@Resource
	private IBwOrderPushInfoService bwOrderPushInfoService;
	@Resource
	private BwOrderRongService bwOrderRongService;
	@Resource
	private BwRepaymentPlanService bwRepaymentPlanService;
	@Resource
	private IBwBankCardService bwBankCardService;
	@Resource
	private BwCapitalPushService bwCapitalPushService;
	@Resource
	private BwProductDictionaryService bwProductDictionaryService;
	@Resource
	private BwOrderProcessRecordService bwOrderProcessRecordService;
	@Resource
	private BwCapitalOrderService bwCapitalOrderService;

	@Resource
	private BwCapitalWithholdService bwCapitalWithholdService;
	@Resource
	private IBwRepaymentService iBwRepaymentService;

	@Resource
	private BwBorrowerService bwBorrowerService;
	@Resource
	private SystemCapitalCentersService systemCapitalCentersService;
	// @Resource
	// private BaofooService baofooService;

	@ResponseBody
	@RequestMapping("/loanAdvicePush.do") // 放款通知推送
	public String loanAdvicePush(HttpServletRequest request, HttpServletResponse response) {
		try {
			Date now = new Date();
			// 计算还款时间
			String params = IOUtils.toString(request.getInputStream(), "utf-8");
			logger.info("宝付回调参数==》" + params);
			Map<String, String> paramsMap = KdaiSignUtils.getNotityMap(params);

			if (CommUtils.isNull(params)) {
				return "FAIL";
			}
			// params =
			// "member_id=100000178&terminal_id=100000859&data_type=XML&data_content=77f530811dd320e5a63118d92f44ec2f8a21574c8bf4a9b565ef24558fa0299d75f49bb7eb4e5ffeeae429cdac8a81aeb786447445f1163445a7fdd9ef9b3dbec373c386fa74e79f01aed5b5c3e31c9059ed24fbeb0ce364bef446246542e5bd88ac49c11eff88284bf10bd41736e5f10843954cd922ea11f9f40d965a7fa11086c722e043f9465aaf72225a50e8d5df0662d5e8b0f5280f85bf1a01bf678fa32f482ea966e298bf1f19e2b3688cdec231e5ef1c5b6314a08cb2bf77e363a1fe4929f6cf9f6fe2c3099c73999fa9949428125120ccfcf73c8a3d3924c38b9a568451827c89ad3fdfa8c778b25a363ba6dbdd3fc9016923db5447e181d39f082ea6a1eccc6cfb9eb74afdfb153da66b1ad4fc2a0118443092fc3961d35951beb129454f338ad9a2b9a1c317cca8c548632d8e157db36c4ce93cc8f96736bcd911d73f10b40cfaddb9b36bb36352a644371326879a06ccdaa748535daa14f2dd7787d49d10e6395dc28f411b85114c2934f711ea53d2dabd1bc4697892085d8768136757911cbc3ff5a587ddce7e975036ac46202ca41459cf3a943fccfd8fa6dc6e60fc898d84fa1999de09d229e6eaae741cc409fe005454b4f06c09e56011a3075fbd8fe292a8883680c906b7b65f5b6c63b16c65772bea375b87b7355b003b8196d3c0f6bc7f5540fd180448acfa35cd3e901a19183c58d9e480bef469416f7fd1d7cba918a92daafbb7efad20c4a739264c538534d8effa74fbee7f3aab56716be098d6668102ee15503dbfd17898fd588258d07dc18ec9d26f1365b0128cd293fa9cf2ddbdeb957751fb02baf44538162930a139f01bf6846fa7d73bd48806dabe32de3de0510d60edcf872899951263ccb33a395af00e46b1913d17d224804f9f84fe524fcfa775d3df3fbc9b1ce9ea2612af47a7896ed19786a2907548863e4c6a3f47b9c5676afbf69a8ea0fb7788d26b993dc819d0912905c92f31ccf93908565ec958536bcbbcfe14a2a4ea9230919008a45f4dfebf621c4103210f7082ce036eb177c36acf65108094249bf2862996ef27d87310d565192791b1ab4e67f462a52aa220ac823006c66077bae06490bffbd14d9fa402c2529df3448887b1bcc72964807a9404fb5b7432e9df600ab8556a58978b4a333abef533d11b3d0a8ad4ddd23554acdb9e9866a9fc6c7a384bc92fd7ef031522418aa5cde4d80a18d9467ba2fc37797780b3101eca9bf810352a64e230b1bd5dc88b073a0e92870562dcc3a9789bbc2ad4dc7a2c85f7d8b770cf892445a32616746b8e904d4851bfe308369eedcd271d462c6da163a182caf53435a7c5347a256a3b42b1d13c2d570f792c6bc8bd3a9b4b816d6aa90c3d05252c3ccec4bdf54e2375926708e688576cf097a03a817e7462f2543d43cb1b8d32f67b4e000554668b052c62f9d8";

			String pub_key = SystemConstant.CAPITAL_BAOFOO_PUBKEYPATH;

			String reslut = RsaCodingUtil.decryptByPubCerFile(paramsMap.get("data_content"), pub_key);
			// 第二步BASE64解密
			reslut = SecurityUtil.Base64Decode(reslut);
			logger.info("解密后结果：" + reslut);
			Document document = DocumentHelper.parseText(reslut);
			Element root = document.getRootElement();
			String orderNo = root.element("trans_reqDatas").element("trans_reqData").element("trans_no").getText();
			String state = root.element("trans_reqDatas").element("trans_reqData").element("state").getText();
			String loanDate = root.element("trans_reqDatas").element("trans_reqData").element("trans_endtime")
					.getText();
			//获取remark信息，代付成功为转账成功，失败为具体原因
			String msg = root.element("trans_reqDatas").element("trans_reqData").elementText("trans_remark");


			if (orderNo.startsWith("YPSS_") || orderNo.startsWith("SXYP_")) {
				return "OK";
			}

			if (orderNo.startsWith("FQ")) {
				// 分期保险回调
				Map map = new HashMap<>();
				map.put("orderNo", orderNo);
				map.put("state", state);
				map.put("code", StringUtil.toString(paramsMap.get("code")));
				map.put("msg", StringUtil.toString(paramsMap.get("result")));
				RedisUtils.rpush("capital:baofoofqReturn", JSON.toJSONString(map));
				return "OK";
			}
			if (orderNo.startsWith("BJ")) {

				systemCapitalCentersService.updateCapitalBaofooNotity(orderNo, state, loanDate,
						StringUtil.toString(paramsMap.get("result")));
				return "OK";
			}
			if (orderNo.startsWith("SAAS")) {
				systemCapitalCentersService.updateCapitalBaofooNotity(orderNo, state, loanDate,
						StringUtil.toString(paramsMap.get("result")));
				return "OK";
			}

			BwOrder bwOrder = new BwOrder();
			bwOrder.setOrderNo(orderNo);
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder); // 查询订单

			if (bwOrder == null) {
				return "OK";
			}
			Long orderId = bwOrder.getId();

			BwCapitalPush bwCapitalPush = bwCapitalPushService.queryBwCapitalPush(orderId, 8); // 查询渠道为宝付的对应工单
			if (bwCapitalPush != null) { // 如果有订单，且状态为放款成功
				if ((2 == bwCapitalPush.getPushStatus() && "1".equals(state))) {
					logger.info("宝付推送处理结果：重复推送，当前工单号：" + orderId);
					return "OK";
				}
			} else { // 如果没有订单，创建订单
				bwCapitalPush = new BwCapitalPush();
				bwCapitalPush.setOrderId(orderId);
				bwCapitalPush.setCreateTime(now);
				bwCapitalPush.setCapitalId(8); // 渠道设置为宝付
				bwCapitalPush.setPushCount(0); // 发送次数
			}

			if (CommUtils.isNull(bwCapitalPush.getId())) {
				bwCapitalPushService.save(bwCapitalPush); // 保存资方订单信息
			}
			bwCapitalPush.setCode(StringUtil.toString(paramsMap.get("code"))); // 获取状态码
			bwCapitalPush.setMsg(StringUtil.toString(paramsMap.get("result"))); // 响应结果
			bwCapitalPush.setUpdateTime(now);

			if ("1".equals(state)) {

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date loanTime = null;
				try {
					loanDate = DateUtil.getDateString(CommUtils.convertStringToDate(loanDate, "yyyyMMddHHmmss"),
							"yyyy-MM-dd");
					loanTime = format.parse(loanDate);

				} catch (Exception e) {
					logger.error("还款时间：", e);
					e.printStackTrace();
					return "FAIL";
				}

				bwCapitalPush.setPushStatus(2);
				bwCapitalPush.setCode(state);
				bwCapitalPush.setUpdateTime(now);
				try {
					// 查询该工单对应的还款计划是否存在，如果存在直接返回成功给宝付
					List<BwRepaymentPlan> bwRepaymentPlanList = bwRepaymentPlanService
							.listBwRepaymentPlanByOrderId(orderId);
					if (bwRepaymentPlanList != null && bwRepaymentPlanList.size() > 0) {
						logger.info("宝付推送处理结果：重复推送，当前工单号：" + orderId);
						return "OK";
					}
					// 还款时间
					Date repaymentTime = null;
					if (7 == bwOrder.getProductId() || 8 == bwOrder.getProductId()) {
						bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder,
								CommUtils.convertStringToDate(loanDate, "yyyy-MM-dd"));
					} else {
						if (2 == bwOrder.getProductType()) { // 如果产品类型是分期
							bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateCapital(bwOrder, loanDate);

							JSONObject jsonObject = new JSONObject();
							jsonObject.put("orderId", CommUtils.toString(orderId));
							jsonObject.put("typeId", "0");
							RedisUtils.rpush("insurance:order", jsonObject.toJSONString());
						} else {
							// 根据产品类型查询产品（分期）
							BwProductDictionary bwProductDictionary = bwProductDictionaryService
									.findBwProductDictionaryById(bwOrder.getProductId());
							String termType = null;
							String term = null;
							if (bwProductDictionary != null) {
								termType = bwProductDictionary.getpTermType(); // 产品类型（1：月；2：天）pTermType
								term = bwProductDictionary.getpTerm(); // 产品期限
							}

							if ("1".equals(termType)) {
								repaymentTime = MyDateUtils.addMonths(loanTime, StringUtil.toInteger(term));
							} else if ("2".equals(termType)) {
								repaymentTime = MyDateUtils.addDays(loanTime, StringUtil.toInteger(term));
							} else {
								repaymentTime = MyDateUtils.addDays(loanTime, 30);
							}
							// 根据工单信息和指定还款时间生成借款人还款计划
							bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder, repaymentTime,
									bwProductDictionary);
						}
					}

					// bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder, repayTime);
					// bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateCapital(bwOrder, repayDate);

					bwCapitalPush.setPushStatus(2);// 放款成功
					bwCapitalPush.setMsg("转账成功");
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);

					// 更新push 表
					BwOrderPushInfo bwOrderPushInfo = bwOrderPushInfoService.getOrderPushInfo(orderId, 11); // 渠道为宝付的工单
					if (bwOrderPushInfo != null) {
						bwOrderPushInfo.setFullTime(loanTime);
						bwOrderPushInfo.setPushStatus(2); // 状态。0：推送失败，1：推送成功，2：接收成功，3：接收失败
						bwOrderPushInfo.setPushRemark("接收成功");
						bwOrderPushInfo.setUpdateTime(now);
						bwOrderPushInfo.setLoanTime(loanTime); // 设置放款时间
						bwOrderPushInfoService.updateOrderPushInfo(bwOrderPushInfo);
					}
					bwOrderProcessRecordService.saveOrUpdateByOrderIdRepay(orderId, loanTime);

					RedisUtils.hdel("capital:baofooQuery", CommUtils.toString(orderId));

					try {

						// 渠道放款成功 - 通知
						String channel = String.valueOf(bwOrder.getChannel());
						if (RedisUtils.hexists("tripartite:channels", "channel_" + bwOrder.getChannel())) {

							HashMap<String, String> hm = new HashMap<>();
							hm.put("channelId", channel);
							hm.put("orderId", String.valueOf(bwOrder.getId()));
							hm.put("orderStatus", String.valueOf(bwOrder.getStatusId()));
							hm.put("result", "放款成功");
							String hmData = JSON.toJSONString(hm);
							RedisUtils.rpush("tripartite:orderStatusNotify:" + channel, hmData);

						}

						// 通知短信
						Long borrowerId = bwOrder.getBorrowerId();
						BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
						String sex = bwBorrower.getSex() + "";
						String userName = bwBorrower.getName();

						MessageDto messageDto = new MessageDto();
						messageDto.setBusinessScenario("1"); // 营销短信
						messageDto.setPhone(bwBorrower.getPhone());
						messageDto.setType("1"); // 文字短信
						if ("0".equals(sex)) {
							messageDto.setMsg("【速秒钱包】尊敬的" + userName + "女士，您在速秒钱包的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
						} else {
							messageDto.setMsg("【速秒钱包】尊敬的" + userName + "先生，您在速秒钱包的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
						}
						RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));

					} catch (Exception e) {
						e.printStackTrace();
					}

				} catch (Exception e) {
					bwCapitalPush.setPushStatus(3);// 接收失败
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
					logger.info("宝付推送处理结果：保存信息出错，当前工单号：" + orderId);
					throw e;
				}

			} else if ("-1".equals(state)) {
				RedisUtils.hdel("capital:baofooQuery", CommUtils.toString(orderId));
				bwCapitalPush.setPushStatus(3);// 接收失败
				//将渠道返回的错误信息报错至msg字段
				if(StringUtils.isEmpty(msg)){
					bwCapitalPush.setMsg("转账失败");
				}else {
					bwCapitalPush.setMsg(msg);
				}
				bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
			}
		} catch (Exception e) {
			logger.error("宝付推送结果异常：", e);
		}

		return "OK";
	}

	@RequestMapping("/queryOrders.do") // 放款通知推送
	public void queryOrders(HttpServletRequest request, HttpServletResponse response) {
		logger.info("宝付获取订单状态");
		String orderIds = request.getParameter("orderIds");
		if (CommUtils.isNull(orderIds)) {
			try {
				orderIds = IOUtils.toString(request.getInputStream(), "utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("宝付获取订单状态所有：" + orderIds);
		if (CommUtils.isNull(orderIds)) {
			return;
		}
		String[] orders = orderIds.split(",");
		if (CommUtils.isNull(orders)) {
			return;
		}
		for (String orderId : orders) {
			try {
				logger.info("宝付获取订单状态开始：" + orderId);
				BwOrder bwOrder = new BwOrder();
				bwOrder.setId(Long.parseLong(orderId.trim()));
				bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);

				BwCapitalOrder bwCapitalOrder = bwCapitalOrderService.queryBwCapitalOrder(bwOrder.getId(), 8);
				if (bwOrder == null || bwCapitalOrder == null) {
					continue;
				}
				String capitalNo = bwCapitalOrder.getCapitalNo();
				Map map = new HashMap<>();
				map.put("order_no", bwOrder.getOrderNo());
				map.put("batchid", capitalNo.split("-")[0]);

				String type = CommUtils.toString(RedisUtils.hget("system:request", "1"));

				String returnStr = "";
				if ("1".equals(type)) {
					// returnStr = baofooService.queryOrder(map);
				} else {
					returnStr = CapitalBaofooService.queryOrder(JSON.toJSONString(map));
				}

				// String returnStr = baofooService.queryOrder(map);

				logger.info("宝付查询结果：" + returnStr);

				JSONObject returnjson = (JSONObject) JSONObject.parse(returnStr);

				JSONObject contentjson = (JSONObject) returnjson.get("trans_content");
				JSONObject headjson = (JSONObject) contentjson.get("trans_head");
				JSONArray jarr = contentjson.getJSONArray("trans_reqDatas");

				JSONObject arrJson = jarr.getJSONObject(0);

				JSONObject reqDataJson = arrJson.getJSONObject("trans_reqData");

				String state = CommUtils.toString(reqDataJson.get("state"));
				String loanDate = CommUtils.toString(reqDataJson.get("trans_endtime"));
				//获取备注信息
				String trans_remark = reqDataJson.getString("trans_remark");

				BwCapitalPush bwCapitalPush = bwCapitalPushService.queryBwCapitalPush(bwOrder.getId(), 8); // 查询渠道为宝付的对应工单
				if (bwCapitalPush != null) { // 如果有订单，且状态为放款成功
					if ((2 == bwCapitalPush.getPushStatus() && "1".equals(state))) {
						logger.info("宝付推送处理结果：重复推送，当前工单号：" + orderId);
						continue;
					}
				} else { // 如果没有订单，创建订单
					bwCapitalPush = new BwCapitalPush();
					bwCapitalPush.setOrderId(bwOrder.getId());
					bwCapitalPush.setCreateTime(new Date());
					bwCapitalPush.setCapitalId(8); // 渠道设置为宝付
					bwCapitalPush.setPushCount(0); // 发送次数
				}

				if (CommUtils.isNull(bwCapitalPush.getId())) {
					bwCapitalPushService.save(bwCapitalPush); // 保存资方订单信息
				}

				if ("1".equals(state)) {

					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date loanTime = null;
					try {
						loanDate = DateUtil.getDateString(
								CommUtils.convertStringToDate(loanDate, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd");
						loanTime = format.parse(loanDate);

					} catch (Exception e) {
						logger.error("还款时间：", e);
						e.printStackTrace();
						continue;
					}

					bwCapitalPush.setPushStatus(2);
					bwCapitalPush.setCode(state);
					bwCapitalPush.setUpdateTime(new Date());
					try {
						// 查询该工单对应的还款计划是否存在，如果存在直接返回成功给宝付
						List<BwRepaymentPlan> bwRepaymentPlanList = bwRepaymentPlanService
								.listBwRepaymentPlanByOrderId(bwOrder.getId());
						if (bwRepaymentPlanList != null && bwRepaymentPlanList.size() > 0) {
							logger.info("宝付推送处理结果：重复推送，当前工单号：" + orderId);
							continue;
						}
						// 还款时间
						Date repaymentTime = null;
						if (7 == bwOrder.getProductId()) {
							bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder,
									CommUtils.convertStringToDate(loanDate, "yyyy-MM-dd"));
						} else {
							if (2 == bwOrder.getProductType()) { // 如果产品类型是分期
								bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateCapital(bwOrder, loanDate);
								JSONObject jsonObject = new JSONObject();
								jsonObject.put("orderId", CommUtils.toString(orderId));
								jsonObject.put("typeId", "0");
								RedisUtils.rpush("insurance:order", jsonObject.toJSONString());
							} else {
								// 根据产品类型查询产品（单期）
								BwProductDictionary bwProductDictionary = bwProductDictionaryService
										.findBwProductDictionaryById(bwOrder.getProductId());
								String termType = null;
								String term = null;
								if (bwProductDictionary != null) {
									termType = bwProductDictionary.getpTermType(); // 产品类型（1：月；2：天）pTermType
									term = bwProductDictionary.getpTerm(); // 产品期限
								}

								if ("1".equals(termType)) {
									repaymentTime = MyDateUtils.addMonths(loanTime, StringUtil.toInteger(term));
								} else if ("2".equals(termType)) {
									repaymentTime = MyDateUtils.addDays(loanTime, StringUtil.toInteger(term));
								} else {
									repaymentTime = MyDateUtils.addDays(loanTime, 30);
								}
								// 根据工单信息和指定还款时间生成借款人还款计划
								bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder, repaymentTime,
										bwProductDictionary);
							}
						}

						// bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder, repayTime);
						// bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateCapital(bwOrder, repayDate);

						bwCapitalPush.setPushStatus(2);// 放款成功
						bwCapitalPush.setMsg("转账成功");
						bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);

						// 更新push 表
						BwOrderPushInfo bwOrderPushInfo = bwOrderPushInfoService.getOrderPushInfo(bwOrder.getId(), 11); // 渠道为宝付的工单
						if (bwOrderPushInfo != null) {
							bwOrderPushInfo.setFullTime(loanTime);
							bwOrderPushInfo.setPushStatus(2); // 状态。0：推送失败，1：推送成功，2：接收成功，3：接收失败
							bwOrderPushInfo.setPushRemark("接收成功");
							bwOrderPushInfo.setUpdateTime(new Date());
							bwOrderPushInfo.setLoanTime(loanTime); // 设置放款时间
							bwOrderPushInfoService.updateOrderPushInfo(bwOrderPushInfo);
						}
						bwOrderProcessRecordService.saveOrUpdateByOrderIdRepay(bwOrder.getId(), loanTime);

						RedisUtils.hdel("capital:baofooQuery", CommUtils.toString(orderId));

						try {

							// 渠道放款成功 - 通知
							String channel = String.valueOf(bwOrder.getChannel());
							if (RedisUtils.hexists("tripartite:channels", "channel_" + bwOrder.getChannel())) {

								HashMap<String, String> hm = new HashMap<>();
								hm.put("channelId", channel);
								hm.put("orderId", String.valueOf(bwOrder.getId()));
								hm.put("orderStatus", String.valueOf(bwOrder.getStatusId()));
								hm.put("result", "放款成功");
								String hmData = JSON.toJSONString(hm);
								RedisUtils.rpush("tripartite:orderStatusNotify:" + channel, hmData);

							}
							// 通知短信
							Long borrowerId = bwOrder.getBorrowerId();
							BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
							String sex = bwBorrower.getSex() + "";
							String userName = bwBorrower.getName();

							MessageDto messageDto = new MessageDto();
							messageDto.setBusinessScenario("1"); // 营销短信
							messageDto.setPhone(bwBorrower.getPhone());
							messageDto.setType("1"); // 文字短信
							if ("0".equals(sex)) {
								messageDto.setMsg(
										"【速秒钱包】尊敬的" + userName + "女士，您在速秒钱包的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
							} else {
								messageDto.setMsg(
										"【速秒钱包】尊敬的" + userName + "先生，您在速秒钱包的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
							}
							RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));

						} catch (Exception e) {
							e.printStackTrace();
						}

					} catch (Exception e) {
						bwCapitalPush.setPushStatus(3);// 接收失败
						bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
						logger.info("宝付推送处理结果：保存信息出错，当前工单号：" + orderId);
						throw e;
					}

				} else if ("-1".equals(state)) {
					RedisUtils.hdel("capital:baofooQuery", CommUtils.toString(orderId));
					bwCapitalPush.setPushStatus(3);// 接收失败
					if(StringUtils.isEmpty(trans_remark)){
						bwCapitalPush.setMsg("转账失败");
					}else {
						bwCapitalPush.setMsg(trans_remark);
					}
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
				}
			} catch (Exception e) {
				logger.error("宝付获取订单异常");
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String ster = "{\"trans_content\":{\"trans_reqDatas\":[{\"trans_reqData\":{\"state\":1,\"to_acc_dept\":\"||中国银行\",\"to_acc_name\":\"崔雄健\",\"to_acc_no\":\"6217857600031940773\",\"trans_batchid\":20978661,\"trans_endtime\":\"2017-11-09 17:04:01\",\"trans_fee\":\"0.01\",\"trans_money\":\"1000.00\",\"trans_no\":\"B20160929030031254123\",\"trans_orderid\":17196744,\"trans_remark\":\"\",\"trans_starttime\":\"2017-11-09 17:03:50\",\"trans_summary\":\"\"}}],\"trans_head\":{\"return_code\":\"0000\",\"return_msg\":\"代付请求交易成功\"}}}";
		JSONObject returnjson = (JSONObject) JSONObject.parse(ster);

		JSONObject contentjson = (JSONObject) returnjson.get("trans_content");
		JSONObject headjson = (JSONObject) contentjson.get("trans_head");
		JSONArray jarr = contentjson.getJSONArray("trans_reqDatas");

		JSONObject arrJson = jarr.getJSONObject(0);

		JSONObject reqDataJson = arrJson.getJSONObject("trans_reqData");

		String state = CommUtils.toString(reqDataJson.get("state"));
		String loanDate = CommUtils.toString(reqDataJson.get("trans_endtime"));

		System.out.println(state + "," + loanDate);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date loanTime = null;
		Date repaymentTime = null;
		try {
			loanDate = DateUtil.getDateString(CommUtils.convertStringToDate(loanDate, "yyyy-MM-dd HH:mm:ss"),
					"yyyy-MM-dd");
			loanTime = format.parse(loanDate);

		} catch (Exception e) {
			e.printStackTrace();
		}

		repaymentTime = MyDateUtils.addDays(loanTime, StringUtil.toInteger(15));
		System.out.println(CommUtils.convertDateToString(repaymentTime, "yyyy-MM-dd"));
	}

}
