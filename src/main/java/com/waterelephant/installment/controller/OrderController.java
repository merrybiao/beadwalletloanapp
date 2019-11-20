/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.installment.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.constants.OrderStatusConstant;
import com.waterelephant.entity.ActivityInfo;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwCheckRecord;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.entity.BwZmxyGrade;
import com.waterelephant.faceID.entity.FaceIDOrderAuthDto;
import com.waterelephant.installment.service.OrderService;
import com.waterelephant.linkface.utils.LinkfaceConstant;
import com.waterelephant.service.ActivityInfoService;
import com.waterelephant.service.BwBlacklistService;
import com.waterelephant.service.BwCheckRecordService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.service.BwZmxyGradeService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.NumberUtil;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.StringUtil;

/**
 * 工单处理控制器
 * 
 * Module:
 * 
 * OrderController.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Controller
@RequestMapping("/app/order")
public class OrderController {

	private Logger logger = Logger.getLogger(OrderController.class);
	@Resource
	private OrderService orderService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private BwRejectRecordService bwRejectRecordService;
	@Autowired
	private BwZmxyGradeService bwZmxyGradeService;
	@Autowired
	private BwOrderAuthService bwOrderAuthService;
	@Autowired
	private BwBlacklistService bwBlacklistService;
	@Autowired
	private BwCheckRecordService bwCheckRecordService;
	@Autowired
	private ActivityInfoService activityInfoService;

	/**
	 * 查询主页还款列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getOrderLoanList.do")
	public AppResponseResult getOrderLoanList(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			String borrowerId = request.getParameter("borrowerId");
			logger.info("【OrderController.getOrderLoanList】borrowerId：" + borrowerId);
			if (CommUtils.isNull(borrowerId)) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg("borrowerId不能为空");
				result.setResult(false);// 处理失败
				return result;
			}
			// 根据借款人Id查询借款人单期工单和分期工单
			List<Map<String, Object>> list = orderService.getOrderLoanList(Long.parseLong(borrowerId));
			logger.info("【OrderController.getOrderLoanList】borrowerId：" + borrowerId + ",还款列表list：" + list);
			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg("查询还款列表成功");
			result.setResult(list);
			return result;
		} catch (Exception e) {
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
			result.setResult(false);// 处理失败
			logger.error("系统异常", e);
			return result;
		}
	}

	/**
	 * 9 查询借款记录-现金分期详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getOrderData.do")
	public AppResponseResult getOrderData(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			String orderId = request.getParameter("orderId");
			logger.info("【OrderController.getOrderData】orderId：" + orderId);
			if (CommUtils.isNull(orderId)) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg("orderId不能为空");
				result.setResult(false);// 处理失败
				return result;
			}
			// 根据借款人Id查询借款人单期工单和分期工单
			result = orderService.getMultiOrderDetail(Long.parseLong(orderId));
			logger.info("【OrderController.getOrderData】orderId：" + orderId + ",现金分期详情result：" + result.getResult());
			return result;
		} catch (Exception e) {
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
			result.setResult(false);// 处理失败
			logger.error("系统异常", e);
			return result;
		}
	}

	/**
	 * 查询借款记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getOrderList.do")
	public AppResponseResult getOrderList(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			String borrowerId = request.getParameter("borrowerId");
			logger.info("【OrderController.getOrderList】borrowerId：" + borrowerId);
			if (CommUtils.isNull(borrowerId)) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg("borrowerId不能为空");
				result.setResult(false);// 处理失败
				return result;
			}
			// 查询借款记录
			List<Map<String, Object>> list = orderService.getOrderList(Long.parseLong(borrowerId));
			logger.info("【OrderController.getOrderList】borrowerId：" + borrowerId + "，借款记录list：" + list);
			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
			result.setResult(list);
			return result;
		} catch (Exception e) {
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
			result.setResult(false);// 处理失败
			logger.error("系统异常", e);
			return result;
		}
	}

	/**
	 * 查询首页工单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getIndexOrders.do")
	public AppResponseResult getIndexOrders(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			String borrowerId = request.getParameter("borrowerId");
			logger.info("【OrderController.getIndexOrders】borrowerId：" + borrowerId);
			if (CommUtils.isNull(borrowerId)) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg("borrowerId不能为空");
				result.setResult(false);// 处理失败
				return result;
			}

			// 根据借款人Id查询借款人单期工单和分期工单
			Map<String, Object> map = orderService.saveIndexOrders(Long.parseLong(borrowerId));
			logger.info("【OrderController.getIndexOrders】borrowerId：" + borrowerId + "，首页工单查询结果map：" + map);

			// 登录成功将登录数量加入redis====start
			ActivityInfo activity = new ActivityInfo();
			activity.setActivityType("3");// 抽奖
			activity.setStatus(1);// 是否启用活动（0、否，1、是）
			activity = activityInfoService.queryActivityInfo(activity);
			if (!CommUtils.isNull(activity)) {
				Long start_time = activity.getStartTime().getTime();
				Long end_time = activity.getEndTime().getTime();
				Long cur_time = new Date().getTime();
				if (start_time <= cur_time && cur_time <= end_time) {// 判断是否在活动时间内
					if (RedisUtils.hexists("activity:app_login:count", borrowerId)) {
						map.put("show_activity", 0);// 0不弹框,首次登陆才弹框
					} else {
						RedisUtils.hset("activity:app_login:count", borrowerId, "1");
						map.put("show_activity", 1);// 1弹框
						// 每位用户每天登陆水象分期APP进入活动页面即获得1次翻牌机会。
						int chance_count = 0;
						if (RedisUtils.hexists("activity:chance:count", borrowerId)) {
							chance_count = Integer.parseInt(RedisUtils.hget("activity:chance:count", borrowerId));
						}
						RedisUtils.hset("activity:chance:count", borrowerId, String.valueOf(chance_count + 1));
					}
					// 签约成功的弹窗
					if (RedisUtils.hexists("activity:addit_pass:count", borrowerId)) {
						int addit_pass_count = Integer
								.parseInt(RedisUtils.hget("activity:addit_pass:count", borrowerId));
						if (addit_pass_count > 0) {
							if (!RedisUtils.hexists("activity:addit_pass:popup", borrowerId)) {
								map.put("show_activity", 2);// 弹框
								RedisUtils.hset("activity:addit_pass:popup", borrowerId, "1");
							}
						}
					}
				}
			}
			// ====end

			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
			result.setResult(map);
			return result;
		} catch (Exception e) {
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
			result.setResult(false);// 处理失败
			logger.error("【OrderController.getIndexOrders】系统异常", e);
			return result;
		}
	}

	/**
	 * 根据borrowerId和productType查询工单认证信息
	 */
	@ResponseBody
	@RequestMapping("/getOrderAuth.do")
	public AppResponseResult getOrderAuth(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult respResult = new AppResponseResult();
		try {
			String borrowerId = request.getParameter("borrowerId");
			String productType = request.getParameter("productType");
			logger.info("【OrderController.getOrderAuth】borrowerId：" + borrowerId + ",productType=" + productType);
			if (CommUtils.isNull(borrowerId)) {
				respResult.setCode(ActivityConstant.ErrorCode.FAIL);
				respResult.setMsg("borrowerId不能为空");
				return respResult;
			}
			if (CommUtils.isNull(productType)) {
				respResult.setCode(ActivityConstant.ErrorCode.FAIL);
				respResult.setMsg("productType不能为空");
				return respResult;
			}
			// 获取借款人信息
			BwBorrower borrower = new BwBorrower();
			borrower.setId(Long.parseLong(borrowerId));
			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
			if (CommUtils.isNull(borrower)) {
				respResult.setCode(ActivityConstant.ErrorCode.FAIL);
				logger.info("借款人" + borrowerId + "不存在");
				respResult.setMsg("借款人不存在");
				return respResult;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("phone", borrower.getPhone() == null ? "" : borrower.getPhone());

			map.put("operators", 0);// 运行商认证
			map.put("borrowMsg", 0);// 个人信息认证
			map.put("borrowCart", 0);// 身份证认证
			map.put("zmxy", 0);// 芝麻信用认证
			map.put("sort", "");// 名单认证
			map.put("photoState", "");
			map.put("shebaoStatu", 0);// 社保认证
			map.put("gjjStatu", 0);// 公积金认证
			map.put("emailStatu", 0);// 邮箱认证
			map.put("taobaoStatu", 0);// 淘宝认证
			map.put("jdStatu", "");// 京东认证
			map.put("creditLimit", 0L);
			String checkMsg = "";// 审核消息
			boolean canUpdate = false;// 是否能修改
			boolean canShowDetail = false;// 是否显示详情
			String detailMsg = "";// 审核详情
			String checkStatus = "";// 审核状态
			map.put("checkMsg", OrderStatusConstant.ORDER_CHECK_BEFORE_MSG);
			map.put("canUpdate", false);
			map.put("canShowDetail", false);
			map.put("detailMsg", "");
			map.put("checkStatus", "");
			map.put("verifySource", LinkfaceConstant.VERIFYSOURCE_CURRENT); // 返回给前端，用于判断是否已认证（code0088）
			/// 获取最新的工单
			BwOrder bwOrder = orderService.getOrderByProductType(borrower.getId(), Integer.parseInt(productType));
			Long statusId = null;
			Long order_id = null;
			Integer auth_channel = null;
			String orderNo = null;
			if (!CommUtils.isNull(bwOrder)) {
				statusId = bwOrder.getStatusId();
				order_id = bwOrder.getId();
				auth_channel = borrower.getChannel();
				orderNo = bwOrder.getOrderNo();
			}

			map.put("orderId", order_id);
			map.put("statusId", statusId);
			map.put("orderNo", orderNo);
			// 获取芝麻信用 大于30天删除芝麻信用
			BwZmxyGrade bwZmxyGrade = bwZmxyGradeService.findZmxyGradeByBorrowerId(borrower.getId());
			if (bwZmxyGrade != null) {
				Date updateTime = bwZmxyGrade.getUpdateTime();
				Date now = new Date();
				int interval = MyDateUtils.getDaySpace(updateTime, now);
				BwOrderAuth bwOrderAuth = bwOrderAuthService.findBwOrderAuth(order_id, 4);
				if (interval > 30) {
					logger.info("【OrderController.getOrderAuth】borrowerId：" + borrowerId + "，芝麻信用认证间隔时间为：" + interval);
					if (bwOrderAuth != null) {
						bwOrderAuthService.deleteBwOrderAuth(bwOrderAuth);
					}
				} else {
					if (bwOrderAuth == null) {
						bwOrderAuth = new BwOrderAuth();
						bwOrderAuth.setAuth_channel(auth_channel);
						bwOrderAuth.setAuth_type(4);
						bwOrderAuth.setCreateTime(now);
						bwOrderAuth.setOrderId(order_id);
						bwOrderAuth.setUpdateTime(now);
						bwOrderAuthService.saveBwOrderAuth(bwOrderAuth);
					}
				}
			}

			// 以前表未使用blacklist记录黑名单，先查询blacklist判断用户是否是黑名单
			int sort = bwBlacklistService.findAdoptTypeByCard(borrower.getIdCard());// 查询是否黑名单 灰名单
			// 查询记录并返回
			BwRejectRecord record = new BwRejectRecord();
			logger.info("【OrderController.getOrderAuth】borrowerId：" + borrowerId + "，工单Id：" + order_id);
			record.setOrderId(order_id);
			record = bwRejectRecordService.findBwRejectRecordByAtta(record);
			if (!CommUtils.isNull(record)) {
				if (sort != 1) {
					logger.info("认证被拒记录：" + record.toString());
					Integer rejectType = record.getRejectType();
					if (rejectType == 1) {
						sort = 2;
					}
					if (rejectType == 0) {
						sort = 1;
					}
				}
			}

			logger.info("【OrderController.getOrderAuth】borrowerId：" + borrowerId + "， sort：" + sort
					+ "（0普通用户1：黑名单，2：灰名单拒，3：白名单）");
			if (null != statusId) {
				// 审核中
				if (statusId == 1) {
					checkStatus = "0";
					checkMsg = OrderStatusConstant.ORDER_CHECK_BEFORE_MSG;
				}
				// 审核中
				if (statusId == 2 || statusId == 3) {
					checkMsg = OrderStatusConstant.ORDER_CHECK_LODING_MSG;
					checkStatus = "1";
				}
				// 拒绝
				if (statusId == 7) {
					checkStatus = "2";
					if (sort == 1) {// 黑名单
						checkMsg = OrderStatusConstant.ORDER_CHECK_FAILURE_MSG;
					} else if (sort == 2) {// 灰名单
						Date whiteTime = bwOrderService
								.findInstalmentBorrowWhiteTimeByBorrowId(borrower.getId().toString(), productType);// 获得分期灰名单的拒绝30天后的时间
						checkMsg = OrderStatusConstant.ORDER_CHECK_FAILURE_MSG;
						if (!CommUtils.isNull(whiteTime)) {
							if (MyDateUtils.getDaySpace(whiteTime, new Date()) >= 0) {
								canUpdate = true;
								detailMsg = "因系统评分不足，您的审核未通过！您可以在"
										+ DateUtil.getDateString(whiteTime, DateUtil.yyyy_MM_dd) + "以后重新认证！";
								logger.info("灰名单到期时间：" + whiteTime);
							} else {
								canShowDetail = true;
								detailMsg = "因系统评分不足，您的审核未通过！您可以在"
										+ DateUtil.getDateString(whiteTime, DateUtil.yyyy_MM_dd) + "以后重新认证！";
								logger.info("灰名单到期时间：" + whiteTime);
							}
						}
					}
				}
				// 撤回
				if (statusId == 8) {
					checkStatus = "3";
					checkMsg = OrderStatusConstant.ORDER_CHECK_BACK_MSG;
					canUpdate = true;
					canShowDetail = true;
					BwCheckRecord checkRecord = bwCheckRecordService
							.findNewWithdrawByOrderId(StringUtil.toString(bwOrder.getId()));
					if (null != checkRecord) {
						detailMsg = StringUtil.toString(checkRecord.getComment());
					}
				}
				// 成功
				if (statusId == 4 || statusId == 5 || statusId == 9 || statusId == 11 || statusId == 12
						|| statusId == 13 || statusId == 14) {
					checkStatus = "4";
					checkMsg = OrderStatusConstant.ORDER_CHECK_SUCCEED_MSG;
				}
				// 结束
				if (statusId == 6) {
					checkStatus = "5";
					checkMsg = OrderStatusConstant.ORDER_CHECK_OVER_MSG;
				}
			}

			map.put("sort", sort);
			map.put("canUpdate", canUpdate);
			map.put("canShowDetail", canShowDetail);
			map.put("checkMsg", checkMsg);
			map.put("detailMsg", detailMsg);
			map.put("checkStatus", checkStatus);// 审核状态
			Long creditLimit = bwOrder.getCreditLimit();
			creditLimit = (creditLimit == null) ? 0L : creditLimit;
			map.put("creditLimit", creditLimit);// 设置额度
			List<FaceIDOrderAuthDto> list = bwOrderAuthService.findBwOrderAuthAndPhotoState(order_id);
			map.put("photoState", "");
			if (list.size() > 0) {
				for (FaceIDOrderAuthDto faceIDOrderAuthDto : list) {
					Integer integer = faceIDOrderAuthDto.getAuthType();
					if (integer.equals(1)) {
						map.put("operators", "1");
					}
					if (integer.equals(2)) {
						map.put("borrowMsg", "1");
					}
					if (integer.equals(3)) {
						map.put("borrowCart", "1");
						map.put("photoState", StringUtil.toString(faceIDOrderAuthDto.getPhotoState()));// 设置活体
					}
					if (integer.equals(4)) {
						map.put("zmxy", "1");
					}
					if (integer.equals(5)) {
						map.put("shebaoStatu", "1");
					}
					if (integer.equals(6)) {
						map.put("gjjStatu", "1");
					}
					if (integer.equals(7)) {
						map.put("emailStatu", "1");
					}
					if (integer.equals(8)) {
						map.put("taobaoStatu", "1");
					}
					if (integer.equals(9)) {
						map.put("jdStatu", "1");
					}

				}
			}
			logger.info("【OrderController.getOrderAuth】borrowerId：" + borrowerId + "认证结果map：" + map);
			respResult.setResult(map);
			respResult.setCode(ActivityConstant.ErrorCode.SUCCESS);
			respResult.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
			return respResult;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			respResult.setCode(ActivityConstant.ErrorCode.FAIL);
			respResult.setMsg(ActivityConstant.ErrorMsg.CATCH_ERROR);
		}

		return respResult;
	}

	/**
	 * 查询分期还款信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMultiOrderRepay.do")
	public AppResponseResult getMultiOrderRepay(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			String orderId = request.getParameter("orderId");
			logger.info("【OrderController.getMultiOrderRepay】orderId：" + orderId);
			if (CommUtils.isNull(orderId)) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg("orderId不能为空");
				result.setResult(false);// 处理失败
				return result;
			}
			// 查询分期还款信息
			Map<String, Object> map = orderService.getMultiOrderRepay(Long.parseLong(orderId));
			logger.info("【OrderController.getMultiOrderRepay】orderId：" + orderId + "，分期还款信息map：" + map);
			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
			result.setResult(map);
			return result;
		} catch (Exception e) {
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
			result.setResult(false);// 处理失败
			logger.error("系统异常", e);
			return result;
		}
	}

	/**
	 * 查询借款记录-现金分期-还款记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getInstallmentPayInfo.do")

	public AppResponseResult getInstallmentPayInfo(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			// 工单id
			String orderId = request.getParameter("orderId");
			logger.info("接收到的参数:orderId:" + orderId);
			if (CommUtils.isNull(orderId)) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg("工单id不能为空");
				result.setResult(false);// 处理失败
				return result;
			}
			// 查询分期工单
			List<Map<String, Object>> list = orderService.getInstallmentPayInfo(Long.parseLong(orderId));
			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
			result.setResult(list);
			return result;
		} catch (Exception e) {
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
			result.setResult(false);// 处理失败
			logger.error("系统异常", e);
			return result;
		}
	}

	/**
	 * 根据borrowerId和productType查询最近一期工单
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getLastOrder.do")
	public AppResponseResult getLastOrder(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		try {
			Long borrowerId = NumberUtil.parseLong(request.getParameter("borrowerId"), null);
			Integer productType = NumberUtil.parseInteger(request.getParameter("productType"), null);
			logger.info("接收到的参数:borrowerId:" + borrowerId);
			if (CommUtils.isNull(borrowerId)) {
				result.setCode("001");
				result.setMsg("borrowerId不能为空");
				result.setResult(false);// 处理失败
				return result;
			}
			if (CommUtils.isNull(productType)) {
				result.setCode("002");
				result.setMsg("productType不能为空");
				result.setResult(false);// 处理失败
				return result;
			}
			BwOrder bwOrder = orderService.getOrderByProductType(borrowerId, productType);
			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
			result.setResult(bwOrder);
			return result;
		} catch (Exception e) {
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
			result.setResult(false);// 处理失败
			logger.error("系统异常", e);
			return result;
		}
	}

}
