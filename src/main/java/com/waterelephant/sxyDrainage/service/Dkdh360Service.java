//package com.waterelephant.sxyDrainage.service;
//
//import com.waterelephant.sxyDrainage.entity.dkdh360.*;
//
///**
// * (code:dkdh001)
// *
// * @Author: zhangchong
// * @Date: 2018/7/25 16:58
// * @Description: 360贷款导航接口
// */
//public interface Dkdh360Service {
//    /**
//     * 1.查询复贷和黑名单信息
//     *
//     * @param sessionId 时间戳
//     * @param checkInfo 请求参数
//     * @return Dkdh360Response
//     */
//    Dkdh360Response checkUser(long sessionId, CheckInfo checkInfo);
//
//    /**
//     * 2.推送用户借款基本信息
//     *
//     * @param sessionId    时间戳
//     * @param loanBaseInfo 请求参数
//     * @return Dkdh360Response
//     * @throws Exception 异常
//     */
//    Dkdh360Response savePushLoanBaseInfo(long sessionId, LoanBaseInfo loanBaseInfo) throws Exception;
//
//    /**
//     * 3.推送用户借款补充信息
//     *
//     * @param sessionId   时间戳
//     * @param loanAddInfo 请求参数
//     * @return Dkdh360Response
//     */
//    Dkdh360Response savePushLoanAddInfo(long sessionId, LoanAddInfo loanAddInfo);
//
//    /**
//     * 4.获取银行卡列表
//     *
//     * @param sessionId   时间戳
//     * @param commonQuery 请求参数
//     * @return Dkdh360Response
//     */
//    Dkdh360Response getBankCardInfo(long sessionId, CommonQuery commonQuery);
//
//    /**
//     * 5.推送用户绑定银行卡
//     *
//     * @param sessionId    时间戳
//     * @param bankCardInfo 请求参数
//     * @return Dkdh360Response
//     */
//    Dkdh360Response updateBindCardReady(long sessionId, BankCardInfo bankCardInfo);
//
//    /**
//     * 6.推送用户验证银行卡
//     *
//     * @param sessionId    时间戳
//     * @param bankCardInfo 请求参数
//     * @return Dkdh360Response
//     */
//    Dkdh360Response updateBindCardSure(long sessionId, BankCardInfo bankCardInfo);
//
//    /**
//     * 7.查询审批结论
//     *
//     * @param sessionId   时间戳
//     * @param commonQuery 请求参数
//     * @return Dkdh360Response
//     */
//    Dkdh360Response queryApprovalStatus(long sessionId, CommonQuery commonQuery);
//
//    /**
//     * 8.试算接口
//     *
//     * @param sessionId   时间戳
//     * @param commonQuery 请求参数
//     * @return Dkdh360Response
//     */
//    Dkdh360Response trial(long sessionId, CommonQuery commonQuery);
//
//    /**
//     * 9.推送用户确认收款信息
//     *
//     * @param sessionId   时间戳
//     * @param confirmInfo 请求参数
//     * @return Dkdh360Response
//     */
//    Dkdh360Response pushConfirm(long sessionId, ConfirmInfo confirmInfo);
//
//    /**
//     * 10.查询借款合同
//     *
//     * @param sessionId   时间戳
//     * @param commonQuery 请求参数
//     * @return Dkdh360Response
//     */
//    Dkdh360Response queryContractUrl(long sessionId, CommonQuery commonQuery);
//
//    /**
//     * 11.查询还款计划
//     *
//     * @param sessionId   时间戳
//     * @param commonQuery 请求参数
//     * @return Dkdh360Response
//     */
//    Dkdh360Response queryRepaymentPlan(long sessionId, CommonQuery commonQuery);
//
//    /**
//     * 12.查询还款详情
//     *
//     * @param sessionId 时间戳
//     * @param repayInfo 请求参数
//     * @return Dkdh360Response
//     */
//    Dkdh360Response queryRepayInfo(long sessionId, RepayInfo repayInfo);
//
//    /**
//     * 13.推送用户还款信息
//     *
//     * @param sessionId 时间戳
//     * @param repayInfo 请求参数
//     * @return Dkdh360Response
//     */
//    Dkdh360Response updatePushRepayInfo(long sessionId, RepayInfo repayInfo);
//
//    /**
//     * 14.查询订单状态
//     *
//     * @param sessionId   时间戳
//     * @param commonQuery 请求参数
//     * @return Dkdh360Response
//     */
//    Dkdh360Response queryOrderStatus(long sessionId, CommonQuery commonQuery);
//}
