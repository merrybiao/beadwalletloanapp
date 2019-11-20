package com.waterelephant.capital.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.beadwallet.service.sms.dto.MessageDto;
import com.waterelephant.capital.service.WoNiuService;
import com.waterelephant.capital.woniu.WoNiuConstant;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwCapitalPush;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderPushInfo;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.service.BwCapitalOrderService;
import com.waterelephant.service.BwCapitalPushService;
import com.waterelephant.service.BwCapitalWithholdService;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwOrderPushInfoService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwRepaymentService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.service.impl.BwRepaymentPlanService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;

/**
 * 蜗牛聚财
 * 
 * 
 * Module:
 * 
 * WoNiuServiceImpl.java
 * 
 * @author 张博
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class WoNiuServiceImpl implements WoNiuService {
	private Logger logger = Logger.getLogger(WoNiuServiceImpl.class);
	@Resource
	private IBwOrderService bwOrderService;
	@Resource
	private IBwOrderPushInfoService bwOrderPushInfoService;
	@Resource
	private BwOrderRongService bwOrderRongService;
	@Resource
	private BwRepaymentPlanService bwRepaymentPlanService;
	@Resource
	private IBwBankCardService bwBankCardService;
	@Resource
	private BwCapitalPushService bwCapitalPushService;
	@Resource
	private BwProductDictionaryService bwProductDictionaryService;
	@Resource
	private BwOrderProcessRecordService bwOrderProcessRecordService;
	@Resource
	private BwCapitalOrderService bwCapitalOrderService;
	@Resource
	private BwCapitalWithholdService bwCapitalWithholdService;
	@Resource
	private IBwRepaymentService iBwRepaymentService;
	@Resource
	private BwBorrowerService bwBorrowerService;

	@Override
	public AppResponseResult saveCallBackSuccess(TreeMap<String, String> tm) {
		AppResponseResult appResponseResult = new AppResponseResult();
		Date now = new Date();
		String code = tm.get("code");
		String message = tm.get("message");
		String transferDate = tm.get("transferDate"); // 转账时间
		String orderNo = tm.get("thirdNo"); // 订单号

		try {
			// 查询我方订单
			BwOrder bwOrder = new BwOrder();
			bwOrder.setOrderNo(orderNo);
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);

			if (bwOrder == null) {
				appResponseResult.setCode("1006");
				appResponseResult.setMsg("工单信息不存在");
				appResponseResult.setResult("FAIL");
				return appResponseResult;
			}

			Long orderId = bwOrder.getId();
			BwCapitalPush bwCapitalPush = bwCapitalPushService.queryBwCapitalPush(orderId,
					Integer.parseInt(WoNiuConstant.CAPTITALID));
			BwOrderPushInfo bwOrderPushInfo = bwOrderPushInfoService.getOrderPushInfo(orderId,
					Integer.parseInt(WoNiuConstant.CAPTIALORDERPUSHID));

			if (bwCapitalPush != null) {
				if ((2 == bwCapitalPush.getPushStatus()) && "0".equals(code)) {
					appResponseResult.setCode("-1");
					appResponseResult.setMsg("该订单已经推送成功，请不要重复推送");
					appResponseResult.setResult("FAIL");
					logger.info("蜗牛推送处理结果：重复推送，当前工单编号：" + orderId);
					return appResponseResult;
				}
				bwCapitalPush.setCode(code);
				bwCapitalPush.setMsg(message);
				bwCapitalPush.setUpdateTime(now);
				bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
			} else {
				bwCapitalPush = new BwCapitalPush();
				bwCapitalPush.setOrderId(orderId);
				bwCapitalPush.setCreateTime(now);
				bwCapitalPush.setCapitalId(Integer.parseInt(WoNiuConstant.CAPTITALID));
				bwCapitalPush.setPushCount(0);
				bwCapitalPush.setCode(code);
				bwCapitalPush.setMsg(message);
				bwCapitalPush.setUpdateTime(now);
				bwCapitalPushService.save(bwCapitalPush);
			}

			// 如果是放款成功
			if ("0".equals(code) && StringUtils.isNotBlank(transferDate)) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				transferDate = DateUtil.getDateString(CommUtils.convertStringToDate(transferDate, "yyyyMMdd"),
						"yyyy-MM-dd");
				Date loanTime = null;
				loanTime = format.parse(transferDate);

				// 生成还款计划
				try {
					List<BwRepaymentPlan> bwRepaymentPlanList = bwRepaymentPlanService
							.listBwRepaymentPlanByOrderId(orderId);

					if (bwRepaymentPlanList != null && bwRepaymentPlanList.size() > 0) {
						appResponseResult.setCode("-1");
						appResponseResult.setMsg("该订单已经推送成功,请不要重复推送");
						appResponseResult.setResult("FAIL");
						logger.info("蜗牛推送处理结果：重复推送，当前工单号：" + orderId);
						return appResponseResult;
					}

					// 还款时间
					Date repaymentTime = null;
					if (2 == bwOrder.getProductType()) {
						bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateCapital(bwOrder, transferDate);
					} else {
						BwProductDictionary bwProductDictionary = bwProductDictionaryService
								.findBwProductDictionaryById(bwOrder.getProductId());

						String termType = null;
						String term = null;

						// 产品表
						if (bwProductDictionary != null) {
							termType = bwProductDictionary.getpTermType();
							term = bwProductDictionary.getpTerm();
						}

						if ("1".equals(termType)) {
							repaymentTime = MyDateUtils.addMonths(loanTime, StringUtil.toInteger(term));
						} else if ("2".equals(termType)) {
							repaymentTime = MyDateUtils.addDays(loanTime, StringUtil.toInteger(term));
						} else {
							repaymentTime = MyDateUtils.addDays(loanTime, 30);
						}

						// 根据工单信息和指定还款时间生成借款人还款计划
						bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder, repaymentTime,
								bwProductDictionary);
					}

					bwCapitalPush.setCode(code);
					bwCapitalPush.setMsg(message);
					bwCapitalPush.setUpdateTime(now);
					bwCapitalPush.setPushStatus(2);
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);

					// 更新bw_order_push_info表
					if (bwOrderPushInfo != null) {
						bwOrderPushInfo.setFullTime(loanTime);
						bwOrderPushInfo.setPushStatus(2); // 状态。0：推送失败，1：推送成功，2：接收成功，3：接收失败
						bwOrderPushInfo.setPushRemark("接收成功");
						bwOrderPushInfo.setUpdateTime(now);
						bwOrderPushInfo.setLoanTime(loanTime);
						bwOrderPushInfoService.updateOrderPushInfo(bwOrderPushInfo);
					}

					// 更新工单处理记录
					bwOrderProcessRecordService.saveOrUpdateByOrderIdRepay(orderId, loanTime);

					try {
						// 渠道放款成功 - 通知
						String channel = String.valueOf(bwOrder.getChannel());
						HashMap<String, String> hm = new HashMap<>();
						hm.put("channelId", channel);
						hm.put("orderId", String.valueOf(bwOrder.getId()));
						hm.put("orderStatus", String.valueOf(bwOrder.getStatusId()));
						hm.put("result", "放款成功");
						String hmData = JSON.toJSONString(hm);
						RedisUtils.rpush("tripartite:orderStatusNotify:" + channel, hmData);

						// 通知短信
						Long borrowerId = bwOrder.getBorrowerId();
						BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
						String sex = bwBorrower.getSex() + "";
						String userName = bwBorrower.getName();

						MessageDto messageDto = new MessageDto();
						messageDto.setBusinessScenario("1"); // 营销短信
						messageDto.setPhone(bwBorrower.getPhone());
						messageDto.setType("1"); // 文字短信
						if ("0".equals(sex)) {
							messageDto.setMsg("【水象分期】尊敬的" + userName + "女士，您在水象分期的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
						} else {
							messageDto.setMsg("【水象分期】尊敬的" + userName + "先生，您在水象分期的借款即将转入到您所绑定的银行卡内，请注意卡内余额变动。祝您生活愉快！");
						}

						RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
					} catch (Exception e) {
						e.printStackTrace();
					}

				} catch (Exception e) {
					bwCapitalPush.setPushStatus(3);
					bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);
					logger.info("蜗牛推送处理结果，保存信息出错，当前工单号：" + orderId);
					throw e;
				}

				appResponseResult.setCode("0");
				appResponseResult.setMsg("接收成功");
				appResponseResult.setResult("SUCCESS");
			} else {
				appResponseResult.setCode("-1");
				appResponseResult.setMsg("未知的请求");
				appResponseResult.setResult("FAIL");
			}

		} catch (Exception e) {
			logger.error("蜗牛放款回调接口异常" + e);
			appResponseResult.setCode("-1");
			appResponseResult.setMsg("放款回调接口异常");
			appResponseResult.setResult("FAIL");
		}
		return appResponseResult;
	}

	@Override
	public AppResponseResult saveCallBackFail(TreeMap<String, String> tm) {
		AppResponseResult appResponseResult = new AppResponseResult();
		try {
			String orderNo = tm.get("thirdNo");
			String message = tm.get("message");
			String code = tm.get("code");
			if (CommUtils.isNull(orderNo)) {
				appResponseResult.setCode("1000");
				appResponseResult.setMsg("参数错误");
				appResponseResult.setResult("FAIL");
				return appResponseResult;
			}

			BwOrder bwOrder = new BwOrder();
			bwOrder.setOrderNo(orderNo);
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);

			if (bwOrder == null) {
				appResponseResult.setCode("1006");
				appResponseResult.setMsg("订单不存在");
				appResponseResult.setResult("FAIL");
				return appResponseResult;
			}

			Long orderId = bwOrder.getId();
			BwOrderPushInfo bwOrderPushInfo = bwOrderPushInfoService.getOrderPushInfoByOrderId(orderId);
			if (bwOrderPushInfo == null) {
				appResponseResult.setCode("1006");
				appResponseResult.setMsg("订单不存在");
				appResponseResult.setResult("FAIL");
				return appResponseResult;
			}

			BwCapitalPush bwCapitalPush = bwCapitalPushService.queryBwCapitalPush(orderId,
					Integer.parseInt(WoNiuConstant.CAPTITALID));
			if (bwCapitalPush == null) {
				appResponseResult.setCode("1006");
				appResponseResult.setMsg("订单不存在");
				appResponseResult.setResult("FAIL");
				return appResponseResult;
			}

			// 打款失败
			// step01：订单推送表
			bwOrderPushInfo.setLoanTime(new Date());
			bwOrderPushInfo.setUpdateTime(new Date());
			bwOrderPushInfo.setPushStatus(3);
			bwOrderPushInfo.setPushRemark("打款失败");
			bwOrderPushInfoService.updateOrderPushInfo(bwOrderPushInfo);

			// step02：资方推送表
			bwCapitalPush.setPushCount(bwCapitalPush.getPushCount() + 1);
			bwCapitalPush.setPushStatus(3);
			bwCapitalPush.setCode(code);
			bwCapitalPush.setMsg(message);
			bwCapitalPushService.updateBwCapitalPush(bwCapitalPush);

			// step03:订单表
			bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			bwOrder.setUpdateTime(new Date());
			bwOrder.setStatusId(12L);
			bwOrderService.update(bwOrder);

			// step04:查询同等金额的订单并推送,把失败订单放入redis轮询匹配
			RedisUtils.lpush(WoNiuConstant.CAPITALFAIL, JSON.toJSONString(bwOrder));

			appResponseResult.setCode("0");
			appResponseResult.setMsg("接收成功");
			appResponseResult.setResult("SUCCESS");
		} catch (Exception e) {
			logger.error("蜗牛放款回调接口异常" + e);
			appResponseResult.setCode("-1");
			appResponseResult.setMsg("放款回调接口异常");
			appResponseResult.setResult("FAIL");
		}
		return appResponseResult;

	}

}
