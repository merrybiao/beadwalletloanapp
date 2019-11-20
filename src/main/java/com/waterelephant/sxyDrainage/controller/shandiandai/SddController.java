//package com.waterelephant.sxyDrainage.controller.shandiandai;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.sxyDrainage.entity.shandiandai.*;
//import com.waterelephant.sxyDrainage.service.SddService;
//import com.waterelephant.sxyDrainage.utils.interfaceLog.SxyThirdInterfaceLogUtils;
//import com.waterelephant.sxyDrainage.utils.shandiandai.SddConstant;
//import com.waterelephant.sxyDrainage.utils.shandiandai.SddUtil;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/5
// * @since JDK 1.8
// */
//@RestController
//@RequestMapping(value = "/sxyDrainage/sdd", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
//public class SddController {
//
//    private static Logger logger = Logger.getLogger(SddController.class);
//    /** 水象渠道号 */
//    private final static String CHANNEL_SX = SddConstant.CHANNEL_SX;
//
//    @Autowired
//    private SddService sddService;
//
//    /**
//     * 异常处理
//     *
//     * @param e 发生的异常
//     * @return 响应
//     */
//    @ExceptionHandler(value = {Exception.class})
//    public SddResponse exception(Exception e) {
//        logger.error("闪电贷>>>异常结束，统一异常处理", e);
//        // 三方接口日志
//        SxyThirdInterfaceLogUtils.setSxyLog(CHANNEL_SX, e.getMessage(), SddResponse.SERVER_EXCEPTION_CODE + "", "关键字：异常名");
//        return new SddResponse(SddResponse.SERVER_EXCEPTION_CODE, "处理异常", "{}");
//    }
//
//    /**
//     * 撞库
//     *
//     * @param sddRequest 请求
//     * @return SddResponse
//     */
//    @RequestMapping("/filter.do")
//    public SddResponse checkUser(@RequestBody SddRequest sddRequest) {
//        Long sessionId = System.currentTimeMillis();
//        // 校验数据
//        SddResponse sddResponse = SddUtil.checkFilter(sddRequest,"checkUser()撞库接口");
//        if (sddResponse != null) {
//            logger.info("闪电贷>>>结束checkUser()撞库：" + sddResponse.getMsg());
//            return sddResponse;
//        }
//
//        // json序列化
//        SddFilterReq sddFilterReq = JSON.parseObject(sddRequest.getParam(), SddFilterReq.class);
//
//        // 处理业务逻辑
//        sddResponse = sddService.checkUser(sddFilterReq, sessionId);
//
//        logger.info("闪电贷>>>结束SddController checkUser()撞库接口,数据为：" + JSON.toJSONString(sddResponse));
//        return sddResponse;
//    }
//
//
//    /**
//     * 预绑卡
//     *
//     * @param sddRequest 请求
//     * @return SddResponse
//     */
//    @RequestMapping("/bindcard.do")
//    public SddResponse saveBankCard(@RequestBody SddRequest sddRequest) {
//        Long sessionId = System.currentTimeMillis();
//        // 校验数据
//        SddResponse sddResponse = SddUtil.checkFilter(sddRequest,"saveBankCard()预绑卡接口");
//        if (sddResponse != null) {
//            logger.info("闪电贷>>>结束saveBankCard()预绑卡：" + sddResponse.getMsg());
//            return sddResponse;
//        }
//
//        // json序列化
//        SddBindCardReq sddBindCardReq = JSON.parseObject(sddRequest.getParam(), SddBindCardReq.class);
//
//        // 处理业务逻辑
//        sddResponse = sddService.saveBankCard(sddBindCardReq, sessionId);
//
//        // 三方接口日志
//        SxyThirdInterfaceLogUtils.setSxyLog(CHANNEL_SX, sddBindCardReq.getIdCard(), sddResponse.getCode() + "", "关键字：身份证");
//
//        logger.info("闪电贷>>>结束SddController saveBankCard()预绑卡接口,数据为：" + JSON.toJSONString(sddResponse));
//        return sddResponse;
//    }
//
//    /**
//     * 验证码绑卡
//     *
//     * @param sddRequest 请求
//     * @return SddResponse
//     */
//    @RequestMapping("/bindcardVerif.do")
//    public SddResponse saveBankCardCode(@RequestBody SddRequest sddRequest) {
//        Long sessionId = System.currentTimeMillis();
//        // 校验数据
//        SddResponse sddResponse = SddUtil.checkFilter(sddRequest,"saveBankCardCode()验证码绑卡接口");
//        if (sddResponse != null) {
//            logger.info("闪电贷>>>结束checkUser()验证码绑卡：" + sddResponse.getMsg());
//            return sddResponse;
//        }
//
//        // json序列化
//        SddBindcardVerifReq sddBindcardVerifReq = JSON.parseObject(sddRequest.getParam(), SddBindcardVerifReq.class);
//
//        // 处理业务逻辑
//        sddResponse = sddService.saveBankCardCode(sddBindcardVerifReq, sessionId);
//
//        // 三方接口日志
//        SxyThirdInterfaceLogUtils.setSxyLog(CHANNEL_SX, sddBindcardVerifReq.getIdCard(), sddResponse.getCode() + "", "关键字：身份证");
//
//        logger.info("闪电贷>>>结束SddController saveBankCardCode()验证码绑卡接口,数据为：" + JSON.toJSONString(sddResponse));
//        return sddResponse;
//    }
//
//    /**
//     * 进件
//     *
//     * @param sddRequest 请求
//     * @return SddResponse
//     */
//    @RequestMapping("/push-order.do")
//    public SddResponse saveOrder(@RequestBody SddRequest sddRequest) {
//        Long sessionId = System.currentTimeMillis();
//        // 校验数据
//        SddResponse sddResponse = SddUtil.checkFilter(sddRequest,"saveOrder()进件接口"); // todo 注意日志
//        if (sddResponse != null) {
//            logger.info("闪电贷>>>结束saveOrder()进件：" + sddResponse.getMsg());
//            return sddResponse;
//        }
//
//        // json序列化
//        SddPushOrderReq sddPushOrderReq = JSON.parseObject(sddRequest.getParam(), SddPushOrderReq.class);
//
//        // 处理业务逻辑
//        sddResponse = sddService.saveOrder(sddPushOrderReq, sessionId);
//
//        // 三方接口日志
//        SxyThirdInterfaceLogUtils.setSxyLog(CHANNEL_SX, sddPushOrderReq.getLoanId(), sddResponse.getCode() + "", "关键字：三方工单号");
//
//        logger.info("闪电贷>>>结束SddController saveOrder()进件接口,数据为：" + JSON.toJSONString(sddResponse));
//        return sddResponse;
//    }
//
//    /**
//     * 借款合同
//     *
//     * @param sddRequest 请求
//     * @return SddResponse
//     */
//    @RequestMapping("/contract.do")
//    public SddResponse getContract(@RequestBody SddRequest sddRequest) {
//        Long sessionId = System.currentTimeMillis();
//        // 校验数据
//        SddResponse sddResponse = SddUtil.checkFilter(sddRequest,"getContract()借款合同接口");
//        if (sddResponse != null) {
//            logger.info("闪电贷>>>结束getContract()借款合同：" + sddResponse.getMsg());
//            return sddResponse;
//        }
//
//        // json序列化
//        SddCommonReq sddCommonReq = JSON.parseObject(sddRequest.getParam(), SddCommonReq.class);
//
//        // 处理业务逻辑
//        sddResponse = sddService.getContract(sddCommonReq, sessionId);
//
//        logger.info("闪电贷>>>结束SddController getContract()借款合同接口,数据为：" + JSON.toJSONString(sddResponse));
//        return sddResponse;
//    }
//
//
//    /**
//     * 订单信息查询
//     *
//     * @param sddRequest 请求
//     * @return SddResponse
//     */
//    @RequestMapping("/repay-detail.do")
//    public SddResponse getOrderInfo(@RequestBody SddRequest sddRequest) {
//        Long sessionId = System.currentTimeMillis();
//        // 校验数据
//        SddResponse sddResponse = SddUtil.checkFilter(sddRequest,"getOrderInfo()订单信息查询接口");
//        if (sddResponse != null) {
//            logger.info("闪电贷>>>结束getOrderInfo()订单信息查询：" + sddResponse.getMsg());
//            return sddResponse;
//        }
//
//        // json序列化
//        SddCommonReq sddCommonReq = JSON.parseObject(sddRequest.getParam(), SddCommonReq.class);
//
//        // 处理业务逻辑
//        sddResponse = sddService.getOrderInfo(sddCommonReq, sessionId);
//
//        logger.info("闪电贷>>>结束SddController getOrderInfo()订单信息查询接口,数据为：" + JSON.toJSONString(sddResponse));
//        return sddResponse;
//    }
//
//
//    /**
//     * 试算
//     *
//     * @param sddRequest 请求
//     * @return SddResponse
//     */
//    @RequestMapping("/loan-calculate.do")
//    public SddResponse trial(@RequestBody SddRequest sddRequest) {
//        Long sessionId = System.currentTimeMillis();
//        // 校验数据
//        SddResponse sddResponse = SddUtil.checkFilter(sddRequest,"trial()试算接口");
//        if (sddResponse != null) {
//            logger.info("闪电贷>>>结束trial()试算：" + sddResponse.getMsg());
//            return sddResponse;
//        }
//
//        // json序列化
//        SddLoanCalculateReq sddLoanCalculateReq = JSON.parseObject(sddRequest.getParam(), SddLoanCalculateReq.class);
//
//        // 处理业务逻辑
//        sddResponse = sddService.trial(sddLoanCalculateReq, sessionId);
//
//        logger.info("闪电贷>>>结束SddController trial()试算接口,数据为：" + JSON.toJSONString(sddResponse));
//        return sddResponse;
//    }
//
//
//    /**
//     * 主动还款
//     *
//     * @param sddRequest 请求
//     * @return SddResponse
//     */
//    @RequestMapping("/repay.do")
//    public SddResponse repayment(@RequestBody SddRequest sddRequest) {
//        Long sessionId = System.currentTimeMillis();
//        // 校验数据
//        SddResponse sddResponse = SddUtil.checkFilter(sddRequest,"repayment()主动还款接口");
//        if (sddResponse != null) {
//            logger.info("闪电贷>>>结束repayment()主动还款：" + sddResponse.getMsg());
//            return sddResponse;
//        }
//
//        // json序列化
//        SddCommonReq sddCommonReq = JSON.parseObject(sddRequest.getParam(), SddCommonReq.class);
//
//        // 处理业务逻辑
//        sddResponse = sddService.updateRepayment(sddCommonReq, sessionId);
//
//        // 三方接口日志
//        SxyThirdInterfaceLogUtils.setSxyLog(CHANNEL_SX, sddCommonReq.getLoanId(), sddResponse.getCode() + "", "关键字：三方工单号", sddResponse.getMsg());
//
//        logger.info("闪电贷>>>结束SddController repayment()主动还款接口,数据为：" + JSON.toJSONString(sddResponse));
//        return sddResponse;
//    }
//
//}
