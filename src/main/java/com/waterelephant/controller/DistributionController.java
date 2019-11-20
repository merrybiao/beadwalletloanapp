package com.waterelephant.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.serve.BeadWalletDistributionService;
import com.sctx.approve.sdk.util.CallBackUtil;
import com.waterelephant.capital.baofoo.BaofooController;
import com.waterelephant.entity.BwCloudExternal;
import com.waterelephant.entity.BwCloudReason;
import com.waterelephant.entity.BwCreditCloudExternal;
import com.waterelephant.entity.BwCreditCloudReason;
import com.waterelephant.service.BwCloudExternalService;
import com.waterelephant.service.BwCloudReasonService;
import com.waterelephant.service.BwCreditCloudExternalService;
import com.waterelephant.service.BwCreditCloudReasonService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.RedisUtils;

@Controller
@RequestMapping("/distribution/bjsh")
public class DistributionController {

	private Logger logger = Logger.getLogger(BaofooController.class);

	@Resource
	private BwCloudExternalService bwCloudExternalService;
	@Resource
	private BwCloudReasonService bwCloudReasonService;

	@Resource
	private BwCreditCloudExternalService bwCreditCloudExternalService;
	@Resource
	private BwCreditCloudReasonService bwCreditCloudReasonService;

	@ResponseBody
	@RequestMapping("/returnDataCallPush.do")
	public Object returnDataCallPush(HttpServletRequest request, HttpServletResponse response) {
		String str = null;
		String returnStr = "SUCCESS";
		try {

			String result = request.getParameter("result");
			String noBus = request.getParameter("noBus");
			logger.info("异步回调审核结果==》noBus:" + noBus);
			logger.info("异步回调审核结果==》result:" + result);
			BwCloudExternal bwCloudExternal = bwCloudExternalService.findBwCloudExternalByExternalNo(noBus, 1);
			if (bwCloudExternal != null) {
				str = CallBackUtil.decrypt(result, bwCloudExternal.getOrderPushNo());
				logger.info("异步回调审核解析结果==》str:" + str);
				// {"resCode":"0000","resMsg":"SUCCESS","handlerData":{"advice":"accept","creditLimit":12,"creditTerm":12,"reasonCode":"AC","version":"v1.0.0","amtDownpay":0,"amtMonthrepay":0,"noBus":"20180723164400567285059","noBusb":"B20171113031553822333_8","dataProd":{"amtLmt":"10000","reasonCode":"0","advice":"accept"}}}
				if (!CommUtils.isNull(str)) {
					JSONObject jsbReturn = JSONObject.parseObject(str);

					String code = jsbReturn.getString("resCode");
					String msg = jsbReturn.getString("resMsg");

					bwCloudExternal.setCode(code);
					bwCloudExternal.setMsg(msg);
					bwCloudExternal.setUpdateTime(new Date());

					if (!CommUtils.isNull(code) && "0000".equals(code)) {
						bwCloudExternal.setType(1);

						JSONObject jsbHandlerData = jsbReturn.getJSONObject("handlerData");
						if (jsbHandlerData != null) {
							String advice = jsbHandlerData.getString("advice");
							// && "accept".equals(advice)
							if (!CommUtils.isNull(advice)) {
								bwCloudExternal.setAdvice(jsbHandlerData.getString("advice"));
								if (!CommUtils.isNull(jsbHandlerData.get("creditLimit"))) {
									bwCloudExternal.setCreditLimit(
											new Double(CommUtils.toString(jsbHandlerData.get("creditLimit"))));
								}
								if (!CommUtils.isNull(jsbHandlerData.get("score"))) {
									bwCloudExternal.setScore(jsbHandlerData.getString("score"));
								}

								if (!CommUtils.isNull(jsbHandlerData.get("creditTerm"))) {
									bwCloudExternal.setCreditTerm(jsbHandlerData.getInteger("creditTerm"));
								}
								bwCloudExternal.setType(2);
								// 队列处理
								JSONObject jsb = new JSONObject();
								jsb.put("orderId", bwCloudExternal.getOrderId());
								jsb.put("type", "accept".equals(advice) ? 0 : 1);
								jsb.put("limit", jsbHandlerData.get("creditLimit"));
								jsb.put("repayLoan", bwCloudExternal.getRepayLoan());
								RedisUtils.rpush("system:cloudReturn", jsb.toJSONString());

								if (!"accept".equals(advice)) {
									String reasonCode = CommUtils.toString(jsbHandlerData.get("reasonCode"));
									String reason = jsbHandlerData.getJSONArray("reason").toJSONString();

									BwCloudReason bwCloudReason = bwCloudReasonService
											.findBwCloudReason(bwCloudExternal.getOrderPushNo());
									if (bwCloudReason == null) {
										bwCloudReason = new BwCloudReason();
										bwCloudReason.setOrderId(bwCloudExternal.getOrderId());
										bwCloudReason.setOrderPushNo(bwCloudExternal.getOrderPushNo());
										bwCloudReason.setReasonCode(reasonCode);
										bwCloudReason.setReason(reason);
										bwCloudReason.setCreateTime(new Date());
										bwCloudReason.setUpdateTime(new Date());
									} else {
										bwCloudReason.setReasonCode(reasonCode);
										bwCloudReason.setReason(reason);
										bwCloudReason.setUpdateTime(new Date());
									}

									if (CommUtils.isNull(bwCloudReason.getId())) {
										bwCloudReasonService.saveBwCloudReason(bwCloudReason);
									} else {
										bwCloudReasonService.updateBwCloudReason(bwCloudReason);
									}
								}

							}

						}
					} else {
						bwCloudExternal.setType(3);
					}
					bwCloudExternalService.updateBwCloudExternal(bwCloudExternal);
					RedisUtils.hdel("system:cloudExternalQuery", CommUtils.toString(bwCloudExternal.getOrderId()));
				}
			} else {
				returnStr = "FAIL";
			}

		} catch (Exception e) {
			logger.error("审核结果异常==》" + e);
			e.printStackTrace();
			RedisUtils.rpush("system:cloudReturnError", str);
			returnStr = "FAIL";
		}
		return returnStr;
	}

	@ResponseBody
	@RequestMapping("/returnCreditDataCallPush.do")
	public Object returnCreditDataCallPush(HttpServletRequest request, HttpServletResponse response) {
		String str = null;
		String returnStr = "SUCCESS";
		try {

			String result = request.getParameter("result");
			String noBus = request.getParameter("noBus");
			logger.info("授信单异步回调审核结果==》noBus:" + noBus);
			logger.info("授信单异步回调审核结果==》result:" + result);
			BwCreditCloudExternal bwCreditCloudExternal = bwCreditCloudExternalService
					.findBwCreditCloudExternalByExternalNo(noBus, 1);

			if (bwCreditCloudExternal != null) {
				str = CallBackUtil.decrypt(result, bwCreditCloudExternal.getCreditPushNo());
				logger.info("授信单异步回调审核解析结果==》str:" + str);
				// {"resCode":"0000","resMsg":"SUCCESS","handlerData":{"advice":"accept","creditLimit":12,"creditTerm":12,"reasonCode":"AC","version":"v1.0.0","amtDownpay":0,"amtMonthrepay":0,"noBus":"20180723164400567285059","noBusb":"B20171113031553822333_8","dataProd":{"amtLmt":"10000","reasonCode":"0","advice":"accept"}}}
				if (!CommUtils.isNull(str)) {
					JSONObject jsbReturn = JSONObject.parseObject(str);

					String code = jsbReturn.getString("resCode");
					String msg = jsbReturn.getString("resMsg");

					bwCreditCloudExternal.setCode(code);
					bwCreditCloudExternal.setMsg(msg);
					bwCreditCloudExternal.setUpdateTime(new Date());

					if (!CommUtils.isNull(code) && "0000".equals(code)) {
						bwCreditCloudExternal.setType(1);

						JSONObject jsbHandlerData = jsbReturn.getJSONObject("handlerData");
						if (jsbHandlerData != null) {
							String advice = jsbHandlerData.getString("advice");
							// && "accept".equals(advice)
							if (!CommUtils.isNull(advice)) {
								bwCreditCloudExternal.setAdvice(jsbHandlerData.getString("advice"));
								if (!CommUtils.isNull(jsbHandlerData.get("creditLimit"))) {
									bwCreditCloudExternal.setCreditLimit(
											new Double(CommUtils.toString(jsbHandlerData.get("creditLimit"))));
								}
								if (!CommUtils.isNull(jsbHandlerData.get("score"))) {
									bwCreditCloudExternal.setScore(jsbHandlerData.getString("score"));
								}

								if (!CommUtils.isNull(jsbHandlerData.get("creditTerm"))) {
									bwCreditCloudExternal.setCreditTerm(jsbHandlerData.getInteger("creditTerm"));
								}
								bwCreditCloudExternal.setType(2);
								// 队列处理
								JSONObject jsb = new JSONObject();
								jsb.put("orderId", bwCreditCloudExternal.getCreditId());
								jsb.put("type", "accept".equals(advice) ? 0 : 1);
								jsb.put("limit", jsbHandlerData.get("creditLimit"));
								jsb.put("repayLoan", bwCreditCloudExternal.getRepayLoan());
								RedisUtils.rpush("system:creditCloudReturn", jsb.toJSONString());

								if (!"accept".equals(advice)) {
									String reasonCode = CommUtils.toString(jsbHandlerData.get("reasonCode"));
									String reason = jsbHandlerData.getJSONArray("reason").toJSONString();

									BwCreditCloudReason bwCreditCloudReason = bwCreditCloudReasonService
											.findBwCreditCloudReason(bwCreditCloudExternal.getCreditPushNo());
									if (bwCreditCloudReason == null) {
										bwCreditCloudReason = new BwCreditCloudReason();
										bwCreditCloudReason.setCreditId(bwCreditCloudExternal.getCreditId());
										bwCreditCloudReason.setCreditPushNo(bwCreditCloudExternal.getCreditPushNo());
										bwCreditCloudReason.setReasonCode(reasonCode);
										bwCreditCloudReason.setReason(reason);
										bwCreditCloudReason.setCreateTime(new Date());
										bwCreditCloudReason.setUpdateTime(new Date());
									} else {
										bwCreditCloudReason.setReasonCode(reasonCode);
										bwCreditCloudReason.setReason(reason);
										bwCreditCloudReason.setUpdateTime(new Date());
									}

									if (CommUtils.isNull(bwCreditCloudReason.getId())) {
										bwCreditCloudReasonService.saveBwCreditCloudReason(bwCreditCloudReason);
									} else {
										bwCreditCloudReasonService.updateBwCreditCloudReason(bwCreditCloudReason);
									}
								}

							}

						}
					} else {
						bwCreditCloudExternal.setType(3);
					}
					bwCreditCloudExternalService.updateBwCreditCloudExternal(bwCreditCloudExternal);
					RedisUtils.hdel("system:creditCloudExternalQuery",
							CommUtils.toString(bwCreditCloudExternal.getCreditId()));
				}
			} else {
				returnStr = "FAIL";
			}

		} catch (Exception e) {
			logger.error("审核结果异常==》" + e);
			e.printStackTrace();
			RedisUtils.rpush("system:creditCloudReturnError", str);
			returnStr = "FAIL";
		}
		return returnStr;
	}

	@ResponseBody
	@RequestMapping("/queryDistribution.do")
	public String queryDistribution(HttpServletRequest request, HttpServletResponse response) {
		String returnStr = null;
		try {

			String param = request.getParameter("param");
			BwCloudExternal bwCloudExternal = bwCloudExternalService.findBwCloudExternalByExternalNo(param, 1);
			if (bwCloudExternal != null) {
				String str = null;
				if ("2".equals(bwCloudExternal.getExt2())) {
					str = BeadWalletDistributionService.queryDistribution77duli(param);
				} else if ("3".equals(bwCloudExternal.getExt2())) {
					str = BeadWalletDistributionService.queryDistribution77Lfq(param);
				} else {
					str = BeadWalletDistributionService.queryDistribution(param);
				}

				logger.info("查询结果==》" + str);
				JSONObject jsbReturn = JSONObject.parseObject(str);

				String code = jsbReturn.getString("resCode");
				String msg = jsbReturn.getString("resMsg");
				bwCloudExternal.setCode(code);
				bwCloudExternal.setMsg(msg);
				bwCloudExternal.setUpdateTime(new Date());

				if (!CommUtils.isNull(code) && "0000".equals(code)) {
					bwCloudExternal.setType(1);

					JSONObject jsbHandlerData = jsbReturn.getJSONObject("handlerData");
					if (jsbHandlerData != null) {
						String advice = jsbHandlerData.getString("advice");
						if (!CommUtils.isNull(advice)) {
							bwCloudExternal.setAdvice(jsbHandlerData.getString("advice"));
							if (!CommUtils.isNull(jsbHandlerData.get("creditLimit"))) {
								bwCloudExternal.setCreditLimit(
										new Double(CommUtils.toString(jsbHandlerData.get("creditLimit"))));
							}
							if (!CommUtils.isNull(jsbHandlerData.get("score"))) {
								bwCloudExternal.setScore(jsbHandlerData.getString("score"));
							}

							if (!CommUtils.isNull(jsbHandlerData.get("creditTerm"))) {
								bwCloudExternal.setCreditTerm(jsbHandlerData.getInteger("creditTerm"));
							}
							bwCloudExternal.setType(2);

							JSONObject jsb = new JSONObject();
							jsb.put("orderId", bwCloudExternal.getOrderId());
							jsb.put("type", "accept".equals(advice) ? 0 : 1);
							jsb.put("limit", jsbHandlerData.get("creditLimit"));
							jsb.put("repayLoan", bwCloudExternal.getRepayLoan());
							RedisUtils.rpush("system:cloudReturn", jsb.toJSONString());
						}

					}
				} else {
					bwCloudExternal.setType(3);
				}
				bwCloudExternalService.updateBwCloudExternal(bwCloudExternal);
				RedisUtils.hdel("system:cloudExternalQuery", CommUtils.toString(bwCloudExternal.getOrderId()));
				returnStr = "OK";
			}

		} catch (Exception e) {
			logger.error("审核结果异常==》" + e);
			returnStr = "NO";
		}
		return returnStr;
	}

	@ResponseBody
	@RequestMapping("/returnCallPush.do")
	public String returnCallPush(HttpServletRequest request, HttpServletResponse response) {
		String returnStr = null;
		try {

			String param = request.getParameter("param");
			logger.info("异步回调审核==》" + param);
			JSONObject jsb = JSONObject.parseObject(param);
			String noBus = jsb.getString("noBus");
			System.out.println("noBus:" + noBus);
			// 查询

		} catch (Exception e) {
			logger.error("审核结果异常==》" + e);
		}
		return "SUCCESS";
	}

	public static void main(String[] args) {
		try {
			String str = "f8uOkENjD1Aw4uZMSw8NpT7hNLCzJpDo/Epdn0nPKTSfP55HFq0ii04ck3nyvnfqTN0QVRB73hpmIOcW8VnZm//bF+yLT+RhjD1iH1npPo/NOZ8bcmgNMpXxyyLTGtK6Gyb0kq98FlX3BqpbF42jmA6JsFluBDMkfXP5BniKioC0NNCU0vrhstP4kEciTvH3sHaiTqkzPhz0vl91Cs76PmXkwIlkImvlMFSu7cnDdv18QWe7tHBkQGZBPmhUdIUYJgc7vaLtG+9tjiZ3nB1k8R4bvhgyVUQCM233iJDLJ7ujdhIdQYfFAZMqhAx53RPLOnqZOfQyj2hXtr3PuYl74X5yrFBupmAsqzpBdvljucFqJ3L1O/dzr2eNUxGok9llazYTLf1tCmPzgwNjvF3XGQ==";

			String sss = CallBackUtil.decrypt(str, "Y20180709023620752138_762014");
			System.out.println(sss);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
