//package com.waterelephant.sxyDrainage.service;
//
//import com.waterelephant.sxyDrainage.entity.xianjinbaika.XianJinBaiKaCommonRequest;
//import com.waterelephant.sxyDrainage.entity.xianjinbaika.XianJinBaiKaResponse;
//
///**
// * Module: (code:xjbk)
// * <p>
// * XianJinBaiKaService.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
//public interface XianJinBaiKaService {
//    /**
//     * 1.3.1. 用户过滤
//     *
//     * @param sessionId                 时间戳
//     * @param xianJinBaiKaCommonRequest 请求参数
//     * @return XianJinBaiKaResponse
//     */
//    XianJinBaiKaResponse checkUser(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest);
//
//    /**
//     * 1.3.5. 获取绑卡银行列表
//     *
//     * @param sessionId 时间戳
//     * @return XianJinBaiKaResponse
//     */
//    XianJinBaiKaResponse getValidBankList(long sessionId);
//
//    /**
//     * 1.3.6. 订单绑卡接口
//     *
//     * @param sessionId                 时间戳
//     * @param xianJinBaiKaCommonRequest 请求参数
//     * @return XianJinBaiKaResponse
//     */
//    XianJinBaiKaResponse updateApplyBindCard(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest);
//
//    /**
//     * 1.3.15. 获取用户已绑银行卡
//     *
//     * @param sessionId                 时间戳
//     * @param xianJinBaiKaCommonRequest 请求参数
//     * @return XianJinBaiKaResponse
//     */
//    XianJinBaiKaResponse getUserBindBankCardList(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest);
//
//    /**
//     * 1.3.11. 合同获取接口
//     *
//     * @param sessionId                 时间戳
//     * @param xianJinBaiKaCommonRequest 公共参数对象
//     * @return XianJinBaiKaResponse
//     */
//    XianJinBaiKaResponse getContracts(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest);
//
//    /**
//     * 1.3.12. 拉取还款计划
//     *
//     * @param sessionId                 时间戳
//     * @param xianJinBaiKaCommonRequest 公共参数对象
//     * @return XianJinBaiKaResponse
//     */
//    XianJinBaiKaResponse getRepayplan(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest);
//
//    /**
//     * 1.3.13. 拉取订单状态
//     *
//     * @param sessionId                 时间戳
//     * @param xianJinBaiKaCommonRequest 公共参数对象
//     * @return XianJinBaiKaResponse
//     */
//    XianJinBaiKaResponse getOrderStatus(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest);
//
//    /**
//     * 1.3.14. 借款试算接口
//     *
//     * @param sessionId                 时间戳
//     * @param xianJinBaiKaCommonRequest 公共参数对象
//     * @return XianJinBaiKaResponse
//     */
//    XianJinBaiKaResponse loanCalculate(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest);
//
//    /**
//     * 1.3.9. 订单还款接口
//     *
//     * @param sessionId                 时间戳
//     * @param xianJinBaiKaCommonRequest 公共参数对象
//     * @return XianJinBaiKaResponse
//     */
//    XianJinBaiKaResponse updateApplyRepay(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest);
//
//    /**
//     * 1.3.21. H5认证状态查询接口
//     *
//     * @param sessionId                 时间戳
//     * @param xianJinBaiKaCommonRequest 公共参数对象
//     * @return XianJinBaiKaResponse
//     */
//    XianJinBaiKaResponse authStatus(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest);
//
//    /**
//     * 1.3.6. 订单绑卡接口需要验证码
//     *
//     * @param sessionId                 时间戳
//     * @param xianJinBaiKaCommonRequest 公共参数对象
//     * @return XianJinBaiKaResponse
//     */
//    XianJinBaiKaResponse updateApplyBindCardNew(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest);
//
//    /**
//     * 1.3.9. 订单还款接口新
//     *
//     * @param sessionId                 时间戳
//     * @param xianJinBaiKaCommonRequest 公共参数对象
//     * @return XianJinBaiKaResponse
//     */
//    XianJinBaiKaResponse updateApplyRepayNew(long sessionId, XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest);
//}
