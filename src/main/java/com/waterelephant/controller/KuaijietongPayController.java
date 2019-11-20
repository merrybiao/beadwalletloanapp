package com.waterelephant.controller;

import com.alibaba.fastjson.JSON;
import com.kjtpay.gateway.common.domain.VerifyResult;
import com.kjtpay.gateway.common.util.security.SecurityService;
import com.waterelephant.annotation.LockAndSyncRequest;
import com.waterelephant.constants.KJTConstant;
import com.waterelephant.dto.RepaymentDto;
import com.waterelephant.entity.BwCapitalWithhold;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwPlatformRecord;
import com.waterelephant.service.*;
import com.waterelephant.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 快捷通支付
 */
@Controller
@RequestMapping("/kuaijietongPay")
public class KuaijietongPayController {
    private Logger logger = Logger.getLogger(KuaijietongPayController.class);
    @Autowired
    private IBwRepaymentService bwRepaymentService;
    @Autowired
    private IBwRepaymentPlanService bwRepaymentPlanService;
    @Autowired
    private BwCapitalWithholdService bwCapitalWithholdService;
    @Autowired
    private IBwOrderService bwOrderService;
    @Autowired
    private BwPlatformRecordService bwPlatformRecordService;

    @ResponseBody
    @RequestMapping("/withholdNotify.do")
    @LockAndSyncRequest(redisKeyAfterByRequestName = "outer_trade_no")
    public void withholdNotify(HttpServletRequest request, HttpServletResponse response) {
        String outerTradeNo = "";
        try {
            Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
            logger.info("【KuaijietongPayController.kuaijietongPay】paramMap:" + paramMap);
            String failReason = paramMap.get("failReason");// 失败原因
            outerTradeNo = paramMap.get("outer_trade_no");//商户三方订单号
            logger.info("【KuaijietongPayController.kuaijietongPay】三方单号为：" + outerTradeNo + "开始验签");
            //验签
            boolean checkSignBool = checkSign(paramMap);
            if (!checkSignBool) {
                logger.info("验签失败");
                ControllerUtil.printText(response, "fail");
                return;
            }
            logger.info("【KuaijietongPayController.kuaijietongPay】三方单号为：" + outerTradeNo + "验签通过");
            //数据持久化
            long withholdId = Long.parseLong(outerTradeNo.substring(outerTradeNo.lastIndexOf("X") + 1));
            BwCapitalWithhold queryCapitalWithhold = bwCapitalWithholdService.queryBwCapitalWithhold(withholdId);
            if (queryCapitalWithhold == null) {
                logger.info("【KuaijietongPayController.kuaijietongPay】" + outerTradeNo + ",BwCapitalWithhold不存在");
                ControllerUtil.printText(response, "fail");
                return;
            }
            Integer queryPushStatus = queryCapitalWithhold.getPushStatus();
            if (queryPushStatus != null && queryPushStatus == 2) {
                logger.info(outerTradeNo + ",重复回调");
                ControllerUtil.printText(response, "success");
                Long orderId = queryCapitalWithhold.getOrderId();
                RedisUtils.hdel("pay_process", orderId.toString());
                RedisUtils.hdel("pay_process:11", orderId.toString());
                return;
            }

            String tradeStatus = request.getParameter("trade_status");// TRADE_SUCCESS 交易成功，用户付款成功,TRADE_FINISHED 交易结束，付款金额已结算给商户,  TRADE_CLOSED 交易关闭，交易失败
            String innerTradeNo = request.getParameter("inner_trade_no");// 快捷通订单号
            String tradeAmount = request.getParameter("trade_amount");//交易金额
            Double orderAmount = NumberUtil.parseDouble(tradeAmount, 0.0);// 支付金额
            Date nowDate = new Date();
            if (StringUtils.isNotEmpty(innerTradeNo)) {
                queryCapitalWithhold.setOtherOrderNo(innerTradeNo);
            }
            Integer payType = queryCapitalWithhold.getPayType();
            if ("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus)) {
                queryCapitalWithhold.setCode("SUCCESS");
            } else if ("TRADE_FINISHED".equalsIgnoreCase(tradeStatus)) {
                queryCapitalWithhold.setCode("FINISHED");
            }
            Map<String, String> notifyThirdData = null;
            Long orderId = queryCapitalWithhold.getOrderId();
            BwOrder bwOrder = bwOrderService.findBwOrderById(orderId.toString());
            if ("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus) || "TRADE_CLOSED".equalsIgnoreCase(tradeStatus)) {
                int payStatus = 0;
                if ("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus)) {
                    failReason = "交易成功";
                    payStatus = 2;
                } else {
                    payStatus = 3;
                }
                notifyThirdData = bwRepaymentPlanService.getNotifyThirdData(bwOrder, orderAmount, payStatus, failReason);
            }

            // 成功和完成都是成功，分两次通知，几乎同时执行
            if ("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus) || "TRADE_FINISHED".equalsIgnoreCase(tradeStatus)) {// 成功，完成
                queryCapitalWithhold.setPushStatus(2);// 扣款成功
                RepaymentDto repaymentDto = new RepaymentDto();
                repaymentDto.setUseCoupon(false);
                repaymentDto.setOrderId(orderId);
                repaymentDto.setType(payType);// 还款类型（1、还款，2、展期）
                repaymentDto.setTradeNo(innerTradeNo);
                if (SystemConstant.WITHHOLD_TEST_BOOL) {
                    repaymentDto.setAmount(queryCapitalWithhold.getMoney().doubleValue());
                } else {
                    repaymentDto.setAmount(orderAmount);
                }
                repaymentDto.setPayChannel(11);
                repaymentDto.setTradeTime(new Date());
                repaymentDto.setTradeType(1);// 交易类型 1:划拨 2:转账
                repaymentDto.setPayWay(3);
                repaymentDto.setTerminalType(queryCapitalWithhold.getTerminalType());
                repaymentDto.setTradeCode(tradeStatus);

                // 重复回调
                if (StringUtils.isNotEmpty(innerTradeNo)) {
                    BwPlatformRecord entity = new BwPlatformRecord();
                    entity.setOrderId(orderId);
                    entity.setTradeNo(innerTradeNo);
                    // 记录流水
                    int count = bwPlatformRecordService.getBwPlatformRecordCount(entity);
                    if (count > 0) {
                        ControllerUtil.printText(response, "success");
                        return;
                    }
                }

                // 支付成功
                AppResponseResult appResponseResult = bwRepaymentService.updateOrderByTradeMoney(repaymentDto);
                if ("000".equals(appResponseResult.getCode())) {
                    logger.info("【KuaijietongPayController.kuaijietongPay】快捷通支付成功orderId：" + orderId + JSON.toJSONString(appResponseResult));
                } else {
                    logger.info("【KuaijietongPayController.kuaijietongPay】快捷通支付失败orderId：" + orderId + JSON.toJSONString(appResponseResult));
                }
                RedisUtils.hdel("pay_info", orderId.toString());

                Integer terminalType = queryCapitalWithhold.getTerminalType();
                // 自动代扣成功，处理下一个还款计划
                if (terminalType != null && terminalType == 5) {
                    // bwRepaymentPlanService.saveAutoPlan2Redis(orderId);
                }
            } else if ("TRADE_CLOSED".equalsIgnoreCase(tradeStatus)) {// 失败
                // auto_withhold_repay扣款记录和还款计划ID对应
                Long repayId = NumberUtil.parseLong(RedisUtils.hget("auto_withhold_repay", withholdId + ""), 0L);
                RedisUtils.hdel("auto_withhold_repay", withholdId + "");// 防止重复回调
                // 第一次扣剩余金额，失败后扣40%，
                // 1.失败存入自动扣款队列，队列根据auto_withhold_fail是否存在判断，不存在则放入队列并做标记(auto_withhold_fail,防止一直失败循环扣款)
                // 2.队列根据auto_withhold_fail是否存在判断并减少扣款金额(40%)
                // 3.下个扣款时间定时获取扣款数据时清除所有auto_withhold_fail
                if (repayId != null && repayId > 0L && !RedisUtils.hexists("auto_withhold_fail", repayId.toString())) {
//                    RepayInfoDto repayInfoDto = bwRepaymentPlanService.selectAutoRepayByRepayId(repayId);
//                    if (repayInfoDto != null) {
//                        logger.info("outerTradeNo:" + outerTradeNo + ",repayId:" + repayId + "代扣失败,存入redis");
//                        RedisUtils.hset("auto_withhold_fail", repayId.toString(), CommUtils.convertDateToString(new Date(), SystemConstant.YMD_HMS));
//                        RedisUtils.lpush("ReapyJob:cloudAutoRepay:repay", JSON.toJSONString(repayInfoDto));
//                        RedisUtils.hset("auto_withhold_fail_store", withholdId + "", CommUtils.convertDateToString(new Date(), SystemConstant.YMD_HMS));
//
//                    }
                }
                queryCapitalWithhold.setPushStatus(3);
                RedisUtils.hdel("pay_info", orderId.toString());
                if (StringUtils.isNotEmpty(failReason)) {
                    queryCapitalWithhold.setMsg(failReason);
                }
                queryCapitalWithhold.setCode("CLOSED");
            }

            if ("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus) || "TRADE_CLOSED".equalsIgnoreCase(tradeStatus)) {
                bwRepaymentPlanService.notifyThird(notifyThirdData);// 通知三方
            }

            queryCapitalWithhold.setMoneyWithhold(new BigDecimal(orderAmount));
            queryCapitalWithhold.setUpdateTime(nowDate);
            bwCapitalWithholdService.updateBwCapitalWithhold(queryCapitalWithhold);
            RedisUtils.hdel("pay_process", orderId.toString());
            RedisUtils.hdel("pay_process:11", orderId.toString());
            RedisUtils.hdel("auto_withhold_repay", withholdId + "");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(outerTradeNo + ",快捷通回调异常", e);
            ControllerUtil.printText(response, "fail");
            return;
        }
        ControllerUtil.printText(response, "success");
    }

    @ResponseBody
    @RequestMapping("/withholdNotify2.do")
    @LockAndSyncRequest(redisKeyAfterByRequestName = "outer_trade_no")
    public void withholdNotify2(HttpServletRequest request, HttpServletResponse response) {
        String outerTradeNo = "";
        try {
            Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
            logger.info("【KuaijietongPayController.withholdNotify2】paramMap:" + paramMap);
            String failReason = paramMap.get("failReason");// 失败原因
            outerTradeNo = paramMap.get("outer_trade_no");//商户三方订单号
            logger.info("【KuaijietongPayController.withholdNotify2】outerTradeNo:" + outerTradeNo + "开始验签");
            //验签
            boolean checkSignBool = checkSign(paramMap);
            if (!checkSignBool) {
                logger.info("验签失败");
                ControllerUtil.printText(response, "fail");
                return;
            }
            logger.info("【KuaijietongPayController.withholdNotify2】outerTradeNo:" + outerTradeNo + "验签通过");
            BwCapitalWithhold queryCapitalWithhold = bwCapitalWithholdService.queryBwCapitalWithhold(outerTradeNo);
            if (queryCapitalWithhold == null) {
                logger.info("【KuaijietongPayController.withholdNotify2】outerTradeNo:" + outerTradeNo + ",BwCapitalWithhold不存在");
                ControllerUtil.printText(response, "fail");
                return;
            }
            Integer queryPushStatus = queryCapitalWithhold.getPushStatus();
            if (queryPushStatus != null && queryPushStatus == 2) {
                logger.info(outerTradeNo + ",重复回调");
                ControllerUtil.printText(response, "success");
                Long orderId = queryCapitalWithhold.getOrderId();
                RedisUtils.hdel("pay_process", orderId.toString());
                RedisUtils.hdel("pay_process:11", orderId.toString());
                return;
            }

            String tradeStatus = request.getParameter("trade_status");// TRADE_SUCCESS 交易成功，用户付款成功,TRADE_FINISHED 交易结束，付款金额已结算给商户,  TRADE_CLOSED 交易关闭，交易失败
            String tradeAmount = request.getParameter("trade_amount");//交易金额
            Double orderAmount = NumberUtil.parseDouble(tradeAmount, 0.0);// 支付金额
            Date nowDate = new Date();
            queryCapitalWithhold.setOtherOrderNo(outerTradeNo);
            Integer payType = queryCapitalWithhold.getPayType();
            if ("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus)) {
                queryCapitalWithhold.setCode("SUCCESS");
            } else if ("TRADE_FINISHED".equalsIgnoreCase(tradeStatus)) {
                queryCapitalWithhold.setCode("FINISHED");
            }
            Map<String, String> notifyThirdData = null;
            Long orderId = queryCapitalWithhold.getOrderId();
            BwOrder bwOrder = bwOrderService.findBwOrderById(orderId.toString());
            if ("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus) || "TRADE_CLOSED".equalsIgnoreCase(tradeStatus)) {
                int payStatus = 0;
                if ("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus)) {
                    failReason = "交易成功";
                    payStatus = 2;
                } else {
                    payStatus = 3;
                }
                notifyThirdData = bwRepaymentPlanService.getNotifyThirdData(bwOrder, orderAmount, payStatus, failReason);
            }

            // 成功和完成都是成功，分两次通知，几乎同时执行
            if ("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus) || "TRADE_FINISHED".equalsIgnoreCase(tradeStatus)) {// 成功，完成
                queryCapitalWithhold.setPushStatus(2);// 扣款成功
                RepaymentDto repaymentDto = new RepaymentDto();
                repaymentDto.setUseCoupon(false);
                repaymentDto.setOrderId(orderId);
                repaymentDto.setType(payType);// 还款类型（1、还款，2、展期）
                repaymentDto.setTradeNo(outerTradeNo);
                if (SystemConstant.WITHHOLD_TEST_BOOL) {
                    repaymentDto.setAmount(queryCapitalWithhold.getMoney().doubleValue());
                } else {
                    repaymentDto.setAmount(orderAmount);
                }
                repaymentDto.setPayChannel(11);
                repaymentDto.setTradeTime(new Date());
                repaymentDto.setTradeType(1);// 交易类型 1:划拨 2:转账
                repaymentDto.setPayWay(3);
                repaymentDto.setTerminalType(queryCapitalWithhold.getTerminalType());
                repaymentDto.setTradeCode(tradeStatus);

                // 重复回调
                if (StringUtils.isNotEmpty(outerTradeNo)) {
                    BwPlatformRecord entity = new BwPlatformRecord();
                    entity.setOrderId(orderId);
                    entity.setTradeNo(outerTradeNo);
                    // 记录流水
                    int count = bwPlatformRecordService.getBwPlatformRecordCount(entity);
                    if (count > 0) {
                        ControllerUtil.printText(response, "success");
                        return;
                    }
                }

                // 支付成功
                AppResponseResult appResponseResult = bwRepaymentService.updateOrderByTradeMoney(repaymentDto);
                if ("000".equals(appResponseResult.getCode())) {
                    logger.info("【KuaijietongPayController.withholdNotify2】快捷通支付成功orderId：" + orderId + JSON.toJSONString(appResponseResult));
                } else {
                    logger.info("【KuaijietongPayController.withholdNotify2】快捷通支付失败orderId：" + orderId + JSON.toJSONString(appResponseResult));
                }
                RedisUtils.hdel("pay_info", orderId.toString());

                Integer terminalType = queryCapitalWithhold.getTerminalType();
                // 自动代扣成功，处理下一个还款计划
                if (terminalType != null && terminalType == 5) {
                    // bwRepaymentPlanService.saveAutoPlan2Redis(orderId);
                }
            } else if ("TRADE_CLOSED".equalsIgnoreCase(tradeStatus)) {// 失败
                queryCapitalWithhold.setPushStatus(3);
                RedisUtils.hdel("pay_info", orderId.toString());
                if (StringUtils.isNotEmpty(failReason)) {
                    queryCapitalWithhold.setMsg(failReason);
                }
                queryCapitalWithhold.setCode("CLOSED");
            }

            if ("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus) || "TRADE_CLOSED".equalsIgnoreCase(tradeStatus)) {
                bwRepaymentPlanService.notifyThird(notifyThirdData);// 通知三方
            }

            queryCapitalWithhold.setMoneyWithhold(new BigDecimal(orderAmount));
            queryCapitalWithhold.setUpdateTime(nowDate);
            bwCapitalWithholdService.updateBwCapitalWithhold(queryCapitalWithhold);
            RedisUtils.hdel("pay_process", orderId.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(outerTradeNo + ",快捷通回调异常", e);
            ControllerUtil.printText(response, "fail");
            return;
        }
        ControllerUtil.printText(response, "success");
    }


    private boolean checkSign(Map<String, String> map) {
        String privateKey = KJTConstant.WEPRIVATEKEY;//
        String kjtPublicKey = KJTConstant.KJTPUBLICKEY;
        SecurityService securityService = new SecurityService(privateKey, kjtPublicKey);
        //获得sign,charset
        String sign = map.get("sign");
        String charset = map.get("_input_charset");

        VerifyResult result = securityService.verify(map, sign, charset);
        if (result != null) {
            if (result.isSuccess()) {
                logger.info("验签成功");
                return true;
            } else {
                logger.info("验签失败");
                return false;
            }

        } else {
            logger.info("验签异常");
            return false;
        }
    }


}
