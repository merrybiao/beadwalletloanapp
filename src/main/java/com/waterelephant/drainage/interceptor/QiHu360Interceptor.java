/// ******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved.
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
// package com.waterelephant.drainage.interceptor;
//
// import java.util.TreeMap;
//
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.servlet.HandlerInterceptor;
// import org.springframework.web.servlet.ModelAndView;
//
// import com.alibaba.fastjson.JSON;
// import com.waterelephant.drainage.entity.qihu360.QiHu360Response;
// import com.waterelephant.drainage.util.qihu360.QiHu360Constant;
// import com.waterelephant.drainage.util.qihu360.QiHu360Utils;
// import com.waterelephant.drainage.util.qihu360.RsaUtils;
// import com.waterelephant.entity.BwOrderChannel;
// import com.waterelephant.service.IBwOrderChannelService;
// import com.waterelephant.utils.CommUtils;
//
/// **
// *
// *
// * Module:奇虎360(code360)
// *
// * QiHu360Interceptor.java
// *
// * @author zhangchong
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
// public class QiHu360Interceptor implements HandlerInterceptor {
// @Autowired
// IBwOrderChannelService bwOrderChannelService;
//
// /**
// * 奇虎360接口 - 预处理
// *
// * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest,
// * javax.servlet.http.HttpServletResponse, java.lang.Object)
// */
// @Override
// public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
// throws Exception {
// String uri = request.getRequestURI();
// if (uri.indexOf("/weapi/qihu360") > 0) {
// // 第一步：获取参数
// String sign = request.getParameter("sign"); // 签名值
// String merchant_id = request.getParameter("merchant_id");// 合作机构给 360 分配的商户号
// String biz_enc = request.getParameter("biz_enc");// biz_data 加密方式（0 不加密，1 加密:采用 DES 加密算法）
//
// QiHu360Response qiHu360Response = new QiHu360Response();
// // 第二步：检查参数
// if (CommUtils.isNull(sign)) {
// response.setContentType("application/json");
// response.setCharacterEncoding("UTF-8");
// qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
// qiHu360Response.setMsg("sign为空");
// response.getWriter().write(JSON.toJSONString(qiHu360Response));
// return false;
// }
//
// if (CommUtils.isNull(merchant_id)) {
// response.setContentType("application/json");
// response.setCharacterEncoding("UTF-8");
// qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
// qiHu360Response.setMsg("merchant_id为空");
// response.getWriter().write(JSON.toJSONString(qiHu360Response));
// return false;
// }
//
// // 商户号 - 查询数据库中是否存在
// BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(merchant_id);
// if (CommUtils.isNull(orderChannel) == true) {
// response.setContentType("application/json");
// response.setCharacterEncoding("UTF-8");
// qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
// qiHu360Response.setMsg("merchant_id不存在");
// response.getWriter().write(JSON.toJSONString(qiHu360Response));
// return false;
// }
//
// // if (CommUtils.isNull(biz_enc)) {
// // response.setContentType("application/json");
// // response.setCharacterEncoding("UTF-8");
// // qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
// // qiHu360Response.setMsg("biz_enc为空");
// // response.getWriter().write(JSON.toJSONString(qiHu360Response));
// // return false;
// // }
//
// String des_key = null;// RSA 加密后的密钥（biz_enc 为 1 时为必传）
// String biz_data = null;// 业务参数
// String des_biz_data = null;// des加密后的业务参数
// if ("1".equals(biz_enc)) {
// des_key = request.getParameter("des_key");
// if (CommUtils.isNull(des_key)) {
// response.setContentType("application/json");
// response.setCharacterEncoding("UTF-8");
// qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
// qiHu360Response.setMsg("des_key为空");
// response.getWriter().write(JSON.toJSONString(qiHu360Response));
// return false;
// }
//
// des_biz_data = request.getParameter("biz_data");
// if (CommUtils.isNull(des_biz_data)) {
// response.setContentType("application/json");
// response.setCharacterEncoding("UTF-8");
// qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
// qiHu360Response.setMsg("biz_data为空");
// response.getWriter().write(JSON.toJSONString(qiHu360Response));
// return false;
// }
// } else {
// biz_data = request.getParameter("biz_data");
// if (CommUtils.isNull(biz_data)) {
// response.setContentType("application/json");
// response.setCharacterEncoding("UTF-8");
// qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
// qiHu360Response.setMsg("biz_data为空");
// response.getWriter().write(JSON.toJSONString(qiHu360Response));
// return false;
// }
// }
//
// // 通知参数，需要对key做字典序排序算签名
// TreeMap<String, String> requestParam = new TreeMap<String, String>();
// requestParam.put("merchant_id", merchant_id);
//
// if ("1".equals(biz_enc)) {
// requestParam.put("biz_enc", biz_enc);
// requestParam.put("des_key", des_key);
// requestParam.put("biz_data", des_biz_data);
// } else if ("0".equals(biz_enc)) {
// requestParam.put("biz_enc", biz_enc);
// requestParam.put("biz_data", biz_data);
// } else {
// requestParam.put("biz_data", biz_data);
// }
//
// String signStr = QiHu360Utils.makeSignStr(requestParam);
//
// // 第二步：验证签名
// boolean flag = RsaUtils.verify(signStr.getBytes(), QiHu360Constant.get(QiHu360Utils.QIHU360_PUBLIC_KEY),
// sign);
// if (!flag) {
// response.setContentType("application/json");
// response.setCharacterEncoding("UTF-8");
// qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
// qiHu360Response.setMsg("验证签名失败");
// response.getWriter().write(JSON.toJSONString(qiHu360Response));
// return false;
// }
// return true;
// } else {
// return true;
// }
// }
//
// /**
// *
// * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest,
// * javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
// */
// @Override
// public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
// ModelAndView modelAndView) throws Exception {
//
// }
//
// /**
// *
// * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest,
// * javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
// */
// @Override
// public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
// throws Exception {
//
// }
//
// }
