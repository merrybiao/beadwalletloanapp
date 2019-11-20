/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.waterelephant.drainage.entity.qihu360.AddInfo;
import com.waterelephant.drainage.entity.qihu360.AddInfoCredit;
import com.waterelephant.drainage.entity.qihu360.AddInfoCreditTrustScore;
import com.waterelephant.drainage.entity.qihu360.AddInfoMobile;
import com.waterelephant.drainage.entity.qihu360.AddInfoMobileTel;
import com.waterelephant.drainage.entity.qihu360.AddInfoMobileTelTeldata;
import com.waterelephant.drainage.entity.qihu360.AddInfoMobileUser;
import com.waterelephant.drainage.entity.qihu360.BaseInfo;
import com.waterelephant.drainage.entity.qihu360.OrderInfo;
import com.waterelephant.drainage.entity.qihu360.PushApprovalConfirmInfo;
import com.waterelephant.drainage.entity.qihu360.PushUserBankCard;
import com.waterelephant.drainage.entity.qihu360.QueryApprovalStatus;
import com.waterelephant.drainage.entity.qihu360.QueryContractUrl;
import com.waterelephant.drainage.entity.qihu360.QueryOrderStatus;
import com.waterelephant.drainage.entity.qihu360.QueryRepayInfo;
import com.waterelephant.drainage.entity.qihu360.QueryRepaymentPlan;
import com.waterelephant.drainage.entity.qihu360.TrialInfo;

/**
 * 
 * 
 * Module:
 * 
 * TestQiHu360.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class TestQiHu360 {

	public static String test3() {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setApplication_amount("1000.00");
		orderInfo.setApplication_term(14);
		orderInfo.setOrder_no("21071201");
		orderInfo.setTerm_unit(1);
		orderInfo.setUser_mobile("15972182935");
		orderInfo.setUser_name("张冲");

		BaseInfo baseInfo = new BaseInfo();
		baseInfo.setCorporate_flow("666");
		baseInfo.setIs_on_type(2);
		baseInfo.setMonthly_average_income("88888");
		baseInfo.setOperating_year(2);
		baseInfo.setUser_education(3);
		baseInfo.setUser_id("421022199307063953");
		baseInfo.setUser_income_by_card("10000");
		baseInfo.setUser_name("张冲");
		baseInfo.setUser_social_security(1);
		baseInfo.setWork_period(3);

		AddInfoCreditTrustScore addInfoCreditTrustScore = new AddInfoCreditTrustScore();
		addInfoCreditTrustScore.setScore(751);
		AddInfoCredit addInfoCredit = new AddInfoCredit();
		addInfoCredit.setTrust_score(addInfoCreditTrustScore);

		AddInfoMobileUser addInfoMobileUser = new AddInfoMobileUser();
		addInfoMobileUser.setAddr("武汉");
		addInfoMobileUser.setAuthentication("4");
		addInfoMobileUser.setContact_phone("15972182934");
		addInfoMobileUser.setId_card("421022199307063953");
		addInfoMobileUser.setPackage_name("36元套餐");
		addInfoMobileUser.setPhone("15972182935");
		addInfoMobileUser.setPhone_remain("52");
		addInfoMobileUser.setPhone_status("1");
		addInfoMobileUser.setReal_name("张冲");
		addInfoMobileUser.setReg_time("2015-03-21");
		addInfoMobileUser.setScore("445");
		addInfoMobileUser.setStar_level("3");
		addInfoMobileUser.setUser_source("联通");

		List<AddInfoMobileTelTeldata> teldatas = new ArrayList<AddInfoMobileTelTeldata>();
		for (int i = 0; i < 5; i++) {
			AddInfoMobileTelTeldata addInfoMobileTelTeldata = new AddInfoMobileTelTeldata();
			addInfoMobileTelTeldata.setCall_time("2017-05-2" + i + " 19:18:27");
			addInfoMobileTelTeldata.setCall_type("3");
			addInfoMobileTelTeldata.setFee("0");
			addInfoMobileTelTeldata.setReceive_phone("1381098821" + i);
			addInfoMobileTelTeldata.setTrade_addr("北京");
			addInfoMobileTelTeldata.setTrade_time("23");
			addInfoMobileTelTeldata.setTrade_type("3");
			teldatas.add(addInfoMobileTelTeldata);
		}
		AddInfoMobileTel addInfoMobileTel = new AddInfoMobileTel();
		addInfoMobileTel.setTeldata(teldatas);

		AddInfoMobile addInfoMobile = new AddInfoMobile();
		addInfoMobile.setUser(addInfoMobileUser);
		addInfoMobile.setTel(addInfoMobileTel);

		AddInfo addInfo = new AddInfo();
		addInfo.setCredit(addInfoCredit);
		addInfo.setMobile(addInfoMobile);

		Map<String, Object> data = new HashMap<>();
		data.put("orderInfo", orderInfo);
		data.put("applyDetail", baseInfo);
		data.put("addInfo", addInfo);
		String biz_data = JSON.toJSONString(data);
		return biz_data;
	}

	public static String test6() {
		PushUserBankCard pushUserBankCard = new PushUserBankCard();
		pushUserBankCard.setBank_address("");
		pushUserBankCard.setBank_card("6217002870001944972");
		pushUserBankCard.setBind_card_src("");
		pushUserBankCard.setCard_id("");
		pushUserBankCard.setId_number("421022199307063953");
		pushUserBankCard.setOpen_bank("CCB");
		pushUserBankCard.setOrder_no("6323404357914963968");
		pushUserBankCard.setUser_mobile("15972182935");
		pushUserBankCard.setUser_name("张冲");

		return JSON.toJSONString(pushUserBankCard);

	}

	public static String test8() {
		QueryApprovalStatus queryApprovalStatus = new QueryApprovalStatus();
		queryApprovalStatus.setOrder_no("6323404357914963968");
		return JSON.toJSONString(queryApprovalStatus);
	}

	public static String test9() {
		TrialInfo trialInfo = new TrialInfo();
		trialInfo.setOrder_no("6323404357914963968");
		trialInfo.setLoan_amount("10000");
		trialInfo.setLoan_term(30);
		return JSON.toJSONString(trialInfo);
	}

	public static String test10() {
		PushApprovalConfirmInfo pushApprovalConfirmInfo = new PushApprovalConfirmInfo();
		pushApprovalConfirmInfo.setOrder_no("6323404357914963968");
		pushApprovalConfirmInfo.setLoan_amount("1000");
		pushApprovalConfirmInfo.setLoan_term(14);
		return JSON.toJSONString(pushApprovalConfirmInfo);
	}

	public static String test11() {
		QueryContractUrl queryContractUrl = new QueryContractUrl();
		queryContractUrl.setOrder_no("6323404357914963968");
		return JSON.toJSONString(queryContractUrl);

	}

	public static String test12() {
		QueryRepaymentPlan queryRepaymentPlan = new QueryRepaymentPlan();
		queryRepaymentPlan.setOrder_no("6323404357914963968");
		return JSON.toJSONString(queryRepaymentPlan);
	}

	public static String test13() {
		QueryRepayInfo queryRepayInfo = new QueryRepayInfo();
		queryRepayInfo.setOrder_no("6323404357914963968");
		queryRepayInfo.setPeriod_nos("1");
		return JSON.toJSONString(queryRepayInfo);
	}

	public static String test15() {
		QueryOrderStatus queryOrderStatus = new QueryOrderStatus();
		queryOrderStatus.setOrder_no("6323404357914963968");
		return JSON.toJSONString(queryOrderStatus);
	}
}
