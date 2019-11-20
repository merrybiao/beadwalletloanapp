//package com.waterelephant.sxyDrainage.service;
//
//import com.waterelephant.sxyDrainage.entity.shandiandai.*;
//
///**
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/5
// * @since JDK 1.8
// */
//public interface SddService {
//    /**
//     * 撞库
//     *
//     * @param sddFilterReq 请求
//     * @param sessionId 时间戳标识
//     * @return SddResponse
//     */
//    SddResponse checkUser(SddFilterReq sddFilterReq, Long sessionId);
//
//    /**
//     * 预绑卡
//     *
//     * @param sddBindCardReq 请求
//     * @param sessionId 时间戳标识
//     * @return SddResponse
//     */
//    SddResponse saveBankCard(SddBindCardReq sddBindCardReq, Long sessionId);
//
//    /**
//     * 验证码绑卡
//     *
//     * @param sddBindCardReq 请求
//     * @param sessionId 时间戳标识
//     * @return SddResponse
//     */
//    SddResponse saveBankCardCode(SddBindcardVerifReq sddBindCardReq, Long sessionId);
//
//    /**
//     * 进件
//     *
//     * @param sddPushOrderReq 请求
//     * @param sessionId 时间戳标识
//     * @return SddResponse
//     */
//    SddResponse saveOrder(SddPushOrderReq sddPushOrderReq, Long sessionId);
//
//    /**
//     * 借款合同
//     *
//     * @param sddCommonReq 请求
//     * @param sessionId 时间戳标识
//     * @return SddResponse
//     */
//    SddResponse getContract(SddCommonReq sddCommonReq, Long sessionId);
//
//    /**
//     * 订单信息查询
//     *
//     * @param sddCommonReq 请求
//     * @param sessionId 时间戳标识
//     * @return SddResponse
//     */
//    SddResponse getOrderInfo(SddCommonReq sddCommonReq, Long sessionId);
//
//    /**
//     * 试算
//     *
//     * @param sddLoanCalculateReq 请求
//     * @param sessionId 时间戳标识
//     * @return SddResponse
//     */
//    SddResponse trial(SddLoanCalculateReq sddLoanCalculateReq, Long sessionId);
//
//    /**
//     * 主动还款
//     *
//     * @param sddCommonReq 请求
//     * @param sessionId 时间戳标识
//     * @return SddResponse
//     */
//    SddResponse updateRepayment(SddCommonReq sddCommonReq, Long sessionId);
//
//
//}
