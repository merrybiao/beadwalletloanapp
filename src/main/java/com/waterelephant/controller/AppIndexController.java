package com.waterelephant.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beadwallet.service.utils.CommUtils;
import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.service.OrderAndBlacklistService;
import com.waterelephant.utils.AppResponseResult;

/**
 * 获取首页的信息
 * 
 * 
 * Module:
 * 
 * AppIndexController.java
 * 
 * @author 胡林浩
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Controller
@RequestMapping("/app/index")
public class AppIndexController {

	private Logger logger = Logger.getLogger(AppIndexController.class);

	@Autowired
	private OrderAndBlacklistService indexService;

	/**
	 * 查询首页用于显示的内容
	 * 
	 * @param request
	 * @param response
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "/getIndexInfo.do")
	public AppResponseResult getIndexInfo(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			String bwId = request.getParameter("bwId");
			if (CommUtils.isNull(bwId)) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg("用户ID不能为空");
				return result;
			}
			logger.info("借款人ID：" + bwId);
			Map<String, Object> OrderMap = indexService.getOrderAndBlacklist(bwId);
			if (null == OrderMap) {
				result.setCode(ActivityConstant.ErrorCode.SUCCESS);
				result.setMsg("没有查到该工单");
				return result;
			}

			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg("请求成功");
			result.setResult(OrderMap);
			return result;
		} catch (Exception e) {
			logger.error("查询首页用于显示的内容异常", e);
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg("系统错误");
			return result;
		}
	}
}
