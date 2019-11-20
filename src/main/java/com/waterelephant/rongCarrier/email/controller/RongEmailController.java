package com.waterelephant.rongCarrier.email.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.rongCarrier.email.service.RongEmailService;
import com.waterelephant.service.impl.BwBorrowerService;

@Controller
public class RongEmailController {

	private Logger logger = LoggerFactory.getLogger(RongEmailController.class);

	@Autowired
	private BwBorrowerService bwBorrowerService;

	@Autowired
	private RongEmailService rongEmailService;

	/**
	 * 传递信息给rong360并获取认证链接
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/app/rongCarrier/emailcollectuser.do")
	public String collectuserEmail(HttpServletRequest request, HttpServletResponse response) {
		String url = "auth_fail_common";
		// 第一步：获取参数
		String userId = request.getParameter("userId"); // borrowerId
		String outUniqueId = request.getParameter("orderId"); // orderId
		String authChannel = request.getParameter("authChannel"); // authChannel
		String phone = request.getParameter("phone"); // phone
		// 第二步：验证参数
		if (StringUtils.isEmpty(userId)) {
			request.setAttribute("msg", "您的邮箱认证失败");
			return "auth_fail_common";
		}
		if (StringUtils.isEmpty(outUniqueId)) {
			request.setAttribute("msg", "您的邮箱认证失败");
			return "auth_fail_common";
		}
		if (StringUtils.isEmpty(authChannel)) {
			request.setAttribute("msg", "您的邮箱认证失败");
			return "auth_fail_common";
		}
		try {
			BwBorrower borrower = new BwBorrower();
			borrower.setId(Long.valueOf(userId));
			BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
			Map<String, String> paramMap = new HashMap<String, String>();
			if (bwBorrower != null) {
				String name = bwBorrower.getName();
				String idCard = bwBorrower.getIdCard();
				paramMap.put("userId", userId);
				paramMap.put("outUniqueId", outUniqueId);
				paramMap.put("authChannel", authChannel);
				paramMap.put("phone", phone);
				paramMap.put("name", name);
				paramMap.put("idCard", idCard);
				paramMap.put("type", "email_crawl");
				String resultStr = rongEmailService.collectuserCommon(paramMap);
				if (StringUtils.isEmpty(resultStr)) {
					request.setAttribute("msg", "您的邮箱认证失败");
					return "auth_fail_common";
				} else {
					Map<String, Object> resultMap = (Map<String, Object>) JSONObject.parse(resultStr);
					if (resultMap.containsKey("tianji_api_tianjireport_collectuser_response")) {
						Map<String, String> resultTemp = (Map<String, String>) resultMap
								.get("tianji_api_tianjireport_collectuser_response");
						if (resultTemp != null) {
							url = resultTemp.get("redirectUrl");
							// System.out.println(url);
							if (StringUtils.isEmpty(url)) {
								request.setAttribute("msg", "您的邮箱认证失败");
								return "auth_fail_common";
							} else {
								response.setStatus(201);
								logger.info("redirect:" + url);
								return "redirect:" + url;
							}
						} else {
							request.setAttribute("msg", "您的邮箱认证失败");
							return "auth_fail_common";
						}
					} else {
						request.setAttribute("msg", "您的邮箱认证失败");
						return "auth_fail_common";
					}
				}
			} else {
				request.setAttribute("msg", "您的邮箱认证失败");
				return "auth_fail_common";
			}
		} catch (Exception e) {
			response.setStatus(101);
			logger.error("系统异常:", e.getMessage());
		}
		request.setAttribute("msg", "您的邮箱认证失败");
		return url;
	}
}
