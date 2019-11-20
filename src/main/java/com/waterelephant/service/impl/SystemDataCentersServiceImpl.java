package com.waterelephant.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.baiqishi.entity.ServiceResult91;
import com.beadwallet.service.baiqishi.service.BaiQiShiServiceSDK;
import com.beadwallet.service.kouDai.service.KouDaiServiceSDK;
import com.waterelephant.baiqishi.entity.BqsDecision;
import com.waterelephant.baiqishi.json.DecisionJSON;
import com.waterelephant.baiqishi.service.BaiQiShiService;
import com.waterelephant.baiqishi.service.BqsDecisionService;
import com.waterelephant.service.SystemDataCentersService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

@Service
public class SystemDataCentersServiceImpl implements SystemDataCentersService {

	private Logger logger = Logger.getLogger(SystemDataCentersServiceImpl.class);

	@Resource
	private BqsDecisionService bqsDecisionService;
	@Resource
	private BaiQiShiService baiQiShiService;
//	@Resource
//	private BwDataLogService bwDataLogService;

	@Override
	public Map<String, Object> queryKoudai(Map<String, Object> map) {
		Map<String, Object> kdMap = new HashMap<String, Object>();
		kdMap.put("decision", "");
		kdMap.put("time", "");
		try {
			String token = RedisUtils.get("koudai");
			if (CommUtils.isNull(token)) {
				token = KouDaiServiceSDK.queryToken();
				RedisUtils.setex("koudai", token, 82800);
			}

			Map<String, String> reqMap = new HashMap<String, String>();
			reqMap.put("name", CommUtils.toString(map.get("name")));
			reqMap.put("id_card", CommUtils.toString(map.get("idCard")));
			reqMap.put("mobile", CommUtils.toString(map.get("phone")));
			reqMap.put("token", token);
			String str = KouDaiServiceSDK.queryBlack(reqMap);
			kdMap.put("decision", str);
			kdMap.put("time", CommUtils.convertDateToString(new Date(), SystemConstant.YMD_HMS));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kdMap;
	}

	@Override
	public Map<String, Object> queryBaiqishi(Map<String, Object> map) {
		Map<String, Object> bqsMap = new HashMap<>();
		bqsMap.put("decision", "");
		bqsMap.put("time", "");
		try {
			BqsDecision bqsDecision = bqsDecisionService.getBqsDecision(CommUtils.toString(map.get("phone")),
					CommUtils.toString(map.get("idCard")));

			if (bqsDecision == null) {
				bqsDecision = bqsDecisionService.getBqsDecisionExternal(CommUtils.toString(map.get("phone")),
						CommUtils.toString(map.get("idCard")));
			}

			if (bqsDecision == null) {
				Map<String, String> reqMap = new HashMap<>();
				reqMap.put("eventType", "verify"); // 事件信息

				// // 设备指纹
				// if (!CommUtils.isNull(tokenKey)) {
				// reqMap.put("tokenKey", tokenKey);
				// }
				reqMap.put("name", CommUtils.toString(map.get("name")));
				reqMap.put("mobile", CommUtils.toString(map.get("phone")));
				reqMap.put("certNo", CommUtils.toString(map.get("idCard")));

				reqMap.put("contactsName", CommUtils.toString(map.get("relationName")));
				reqMap.put("contactsMobile", CommUtils.toString(map.get("relationPhone")));

				reqMap.put("bankCardName", CommUtils.toString(map.get("bankName")));
				reqMap.put("bankCardNo", CommUtils.toString(map.get("bankNo")));
				ServiceResult91 serviceResult = BaiQiShiServiceSDK.decision(reqMap);

				String obj = (String) serviceResult.getObj();
				logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~　白骑士反欺诈认 证事件出参：" + obj);
				JSONObject jsonObject = (JSONObject) JSONObject.parse(obj);
				String result = (String) jsonObject.get("obj");
				DecisionJSON decisionReturnEntity = JSONObject.parseObject(result, DecisionJSON.class);
				if (decisionReturnEntity != null) {
					if ("BQS000".equals(decisionReturnEntity.getResultCode())) {
						String finalDecision = decisionReturnEntity.getFinalDecision();
						Long dataId = baiQiShiService.saveBaiqishiExternal(decisionReturnEntity, 0L, 0L, "verify"); // 返回数据入库
//						BwDataLog bwDataLog = new BwDataLog();
//						bwDataLog.setIdCard(CommUtils.toString(map.get("idCard")));
//						bwDataLog.setPhone(CommUtils.toString(map.get("phone")));
//						bwDataLog.setExternalOrder(Long.parseLong(CommUtils.toString(map.get("externalOrder"))));
//						bwDataLog.setDataType("102");
//						bwDataLog.setCreateTime(new Date());
//						bwDataLog.setSysDataId(dataId);
//						bwDataLog.setRequestType(1);
//
//						bwDataLogService.save(bwDataLog);

						bqsMap.put("decision", finalDecision);
						bqsMap.put("time", CommUtils.convertDateToString(new Date(), SystemConstant.YMD_HMS));
					}
				}

			} else {
				bqsMap.put("decision", bqsDecision.getFinalDecision());
				bqsMap.put("time", CommUtils.convertDateToString(bqsDecision.getCreateTime(), SystemConstant.YMD_HMS));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bqsMap;
	}

}
