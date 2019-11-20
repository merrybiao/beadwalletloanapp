///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 本软件为武汉水象科技有限公司开发研制。
// * 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.controller.haodai;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.service.IBwOrderService;
//import com.waterelephant.sxyDrainage.entity.haodai.*;
//import com.waterelephant.sxyDrainage.service.HaoDaiNewService;
//import com.waterelephant.sxyDrainage.utils.haodai.HaoDaiConstant;
//import com.waterelephant.sxyDrainage.utils.haodai.HaoDaiUtils;
//import com.waterelephant.sxyDrainage.utils.interfaceLog.SxyThirdInterfaceLogUtils;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.RedisUtils;
//import org.apache.commons.io.IOUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * HaoDaiController.java
// *
// * @author: 王亚楠
// * @since: JDK 1.8
// * @version: 1.0
// * @date: 2018年5月7日
// * @Description: <好贷网接口对接>
// *
// */
//@Controller
//public class HaoDaiWangController {
//    private static Logger logger = Logger.getLogger(HaoDaiWangController.class);
//
//    @Autowired
//    private HaoDaiNewService haoDaiNewService;
//
//    @Autowired
//    private IBwOrderService bwOrderService;
//
//    private final static String CHANNEL_ID = HaoDaiConstant.CHANNEL_ID;
//
//    /**
//     * 可否申请&复贷判断接口
//     *
//     * @param appid
//     * @param version
//     * @param timestamp
//     * @param signType
//     * @param signData
//     * @param hdCheckUserReq
//     * @return
//     */
//    @RequestMapping("sxyDrainage/haodai/checkUser.do")
//    @ResponseBody
//    public HaoDaiResponse checkUser(HttpServletRequest request) {
//        logger.info("好贷网>>>进入HaoDaiWangController checkUser()可否申请&复贷判断接口");
//        long sessionId = System.currentTimeMillis();
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//        try {
//            String appid = request.getHeader("X-Haodai-Openapi-Appid");
//            String version = request.getHeader("X-Haodai-Openapi-Version");
//            String timestamp = request.getHeader("X-Haodai-Openapi-Timestamp");
//            String signData = request.getHeader("X-Haodai-Openapi-SignData");
//            String signType = request.getHeader("X-Haodai-Openapi-SignType");
//            // String body = HaoDaiUtils.getBody(request);
//            String body = IOUtils.toString(request.getInputStream(), "UTF-8");
//            logger.info("好贷网>>>请求参数Body:" + body + "APP_ID：" + appid + " 版本:" + version + " 时间戳:" + timestamp + " 签名算法:" + signType + " 签名值" + signData);
//
//            // 验签
//            String checkFilter = HaoDaiUtils.checkFilter(body, signData, appid, version, timestamp);
//            if (!CommUtils.isNull(checkFilter)) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_AUTH_FAIL);
//                haoDaiResponse.setMsg(checkFilter);
//                logger.info("checkUser()请求失败：" + checkFilter);
//                return haoDaiResponse;
//            }
//
//            HdCheckUserReq hdCheckUserReq = JSON.parseObject(body, HdCheckUserReq.class);
//            haoDaiResponse = haoDaiNewService.checkUser(hdCheckUserReq, sessionId);
//
//        } catch (Exception e) {
//            logger.error("=====好贷网>>可否申请&复贷判断接口方法checkUser()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("可否申请&复贷判断接口系统异常，请稍后重试");
//        }
//        return haoDaiResponse;
//    }
//
//    /**
//     * 保存订单
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping("sxyDrainage/haodai/saveUserInfo.do")
//    @ResponseBody
//    public HaoDaiResponse saveOrder(HttpServletRequest request) {
//        logger.info("好贷网>>>进入HaoDaiWangController saveOrder()接口");
//        long sessionId = System.currentTimeMillis();
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//        String orderNo = null;
//
//        try {
//
//            String appid = request.getHeader("X-Haodai-Openapi-Appid");
//            String version = request.getHeader("X-Haodai-Openapi-Version");
//            String timestamp = request.getHeader("X-Haodai-Openapi-Timestamp");
//            String signType = request.getHeader("X-Haodai-Openapi-SignType");
//            String signData = request.getHeader("X-Haodai-Openapi-SignData");
//
//            String params = IOUtils.toString(request.getInputStream(), "UTF-8");
//
//
//            // 验签
//            String checkFilter = HaoDaiUtils.checkFilter(params, signData, appid, version, timestamp);
//
//            if (!CommUtils.isNull(checkFilter)) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_AUTH_FAIL);
//                haoDaiResponse.setMsg(checkFilter);
//                logger.info("saveOrder()请求失败：" + checkFilter);
//                return haoDaiResponse;
//            }
//            HdBaseInfoReq hdBaseInfoReq = JSONObject.parseObject(params, HdBaseInfoReq.class);
//
//            // 获得运营商数据
//            JSONObject parseObject = JSONObject.parseObject(params);
//            JSONObject addInfoObj = JSONObject.parseObject(parseObject.getString("addInfo"));
//            Object object = null;
//            if (addInfoObj != null) {
//                object = addInfoObj.get("mobile_src");
//            } else {
//                logger.info("====运营商数据不存在");
//            }
//
//            haoDaiResponse = haoDaiNewService.saveOrder(hdBaseInfoReq, sessionId, object);
//            if (hdBaseInfoReq == null || hdBaseInfoReq.getOrderInfo() == null) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("进件请求参数为空");
//                logger.info(sessionId + "好贷进件请求参数为空");
//                return haoDaiResponse;
//            }
//            orderNo = hdBaseInfoReq.getOrderInfo().getOrder_no();
//            SxyThirdInterfaceLogUtils.setSxyLog(CHANNEL_ID, orderNo, haoDaiResponse.getCode(), haoDaiResponse.getMsg(), "订单号");
//        } catch (Exception e) {
//            logger.error("=====好贷网>>保存订单接口方法saveOrder()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("保存订单接口系统异常，请稍后重试");
//        }
//        logger.info(sessionId + "好贷进件接口返回数据为" + JSON.toJSONString(haoDaiResponse));
//
//        return haoDaiResponse;
//    }
//
//    /**
//     * 补充信息接口
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping("sxyDrainage/haodai/saveUserAddInfo.do")
//    @ResponseBody
//    public HaoDaiResponse saveAddOrder(HttpServletRequest request) {
//        logger.info("好贷网>>>进入HaoDaiWangController saveAddOrder()用户补充信息接口");
//        long sessionId = System.currentTimeMillis();
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//        String orderNo = null;
//        try {
//
//            String appid = request.getHeader("X-Haodai-Openapi-Appid");
//            String version = request.getHeader("X-Haodai-Openapi-Version");
//            String timestamp = request.getHeader("X-Haodai-Openapi-Timestamp");
//            String signType = request.getHeader("X-Haodai-Openapi-SignType");
//            String signData = request.getHeader("X-Haodai-Openapi-SignData");
//
//            String params = IOUtils.toString(request.getInputStream(), "UTF-8");
//
//
//            // 验签
//            String checkFilter = HaoDaiUtils.checkFilter(params, signData, appid, version, timestamp);
//
//            if (!CommUtils.isNull(checkFilter)) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_AUTH_FAIL);
//                haoDaiResponse.setMsg(checkFilter);
//                logger.info("saveAddOrder()请求失败：" + checkFilter);
//                return haoDaiResponse;
//            }
//            HdSupplementInfoReq hdSupplementInfoReq = JSONObject.parseObject(params, HdSupplementInfoReq.class);
//
//            haoDaiResponse = haoDaiNewService.saveAddOrder(hdSupplementInfoReq, sessionId);
//
//            if (hdSupplementInfoReq == null || hdSupplementInfoReq.getOrderInfo() == null) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("补充参数为空");
//                logger.info(sessionId + "好贷补充信息请求参数为空");
//                return haoDaiResponse;
//            }
//            orderNo = hdSupplementInfoReq.getOrderInfo().getOrder_no();
//            SxyThirdInterfaceLogUtils.setSxyLog(CHANNEL_ID, orderNo, haoDaiResponse.getCode(), haoDaiResponse.getMsg(), "订单号");
//        } catch (Exception e) {
//            logger.error("=====好贷网>>补充信息接口saveAddOrder()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("补充信息接口系统异常，请稍后重试");
//        }
//        logger.info(sessionId + "好贷补充信息接口返回数据为" + JSON.toJSONString(haoDaiResponse));
//
//        return haoDaiResponse;
//    }
//
//    /**
//     * 保存银行卡信息
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping("sxyDrainage/haodai/saveBankCard.do")
//    @ResponseBody
//    public HaoDaiResponse saveBankCard(HttpServletRequest request) {
//        logger.info("好贷网>>>进入HaoDaiWangController saveBankCard()保存银行卡接口");
//        long sessionId = System.currentTimeMillis();
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//        String orderNo = null;
//
//        try {
//
//            String appid = request.getHeader("X-Haodai-Openapi-Appid");
//            String version = request.getHeader("X-Haodai-Openapi-Version");
//            String timestamp = request.getHeader("X-Haodai-Openapi-Timestamp");
//            String signType = request.getHeader("X-Haodai-Openapi-SignType");
//            String signData = request.getHeader("X-Haodai-Openapi-SignData");
//
//            String params = IOUtils.toString(request.getInputStream(), "UTF-8");
//
//
//            // 验签
//            String checkFilter = HaoDaiUtils.checkFilter(params, signData, appid, version, timestamp);
//
//            if (!CommUtils.isNull(checkFilter)) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_AUTH_FAIL);
//                haoDaiResponse.setMsg(checkFilter);
//                logger.info("saveBankCard()请求失败：" + checkFilter);
//                return haoDaiResponse;
//            }
//            HdBindCardCheckReq hdBindCardCheckReq = JSONObject.parseObject(params, HdBindCardCheckReq.class);
//            if (hdBindCardCheckReq == null) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("保存银行卡参数为空");
//                logger.info(sessionId + "好贷保存银行卡请求参数为空");
//                return haoDaiResponse;
//            }
//            orderNo = hdBindCardCheckReq.getOrder_no();
//            haoDaiResponse = haoDaiNewService.saveBankCardInfo(hdBindCardCheckReq, sessionId);
//            SxyThirdInterfaceLogUtils.setSxyLog(CHANNEL_ID, orderNo, haoDaiResponse.getCode(), haoDaiResponse.getMsg(), "订单号");
//        } catch (Exception e) {
//            logger.error("=====好贷网>>绑卡接口saveBankCard()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("绑卡接口系统异常，请稍后重试");
//        }
//        logger.info(sessionId + "好贷保存银行卡接口返回数据为" + JSON.toJSONString(haoDaiResponse));
//
//        return haoDaiResponse;
//    }
//
//    /**
//     * 获取银行卡验证码
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping("sxyDrainage/haodai/bankCardCode.do")
//    @ResponseBody
//    public HaoDaiResponse saveCode(HttpServletRequest request) {
//        logger.info("好贷网>>>进入HaoDaiWangController saveCode()银行卡验证码接口");
//        long sessionId = System.currentTimeMillis();
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//        String orderNo = null;
//        try {
//
//            String appid = request.getHeader("X-Haodai-Openapi-Appid");
//            String version = request.getHeader("X-Haodai-Openapi-Version");
//            String timestamp = request.getHeader("X-Haodai-Openapi-Timestamp");
//            String signType = request.getHeader("X-Haodai-Openapi-SignType");
//            String signData = request.getHeader("X-Haodai-Openapi-SignData");
//
//            String params = IOUtils.toString(request.getInputStream(), "UTF-8");
//            logger.info("验证码参数：" + params);
//
//
//
//            // 验签
//            String checkFilter = HaoDaiUtils.checkFilter(params, signData, appid, version, timestamp);
//
//            if (!CommUtils.isNull(checkFilter)) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_AUTH_FAIL);
//                haoDaiResponse.setMsg(checkFilter);
//                logger.info("saveCode()请求失败：" + checkFilter);
//                return haoDaiResponse;
//            }
//
//            HdBindCardCheckReq hdBindCardCheckReq = JSONObject.parseObject(params, HdBindCardCheckReq.class);
//
//            if (hdBindCardCheckReq == null) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("保存银行卡验证码参数为空");
//                logger.info(sessionId + "好贷保存银行卡验证码请求参数为空");
//                return haoDaiResponse;
//            }
//            orderNo = hdBindCardCheckReq.getOrder_no();
//            haoDaiResponse = haoDaiNewService.saveBankCardWithCode(hdBindCardCheckReq, sessionId);
//            SxyThirdInterfaceLogUtils.setSxyLog(CHANNEL_ID, orderNo, haoDaiResponse.getCode(), haoDaiResponse.getMsg(), "订单号");
//        } catch (Exception e) {
//            logger.error("=====好贷网>>银行卡验证码接口saveCode()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("银行卡验证码系统异常，请稍后重试");
//        }
//        logger.info(sessionId + "获取银行卡验证码接口返回数据为" + JSON.toJSONString(haoDaiResponse));
//
//        return haoDaiResponse;
//    }
//
//    /**
//     * 确认签约
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping("sxyDrainage/haodai/withDraw.do")
//    @ResponseBody
//    public HaoDaiResponse withDraw(HttpServletRequest request) {
//        logger.info("好贷网>>>进入HaoDaiWangController withDraw()确认金额接口");
//        long sessionId = System.currentTimeMillis();
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//
//        try {
//
//            String appid = request.getHeader("X-Haodai-Openapi-Appid");
//            String version = request.getHeader("X-Haodai-Openapi-Version");
//            String timestamp = request.getHeader("X-Haodai-Openapi-Timestamp");
//            String signType = request.getHeader("X-Haodai-Openapi-SignType");
//            String signData = request.getHeader("X-Haodai-Openapi-SignData");
//
//            String params = IOUtils.toString(request.getInputStream(), "UTF-8");
//
//            // 验签
//            String checkFilter = HaoDaiUtils.checkFilter(params, signData, appid, version, timestamp);
//
//            if (!CommUtils.isNull(checkFilter)) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_AUTH_FAIL);
//                haoDaiResponse.setMsg(checkFilter);
//                logger.info("withDraw()请求失败：" + checkFilter);
//                return haoDaiResponse;
//            }
//
//            HdSureLoanReq hdSureLoanReq = JSONObject.parseObject(params, HdSureLoanReq.class);
//
//            haoDaiResponse = haoDaiNewService.withDraw(hdSureLoanReq, sessionId);
//
//        } catch (Exception e) {
//            logger.error("=====好贷网>>确认金额接口withDraw()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("确认金额系统异常，请稍后重试");
//        }
//        logger.info(sessionId + "确认签约返回数据为" + JSON.toJSONString(haoDaiResponse));
//        return haoDaiResponse;
//    }
//
//    /**
//     * 主动还款
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping("sxyDrainage/haodai/activeRepay.do")
//    @ResponseBody
//    public HaoDaiResponse updateRepay(HttpServletRequest request) {
//        logger.info("好贷网>>>进入HaoDaiWangController updateRepay()主动还款接口");
//        long sessionId = System.currentTimeMillis();
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//        String orderNo = null;
//        try {
//
//            String appid = request.getHeader("X-Haodai-Openapi-Appid");
//            String version = request.getHeader("X-Haodai-Openapi-Version");
//            String timestamp = request.getHeader("X-Haodai-Openapi-Timestamp");
//            String signType = request.getHeader("X-Haodai-Openapi-SignType");
//            String signData = request.getHeader("X-Haodai-Openapi-SignData");
//
//            String params = IOUtils.toString(request.getInputStream(), "UTF-8");
//
//            // 验签
//            String checkFilter = HaoDaiUtils.checkFilter(params, signData, appid, version, timestamp);
//
//            if (!CommUtils.isNull(checkFilter)) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_AUTH_FAIL);
//                haoDaiResponse.setMsg(checkFilter);
//                logger.info("updateRepay()请求失败：" + checkFilter);
//                return haoDaiResponse;
//            }
//
//            HdCommonReq hdCommonReq = JSONObject.parseObject(params, HdCommonReq.class);
//
//            orderNo = hdCommonReq.getOrder_no();
//
//            haoDaiResponse = haoDaiNewService.updateActiveRepayment(hdCommonReq, sessionId);
//            SxyThirdInterfaceLogUtils.setSxyLog(CHANNEL_ID, orderNo, haoDaiResponse.getCode(), haoDaiResponse.getMsg(), "订单号");
//        } catch (Exception e) {
//            logger.error("=====好贷网>>主动还款接口updateRepay()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("主动还款系统异常，请稍后重试");
//        }
//        logger.info(sessionId + "HaoDaiWangController.updateRepay" + JSON.toJSONString(haoDaiResponse));
//
//        return haoDaiResponse;
//    }
//
//    /**
//     * 获取银行卡列表接口
//     *
//     * @param appid
//     * @param version
//     * @param timestamp
//     * @param signType
//     * @param signData
//     * @param hdCommonReq
//     * @return
//     */
//    @RequestMapping("sxyDrainage/haodai/getBankCard.do")
//    @ResponseBody
//    public HaoDaiResponse getBankCard(HttpServletRequest request) {
//        logger.info("好贷网>>>进入HaoDaiWangController getBankCard()获取银行卡列表接口");
//        long sessionId = System.currentTimeMillis();
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//
//        try {
//            String appid = request.getHeader("X-Haodai-Openapi-Appid");
//            String version = request.getHeader("X-Haodai-Openapi-Version");
//            String timestamp = request.getHeader("X-Haodai-Openapi-Timestamp");
//            String signData = request.getHeader("X-Haodai-Openapi-SignData");
//            String signType = request.getHeader("X-Haodai-Openapi-SignType");
//            // String body = HaoDaiUtils.getBody(request);
//            String body = IOUtils.toString(request.getInputStream(), "UTF-8");
//            logger.info("好贷网>>>请求参数Body:" + body + "APP_ID：" + appid + " 版本:" + version + " 时间戳:" + timestamp + " 签名算法:" + signType + " 签名值" + signData);
//
//            // 验签
//            String checkFilter = HaoDaiUtils.checkFilter(body, signData, appid, version, timestamp);
//            if (!CommUtils.isNull(checkFilter)) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_AUTH_FAIL);
//                haoDaiResponse.setMsg(checkFilter);
//                logger.info("checkUser()请求失败：" + checkFilter);
//                return haoDaiResponse;
//            }
//
//            HdCommonReq hdCommonReq = JSON.parseObject(body, HdCommonReq.class);
//            String orderNo = hdCommonReq.getOrder_no();
//            haoDaiResponse = haoDaiNewService.getBankCard(hdCommonReq, sessionId);
//            SxyThirdInterfaceLogUtils.setSxyLog(CHANNEL_ID, orderNo, haoDaiResponse.getCode(), haoDaiResponse.getMsg(), "订单号");
//        } catch (Exception e) {
//            logger.error("=====好贷网>>>获取银行卡列表接口方法getBankCard()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("获取银行卡列表接口系统异常，请稍后重试");
//        }
//
//        return haoDaiResponse;
//    }
//
//    /**
//     * 借款合同查询接口
//     *
//     * @param appid
//     * @param version
//     * @param timestamp
//     * @param signType
//     * @param signData
//     * @param hdCommonReq
//     * @return
//     */
//    @RequestMapping("sxyDrainage/haodai/getContract.do")
//    @ResponseBody
//    public HaoDaiResponse getContract(HttpServletRequest request) {
//        logger.info("好贷网>>>进入HaoDaiWangController getContract()借款合同查询接口");
//        long sessionId = System.currentTimeMillis();
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//
//        try {
//            String appid = request.getHeader("X-Haodai-Openapi-Appid");
//            String version = request.getHeader("X-Haodai-Openapi-Version");
//            String timestamp = request.getHeader("X-Haodai-Openapi-Timestamp");
//            String signData = request.getHeader("X-Haodai-Openapi-SignData");
//            String signType = request.getHeader("X-Haodai-Openapi-SignType");
//            // String body = HaoDaiUtils.getBody(request);
//            String body = IOUtils.toString(request.getInputStream(), "UTF-8");
//            logger.info("好贷网>>>请求参数Body:" + body + "APP_ID：" + appid + " 版本:" + version + " 时间戳:" + timestamp + " 签名算法:" + signType + " 签名值" + signData);
//
//            // 验签
//            String checkFilter = HaoDaiUtils.checkFilter(body, signData, appid, version, timestamp);
//            if (!CommUtils.isNull(checkFilter)) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_AUTH_FAIL);
//                haoDaiResponse.setMsg(checkFilter);
//                logger.info("checkUser()请求失败：" + checkFilter);
//                return haoDaiResponse;
//            }
//
//            HdCommonReq hdCommonReq = JSON.parseObject(body, HdCommonReq.class);
//
//            haoDaiResponse = haoDaiNewService.getContract(hdCommonReq, sessionId);
//
//        } catch (Exception e) {
//            logger.error("=====好贷网>>>借款合同查询接口方法getContract()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("借款合同查询接口系统异常，请稍后重试");
//        }
//        return haoDaiResponse;
//    }
//
//    /**
//     * 试算接口
//     *
//     * @param appid
//     * @param version
//     * @param timestamp
//     * @param signType
//     * @param signData
//     * @param hdTrialReq
//     * @return
//     */
//    @RequestMapping("sxyDrainage/haodai/trial.do")
//    @ResponseBody
//    public HaoDaiResponse trial(HttpServletRequest request) {
//        logger.info("好贷网>>>进入HaoDaiWangController trial()试算接口");
//        long sessionId = System.currentTimeMillis();
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//
//        try {
//            String appid = request.getHeader("X-Haodai-Openapi-Appid");
//            String version = request.getHeader("X-Haodai-Openapi-Version");
//            String timestamp = request.getHeader("X-Haodai-Openapi-Timestamp");
//            String signData = request.getHeader("X-Haodai-Openapi-SignData");
//            String signType = request.getHeader("X-Haodai-Openapi-SignType");
//            // String body = HaoDaiUtils.getBody(request);
//            String body = IOUtils.toString(request.getInputStream(), "UTF-8");
//            logger.info("好贷网>>>请求参数Body:" + body + "APP_ID：" + appid + " 版本:" + version + " 时间戳:" + timestamp + " 签名算法:" + signType + " 签名值" + signData);
//
//            // 验签
//            String checkFilter = HaoDaiUtils.checkFilter(body, signData, appid, version, timestamp);
//            if (!CommUtils.isNull(checkFilter)) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_AUTH_FAIL);
//                haoDaiResponse.setMsg(checkFilter);
//                logger.info("checkUser()请求失败：" + checkFilter);
//                return haoDaiResponse;
//            }
//
//            HdTrialReq hdTrialReq = JSON.parseObject(body, HdTrialReq.class);
//            haoDaiResponse = haoDaiNewService.trial(hdTrialReq, sessionId);
//
//        } catch (Exception e) {
//            logger.error("=====好贷网>>>试算接口方法trial()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("试算接口系统异常，请稍后重试");
//        }
//        return haoDaiResponse;
//    }
//
//    /**
//     * 拉取审批结论接口
//     *
//     * @param appid
//     * @param version
//     * @param timestamp
//     * @param signType
//     * @param signData
//     * @param hdCommonReq
//     * @return
//     */
//    @RequestMapping("sxyDrainage/haodai/pullApprove.do")
//    @ResponseBody
//    public HaoDaiResponse pullApprove(HttpServletRequest request) {
//        logger.info("好贷网>>>进入HaoDaiWangController pullApprove()拉取审批结论接口");
//        long sessionId = System.currentTimeMillis();
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//
//        try {
//            String appid = request.getHeader("X-Haodai-Openapi-Appid");
//            String version = request.getHeader("X-Haodai-Openapi-Version");
//            String timestamp = request.getHeader("X-Haodai-Openapi-Timestamp");
//            String signData = request.getHeader("X-Haodai-Openapi-SignData");
//            String signType = request.getHeader("X-Haodai-Openapi-SignType");
//            // String body = HaoDaiUtils.getBody(request);
//            String body = IOUtils.toString(request.getInputStream(), "UTF-8");
//            logger.info("好贷网>>>请求参数Body:" + body + "APP_ID：" + appid + " 版本:" + version + " 时间戳:" + timestamp + " 签名算法:" + signType + " 签名值" + signData);
//
//            // 验签
//            String checkFilter = HaoDaiUtils.checkFilter(body, signData, appid, version, timestamp);
//            if (!CommUtils.isNull(checkFilter)) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_AUTH_FAIL);
//                haoDaiResponse.setMsg(checkFilter);
//                logger.info("checkUser()请求失败：" + checkFilter);
//                return haoDaiResponse;
//            }
//
//            HdCommonReq hdCommonReq = JSON.parseObject(body, HdCommonReq.class);
//            haoDaiResponse = haoDaiNewService.pullApprove(hdCommonReq, sessionId);
//
//        } catch (Exception e) {
//            logger.error("=====好贷网>>>拉取审批结论接口法pullApprove()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("拉取审批结论接口系统异常，请稍后重试");
//        }
//        return haoDaiResponse;
//    }
//
//    /**
//     * 拉取订单状态接口
//     *
//     * @param appid
//     * @param version
//     * @param timestamp
//     * @param signType
//     * @param signData
//     * @param hdCommonReq
//     * @return
//     */
//    @RequestMapping("sxyDrainage/haodai/pullOrderStatus.do")
//    @ResponseBody
//    public HaoDaiResponse pullOrderStatus(HttpServletRequest request) {
//        logger.info("好贷网>>>进入HaoDaiWangController pullOrderStatus()订单状态反馈拉取接口");
//        long sessionId = System.currentTimeMillis();
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//
//        try {
//            String appid = request.getHeader("X-Haodai-Openapi-Appid");
//            String version = request.getHeader("X-Haodai-Openapi-Version");
//            String timestamp = request.getHeader("X-Haodai-Openapi-Timestamp");
//            String signData = request.getHeader("X-Haodai-Openapi-SignData");
//            String signType = request.getHeader("X-Haodai-Openapi-SignType");
//            // String body = HaoDaiUtils.getBody(request);
//            String body = IOUtils.toString(request.getInputStream(), "UTF-8");
//            logger.info("好贷网>>>请求参数Body:" + body + "APP_ID：" + appid + " 版本:" + version + " 时间戳:" + timestamp + " 签名算法:" + signType + " 签名值" + signData);
//
//            // 验签
//            String checkFilter = HaoDaiUtils.checkFilter(body, signData, appid, version, timestamp);
//            if (!CommUtils.isNull(checkFilter)) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_AUTH_FAIL);
//                haoDaiResponse.setMsg(checkFilter);
//                logger.info("checkUser()请求失败：" + checkFilter);
//                return haoDaiResponse;
//            }
//
//            HdCommonReq hdCommonReq = JSON.parseObject(body, HdCommonReq.class);
//            haoDaiResponse = haoDaiNewService.pullOrderStatus(hdCommonReq, sessionId);
//
//        } catch (Exception e) {
//            logger.error("=====好贷网>>>结束订单状态反馈方法pullOrderStatus()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("订单状态反馈拉取接口系统异常，请稍后重试");
//        }
//        return haoDaiResponse;
//    }
//
//    /**
//     * 还款计划反馈拉取接口
//     *
//     * @param appid
//     * @param version
//     * @param timestamp
//     * @param signType
//     * @param signData
//     * @param hdCommonReq
//     * @return
//     */
//
//    @RequestMapping("sxyDrainage/haodai/pullRepaymentPlan.do")
//    @ResponseBody
//    public HaoDaiResponse pullRepaymentPlan(HttpServletRequest request) {
//        logger.info("好贷网>>>进入HaoDaiWangController pullRepaymentPlan()还款计划反馈拉取接口");
//        long sessionId = System.currentTimeMillis();
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//
//        try {
//            String appid = request.getHeader("X-Haodai-Openapi-Appid");
//            String version = request.getHeader("X-Haodai-Openapi-Version");
//            String timestamp = request.getHeader("X-Haodai-Openapi-Timestamp");
//            String signData = request.getHeader("X-Haodai-Openapi-SignData");
//            String signType = request.getHeader("X-Haodai-Openapi-SignType");
//            // String body = HaoDaiUtils.getBody(request);
//            String body = IOUtils.toString(request.getInputStream(), "UTF-8");
//            logger.info("好贷网>>>请求参数Body:" + body + "APP_ID：" + appid + " 版本:" + version + " 时间戳:" + timestamp + " 签名算法:" + signType + " 签名值" + signData);
//
//            // 验签
//            String checkFilter = HaoDaiUtils.checkFilter(body, signData, appid, version, timestamp);
//            if (!CommUtils.isNull(checkFilter)) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_AUTH_FAIL);
//                haoDaiResponse.setMsg(checkFilter);
//                logger.info("checkUser()请求失败：" + checkFilter);
//                return haoDaiResponse;
//            }
//
//            HdCommonReq hdCommonReq = JSON.parseObject(body, HdCommonReq.class);
//            haoDaiResponse = haoDaiNewService.pullRepaymentPlan(hdCommonReq, sessionId);
//
//        } catch (Exception e) {
//            logger.error("=====好贷网>>>结束还款计划反馈拉取接口pullRepaymentPlan()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("还款计划反馈拉取接口系统异常，请稍后重试");
//        }
//        return haoDaiResponse;
//    }
//
//    @RequestMapping("sxyDrainage/haodai/withH5Result.do")
//    public String getResult(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
//        try {
//            long sessionId = System.currentTimeMillis();
//            String orderNo = request.getParameter("order_no");
//            String result = request.getParameter("result");
//            JSONObject parseObject = JSONObject.parseObject(result);
//            Map<String, Object> map = new HashMap<>();
//            SxyThirdInterfaceLogUtils.setSxyLog(CHANNEL_ID, orderNo, "00", result, "订单号");
//
//            if (CommUtils.isNull(orderNo)) {
//                logger.info(sessionId + "我方签约处理三方订单为空");
//                modelMap.put("result", "我方签约处理三方订单为空");
//                return "sign_fail_hd";
//            }
//            BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(orderNo);
//            if (bwOrder == null) {
//                logger.info(orderNo + "我方签约处理订单为空");
//                modelMap.put("result", "我方签约处理订单为空");
//                return "sign_fail_hd";
//            }
//
//            if (!CommUtils.isNull(parseObject)) {
//                String code = parseObject.getString("code");
//                String msg = parseObject.getString("msg");
//
//                if ("0".equals(code)) {
//                    // String hdSuccessUrl = RedisUtils.lpop("tripartite:hdWithDraw:" + orderNo);
//                    String hdSuccessUrl = RedisUtils.get("tripartite:hdWithDraw:" + orderNo);
//                    if (CommUtils.isNull(hdSuccessUrl)) {
//                        modelMap.put("result", "签约时间过长请重新返回确认金额");
//                        return "sign_fail_hd";
//                    }
//                    RedisUtils.del("tripartite:hdWithDraw:" + orderNo);
//
//                    return "redirect:" + hdSuccessUrl;
//                } else {
//                    if (bwOrder.getStatusId() == 11 || bwOrder.getStatusId() == 12 || bwOrder.getStatusId() == 14 || bwOrder.getStatusId() == 9 || bwOrder.getStatusId() == 6) {
//                        logger.info(orderNo + "已经签约");
//
//                        // RedisUtils.lpop("tripartite:hdWithDraw:" + orderNo);
//                        modelMap.put("result", "请勿重复签约");
//                        return "sign_fail_hd";
//                    } else {
//                        // RedisUtils.lpop("tripartite:hdWithDraw:" + orderNo);
//
//                        map.put("result", msg);
//                        map.put("channelId", CHANNEL_ID);
//                        map.put("userPhone", CHANNEL_ID);
//                        map.put("orderId", bwOrder.getId());
//                        String json = JSON.toJSONString(map);
//                        RedisUtils.rpush("tripartite:orderStatusNotify:" + CHANNEL_ID, json);
//                        modelMap.put("result", msg);
//                        return "sign_fail_hd";
//                    }
//
//                }
//
//            } else {
//                logger.info(orderNo + "签约接口返回为空");
//                return "sign_fail_hd";
//            }
//        } catch (Exception e) {
//            logger.error("签约回调地址方法异常", e);
//
//            return "sign_fail_hd";
//        }
//
//    }
//
//}
