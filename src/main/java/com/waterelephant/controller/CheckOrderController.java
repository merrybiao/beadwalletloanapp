package com.waterelephant.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.StringUtil;

/**
 * 验证认证信息
 * 
 * 
 * Module:
 * 
 * CheckOrderController.java
 * 
 * @author 胡林浩
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Controller
@RequestMapping(value = "/app/CheckOrder")
public class CheckOrderController {

	private Logger logger = Logger.getLogger(CheckOrderController.class);
	@Autowired
	private BwOrderAuthService bwOrderAuthService;

	/**
	 * 
	 * 查询运营商以及身份信息是否已经验证
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkOrderAuth.do")
	public AppResponseResult checkOrderAuth(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {

			String orderId = request.getParameter("orderId");
			if (StringUtil.isEmpty(orderId)) {
				logger.info("工单ID不能为空");
				result.setMsg("工单ID不能为空");
				result.setCode("111");
				return result;
			} else {
				boolean llxx = bwOrderAuthService.checkOrderAuth(StringUtil.toInteger(orderId),
						ActivityConstant.ORDER_AUTH_TYPE.AUTH_TYPE_2);
				boolean yys = bwOrderAuthService.checkOrderAuth(StringUtil.toInteger(orderId),
						ActivityConstant.ORDER_AUTH_TYPE.AUTH_TYPE_1);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("llxx", llxx);
				map.put("yys", yys);
				result.setResult(map);
				result.setCode("000");

				result.setMsg("请求成功");
				return result;

			}
		} catch (Exception e) {
			result.setMsg("系统错误");
			result.setCode("111");
			return result;
		}
	}
}
