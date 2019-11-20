///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 本软件为武汉水象科技有限公司开发研制。
// * 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.utils.interfaceLog;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
///**
// * SxyLogUtils.java
// *
// * @author: 王亚楠
// * @since: JDK 1.8
// * @version: 1.0
// * @date: 2018年5月14日
// * @Description: <日志工具>
// *
// */
//public class SxyThirdInterfaceLogUtils {
//	/**
//     * 方法写在Controller的最后
//     *
//     * @param channel_id 渠道编码
//     * @param index_key 请求标识,可以是手机号，订单号等标识，方便查询定位
//     * @param response_code 返回的code
//     * @param remarks 备注(0-2个)
//     *
//     */
//	public static void setSxyLog(String channel_id, String index_key, String response_code, String... remarks) {
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		request.setAttribute("saveCode", "1");
//
//		request.setAttribute("channel_id", channel_id);// 渠道编码
//		request.setAttribute("index_key", index_key);// 请求标识,可以是手机号，订单号等标识，方便查询定位
//		request.setAttribute("response_code", response_code);// 返回的code
//		// request.setAttribute("status_id", status_id);// 订单状态,回调填写
//
//        for (int i = 0; i < remarks.length && i < 2; i++) {
//            request.setAttribute("remark" + (i + 1), remarks[i]);// 备注(0-2个)
//		}
//	}
//}
