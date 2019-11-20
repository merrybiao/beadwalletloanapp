package com.waterelephant.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beadwallet.service.entity.request.PostLoanInfo;
import com.beadwallet.service.rong360.entity.request.OrderFeedBackReq;
import com.beadwallet.service.rong360.entity.request.PushRepaymentReq;
import com.beadwallet.service.rong360.entity.request.RepaymentFeedBackReq;
import com.beadwallet.service.rong360.entity.request.RepaymentPlan;
import com.beadwallet.service.rong360.entity.response.OrderFeedBackResp;
import com.beadwallet.service.rong360.entity.response.PushRepaymentResp;
import com.beadwallet.service.rong360.entity.response.RepaymentFeedBackResp;
import com.beadwallet.service.rong360.service.BeadWalletRong360Service;
import com.beadwallet.service.serve.BeadWalletHaoDaiService;
import com.beadwallet.service.serve.BeadWalletYqhService;
import com.waterelephant.bd.ChannelService;
import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.dto.YiQiHaoLoanPushDto;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderPushInfo;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.haoDai.service.HaoDaiService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwOrderStatusRecordService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwOrderPushInfoService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.MyDateUtils;

import tk.mybatis.mapper.entity.Example;

/**
 * 一起好数据对接 20161215
 * 
 * @author duxiaoyong
 *
 */
@Controller
@RequestMapping("/yqh")
public class YiQiHaoController {

	private Logger logger = Logger.getLogger(YiQiHaoController.class);
	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private BwOrderRongService bwOrderRongService;
	@Autowired
	private IBwRepaymentPlanService bwRepaymentPlanService;
	@Autowired
	private IBwOrderPushInfoService bwOrderPushInfoService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private BwOrderStatusRecordService bwOrderStatusRecordService;

	@Resource
	private HaoDaiService haoDaiService;
	@Resource
	private ChannelService channelService;

	@ResponseBody
	@RequestMapping("/loanPush.do")
	public AppResponseResult loanPush(@RequestBody YiQiHaoLoanPushDto yiQiHaoLoanPushDto) {
		AppResponseResult result = new AppResponseResult();
		if (CommUtils.isNull(yiQiHaoLoanPushDto)) {
			result.setCode("1001");
			result.setMsg("参数格式错误");
			logger.info("一起好推送处理结果：参数格式错误。");
			return result;
		}
		if (CommUtils.isNull(yiQiHaoLoanPushDto.getAppid())) {
			result.setCode("1001");
			result.setMsg("appid为空");
			logger.info("一起好推送处理结果：appid为空。");
			return result;
		}
		if (CommUtils.isNull(yiQiHaoLoanPushDto.getSign())) {
			result.setCode("1001");
			result.setMsg("sign为空");
			logger.info("一起好推送处理结果：sign为空。");
			return result;
		}
		if (CommUtils.isNull(yiQiHaoLoanPushDto.getLoanSid())) {
			result.setCode("1001");
			result.setMsg("loanSid为空");
			logger.info("一起好推送处理结果：loanSid为空。");
			return result;
		}
		if (CommUtils.isNull(yiQiHaoLoanPushDto.getRepayDate())) {
			result.setCode("1001");
			result.setMsg("repayDate为空");
			logger.info("一起好推送处理结果：repayDate为空。");
			return result;
		}
		// 计算还款时间
		String repayDate = yiQiHaoLoanPushDto.getRepayDate();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date repayTime = null;
		try {
			repayTime = format.parse(repayDate);
			repayTime = MyDateUtils.addDays(repayTime, -1);
		} catch (Exception e) {
			logger.error("一起好推送处理结果：还款日期格式错误，当前工单号：" + yiQiHaoLoanPushDto.getLoanSid() + "，还款日期："
					+ yiQiHaoLoanPushDto.getRepayDate());
			e.printStackTrace();
			result.setCode("1001");
			result.setMsg("repayDate格式错误");
			return result;
		}
		try {
			boolean validateStatus = BeadWalletYqhService.validateRequestSign(yiQiHaoLoanPushDto);
			if (!validateStatus) {
				result.setCode("1002");
				result.setMsg("sign验证失败");
				logger.info("一起好推送处理结果：sign验证失败，当前工单号：" + yiQiHaoLoanPushDto.getLoanSid());
				return result;
			}
			BwOrder bwOrder = new BwOrder();
			bwOrder.setOrderNo(yiQiHaoLoanPushDto.getLoanSid());
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
			if (bwOrder == null) {
				result.setCode("1003");
				result.setMsg("loanSid不存在");
				logger.info("一起好推送处理结果：loanSid不存在，当前工单号：" + yiQiHaoLoanPushDto.getLoanSid());
				return result;
			}
			if (bwOrder.getStatusId().longValue() != 14) {
				result.setCode("1003");
				result.setMsg("loanSid不在可推送状态");
				logger.info("一起好推送处理结果：loanSid不在可推送状态，当前工单号：" + yiQiHaoLoanPushDto.getLoanSid());
				return result;
			}
			Example example = new Example(BwOrderPushInfo.class);
			List<Object> statusList = new ArrayList<>();
			statusList.add("1");// 推送成功
			statusList.add("2");// 接收成功
			statusList.add("3");// 接收失败
			example.createCriteria().andEqualTo("orderNo", yiQiHaoLoanPushDto.getLoanSid())
					.andEqualTo("financingChannel", 1).andIn("pushStatus", statusList);
			BwOrderPushInfo bwOrderPushInfo = bwOrderPushInfoService.getUniqueOrderPushInfoByExample(example);
			if (bwOrderPushInfo == null) {
				result.setCode("1003");
				result.setMsg("loanSid不存在");
				logger.info("一起好推送处理结果：loanSid不存在对应的推送记录，当前工单号：" + yiQiHaoLoanPushDto.getLoanSid());
				return result;
			} else if (bwOrderPushInfo.getPushStatus().intValue() == 2) {
				result.setCode("1004");
				result.setMsg("该loanSid已推送成功，请不要重复推送");
				logger.info("一起好推送处理结果：重复推送，当前工单号：" + yiQiHaoLoanPushDto.getLoanSid());
				return result;
			}
			Date now = new Date();
			bwOrderPushInfo.setUpdateTime(now);
			try {
				// 查询该工单对应的还款计划是否存在，如果存在直接返回成功给一起好
				boolean b = bwRepaymentPlanService.findBwRepaymentPlanByOrderNo(yiQiHaoLoanPushDto.getLoanSid());
				if (b) {
					result.setCode("1004");
					result.setMsg("该loanSid已推送成功，请不要重复推送");
					logger.info("一起好推送处理结果：重复推送，当前工单号：" + yiQiHaoLoanPushDto.getLoanSid());
					return result;
				}
				bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder, repayTime, null);
				bwOrderPushInfo.setFullTime(now);
				bwOrderPushInfo.setLoanTime(now);
				bwOrderPushInfo.setPushStatus(2);// 接收成功
				bwOrderPushInfo.setPushRemark("接收成功");
				bwOrderPushInfoService.updateOrderPushInfo(bwOrderPushInfo);
				logger.info("添加放款成功弹窗：" + yiQiHaoLoanPushDto.getLoanSid());
				// 往BwOrderStatusRecord表添加记录用于弹框显示
				bwOrderStatusRecordService.insertRecord(bwOrder, ActivityConstant.BWORDERSTATUSRECORD_MSG.MSG_SUCCESS,
						ActivityConstant.BWORDERSTATUSRECORD_DIALOGSTYLE.DIALOGSTYLE_LOANSUCCESS);
				logger.info("添加放款成功弹窗成功：" + yiQiHaoLoanPushDto.getLoanSid());

				try {
					// // 12:渠道为好贷
					// String channel = String.valueOf(bwOrder.getChannel());
					// if ("12".equals(channel) || "81".equals(channel)) {
					// // Map<String, String> maps = haoDaiService.getLoanStatusData(bwOrder.getId());
					// // if (maps.get("code").equals("1000")) {
					// // 放完款推送数据好贷
					// // 获取对应好贷 订单
					// String thirdOrderId = bwOrderRongService
					// .findThirdOrderNoByOrderId(String.valueOf(bwOrder.getId()));
					// BeadWalletHaoDaiService.sendOrderStatus(thirdOrderId, "3");
					// // }
					// PostLoanInfo postLoanInfo = haoDaiService.getLoanInfo(bwOrder.getId());
					// BeadWalletHaoDaiService.sendPostLoanInfo(postLoanInfo);
					// }
					channelService.sendOrderStatus(CommUtils.toString(bwOrder.getChannel()),
							CommUtils.toString(bwOrder.getId()), "14");
					PostLoanInfo postLoanInfo = haoDaiService.getLoanInfo(bwOrder.getId());
					BeadWalletHaoDaiService.sendPostLoanInfo(postLoanInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// 根据工单id查询融360对应的订单号
				try {
					logger.info("判断工单来源是否为融360");
					if (11 == bwOrder.getChannel().intValue()) {
						BwOrderRong bwOrderRong = new BwOrderRong();
						bwOrderRong.setOrderId(bwOrder.getId());
						bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
						BwBankCard bwBankCard = new BwBankCard();
						bwBankCard.setBorrowerId(bwOrder.getBorrowerId());
						bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
						/**
						 * 融360反馈
						 */
						OrderFeedBackReq orderFeedBackReq = new OrderFeedBackReq();
						orderFeedBackReq.setOrder_no(bwOrderRong.getThirdOrderNo());
						orderFeedBackReq.setOrder_status(170);
						logger.info("开始调用融360订单状态反馈接口," + orderFeedBackReq);
						OrderFeedBackResp orderFeedBackResp = BeadWalletRong360Service.orderFeedBack(orderFeedBackReq);
						logger.info("结束调用融360订单状态反馈接口," + orderFeedBackResp);

						/**
						 * 融360反馈
						 */
						// 还款计划推送
						PushRepaymentReq pushRepaymentReq = new PushRepaymentReq();
						RepaymentPlan plan = new RepaymentPlan();

						plan.setAmount(String.valueOf(bwOrder.getBorrowAmount()));
						String timestamp = String.valueOf(repayTime.getTime() / 1000);
						plan.setDue_time(Integer.valueOf(timestamp).toString());
						plan.setPay_type(5);
						plan.setPeriod_no("1"); // 默认只有1期
						String remark = "本金:" + bwOrder.getBorrowAmount();
						plan.setRemark(remark);
						plan.setBill_status(1); // 1.表示未到期（默认是1）

						List<RepaymentPlan> planList = new ArrayList<RepaymentPlan>();
						planList.add(plan);

						pushRepaymentReq.setOrder_no(bwOrderRong.getThirdOrderNo());
						pushRepaymentReq.setRepayment_plan(planList);
						pushRepaymentReq.setBank_card(bwBankCard.getCardNo());
						pushRepaymentReq.setOpen_bank(bwBankCard.getBankName());
						logger.info("开始调用融360放款账单推送接口," + pushRepaymentReq);
						PushRepaymentResp pushRepaymentResp = BeadWalletRong360Service.pushRepayment(pushRepaymentReq);
						logger.info("结束调用融360放款账单推送接口，" + pushRepaymentResp);

						// 账单状态
						RepaymentFeedBackReq repaymentFeedBackReq = new RepaymentFeedBackReq();
						repaymentFeedBackReq.setOrder_no(bwOrderRong.getThirdOrderNo());
						repaymentFeedBackReq.setPeriod_no("1");
						repaymentFeedBackReq.setBill_status("1");
						repaymentFeedBackReq.setRemark("未到期");
						logger.info("开始调用融360账单状态反馈接口," + repaymentFeedBackReq);
						RepaymentFeedBackResp repaymentFeedBackResp = BeadWalletRong360Service
								.repaymentFeedBack(repaymentFeedBackReq);
						logger.info("结束调用融360账单状态反馈接口," + repaymentFeedBackResp);
					}
				} catch (Exception e) {
					logger.error("调用融360接口异常", e);
				}

				logger.info("一起好推送处理结果：接收成功，当前工单号：" + yiQiHaoLoanPushDto.getLoanSid() + "，还款日期："
						+ yiQiHaoLoanPushDto.getRepayDate());
			} catch (Exception e) {
				bwOrderPushInfo.setPushStatus(3);// 接收失败
				bwOrderPushInfo.setPushRemark("保存信息出错");
				bwOrderPushInfoService.updateOrderPushInfo(bwOrderPushInfo);
				logger.info("一起好推送处理结果：保存信息出错，当前工单号：" + yiQiHaoLoanPushDto.getLoanSid());
				throw e;
			}
			result.setCode("0000");
			result.setMsg("推送成功");
		} catch (Exception e) {
			logger.error("保存一起好推送信息出错，工单号[" + yiQiHaoLoanPushDto.getLoanSid() + "]，错误信息：" + e.getMessage());
			e.printStackTrace();
			result.setCode("9999");
			result.setMsg("接口错误");
		}
		return result;
	}

	/**
	 * 借款人图片查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getBorrowerPic.do")
	public String getBorrowerPic(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String orderNo = request.getParameter("order_no");
		List<String> allpaths = new ArrayList<String>();
		if (CommUtils.isNull(orderNo)) {
			modelMap.put("allpaths", allpaths);
			return "pic";
		}
		try {
			Long orderId = bwOrderService.findOrderIdByNo(orderNo);
			List<String> paths = bwOrderService.findPathByOrderId(orderId);
			if (!CommUtils.isNull(paths)) {
				for (String path : paths) {
					path = "http://img.beadwallet.com/" + path;
					allpaths.add(path);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelMap.put("allpaths", allpaths);
		return "pic";
	}

	/**
	 * 模拟一起好放款回调
	 * 
	 * @param resquest
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loanPushTest.do")
	public AppResponseResult loanPushTest(HttpServletRequest resquest) {
		YiQiHaoLoanPushDto yiQiHaoLoanPushDto = new YiQiHaoLoanPushDto();
		yiQiHaoLoanPushDto.setAppid("abc");
		yiQiHaoLoanPushDto.setSign("abc");
		yiQiHaoLoanPushDto.setRepayDate(resquest.getParameter("repayDate"));
		yiQiHaoLoanPushDto.setLoanSid(resquest.getParameter("orderId"));
		AppResponseResult result = new AppResponseResult();
		if (CommUtils.isNull(yiQiHaoLoanPushDto)) {
			result.setCode("1001");
			result.setMsg("参数格式错误");
			logger.info("一起好推送处理结果：参数格式错误。");
			return result;
		}
		if (CommUtils.isNull(yiQiHaoLoanPushDto.getAppid())) {
			result.setCode("1001");
			result.setMsg("appid为空");
			logger.info("一起好推送处理结果：appid为空。");
			return result;
		}
		if (CommUtils.isNull(yiQiHaoLoanPushDto.getSign())) {
			result.setCode("1001");
			result.setMsg("sign为空");
			logger.info("一起好推送处理结果：sign为空。");
			return result;
		}
		if (CommUtils.isNull(yiQiHaoLoanPushDto.getLoanSid())) {
			result.setCode("1001");
			result.setMsg("loanSid为空");
			logger.info("一起好推送处理结果：loanSid为空。");
			return result;
		}
		if (CommUtils.isNull(yiQiHaoLoanPushDto.getRepayDate())) {
			result.setCode("1001");
			result.setMsg("repayDate为空");
			logger.info("一起好推送处理结果：repayDate为空。");
			return result;
		}
		// 计算还款时间
		String repayDate = yiQiHaoLoanPushDto.getRepayDate();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date repayTime = null;
		try {
			repayTime = format.parse(repayDate);
			repayTime = MyDateUtils.addDays(repayTime, -1);
		} catch (Exception e) {
			logger.error("一起好推送处理结果：还款日期格式错误，当前工单号：" + yiQiHaoLoanPushDto.getLoanSid() + "，还款日期："
					+ yiQiHaoLoanPushDto.getRepayDate());
			e.printStackTrace();
			result.setCode("1001");
			result.setMsg("repayDate格式错误");
			return result;
		}
		try {
			// boolean validateStatus = BeadWalletYqhService.validateRequestSign(yiQiHaoLoanPushDto);
			// if (!validateStatus) {
			// result.setCode("1002");
			// result.setMsg("sign验证失败");
			// logger.info("一起好推送处理结果：sign验证失败，当前工单号：" + yiQiHaoLoanPushDto.getLoanSid());
			// return result;
			// }
			BwOrder bwOrder = new BwOrder();
			bwOrder.setId(Long.parseLong(yiQiHaoLoanPushDto.getLoanSid()));
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
			if (bwOrder == null) {
				result.setCode("1003");
				result.setMsg("loanSid不存在");
				logger.info("一起好推送处理结果：loanSid不存在，当前工单号：" + yiQiHaoLoanPushDto.getLoanSid());
				return result;
			}
			if (bwOrder.getStatusId().longValue() != 14) {
				result.setCode("1003");
				result.setMsg("loanSid不在可推送状态");
				logger.info("一起好推送处理结果：loanSid不在可推送状态，当前工单号：" + yiQiHaoLoanPushDto.getLoanSid());
				return result;
			}
			Example example = new Example(BwOrderPushInfo.class);
			List<Object> statusList = new ArrayList<>();
			statusList.add("1");// 推送成功
			statusList.add("2");// 接收成功
			statusList.add("3");// 接收失败
			example.createCriteria().andEqualTo("orderNo", bwOrder.getOrderNo()).andEqualTo("financingChannel", 1)
					.andIn("pushStatus", statusList);
			BwOrderPushInfo bwOrderPushInfo = bwOrderPushInfoService.getUniqueOrderPushInfoByExample(example);
			if (bwOrderPushInfo == null) {
				result.setCode("1003");
				result.setMsg("loanSid不存在");
				logger.info("一起好推送处理结果：loanSid不存在对应的推送记录，当前工单号：" + yiQiHaoLoanPushDto.getLoanSid());
				return result;
			} else if (bwOrderPushInfo.getPushStatus().intValue() == 2) {
				result.setCode("1004");
				result.setMsg("该loanSid已推送成功，请不要重复推送");
				logger.info("一起好推送处理结果：重复推送，当前工单号：" + yiQiHaoLoanPushDto.getLoanSid());
				return result;
			}
			Date now = new Date();
			bwOrderPushInfo.setUpdateTime(now);
			try {
				// 查询该工单对应的还款计划是否存在，如果存在直接返回成功给一起好
				boolean b = bwRepaymentPlanService.findBwRepaymentPlanByOrderNo(yiQiHaoLoanPushDto.getLoanSid());
				if (b) {
					result.setCode("1004");
					result.setMsg("该loanSid已推送成功，请不要重复推送");
					logger.info("一起好推送处理结果：重复推送，当前工单号：" + yiQiHaoLoanPushDto.getLoanSid());
					return result;
				}
				bwRepaymentPlanService.saveRepaymentPlanByOrderAndReapyDateNew(bwOrder, repayTime, null);
				bwOrderPushInfo.setFullTime(now);
				bwOrderPushInfo.setLoanTime(now);
				bwOrderPushInfo.setPushStatus(2);// 接收成功
				bwOrderPushInfo.setPushRemark("接收成功");
				bwOrderPushInfoService.updateOrderPushInfo(bwOrderPushInfo);
				logger.info("添加放款成功弹窗：" + yiQiHaoLoanPushDto.getLoanSid());
				// 往BwOrderStatusRecord表添加记录用于弹框显示
				bwOrderStatusRecordService.insertRecord(bwOrder, ActivityConstant.BWORDERSTATUSRECORD_MSG.MSG_SUCCESS,
						ActivityConstant.BWORDERSTATUSRECORD_DIALOGSTYLE.DIALOGSTYLE_LOANSUCCESS);
				logger.info("添加放款成功弹窗成功：" + yiQiHaoLoanPushDto.getLoanSid());

				try {
					// // 12:渠道为好贷
					String channel = String.valueOf(bwOrder.getChannel());
					if ("12".equals(channel) || "81".equals(channel)) {
						// Map<String, String> maps = haoDaiService.getLoanStatusData(bwOrder.getId());
						// if (maps.get("code").equals("1000")) {
						// 放完款推送数据好贷
						// 获取对应好贷 订单
						String thirdOrderId = bwOrderRongService
								.findThirdOrderNoByOrderId(String.valueOf(bwOrder.getId()));
						BeadWalletHaoDaiService.sendOrderStatus(thirdOrderId, "3");
						// }
						PostLoanInfo postLoanInfo = haoDaiService.getLoanInfo(bwOrder.getId());
						BeadWalletHaoDaiService.sendPostLoanInfo(postLoanInfo);
					}
					// channelService.sendOrderStatus(CommUtils.toString(bwOrder.getChannel()),
					// CommUtils.toString(bwOrder.getId()), "14");
					PostLoanInfo postLoanInfo = haoDaiService.getLoanInfo(bwOrder.getId());
					BeadWalletHaoDaiService.sendPostLoanInfo(postLoanInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// 根据工单id查询融360对应的订单号
				try {
					logger.info("判断工单来源是否为融360");
					if (11 == bwOrder.getChannel().intValue()) {
						BwOrderRong bwOrderRong = new BwOrderRong();
						bwOrderRong.setOrderId(bwOrder.getId());
						bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
						BwBankCard bwBankCard = new BwBankCard();
						bwBankCard.setBorrowerId(bwOrder.getBorrowerId());
						bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
						/**
						 * 融360反馈
						 */
						OrderFeedBackReq orderFeedBackReq = new OrderFeedBackReq();
						orderFeedBackReq.setOrder_no(bwOrderRong.getThirdOrderNo());
						orderFeedBackReq.setOrder_status(170);
						logger.info("开始调用融360订单状态反馈接口," + orderFeedBackReq);
						OrderFeedBackResp orderFeedBackResp = BeadWalletRong360Service.orderFeedBack(orderFeedBackReq);
						logger.info("结束调用融360订单状态反馈接口," + orderFeedBackResp);

						/**
						 * 融360反馈
						 */
						// 还款计划推送
						PushRepaymentReq pushRepaymentReq = new PushRepaymentReq();
						RepaymentPlan plan = new RepaymentPlan();

						plan.setAmount(String.valueOf(bwOrder.getBorrowAmount()));
						String timestamp = String.valueOf(repayTime.getTime() / 1000);
						plan.setDue_time(Integer.valueOf(timestamp).toString());
						plan.setPay_type(4);
						plan.setPeriod_no("1"); // 默认只有1期
						String remark = "本金:" + bwOrder.getBorrowAmount();
						plan.setRemark(remark);
						plan.setBill_status(1); // 1.表示未到期（默认是1）

						List<RepaymentPlan> planList = new ArrayList<RepaymentPlan>();
						planList.add(plan);

						pushRepaymentReq.setOrder_no(bwOrderRong.getThirdOrderNo());
						pushRepaymentReq.setRepayment_plan(planList);
						pushRepaymentReq.setBank_card(bwBankCard.getCardNo());
						pushRepaymentReq.setOpen_bank(bwBankCard.getBankName());
						logger.info("开始调用融360放款账单推送接口," + pushRepaymentReq);
						PushRepaymentResp pushRepaymentResp = BeadWalletRong360Service.pushRepayment(pushRepaymentReq);
						logger.info("结束调用融360放款账单推送接口，" + pushRepaymentResp);

						// 账单状态
						RepaymentFeedBackReq repaymentFeedBackReq = new RepaymentFeedBackReq();
						repaymentFeedBackReq.setOrder_no(bwOrderRong.getThirdOrderNo());
						repaymentFeedBackReq.setPeriod_no("1");
						repaymentFeedBackReq.setBill_status("1");
						repaymentFeedBackReq.setRemark("未到期");
						logger.info("开始调用融360账单状态反馈接口," + repaymentFeedBackReq);
						RepaymentFeedBackResp repaymentFeedBackResp = BeadWalletRong360Service
								.repaymentFeedBack(repaymentFeedBackReq);
						logger.info("结束调用融360账单状态反馈接口," + repaymentFeedBackResp);
					}
				} catch (Exception e) {
					logger.error("调用融360接口异常", e);
				}

				logger.info("一起好推送处理结果：接收成功，当前工单号：" + yiQiHaoLoanPushDto.getLoanSid() + "，还款日期："
						+ yiQiHaoLoanPushDto.getRepayDate());
			} catch (Exception e) {
				bwOrderPushInfo.setPushStatus(3);// 接收失败
				bwOrderPushInfo.setPushRemark("保存信息出错");
				bwOrderPushInfoService.updateOrderPushInfo(bwOrderPushInfo);
				logger.info("一起好推送处理结果：保存信息出错，当前工单号：" + yiQiHaoLoanPushDto.getLoanSid());
				throw e;
			}
			result.setCode("0000");
			result.setMsg("推送成功");
		} catch (Exception e) {
			logger.error("保存一起好推送信息出错，工单号[" + yiQiHaoLoanPushDto.getLoanSid() + "]，错误信息：" + e.getMessage());
			e.printStackTrace();
			result.setCode("9999");
			result.setMsg("接口错误");
		}
		return result;
	}

}
