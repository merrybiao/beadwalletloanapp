package com.waterelephant.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.service.TestAnalogService;
import com.waterelephant.utils.ControllerUtil;
import com.waterelephant.utils.StringUtil;

/**
 * 模拟数据
 * 
 * 
 * Module:
 * 
 * TestController.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Controller
@RequestMapping("/testAnalog")
public class TestAnalogController {

	private Logger logger = Logger.getLogger(TestAnalogController.class);

	@Autowired
	private TestAnalogService testAnalogService;

	/**
	 * 模拟测试
	 * 
	 * @param resquest
	 * @return
	 */
	@RequestMapping("/analogTest.do")
	public String analogTest(HttpServletRequest resquest) {
		return "analogTest";
	}

	/**
	 * 删除用户
	 * 
	 * @param resquest
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/testDeleteBorrower.do")
	public String deleteBorrower(HttpServletRequest resquest) {
		try {
			String phone = resquest.getParameter("phone");
			String resultStr = testAnalogService.deleteBorrower(phone);
			return resultStr;
		} catch (Exception e) {
			logger.error("删除用户失败", e);
			return "ERROR";
		}
	}

	/**
	 * 绑定银行卡
	 * 
	 * @param resquest
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/testSignBank.do")
	public String testSignBank(HttpServletRequest resquest) {
		try {
			String name = resquest.getParameter("name");
			String phone = resquest.getParameter("phone");
			String idCard = resquest.getParameter("idCard");
			String cardNo = resquest.getParameter("cardNo");
			String resultStr = testAnalogService.updateAndSignBank(name, phone, idCard, cardNo);
			return resultStr;
		} catch (Exception e) {
			logger.error("银行卡签约失败", e);
			return "ERROR";
		}
	}

	/**
	 * 认证信息
	 * 
	 * @param resquest
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/testOrderAuth.do")
	public String testOrderAuth(HttpServletRequest resquest) {
		try {
			String orderId = resquest.getParameter("orderId");
			String authType = resquest.getParameter("authType");
			String resultStr = testAnalogService.updateAndOrderAuth(orderId, authType);
			return resultStr;
		} catch (Exception e) {
			logger.error("认证失败", e);
			return "ERROR";
		}
	}

	/**
	 * 审核通过
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/testAuditSuccess.do")
	public String testAuditSuccess(HttpServletRequest request) {
		try {
			Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
			logger.info("【TestAnalogController.testAuditSuccess】paramMap=" + paramMap);
			String phone = request.getParameter("phone");
			String orderIdStr = request.getParameter("orderId");
			testAnalogService.updateAndAuditSuccess(phone, orderIdStr);
		} catch (Exception e) {
			logger.error("审核异常", e);
			return "ERROR";
		}
		return "OK";
	}

	/**
	 * 放款
	 * 
	 * @param resquest
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/analogMultiTermLoan.do")
	public String analogMultiTermLoan(HttpServletRequest resquest) {
		try {
			String repayDate = resquest.getParameter("repayDate");
			String orderId = resquest.getParameter("orderId");
			String resultStr = testAnalogService.updateAndAnalogLoan(orderId, repayDate);
			return resultStr;
		} catch (Exception e) {
			logger.error("插入分期还款计划失败", e);
			return "ERROR";
		}
	}

	/**
	 * 生成逾期记录
	 * 
	 * @param resquest
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/analogMultiTermOverDue.do")
	public String analogMultiTermOverDue(HttpServletRequest resquest) {
		try {
			String orderId = resquest.getParameter("orderId");
			String resultStr = testAnalogService.updateAndAnalogOverDue(orderId);
			return resultStr;
		} catch (Exception e) {
			logger.error("插入逾期记录失败", e);
			return "ERROR";
		}
	}

	/**
	 * 清除还款数据
	 * 
	 * @param resquest
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/clearMultiOrder.do")
	public String clearMultiOrder(HttpServletRequest resquest) {
		try {
			String orderId = resquest.getParameter("orderId");
			if (StringUtil.isEmpty(orderId)) {
				return "orderId不能为空";
			}
			String resultStr = testAnalogService.updateAndClearOrder(orderId);
			return resultStr;
		} catch (Exception e) {
			logger.error("清除还款数据失败", e);
		}
		return "OK";
	}

	/**
	 * 删除工单、认证、还款计划
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteOrderInfo.do")
	public String deleteOrderInfo(HttpServletRequest request) {
		String resultStr = "FAIL";
		try {
			Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
			logger.info("【TestAnalogController.deleteOrderInfo】paramMap=" + paramMap);
			String phone = request.getParameter("phone");
			String orderIdStr = request.getParameter("orderId");
			resultStr = testAnalogService.deleteOrderInfo(phone, orderIdStr);
		} catch (Exception e) {
			logger.error("删除工单、还款记录、认证、分批、支付明细、弹框、优惠券等信息异常", e);
			resultStr = "ERROR";
		}
		return resultStr;
	}

	@RequestMapping("/test.do")
	public String test() {
		return "testHtml";
	}
}
