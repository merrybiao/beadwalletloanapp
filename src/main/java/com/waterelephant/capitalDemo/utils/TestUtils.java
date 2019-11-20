package com.waterelephant.capitalDemo.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.waterelephant.utils.CommUtils;

public class TestUtils {

	public static String checkOrderPushData(HttpServletRequest request) {
		String check =null;
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

}
