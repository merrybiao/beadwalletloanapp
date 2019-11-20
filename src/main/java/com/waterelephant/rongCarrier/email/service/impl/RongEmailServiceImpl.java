package com.waterelephant.rongCarrier.email.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.waterelephant.utils.CommUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.rong360.email.RongEmailSDK;
import com.waterelephant.rongCarrier.email.entity.EmailInfo;
import com.waterelephant.rongCarrier.email.entity.TransDetail;
import com.waterelephant.rongCarrier.email.service.EmailInfoService;
import com.waterelephant.rongCarrier.email.service.RongEmailService;
import com.waterelephant.rongCarrier.email.service.TransDetailService;

@Service
public class RongEmailServiceImpl implements RongEmailService {

	private Logger logger = LoggerFactory.getLogger(RongEmailServiceImpl.class);
	
	@Autowired
	private EmailInfoService emailInfoService;
	@Autowired
	private TransDetailService transDetailService;
	/**
	 * rong360获取认证链接地址
	 */
	@Override
	public String collectuserCommon(Map<String, String> paramMap) {
		logger.info("开始执行app的RongEmailServiceImpl的collectuserCommon（）方法，入参：" + JSONObject.toJSONString(paramMap) );
		String json = null;
		Map<String, String> params = new HashMap<String, String>();
		try {
			// 第一步：验证参数是否为空
			if (paramMap == null) {
				params.put("code", "103");
				params.put("msg", "参数为空！");
				return JSONObject.toJSONString(params);
			
			}
			
			json = RongEmailSDK.collectuserCommon(paramMap);
			if (StringUtils.isEmpty(json)) {
				params.put("code", "103");
				params.put("msg", "server返回信息为空");
				return JSONObject.toJSONString(params);
			}
		}catch (Exception e) {
			params.put("code", "900");
			params.put("msg", "执行app的RongEmailServiceImpl的collectuserCommon（）方法异常。");
			logger.error("执行app的RongEmailServiceImpl的collectuserCommon（）方法,异常：",e.getMessage());
			return JSONObject.toJSONString(params);
		}
		
		logger.info("结束执行app的RongEmailServiceImpl的collectuserCommon（）方法，出参：" + json);
		return json;
	}

	/**
	 * 获取邮箱数据
	 */
	@Override
	public String getData(String userId, String searchId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", searchId);
		try {
			String json = RongEmailSDK.getData(params);
			Map<String, Object> resultMap = JSONObject.parseObject(json, Map.class);
			long borrowerId = Long.parseLong(userId);
			if (resultMap != null) {
				Map<String, Object> mapObj = JSONObject.parseObject(resultMap.get("obj").toString(), Map.class);
				if (CommUtils.isNull(mapObj)) {
					return "error";
				}
				Map<String, Object> mapData = (Map<String, Object>)mapObj.get("data");
				if (CommUtils.isNull(mapData)) {
					return "error";
				}
				List<List<Map<String, Object>>> dataList = (List<List<Map<String, Object>>>)mapData.get("data_list");
				if (dataList.size() > 0) {
					for (List<Map<String, Object>> objectList : dataList) {
						// 保存你邮箱信息
						for (Map<String, Object> mapEmailInfo : objectList) {
							long paymentCurDate = Long.parseLong(mapEmailInfo.get("payment_cur_date").toString());
							String mail = mapEmailInfo.get("mail").toString();
							EmailInfo emailInfo = new EmailInfo();
							emailInfo.setBorrowerId(borrowerId);
							emailInfo.setMail(mail);
							emailInfo.setBankName(mapEmailInfo.get("bank_name").toString());
							emailInfo.setCardNo(mapEmailInfo.get("card_no").toString());
							emailInfo.setName(mapEmailInfo.get("name").toString());
							emailInfo.setLastBalance(Integer.parseInt(mapEmailInfo.get("last_balance").toString()));
							emailInfo.setLastPayment(Integer.parseInt(mapEmailInfo.get("last_payment").toString()));
							emailInfo.setStatementStartDate(Long.parseLong(mapEmailInfo.get("statement_start_date").toString()));
							emailInfo.setStatementEndDate(Long.parseLong(mapEmailInfo.get("statement_end_date").toString()));
							emailInfo.setPaymentCurDate(paymentCurDate);
							emailInfo.setPaymentDueDate(Long.parseLong(mapEmailInfo.get("payment_due_date").toString()));
							emailInfo.setCreditLimit(Integer.parseInt(mapEmailInfo.get("credit_limit").toString()));
							emailInfo.setTotalPoints(Integer.parseInt(mapEmailInfo.get("total_points").toString()));
							emailInfo.setNewBalance(Integer.parseInt(mapEmailInfo.get("new_balance").toString()));
							emailInfo.setMinPayment(Integer.parseInt(mapEmailInfo.get("min_payment").toString()));
							emailInfo.setNewCharges(Integer.parseInt(mapEmailInfo.get("new_charges").toString()));
							emailInfo.setAdjustment(Integer.parseInt(mapEmailInfo.get("adjustment").toString()));
							emailInfo.setInterest(Integer.parseInt(mapEmailInfo.get("interest").toString()));
							emailInfo.setLastPoints(Integer.parseInt(mapEmailInfo.get("last_points").toString()));
							emailInfo.setEarnedPoints(Integer.parseInt(mapEmailInfo.get("earned_points").toString()));
							emailInfo.setAdjustedPoints(Integer.parseInt(mapEmailInfo.get("adjusted_points").toString()));
							emailInfo.setAvailableBalanceUsd(Integer.parseInt(mapEmailInfo.get("available_balance_usd").toString()));
							emailInfo.setAvailableBalance(Integer.parseInt(mapEmailInfo.get("available_balance").toString()));
							emailInfo.setCashAdvanceLimitUsd(Integer.parseInt(mapEmailInfo.get("cash_advance_limit_usd").toString()));
							emailInfo.setCreditLimitUsd(Integer.parseInt(mapEmailInfo.get("credit_limit_usd").toString()));
							emailInfo.setCashAdvanceLimit(Integer.parseInt(mapEmailInfo.get("cash_advance_limit").toString()));
							emailInfo.setMinPaymentUsd(Integer.parseInt(mapEmailInfo.get("min_payment_usd").toString()));
							emailInfo.setNewBalanceUsd(Integer.parseInt(mapEmailInfo.get("new_balance_usd").toString()));
							emailInfo.setRedeemedPoints(Integer.parseInt(mapEmailInfo.get("redeemed_points").toString()));
							emailInfo.setSenderEmail(mapEmailInfo.get("sender_email").toString());
							EmailInfo queryEmailInfo = emailInfoService.queryEmailInfo(borrowerId, mail, paymentCurDate);
							if (queryEmailInfo == null) {
								emailInfo.setCreateTime(new Date());
								emailInfo.setUpdateTime(new Date());
								emailInfoService.saveEmailInfo(emailInfo);
							}else {
								emailInfo.setId(queryEmailInfo.getId());
								emailInfo.setUpdateTime(new Date());
								 emailInfoService.updateEmailInfo(emailInfo);
							}

							// 保存账单明细
							List<Map<String, String>> transDetailList = (List<Map<String, String>>)mapEmailInfo.get("trans_detail");
							for (Map<String, String> mapTransDetail : transDetailList) {
								long transDate = Long.parseLong(mapTransDetail.get("trans_date"));
								TransDetail transDetail = new TransDetail();
								transDetail.setBorrowerId(borrowerId);
								transDetail.setTransDate(transDate);
								transDetail.setPostDate(Long.parseLong(mapTransDetail.get("post_date")));
								transDetail.setDescription(mapTransDetail.get("description"));
								transDetail.setRmbAmount(Integer.parseInt(mapTransDetail.get("rmb_amount")));
								transDetail.setRmbOrgAmount(Integer.parseInt(mapTransDetail.get("rmb_org_amount")));
								transDetail.setCurrency(Integer.parseInt(mapTransDetail.get("currency")));
								transDetail.setTransArea(mapTransDetail.get("trans_area"));
								TransDetail queryTransDetail = transDetailService.queryTransDetail(borrowerId, transDate);
								if (queryTransDetail == null) {
									transDetail.setCreateTime(new Date());
									transDetail.setUpdateTime(new Date());
									transDetailService.saveTransDetail(transDetail);
								}else {
									transDetail.setId(queryTransDetail.getId());
									transDetail.setUpdateTime(new Date());
									transDetailService.updateTransDetail(transDetail);
								}
							}
						}
					}
				}
				return "success";
			}
		}catch (Exception e) {
			logger.error("获取数据异常，异常信息：" + e.getMessage());
		}
		return "false";
	}

}
