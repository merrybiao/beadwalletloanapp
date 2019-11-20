/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.waterelephant.dto.RepaymentDto;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.RepaymentDistributeService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;
import com.waterelephant.utils.SystemConstant;

/**
 * 还款派发业务处理
 * 
 * Module:
 * 
 * RepaymentDistributeServiceImpl.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class RepaymentDistributeServiceImpl implements RepaymentDistributeService {

	@Autowired
	private IBwBankCardService bwBankCardService;

	@Autowired
	private IBwOrderService bwOrderService;

	/**
	 * 参数验证
	 * 
	 * @see com.waterelephant.service.RepaymentDistributeService#paramVerfy(java.util.Map)
	 */
	@Override
	public AppResponseResult paramVerfy(Map<String, String> paramMap, RepaymentDto dto) {
		AppResponseResult result = new AppResponseResult();
		String borrowerId = StringUtil.toString(paramMap.get("borrowerId"));// 借款人Id
		String orderId = StringUtil.toString(paramMap.get("orderId")); // 工单Id
		String type = StringUtil.toString(paramMap.get("type"));// 1正常还款 2续贷
		String terminalType = StringUtil.toString(paramMap.get("app_request"));// 1-Android 2-ios 3-WAP
		String isUseCoupon = StringUtil.toString(paramMap.get("isUseCoupon"));
		if (StringUtil.isEmpty(borrowerId)) {
			result.setCode("102");
			result.setMsg("borrowerId不能为空");
			result.setResult("");
		}
		if (StringUtil.isEmpty(orderId)) {
			result.setCode("102");
			result.setMsg("orderId不能为空");
			result.setResult("");
		}
		BwOrder order = bwOrderService.findBwOrderById(orderId);
		if (null == order) {
			result.setCode("102");
			result.setMsg("工单不存在");
			result.setResult("");
		}
		if (StringUtil.isEmpty(type)) {
			result.setCode("102");
			result.setMsg("type不能为空");
			result.setResult("");
		}
		if (StringUtil.isEmpty(terminalType)) {
			result.setCode("102");
			result.setMsg("app_request不能为空");
			result.setResult("");
		}
		dto.setBorrowerId(Long.parseLong(borrowerId));
		dto.setOrderId(Long.parseLong(orderId));
		dto.setType(Integer.parseInt(type));
		dto.setTerminalType(Integer.parseInt(terminalType));
		dto.setUseCoupon("1".equals(isUseCoupon) ? true : false);
		return result;
	}

	/**
	 * 判断是否能还款（1、是否进行银行卡签约，2、工单是否正在处理中）
	 * 
	 * @see com.waterelephant.service.RepaymentDistributeService#signType(java.lang.Long)
	 */
	@Override
	public AppResponseResult canRepayment(RepaymentDto dto) {
		AppResponseResult result = new AppResponseResult();
		// 查询签约状态
		BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBorrowerId(dto.getBorrowerId());
		if (null == bwBankCard) {
			result.setCode("102");
			result.setMsg("您还未签约,请先签约!");
			result.setResult("");
		} else {

		}
		// 判断工单是否在处理中
		if (RedisUtils.hexists(SystemConstant.WEIXIN_ORDER_ID, dto.getOrderId() + "")
				|| RedisUtils.hexists(SystemConstant.NOTIFY_BAOFU, dto.getOrderId() + "")
				|| RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + dto.getOrderId() + "")) {
			result.setCode("102");
			if (dto.getType() != null && dto.getType() == 1) {
				result.setMsg("此工单还款正在处理中..");
			} else {
				result.setMsg("此工单展期正在处理中..");
			}
			return result;
		}
		return result;
	}

	/**
	 * 还款派发
	 * 
	 * @see com.waterelephant.service.RepaymentDistributeService#repaymentDistribute()
	 */
	@Override
	public AppResponseResult repaymentDistribute(RepaymentDto dto) {
		AppResponseResult result = new AppResponseResult();
		return result;
	}

}
