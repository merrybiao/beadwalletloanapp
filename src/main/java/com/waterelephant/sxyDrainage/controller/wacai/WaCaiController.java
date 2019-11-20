//package com.waterelephant.sxyDrainage.controller.wacai;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.sxyDrainage.entity.wacai.WaCaiCommonReq;
//import com.waterelephant.sxyDrainage.entity.wacai.WaCaiResponse;
//import com.waterelephant.sxyDrainage.service.WaCaiService;
//import com.waterelephant.sxyDrainage.utils.wacaiutils.WaCaiUtil;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/30
// * @since JDK 1.8
// */
//@RestController
//@RequestMapping("sxyDrainage/wacai")
//public class WaCaiController {
//
//    private static Logger logger = Logger.getLogger(WaCaiController.class);
//
//    @Autowired
//    private WaCaiService waCaiService;
//
//
//    /**
//     * 统一异常处理
//     *
//     * @param e 发生的异常
//     * @return 响应
//     */
//    @ExceptionHandler(value = {Exception.class})
//    public WaCaiResponse exception(Exception e) {
//        logger.error("挖财>>>异常结束，统一异常处理", e);
//        return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "处理异常");
//    }
//
//    /**
//     * 过滤接口
//     *
//     * @param request 请求
//     * @return WaCaiResponse
//     */
//    @RequestMapping("/blackuser.do")
//    public WaCaiResponse checkBlack(HttpServletRequest request) {
//        // 生成时间戳标记
//        Long sessionId = System.currentTimeMillis();
//
//        // 数据过滤
//        WaCaiResponse waCaiResponse = WaCaiUtil.filter(request, sessionId, "checkBlack()用户校验接口");
//        if (waCaiResponse.getCode() != null) {
//            logger.info("挖财>>>" + sessionId + " 结束checkBlack()用户校验接口：" + waCaiResponse.getMessage());
//            return waCaiResponse;
//        }
//
//        // 业务逻辑处理
//        WaCaiCommonReq waCaiCommonReq = JSON.parseObject(waCaiResponse.getMessage(), WaCaiCommonReq.class);
//        waCaiResponse.setMessage("");
//        waCaiResponse = waCaiService.checkUser(sessionId, waCaiCommonReq);
//
//        logger.info("挖财>>>" + sessionId + " 结束WaCaiController checkBlack()用户校验接口,数据为：" + JSON.toJSONString(waCaiResponse));
//        return waCaiResponse;
//    }
//
//
//    /**
//     * 进件
//     *
//     * @param request 请求
//     * @return WaCaiResponse
//     */
//    @RequestMapping("/saveOrder.do")
//    public WaCaiResponse saveOrder(HttpServletRequest request) throws Exception {
//        // 生成时间戳标记
//        Long sessionId = System.currentTimeMillis();
//
//        // 数据过滤
//        WaCaiResponse waCaiResponse = WaCaiUtil.filter(request, sessionId, "saveOrder()进件接口");
//        if (waCaiResponse.getCode() != null) {
//            logger.info("挖财>>>" + sessionId + " 结束saveOrder()进件接口：" + waCaiResponse.getMessage());
//            return waCaiResponse;
//        }
//
//        // 业务逻辑处理
//        WaCaiCommonReq waCaiCommonReq = JSON.parseObject(waCaiResponse.getMessage(), WaCaiCommonReq.class);
//        waCaiResponse.setMessage("");
//        waCaiResponse = waCaiService.saveOrder(sessionId, waCaiCommonReq);
//
//        logger.info("挖财>>>" + sessionId + " 结束WaCaiController saveOrder()进件接口,数据为：" + JSON.toJSONString(waCaiResponse));
//        return waCaiResponse;
//    }
//
//    /**
//     * 预绑卡接口
//     *
//     * @param request 请求
//     * @return WaCaiResponse
//     */
//    @RequestMapping("/saveBankCard.do")
//    public WaCaiResponse saveBankCard(HttpServletRequest request) {
//        // 生成时间戳标记
//        Long sessionId = System.currentTimeMillis();
//
//        // 数据过滤
//        WaCaiResponse waCaiResponse = WaCaiUtil.filter(request, sessionId, "saveBankCard()预绑卡接口");
//        if (waCaiResponse.getCode() != null) {
//            logger.info("挖财>>>" + sessionId + " 结束saveBankCard()预绑卡接口：" + waCaiResponse.getMessage());
//            return waCaiResponse;
//        }
//
//        // 业务逻辑处理
//        WaCaiCommonReq waCaiCommonReq = JSON.parseObject(waCaiResponse.getMessage(), WaCaiCommonReq.class);
//        waCaiResponse.setMessage("");
//        waCaiResponse = waCaiService.saveBankCard(sessionId, waCaiCommonReq);
//
//        logger.info("挖财>>>" + sessionId + " 结束WaCaiController saveBankCard()预绑卡接口,数据为：" + JSON.toJSONString(waCaiResponse));
//        return waCaiResponse;
//    }
//
//
//    /**
//     * 验证码绑卡接口
//     *
//     * @param request 请求
//     * @return WaCaiResponse
//     */
//    @RequestMapping("/saveBankCardCode.do")
//    public WaCaiResponse saveBankCardCode(HttpServletRequest request) {
//        // 生成时间戳标记
//        Long sessionId = System.currentTimeMillis();
//
//        // 数据过滤
//        WaCaiResponse waCaiResponse = WaCaiUtil.filter(request, sessionId, "saveBankCardCode()验证码绑卡接口");
//        if (waCaiResponse.getCode() != null) {
//            logger.info("挖财>>>" + sessionId + " 结束saveBankCardCode()验证码绑卡接口：" + waCaiResponse.getMessage());
//            return waCaiResponse;
//        }
//
//        // 业务逻辑处理
//        WaCaiCommonReq waCaiCommonReq = JSON.parseObject(waCaiResponse.getMessage(), WaCaiCommonReq.class);
//        waCaiResponse.setMessage("");
//        waCaiResponse = waCaiService.saveBankCardCode(sessionId, waCaiCommonReq);
//
//        logger.info("挖财>>>" + sessionId + " 结束WaCaiController saveBankCardCode()验证码绑卡接口,数据为：" + JSON.toJSONString(waCaiResponse));
//        return waCaiResponse;
//    }
//
//    /**
//     * 挖财签约接口
//     *
//     * @param sessionId 时间戳
//     * @param waCaiCommonReq 请求参数
//     * @return 响应
//     */
//    @RequestMapping("/saveSign.do")
//    public WaCaiResponse saveSign(HttpServletRequest request) {
//        // 生成时间戳标记
//        Long sessionId = System.currentTimeMillis();
//
//        // 数据过滤
//        WaCaiResponse waCaiResponse = WaCaiUtil.filter(request, sessionId, "saveSign()挖财签约接口");
//        if (waCaiResponse.getCode() != null) {
//            logger.info("挖财>>>" + sessionId + " 结束saveSign()挖财签约接口：" + waCaiResponse.getMessage());
//            return waCaiResponse;
//        }
//
//        // 业务逻辑处理
//        WaCaiCommonReq waCaiCommonReq = JSON.parseObject(waCaiResponse.getMessage(), WaCaiCommonReq.class);
//        waCaiResponse.setMessage("");
//        waCaiResponse = waCaiService.saveSign(sessionId, waCaiCommonReq);
//
//        logger.info("挖财>>>" + sessionId + " 结束WaCaiController saveSign()挖财签约接口,数据为：" + JSON.toJSONString(waCaiResponse));
//        return waCaiResponse;
//    }
//
//
//    /**
//     * 合同接口
//     *
//     * @param request 请求
//     * @return WaCaiResponse
//     */
//    @RequestMapping("/getContract.do")
//    public WaCaiResponse getContract(HttpServletRequest request) {
//        // 生成时间戳标记
//        Long sessionId = System.currentTimeMillis();
//
//        // 数据过滤
//        WaCaiResponse waCaiResponse = WaCaiUtil.filter(request, sessionId, "getContract()合同接口");
//        if (waCaiResponse.getCode() != null) {
//            logger.info("挖财>>>" + sessionId + " 结束getContract()合同接口：" + waCaiResponse.getMessage());
//            return waCaiResponse;
//        }
//
//        // 业务逻辑处理
//        WaCaiCommonReq waCaiCommonReq = JSON.parseObject(waCaiResponse.getMessage(), WaCaiCommonReq.class);
//        waCaiResponse.setMessage("");
//        waCaiResponse = waCaiService.getContract(sessionId, waCaiCommonReq);
//
//        logger.info("挖财>>>" + sessionId + " 结束WaCaiController getContract()合同接口,数据为：" + JSON.toJSONString(waCaiResponse));
//        return waCaiResponse;
//
//    }
//
//    /**
//     * 授信接口查询
//     *
//     * @param request 请求
//     * @return WaCaiResponse
//     */
//    @RequestMapping("/getApprovalResult.do")
//    public WaCaiResponse getApprovalResult(HttpServletRequest request) {
//        // 生成时间戳标记
//        Long sessionId = System.currentTimeMillis();
//
//        // 数据过滤
//        WaCaiResponse waCaiResponse = WaCaiUtil.filter(request, sessionId, "getContract()合同接口");
//        if (waCaiResponse.getCode() != null) {
//            logger.info("挖财>>>" + sessionId + " 结束getContract()合同接口：" + waCaiResponse.getMessage());
//            return waCaiResponse;
//        }
//
//        // 业务逻辑处理
//        WaCaiCommonReq waCaiCommonReq = JSON.parseObject(waCaiResponse.getMessage(), WaCaiCommonReq.class);
//        waCaiResponse.setMessage("");
//        waCaiResponse = waCaiService.getApprovalResult(sessionId, waCaiCommonReq);
//
//        logger.info("挖财>>>" + sessionId + " 结束WaCaiController getContract()合同接口,数据为：" + JSON.toJSONString(waCaiResponse));
//        return waCaiResponse;
//    }
//
//    @RequestMapping("/getOrder.do")
//    public WaCaiResponse getOrder(HttpServletRequest request) {
//        // 生成时间戳标记
//        Long sessionId = System.currentTimeMillis();
//
//        // 数据过滤
//        WaCaiResponse waCaiResponse = WaCaiUtil.filter(request, sessionId, "getOrder()订单状态拉取接口");
//        if (waCaiResponse.getCode() != null) {
//            logger.info("挖财>>>" + sessionId + " 结束getOrder()订单状态拉取接口：" + waCaiResponse.getMessage());
//            return waCaiResponse;
//        }
//
//        // 业务逻辑处理
//        WaCaiCommonReq waCaiCommonReq = JSON.parseObject(waCaiResponse.getMessage(), WaCaiCommonReq.class);
//        waCaiResponse.setMessage("");
//        waCaiResponse = waCaiService.getApprovalResult(sessionId, waCaiCommonReq);
//
//        logger.info("挖财>>>" + sessionId + " 结束WaCaiController getOrder()订单状态拉取接口,数据为：" + JSON.toJSONString(waCaiResponse));
//        return waCaiResponse;
//    }
//
//    /**
//     * 主动还款接口
//     *
//     * @param request 请求
//     * @return WaCaiResponse
//     */
//    @RequestMapping("/updateRepayment.do")
//    public WaCaiResponse updateRepayment(HttpServletRequest request) {
//        // 生成时间戳标记
//        Long sessionId = System.currentTimeMillis();
//
//        // 数据过滤
//        WaCaiResponse waCaiResponse = WaCaiUtil.filter(request, sessionId, "updateRepayment()主动还款接口");
//        if (waCaiResponse.getCode() != null) {
//            logger.info("挖财>>>" + sessionId + " 结束updateRepayment()主动还款接口：" + waCaiResponse.getMessage());
//            return waCaiResponse;
//        }
//
//        // 业务逻辑处理
//        WaCaiCommonReq waCaiCommonReq = JSON.parseObject(waCaiResponse.getMessage(), WaCaiCommonReq.class);
//        waCaiResponse.setMessage("");
//        waCaiResponse = waCaiService.getApprovalResult(sessionId, waCaiCommonReq);
//
//        logger.info("挖财>>>" + sessionId + " 结束WaCaiController updateRepayment()主动还款接口,数据为：" + JSON.toJSONString(waCaiResponse));
//        return waCaiResponse;
//    }
//}
