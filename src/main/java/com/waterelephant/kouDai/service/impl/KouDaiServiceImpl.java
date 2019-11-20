//package com.waterelephant.kouDai.service.impl;
//
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Service;
//
//import com.beadwallet.service.kouDai.service.KouDaiServiceSDK;
//import com.waterelephant.kouDai.service.KouDaiService;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.StringUtil;
//
///**
// * 口袋征信service
// *
// * @author GuoKun
// * @version 1.0
// * @create_date 2017/4/28 16:57
// */
//@Service
//public class KouDaiServiceImpl implements KouDaiService {
//	private Logger logger = Logger.getLogger(KouDaiServiceImpl.class);
//
//	/**
//	 * 获取口袋征信黑名单
//	 *
//	 * @param reqMap
//	 */
//	public String queryBlack(Map<String, String> reqMap) {
//		String token = RedisUtils.get("koudai");
//		if (StringUtil.isEmpty(token)) {
//			logger.info(" ~~~~~~~~~~~~~~~~~~~~~~ 口袋征信获取token开始");
//			token = KouDaiServiceSDK.queryToken();
//			RedisUtils.setex("koudai", token, 10);
//		}
//		reqMap.put("token", token);
//		logger.info(" ~~~~~~~~~~~~~~~~~~~~~~ 口袋征信获取黑名单开始: id_card: " + reqMap.get("id_card") + " token: " + token);
//		String responseBlack = KouDaiServiceSDK.queryBlack(reqMap);
//		return responseBlack;
//	}
//
//}
