///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 本软件为武汉水象科技有限公司开发研制。
// * 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.utils.interfaceLog;
//
//import com.waterelephant.entity.BwThirdInterfaceLog;
//import com.waterelephant.service.BwThirdInterfaceLogService;
//import com.waterelephant.utils.CommUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.net.InetAddress;
//import java.util.Date;
//
///**
// * SxyInterceptor.java
// *
// * @author: 王亚楠
// * @since: JDK 1.8
// * @version: 1.0
// * @date: 2018年5月14日
// * @Description: <TODO>
// *
// */
//public class SxyThirdLogInterceptor implements HandlerInterceptor {
//
//    private static final String responseType = "1";// 请求类型，0 请求 1 接收
//
//    private static Logger logger = Logger.getLogger(SxyThirdLogInterceptor.class);
//    @Autowired
//    private BwThirdInterfaceLogService bwThirdInterfaceLogService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        long startTime = System.currentTimeMillis();
//        request.setAttribute("startTime", startTime);
//
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//        try {
//            // 判断是否使用SxyThirdInterfaceLogUtils保存日志
//            String saveCode = (String) request.getAttribute("saveCode");
//            if (!"1".equals(saveCode)) {
//                return;
//            }
//
//            long endTime = System.currentTimeMillis();
//            long startTime = (long) request.getAttribute("startTime");
//
//            String channel_id = (String) request.getAttribute("channel_id");// 渠道编码
//            String index_key = (String) request.getAttribute("index_key");// 请求标识:可以是手机号，订单号等标识，方便查询定位
//            String response_code = (String) request.getAttribute("response_code");// 返回的code
//            // String status_id = (String) request.getAttribute("status_id");// 订单状态
//            String remark1 = (String) request.getAttribute("remark1");// 备注1
//            String remark2 = (String) request.getAttribute("remark2");// 备注2
//
//            String requestURI = request.getRequestURI();
//            int lastIndexOf = requestURI.lastIndexOf("/");
//            String interface_name = requestURI.substring(lastIndexOf);
//
//            // 封装实体类,保存
//            BwThirdInterfaceLog bwThirdInterfaceLog = new BwThirdInterfaceLog();
//            bwThirdInterfaceLog.setConsumeTime(endTime - startTime);
//            bwThirdInterfaceLog.setRequestTime(new Date(startTime));
//            bwThirdInterfaceLog.setChannelId(channel_id);
//            bwThirdInterfaceLog.setInterfaceType(responseType);
//            bwThirdInterfaceLog.setIndexKey(index_key);
//            bwThirdInterfaceLog.setResponseCode(response_code);
//            // bwThirdInterfaceLog.setStatusId(status_id);
//            if (!StringUtils.isBlank(remark1) && remark1.length() > 50) {
//                remark1 = remark1.substring(0, 45);
//            }
//            bwThirdInterfaceLog.setRemark1(remark1);
//            bwThirdInterfaceLog.setRemark2(remark2);
//            // IP
//            String hostAddress = InetAddress.getLocalHost().getHostAddress();
//            bwThirdInterfaceLog.setRemark3("ip:" + getHostAddress(hostAddress));
//
//            bwThirdInterfaceLog.setInterfaceName(interface_name);
//
//            bwThirdInterfaceLogService.save(bwThirdInterfaceLog);
//        } catch (Exception e) {
//            logger.error("三方渠道接口日志信息保存异常，地址路径[" + request.getRequestURL() + "]>>>", e);
//        }
//
//    }
//
//    private String getHostAddress(String hostAddress) {
//        if (CommUtils.isNull(hostAddress)) {
//            return "空";
//        }
//        if ("10.27.148.150".equals(hostAddress)) {
//            return "(外)106.14.82.41";
//        }
//        if ("10.27.146.81".equals(hostAddress)) {
//            return "(外)106.15.3.122";
//        }
//        if ("10.27.148.182".equals(hostAddress)) {
//            return "(外)106.14.79.93";
//        }
//        if ("10.27.148.142".equals(hostAddress)) {
//            return "(外)106.15.6.190";
//        }
//        if ("10.27.148.133".equals(hostAddress)) {
//            return "(外)139.224.213.116";
//        }
//        return "(内)" + hostAddress;
//    }
//}
