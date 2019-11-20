package com.waterelephant.service.impl;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.beadwallet.service.sms.dto.MessageDto;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.CapitalBaseService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.utils.RedisUtils;

@Service("capitalBaseService")
public class CapitalBaseServiceImpl implements CapitalBaseService {
	private Logger logger = Logger.getLogger(CapitalBaseServiceImpl.class);

	@Resource
	private BwOrderRongService bwOrderRongService;
	@Resource
	private IBwBankCardService bwBankCardService;
	@Resource
	private IBwRepaymentPlanService bwRepaymentPlanService;
	@Resource
	private BwBorrowerService bwBorrowerService;

	@Override
	public void pushOrderStatus(BwOrder bwOrder, Date repaymentTime) {
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

			// 待定
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

			// 12:渠道为好贷
			// String channel = String.valueOf(bwOrder.getChannel());
			// if ("12".equals(channel) || "81".equals(channel)) {
			// // Map<String, String> maps = haoDaiService.getLoanStatusData(bwOrder.getId());
			// // if (maps.get("code").equals("1000")) {
			// // 放完款推送数据好贷
			// // 获取对应好贷 订单
			// String thirdOrderId = bwOrderRongService.findThirdOrderNoByOrderId(String.valueOf(bwOrder.getId()));
			// BeadWalletHaoDaiService.sendOrderStatus(thirdOrderId, "3");
			// // }
			// PostLoanInfo postLoanInfo = haoDaiService.getLoanInfo(bwOrder.getId());
			// BeadWalletHaoDaiService.sendPostLoanInfo(postLoanInfo);
			// }
			// channelService.sendOrderStatus(CommUtils.toString(bwOrder.getChannel()),
			// CommUtils.toString(bwOrder.getId()), "14");
			// PostLoanInfo postLoanInfo = haoDaiService.getLoanInfo(bwOrder.getId());
			// BeadWalletHaoDaiService.sendPostLoanInfo(postLoanInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// // 根据工单id查询融360对应的订单号
		// try {
		// // logger.info("判断工单来源是否为融360");
		// if (11 == bwOrder.getChannel().intValue()) {
		// BwOrderRong bwOrderRong = new BwOrderRong();
		// bwOrderRong.setOrderId(bwOrder.getId());
		// bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
		// BwBankCard bwBankCard = new BwBankCard();
		// bwBankCard.setBorrowerId(bwOrder.getBorrowerId());
		// bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
		// /**
		// * 融360反馈
		// */
		// OrderFeedBackReq orderFeedBackReq = new OrderFeedBackReq();
		// orderFeedBackReq.setOrder_no(bwOrderRong.getThirdOrderNo());
		// orderFeedBackReq.setOrder_status(170);
		// logger.info("开始调用融360订单状态反馈接口," + orderFeedBackReq);
		// OrderFeedBackResp orderFeedBackResp = BeadWalletRong360Service.orderFeedBack(orderFeedBackReq);
		// logger.info("结束调用融360订单状态反馈接口," + orderFeedBackResp);
		//
		// /**
		// * 融360反馈
		// */
		// // 还款计划推送
		// BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService.getBwRepaymentPlanByOrderId(bwOrder.getId());//
		// （code0090）
		// double realityRepayMoney = 0;
		// if (bwRepaymentPlan != null && bwRepaymentPlan.getZjw() != null) {
		// realityRepayMoney = bwRepaymentPlan.getRealityRepayMoney();
		// }
		//
		// PushRepaymentReq pushRepaymentReq = new PushRepaymentReq();
		// RepaymentPlan plan = new RepaymentPlan();
		//
		// plan.setAmount(String.valueOf(realityRepayMoney));
		// String timestamp = String.valueOf(repaymentTime.getTime() / 1000);
		// plan.setDue_time(Integer.valueOf(timestamp).toString());
		// plan.setPay_type(4);
		// plan.setPeriod_no("1"); // 默认只有1期
		// String remark = "本金:" + bwOrder.getBorrowAmount();
		// plan.setRemark(remark);
		// plan.setBill_status(1); // 1.表示未到期（默认是1）
		//
		// List<RepaymentPlan> planList = new ArrayList<RepaymentPlan>();
		// planList.add(plan);
		//
		// pushRepaymentReq.setOrder_no(bwOrderRong.getThirdOrderNo());
		// pushRepaymentReq.setRepayment_plan(planList);
		// pushRepaymentReq.setBank_card(bwBankCard.getCardNo());
		// pushRepaymentReq.setOpen_bank(bwBankCard.getBankName());
		// logger.info("开始调用融360放款账单推送接口," + pushRepaymentReq);
		// PushRepaymentResp pushRepaymentResp = BeadWalletRong360Service.pushRepayment(pushRepaymentReq);
		// logger.info("结束调用融360放款账单推送接口，" + pushRepaymentResp);
		//
		// // 账单状态
		// RepaymentFeedBackReq repaymentFeedBackReq = new RepaymentFeedBackReq();
		// repaymentFeedBackReq.setOrder_no(bwOrderRong.getThirdOrderNo());
		// repaymentFeedBackReq.setPeriod_no("1");
		// repaymentFeedBackReq.setBill_status("1");
		// repaymentFeedBackReq.setRemark("未到期");
		// logger.info("开始调用融360账单状态反馈接口," + repaymentFeedBackReq);
		// RepaymentFeedBackResp repaymentFeedBackResp = BeadWalletRong360Service
		// .repaymentFeedBack(repaymentFeedBackReq);
		// logger.info("结束调用融360账单状态反馈接口," + repaymentFeedBackResp);
		// }
		// } catch (Exception e) {
		// logger.error("调用融360接口异常", e);
		// }
	}
}
