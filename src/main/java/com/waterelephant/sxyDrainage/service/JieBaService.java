//package com.waterelephant.sxyDrainage.service;
//
//import com.waterelephant.sxyDrainage.entity.jieba.*;
//
///**
// * (code:jb001)
// *
// * @Author: ZhangChong
// * @Description:
// * @Date: Created in 15:33 2018/6/13
// * @Modified By:
// */
//public interface JieBaService {
//    /**
//     * 检查用户
//     *
//     * @param sessionId 时间戳
//     * @param checkUser 参数
//     * @return JieBaResponse
//     */
//    JieBaResponse checkUser(Long sessionId, CheckUser checkUser);
//
//    /**
//     * 推送基本信息
//     *
//     * @param sessionId     时间戳
//     * @param pushOrderInfo 参数
//     * @return JieBaResponse
//     */
//    JieBaResponse savePushOrderInfo(Long sessionId, PushOrderInfo pushOrderInfo);
//
//    /**
//     * 推送补充信息
//     *
//     * @param sessionId        时间戳
//     * @param pushAddOrderInfo 参数
//     * @return JieBaResponse
//     */
//    JieBaResponse savePushAddOrderInfo(Long sessionId, PushAddOrderInfo pushAddOrderInfo);
//
//    /**
//     * 预绑卡
//     *
//     * @param sessionId    时间戳
//     * @param bindCardInfo 参数
//     * @return JieBaResponse
//     */
//    JieBaResponse updateBindCardReady(Long sessionId, BindCardInfo bindCardInfo);
//
//    /**
//     * 确认绑卡
//     *
//     * @param sessionId    时间戳
//     * @param bindCardInfo 参数
//     * @return JieBaResponse
//     */
//    JieBaResponse updateBindCardSure(Long sessionId, BindCardInfo bindCardInfo);
//
//    /**
//     * 拉取审批结论
//     *
//     * @param sessionId      时间戳
//     * @param commonPullInfo 参数
//     * @return JieBaResponse
//     */
//    JieBaResponse pullApproveResult(Long sessionId, CommonPullInfo commonPullInfo);
//
//    /**
//     * 拉取还款计划
//     *
//     * @param sessionId      时间戳
//     * @param commonPullInfo 参数
//     * @return JieBaResponse
//     */
//    JieBaResponse pullRepaymentPlan(Long sessionId, CommonPullInfo commonPullInfo);
//
//    /**
//     * 拉取订单状态
//     *
//     * @param sessionId      时间戳
//     * @param commonPullInfo 参数
//     * @return JieBaResponse
//     */
//    JieBaResponse pullOrderStatus(Long sessionId, CommonPullInfo commonPullInfo);
//
//    /**
//     * 主动还款
//     *
//     * @param sessionId 时间戳
//     * @param repayInfo 参数
//     * @return JieBaResponse
//     */
//    JieBaResponse updateApplyRepay(Long sessionId, RepayInfo repayInfo);
//}
