/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.bd.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.beadwallet.service.utils.ConfigReader;
import com.beadwallet.service.utils.HttpRequest;
import com.waterelephant.bd.ChannelService;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.impl.BwOrderService;
import com.waterelephant.utils.CommUtils;

import net.sf.json.JSONObject;

/**
 * @author 崔雄健
 * @date 2017年3月21日
 * @description
 */
@Service("channelServiceImpl")
public class ChannelServiceImpl implements ChannelService {
	private Logger logger = Logger.getLogger(ChannelServiceImpl.class);
	@Resource
	private BwOrderRongService bwOrderRongService;
	// @Resource
	// private MqProducer mqProducer;
	@Resource
	private BwOrderService bwOrderService;
	@Resource
	private BwOverdueRecordService bwOverdueRecordService;
	@Resource
	private BwProductDictionaryService bwProductDictionaryService;

	/**
	 * 三方渠道-工单状态同步
	 * 
	 * @param channelId 渠道编号
	 * @param orderId 水象单号
	 * @param statusId 水象工单状态
	 */
	@Override
	public Map<String, Object> sendOrderStatus(String channelId, String orderId, String statusId) {
		Map<String, Object> returnMap = new HashMap<>();
		try {
			if (CommUtils.isNull(channelId)) {
				returnMap.put("code", "1001");
				returnMap.put("version", "1.0");
				returnMap.put("message", "渠道编码为空");
				return returnMap;
			}

			if ("1".equals(channelId) || "2".equals(channelId) || "3".equals(channelId) || "4".equals(channelId)) {
				returnMap.put("code", "1001");
				returnMap.put("version", "1.0");
				returnMap.put("message", "非三方渠道");
				return returnMap;
			}

			if (CommUtils.isNull(orderId)) {
				returnMap.put("code", "1002");
				returnMap.put("version", "1.0");
				returnMap.put("message", "工单编号为空");
				return returnMap;
			}
			if (CommUtils.isNull(statusId)) {
				returnMap.put("code", "1003");
				returnMap.put("version", "1.0");
				returnMap.put("message", "工单状态");
				return returnMap;
			}
			String thirdOrderId = bwOrderRongService.findThirdOrderNoByOrderId(orderId);
			// 请求
			String result = HttpRequest.doPost(
					ConfigReader.getConfig("request_path") + "/channelBusi/sendOrderStatus.do",
					"channelId=" + channelId + "&thirdOrderId=" + thirdOrderId + "&statusId=" + statusId);
			logger.info("渠道请求返回参数:" + result);
			JSONObject jsonObject = JSONObject.fromObject(result);
			returnMap.put("code", jsonObject.getString("code"));
			returnMap.put("version", "1.0");
			returnMap.put("message", jsonObject.getString("message"));
		} catch (Exception e) {
			logger.error("渠道请求失败》》channelId:" + channelId + ",orderId:" + orderId, e);
			return returnMap;
		}
		return returnMap;

	}

	/**
	 * 三方渠道-工单状态同步
	 * 
	 * @param channelId 渠道编号
	 * @param orderId 水象单号
	 * @param statusId 水象工单状态
	 */
	@Override
	public Map<String, Object> sendOrderStatusMQ(String channelId, String orderId, String statusId) {
		Map<String, Object> returnMap = new HashMap<>();
		try {
			if (CommUtils.isNull(channelId)) {
				returnMap.put("code", "1001");
				returnMap.put("version", "1.0");
				returnMap.put("message", "渠道编码为空");
				return returnMap;
			}

			if ("1".equals(channelId) || "2".equals(channelId) || "3".equals(channelId) || "4".equals(channelId)) {
				returnMap.put("code", "1001");
				returnMap.put("version", "1.0");
				returnMap.put("message", "非三方渠道");
				return returnMap;
			}

			if (CommUtils.isNull(orderId)) {
				returnMap.put("code", "1002");
				returnMap.put("version", "1.0");
				returnMap.put("message", "工单编号为空");
				return returnMap;
			}
			if (CommUtils.isNull(statusId)) {
				returnMap.put("code", "1003");
				returnMap.put("version", "1.0");
				returnMap.put("message", "工单状态");
				return returnMap;
			}

			String thirdOrderId = bwOrderRongService.findThirdOrderNoByOrderId(orderId);

			if (CommUtils.isNull(thirdOrderId)) {
				returnMap.put("code", "1005");
				returnMap.put("version", "1.0");
				returnMap.put("message", "渠道工单编号为空");
				return returnMap;
			}

			Map<String, String> map = new HashMap<>();
			map.put("channelId", channelId);
			map.put("thirdOrderId", thirdOrderId);
			map.put("statusId", statusId);
			// mqProducer.sendMsg("sendStatus_" + channelId + "_" + thirdOrderId + "_" + statusId,
			// JSON.toJSONString(map));
			// // 请求
			// String result = HttpRequest.doPost(
			// ConfigReader.getConfig("request_path") + "/channelBusi/sendOrderStatus.do",
			// "channelId=" + channelId + "&thirdOrderId=" + thirdOrderId + "&statusId=" + statusId);
			// logger.info("渠道请求返回参数:" + result);
			// JSONObject jsonObject = JSONObject.fromObject(result);
			// returnMap.put("code", jsonObject.getString("code"));
			// returnMap.put("version", "1.0");
			// returnMap.put("message", jsonObject.getString("message"));
		} catch (Exception e) {
			logger.error("渠道请求失败》》channelId:" + channelId + ",orderId:" + orderId, e);
			return returnMap;
		}
		return returnMap;
	}

	/**
	 * 三方渠道-工单状态同步（code0082）
	 * 
	 * @param channelId 渠道编号
	 * @param orderId 水象单号
	 * @param statusId 水象工单状态
	 */
	@Override
	public Map<String, Object> sendOrderStatusMQ(Map<String, String> paramMap) {
		Map<String, Object> returnMap = new HashMap<>();
		// 第一步：取参
		String channelId = paramMap.get("channelId"); // 渠道编码
		String orderId = paramMap.get("orderId"); // 工单编号
		String statusId = paramMap.get("statusId"); // 工单状态
		try {
			// 第二步：验证参数
			if (CommUtils.isNull(channelId)) {
				returnMap.put("code", "1001");
				returnMap.put("version", "1.0");
				returnMap.put("message", "渠道编码为空");
				return returnMap;
			}

			if ("1".equals(channelId) || "2".equals(channelId) || "3".equals(channelId) || "4".equals(channelId)) {
				returnMap.put("code", "1001");
				returnMap.put("version", "1.0");
				returnMap.put("message", "非三方渠道");
				return returnMap;
			}

			if (CommUtils.isNull(orderId)) {
				returnMap.put("code", "1002");
				returnMap.put("version", "1.0");
				returnMap.put("message", "工单编号为空");
				return returnMap;
			}
			if (CommUtils.isNull(statusId)) {
				returnMap.put("code", "1003");
				returnMap.put("version", "1.0");
				returnMap.put("message", "工单状态");
				return returnMap;
			}

			String thirdOrderId = bwOrderRongService.findThirdOrderNoByOrderId(orderId);

			if (CommUtils.isNull(thirdOrderId)) {
				returnMap.put("code", "1005");
				returnMap.put("version", "1.0");
				returnMap.put("message", "渠道工单编号为空");
				return returnMap;
			}

			paramMap.put("thirdOrderId", thirdOrderId); // 第三方工单号
			// mqProducer.sendMsg("sendStatus_" + channelId + "_" + thirdOrderId + "_" + statusId,
			// JSON.toJSONString(paramMap));
		} catch (Exception e) {
			logger.error("渠道请求失败》》channelId:" + channelId + ",orderId:" + orderId, e);
			return returnMap;
		}
		return returnMap;
	}

	/**
	 * 贷款钱包 - 审核通过（code0082）
	 * 
	 * @param paramMap
	 * @return
	 */
	@Override
	public Map<String, Object> loanWallet_sendOrderStatusMQ_checkThrough(Map<String, String> paramMap) {
		Map<String, Object> returnMap = new HashMap<>();
		// 第一步：取参
		String channelId = paramMap.get("channelId"); // 渠道编码
		String orderId = paramMap.get("orderId"); // 工单编号
		String statusId = paramMap.get("statusId"); // 工单状态
		try {
			// 第二步：验证
			returnMap = loanWallet_validate(paramMap, new int[] { 4 });
			if (returnMap != null) {
				return returnMap;
			}

			// 第三步：组装参数
			String thirdOrderId = bwOrderRongService.findThirdOrderNoByOrderId(orderId); // 第三方工单号
			BwOrder bwOrder = new BwOrder(); // 订单
			bwOrder.setId(Long.parseLong(orderId));
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);

			BwOverdueRecord bwOverdueRecord = new BwOverdueRecord(); // 逾期
			bwOverdueRecord.setOrderId(bwOrder.getId());
			bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
			double verdueAccrualMoney = 0;
			if (bwOverdueRecord != null) {
				verdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney();
			}

			BwProductDictionary bwProductDictionary = bwProductDictionaryService
					.findBwProductDictionaryById(bwOrder.getProductId()); // 产品类型
			int loanDate = 0; // 放款天数
			int pTermType = Integer.valueOf(bwProductDictionary.getpTermType()); // 产品类型（1：月 2：天）
			int pTerm = Integer.valueOf(bwProductDictionary.getpTerm()); // 产品期限
			if (1 == pTermType) {
				loanDate = pTerm * 30; // 产品期限为月时，乘以30，换算为天
			} else if (2 == pTermType) {
				loanDate = pTerm;
			}

			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("orderNo", thirdOrderId); // 第三方工单号
			dataMap.put("amount", bwOrder.getBorrowAmount()); // 授信贷款金额
			dataMap.put("poundage", bwOrder.getBorrowAmount() * 0.18); // 手续费
			dataMap.put("rate", "0"); // 日利率
			dataMap.put("overdueFine", verdueAccrualMoney); // 滞纳金
			dataMap.put("poundageType", "1"); // 扣除手续费的方式（1:从借款本金中扣除手续费 2:用户还款时支付手续费）
			dataMap.put("isEditAmount", "2"); // 是否允许用户修改贷款金额（1：允许 2：不允许）
			dataMap.put("isEditLoanDate", "2"); // 是否允许用户修改贷款天数（1：允许 2：不允许）
			dataMap.put("loanDate", String.valueOf(loanDate)); // 放款天数

			paramMap.put("thirdOrderId", thirdOrderId);// 第三方工单号
			paramMap.put("methodName", "checkThrough"); // 请求方法名（用于反射）
			paramMap.put("data", com.alibaba.fastjson.JSONObject.toJSONString(dataMap)); // 接口参数

			// 第四步：发送消息队列
			// mqProducer.sendMsg("sendStatus_" + channelId + "_" + thirdOrderId + "_" + statusId,
			// JSON.toJSONString(paramMap));
		} catch (Exception e) {
			logger.error("渠道请求失败》》channelId:" + channelId + ",orderId:" + orderId, e);
			return returnMap;
		}
		return returnMap;
	}

	/**
	 * 贷款钱包 - 审核拒绝（code0082）
	 * 
	 * @param paramMap
	 * @return
	 */
	@Override
	public Map<String, Object> loanWallet_sendOrderStatusMQ_checkReject(Map<String, String> paramMap) {
		Map<String, Object> returnMap = new HashMap<>();
		// 第一步：取参
		String channelId = paramMap.get("channelId"); // 渠道编码
		String orderId = paramMap.get("orderId"); // 工单编号
		String statusId = paramMap.get("statusId"); // 工单状态
		try {
			// 第二步：验证
			returnMap = loanWallet_validate(paramMap, new int[] { 7 });
			if (returnMap != null) {
				return returnMap;
			}

			// 第三步：组装参数
			String thirdOrderId = bwOrderRongService.findThirdOrderNoByOrderId(orderId); // 第三方工单号
			Map<String, String> dataMap = new HashMap<>();
			dataMap.put("orderNo", thirdOrderId); // 第三方工单号
			dataMap.put("message", "评分不足"); // 审批驳回原因

			paramMap.put("thirdOrderId", thirdOrderId);// 第三方工单号
			paramMap.put("methodName", "checkReject"); // 请求方法名（用于反射）
			paramMap.put("data", com.alibaba.fastjson.JSONObject.toJSONString(dataMap)); // 接口参数

			// 第四步：发送消息队列
			// mqProducer.sendMsg("sendStatus_" + channelId + "_" + thirdOrderId + "_" + statusId,
			// JSON.toJSONString(paramMap));
		} catch (Exception e) {
			logger.error("渠道请求失败》》channelId:" + channelId + ",orderId:" + orderId, e);
			return returnMap;
		}
		return returnMap;
	}

	/**
	 * 贷款钱包 - 回调（code0082）
	 * 
	 * @param paramMap
	 * @return
	 */
	@Override
	public Map<String, Object> loanWallet_sendOrderStatusMQ_callback(Map<String, String> paramMap) {
		Map<String, Object> returnMap = new HashMap<>();
		// 第一步：取参
		String channelId = paramMap.get("channelId"); // 渠道编码
		String orderId = paramMap.get("orderId"); // 工单编号
		String statusId = paramMap.get("statusId"); // 工单状态
		try {
			// 第二步：验证
			returnMap = loanWallet_validate(paramMap, new int[] { 5, 6 });
			if (returnMap != null) {
				return returnMap;
			}

			// 第三步：组装参数
			String thirdOrderId = bwOrderRongService.findThirdOrderNoByOrderId(orderId); // 第三方工单号
			Map<String, String> dataMap = new HashMap<>();
			dataMap.put("orderNo", thirdOrderId); // 第三方工单号
			dataMap.put("code", paramMap.get("code")); // 操作成功/操作失败（success/error）
			dataMap.put("message", paramMap.get("message")); // 失败返回失败原因
			dataMap.put("type", paramMap.get("type"));// 1:用户提现，2：用户还款/代扣款 3：用户绑卡

			paramMap.put("thirdOrderId", thirdOrderId);// 第三方工单号
			paramMap.put("methodName", "callback"); // 请求方法名（用于反射）
			paramMap.put("data", com.alibaba.fastjson.JSONObject.toJSONString(dataMap)); // 接口参数

			// 第四步：发送消息队列
			// mqProducer.sendMsg("sendStatus_" + channelId + "_" + thirdOrderId + "_" + statusId,
			// JSON.toJSONString(paramMap));
		} catch (Exception e) {
			logger.error("渠道请求失败》》channelId:" + channelId + ",orderId:" + orderId, e);
			return returnMap;
		}
		return returnMap;
	}

	/**
	 * 贷款钱包 - 公共方法 - 验证参数
	 * 
	 * @param paramMap
	 * @param orderStatus
	 * @return
	 */
	private Map<String, Object> loanWallet_validate(Map<String, String> paramMap, int[] orderStatus) {
		Map<String, Object> returnMap = new HashMap<>();
		// 第一步：取参
		String channelId = paramMap.get("channelId"); // 渠道编码
		String orderId = paramMap.get("orderId"); // 工单编号
		String statusId = paramMap.get("statusId"); // 工单状态
		try {
			// 第二步：验证参数
			if (CommUtils.isNull(channelId)) {
				returnMap.put("code", "1001");
				returnMap.put("version", "1.0");
				returnMap.put("message", "渠道编码为空");
				return returnMap;
			}

			if ("1".equals(channelId) || "2".equals(channelId) || "3".equals(channelId) || "4".equals(channelId)) {
				returnMap.put("code", "1001");
				returnMap.put("version", "1.0");
				returnMap.put("message", "非三方渠道");
				return returnMap;
			}

			if (CommUtils.isNull(orderId)) {
				returnMap.put("code", "1002");
				returnMap.put("version", "1.0");
				returnMap.put("message", "工单编号为空");
				return returnMap;
			}
			if (CommUtils.isNull(statusId)) {
				returnMap.put("code", "1003");
				returnMap.put("version", "1.0");
				returnMap.put("message", "工单状态为空");
				return returnMap;
			}

			// boolean isStatusExists = false;
			// for (int status : orderStatus) {
			// if (String.valueOf(status).equals(statusId) == true) {
			// isStatusExists = true;
			// break;
			// }
			// }
			// if (isStatusExists == false) {
			// returnMap.put("code", "1003");
			// returnMap.put("version", "1.0");
			// returnMap.put("message", "工单状态不符合推送要求");
			// return returnMap;
			// }

			String thirdOrderId = bwOrderRongService.findThirdOrderNoByOrderId(orderId);
			if (CommUtils.isNull(thirdOrderId)) {
				returnMap.put("code", "1005");
				returnMap.put("version", "1.0");
				returnMap.put("message", "渠道工单编号为空");
				return returnMap;
			}

			returnMap = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

}
