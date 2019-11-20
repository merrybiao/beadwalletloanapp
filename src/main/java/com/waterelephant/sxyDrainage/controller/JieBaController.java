//package com.waterelephant.sxyDrainage.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.sxyDrainage.entity.jieba.*;
//import com.waterelephant.sxyDrainage.service.JieBaService;
//import com.waterelephant.sxyDrainage.utils.jieba.JieBaConstant;
//import com.waterelephant.sxyDrainage.utils.jieba.JieBaUtil;
//import com.waterelephant.utils.StringUtil;
//import org.apache.commons.lang3.StringEscapeUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//
///**
// * (code:jb001)
// *
// * @Author: ZhangChong
// * @Description:
// * @Date: Created in 11:29 2018/6/13
// * @Modified By:
// */
//@RequestMapping("/sxyDrainage")
//@Controller
//public class JieBaController {
//    private Logger logger = Logger.getLogger(JieBaController.class);
//    private JieBaService jieBaService;
//
//    @Autowired
//    public JieBaController(JieBaService jieBaService) {
//        this.jieBaService = jieBaService;
//    }
//
//    @ResponseBody
//    @RequestMapping("/checkUser.do")
//    public JieBaResponse checkUser(@RequestBody JieBaRequest jieBaRequest) {
//        Long sessionId = System.currentTimeMillis();
//        logger.info(sessionId + "：开始JieBaController checkUser method：" + JSON.toJSONString(jieBaRequest));
//        JieBaResponse jieBaResponse = new JieBaResponse();
//        try {
//            //检验参数
//            String sign = jieBaRequest.getSign();
//            String timestamp = jieBaRequest.getTimestamp();
//            String bizData = jieBaRequest.getBiz_data();
//
//            if (StringUtil.isEmpty(sign) || StringUtil.isEmpty(timestamp) || StringUtil.isEmpty(bizData)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("参数为空");
//                logger.info(sessionId + "：结束JieBaController checkUser method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            //校验签名
//            String signStr = JieBaUtil.getSignStr(bizData, timestamp);
//            boolean flag = JieBaUtil.checkSign(sign, signStr, JieBaConstant.jbPublicKey);
//            if (!flag) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("签名错误");
//                logger.info(sessionId + "：结束JieBaController checkUser method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            CheckUser checkUser = JSON.parseObject(bizData, CheckUser.class);
//            //处理业务
//            jieBaResponse = jieBaService.checkUser(sessionId, checkUser);
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行JieBaController checkUser method异常：", e);
//            jieBaResponse.setCode(400);
//            jieBaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束JieBaController checkUser method：" + JSON.toJSONString(jieBaResponse));
//        return jieBaResponse;
//    }
//
//    @ResponseBody
//    @RequestMapping("/pushOrderInfo.do")
//    public JieBaResponse pushOrderInfo(@RequestBody JieBaRequest jieBaRequest) {
//        Long sessionId = System.currentTimeMillis();
//        logger.info(sessionId + "：开始JieBaController pushOrderInfo method");
//        JieBaResponse jieBaResponse = new JieBaResponse();
//        try {
//            //检验参数
//            String sign = jieBaRequest.getSign();
//            String timestamp = jieBaRequest.getTimestamp();
//            String bizData = jieBaRequest.getBiz_data();
//
//            if (StringUtil.isEmpty(sign) || StringUtil.isEmpty(timestamp) || StringUtil.isEmpty(bizData)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("参数为空");
//                logger.info(sessionId + "：结束JieBaController pushOrderInfo method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            //校验签名
//            String signStr = JieBaUtil.getSignStr(bizData, timestamp);
//            boolean flag = JieBaUtil.checkSign(sign, signStr, JieBaConstant.jbPublicKey);
//            if (!flag) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("签名错误");
//                logger.info(sessionId + "：结束JieBaController pushOrderInfo method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            PushOrderInfo pushOrderInfo = JSON.parseObject(bizData, PushOrderInfo.class);
//            //处理业务
//            jieBaResponse = jieBaService.savePushOrderInfo(sessionId, pushOrderInfo);
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行JieBaController pushOrderInfo method异常：", e);
//            jieBaResponse.setCode(400);
//            jieBaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束JieBaController pushOrderInfo method：" + JSON.toJSONString(jieBaResponse));
//        return jieBaResponse;
//    }
//
//    @ResponseBody
//    @RequestMapping("/pushAddOrderInfo.do")
//    public JieBaResponse pushAddOrderInfo(@RequestBody JieBaRequest jieBaRequest) {
//        Long sessionId = System.currentTimeMillis();
//        logger.info(sessionId + "：开始JieBaController pushAddOrderInfo method");
//        JieBaResponse jieBaResponse = new JieBaResponse();
//        try {
//            //检验参数
//            String sign = jieBaRequest.getSign();
//            String timestamp = jieBaRequest.getTimestamp();
//            String bizData = jieBaRequest.getBiz_data();
//
//            if (StringUtil.isEmpty(sign) || StringUtil.isEmpty(timestamp) || StringUtil.isEmpty(bizData)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("参数为空");
//                logger.info(sessionId + "：结束JieBaController pushAddOrderInfo method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            //校验签名
//            String signStr = JieBaUtil.getSignStr(bizData, timestamp);
//            boolean flag = JieBaUtil.checkSign(sign, signStr, JieBaConstant.jbPublicKey);
//            if (!flag) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("签名错误");
//                logger.info(sessionId + "：结束JieBaController pushAddOrderInfo method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            PushAddOrderInfo pushAddOrderInfo = JSON.parseObject(bizData, PushAddOrderInfo.class);
//            //处理业务
//            jieBaResponse = jieBaService.savePushAddOrderInfo(sessionId, pushAddOrderInfo);
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行JieBaController pushAddOrderInfo method异常：", e);
//            jieBaResponse.setCode(400);
//            jieBaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束JieBaController pushAddOrderInfo method：" + JSON.toJSONString(jieBaResponse));
//        return jieBaResponse;
//    }
//
//    @ResponseBody
//    @RequestMapping("/bindCardReady.do")
//    public JieBaResponse bindCardReady(@RequestBody JieBaRequest jieBaRequest) {
//        Long sessionId = System.currentTimeMillis();
//        logger.info(sessionId + "：开始JieBaController bindCardReady method：" + JSON.toJSONString(jieBaRequest));
//        JieBaResponse jieBaResponse = new JieBaResponse();
//        try {
//            //检验参数
//            String sign = jieBaRequest.getSign();
//            String timestamp = jieBaRequest.getTimestamp();
//            String bizData = jieBaRequest.getBiz_data();
//
//            if (StringUtil.isEmpty(sign) || StringUtil.isEmpty(timestamp) || StringUtil.isEmpty(bizData)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("参数为空");
//                logger.info(sessionId + "：结束JieBaController bindCardReady method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            //校验签名
//            String signStr = JieBaUtil.getSignStr(bizData, timestamp);
//            boolean flag = JieBaUtil.checkSign(sign, signStr, JieBaConstant.jbPublicKey);
//            if (!flag) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("签名错误");
//                logger.info(sessionId + "：结束JieBaController bindCardReady method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            BindCardInfo bindCardInfo = JSON.parseObject(bizData, BindCardInfo.class);
//            //处理业务
//            jieBaResponse = jieBaService.updateBindCardReady(sessionId, bindCardInfo);
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行JieBaController bindCardReady method异常：", e);
//            jieBaResponse.setCode(400);
//            jieBaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束JieBaController bindCardReady method：" + JSON.toJSONString(jieBaResponse));
//        return jieBaResponse;
//    }
//
//    @ResponseBody
//    @RequestMapping("/bindCardSure.do")
//    public JieBaResponse bindCardSure(@RequestBody JieBaRequest jieBaRequest) {
//        Long sessionId = System.currentTimeMillis();
//        logger.info(sessionId + "：开始JieBaController bindCardSure method：" + JSON.toJSONString(jieBaRequest));
//        JieBaResponse jieBaResponse = new JieBaResponse();
//        try {
//            //检验参数
//            String sign = jieBaRequest.getSign();
//            String timestamp = jieBaRequest.getTimestamp();
//            String bizData = jieBaRequest.getBiz_data();
//
//            if (StringUtil.isEmpty(sign) || StringUtil.isEmpty(timestamp) || StringUtil.isEmpty(bizData)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("参数为空");
//                logger.info(sessionId + "：结束JieBaController bindCardSure method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            //校验签名
//            String signStr = JieBaUtil.getSignStr(bizData, timestamp);
//            boolean flag = JieBaUtil.checkSign(sign, signStr, JieBaConstant.jbPublicKey);
//            if (!flag) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("签名错误");
//                logger.info(sessionId + "：结束JieBaController bindCardSure method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            BindCardInfo bindCardInfo = JSON.parseObject(bizData, BindCardInfo.class);
//            //处理业务
//            jieBaResponse = jieBaService.updateBindCardSure(sessionId, bindCardInfo);
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行JieBaController bindCardSure method异常：", e);
//            jieBaResponse.setCode(400);
//            jieBaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束JieBaController bindCardSure method：" + JSON.toJSONString(jieBaResponse));
//        return jieBaResponse;
//    }
//
//    @ResponseBody
//    @RequestMapping("/pullApproveResult.do")
//    public JieBaResponse pullApproveResult(@RequestBody JieBaRequest jieBaRequest) {
//        Long sessionId = System.currentTimeMillis();
//        logger.info(sessionId + "：开始JieBaController pullApproveResult method：" + JSON.toJSONString(jieBaRequest));
//        JieBaResponse jieBaResponse = new JieBaResponse();
//        try {
//            //检验参数
//            String sign = jieBaRequest.getSign();
//            String timestamp = jieBaRequest.getTimestamp();
//            String bizData = jieBaRequest.getBiz_data();
//
//            if (StringUtil.isEmpty(sign) || StringUtil.isEmpty(timestamp) || StringUtil.isEmpty(bizData)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("参数为空");
//                logger.info(sessionId + "：结束JieBaController pullApproveResult method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            //校验签名
//            String signStr = JieBaUtil.getSignStr(bizData, timestamp);
//            boolean flag = JieBaUtil.checkSign(sign, signStr, JieBaConstant.jbPublicKey);
//            if (!flag) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("签名错误");
//                logger.info(sessionId + "：结束JieBaController pullApproveResult method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            CommonPullInfo commonPullInfo = JSON.parseObject(bizData, CommonPullInfo.class);
//            //处理业务
//            jieBaResponse = jieBaService.pullApproveResult(sessionId, commonPullInfo);
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行JieBaController pullApproveResult method异常：", e);
//            jieBaResponse.setCode(400);
//            jieBaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束JieBaController pullApproveResult method：" + JSON.toJSONString(jieBaResponse));
//        return jieBaResponse;
//    }
//
//    @ResponseBody
//    @RequestMapping("/pullRepaymentPlan.do")
//    public JieBaResponse pullRepaymentPlan(@RequestBody JieBaRequest jieBaRequest) {
//        Long sessionId = System.currentTimeMillis();
//        logger.info(sessionId + "：开始JieBaController pullRepaymentPlan method：" + JSON.toJSONString(jieBaRequest));
//        JieBaResponse jieBaResponse = new JieBaResponse();
//        try {
//            //检验参数
//            String sign = jieBaRequest.getSign();
//            String timestamp = jieBaRequest.getTimestamp();
//            String bizData = jieBaRequest.getBiz_data();
//
//            if (StringUtil.isEmpty(sign) || StringUtil.isEmpty(timestamp) || StringUtil.isEmpty(bizData)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("参数为空");
//                logger.info(sessionId + "：结束JieBaController pullRepaymentPlan method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            //校验签名
//            String signStr = JieBaUtil.getSignStr(bizData, timestamp);
//            boolean flag = JieBaUtil.checkSign(sign, signStr, JieBaConstant.jbPublicKey);
//            if (!flag) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("签名错误");
//                logger.info(sessionId + "：结束JieBaController pullRepaymentPlan method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            CommonPullInfo commonPullInfo = JSON.parseObject(bizData, CommonPullInfo.class);
//            //处理业务
//            jieBaResponse = jieBaService.pullRepaymentPlan(sessionId, commonPullInfo);
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行JieBaController pullRepaymentPlan method异常：", e);
//            jieBaResponse.setCode(400);
//            jieBaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束JieBaController pullRepaymentPlan method：" + JSON.toJSONString(jieBaResponse));
//        return jieBaResponse;
//    }
//
//    @ResponseBody
//    @RequestMapping("/pullOrderStatus.do")
//    public JieBaResponse pullOrderStatus(@RequestBody JieBaRequest jieBaRequest) {
//        Long sessionId = System.currentTimeMillis();
//        logger.info(sessionId + "：开始JieBaController pullOrderStatus method：" + JSON.toJSONString(jieBaRequest));
//        JieBaResponse jieBaResponse = new JieBaResponse();
//        try {
//            //检验参数
//            String sign = jieBaRequest.getSign();
//            String timestamp = jieBaRequest.getTimestamp();
//            String bizData = jieBaRequest.getBiz_data();
//
//            if (StringUtil.isEmpty(sign) || StringUtil.isEmpty(timestamp) || StringUtil.isEmpty(bizData)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("参数为空");
//                logger.info(sessionId + "：结束JieBaController pullOrderStatus method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            //校验签名
//            String signStr = JieBaUtil.getSignStr(bizData, timestamp);
//            boolean flag = JieBaUtil.checkSign(sign, signStr, JieBaConstant.jbPublicKey);
//            if (!flag) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("签名错误");
//                logger.info(sessionId + "：结束JieBaController pullOrderStatus method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            CommonPullInfo commonPullInfo = JSON.parseObject(bizData, CommonPullInfo.class);
//            //处理业务
//            jieBaResponse = jieBaService.pullOrderStatus(sessionId, commonPullInfo);
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行JieBaController pullOrderStatus method异常：", e);
//            jieBaResponse.setCode(400);
//            jieBaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束JieBaController pullOrderStatus method：" + JSON.toJSONString(jieBaResponse));
//        return jieBaResponse;
//    }
//
//    @ResponseBody
//    @RequestMapping("/updateApplyRepay.do")
//    public JieBaResponse updateApplyRepay(@RequestBody JieBaRequest jieBaRequest) {
//        Long sessionId = System.currentTimeMillis();
//        logger.info(sessionId + "：开始JieBaController updateApplyRepay method：" + JSON.toJSONString(jieBaRequest));
//        JieBaResponse jieBaResponse = new JieBaResponse();
//        try {
//            //检验参数
//            String sign = jieBaRequest.getSign();
//            String timestamp = jieBaRequest.getTimestamp();
//            String bizData = jieBaRequest.getBiz_data();
//
//            if (StringUtil.isEmpty(sign) || StringUtil.isEmpty(timestamp) || StringUtil.isEmpty(bizData)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("参数为空");
//                logger.info(sessionId + "：结束JieBaController updateApplyRepay method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            //校验签名
//            String signStr = JieBaUtil.getSignStr(bizData, timestamp);
//            boolean flag = JieBaUtil.checkSign(sign, signStr, JieBaConstant.jbPublicKey);
//            if (!flag) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("签名错误");
//                logger.info(sessionId + "：结束JieBaController updateApplyRepay method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            RepayInfo repayInfo = JSON.parseObject(bizData, RepayInfo.class);
//            //处理业务
//            jieBaResponse = jieBaService.updateApplyRepay(sessionId, repayInfo);
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行JieBaController updateApplyRepay method异常：", e);
//            jieBaResponse.setCode(400);
//            jieBaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束JieBaController updateApplyRepay method：" + JSON.toJSONString(jieBaResponse));
//        return jieBaResponse;
//    }
//}
