package com.waterelephant.capital.woniu;

import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.waterelephant.capital.service.WoNiuService;
import com.waterelephant.utils.AppResponseResult;

/**
 * 
 * 
 * Module: 
 * 
 * WoNiuController.java 
 * @author 张博
 * @since JDK 1.8
 * @version 1.0
 * @description: <蜗牛聚财 - 放款回调>
 */
@Controller
public class WoNiuController {
	private Logger logger = Logger.getLogger(WoNiuController.class);

	@Resource
	private WoNiuService woNiuService;

	/**
	 * 放款成功通知
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/capital/woniu/loanCallBackSuccess.do")
	public AppResponseResult loanCallBackSuccess(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult appResponseResult = new AppResponseResult();
		logger.info("开始蜗牛放款回调接口");
		try {
			String check = WoNiuUtils.checkLoanCallBack(request);
			if (StringUtils.isNotBlank(check)) {
				appResponseResult.setCode("1000");
				appResponseResult.setMsg("参数错误");
				appResponseResult.setResult("FAIL");
				return appResponseResult;
			}

			String code = request.getParameter("code");
			String message = request.getParameter("message");
			String transferDate = request.getParameter("transferDate"); // 转账时间
			String orderNo = request.getParameter("thirdNo"); // 订单号
			String sign = request.getParameter("sign");

			TreeMap<String, String> tm = new TreeMap<>();
			tm.put("code", code);
			tm.put("message", message);
			tm.put("transferDate", transferDate);
			tm.put("thirdNo", orderNo);

			// 验签
			boolean flag = WoNiuSignUtils.verifySign(JSON.toJSONString(tm), WoNiuConstant.Key, sign);
			if (!flag) {
				appResponseResult.setCode("1001");
				appResponseResult.setMsg("签名失败");
				appResponseResult.setResult("FAIL");
				return appResponseResult;
			}

			appResponseResult = woNiuService.saveCallBackSuccess(tm); // 放款成功业务处理

		} catch (Exception e) {
			logger.error("蜗牛放款回调接口异常" + e);
			appResponseResult.setCode("-1");
			appResponseResult.setMsg("放款回调接口异常");
			appResponseResult.setResult("FAIL");
		}
		return appResponseResult;
	}

	/**
	 * 放款失败通知
	 * 
	 * @param request
	 * @param response
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/capital/woniu/loanCallBackFail.do")
	public AppResponseResult loanCallBackFail(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult appResponseResult = new AppResponseResult();
		try {
			String orderNo = request.getParameter("thirdNo"); 		// 订单号
			String sign = request.getParameter("sign"); 		    // 签名
			String code = request.getParameter("code");
			String message = request.getParameter("message");

			TreeMap<String, String> tm = new TreeMap<>();
			tm.put("thirdNo", orderNo);
			tm.put("code", code);
			tm.put("message", message);
			// 验签
			boolean flag = WoNiuSignUtils.verifySign(JSON.toJSONString(tm), WoNiuConstant.Key, sign);
			if (!flag) {
				appResponseResult.setCode("1001");
				appResponseResult.setMsg("签名失败");
				appResponseResult.setResult("FAIL");
				return appResponseResult;
			}

			appResponseResult = woNiuService.saveCallBackFail(tm); // 放款失败业务处理

		} catch (Exception e) {
			logger.error("蜗牛放款回调接口异常" + e);
			appResponseResult.setCode("-1");
			appResponseResult.setMsg("放款回调接口异常");
			appResponseResult.setResult("FAIL");
		}

		return appResponseResult;
	}

}
