//package com.waterelephant.sxyDrainage.service;
//
//import com.waterelephant.sxyDrainage.entity.wacai.WaCaiCommonReq;
//import com.waterelephant.sxyDrainage.entity.wacai.WaCaiResponse;
//
//
///**
// * 挖财渠道接口
// *
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/29
// * @since JDK 1.8
// */
//public interface WaCaiService {
//
//
//
//    /**
//     * 用户过滤
//     *
//     * @param sessionId 时间戳标记
//     * @param waCaiCommonReq 请求参数
//     *
//     * @return 响应
//     */
//    WaCaiResponse checkUser(Long sessionId, WaCaiCommonReq waCaiCommonReq);
//
//    /**
//     * 进件
//     *
//     * @param sessionId 时间戳标记
//     * @param waCaiOrderReq 进件请求
//     *
//     * @return 响应
//     */
//    WaCaiResponse saveOrder(Long sessionId, WaCaiCommonReq waCaiCommonReq) throws Exception;
//
//    /**
//     * 预绑卡接口
//     *
//     * @param sessionId 时间戳标记
//     * @param waCaiCommonReq 请求参数
//     *
//     * @return 响应
//     */
//    WaCaiResponse saveBankCard(Long sessionId, WaCaiCommonReq waCaiCommonReq);
//
//    /**
//     * 验证码绑卡接口
//     *
//     * @param sessionId 时间戳标记
//     * @param waCaiCommonReq 请求参数
//     *
//     * @return 响应
//     */
//    WaCaiResponse saveBankCardCode(Long sessionId, WaCaiCommonReq waCaiCommonReq);
//
//    /**
//     * 试算接口
//     *
//     * @param sessionId 时间戳标记
//     * @param waCaiCommonReq 请求参数
//     *
//     * @return 响应
//     */
//    WaCaiResponse trial(Long sessionId, WaCaiCommonReq waCaiCommonReq);
//
//    /**
//     * 挖财签约接口
//     *
//     * @param sessionId 时间戳
//     * @param waCaiCommonReq 请求参数
//     * @return 响应
//     */
//    WaCaiResponse saveSign(Long sessionId, WaCaiCommonReq waCaiCommonReq);
//
//    /**
//     * 合同接口
//     *
//     * @param sessionId 时间戳标记
//     * @param waCaiCommonReq 请求参数
//     *
//     * @return 响应
//     */
//    WaCaiResponse getContract(Long sessionId, WaCaiCommonReq waCaiCommonReq);
//
//    /**
//     * 授信接口查询
//     *
//     * @param sessionId 时间戳标记
//     * @param waCaiCommonReq 请求参数
//     * @return 响应
//     */
//    WaCaiResponse getApprovalResult(Long sessionId, WaCaiCommonReq waCaiCommonReq);
//
//    /**
//     * 订单状态拉取接口
//     *
//     * @param sessionId 时间戳标记
//     * @param waCaiCommonReq 请求参数
//     *
//     * @return 响应
//     */
//    WaCaiResponse getOrder(Long sessionId, WaCaiCommonReq waCaiCommonReq);
//
//    /**
//     * 主动还款接口
//     *
//     * @param sessionId 时间戳标记
//     * @param waCaiCommonReq 请求参数
//     *
//     * @return 响应
//     */
//    WaCaiResponse updateRepayment(Long sessionId, WaCaiCommonReq waCaiCommonReq);
//
//
//
//}
