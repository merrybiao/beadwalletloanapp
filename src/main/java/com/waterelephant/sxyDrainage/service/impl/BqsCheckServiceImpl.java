//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.beadwallet.service.utils.HttpClientHelper;
//import com.waterelephant.sxyDrainage.service.BqsCheckService;
//
//@Service
//public class BqsCheckServiceImpl implements BqsCheckService {
//
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//	private String BQS_URL = "http://tt.sxfq.com";
//	// private String BQS_URL = "https://www.sxfq.com";
//	
//	/**
//	 * -1 失败 0 成功
//	 */
//	@Override
//	public String doBqsCheck(Long sessionId, String orderId) {
//		logger.info(sessionId + "开始白骑士校验：orderId>>>" + orderId);
//		try {
//			if (StringUtils.isBlank(orderId)) {
//				return "-1";
//			}
//			String url = BQS_URL + "/loanapp-api-web/v3/app/order/a10/getSanFangBqs.do";
//			Map<String, String> params = new HashMap<>();
//			params.put("orderId", orderId);
//			String returnStr = HttpClientHelper.post(url, "UTF-8", params);
//			logger.info(sessionId + "白骑士校验：orderId>>>" + orderId + ">>>>+ 返回  >>>" + returnStr);
//			if (StringUtils.isNotBlank(returnStr)) {
//				JSONObject jsonObject = JSON.parseObject(returnStr);
//				String code = jsonObject.getString("code");
//				if ("000".equals(code)) {
//					logger.info(sessionId + "结束白骑士校验：orderId>>>" + orderId + ">> 成功");
//					return "0";
//				}
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + "结束白骑士校验>> 异常：orderId>>>" + orderId + ">>>" + e);
//		}
//		logger.info(sessionId + "结束白骑士校验：orderId>>>" + orderId + ">> 失败");
//		return "-1";
//	}
//}
