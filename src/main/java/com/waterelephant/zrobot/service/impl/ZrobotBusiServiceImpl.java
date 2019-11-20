package com.waterelephant.zrobot.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.zrobot.FinanceBehaviorSDKService;
import com.waterelephant.entity.BwZrobotFinanceBehavior;
import com.waterelephant.entity.BwZrobotPanguScore;
import com.waterelephant.exception.BusinessException;
import com.waterelephant.service.BwZrobotFinanceBehaviorService;
import com.waterelephant.service.BwZrobotPanguScoreService;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.zrobot.dto.ZrobotPanguScoreDto;
import com.waterelephant.zrobot.service.ZrobotBusiService;

/**
 * ZrobotBusiServiceImpl.java
 * @author dinglinhao
 *
 */
@Service
public class ZrobotBusiServiceImpl implements ZrobotBusiService {
	
	private Logger logger = LoggerFactory.getLogger(ZrobotBusiServiceImpl.class);

	private static final String REDIS_ZROBOT_TOKEN_KEY = "system:zrobot:token";
	
	@Autowired
	private BwZrobotFinanceBehaviorService bwZrobotFinanceBehaviorService;
	
	@Autowired
	private BwZrobotPanguScoreService bwZrobotPanguScoreService;
	
	@Override
	public String getToken() throws Exception {
		
		String token = RedisUtils.get(REDIS_ZROBOT_TOKEN_KEY);
		
		if(StringUtils.isEmpty(token)) {
			String result = FinanceBehaviorSDKService.getToken();
//			String params = "account=waterelephant&password=kvhyxdqvd";
//			String result = httpPost("https://solution.zrobot.cn/microLoanApiTest/api/login.htm", params);
			logger.info("FinanceBehaviorSDKService.getToken()返回结果：{}",result);
			Assert.hasText(result, "SDK获取token返回为空~");
			JSONObject json = JSON.parseObject(result);
			if("10000".equals(json.getString("result_code"))) {
				token = json.getString("token");
				int seconds = json.getIntValue("expires_in")/1000;
				RedisUtils.setex(REDIS_ZROBOT_TOKEN_KEY, token, seconds);
			}else {
				throw new BusinessException(json.getString("result_msg")+"["+json.getString("result_code")+"]");
			}
		}
		return token;
	}

	@Override
	public Map<String,Object> queryfb(String token, String name, String idCardNum, String cellPhoneNum)
			throws Exception {
//		Map<String,String> paramsMap = new HashMap<>();
//		paramsMap.put("token",token);
//		paramsMap.put("name",name);
//		paramsMap.put("id_card_num",idCardNum);
//		paramsMap.put("cell_phone_num",cellPhoneNum);
//		paramsMap.put("keyType","1");
//		String params = JSON.toJSONString(paramsMap);
//		
//		String result = httpPostByJson("https://solution.zrobot.cn/microLoanApiTest/api/financeBehavior/queryfb.htm", params);
		
		String result = FinanceBehaviorSDKService.queryfb(token, name, idCardNum, cellPhoneNum, "1");
		logger.info("FinanceBehaviorSDKService.queryfb()返回结果：{}",result);
		Assert.hasText(result, "SDK查询金融行为标签返回结果为空");
		
		Map<String,Object> resultMap = new HashMap<>();
		JSONObject json = JSON.parseObject(result);
		if("YW000".equals(json.getString("result_code"))) {
			String transactionId = json.getString("transaction_id");
//			String encodedText = json.getString("data");
//			String data = decryptByPrivateKey(encodedText);
//			resultMap.put("data", JSON.parse(data));
			resultMap.put("transaction_id", transactionId);
			resultMap.put("data", json.getJSONObject("data"));
		}else {
			throw new BusinessException(json.getString("result_msg")+"["+json.getString("result_code")+"]");
		}
		return resultMap;
	}
	
 	
 	@Override
	public boolean savefb(String name, String idCardNum, String cellPhoneNum, Map<String, Object> resultMap)
			throws Exception {
 		String transactionId = String.valueOf(resultMap.get("transaction_id"));
 		JSONObject json = (JSONObject)resultMap.get("data");
		BwZrobotFinanceBehavior record = new BwZrobotFinanceBehavior();
		record.setName(name);
		record.setIdCardNum(idCardNum);
		record.setCellPhoneNum(cellPhoneNum);
		record.setTransactionId(transactionId);
		
		record.setCreditCardNum(json.getString("credit_card_num"));
		record.setDebitCardNum(json.getString("debit_card_num"));
		
		record.setOverdueStatus1m(json.getString("overdue_status_1m"));
		record.setPaymentSum1m(json.getString("payment_sum_1m"));
		record.setDebtLenderNum1m(json.getString("debt_lender_num_1m"));
		record.setLoanLenderNum1m(json.getString("loan_lender_num_1m"));
		record.setLoanSum1m(json.getString("loan_sum_1m"));
		
		record.setOverdueStatus3m(json.getString("overdue_status_3m"));
		record.setPaymentSum3m(json.getString("payment_sum_3m"));
		record.setDebtLenderNum3m(json.getString("debt_lender_num_3m"));
		record.setLoanLenderNum3m(json.getString("loan_lender_num_3m"));
		record.setLoanSum3m(json.getString("loan_sum_3m"));
		
		record.setOverdueStatus6m(json.getString("overdue_status_6m"));
		record.setPaymentSum6m(json.getString("payment_sum_6m"));
		record.setDebtLenderNum6m(json.getString("debt_lender_num_6m"));
		record.setLoanLenderNum6m(json.getString("loan_lender_num_6m"));
		record.setLoanSum6m(json.getString("loan_sum_6m"));
		
		record.setOverdueStatus12m(json.getString("overdue_status_12m"));
		record.setPaymentSum12m(json.getString("payment_sum_12m"));
		record.setDebtLenderNum12m(json.getString("debt_lender_num_12m"));
		record.setLoanLenderNum12m(json.getString("loan_lender_num_12m"));
		record.setLoanSum12m(json.getString("loan_sum_12m"));

		record.setCreateTime(new Date());
		return bwZrobotFinanceBehaviorService.save(record);
	}
 	
 	@Override
 	public ZrobotPanguScoreDto queryPangu(String name,String idCardNum,String cellPhoneNum,String bankCardNum) throws Exception {
 		ZrobotPanguScoreDto dto = null;
 		String result = FinanceBehaviorSDKService.queryPangu(name, idCardNum, cellPhoneNum, bankCardNum);
 		logger.info("FinanceBehaviorSDKService.queryPangu()返回结果：{}",result);
		Assert.hasText(result, "SDK查询盘古分数返回结果为空");
		
		JSONObject json = JSON.parseObject(result);
		String resultCode = json.getString("result_code");
		switch(resultCode) {
			case "0000":
				dto = JSON.parseObject(json.getString("data"),ZrobotPanguScoreDto.class);
				break;
			case "700":
				throw new BusinessException(json.getString("result_msg")+"["+resultCode+"]");
			case "9000":
				throw new BusinessException(json.getString("result_msg")+"["+resultCode+"]");
			default:
				break;
		}
		return dto;
 	}
 	
 	@Override
 	public Long savePangu(String name,String idCardNum,String cellPhoneNum,String bankCardNum) throws Exception {
 		
 		return bwZrobotPanguScoreService.save(name, idCardNum, cellPhoneNum, bankCardNum);
 	}
 	
 	@Override
 	public boolean updatePangu(Long id,ZrobotPanguScoreDto dto) throws Exception {
 		Assert.notNull(id, "参数id为空~");
 		BwZrobotPanguScore record = new BwZrobotPanguScore();
 		record.setId(id);
 		BeanUtils.copyProperties(dto,record);
 		return bwZrobotPanguScoreService.update(record);
 	}
 	
 	public static void main(String[] args) {
 		
 		try {
			ZrobotBusiServiceImpl impl = new ZrobotBusiServiceImpl();
			String name = "丁林浩";
			String idCardNum = "4208211999003283531";
			String cellPhoneNum = "15021097842";
			String bankCardNum = "6227000016150005113";
			ZrobotPanguScoreDto dto = impl.queryPangu(name, idCardNum, cellPhoneNum, bankCardNum);
			System.out.println(dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	}

}
