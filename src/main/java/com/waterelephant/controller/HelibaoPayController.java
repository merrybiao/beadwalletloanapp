package com.waterelephant.controller;

import com.alibaba.fastjson.JSON;
import com.waterelephant.constants.HelibaoConstant;
import com.waterelephant.constants.RedisKeyConstant;
import com.waterelephant.dto.RepaymentDto;
import com.waterelephant.entity.BwCapitalWithhold;
import com.waterelephant.service.BwCapitalWithholdService;
import com.waterelephant.service.IBwRepaymentService;
import com.waterelephant.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * 三方支付
 */
@Controller
@RequestMapping("/helibaoPay")
public class HelibaoPayController {
    private Logger logger = Logger.getLogger(HelibaoPayController.class);
    @Autowired
    private IBwRepaymentService bwRepaymentService;
    @Autowired
    private BwCapitalWithholdService bwCapitalWithholdService;

    @ResponseBody
    @RequestMapping("/withholdNotify.do")
    public String withholdNotify(HttpServletRequest request) {
        String rt5_orderId = request.getParameter("rt5_orderId");// 商户订单号
        try {
            boolean checkSignBool = checkSign(request);
                if (!checkSignBool) {
                logger.info(request.getParameter("rt5_orderId") + "验签失败");
                return "fail";
            }
            logger.info(rt5_orderId + "验签通过");
            long withholdId = Long.parseLong(rt5_orderId.substring(rt5_orderId.lastIndexOf("X") + 1));
            BwCapitalWithhold queryCapitalWithhold = bwCapitalWithholdService.queryBwCapitalWithhold(withholdId);
            if (queryCapitalWithhold == null) {
                logger.info(rt5_orderId + ",BwCapitalWithhold不存在");
                return "fail";
            }
            Integer queryPushStatus = queryCapitalWithhold.getPushStatus();
            if (queryPushStatus != null && queryPushStatus == 2) {
                logger.info(rt5_orderId + ",重复回调");
                return "success";
            }
            String rt13_orderStatus = request.getParameter("rt13_orderStatus");// INIT:未支付 SUCCESS：成功 FAILED：失败 DOING：处理中
            String rt6_serialNumber = request.getParameter("rt6_serialNumber");// 合利宝流水号
            String rt3_retMsg = request.getParameter("rt3_retMsg");// 返回信息
            Double orderAmount = NumberUtil.parseDouble(request.getParameter("rt7_orderAmount"), 0.0);// 支付金额
            Date nowDate = new Date();
            if (StringUtils.isNotEmpty(rt6_serialNumber)) {
                queryCapitalWithhold.setOtherOrderNo(rt6_serialNumber);
            }
            queryCapitalWithhold.setCode(rt13_orderStatus);
            Long orderId = queryCapitalWithhold.getOrderId();
            if ("SUCCESS".equalsIgnoreCase(rt13_orderStatus)) {// 成功、处理中、未支付
                queryCapitalWithhold.setPushStatus(2);// 扣款成功
                RepaymentDto repaymentDto = new RepaymentDto();
                repaymentDto.setUseCoupon(false);
                repaymentDto.setOrderId(orderId);
                repaymentDto.setType(1);
                repaymentDto.setTradeNo(rt6_serialNumber);
                if (SystemConstant.WITHHOLD_TEST_BOOL) {
                    repaymentDto.setAmount(queryCapitalWithhold.getMoney().doubleValue());
                } else {
                    repaymentDto.setAmount(orderAmount);
                }
                repaymentDto.setPayChannel(10);
                repaymentDto.setTradeTime(new Date());
                repaymentDto.setTradeType(1);
                repaymentDto.setTerminalType(queryCapitalWithhold.getTerminalType());
                repaymentDto.setTradeCode(rt13_orderStatus);

                // 支付成功
                AppResponseResult appResponseResult = bwRepaymentService.updateOrderByTradeMoney(repaymentDto);
                if ("000".equals(appResponseResult.getCode())) {
                    logger.info("合利宝支付成功orderId：" + orderId + JSON.toJSONString(appResponseResult));
                } else {
                    logger.info("合利宝支付失败orderId：" + orderId + JSON.toJSONString(appResponseResult));
                }
                RedisUtils.hdel("pay_info", orderId.toString());
            } else if ("DOING".equalsIgnoreCase(rt13_orderStatus) || "INIT".equalsIgnoreCase(rt13_orderStatus)) {// 处理中
                queryCapitalWithhold.setPushStatus(1);// 扣款中
            } else if ("FAILED".equalsIgnoreCase(rt13_orderStatus)) {// 失败
                queryCapitalWithhold.setPushStatus(3);
                RedisUtils.hdel("pay_info", orderId.toString());
            }
            queryCapitalWithhold.setMsg(rt3_retMsg);
            queryCapitalWithhold.setMoneyWithhold(new BigDecimal(orderAmount));
            queryCapitalWithhold.setUpdateTime(nowDate);
            bwCapitalWithholdService.updateBwCapitalWithhold(queryCapitalWithhold);
            RedisUtils.hdel("helibao_process", orderId.toString());
            RedisUtils.hdel("pay_process:10", orderId.toString());
        } catch (Exception e) {
            logger.error(rt5_orderId + ",合利宝回调异常", e);
            return "fail";
        }
        return "success";
    }

    private boolean checkSign(HttpServletRequest request) {
        String signOrig = getSignOrig(request, HelibaoConstant.SIGNKEY_MD5);
        logger.info("签名原串:" + signOrig);
        String sign = request.getParameter("sign");
        String rt14_reason = request.getParameter("rt14_reason");
        logger.info("合利宝回调参数:" + signOrig + ",sign=" + sign + ",rt14_reason=" + rt14_reason);
        String sign2 = null;
        try {
            sign2 = MD5Util.md5(signOrig);
        } catch (Exception e) {
        }
        boolean bool = false;
        if (sign2 != null && sign2.equalsIgnoreCase(sign)) {
            bool = true;
        }
        return bool;
    }

    /**
     * 获取合利宝签名原串
     *
     * @param request
     * @param md5Key
     * @return
     */
    private String getSignOrig(HttpServletRequest request, String md5Key) {
        StringBuilder signOrigSB = new StringBuilder();
        Enumeration<String> parameterNames = request.getParameterNames();
        TreeMap<String, String> signMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String key1, String key2) {
                if (StringUtil.isEmpty(key1)) {
                    return 1;
                }
                if (StringUtil.isEmpty(key2)) {
                    return -1;
                }
                int keyNum1 = NumberUtils.toInt(key1.substring(key1.indexOf("rt") + "rt".length(), key1.indexOf("_")), 0);
                int keyNum2 = NumberUtils.toInt(key2.substring(key2.indexOf("rt") + "rt".length(), key2.indexOf("_")), 0);
                return keyNum1 - keyNum2;
            }
        });
        while(parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);
            if (name.contains("rt") && name.contains("_")) {
                signMap.put(name, value);
            }
        }
        signMap.remove("rt14_reason");
        for (Map.Entry<String, String> entry : signMap.entrySet()) {
            String value = entry.getValue();
            signOrigSB.append("&");
            if (StringUtils.isNotEmpty(value)) {
                signOrigSB.append(value);
            }
        }
        signOrigSB.append("&");
        signOrigSB.append(md5Key);
        return signOrigSB.toString();
    }
}