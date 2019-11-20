//package com.waterelephant.sxyDrainage.aspect;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.entity.BwThirdInterfaceLog;
//import com.waterelephant.service.BwThirdInterfaceLogService;
//import com.waterelephant.sxyDrainage.entity.geinihua.BasicInfo;
//import com.waterelephant.sxyDrainage.entity.geinihua.BindingCard;
//import com.waterelephant.sxyDrainage.entity.geinihua.ResponseInfo;
//import com.waterelephant.sxyDrainage.entity.geinihua.Supplement;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqBindCardReq;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqOrderInfoRequest;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqResponse;
//import com.waterelephant.utils.CommUtils;
//
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.Date;
//
///**
// * @author xanthuim@gmail.com
// * @since 2018-07-24
// * <p>
// * 请求AOP类，用来记录各个调用日志
// */
//@Aspect
//@Component
//public class LogAspect {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);
//
//    /**
//     * 借点钱
//     */
//    private static final String JDQ_CONTROLLER = "JdqController";
//    private static final String JDQ_VALIDATED = JDQ_CONTROLLER + ".validated";
//    private static final String JDQ_SAVE = JDQ_CONTROLLER + ".saveOrder";
//    private static final String JDQ_EXCEPTION = JDQ_CONTROLLER + ".exception";
//    private static final String JDQ_ORDER_STATUS = JDQ_CONTROLLER + ".queryOrderStatus";
//    private static final String JDQ_BIND_CARD_PRE = JDQ_CONTROLLER + ".bindCardPreNew";
//    private static final String JDQ_BIND_CARD_WITH_CODE = JDQ_CONTROLLER + ".bindCardWithCodeNew";
//
//    /**
//     * 给你花
//     */
//    private static final String GNH_CONTROLLER = "GeinihuaController";
//    private static final String GNH_VALIDATED = GNH_CONTROLLER + ".validated";
//    private static final String GNH_BASIC = GNH_CONTROLLER + ".basic";
//    private static final String GNH_SUPPLEMENT = GNH_CONTROLLER + ".supplement";
//    private static final String GNH_EXCEPTION = GNH_CONTROLLER + ".exception";
//    private static final String GNH_BIND_CARD = GNH_CONTROLLER + ".bindCard";
//    private static final String GNH_CONFIRM_BINDING = GNH_CONTROLLER + ".confirmBinding";
//
//    @Autowired
//    private BwThirdInterfaceLogService bwThirdInterfaceLogService;
//
//    @Value("${GNH_CHANNEL_ID}")
//    private Integer GNH_CHANNEL_ID;
//    @Value("${channel_id}")
//    private Integer JDQ_CHANNEL_ID;
//
//    /**
//     * 给你花
//     *
//     * @param pjp
//     * @return
//     * @throws Throwable
//     */
//    @Around("execution(public * com.waterelephant.sxyDrainage.controller.GeinihuaController.*(..)) " +
//            "&& !execution(public * com.waterelephant.sxyDrainage.controller.GeinihuaController.validated(..)) ")
//    public Object aroundGnhController(ProceedingJoinPoint pjp) throws Throwable {
//        String signature = pjp.getSignature().toString();
//        String shortSignature = pjp.getSignature().toShortString();
//
//        //当前时间
//        Instant start = Instant.now();
//        Date startDate = new Date();
//        LOGGER.info("-----【{}】开始访问", shortSignature);
//
//        BwThirdInterfaceLog bwThirdInterfaceLog = new BwThirdInterfaceLog();
//
//        Object[] args = pjp.getArgs();
//        if (args != null && args.length > 0) {
//            if (signature.contains(GNH_BASIC)) {
//                BasicInfo basicInfo = (BasicInfo) args[0];
//                bwThirdInterfaceLog.setIndexKey(basicInfo.getOrderInfo().getOrderNo());
//            } else if (signature.contains(GNH_SUPPLEMENT)) {
//                Supplement supplement = (Supplement) args[0];
//                bwThirdInterfaceLog.setIndexKey(supplement.getOrderInfo().getOrderNo());
//            } else if (signature.contains(GNH_BIND_CARD)
//                    || signature.contains(GNH_CONFIRM_BINDING)) {
//                BindingCard bindingCard = (BindingCard) args[0];
//                bwThirdInterfaceLog.setIndexKey(bindingCard.getOrderNo());
//            }
//
//            //进件数据太多，就不显示了
//            if (!signature.contains(GNH_BASIC)
//                    && !signature.contains(GNH_SUPPLEMENT)
//                    && !signature.contains(GNH_EXCEPTION)) {
//                LOGGER.info("-----【{}】访问参数：{}", shortSignature, JSON.toJSONString(args[0]));
//            }
//        }
//
//        Object proceed = pjp.proceed();
//        LOGGER.info("-----【{}】返回结果：{}", shortSignature, JSON.toJSONString(proceed));
//
//        long between = ChronoUnit.MILLIS.between(start, Instant.now());
//        LOGGER.info("-----【{}】结束访问：消耗时间：{}毫秒-----", shortSignature, between);
//
//        //记录日志到数据库
//        ResponseInfo responseInfo = (ResponseInfo) proceed;
//        bwThirdInterfaceLog.setResponseCode(responseInfo.getCode().toString());
//        bwThirdInterfaceLog.setRemark1(responseInfo.getMessage());
//        bwThirdInterfaceLog.setChannelId(GNH_CHANNEL_ID.toString());
//        if (!StringUtils.isEmpty(bwThirdInterfaceLog.getIndexKey())) {
//            saveLog(shortSignature, between, bwThirdInterfaceLog, startDate);
//        }
//        return proceed;
//    }
//
//
//    /**
//     * 借点钱
//     *
//     * @param pjp
//     * @return Throwable
//     */
//    @Around("execution(public * com.waterelephant.sxyDrainage.controller.jdq.JdqController.*(..)) " +
//            "&& !execution(public * com.waterelephant.sxyDrainage.controller.jdq.JdqController.validated(..)) " +
//            "&& !execution(public * com.waterelephant.sxyDrainage.controller.jdq.JdqController.queryOrderStatus(..)) ")
//    public Object aroundJdqController(ProceedingJoinPoint pjp) throws Throwable {
//        String signature = pjp.getSignature().toString();
//        String shortSignature = pjp.getSignature().toShortString();
//
//        Instant start = Instant.now();
//        Date startDate = new Date();
//        LOGGER.info("-----【{}】开始访问-----", shortSignature);
//
//        BwThirdInterfaceLog bwThirdInterfaceLog = new BwThirdInterfaceLog();
//
//        Object[] args = pjp.getArgs();
//        if (args != null && args.length > 0) {
//            JdqResponse jdqResponse = (JdqResponse) args[0];
//            if (signature.contains(JDQ_SAVE)) {
//                JdqOrderInfoRequest jdqOrderInfoRequest = JSON.parseObject(jdqResponse.getData().toString(),
//                        JdqOrderInfoRequest.class);
//                bwThirdInterfaceLog.setIndexKey(jdqOrderInfoRequest.getJdq_order_id());
//            } else if (signature.contains(JDQ_BIND_CARD_PRE)
//                    || signature.contains(JDQ_BIND_CARD_WITH_CODE)) {
//                JdqBindCardReq jdqBindCardReq = JSON.parseObject(jdqResponse.getData().toString(),
//                        JdqBindCardReq.class);
//                bwThirdInterfaceLog.setIndexKey(jdqBindCardReq.getMobile());
//            }
//
//            //进件、异常数据太多，就不显示了
//            if (!signature.contains(JDQ_SAVE)
//                    && !signature.contains(JDQ_EXCEPTION)) {
//                LOGGER.info("-----【{}】访问参数：{}", shortSignature, JSON.toJSONString(args[0]));
//            }
//        }
//
//        Object proceed = pjp.proceed();
//
//        LOGGER.info("-----【{}】返回结果：{}", shortSignature, JSON.toJSONString(proceed));
//
//        long between = ChronoUnit.MILLIS.between(start, Instant.now());
//        LOGGER.info("-----【{}】结束访问：消耗时间：{}毫秒-----", shortSignature, between);
//
//        //记录日志到数据库
//        JdqResponse jdqResponse = (JdqResponse) proceed;
//        bwThirdInterfaceLog.setResponseCode(jdqResponse.getCode().toString());
//        bwThirdInterfaceLog.setRemark1(jdqResponse.getDesc());
//        bwThirdInterfaceLog.setChannelId(JDQ_CHANNEL_ID.toString());
//        if (!StringUtils.isEmpty(jdqResponse.getKey())) {
//            saveLog(pjp.getSignature().toShortString(), between, bwThirdInterfaceLog, startDate);
//        }
//
//        return proceed;
//    }
//
//    /**
//     * 保存日志
//     *
//     * @param signature
//     * @param consumeTime
//     * @param bwThirdInterfaceLog
//     * @param startDate
//     */
//    private void saveLog(String signature, long consumeTime,
//                         BwThirdInterfaceLog bwThirdInterfaceLog,
//                         Date startDate) {
//        // 封装实体类,保存
//        bwThirdInterfaceLog.setConsumeTime(consumeTime);
//        bwThirdInterfaceLog.setRequestTime(startDate);
//        bwThirdInterfaceLog.setInterfaceType("1");
//        try {
//            String hostAddress = InetAddress.getLocalHost().getHostAddress();
//            bwThirdInterfaceLog.setRemark3("IP：" + getHostAddress(hostAddress));
//            bwThirdInterfaceLog.setInterfaceName(signature);
//            bwThirdInterfaceLogService.save(bwThirdInterfaceLog);
//        } catch (UnknownHostException e) {
//            LOGGER.error("未知的主机", e);
//        }
//    }
//
//    private String getHostAddress(String hostAddress) {
//        if (CommUtils.isNull(hostAddress)) {
//            return "为空";
//        }
//        if ("10.27.144.173".equals(hostAddress)) {
//            return "(外)106.14.45.32";
//        }
//        if ("10.31.21.188".equals(hostAddress)) {
//            return "(外)106.15.50.148";
//        }
//        if ("10.28.99.88".equals(hostAddress)) {
//            return "(外)106.14.237.235";
//        }
//        if ("10.25.35.1".equals(hostAddress)) {
//            return "(外)106.14.63.234";
//        }
//        if ("10.27.251.107".equals(hostAddress)) {
//            return "(外)106.14.62.233";
//        }
//        return "(内)" + hostAddress;
//    }
//}
