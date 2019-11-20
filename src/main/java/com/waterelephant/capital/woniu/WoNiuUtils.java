package com.waterelephant.capital.woniu;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.waterelephant.utils.CommUtils;

public class WoNiuUtils {

	public static String checkOrderPushData(HttpServletRequest request) {
		String check = null;
		if (CommUtils.isNull(request)) {
			return "";
		}

		if (StringUtils.isBlank(request.getParameter("params"))) {
			return "拉取水象推送参数为空";
		}

		if (StringUtils.isBlank(request.getParameter("sign"))) {
			return "拉去签名为空";
		}
		return check;
	}

	public static String checkLoanCallBack(HttpServletRequest request) {
		String check = null;
		if (CommUtils.isNull(request)) {
			return "";
		}

		if (StringUtils.isBlank(request.getParameter("code"))) {
			return "拉取蜗牛状态码为空";
		}

		if (StringUtils.isBlank(request.getParameter("message"))) {
			return "拉取状态码对应消息为空";
		}

		if (StringUtils.isBlank(request.getParameter("thirdNo"))) {
			return "拉取水象订单编号为空";
		}
		
		if (StringUtils.isBlank(request.getParameter("transferDate"))) {
			return "拉取水象订单编号为空";
		}

		if (StringUtils.isBlank(request.getParameter("sign"))) {
			return "拉取签名串为空";
		}
		return check;
	}
}
