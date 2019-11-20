package com.waterelephant.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.service.BwCapitalExternalPushService;
import com.waterelephant.service.SystemQfqCapitalCentersService;
import com.waterelephant.utils.AESUtil;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.RedisUtils;

@Controller
@RequestMapping("/app/qfqCapitalCenters")
public class SystemQfqCapitalCentersController {

	private Logger logger = Logger.getLogger(SystemQfqCapitalCentersController.class);
	@Resource
	private BwCapitalExternalPushService bwCapitalExternalPushService;
	@Resource
	private SystemQfqCapitalCentersService systemQfqCapitalCentersService;

	@ResponseBody
	@RequestMapping("/pushCapital.do")
	public JSONObject pushCapitalQfq(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			String jsonRequest = IOUtils.toString(request.getInputStream(), "utf-8");
			if (!CommUtils.isNull(jsonRequest)) {
				JSONObject jsb = JSONObject.parseObject(jsonRequest);
				String appId = CommUtils.toString(jsb.get("appId"));
				String type = RedisUtils.hget("external:appId", appId + "_12");

				String moneyQuota = RedisUtils.hget("external:quota", appId + "_12");

				String day = CommUtils.convertDateToString(new Date(), "yyyy-MM-dd");
				String moneyRealQuota = "";
				logger.info("限额：" + moneyQuota);
				logger.info("宝付放款请求：" + JSON.toJSONString(jsb));
				if (!CommUtils.isNull(appId) && "1".equals(type)) {
					String params = CommUtils.toString(jsb.get("params"));
					String aesKey = RedisUtils.hget("external:aesKey", appId);
					try {
						String jsonStr = AESUtil.Decrypt(params, aesKey);
						Map<String, Object> map = JSON.parseObject(jsonStr);

						if (CommUtils.isNull(map.get("name"))) {
							returnMap.put("code", "10002");
							returnMap.put("msg", "name不可为空");

							return JSON.parseObject(JSON.toJSONString(returnMap));
						}

						if (CommUtils.isNull(map.get("idCard"))) {
							returnMap.put("code", "10002");
							returnMap.put("msg", "idCard不可为空");
							return JSON.parseObject(JSON.toJSONString(returnMap));
						}

						if (CommUtils.isNull(map.get("money"))) {
							returnMap.put("code", "10002");
							returnMap.put("msg", "money不可为空");
							return JSON.parseObject(JSON.toJSONString(returnMap));
						}

						moneyRealQuota = RedisUtils.hget("external:realQuota", appId + "_12" + "_" + day);
						moneyRealQuota = CommUtils.isNull(moneyRealQuota) ? "0" : moneyRealQuota;
						logger.info("当前额度:" + moneyRealQuota);

						if (!CommUtils.isNull(moneyQuota)
								&& (Double.parseDouble(moneyQuota) - Double.parseDouble(moneyRealQuota)
										- Double.parseDouble(CommUtils.toString(map.get("money")))) <= 0) {
							returnMap.put("code", "10003");
							returnMap.put("msg", "余额不足");
							return JSON.parseObject(JSON.toJSONString(returnMap));
						}

						if (CommUtils.isNull(map.get("orderNo"))) {
							returnMap.put("code", "10002");
							returnMap.put("msg", "orderNo不可为空");
							return JSON.parseObject(JSON.toJSONString(returnMap));
						}

						if (CommUtils.isNull(map.get("phone"))) {
							returnMap.put("code", "10002");
							returnMap.put("msg", "phone不可为空");
							return JSON.parseObject(JSON.toJSONString(returnMap));
						}

						if (CommUtils.isNull(map.get("cardNo"))) {
							returnMap.put("code", "10002");
							returnMap.put("msg", "cardNo不可为空");
							return JSON.parseObject(JSON.toJSONString(returnMap));
						}

						if (CommUtils.isNull(map.get("bankName"))) {
							returnMap.put("code", "10002");
							returnMap.put("msg", "bankName不可为空");
							return JSON.parseObject(JSON.toJSONString(returnMap));
						}

						String orderNo = RedisUtils.hget("external:keyWord", appId) + "_" + map.get("orderNo");

						map.put("orderNo", orderNo);

						if (bwCapitalExternalPushService.queryBwCapitalExternalPushCount(orderNo) > 0) {
							RedisUtils.rpush("capital:externalError", "12已存在成功的放款记录：" + orderNo);
							returnMap.put("code", "10003");
							returnMap.put("msg", "已存在成功的放款记录");
							return JSON.parseObject(JSON.toJSONString(returnMap));
						}

						String returnStr = systemQfqCapitalCentersService.saveCapitalBaofooQfq(map);
						logger.info("返回结果：" + returnStr);
						JSONObject json = JSON.parseObject(returnStr);
						String code = json.getString("code");
						if ("0000".equals(code)) {
							moneyRealQuota = RedisUtils.hget("external:realQuota", appId + "_12" + "_" + day);
							moneyRealQuota = CommUtils.isNull(moneyRealQuota) ? "0" : moneyRealQuota;

							RedisUtils.hset("external:realQuota", appId + "_12" + "_" + day,
									CommUtils.toString(Double.parseDouble(moneyRealQuota)
											+ Double.parseDouble(CommUtils.toString(map.get("money")))));
						}

						return JSON.parseObject(returnStr);

					} catch (Exception e) {
						e.printStackTrace();
						returnMap.put("code", "10002");
						returnMap.put("msg", "秘钥不合法");
					}
				} else {
					returnMap.put("code", "10001");
					returnMap.put("msg", "appId不存在");
				}
			} else {
				returnMap.put("code", "9999");
				returnMap.put("msg", "参数为空");
			}

		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("code", "9999");
			returnMap.put("msg", "请求异常");
		}

		return JSON.parseObject(JSON.toJSONString(returnMap));
	}

	@ResponseBody
	@RequestMapping("/queryCapital.do")
	public JSONObject queryCapital(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			String jsonRequest = IOUtils.toString(request.getInputStream(), "utf-8");
			if (!CommUtils.isNull(jsonRequest)) {
				JSONObject jsb = JSONObject.parseObject(jsonRequest);
				String appId = CommUtils.toString(jsb.get("appId"));
				String type = RedisUtils.hget("external:appId", appId + "_" + 12);

				if (!CommUtils.isNull(appId) && "1".equals(type)) {
					String params = CommUtils.toString(jsb.get("params"));
					String aesKey = RedisUtils.hget("external:aesKey", appId);
					try {
						String jsonStr = AESUtil.Decrypt(params, aesKey);
						logger.info("查询工单入参：" + jsonStr);
						Map<String, Object> map = JSON.parseObject(jsonStr);

						if (CommUtils.isNull(map.get("orderNo"))) {
							returnMap.put("code", "10002");
							returnMap.put("msg", "orderNo不可为空");
							return JSON.parseObject(JSON.toJSONString(returnMap));
						}
						String orderNo = RedisUtils.hget("external:keyWord", appId) + "_" + map.get("orderNo");

						String returnStr = systemQfqCapitalCentersService.updateCapitalBaofooQuery(orderNo);
						logger.info("宝付查询返回:" + returnStr);

						return JSON.parseObject(returnStr);
					} catch (Exception e) {
						e.printStackTrace();
						returnMap.put("code", "10002");
						returnMap.put("msg", "秘钥不合法");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("code", "9999");
			returnMap.put("msg", "请求异常");
		}

		return JSON.parseObject(JSON.toJSONString(returnMap));
	}

	@ResponseBody
	@RequestMapping("/testCapital.do")
	public String testCapital(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			String jsonRequest = IOUtils.toString(request.getInputStream(), "utf-8");
			System.out.println(jsonRequest);
			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("code", "9999");
			returnMap.put("msg", "请求异常");
		}
		return "OK";
	}

	public static void main(String[] args) throws Exception {
		System.out.println(CommUtils.convertDateToString(new Date(), "yyyy-MM-dd"));
		System.out
				.println((Double.parseDouble("100000") - Double.parseDouble("0") - Double.parseDouble("2020.12")) < 0);
		String moneyRealQuota = "";
		moneyRealQuota = CommUtils.isNull(moneyRealQuota) ? "0" : moneyRealQuota;
		System.out.println(moneyRealQuota);

	}
}
