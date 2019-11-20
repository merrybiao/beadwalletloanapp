package com.waterelephant.jiedianqian.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beadwallet.entity.lianlian.SignLess;
import com.beadwallet.servcie.LianLianPayService;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.base.BaseController;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.jiedianqian.util.JieDianQianLogUtil;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.DoubleUtil;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/app/jiedianqian")
public class SignJieDianQianController extends BaseController {

	private static JieDianQianLogUtil logger = new JieDianQianLogUtil(SignJieDianQianController.class);

	@Resource
	private IBwBankCardService iBwBankCardService;

	@Resource
	private IBwOrderService bwOrderService;

	@Autowired
	private BwOrderRongService bwOrderRongService;

	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;

	private static String BIND_CARD_STATUS_SUCCESS = "success";
	private static String BIND_CARD_STATUS_FAILED = "error";
	private static String ORDER_NO_SESSION = "jieDianQianOrderNo";

	/**
	 * 签约操作
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/signJieDianQianCard.do")
	public void signJieDianQianCard(HttpServletRequest request, HttpServletResponse response) {
		String methodName = "SignController.signJieDianQianCard";
		String sessionId = DateUtil.getSessionId();
		try {
			String userId = request.getParameter("userId");
			String idNo = request.getParameter("idNo");
			String accName = request.getParameter("accName");
			String cardNo = request.getParameter("cardNo");
			String urlReturn = SystemConstant.NOTIRY_URL + "/app/jiedianqian/signJieDianQianCardCallBack.do";
			String orderNo = request.getParameter("orderNo");
			logger.set("SIGNCARD-" + orderNo);

			HttpSession session = request.getSession();
			session.setAttribute(ORDER_NO_SESSION, orderNo);
			RedisUtils.hset("jiedianqian:bindCard", userId, orderNo);
			RedisUtils.hset("jiedianqian:session", session.getId(), orderNo);

			logger.info(methodName + " start,userId=" + userId + ",idNo=" + idNo + ",accName=" + accName + ",cardNo="
					+ cardNo + ",urlReturn=" + urlReturn + ",orderNo=" + orderNo);

			SignLess signLess = new SignLess();
			signLess.setUser_id(userId);
			signLess.setId_no(idNo);
			signLess.setAcct_name(accName);
			signLess.setCard_no(cardNo);
			signLess.setUrl_return(urlReturn);
			// logger.info("开始调用连连签约接口,signLess="+com.alibaba.fastjson.JSONObject.toJSONString(signLess)); // 去掉日志1001
			LianLianPayService.signAccreditPay(signLess, response);
			logger.info(sessionId + " 结束调用连连签约接口");

		} catch (Exception e) {
			logger.error(methodName + " 接口出现异常", e);
		}
		logger.info(sessionId + "{ " + methodName + "  }end");
		logger.remove();
	}

	@RequestMapping(value = "/signJieDianQianCardCallBack.do")
	public String signJieDianQianCardCallBack(HttpServletRequest request, HttpServletResponse response,ModelMap map) {
		String methodName = "SignJieDianQianController.signJieDianQianCardCallBack";
		
		try {
			String status = request.getParameter("status");
			String result = request.getParameter("result");
			//String thirdUrl = RedisUtils.hget("third:url", "jiedianqian");
			
			HttpSession session = request.getSession();
			session.getAttribute(ORDER_NO_SESSION);
			
			String orderNo = null;
			orderNo = session.getAttribute(ORDER_NO_SESSION) == null ? null
					: String.valueOf(session.getAttribute(ORDER_NO_SESSION));
			session.removeAttribute(ORDER_NO_SESSION);
			
			if (orderNo == null || "".equals(orderNo) || "null".equals(orderNo)) {
				orderNo = RedisUtils.hget("jiedianqian:session", session.getId());
			}
			
			RedisUtils.hdel("jiedianqian:session", session.getId());
			
			logger.set("SIGNBACK-" + orderNo);
			
			if (CommUtils.isNull(status)) {
				bindCardFeedBack(orderNo, BIND_CARD_STATUS_FAILED);
				map.put("result", "系统异常");
				logger.info(methodName + " end,status is null,return index page");
				logger.remove();
				return "sign_fail_jiedianqian";
			}
			
			// 签约成功
			if (status.equals("0000")) {
				logger.info("signJieDianQianCardCallBack success,status = 0000,start update signStatus=2");
				try {
					JSONObject jsonObject = JSONObject.fromObject(result);
					String userId = jsonObject.getString("user_id");
					// logger.info("get orderNo from redis[loanWallet:bindCard]");
					orderNo = RedisUtils.hget("jiedianqian:bindCard", userId);
					// logger.info("get orderNo from redis[loanWallet:bindCard] is " + orderNo);
					RedisUtils.hdel("jiedianqian:bindCard", userId);

					// 更新为已签约
					iBwBankCardService.updateSignStatusByBorrowerId(Long.valueOf(userId));

					bindCardFeedBack(orderNo, BIND_CARD_STATUS_SUCCESS);
					// 修改工单为待生成合同
					approveConfirm(orderNo);
					// logger.info(methodName + " end,signCardCallBack success,status = 0000");
					logger.remove();
					return "sign_success_jiedianqian";
				} catch (Exception e) {
					bindCardFeedBack(orderNo, BIND_CARD_STATUS_FAILED);
					map.put("result", "系统异常");
					logger.error(methodName + " end,signJieDianQianCardCallBack occured exception", e);
					logger.remove();
					return "sign_fail_jiedianqian";
				}
			} else {
				// 签约失败
				map.put("result", result);
				bindCardFeedBack(orderNo, BIND_CARD_STATUS_FAILED);
				logger.remove();
				return "sign_fail_jiedianqian";
			}
		} catch (Exception e) {
			logger.error(methodName + " occured exception", e);
			map.put("result", "系统异常");
			logger.remove();
			return "sign_fail_jiedianqian";
		}
	}
	
	
	public BwOrder getOrderByTOrderNo(String orderNo) {
		BwOrder bwOrder = null;
		try {
			logger.info("开始查询贷款钱包工单，thirdOrderNo=" + orderNo + "...");
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
			// logger.info("查询贷款钱包工单为:" + com.alibaba.fastjson.JSONObject.toJSONString(bwOrderRong));

			if (CommUtils.isNull(bwOrderRong)) {
				logger.info(orderNo + "贷款钱包工单不存在");
				return null;
			}

			logger.info("开始查询工单信息，orderId=" + bwOrderRong.getOrderId() + "...");
			bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
			// logger.info("查询工单信息为:" + com.alibaba.fastjson.JSONObject.toJSONString(bwOrder)); // 去掉日志1001

			if (CommUtils.isNull(bwOrder)) {
				logger.info(orderNo + "工单不存在");
				return null;
			}
		} catch (Exception e) {
			logger.error("getOrderByTOrderNo occured exception:", e);
		}

		return bwOrder;
	}

	public void approveConfirm(String thirdOrderNo) {
		try {
			// 校验请求参数
			String orderTerm = "1";

			// 获取利率字典表信息
			BwProductDictionary bwProductDictionary = bwProductDictionaryService.findById(3L);// 固定查询产品id为2的合同利率
			Double contractMonthRate = 0.0;
			Double repayAmount = 0.0;
			Double contractAmount = 0.0;

			// 等额本息
			// 计算合同月利率
			contractMonthRate = bwProductDictionary.getpBorrowRateMonth();
			// 计算合同金额
			BwOrder order = getOrderByTOrderNo(thirdOrderNo);

			// 计算还款金额
			repayAmount = DoubleUtil.round(((order.getBorrowAmount() / Integer.parseInt(orderTerm))
					+ (order.getBorrowAmount() * bwProductDictionary.getpInvestRateMonth())), 2);
			// 计算合同金额
			contractAmount = DoubleUtil
					.round((repayAmount * (Math.pow(1 + contractMonthRate, Integer.parseInt(orderTerm)) - 1))
							/ (contractMonthRate * (Math.pow(1 + contractMonthRate, Integer.parseInt(orderTerm)))), 2);

			order.setRepayTerm(Integer.parseInt(orderTerm));
			order.setRepayType(Integer.parseInt("1"));// 待确认
			order.setBorrowRate(bwProductDictionary.getpInvestRateMonth());
			order.setContractRate(bwProductDictionary.getpInvesstRateYear());
			order.setContractMonthRate(contractMonthRate);
			// 将产品类型更新成2
			order.setProductId(3);
			order.setContractAmount(contractAmount);
			// 工单修改时间
			order.setUpdateTime(new Date());

			if (order.getStatusId().intValue() == 4) {
				order.setStatusId(11L);
				int num = bwOrderService.updateBwOrder(order);
				logger.info("修改工单条数:" + num);
				if (num == 0) {
					return;
				}
			}
		} catch (Exception e) {
			logger.error(" 异常", e);
		}
	}	
	
	
	private void bindCardFeedBack(String orderNo, String code) {
		try {
			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setThirdOrderNo(orderNo);
			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
			if (CommUtils.isNull(bwOrderRong)) {
				logger.info("bindCardFeedBack occured exception: bwOrderRong is null");
			}

			RedisUtils.lpush("notify:orderState", String.valueOf(bwOrderRong.getOrderId()));
		} catch (Exception e) {
		}
	}
}
